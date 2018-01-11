package com.coorun.logcreate;

import com.coorun.entity.MessageBean;
import com.coorun.entity.RawMessage;

/**
 * 日志接口
 *
 */
public interface ILogAppender {
	// 第三方获取messagebean
	public MessageBean getMessage();

	// 日志写入方式调用入口
	public void setMessage(RawMessage message, Class clasz);

	// 日志写入数据库
	public void write2DB(MessageBean message);

	// 日志写入文件
	public void write2File(MessageBean message, Class clasz);
}
