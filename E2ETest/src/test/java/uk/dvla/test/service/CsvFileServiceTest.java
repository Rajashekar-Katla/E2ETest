package uk.dvla.test.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class CsvFileServiceTest {
	@Mock
	private CsvFileService csvFileService;

	@Before
	public void setUp() {
		csvFileService = Mockito.mock(CsvFileService.class);
	}

	@Test
	public void testGetRecords() throws IOException {
		final List<CSVRecord> records = Arrays.asList();
		when(csvFileService.getRecords()).thenReturn(records);

		final List<CSVRecord> list = csvFileService.getRecords();

		assertThat(list, not(equalTo(null)));

		verify(csvFileService, times(1)).getRecords();
		verifyNoMoreInteractions(csvFileService);

	}

	@Test
	public void testGetRegistrationNumber() throws IOException {

		final String str = "LPIIXKF;VOLKSWAGEN;BLACK";
		when(csvFileService.getRegistrationNumber(anyString())).thenReturn(str.split(";")[0]);
		final String registrationNumber = csvFileService.getRegistrationNumber(str);

		assertThat(registrationNumber, equalTo("LPIIXKF"));
		verify(csvFileService, times(1)).getRegistrationNumber(anyString());
		verifyNoMoreInteractions(csvFileService);

	}

}
