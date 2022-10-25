package com.example;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.bcel.generic.NEW;
import org.apache.pdfbox.contentstream.operator.color.SetNonStrokingColor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1CFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;


public class main {

	public static void main(String[] args) throws IOException {
		PDDocument document =new PDDocument();
		PDPage firsPage=new PDPage(PDRectangle.A4);
		document.addPage(firsPage);
		
		int pageWidth=(int) firsPage.getTrimBox().getWidth();
		int pageHeight=(int) firsPage.getTrimBox().getHeight();
		
		PDPageContentStream contentStream=new PDPageContentStream(document, firsPage);
		
		TableClass table =new TableClass(document, contentStream);
		PDFont font=PDType1Font.TIMES_ROMAN;
		
		
		int [] cellwidths= {70,160,120,90,100};
		table.setTable(cellwidths, 30, 25, -350);
		table.setTableFont(font, 16, Color.BLACK);
		
		Color tableHeaderColor=new Color(240,93,11);
		Color tableBodyColor=new Color(219,218,198);
		
		table.addCell("FIRS_NAME", tableHeaderColor);
		table.addCell("LAST_NAME", tableHeaderColor);
		table.addCell("ACCOUNT_TYPE_NAME", tableHeaderColor);
		table.addCell("ALIAS", tableHeaderColor);
		table.addCell("STATUS", tableHeaderColor);
		table.addCell("BALANCE", tableHeaderColor);
		
		
		
		

		
		
		contentStream.close();
		document.save("mypdf.pdf");
		document.close();
		System.out.println("PDF Created");
		
	
		

	}
	
	private static class TextClass{

		
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
	private static class TableClass{
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
				contentStream.newLineAtOffset(xPosition+colWidths[colPosition]-20-fontWidth, yPosition+10);
				
			}
			else {
				contentStream.newLineAtOffset(xPosition+20, yPosition+10);
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
 	}


