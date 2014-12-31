package com.ast.Util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class FileUtil {

	private static final Logger LOG = Logger.getLogger(FileUtil.class);

	private static final String DEMO_FILE_PATH = "testFiles";

	public static char[] convertFileToArray(String javaFilePath) throws IOException {
		String fileName = FileUtil.getCurrentFilePath() + File.separator + DEMO_FILE_PATH + File.separator
				+ javaFilePath;

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

	// read file content into a string
	public static String readFileToString(String filePath) throws IOException {
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));

		char[] buf = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			System.out.println(numRead);
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}

		reader.close();

		return fileData.toString();
	}

	// loop directory to get file list
	public static List<String> getFilesInDir() throws IOException {

		String dirPath = getCurrentFilePath() + File.separator + "src" + File.separator;

		return getFiles(dirPath);
	}

	public static String getCurrentFilePath() throws IOException {
		File dirs = new File(".");
		String dirPath = dirs.getCanonicalPath();
		return dirPath;
	}

	private static List<String> getFiles(String dirPath) {
		List<String> fileList = new ArrayList<String>();

		File root = new File(dirPath);
		File[] files = root.listFiles();
		String filePath = null;

		for (File f : files) {
			filePath = f.getAbsolutePath();
			if (f.isFile()) {
				fileList.add(filePath);
			} else if (f.isDirectory()) {
				fileList.addAll(getFiles(filePath));
			}

		}

		return fileList;

	}
}
