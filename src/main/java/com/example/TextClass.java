package com.example;

import java.awt.Color;
import java.io.IOException;

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
	
	void addMultiLineText(String[] text,Float leading,int xPosition,int yPosition,PDFont font,float fontSize,Color color) throws IOException {
		contentStream.beginText();
		contentStream.setFont(font, fontSize);
		contentStream.setNonStrokingColor(color);
		contentStream.newLineAtOffset(xPosition, yPosition);
		contentStream.setLeading(leading);
		
	}
}
