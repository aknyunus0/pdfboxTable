package com.example;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import com.poiji.bind.Poiji;


public class main {
	
	

	public static void main(String[] args) throws IOException {
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();  
		
		File file = new File("Account_info.xlsx");
		List<AccountTableEntity> accountList = Poiji.fromExcel(file, AccountTableEntity.class);	
		
		
		//Birden fazla gruplamak istendiðinde
		Map<String, Map<String, List<AccountTableEntity>>> multiMap = accountList.stream()
				.collect(Collectors.groupingBy(AccountTableEntity::getAccountTypeName, Collectors.groupingBy(AccountTableEntity::getStatus)));
		//Tekli gruplama yapmak istenildiðinde
		Map<String,  List<AccountTableEntity>> singleMap = accountList.stream()
				.collect(Collectors.groupingBy(AccountTableEntity::getStatus));
		
				
		
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
		
	
		double grantTotal=0.0;	
		for (Entry<String, Map<String, List<AccountTableEntity>>> entry : multiMap.entrySet()) {
			if (pageHeight>50) {
			table.addCell("ACCOUNT_TYPE_NAME:"+entry.getKey(), tableBodyColor);
			table.addCell("", tableBodyColor);
			table.addCell("", tableBodyColor);
			table.addCell("", tableBodyColor);
			table.addCell("", tableBodyColor);
			table.addCell("", tableBodyColor);
			
			double AccSubtotal=0.0;
			for (Entry<String, List<AccountTableEntity>> entry2 : entry.getValue().entrySet()) {
				if (pageHeight>50) {
				table.addCell("Status:"+entry2.getKey(), tableBodyColor);
				table.addCell("", tableBodyColor);
				table.addCell("", tableBodyColor);
				table.addCell("", tableBodyColor);
				table.addCell("", tableBodyColor);
				table.addCell("", tableBodyColor);
				
				double Status_SubTotal=0.0;
				List<AccountTableEntity> infoList=entry2.getValue();
				for (AccountTableEntity info : infoList) {
					if (pageHeight>50) {
						table.addCell(info.getFirstName(), tableBodyColor);
						table.addCell(info.getLastName(), tableBodyColor);
						table.addCell(info.getAccountTypeName(), tableBodyColor);
						table.addCell(" ", tableBodyColor);
						table.addCell(info.getStatus(), tableBodyColor);
						table.addCell(""+info.getBalance()/100, tableBodyColor);
						Status_SubTotal=Status_SubTotal+info.getBalance();
					}

				}
				
				table.addCell("", tableBodyColor);
				table.addCell("", tableBodyColor);
				table.addCell("", tableBodyColor);
				table.addCell("", tableBodyColor);
				table.addCell("Status SubTotal ", tableSubColor);
				table.addCell(""+Status_SubTotal/100, tableSubColor);
				AccSubtotal=AccSubtotal+Status_SubTotal;
				}	
			}
			table.addCell("", tableBodyColor);
			table.addCell("", tableBodyColor);
			table.addCell("", tableBodyColor);
			table.addCell("", tableBodyColor);
			table.addCell("Account Type Name Subtotal", tableSubColor);
			table.addCell(""+AccSubtotal/100, tableSubColor);
			grantTotal=grantTotal+AccSubtotal;
		}
		}
		
		table.addCell("", tableBodyColor);
		table.addCell("", tableBodyColor);
		table.addCell("", tableBodyColor);
		table.addCell("GRAND TOTAL", tableGrandColor);
		table.addCell("", tableGrandColor);
		table.addCell(""+grantTotal/100, tableGrandColor);

		table.contentStream.close();			
		contentStream.close();
		document.save("mypdf.pdf");
		document.close();
		System.out.println("PDF Created");
		
	
		

	}
	
}
	