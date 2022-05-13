package ar.com.tinello.katas.virtualwallet;

import ar.com.tinello.katas.virtualwallet.customer.domain.Customer;

public class CustomerMother {

    private CustomerMother() {
        super();
    }

    public static Customer createCustomer() {
        final var systemUUID = new SystemUUID();
        return new Customer(systemUUID.randomUUID(), "Darth", "Vader", "1", "5555-5555");
    }
    
    public static Customer createCustomerWithEvent() {
        final var systemUUID = new SystemUUID();
        return Customer.createWithEvents(systemUUID.randomUUID(), "Darth", "Vader", "1", "5555-5555", null);
    }

}
