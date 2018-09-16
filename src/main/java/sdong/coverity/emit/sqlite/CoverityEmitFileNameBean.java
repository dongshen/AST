package sdong.coverity.emit.sqlite;

import java.io.Serializable;

public class CoverityEmitFileNameBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6738274528378476955L;

	private int fileNameId;
	private int parent;
	private String component;

	public int getFileNameId() {
		return fileNameId;
	}

	public void setFileNameId(int fileNameId) {
		this.fileNameId = fileNameId;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

}
