package davidev.notificacao.domain.transacaodocument.output;

import lombok.Builder;

import java.util.List;

@Builder
public record StatsGastosPorContaDestinoOutput(
        List<GastoPorContaDestinoOutput> resultados
) {
}
