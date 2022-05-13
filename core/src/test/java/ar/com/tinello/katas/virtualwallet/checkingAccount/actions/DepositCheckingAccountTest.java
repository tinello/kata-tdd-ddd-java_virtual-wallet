package ar.com.tinello.katas.virtualwallet.checkingAccount.actions;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.com.tinello.katas.virtualwallet.AccountsMother;
import ar.com.tinello.katas.virtualwallet.CustomerMother;
import ar.com.tinello.katas.virtualwallet.InMemoryDB;
import ar.com.tinello.katas.virtualwallet.MoneyMother;
import ar.com.tinello.katas.virtualwallet.checkingAccount.domain.Accounts;
import ar.com.tinello.katas.virtualwallet.customer.domain.Customer;

class DepositCheckingAccountTest {

    private Customer customer;
    private Accounts accounts;


    @BeforeEach
    void before_each() {
        customer = CustomerMother.createCustomer();
        accounts = AccountsMother.createRepository(new InMemoryDB());
    }

    @Test
    void  a_customer_to_deposit_funds_into_an_existing_checking_account() {
        final var newCheckingAccount = new NewCheckingAccount(accounts);
        newCheckingAccount.execute(customer.getId());


        final var depositCheckingAccount = new DepositCheckingAccount(accounts);
        depositCheckingAccount.execute(customer.getId(), MoneyMother.amount10());



        final var credits = accounts.getCredits(customer.getId());

        assertThat(credits.count().blockingGet()).isEqualTo(1);

        credits.subscribe(credit -> assertThat(credit.getAmount()).isEqualTo(new BigDecimal("10")) );
    }
}
