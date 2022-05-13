package ar.com.tinello.katas.virtualwallet.checkingAccount.actions;

import ar.com.tinello.katas.virtualwallet.checkingAccount.domain.Accounts;
import ar.com.tinello.katas.virtualwallet.customer.domain.Customer;
import io.reactivex.Observable;

public class GetCustomerDetails {

    private final Accounts accounts;

    public GetCustomerDetails(final Accounts accounts) {
        this.accounts = accounts;
    }

    public Observable<Customer> execute(final String customerId) {
        return accounts.getCustomerDetails(customerId);
    }

}
