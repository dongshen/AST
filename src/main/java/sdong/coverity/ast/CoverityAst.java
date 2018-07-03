package sdong.coverity.ast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoverityAst {

	String fileName;
	DefinitionType type;
	String className;

	int fromLine;
	int fromColumn;
	int toLine;
	int toColumn;

	List<String> contentForDefinition;
	List<String> contentForDebug;

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

	public enum DefinitionType {
		CLASS("class"), FUNCTION("function"), GLOBAL("global");

		private String type;

		private DefinitionType(String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
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
}
