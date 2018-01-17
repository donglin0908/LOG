package com.coorun.entity;

/**
 * ԭʼ��־��Ϣ
 *
 */
public class RawMessage {
	// ��־����
	private String code;
	// ��־ҪԼ
	private String content;

	// ��־��Դ
	private String origin;
	// ��½��
	private String userName;
	// ������
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
