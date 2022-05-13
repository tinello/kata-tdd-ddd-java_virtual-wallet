package ar.com.tinello.katas.virtualwallet.customer.infrastructure;

import com.google.gson.Gson;

import ar.com.tinello.katas.virtualwallet.InMemoryDB;
import ar.com.tinello.katas.virtualwallet.customer.domain.Customer;
import ar.com.tinello.katas.virtualwallet.customer.domain.Customers;
import io.reactivex.Single;

public class InMemoryCustomers extends Customers {

	private final InMemoryDB inMemoryDB;
	private final Gson serialization = new Gson();
	
	public InMemoryCustomers(InMemoryDB inMemoryDB) {
		this.inMemoryDB = inMemoryDB;
	}
	
	@Override
	public Single<Customer> getById(final String customerId) {
		String customer = inMemoryDB.getCustomer(customerId);
        if (customer == null) {
            return null;
        }
		return Single.just(serialization.fromJson(customer, Customer.class));
	}

	@Override
	public void add(final Customer customer) {
		inMemoryDB.putCustomer(customer.getId(), serialization.toJson(customer));
	}

}
