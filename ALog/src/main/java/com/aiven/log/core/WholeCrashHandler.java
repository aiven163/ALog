package com.aiven.log.core;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.aiven.log.LogManager;
import com.aiven.log.Logs;
import com.aiven.log.mode.LogBean;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;

public class WholeCrashHandler implements UncaughtExceptionHandler {
    private static WholeCrashHandler crashHandler = new WholeCrashHandler();
    private List<AppInfo> mAppInfoMap;
    private PackageManager mPkgManager;
    private PackageInfo mPkgInfo;

    private WholeCrashHandler() {
    }

    public static WholeCrashHandler getInstance() {
        return crashHandler;
    }

    private UncaughtExceptionHandler mDefaultHandler;
    private Context context;

    public void init(Context context) {
        this.context = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        initCfg(context);
    }

    private void initCfg(Context context) {
        if (context == null)
            return;
        try {
            mPkgManager = context.getPackageManager();
            mPkgInfo = mPkgManager.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
        } catch (Exception e) {
        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    /**
     * 异常处理
     *
     * @param ex 异常信息
     * @return boolean true 已处理，false 未处理
     */
    private boolean handleException(Throwable ex) {
        if (ex == null)
            return false;
        if (LogManager.getInstance().getState() == LogManager.CLOSE
                || LogManager.getInstance().getState() == LogManager.ONLY_PRINT_LOG)
            return false;
        if (context == null)
            return false;
        collectDeviceInfo();
        String log = saveCrashInfo2File(ex);
        Log.e("crash", log);
        LogManager.getInstance().pushLog(new LogBean("crash", log, Logs.LOG_TYPE_CRASH));
        if (mAppInfoMap != null) {
            mAppInfoMap.clear();
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
        sb.append("错误原因:" + ex.toString() + "\n");
        sb.append("具体如下:\n=======================================================\n");
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
        return sb.toString();
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
                mAppInfoMap.add(new AppInfo("apk基本信息", ""));
                mAppInfoMap.add(new AppInfo("versionName", versionName));
                mAppInfoMap.add(new AppInfo("versionCode", versionCode));
                mAppInfoMap.add(new AppInfo("设备基本信息", ""));
                mAppInfoMap.add(new AppInfo("型号", Build.MODEL));
                mAppInfoMap.add(new AppInfo("SDK", String.valueOf(Build.VERSION.SDK_INT)));
                mAppInfoMap.add(new AppInfo("SystemVersion", Build.VERSION.RELEASE));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mAppInfoMap.add(new AppInfo("SUPPORTED_ABIS", arrToStr(Build.SUPPORTED_ABIS)));
                    mAppInfoMap.add(new AppInfo("SUPPORTED_32_BIT_ABIS", arrToStr(Build.SUPPORTED_32_BIT_ABIS)));
                    mAppInfoMap.add(new AppInfo("SUPPORTED_64_BIT_ABIS", arrToStr(Build.SUPPORTED_64_BIT_ABIS)));
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
            if (TextUtils.isEmpty(value)) {
                return key + "\n";
            }
            return key + "=" + value + "\n";
        }
    }
}
