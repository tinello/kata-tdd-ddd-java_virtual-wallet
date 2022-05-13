package ar.com.tinello.katas.virtualwallet.customer.infrastructure;

import ar.com.tinello.katas.virtualwallet.customer.domain.Customer;
import ar.com.tinello.katas.virtualwallet.customer.domain.Customers;
import ar.com.tinello.katas.virtualwallet.infrastructure.JdbcSqlClient;
import io.reactivex.Single;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.sql.UpdateResult;

public class SqlCustomers extends Customers {
	
	private final JdbcSqlClient jdbcSqlClient;

	public SqlCustomers(final JdbcSqlClient jdbcSqlClient) {
		this.jdbcSqlClient = jdbcSqlClient;
	}

	@Override
	public Single<Customer> getById(final String string) {
		return null;
	}

	@Override
	public void add(final Customer customer) {
		Single<UpdateResult> insert = jdbcSqlClient.rxUpdateWithParams("INSERT INTO customer (id, firstName, lastName, personalNumber, personalPhoneNumber) VALUES (?, ?, ?, ?, ?)", 
				new JsonArray()
					.add(customer.getId())
					.add(customer.getFirstName())
					.add(customer.getLastName())
					.add(customer.getPersonalNumber())
					.add(customer.getPersonalPhoneNumber())
				);
		
		insert.subscribe(result -> {
		    // Ok
		  }, err -> System.err.println("Failed to insert operation in database: " + err) );
	}

}
