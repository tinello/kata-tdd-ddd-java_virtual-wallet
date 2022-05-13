package ar.com.tinello.katas.virtualwallet.checkingAccount.domain;

import ar.com.tinello.katas.virtualwallet.domain.DomainError;

public class InsufficientFundsException extends DomainError {

    private static final long serialVersionUID = -1767312114318658036L;

	public InsufficientFundsException(final String message) {
        super(message);
    }

}
