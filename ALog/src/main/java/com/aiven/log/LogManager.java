package com.aiven.log;

import android.app.Application;

import com.aiven.log.core.WholeCrashHandler;
import com.aiven.log.core.writer.BaseWriter;
import com.aiven.log.core.writer.CWriter;
import com.aiven.log.core.writer.CrashWriter;
import com.aiven.log.core.writer.ExceptionWriter;
import com.aiven.log.mode.LogBean;
import com.aiven.log.util.LogFileUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Author: Aiven
 * Email : aiven163@sina.com
 * Date : 2015/11/1  10:57
 * Desc :
 */
public class LogManager {

    /**
     * 关闭
     **/
    public static final int CLOSE = 0;
    /**
     * 只输出日志
     **/
    public static final int ONLY_PRINT_LOG = 1;
    /**
     * 打印并保存文件
     **/
    public static final int PRINT_AND_SAVE = 2;

    /**
     * 只抓取全局崩溃
     */
    public static final int ONLY_COLLECT_CRASH = 3;

    private static int switchState = CLOSE;

    public static boolean printAble;

    private WholeCrashHandler crashHandler;

    private static LogManager mInstance;
    private LogLinkQueueIterator logLinkIterator;
    private ArrayBlockingQueue<LogBean> logQueue;
    private String rootPath;
    private Map<Integer, BaseWriter> mWriteMap;

    private LogManager() {
        mWriteMap = new HashMap<>();
    }

    public static LogManager getInstance() {
        if (mInstance == null) {
            mInstance = new LogManager();
        }
        return mInstance;
    }

    /**
     * 初始化日志管理器
     *
     * @param state             日志输出状态标签,默认关闭{@link #CLOSE },仅输出日志，不保存文件{@link #ONLY_PRINT_LOG},打印日志，
     *                          并保存日志文件{@link #PRINT_AND_SAVE},只抓取全局崩溃信息{@link #ONLY_COLLECT_CRASH}
     * @param rootDirectoryPath 日志文件存储跟目录
     * @return
     */
    public LogManager initLogManager(int state, String rootDirectoryPath) {
        this.switchState = state;
        this.rootPath = rootDirectoryPath;
        if (state == CLOSE) {
            printAble = false;
        } else {
            printAble = true;
        }
        if (state == PRINT_AND_SAVE && logLinkIterator == null) {
            logLinkIterator = new LogLinkQueueIterator();
            logQueue = new ArrayBlockingQueue<LogBean>(30);
            logLinkIterator.start();
        }
        return this;
    }

    /**
     * 开启全局崩溃异常抓取
     *
     * @param application
     * @return
     */
    public LogManager openGlobalCrashHanler(Application application) {
        crashHandler = WholeCrashHandler.getInstance();
        crashHandler.init(application.getApplicationContext());
        return this;
    }

    public int getState() {
        return this.switchState;
    }


    public void pushLog(LogBean bean) {
        if (bean.getType() == Logs.LOG_TYPE_CRASH) {
            writeCrash(bean);
            return;
        }
        if (bean != null && logQueue != null) {
            try {
                logQueue.put(bean);
            } catch (Exception e) {
                logQueue.clear();
            }
        }
    }

    private void writeCrash(LogBean bean) {
        if (switchState == CLOSE || switchState == ONLY_PRINT_LOG) {
            return;
        }
        BaseWriter writer = mWriteMap.get(Logs.LOG_TYPE_CRASH);
        if (writer == null) {
            writer = new CrashWriter();
            mWriteMap.put(Logs.LOG_TYPE_CRASH, writer);
        }
        writer.startWrite(bean);
    }

    private class LogLinkQueueIterator extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    LogBean bean = logQueue.take();
                    BaseWriter writer = mWriteMap.get(bean.getType());
                    if (bean.getType() == Logs.LOG_TYPE_EXCEPTION) {
                        if (writer == null) {
                            writer = new ExceptionWriter();
                            mWriteMap.put(bean.getType(), writer);
                        }
                    } else {
                        if (writer == null) {
                            writer = new CWriter();
                            mWriteMap.put(bean.getType(), writer);
                        }
                    }
                    writer.startWrite(bean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public String getRootPath() {
        return rootPath;
    }

    public void clearLog() {
        synchronized (mWriteMap) {
            File file = new File(getRootPath());
            LogFileUtil.deleteDirectory(file);
            mWriteMap.clear();
        }
    }
}
