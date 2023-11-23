package com.poc.pageobject;
import org.openqa.selenium.WebDriver;

import com.poc.library.AppLibrary;


public class HomePage {

	private AppLibrary appLibrary;
	private WebDriver driver;
	public String login = "xpath:-://a[@href='/Account/Login']";
	public String cookiesAlert = "id:-:cookiescript_accept";
	

	public HomePage(AppLibrary appLibrary) {
		super();
		this.appLibrary = appLibrary;
		this.driver = appLibrary.getCurrentDriverInstance();
	}

	public void verifyHomePageUi() throws Exception {
		AppLibrary.verifyElement(driver, login, true);
	//	AppLibrary.verifyElement(driver, cookiesAlert, true);
	}
	
	public void acceptCookies() throws Exception {
		AppLibrary.clickElement(driver, cookiesAlert);
	}

	public LoginPage navigateToLoginPage() throws Exception {
		AppLibrary.waitTillElementClickable(driver, login);
		AppLibrary.clickElement(driver, login);
		return new LoginPage(appLibrary);
	}

}
