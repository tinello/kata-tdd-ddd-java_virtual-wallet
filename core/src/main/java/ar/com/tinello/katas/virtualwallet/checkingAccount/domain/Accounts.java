package ar.com.tinello.katas.virtualwallet.checkingAccount.domain;

import ar.com.tinello.katas.virtualwallet.customer.domain.Customer;
import ar.com.tinello.katas.virtualwallet.domain.Money;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface Accounts {

    void add(final CheckingAccount checkingAccount);

    Single<CheckingAccount> getById(final String id);

    Observable<Credit> getCredits(final String customerId);

    Single<CheckingAccount> getCheckingAccountByCustomer(final String customerId);

    void addCredit(final CheckingAccount account, final Money money);

    void addDebit(final CheckingAccount account, final Money money);

    Observable<Debit> getDebits(final String customerId);

    void put(final CheckingAccount account);

    Single<CheckingAccount> getCheckingAccountDetails(final String customerId);

    Observable<Customer> getCustomerDetails(final String customerId);

}
