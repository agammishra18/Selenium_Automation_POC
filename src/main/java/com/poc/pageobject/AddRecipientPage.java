package com.poc.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.poc.library.AppLibrary;

public class AddRecipientPage {

	private WebDriver driver;
	private AppLibrary appLibrary;
	public String individualRadioButton = "id:-:rblPayeeTypeIndividual";
	public String disregardedEntity = "id:-:DisregardedEntity";
	public String recipientPhoneNo = "id:-:PayeePhNo";

	public String ssn = "id:-:PayeeFedaralID";
	public String firstName = "id:-:PayeeFirstName";
	public String lastName = "id:-:PayeeLastName";
	public String addNewPayer = "xpath:-://span[text()='ADD NEW PAYER']";
	public String businessRadioButton = "xpath:-://p[label[text()='Business']]//input[@value='Business'][@id='rblPayeeType']";
	public String ein = "id:-:PayeeFedaralID";
	public String businessName = "id:-:PayeeLastName";
	public String address = "id:-:PayeeAddress";
	public String city = "id:-:PayeeCity";
	public String state = "id:-:PayeeState";
	public String zipCode = "id:-:PayeeZIP";
	public String country = "id:-:PayeeCountry";
	public String email = "id:-:PayeeEmail";
	public String addButton = "xpath:-://div[div[@id='AddPayeeDialog']]//div//button[text()='Add']";
	public String alertMessage = "xpath:-://article[p[contains(text(),'Recipient  Info Added Successfully')]]//button[text()='OK']";

	public AddRecipientPage(AppLibrary appLibrary) {
		super();
		this.appLibrary = appLibrary;
		this.driver = appLibrary.getCurrentDriverInstance();
	}

	public void verifyAddRecipientPageUi() throws Exception {
		AppLibrary.verifyElement(driver, addNewPayer, true);
		AppLibrary.verifyElement(driver, businessRadioButton, true);
		AppLibrary.verifyElement(driver, ein, true);
		AppLibrary.verifyElement(driver, businessName, true);

		AppLibrary.verifyElement(driver, address, true);
		AppLibrary.verifyElement(driver, city, true);
		AppLibrary.verifyElement(driver, state, true);
		AppLibrary.verifyElement(driver, zipCode, true);
		AppLibrary.verifyElement(driver, country, true);

	}

	public void fillAddRecipientForm(String einNo, String businessNameData, String addressData, String cityData,
			String stateData, String zipCodeData, String countryData, String emailData) throws Exception {
		AppLibrary.syncProgress(driver);
		AppLibrary.waitTillElementClickable(driver, businessRadioButton);
		AppLibrary.clickElement(driver, businessRadioButton);
		AppLibrary.sendKeys(driver, ein, einNo);
		AppLibrary.enterText(driver, businessName, businessNameData);
		AppLibrary.enterText(driver, address, addressData);
		AppLibrary.enterText(driver, city, cityData);
		WebElement stateOption = AppLibrary.findElement(driver, state);
		AppLibrary.selectByPartOfVisibleText(stateOption, stateData);
		AppLibrary.sendKeys(driver, zipCode, zipCodeData);
		WebElement countryOption = AppLibrary.findElement(driver, country);
		AppLibrary.selectByPartOfVisibleText(countryOption, countryData);

		AppLibrary.enterText(driver, email, emailData);
	}

	public void addRecipientDetails() throws Exception {
		AppLibrary.clickElement(driver, addButton);

	}

	public NECFormSingleFiling VerifyRecipientConfirmation() throws Exception {
		AppLibrary.syncProgress(driver);
		AppLibrary.waitTillElementLoaded(driver, alertMessage);
		AppLibrary.verifyElement(driver, alertMessage, true);
		AppLibrary.clickElement(driver, alertMessage);
		return new NECFormSingleFiling(appLibrary);
	}

	public void fillIndividualAddRecipientForm(String ssnNo, String firstNameData, String lastNameData,
			String disregardedEntityData, String addressData, String cityData, String stateData, String zipCodeData,
			String countryData, String phoneData, String emailData) throws Exception {

		AppLibrary.clickElement(driver, individualRadioButton);
		AppLibrary.sendKeys(driver, ssn, ssnNo);
		AppLibrary.enterText(driver, firstName, firstNameData);
		AppLibrary.enterText(driver, lastName, lastNameData);
		AppLibrary.enterText(driver, address, addressData);
		AppLibrary.enterText(driver, city, cityData);
		WebElement stateOption = AppLibrary.findElement(driver, state);
		AppLibrary.selectByPartOfVisibleText(stateOption, stateData);
		AppLibrary.sendKeys(driver, zipCode, zipCodeData);
		WebElement countryOption = AppLibrary.findElement(driver, country);
		AppLibrary.selectByPartOfVisibleText(countryOption, countryData);
		AppLibrary.enterText(driver, recipientPhoneNo, phoneData);
		AppLibrary.enterText(driver, email, emailData);
	}
}
