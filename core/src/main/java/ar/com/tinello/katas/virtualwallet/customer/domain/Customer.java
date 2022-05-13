package ar.com.tinello.katas.virtualwallet.customer.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ar.com.tinello.katas.virtualwallet.checkingAccount.domain.CheckingAccount;
import ar.com.tinello.katas.virtualwallet.domain.Event;

public class Customer {

    private final String id;
    private final String firstName;
    private final String lastName;
    private final String personalNumber;
    private final String personalPhoneNumber;
    private CheckingAccount account;
    
    private List<Event> events;

    public Customer(final String customerId, final String firstName, final String lastName, 
    		final String personalNumber, final String personalPhoneNumber) {
    	this(customerId, firstName, lastName, personalNumber, personalPhoneNumber, null);
    }
    
    public Customer(final String customerId, final String firstName, final String lastName, 
    		final String personalNumber, final String personalPhoneNumber, final CheckingAccount account) {
    	this.id = customerId;
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.personalNumber = personalNumber;
    	this.personalPhoneNumber = personalPhoneNumber;
        this.account = account;
        this.events = new ArrayList<>();        
	}

    private Customer(final String customerId, final String firstName, final String lastName, 
    		final String personalNumber, final String personalPhoneNumber, final CheckingAccount account, 
    		final CustomerWasCreatedEvent customerCreateEvent) {
    	this(customerId, firstName, lastName, personalNumber, personalPhoneNumber, account);
        
        events.add(customerCreateEvent);
	}
    
    public static Customer createWithEvents(final String customerId, final String firstName, final String lastName, 
    		final String personalNumber, final String personalPhoneNumber, final CheckingAccount account) {
    	return new Customer(customerId, firstName, lastName, personalNumber, personalPhoneNumber, account, 
    			new CustomerWasCreatedEvent(customerId));
    }
    
	public String getId() {
        return id;
    }

    public CheckingAccount getAccount() {
        return account;
    }

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPersonalNumber() {
		return personalNumber;
	}

	public String getPersonalPhoneNumber() {
		return personalPhoneNumber;
	}

	
	//TODO cambiar nombre, no expresa intencion.
	public List<Event> getEvents() {
		final var tmpEvents = events;
		events = Collections.emptyList();
		return tmpEvents;
	}
}
