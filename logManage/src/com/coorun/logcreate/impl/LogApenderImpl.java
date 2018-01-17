package com.coorun.logcreate.impl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
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
	ConcurrentHashMap<String, Logger> hashMap = new ConcurrentHashMap();

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
	public void write2File(MessageBean message, Class clasz) {
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
			logger.debug(message.getCode() + " - " + message.getContent());
			break;
		case INFO:
			logger.info(message.getCode() + " - " + message.getContent());
			break;
		case WARNING:
			logger.warn(message.getCode() + " - " + message.getContent());
			break;
		default:
			logger.error(message.getCode() + " - " + message.getContent());
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
				String tableName = logConfig.getDbTableName();
				if ((DBTypeEnum.ORACLE).equals(logConfig.getDbType())) {
					sql = "insert into " + tableName
							+ " (ID,ORIGIN,USERNAME,CLASSNAME,METHODNAME,LOGLEVEL,LOGCODE,CONTENT,DATETIME) values ("
							+ tableName + "_SEQ.NEXTVAL,?,?,?,?,?,?,?,sysdate)";
				} else if ((DBTypeEnum.MYSQL).equals(logConfig.getDbType())) {
					sql = "insert into " + tableName
							+ " (ID,ORIGIN,USERNAME,CLASSNAME,METHODNAME,LOGLEVEL,LOGCODE,CONTENT,DATETIME) values (null,?,?,?,?,?,?,?,now())";
				} else {// 预留其他类型数据库SQL
					
				}
				try {
					connection = getConnecion();
					PreparedStatement ps = connection.prepareStatement(sql);
					ps.setString(1, message.getOrigin());
					ps.setString(2, message.getUserName());
					ps.setString(3, message.getClassName());
					ps.setString(4, message.getMethodName());
					ps.setString(5, message.getLevel());
					ps.setString(6, message.getCode());
					ps.setString(7, message.getContent());
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
	private MessageBean assembleMessage(RawMessage message, Class clasz) {
		MessageBean newMessage = new MessageBean(message.getOrigin() == null ? "" : message.getOrigin(),
				message.getUserName() == null ? "" : message.getUserName(), clasz.getName(),
				message.getMethodName() == null ? "" : message.getMethodName(), logConfig.getLogType().toString(),
				message.getCode(), message.getContent(), new Date(System.currentTimeMillis()));
		return newMessage;
	}

	@Override
	/**
	 * 调用日志功能
	 */
	public void setMessage(RawMessage message, Class clasz) {
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
