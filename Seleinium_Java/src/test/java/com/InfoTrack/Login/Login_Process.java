package com.InfoTrack.Login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.InfoTrack.Init.AbstractPage;
import com.InfoTrack.Init.Common;

public class Login_Process extends AbstractPage {
	public Login_Process(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(xpath = "//input[@id='identifierId']")
	WebElement emailInput;
	public Login_Process enterEmailAddress(String email) {
		Common.PresenceOfElement(By.xpath("//input[@id='identifierId']"), driver);
		Common.clickableElement(emailInput,driver);
		emailInput.sendKeys(email);
		return new Login_Process(driver);
	}
	
	@FindBy(xpath = "//button/span[contains(.,'Next')]")
	WebElement nextButton;
	public Login_Process clickOnNextButton() {
		Common.PresenceOfElement(By.xpath("//button/span[contains(.,'Next')]"), driver);
		Common.clickableElement(nextButton,driver);
		Common.jsClick(driver, nextButton);
		return new Login_Process(driver);
	}
	
	@FindBy(xpath = "//input[@name='password']")
	WebElement passwordInput;
	public Login_Process enterpassword(String password) {
		Common.PresenceOfElement(By.xpath("//input[@name='password']"), driver);
		Common.clickableElement(passwordInput,driver);
		passwordInput.sendKeys(password);
		return new Login_Process(driver);
	}
	
}
