package com.ast.Util;

import java.io.IOException;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.junit.Test;

public class FileUtilTest extends TestCase {
	static Logger logger = Logger.getLogger(FileUtilTest.class);

	@Test
	public void testConvertFileToArray() {
		String input = "ClassDemo.java";
		char[] output = null;
		try {
			output = FileUtil.convertFileToArray(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// assertEquals(output);
		assertNotNull(output);

	}

}
