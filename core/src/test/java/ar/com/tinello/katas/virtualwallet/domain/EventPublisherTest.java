package ar.com.tinello.katas.virtualwallet.domain;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import ar.com.tinello.katas.virtualwallet.CustomerMother;
import ar.com.tinello.katas.virtualwallet.InMemoryDB;
import ar.com.tinello.katas.virtualwallet.checkingAccount.actions.NewCheckingAccount;
import ar.com.tinello.katas.virtualwallet.checkingAccount.infrastructure.InMemoryAccounts;
import ar.com.tinello.katas.virtualwallet.customer.domain.CustomerWasCreatedEvent;

class EventPublisherTest {

	
	@Test
	void subscribe_and_publish_customer_create_event() {
		
		final var accounts = new InMemoryAccounts(new InMemoryDB());
		
		final var eventPublish = new EventPublisher();
		
		final var newCheckingAccount = new NewCheckingAccount(accounts);
		eventPublish.subscribe(CustomerWasCreatedEvent.class, newCheckingAccount::execute);
		
		final var customer = CustomerMother.createCustomerWithEvent();
		eventPublish.publish(customer.getEvents().get(0));
		
		
		final var account = accounts.getCheckingAccountDetails(customer.getId()).blockingGet();
		
		final var softly = new SoftAssertions();
		softly.assertThat(account.getCustomerId()).isEqualTo(customer.getId());
		softly.assertAll();
	}
	
}
