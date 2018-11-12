package sdong.coverity.ast.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdong.common.exception.SdongException;
import sdong.coverity.ast.CoverityAstFunction;

public class AstUtil {

	private static final Logger logger = LoggerFactory.getLogger(AstUtil.class);

	public static final String COMMENT_START = "/*";
	public static final String COMMENT_END = " */";
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

	/**
	 * split multiple tu export ast by TU number 
	 * @param astContent
	 * @return
	 * @throws SdongException
	 */
	public static Map<String, List<String>> splitTUAst(List<String> astContent) throws SdongException {
		Map<String, List<String>> tuList = new HashMap<String, List<String>>();
		List<String> tu = null;
		String tuNum = "";
		int tuStart = 0;
		int tuEnd = 0;

		try {

			for (String line : astContent) {
				if (line.equals(COMMENT_START)) {
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
						tuNum = line.substring(tuStart);
					} else {
						tuNum = line.substring(tuStart, tuEnd);
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

	public static List<CoverityAstFunction> getGlobleVariableList(List<CoverityAstFunction> functionList) {
		List<CoverityAstFunction> globalList = new ArrayList<CoverityAstFunction>();
		for (CoverityAstFunction fun : functionList) {
			if (fun.getType().equals(CoverityAstFunction.DefinitionType.GLOBAL)) {
				globalList.add(fun);
			}
		}
		return globalList;
	}

	/*
	 * Base on line num to get function
	 */
	public static CoverityAstFunction getFunctionPerlineNum(List<CoverityAstFunction> functionList, int lineNum) {
		CoverityAstFunction fun = null;

		for (CoverityAstFunction function : functionList) {
			if (function.getFromLine() <= lineNum) {
				fun = function;
			} else {
				break;
			}
		}

		return fun;
	}

	public static String getVariableTypeFromDefinition(CoverityAstFunction fun, List<CoverityAstFunction> globalList, String variable) {
		String type = null;

		// check global first
		if (globalList != null) {
			for (CoverityAstFunction global : globalList) {
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
