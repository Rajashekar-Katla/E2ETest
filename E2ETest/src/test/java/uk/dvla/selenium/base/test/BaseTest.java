package uk.dvla.selenium.base.test;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import uk.dvla.selenium.pages.HomePage;
import uk.dvla.selenium.pages.VehicleLookUpResultPage;
import uk.dvla.selenium.pages.VehicleLookupPage;
import uk.dvla.test.model.FileInformation;
import uk.dvla.test.service.FileScannerService;
import uk.dvla.test.service.FileService;
import uk.dvla.test.service.impl.FileScannerServiceImpl;
import uk.dvla.test.service.impl.FileServiceImpl;

public class BaseTest {

	protected static final String PATH_SEPARATOR = "/";
	private static String firefoxDriver = "src/test/resources/drivers/firefox/geckodriver.exe";
	protected static WebDriver webDriver;
	protected static WebDriverWait wait;
	protected static int waitSecs = 10;
	protected static String sourceDirectory = "files";
	protected static FileScannerService resource = null;
	protected static List<FileInformation> filesInformationList = null;
	protected static FileService service = null;
	protected static Set<String> mimeType = null;
	protected static HomePage homePage = null;
	protected static VehicleLookupPage lookupPage = null;
	protected static VehicleLookUpResultPage resultPage = null;
	private static final String PNG = ".png";
	private static final String SCREENSHOTS_PATH = "screenshots/screenshot";

	@BeforeClass
	public static void setUp() {
		resource = new FileScannerServiceImpl(sourceDirectory);
		filesInformationList = resource.loadFileInformationFromDirectory();
		service = new FileServiceImpl(filesInformationList);
		mimeType = new HashSet<>();

		System.setProperty("webdriver.gecko.driver", firefoxDriver);
		final FirefoxOptions options = new FirefoxOptions();
		options.setHeadless(true);

		// Create a driver. All test and page classes use this driver
		webDriver = new FirefoxDriver(options);

		// Create a wait. All test and page classes use this wait.
		wait = new WebDriverWait(webDriver, waitSecs);

		// Page Instantiation
		homePage = new HomePage(webDriver, wait);
		lookupPage = new VehicleLookupPage(webDriver, wait);
		resultPage = new VehicleLookUpResultPage(webDriver, wait);

	}

	@AfterClass
	public static void teardown() {
		webDriver.quit();
	}

	protected void doHomePageAtions() {
		// home page method
		homePage.goToHomePage();

		if (Optional.of(filesInformationList).isPresent()) {
			// Go to vehicle lookup page
			homePage.goToVehicleLookupPage();
		}
	}

	protected void doAssertionsAndTakeScreenshot(final int row, final String registrationNumber,
			final String expectedMake, final String expectedVehicleColour) throws IOException {

		lookupPage.verifyTextBoxIsDisplayed();

		// Enter registration number in vehicle lookup page text box
		lookupPage.enterVehicleRegistrationNumber(registrationNumber);

		lookupPage.verifyContinueButtonIsDisplayed();

		// Click Continue button
		lookupPage.goToVehicleLookUpResultPage();

		// Verify vehicle make
		resultPage.verifyVehicleMake(expectedMake);

		// Verify vehicle colour
		resultPage.verifyVehicleColour(expectedVehicleColour);

		takeScreenShotsAndSave(webDriver, row);

		webDriver.navigate().back();
	}

	protected String getFilePath(final String fileName) {
		return new StringBuilder(sourceDirectory).append(PATH_SEPARATOR).append(fileName).toString();
	}

	public static void takeScreenShotsAndSave(final WebDriver webDriver, final int row) throws IOException {
		File scrFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(SCREENSHOTS_PATH + row + PNG));
	}
}
