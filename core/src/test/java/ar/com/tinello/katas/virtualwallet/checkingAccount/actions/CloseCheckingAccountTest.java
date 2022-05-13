package ar.com.tinello.katas.virtualwallet.checkingAccount.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.com.tinello.katas.virtualwallet.AccountsMother;
import ar.com.tinello.katas.virtualwallet.CustomerMother;
import ar.com.tinello.katas.virtualwallet.InMemoryDB;
import ar.com.tinello.katas.virtualwallet.MoneyMother;
import ar.com.tinello.katas.virtualwallet.checkingAccount.domain.Accounts;
import ar.com.tinello.katas.virtualwallet.checkingAccount.domain.BalanceNotZeroException;
import ar.com.tinello.katas.virtualwallet.customer.domain.Customer;

class CloseCheckingAccountTest {

    private Customer customer;
    private Accounts accounts;


    @BeforeEach
    void before_each() {
        customer = CustomerMother.createCustomer();
        accounts = AccountsMother.createRepository(new InMemoryDB());
    }

    @Test
    void a_customer_to_close_a_checking_account_only_if_the_balance_is_zero() {
        final var newCheckingAccount = new NewCheckingAccount(accounts);
        final var checkingAccountSingle = newCheckingAccount.execute(customer.getId());
        final var checkingAccount = checkingAccountSingle.blockingGet();

        final var closeCheckingAccount = new CloseCheckingAccount(accounts);
        closeCheckingAccount.execute(customer.getId());


        assertThat( accounts.getById(checkingAccount.getId()).blockingGet().isClose() ).isTrue();
    }

    @Test
    void a_user_cannot_close_a_checking_account_because_the_balance_is_not_zero() {
        final var newCheckingAccount = new NewCheckingAccount(accounts);
        newCheckingAccount.execute(customer.getId());

        final var depositCheckingAccount = new DepositCheckingAccount(accounts);
        final var amount10 = MoneyMother.amount10();
        depositCheckingAccount.execute(customer.getId(), amount10);



        final var closeCheckingAccount = new CloseCheckingAccount(accounts);

        final var thrown = catchThrowable(() -> closeCheckingAccount.execute(customer.getId()) );
        assertThat(thrown).isInstanceOf(BalanceNotZeroException.class);
    }


    @Test
    void a_user_cannot_close_a_checking_account_with_credit_and_debit() {
        final var  newCheckingAccount = new NewCheckingAccount(accounts);
        final var checkingAccountSingle = newCheckingAccount.execute(customer.getId());
        final var checkingAccount = checkingAccountSingle.blockingGet();

        DepositCheckingAccount depositCheckingAccount = new DepositCheckingAccount(accounts);
        depositCheckingAccount.execute(customer.getId(), MoneyMother.amount10());
        ExtractCheckingAccount extractCheckingAccount = new ExtractCheckingAccount(accounts);
        extractCheckingAccount.execute(customer.getId(), MoneyMother.amount10());


        final var closeCheckingAccount = new CloseCheckingAccount(accounts);
        closeCheckingAccount.execute(customer.getId());

        assertThat( accounts.getById(checkingAccount.getId()).blockingGet().isClose() ).isTrue();
    }

}
