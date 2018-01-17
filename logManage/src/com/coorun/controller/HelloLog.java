package com.coorun.controller;

import com.coorun.entity.RawMessage;
import com.coorun.logcreate.impl.LogCollectionImpl;

public class HelloLog {
	public static void main(String[] args) {
		RawMessage rmsg = new RawMessage();
		// 生成日志
		new LogCollectionImpl().setLog(HelloLog.class, rmsg);
	}
}
