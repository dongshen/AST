package sdong.coverity.ast;

public class TypeClass extends Type {
	String dflags;// = {abstract <inferred>}
	String classKey;// = class

	public String getDflags() {
		return dflags;
	}

	public void setDflags(String dflags) {
		this.dflags = dflags;
	}

	public String getClassKey() {
		return classKey;
	}

	public void setClassKey(String classKey) {
		this.classKey = classKey;
	}

}
