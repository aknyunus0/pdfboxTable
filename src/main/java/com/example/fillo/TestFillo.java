package com.example.fillo;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class TestFillo {

	public static void main(String[] args) throws FilloException {
		Fillo fillo=new Fillo();
		Connection connection=fillo.getConnection("C:\\Users\\yunus\\git\\pdfboxTable\\Account_info.xlsx");
		System.out.println("connection.getMetaData() : "+connection.getMetaData().getTableNames());
		String strQuery="Select ACCOUNT_TYPE_NAME from RawData group by ACCOUNT_TYPE_NAME";
		Recordset recordset=connection.executeQuery(strQuery);
		System.out.println("recordset.getCount() : "+recordset.getCount());
		while(recordset.next()){
//		System.out.print(recordset.getField("FIRST_NAME")+" ");
//		System.out.print(recordset.getField("LAST_NAME")+" ");
//		System.out.print(recordset.getField("ACCOUNT_TYPE_NAME")+" ");
//		System.out.print(recordset.getField("ALIAS")+" ");
//		System.out.print(recordset.getField("STATUS")+" ");		
//		System.out.println(recordset.getField("BALANCE"));
			System.out.print(recordset.getField("ACCOUNT_TYPE_NAME")+" ");		
			//System.out.println(recordset.getField(1));
		}

		recordset.close();
		connection.close();

	}
//FIRST_NAME,LAST_NAME,ACCOUNT_TYPE_NAME,ALIAS,STATUS,BALANCE
}
