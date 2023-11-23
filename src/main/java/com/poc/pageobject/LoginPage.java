package com.poc.pageobject;

import org.openqa.selenium.WebDriver;

import com.poc.library.AppLibrary;


public class LoginPage {

	AppLibrary appLibrary;
	private WebDriver driver;
	public String login = "xpath:-://a[text()='Login ']";
	public String loginUsername = "id:-:txtLoginEmail";
	public String loginPassword = "id:-:txtLoginPassword";
	public String loginButton = "id:-:btnLogin";

	public LoginPage(AppLibrary appLibrary) {
		super();
		this.appLibrary = appLibrary;
		this.driver = appLibrary.getCurrentDriverInstance();
	}

	public void verifyLoginPageUi() throws Exception {
		AppLibrary.verifyElement(driver, loginUsername, true);
		AppLibrary.verifyElement(driver, loginPassword, true);

	}


	public DashboardPage login(String username, String password) throws Exception {
		AppLibrary.syncProgress(driver);
		AppLibrary.waitTillElementLoaded(driver, loginUsername);
		AppLibrary.enterText(driver, loginUsername, username);
		AppLibrary.enterText(driver, loginPassword, password);
		AppLibrary.clickElement(driver, loginButton);
		return new DashboardPage(appLibrary);

	}

}
