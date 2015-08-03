package com.aiven.log.action;

import java.io.File;
import java.io.FileOutputStream;

import android.os.Environment;

import com.aiven.log.LogConfig;
import com.aiven.log.model.LogMode;
import com.aiven.log.util.LogPrintWriter;
import com.aiven.log.util.LogTimeUtils;

/**
 * @author Aiven
 * @date 2014-6-3 下午6:11:55
 * @email aiven163@sina.com
 * @Description 日志写入文件工具类
 */
public class LogWriteUtils extends Thread {

	private LogPrintWriter mOs;
	private LogCheckListListener listener;

	public LogWriteUtils(LogCheckListListener listener) {
		this.listener = listener;
	}

	@Override
	public void run() {
		try {
			while (true) {
				LogMode mode = listener.backNextLog();
				writeLog(mode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	synchronized private void writeLog(LogMode mode) {
		if (mode == null)
			return;
		try {
			initOs(mode);
			if (mOs != null) {
				mOs.println(mode.getTime() + "  " + mode.getTag() + "  " + mode.getMsg());
				mOs.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
			mOs = null;
		} finally {
			mode = null;
		}
	}

	private void initOs(LogMode mode) {
		String filePath;
		if (mode.isCrashInfo()) {
			filePath = LogConfig.SAVE_PATH + "crash" + File.separator + LogTimeUtils.getInstance().getData() + ".txt";
		} else {
			filePath = LogConfig.SAVE_PATH + LogTimeUtils.getInstance().getData() + ".txt";
		}
		File f = new File(filePath);
		if (checkFile(f)) {// 如果文件存在
			if (mOs != null) {// 如果文件流已经打开
				if ((mOs.isCrashWriter() && mode.isCrashInfo()) || (!mOs.isCrashWriter() && !mode.isCrashInfo())) {
					// 要么同时时崩溃，要么同时不失崩溃
					return;
				} else {
					try {
						mOs.close();
					} catch (Exception e) {
					} finally {
						mOs = null;
					}
				}
			}
		}
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			try {
				if (!f.exists()) {
					try {
						f.getParentFile().mkdirs();
					} catch (Exception e) {
						e.printStackTrace();
					}
					f.createNewFile();
				}
				mOs = new LogPrintWriter(new FileOutputStream(f, true));
			} catch (Exception e) {
				e.printStackTrace();
				mOs = null;
			} finally {
				f = null;
			}
		} else {
			mOs = null;
			f = null;
		}
	}

	private boolean checkFile(File f) {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			try {
				if (!f.exists()) {
					return false;
				} else {
					return true;
				}
			} catch (Exception e) {
				return false;
			}
		} else {
			f = null;
		}
		return false;
	}

	public void startEngine() {
		start();
	}

}
