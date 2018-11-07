package sdong.coverity.emit.sqlite;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class CoverityEmitFileInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -903164678002753533L;

	private int fileContentsId;
	private int fileNameId;
	private String fileName;
	private int contentSize;
	private int blankLines;
	private int codeLines;
	private int commentLines;
	private int inlineCommentLines;
	private byte[] compressContents;
	private List<String> sourceCode;
	private String contents;

	public int getFileContentsId() {
		return fileContentsId;
	}

	public void setFileContentsId(int fileContentsId) {
		this.fileContentsId = fileContentsId;
	}

	public int getFileNameId() {
		return fileNameId;
	}

	public void setFileNameId(int fileNameId) {
		this.fileNameId = fileNameId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getContentSize() {
		return contentSize;
	}

	public void setContentSize(int contentSize) {
		this.contentSize = contentSize;
	}

	public int getBlankLines() {
		return blankLines;
	}

	public void setBlankLines(int blankLines) {
		this.blankLines = blankLines;
	}

	public int getCodeLines() {
		return codeLines;
	}

	public void setCodeLines(int codeLines) {
		this.codeLines = codeLines;
	}

	public int getCommentLines() {
		return commentLines;
	}

	public void setCommentLines(int commentLines) {
		this.commentLines = commentLines;
	}

	public int getInlineCommentLines() {
		return inlineCommentLines;
	}

	public void setInlineCommentLines(int inlineCommentLines) {
		this.inlineCommentLines = inlineCommentLines;
	}

	public byte[] getCompressContents() {
		return compressContents;
	}

	public void setCompressContents(byte[] compressContents) {
		this.compressContents = compressContents;
	}

	public List<String> getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(List<String> sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	@Override
	public String toString() {
		return "CoverityEmitFileInfo [fileContentsId=" + fileContentsId + ", fileNameId=" + fileNameId + ", fileName="
				+ fileName + ", contentSize=" + contentSize + ", blankLines=" + blankLines + ", codeLines=" + codeLines
				+ ", commentLines=" + commentLines + ", inlineCommentLines=" + inlineCommentLines
				+ ", compressContents=" + Arrays.toString(compressContents) + ", sourceCode=" + sourceCode
				+ ", contents=" + contents + "]";
	}

}
