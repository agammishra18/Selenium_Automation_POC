package com.poc.pageobject;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.poc.library.AppLibrary;


public class ManagePayerPage {
	AppLibrary appLibrary;
	private WebDriver driver;

	public String managePayer = "xpath:-://h3[text()='Manage Payer']";
	public String addPayerButton = "xpath:-://input[@value='Add Payer']";	
	public String deletePayerButton = "xpath:-://input[@value='Delete Payer']";
	public String assignPayerButton = "id:-:btnAssignPayer";
	public String activeInactiveButton = "id:-:btnActInactPayer";
	public String exportToExcelButton = "id:-:btnExportToExcelAll_Payerss";
	public String selectAllPayerCheckbox = "id:-:selectall";
	public String singlePayerCheckbox = "xpath:-:(//input[@id='checkbox'])[1]";
	public String selectedPayerDeleteButton = "xpath:-:(//a[@id='deleteButton'])[1]";
	public String searchPayerTextBox = "id:-:payerName";
	public String deleteSingleConformationOkButton = "xpath:-://article[p[contains(text(),'Are You Sure You Want to Delete?')]]//button[text()='OK']";
	public String deleteConfirmOkButton = "xpath:-://article[p[contains(text(),'The Payers has forms attached or pending will be deleted')]]//button[text()='OK']";
	public String deleteSuccessOkButton = "xpath:-://article[p[contains(text(),'Selected Payer(s) Deleted Successfully.')]]//button[text()='OK']";
	public String payerSuccessfullyDeletedTextMessage = "xpath:-://p[text()='Payer Successfully Deleted']";
	public String singlePayerSuccessfullyDeletedOkButton = "xpath:-://article[p[contains(text(),'Payer Successfully Deleted')]]//button[text()='OK']";
	
	public String selectFiles = "xpath:-://input[@type='file' and @id='upload']";
	public String selectFilesForDelete = "xpath:-:(//input[@type='file'])[1]";
	public String selectFile="xpath:-://div[span[text()='Select files...']]//input[@id='upload']";
	
	//public String selectFiles = "id:-:upload";
	public String bulkUploadButton = "id:-:Next";
	public String spreadSheetUploadedSuccessfullyOkButton = "xpath:-://button[text()='OK' and @id='alertify-ok']";
	public String filterIcon = "xpath:-:(//span[@class='k-icon k-i-filter'])[2]";
	public String payerSearchTextField = "xpath:-:(//input[@class='k-textbox'])[1]";
	public String filterButton = "xpath:-:(//button[text()='Filter'])[1]";
	public String deleteConfirmationText = "xpath:-://p[contains(text(),'The Payers has forms attached or pending will be deleted')]";
	public String deleteSuccesfulText = "xpath:-://p[contains(text(),'Selected Payer(s) Deleted Successfully.')]";
	public String payerNameHeading = "xpath:-://th[@class='k-header k-filterable k-with-icon']//a[text()='Payer Name']";
	

	public ManagePayerPage(AppLibrary appLibrary) {
		super();
		this.appLibrary = appLibrary;
		this.driver = appLibrary.getCurrentDriverInstance();
	}

	public void verifyManagePayerPageUi() throws Exception {
		AppLibrary.verifyElement(driver, managePayer, true);
	}

	public AddPayerForm addPayerButton() throws Exception {
		AppLibrary.syncProgress(driver);
		AppLibrary.waitTillElementLoaded(driver, payerNameHeading);
		AppLibrary.clickByJavascript(driver, addPayerButton);
		return new AddPayerForm(appLibrary);
	}

	public void singlePayerDelete(String payer) throws Exception {
		AppLibrary.waitTillElementClickable(driver, filterIcon);
		AppLibrary.clickElement(driver, filterIcon);
		AppLibrary.enterText(driver, payerSearchTextField, payer);
		AppLibrary.clickElement(driver, filterButton);
		AppLibrary.clickElement(driver, selectAllPayerCheckbox);
		AppLibrary.waitTillElementClickable(driver, selectedPayerDeleteButton);
		AppLibrary.clickByJavascript(driver, selectedPayerDeleteButton);
		AppLibrary.waitTillElementClickable(driver, deleteSingleConformationOkButton);
		AppLibrary.clickElement(driver, deleteSingleConformationOkButton);
		AppLibrary.waitTillElementClickable(driver, singlePayerSuccessfullyDeletedOkButton);
		AppLibrary.clickElement(driver, singlePayerSuccessfullyDeletedOkButton);
	}

	public void bulkDeletePayerButton() throws Exception {
		AppLibrary.clickElement(driver, selectAllPayerCheckbox);
		AppLibrary.clickElement(driver, deletePayerButton);
	}

	public void assignPayerButton() throws Exception {
		AppLibrary.clickElement(driver, assignPayerButton);
	}

	public void activeInactiveButton() throws Exception {
		AppLibrary.clickElement(driver, activeInactiveButton);
	}

	public void exportToExcel() throws Exception {
		AppLibrary.clickElement(driver, exportToExcelButton);
	}


	public void uploadFile() throws Exception {
		WebElement file = AppLibrary.findElement(driver, selectFiles);
		String filePath = System.getProperty("user.dir")+File.separator+"Resources"+File.separator+"Recipient_Data_Import_Templatew8w9.xlsx";
		file.sendKeys(filePath);
		AppLibrary.clickElement(driver, bulkUploadButton);
		AppLibrary.clickElement(driver, spreadSheetUploadedSuccessfullyOkButton);

	}
	
	public void uploadFileForDeletePayer() throws Exception {
		AppLibrary.waitTillElementLoaded(driver, selectFilesForDelete);
		WebElement file = AppLibrary.findElement(driver, selectFile);
		String filePath = System.getProperty("user.dir")+File.separator+"Resources"+File.separator+"Payer_Data_Import_Template (1).xlsx";
		file.sendKeys(filePath);
		AppLibrary.clickElement(driver, bulkUploadButton);
		AppLibrary.waitTillElementClickable(driver, spreadSheetUploadedSuccessfullyOkButton);
		AppLibrary.clickByJavascript(driver, spreadSheetUploadedSuccessfullyOkButton);

	}

	public void bulkDeletePayerButton(String payerName) throws Exception {
		AppLibrary.syncProgress(driver);
		AppLibrary.waitTillElementClickable(driver, filterIcon);
		AppLibrary.clickElement(driver, filterIcon);
		AppLibrary.enterText(driver, payerSearchTextField, payerName);
		AppLibrary.clickElement(driver, filterButton);
		AppLibrary.waitTillElementClickable(driver, selectAllPayerCheckbox);
		AppLibrary.clickElement(driver, selectAllPayerCheckbox);
		AppLibrary.clickElement(driver, deletePayerButton);
		AppLibrary.waitTillElementClickable(driver, deleteConfirmOkButton);
		AppLibrary.clickByJavascript(driver, deleteConfirmOkButton);
		AppLibrary.waitTillElementLoaded(driver, deleteSuccessOkButton);
		AppLibrary.clickElement(driver, deleteSuccessOkButton);

	}
}
