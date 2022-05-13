package ar.com.tinello.katas.virtualwallet.domain;

import java.math.BigDecimal;
import java.util.Currency;

public class Money {

    private final BigDecimal amount;
    private final Currency currency;


    public Money(final BigDecimal amount) {
        this.amount = amount;
        this.currency = Currency.getInstance("USD");
    }


    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
    	return currency;
    }
}
