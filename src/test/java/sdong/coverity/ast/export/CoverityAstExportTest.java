package sdong.coverity.ast.export;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdong.common.exception.SdongException;

public class CoverityAstExportTest {
	private static final Logger logger = LoggerFactory.getLogger(CoverityAstExportTest.class);

	@Test
	public void testExportAstByTUList() {
		String emitDir = "D:/git/sdong/AST/input/coverityEmit";
		String command = CoverityAstExport.PRINT_DEBUG;
		Map<String, Integer> tuList = new HashMap<String, Integer>();
		tuList.put("10", 10);
		tuList.put("32", 32);
		tuList.put("33", 33);
		tuList.put("34", 34);
		tuList.put("35", 35);
		tuList.put("36", 36);
		tuList.put("37", 37);
		tuList.put("38", 38);
		tuList.put("39", 39);
		tuList.put("40", 40);
		tuList.put("41", 41);

		try {
			Map<String, List<String>> astMap = CoverityAstExport.exportAstByTUList(tuList, command, emitDir);
			logger.info("astMap size=" + astMap.size());
			assertEquals(7019, astMap.get("10").size());
		} catch (SdongException e) {
			e.printStackTrace();
			fail("should not have exception!");
		}
	}

}
