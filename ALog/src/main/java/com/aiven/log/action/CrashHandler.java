package com.aiven.log.action;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.aiven.log.LogConfig;
import com.aiven.log.Logs;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Aiven
 * Email : aiven163@sina.com
 * Date : 2015/8/10  14:54
 * Desc :
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static CrashHandler mInstance;
    private Thread.UncaughtExceptionHandler mSystemHandler;
    private List<AppInfo> mAppInfoMap;
    private PackageManager mPkgManager;
    private PackageInfo mPkgInfo;

    private CrashHandler() {
    }

    public static CrashHandler getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new CrashHandler();
            mInstance.init(context);
        }
        return mInstance;
    }

    public void initCfg(Context context) {
        if (context == null)
            return;
        try {
            mPkgManager = context.getPackageManager();
            mPkgInfo = mPkgManager.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
        } catch (Exception e) {
        }
    }

    private void init(Context context) {
        mSystemHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        initCfg(context);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mSystemHandler != null) {
            mSystemHandler.uncaughtException(thread, ex);
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        if (LogConfig.Debug) {
            collectDeviceInfo();
            saveCrashInfo2File(ex);
        }
        return true;
    }


    /**
     * 保存错误信息到文件中 *
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {
        StringBuffer sb = new StringBuffer("\n");
        if (mAppInfoMap != null && mAppInfoMap.size() > 0) {
            synchronized (mAppInfoMap) {
                for (int i = 0; i < mAppInfoMap.size(); i++) {
                    sb.append(mAppInfoMap.get(i).toString());
                }
            }
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        Logs.logCrash("Crash", sb.toString());
        return null;
    }

    /**
     * 收集设备参数信息
     */
    public void collectDeviceInfo() {
        if (mAppInfoMap == null) {
            mAppInfoMap = new ArrayList<>();
        }
        synchronized (mAppInfoMap) {
            if (mPkgInfo != null) {
                String versionName = mPkgInfo.versionName == null ? "null"
                        : mPkgInfo.versionName;
                String versionCode = mPkgInfo.versionCode + "";
                mAppInfoMap.add(new AppInfo("versionName", versionName));
                mAppInfoMap.add(new AppInfo("versionCode", versionCode));
                mAppInfoMap.add(new AppInfo("SDK", String.valueOf(android.os.Build.VERSION.SDK_INT)));
                mAppInfoMap.add(new AppInfo("SystemVersion", android.os.Build.VERSION.RELEASE));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mAppInfoMap.add(new AppInfo("SUPPORTED_ABIS", arrToStr(android.os.Build.SUPPORTED_ABIS)));
                    mAppInfoMap.add(new AppInfo("SUPPORTED_32_BIT_ABIS", arrToStr(android.os.Build.SUPPORTED_32_BIT_ABIS)));
                    mAppInfoMap.add(new AppInfo("SUPPORTED_64_BIT_ABIS", arrToStr(android.os.Build.SUPPORTED_64_BIT_ABIS)));
                } else {
                    mAppInfoMap.add(new AppInfo("CPU_ABI", Build.CPU_ABI));
                    mAppInfoMap.add(new AppInfo("CPU_ABI2", Build.CPU_ABI2));
                }
                mAppInfoMap.add(new AppInfo("Product", Build.PRODUCT));
                mAppInfoMap.add(new AppInfo("deviceID", Build.ID));
                mAppInfoMap.add(new AppInfo("SERIAL", Build.SERIAL));
            }
        }
    }

    private String arrToStr(String[] strs) {
        if (strs != null && strs.length > 0) {
            StringBuffer bf = new StringBuffer();
            for (int i = 0; i < strs.length; i++) {
                bf.append(strs[i]);
                bf.append("     ");
            }
            return bf.toString();
        } else {
            return "NULL";
        }
    }


    class AppInfo {
        String key;
        String value;

        public AppInfo(String key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return key + "=" + value + "\n";
        }
    }

}
