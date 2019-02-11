package sdong.coverity.emit.sqlite;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdong.common.exception.SdongException;
import sdong.common.utils.ZlibUtil;

public class CoverityEmitSqliteUtil {
	private static final Logger logger = LoggerFactory.getLogger(CoverityEmitSqliteUtil.class);
	private static final String SQL_COVERITY_FILENAME_LIST = "select FileNameId,parent,case_preserved_component component from filename order by FileNameId";
	private static final String SQL_COVERITY_FILE_CONTENT_LIST = "select FileContentsId,filename filenameId,contentSize,blankLines,commentLines,codeLines,inlineCommentLines,contentsHashHi,contentsHashLo from FileContents";
	private static final String SQL_COVERITY_FILE_CONTENT = "select contents,contentSize from FileContents where filename = ?";
	private static final String SQL_COVERITY_FILE_ENCODING = "select InputFileEncodingId Id,s   from InputFileEncoding";
	private static final int QUERY_TIMEOUT = 30;
	private static final int BUFFER_SIZE = 1024 * 1024;

	public static List<CoverityEmitFileInfo> getCoverityEmitDBFileInfoList(String dbfile) throws SdongException {
		List<CoverityEmitFileInfo> fileInfoList = new ArrayList<CoverityEmitFileInfo>();
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		CoverityEmitFileInfo fileInfo;
		try {
			Map<Integer, String> fileMap = getCoverityEmitDBFileNameList(dbfile);

			conn = getConnection(dbfile);
			statement = conn.createStatement();
			statement.setQueryTimeout(QUERY_TIMEOUT);

			rs = statement.executeQuery(SQL_COVERITY_FILE_CONTENT_LIST);
			while (rs.next()) {
				fileInfo = new CoverityEmitFileInfo();
				fileInfo.setFileContentsId(rs.getInt("FileContentsId"));
				fileInfo.setFileNameId(rs.getInt("filenameId"));
				fileInfo.setContentSize(rs.getInt("contentSize"));
				fileInfo.setBlankLines(rs.getInt("blankLines"));
				fileInfo.setCommentLines(rs.getInt("commentLines"));
				fileInfo.setCodeLines(rs.getInt("codeLines"));
				fileInfo.setInlineCommentLines(rs.getInt("inlineCommentLines"));
				fileInfo.setFileName(fileMap.get(fileInfo.getFileNameId()));
				fileInfo.setContentsHashHi(rs.getLong("contentsHashHi"));
				fileInfo.setContentsHashLo(rs.getLong("contentsHashLo"));
				fileInfoList.add(fileInfo);
			}

		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new SdongException(e);
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return fileInfoList;
	}

	public static String getCoverityEmitDBFileEncoding(String dbfile) throws SdongException {
		String encoding = "";
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		try {

			conn = getConnection(dbfile);
			statement = conn.createStatement();
			statement.setQueryTimeout(QUERY_TIMEOUT);

			rs = statement.executeQuery(SQL_COVERITY_FILE_ENCODING);

			while (rs.next()) {
				// rs.getInt("Id");
				encoding = encoding + rs.getString("s") + ",";
			}

			if (encoding.length() > 1) {
				encoding = encoding.substring(0, encoding.length() - 1);
			}

		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new SdongException(e);
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return encoding;
	}

	/**
	 * get coverity emit file list from emit-db
	 * 
	 * @param dbfile
	 * @return Map<String,Integer>, String:file name with folder, Integer:
	 *         filenameId
	 * @throws SdongException
	 */
	public static Map<Integer, String> getCoverityEmitDBFileNameList(String dbfile) throws SdongException {
		Map<Integer, String> fileList = new HashMap<Integer, String>();
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			conn = getConnection(dbfile);
			statement = conn.createStatement();
			statement.setQueryTimeout(QUERY_TIMEOUT);
			int fileNameId, parent;
			String component;
			rs = statement.executeQuery(SQL_COVERITY_FILENAME_LIST);

			Map<Integer, List<CoverityEmitFileNameBean>> compList = new HashMap<Integer, List<CoverityEmitFileNameBean>>();
			CoverityEmitFileNameBean bean;
			List<CoverityEmitFileNameBean> beanList;
			while (rs.next()) {
				bean = new CoverityEmitFileNameBean();
				component = rs.getString("component");
				fileNameId = rs.getInt("filenameId");
				parent = rs.getInt("parent");
				fileList.put(fileNameId, component);

				bean.setFileNameId(fileNameId);
				bean.setParent(parent);
				bean.setComponent(component);
				if (compList.containsKey(parent)) {
					compList.get(parent).add(bean);
				} else {
					beanList = new ArrayList<CoverityEmitFileNameBean>();
					beanList.add(bean);
					compList.put(parent, beanList);
				}

			}

			logger.debug("compList=" + compList.size());

			buildFileListByRecursiveResult(compList, fileList, -1);

		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new SdongException(e);
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return fileList;

	}

	private static Connection getConnection(String dbfile) throws SdongException {
		Connection conn = null;
		try {
			// create a database connection
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbfile);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new SdongException(e);
		}

		/*
		 * ResultSetMetaData rsmd = rs.getMetaData();
		 * 
		 * int columnsCount = rsmd.getColumnCount(); for (int i = 1; i <= columnsCount;
		 * i++) { logger.info("column:" + rsmd.getColumnName(i)); }
		 */
		return conn;
	}

	public static Map<Integer, String> buildFileListByRecursiveResult(
			Map<Integer, List<CoverityEmitFileNameBean>> compList, Map<Integer, String> fileList, int start) {
		String component = "";
		String parentComponent = "";
		List<CoverityEmitFileNameBean> childList;
		if (compList.containsKey(start)) {
			childList = compList.get(start);
		} else {
			return fileList;
		}

		for (CoverityEmitFileNameBean child : childList) {
			parentComponent = fileList.get(child.getParent());
			if (parentComponent == null) {
				component = child.getComponent();
			} else {
				component = parentComponent + "/" + child.getComponent();
			}
			fileList.put(child.getFileNameId(), component);
			// when is parent
			if (compList.containsKey(child.getFileNameId())) {
				buildFileListByRecursiveResult(compList, fileList, child.getFileNameId());
			}
		}
		// remove unused node
		fileList.remove(start);

		return fileList;
	}

	public static String getCoverityEmitDBFileContent(String dbfile, int fileId) throws SdongException {
		// List<String> contentList = new ArrayList<String>();
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String content = null;
		byte[] buffer;
		InputStream input = null;
		int contentSize;
		ByteArrayOutputStream outputStream =null;

		try {
			conn = getConnection(dbfile);
			pstmt = conn.prepareStatement(SQL_COVERITY_FILE_CONTENT);
			pstmt.setInt(1, fileId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				contentSize = rs.getInt("contentSize");
				outputStream = new ByteArrayOutputStream(contentSize);
				input = rs.getBinaryStream("contents");
				buffer = new byte[BUFFER_SIZE];
				while (input.read(buffer) > 0) {
					outputStream.write(buffer);
				}
				input.close();
				content = new String(ZlibUtil.decompress(outputStream.toByteArray()));
			}

			outputStream.close();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new SdongException(e);
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new SdongException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}

			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return content;
	}

	public static String getEmitBlobContent(String dbfile, String sql) throws SdongException {
		String result = null;
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		byte[] buffer = null;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		InputStream input = null;

		try {
			conn = getConnection(dbfile);
			statement = conn.createStatement();
			statement.setQueryTimeout(QUERY_TIMEOUT);
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				input = rs.getBinaryStream("content");
				buffer = new byte[BUFFER_SIZE];
				while (input.read(buffer) > 0) {
					outputStream.write(buffer);
				}

				input.close();
				//result =  new String(ZlibUtil.decompress(outputStream.toByteArray()));
				result =  new String(ZlibUtil.decompress(ZlibUtil.subBytes(outputStream.toByteArray(),0)));
				//result = ZlibUtil.unzip(ZlibUtil.subBytes(outputStream.toByteArray(),0));
				//result = ZlibUtil.unzip(outputStream.toByteArray());
				break;
			}

		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new SdongException(e);
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new SdongException(e);
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}

			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}

		return result;
	}
}
