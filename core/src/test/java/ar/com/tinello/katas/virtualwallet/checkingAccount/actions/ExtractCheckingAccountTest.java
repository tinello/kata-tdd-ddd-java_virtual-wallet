package ar.com.tinello.katas.virtualwallet.checkingAccount.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.com.tinello.katas.virtualwallet.AccountsMother;
import ar.com.tinello.katas.virtualwallet.CustomerMother;
import ar.com.tinello.katas.virtualwallet.InMemoryDB;
import ar.com.tinello.katas.virtualwallet.MoneyMother;
import ar.com.tinello.katas.virtualwallet.checkingAccount.domain.Accounts;
import ar.com.tinello.katas.virtualwallet.checkingAccount.domain.InsufficientFundsException;
import ar.com.tinello.katas.virtualwallet.customer.domain.Customer;

class ExtractCheckingAccountTest {

    private Customer customer;
    private Accounts accounts;


    @BeforeEach
    void before_each() {
        customer = CustomerMother.createCustomer();
        accounts = AccountsMother.createRepository(new InMemoryDB());
    }

    @Test
    void a_customer_to_withdraw_funds_from_an_existing_account() {
        final var newCheckingAccount = new NewCheckingAccount(accounts);
        newCheckingAccount.execute(customer.getId());

        final var depositCheckingAccount = new DepositCheckingAccount(accounts);
        depositCheckingAccount.execute(customer.getId(), MoneyMother.amount10());

        final var extractCheckingAccount = new ExtractCheckingAccount(accounts);
        extractCheckingAccount.execute(customer.getId(), MoneyMother.amount10());


        final var debits = accounts.getDebits(customer.getId());

        assertThat(debits.count().blockingGet()).isEqualTo(1);

        debits.subscribe(debit -> assertThat(debit.getAmount()).isEqualTo(new BigDecimal("10")) );
    }


    @Test
    void do_not_allow_the_customer_to_withdraw_more_than_the_existing_funds() {
        final var newCheckingAccount = new NewCheckingAccount(accounts);
        newCheckingAccount.execute(customer.getId());


        final var extractCheckingAccount = new ExtractCheckingAccount(accounts);
        final var thrown = catchThrowable(() -> extractCheckingAccount.execute(customer.getId(), MoneyMother.amount10()) );
        assertThat(thrown).isInstanceOf(InsufficientFundsException.class);
    }
}
