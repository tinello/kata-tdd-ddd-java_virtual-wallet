package ar.com.tinello.katas.virtualwallet.customer.domain;

import io.reactivex.Single;

public abstract class Customers {
	
	public abstract Single<Customer> getById(final String customerId);

	public abstract void add(final Customer customer);

}
