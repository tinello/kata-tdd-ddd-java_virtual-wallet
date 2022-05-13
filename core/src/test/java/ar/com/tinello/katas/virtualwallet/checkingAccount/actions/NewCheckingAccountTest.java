package ar.com.tinello.katas.virtualwallet.checkingAccount.actions;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.com.tinello.katas.virtualwallet.AccountsMother;
import ar.com.tinello.katas.virtualwallet.CustomerMother;
import ar.com.tinello.katas.virtualwallet.InMemoryDB;
import ar.com.tinello.katas.virtualwallet.checkingAccount.domain.Accounts;
import ar.com.tinello.katas.virtualwallet.customer.domain.Customer;

class NewCheckingAccountTest {

    private Customer customer;
    private Accounts accounts;


    @BeforeEach
    void before_each() {
        customer = CustomerMother.createCustomer();
        accounts = AccountsMother.createRepository(new InMemoryDB());
    }

    @Test
    void a_customer_could_register_a_new_checking_account_using_its_personal_details() {
        final var newCheckingAccount = new NewCheckingAccount(accounts);

        final var checkingAccountSingle = newCheckingAccount.execute(customer.getId());
        final var checkingAccount = checkingAccountSingle.blockingGet();


        final var repoCheckingAccount = accounts.getById(checkingAccount.getId()).blockingGet();
        final var softly = new SoftAssertions();
        softly.assertThat(checkingAccount).usingRecursiveComparison().isEqualTo(repoCheckingAccount);
        softly.assertThat(checkingAccount.getPrintOpeningDate()).isEqualTo(repoCheckingAccount.getPrintOpeningDate());
        softly.assertAll();
    }
}
