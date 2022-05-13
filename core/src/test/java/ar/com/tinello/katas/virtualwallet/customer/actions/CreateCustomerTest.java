package ar.com.tinello.katas.virtualwallet.customer.actions;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import ar.com.tinello.katas.virtualwallet.InMemoryDB;
import ar.com.tinello.katas.virtualwallet.customer.domain.Customer;
import ar.com.tinello.katas.virtualwallet.customer.domain.CustomerWasCreatedEvent;
import ar.com.tinello.katas.virtualwallet.customer.infrastructure.InMemoryCustomers;
import ar.com.tinello.katas.virtualwallet.domain.EventPublisher;

class CreateCustomerTest {

    @Test
    void create_customer() {
    	final var customers = new InMemoryCustomers(new InMemoryDB());
    	final var eventPublisher = new EventPublisher();
    	final var createCustomer = new CreateCustomer(customers, eventPublisher);
        createCustomer.execute("1", "First Name", "Last Name", "Personal Number", "Personal Phone Number");
        final var customer = customers.getById("1").blockingGet();
        
        final var softly = new SoftAssertions();
        softly.assertThat(customer.getId()).isEqualTo("1");
        softly.assertThat(customer.getFirstName()).isEqualTo("First Name");
        softly.assertThat(customer.getLastName()).isEqualTo("Last Name");
        softly.assertThat(customer.getPersonalNumber()).isEqualTo("Personal Number");
        softly.assertThat(customer.getPersonalPhoneNumber()).isEqualTo("Personal Phone Number");
        softly.assertAll();
    }
    
    
    @Test
    void create_event_on_new_customer() {
    	final var customer = Customer.createWithEvents("1", "First Name", "Last Name", "Personal Number", "Personal Phone Number", null);
    	final var events = customer.getEvents();
    	final var event = events.get(0);
    	
    	final var softly = new SoftAssertions();
    	softly.assertThat(events.size()).isEqualTo(1);
    	softly.assertThat(event).isInstanceOf(CustomerWasCreatedEvent.class);
    	softly.assertAll();
    }

}
