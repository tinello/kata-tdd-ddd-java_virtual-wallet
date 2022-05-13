package ar.com.tinello.katas.virtualwallet.checkingAccount.infrastructure;

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import ar.com.tinello.katas.virtualwallet.checkingAccount.domain.Accounts;
import ar.com.tinello.katas.virtualwallet.checkingAccount.domain.CheckingAccount;
import ar.com.tinello.katas.virtualwallet.checkingAccount.domain.CheckingAccountState;
import ar.com.tinello.katas.virtualwallet.checkingAccount.domain.Credit;
import ar.com.tinello.katas.virtualwallet.checkingAccount.domain.Debit;
import ar.com.tinello.katas.virtualwallet.checkingAccount.domain.OpeningDate;
import ar.com.tinello.katas.virtualwallet.customer.domain.Customer;
import ar.com.tinello.katas.virtualwallet.domain.Money;
import ar.com.tinello.katas.virtualwallet.infrastructure.JdbcSqlClient;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.UpdateResult;

public class SqlAccounts implements Accounts {

    private final JdbcSqlClient jdbcSqlClient;

    public SqlAccounts(final JdbcSqlClient jdbcSqlClient) {
        this.jdbcSqlClient = jdbcSqlClient;
    }

    @Override
    public void add(final CheckingAccount checkingAccount) {
    	Single<UpdateResult> insert = jdbcSqlClient.rxUpdateWithParams("INSERT INTO checikng_account (id, customer_id, opening_date , state , balance ) VALUES (?, ?, ?, ?, ?);", 
        		new JsonArray()
        				.add(checkingAccount.getId())
        				.add(checkingAccount.getCustomerId())
        				.add(checkingAccount.getOpeningDateTimestamp())
        				.add(checkingAccount.getStateName())
        				.add(checkingAccount.getBalance().floatValue())
        	);
        
        insert.subscribe(result -> {
		    // Ok
		  }, err -> {
			  System.out.println(checkingAccount.getCustomerId());
		    System.err.println("Failed to insert operation in database: " + err);
		  });
		
    }

    @Override
    public Single<CheckingAccount> getById(final String id) {
        return null;
    }

    @Override
    public Observable<Credit> getCredits(final String customerId) {
        return null;
    }

    @Override
    public Single<CheckingAccount> getCheckingAccountByCustomer(final String customerId) {
        return null;
    }

    @Override
    public void addCredit(final CheckingAccount account, final Money money) {
    }

    @Override
    public void addDebit(final CheckingAccount account, final Money money) {
    }

    @Override
    public Observable<Debit> getDebits(final String customerId) {
        return null;
    }

    @Override
    public void put(final CheckingAccount account) {

    }

    @Override
    public Single<CheckingAccount> getCheckingAccountDetails(final String customerId) {
        return null;
    }

    @Override
    public Observable<Customer> getCustomerDetails(final String customerId) {
    	return jdbcSqlClient.rxQueryWithParams("select a.id, customer_id, opening_date, state, balance, firstName, lastName, personalNumber, personalPhoneNumber from checikng_account a inner join customer c on a.customer_id=c.id where c.id = ?", new JsonArray().add(customerId))
    		.map(this::makeEntityTypes)
    		.flatMapObservable(Observable::fromIterable);
    }
    
    private List<Customer> makeEntityTypes(final ResultSet resultSet) {
        return resultSet.getResults().stream().map(this::makeEntityType).collect(toList());
    }

    private Customer makeEntityType(final JsonArray row) {
        return new Customer(
                row.getString(1),
                row.getString(5),
                row.getString(6),
                row.getString(7),
                row.getString(8),
                new CheckingAccount(
                		row.getString(0), 
                		row.getString(1), 
                		new OpeningDate(
                				new Timestamp(
                						row.getLong(2)
        						)
        				), 
		        		CheckingAccountState.valueOf(row.getString(3)), 
		        		BigDecimal.valueOf(row.getFloat(4)), 
		        		new ArrayList<>(), 
		        		new ArrayList<>())
                );
    }
}
