package com.niiwoo.util.log.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import android.os.Environment;

import com.niiwoo.util.log.LogConfig;

/**
 * @author Aiven
 * @date 2014-6-3 下午6:11:55
 * @email aiven163@sina.com
 * @Description 日志写入文件工具类
 */
public class LogWriteUtils extends Thread {

	private static LogWriteUtils mInstance;
	private boolean isRunning;
	private LogCheckListListener listener;
	private PrintWriter mOs;
	private boolean isStating = false;

	private static final long SLEEP_TIME = 1 * 100;

	public static LogWriteUtils getInstance() {
		if (mInstance == null)
			mInstance = new LogWriteUtils();
		return mInstance;
	}

	private LogWriteUtils() {
		isRunning = false;
	};

	public void setCheckListListener(LogCheckListListener listener) {
		this.listener = listener;
	}

	@Override
	public void run() {
		isRunning = true;
		try {
			while (isRunning) {
				if (listener != null) {
					LogMode mode = listener.backNextLog();
					writeLog(mode);
				}
				sleep(SLEEP_TIME);
			}
		} catch (Exception e) {
			e.printStackTrace();
			isRunning = false;
		}
	}

	public LogCheckListListener getListener() {
		return listener;
	}

	public void setListener(LogCheckListListener listener) {
		this.listener = listener;
	}

	synchronized private void writeLog(LogMode mode) {
		if (mode == null)
			return;
		try {
			if (mOs == null || !checkFile()) {
				initOs();
			}
			if (mOs != null) {
				mOs.println(mode.getTime() + "  " + mode.getTag() + "  "
						+ mode.getMsg());
				mOs.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
			mOs = null;
		} finally {
			mode = null;
		}
	}

	private void initOs() {
		String filePath = LogConfig.SAVE_PATH
				+ LogTimeUtils.getInstance().getData() + ".txt";
		File f = new File(filePath);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			try {
				if (!f.exists()) {
					try {
						f.getParentFile().mkdirs();
					} catch (Exception e) {
						e.printStackTrace();
					}
					f.createNewFile();
				}
				mOs = new PrintWriter(new FileOutputStream(f, true));
			} catch (Exception e) {
				e.printStackTrace();
				mOs = null;
			}
		} else {
			mOs = null;
		}
	}

	private boolean checkFile() {
		String filePath = LogConfig.SAVE_PATH
				+ LogTimeUtils.getInstance().getData() + ".txt";
		File f = new File(filePath);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			try {
				if (!f.exists()) {
					return false;
				} else {
					return true;
				}
			} catch (Exception e) {
				return false;
			} finally {
				f = null;
			}
		} else {
			f = null;
		}
		return false;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public void startEngine() {
		try {
			if (isStating)
				return;
			isStating = true;
			isRunning = false;
			new Thread() {
				public void run() {
					try {
						sleep(10 * SLEEP_TIME + 1);
						if (isRunning)
							return;
						mInstance.start();
					} catch (Exception e) {
						e.printStackTrace();
						isRunning = false;
						try {
							sleep(SLEEP_TIME + 1);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					} finally {
						isStating = false;
					}
				};
			}.start();
		} catch (Exception e) {
			e.printStackTrace();
			isStating = false;
			isRunning = false;
			try {
				sleep(SLEEP_TIME + 1);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
