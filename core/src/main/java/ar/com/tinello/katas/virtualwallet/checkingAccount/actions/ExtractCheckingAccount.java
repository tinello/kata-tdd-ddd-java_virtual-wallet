package ar.com.tinello.katas.virtualwallet.checkingAccount.actions;

import ar.com.tinello.katas.virtualwallet.checkingAccount.domain.Accounts;
import ar.com.tinello.katas.virtualwallet.domain.Money;

public class ExtractCheckingAccount {

    private Accounts accounts;

    public ExtractCheckingAccount(final Accounts accounts) {
        this.accounts = accounts;
    }

    public void execute(final String customerId, final Money money) {
        final var accountSingle = accounts.getCheckingAccountByCustomer(customerId);

        /* TODO investigar como hacer para lanzar la excepcion para arriba
        accountSingle.subscribe(account -> {
            account.subtractBalance(money);
            accounts.addDebit(account, money);
            }
        );
        */

        final var account = accountSingle.blockingGet();
        account.subtractBalance(money);
        accounts.addDebit(account, money);
    }

}
