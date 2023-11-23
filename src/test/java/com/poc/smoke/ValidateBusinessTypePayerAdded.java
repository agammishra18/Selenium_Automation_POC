
package com.poc.smoke;

import java.io.File;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.poc.library.AppLibrary;
import com.poc.library.TestBase;
import com.poc.pageobject.AddPayerForm;
import com.poc.pageobject.DashboardPage;
import com.poc.pageobject.HomePage;
import com.poc.pageobject.LoginPage;
import com.poc.pageobject.ManagePayerPage;
import com.poc.pageobject.People;
import com.poc.pageobject.SideNavigation;

public class ValidateBusinessTypePayerAdded extends TestBase {
	@BeforeClass
	public void setUp() {
		appLibrary = new AppLibrary("ValidateBusinessTypeAddPayer");
	}

	@DataProvider(name = "PayerData")
	public Object[][] getData() throws Exception {
		String filePath = "Resources" + File.separator + "AddNewPayerData.xls";
		Object object[][] = AppLibrary.readExcel(filePath, 0);
		return object;
	}

	@Test(dataProvider = "PayerData")
	public void businessTypeAddPayer(String businessName, String address, String city, String state, String zipCode,
			String country, String phone, String exeIndicator) throws Exception {

		String payerEinNum = AppLibrary.generateRandomNumber(9);
		String payerBusinessName = businessName + " " + AppLibrary.getFormattedDate().replace("_", "");
		String disregardedEntity = AppLibrary.generateRandomNumber(9);
		String payerAddress = AppLibrary.randIntDigits(1111, 9999) + " " + address;
		String payerPhoneNumber = phone + AppLibrary.generateRandomNumber(8);

		appLibrary.getDriverInstance();
		appLibrary.launchApp();// launching the application in browser
		HomePage ap = new HomePage(appLibrary);
		DashboardPage dp = new DashboardPage(appLibrary);
		SideNavigation sn = new SideNavigation(appLibrary);
		LoginPage lp = ap.navigateToLoginPage();
		lp.login(getUserID(), getPassword());
		dp.plansPopupCancel();
		People p = sn.selectPeople();
		ManagePayerPage mpp = p.selectManagePayer();
		AddPayerForm apf = mpp.addPayerButton();
		apf.fillAddPayerForm(payerEinNum, payerBusinessName, disregardedEntity, payerAddress, city, state, zipCode,
				country, payerPhoneNumber);
		apf.addPayerDetails();
		apf.verifyAlertMessage();

	}
}
