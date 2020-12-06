package com.InfoTrack.Login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.InfoTrack.Init.AbstractPage;
import com.InfoTrack.Init.Common;

public class Login_Verification extends AbstractPage {

	public Login_Verification(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(xpath = "//h1[contains(.,'Welcome')]")
	WebElement welcomeText;
	public boolean verifyUserLoggedIn()
	{
	    if(Common.isElementDisplayed(welcomeText))
	    	return true;
	    else
	    	return false;
	}
	
	@FindBy(xpath = "//button/span[contains(.,'Next')]")
	WebElement nextButton;
	public boolean verifyNextButtonEnable()
	{
		Common.PresenceOfElement(By.xpath("//button/span[contains(.,'Next')]"), driver);
	    if(Common.isElemenEnabled(nextButton))
	    	return true;
	    else
	    	return false;
	}
	
	public boolean invalidEmailValidaion(String actualValidationMessage)
	{
		
		Common.PresenceOfElement(By.xpath("//div[@class='o6cuMc'][contains(.,\"Couldn't find your Google Account\")]"), driver);
		String validationMessage =driver.findElement(By.xpath("//div[@class='o6cuMc'][contains(.,\"Couldn't find your Google Account\")]")).getText();
		System.out.println("validation meesage from UI:- "+validationMessage);
		
		System.out.println("Actual validation message from excel:-"+actualValidationMessage);
		System.out.println("UI vaidation message:-"+validationMessage);
	    if(validationMessage.equals(actualValidationMessage))
	    	return true;
	    else
	    	return false;
	}
	
}
