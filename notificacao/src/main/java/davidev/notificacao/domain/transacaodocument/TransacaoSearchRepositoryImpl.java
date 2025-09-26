package davidev.notificacao.domain.transacaodocument;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.JsonData;
import davidev.notificacao.domain.transacaodocument.output.GastoPorContaDestinoOutput;
import davidev.notificacao.domain.transacaodocument.output.StatsGastosPorContaDestinoOutput;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Repository
@RequiredArgsConstructor
public class TransacaoSearchRepositoryImpl implements TransacaoSearchRepository {

    private static final DateTimeFormatter ES_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private final ElasticsearchClient elasticsearchClient;

    @Override
    @SneakyThrows
    public StatsGastosPorContaDestinoOutput calcularGastosPorContaDestino(LocalDateTime dataInicio, LocalDateTime dataFim, int topN) {
        Query query = Query.of(q -> q
                .bool(b -> {
                    if (isNull(dataInicio) && isNull(dataFim)) {
                        b.must(m -> m.matchAll(ma -> ma));
                    } else {
                        b.filter(f -> f.range(r -> r
                                .untyped(u -> {
                                    u.field("dataHora");
                                    if (nonNull(dataInicio)) {
                                        u.gte(JsonData.of(dataInicio.format(ES_DATE_FORMATTER)));
                                    }
                                    if (nonNull(dataFim)) {
                                        u.lte(JsonData.of(dataFim.format(ES_DATE_FORMATTER)));
                                    }
                                    return u;
                                })
                        ));
                    }
                    return b;
                })
        );

        SearchRequest request = new SearchRequest.Builder()
                .index("transacoes")
                .size(0)
                .query(query)
                .aggregations("por_conta_destino", a -> a
                        .terms(t -> t
                                .field("contaDestinoId")
                                .size(topN)
                        )
                        .aggregations("soma_valor", sa -> sa
                                .sum(s -> s
                                        .field("valor")
                                )
                        )
                )
                .build();

        SearchResponse<Void> response = elasticsearchClient.search(request, Void.class);

        Map<String, Aggregate> aggregations = response.aggregations();
        Aggregate aggregate = aggregations.get("por_conta_destino");

        if (isNull(aggregate) || !aggregate.isSterms()) {
            return new StatsGastosPorContaDestinoOutput(Collections.emptyList());
        }

        List<GastoPorContaDestinoOutput> resultados = aggregate.sterms().buckets().array().stream()
                .map(bucket -> {
                    double valorTotal = bucket.aggregations().get("soma_valor").sum().value();
                    return GastoPorContaDestinoOutput.builder()
                            .contaDestinoId(UUID.fromString(bucket.key().stringValue()))
                            .quantidade(bucket.docCount())
                            .total(BigDecimal.valueOf(valorTotal))
                            .build();
                })
                .collect(Collectors.toList());

        return new StatsGastosPorContaDestinoOutput(resultados);
    }
}