package uk.dvla.test.service.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import uk.dvla.test.service.ExcelFileService;

/**
 * This class is used to process excel files.
 * 
 * @author Raj
 *
 */
public class ExcelFileServiceImpl implements ExcelFileService {

	private static final String EMPTY = "";
	private XSSFWorkbook excelBook;
	private XSSFSheet sheet;

	public ExcelFileServiceImpl(final FileInputStream input) throws IOException {
		if (checkNotNull(input)) {
			this.excelBook = new XSSFWorkbook(input);
		}

	}

	@Override
	public int getNumberOfSheets() {
		if (checkNotNull(excelBook)) {
			return excelBook.getNumberOfSheets();
		} else {
			return 0;
		}
	}

	private XSSFSheet getSheetForIndex(final int index) {
		if (checkNotNull(excelBook)) {
			sheet = excelBook.getSheetAt(index);
			return sheet;
		} else {
			return null;
		}
	}

	@Override
	public int getNumberOfRowsForSheet(final int index) {
		if ((checkNotNull(getSheetForIndex(index)))) {
			return getSheetForIndex(index).getPhysicalNumberOfRows();
		}
		return 0;
	}

	@Override
	public String getCellValue(final int row, final int col) {
		if (checkNotNull(sheet)) {
			final XSSFCell cell = sheet.getRow(row).getCell(col);
			if (checkNotNull(cell)) {
				return cell.getStringCellValue();
			}
		}
		return EMPTY;
	}

	private boolean checkNotNull(Object obj) {
		return Optional.ofNullable(obj).isPresent();
	}

}
