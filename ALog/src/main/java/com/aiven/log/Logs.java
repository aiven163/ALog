package com.aiven.log;

import android.util.Log;

import com.aiven.log.mode.LogBean;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Author: Aiven
 * Email : aiven163@sina.com
 * Date : 2015/11/1  10:58
 * Desc :
 */
public class Logs {

    public static final int LOG_TYPE_COMMON = 0X101;
    public static final int LOG_TYPE_EXCEPTION = 0X202;
    public static final int LOG_TYPE_CRASH = 0X303;


    /**
     * 输出异常信息
     *
     * @param e
     * @Description
     * @author Aiven
     */
    public static void logE(Exception e) {
        if (e == null)
            return;
        if (LogManager.printAble) {
            e.printStackTrace();
            if (LogManager.getInstance().getState() == LogManager.PRINT_AND_SAVE) {
                Writer writer = new StringWriter();
                PrintWriter printWriter = new PrintWriter(writer);
                e.printStackTrace(printWriter);
                Throwable cause = e.getCause();
                while (cause != null) {
                    cause.printStackTrace(printWriter);
                    cause = cause.getCause();
                }
                printWriter.close();
                String result = writer.toString();
                writer = null;
                LogManager.getInstance().pushLog(new LogBean("ERROR", result, LOG_TYPE_EXCEPTION));
            }
        }
    }

    /**
     * 通过Logcat输出日志信息
     *
     * @param tag
     * @param msg
     */
    public static void logError(String tag, String msg) {
        if (msg == null)
            msg = "null";
        if (LogManager.printAble) {
            Log.e(tag, msg);
            if (LogManager.getInstance().getState() == LogManager.PRINT_AND_SAVE) {
                LogManager.getInstance().pushLog(new LogBean(tag, msg));
            }
        }
    }

    /**
     * 通过Logcat输出日志信息
     *
     * @param tag
     * @param msg
     * @Description
     * @author Aiven
     */
    public static void logI(String tag, String msg) {
        if (msg == null)
            msg = "null";
        if (LogManager.printAble) {
            Log.i(tag, msg);
            if (LogManager.getInstance().getState() == LogManager.PRINT_AND_SAVE) {
                LogManager.getInstance().pushLog(new LogBean(tag, msg));
            }
        }
    }

    /**
     * 通过Logcat输出日志信息
     *
     * @param tag
     * @param msg
     * @Description
     * @author Aiven
     */
    public static void logV(String tag, String msg) {
        if (msg == null)
            msg = "null";
        if (LogManager.printAble) {
            Log.v(tag, msg);
            if (LogManager.getInstance().getState() == LogManager.PRINT_AND_SAVE) {
                LogManager.getInstance().pushLog(new LogBean(tag, msg));
            }
        }
    }

    /**
     * 通过Logcat输出日志信息
     *
     * @param tag
     * @param msg
     * @Description
     * @author Aiven
     */
    public static void logW(String tag, String msg) {
        if (msg == null)
            msg = "null";
        if (LogManager.printAble) {
            Log.w(tag, msg);
            if (LogManager.getInstance().getState() == LogManager.PRINT_AND_SAVE) {
                LogManager.getInstance().pushLog(new LogBean(tag, msg));
            }
        }
    }

    /**
     * 通过Logcat输出日志信息
     *
     * @param tag
     * @param msg
     * @Description
     * @author Aiven
     */
    public static void logD(String tag, String msg) {
        if (msg == null)
            msg = "null";
        if (LogManager.printAble) {
            Log.d(tag, msg);
            if (LogManager.getInstance().getState() == LogManager.PRINT_AND_SAVE) {
                LogManager.getInstance().pushLog(new LogBean(tag, msg));
            }
        }
    }

    /**
     * 通过打印模式输出日志
     *
     * @param tag
     * @param msg
     * @Description
     * @author Aiven
     */
    public static void logPrint(String tag, String msg) {
        if (msg == null) {
            msg = "null";
        }
        if (LogManager.printAble) {
            System.out.println(tag + "  " + msg);
            if (LogManager.getInstance().getState() == LogManager.PRINT_AND_SAVE) {
                LogManager.getInstance().pushLog(new LogBean(tag, msg));
            }
        }
    }

    /**
     * 通过打印模式输出日志
     *
     * @param tag
     * @param msg
     * @Description
     * @author Aiven
     */
    public static void logPrint(String tag, Object msg) {
        if (msg == null) {
            msg = "null";
        }
        if (LogManager.printAble) {
            System.out.println(tag + "  " + msg);
            if (LogManager.getInstance().getState() == LogManager.PRINT_AND_SAVE) {
                LogManager.getInstance().pushLog(new LogBean(tag, msg.toString()));
            }
        }
    }

    /**
     * 通过打印模式输出日志
     *
     * @param msg
     * @Description
     * @author Aiven
     */
    public static void logPrint(Object msg) {
        if (msg == null) {
            msg = "null";
        }
        if (LogManager.printAble) {
            System.out.println(msg);
            if (LogManager.getInstance().getState() == LogManager.PRINT_AND_SAVE) {
                LogManager.getInstance().pushLog(new LogBean("", msg.toString()));
            }
        }
    }


    /**
     * 清除本地所有的日志文件
     */
    public static void clearLogFiles() {
        LogManager.getInstance().clearLog();
    }


}
