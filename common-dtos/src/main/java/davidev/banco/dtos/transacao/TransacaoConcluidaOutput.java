package davidev.banco.dtos.transacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransacaoConcluidaOutput(UUID transacaoId,
                                       UUID contaOrigemId,
                                       UUID contaDestinoId,
                                       BigDecimal valor,
                                       LocalDateTime dataHora) {
}
