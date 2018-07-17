package sdong.coverity.ast;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class AST extends ASTNode {

	private static final Class[] AST_CLASS = new Class[] { AST.class };
	
	private final Object[] THIS_AST= new Object[] {this};

	public ASTNode createInstance(String nodeType) {
		// nodeClassForType throws IllegalArgumentException if nodeType is bogus
		Class nodeClass = ASTNode.nodeClassForType(nodeType);
		return createInstance(nodeClass);
	}

	public ASTNode createInstance(Class nodeClass) {
		if (nodeClass == null) {
			throw new IllegalArgumentException();
		}
		try {
			// invoke constructor with signature Foo(AST)
			Constructor c = nodeClass.getDeclaredConstructor(AST_CLASS);
			Object result = c.newInstance(this.THIS_AST);
			return (ASTNode) result;
		} catch (NoSuchMethodException e) {
			// all AST node classes have a Foo(AST) constructor
			// therefore nodeClass is not legit
			throw new IllegalArgumentException(e);
		} catch (InstantiationException e) {
			// all concrete AST node classes can be instantiated
			// therefore nodeClass is not legit
			throw new IllegalArgumentException(e);
		} catch (IllegalAccessException e) {
			// all AST node classes have an accessible Foo(AST) constructor
			// therefore nodeClass is not legit
			throw new IllegalArgumentException(e);
		} catch (InvocationTargetException e) {
			// concrete AST node classes do not die in the constructor
			// therefore nodeClass is not legit
			throw new IllegalArgumentException(e.getCause());
		}
	}

}
