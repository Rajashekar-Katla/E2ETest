package uk.dvla.test.service;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;

import uk.dvla.test.mime.types.MIMEType;
import uk.dvla.test.model.FileInformation;
import uk.dvla.test.model.FileInformation.FileInformationBuilder;

public class ServiceTestData {
	public static String randomString() {
		int length = 10;
		boolean useLetters = true;
		boolean useNumbers = false;
		return RandomStringUtils.random(length, useLetters, useNumbers);
	}

	public static Long randomLong() {
		return RandomUtils.nextLong();
	}

	public static List<FileInformation> prepareData() {
		final FileInformation fileInformation1 = new FileInformationBuilder().withFileName(randomString())
				.withFileExtension(randomString()).withFileMIMEType(MIMEType.CSV.getMimeType())
				.withFileSize(randomLong()).build();
		final FileInformation fileInformation2 = new FileInformationBuilder().withFileName(randomString())
				.withFileExtension(randomString()).withFileMIMEType(MIMEType.CSV.getMimeType())
				.withFileSize(randomLong()).build();
		final FileInformation fileInformation3 = new FileInformationBuilder().withFileName(randomString())
				.withFileExtension(randomString()).withFileMIMEType(MIMEType.XLS.getMimeType())
				.withFileSize(randomLong()).build();
		final List<FileInformation> list = Arrays.asList(fileInformation1, fileInformation2, fileInformation3);

		return list;
	}
}
