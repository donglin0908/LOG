package com.coorun.entity;

/**
 * ԭʼ��־��Ϣ
 *
 */
public class RawMessage {

	private String code;// ��־����

	private String content;// ��־ҪԼ

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
