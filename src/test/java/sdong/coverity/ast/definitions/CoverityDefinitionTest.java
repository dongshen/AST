package sdong.coverity.ast.definitions;

import static org.junit.Assert.*;

import org.junit.Test;

import sdong.coverity.ast.definitions.CoverityDefinition.DefinitionType;

public class CoverityDefinitionTest {

	@Test
	public void testSetType() {
		CoverityDefinition definition = new CoverityDefinition();
		definition.setType(DefinitionType.CLASS);
		assertEquals(DefinitionType.CLASS, definition.getType());
	}

}
