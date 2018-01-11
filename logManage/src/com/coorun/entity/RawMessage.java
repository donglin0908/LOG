package com.coorun.entity;

/**
 * 原始日志信息
 *
 */
public class RawMessage {

	private String code;// 日志内容

	private String content;// 日志要约

	public RawMessage(String code, String content) {
		super();
		this.code = code;
		this.content = content;
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

	@Override
	public String toString() {
		return "RawMessage [code=" + code + ", content=" + content + "]";
	}

}
