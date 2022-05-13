package ar.com.tinello.katas.virtualwallet;

import ar.com.tinello.katas.virtualwallet.customer.domain.Customers;
import ar.com.tinello.katas.virtualwallet.customer.infrastructure.InMemoryCustomers;

public class CustomersMother {

	public static Customers createRepository(InMemoryDB inMemoryDB) {
		return new InMemoryCustomers(inMemoryDB);
	}

}
