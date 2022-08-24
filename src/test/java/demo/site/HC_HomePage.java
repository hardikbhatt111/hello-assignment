package demo.site;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.baseClass.BaseClass;
import org.apache.log4j.BasicConfigurator;  
import org.apache.log4j.LogManager;  
import org.apache.log4j.Logger;  
import com.baseClass.ExcelUtility;
import com.baseClass.ScreenshotUtil;

public class HC_HomePage extends BaseClass {
		
	private static final Logger logger = LogManager.getLogger(HC_HomePage.class);
		
	String lstName=null;
	String keyword="Miami";
	String startDt,endDt,uiDt,eventName,eventDate;
	
	@BeforeClass
	public void launch()
	{
		BasicConfigurator.configure();
		logger.info("Browser Launched");
		String name = browserName();
		switch(name) {
		  case "Chrome":
			    System.setProperty(propconfig.getProperty("chromedrivername"), currentdir+propconfig.getProperty("chromedriverpath"));
		        wd = new ChromeDriver();
		    break;
		  case "Firefox":
			    System.setProperty(propconfig.getProperty("ffdrivername"), currentdir+propconfig.getProperty("ffdriverpath"));
		        wd = new FirefoxDriver();
		    break;
		  default:
			  System.out.println("Invalid Browser Selection");
		}
		wd.get(propconfig.getProperty("weburl"));
        wd.manage().window().maximize();
	}
	
	//@Test(priority=1)
	public void dropBoxSelection() throws Exception
	{
		wd.findElement(By.xpath("//*[@id=\"vs1__combobox\"]/div[1]/input")).click();
		Thread.sleep(2000);
		wd.findElement(By.xpath("//*[@id=\"vs1__option-0\"]")).click();
		submit();
	}
	
	//@Test(priority=2)
		public void keyword() throws Exception
		{
			Thread.sleep(3000);
			wd.findElement(By.id("searchInput")).sendKeys(keyword);
			submit();
		}
	
	//@Test(priority=3)
	public void records() throws Exception
	{
		Thread.sleep(5000);
		we = wd.findElement(By.xpath("//*[@id=\"app\"]/div[2]/div[3]/label/select"));
		Select sel = new Select(we);
		sel.selectByVisibleText("200");
		Thread.sleep(5000);
	}
	
	//@Test(priority=4)
	public void sorting() throws Exception
	{
		Thread.sleep(3000);
		we = wd.findElement(By.cssSelector("#app > div.filter-container > div:nth-child(2) > svg > path"));
		Actions actions1 = new Actions(wd);
		actions1.moveToElement(we).click().perform();
		Thread.sleep(3000);
		we1 = wd.findElement(By.cssSelector("#app > div.filter-container > div:nth-child(2) > svg > path"));
		Actions actions2 = new Actions(wd);
		actions2.moveToElement(we1).click().perform();
	}
	
	//@Test(priority=5)
	public void validatingList() throws Exception
	{
		ExcelUtility.CreateExcel("HC-Excel", LocalDate.now().toString());
		
		ExcelUtility.WriteToExcel(ExcelUtility.getExcelpath(), LocalDate.now().toString(), 1, "Name");
		
		listCheck();
		Thread.sleep(3000);
		wd.findElement(By.xpath("//*[@id=\"app\"]/ul/li[6]/button")).click();
		Thread.sleep(3000);
		listCheck();
	}
	
	//@Test(priority=6)
	public void startDate() throws Exception
	{
		wd.navigate().refresh();
		Thread.sleep(2000);
		wd.findElement(By.cssSelector("#startDate > input")).sendKeys(propconfig.getProperty("startDate")); //MM/DD/YYYY
		submit();
		Thread.sleep(2000);
		
		List<WebElement> lst = wd.findElements(By.xpath("//*[@id=\"app\"]/div[3]/a"));
		//System.out.println("Size = "+lst.size());
		
		if(lst.size() > 1)
		{
			for (int i=1; i<=lst.size();i++){
				uiDt = wd.findElement(By.xpath("//*[@id=\"app\"]/div[3]/a["+i+"]/div/span")).getText().replaceAll("^.*on\s*", "").trim(); //YYYY-MM-DD
				  
				startDt = propconfig.getProperty("startDate").replaceAll("(\\d+)/(\\d+)/(\\d+)", "$3-$2-$1");
				  
				dateComparison(startDt,uiDt);
			}
		}
		else if(lst.size() == 1)
		{
				uiDt = wd.findElement(By.xpath("//*[@id=\"app\"]/div[3]/a/div/span")).getText().replaceAll("^.*on\s*", "").trim(); //YYYY-MM-DD
				
				startDt = propconfig.getProperty("startDate").replaceAll("(\\d+)/(\\d+)/(\\d+)", "$3-$2-$1");
				
				dateComparison(startDt,uiDt);
		}
		else
		{
			logger.error("No Match Found");
		}
	}
	
	//@Test(priority=7)
	public void endDate() throws Exception
	{
		wd.navigate().refresh();
		Thread.sleep(2000);
		wd.findElement(By.cssSelector("#endDate > input")).sendKeys(propconfig.getProperty("endDate")); //MM/DD/YYYY
		submit();
		Thread.sleep(2000);
		
		List<WebElement> lst = wd.findElements(By.xpath("//*[@id=\"app\"]/div[3]/a"));
		
		if(lst.size() > 1)
		{
			for (int i=1; i<=lst.size();i++){
				
				uiDt = wd.findElement(By.xpath("//*[@id=\"app\"]/div[3]/a["+i+"]/div/span")).getText().replaceAll("^.*on\s*", "").trim(); //YYYY-MM-DD
				  
				endDt = propconfig.getProperty("endDate").replaceAll("(\\d+)/(\\d+)/(\\d+)", "$3-$2-$1");
				  
				dateComparison(endDt,uiDt);
			}
		}
		else if(lst.size() == 1)
		{
				uiDt = wd.findElement(By.xpath("//*[@id=\"app\"]/div[3]/a/div/span")).getText().replaceAll("^.*on\s*", "").trim(); //YYYY-MM-DD
				
				endDt = propconfig.getProperty("endDate").replaceAll("(\\d+)/(\\d+)/(\\d+)", "$3-$2-$1");
				
				dateComparison(endDt,uiDt);
		}
		else
		{
			logger.error("No Match Found");
		}
	}
	
	@Test (priority=8)
	public void eventPage() throws Exception
	{
		submit();
		Thread.sleep(3000);
		List<WebElement> lst = wd.findElements(By.xpath("//*[@id=\"app\"]/div[3]/a"));
		System.out.println("Size = "+lst.size());
		
		for (int i=1; i<lst.size();i++){

			if(i>1 && i<6)
		      {
		    	  wd.navigate().back();
		    	  Thread.sleep(3000);
		    	  submit();
		      }
		      if(i==20)
		    	  break;
		      
		      Thread.sleep(3000);
		      lstName = wd.findElement(By.xpath("//*[@id=\"app\"]/div[3]/a["+i+"]/div/h4")).getText();
		      uiDt = wd.findElement(By.xpath("//*[@id=\"app\"]/div[3]/a["+i+"]/div/span")).getText().replaceAll("^.*on\s*", "").trim(); //YYYY-MM-DD
		      //System.out.println(" Name = "+lstName + " Date = "+uiDt);
		      
		      wd.findElement(By.xpath("//*[@id=\"app\"]/div[3]/a["+i+"]")).click();
		      Thread.sleep(5000);
		      eventName = wd.findElement(By.tagName("h3")).getText();
		      eventDate = wd.findElement(By.xpath("//*[@id=\"app\"]/div[2]/div/div[1]/p[3]")).getText().replaceAll("\s*Date\s*:\s*", "").trim();
		      
		      //System.out.println(" Event Name = "+eventName + " Event Date = "+eventDate);
		      
		      if(lstName.compareTo(eventName) == 0)
		      {
		    	  logger.info("List Name = "+lstName+" matches with Event Name = "+eventName);
		      }
		      else
		      {
		    	  Thread.sleep(500); 
				  ScreenshotUtil.captureScreenShot(wd);
		      }
		      
		      if(uiDt.compareTo(eventDate) == 0)
		      {
		    	  logger.info("List Date = "+uiDt+" matches with Event Date = "+eventDate);
		      }
		      else
		      {
		    	  Thread.sleep(500); 
				  ScreenshotUtil.captureScreenShot(wd);
		      }
		      
		      
		}
	}
	
	public void submit()
	{
		wd.findElement(By.xpath("//*[@id=\"app\"]/form/div/div[5]/button")).click();
	}
	
	public void listCheck() throws Exception
	{
		List<WebElement> lst = wd.findElements(By.xpath("//*[@id=\"app\"]/div[3]/a"));
		//System.out.println("Size = "+lst.size());
		
		for (int i=1; i<lst.size()-1;i++){
		      //System.out.println("List Name: " + wd.findElement(By.xpath("//*[@id=\"app\"]/div[3]/a["+i+"]/div/h4")).getText());
		      lstName = wd.findElement(By.xpath("//*[@id=\"app\"]/div[3]/a["+i+"]/div/h4")).getText();	      
		      
			  if(lstName.contains(keyword)) 
			  {		 
				  ExcelUtility.WriteToExcel(ExcelUtility.getExcelpath(),LocalDate.now().toString(), 1, lstName);
			  }
			  else
			  {
				  WebElement element = wd.findElement(By.xpath("//*[@id=\"app\"]/div[3]/a["+i+"]/div/h4"));
				  ((JavascriptExecutor) wd).executeScript("arguments[0].scrollIntoView(true);", element);
				  Thread.sleep(500); 
				  ScreenshotUtil.captureScreenShot(wd);
				  break;
			  }
			  
		    }
		WebElement element = wd.findElement(By.tagName("form"));

		JavascriptExecutor js = (JavascriptExecutor)wd;
		js.executeScript("arguments[0].scrollIntoView();", element); 
	}
	
	
	public void dateComparison(String startDt, String uiDt) throws ParseException
	{
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = sdformat.parse(startDt);
	    Date d2 = sdformat.parse(uiDt);

	    if(d1.compareTo(d2) > 0) {
	         logger.info(startDt+" occurs after "+uiDt);
	    }else if(d1.compareTo(d2) < 0) {
	          logger.info(startDt+" occurs before "+uiDt);
	    } else if(d1.compareTo(d2) == 0) {
	         System.out.println(startDt+" equals "+uiDt);
	    }
	}
	
	@AfterClass
	public void destroy()
	{
		//wd.quit();
	}
}
