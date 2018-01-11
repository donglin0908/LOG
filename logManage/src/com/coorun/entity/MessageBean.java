package com.coorun.entity;

import java.util.Date;

/**
 * ��־����
 *
 */
public class MessageBean {
	// ��־�ȼ�
	private String level;// ��־����

	private String code;// ��־����
	// ��־����
	private String content;// ��־ҪԼ

	private Date datetime;// ��־ʱ��

	private String className;// ��־����

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
