package davidev.notificacao.domain.transacaodocument.output;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record GastoPorContaDestinoOutput(
        UUID contaDestinoId,
        long quantidade,
        BigDecimal total
) {
}
