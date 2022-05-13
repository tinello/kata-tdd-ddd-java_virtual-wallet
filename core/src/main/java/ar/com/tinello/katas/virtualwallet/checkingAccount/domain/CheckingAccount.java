package ar.com.tinello.katas.virtualwallet.checkingAccount.domain;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import ar.com.tinello.katas.virtualwallet.domain.Money;

public class CheckingAccount {

    private final String id;
    private final String customerId;
    private OpeningDate openingDate;
    private CheckingAccountState state;
    private BigDecimal balance;

    private List<Credit> credits;
    private List<Debit> debits;

    public CheckingAccount(final String id, final String customerId) {
        this.id = id;
        this.customerId = customerId;
        this.openingDate = new OpeningDate();
        this.state = CheckingAccountState.OPEN;
        this.balance = new BigDecimal(0);
        this.credits = List.of();
        this.debits = List.of();
    }

    public CheckingAccount(final String id, final String customerId, final OpeningDate openingDate, 
    		final CheckingAccountState state, final BigDecimal balance, final List<Credit> credits, 
    		final List<Debit> debits) {
        this(id, customerId);
        this.openingDate = openingDate;
        this.state = state;
        this.balance = balance;
        this.credits = Collections.unmodifiableList(credits);
        this.debits = Collections.unmodifiableList(debits);
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getPrintOpeningDate() {
        return openingDate.getDatePrintFormat();
    }

    public OpeningDate getOpeningDate() {
        return openingDate;
    }

    public CheckingAccountState getState() {
        return state;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void close() {
        if (!balance.equals(new BigDecimal(0))) {
            throw new BalanceNotZeroException("Error al intentar cerrar un cuenta con balance distinto a cero.");
        }
        state = CheckingAccountState.CLOSE;
    }

    public Boolean isClose() {
        return CheckingAccountState.CLOSE.equals(state);
    }

    public void plusBalance(final Money money) {
        this.balance = this.balance.add(money.getAmount());
    }

    public void subtractBalance(final Money money) {
        if (balance.compareTo(money.getAmount()) < 0 ) {
            throw new InsufficientFundsException("Saldo: " + balance + "; Extraccion: " + money.getAmount());
        }
        this.balance = this.balance.subtract(money.getAmount());
    }

    public List<Credit> getCredits() {
        return credits;
    }

    public List<Debit> getDebits() {
        return debits;
    }

	public String getStateName() {
		return this.state.name();
	}

	public Long getOpeningDateTimestamp() {
		return this.openingDate.getTimestamp();
	}

}
