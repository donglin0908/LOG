package com.coorun.entity;

import com.coorun.constant.DBTypeEnum;
import com.coorun.constant.LevelEnum;
import com.coorun.constant.LogStatusEnum;

/**
 * 配置文件
 *
 */
public class LogConfig {
	// 是否开启日志功能
	private boolean isLog;
	// 日志级别
	private LevelEnum logType;
	// 存储日志方式
	private LogStatusEnum log2Where;
	// 数据库类型
	private DBTypeEnum dbType;
	// 数据库表名
	private String dbTableName;
	// DBdriverName
	private String dbUrl;
	// DBurl
	private String dbDriverName;
	// DBusername
	private String dbUsername;
	// DBpassword
	private String dbPassword;

	public boolean isLog() {
		return isLog;
	}

	public void setLog(boolean isLog) {
		this.isLog = isLog;
	}

	public LevelEnum getLogType() {
		return logType;
	}

	public void setLogType(LevelEnum logType) {
		this.logType = logType;
	}

	public LogStatusEnum getLog2Where() {
		return log2Where;
	}

	public void setLog2Where(LogStatusEnum log2Where) {
		this.log2Where = log2Where;
	}

	public DBTypeEnum getDbType() {
		return dbType;
	}

	public void setDbType(DBTypeEnum dbType) {
		this.dbType = dbType;
	}

	public String getDbTableName() {
		return dbTableName;
	}

	public void setDbTableName(String dbTableName) {
		this.dbTableName = dbTableName;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDbDriverName() {
		return dbDriverName;
	}

	public void setDbDriverName(String dbDriverName) {
		this.dbDriverName = dbDriverName;
	}

	public String getDbUsername() {
		return dbUsername;
	}

	public void setDbUsername(String dbUsername) {
		this.dbUsername = dbUsername;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

}
