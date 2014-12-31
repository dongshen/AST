package com.ast.Util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;

public class FileUtil {
	

	private static final Logger LOG = Logger.getLogger(FileUtil.class);
	
	private static final String DEMO_FILE_PATH = "demofile";

	public static char[] convertFileToArray(String javaFilePath) throws IOException {
		String fileName = DEMO_FILE_PATH + File.separator + javaFilePath;
		
		File f = new File(fileName);
		LOG.debug(f.getCanonicalFile());
		
		byte[] input = null;
		BufferedInputStream bufferedInputStream = null;
		try {
			bufferedInputStream = new BufferedInputStream(new FileInputStream(
					fileName));
			input = new byte[bufferedInputStream.available()];
			bufferedInputStream.read(input);
			bufferedInputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedInputStream != null) {
				try {
					bufferedInputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return new String(input).toCharArray();
	}
}
