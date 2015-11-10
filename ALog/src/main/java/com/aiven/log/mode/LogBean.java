package com.aiven.log.mode;

import com.aiven.log.Logs;
import com.aiven.log.util.LogTimeUtils;

/**
 * Author: Aiven
 * Email : aiven163@sina.com
 * Date : 2015/11/1  11:42
 * Desc :
 */
public class LogBean {

    private long mTime;
    private int type;
    private String timeStr;
    private String tag = "ALOG";
    private String message;


    public LogBean(String message) {
        this("NULL", message);
    }

    public LogBean(String tag, String message) {
        this(tag, message, Logs.LOG_TYPE_COMMON);
    }

    public LogBean(String tag, String message, int type) {
        this.message = message;
        this.tag = tag;
        this.type = type;
        mTime = System.currentTimeMillis();
        timeStr = LogTimeUtils.getInstance().getTime();
    }

    public long getTime() {
        return mTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuffer bf = new StringBuffer(timeStr);
        bf.append("  ");
        bf.append(tag);
        bf.append("  ");
        bf.append(message);
        return bf.toString();
    }
}
