package com.aiven.log.core.writer;

import com.aiven.log.LogManager;
import com.aiven.log.util.LogFileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * Author: Aiven
 * Email : aiven163@sina.com
 * Date : 2015/11/1  11:43
 * Desc :
 */
public class CWriter extends BaseWriter {


    @Override
    public void initWriter() {
        File file = LogFileUtil.createFileIfNotExist(LogManager.getInstance().getRootPath(), getFileName());
        try {
            mWriter = new PrintWriter(new FileOutputStream(file, true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
