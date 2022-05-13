package ar.com.tinello.katas.virtualwallet.customer.domain;

import ar.com.tinello.katas.virtualwallet.domain.Event;

public class CustomerWasCreatedEvent extends Event {

	private final String customerId;
	
	public CustomerWasCreatedEvent(final String customerId) {
		super();
		this.customerId = customerId;
	}

	public String getCustomerId() {
		return customerId;
	}

}
