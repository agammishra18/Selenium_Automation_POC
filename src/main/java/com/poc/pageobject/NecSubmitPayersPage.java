package com.poc.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.poc.library.AppLibrary;

public class NecSubmitPayersPage {

	private AppLibrary appLibrary;
	private WebDriver driver;
	public String selectAll = "id:-:selectallGlobal";
	public String uspsMail = "id:-:selectallUspsGlobal";
	public String emailRecipient = "id:-:selectallRecipientGlobal";
	public String tinMatch = "id:-:selectallTinMatchGlobal";
	public String submitForThisPayerButton = "id:-:btnSubmit";
	public String scheduleDateAlertText = "xpath:-://p[contains(text(),'The Scheduled date is the date when the form/s is/are submitted to the IRS/SSA.')]";
	public String scheduleDateAlertCanelButton = "id:-:alertify-cancel";
	public String scheduleDateAlertOkButton = "id:-:alertify-ok";
	public String agreePopUpText = "xpath:-://p[text()=' I have reviewed and verified the data for submission']";
	public String agreePopUpCheckBox = "id:-:AgreeChkBoxId";
	public String OkButton = "id:-:alertify-ok";
	public String confirmationPopup = "xpath:-://article[p[contains(text(),'Though the Scheduled Date may be in future')]]//button[text()='OK']";
	public String agreePopupOkButton = "xpath:-://article[p[contains(text(),' I have reviewed and verified the data for submission')]]//button[text()='OK']";
	public String saveAndContinue = "id:-:btnSaveContinue";
	public String singlePayerSelectCheckbox = "xpath:-://td//input[@class='chkbxq']";
	public String stateFilingCheckbox = "id:-:selectallStatesGlobal";
	public String changeScheduleDateForAll = "id:-:btnScheduleAll";
	public String scheduleDateButton = "xpath:-://span[@class='k-icon k-i-calendar']";
	public String futureDate = "xpath:-://tbody//tr//td//a[text()='28']";
	public String okButtonOfScheduleForm = "xpath:-://button[text()='OK']";
//	public String okButtonOfPlanNotify = "xpath:-://button[text()='OK' and @id='alertify-ok']";
	public String calenderRightArrow = "xpath:-://div[@id='084bde9d-6597-4e82-894c-b0aaf4aa8a51']//div//a[text()='April 2023']";
	public String okButtonOfPlanNotify = "xpath:-://article[p[contains(text()='You have not chosen either USPS Mail')]]//button[text()='Cancel']";
	public String okButtonSubmitIRS = "xpath:-://article[p[contains(text(),'Please select recipient details to submit to IRS')]]//button[text()='OK']";
	public String firstCheckBox = "xpath:-:(//input[@class='chkbxq'])[1]";
	
			
	
	public NecSubmitPayersPage(AppLibrary appLibrary) {
		super();
		this.appLibrary = appLibrary;
		this.driver = appLibrary.getCurrentDriverInstance();
	}

	public void verifysubmitPayersPageUi() throws Exception {
		AppLibrary.waitTillElementClickable(driver, selectAll);
		AppLibrary.verifyElement(driver, selectAll, true);
		AppLibrary.verifyElement(driver, uspsMail, true);
		AppLibrary.verifyElement(driver, emailRecipient, true);
		AppLibrary.verifyElement(driver, tinMatch, true);
	}

	public void selectAllDetails() throws Exception {
		AppLibrary.syncProgress(driver);
		AppLibrary.waitTillElementClickable(driver, selectAll);
		AppLibrary.clickByJavascript(driver, selectAll);
		AppLibrary.waitTillElementClickable(driver, uspsMail);
		AppLibrary.clickByJavascript(driver, uspsMail);
		AppLibrary.waitTillElementClickable(driver, emailRecipient);
		AppLibrary.clickByJavascript(driver, emailRecipient);
		AppLibrary.waitTillElementClickable(driver, tinMatch);
		AppLibrary.clickElement(driver, tinMatch);
	}

	public void selectDetailsWithoutUsps() throws Exception {
		AppLibrary.syncProgress(driver);
		AppLibrary.waitTillElementLoaded(driver, firstCheckBox);
		AppLibrary.clickElement(driver, selectAll);
		AppLibrary.waitTillElementClickable(driver, emailRecipient);
		AppLibrary.clickElement(driver, emailRecipient);
		AppLibrary.waitTillElementClickable(driver, tinMatch);
		AppLibrary.clickElement(driver, tinMatch);
	}

	public void submitPayerWithoutSelectionPopUp() throws Exception {
		WebElement ele = AppLibrary.findElement(driver, okButtonOfPlanNotify);
		String s = ele.getText();
		if (s.equalsIgnoreCase("You have not chosen either USPS Mail")) {
			AppLibrary.clickElement(driver, okButtonOfPlanNotify);
			
		}

	}

	public void verifyScheduleDateAlert() throws Exception {
		AppLibrary.verifyElement(driver, scheduleDateAlertText, true);
		AppLibrary.verifyElement(driver, scheduleDateAlertCanelButton, true);
		AppLibrary.verifyElement(driver, scheduleDateAlertOkButton, true);
	}

	public void acceptScheduleDateAlert() throws Exception {
		AppLibrary.clickElement(driver, scheduleDateAlertOkButton);
	}

	public void verifyAgreementAlertUi() {
		AppLibrary.verifyElement(driver, agreePopUpText, true);
	}


	public NecSubmitPayersPage saveAndContinue() throws Exception {
		AppLibrary.waitTillElementLoaded(driver, saveAndContinue);
		AppLibrary.clickElement(driver, saveAndContinue);
		return new NecSubmitPayersPage(appLibrary);
	}

}
