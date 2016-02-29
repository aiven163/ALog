package com.aiven.log.core.writer;

import android.text.TextUtils;

import com.aiven.log.LogManager;
import com.aiven.log.core.IFullLinkQueueListener;
import com.aiven.log.mode.LinkQueue;
import com.aiven.log.mode.LogBean;
import com.aiven.log.util.LogFileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

/**
 * Author: Aiven
 * Email : aiven163@sina.com
 * Date : 2015/11/1  11:43
 * Desc :
 */
public class CWriter extends BaseWriter implements IFullLinkQueueListener {

    private HashMap<String, LinkQueue> tagDivideMap;
    private HashMap<String, PieWriter> executeWritePie;

    public CWriter() {
        tagDivideMap = new HashMap<>();
    }

    @Override
    public void initWriter() {
        File file = LogFileUtil.createFileIfNotExist(LogManager.getInstance().getRootPath(), getFileName());
        try {
            mWriter = new PrintWriter(new FileOutputStream(file, true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startWrite(LogBean log) {
        super.startWrite(log);
        if (LogManager.getInstance().subTag) {
            String tag = log.getTag();
            if (TextUtils.isEmpty(tag)) {
                tag = "--ALOG--";
            }
            LinkQueue queue = tagDivideMap.get(tag);
            if (queue == null) {
                queue = new LinkQueue(this);
                tagDivideMap.put(tag, queue);
            }
            queue.poll(log);
        }
    }


    @Override
    public void popAll(List<LogBean> beanList) {
        if (beanList != null && beanList.size() > 0) {
            if (executeWritePie == null) {
                executeWritePie = new HashMap<>();
            }
            String tag = beanList.get(0).getTag();
            PieWriter writer = executeWritePie.get(tag);
            if (writer == null) {
                writer = new PieWriter(beanList, this);
                executeWritePie.put(writer.getTag(), writer);
                writer.execute();
            } else {
                writer.setNextList(beanList);
            }
        }

    }

    @Override
    public void writeOver(String tag) {
        try {
            executeWritePie.remove(tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
