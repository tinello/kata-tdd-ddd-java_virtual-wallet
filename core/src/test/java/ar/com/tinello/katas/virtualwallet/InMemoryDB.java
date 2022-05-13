package ar.com.tinello.katas.virtualwallet;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InMemoryDB {

	private final Map<String, String> customers;
	private final Map<String, String> accounts;
	private final List<String> credits;
    private final List<String> debits;
	
	public InMemoryDB() {
		accounts = new LinkedHashMap<>();
		customers = new LinkedHashMap<>();
		credits = new ArrayList<>();
		debits = new ArrayList<>();
	}
	
	public void putCustomer(String customerId, String customer) {
		customers.put(customerId, customer);
	}
	
	public String getCustomer(String customerId) {
		return customers.get(customerId);
	}
	
	public void putAccount(String accountId, String account) {
		accounts.put(accountId, account);
	}
	
	public String getAccount(String accountId) {
		return accounts.get(accountId);
	}

	public List<String> getCredits() {
		return credits;
	}

	public List<String> getDebits() {
		return debits;
	}
	
	public void addCredit(String credit) {
		credits.add(credit);
	}
	
	public void addDebit(String debit) {
		debits.add(debit);
	}

	public Map<String, String> getAccounts() {
		return accounts;
	}
	
	
}
