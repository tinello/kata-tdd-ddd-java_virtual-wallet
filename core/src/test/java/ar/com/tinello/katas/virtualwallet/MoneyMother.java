package ar.com.tinello.katas.virtualwallet;

import java.math.BigDecimal;

import ar.com.tinello.katas.virtualwallet.domain.Money;

public class MoneyMother {

    private MoneyMother() {
        super();
    }

    public static Money amount10() {
        return new Money(new BigDecimal("10"));
    }

}
