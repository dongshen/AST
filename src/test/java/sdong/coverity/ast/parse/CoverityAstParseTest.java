package sdong.coverity.ast.parse;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdong.common.exception.SdongException;
import sdong.coverity.ast.CoverityAst;
import sdong.coverity.ast.CoverityAst.DefinitionType;

public class CoverityAstParseTest {

	private static final Logger logger = LoggerFactory.getLogger(CoverityAstParseTest.class);

	@Test
	public void testParseForDefinition() {
		String fileName = "input/coverityAST/BenchmarkTest00001_definition_update";
		try {
			List<String> contentList = readASTDefinitionFile(fileName);
			CoverityAstParse parse = new CoverityAstParse();

			List<CoverityAst> deflist = parse.parse(contentList);

			// check total
			assertEquals(6, deflist.size());

			// check first one
			CoverityAst def = deflist.get(0);
			assertEquals("D:/git/benchmark/src/main/java/org/owasp/benchmark/testcode/BenchmarkTest00001.java",
					def.getFileName());
			assertEquals(CoverityAst.DefinitionType.CLASS, def.getType());
			assertEquals("org.owasp.benchmark.testcode.BenchmarkTest00001", def.getClassName());
			assertEquals(30, def.getFromLine());
			assertEquals(14, def.getFromColumn());
			assertEquals(30, def.getToLine());
			assertEquals(31, def.getToColumn());
			assertEquals(8, def.getContentForDefinition().size());

			// check last one
			def = deflist.get(deflist.size() - 1);
			assertEquals("D:/git/benchmark/src/main/java/org/owasp/benchmark/testcode/BenchmarkTest00001.java",
					def.getFileName());
			assertEquals(CoverityAst.DefinitionType.FUNCTION, def.getType());
			assertEquals(
					"org.owasp.benchmark.testcode.BenchmarkTest00001.doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)void",
					def.getClassName());
			assertEquals(40, def.getFromLine());
			assertEquals(14, def.getFromColumn());
			assertEquals(40, def.getToLine());
			assertEquals(114, def.getToColumn());
			assertEquals(36, def.getContentForDefinition().size());

			// check global
			def = deflist.get(3);
			assertEquals("D:/git/benchmark/src/main/java/org/owasp/benchmark/testcode/BenchmarkTest00001.java",
					def.getFileName());
			assertEquals(CoverityAst.DefinitionType.GLOBAL, def.getType());
			assertEquals("org.owasp.benchmark.testcode.BenchmarkTest00001.serialVersionUID", def.getClassName());
			assertEquals(32, def.getFromLine());
			assertEquals(28, def.getFromColumn());
			assertEquals(32, def.getToLine());
			assertEquals(43, def.getToColumn());
			assertEquals(1, def.getContentForDefinition().size());

		} catch (SdongException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testParseForDebug() {
		String fileName = "input/coverityAST/BenchmarkTest00001_debug_update";
		try {
			List<String> contentList = readASTDefinitionFile(fileName);
			CoverityAstParse parse = new CoverityAstParse();

			List<CoverityAst> deflist = parse.parse(contentList);

			// check total
			assertEquals(4, deflist.size());

			// check first one
			CoverityAst def = deflist.get(0);
			assertEquals("D:/git/benchmark/src/main/java/org/owasp/benchmark/testcode/BenchmarkTest00001.java",
					def.getFileName());
			assertEquals(CoverityAst.DefinitionType.FUNCTION, def.getType());
			assertEquals("org.owasp.benchmark.testcode.BenchmarkTest00001.<clinit>()void", def.getClassName());
			assertEquals(30, def.getFromLine());
			assertEquals(14, def.getFromColumn());
			assertEquals(30, def.getToLine());
			assertEquals(31, def.getToColumn());
			assertEquals(91, def.getContentForDefinition().size());

			// check last one
			def = deflist.get(deflist.size() - 1);
			assertEquals("D:/git/benchmark/src/main/java/org/owasp/benchmark/testcode/BenchmarkTest00001.java",
					def.getFileName());
			assertEquals(CoverityAst.DefinitionType.FUNCTION, def.getType());
			assertEquals(
					"org.owasp.benchmark.testcode.BenchmarkTest00001.doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)void",
					def.getClassName());
			assertEquals(40, def.getFromLine());
			assertEquals(14, def.getFromColumn());
			assertEquals(40, def.getToLine());
			assertEquals(114, def.getToColumn());
			assertEquals(2897, def.getContentForDefinition().size());

		} catch (SdongException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testSetMatchingType() {
		CoverityAstParse parse = new CoverityAstParse();
		String line = " * Matching class: java.security.Provider[]";
		CoverityAst definition = new CoverityAst();
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
		CoverityAstParse parse = new CoverityAstParse();
		String line = " *   <unknown>";
		CoverityAst definition = new CoverityAst();
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
		String fileName = "input/coverityAST/BenchmarkTest00001_definition";
		List<String> astContent = readASTDefinitionFile(fileName);
		int total_lines = 114;
		assertEquals(total_lines, astContent.size());
		
		List<String> astContent_without_DEFINED_IN_TU = CoverityAstParse.removeDEFINED_IN_TU(astContent);
		assertEquals(total_lines - 8, astContent_without_DEFINED_IN_TU.size());
	}

	@Test
	public void testRemoveDEFINED_IN_TU_for_debug() {
		String fileName = "input/coverityAST/BenchmarkTest00001_debug";
		List<String> astContent = readASTDefinitionFile(fileName);
		int total_lines = 3665;
		assertEquals(total_lines, astContent.size());
		CoverityAstParse parse = new CoverityAstParse();
		List<String> astContent_without_DEFINED_IN_TU = parse.removeDEFINED_IN_TU(astContent);
		assertEquals(total_lines - 4, astContent_without_DEFINED_IN_TU.size());
	}

	@Test
	public void testSplitTUAst() {
		String fileName = "input/coverityAST/270.271";
		List<String> astContent = readASTDefinitionFile(fileName);
		try {
			Map<Integer, List<String>> tuList = CoverityAstParse.splitTUAst(astContent);
			assertEquals(2, tuList.size());
			assertEquals(114, tuList.get(270).size());
			assertEquals(80, tuList.get(271).size());
		} catch (SdongException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
