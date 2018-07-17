package sdong.coverity.ast.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdong.common.exception.SdongException;
import sdong.coverity.ast.CoverityAst;

public class AstUtil {

	private static final Logger logger = LoggerFactory.getLogger(AstUtil.class);
	
	public static final String COMMENT_START = "/*";
	public static final String COMMENT_END = "*/";
	public static final String DEFINED_IN_TU = " * defined in TU ";
	private static final String DEFINED_IN_TU_WITH_RON = " with row ";
	

	public static final String MATCHING = " * Matching ";
	public static final String DECLARED_AT = " * declared at:";
	public static final String PREFIX = " *   ";
	public static final String UNKONW = "<unknown>";
	
	public static List<String> removeDEFINED_IN_TU(List<String> tuContent) {
		String line;
		for (Iterator<String> iter = tuContent.listIterator(); iter.hasNext();) {
			line = iter.next();
			if (line.contains(DEFINED_IN_TU)) {
				iter.remove();
			}
		}

		return tuContent;
	}

	public static Map<Integer, List<String>> splitTUAst(List<String> astContent) throws SdongException {
		Map<Integer, List<String>> tuList = new HashMap<Integer, List<String>>();
		List<String> tu = null;
		int tuNum = 0;
		int tuStart = 0;
		int tuEnd = 0;

		try {

			for (String line : astContent) {
				if (line.startsWith(COMMENT_START)) {
					if (tu == null) {
						tu = new ArrayList<String>();
					} else {
						if (tuList.containsKey(tuNum)) {
							tuList.get(tuNum).addAll(tu);
						} else {
							tuList.put(tuNum, tu);
						}
						tu = new ArrayList<String>();
					}

				} else if (line.contains(DEFINED_IN_TU)) {
					tuStart = line.indexOf(DEFINED_IN_TU) + DEFINED_IN_TU.length();
					tuEnd = line.indexOf(DEFINED_IN_TU_WITH_RON);
					if (tuEnd == -1) {
						tuNum = Integer.parseInt(line.substring(tuStart));
					} else {
						tuNum = Integer.parseInt(line.substring(tuStart, tuEnd));
					}
				}
				tu.add(line);
			}
			if (tu != null) {
				if (tuList.containsKey(tuNum)) {
					tuList.get(tuNum).addAll(tu);
				} else {
					tuList.put(tuNum, tu);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SdongException(e.getMessage());
		}

		return tuList;
	}
	
	public static List<CoverityAst> getGlobleVariableList(List<CoverityAst> functionList) {
		List<CoverityAst> globalList = new ArrayList<CoverityAst>();
		for (CoverityAst fun : functionList) {
			if (fun.getType().equals(CoverityAst.DefinitionType.GLOBAL)) {
				globalList.add(fun);
			}
		}
		return globalList;
	}

	/*
	 * Base on line num to get function
	 */
	public static CoverityAst getFunctionPerlineNum(List<CoverityAst> functionList, int lineNum) {
		CoverityAst fun = null;

		for (CoverityAst function : functionList) {
			if (function.getFromLine() <= lineNum) {
				fun = function;
			} else {
				break;
			}
		}

		return fun;
	}

	public static String getVariableTypeFromDefinition(CoverityAst fun, List<CoverityAst> globalList, String variable) {
		String type = null;

		// check global first
		if (globalList != null) {
			for (CoverityAst global : globalList) {
				List<String> contentList = global.getContentForDefinition();
				for (String content : contentList) {
					type = getVaribleTypeInLine(content, variable);
					if (type != null && !type.isEmpty()) {
						break;
					}
				}
			}
		}

		if (type == null || type.isEmpty()) {
			for (String line : fun.getContentForDefinition()) {
				type = getVaribleTypeInLine(line, variable);
				if (type != null && !type.isEmpty()) {
					break;
				}
			}
		}

		return type;
	}

	/**
	 * Get varible type from ast line
	 * 
	 * @param line
	 * @param variable
	 * @return
	 */
	private static String getVaribleTypeInLine(String line, String variable) {

		String type = null;
		if (variable == null || variable.isEmpty()) {
			return type;
		}

		String preType = "";
		String[] tokens = splitLineToToken(line);
		if (tokens != null) {
			preType = "";
			for (int i = 0; i < tokens.length; i++) {
				if (i != 0 && tokens[i].equals(variable)) {
					type = preType;
					break;
				}
				preType = tokens[i];
			}
		}

		return type;
	}

	/*
	 * split line to token by ' ', ',' , '(', ')','{','}',';'
	 */
	public static String[] splitLineToToken(String line) {
		return line.split("\\s*\\(\\s*|\\s*\\)\\s*|\\s*,\\s*|\\s*\\{\\s*|\\s*\\}\\s*|\\s*;\\s*|\\s+");
	}
}
