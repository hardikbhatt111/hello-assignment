package com.baseClass;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtil {

	public static void captureScreenShot(WebDriver ldriver){
		// Take screenshot and store as a file format             
		 File src=((TakesScreenshot)ldriver).getScreenshotAs(OutputType.FILE); 
		 String currentDir = System.getProperty("user.dir");
		try {
		// now copy the  screenshot to desired location using copyFile method
		 
		FileUtils.copyFile(src, new File(currentDir + "/Screenshots/"+System.currentTimeMillis()+".png"));                              
		} catch (IOException e)
		 
		 {
		  System.out.println(e.getMessage()); 
		 }
	}
		 
}
