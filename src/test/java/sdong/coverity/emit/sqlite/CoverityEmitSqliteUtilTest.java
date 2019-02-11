package sdong.coverity.emit.sqlite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdong.common.exception.SdongException;
import sdong.common.utils.StringUtil;

public class CoverityEmitSqliteUtilTest {

	private static final Logger logger = LoggerFactory.getLogger(CoverityEmitSqliteUtilTest.class);

	@Test
	public void testGetCoverityEmitDBFileNameList() {
		String dbfile = "input/coverityEmit/emit/SHENDONG-PC/emit-db";
		Map<Integer, String> fileList;
		try {
			fileList = CoverityEmitSqliteUtil.getCoverityEmitDBFileNameList(dbfile);

			logger.info("file num=" + fileList.size());
			for (Map.Entry<Integer, String> entry : fileList.entrySet()) {
				logger.info("content id=" + entry.getKey() + " " + entry.getValue());
			}
			assertEquals(2016, fileList.size());
		} catch (SdongException e) {
			e.printStackTrace();
			fail("should not get exception");
		}

	}

	@Test
	public void testGetCoverityEmitDBFileInfoList() {
		String dbfile = "input/coverityEmit/emit/SHENDONG-PC/emit-db";
		List<CoverityEmitFileInfo> fileList;
		try {
			fileList = CoverityEmitSqliteUtil.getCoverityEmitDBFileInfoList(dbfile);

			logger.info("file num=" + fileList.size());
			for (CoverityEmitFileInfo file : fileList) {
				logger.info(file.toString());
			}
			assertEquals(2014, fileList.size());
		} catch (SdongException e) {
			e.printStackTrace();
			fail("should not get exception");
		}

	}

	@Test
	public void testBuildFileListByRecursiveResult() {
		// id, parentid, component
		// 1 -1 ""
		// 2 1 "a"
		// 3 2 "b"
		// 4 2 "c"
		// 5 3 "d"
		// 6 4 "e"
		// 7 4 "f"
		// 8 7 "g"
		// ========
		// 5 /a/b/d
		// 6 /a/c/e
		// 8 /a/c/f/g
		Map<Integer, List<CoverityEmitFileNameBean>> compList = new HashMap<Integer, List<CoverityEmitFileNameBean>>();
		List<CoverityEmitFileNameBean> list;
		CoverityEmitFileNameBean bean;
		int parent;

		parent = -1;
		list = new ArrayList<CoverityEmitFileNameBean>();
		bean = new CoverityEmitFileNameBean();
		bean.setFileNameId(1);
		bean.setParent(parent);
		bean.setComponent("");
		list.add(bean);
		compList.put(parent, list);

		parent = 1;
		list = new ArrayList<CoverityEmitFileNameBean>();
		bean = new CoverityEmitFileNameBean();
		bean.setFileNameId(2);
		bean.setParent(parent);
		bean.setComponent("a");
		list.add(bean);
		compList.put(parent, list);

		parent = 2;
		list = new ArrayList<CoverityEmitFileNameBean>();
		bean = new CoverityEmitFileNameBean();
		bean.setFileNameId(3);
		bean.setParent(parent);
		bean.setComponent("b");
		list.add(bean);
		bean = new CoverityEmitFileNameBean();
		bean.setFileNameId(4);
		bean.setParent(parent);
		bean.setComponent("c");
		list.add(bean);
		compList.put(parent, list);

		parent = 3;
		list = new ArrayList<CoverityEmitFileNameBean>();
		bean = new CoverityEmitFileNameBean();
		bean.setFileNameId(5);
		bean.setParent(parent);
		bean.setComponent("d");
		list.add(bean);
		compList.put(parent, list);

		parent = 4;
		list = new ArrayList<CoverityEmitFileNameBean>();
		bean = new CoverityEmitFileNameBean();
		bean.setFileNameId(6);
		bean.setParent(parent);
		bean.setComponent("e");
		list.add(bean);
		bean = new CoverityEmitFileNameBean();
		bean.setFileNameId(7);
		bean.setParent(parent);
		bean.setComponent("f");
		list.add(bean);
		compList.put(parent, list);

		parent = 7;
		list = new ArrayList<CoverityEmitFileNameBean>();
		bean = new CoverityEmitFileNameBean();
		bean.setFileNameId(8);
		bean.setParent(parent);
		bean.setComponent("g");
		list.add(bean);
		compList.put(parent, list);

		Map<Integer, String> tempList = new HashMap<Integer, String>();
		Map<Integer, String> fileList = CoverityEmitSqliteUtil.buildFileListByRecursiveResult(compList, tempList, -1);

		assertEquals(3, fileList.size());
		assertEquals("/a/b/d", fileList.get(5));
		assertEquals("/a/c/e", fileList.get(6));
		assertEquals("/a/c/f/g", fileList.get(8));
	}

	@Test
	public void testGetCoverityEmitDBFileEncoding() {
		String dbfile = "input/coverityEmit/emit/SHENDONG-PC/emit-db";

		try {
			String encoding = CoverityEmitSqliteUtil.getCoverityEmitDBFileEncoding(dbfile);

			logger.info("encoding=" + encoding);

			assertEquals("GBK", encoding);
		} catch (SdongException e) {
			e.printStackTrace();
			fail("should not get exception");
		}

	}

	@Test
	public void testGetCoverityEmitDBFileContent() {
		String dbfile = "input/coverityEmit/emit/SHENDONG-PC/emit-db";
		int fileId = 12;
		try {

			String content = CoverityEmitSqliteUtil.getCoverityEmitDBFileContent(dbfile, fileId);
			List<String> list = StringUtil.splitStringToListByLineBreak(content);
			logger.info("size=" + list.size());
			for (String line : list) {
				logger.info(line);
			}

			assertEquals(64, list.size());
			assertEquals("public abstract class AbstractTestCase extends AbstractTestCaseBase ", list.get(9));
		} catch (SdongException e) {
			e.printStackTrace();
			fail("should not get exception");
		}

	}

	@Test
	public void testGetEmitBlobContent() {
		String dbfile = "input/coverityEmit/emit/SHENDONG-PC/emit-db";
		//String sql = "select contents content from FileContents where filename =12";
		//String sql = "select inputFiles content from ClassPathFiles where classpathfilesId=1";
		//String sql = "select info content from definedclassinfo where definedclassinfoId=3";
		//String sql = "select info content from xrefs where xrefsId=13";
		String sql = "select workerinfo content from definedfunctionInfo where rowId=1";
		

		try {
			String content = CoverityEmitSqliteUtil.getEmitBlobContent(dbfile, sql);

			logger.info("content=" + content);

			// assertEquals("GBK", content);
		} catch (SdongException e) {
			e.printStackTrace();
			fail("should not get exception");
		}

	}
}
