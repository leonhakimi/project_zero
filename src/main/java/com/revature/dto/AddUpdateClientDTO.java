package com.revature.dto;

import java.util.Objects;

public class AddUpdateClientDTO {
	private String clientFirstName;
	private String clientLastName;
	
	public AddUpdateClientDTO() {
		
	}
	
	public AddUpdateClientDTO(String clientFirstName, String clientLastName) {
		this.clientFirstName = clientFirstName;
		this.clientLastName = clientLastName;
	}

	public String getClientFirstName() {
		return clientFirstName;
	}

	public void setClientFirstName(String clientFirstName) {
		this.clientFirstName = clientFirstName;
	}

	public String getClientLastName() {
		return clientLastName;
	}

	public void setClientLastName(String clientLastName) {
		this.clientLastName = clientLastName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(clientFirstName, clientLastName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddUpdateClientDTO other = (AddUpdateClientDTO) obj;
		return Objects.equals(clientFirstName, other.clientFirstName)
				&& Objects.equals(clientLastName, other.clientLastName);
	}

	@Override
	public String toString() {
		return "AddUpdateClientDTO [clientFirstName=" + clientFirstName + ", clientLastName=" + clientLastName + "]";
	}
	
	
}
