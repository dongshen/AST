package sdong.coverity.ast.parse;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdong.common.exception.SdongException;
import sdong.coverity.ast.definitions.CoverityDefinition;
import sdong.coverity.ast.definitions.CoverityDefinition.DefinitionType;

public class CoverityASTDefinitionsParseTest {

	private static final Logger logger = LoggerFactory.getLogger(CoverityASTDefinitionsParseTest.class);

	@Test
	public void testParse() {
		String fileName = "input/coverityAST/270";
		try {
			List<String> content = readASTDefinitionFile(fileName);
			CoverityASTDefinitionsParse parse = new CoverityASTDefinitionsParse();
			List<String> contentList = parse.removeDEFINED_IN_TU(content);

			List<CoverityDefinition> deflist = parse.parse(contentList);

			//check total
			assertEquals(6, deflist.size());
			
			//check first one
			CoverityDefinition def =  deflist.get(0);
			assertEquals("D:/git/benchmark/src/main/java/org/owasp/benchmark/testcode/BenchmarkTest00001.java", def.getFileName());
			assertEquals(CoverityDefinition.DefinitionType.CLASS, def.getType());
			assertEquals("org.owasp.benchmark.testcode.BenchmarkTest00001", def.getClassName());
			assertEquals(30, def.getFromLine());
			assertEquals(14, def.getFromColumn());
			assertEquals(30, def.getToLine());
			assertEquals(31, def.getToColumn());
			assertEquals(8, def.getContent().size());
			
			//check last one
			def =  deflist.get(deflist.size()-1);
			assertEquals("D:/git/benchmark/src/main/java/org/owasp/benchmark/testcode/BenchmarkTest00001.java", def.getFileName());
			assertEquals(CoverityDefinition.DefinitionType.FUNCTION, def.getType());
			assertEquals("org.owasp.benchmark.testcode.BenchmarkTest00001.doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)void", def.getClassName());
			assertEquals(40, def.getFromLine());
			assertEquals(14, def.getFromColumn());
			assertEquals(40, def.getToLine());
			assertEquals(114, def.getToColumn());
			
			assertEquals(36, def.getContent().size());

		} catch (SdongException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testSetMatchingType() {
		CoverityASTDefinitionsParse parse = new CoverityASTDefinitionsParse();
		String line = " * Matching class: java.security.Provider[]";
		CoverityDefinition definition = new CoverityDefinition();
		try {
			parse.setMatchingType(line, definition);

			assertEquals(DefinitionType.CLASS, definition.getType());
			assertEquals("java.security.Provider[]", definition.getClassName());
		} catch (SdongException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testSetDeclaredAt() {
		CoverityASTDefinitionsParse parse = new CoverityASTDefinitionsParse();
		String line = " *   <unknown>";
		CoverityDefinition definition = new CoverityDefinition();
		try {
			parse.setDeclaredAt(line, definition);

			assertEquals(null, definition.getFileName());
			assertEquals(0, definition.getFromLine());
			assertEquals(0, definition.getToLine());


			line = " *   D:/git/benchmark/src/main/java/org/owasp/benchmark/testcode/BenchmarkTest00001.java:30:14-D:/git/benchmark/src/main/java/org/owasp/benchmark/testcode/BenchmarkTest00001.java:30:31";
			parse.setDeclaredAt(line, definition);
			assertEquals("D:/git/benchmark/src/main/java/org/owasp/benchmark/testcode/BenchmarkTest00001.java",
					definition.getFileName());
			assertEquals(30, definition.getFromLine());
			assertEquals(14, definition.getFromColumn());
			assertEquals(30, definition.getToLine());
			assertEquals(31, definition.getToColumn());


		} catch (SdongException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testRemoveDEFINED_IN_TU() {
		String fileName = "input/coverityAST/270";
		List<String> astContent = readASTDefinitionFile(fileName);
		assertEquals(114, astContent.size());
		CoverityASTDefinitionsParse parse = new CoverityASTDefinitionsParse();
		List<String> astContent_without_DEFINED_IN_TU = parse.removeDEFINED_IN_TU(astContent);
		assertEquals(114 - 8, astContent_without_DEFINED_IN_TU.size());
	}

	private List<String> readASTDefinitionFile(String fileName) {
		List<String> astContent = null;
		try {
			Path path = Paths.get(fileName);
			astContent = Files.readAllLines(path);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return astContent;
	}
}
