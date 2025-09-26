package davidev.notificacao.domain.transacaodocument;

import davidev.notificacao.domain.transacaodocument.output.StatsGastosPorContaDestinoOutput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface TransacaoSearchService {

    Page<TransacaoDocument> buscarTransacoes(UUID contaId,
                                             LocalDateTime dataInicio,
                                             LocalDateTime dataFim,
                                             Pageable pageable);

    Page<TransacaoDocument> buscaLivre(String termo, Pageable pageable);

    StatsGastosPorContaDestinoOutput gastosPorContaDestino(LocalDateTime dataInicio,
                                                           LocalDateTime dataFim,
                                                           int topN);
}
