package ar.com.tinello.katas.virtualwallet.customer.actions;

import ar.com.tinello.katas.virtualwallet.customer.domain.Customer;
import ar.com.tinello.katas.virtualwallet.customer.domain.Customers;
import ar.com.tinello.katas.virtualwallet.domain.EventPublisher;

public class CreateCustomer {

	private final Customers customers;
	private final EventPublisher eventPublisher;
	
	public CreateCustomer(final Customers customers, final EventPublisher eventPublisher) {
		this.customers = customers;
		this.eventPublisher = eventPublisher;
	}

	public void execute(final String customerId, final String firstName, final String lastName, 
			final String personalNumber, final String personalPhoneNumber) {
		final var customer = Customer.createWithEvents(customerId, firstName, lastName, personalNumber, personalPhoneNumber, null);
		final var events = customer.getEvents();
		customers.add(customer);
		
		events.forEach(eventPublisher::publish);
	}
}
