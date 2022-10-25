package com.example;

import java.awt.Color;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


public class main {
	
	private static final String FIRS_NAME="FIRS_NAME";
	private static final String LAST_NAME="LAST_NAME";
	private static final String ACCOUNT_TYPE_NAME="ACCOUNT_TYPE_NAME";
	private static final String ALIAS="ALIAS";
	private static final String STATUS="STATUS";
	private static final String BALANCE="BALANCE";

	public static void main(String[] args) throws IOException {
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
		
		table.addCell("Enes AKIN Enes ", tableHeaderColor);
		table.addCell("", tableHeaderColor);
		
		
		
		

		
		
				
		contentStream.close();
		document.save("mypdf.pdf");
		document.close();
		System.out.println("PDF Created");
		
	
		

	}
	
}
	