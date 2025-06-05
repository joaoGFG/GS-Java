package fiap.tds.exception;

public class AvaliacaoInvalidaException extends RuntimeException {
    public AvaliacaoInvalidaException(String message) {
        super(message);
    }

    public AvaliacaoInvalidaException(String message, Throwable cause) {
        super(message, cause);
    }
}
