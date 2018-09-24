package sdong.coverity.emit.tu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONReader;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.google.gson.stream.JsonReader;

import sdong.common.exception.SdongException;

public class ParseCoverityTU {
	private static final Logger logger = LoggerFactory.getLogger(ParseCoverityTU.class);

	public static List<CoverityTransactionUnit> parseCoverityTU_Gson(String fileName) throws SdongException {

		List<CoverityTransactionUnit> tuList = new ArrayList<CoverityTransactionUnit>();
		CoverityTransactionUnit tu;
//		Gson gson = new Gson();
		String tag;
		JsonReader reader = null;
		try {
			reader = new JsonReader(new FileReader(fileName));
			reader.beginArray();
			while (reader.hasNext()) {
				tu = new CoverityTransactionUnit();
				reader.beginObject();
				while (reader.hasNext()) {
					tag = reader.nextName();
					if ("id".equals(tag)) {
						tu.setId(reader.nextInt());
					} else if ("primaryFilename".equals(tag)) {
						tu.setPrimaryFilename(reader.nextString());
					} else if ("language".equals(tag)) {
						tu.setLanguage(reader.nextString());
					} else if ("userLanguage".equals(tag)) {
						tu.setUserLanguage(reader.nextString());
					} else if ("hasASTs".equals(tag)) {
						tu.setHasASTs(reader.nextBoolean());
					} else if ("isFailure".equals(tag)) {
						tu.setFailure(reader.nextBoolean());
					} else {
						reader.skipValue();
					}

				}
				reader.endObject();
				tuList.add(tu);
			}
			reader.endArray();

		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			throw new SdongException(e);
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new SdongException(e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
					throw new SdongException(e);
				}
			}
		}
		// List<Review> data = gson(reader, REVIEW_TYPE);
		return tuList;

	}

	public static List<CoverityTransactionUnit> parseCoverityTU_fastjson(String fileName) throws SdongException {
		List<CoverityTransactionUnit> tuList = new ArrayList<CoverityTransactionUnit>();
		CoverityTransactionUnit tu;
		JSONReader reader = null;
		try {
			reader = new JSONReader(new FileReader(fileName));

			reader.startArray();
			while (reader.hasNext()) {
				tu = reader.readObject(CoverityTransactionUnit.class);
				tuList.add(tu);
			}
			reader.endArray();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			throw new SdongException(e);
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return tuList;

	}

	public static List<CoverityTransactionUnit> parseCoverityTU_jackson(String fileName) throws SdongException {
		List<CoverityTransactionUnit> tuList = new ArrayList<CoverityTransactionUnit>();
		JsonFactory jasonFactory = new JsonFactory();
		JsonParser jp = null;
		CoverityTransactionUnit tu = null;
		String tag;
		try {
			jp = jasonFactory.createParser(new File(fileName));
			if (jp.nextToken() != JsonToken.START_ARRAY) {
				throw new SdongException("Expected data to start with an Object");
			}
			//jp.nextToken();
			while (jp.nextToken() != JsonToken.END_ARRAY) {
				tag = jp.getCurrentName();
				if ("id".equals(tag)) {
					if (tu == null) {
						tu = new CoverityTransactionUnit();
					} else {
						tuList.add(tu);
						tu = new CoverityTransactionUnit();
					}
					tu.setId(jp.getIntValue());
				} else if ("primaryFilename".equals(tag)) {
					tu.setPrimaryFilename(jp.getText());
				} else if ("language".equals(tag)) {
					tu.setLanguage(jp.getText());
				} else if ("userLanguage".equals(tag)) {
					tu.setUserLanguage(jp.getText());
				} else if ("hasASTs".equals(tag)) {
					tu.setHasASTs(jp.getBooleanValue());
				} else if ("isFailure".equals(tag)) {
					tu.setFailure(jp.getBooleanValue());
				} else {
					// jp.skipChildren();
				}
			}
			if (tu != null) {
				tuList.add(tu);
			}

		} catch (JsonParseException e) {
			logger.error(e.getMessage());
			throw new SdongException(e);
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new SdongException(e);
		} finally {
			if (jp != null && !jp.isClosed()) {
				try {
					jp.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
					throw new SdongException(e);
				}
			}
		}
		return tuList;

	}

}
