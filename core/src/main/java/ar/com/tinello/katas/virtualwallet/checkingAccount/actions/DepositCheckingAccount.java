package ar.com.tinello.katas.virtualwallet.checkingAccount.actions;

import ar.com.tinello.katas.virtualwallet.checkingAccount.domain.Accounts;
import ar.com.tinello.katas.virtualwallet.domain.Money;

public class DepositCheckingAccount {

    private Accounts accounts;

    public DepositCheckingAccount(final Accounts accounts) {
        this.accounts = accounts;
    }

    public void execute(final String customerId, final Money money) {
        final var accountSingle = accounts.getCheckingAccountByCustomer(customerId);

        accountSingle.subscribe(account -> {
            account.plusBalance(money);
            accounts.addCredit(account, money);
        });
    }

}
