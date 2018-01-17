package com.coorun.entity;

import java.util.Date;

/**
 * 日志内容
 *
 */
public class MessageBean {
	// 日志来源
	private String origin;
	// 登陆者
	private String userName;
	// 日志类名
	private String className;
	// 方法名
	private String methodName;
	// 日志级别
	private String level;
	// 日志内容
	private String code;
	// 日志要约
	private String content;
	// 日志时间
	private Date datetime;

	public MessageBean(String origin, String userName, String className, String methodName, String level, String code,
			String content, Date datetime) {
		super();
		this.origin = origin;
		this.userName = userName;
		this.className = className;
		this.methodName = methodName;
		this.level = level;
		this.code = code;
		this.content = content;
		this.datetime = datetime;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	@Override
	public String toString() {
		return "MessageBean [origin=" + origin + ", userName=" + userName + ", className=" + className + ", methodName="
				+ methodName + ", level=" + level + ", code=" + code + ", content=" + content + ", datetime=" + datetime
				+ "]";
	}

}
