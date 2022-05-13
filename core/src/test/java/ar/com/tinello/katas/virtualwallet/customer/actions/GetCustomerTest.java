package ar.com.tinello.katas.virtualwallet.customer.actions;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import ar.com.tinello.katas.virtualwallet.CustomerMother;
import ar.com.tinello.katas.virtualwallet.InMemoryDB;
import ar.com.tinello.katas.virtualwallet.customer.infrastructure.InMemoryCustomers;
import ar.com.tinello.katas.virtualwallet.domain.EventPublisher;

class GetCustomerTest {

	@Test
	void get_customer_by_id() {
		final var customers = new InMemoryCustomers(new InMemoryDB());
		
		final var createCustomer = new CreateCustomer(customers, new EventPublisher());
		final var customer = CustomerMother.createCustomer();
        createCustomer.execute(customer.getId(), customer.getFirstName(), customer.getLastName(), 
        		customer.getPersonalNumber(), customer.getPersonalPhoneNumber());
		
		
		final var getCustomer = new GetCustomer(customers);
		
		final var customerNew = getCustomer.execute(customer.getId()).blockingGet();
		
		final var softly = new SoftAssertions();
		softly.assertThat(customer).usingRecursiveComparison().isEqualTo(customerNew);
		softly.assertAll();
	}
}
