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
		
		
		TableClass table =new TableClass(document, contentStream);
		
		System.out.println("pageHeight : "+pageHeight);
		
		PDFont font=PDType1Font.TIMES_ROMAN;
		
		
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
				(int) fontWidthFIRS_NAME,
				(int) fontWidthLAST_NAME,
				(int) fontWidthACCOUNT_TYPE_NAME,
				(int) fontWidthALIAS,
				(int) fontWidthSTATUS,
				(int) fontWidthBALANCE};	
		
		
		table.setTable(cellwidths, 30, 5, 800);
		table.setTableFont(font, 11, Color.BLACK);
			
		
		Color tableHeaderColor=new Color(240,93,11);
		Color tableBodyColor=new Color(219,218,198);
		
		table.addCell(FIRS_NAME, tableHeaderColor);
		table.addCell(LAST_NAME, tableHeaderColor);
		table.addCell(ACCOUNT_TYPE_NAME, tableHeaderColor);
		table.addCell(ALIAS, tableHeaderColor);
		table.addCell(STATUS, tableHeaderColor);
		table.addCell(BALANCE, tableHeaderColor);
		
	
		double grantTotal=0.0;	
		for (Entry<String, Map<String, List<AccountTableEntity>>> entry : multiMap.entrySet()) {
			table.addCell("ACCOUNT_TYPE_NAME:"+entry.getKey(), tableHeaderColor);
			table.addCell("", tableHeaderColor);
			table.addCell("", tableHeaderColor);
			table.addCell("", tableHeaderColor);
			table.addCell("", tableHeaderColor);
			table.addCell("", tableHeaderColor);
			
			double toplam2=0.0;
			for (Entry<String, List<AccountTableEntity>> entry2 : entry.getValue().entrySet()) {
				table.addCell("Status:"+entry2.getKey(), tableHeaderColor);
				table.addCell("", tableHeaderColor);
				table.addCell("", tableHeaderColor);
				table.addCell("", tableHeaderColor);
				table.addCell("", tableHeaderColor);
				table.addCell("", tableHeaderColor);
				
				double toplam=0.0;
				List<AccountTableEntity> infoList=entry2.getValue();
				for (AccountTableEntity info : infoList) {
					table.addCell(info.getFirstName(), tableHeaderColor);
					table.addCell(info.getLastName(), tableHeaderColor);
					table.addCell(info.getAccountTypeName(), tableHeaderColor);
					table.addCell(""+info.getAlias(), tableHeaderColor);
					table.addCell(info.getStatus(), tableHeaderColor);
					table.addCell(""+info.getBalance(), tableHeaderColor);
					toplam=toplam+info.getBalance();
				}
				
				table.addCell("", tableHeaderColor);
				table.addCell("", tableHeaderColor);
				table.addCell("", tableHeaderColor);
				table.addCell("", tableHeaderColor);
				table.addCell("Toplam: ", tableHeaderColor);
				table.addCell(""+toplam, tableHeaderColor);
				toplam2=toplam2+toplam;
				
			}
			table.addCell("", tableHeaderColor);
			table.addCell("", tableHeaderColor);
			table.addCell("", tableHeaderColor);
			table.addCell("", tableHeaderColor);
			table.addCell("Toplam2 : ", tableHeaderColor);
			table.addCell(""+toplam2, tableHeaderColor);
			grantTotal=grantTotal+toplam2;
			
		}
		
		table.addCell("", tableHeaderColor);
		table.addCell("", tableHeaderColor);
		table.addCell("", tableHeaderColor);
		table.addCell("", tableHeaderColor);
		table.addCell("Grant Total : ", tableHeaderColor);
		table.addCell(""+grantTotal, tableHeaderColor);

		
		
				
		contentStream.close();
		document.save("mypdf.pdf");
		document.close();
		System.out.println("PDF Created");
		
	
		

	}
	
}
	