package uk.dvla.test.service.impl;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.io.FilenameUtils;

import uk.dvla.test.model.FileInformation;
import uk.dvla.test.model.FileInformation.FileInformationBuilder;
import uk.dvla.test.service.FileScannerService;

/**
 * This class used to scan files from a given directory and populates file information.
 * @author Raj
 *
 */
public class FileScannerServiceImpl implements FileScannerService {

	final MimetypesFileTypeMap mimeFileType = new MimetypesFileTypeMap();

	private String directoryPath = null;

	public FileScannerServiceImpl(final String directoryPath) {
		this.directoryPath = directoryPath;
	}

	@Override
	public List<FileInformation> loadFileInformationFromDirectory() {

		if (checkNotNull(directoryPath)) {

			final File directory = new File(directoryPath);
			final File[] files = directory.listFiles();

			if (checkNotNull(files)) {
				final List<FileInformation> fileInformationList = Stream.of(files).map(file -> {
					final String name = file.getName();
					final long size = file.length();
					final String type = mimeFileType.getContentType(file);
					final String fileExt = FilenameUtils.getExtension(name);

					return new FileInformationBuilder().withFileName(name).withFileSize(size).withfileMIMEType(type)
							.withFileExtension(fileExt).build();
				}).collect(Collectors.toList());

				return fileInformationList;
			}
		}
		return Collections.emptyList();

	}

	private boolean checkNotNull(final Object object) {
		final Optional<Object> directoryPathOptional = Optional.ofNullable(object);
		return directoryPathOptional.isPresent();
	}

}
