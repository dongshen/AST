package sdong.coverity.ast.Util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import sdong.common.exception.SdongException;
import sdong.common.utils.FileUtil;
import sdong.coverity.ast.CoverityAstFunction;
import sdong.coverity.ast.parse.CoverityAstParse;

public class AstUtilTest {

	@Test
	public void testRemoveDEFINED_IN_TU() {
		String fileName = "input/coverityAST/BenchmarkTest00001_definition";
		List<String> astContent;
		try {
			astContent = FileUtil.readFileToStringList(fileName);

			int total_lines = 114;
			assertEquals(total_lines, astContent.size());

			List<String> astContent_without_DEFINED_IN_TU = AstUtil.removeDEFINED_IN_TU(astContent);
			assertEquals(total_lines - 8, astContent_without_DEFINED_IN_TU.size());
		} catch (SdongException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testRemoveDEFINED_IN_TU_for_debug() {
		String fileName = "input/coverityAST/BenchmarkTest00001_debug";
		List<String> astContent;
		try {
			astContent = FileUtil.readFileToStringList(fileName);

			int total_lines = 3665;
			assertEquals(total_lines, astContent.size());

			List<String> astContent_without_DEFINED_IN_TU = AstUtil.removeDEFINED_IN_TU(astContent);
			assertEquals(total_lines - 4, astContent_without_DEFINED_IN_TU.size());
		} catch (SdongException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetFunctionPerlineNum() {
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

		assertEquals(0, AstUtil.getFunctionPerlineNum(list, 25).getFromLine());
		assertEquals(50, AstUtil.getFunctionPerlineNum(list, 75).getFromLine());
		assertEquals(100, AstUtil.getFunctionPerlineNum(list, 100).getFromLine());
		assertEquals(150, AstUtil.getFunctionPerlineNum(list, 200).getFromLine());
	}

	@Test
	public void testSplitLineToToken() {
		String line = "void org.owasp.benchmark.testcode.BenchmarkTest00001.doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws java.io.IOException, javax.servlet.ServletException {";
		String[] tokens = AstUtil.splitLineToToken(line);
		assertEquals(9, tokens.length);
		assertEquals("void", tokens[0]);
		assertEquals("org.owasp.benchmark.testcode.BenchmarkTest00001.doPost", tokens[1]);

		assertEquals("javax.servlet.http.HttpServletRequest", tokens[2]);
		assertEquals("request", tokens[3]);
		assertEquals("javax.servlet.http.HttpServletResponse", tokens[4]);
		assertEquals("response", tokens[5]);

		assertEquals("throws", tokens[6]);
		assertEquals("java.io.IOException", tokens[7]);
		assertEquals("javax.servlet.ServletException", tokens[8]);

	}

	@Test
	public void testGetVariableTypeFromDefinition() {
		String fileName = "input/coverityAST/BenchmarkTest00001_definition";
		List<String> astContent;
		int lineNum = 43; // javax.servlet.http.Cookie[] cookies = request.getCookies();
		String variable;
		try {
			// get Ast
			astContent = FileUtil.readFileToStringList(fileName);
			CoverityAstParse parse = new CoverityAstParse();
			List<CoverityAstFunction> functionList = parse.parse(astContent);

			CoverityAstFunction fun = AstUtil.getFunctionPerlineNum(functionList, lineNum);

			variable = "cookies";
			String type = AstUtil.getVariableTypeFromDefinition(fun, null, variable);
			assertEquals("javax.servlet.http.Cookie[]", type);

			variable = "request";
			type = AstUtil.getVariableTypeFromDefinition(fun, null, variable);
			assertEquals("javax.servlet.http.HttpServletRequest", type);
			
			//global
			variable = "org.owasp.benchmark.testcode.BenchmarkTest00001.serialVersionUID";
			type = AstUtil.getVariableTypeFromDefinition(fun, AstUtil.getGlobleVariableList(functionList), variable);
			assertEquals("long", type);

		} catch (SdongException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testGetGlobleVariableList() {
		String fileName = "input/coverityAST/BenchmarkTest00001_definition";
		List<String> astContent;
		try {
			// get Ast
			astContent = FileUtil.readFileToStringList(fileName);
			CoverityAstParse parse = new CoverityAstParse();
			List<CoverityAstFunction> functionList = parse.parse(astContent);

			List<CoverityAstFunction> globalList = AstUtil.getGlobleVariableList(functionList);

			assertEquals(1, globalList.size());
			assertEquals("org.owasp.benchmark.testcode.BenchmarkTest00001.serialVersionUID",
					globalList.get(0).getClassName());

		} catch (SdongException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSplitTUAst() {
		String fileName = "input/coverityAST/270.271";
		try {
			List<String> astContent = FileUtil.readFileToStringList(fileName);

			Map<String, List<String>> tuList = AstUtil.splitTUAst(astContent);
			assertEquals(2, tuList.size());
			assertEquals(114, tuList.get("270").size());
			assertEquals(80, tuList.get("271").size());
		} catch (SdongException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
