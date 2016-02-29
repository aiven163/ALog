package com.aiven.log.mode;

import com.aiven.log.core.IFullLinkQueueListener;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Author: Aiven
 * Email : aiven163@sina.com
 * Date : 2016/2/29  11:05
 * Desc :
 */
public class LinkQueue {
    private static final int MAX_CAPACITY = 80;
    private IFullLinkQueueListener mListener;
    Vector vector;

    public LinkQueue(IFullLinkQueueListener listener) {
        this.mListener = listener;
        vector = new Vector<LogBean>(MAX_CAPACITY);
    }

    public void poll(LogBean bean) {
        vector.add(bean);
        try {
            if (vector.size() > MAX_CAPACITY - 30) {
                if (mListener != null) {
                    ArrayList list = new ArrayList<LogBean>();
                    list.addAll(vector);
                    mListener.popAll(list);
                }
                vector.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
