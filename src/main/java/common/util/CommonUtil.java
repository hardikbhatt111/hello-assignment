package common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.baseClass.BaseClass;

public class CommonUtil extends BaseClass{

	public ArrayList<String> formExcel() throws Exception
	{
		currentdir = System.getProperty("user.dir");
		File file =    new File(currentdir+"/"+propconfig.getProperty("filePath"));
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet ws = wb.getSheet("Details");
		DataFormatter formatter = new DataFormatter();
		int rowCount = ws.getLastRowNum()-ws.getFirstRowNum();
		ArrayList<String> list = new ArrayList<String>();
		
		for (int i = 0; i < rowCount+1; i++) {

	        Row row = ws.getRow(i);
	        for (int j = 0; j < row.getLastCellNum(); j++) {
	            //System.out.print(row.getCell(j).getStringCellValue().toString()+"|| ");
	            //System.out.print(formatter.formatCellValue(row.getCell(j))+"|| ");
	            list.add(formatter.formatCellValue(row.getCell(j)));
	        }
	        System.out.println();
	    }
		wb.close();
		return(list);
	}
	
	
	
}
