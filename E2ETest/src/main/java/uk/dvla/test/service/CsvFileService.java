package uk.dvla.test.service;

import java.io.IOException;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

public interface CsvFileService {
	
	public String getMake(final String str) throws IOException;

	public String getColour(final String str) throws IOException;

	public List<CSVRecord> getRecords() throws IOException;
	
	public String getValue(CSVRecord record) ;
	
	public String getRegistrationNumber(final String str);

}
