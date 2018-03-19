package uk.dvla.test.model;

/**
 * This class holds file information. 
 * @author Raj
 *
 */
public final class FileInformation {
	
	private String fileName;
	private String fileMIMEType;
	private String fileExtension;
	private Long fileSize;

	public FileInformation(final FileInformationBuilder fileInformationBuilder) {
		this.fileName = fileInformationBuilder.fileName;
		this.fileMIMEType = fileInformationBuilder.fileMIMEType;
		this.fileExtension = fileInformationBuilder.fileExtension;
		this.fileSize = fileInformationBuilder.fileSize;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileMIMEType() {
		return fileMIMEType;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public static class FileInformationBuilder {
		private String fileName;
		private String fileMIMEType;
		private String fileExtension;
		private Long fileSize;

		public FileInformationBuilder withFileName(final String fileName) {
			this.fileName = fileName;
			return this;
		}

		public FileInformationBuilder withfileMIMEType(final String fileMIMEType) {
			this.fileMIMEType = fileMIMEType;
			return this;
		}

		public FileInformationBuilder withFileExtension(final String fileExtension) {
			this.fileExtension = fileExtension;
			return this;
		}

		public FileInformationBuilder withFileSize(final Long fileSize) {
			this.fileSize = fileSize;
			return this;
		}

		public FileInformation build() {
			return new FileInformation(this);
		}

	}

}
