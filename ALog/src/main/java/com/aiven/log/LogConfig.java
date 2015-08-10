package com.aiven.log;

import java.io.File;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

/**
 * @author Aiven
 * @date 2014-6-3  下午6:12:24
 * @email aiven163@sina.com
 * @Description 日志管理配置
 */
public class LogConfig {
    /**
     * 是否开启Debug
     */
    public static boolean Debug = false;
    /**
     * 谁否记录在客户端
     */
    public static boolean recodeAble = false;
    /**
     * 存储路径根目录文件夹路径:注意，一定是文件夹路径
     */
    public static String logFileSavePath = "";

    /**
     * 日志目录文件夹名称(一般为应用名称)
     */
    public static final String appRootName = "log";

    public static final String DEFAULT_TAG = "--APPLOG--";

    public static String getLogSavePath() {
        if (!TextUtils.isEmpty(logFileSavePath)) {
            return logFileSavePath + File.separator + appRootName + File.separator;
        } else {
            return Environment
                    .getExternalStorageDirectory() + File.separator + appRootName + File.separator;
        }
    }

    public static void configGlobleCrash(Context context) {

    }
}
