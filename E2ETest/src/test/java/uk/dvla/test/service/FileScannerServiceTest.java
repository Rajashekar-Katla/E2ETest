package uk.dvla.test.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static uk.dvla.test.service.ServiceTestData.prepareData;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import uk.dvla.test.model.FileInformation;

public class FileScannerServiceTest {

	private FileScannerService fileScannerService;

	@Before
	public void setUp() {
		fileScannerService = Mockito.mock(FileScannerService.class);
	}

	@Test
	public void testLoadFileInformationFromDirectory() {
		when(fileScannerService.loadFileInformationFromDirectory()).thenReturn(prepareData());
		final List<FileInformation> fileInfoList = fileScannerService.loadFileInformationFromDirectory();

		assertThat(fileInfoList, not(equalTo(null)));
		assertThat(fileInfoList, not(fileInfoList.isEmpty()));
		assertThat(fileInfoList.size(), equalTo(3));

	}

}
