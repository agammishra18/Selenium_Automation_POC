package com.poc.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.poc.library.AppLibrary;


public class AddPayerForm {

	private WebDriver driver;
	private AppLibrary appLibrary;
	public String addNewPayer = "xpath:-://span[text()='ADD NEW PAYER']";
	public String businessRadioButton = "id:-:rblPayerTypeBusiness";
	public String individualRadioButton = "id:-:rblPayerTypeIndividual";

	public String ein = "id:-:PayerFedaralID";
	public String ssn = "id:-:PayerFedaralID";

	public String firstName = "id:-:PayerFirstName";
	public String businessName = "id:-:PayerLastName";
	public String lastName = "id:-:PayerLastName";

	public String disregardedEntity = "id:-:DisregardedEntity";
	public String address = "id:-:PayerAddress";
	public String city = "id:-:PayerCity";
	public String state = "id:-:PayerState";
	public String zipCode = "id:-:PayerZIP";
	public String country = "id:-:PayerCountry";
	public String phoneNo = "id:-:PayerPhNo";
	public String email = "id:-:PayerEmail";

	public String addButton = "xpath:-://div[div[@id='PayerDialog']]//div//button[text()='Add']";
	public String alertMessage = "xpath:-://article[p[text()='Payer Info Added Successfully']]//button[text()='OK']";
	public String okButton = "xpath:-://button[text()='OK']";
	public String duplicateEin = "xpath:-://p[contains(text(),'Payer with same EIN/SSN already exists')]";

	public AddPayerForm(AppLibrary appLibrary) {  
		super();
		this.appLibrary = appLibrary;
		this.driver = appLibrary.getCurrentDriverInstance();
	}

	public void verifyAddPayerPageUi() throws Exception {
		
		AppLibrary.verifyElement(driver, addNewPayer, true);
		AppLibrary.verifyElement(driver, businessRadioButton, true);
		AppLibrary.verifyElement(driver, ein, true);
		AppLibrary.verifyElement(driver, firstName, true);
		AppLibrary.verifyElement(driver, businessName, true);
		AppLibrary.verifyElement(driver, disregardedEntity, true);
		AppLibrary.verifyElement(driver, address, true);
		AppLibrary.verifyElement(driver, city, true);
		AppLibrary.verifyElement(driver, state, true);
		AppLibrary.verifyElement(driver, zipCode, true);
		AppLibrary.verifyElement(driver, country, true);
		AppLibrary.verifyElement(driver, phoneNo, true);
		AppLibrary.verifyElement(driver, addButton, true);

	}

	public void fillAddPayerForm(String einNo, String businessNameData, String disregardedEntityData,
			String addressData, String cityData, String stateData, String zipCodeData, String countryData,
			String phoneData) throws Exception {
		AppLibrary.waitTillElementClickable(driver, businessRadioButton);
		AppLibrary.clickByJavascript(driver, businessRadioButton);
		AppLibrary.sendKeys(driver, ein, einNo);
		AppLibrary.enterText(driver, businessName, businessNameData);
		AppLibrary.enterText(driver, disregardedEntity, disregardedEntityData);
		AppLibrary.enterText(driver, address, addressData);
		AppLibrary.enterText(driver, city, cityData);
		WebElement stateOption = AppLibrary.findElement(driver, state);
		AppLibrary.selectByPartOfVisibleText(stateOption, stateData);
		AppLibrary.sendKeys(driver, zipCode, zipCodeData);
		WebElement countryOption = AppLibrary.findElement(driver, country);
		AppLibrary.selectByPartOfVisibleText(countryOption, countryData);
		AppLibrary.enterText(driver, phoneNo, phoneData);

	}

	public void fillAddPayerFormSingleFiling(String einNo, String businessNameData, String disregardedEntityData,
			String addressData, String cityData, String stateData, String zipCodeData, String countryData,
			String phoneData, String emailData) throws Exception {
		AppLibrary.waitTillElementLoaded(driver, businessRadioButton);
		AppLibrary.clickElement(driver, businessRadioButton);
		AppLibrary.sendKeys(driver, ein, einNo);
		AppLibrary.enterText(driver, businessName, businessNameData);
		AppLibrary.enterText(driver, disregardedEntity, disregardedEntityData);
		AppLibrary.enterText(driver, address, addressData);
		AppLibrary.enterText(driver, city, cityData);
		WebElement stateOption = AppLibrary.findElement(driver, state);
		AppLibrary.selectByPartOfVisibleText(stateOption, stateData);
		AppLibrary.sendKeys(driver, zipCode, zipCodeData);
		WebElement countryOption = AppLibrary.findElement(driver, country);
		AppLibrary.selectByPartOfVisibleText(countryOption, countryData);
		AppLibrary.enterText(driver, phoneNo, phoneData);
		AppLibrary.enterText(driver, email, emailData);

	}

	public void fillIndividualAddPayerForm(String ssnNo, String firstNameData, String lastNameData,
			String disregardedEntityData, String addressData, String cityData, String stateData, String zipCodeData,
			String countryData, String phoneData) throws Exception {
		AppLibrary.waitTillElementLoaded(driver, businessRadioButton);
		AppLibrary.clickElement(driver, individualRadioButton);
		AppLibrary.sendKeys(driver, ssn, ssnNo);
		AppLibrary.enterText(driver, firstName, firstNameData);
		AppLibrary.enterText(driver, lastName, lastNameData);
		AppLibrary.enterText(driver, disregardedEntity, disregardedEntityData);
		AppLibrary.enterText(driver, address, addressData);
		AppLibrary.enterText(driver, city, cityData);
		WebElement stateOption = AppLibrary.findElement(driver, state);
		AppLibrary.selectByPartOfVisibleText(stateOption, stateData);
		AppLibrary.sendKeys(driver, zipCode, zipCodeData);
		WebElement countryOption = AppLibrary.findElement(driver, country);
		AppLibrary.selectByPartOfVisibleText(countryOption, countryData);
		AppLibrary.enterText(driver, phoneNo, phoneData);

	}

	public void addPayerDetails() throws Exception {
		AppLibrary.waitTillElementClickable(driver, addButton);
		AppLibrary.clickByJavascript(driver, addButton);
	}

	public void verifyAlertMessage() throws Exception {
		AppLibrary.syncProgress(driver);
		AppLibrary.waitTillElementClickable(driver, alertMessage);
		AppLibrary.clickByJavascript(driver, alertMessage);
	}

	public NECFormSingleFiling alertifyOk() throws Exception {
		AppLibrary.waitTillElementClickable(driver, okButton);
		AppLibrary.clickElement(driver, okButton);
		return new NECFormSingleFiling(appLibrary);
	}

}
