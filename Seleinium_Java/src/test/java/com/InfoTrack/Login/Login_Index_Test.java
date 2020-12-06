package com.InfoTrack.Login;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.InfoTrack.Init.SeleniumInit;
import com.InfoTrack.Utility.TestData;

public class Login_Index_Test extends SeleniumInit{
	
	@Test 
	public void verifyLoginWithValidInputs()
	{
		try{
			logCase("Verify if a user able to login with a valid username and valid password.");
			int numOfFailure=0;
			int step=1;
		
			assignAuthor_Module(AuthorName,ModuleName);
			
			String email = TestData.getCellValue("Data/DataSet.xlsx","Login",1,0);
			String password = TestData.getCellValue("Data/DataSet.xlsx","Login",1,1);
			
		
			logStep(step++,"Enter URL : "+testUrl);
			
			logStep(step++,"Enter email address in input box: "+email);
			login_Process.enterEmailAddress(email);
			
			log(" To verify if next button is enabled.");
			if(login_Verification.verifyNextButtonEnable())
				logStatus(1,"Next button is enabled.");
			else
			{
				logStatus(2,"Next button is not enabled.");
				numOfFailure++;
			}
			
			logStep(step++,"Click on 'Next' Button.");
			login_Process.clickOnNextButton();
			
			logStep(step++,"Enter Password in password input box: "+password);
			login_Process.enterpassword(password);
			
			log(" To verify if next button is enabled.");
			if(login_Verification.verifyNextButtonEnable())
				logStatus(1,"Next button is enabled.");
			else
			{
				logStatus(2,"Next button is not enabled.");
				numOfFailure++;
			}
			
			logStep(step++,"Click on 'Next' Button.");
			login_Process.clickOnNextButton();
			
			log(" To verify if user successfully logged in with valid credentials.");
			if(login_Verification.verifyNextButtonEnable())
				logStatus(1,"User is able to loggin and welcome page is displayed");
			else
			{
				logStatus(2,"User is not able to login with valid credentials");
				numOfFailure++;
			}
			
			if (numOfFailure > 0) 
			{
				Assert.assertTrue(false);
			}
		}catch(Exception e)
		{
			System.out.println(e);
			Assert.assertTrue(false);
		}
	}
	
	@Test 
	public void verifyLoginWithInvalidEmail()
	{
		try{
			logCase(" Verify if a user can not log in with an invalid username and valid password.");
			int numOfFailure=0;
			int step=1;
		
			assignAuthor_Module(AuthorName,ModuleName);
			
			String invalidEmail = TestData.getCellValue("Data/DataSet.xlsx","Login",5,1);
			String actualValidationMessage = TestData.getCellValue("Data/DataSet.xlsx","Login",5,0);
			
		
			logStep(step++,"Enter URL : "+testUrl);
			
			logStep(step++,"Enter invalid email address in input box: "+invalidEmail);
			login_Process.enterEmailAddress(invalidEmail);
			
			log(" To verify if next button is enabled.");
			if(login_Verification.verifyNextButtonEnable())
				logStatus(1,"Next button is enabled.");
			else
			{
				logStatus(2,"Next button is not enabled.");
				numOfFailure++;
			}
			
			logStep(step++,"Click on 'Next' Button.");
			login_Process.clickOnNextButton();
			
			
			log(" To verify if user is not alowed to log in with invalid email and through vaidation message.");
			if(login_Verification.invalidEmailValidaion(actualValidationMessage))
				logStatus(1,"System throws invalid email validation message");
			else
			{
				logStatus(2,"System did not throw invalid email validation message while login in with invalid email.");
				numOfFailure++;
			}
			
			if (numOfFailure > 0) 
			{
				Assert.assertTrue(false);
			}
		}catch(Exception e)
		{
			System.out.println(e);
			Assert.assertTrue(false);
		}
	}
	
}
