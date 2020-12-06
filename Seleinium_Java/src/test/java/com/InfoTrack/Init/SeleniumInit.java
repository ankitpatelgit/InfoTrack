package com.InfoTrack.Init;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;


import com.InfoTrack.Login.Login_Process;
import com.InfoTrack.Login.Login_Verification;
import com.InfoTrack.Utility.TestData;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class SeleniumInit{
	public String suiteName = "";
	public String testName = "";
	public static String testUrl;
	public static String seleniumHub; // Selenium hub IP
	public static String seleniumHubPort; // Selenium hub port
	protected String targetBrowser; // Target browser
	protected static String test_data_folder_path = null;
	public static String currentWindowHandle = "";// Get Current Window handle
	public static String browserName = "";
	public static String osName = "";
	public String HomeDir="";
	public static String browserVersion = "";
	public static String browseruse = "";
	public static String Testenvironment="";
	public static String Url="";
	public static String AuthorName;
	public static String ModuleName;
	public static String Version="";
	public static String header="";
	public static int col=0;
	

	protected static String screenshot_folder_path = null;
	public static String currentTest; // current running test
	protected static Logger logger = Logger.getLogger("testing");
	protected WebDriver driver;
	
	public static com.aventstack.extentreports.ExtentReports extent;
	public static com.aventstack.extentreports.ExtentTest test;
	public static ExtentHtmlReporter htmlReporter;
	
	
	//Google -> Login Screen -> Login
	protected Login_Process login_Process;
    protected Login_Verification login_Verification;
  	
	@BeforeSuite
	public void fetchSuite() throws IOException
	{
		TestData.deletePastScreenshots(System.getProperty("user.dir") +"\\test-output\\screenshots");
		htmlReporter= new ExtentHtmlReporter(System.getProperty("user.dir") +"/test-output/InfoTrackTestAutomation.html");
		htmlReporter.setAppendExisting(false);
		htmlReporter.loadXMLConfig(new File(System.getProperty("user.dir")+"\\extent-config.xml"));
		extent = new com.aventstack.extentreports.ExtentReports();
		extent.attachReporter(htmlReporter);
		Testenvironment=TestData.getValueFromConfig("config.properties","TestEnvironment");
		extent.setSystemInfo("Environment", TestData.getValueFromConfig("config.properties",Testenvironment));
		extent.setSystemInfo("User Name", TestData.getCellValue("Data/DataSet.xlsx","Login",1,0));
		extent.setSystemInfo("Browser", TestData.getValueFromConfig("config.properties","Browser"));
		header=Common.getCurrentTimeStampString2();
		
	}
	
	@BeforeTest(alwaysRun = true)
	public void fetchSuiteConfiguration(ITestContext testContext) throws IOException 
	{
		
		testUrl=TestData.getValueFromConfig("config.properties",Testenvironment);
		
	}
	
	
	@Parameters({ "Author", "Module" })
	@BeforeMethod(alwaysRun = true)
	public void setUp(String Author,String Module,Method method, ITestContext testContext,ITestResult testResult) throws IOException, InterruptedException 
	{
		AuthorName=Author;
		ModuleName=Module;
		targetBrowser =TestData.getValueFromConfig("config.properties","Browser");
		browserName=targetBrowser;
		currentTest = method.getName(); // get Name of current test.
		String SCREENSHOT_FOLDER_NAME = "screenshots";
		String TESTDATA_FOLDER_NAME = "test_data";
		test_data_folder_path = new File(TESTDATA_FOLDER_NAME).getAbsolutePath();
		screenshot_folder_path = new File(SCREENSHOT_FOLDER_NAME).getAbsolutePath();
		
		try{
		DesiredCapabilities capability = null;		
		if (targetBrowser == null || targetBrowser.contains("chrome") || targetBrowser.equalsIgnoreCase("chrome"))
		{
			capability = DesiredCapabilities.chrome();
			File driverpath = new File("Resource/chromedriver.exe");
			String path1 = driverpath.getAbsolutePath();
			System.setProperty("webdriver.chrome.driver",path1);
			final ChromeOptions chromeOptions = new ChromeOptions();
			capability.setBrowserName("chrome");
			capability.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
			capability.setJavascriptEnabled(true);
			capability.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
			capability.setCapability("disable-popup-blocking", true);
			osName = capability.getPlatform().name();
			capability = DesiredCapabilities.chrome();
			capability.setBrowserName("chrome");
			capability.setJavascriptEnabled(true);
			osName = capability.getPlatform().name();
			browserVersion = capability.getVersion();
			driver= new ChromeDriver(capability);
		}else if (targetBrowser.contains("safari"))
		{
			capability = DesiredCapabilities.safari();
			capability.setJavascriptEnabled(true);
			capability.setBrowserName("safari");
			driver = new SafariDriver(capability);
		}
		
		suiteName = testContext.getSuite().getName();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(testUrl);
		
		
		suiteName = testContext.getSuite().getName();

		//Login -> Login
		login_Process = new Login_Process(driver) ;
		login_Verification = new Login_Verification(driver) ;
 	   
		}catch(Exception e){
			e.printStackTrace();
			test.log(Status.FAIL, testResult.getThrowable());
		}
	}
	
	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult testResult)
	{
		String screenshotName="";
		testName = testResult.getName();
		
		
		try 
		{
			Version= driver.getCurrentUrl();
			screenshotName = Common.getCurrentTimeStampString(); //+ testName;
			if (!testResult.isSuccess()) 
			{
				/* Print test result to Jenkins Console */
				System.out.println();
				System.out.println("TEST FAILED - " + testName);
				System.out.println();
				System.out.println("ERROR MESSAGE: " + testResult.getThrowable());
				System.out.println("\n");
				Reporter.setCurrentTestResult(testResult);
				/* Make a screenshot for test that failed */
				if(testResult.getStatus()==ITestResult.FAILURE)
				{ 
					System.out.println("1 message from tear down");
					  test.log(Status.FAIL,"Please look to the screenshot :- "+ Common.makeScreenshot(driver, screenshotName));
					  test.log(Status.FAIL, testResult.getThrowable());
		        }
				if(testResult.getStatus()==ITestResult.SKIP)
				{
					  test.log(Status.SKIP, testResult.getThrowable());
					  driver.manage().deleteAllCookies();
						 driver.close();
						 driver.quit();
		        }
				driver.manage().deleteAllCookies();
				driver.close();
				driver.quit();
			}
			else 
			{
				driver.manage().deleteAllCookies();
				driver.close();
				driver.quit();
			}
		}catch (Throwable throwable)
		{
			try
			{
				if(testResult.getStatus()==ITestResult.SKIP)
				{
					test.log(Status.SKIP, testResult.getThrowable());
				}else
				{
					test.log(Status.FAIL, testResult.getThrowable());
				}
				driver.manage().deleteAllCookies();
				driver.close();
				driver.quit();
			}catch(Exception e)
			{
				System.out.println(e);
			}
		}
	}
	
	
	@AfterSuite(alwaysRun = true)
	public void postConfigue()
	{
		extent.setSystemInfo("Version",Version.split("/")[3]);
		extent.flush();
	}
	public void log(String msg) 
	{
		Reporter.log(msg + "</br>");
		test.log(Status.INFO,"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"+" "+msg);
		System.out.println(msg);
	}
	public void logStep(int msg1, String msg2) 
	{
		Reporter.log("Step-"+msg1+" : "+msg2 + "</br>");
		test.log(Status.INFO,"Step-"+msg1+" : "+msg2);
		System.out.println("Step-"+msg1+" : "+msg2);// for jenkins  
	}
	public void logCase(String msg)
	{
		System.out.println("Test Case : "+msg);
		test=extent.createTest(msg);
	}
	public static void slog(String msg)
	{
		Reporter.log(msg + "</br>");
		test.log(Status.INFO,"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"+" "+msg);
		System.out.println(msg);
	}
	public void assignAuthor_Module(String AuthorNm,String ModuleNm )
	{
		test.assignAuthor(AuthorNm);
		test.assignCategory(ModuleNm);
	}
	
	public void logStatus(final int test_status,String msg) 
	{
		switch (test_status) 
		{
			case ITestStatus.PASSED:
				test.log(Status.PASS,"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp "+msg+" ");
				break;
			case ITestStatus.FAILED:
				String screenshotName = Common.getCurrentTimeStampString();
				test.log(Status.FAIL,"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"+msg+" Please look to the screenshot :- "+ Common.makeScreenshot(driver, screenshotName));
				MakeScreenshots();
				break;
			case ITestStatus.SKIPPED:
				test.log(Status.SKIP," "+msg);
				break;
			default:
				break;
		}
	}
	public void MakeScreenshots()
	{
		String screenshotName = Common.getCurrentTimeStampString() + testName;
		Common.makeScreenshot2(driver, screenshotName);
	}
}