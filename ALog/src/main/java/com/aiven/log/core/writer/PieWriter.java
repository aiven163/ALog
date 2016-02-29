package com.aiven.log.core.writer;

import android.text.TextUtils;

import com.aiven.log.LogManager;
import com.aiven.log.core.IFullLinkQueueListener;
import com.aiven.log.mode.LogBean;
import com.aiven.log.util.LogFileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * Author: Aiven
 * Email : aiven163@sina.com
 * Date : 2016/2/29  11:23
 * Desc :
 */
public class PieWriter extends BaseWriter implements Runnable {

    private List<LogBean> beanList;
    private List<LogBean> nextList;
    private IFullLinkQueueListener mListener;
    private String tag;
    private Thread thread;

    public PieWriter(List<LogBean> list, IFullLinkQueueListener listener) {
        this.beanList = list;
        this.mListener = listener;
        tag = beanList.get(0).getTag();
        thread = new Thread(this);
    }

    public void setNextList(List<LogBean> list) {
        this.nextList = list;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public void initWriter() {
        if (!TextUtils.isEmpty(tag)) {
            File file = LogFileUtil.createFileIfNotExist(LogManager.getInstance().getRootPath() + File.separator + "sub" + File.separator + tag, getFileName());
            try {
                mWriter = new PrintWriter(new FileOutputStream(file, true));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void startWrite(LogBean log) {
        checkIsValid(log);
        if (mWriter == null)
            return;
        mWriter.println(log.toString());
    }

    public void over(boolean b) {
        if (b) {
            mWriter.flush();
        }
        if (mListener != null) {
            mListener.writeOver(tag);
        }
    }

    @Override
    public void run() {
        boolean b = false;
        if (beanList != null && beanList.size() > 0) {
            b = true;
            for (int i = 0; i < beanList.size(); i++) {
                startWrite(beanList.get(i));
            }
            beanList = null;
        }
        if (nextList != null && nextList.size() > 0) {
            b = true;
            for (int i = 0; i < nextList.size(); i++) {
                startWrite(nextList.get(i));
            }
        }
        nextList = null;
        over(b);
    }

    public void execute() {
        thread.start();
    }

}
