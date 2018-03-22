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
import org.mockito.Mockito;

public class CsvFileServiceTest {
	private static final String REGEX = ";";
	private static final String STR = "LPIIXKF;VOLKSWAGEN;BLACK";
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
		when(csvFileService.getRegistrationNumber(anyString())).thenReturn(getVehicleRegistrationNumber(STR));
		final String registrationNumber = csvFileService.getRegistrationNumber(STR);

		assertThat(registrationNumber, equalTo("LPIIXKF"));
		verify(csvFileService, times(1)).getRegistrationNumber(anyString());
		verifyNoMoreInteractions(csvFileService);
	}

	@Test
	public void testGetVehicleMake() throws IOException {
		when(csvFileService.getRegistrationNumber(anyString())).thenReturn(getVehicleMake(STR));
		final String registrationNumber = csvFileService.getRegistrationNumber(STR);

		assertThat(registrationNumber, equalTo("VOLKSWAGEN"));
		verify(csvFileService, times(1)).getRegistrationNumber(anyString());
		verifyNoMoreInteractions(csvFileService);
	}

	@Test
	public void testGetVehicleColour() throws IOException {
		when(csvFileService.getRegistrationNumber(anyString())).thenReturn(getVehicleColour(STR));
		final String registrationNumber = csvFileService.getRegistrationNumber(STR);

		assertThat(registrationNumber, equalTo("BLACK"));
		verify(csvFileService, times(1)).getRegistrationNumber(anyString());
		verifyNoMoreInteractions(csvFileService);
	}

	private String getVehicleMake(final String str) {
		return str.split(REGEX)[1];
	}

	private String getVehicleColour(final String str) {
		return str.split(REGEX)[2];
	}

	private String getVehicleRegistrationNumber(final String str) {
		return str.split(REGEX)[0];
	}

}
