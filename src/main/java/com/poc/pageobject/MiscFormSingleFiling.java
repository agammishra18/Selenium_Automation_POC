package com.poc.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.poc.library.AppLibrary;


public class MiscFormSingleFiling {

	private WebDriver driver;
	private AppLibrary appLibrary;
	public String singleFiling = "xpath:-://a[text()='Single Filing']";
	public String addPayer = "id:-:btnAddEditPayer";
	public String addRecipient = "id:-:btnAddPayee";
	public String rent = "id:-:Rents";
	public String federalIncomeTax = "id:-:FederalIncomeTaxWH";
	public String saveAndContinue = "id:-:btnSaveContinue";

	public String addNewPayer = "xpath:-://span[text()='ADD NEW PAYER']";
	public String payerBusinessRadioButton = "id:-:rblPayerTypeBusiness";
	public String payerIndividualRadioButton = "id:-:rblPayerTypeIndividual";
	public String payerEin = "id:-:PayerFedaralID";
	public String payerSsn = "id:-:PayerFedaralID";
	public String payerFirstName = "id:-:PayerFirstName";
	public String payerBusinessName = "id:-:PayerLastName";
	public String payerLastName = "id:-:PayerLastName";
	public String disregardedEntity = "id:-:DisregardedEntity";
	public String payerAddress = "id:-:PayerAddress";
	public String payerCity = "id:-:PayerCity";
	public String payerState = "id:-:PayerState";
	public String payerZipCode = "id:-:PayerZIP";
	public String payerCountry = "id:-:PayerCountry";
	public String payerPhoneNo = "id:-:PayerPhNo";
	public String addPayerButton = "xpath:-://div[div[@id='PayerDialog']]//div//button[text()='Add']";
	public String payerAlertMessage = "xpath:-://p[text()='Payer Info Added Successfully']";
	public String payerOkButton = "xpath:-://button[text()='OK']";
	public String payerDuplicateEin = "xpath:-://p[contains(text(),'Payer with same EIN/SSN already exists')]";
	public String recipientIndividualRadioButton = "id:-:rblPayeeTypeIndividual";
	public String recipientPhoneNo = "id:-:PayeePhNo";
	public String recipientEin = "id:-:PayeeFedaralID";
	public String recipientSsn = "id:-:PayeeFedaralID";
	public String recipientFirstName = "id:-:PayeeFirstName";
	public String recipientLastName = "id:-:PayeeLastName";
	public String recipientBusinessRadioButton = "id:-:rblPayeeType";
	public String recipienteEin = "id:-:PayeeFedaralID";
	public String recipientBusinessName = "id:-:PayeeLastName";
	public String recipientAddress = "id:-:PayeeAddress";
	public String recipientCity = "id:-:PayeeCity";
	public String recipientState = "id:-:PayeeState";
	public String recipientZipCode = "id:-:PayeeZIP";
	public String recipientCountry = "id:-:PayeeCountry";
	public String recipientEmail = "id:-:PayeeEmail";
	public String recipientAddButton = "xpath:-://div[div[@id='AddPayeeDialog']]//div//button[text()='Add']";
	public String recipientAlertMessage = "xpath:-://p[text()='Recipient  Info Added Successfully']";
	public String recipientOkButton = "id:-:alertify-ok";

	public MiscFormSingleFiling(AppLibrary appLibrary) {
		super();
		this.appLibrary = appLibrary;
		this.driver = appLibrary.getCurrentDriverInstance();
	}

	public void verifyMiscFormPageUi() throws Exception {
		AppLibrary.syncProgress(driver);
		AppLibrary.waitTillElementLoaded(driver, singleFiling);
		AppLibrary.verifyElement(driver, singleFiling, true);
		AppLibrary.verifyElement(driver, addPayer, true);
		AppLibrary.verifyElement(driver, addRecipient, true);
		AppLibrary.verifyElement(driver, rent, true);
		AppLibrary.verifyElement(driver, federalIncomeTax, true);
		AppLibrary.verifyElement(driver, saveAndContinue, true);
	}

	public AddPayerForm addNewPayer() throws Exception {
		AppLibrary.clickElement(driver, addPayer);
		return new AddPayerForm(appLibrary);
	}

	public AddRecipientPage addNewRecipient() throws Exception {
		AppLibrary.clickElement(driver, addRecipient);
		return new AddRecipientPage(appLibrary);
	}

	public void enterRent(String amount) throws Exception {
		AppLibrary.syncProgress(driver);
		AppLibrary.waitTillElementLoaded(driver, rent);
		AppLibrary.enterText(driver, rent, amount);
	}

	public void enterFederalIncomeTaxWithheld(String amount) throws Exception {
		AppLibrary.enterText(driver, federalIncomeTax, amount);
	}

	public void addfederalIncomeTax(String amount) throws Exception {
		AppLibrary.enterText(driver, federalIncomeTax, amount);
	}


	public void fillAddPayerForm(String einNo, String businessNameData, String disregardedEntityData,
			String addressData, String cityData, String stateData, String zipCodeData, String countryData,
			String phoneData) throws Exception {

		AppLibrary.clickElement(driver, payerBusinessRadioButton);
		AppLibrary.sendKeys(driver, payerEin, einNo);
		AppLibrary.enterText(driver, payerBusinessName, businessNameData);
		AppLibrary.enterText(driver, disregardedEntity, disregardedEntityData);
		AppLibrary.enterText(driver, payerAddress, addressData);
		AppLibrary.enterText(driver, payerCity, cityData);
		WebElement stateOption = AppLibrary.findElement(driver, payerState);
		AppLibrary.selectByPartOfVisibleText(stateOption, stateData);
		AppLibrary.sendKeys(driver, payerZipCode, zipCodeData);
		WebElement countryOption = AppLibrary.findElement(driver, payerCountry);
		AppLibrary.selectByPartOfVisibleText(countryOption, countryData);
		AppLibrary.enterText(driver, payerPhoneNo, phoneData);

	}

	public void fillIndividualAddPayerForm(String ssnNo, String firstNameData, String lastNameData,
			String disregardedEntityData, String addressData, String cityData, String stateData, String zipCodeData,
			String countryData, String phoneData) throws Exception {

		AppLibrary.clickElement(driver, payerIndividualRadioButton);
		AppLibrary.sendKeys(driver, payerSsn, ssnNo);
		AppLibrary.enterText(driver, payerFirstName, firstNameData);
		AppLibrary.enterText(driver, payerLastName, lastNameData);
		AppLibrary.enterText(driver, disregardedEntity, disregardedEntityData);
		AppLibrary.enterText(driver, payerAddress, addressData);
		AppLibrary.enterText(driver, payerCity, cityData);
		WebElement stateOption = AppLibrary.findElement(driver, payerState);
		AppLibrary.selectByPartOfVisibleText(stateOption, stateData);
		AppLibrary.sendKeys(driver, payerZipCode, zipCodeData);
		WebElement countryOption = AppLibrary.findElement(driver, payerCountry);
		AppLibrary.selectByPartOfVisibleText(countryOption, countryData);
		AppLibrary.enterText(driver, payerPhoneNo, phoneData);

	}

	public void addPayerDetails() throws Exception {
		// WebElement addButton = AppLibrary.findElement(driver, addPayerButton);
		// AppLibrary.waitForElementClickable(driver, addButton);
		AppLibrary.clickElement(driver, addPayerButton);
	}

	public void verifyPayerAlertMessage() {
		AppLibrary.verifyElement(driver, payerAlertMessage, true);
	}


	public void payerAlertifyOk() throws Exception {
		AppLibrary.clickElement(driver, payerOkButton);
	}

	public void verifyAddRecipientPageUi() throws Exception {
		AppLibrary.verifyElement(driver, addNewPayer, true);
		AppLibrary.verifyElement(driver, recipientBusinessRadioButton, true);
		AppLibrary.verifyElement(driver, recipientEin, true);
		AppLibrary.verifyElement(driver, recipientBusinessName, true);

		AppLibrary.verifyElement(driver, recipientAddress, true);
		AppLibrary.verifyElement(driver, recipientCity, true);
		AppLibrary.verifyElement(driver, recipientState, true);
		AppLibrary.verifyElement(driver, recipientZipCode, true);
		AppLibrary.verifyElement(driver, recipientCountry, true);

	}

	public void fillAddRecipientForm(String einNo, String businessNameData, String addressData, String cityData,
			String stateData, String zipCodeData, String countryData, String emailData) throws Exception {

		AppLibrary.clickElement(driver, recipientBusinessRadioButton);
		AppLibrary.sendKeys(driver, recipientEin, einNo);
		AppLibrary.enterText(driver, recipientBusinessName, businessNameData);
		AppLibrary.enterText(driver, recipientAddress, addressData);
		AppLibrary.enterText(driver, recipientCity, cityData);
		WebElement stateOption = AppLibrary.findElement(driver, recipientState);
		AppLibrary.selectByPartOfVisibleText(stateOption, stateData);
		AppLibrary.sendKeys(driver, recipientZipCode, zipCodeData);
		WebElement countryOption = AppLibrary.findElement(driver, recipientCountry);
		AppLibrary.selectByPartOfVisibleText(countryOption, countryData);

		AppLibrary.enterText(driver, recipientEmail, emailData);
	}

	public void addRecipientDetails() throws Exception {
		AppLibrary.clickElement(driver, recipientAddButton);

	}

	public void verifyRecipientAlertMessage() {
		AppLibrary.verifyElement(driver, recipientAlertMessage, true);
	}

	public void recipientAlertifyOk() throws Exception {
		AppLibrary.clickElement(driver, recipientOkButton);
	}

	public void fillIndividualAddRecipientForm(String ssnNo, String firstNameData, String lastNameData,
			String disregardedEntityData, String addressData, String cityData, String stateData, String zipCodeData,
			String countryData, String phoneData, String emailData) throws Exception {

		AppLibrary.clickElement(driver, recipientIndividualRadioButton);
		AppLibrary.sendKeys(driver, recipientSsn, ssnNo);
		AppLibrary.enterText(driver, recipientFirstName, firstNameData);
		AppLibrary.enterText(driver, recipientLastName, lastNameData);
		AppLibrary.enterText(driver, disregardedEntity, disregardedEntityData);
		AppLibrary.enterText(driver, recipientAddress, addressData);
		AppLibrary.enterText(driver, recipientCity, cityData);
		WebElement stateOption = AppLibrary.findElement(driver, recipientState);
		AppLibrary.selectByPartOfVisibleText(stateOption, stateData);
		AppLibrary.sendKeys(driver, recipientZipCode, zipCodeData);
		WebElement countryOption = AppLibrary.findElement(driver, recipientCountry);
		AppLibrary.selectByPartOfVisibleText(countryOption, countryData);
		AppLibrary.enterText(driver, recipientPhoneNo, phoneData);
		AppLibrary.enterText(driver, recipientEmail, emailData);
	}

}
