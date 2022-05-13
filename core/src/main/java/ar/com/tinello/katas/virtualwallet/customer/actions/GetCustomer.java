package ar.com.tinello.katas.virtualwallet.customer.actions;

import ar.com.tinello.katas.virtualwallet.customer.domain.Customer;
import ar.com.tinello.katas.virtualwallet.customer.domain.Customers;
import io.reactivex.Single;

public class GetCustomer {

	private final Customers customers;
	
	public GetCustomer(final Customers customers) {
		this.customers = customers;
	}

	public Single<Customer> execute(final String customerId) {
		return customers.getById(customerId);
	}

}
