package com.coorun.logcreate;

import com.coorun.entity.RawMessage;

/**
 * ��־���ýӿ�
 *
 */
public interface ILogCollection {
	// д������־
	public void setException(Class claaz, RawMessage message);

	// д��־ҪԼ
	public void setLog(Class claaz, RawMessage message);
}
