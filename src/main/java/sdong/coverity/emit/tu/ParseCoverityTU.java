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
					} else if ("isFromBootClassPath".equals(tag)) {
						tu.setFromBootClassPath(reader.nextBoolean());
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
		String tag;
		try {
			reader = new JSONReader(new FileReader(fileName));

			reader.startArray();
			while (reader.hasNext()) {
				reader.startObject();
				tu = new CoverityTransactionUnit();
				while (reader.hasNext()) {
					tag = reader.readString();
					if ("id".equals(tag)) {
						tu.setId(reader.readInteger());
					} else if ("primaryFilename".equals(tag)) {
						tu.setPrimaryFilename(reader.readString());
					} else if ("language".equals(tag)) {
						tu.setLanguage(reader.readString());
					} else if ("userLanguage".equals(tag)) {
						tu.setUserLanguage(reader.readString());
					} else if ("hasASTs".equals(tag)) {
						tu.setHasASTs(Boolean.parseBoolean(reader.readString()));
					} else if ("isFailure".equals(tag)) {
						tu.setFailure(Boolean.parseBoolean(reader.readString()));
					} else if ("isFromBootClassPath".equals(tag)) {
						tu.setFromBootClassPath(Boolean.parseBoolean(reader.readString()));

					} else {
						reader.readObject();
					}
				}
				reader.endObject();
				// tu = reader.readObject(CoverityTransactionUnit.class);
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
			JsonToken nextToken = jp.nextToken();
			while (nextToken != JsonToken.END_ARRAY) {
				if (nextToken == JsonToken.START_OBJECT) {
					tu = new CoverityTransactionUnit();
					while (jp.nextToken() != JsonToken.END_OBJECT) {
						tag = jp.getCurrentName();
						if ("id".equals(tag)) {
							jp.nextToken();
							tu.setId(jp.getIntValue());
						} else if ("primaryFilename".equals(tag)) {
							jp.nextToken();
							tu.setPrimaryFilename(jp.getText());
						} else if ("language".equals(tag)) {
							jp.nextToken();
							tu.setLanguage(jp.getText());
						} else if ("userLanguage".equals(tag)) {
							jp.nextToken();
							tu.setUserLanguage(jp.getText());
						} else if ("hasASTs".equals(tag)) {
							jp.nextToken();
							tu.setHasASTs(jp.getBooleanValue());
						} else if ("isFailure".equals(tag)) {
							jp.nextToken();
							tu.setFailure(jp.getBooleanValue());
						} else if ("isFromBootClassPath".equals(tag)) {
							jp.nextToken();
							tu.setFromBootClassPath(jp.getBooleanValue());
						} else {
							jp.nextToken();
						}
					}
					tuList.add(tu);
				}
				nextToken = jp.nextToken();
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
