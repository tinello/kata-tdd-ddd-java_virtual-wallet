package ar.com.tinello.katas.virtualwallet.checkingAccount.infrastructure;

import java.util.LinkedHashMap;
import java.util.Map;

import ar.com.tinello.katas.virtualwallet.customer.domain.Customer;

public class PrimitiveRepresentation {
	
	private PrimitiveRepresentation() {
		super();
	}
	
	public static Map<String, Object> toMap(final Customer customer) {
		final var map = new LinkedHashMap<String, Object>();
		
        map.put("customerId", customer.getId());
        map.put("firstName", customer.getFirstName());
        map.put("lastName", customer.getLastName());
        map.put("personalNumber", customer.getPersonalNumber());
        map.put("personalPhoneNumber", customer.getPersonalPhoneNumber());
        
        final var accountMap = new LinkedHashMap<String, Object>();
        final var account = customer.getAccount();
        accountMap.put("id", account.getId());
        accountMap.put("openingDate", account.getOpeningDate().getDatePrintFormat());
        accountMap.put("state", account.getStateName());
        accountMap.put("balance", account.getBalance());
        
        map.put("account", accountMap);
        return map;
	}

}
