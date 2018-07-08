package sdong.coverity.ast.parse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdong.common.exception.SdongException;
import sdong.coverity.ast.CoverityAst;
import sdong.coverity.ast.CoverityAst.DefinitionType;
import sdong.coverity.ast.Loc;

public class CoverityAstParse {

	private static final Logger logger = LoggerFactory.getLogger(CoverityAstParse.class);

	private static final String COMMENT_START = "/*";
	private static final String COMMENT_END = "*/";
	public static final String DEFINED_IN_TU = " * defined in TU ";
	private static final String DEFINED_IN_TU_WITH_RON = " with row ";
	private static final String MATCHING = " * Matching ";
	private static final String DECLARED_AT = " * declared at:";
	private static final String PREFIX = " *   ";
	private static final String UNKONW = "<unknown>";

	public List<CoverityAst> parse(List<String> tuContent) throws SdongException {
		List<CoverityAst> definitionList = new ArrayList<CoverityAst>();
		String line;
		boolean first = true;
		boolean start = true;
		boolean end = false;
		CoverityAst definition = null;
		List<String> content = null;

		try {
			for (int i = 1; i <= tuContent.size(); i++) {
				line = tuContent.get(i - 1);
				if (line.contains(COMMENT_START)) {
					start = true;
					end = false;
					// not the first one, need add to list
					if (first == false) {
						if (definition.getFileName() != null) {
							definition.setContentForDefinition(content);
							definitionList.add(definition);
						}
					}
					if (first == true) {
						first = false;
					}

					definition = new CoverityAst();
					content = new ArrayList<String>();

					continue;

				}

				if (line.contains(COMMENT_END) && definition != null) {
					end = true;
					start = false;
					continue;
				}

				if (start == true) {
					if (line.contains(MATCHING)) {
						setMatchingType(line, definition);
					} else if (line.contains(DECLARED_AT)) {
						setDeclaredAt(tuContent.get(i), definition);
					}
				}

				if (end == true) {
					content.add(line);
				}

			}
			if (definition != null) {
				if (definition.getFileName() != null) {
					definition.setContentForDefinition(content);
					definitionList.add(definition);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SdongException(e.getMessage());
		}

		//sort by line num
		Collections.sort(definitionList);

		return definitionList;
	}

	public void setDeclaredAt(String line, CoverityAst definition) throws SdongException {
		try {
			if (line.startsWith(PREFIX)) {
				line = line.substring(PREFIX.length()).trim();
			}

			if (line.equals(UNKONW)) {
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
			definition.setFromLine(fromLoc.getLn());
			definition.setFromColumn(fromLoc.getCol());
			definition.setToLine(toLoc.getLn());
			definition.setToColumn(toLoc.getCol());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SdongException(e.getMessage());
		}
	}

	public void setMatchingType(String line, CoverityAst definition) throws SdongException {
		try {
			line = line.substring(line.indexOf(MATCHING) + MATCHING.length());
			String[] types = line.split(":");
			DefinitionType type = DefinitionType.get(types[0].trim());
			definition.setType(type);
			definition.setClassName(types[1].trim());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SdongException(e.getMessage());
		}
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

}
