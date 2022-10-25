package com.example;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.poiji.bind.Poiji;


public class ReadExcel {
	

	public static void main(String[] args) throws IOException{
		
		
		File file = new File("Account_info.xlsx");
		List<AccountTableEntity> accountList = Poiji.fromExcel(file, AccountTableEntity.class);	
		
		
		//Birden fazla gruplamak istendi�inde
		Map<String, Map<String, List<AccountTableEntity>>> multiMap = accountList.stream()
				.collect(Collectors.groupingBy(AccountTableEntity::getStatus, Collectors.groupingBy(AccountTableEntity::getAccountTypeName)));
		
		//Tekli gruplama yapmak istenildi�inde
		Map<String,  List<AccountTableEntity>> singleMap = accountList.stream()
				.collect(Collectors.groupingBy(AccountTableEntity::getStatus));
		
	}
}
