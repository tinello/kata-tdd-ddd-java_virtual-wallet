package ar.com.tinello.katas.virtualwallet;

import ar.com.tinello.katas.virtualwallet.checkingAccount.domain.Accounts;
import ar.com.tinello.katas.virtualwallet.checkingAccount.infrastructure.InMemoryAccounts;

public class AccountsMother {

    private AccountsMother() {
        super();
    }

    public static Accounts createRepository(InMemoryDB inMemoryDB) {
        return new InMemoryAccounts(inMemoryDB);
    }

}
