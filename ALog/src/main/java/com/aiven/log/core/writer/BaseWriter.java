package com.aiven.log.core.writer;

import com.aiven.log.mode.LogBean;
import com.aiven.log.util.LogTimeUtils;

import java.io.PrintWriter;

/**
 * Author: Aiven
 * Email : aiven163@sina.com
 * Date : 2015/11/1  11:37
 * Desc :
 */
public abstract class BaseWriter {

    protected PrintWriter mWriter;

    public BaseWriter() {
        initWriter();
    }

    abstract public void initWriter();

    public void startWrite(LogBean log) {
        checkIsValid(log);
        if (mWriter == null)
            return;
        mWriter.println(log.toString());
        mWriter.flush();
    }

    protected void checkIsValid(LogBean log) {
        if (mWriter == null) {
            initWriter();
            return;
        }
        if ((System.currentTimeMillis() / 1000 / 60 / 60 / 24) != (log.getTime() / 1000 / 60 / 60 / 24)) {
            mWriter = null;
            initWriter();
        }
    }

    protected String getFileName() {
        return LogTimeUtils.getInstance().getDate() + ".txt";
    }

}
