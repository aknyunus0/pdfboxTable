package com.example;

import com.poiji.annotation.ExcelCellName;

public class GroupBy {
	
	String firstName;
	
	
	String lastName;
	
	
	String accountTypeName;
	
	
	String alias;
	
	
	String status;
	
	
		
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAccountTypeName() {
		return accountTypeName;
	}

	public void setAccountTypeName(String accountTypeName) {
		this.accountTypeName = accountTypeName;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {

		return this.firstName.length() + this.lastName.length()+ this.accountTypeName.length()+ this.alias.length()+ this.status.length();
	}

	@Override
	public boolean equals(Object obj) {

		GroupBy other = (GroupBy) obj;

		if (other.getAccountTypeName().equals(this.accountTypeName) 
				&& other.getAlias().equals(this.alias)
				&& other.getFirstName().equals(this.alias)
				&& other.getLastName().equals(this.alias)
				&& other.getLastName().equals(this.alias))
			return true;
		return false;
	}

	@Override
	public String toString() {
		return "GroupBy [firstName=" + firstName + ", lastName=" + lastName + ", accountTypeName=" + accountTypeName
				+ ", alias=" + alias + ", status=" + status + "]";
	}

	
}
