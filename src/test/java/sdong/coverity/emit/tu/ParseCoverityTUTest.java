package sdong.coverity.emit.tu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdong.common.exception.SdongException;

public class ParseCoverityTUTest {
	private static final Logger logger = LoggerFactory.getLogger(ParseCoverityTUTest.class);

	@Test
	public void testParseCoverityTU_Gson() {
		String fileName = "input/coverityEmit/list-json.json";
		try {
			long startTime = Calendar.getInstance().getTimeInMillis();
			List<CoverityTransactionUnit> tuList = ParseCoverityTU.parseCoverityTU_Gson(fileName);
			long endTime = Calendar.getInstance().getTimeInMillis();

			logger.info("tu list =" + tuList.size() + " use:" + (endTime - startTime));

			assertEquals(1012, tuList.size());
		} catch (SdongException e) {
			e.printStackTrace();
			fail("should not have exception!");
		}
	}

	@Test
	public void testParseCoverityTU_fastjson() {
		String fileName = "input/coverityEmit/list-json.json";
		try {
			long startTime = Calendar.getInstance().getTimeInMillis();
			List<CoverityTransactionUnit> tuList = ParseCoverityTU.parseCoverityTU_fastjson(fileName);
			long endTime = Calendar.getInstance().getTimeInMillis();

			logger.info("tu list =" + tuList.size() + " use:" + (endTime - startTime));

			assertEquals(1012, tuList.size());
		} catch (SdongException e) {
			e.printStackTrace();
			fail("should not have exception!");
		}
	}

	@Test
	public void testParseCoverityTU_jackson() {
		String fileName = "input/coverityEmit/list-json.json";
		try {
			long startTime = Calendar.getInstance().getTimeInMillis();
			List<CoverityTransactionUnit> tuList = ParseCoverityTU.parseCoverityTU_jackson(fileName);
			long endTime = Calendar.getInstance().getTimeInMillis();

			logger.info("tu list =" + tuList.size() + " use:" + (endTime - startTime));

			assertEquals(1012, tuList.size());
		} catch (SdongException e) {
			e.printStackTrace();
			fail("should not have exception!");
		}
	}

}
