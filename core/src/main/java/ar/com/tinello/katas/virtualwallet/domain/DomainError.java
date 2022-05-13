package ar.com.tinello.katas.virtualwallet.domain;

public class DomainError extends RuntimeException {
    private static final long serialVersionUID = 7010156292315593079L;

    public DomainError(final String message) {
        super(message);
    }

}
