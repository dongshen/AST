package sdong.coverity.ast.parse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdong.common.exception.SdongException;
import sdong.common.utils.StringUtil;
import sdong.coverity.ast.CoverityAstFunction;
import sdong.coverity.ast.CoverityAstFunction.DefinitionType;
import sdong.coverity.ast.Loc;
import sdong.coverity.ast.Util.AstUtil;

public class CoverityAstParse {

	private static final Logger logger = LoggerFactory.getLogger(CoverityAstParse.class);


	public List<CoverityAstFunction> parse(List<String> tuContent) throws SdongException {
		List<CoverityAstFunction> definitionList = new ArrayList<CoverityAstFunction>();
		String line;
		boolean first = true;
		boolean start = true;
		boolean end = false;
		CoverityAstFunction definition = null;
		List<String> content = null;

		try {
			for (int i = 1; i <= tuContent.size(); i++) {
				line = tuContent.get(i - 1);
				if (line.equals(AstUtil.COMMENT_START)) {
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

					definition = new CoverityAstFunction();
					content = new ArrayList<String>();

					continue;

				}

				if (line.equals(AstUtil.COMMENT_END) && definition != null) {
					end = true;
					start = false;
					continue;
				}

				if (start == true) {
					if (line.contains(AstUtil.MATCHING)) {
						AstUtil.setMatchingType(line, definition);
					} else if (line.contains(AstUtil.DECLARED_AT)) {
						AstUtil.setDeclaredAt(tuContent.get(i), definition);
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

		// sort by line num
		Collections.sort(definitionList);

		return definitionList;
	}

	public void parseAstDebug(List<String> tuContent) throws SdongException {
		try {
			String line;
			String checkChar = "";
			int indented = -1;
			int preIndented = -1;
			for (int i = 1; i <= tuContent.size(); i++) {
				line = tuContent.get(i);
				indented = StringUtil.checkIndentNum(line, checkChar);
				if (indented != preIndented) {
					if(indented == 0) {
						
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SdongException(e.getMessage());
		}

	}

}
