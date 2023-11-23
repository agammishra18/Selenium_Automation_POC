package com.poc.pageobject;

import org.openqa.selenium.WebDriver;

import com.poc.library.AppLibrary;


public class Forms {
	private AppLibrary appLibrary;
	private WebDriver driver;
	public String newForm = "xpath:-://a[text()='New Form']";
	public String manageForm = "xpath:-://a[text()='Manage Forms']";
	public String payerDropdown = "xpath:-:(//label[text()='Payer:'])[1]";
	

	public Forms(AppLibrary appLibrary) {
		super();
		this.appLibrary = appLibrary;
		this.driver = appLibrary.getCurrentDriverInstance();
	}

	public void verifyFormsOptionsUi() throws Exception {
		AppLibrary.verifyElement(driver, newForm, true);
	}

	public NewForm selectNewForm() throws Exception {
		AppLibrary.waitTillElementLoaded(driver, newForm);
		AppLibrary.clickByJavascript(driver, newForm);
		return new NewForm(appLibrary);
	}
	
//	public ManageFormPage selectManageForm() throws Exception {
//        AppLibrary.clickElement(driver, manageForm);
//        AppLibrary.waitTillElementLoaded(driver, payerDropdown);
//        return new ManageFormPage(appLibrary);
//    }
	
}
