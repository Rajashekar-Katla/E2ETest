package uk.dvla.selenium.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import uk.dvla.test.elements.path.ElementsPath;
import uk.dvla.test.mime.types.MIMEType;
import uk.dvla.test.model.FileInformation;
import uk.dvla.test.service.CsvFileService;
import uk.dvla.test.service.ExcelFileService;
import uk.dvla.test.service.FileScannerService;
import uk.dvla.test.service.FileService;
import uk.dvla.test.service.impl.CsvFileServiceImpl;
import uk.dvla.test.service.impl.ExcelFileServiceImpl;
import uk.dvla.test.service.impl.FileScannerServiceImpl;
import uk.dvla.test.service.impl.FileServiceImpl;

public class VehicleRegistrationDetailsTest {

	private String firefoxDriver = "src/test/resources/drivers/firefox/geckodriver.exe";
	private String url = "https://www.gov.uk/get-vehicle-information-from-dvla";
	private String sourceDirectory = "files";
	private int waitSecs = 10;
	private static Set<String> mimeType = null;
	private ExcelFileService excelFileService;
	private WebDriver webDriver;
	private ElementsPath elementsPath;
	final FileScannerService resource = new FileScannerServiceImpl(sourceDirectory);
	List<FileInformation> filesInformationList = resource.loadFileInformationFromDirectory();
	final FileService service = new FileServiceImpl(filesInformationList);

	@Before
	public void setup() throws Throwable {
		System.setProperty("webdriver.gecko.driver", firefoxDriver);
		final FirefoxOptions options = new FirefoxOptions();
		options.setHeadless(true);
		mimeType = new HashSet<>();
		webDriver = new FirefoxDriver(options);
		webDriver.manage().timeouts().implicitlyWait(waitSecs, TimeUnit.SECONDS);
		elementsPath = new ElementsPath(webDriver);
		webDriver.get(url);
	}

	@After
	public void tearDown() {
		if (webDriver != null) {
			webDriver.quit();
		}
	}

	@Test
	public void testVehicleMakeAndColourForCSV() throws IOException {
		mimeType.add(MIMEType.CSV.getMimeType());
		mimeType.add(MIMEType.OCTET_STREAM.getMimeType());

		filesInformationList = service.getSupportedMIMETypeFiles(mimeType);

		for (FileInformation fileInfo : filesInformationList) {
			final String filePath = new StringBuilder(sourceDirectory).append("/").append(fileInfo.getFileName())
					.toString();

			if (fileInfo.getFileExtension().equals("csv")) {

				final CsvFileService csvService = new CsvFileServiceImpl(Paths.get(filePath));

				assertTrue(elementsPath.startButton.isDisplayed());

				elementsPath.clickOnStartButton();

				final List<CSVRecord> csvRecords = csvService.getRecords();

				for (int row = 0; row < csvRecords.size(); row++) {

					final String value = csvService.getValue(csvRecords.get(row));
					final String registrationNumber = csvService.getRegistrationNumber(value);
					final String expectedMake = csvService.getMake(value);
					final String expectedVehicleColour = csvService.getColour(value);

					assertions(registrationNumber, expectedMake, expectedVehicleColour);

					File scrFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
					FileUtils.copyFile(scrFile, new File("screenshots/screenshot" + row + ".png"));

					webDriver.navigate().back();

				}
			}
		}
	}

	@Test
	public void testVehicleMakeAndColourForExcel() throws IOException {
		mimeType.add(MIMEType.XLS.getMimeType());
		mimeType.add(MIMEType.XLSX.getMimeType());

		filesInformationList = service.getSupportedMIMETypeFiles(mimeType);

		for (FileInformation fileInfo : filesInformationList) {
			final String filePath = new StringBuilder(sourceDirectory).append("/").append(fileInfo.getFileName())
					.toString();
			if (fileInfo.getFileExtension().equals("xlsx")) {
				final FileInputStream input = new FileInputStream(filePath);
				testExcelFiles(input);
			}
		}
	}

	private void testExcelFiles(final FileInputStream input) throws IOException {
		excelFileService = new ExcelFileServiceImpl(input);

		assertTrue(elementsPath.startButton.isDisplayed());

		elementsPath.clickOnStartButton();

		final int numberOfSheets = excelFileService.getNumberOfSheets();

		for (int i = 0; i < numberOfSheets; i++) {

			final int rows = excelFileService.getNumberOfRowsForSheet(i);

			for (int row = 1; row < rows; row++) {

				int col = 0;

				final String registrationNumber = excelFileService.getCellValue(row, col);

				final String expectedMake = excelFileService.getCellValue(row, ++col);

				final String expectedVehicleColour = excelFileService.getCellValue(row, ++col);

				assertions(registrationNumber, expectedMake, expectedVehicleColour);

				File scrFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile, new File("screenshots/screenshot" + row + ".png"));

				webDriver.navigate().back();
			}
		}
	}

	private void assertions(final String registrationNumber, final String expectedMake,
			final String expectedVehicleColour) {

		assertTrue(elementsPath.textBox.isDisplayed());

		elementsPath.enterVehicaleRegisterationNumber(registrationNumber);

		assertTrue(elementsPath.continueButton.isDisplayed());

		elementsPath.submitRegisterationNumber();

		final String actualMake = elementsPath.vehicleMake.getText();

		assertTrue(expectedMake.trim().equalsIgnoreCase(actualMake.trim()));

		final String actualVehicleColour = elementsPath.vehicleColour.getText();

		assertTrue(expectedVehicleColour.trim().equalsIgnoreCase(actualVehicleColour.trim()));
	}
}
