package com.coorun.logcreate;

import com.coorun.entity.RawMessage;

/**
 * 日志调用接口
 *
 */
public interface ILogCollection {
	// 写日志内容
	public void setException(Class<?> claaz, RawMessage message);

	// 写日志要约
	public void setLog(Class<?> claaz, RawMessage message);
}
