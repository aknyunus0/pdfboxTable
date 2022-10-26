package com.example;

import java.awt.Color;
import java.io.IOException;

import javax.swing.text.AbstractDocument.Content;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

public class TextClass {

	PDDocument document;
	PDPageContentStream contentStream;
	
	public TextClass(PDDocument document, PDPageContentStream contentStream) {		
		this.document = document;
		this.contentStream = contentStream;
	}
	
	void addSingelLine(String text,int xPosition,int yPosition,PDFont font,float fontSize,Color color) throws IOException {
		contentStream.beginText();
		contentStream.setFont(font, fontSize);
		contentStream.setNonStrokingColor(color);
		contentStream.newLineAtOffset(xPosition, yPosition);
		contentStream.showText(text);
		contentStream.endText();
		contentStream.moveTo(0, 0);
	}
	
	void addMultiLineText(String[] textarr,Float leading,int xPosition,int yPosition,PDFont font,float fontSize,Color color) throws IOException {
		contentStream.beginText();
		contentStream.setFont(font, fontSize);
		contentStream.setNonStrokingColor(color);
		contentStream.newLineAtOffset(xPosition, yPosition);
		contentStream.setLeading(leading);
		
		for(String text:textarr) {
			
			contentStream.showText(text);
			contentStream.newLine();
			
		}
		contentStream.endText();
		contentStream.moveTo(0, 0);
	}
	
	float getTextWidth(String text,PDFont font,float fontSize) throws IOException {
		
		return font.getStringWidth(text)/1000*fontSize;
	}
}
