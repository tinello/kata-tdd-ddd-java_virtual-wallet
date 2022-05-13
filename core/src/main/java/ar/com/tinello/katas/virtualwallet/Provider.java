package ar.com.tinello.katas.virtualwallet;


import ar.com.tinello.katas.virtualwallet.checkingAccount.actions.CloseCheckingAccount;
import ar.com.tinello.katas.virtualwallet.checkingAccount.actions.DepositCheckingAccount;
import ar.com.tinello.katas.virtualwallet.checkingAccount.actions.ExtractCheckingAccount;
import ar.com.tinello.katas.virtualwallet.checkingAccount.actions.GetAccountDetails;
import ar.com.tinello.katas.virtualwallet.checkingAccount.actions.GetCustomerDetails;
import ar.com.tinello.katas.virtualwallet.checkingAccount.actions.NewCheckingAccount;
import ar.com.tinello.katas.virtualwallet.checkingAccount.domain.Accounts;
import ar.com.tinello.katas.virtualwallet.checkingAccount.infrastructure.SqlAccounts;
import ar.com.tinello.katas.virtualwallet.customer.actions.CreateCustomer;
import ar.com.tinello.katas.virtualwallet.customer.domain.CustomerWasCreatedEvent;
import ar.com.tinello.katas.virtualwallet.customer.domain.Customers;
import ar.com.tinello.katas.virtualwallet.customer.infrastructure.SqlCustomers;
import ar.com.tinello.katas.virtualwallet.domain.EventPublisher;
import ar.com.tinello.katas.virtualwallet.infrastructure.JdbcSqlClient;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.jdbc.JDBCClient;

public class Provider {

    private final NewCheckingAccount newCheckingAccount;
    private final CloseCheckingAccount closeCheckingAccount;
    private final DepositCheckingAccount depositCheckingAccount;
    private final ExtractCheckingAccount extractCheckingAccount;
    private final GetAccountDetails getAccountDetails;
    private final GetCustomerDetails getCustomerDetails;
    
    private final CreateCustomer createCustomer;
    
    private final EventPublisher eventPublisher;
    

    public Provider(final Vertx vertx, final String customerId, final Environment environment) {
        this( new JdbcSqlClient( createJdbcClientForCustomerDatabase(vertx, customerId, environment) ), new EventPublisher() );        
    }
    
    public Provider(final JdbcSqlClient jdbcSqlClient, final EventPublisher eventPublisher) {
    	this(new SqlAccounts(jdbcSqlClient), new SqlCustomers(jdbcSqlClient), eventPublisher);
    }
    
    public Provider(final Accounts accounts, final Customers customers, final EventPublisher eventPublisher) {
    	newCheckingAccount = new NewCheckingAccount(accounts);
        closeCheckingAccount = new CloseCheckingAccount(accounts);
        depositCheckingAccount = new DepositCheckingAccount(accounts);
        extractCheckingAccount = new ExtractCheckingAccount(accounts);
        getAccountDetails = new GetAccountDetails(accounts);
        getCustomerDetails = new GetCustomerDetails(accounts);
        
        createCustomer = new CreateCustomer(customers, eventPublisher);
        
        this.eventPublisher = eventPublisher;

        this.eventPublisher.subscribe(CustomerWasCreatedEvent.class, newCheckingAccount::execute);
    }


    public NewCheckingAccount newCheckingAccount() {
       return newCheckingAccount;
    }

    public CloseCheckingAccount closeCheckingAccount() {
    	return closeCheckingAccount;
    }
    
    public DepositCheckingAccount depositCheckingAccount() {
    	return depositCheckingAccount;
    }
    
    public ExtractCheckingAccount extractCheckingAccount() {
    	return extractCheckingAccount;
    }
    
    public GetAccountDetails getAccountDetails() {
    	return getAccountDetails;
    }
    
    public GetCustomerDetails getCustomerDetails() {
    	return getCustomerDetails;
    }
    
    public CreateCustomer createCustomer() {
    	return createCustomer;
    }
    
    private static JDBCClient createJdbcClientForCustomerDatabase(final Vertx vertx, final String customerId, final Environment environment) {

        return JDBCClient.createShared(vertx,
                new JsonObject().put("provider_class", "io.vertx.ext.jdbc.spi.impl.HikariCPDataSourceProvider")
                        .put("jdbcUrl", environment.getVariable("JDBC_URL", "jdbc:h2:mem:db;INIT=create schema if not exists db\\;RUNSCRIPT FROM 'classpath:init.sql';DB_CLOSE_ON_EXIT=FALSE;DB_CLOSE_DELAY=-1;"))
                        .put("username", environment.getVariable("JDBC_USERNAME", "sa"))
                        .put("connectionTimeout", 5000)
                        .put("password", environment.getVariable("JDBC_PASSWORD", ""))
                        .put("driverClassName", environment.getVariable("JDBC_DRIVER", "org.h2.Driver"))
                        .put("maximumPoolSize", environment.getInt("POOL_MAXIMUM_SIZE", 1)),
                customerId);

        /*
        return JDBCClient.createShared(vertx,
                new JsonObject().put("provider_class", "io.vertx.ext.jdbc.spi.impl.HikariCPDataSourceProvider")
                        .put("jdbcUrl", environment.getVariable("JDBC_URL", "jdbc:mysql://localhost:3306/db"))
                        .put("username", environment.getVariable("JDBC_USERNAME", "root"))
                        .put("connectionTimeout", 5000)
                        .put("password", environment.getVariable("JDBC_PASSWORD", "password"))
                        .put("driverClassName", environment.getVariable("JDBC_DRIVER", "com.mysql.cj.jdbc.Driver"))
                        .put("maximumPoolSize", environment.getInt("POOL_MAXIMUM_SIZE", 10)),
                customerId);
       */
    }
}
