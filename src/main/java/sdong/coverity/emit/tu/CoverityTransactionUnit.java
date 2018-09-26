package sdong.coverity.emit.tu;

import java.io.Serializable;

public class CoverityTransactionUnit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7398807250101034421L;

	int id;
	String primaryFilename;
	int primaryFileSizeInBytes;
	String language;
	String userLanguage;
	boolean hasASTs;
	boolean isFailure;
	boolean isCreateDEGPCH;
	boolean hadRecoverableErrors;
	boolean isFromBootClassPath;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPrimaryFilename() {
		return primaryFilename;
	}

	public void setPrimaryFilename(String primaryFilename) {
		this.primaryFilename = primaryFilename;
	}

	public int getPrimaryFileSizeInBytes() {
		return primaryFileSizeInBytes;
	}

	public void setPrimaryFileSizeInBytes(int primaryFileSizeInBytes) {
		this.primaryFileSizeInBytes = primaryFileSizeInBytes;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getUserLanguage() {
		return userLanguage;
	}

	public void setUserLanguage(String userLanguage) {
		this.userLanguage = userLanguage;
	}

	public boolean isHasASTs() {
		return hasASTs;
	}

	public void setHasASTs(boolean hasASTs) {
		this.hasASTs = hasASTs;
	}

	public boolean isFailure() {
		return isFailure;
	}

	public void setFailure(boolean isFailure) {
		this.isFailure = isFailure;
	}

	public boolean isCreateDEGPCH() {
		return isCreateDEGPCH;
	}

	public void setCreateDEGPCH(boolean isCreateDEGPCH) {
		this.isCreateDEGPCH = isCreateDEGPCH;
	}

	public boolean isHadRecoverableErrors() {
		return hadRecoverableErrors;
	}

	public void setHadRecoverableErrors(boolean hadRecoverableErrors) {
		this.hadRecoverableErrors = hadRecoverableErrors;
	}

	public boolean isFromBootClassPath() {
		return isFromBootClassPath;
	}

	public void setFromBootClassPath(boolean isFromBootClassPath) {
		this.isFromBootClassPath = isFromBootClassPath;
	}

	public enum TuLanguage {
		JAVA_SOURCE("Java source"), JAVA_BYTECODE("Java bytecode");

		private String languageName;

		TuLanguage(String name) {
			this.languageName = name;
		}

		public String getLanguageName() {
			return languageName;
		}

		public void setLanguageName(String languageName) {
			this.languageName = languageName;
		}

		public static TuLanguage fromLanguageName(String languageName) {
			for (TuLanguage lang : TuLanguage.values()) {
				if (lang.getLanguageName().equals(languageName)) {
					return lang;
				}
			}
			return null;
		}

	}
}
