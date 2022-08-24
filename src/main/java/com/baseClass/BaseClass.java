package com.baseClass;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BaseClass {
	
	public static WebDriver wd;
	public static WebElement we,we1;
	public static String currentdir;
	public static Properties propconfig;
		
	public BaseClass()
	{	
			try 
			{
				//System.out.println("BaseClass constructor called...."); 
				currentdir = System.getProperty("user.dir");
			  
				//System.out.println("Current Directory...."+currentdir); 
				propconfig = new Properties();
						
				FileInputStream fp = new FileInputStream(currentdir+"/src/test/resources/config.properties"); 
				propconfig.load(fp);
			}
		  	catch (IOException e) 
			{
		  		e.printStackTrace(); System.out.println(e); 
		  	}
	}
	
	public String browserName()
	{
		//System.out.println("Browser Name = "+propconfig.getProperty("Browser"));
		return propconfig.getProperty("Browser");
	}

}
