package com.InfoTrack.Init;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.SimpleTimeZone;
import java.util.TimeZone;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.aventstack.extentreports.Status;

public class Common 
{
	protected static Wait<WebDriver> wait;
	public static String alerttext;
	public static com.aventstack.extentreports.ExtentTest test;

	public static void logcase(String msg) 
	{
		System.out.println(msg);
		Reporter.log("<strong> <h3 style=\"color:DarkViolet\"> " + msg + "</h3> </strong>");
	}

	public static void logstep(String msg) 
	{
		System.out.println(msg);
		Reporter.log("<strong>" + msg + "</strong></br>");
	}

	/**
	 * Checks checkbox or toggle element.
	 * 
	 * @param element
	 *            Checkbox element.
	 */
	public static void checkChkBox(WebElement element) 
	{
		boolean isCheckBoxSet;
		isCheckBoxSet = element.isSelected();
		if (!isCheckBoxSet) 
		{
			element.click();
		}
	}

	public static void movetoalertAndAccept(WebDriver webDriver) 
	{
		try 
		{
			waitForAlert(webDriver);
			Alert alert = webDriver.switchTo().alert();
			alerttext = alert.getText();
			System.out.println("alert----:"+alerttext);
			alert.accept();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	public static String getalertText(WebDriver webDriver) 
	{
		try 
		{
			Alert alert = webDriver.switchTo().alert();
			alerttext= alert.getText();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return alerttext;
	}
	public static void waitForAlert(WebDriver driver)
	{
	   int i=0;
	   while(i++<5)
	   {
	        try
	        {
				Alert alert = driver.switchTo().alert();
	            break;
	        }
	        catch(Exception e)
	        {
	         pause(2);
	          continue;
	        }
	   }
	}
	
	public static String makeScreenshot(WebDriver driver, String screenshotName) 
	{
		WebDriver augmentedDriver = new Augmenter().augment(driver);
		/* Take a screenshot */
		File screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
		String nameWithExtention = screenshotName + ".png";
		/* Copy screenshot to specific folder */
		try 
		{
			String reportFolder = "test-output" + File.separator;
			String screenshotsFolder = "screenshots";
			File screenshotFolder = new File(reportFolder + screenshotsFolder);
			if (!screenshotFolder.getAbsoluteFile().exists()) 
			{
				screenshotFolder.mkdir();
			}
			FileUtils.copyFile(screenshot,
					new File(screenshotFolder + File.separator + nameWithExtention).getAbsoluteFile());
		} 
		catch (IOException e) 
		{
			log("Failed to capture screenshot: " + e.getMessage());
		}
		//log(getScreenshotLink(nameWithExtention, nameWithExtention));
		return getScreenshotLink(nameWithExtention, nameWithExtention);
	}
	
	public static void makeScreenshot2(WebDriver driver, String screenshotName) 
	{
		WebDriver augmentedDriver = new Augmenter().augment(driver);
		/* Take a screenshot */
		File screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
		String nameWithExtention = screenshotName + ".png";
		/* Copy screenshot to specific folder */
		try 
		{
			String reportFolder = "test-output" + File.separator;
			String screenshotsFolder = "screenshots";
			File screenshotFolder = new File(reportFolder + screenshotsFolder);
			if (!screenshotFolder.getAbsoluteFile().exists()) 
			{
				screenshotFolder.mkdir();
			}
			FileUtils.copyFile(screenshot,
					new File(screenshotFolder + File.separator + nameWithExtention).getAbsoluteFile());
		} 
		catch (IOException e) 
		{
			new SeleniumInit();
			//Reporter.log("Failed to capture screenshot: " + e.getMessage());
			SeleniumInit.test.log(null, "Failed to capture screenshot: " + e.getMessage());
		}
		log("<b>Please look to the screenshot - </b>"+getScreenshotLink2(nameWithExtention, nameWithExtention)+"<br>");
	}

	/**
	 * Log given message to Reporter output.
	 * 
	 * @param msg
	 *            Message/Log to be reported.
	 */
	public static void log(String msg) 
	{
		System.out.println(msg);
		Reporter.log(msg + "</br>");
	}

	public static void logStatus(String Status) 
	{
		System.out.println(Status);
		if (Status.equalsIgnoreCase("Pass")) 
		{
			log("<br><Strong><font color=#008000>Pass</font></strong></br>");
		} 
		else if (Status.equalsIgnoreCase("Fail")) 
		{
			log("<br><Strong><font color=#FF0000>Fail</font></strong></br>");
		}
	}

	/**
	 * Generates link for TestNG report.
	 * 
	 * @param screenshot_name
	 *            Screenshot name.
	 * @param link_text
	 *            Link text.
	 * @return Formatted link for TestNG report.
	 */
	public static String getScreenshotLink(String screenshot_name, String link_text) 
	{
		/*String dataFilePath = "test-output/screenshots";
		File datafile = new File(dataFilePath);
		String fullpath = datafile.getAbsolutePath();*/
		log("<br><Strong><font color=#FF0000>--Failed</font></strong>");
		return "<a href='../test-output/screenshots/" + screenshot_name + "' target='_new'>" + link_text + "</a>";
	}
	
	public static String getScreenshotLink2(String screenshot_name, String link_text) 
	{
		String dataFilePath = "test-output/screenshots";
		File datafile = new File(dataFilePath);
		String fullpath = datafile.getAbsolutePath();
		return "<a href='" + fullpath + "/" + screenshot_name + "' target='_new'>" + link_text + "</a>";
	}

	/**
	 * Checks whether the needed WebElement is displayed or not.
	 * 
	 * @param element
	 *            Needed element
	 * 
	 * @return true or false.
	 */
	public static boolean isElementDisplayed(WebElement element) 
	{
		try 
		{
			return element.isDisplayed();
		} 
		catch (Exception e ) 
		{
			return false;
		}
	}
	public static boolean isElemenEnabled(WebElement element) 
	{
		try 
		{
			return element.isEnabled();
		} 
		catch (Exception e) 
		{
			return false;
		}
	}

	public static boolean isElementNotDisplayed(WebElement element) 
	{
		try 
		{
			return !element.isDisplayed();
		} 
		catch (Exception e) 
		{
			return false;
		}
	}

	/**
	 * Wait(max. 1 minute) till given element does not disappear from page.
	 * 
	 * @param by
	 *            Locator of element.
	 * @return
	 * @throws InterruptedException
	 */
	
	public static boolean isElementDisplayed(WebDriver driver, By elementLocator) 
	{
		try 
		{
			return driver.findElement(elementLocator).isDisplayed();
		} 
		catch (Exception e) 
		{
			return false;
		}
	}

	/**
	 * Set data in to clipboard
	 * 
	 * @param string
	 */
	
	public static void PresenceOfElement(By locator, WebDriver driver) 
	{
		//long starttime=System.currentTimeMillis();
		(new WebDriverWait(driver, 60)).until(ExpectedConditions.presenceOfElementLocated(locator));
		//long endtime=System.currentTimeMillis()-starttime;
		/*SeleniumInit.ExplicitWait=SeleniumInit.ExplicitWait+endtime;
		System.out.println(" Presence of Element wait --> "+SeleniumInit.ExplicitWait);*/
	}
	
	public static void clickableElement(By locator, WebDriver driver) 
	{
		//long starttime=System.currentTimeMillis();
		(new WebDriverWait(driver, 60)).until(ExpectedConditions.elementToBeClickable(locator));
		//long endtime=System.currentTimeMillis()-starttime;
		/*SeleniumInit.ExplicitWait=SeleniumInit.ExplicitWait+endtime;
		System.out.println(" clickable Element (By locator)  wait --> "+SeleniumInit.ExplicitWait);*/
	}
	public static void clickableElement(WebElement webelement, WebDriver driver) 
	{
		//long starttime=System.currentTimeMillis();
		(new WebDriverWait(driver, 60)).until(ExpectedConditions.elementToBeClickable(webelement));
		//long endtime=System.currentTimeMillis()-starttime;
		/*SeleniumInit.ExplicitWait=SeleniumInit.ExplicitWait+endtime;
		System.out.println(" clickable Element (By element) wait --> "+SeleniumInit.ExplicitWait);*/
	}
	
	public static void selectFromCombo(WebElement element, String value) 
	{
		Select select = new Select(element);
		select.selectByValue(value);
	}

	/**
	 * Select data form dropdown or combobox by visible element
	 * 
	 * @param element
	 * @param value
	 */
	public static void selectFromComboByVisibleElement(WebElement element, String value) 
	{
		Select select = new Select(element);
		select.selectByVisibleText(value);
	}
	public static void selectFromComboByIndex(WebElement element, int index) 
	{
		Select select = new Select(element);
		select.selectByIndex(index);
	}
	public static String selectFromComboByIndexReturnValue(WebElement element, int index) 
	{
		Select select = new Select(element);
		select.selectByIndex(index);
		Common.pause(1);
		return select.getFirstSelectedOption().getText();
	}
	public static String selectFromComboByIndexReturnValue(WebElement element) 
	{
		Select select = new Select(element);
		return select.getFirstSelectedOption().getText();
	}
	public static void highlightElement(WebDriver driver, WebElement element) 
	{
		((JavascriptExecutor) driver).executeScript("arguments[0].style.border = '3px solid yellow'", element);
		pause(2);
	}
	public static void jsClick(WebDriver driver, WebElement element) 
	{
		highlightElement(driver, element);
		((JavascriptExecutor) driver).executeScript("return arguments[0].click();", element);
		// this.waitForAjax("0");
	}

	
	 
	public static void pause(int secs) 
	{
		try 
		{
			System.out.println(" Added wait --> "+secs);
			Thread.sleep(secs * 1000);
		/*	SeleniumInit.tim=SeleniumInit.tim+secs;
			System.out.println(" Pause wait --> "+SeleniumInit.tim);*/
		} 
		catch (InterruptedException interruptedException) 
		{
			interruptedException.printStackTrace();
		}
	}

	/**
	 * Get random numeric of given lenth.
	 * 
	 * @param length
	 *            desired length.
	 * @return
	 */
	
	public static void Log(String msg) 
	{
		//Reporter.log(msg + "</br>");
		test.log(Status.INFO,"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"+" "+msg);
	}
	
	
	public static  boolean isElementPresent(WebDriver driver,By identifier) 
	{		
		int len = driver.findElements(identifier).size();		
		if (len == 0)		
			return false;		
		else		
			return true;		
	}
	
	public static String getCurrentTimeStampString2() 
	{
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		TimeZone timeZone = TimeZone.getDefault();
		Calendar cal = Calendar.getInstance(new SimpleTimeZone(timeZone.getOffset(date.getTime()), "IST"));
		sd.setCalendar(cal);
		return sd.format(date);
	}
	
	public static String getCurrentTimeStampString() 
	{
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmssSS");
		TimeZone timeZone = TimeZone.getDefault();
		Calendar cal = Calendar.getInstance(new SimpleTimeZone(timeZone.getOffset(date.getTime()), "IST"));
		sd.setCalendar(cal);
		return sd.format(date);
	}
	
}
