package ar.com.tinello.katas.virtualwallet.checkingAccount.domain;

import java.math.BigDecimal;

import ar.com.tinello.katas.virtualwallet.domain.Money;

public class Debit {

    private final String checkingAccountId;
    private final Money money;

    public Debit(final String checkingAccountId, final Money money) {
        this.checkingAccountId = checkingAccountId;
        this.money = money;
    }

    public BigDecimal getAmount() {
        return money.getAmount();
    }

    public String getCheckingAccountId() {
        return checkingAccountId;
    }

}
