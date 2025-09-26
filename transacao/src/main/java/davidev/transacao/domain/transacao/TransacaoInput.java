package davidev.transacao.domain.transacao;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record TransacaoInput(
        @NotNull UUID contaOrigemId,
        @NotNull UUID contaDestinoId,
        @NotNull @Positive BigDecimal valor
) {
}
