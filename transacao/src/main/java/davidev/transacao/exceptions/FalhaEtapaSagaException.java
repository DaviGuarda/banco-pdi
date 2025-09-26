package davidev.transacao.exceptions;

public class FalhaEtapaSagaException extends RuntimeException {
    public FalhaEtapaSagaException(String message, Throwable cause) {
        super(message, cause);
    }
}
