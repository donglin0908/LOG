package com.coorun.logcreate.impl;

import com.coorun.entity.RawMessage;
import com.coorun.logcreate.ILogAppender;
import com.coorun.logcreate.ILogCollection;

/**
 * 
 * ��־���ýӿ�ʵ��
 *
 */
public class LogCollectionImpl implements ILogCollection {

	ILogAppender logAppender = new LogApenderImpl();

	@Override
	public void setException(Class<?> claaz, RawMessage message) {
		logAppender.setMessage(message, claaz);
	}

	@Override
	public void setLog(Class<?> claaz, RawMessage message) {
		logAppender.setMessage(message, claaz);
	}

}
