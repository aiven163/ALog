# AndroidLogsManager


这是一个android项目日志输出记录库。作为一个库功能引用到你的项目中。具体用法如下：<br>
This is an android log output record library project,  referred to as a library into your project.
usage is as follows:<br>


配置管理类：配置你的日志输出文件夹路径。日志将每天输出一个文档，已日期为名称的.txt文件。

```Java
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
	public static final String appRootName = "aiven";
	
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

```


如何调用：

首先：<br>
开启日志输出<br>

```Java
LogConfig.Debug = true; //
```

如果要存本地文件：
```Java
LogConfig.recodeAble=true;
```

1、输出异常：

 ```Java
 catch (Exception e) {
      Logs.logE(e);
   }
 ```
      
2、输出错误：
  ```Java
    Logs.logError(String tag, String msg)
  ```
 还有很多方法这里就不一一列举了，基本上系统Log有的方法，本库Logs也有

具体系统的Log和System.out.print方法相对应的都有。<br>

如果觉得代码有更好的优化或者更好的方法，欢迎一起更新
