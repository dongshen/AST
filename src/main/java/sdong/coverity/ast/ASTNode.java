package sdong.coverity.ast;

import java.util.HashMap;
import java.util.Map;

public abstract class ASTNode {

	String type;

	String id;

	String name;

	Loc fromLoc;
	Loc toLoc;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static Class nodeClassForType(String nodeType) {
		switch (NodeType.get(nodeType)) {
		case TF_FUNC:
			return TF_func.class;
		}
		throw new IllegalArgumentException();
	}

	public enum NodeType {
		TF_FUNC("TF_Funct"), FUNCTION("function"), GLOBAL("global");

		private String nodeType;

		private NodeType(String nodeType) {
			this.nodeType = nodeType;
		}

		public String getNodeType() {
			return nodeType;
		}

		public void setNodeType(String nodeType) {
			this.nodeType = nodeType;
		}

		private static final Map<String, NodeType> lookup = new HashMap<String, NodeType>();

		static {
			for (NodeType type : NodeType.values()) {
				lookup.put(type.getNodeType(), type);
			}
		}

		public static NodeType get(String nodeType) {
			return lookup.get(nodeType);
		}

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Loc getFromLoc() {
		return fromLoc;
	}

	public void setFromLoc(Loc fromLoc) {
		this.fromLoc = fromLoc;
	}

	public Loc getToLoc() {
		return toLoc;
	}

	public void setToLoc(Loc toLoc) {
		this.toLoc = toLoc;
	}

}
