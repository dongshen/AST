package com.ast.parse;

import java.io.IOException;

import org.junit.Test;

import com.ast.Util.FileUtil;

public class ParserTest {

	@Test
	public void testParseDemoVisitor() {
		String input = "Test.java";

		char[] output = null;
		try {
			output = FileUtil.convertFileToArray(input);

			Parser.parseDemoVisitor(output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
