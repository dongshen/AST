package sdong.coverity.ast.definitions;

import static org.junit.Assert.*;

import org.junit.Test;

import sdong.coverity.ast.CoverityAst;
import sdong.coverity.ast.CoverityAst.DefinitionType;

public class CoverityAstTest {

	@Test
	public void testSetType() {
		CoverityAst definition = new CoverityAst();
		definition.setType(DefinitionType.CLASS);
		assertEquals(DefinitionType.CLASS, definition.getType());
	}

}
