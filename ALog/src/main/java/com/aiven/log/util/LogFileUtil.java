package com.aiven.log.util;

import java.io.File;

/**
 * Author: Aiven
 * Email : aiven163@sina.com
 * Date : 2015/8/20  9:40
 * Desc :
 */
public class LogFileUtil {

    public void deleteFolder(File file) {
        if (file == null)
            return;
        if (file.isDirectory()) {//如果是文件夹
            File[] ls = file.listFiles();
            if (ls != null && ls.length > 0) {
                for (File f : ls) {
                    deleteFolder(f);
                }
            } else {
                file.delete();
            }
        } else {
            file.delete();
        }
    }


}
