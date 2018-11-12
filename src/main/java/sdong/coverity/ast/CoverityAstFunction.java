package sdong.coverity.ast;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoverityAstFunction implements Serializable, Comparable<CoverityAstFunction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1122671580213079468L;

	String fileName;
	String fileNameHash;

	DefinitionType type;
	String className;

	int fromLine;
	int fromColumn;
	int toLine;
	int toColumn;

	List<String> contentForDefinition;
	List<String> contentForDebug;

	byte[] compressDefinition;
	byte[] compressDebug;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getFromLine() {
		return fromLine;
	}

	public void setFromLine(int fromLine) {
		this.fromLine = fromLine;
	}

	public int getToLine() {
		return toLine;
	}

	public void setToLine(int toLine) {
		this.toLine = toLine;
	}

	public int getFromColumn() {
		return fromColumn;
	}

	public void setFromColumn(int fromColumn) {
		this.fromColumn = fromColumn;
	}

	public int getToColumn() {
		return toColumn;
	}

	public void setToColumn(int toColumn) {
		this.toColumn = toColumn;
	}

	public DefinitionType getType() {
		return type;
	}

	public void setType(DefinitionType type) {
		this.type = type;
	}

	public List<String> getContentForDefinition() {
		return contentForDefinition;
	}

	public void setContentForDefinition(List<String> content) {
		this.contentForDefinition = content;
	}

	public List<String> getContentForDebug() {
		return contentForDebug;
	}

	public void setContentForDebug(List<String> contentForDebug) {
		this.contentForDebug = contentForDebug;
	}

	public String getFileNameHash() {
		return fileNameHash;
	}

	public void setFileNameHash(String fileNameHash) {
		this.fileNameHash = fileNameHash;
	}

	public byte[] getCompressDefinition() {
		return compressDefinition;
	}

	public void setCompressDefinition(byte[] compressDefinition) {
		this.compressDefinition = compressDefinition;
	}

	public byte[] getCompressDebug() {
		return compressDebug;
	}

	public void setCompressDebug(byte[] compressDebug) {
		this.compressDebug = compressDebug;
	}

	public enum DefinitionType {
		CLASS("class", 1), FUNCTION("function", 2), GLOBAL("global", 3), ENUM("enum", 4);

		private String type;
		private int index;

		private DefinitionType(String type, int index) {
			this.type = type;
			this.index = index;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		private static final Map<String, DefinitionType> lookup = new HashMap<String, DefinitionType>();

		static {
			for (DefinitionType type : DefinitionType.values()) {
				lookup.put(type.getType(), type);
			}
		}

		public static DefinitionType get(String type) {
			return lookup.get(type);
		}

	}

	// sort from small to big
	public int compareTo(CoverityAstFunction comp) {
		if (this.getFromLine() > comp.getFromLine()) {
			return 1;
		} else if (this.getFromLine() == comp.getFromLine()) {
			return 0;
		} else {
			return -1;
		}

	}
}
