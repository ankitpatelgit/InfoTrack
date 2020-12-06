package com.InfoTrack.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestData {

	public static String uploadFilePath(String filename) {
		String dataFilePath = "Data/" + filename + "";
		File datafile = new File(dataFilePath);
		String fullpath = datafile.getAbsolutePath();
		return fullpath;
	}

	public static XSSFSheet ExcelWSheet;
	public static XSSFWorkbook ExcelWBook;
	public static XSSFCell Cell;
	public static XSSFRow Row;
	// Data provider
	

//  write data into excel file
	
	public static void writeExcel(String filename, String sheetname,  String cell[]) throws IOException {
		File datafile = new File(filename);
		String fullpath = datafile.getAbsolutePath();
		ExcelWBook = new XSSFWorkbook(fullpath);
		ExcelWSheet = ExcelWBook.getSheet(sheetname);
		int totalRows = ExcelWSheet.getLastRowNum();
		try {
			int rowno = totalRows + 1;
			FileInputStream inputStream = new FileInputStream(new File(fullpath));
			@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			XSSFSheet firstSheet = workbook.getSheetAt(0);
			XSSFRow row = firstSheet.createRow(rowno);
			for(int i=0;i<cell.length;i++)
			{
				row.createCell(i).setCellValue(cell[i].toString());
			}
			inputStream.close();
			FileOutputStream fos = new FileOutputStream(new File(fullpath));
			workbook.write(fos);
			fos.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	// Data provider
		@SuppressWarnings("unchecked")
		public static <UnicodeString> UnicodeString getCellData(int RowNum, int ColNum) throws Exception {
			try {
				Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
				int dataType = Cell.getCellType();
				if (dataType == 3) {
					return (UnicodeString) "";
				} else {
					DataFormatter formatter = new DataFormatter();
					UnicodeString Data = (UnicodeString) formatter.formatCellValue(Cell);
					return Data;
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				throw (e);
			}
		}
	
	
	public static String getCellValue(String datafile,String sheet,int i , int j)
	{
		String ser=null;
		try {
			FileInputStream ExcelFile = new FileInputStream(datafile);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(sheet);
			ser = getCellData(i, j);
		} catch (FileNotFoundException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ser;
	}

	
	public static void setCellData(String DataFile,String sheet, String Result,  int RowNum, int ColNum) throws Exception  
	 {
	  try
	  {
		  FileInputStream ExcelFile = new FileInputStream(DataFile);
		  ExcelWBook = new XSSFWorkbook(ExcelFile);
		  ExcelWSheet = ExcelWBook.getSheet(sheet);
	   Row  = ExcelWSheet.getRow(RowNum);
	   Cell = Row.getCell(ColNum);
	   if (Cell == null) 
	   {
	    Cell = Row.createCell(ColNum);
	    Cell.setCellValue(Result);
	   } 
	   else 
	   {
	    Cell.setCellValue(Result);
	   }
	   
	   /*Constant variables Test Data path and Test Data file name*/
	   FileOutputStream fileOut = new FileOutputStream(DataFile);
	   ExcelWBook.write(fileOut);
	   fileOut.flush();
	   fileOut.close();
	  }
	  catch(Exception e)
	  {
	   throw (e);
	  }
	 }
	
	
	public static void deletePastScreenshots(String path)
	{
		File index = new File(path);
		
		if(index.exists() && index.isDirectory())
		{
			String[]entries = index.list();
			
			for(String s: entries)
			{
			    File currentFile = new File(index.getPath(),s);
			    currentFile.delete();
			}
			
			index.delete();
		}
	}
	
	public static String getValueFromConfig(String Datafile, String value) throws IOException {
		String result="";
		File file = new File(Datafile);
		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Properties prop = new Properties();
		try {
				prop.load(fileInput);
				result = prop.getProperty(value);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
		}
		return result;
	}
	
}
