package uk.dvla.test.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import uk.dvla.test.model.FileInformation;
import uk.dvla.test.service.FileService;

/**
 * This class is used to process csv and excel files. 
 * @author Raj
 *
 */
public class FileServiceImpl implements FileService {

	private List<FileInformation> fileList;

	public FileServiceImpl(final List<FileInformation> fileList) {
		this.fileList = fileList;
	}

	public List<FileInformation> getAllFiles() {
		if (checkNotNull(fileList)) {
			return fileList;
		} else {
			return Collections.emptyList();
		}
	}

	public List<FileInformation> getSupportedMIMETypeFiles(final Set<String> fileMIMETypes) {
		if (checkNotNull(fileList)) {
			final Predicate<FileInformation> filter = file -> {
				if (checkNotNull(fileMIMETypes)) {
					if (fileMIMETypes.contains(file.getFileMIMEType())) {
						return true;
					} else {
						return false;
					}
				}
				return false;

			};
			return fileList.stream().filter(filter).collect(Collectors.toList());
		}
		return Collections.emptyList();

	}

	private boolean checkNotNull(final Object fileList) {
		return Optional.ofNullable(fileList).isPresent();
	}

}
