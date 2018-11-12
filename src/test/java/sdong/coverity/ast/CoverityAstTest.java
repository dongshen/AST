package sdong.coverity.ast;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import sdong.coverity.ast.CoverityAstFunction.DefinitionType;

public class CoverityAstTest {

	@Test
	public void testSetType() {
		CoverityAstFunction definition = new CoverityAstFunction();
		definition.setType(DefinitionType.CLASS);
		assertEquals(DefinitionType.CLASS, definition.getType());
	}

	@Test
	public void testSort() {
		List<CoverityAstFunction> list = new ArrayList<CoverityAstFunction>();

		CoverityAstFunction definition = new CoverityAstFunction();
		definition.setFromLine(150);
		list.add(definition);

		definition = new CoverityAstFunction();
		definition.setFromLine(100);
		definition.setFromColumn(1);
		list.add(definition);

		definition = new CoverityAstFunction();
		definition.setFromLine(50);
		list.add(definition);

		definition = new CoverityAstFunction();
		definition.setFromLine(100);
		definition.setFromColumn(2);
		list.add(definition);

		definition = new CoverityAstFunction();
		definition.setFromLine(0);
		list.add(definition);

		Collections.sort(list);

		assertEquals(0, list.get(0).getFromLine());
		assertEquals(50, list.get(1).getFromLine());
		assertEquals(100, list.get(2).getFromLine());
		assertEquals(2, list.get(2).getFromColumn());
		assertEquals(100, list.get(3).getFromLine());
		assertEquals(1, list.get(3).getFromColumn());
		assertEquals(150, list.get(4).getFromLine());

	}

}
