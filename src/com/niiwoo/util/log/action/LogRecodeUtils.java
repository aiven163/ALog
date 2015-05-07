package com.niiwoo.util.log.action;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aiven
 * @date 2014-6-3 下午6:11:10
 * @email aiven163@sina.com
 * @Description 日志记录工具类
 */
public class LogRecodeUtils implements LogCheckListListener {
	private static LogRecodeUtils mInstance;
	private List<LogMode> list;

	private LogRecodeUtils() {
		list = new ArrayList<LogMode>();
	};

	public static LogRecodeUtils getInstance() {
		if (mInstance == null)
			mInstance = new LogRecodeUtils();
		return mInstance;
	}

	public void addLog(LogMode mode) {
		if (list == null) {
			list = new ArrayList<LogMode>();
		}
		synchronized (list) {
			list.add(mode);
			if (!LogWriteUtils.getInstance().isRunning()) {
				LogWriteUtils.getInstance().setCheckListListener(mInstance);
				LogWriteUtils.getInstance().startEngine();
			} else if (LogWriteUtils.getInstance().getListener() == null) {
				LogWriteUtils.getInstance().setCheckListListener(mInstance);
			}
			if (list.size() > 300) {
				LogWriteUtils.getInstance().setRunning(false);
			}
		}
	}

	@Override
	public LogMode backNextLog() {
		if (list == null)
			return null;
		synchronized (list) {
			if (list.size() > 0) {
				return list.remove(0);
			} else {
				return null;
			}
		}
	}
}
