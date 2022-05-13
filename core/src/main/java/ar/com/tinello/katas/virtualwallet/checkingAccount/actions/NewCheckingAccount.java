package ar.com.tinello.katas.virtualwallet.checkingAccount.actions;

import ar.com.tinello.katas.virtualwallet.SystemUUID;
import ar.com.tinello.katas.virtualwallet.checkingAccount.domain.Accounts;
import ar.com.tinello.katas.virtualwallet.checkingAccount.domain.CheckingAccount;
import io.reactivex.Single;

public class NewCheckingAccount {

    private final Accounts accounts;
    private final SystemUUID systemUUID;

    public NewCheckingAccount(final Accounts accounts) {
        this.systemUUID = new SystemUUID();
        this.accounts = accounts;
    }

    public Single<CheckingAccount> execute(final String customerId) {
        final var checkingAccount = new CheckingAccount(systemUUID.randomUUID(), customerId);

        accounts.add(checkingAccount);

        return Single.just(checkingAccount);
    }

}
