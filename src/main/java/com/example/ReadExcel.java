package com.example;


import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.crypto.dsig.keyinfo.KeyValue;

import com.poiji.bind.Poiji;


public class ReadExcel {
	

	public static void main(String[] args) throws IOException{
		
		
		File file = new File("Account_info.xlsx");
		List<AccountTableEntity> accountList = Poiji.fromExcel(file, AccountTableEntity.class);	
		
		List<KeyValue> accountList2 = Poiji.fromExcel(file, KeyValue.class);	
		System.out.println(accountList2.toString());
		//Birden fazla gruplamak istendiðinde
		Map<String, Map<String, List<AccountTableEntity>>> multiMap = accountList.stream()
				.collect(Collectors.groupingBy(AccountTableEntity::getAccountTypeName, Collectors.groupingBy(AccountTableEntity::getStatus)));
		
		//Tekli gruplama yapmak istenildiðinde
		Map<String,  List<AccountTableEntity>> singleMap = accountList.stream()
				.collect(Collectors.groupingBy(AccountTableEntity::getStatus));
		
			
	}
}
