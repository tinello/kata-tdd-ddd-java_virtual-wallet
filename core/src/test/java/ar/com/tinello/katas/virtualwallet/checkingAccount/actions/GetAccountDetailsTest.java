package ar.com.tinello.katas.virtualwallet.checkingAccount.actions;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.com.tinello.katas.virtualwallet.AccountsMother;
import ar.com.tinello.katas.virtualwallet.CustomerMother;
import ar.com.tinello.katas.virtualwallet.InMemoryDB;
import ar.com.tinello.katas.virtualwallet.MoneyMother;
import ar.com.tinello.katas.virtualwallet.checkingAccount.domain.Accounts;
import ar.com.tinello.katas.virtualwallet.customer.domain.Customer;

class GetAccountDetailsTest {

    private Customer customer;
    private Accounts accounts;


    @BeforeEach
    void before_each() {
        customer = CustomerMother.createCustomer();
        accounts = AccountsMother.createRepository(new InMemoryDB());
    }

    @Test
    void get_the_account_details() {
        final var newCheckingAccount = new NewCheckingAccount(accounts);
        final var newAccount = newCheckingAccount.execute(customer.getId()).blockingGet();
        final var depositCheckingAccount = new DepositCheckingAccount(accounts);
        depositCheckingAccount.execute(customer.getId(), MoneyMother.amount10());
        final var extractCheckingAccount = new ExtractCheckingAccount(accounts);
        extractCheckingAccount.execute(customer.getId(), MoneyMother.amount10());

        final var getAccountDetails = new GetAccountDetails(accounts);
        final var checkingAccount = getAccountDetails.execute(customer.getId());

        final var account = checkingAccount.blockingGet();

        final var softly = new SoftAssertions();
        softly.assertThat(account.getId()).isEqualTo(newAccount.getId());
        softly.assertThat(account.getCredits().size()).isEqualTo(1);
        softly.assertThat(account.getDebits().size()).isEqualTo(1);
        softly.assertAll();
    }

    @Test
    void get_nonexistent_account() {
        final var account = accounts.getById("nonexistent");

        assertThat(account).isNull();
    }
}
