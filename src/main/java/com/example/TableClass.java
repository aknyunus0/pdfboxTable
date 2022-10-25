package com.example;

import java.awt.Color;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

public class TableClass {

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
	
	void addCell(String text, Color fillColor) throws IOException {
		contentStream.setStrokingColor(1f);
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
	

