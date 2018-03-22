package uk.dvla.test.mime.types;

/**
 * This enum is used to identify file MIMETypes.
 * 
 * @author Raj
 *
 */
public enum MIMEType {

	CSV("text/csv"), 
	XLS("application/vnd.ms-excel"), 
	XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"), 
	OCTET_STREAM("application/octet-stream");

	final String mimeType;

	MIMEType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getMimeType() {
		return mimeType;
	}

}
