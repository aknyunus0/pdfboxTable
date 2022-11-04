package com.example;

import java.util.ArrayList;

import com.poiji.annotation.ExcelCellName;

public class AccountTableEntity {

	@ExcelCellName("FIRST_NAME")
	String firstName;
	
	@ExcelCellName("LAST_NAME")
	String lastName;
	
	@ExcelCellName("ACCOUNT_TYPE_NAME")
	String accountTypeName;
	
	@ExcelCellName("ALIAS")
	String alias;
	
	@ExcelCellName("STATUS")
	String status;
	
	@ExcelCellName("BALANCE")
	Double balance;
	

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
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "AccountTableEntity [firstName=" + firstName + ", lastName=" + lastName + ", accountTypeName="
				+ accountTypeName + ", alias=" + alias + ", status=" + status + ", balance=" + balance + "]";
	}
	
	
	
}
