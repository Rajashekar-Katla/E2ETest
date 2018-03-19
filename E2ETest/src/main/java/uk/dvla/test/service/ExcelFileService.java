package uk.dvla.test.service;

public interface ExcelFileService {
	
	String getCellValue(final int row, final int col);

	int getNumberOfRowsForSheet(final int index);
	
	int getNumberOfSheets();
	
}
