package com.example;

import javax.persistence.*;



public class Table {



	String FIRST_NAME;

	String LAST_NAME;

	String ACCOUNT_TYPE_NAME;

	String ALIAS;
	
	String STATUS;

	double BALANCE;
	

	public Table(String fIRST_NAME, String lAST_NAME, String aCCOUNT_TYPE_NAME, String aLIAS, String sTATUS,
			double bALANCE) {
		super();
		
		FIRST_NAME = fIRST_NAME;
		LAST_NAME = lAST_NAME;
		ACCOUNT_TYPE_NAME = aCCOUNT_TYPE_NAME;
		ALIAS = aLIAS;
		STATUS = sTATUS;
		BALANCE = bALANCE;
	}


	public String getFIRST_NAME() {
		return FIRST_NAME;
	}

	public void setFIRST_NAME(String fIRST_NAME) {
		FIRST_NAME = fIRST_NAME;
	}

	public String getLAST_NAME() {
		return LAST_NAME;
	}

	public void setLAST_NAME(String lAST_NAME) {
		LAST_NAME = lAST_NAME;
	}

	public String getACCOUNT_TYPE_NAME() {
		return ACCOUNT_TYPE_NAME;
	}

	public void setACCOUNT_TYPE_NAME(String aCCOUNT_TYPE_NAME) {
		ACCOUNT_TYPE_NAME = aCCOUNT_TYPE_NAME;
	}

	public String getALIAS() {
		return ALIAS;
	}

	public void setALIAS(String aLIAS) {
		ALIAS = aLIAS;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public double getBALANCE() {
		return BALANCE;
	}

	public void setBALANCE(double bALANCE) {
		BALANCE = bALANCE;
	}

}
