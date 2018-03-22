package uk.dvla.test.service.impl;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import uk.dvla.test.service.CsvFileService;
/**
 * This class is used to process CSV file records. 
 * @author Raj
 *
 */
public class CsvFileServiceImpl implements CsvFileService {

	private static final String EMPTY = "";
	private static final String REGEX = ";";

	private CSVParser csvParser;

	public CsvFileServiceImpl(final Path path) throws IOException {
		if (checkNotNull(path)) {
			final Reader reader = Files.newBufferedReader(path);
			this.csvParser = new CSVParser(reader,
					CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
		}

	}

	public List<CSVRecord> getRecords() throws IOException {
		if (checkNotNull(csvParser)) {
			return csvParser.getRecords();
		} else {
			return Collections.emptyList();
		}

	}

	public String getValue(CSVRecord record) {
		if (checkNotNull(record)) {
			return record.iterator().next();
		} else {
			return EMPTY;
		}
	}

	public String getRegistrationNumber(final String str) {
		if (checkNotNull(str)) {
			return str.split(REGEX)[0];
		} else {
			return EMPTY;
		}
	}

	public String getMake(final String str) throws IOException {
		if (checkNotNull(str)) {
			return str.split(REGEX)[1];
		} else {
			return EMPTY;
		}
	}

	public String getColour(final String str) throws IOException {
		if (checkNotNull(str)) {
			return str.split(REGEX)[2];
		} else {
			return EMPTY;
		}
	}

	private boolean checkNotNull(Object obj) {
		return Optional.ofNullable(obj).isPresent();
	}

}
