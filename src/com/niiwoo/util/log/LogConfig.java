package com.niiwoo.util.log;

import java.io.File;

import android.os.Environment;

/**
 * @author Aiven
 * @date 2014-6-3  下午6:12:24
 * @email aiven163@sina.com
 * @Description 日志管理配置
 */
public class LogConfig {
	/**
	 * 是否开启Debug
	 */
	public static boolean Debug = false;
	/**
	 * 谁否记录在客户端
	 */
	public static boolean recodeAble = false;
	/**
	 * 日志目录文件夹名称(一般为应用名称)
	 */
	public static final String appRootName = "niiwoo";
	
	 public static final java.lang.String DEFAULT_TAG = "--APPLOG--";

	/**
	 * 存储路径,请注意必须是 ' / '结尾的一个目录路径
	 */
	public static final String SAVE_PATH = Environment
			.getExternalStorageDirectory()
			+ File.separator
			+ appRootName
			+ File.separator + "log" + File.separator;
}
