package uk.dvla.test.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ExcelFileServiceTest {
	
	private ExcelFileService excelFileService;

	@Before
	public void setUp() {
		excelFileService = Mockito.mock(ExcelFileService.class);
	}

	@Test
	public void testGetNumberOfSheets() {
		when(excelFileService.getNumberOfSheets()).thenReturn(2);
		int sheets = excelFileService.getNumberOfSheets();

		assertThat(sheets, equalTo(2));

		verify(excelFileService, times(1)).getNumberOfSheets();
		verifyNoMoreInteractions(excelFileService);
	}

	@Test
	public void testGetCellValue() {
		final String value = "LPII XKF";
		when(excelFileService.getCellValue(anyInt(), anyInt())).thenReturn(value);
		final String cellData = excelFileService.getCellValue(anyInt(), anyInt());

		assertThat(cellData, equalTo(value));

		verify(excelFileService, times(1)).getCellValue(anyInt(), anyInt());
		verifyNoMoreInteractions(excelFileService);
	}

}
