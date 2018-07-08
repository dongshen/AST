package sdong.coverity.ast.Util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import sdong.coverity.ast.CoverityAst;
import sdong.coverity.ast.parse.CoverityAstParse;

public class AstUtil {

	public static List<String> removeDEFINED_IN_TU(List<String> tuContent) {
		String line;
		for (Iterator<String> iter = tuContent.listIterator(); iter.hasNext();) {
			line = iter.next();
			if (line.contains(CoverityAstParse.DEFINED_IN_TU)) {
				iter.remove();
			}
		}

		return tuContent;
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
