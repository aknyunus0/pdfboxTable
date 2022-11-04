package com.example;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.poiji.bind.Poiji;

@SpringBootApplication
public class main {
	private static List<String> buildClassificationFunction(Map<String,String> map, List<String> fields) {
	    return fields.stream()
	            .map(map::get)
	            .collect(Collectors.toList());
	}

	public static void main(String[] args) throws IOException {
		SpringApplication.run(main.class, args);
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();  
		
		File file = new File("Account_info.xlsx");
		List<AccountTableEntity> accountList = Poiji.fromExcel(file, AccountTableEntity.class);	
		
		

		Map<String, Function<AccountTableEntity, Object>> extractors = new HashMap<>();
		extractors.put("FIRST_NAME", AccountTableEntity::getFirstName);
		extractors.put("LAST_NAME", AccountTableEntity::getLastName);
		extractors.put("ACCOUNT_TYPE_NAME", AccountTableEntity::getAccountTypeName);
		extractors.put("ALIAS", AccountTableEntity::getAlias);
		extractors.put("STATUS", AccountTableEntity::getStatus);
		extractors.put("BALANCE", AccountTableEntity::getBalance);
		
		
		
		Map<String,String>  attributes =new HashMap<>(); // get attributes from JSON (left as exercise)
		attributes.put("ACCOUNT_TYPE_NAME", "STATUS");

		//attributes.add(2,"FIRST_NAME");
		
		List<String> att=new ArrayList<>();
		att.add("ACCOUNT_TYPE_NAME");
		//att.add("STATUS");
		

		Function<AccountTableEntity, List<Object>> classifier = emp -> att.stream()
				    .map(attr -> extractors.get(attr).apply(emp))
				    .collect(Collectors.toList());
				
		
			Map<List<Object>, List<AccountTableEntity>> mapend = accountList.stream()
					    .collect(Collectors.groupingBy(s -> classifier.apply(s), Collectors.toList()));
		
			
		Map<List<Object>, List<AccountTableEntity>> mapend2=new HashMap<>();
		
		for (Entry<List<Object>, List<AccountTableEntity>> entry : mapend.entrySet()) {
			List<AccountTableEntity> infoList=new ArrayList<>();
			infoList=entry.getValue();
			Map<String, List<AccountTableEntity>> 	mapend1=infoList.stream().collect(Collectors.groupingBy(AccountTableEntity::getStatus,Collectors.toList()));
			infoList=mapend1.entrySet().iterator().next().getValue();
			
			mapend2.put(entry.getKey(), infoList);
			
			
		}
	
				
		
		PDDocument document =new PDDocument();
		PDPage page=new PDPage(PDRectangle.A4);	
		document.addPage(page);	   
		PDPageContentStream contentStream=new PDPageContentStream(document, page);
				
		int pageWidth=(int) page.getTrimBox().getWidth();
		int pageHeight=(int) page.getTrimBox().getHeight();	
		PDFont font=PDType1Font.TIMES_ROMAN;
		PDFont fonttitle=PDType1Font.TIMES_ROMAN.TIMES_BOLD;
		
		
		TextClass textClass=new TextClass(document, contentStream);
		
		textClass.addSingelLine("Account Balance Report", 220, 820, fonttitle, 18, Color.BLACK);
		textClass.addSingelLine("Group By : ACCOUNT_TYPE_NAME & STATUS", 25,780, font, 12, Color.BLACK);
		textClass.addSingelLine("Date: "+formatter.format(date), 475, 780, font, 12, Color.BLACK);
		textClass.addSingelLine("Report - Page 1", 250, 40, font, 10, Color.BLACK);
				
		TableClass table =new TableClass(document, contentStream);
		
		System.out.println("pageHeight : "+pageHeight);
		
		
		
		
		float fontWidthFIRS_NAME =font.getStringWidth(TableClass.FIRS_NAME)/1000*11+20;
		float fontWidthLAST_NAME =font.getStringWidth(TableClass.LAST_NAME)/1000*11+20;
		float fontWidthACCOUNT_TYPE_NAME =font.getStringWidth(TableClass.ACCOUNT_TYPE_NAME)/1000*11+20;
		float fontWidthALIAS =font.getStringWidth(TableClass.ALIAS)/1000*11+20;
		float fontWidthSTATUS =font.getStringWidth(TableClass.STATUS)/1000*11+20;
		float fontWidthBALANCE =font.getStringWidth(TableClass.BALANCE)/1000*11+20;
		
		System.out.println("fontWidthFIRS_NAME : "+fontWidthFIRS_NAME);
		System.out.println("fontWidthLAST_NAME : "+fontWidthLAST_NAME);
		System.out.println("fontWidthACCOUNT_TYPE_NAME : "+fontWidthACCOUNT_TYPE_NAME);
		System.out.println("fontWidthALIAS : "+fontWidthALIAS);
		System.out.println("fontWidthSTATUS : "+fontWidthSTATUS);
		System.out.println("fontWidthBALANCE : "+fontWidthBALANCE);
		
		int [] cellwidths= {
				(int) fontWidthACCOUNT_TYPE_NAME-5,
				(int) fontWidthLAST_NAME,
				(int) fontWidthFIRS_NAME+10,
				(int) fontWidthALIAS+8,
				(int) fontWidthACCOUNT_TYPE_NAME-7,
				(int) fontWidthBALANCE};	
		
		
		table.setTable(cellwidths, 20, 5, pageHeight-100);
		table.setTableFont(font, 7, Color.BLACK);
			
		
		Color tableHeaderColor=new Color(0,0,255);
		Color tableBodyColor=new Color(255,255,255);
		Color tableSubColor=new Color(255,255,0);
		Color tableGrandColor=new Color(0,255,0);
		
		
		table.addCell(TableClass.FIRS_NAME, tableHeaderColor);
		table.addCell(TableClass.LAST_NAME, tableHeaderColor);
		table.addCell(TableClass.ACCOUNT_TYPE_NAME, tableHeaderColor);
		table.addCell(TableClass.ALIAS, tableHeaderColor);
		table.addCell(TableClass.STATUS, tableHeaderColor);
		table.addCell(TableClass.BALANCE, tableHeaderColor);
		
		System.out.println(mapend2);
		
	
		
		for(Entry<List<Object>, List<AccountTableEntity>> entry : mapend.entrySet()) {
			//entry.getKey().set(0, "Yunus");
			System.out.println(entry.getKey());
			List<AccountTableEntity> infoList=new ArrayList<>();			
					
			
			for(int i=0;i<entry.getKey().size();i++){	
				
				table.addCell("RESULT::  "+entry.getKey().get(i).toString(), tableBodyColor);
				table.addCell("", tableBodyColor);
				table.addCell("", tableBodyColor);
				table.addCell("", tableBodyColor);
				table.addCell("", tableBodyColor);
				table.addCell("", tableBodyColor);
				
			
							
			}
			
			
			
			
			for (AccountTableEntity info :infoList) {
				
   				table.addCell(info.getFirstName(), tableBodyColor);
				table.addCell(""+info.getLastName(), tableBodyColor);
				table.addCell(""+info.getAccountTypeName(), tableBodyColor);
				table.addCell(" ", tableBodyColor);
				table.addCell("" +info.getStatus(), tableBodyColor);
				table.addCell(" "+info.getBalance()/100, tableBodyColor);
			
			}
			

				
			table.addCell("", tableBodyColor);
			table.addCell("", tableBodyColor);
			table.addCell("", tableBodyColor);
			table.addCell("", tableBodyColor);
			table.addCell("Status SubTotal ", tableSubColor);
			table.addCell("", tableSubColor);
			
		}
		
		
		
		table.addCell("", tableBodyColor);
		table.addCell("", tableBodyColor);
		table.addCell("", tableBodyColor);
		table.addCell("GRAND TOTAL", tableGrandColor);
		table.addCell("", tableGrandColor);
		table.addCell("", tableGrandColor); 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
/*		
		int x=0;
		List<AccountTableEntity> resutList=new ArrayList<AccountTableEntity>();
       for(Map<String,  List<AccountTableEntity>> map: mapList) {
    	   if(columnSelected.length!=x+1) {
    	   AccountTableEntity tableclas=new AccountTableEntity();
    	   List<AccountTableEntity> infoList=new ArrayList<AccountTableEntity>();
    	   
        	for(Entry<String, List<AccountTableEntity>> entry: map.entrySet()) {
        		List<AccountTableEntity> resuList=new ArrayList<AccountTableEntity>();
    			/*table.addCell("Result With key:"+entry.getKey(), tableBodyColor);
    			table.addCell("", tableBodyColor);
    			table.addCell("", tableBodyColor);
    			table.addCell("", tableBodyColor);
    			table.addCell("", tableBodyColor);
    			table.addCell("", tableBodyColor); 			
        		infoList=entry.getValue();
    		//	tableclas.setFirstName("SONUC??? "+entry.getKey());
    		//	infoList.add(0,tableclas);
    			
    			for(Entry<String, List<AccountTableEntity>> entry2: mapList.get(x+1).entrySet()) {   				
    				List<AccountTableEntity> list = infoList.stream()
    					    .filter(entry2.getValue()::contains).filter(resutList::contains)
    					    .distinct()
    					    .collect(Collectors.toList()); 
    					resuList.addAll(list);
    					System.out.println(entry.getKey());
    					System.out.println(entry2.getKey());
    					System.out.println(resuList.toArray().length);

    			}
    			
    			resutList.addAll(resuList);
    			for (AccountTableEntity info : resuList) {
    				System.out.println(info.firstName);
    				
    				table.addCell(info.getFirstName(), tableBodyColor);
    				table.addCell(""+info.getLastName(), tableBodyColor);
    				table.addCell(""+info.getAccountTypeName(), tableBodyColor);
    				table.addCell(" ", tableBodyColor);
    				table.addCell("" +info.getStatus(), tableBodyColor);
    				if(info.getBalance()==null)table.addCell(" ", tableBodyColor);
    				else table.addCell(" "+info.getBalance()/100, tableBodyColor);
    				//Status_SubTotal=Status_SubTotal+info.getBalance();
    			
    		}
       	}
        	
       }	
        x++;	

        	
       } */
		
		
		
		
		
		
	/*
		double grantTotal=0.0;	
		for (Entry<String, Map<String, List<AccountTableEntity>>> entry : multiMap.entrySet()) {
			
			table.addCell("ACCOUNT_TYPE_NAME:"+entry.getKey(), tableBodyColor);
			table.addCell("", tableBodyColor);
			table.addCell("", tableBodyColor);
			table.addCell("", tableBodyColor);
			table.addCell("", tableBodyColor);
			table.addCell("", tableBodyColor);
			
			double AccSubtotal=0.0;
			for (Entry<String, List<AccountTableEntity>> entry2 : entry.getValue().entrySet()) {
				
				table.addCell("Status:"+entry2.getKey(), tableBodyColor);
				table.addCell("", tableBodyColor);
				table.addCell("", tableBodyColor);
				table.addCell("", tableBodyColor);
				table.addCell("", tableBodyColor);
				table.addCell("", tableBodyColor);
				
				double Status_SubTotal=0.0;
				List<AccountTableEntity> infoList=entry2.getValue();
				for (AccountTableEntity info : infoList) {
					
						table.addCell(info.getFirstName(), tableBodyColor);
						table.addCell(info.getLastName(), tableBodyColor);
						table.addCell(info.getAccountTypeName(), tableBodyColor);
						table.addCell(" ", tableBodyColor);
						table.addCell(info.getStatus(), tableBodyColor);
						table.addCell(""+info.getBalance()/100, tableBodyColor);
						Status_SubTotal=Status_SubTotal+info.getBalance();
					

				}
				
				table.addCell("", tableBodyColor);
				table.addCell("", tableBodyColor);
				table.addCell("", tableBodyColor);
				table.addCell("", tableBodyColor);
				table.addCell("Status SubTotal ", tableSubColor);
				table.addCell(""+Status_SubTotal/100, tableSubColor);
				AccSubtotal=AccSubtotal+Status_SubTotal;
				
			}
			table.addCell("", tableBodyColor);
			table.addCell("", tableBodyColor);
			table.addCell("", tableBodyColor);
			table.addCell("", tableBodyColor);
			table.addCell("Account Type Name Subtotal", tableSubColor);
			table.addCell(""+AccSubtotal/100, tableSubColor);
			grantTotal=grantTotal+AccSubtotal;
		}
		
		
		table.addCell("", tableBodyColor);
		table.addCell("", tableBodyColor);
		table.addCell("", tableBodyColor);
		table.addCell("GRAND TOTAL", tableGrandColor);
		table.addCell("", tableGrandColor);
		table.addCell(""+grantTotal/100, tableGrandColor); */

		table.contentStream.close();			
		contentStream.close();
		document.save("mypdf.pdf");
		document.close();
		System.out.println("PDF Created");
		
	
		

	}

	
}
	