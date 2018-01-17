package com.coorun.logcreate.impl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.coorun.constant.DBTypeEnum;
import com.coorun.constant.LevelEnum;
import com.coorun.constant.LogStatusEnum;
import com.coorun.entity.LogConfig;
import com.coorun.entity.MessageBean;
import com.coorun.entity.RawMessage;
import com.coorun.logcreate.ILogAppender;

/**
 * 日志模块入口
 *
 */
public class LogApenderImpl implements ILogAppender {
	// log4j
	Logger logger = Logger.getLogger(getClass());

	// 类名与logger对象的映射
	ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

	// 存储类名与日志
	ConcurrentHashMap<String, Logger> hashMap = new ConcurrentHashMap<String, Logger>();

	// //此管道放置将要写入文件或db中的messagebean
	// ConcurrentLinkedDeque<MessageBean> writeQueue = new
	// ConcurrentLinkedDeque<MessageBean>();

	// 读取配置文件config.properties
	LogConfig logConfig = new LogConfig();

	// 第三方要获取的messagebean
	ConcurrentLinkedDeque<MessageBean> readQueue = new ConcurrentLinkedDeque<MessageBean>();

	// 设置readqueue中的最大消息长度
	int MAXREADQUEUESIZE = 100;

	/**
	 * 创建对象同时读取配置文件，并赋值给全局变量config
	 */
	public LogApenderImpl() {
		// 读取配置文件
		Properties cfg = new Properties();
		String pathUrl = this.getClass().getClassLoader().getResource("").getPath() + "logConfig.properties";
		InputStream in = null;
		try {
			in = new FileInputStream(pathUrl);
			cfg.load(in);
			boolean isLog = false;
			LevelEnum logType = null;
			LogStatusEnum log2Where = null;
			DBTypeEnum dbType = null;
			switch (cfg.getProperty("isLog").trim().toUpperCase()) {
			case "TRUE":
				isLog = true;
				break;
			default:
				isLog = false;
				break;
			}
			switch (cfg.getProperty("logType").trim().toUpperCase()) {
			case "DEBUG":
				logType = LevelEnum.DEBUG;
				break;
			case "INFO":
				logType = LevelEnum.INFO;
				break;
			case "WARN":
				logType = LevelEnum.WARNING;
				break;
			default:
				logType = LevelEnum.ERROR;
				break;
			}
			switch (cfg.getProperty("log2Where").trim().toUpperCase()) {
			case "LOG2DB":
				log2Where = LogStatusEnum.LOG2DB;
				break;
			case "LOG2ALL":
				log2Where = LogStatusEnum.LOG2ALL;
				break;
			default:
				log2Where = LogStatusEnum.LOG2FILE;
				break;
			}

			switch (cfg.getProperty("dbType").trim().toUpperCase()) {
			case "MYSQL":
				dbType = DBTypeEnum.MYSQL;
				break;
			case "POSTGRESQL":
				dbType = DBTypeEnum.POSTGRESQL;
				break;
			default:
				dbType = DBTypeEnum.ORACLE;
				break;
			}
			// 赋值给config变量
			logConfig.setLog(isLog);
			logConfig.setLogType(logType);
			logConfig.setLog2Where(log2Where);
			logConfig.setDbType(dbType);
			logConfig.setDbTableName(cfg.getProperty("dbTableName").trim());
			logConfig.setDbDriverName(cfg.getProperty("dbDriverName").trim());
			logConfig.setDbUrl(cfg.getProperty("dbUrl").trim());
			logConfig.setDbUsername(cfg.getProperty("dbUsername").trim());
			logConfig.setDbPassword(cfg.getProperty("dbPassword").trim());
		} catch (Exception e) {
			logger.error("读取配置文件错误!");
			e.printStackTrace();
		}
	}

	@Override
	/**
	 * 写日志文件
	 */
	public void write2File(MessageBean message, Class<?> clasz) {
		Logger logger;
		// 判断hashMap中的类名
		if ((logger = hashMap.get(clasz.getName())) == null) {
			// 取得logger
			logger = Logger.getLogger(clasz.getName());
			hashMap.put(clasz.getName(), logger);
		}
		// 根据级别记录日志信息
		switch (logConfig.getLogType()) {
		case DEBUG:
			logger.debug(message.getContent());
			break;
		case INFO:
			logger.info(message.getContent());
			break;
		case WARNING:
			logger.warn(message.getContent());
			break;
		default:
			logger.error(message.getContent());
			break;
		}
	}

	@Override
	/**
	 * 日志写入数据库
	 */
	public void write2DB(final MessageBean message) {
		cachedThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				Connection connection = null;
				String sql = null;
				// 数据库表名
				String tableName = logConfig.getDbTableName();
				if ((DBTypeEnum.ORACLE).equals(logConfig.getDbType())) {
					sql = "insert into " + tableName
							+ "(ID,ORIGIN,USERID,CLASSNAME,METHODNAME,LOGLEVEL,LOGCODE,CONTENT,TYPE,LOGINIP,REMARKS,DATETIME)"
							+ "values " + "(?,?,?,?,?,?,?,?,?,?,?,sysdate)";
				} else if ((DBTypeEnum.MYSQL).equals(logConfig.getDbType())) {
					sql = "insert into " + tableName
							+ " (ID,ORIGIN,USERID,CLASSNAME,METHODNAME,LOGLEVEL,LOGCODE,CONTENT,TYPE,LOGINIP,REMARKS,DATETIME) "
							+ "values (null,?,?,?,?,?,?,?,?,?,?,now())";
				} else {// 预留其他类型数据库SQL

				}
				try {
					connection = getConnecion();
					PreparedStatement ps = connection.prepareStatement(sql);
					ps.setString(1, UUID.randomUUID().toString());
					ps.setString(2, message.getOrigin() == null ? "" : message.getOrigin());
					ps.setString(3, message.getUserID() == null ? "" : message.getUserID());
					ps.setString(4, message.getClassName() == null ? "" : message.getClassName());
					ps.setString(5, message.getMethodName() == null ? "" : message.getMethodName());
					ps.setString(6, message.getLevel() == null ? "" : message.getLevel());
					ps.setString(7, message.getCode() == null ? "" : message.getCode());
					ps.setString(8, message.getContent() == null ? "" : message.getContent());
					ps.setString(9, message.getType() == null ? "" : message.getType());
					ps.setString(10, message.getLoginIP() == null ? "" : message.getLoginIP());
					ps.setString(11, message.getRemarks() == null ? "" : message.getRemarks());
					ps.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	@Override
	/**
	 * 组装massageBean
	 */
	public MessageBean getMessage() {
		return readQueue.pop();
	}

	/**
	 * 封装massageBean
	 */
	private MessageBean assembleMessage(RawMessage message, Class<?> clasz) {
		MessageBean msg = new MessageBean();
		msg.setOrigin(message.getOrigin());
		msg.setUserID(message.getUserID());
		msg.setClassName(clasz.getName());
		msg.setMethodName(message.getMethodName());
		msg.setLevel(message.getLevel());
		msg.setCode(message.getCode());
		msg.setContent(message.getContent());
		msg.setLoginIP(message.getLoginIP());
		return msg;
	}

	@Override
	/**
	 * 调用日志功能
	 */
	public void setMessage(RawMessage message, Class<?> clasz) {
		MessageBean e = assembleMessage(message, clasz);
		// writeQueue.add(e);
		if (readQueue.size() > MAXREADQUEUESIZE) {
			readQueue.clear();
		}
		readQueue.add(e);
		if (!logConfig.isLog()) {
			return;
		}
		// 日志写入方式调用
		switch (logConfig.getLog2Where()) {
		case LOG2DB:
			write2DB(e);
			break;
		case LOG2ALL:
			write2File(e, clasz);
			write2DB(e);
			break;
		default:
			write2File(e, clasz);
			break;
		}
	}

	/**
	 * 调用第三方数据库连接池接口待实现
	 */
	private Connection getConnecion() {
		Connection conn = null;
		String driverName = logConfig.getDbDriverName();
		String url = logConfig.getDbUrl();
		String username = logConfig.getDbUsername();
		String password = logConfig.getDbPassword();
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

}
