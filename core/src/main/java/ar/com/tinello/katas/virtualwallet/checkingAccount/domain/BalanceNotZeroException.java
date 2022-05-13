package ar.com.tinello.katas.virtualwallet.checkingAccount.domain;

import ar.com.tinello.katas.virtualwallet.domain.DomainError;

public class BalanceNotZeroException extends DomainError {

    private static final long serialVersionUID = -8439882033408304062L;

	public BalanceNotZeroException(final String message) {
        super(message);
    }

}
