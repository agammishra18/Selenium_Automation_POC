
package com.poc.pageobject;

import org.openqa.selenium.WebDriver;

import com.poc.library.AppLibrary;


public class NewForm {
	
	private AppLibrary appLibrary;
	private WebDriver driver;
	
	
	public String year = "xpath:-://ul//li[contains(@class,'year2013')]//h1[text()='2022']";
	public String year2023 = "xpath:-://ul//li[@class='year2013 selected']//h1[text()='2023']";
	public String necForm = "id:-:NEC";
	public String miscForm = "id:-:MISC";
	
	public NewForm(AppLibrary appLibrary) {
		super();
		this.appLibrary = appLibrary;
		this.driver = appLibrary.getCurrentDriverInstance();
	}
	
	public void verifyNewFormUi() throws Exception {
		AppLibrary.verifyElement(driver, year, true);
		AppLibrary.verifyElement(driver, necForm, true);
		AppLibrary.verifyElement(driver, miscForm, true);
	}
	
	
	
	public void selectYear() throws Exception {
		AppLibrary.syncProgress(driver);
		AppLibrary.waitTillElementLoaded(driver, year2023);
		AppLibrary.clickElement(driver, year);
		
	}
	
	public NECFormSingleFiling selectNecForm() throws Exception {
		AppLibrary.syncProgress(driver);
		AppLibrary.waitTillElementLoaded(driver, necForm);
		AppLibrary.clickElement(driver, necForm);
		return new NECFormSingleFiling(appLibrary);
	}

}
