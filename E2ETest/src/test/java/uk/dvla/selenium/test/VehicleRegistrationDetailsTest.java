package uk.dvla.selenium.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

import uk.dvla.selenium.base.test.BaseTest;
import uk.dvla.test.mime.types.MIMEType;
import uk.dvla.test.model.FileInformation;
import uk.dvla.test.service.CsvFileService;
import uk.dvla.test.service.ExcelFileService;
import uk.dvla.test.service.impl.CsvFileServiceImpl;
import uk.dvla.test.service.impl.ExcelFileServiceImpl;

/**
 * @author Raj
 */
public class VehicleRegistrationDetailsTest extends BaseTest {

	@Test
	public void testVehicleMakeAndColourForCSV() throws IOException {
		mimeType.add(MIMEType.CSV.getMimeType());
		mimeType.add(MIMEType.OCTET_STREAM.getMimeType());
		filesInformationList = service.getSupportedMIMETypeFiles(mimeType);

		doHomePageAtions();

		for (final FileInformation fileInfo : filesInformationList) {
			final String filePath = getFilePath(fileInfo.getFileName());
			if (fileInfo.getFileExtension().equals("csv")) {
				final CsvFileService csvService = new CsvFileServiceImpl(Paths.get(filePath));
				final List<CSVRecord> csvRecords = csvService.getRecords();

				for (int row = 0; row < csvRecords.size(); row++) {
					final String value = csvService.getValue(csvRecords.get(row));
					final String registrationNumber = csvService.getRegistrationNumber(value);
					final String expectedMake = csvService.getMake(value);
					final String expectedVehicleColour = csvService.getColour(value);
					// do assertions
					doAssertionsAndTakeScreenshot(row, registrationNumber, expectedMake, expectedVehicleColour);
				}
			}
		}
	}

	@Test
	public void testVehicleMakeAndColourForExcel() throws IOException {
		mimeType.add(MIMEType.XLS.getMimeType());
		mimeType.add(MIMEType.XLSX.getMimeType());
		mimeType.add(MIMEType.OCTET_STREAM.getMimeType());
		filesInformationList = service.getSupportedMIMETypeFiles(mimeType);

		doHomePageAtions();

		for (final FileInformation fileInfo : filesInformationList) {
			final String filePath = getFilePath(fileInfo.getFileName());
			if (fileInfo.getFileExtension().equals("xlsx")) {
				final FileInputStream input = new FileInputStream(filePath);
				final ExcelFileService excelFileService = new ExcelFileServiceImpl(input);
				final int numberOfSheets = excelFileService.getNumberOfSheets();
				for (int i = 0; i < numberOfSheets; i++) {
					final int rows = excelFileService.getNumberOfRowsForSheet(i);
					for (int row = 1; row < rows; row++) {
						int col = 0;
						final String registrationNumber = excelFileService.getCellValue(row, col);
						final String expectedMake = excelFileService.getCellValue(row, ++col);
						final String expectedVehicleColour = excelFileService.getCellValue(row, ++col);
						// do assertions
						doAssertionsAndTakeScreenshot(row, registrationNumber, expectedMake, expectedVehicleColour);
					}
				}
			}
		}
	}
}
