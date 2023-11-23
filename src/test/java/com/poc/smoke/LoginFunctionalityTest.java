package com.poc.smoke;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.poc.library.AppLibrary;
import com.poc.library.TestBase;
import com.poc.pageobject.DashboardPage;
import com.poc.pageobject.HomePage;
import com.poc.pageobject.LoginPage;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class LoginFunctionalityTest extends TestBase {

	@BeforeClass
	public void setUp() {
		appLibrary = new AppLibrary("LoginFuctionalityTest");
	}

	ExtentReports report;
	ExtentTest logger;

	@Test
	public void loginFunctioalityTest() throws Exception {
		appLibrary.getDriverInstance();
		appLibrary.launchApp();// launching the application in browser
		HomePage ap = new HomePage(appLibrary);
		DashboardPage dp = new DashboardPage(appLibrary);
		ap.verifyHomePageUi();
		LoginPage lp = ap.navigateToLoginPage();
		lp.verifyLoginPageUi();
		lp.login(getUserID(), getPassword());
		dp.verifyDashboardPage();

	}

}
