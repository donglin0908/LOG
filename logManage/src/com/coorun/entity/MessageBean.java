package com.coorun.entity;

import java.util.Date;

/**
 * 日志内容
 *
 */
public class MessageBean {
	// 日志等级
	private String level;// 日志级别

	private String code;// 日志内容
	// 日志内容
	private String content;// 日志要约

	private Date datetime;// 日志时间

	private String className;// 日志类名

	public MessageBean(String level, String code, String content, Date datetime, String className) {
		this.level = level;
		this.code = code;
		this.content = content;
		this.datetime = datetime;
		this.className = className;
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

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Override
	public String toString() {
		return "MessageBean [level=" + level + ", code=" + code + ", content=" + content + ", datetime=" + datetime
				+ ", className=" + className + "]";
	}

}
