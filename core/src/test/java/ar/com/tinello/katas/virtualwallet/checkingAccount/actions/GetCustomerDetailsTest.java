package ar.com.tinello.katas.virtualwallet.checkingAccount.actions;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.com.tinello.katas.virtualwallet.AccountsMother;
import ar.com.tinello.katas.virtualwallet.CustomerMother;
import ar.com.tinello.katas.virtualwallet.CustomersMother;
import ar.com.tinello.katas.virtualwallet.InMemoryDB;
import ar.com.tinello.katas.virtualwallet.MoneyMother;
import ar.com.tinello.katas.virtualwallet.checkingAccount.domain.Accounts;
import ar.com.tinello.katas.virtualwallet.customer.domain.Customer;
import ar.com.tinello.katas.virtualwallet.customer.domain.Customers;

class GetCustomerDetailsTest {

    private Customer customer;
    private Accounts accounts;
    private Customers customers;


    @BeforeEach
    void before_each() {
    	InMemoryDB inMemoryDB = new InMemoryDB();
        customer = CustomerMother.createCustomer();
        accounts = AccountsMother.createRepository(inMemoryDB);
        customers = CustomersMother.createRepository(inMemoryDB);
    }

    @Test
    void get_the_customer_details() {
    	
    	customers.add(customer);
    	
        final var newCheckingAccount = new NewCheckingAccount(accounts);
        final var account = newCheckingAccount.execute(customer.getId()).blockingGet();
        final var depositCheckingAccount = new DepositCheckingAccount(accounts);
        depositCheckingAccount.execute(customer.getId(), MoneyMother.amount10());
        final var extractCheckingAccount = new ExtractCheckingAccount(accounts);
        extractCheckingAccount.execute(customer.getId(), MoneyMother.amount10());


        final var getCustomerDetails = new GetCustomerDetails(accounts);
        final var customerDetails = getCustomerDetails.execute(customer.getId()).blockingFirst();

        final var softly = new SoftAssertions();
        softly.assertThat(customerDetails.getId()).isEqualTo(customer.getId());
        softly.assertThat(customerDetails.getAccount().getId()).isEqualTo(account.getId());
        softly.assertThat(customerDetails.getAccount().getCredits().size()).isEqualTo(1);
        softly.assertThat(customerDetails.getAccount().getDebits().size()).isEqualTo(1);
        softly.assertAll();
    }
}
