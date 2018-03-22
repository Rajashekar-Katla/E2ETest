package uk.dvla.test.service;

import java.util.List;

import uk.dvla.test.model.FileInformation;


public interface FileScannerService {
	List<FileInformation> loadFileInformationFromDirectory();
}
