package com.aiven.log.core;

import com.aiven.log.mode.LogBean;

import java.util.List;

/**
 * Author: Aiven
 * Email : aiven163@sina.com
 * Date : 2016/2/29  11:22
 * Desc :
 */
public interface IFullLinkQueueListener {
    void popAll(List<LogBean> beanList);
    void writeOver(String tag);
}
