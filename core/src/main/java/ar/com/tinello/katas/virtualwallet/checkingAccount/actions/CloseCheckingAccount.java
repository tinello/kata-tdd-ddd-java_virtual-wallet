package ar.com.tinello.katas.virtualwallet.checkingAccount.actions;

import ar.com.tinello.katas.virtualwallet.checkingAccount.domain.Accounts;

public class CloseCheckingAccount {

    private final Accounts accounts;

    public CloseCheckingAccount(final Accounts accounts) {
        this.accounts = accounts;
    }

    public void execute(final String customerId) {
        final var accountSingle = accounts.getCheckingAccountByCustomer(customerId);

        /* TODO investigar como hacer para lanzar la excepcion para arriba
        accountSingle.subscribe(account -> {
            account.close();
            accounts.put(account);
            });
        */

        final var account = accountSingle.blockingGet();
        account.close();
        accounts.put(account);
    }

}
