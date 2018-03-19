package uk.dvla.test.service;

import java.util.List;
import java.util.Set;

import uk.dvla.test.model.FileInformation;

public interface FileService {

	List<FileInformation> getAllFiles();
	List<FileInformation> getSupportedMIMETypeFiles(final Set<String> fileMIMETypes);
}
