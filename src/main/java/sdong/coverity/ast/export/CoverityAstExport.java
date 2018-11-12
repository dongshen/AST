package sdong.coverity.ast.export;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdong.common.exception.SdongException;

public class CoverityAstExport {
	private static final Logger logger = LoggerFactory.getLogger(CoverityAstExport.class);

	public static final String PRINT_DEFINITION = "cov-manage-emit --dir {dir} --tu {tuList} find . -print-definitions";
	public static final String PRINT_DEBUG = "cov-manage-emit --dir {dir} --tu {tuList} find . -print-debug";

	public static final String COMMENT_START = "/*";
	public static final String COMMENT_END = " */";
	public static final String DEFINED_IN_TU = " * defined in TU ";
	private static final String DEFINED_IN_TU_WITH_RON = " with row ";

	public static final String MATCHING = " * Matching ";
	public static final String DECLARED_AT = " * declared at:";
	public static final String PREFIX = " *   ";
	public static final String UNKONW = "<unknown>";

	public static Map<String, List<String>> exportAstByTUList(Map<String, Integer> tuList, String command,
			String emitDir) throws SdongException {

		Map<String, List<String>> tuAstList = new HashMap<String, List<String>>();

		// get emit list;
		if (tuList == null || tuList.isEmpty()) {
			return tuAstList;
		}

		String tuListStr = "";
		for (Map.Entry<String, Integer> tu : tuList.entrySet()) {
			tuListStr = tuListStr + tu.getValue() + ",";
		}
		tuListStr = tuListStr.substring(0, tuListStr.length() - 1);

		// set cmd
		command = command.replace("{dir}", emitDir).replace("{tuList}", tuListStr);
		logger.debug("command=" + command);
		String[] cmd = { "cmd", "/C", command };

		StringBuffer result = new StringBuffer();
		Process p;

		try {
			p = Runtime.getRuntime().exec(cmd);
			// 取得命令结果的输出流
			InputStream fis = p.getInputStream();
			// 用一个读输出流类去读
			InputStreamReader isr = new InputStreamReader(fis);
			// 用缓冲器读行
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			// 直到读完为止

			List<String> tu = null;
			String tuNum = "";
			int tuStart = 0;
			int tuEnd = 0;

			while ((line = br.readLine()) != null) {
				if (line.equals(COMMENT_START)) {
					if (tu == null) {
						tu = new ArrayList<String>();
					} else {
						if (tuAstList.containsKey(tuNum)) {
							tuAstList.get(tuNum).addAll(tu);
						} else {
							tuAstList.put(tuNum, tu);
						}
						tu = new ArrayList<String>();
					}

				} else if (line.contains(DEFINED_IN_TU)) {
					tuStart = line.indexOf(DEFINED_IN_TU) + DEFINED_IN_TU.length();
					tuEnd = line.indexOf(DEFINED_IN_TU_WITH_RON);
					if (tuEnd == -1) {
						tuNum = line.substring(tuStart);
					} else {
						tuNum = line.substring(tuStart, tuEnd);
					}
				}
				tu.add(line);
			}
			if (tu != null) {
				if (tuAstList.containsKey(tuNum)) {
					tuAstList.get(tuNum).addAll(tu);
				} else {
					tuAstList.put(tuNum, tu);
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new SdongException(e);
		}
		return tuAstList;
	}
}
