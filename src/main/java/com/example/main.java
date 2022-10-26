package com.example;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
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
	
	private static final String FIRS_NAME="FIRS_NAME";
	private static final String LAST_NAME="LAST_NAME";
	private static final String ACCOUNT_TYPE_NAME="ACCOUNT_TYPE_NAME";
	private static final String ALIAS="ALIAS";
	private static final String STATUS="STATUS";
	private static final String BALANCE="BALANCE";

	public static void main(String[] args) throws IOException {
		
		File file = new File("Account_info.xlsx");
		List<AccountTableEntity> accountList = Poiji.fromExcel(file, AccountTableEntity.class);	
		
		
		//Birden fazla gruplamak istendiðinde
		Map<String, Map<String, List<AccountTableEntity>>> multiMap = accountList.stream()
				.collect(Collectors.groupingBy(AccountTableEntity::getAccountTypeName, Collectors.groupingBy(AccountTableEntity::getStatus)));
		
				
		
		PDDocument document =new PDDocument();
		PDPage firsPage=new PDPage(PDRectangle.A4);
		document.addPage(firsPage);
		PDPageContentStream contentStream=new PDPageContentStream(document, firsPage);
				
		int pageWidth=(int) firsPage.getTrimBox().getWidth();
		int pageHeight=(int) firsPage.getTrimBox().getHeight();	
		PDFont font=PDType1Font.TIMES_ROMAN;
		PDFont fonttitle=PDType1Font.TIMES_ROMAN.TIMES_BOLD;
		
		
		TextClass textClass=new TextClass(document, contentStream);
		
		textClass.addSingelLine("Account Balance Report", 220, 820, fonttitle, 18, Color.BLACK);
		textClass.addSingelLine("Group By : ACCOUNT_TYPE_NAME & STATUS", 25,780, font, 12, Color.BLACK);
		textClass.addSingelLine("Date: 26.10.2022", 475, 780, font, 12, Color.BLACK);
		textClass.addSingelLine("Report - Page 1", 250, 40, font, 10, Color.BLACK);
		
		
		
		
		
		
		
		
		
		TableClass table =new TableClass(document, contentStream);
		
		System.out.println("pageHeight : "+pageHeight);
		
		
		
		
		float fontWidthFIRS_NAME =font.getStringWidth(FIRS_NAME)/1000*11+20;
		float fontWidthLAST_NAME =font.getStringWidth(LAST_NAME)/1000*11+20;
		float fontWidthACCOUNT_TYPE_NAME =font.getStringWidth(ACCOUNT_TYPE_NAME)/1000*11+20;
		float fontWidthALIAS =font.getStringWidth(ALIAS)/1000*11+20;
		float fontWidthSTATUS =font.getStringWidth(STATUS)/1000*11+20;
		float fontWidthBALANCE =font.getStringWidth(BALANCE)/1000*11+20;
		
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
		
		
		table.addCell(FIRS_NAME, tableHeaderColor);
		table.addCell(LAST_NAME, tableHeaderColor);
		table.addCell(ACCOUNT_TYPE_NAME, tableHeaderColor);
		table.addCell(ALIAS, tableHeaderColor);
		table.addCell(STATUS, tableHeaderColor);
		table.addCell(BALANCE, tableHeaderColor);
		
	
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
		table.addCell(""+grantTotal/100, tableGrandColor);

						
		contentStream.close();
		document.save("mypdf.pdf");
		document.close();
		System.out.println("PDF Created");
		
	
		

	}
	
}
	