package com.example;

import java.awt.Color;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;

public class TableClass {
	
	public static final String FIRS_NAME="FIRS_NAME";
	public static final String LAST_NAME="LAST_NAME";
	public static final String ACCOUNT_TYPE_NAME="ACCOUNT_TYPE_NAME";
	public static final String ALIAS="ALIAS";
	public static final String STATUS="STATUS";
	public static final String BALANCE="BALANCE";

	PDDocument document;
	PDPageContentStream contentStream;
	private int [] colWidths;
	private int cellHight;
	private int yPosition;
	private int xPosition;
	private int colPosition =0;
	private int xInitialPosition;
	private float fontSize;
	private PDFont font;
	private Color fontColor;
	
	public TableClass(PDDocument document, PDPageContentStream contentStream) {
		this.document = document;
		this.contentStream = contentStream;
	}
	
	void setTable(int [] colWidths,int cellHight,int xPosition,int yPosition) {
		this.colWidths=colWidths;
		this.cellHight=cellHight;
		this.xPosition=xPosition;
		this.yPosition=yPosition;
		xInitialPosition=xPosition;
		
	}
	
	void setTableFont(PDFont font,float fontSize,Color fontColor) {
		this.font=font;
		this.fontSize=fontSize;
		this.fontColor=fontColor;
		
	}
	int pageNum=2;
	void addCell(String text, Color fillColor) throws IOException {
		Color tableHeaderColor=new Color(0,0,255);
		Color tableBodyColor=new Color(255,255,255);
		Color tableSubColor=new Color(255,255,0);
		Color tableGrandColor=new Color(0,255,0);
		
		if(yPosition<50) {
	    yPosition=801;	
		contentStream.close();
		PDPage page = new PDPage(PDRectangle.A4);
		document.addPage(page);
		contentStream = new PDPageContentStream(document,page);	
		addCell(FIRS_NAME, tableHeaderColor);
		addCell(LAST_NAME, tableHeaderColor);
		addCell(ACCOUNT_TYPE_NAME, tableHeaderColor);
		addCell(ALIAS, tableHeaderColor);
		addCell(STATUS, tableHeaderColor);
		addCell(BALANCE, tableHeaderColor);
		TextClass textClass=new TextClass(document, contentStream);
		textClass.addSingelLine("Report - Page "+pageNum, 250, 40, font, 10, Color.BLACK);
		pageNum++;
		}
		
		
		contentStream.setStrokingColor(fillColor);
		if (fillColor != null) {
			contentStream.setNonStrokingColor(fillColor);
			
		}
		contentStream.addRect(xPosition, yPosition, colWidths[colPosition], cellHight);
		if (fillColor== null) {
			contentStream.stroke();
		}
		else contentStream.fillAndStroke();
		
		contentStream.beginText();
		contentStream.setNonStrokingColor(fontColor);
		
		if(colPosition==5) {
			float fontWidth =font.getStringWidth(text)/1000*fontSize;
			contentStream.newLineAtOffset(xPosition+colWidths[colPosition]-10-fontWidth, yPosition+10);
			
		}
		else {
			contentStream.newLineAtOffset(xPosition+10, yPosition+10);
		}
		
		
		contentStream.setFont(font, fontSize);			
		contentStream.showText(text);
		contentStream.endText();
		
		xPosition+=colWidths[colPosition];
		colPosition++;
		if(colPosition==colWidths.length) {
			colPosition=0;
			xPosition=xInitialPosition;
			yPosition-=cellHight;
		}
	}

	
	
}
	

