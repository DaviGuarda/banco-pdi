package davidev.notificacao.domain.transacaodocument;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

public interface TransacaoDocumentRepository extends ElasticsearchRepository<TransacaoDocument, UUID> {

    @Query("""
            {
                "bool": {
                    "filter": [
                        {
                            "bool": {
                                "should": [
                                    { "term": { "contaOrigemId": "?0" } },
                                    { "term": { "contaDestinoId": "?0" } }
                                ],
                                "minimum_should_match": 1
                            }
                        },
                        {
                            "range": {
                                "dataHora": {
                                    "gte": "?1",
                                    "lte": "?2"
                                }
                            }
                        }
                    ]
                }
            }
            """)
    Page<TransacaoDocument> findByContaAndDataBetween(UUID contaId, String dataInicio, String dataFim, Pageable pageable);

    @Query("""
            {
                "bool": {
                    "filter": [
                        {
                            "bool": {
                                "should": [
                                    { "match": { "contaOrigemId": "?0" } },
                                    { "match": { "contaDestinoId": "?0" } }
                                ],
                                "minimum_should_match": 1
                            }
                        }
                    ]
                }
            }
            """)
    Page<TransacaoDocument> findByConta(UUID contaId, Pageable pageable);

    @Query("""
            {
                "match": {
                    "descricao": "?0"
                }
            }
            """)
    Page<TransacaoDocument> findByDescricaoContaining(String termo, Pageable pageable);
}
