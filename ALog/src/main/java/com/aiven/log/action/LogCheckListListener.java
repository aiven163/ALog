package com.aiven.log.action;

import com.aiven.log.model.LogMode;

/**
 * @author Aiven
 * @date 2014-6-3  下午6:10:34
 * @email aiven163@sina.com
 * @Description 日志输出监听器
 */
public interface LogCheckListListener {
	public LogMode backNextLog();
}
