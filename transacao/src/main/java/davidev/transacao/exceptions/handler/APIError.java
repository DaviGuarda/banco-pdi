package davidev.transacao.exceptions.handler;

import java.time.Instant;

public record APIError(Instant timestamp,
                       Integer status,
                       String mensagem) {
}
