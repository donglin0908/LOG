package com.coorun.logcreate;

import com.coorun.entity.MessageBean;
import com.coorun.entity.RawMessage;

/**
 * ��־�ӿ�
 *
 */
public interface ILogAppender {
	// ��������ȡmessagebean
	public MessageBean getMessage();

	// ��־д�뷽ʽ�������
	public void setMessage(RawMessage message, Class clasz);

	// ��־д�����ݿ�
	public void write2DB(MessageBean message);

	// ��־д���ļ�
	public void write2File(MessageBean message, Class clasz);
}
