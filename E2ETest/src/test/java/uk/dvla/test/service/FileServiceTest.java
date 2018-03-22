package uk.dvla.test.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anySet;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static uk.dvla.test.service.ServiceTestData.prepareData;
import static uk.dvla.test.service.ServiceTestData.randomLong;
import static uk.dvla.test.service.ServiceTestData.randomString;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import uk.dvla.test.mime.types.MIMEType;
import uk.dvla.test.model.FileInformation;
import uk.dvla.test.model.FileInformation.FileInformationBuilder;
import uk.dvla.test.service.impl.FileServiceImpl;

public class FileServiceTest {

	private FileService fileService;

	@Before
	public void setUp() {
		fileService = Mockito.mock(FileService.class);
	}

	@Test
	public void testGetAllFiles() {
		final FileInformation fileInformation = new FileInformationBuilder().withFileName(randomString())
				.withFileExtension(randomString()).withFileMIMEType(randomString()).withFileSize(randomLong()).build();
		final List<FileInformation> list = Arrays.asList(fileInformation);
		when(fileService.getAllFiles()).thenReturn(list);

		final List<FileInformation> fileList = fileService.getAllFiles();
		final FileInformation fileInfo = fileList.get(0);

		assertThat(list.size(), equalTo(fileList.size()));
		assertThat(fileInfo.getFileName(), equalTo(fileInformation.getFileName()));
		assertThat(fileInfo.getFileExtension(), equalTo(fileInformation.getFileExtension()));
		assertThat(fileInfo.getFileMIMEType(), equalTo(fileInformation.getFileMIMEType()));
		assertThat(fileInfo.getFileSize(), equalTo(fileInformation.getFileSize()));

		verify(fileService, times(1)).getAllFiles();
		verifyNoMoreInteractions(fileService);
	}

	@Test
	public void testGetSupportedMIMETypeFiles() {
		final List<FileInformation> fileInfoList = prepareData();
		final Set<String> mimeType = new HashSet<>();
		when(fileService.getSupportedMIMETypeFiles(anySet())).thenReturn(fileInfoList);

		final FileInformation actual = fileInfoList.get(0);
		final List<FileInformation> fileList = fileService.getSupportedMIMETypeFiles(mimeType);
		final FileInformation fileInfo = fileList.get(0);

		assertThat(fileInfoList.size(), equalTo(fileList.size()));
		assertThat(fileInfo.getFileName(), equalTo(actual.getFileName()));
		assertThat(fileInfo.getFileExtension(), equalTo(actual.getFileExtension()));
		assertThat(fileInfo.getFileMIMEType(), equalTo(actual.getFileMIMEType()));
		assertThat(fileInfo.getFileSize(), equalTo(actual.getFileSize()));

		verify(fileService, times(1)).getSupportedMIMETypeFiles(mimeType);
		verifyNoMoreInteractions(fileService);

	}

	@Test
	public void testGetSupportedMIMETypeFilesForCSV() {
		final List<FileInformation> fileInfoList = prepareData();
		final Set<String> mimeType = new HashSet<>();
		mimeType.add(MIMEType.CSV.getMimeType());
		FileService fileService = new FileServiceImpl(fileInfoList);

		final FileInformation actual = fileInfoList.get(0);
		final List<FileInformation> fileList = fileService.getSupportedMIMETypeFiles(mimeType);
		final FileInformation fileInfo = fileList.get(0);

		assertThat(fileList.size(), equalTo(2));
		assertThat(fileInfo.getFileName(), equalTo(actual.getFileName()));
		assertThat(fileInfo.getFileExtension(), equalTo(actual.getFileExtension()));
		assertThat(fileInfo.getFileMIMEType(), equalTo(actual.getFileMIMEType()));
		assertThat(fileInfo.getFileSize(), equalTo(actual.getFileSize()));

	}

	@Test
	public void testGetSupportedMIMETypeFilesForExcel() {
		final List<FileInformation> fileInfoList = prepareData();
		final Set<String> mimeType = new HashSet<>();
		mimeType.add(MIMEType.XLS.getMimeType());
		FileService fileService = new FileServiceImpl(fileInfoList);

		final FileInformation actual = fileInfoList.get(2);
		final List<FileInformation> fileList = fileService.getSupportedMIMETypeFiles(mimeType);
		final FileInformation fileInfo = fileList.get(0);

		assertThat(fileList.size(), equalTo(1));
		assertThat(fileInfo.getFileName(), equalTo(actual.getFileName()));
		assertThat(fileInfo.getFileExtension(), equalTo(actual.getFileExtension()));
		assertThat(fileInfo.getFileMIMEType(), equalTo(actual.getFileMIMEType()));
		assertThat(fileInfo.getFileSize(), equalTo(actual.getFileSize()));

	}

	@Test
	public void testGetSupportedMIMETypeFilesForCSVORExcelWithNull() {
		final List<FileInformation> fileInfoList = prepareData();
		final Set<String> mimeType = null;
		FileService fileService = new FileServiceImpl(fileInfoList);

		final List<FileInformation> fileList = fileService.getSupportedMIMETypeFiles(mimeType);

		assertThat(fileList, not(equalTo(null)));
		assertThat(fileList.isEmpty(), equalTo(true));
		assertThat(fileList.size(), equalTo(0));
	}

	@Test
	public void testGetAllFilesForCSVORExcelwithNull() {
		final List<FileInformation> fileInfoList = null;
		FileService fileService = new FileServiceImpl(fileInfoList);

		final List<FileInformation> fileList = fileService.getAllFiles();

		assertThat(fileList, not(equalTo(null)));
		assertThat(fileList.isEmpty(), equalTo(true));
		assertThat(fileList.size(), equalTo(0));

	}

}
