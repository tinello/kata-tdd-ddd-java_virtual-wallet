package ar.com.tinello.katas.virtualwallet.checkingAccount.actions;

import ar.com.tinello.katas.virtualwallet.checkingAccount.domain.Accounts;
import ar.com.tinello.katas.virtualwallet.checkingAccount.domain.CheckingAccount;
import io.reactivex.Single;

public class GetAccountDetails {

    private final Accounts accounts;

    public GetAccountDetails(final Accounts accounts) {
        this.accounts = accounts;
    }

    public Single<CheckingAccount> execute(final String customerId) {
        return accounts.getCheckingAccountDetails(customerId);
    }

}
