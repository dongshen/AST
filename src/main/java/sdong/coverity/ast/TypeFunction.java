package sdong.coverity.ast;

import java.util.List;

public class TypeFunction extends Type {
	TypeScalar returnType;

	List<TypePointer> parameters;

	public TypeScalar getReturnType() {
		return returnType;
	}

	public void setReturnType(TypeScalar returnType) {
		this.returnType = returnType;
	}

	public List<TypePointer> getParameters() {
		return parameters;
	}

	public void setParameters(List<TypePointer> parameters) {
		this.parameters = parameters;
	}

}
