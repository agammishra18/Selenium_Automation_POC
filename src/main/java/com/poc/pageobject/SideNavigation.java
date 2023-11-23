package com.poc.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.poc.library.AppLibrary;


public class SideNavigation {

	private AppLibrary appLibrary;
	private WebDriver driver;
	public String forms = "xpath:-://div//p[contains(@class,'menu_head') and text()='Forms']";
	public String people = "xpath:-://div//p[contains(@class,'menu_head') and text()='People']";

	public SideNavigation(AppLibrary appLibrary) {
		super();
		this.appLibrary = appLibrary;
		this.driver = appLibrary.getCurrentDriverInstance();
	}

	public void verifySideNavigationUi() throws Exception {
		AppLibrary.verifyElement(driver, forms, true);
	}

	public Forms selectForms() throws Exception {
		AppLibrary.waitTillElementClickable(driver, forms);
		AppLibrary.clickElement(driver, forms);
		return new Forms(appLibrary);
	}

	public People selectPeople() throws Exception {
		AppLibrary.syncProgress(driver);
		AppLibrary.waitTillElementClickable(driver, people);
		AppLibrary.clickElement(driver, people);
		return new People(appLibrary);
	}

}
