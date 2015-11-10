package com.aiven.log.util;

import java.io.File;

/**
 * Author: Aiven
 * Email : aiven163@sina.com
 * Date : 2015/11/1  11:21
 * Desc :
 */
public class LogFileUtil {


    /**
     * 创建文件
     *
     * @param rootDirectory
     * @param fileName
     * @return
     */
    public static File createFileIfNotExist(String rootDirectory, String fileName) {
        File file = new File(rootDirectory, fileName);
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            return file;
        } catch (Exception e) {
            return null;
        }
    }

    public static void deleteDirectory(File file) {
        if (file.exists()) {
            if (file.isDirectory() && file.list().length > 0) {
                File[] list = file.listFiles();
                for (File f : list) {
                    deleteDirectory(f);
                }
            } else {
                file.delete();
            }
        }
    }

}
