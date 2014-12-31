package com.ast.Util;

import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.junit.Test;

public class FileUtilTest extends TestCase {
	static Logger logger = Logger.getLogger(FileUtilTest.class);

	@Test
	public void testConvertFileToArray() throws IOException {
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

	@Test
	public void testGetFilesInDir() {

		List<String> list;
		try {
			list = FileUtil.getFilesInDir();
			logger.debug(list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
