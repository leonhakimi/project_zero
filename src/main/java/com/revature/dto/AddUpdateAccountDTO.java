package com.revature.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class AddUpdateAccountDTO {
	private BigDecimal balance;
	
	public AddUpdateAccountDTO() {
		
	}
	
	public AddUpdateAccountDTO(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@Override
	public int hashCode() {
		return Objects.hash(balance);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddUpdateAccountDTO other = (AddUpdateAccountDTO) obj;
		return Objects.equals(balance, other.balance);
	}

	@Override
	public String toString() {
		return "AddUpdateAccountDTO [balance=" + balance + "]";
	}
	
}
