package com.poc.pageobject;

import java.io.File;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.poc.library.AppLibrary;

public class ManageRecipient {
	private WebDriver driver;
	private AppLibrary appLibrary;
	public String payersDropdown = "id:-:ddlPayer_chosen";
	public String searchPayer = "xpath:-://input[@class='chosen-search-input']";
	public String requestW8Button = "id:-:requestW8Button";
	public String requestW9Button = "id:-:requestW9Button";
	public String selection = "xpath:-://li[contains(@class,'active-result')]";
	public String selectFiles = "xpath:-://input[@type='file' and @id='upload']";
	public String bulkUpload = "id:-:Next";
	public String uploadSuccessMsgText = "xpath:-://p[text()='Spread Sheet Uploaded Successfully']";
	public String uploadSucessPopupButton = "xpath:-://article[p[contains(text(),'Spread Sheet Uploaded Successfully')]]//nav//button[text()='OK']";
	public String bulkW8request = "id:-:btnBulkW8Request";
	public String BulkW8confirmationText = "xpath:-://p[text()='Do you want to W8 Request all recipients under selected payer or W8 Request only selected recipients ? ']";
	public String W8requestAllRecipients = "xpath:-://button[text()='W8 Request All Recipients']";
	public String W8requestSelectedRecipients = "xpath:-://button[text()='W8 Request selected Recipients']";
	public String selectAllCheckbox = "id:-:selectall";
	public String removeFirstRecipient = "xpath:-:(//tr//td//input[@id='checkbox'])[1]";
	public String selectFirstRecipient = "xpath:-:(//tr//td//input[@id='checkbox'])[1]";
	public String bulkW9request = "id:-:btnBulkW9Request";
	public String W9requestAllRecipients = "xpath:-://button[text()='W9 Request All Recipients']";
	public String W9requestSelectedRecipients = "xpath:-://button[text()='W9 Request selected Recipients']";
	public String BulkW9confirmationText = "xpath:-://p[text()='Do you want to W9 Request all recipients under selected payer or W9 Request only selected recipients ? ']";
	public String secondConfiramtionText = "xpath:-://p[text()='Click on proceed to continue sending bulk W9/W8 requests']";
	public String secondConfiramtionProceedButton = "xpath:-://button[text()='Proceed' and @id='alertify-ok']";
	public String secondConfiramtionCancelButton = "xpath:-://button[text()='Cancel' and @id='alertify-cancel']";
	public String successConfirmationPopup = "xpath:-://p[text()='Request sent successfully']";
	public String successConfirmationOkButton = "xpath:-://button[text()='ok' and @id='alertify-ok']";
	public String recipientCheckBox = "xpath:-:(//input[@id='checkbox'])[2]";
	public String requestTinMatchButton = "xpath:-:(//a[@id='requestTINMatch'])[1]";
	public String deleteRecipientConfirmationOkButton = "xpath:-://button[text()='OK' and @id='alertify-ok']";
	public String successfullyDeletedOkButton = "xpath:-://button[text()='OK' and @id='alertify-ok']";
	public String deleteRecipientButton = "id:-:btnDeletePayee";
	public String spreadSheetUploadedSuccessfullyOkButton = "xpath:-://button[text()='OK' and @id='alertify-ok']";
//	public String doYouWantToUsePrepayAmountOkButton = "xpath:-://button[text()='OK' and @id='alertify-ok']";
	// public String tinMatchSuccessfullyOkButton = "xpath:-://button[text()='OK'
	// and @id='alertify-ok']";
	public String tinMatchSuccessfullyOkButton = "xpath:-://article[p[text()='TIN Match request Successful.']]//button[text()='OK']";
	public String multipleTinMatchSuccessfullyOkButton = "xpath:-://article[p[text()='TIN match request successful.']]//button[text()='OK']";
	public String bulkRequestTinMatchButton = "xpath:-://input[@id='btnBulkTinMatch']";
	public String tinMatchAllRecipientConfirmationButton = "xpath:-://button[text()='TIN MATCH All Recipients']";
	public String tinMatchSelectedRecipientConfirmationButton = "xpath:-://button[text()='TIN MATCH selected Recipients']";
	public String proceedConfirmationButton = "xpath:-://button[text()='Proceed' and @id='alertify-ok']";
	public String addRecipientButton = "id:-:btnAddPayee";
	public String SheetRemoveButton = "id:-:remove";

	public String bulkW8RequestDropdown = "id:-:ddlBulkW8List";
	public String w8RequestDropdown = "id:-:ddlW8List";
	public String w8RequestWindowText = "xpath:-:(//span[text()='W8 Request'])[1]";

	public String sendW8Request = "xpath:-://input[@value='Send Request']";
	public String proceedPopupText = "xpath:-://p[text()='Click on proceed to continue sending bulk W8 requests']";
	public String proceedButton = "id:-:alertify-ok";
	public String successPopupText = "xpath:-://p[text()='Request sent successfully']";
	public String successPopupOk = "xpath:-://article[p[contains(text(),'Request sent successfully')]]//button[text()='ok']";

	public String w8RequestButtonSingleRecipient = "xpath:-:(//a[@id='requestW8Button'])[1]";
	public String emailSentText = "xpath:-://p[text()='Email sent to Recipient']";
	public String emailSentPopupOk = "id:-:alertify-ok";
	public String okButton = "id:-:alertify-ok";
	public String successUploadSheetPopupText = "xpath:-://p[contains(text(),'Spread Sheet Uploaded Successfully')]";
	public String planTypeOkButton = "xpath:-://article[p[span[contains(text(),'Plan Type')]]]//button[text()='OK']";
	public String prepayAmtOkButton = "xpath:-://article[p[text()='Do you want to use prepay amount for this?']]//button[text()='OK']";

	public String paymentSuccessfulOkButton = "xpath:-://article[p[text()='Payment successful']]//button[text()='OK']";
	public String submittedForTenNewRecipientProceedButton = "xpath:-://article[p[contains(text(),'A TIN match request has been submitted')]]//button[text()='Proceed']";
	public String usePrepayAmountForThisProceedButton = "xpath:-://article[p[contains(text(),'TIN matching requests')]]//button[text()='Proceed']";
	public String proceedToContinueW9 = "xpath:-://article[p[contains(text(),'Click on proceed to continue sending bulk W9/W8 requests')]]//button[text()='Proceed']";
	public String requestSentSuccessOkButton = "xpath:-://article[p[contains(text(),'Request sent successfully')]]//button[text()='ok']";
	public String paginationForwardButton = "xpath:-://span[@class='k-icon k-i-arrow-e']";
	public String clickOnOkRequestTinInProgressButton = "xpath:-://article[p[text()='TIN Matching Request is in process.']]//button[text()='OK']";
	public String clickOnOkRequestTinMatchButton = "xpath:-://article[p[text()='Click on OK to request TIN match']]//button[text()='OK']";
	public String clickOnOkRequestTinFailedOkButton = "xpath:-://article[p[text()='TIN Match request Failed.Please Check card details']]//button[text()='OK']";
	public String proceedToContinueW8 = "xpath:-://article[p[text()='Click on proceed to continue sending bulk W8 requests']]//button[text()='Proceed']";
	public String clickOnPaginationDropdown = "xpath:-://span[@class='k-dropdown-wrap k-state-default']";
	public String paginationDropdown = "xpath:-://div[@class='k-list-scroller']//ul[@class='k-list k-reset']//li";
	public String requestNotCompletedW9OkButton = "xpath:-://article[p[contains(text(),'Recipient(s), W9 request has not been completed.Do you want to issue a new W9 to the recipients?')]]//button[text()='OK']";
	
	
	public ManageRecipient(AppLibrary appLibrary) {
		super();
		this.appLibrary = appLibrary;
		this.driver = appLibrary.getCurrentDriverInstance();
	}

	public void verifyMiscFormPageUi() throws Exception {
		AppLibrary.verifyElement(driver, payersDropdown, true);
		AppLibrary.verifyElement(driver, requestW8Button, true);
		AppLibrary.verifyElement(driver, requestW9Button, true);
		AppLibrary.verifyElement(driver, bulkW8request, true);
	}

	public void selectPayerFromDropdown(String payerName) throws Exception {
		AppLibrary.syncProgress(driver);
		AppLibrary.waitTillElementClickable(driver, payersDropdown);
		AppLibrary.clickElement(driver, payersDropdown);
//		AppLibrary.waitTillElementLoaded(driver, payerName);
		AppLibrary.enterText(driver, searchPayer, payerName);
		AppLibrary.waitTillElementLoaded(driver, selection);
		AppLibrary.clickElement(driver, selection);

	}

	public void validateW8() throws Exception {
		AppLibrary.waitTillElementClickable(driver, selectFirstRecipient);
		AppLibrary.clickElement(driver, selectFirstRecipient);
		AppLibrary.waitTillElementClickable(driver, requestW8Button);
		AppLibrary.clickElement(driver, requestW8Button);
		AppLibrary.waitTillElementLoaded(driver, w8RequestWindowText);
		WebElement W8options = AppLibrary.findElement(driver, w8RequestDropdown);
		Select sel = new Select(W8options);
		sel.selectByIndex(1);
		AppLibrary.clickElement(driver, sendW8Request);
		AppLibrary.waitTillElementLoaded(driver, emailSentText);
		AppLibrary.verifyElement(driver, emailSentText, true);
		AppLibrary.clickElement(driver, emailSentPopupOk);

	}

	public void validateW9() throws Exception {
		AppLibrary.waitTillElementClickable(driver, selectFirstRecipient);
		AppLibrary.clickElement(driver, selectFirstRecipient);
		AppLibrary.waitTillElementClickable(driver, requestW9Button);
		AppLibrary.clickElement(driver, requestW9Button);
		AppLibrary.clickElement(driver, okButton);
		AppLibrary.waitTillElementLoaded(driver, emailSentText);
		AppLibrary.verifyElement(driver, emailSentText, true);
		AppLibrary.clickElement(driver, okButton);
	}

	public void uploadFile() throws Exception {
		AppLibrary.waitTillElementLoaded(driver, selectFiles);
		WebElement file = AppLibrary.findElement(driver, selectFiles);
		String filePath = System.getProperty("user.dir") + File.separator + "Resources" + File.separator
				+ "Recipient_Data_Import_Templatew8w9.xlsx";
		file.sendKeys(filePath);
		AppLibrary.waitTillElementLoaded(driver, SheetRemoveButton);
		// AppLibrary.sleep(2000);
		AppLibrary.waitTillElementLoaded(driver, bulkUpload);
		AppLibrary.clickElement(driver, bulkUpload);
		AppLibrary.waitTillElementLoaded(driver, uploadSucessPopupButton);
		AppLibrary.clickByJavascript(driver, uploadSucessPopupButton);
	}

	public void uploadFileW8W9Selected() throws Exception {
		AppLibrary.waitTillElementLoaded(driver, selectFiles);
		WebElement file = AppLibrary.findElement(driver, selectFiles);
		String filePath = System.getProperty("user.dir") + File.separator + "Resources" + File.separator
				+ "Recipient_Data_Import_Templatew8w9selected.xlsx";
		file.sendKeys(filePath);
		AppLibrary.waitTillElementClickable(driver, SheetRemoveButton);
		// AppLibrary.sleep(2000);
		AppLibrary.waitTillElementLoaded(driver, bulkUpload);
		AppLibrary.clickElement(driver, bulkUpload);
		AppLibrary.waitTillElementLoaded(driver, successUploadSheetPopupText);
		AppLibrary.verifyElement(driver, successUploadSheetPopupText, true);
		AppLibrary.clickByJavascript(driver, uploadSucessPopupButton);
	}

	public void sendBulkW8RequestForSelectedRecipient() throws Exception {
		AppLibrary.clickElement(driver, clickOnPaginationDropdown);
		List<WebElement> option = AppLibrary.findElements(driver, paginationDropdown);
		for (WebElement webElement : option) {
			String text = webElement.getText();
			if (text.equalsIgnoreCase("50")) {
				webElement.click();
				break;

			}

		}
		AppLibrary.waitTillElementClickable(driver, selectAllCheckbox);

		// Hard wait
		AppLibrary.clickByJavascript(driver, selectAllCheckbox);
		AppLibrary.clickByJavascript(driver, removeFirstRecipient);

		AppLibrary.scroll(driver, bulkW8request);
		AppLibrary.clickByJavascript(driver, W8requestSelectedRecipients);

		try {
			AppLibrary.clickByJavascript(driver, planTypeOkButton);
			AppLibrary.waitTillElementClickable(driver, prepayAmtOkButton);
			AppLibrary.clickByJavascript(driver, prepayAmtOkButton);
			AppLibrary.clickElement(driver, paymentSuccessfulOkButton);
		} catch (Exception e) {
			System.out.println("handled");
		}

		AppLibrary.waitTillElementClickable(driver, bulkW8RequestDropdown);
		WebElement W8options = AppLibrary.findElement(driver, bulkW8RequestDropdown);
		Select sel = new Select(W8options);
		sel.selectByIndex(1);

		AppLibrary.clickByJavascript(driver, sendW8Request);
		AppLibrary.clickByJavascript(driver, proceedToContinueW8);

		try {
			AppLibrary.clickByJavascript(driver, successPopupOk);
		} catch (Exception e) {
			System.out.println("completed");
		}
		;

	}

	public void sendBulkW8RequestForAllRecipient() throws Exception {
		AppLibrary.waitTillElementLoaded(driver, selectAllCheckbox);
		AppLibrary.clickElement(driver, selectAllCheckbox);
		AppLibrary.clickElement(driver, paginationForwardButton);
		AppLibrary.scroll(driver, selectAllCheckbox);

		AppLibrary.clickByJavascript(driver, selectAllCheckbox);
		AppLibrary.clickElement(driver, paginationForwardButton);
		AppLibrary.scroll(driver, selectAllCheckbox);

		AppLibrary.waitTillElementClickable(driver, selectAllCheckbox);
		AppLibrary.clickByJavascript(driver, selectAllCheckbox);
		AppLibrary.scroll(driver, bulkW8request);
		AppLibrary.clickByJavascript(driver, W8requestAllRecipients);

		try {
//            AppLibrary.waitTillElementClickable(driver, planTypeOkButton);
			AppLibrary.clickByJavascript(driver, planTypeOkButton);
			AppLibrary.waitTillElementClickable(driver, prepayAmtOkButton);
			AppLibrary.clickByJavascript(driver, prepayAmtOkButton);
			AppLibrary.clickByJavascript(driver, paymentSuccessfulOkButton);
		} catch (Exception e) {
			System.out.println("handled");
		}

		AppLibrary.waitTillElementLoaded(driver, bulkW8RequestDropdown);
		WebElement W8options = AppLibrary.findElement(driver, bulkW8RequestDropdown);
		Select sel = new Select(W8options);
		sel.selectByIndex(1);
		AppLibrary.clickByJavascript(driver, sendW8Request);
		AppLibrary.clickByJavascript(driver, proceedButton);
		AppLibrary.waitTillElementLoaded(driver, successPopupText);
		AppLibrary.clickByJavascript(driver, successPopupOk);
	}

	public void sendBulkW9RequestForSelectedRecipient() throws Exception {
		AppLibrary.clickElement(driver, clickOnPaginationDropdown);
        List<WebElement> option = AppLibrary.findElements(driver, paginationDropdown);
        for (WebElement webElement : option) {
            String text = webElement.getText();
            if (text.equalsIgnoreCase("50")) {
                webElement.click();
                break;

            }
        }

        AppLibrary.waitTillElementClickable(driver, selectAllCheckbox);
        // Hard Wait
        AppLibrary.clickByJavascript(driver, selectAllCheckbox);
        AppLibrary.scroll(driver, bulkW9request);
        AppLibrary.clickByJavascript(driver, W9requestSelectedRecipients);

        try {
            Boolean a = AppLibrary.isElementPresent(driver, requestNotCompletedW9OkButton);
            System.out.println(a);
            if (a.equals(true)) {
                AppLibrary.clickByJavascript(driver, requestNotCompletedW9OkButton);
            }
            AppLibrary.clickByJavascript(driver, planTypeOkButton);
            AppLibrary.clickByJavascript(driver, prepayAmtOkButton);
            AppLibrary.clickByJavascript(driver, paymentSuccessfulOkButton);
            AppLibrary.clickElement(driver, proceedToContinueW9);
        } catch (Exception e) {
            AppLibrary.clickByJavascript(driver, proceedToContinueW9);

        }

        AppLibrary.waitTillElementLoaded(driver, successPopupText);
        AppLibrary.verifyElement(driver, successPopupText, true);
        AppLibrary.clickElement(driver, successPopupOk);

	}

	public void sendBulkW9RequestForAllRecipient() throws Exception {
		AppLibrary.waitTillElementLoaded(driver, selectAllCheckbox);
		AppLibrary.clickElement(driver, selectAllCheckbox);
		AppLibrary.clickElement(driver, paginationForwardButton);
		AppLibrary.scroll(driver, selectAllCheckbox);

		AppLibrary.clickByJavascript(driver, selectAllCheckbox);
		AppLibrary.clickElement(driver, paginationForwardButton);
		AppLibrary.scroll(driver, selectAllCheckbox);

		AppLibrary.waitTillElementClickable(driver, selectAllCheckbox);
		AppLibrary.clickByJavascript(driver, selectAllCheckbox);
		AppLibrary.scroll(driver, bulkW9request);
		AppLibrary.clickByJavascript(driver, W9requestAllRecipients);
		try {
			Boolean a = AppLibrary.isElementPresent(driver, requestNotCompletedW9OkButton);
			System.out.println(a);
			if (a.equals(true)) {
				AppLibrary.clickByJavascript(driver, requestNotCompletedW9OkButton);
			}
//            AppLibrary.waitTillElementLoaded(driver, planTypeOkButton);
			AppLibrary.clickByJavascript(driver, planTypeOkButton);
			AppLibrary.clickByJavascript(driver, prepayAmtOkButton);
			AppLibrary.clickByJavascript(driver, paymentSuccessfulOkButton);
			AppLibrary.clickElement(driver, proceedToContinueW9);
		} catch (Exception e) {
			AppLibrary.clickByJavascript(driver, proceedToContinueW9);

		}

		AppLibrary.waitTillElementLoaded(driver, requestSentSuccessOkButton);
		AppLibrary.clickElement(driver, requestSentSuccessOkButton);
	}

	public void requestTinMatchWithPrepayForSingleRecipient() throws Exception {
		AppLibrary.syncProgress(driver);
		int i = 2;
		for (; i <= 5;) {
			try {

				AppLibrary.waitTillElementLoaded(driver, recipientCheckBox.replace("2", String.valueOf(i)));
				AppLibrary.clickElement(driver, recipientCheckBox.replace("2", String.valueOf(i)));
				AppLibrary.clickElement(driver, requestTinMatchButton.replace("1", String.valueOf(i - 1)));
				AppLibrary.clickByJavascript(driver, clickOnOkRequestTinMatchButton);
				AppLibrary.waitTillElementClickable(driver, prepayAmtOkButton);
				AppLibrary.clickElement(driver, prepayAmtOkButton);
				AppLibrary.waitTillElementClickable(driver, tinMatchSuccessfullyOkButton);
				AppLibrary.clickByJavascript(driver, tinMatchSuccessfullyOkButton);
				break;
			} catch (Exception e) {
				try {
					AppLibrary.clickElement(driver, clickOnOkRequestTinInProgressButton);

				} catch (Exception ex) {
					AppLibrary.clickElement(driver, clickOnOkRequestTinFailedOkButton);
				}
				i++;
			}
		}
	}

	public void multipleRequestTinMatchWithPrepay() throws Exception {
		AppLibrary.waitTillElementLoaded(driver, selectAllCheckbox);
		AppLibrary.clickElement(driver, selectAllCheckbox);
		AppLibrary.clickElement(driver, bulkRequestTinMatchButton);
		AppLibrary.waitTillElementClickable(driver, tinMatchAllRecipientConfirmationButton);
		AppLibrary.clickElement(driver, tinMatchAllRecipientConfirmationButton);
		AppLibrary.waitTillElementClickable(driver, submittedForTenNewRecipientProceedButton);
		AppLibrary.clickElement(driver, submittedForTenNewRecipientProceedButton);
		AppLibrary.clickElement(driver, proceedConfirmationButton);
		AppLibrary.clickElement(driver, usePrepayAmountForThisProceedButton);
		AppLibrary.waitTillElementClickable(driver, multipleTinMatchSuccessfullyOkButton);
		AppLibrary.clickByJavascript(driver, multipleTinMatchSuccessfullyOkButton);

	}

	public void deleteAllRecipient() throws Exception {
		AppLibrary.waitTillElementClickable(driver, selectAllCheckbox);
		AppLibrary.clickElement(driver, selectAllCheckbox);
		AppLibrary.clickElement(driver, deleteRecipientButton);
		AppLibrary.clickElement(driver, deleteRecipientConfirmationOkButton);
		AppLibrary.clickElement(driver, successfullyDeletedOkButton);
	}

//	public AddRecipientPage addRecipient() throws Exception {
//		AppLibrary.clickElement(driver, addRecipientButton);
//		AppLibrary.syncProgress(driver);
//		return new AddRecipientPage(appLibrary);
//	}

}
