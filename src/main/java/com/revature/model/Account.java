package com.revature.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Account {
	private int accountId;
	private BigDecimal balance;
	private int clientId;
	
	public Account(int accountId, BigDecimal balance, int clientId) {
		this.accountId = accountId;
		this.balance = balance;
		this.clientId = clientId;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountId, balance, clientId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return accountId == other.accountId && Objects.equals(balance, other.balance) && clientId == other.clientId;
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", balance=" + balance + ", clientId=" + clientId + "]";
	}
	
	
}
