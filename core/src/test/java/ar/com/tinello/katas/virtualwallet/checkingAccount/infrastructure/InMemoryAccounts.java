package ar.com.tinello.katas.virtualwallet.checkingAccount.infrastructure;

import static io.reactivex.Observable.fromIterable;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import ar.com.tinello.katas.virtualwallet.InMemoryDB;
import ar.com.tinello.katas.virtualwallet.checkingAccount.domain.Accounts;
import ar.com.tinello.katas.virtualwallet.checkingAccount.domain.CheckingAccount;
import ar.com.tinello.katas.virtualwallet.checkingAccount.domain.Credit;
import ar.com.tinello.katas.virtualwallet.checkingAccount.domain.Debit;
import ar.com.tinello.katas.virtualwallet.customer.domain.Customer;
import ar.com.tinello.katas.virtualwallet.domain.Money;
import io.reactivex.Observable;
import io.reactivex.Single;

public class InMemoryAccounts implements Accounts {

	private final InMemoryDB inMemoryDB;
    private final Gson serialization = new Gson();

    public InMemoryAccounts(final InMemoryDB inMemoryDB) {
    	this.inMemoryDB = inMemoryDB;
    }
    
    @Override
    public void add(final CheckingAccount checkingAccount) {
    	inMemoryDB.putAccount(checkingAccount.getId(), serialization.toJson(checkingAccount));
    }

    @Override
    public Single<CheckingAccount> getById(final String id) {
        String account = inMemoryDB.getAccount(id);
        if (account == null) {
            return null;
        }
        return Single.just(serialization.fromJson(account, CheckingAccount.class));
    }

    @Override
    public Observable<Credit> getCredits(final String customerId) {
        return fromIterable(getCreditsByCustomer(customerId));
    }

    @Override
    public Single<CheckingAccount> getCheckingAccountByCustomer(final String customerId) {
        return Single.just(getCheckingAccount(customerId));
    }

    @Override
    public void addCredit(final CheckingAccount account, final Money money) {
        final var credit = new Credit(account.getId(), money);
        inMemoryDB.addCredit(serialization.toJson(credit));
        put(account);
    }

    @Override
    public void addDebit(final CheckingAccount account, final Money money) {
        final var debit = new Debit(account.getId(), money);
        inMemoryDB.addDebit(serialization.toJson(debit));
        put(account);
    }

    @Override
    public Observable<Debit> getDebits(final String customerId) {
        return fromIterable(getDebitsByCustomer(customerId));
    }

    @Override
    public void put(final CheckingAccount checkingAccount) {
    	inMemoryDB.putAccount(checkingAccount.getId(), serialization.toJson(checkingAccount));
    }

    @Override
    public Single<CheckingAccount> getCheckingAccountDetails(final String customerId) {
        return Single.just(getCheckingAccountWithTransactions(customerId));
    }

    @Override
    public Observable<Customer> getCustomerDetails(final String customerId) {
    	final var customer = serialization.fromJson(inMemoryDB.getCustomer(customerId), Customer.class);
        final var account = getCheckingAccountWithTransactions(customer.getId());
        return Observable.just(new Customer(customer.getId(), customer.getFirstName(), customer.getLastName(), 
        		customer.getPersonalNumber(), customer.getPersonalPhoneNumber(), account));
    }

    private CheckingAccount getCheckingAccountWithTransactions(final String customerId) {
        final var account = getCheckingAccount(customerId);
        final var debits = getDebitsByCustomer(customerId);
        final var credits = getCreditsByCustomer(customerId);
        return new CheckingAccount(account.getId(), account.getCustomerId(), account.getOpeningDate(), 
        		account.getState(), account.getBalance(), credits, debits);
    }

    private CheckingAccount getCheckingAccount(final String customerId) {
        return inMemoryDB.getAccounts().values().stream()
                .map(account -> serialization.fromJson(account, CheckingAccount.class))
                .filter(account -> account.getCustomerId().equals(customerId))
                .findFirst().get();
    }

    private List<Debit> getDebitsByCustomer(final String customerId) {
        final var checkingAccount = getCheckingAccountByCustomer(customerId);
        final var debitsAccount = new ArrayList<Debit>();
        checkingAccount.subscribe(account -> {
        	inMemoryDB.getDebits().stream()
                    .map(debit -> serialization.fromJson(debit, Debit.class))
                    .filter(debit -> debit.getCheckingAccountId().equals(account.getId()))
                    .forEach(debit -> debitsAccount.add(debit));
        });
        return debitsAccount;
    }

    private List<Credit> getCreditsByCustomer(final String customerId) {
        final var checkingAccount = getCheckingAccountByCustomer(customerId);

        final var creditsAccount = new ArrayList<Credit>();
        checkingAccount.subscribe(account -> {
        	inMemoryDB.getCredits().stream()
                    .map(credit -> serialization.fromJson(credit, Credit.class))
                    .filter(credit -> credit.getCheckingAccountId().equals(account.getId()))
                    .forEach(credit -> creditsAccount.add(credit));
        });
        return creditsAccount;
    }

}
