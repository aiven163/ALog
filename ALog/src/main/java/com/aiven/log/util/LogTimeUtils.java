package com.aiven.log.util;

import java.util.Calendar;

/**
 * @author Aiven
 * @date 2014-6-3  6:11:24
 * @email aiven163@sina.com
 * @Description
 */
public class LogTimeUtils {

    private Calendar calendar;

    private static LogTimeUtils mInstance;

    private LogTimeUtils() {
    }

    public static LogTimeUtils getInstance() {
        if (mInstance == null)
            mInstance = new LogTimeUtils();
        return mInstance;
    }

    public String getTime() {
        if (calendar == null)
            calendar = Calendar.getInstance();
        StringBuffer bf = new StringBuffer();
        bf.append(getDate());
        bf.append(" ");
        bf.append(calendar.get(Calendar.HOUR_OF_DAY) < 10 ? "0"
                + calendar.get(Calendar.HOUR_OF_DAY) : calendar
                .get(Calendar.HOUR_OF_DAY));
        bf.append(":");
        bf.append(calendar.get(Calendar.MINUTE) < 10 ? "0"
                + calendar.get(Calendar.MINUTE) : calendar.get(Calendar.MINUTE));
        bf.append(":");
        bf.append(calendar.get(Calendar.SECOND) < 10 ? "0"
                + calendar.get(Calendar.SECOND) : calendar.get(Calendar.SECOND));
        return bf.toString();
    }

    public String getDate() {
        if (calendar == null)
            calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        StringBuffer bf = new StringBuffer();
        bf.append(calendar.get(Calendar.YEAR));
        bf.append("-");
        bf.append(calendar.get(Calendar.MONTH) < 9 ? "0"
                + (calendar.get(Calendar.MONTH) + 1) : (calendar
                .get(Calendar.MONTH) + 1));
        bf.append("-");
        bf.append(calendar.get(Calendar.DAY_OF_MONTH) < 10 ? "0"
                + calendar.get(Calendar.DAY_OF_MONTH) : calendar
                .get(Calendar.DAY_OF_MONTH));
        return bf.toString();
    }

}
