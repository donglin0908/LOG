package com.coorun.entity;

/**
 * 原始日志信息
 *
 */
public class RawMessage {
	// 日志内容
	private String code;
	// 日志要约
	private String content;

	// 日志来源
	private String origin;
	// 登陆者
	private String userName;
	// 方法名
	private String methodName;

	public RawMessage(String code, String content) {
		super();
		this.code = code;
		this.content = content;
	}

	public RawMessage(String code, String content, String origin, String userName, String methodName) {
		super();
		this.code = code;
		this.content = content;
		this.origin = origin;
		this.userName = userName;
		this.methodName = methodName;
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

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	@Override
	public String toString() {
		return "RawMessage [code=" + code + ", content=" + content + ", origin=" + origin + ", userName=" + userName
				+ ", methodName=" + methodName + "]";
	}

}
