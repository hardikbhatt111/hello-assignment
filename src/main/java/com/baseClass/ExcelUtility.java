package com.baseClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static XSSFCell Cell;
	
	private static String excelpath;

	private static int rowno;
	
	public static String getExcelpath() {
		return excelpath;
	}

	public static void setExcelpath(String excelpath) {
		ExcelUtility.excelpath = excelpath;
	}

	public static int getRowno() {
		return rowno;
	}

	public static void setRowno(int rowno) {
		ExcelUtility.rowno = rowno;
	}

	public static Object[][] getTableArray(String FilePath, String SheetName) throws Exception {   

   String[][] tabArray = null;

   try {

	   FileInputStream ExcelFile = new FileInputStream(FilePath);

	   // Access the required test data sheet

	   ExcelWBook = new XSSFWorkbook(ExcelFile);

	   ExcelWSheet = ExcelWBook.getSheet(SheetName);

	   int startRow = 1;

	   int startCol = 1;

	   int ci,cj;

	   int totalRows = ExcelWSheet.getLastRowNum();

	   // you can write a function as well to get Column count

	   int totalCols = ExcelWSheet.getRow(0).getLastCellNum();
	   totalCols=totalCols-1;

	   tabArray=new String[totalRows][totalCols];

	   ci=0;

	   for (int i=startRow;i<=totalRows;i++, ci++) {           	   

		  cj=0;

		   for (int j=startCol;j<=totalCols;j++, cj++){

			   tabArray[ci][cj]=getCellData(i,j);

				}

			}

		}

	catch (FileNotFoundException e){

		System.out.println("Could not read the Excel sheet");

		e.printStackTrace();

		}

	catch (IOException e){

		System.out.println("Could not read the Excel sheet");

		e.printStackTrace();

		}

	return(tabArray);

	}

public static String getCellData(int RowNum, int ColNum) throws Exception {

	String CellData = null;
	
	try{

		Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);

		 CellType dataType =Cell.getCellType();

		
		if  (dataType.toString().equalsIgnoreCase("3")) {

			return "";
		}
		
		else if(dataType.toString().equalsIgnoreCase("1"))
		{
			CellData = Cell.getStringCellValue();
		}

		else if( dataType.toString().equalsIgnoreCase("0"))
		{
			double cellnum = Cell.getNumericCellValue();	
			CellData = Double.toString(cellnum);
		}
		return CellData;
		
		}catch (Exception e){

		System.out.println(e.getMessage());

		throw (e);

		}

	}
	
		public static String CreateExcel(String excelname, String sheetname)
		{
			int k=0;
			Path currentPath = Paths.get("").toAbsolutePath();
			
			excelpath = String.join("\\", currentPath.toString(), excelname +".xlsx");
			File file = new File(excelpath);
		
			if(file.exists())
			{
				try {
					FileInputStream fis = new FileInputStream(excelpath);
					ExcelWBook = new XSSFWorkbook(fis);
				} catch (Exception e) {
					e.printStackTrace();
				}
				for(int i=0;i<ExcelWBook.getNumberOfSheets();i++)
				{
					if(ExcelWBook.getSheetName(i).equalsIgnoreCase(sheetname))
							{
						ExcelWSheet = ExcelWBook.getSheet(sheetname);
						k =2;
						break;
							}
				}		
							if (k!=2)
							{
								ExcelWSheet = ExcelWBook.createSheet(sheetname);
							}
				
			}else {	
			
			ExcelWBook = new XSSFWorkbook();
			ExcelWSheet = ExcelWBook.createSheet(sheetname);
			}
			try {
			FileOutputStream fos = new FileOutputStream(file);
			ExcelWBook.write(fos);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			return excelpath;
		}
		
		public static void WriteToExcel(String filepath, String sheetname, int col, String val) throws Exception
		{	
			FileInputStream fis = new FileInputStream(filepath);
			ExcelWBook = new XSSFWorkbook(fis);
			
			ExcelWSheet = ExcelWBook.getSheet(sheetname);
			
			if(ExcelWSheet.getPhysicalNumberOfRows()==0)
			{
				rowno=0;
				ExcelWSheet.createRow(rowno).createCell(col).setCellValue(val);
				
			}else {
				rowno = ExcelWSheet.getLastRowNum()+1;
				ExcelWSheet.createRow(rowno).createCell(col).setCellValue(val);
			}
			
			try {
			FileOutputStream fos = new FileOutputStream(filepath);
			ExcelWBook.write(fos);
			fos.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		public static void WriteToExistingRow(String filepath, String sheetname, int row, int col, String val) throws Exception
		{	
			FileInputStream fis = new FileInputStream(filepath);
			ExcelWBook = new XSSFWorkbook(fis);
			
			ExcelWSheet = ExcelWBook.getSheet(sheetname);
			ExcelWSheet.getRow(row).createCell(col).setCellValue(val);
			
			try {
			FileOutputStream fos = new FileOutputStream(filepath);
			ExcelWBook.write(fos);
			fos.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		
		public static int getCurrentRowNumber() throws IOException
		{
		int row_num;
		if(ExcelWSheet.getPhysicalNumberOfRows()==0)
		{
		row_num=0;
		//ExcelWSheet.createRow(rowno).createCell(col).setCellValue(val);
		}else {
		row_num = ExcelWSheet.getLastRowNum()+1;
		//ExcelWSheet.createRow(rowno).createCell(col).setCellValue(val);
		}
		return row_num;
		}
}
