package davidev.notificacao.domain.transacaodocument;

import davidev.notificacao.domain.transacaodocument.output.GastoPorContaDestinoOutput;
import davidev.notificacao.domain.transacaodocument.output.StatsGastosPorContaDestinoOutput;

import java.time.LocalDateTime;
import java.util.List;

public interface TransacaoSearchRepository {

    StatsGastosPorContaDestinoOutput calcularGastosPorContaDestino(LocalDateTime dataInicio, LocalDateTime dataFim, int topN);
}
