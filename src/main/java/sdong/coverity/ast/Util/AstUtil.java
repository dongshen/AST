package sdong.coverity.ast.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdong.common.exception.SdongException;
import sdong.common.utils.StringUtil;
import sdong.common.utils.Util;
import sdong.common.utils.ZlibUtil;
import sdong.coverity.ast.CoverityAstFunction;
import sdong.coverity.ast.CoverityAstFunction.DefinitionType;
import sdong.coverity.ast.Loc;

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

	public static final int AST_TYPE_DEFINITION = 1;
	public static final int AST_TYPE_DEBUG = 2;

	public static Map<String, List<CoverityAstFunction>> splitMultipleTuToFunAstList(List<String> astContent,
			int astType) throws SdongException {
		Map<String, List<CoverityAstFunction>> tuMap = new HashMap<String, List<CoverityAstFunction>>();
		List<String> tu = new ArrayList<String>();

		try {

			for (String line : astContent) {
				if (line.equals(COMMENT_START)) {
					if (tu.size() > 0) {
						addAstFunToTuMap(convertFunAst(tu, astType), tuMap);
						tu = new ArrayList<String>();
					}
				}
				tu.add(line);
			}

			if (tu.size() > 0) {
				addAstFunToTuMap(convertFunAst(tu, astType), tuMap);
			}

			if (astType == AST_TYPE_DEFINITION) {
				for (Map.Entry<String, List<CoverityAstFunction>> entry : tuMap.entrySet()) {
					updateAstFunList(entry.getValue());
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SdongException(e.getMessage());
		}

		return tuMap;
	}

	private static void addAstFunToTuMap(CoverityAstFunction fun, Map<String, List<CoverityAstFunction>> tuMap) {
		if (fun == null) {
			return;
		}

		String tuNum = fun.getTuNum();
		if (tuMap.containsKey(tuNum)) {
			tuMap.get(tuNum).add(fun);
		} else {
			List<CoverityAstFunction> tuList = new ArrayList<CoverityAstFunction>();
			tuList.add(fun);
			tuMap.put(tuNum, tuList);
		}
	}

	private static String updateAstFunList(List<CoverityAstFunction> funList) throws SdongException {
		String astId = null;
		Iterator<CoverityAstFunction> iter = funList.iterator();
		CoverityAstFunction fun;
		List<String> tu = new ArrayList<String>();
		while (iter.hasNext()) {
			fun = iter.next();
			tu.addAll(fun.getContentForDefinition());
			if (fun.getFileName() == null) {
				iter.remove();
			}
		}

		astId = Util.generateMD5(StringUtil.joinStringListToStringByLineBreak(tu));

		for (CoverityAstFunction function : funList) {
			function.setAstId(astId);
		}

		return astId;
	}

	/**
	 * split multiple tu export ast by TU number
	 * 
	 * @param astContent
	 * @return
	 * @throws SdongException
	 */
	public static Map<String, List<String>> splitTUAst(List<String> astContent) throws SdongException {
		Map<String, List<String>> tuMap = new HashMap<String, List<String>>();
		List<String> tu = new ArrayList<String>();

		try {

			for (String line : astContent) {
				if (line.equals(COMMENT_START)) {
					if (tu.size() > 0) {
						addTuToTuMap(tu, tuMap);
						tu = new ArrayList<String>();
					}
				}
				tu.add(line);
			}
			if (tu.size() > 0) {
				addTuToTuMap(tu, tuMap);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SdongException(e.getMessage());
		}

		return tuMap;
	}

	private static void addTuToTuMap(List<String> tu, Map<String, List<String>> tuMap) {
		String tuNum = getTuNumFromTu(tu);
		if (tuMap.containsKey(tuNum)) {
			tuMap.get(tuNum).addAll(tu);
		} else {
			tuMap.put(tuNum, tu);
		}
	}

	private static CoverityAstFunction convertFunAst(List<String> tu, int astType) throws SdongException {
		CoverityAstFunction fun = new CoverityAstFunction();

		String line;
		Iterator<String> iter = tu.iterator();
		String contentStr;

		while (iter.hasNext()) {
			line = iter.next();
			if (line.contains(DEFINED_IN_TU)) {
				fun.setTuNum(getTuNumFromLine(line));
				iter.remove();
				break;
			} else if (line.contains(AstUtil.MATCHING)) {
				setMatchingType(line, fun);
			} else if (line.contains(AstUtil.DECLARED_AT)) {
				setDeclaredAt(iter.next(), fun);
			}
		}

		if (astType == AST_TYPE_DEFINITION) {
			fun.setContentForDefinition(tu);
			if (fun.getFileName() != null) {
				contentStr = StringUtil.joinStringListToStringByLineBreak(tu);
				fun.setCompressDefinition(ZlibUtil.compress(contentStr.getBytes()));
				fun.setFunId(Util.generateMD5(contentStr));
			}
		} else if (astType == AST_TYPE_DEBUG) {
			if (fun.getFileName() == null) {
				tu.clear();
				fun = null;
			} else {
				fun.setCompressDebug(ZlibUtil.compress(tu));
			}
		}

		contentStr = null;

		return fun;
	}

	public static List<CoverityAstFunction> convertTuToFunAstList(List<String> astContent, int astType)
			throws SdongException {
		List<CoverityAstFunction> funList = new ArrayList<CoverityAstFunction>();
		List<String> tu = new ArrayList<String>();
		CoverityAstFunction fun;

		for (String line : astContent) {
			if (line.equals(COMMENT_START)) {
				if (tu.size() > 0) {
					fun = convertFunAst(tu, astType);
					if (fun != null) {
						funList.add(fun);
					}
					tu = new ArrayList<String>();
				}
			}
			tu.add(line);
		}

		if (tu.size() > 0) {
			fun = convertFunAst(tu, astType);
			if (fun != null) {
				funList.add(fun);
			}
		}

		if (astType == AST_TYPE_DEFINITION) {
			updateAstFunList(funList);
		}

		return funList;
	}

	private static String getTuNumFromTu(List<String> tu) {
		String tuNum = null;
		String line;
		Iterator<String> iter = tu.iterator();

		while (iter.hasNext()) {
			line = iter.next();
			if (line.contains(DEFINED_IN_TU)) {
				tuNum = getTuNumFromLine(line);
				iter.remove();
				break;
			}
		}

		return tuNum;
	}

	private static String getTuNumFromLine(String line) {
		String tuNum = null;
		int tuStart = 0;
		int tuEnd = 0;

		tuStart = line.indexOf(DEFINED_IN_TU) + DEFINED_IN_TU.length();
		tuEnd = line.indexOf(DEFINED_IN_TU_WITH_RON);
		if (tuEnd == -1) {
			tuNum = line.substring(tuStart);
		} else {
			tuNum = line.substring(tuStart, tuEnd);
		}

		return tuNum;
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

	public static String getVariableTypeFromDefinition(CoverityAstFunction fun, List<CoverityAstFunction> globalList,
			String variable) {
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

	public static void setDeclaredAt(String line, CoverityAstFunction definition) throws SdongException {
		try {
			if (line.startsWith(AstUtil.PREFIX)) {
				line = line.substring(AstUtil.PREFIX.length()).trim();
			}

			if (line.equals(AstUtil.UNKONW)) {
				return;
			}

			String[] declared = line.split("-");
			if (declared.length != 2) {
				throw new SdongException("wrong split declared at:" + line);
			}

			String from = declared[0].trim();

			// get col of from
			int iCol = from.lastIndexOf(":");
			String strCol = from.substring(iCol + 1);
			Loc fromLoc = new Loc();
			fromLoc.setCol(Integer.parseInt(strCol));

			// get ln of from
			from = from.substring(0, iCol);
			int iLn = from.lastIndexOf(":");
			String strLn = from.substring(iLn + 1);
			fromLoc.setLn(Integer.parseInt(strLn));

			// file of from
			from = from.substring(0, iLn);
			fromLoc.setFileName(from);

			String to = declared[1].trim();

			// get col of to
			iCol = to.lastIndexOf(":");
			strCol = to.substring(iCol + 1);
			Loc toLoc = new Loc();
			toLoc.setCol(Integer.parseInt(strCol));

			// get ln of from
			to = to.substring(0, iCol);
			iLn = to.lastIndexOf(":");
			strLn = to.substring(iLn + 1);
			toLoc.setLn(Integer.parseInt(strLn));

			// file of to
			to = to.substring(0, iLn);
			toLoc.setFileName(to);

			definition.setFileName(fromLoc.getFileName());
			definition.setFileNameHash(Util.generateMD5(definition.getFileName()));
			definition.setFromLine(fromLoc.getLn());
			definition.setFromColumn(fromLoc.getCol());
			definition.setToLine(toLoc.getLn());
			definition.setToColumn(toLoc.getCol());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SdongException(e);
		}
	}

	public static void setMatchingType(String line, CoverityAstFunction definition) throws SdongException {
		try {
			line = line.substring(line.indexOf(AstUtil.MATCHING) + AstUtil.MATCHING.length());
			String[] types = line.split(":");
			DefinitionType type = DefinitionType.get(types[0].trim());
			definition.setType(type);
			definition.setClassName(types[1].trim());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SdongException(e);
		}
	}
}
