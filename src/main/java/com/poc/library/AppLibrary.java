package com.poc.library;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;

import io.github.bonigarcia.wdm.WebDriverManager;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class AppLibrary {

	public static final long GLOBALTIMEOUT = 500;
	private WebDriver driver; // Driver instance
	private static Configuration config;
	public String baseUrl;
	public String mailUrl;
	public String siteName;
	public String browser;
	public String device;
	public boolean isExecutionOnMobile;
	public String currentTestName;
	private AppLibrary appLibrary;

	public String getCurrentTestName() {
		return currentTestName;
	}

	public void setCurrentTestName(String currentTestName) {
		this.currentTestName = currentTestName;
	}

	private String currentSessionID;

	// This is used for parameterized tests
	public AppLibrary(String testName) {
		this.currentTestName = testName;
		config = new Configuration();
	}

	public AppLibrary() {
		// do nothign
	}

	public static Configuration getConfig() {
		if (config == null) {
			config = new Configuration();
		}
		return config;
	}

	public WebDriver getDriverInstance() throws Exception {
		DesiredCapabilities caps = new DesiredCapabilities();
		String browserVersion, os, browserStackOSVersion, remoteGridUrl, environment;

		this.browser = config.getBrowserName();
		baseUrl = config.getURL();
		environment = config.getExecutionEnvironment();

		switch (environment) {

		case "browserstack":
			browserStackOSVersion = config.getBrowserStackOSVersion();
			browserVersion = config.getBrowserVersion();
			os = config.getOS();

			if (config.getBrowserName().equalsIgnoreCase("IE")) {
				caps.setCapability("browser", "IE");
			} else if (config.getBrowserName().equalsIgnoreCase("GCH")
					|| config.getBrowserName().equalsIgnoreCase("chrome")) {
				caps.setCapability("browser", "Chrome");
			} else if (config.getBrowserName().equalsIgnoreCase("safari")) {
				caps.setCapability("browser", "Safari");
			} else {
				caps.setCapability("browser", "Firefox");
			}

			if (browserVersion != null && !browserVersion.equals("") && !browserVersion.equals("latest")) {
				caps.setCapability("browser_version", browserVersion);
			}

			if (browserStackOSVersion != null) {
				caps.setCapability("os", os);
				if (os.toLowerCase().startsWith("win")) {
					caps.setCapability("os", "Windows");
				} else if (os.toLowerCase().startsWith("mac-") || os.toLowerCase().startsWith("os x-")) {
					caps.setCapability("os", "OS X");
				}

				if (os.equalsIgnoreCase("win7")) {
					browserStackOSVersion = "7";
				} else if (os.equalsIgnoreCase("win8")) {
					browserStackOSVersion = "8";
				} else if (os.equalsIgnoreCase("win8.1") || os.equalsIgnoreCase("win8_1")) {
					browserStackOSVersion = "8.1";
				} else if (os.toLowerCase().startsWith("mac-") || os.toLowerCase().startsWith("os x-")) {
					browserStackOSVersion = os.split("-")[1];
				}
				caps.setCapability("os_version", browserStackOSVersion);
			}
			caps.setCapability("resolution", "1920x1080");
			caps.setCapability("browserstack.debug", "true");
			caps.setCapability("build", System.getProperty("Build"));
			caps.setCapability("project", System.getProperty("Suite"));
			caps.setCapability("name", currentTestName);

			try {
				driver = new RemoteWebDriver(new URL("http://" + config.getBrowserStackUserName() + ":"
						+ config.getBrowserStackAuthKey() + "@hub.browserstack.com/wd/hub"), caps);
				((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
			} catch (Exception e) {
				autoLogger("Issue creating new driver instance due to following error: " + e.getMessage() + "\n"
						+ e.getStackTrace());
				throw e;
			}

			break;

		case "seleniumgrid":
			autoLogger("Remote execution set up on URL: " + config.getRemoteGridUrl());
			remoteGridUrl = config.getRemoteGridUrl();
			caps.setBrowserName("chrome");
			caps.setPlatform(Platform.LINUX);
			String url = "http://" + remoteGridUrl + ":4444/wd/hub";
			autoLogger("===================================" + "\n" + "URL:" + url);
			driver = new RemoteWebDriver(new URL(url), caps);
			((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
			break;

		case "local":

			if (config.getBrowserName().equalsIgnoreCase("GCH") || config.getBrowserName().equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();
				System.setProperty("webdriver.http.factory", "jdk-http-client");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--test-type");
				options.addArguments("--disable-extensions");
				options.addArguments("--start-maximized");
				options.addArguments("--remote-allow-origins=*");

				driver = new ChromeDriver(options);
			} else if (config.getBrowserName().equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().setup();
//				System.setProperty("webdriver.firefox.profile", "default");
				driver = new FirefoxDriver();
			}

			else {
				WebDriverManager.safaridriver().setup();
				driver = new SafariDriver();

			}
			break;
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(GLOBALTIMEOUT));
		driver.manage().window().maximize();
		return driver;
	}

	/**
	 * Launch the Application with the specified url
	 *
	 * @param url -URL of the product to be launched
	 */
	public void launchApp(String target) {

		// Delete cookies and Launch the Application
		driver.manage().deleteAllCookies();

		driver.get(target);

	}

	public void launchAppDirectURL(String target) {

		// Delete cookies and Launch the Application
		driver.manage().deleteAllCookies();

		if (target.equalsIgnoreCase("registration")) {
			driver.get(getBaseUrl() + "/start");
		} else if (target.equalsIgnoreCase("login")) {
			driver.get(getBaseUrl() + "/login");
		} else {
			driver.get(getBaseUrl());
		}

	}

	public void launchApp() {
		// Delete cookies and Launch the Application
		driver.manage().deleteAllCookies();
		baseUrl = config.getURL();
		driver.get(baseUrl);

		// Maximize the browser
		driver.manage().window().maximize();
		waitForPageToLoad(driver);
	}

	public String getBaseUrl() {
		String baseUrl = getConfiguration().getURL();
		return baseUrl; // .replace("http://", "https://");
	}

	/**
	 * Returns the array of InputData by parsing an excel at the first sheet
	 *
	 * @throws BiffException
	 * @throws IOException
	 */
	public static String[][] readExcel(String excelFilePath) throws BiffException, IOException {
		String[][] data = readExcel(excelFilePath, 0);
		return data;
	}

	/**
	 * Returns the array of InputData by parsing an excel at a given sheetNo(index
	 * starts from 0)
	 *
	 * @throws BiffException
	 * @throws IOException
	 */
	public static String[][] readExcel(String excelFilePath, int sheetNo) throws BiffException, IOException {
		File file = new File(excelFilePath);
		String inputData[][] = null;
		Workbook w;

		try {
			w = Workbook.getWorkbook(file);

			// Get the first sheet
			Sheet sheet = w.getSheet(sheetNo);

			int colcount = sheet.getColumns();

			int rowcount = sheet.getRows();
			int countYes = 0;

			for (int i = 0; i < rowcount; i++) {
				if (sheet.getCell(colcount - 1, i).getContents().equalsIgnoreCase("Yes")) {
					countYes = countYes + 1;

				}
			}
			inputData = new String[countYes][colcount];
			int k = 0;
			for (int i = 0; i < rowcount; i++) {
				if (sheet.getCell(colcount - 1, i).getContents().equalsIgnoreCase("Yes")) {

					for (int j = 0; j < colcount; j++) {
						Cell cell = sheet.getCell(j, i);
						inputData[k][j] = cell.getContents().trim();

					}
					k = k + 1;
				}

			}

		} catch (BiffException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		return inputData;
	}

	/**
	 * Returns a pseudo-random number between min and max, inclusive. The difference
	 * between min and max can be at most
	 */

	public static int randInt() {
		int min = 1;
		int max = 999;
		Random rand = new Random();
		int randomNum = (rand.nextInt((max - min) + 1) + rand.nextInt((max - min) + 1)) / 2;
		return randomNum;
	}

	/**
	 * Returns a pseudo-random number between min and max, inclusive. The difference
	 * between min and max can be at most
	 */

	public static int randIntDigits(int min, int max) {
		Random rand = new Random();
		int randomNum = (rand.nextInt((max - min) + 1) + rand.nextInt((max - min) + 1)) / 2;
		return randomNum;
	}

	/**
	 * Get Driver Instance
	 */
	public WebDriver getCurrentDriverInstance() {
		return driver;
	}

	/**
	 * Closes the Browser
	 */
	public void closeBrowser() {
		if (driver != null)
//			driver.close();
			driver.quit();
	}

	public void writeToExcel(String fileName) {
		try {
			File file = new File("BrokenLinks");
			if (!file.exists()) {
				if (file.mkdir()) {
					System.out.println("Directory is created!");
				} else {
					System.out.println("Failed to create directory!");
				}
			}

			File exlFile = new File("BrokenLinks\\" + fileName + ".xls");
			WritableWorkbook writableWorkbook = Workbook.createWorkbook(exlFile);

			WritableSheet writableSheet = writableWorkbook.createSheet("Sheet1", 0);

			// Create Cells with contents of different data types.
			// Also specify the Cell coordinates in the constructor
			Label label = new Label(0, 0, "Label (String)");
			DateTime date = new DateTime(1, 0, new Date());
			jxl.write.Boolean bool = new jxl.write.Boolean(2, 0, true);
			jxl.write.Number num = new jxl.write.Number(3, 0, 9.99);

			// Add the created Cells to the sheet
			writableSheet.addCell(label);
			writableSheet.addCell(date);
			writableSheet.addCell(bool);
			writableSheet.addCell(num);

			// Write and close the workbook
			writableWorkbook.write();
			writableWorkbook.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}

	}

	public static WebElement findElement(WebDriver driver, String locatorString) {

		String string = locatorString;
		String[] parts = string.split(":-:");
		String type = parts[0]; // 004
		String object = parts[1];
		if (parts.length > 2) {
			for (int i = 2; i < parts.length; i++) {
				object = object + ":" + parts[i];
			}
		}
		Reporter.log("Finding element with logic: " + locatorString, true);
		System.out.println("Finding element with logic: " + locatorString);
		WebElement element = null;
		if (type.equals("id")) {
			element = driver.findElement(By.id(object));
		} else if (type.equals("name")) {
			element = driver.findElement(By.name(object));
		} else if (type.equals("class")) {
			element = driver.findElement(By.className(object));
		} else if (type.equals("link")) {
			;
			element = driver.findElement(By.linkText(object));
		} else if (type.equals("partiallink")) {
			;
			element = driver.findElement(By.partialLinkText(object));
		} else if (type.equals("css")) {
			element = driver.findElement(By.cssSelector(object));
		} else if (type.equals("xpath")) {
			element = driver.findElement(By.xpath(object));
		} else {
			throw new RuntimeException("Please provide correct element locating strategy");
		}

		return element;
	}

	public static By getLocatorType(String locatorString) {
		String string = locatorString;
		String[] parts = string.split(":-:");
		String type = parts[0]; // 004
		String object = parts[1];
		if (parts.length > 2) {
			for (int i = 2; i < parts.length; i++) {
				object = object + ":" + parts[i];
			}
		}

		if (type.equals("id")) {
			return By.id(object);
		} else if (type.equals("name")) {
			return By.name(object);
		} else if (type.equals("class")) {
			return By.className(object);
		} else if (type.equals("link")) {
			return By.linkText(object);
		} else if (type.equals("partiallink")) {
			return By.partialLinkText(object);
		} else if (type.equals("css")) {
			return By.cssSelector(object);
		} else if (type.equals("xpath")) {
			return By.xpath(object);
		} else {
			throw new RuntimeException("Please provide correct element locating strategy");
		}
	}

	public static WebElement findElement(WebDriver driver, String locatorString, boolean isOptional) {
		try {
			System.out.println(locatorString);
			return findElement(driver, locatorString);
		} catch (NoSuchElementException nse) {
			if (isOptional) {
				return null;
			} else {
				throw new RuntimeException("Element " + locatorString + " not found");
			}
		}
	}

	public static WebElement clickByJavascript(WebDriver driver, String objectLocator) throws Exception {
		String[] parts = objectLocator.split(":-:");
		if (parts.length < 2) {
			throw new RuntimeException("No type is specified in object locator: " + objectLocator);
		}
		String type = parts[0];
		String object = parts[1];

		WebElement element;
		try {
			if (type.equals("id")) {
				element = driver.findElement(By.id(object));
			} else if (type.equals("name")) {
				element = driver.findElement(By.name(object));
			} else if (type.equals("class")) {
				element = driver.findElement(By.className(object));
			} else if (type.equals("link")) {
				;
				element = driver.findElement(By.linkText(object));
			} else if (type.equals("partiallink")) {
				;
				element = driver.findElement(By.partialLinkText(object));
			} else if (type.equals("css")) {
				element = driver.findElement(By.cssSelector(object));
			} else if (type.equals("xpath")) {
				element = driver.findElement(By.xpath(object));
			} else {
				throw new RuntimeException("Please provide correct element locating strategy");
			}

			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
		} catch (Exception e) {
			System.out.println(objectLocator + ": exception occurred: " + e.getClass().toString());
			throw e;
		}
		return element;
	}

	public Configuration getConfiguration() {
		if (config == null) {
			config = new Configuration();
		}
		return config;
	}

	public static void selectElement(WebElement element, String option) {
		Select select = new Select(element);
		select.selectByVisibleText(option);
		sleep(2000);
	}

	public static void selectByPartOfVisibleText(WebElement element, String value) {
		boolean flag = true;
		List<WebElement> optionElements = element.findElements(By.tagName("option"));
		Select select = new Select(element);
		for (WebElement optionElement : optionElements) {
			if (optionElement.getText().contains(value)) {
				String optionValue = optionElement.getAttribute("value");
				select.selectByValue(optionValue);
				flag = false;
				break;
			}
		}

		if (flag) {
			Assert.assertTrue(false, "Option " + value + " was not found in the select");
		}
	}

	public static void selectByPartOfVisibleTextAtEnd(WebElement element, String value) {
		boolean flag = true;
		List<WebElement> optionElements = element.findElements(By.tagName("option"));
		Select select = new Select(element);
		for (WebElement optionElement : optionElements) {
			if (optionElement.getText().endsWith(value)) {
				String optionIndex = optionElement.getAttribute("index");
				select.selectByIndex(Integer.parseInt(optionIndex));
				flag = false;
				break;
			}
		}

		if (flag) {
			Assert.assertTrue(false, "Option " + value + " was not found in the select");
		}
	}

	public static boolean verifyElement(WebDriver driver, String locatorString, boolean checkVisibility) {
		boolean isDisplayed = true;
		try {
			if (checkVisibility) {
				isDisplayed = (findElement(driver, locatorString).isDisplayed());
			} else {
				findElement(driver, locatorString);
			}
		} catch (NoSuchElementException nsee) {
			Assert.assertTrue(false, "Element not found using locator: " + locatorString);
		}
		return isDisplayed;
	}

	public static boolean verifyElement(WebDriver driver, String locatorString, long timeOutInSeconds) {
		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
			findElement(driver, locatorString);
		} catch (NoSuchElementException nsee) {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
			return false;
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		return true;
	}

	public static void sleep(long milliSeconds) {
		try {
			Thread.sleep(milliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

//	public static void getDropdownList() {
//		List<WebElements> options = findE
//	}

	public static List<WebElement> findElements(WebDriver driver, String locatorString) {

		String string = locatorString;
		String[] parts = string.split(":-:");
		String type = parts[0]; // 004
		String object = parts[1];

		Reporter.log("Finding element with logic: " + locatorString, true);
		List<WebElement> element = null;
		if (type.equals("id")) {
			element = driver.findElements(By.id(object));
		} else if (type.equals("name")) {
			element = driver.findElements(By.name(object));
		} else if (type.equals("class")) {
			element = driver.findElements(By.className(object));
		} else if (type.equals("link")) {
			;
			element = driver.findElements(By.linkText(object));
		} else if (type.equals("partiallink")) {
			;
			element = driver.findElements(By.partialLinkText(object));
		} else if (type.equals("css")) {
			element = driver.findElements(By.cssSelector(object));
		} else if (type.equals("xpath")) {
			element = driver.findElements(By.xpath(object));
		} else {
			throw new RuntimeException("Please provide correct element locating strategy");
		}
		return element;
	}

	public static By getBy(WebDriver driver, String locatorString) {

		String string = locatorString;
		String[] parts = string.split(":-:");
		String type = parts[0];
		String object = parts[1];

		if (type.equals("id")) {
			return By.id(object);
		} else if (type.equals("name")) {
			return By.name(object);
		} else if (type.equals("class")) {
			return By.className(object);
		} else if (type.equals("link")) {
			return By.linkText(object);
		} else if (type.equals("partiallink")) {
			return By.partialLinkText(object);
		} else if (type.equals("css")) {
			return By.cssSelector(object);
		} else if (type.equals("xpath")) {
			return By.xpath(object);
		} else {
			throw new RuntimeException("Please provide correct element locating strategy");
		}
	}

	public void switchToWindow(int windowNo) {
		Set<String> set = driver.getWindowHandles();
		String windowHandle = null;
		Reporter.log("Current no. of windows are: " + set.size(), true);
		if (windowNo <= set.size()) {
			ArrayList<String> windows = new ArrayList<String>(set);
			windowHandle = windows.get(windowNo - 1);
		}

		if (windowHandle != null) {
			driver.switchTo().window(windowHandle);
		} else {
			throw new RuntimeException("Specified window not available");
		}
	}

	public String getCurrentSessionID() {
		return currentSessionID;
	}

	public static void waitForElementClickable(WebDriver driver, WebElement element) {
		new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(element));
	}

	public void waitForElementVisible(WebElement element) {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void enterText(WebDriver driver, String locator, String text) throws Exception {
		WebElement element = findElement(driver, locator);
		element.click();
		element.clear();
		element.sendKeys(text);
	}

	public static void sendKeys(WebDriver driver, String locator, String text) throws Exception {
		WebElement element = findElement(driver, locator);
		element.click();
		element.sendKeys(text);
	}

	public void uploadFile(String locator) {

		WebElement upload_file = driver.findElement(By.xpath("//input[@id='file_up']"));

		upload_file.sendKeys();
	}

	public static void clickElement(WebDriver driver, String locator) throws Exception {

		int i = 0;
		do {
			try {
				driver.findElement(getBy(driver, locator)).click();
				break;
			} catch (Exception e) {
				AppLibrary.sleep(1000);
				i++;
				continue;
			}

		} while (i < 5);

		if (i >= 5) {
			throw new Exception("Failed to click element, Locator: " + locator);
		}
	}

	public static void waitUntilElementDisplayed(WebDriver driver, String locatorString) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofHours(10));
		String string = locatorString;
		String[] parts = string.split(":");
		String type = parts[0];
		String object = parts[1];

		Reporter.log("Finding element with logic: " + locatorString, true);
		if (type.equals("id")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(object)));
		} else if (type.equals("name")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(object)));
		} else if (type.equals("class")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(object)));
		} else if (type.equals("link")) {
			;
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(object)));
		} else if (type.equals("partiallink")) {
			;
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(object)));
		} else if (type.equals("css")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(object)));
		} else if (type.equals("xpath")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(object)));
		} else {
			throw new RuntimeException("Please provide correct element locating strategy");
		}
	}

	public static void verifyAbsentWithTimeOut(WebDriver driver, String locatorString, long timeOutInSeconds) {

		String string = locatorString;
		String[] parts = string.split(":");
		String type = parts[0]; // 004
		String object = parts[1];

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		WebElement element = null;
		try {
			Reporter.log("Finding element with logic: " + locatorString, true);
			if (type.equals("id")) {
				element = driver.findElement(By.id(object));
			} else if (type.equals("name")) {
				element = driver.findElement(By.name(object));
			} else if (type.equals("class")) {
				element = driver.findElement(By.className(object));
			} else if (type.equals("link")) {
				element = driver.findElement(By.linkText(object));
			} else if (type.equals("partiallink")) {
				element = driver.findElement(By.partialLinkText(object));
			} else if (type.equals("css")) {
				element = driver.findElement(By.cssSelector(object));
			} else if (type.equals("xpath")) {
				element = driver.findElement(By.xpath(object));
			}

			org.testng.Assert.assertTrue(false,
					"Expected element to be absent, but it was found on the page. Text = " + element.getText());

		} catch (Exception e) {
			// It's good if not found
		} finally {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		}

	}

	public static void verifyAbsent(WebDriver driver, String locatorString) {

		String string = locatorString;
		String[] parts = string.split(":");
		String type = parts[0]; // 004
		String object = parts[1];

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		WebElement element = null;
		try {
			Reporter.log("Finding element with logic: " + locatorString, true);
			if (type.equals("id")) {
				element = driver.findElement(By.id(object));
			} else if (type.equals("name")) {
				element = driver.findElement(By.name(object));
			} else if (type.equals("class")) {
				element = driver.findElement(By.className(object));
			} else if (type.equals("link")) {
				element = driver.findElement(By.linkText(object));
			} else if (type.equals("partiallink")) {
				element = driver.findElement(By.partialLinkText(object));
			} else if (type.equals("css")) {
				element = driver.findElement(By.cssSelector(object));
			} else if (type.equals("xpath")) {
				element = driver.findElement(By.xpath(object));
			}

			org.testng.Assert.assertTrue(false,
					"Expected element to be absent, but it was found on the page. Text = " + element.getText());

		} catch (Exception e) {
			// It's good if not found
		} finally {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		}

	}

	public static void waitForNavigation(WebDriver driver, String url) {

		int counter = 10;
		for (; counter > 0; counter--) {
			if (driver.getCurrentUrl().contains(url)) {
				break;
			} else {
				AppLibrary.sleep(10000);
			}
		}
	}

	public void verifySite(String currentURL) {
		Assert.assertTrue(driver.getCurrentUrl().contains(siteName.trim()),
				siteName + " is not present in " + driver.getCurrentUrl());
		Assert.assertTrue(currentURL.contains("https:"), "We are not secure Current URL " + currentURL);
	}

	public static void syncAndClick(WebDriver driver, String locator) {
		try {
			AppLibrary.waitForElementClickable(driver, AppLibrary.findElement(driver, locator, true));
			AppLibrary.findElement(driver, locator, true).click();
		} catch (Exception e) {
			AppLibrary.sleep(5000);
			AppLibrary.findElement(driver, locator, true).click();
		}
	}

	public static void switchToWindow(WebDriver driver, int windowNo) {
		Set<String> set = driver.getWindowHandles();
		String windowHandle = null;
		Reporter.log("Current no. of windows are: " + set.size(), true);
		if (windowNo <= set.size()) {
			ArrayList<String> windows = new ArrayList<String>(set);
			windowHandle = windows.get(windowNo - 1);
		}

		if (windowHandle != null) {
			driver.switchTo().window(windowHandle);
		} else {
			throw new RuntimeException("Specified window not available");
		}
	}

	public String getFileData(String defaultEditorVal) {
		byte[] data = null;
		String str = "";
		FileInputStream fis = null;

		File file = new File("resources" + File.separator + defaultEditorVal);

		try {
			fis = new FileInputStream(file);
			data = new byte[(int) file.length()];
			fis.read(data);
			str = new String(data, "UTF-8");
			return str;

		} catch (FileNotFoundException e) {
			Assert.assertTrue(false, "File not found " + defaultEditorVal);
		} catch (IOException e) {
			Assert.assertTrue(false, "IO exception when reading file " + defaultEditorVal);
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				Assert.assertTrue(false, "IO exception when closing file " + defaultEditorVal);
			}
		}

		return str;

	}

	public void clickElement(WebElement element) {
		try {
			AppLibrary.sleep(1000);
			element.click();
		} catch (Exception e) {
			AppLibrary.sleep(3000);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView()", element);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
		}
	}

	public static void scroll(WebDriver driver, String locator) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement element = AppLibrary.findElement(driver, locator);
		// This will scroll the page till the element is found
		js.executeScript("arguments[0].scrollIntoView();", element);
		waitTillElementClickable(driver, locator);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	public static void verification(WebDriver driver, String locator, String expectedValue) {

		String actualValue = AppLibrary.findElement(driver, locator).getText();

		Assert.assertEquals(actualValue.replace(",", ""), expectedValue,
				"values didnot match for " + locator + "Expected =" + expectedValue + "  Actual =" + actualValue);
	}

	public static String GetTextForVerification(WebDriver driver, String locator) {
		String actualValue = AppLibrary.findElement(driver, locator).getText();
		return actualValue;
	}

	public static void verificationWithAttribute(WebDriver driver, String locator, String expectedValue) {

		String actualValue = AppLibrary.findElement(driver, locator).getAttribute("value");

		Assert.assertEquals(actualValue.replace(",", ""), expectedValue,
				"values didnot match for " + locator + "Expected =" + expectedValue + "  Actual =" + actualValue);
	}

	public String generateRandomString(int length) {
		return RandomStringUtils.randomAlphabetic(length);
	}

	public static String generateRandomNumber(int length) {
		return RandomStringUtils.randomNumeric(length);
	}

	public static String generateRandomNumberEin(int length) {
		return RandomStringUtils.randomNumeric(length);
	}

	public String generateRandomAlphaNumeric(int length) {
		return RandomStringUtils.randomAlphanumeric(length);
	}

	public String generateStringWithAllobedSplChars(int length, String allowdSplChrs) {
		String allowedChars = "abcdefghijklmnopqrstuvwxyz" + // alphabets
				"1234567890" + // numbers
				allowdSplChrs;
		return RandomStringUtils.random(length, allowedChars);
	}

	public String generateEmail(int length) {
		String allowedChars = "abcdefghijklmnopqrstuvwxyz" + // alphabets
				"1234567890" + // numbers
				"_-."; // special characters
		String email = "";
		String temp = RandomStringUtils.random(length, allowedChars);
		email = temp.substring(0, temp.length() - 9) + "@test.org";
		return email;
	}

	public String generateUrl(int length) {
		String allowedChars = "abcdefghijklmnopqrstuvwxyz" + // alphabets
				"1234567890" + // numbers
				"_-."; // special characters
		String url = "";
		String temp = RandomStringUtils.random(length, allowedChars);
		url = temp.substring(0, 3) + "." + temp.substring(4, temp.length() - 4) + "."
				+ temp.substring(temp.length() - 3);
		return url;
	}

	public static void mouseHover(WebDriver driver, String locator) {

		WebElement element = AppLibrary.findElement(driver, locator);
		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();

	}

	public static void mouseRightClick(WebDriver driver, String locator) {

		Actions actions = new Actions(driver);
		WebElement elementLocator = AppLibrary.findElement(driver, locator);
///	actions.contextClick(elementLocator).perform();
		actions.contextClick(elementLocator).sendKeys(Keys.ARROW_UP).sendKeys(Keys.ENTER).build().perform();
	}

	public static boolean isElementPresent(WebDriver driver, String locator) {
		try {

			AppLibrary.findElement(driver, locator);
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	public static void openNewTabByJavaScript(WebDriver driver) {

		((JavascriptExecutor) driver).executeScript("window.open();");

	}

	public void getScreenshot(String name) throws IOException {
		driver = getCurrentDriverInstance();
		String path = "screenshots/" + name;
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File(path));
		System.out.println("screenshot at :" + path);
		Reporter.log("screenshot for " + name + " available at :" + path, true);
	}

	public void checkAlertsForScreenshot(ITestResult result, String testName) {
		System.out.println("im doing nothign");
		String screenshotName = result.getName() + "_" + browser + "_" + AppLibrary.randInt() + ".png";

		if (result.getStatus() == ITestResult.FAILURE) {
			try {

				getScreenshot(screenshotName);
				Reporter.log("Failed at URL: " + getCurrentDriverInstance().getCurrentUrl(), true);
				int paramsLength = result.getParameters().length;
				Reporter.log("Screenshot Name : " + screenshotName, true);
				Reporter.log("ScreenShot for " + testName + " "
						+ ((paramsLength > 0) ? " with parameter " + result.getParameters()[1] : "") + " saved as "
						+ screenshotName + ".png", true);
				driver.navigate().refresh();

			} catch (Exception e) {
				Reporter.log("Failed fetching URL and screenshot due to error:" + e.getMessage(), true);
				e.printStackTrace();
			}

			if (getCurrentSessionID() != null) {
				Reporter.log("Session id for " + testName + " is " + getCurrentSessionID(), true);
				Reporter.log("Session details for " + testName
						+ " can be found at https://www.browserstack.com/automate/sessions/" + getCurrentSessionID()
						+ ".json", true);
			}
		}

	}

	public static void selectDropDown(WebDriver driver, String locator, String locator2, String Option) {

		AppLibrary.findElement(driver, locator).click();
		AppLibrary.findElement(driver, locator).sendKeys(Option);
		;

		AppLibrary.sleep(3000);

		boolean flag = true;
		List<WebElement> all = AppLibrary.findElements(driver, locator2);
		for (WebElement element : all) {
			if (element.getText().contains(Option)) {
				element.click();
				flag = false;
				break;
			}
		}

	}

	public void verificationwithtwoValue(WebDriver driver, String actualValue, String expectedValue) {

		Assert.assertEquals(actualValue.replace(",", ""), expectedValue,
				"values didnot match for " + actualValue + "Expected =" + expectedValue + "  Actual =" + actualValue);

	}

	public void verificationwithtwoStrings(WebDriver driver, String actualValue, String expectedValue) {

		Assert.assertTrue(actualValue.contains(expectedValue));
	}

	public static WebElement clearTextByJavascript(WebDriver driver, String objectLocator) throws Exception {
		String[] parts = objectLocator.split(":");
		if (parts.length < 2) {
			throw new RuntimeException("No type is specified in object locator: " + objectLocator);
		}
		String type = parts[0];
		String object = parts[1];

		WebElement element;
		try {
			if (type.equals("id")) {
				element = driver.findElement(By.id(object));
			} else if (type.equals("name")) {
				element = driver.findElement(By.name(object));
			} else if (type.equals("class")) {
				element = driver.findElement(By.className(object));
			} else if (type.equals("link")) {
				;
				element = driver.findElement(By.linkText(object));
			} else if (type.equals("partiallink")) {
				;
				element = driver.findElement(By.partialLinkText(object));
			} else if (type.equals("css")) {
				element = driver.findElement(By.cssSelector(object));
			} else if (type.equals("xpath")) {
				element = driver.findElement(By.xpath(object));
			} else {
				throw new RuntimeException("Please provide correct element locating strategy");
			}

			((JavascriptExecutor) driver).executeScript("arguments[0].value ='';", element);
		} catch (Exception e) {
			System.out.println(objectLocator + ": exception occurred: " + e.getClass().toString());
			throw e;
		}
		return element;
	}

	public static void Verificationwithcontains(WebDriver driver, String locator, String expectedValue) {

		String actualValue = AppLibrary.findElement(driver, locator).getText();

		boolean a = actualValue.contains(expectedValue);

		Assert.assertTrue(a, "Verification failed");

	}

	public static WebElement setAttributeByJavascript(WebDriver driver, String objectLocator, String value)
			throws Exception {
		String[] parts = objectLocator.split(":");
		if (parts.length < 2) {
			throw new RuntimeException("No type is specified in object locator: " + objectLocator);
		}
		String type = parts[0];
		String object = parts[1];

		WebElement element;
		try {
			if (type.equals("id")) {
				element = driver.findElement(By.id(object));
			} else if (type.equals("name")) {
				element = driver.findElement(By.name(object));
			} else if (type.equals("class")) {
				element = driver.findElement(By.className(object));
			} else if (type.equals("link")) {
				;
				element = driver.findElement(By.linkText(object));
			} else if (type.equals("partiallink")) {
				;
				element = driver.findElement(By.partialLinkText(object));
			} else if (type.equals("css")) {
				element = driver.findElement(By.cssSelector(object));
			} else if (type.equals("xpath")) {
				element = driver.findElement(By.xpath(object));
			} else {
				throw new RuntimeException("Please provide correct element locating strategy");
			}

			((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);",
					element, "value", value);
		} catch (Exception e) {
			System.out.println(objectLocator + ": exception occurred: " + e.getClass().toString());
			throw e;
		}
		return element;
	}

	public static boolean isAttribtuePresent(WebElement element, String attribute) {
		Boolean result = false;
		try {
			String value = element.getAttribute(attribute);
			if (value != null) {
				result = true;
			}
		} catch (Exception e) {
		}

		return result;
	}

	public static boolean waitTillElementLoaded(WebDriver driver, String locator) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		int counter = 10;
		do {
			try {
				if (AppLibrary.findElement(driver, locator) != null) {
					return true;
				} else {
					sleep(1000);
					counter--;
				}
			} catch (Exception e) {
				sleep(3000);
				counter--;
				continue;
			}
		} while (counter > 0);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(GLOBALTIMEOUT));
		throw new RuntimeException("element was not loaded:" + locator);
	}

	public static void waitForPageToLoad(WebDriver driver) {
		new WebDriverWait(driver, Duration.ofHours(10)).until(webDriver -> ((JavascriptExecutor) webDriver)
				.executeScript("return document.readyState").equals("complete"));

	}

	public static void switchToFrame(WebDriver driver, String locatorString) {
		driver.switchTo().frame(locatorString);
	}

	public static void scrollIframe(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(400,400)");
	}

	public static void SwitchWindow(WebDriver driver) {
		String parent = driver.getWindowHandle();
		Set<String> s = driver.getWindowHandles();
		Iterator<String> I1 = s.iterator();

		while (I1.hasNext()) {

			String child_window = I1.next();
			driver.switchTo().window(child_window);
		}
	}

	public static void clickElementWithJs(WebDriver driver, String locator) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		By by = getBy(driver, locator);
		WebElement element = driver.findElement(by);
		js.executeScript("arguments[0].click();", element);
	}

	public static void selectOption(WebDriver driver, String xpath, int index) {
		WebElement element = driver.findElement(By.xpath(xpath));
		Select objSelect = new Select(element);
		objSelect.selectByIndex(index);
	}

	public static boolean waitTillElementClickable(WebDriver driver, String locator) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
//		sleep(2000);
		int counter = 10;
		do {
			try {
				if (AppLibrary.findElement(driver, locator) != null) {
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofHours(10));
					wait.until(ExpectedConditions.elementToBeClickable(AppLibrary.findElement(driver, locator)));
					return true;
				} else {
					sleep(1000);
					counter--;
				}
			} catch (Exception e) {
				sleep(3000);
				counter--;
				continue;
			}
		} while (counter > 0);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		throw new RuntimeException("element was not loaded:" + locator);
	}

	public static String getFormattedDate() {
		return getDate().replaceAll("/", "_").replaceAll(":", "_").replaceAll(" ", "_");
	}

	public static String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd HH:mm:ss:SSS");
		Date date = new Date();
		autoLogger(dateFormat.format(date));
		return dateFormat.format(date);

//		LocalDateTime now = LocalDateTime.now();
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd HH:mm:ss:SSSSSSSSS");
//		String formattedDate = now.format(formatter);
//		autoLogger(formattedDate);
//		return formattedDate;

	}

	public static void autoLogger(String message) {
		Reporter.log(message, true);
	}

	public static boolean syncProgress(WebDriver driver) throws Exception {
		int loadCounter = 10;
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		while (loadCounter > 0) {
			try {
				if (isLoaderDisplayed(driver)) {
					System.out.println("syncProgress: true");
					driver.findElement(By.xpath("//div[@id='loading'][(contains(@style, 'display: none'))]//p"));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(GLOBALTIMEOUT));
					return true;
				} else {
					System.out.println("syncProgress: false");
					return true;
				}
			} catch (Exception e) {
				loadCounter--;
			}
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(GLOBALTIMEOUT));
		throw new Exception("Progress was not completed within specified time");
	}

	private static boolean isLoaderDisplayed(WebDriver driver) {
		try {
			driver.findElement(By.xpath("//div[@id='loading'][not(contains(@style, 'display'))]//p"));
			System.out.println("isLoaderDisplayed: true");
			return true;
		} catch (NoSuchElementException e) {
			System.out.println("isLoaderDisplayed: false");
			return false;
		}
	}

}
