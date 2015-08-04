# AndroidLogsManager

* 作者：Aiven <br/>
* 联系方式：aiven163@sina.com <br/>

#Descript

这是一个Android开发日志管理库，在项目中，能够通过Logcat输出日志信息，同时可以将日志已经错误信息记录到指定的目录文件夹下面。
日志记录规则是按照日期生成的txt文件。每日生成一个错误日志文件

#使用方式如下

1、首先在我们的Application的onCreate方法中添加以下代码，如果你没有重写Application类的话，可以在你APP的启动Activity的onCreate()方法中添加如下代码。<br>

 ```Java
        /**
         * 开启日志管理工具
         */
        LogConfig.Debug=true;

        /**
         * 开启本地记录文件
         */
        LogConfig.recodeAble=true;

        /**
         * 日志存储本地的根目录
         */
        LogConfig.logFileSavePath= Environment
                .getExternalStorageDirectory()+"MyAppName";
```

只有配置好了，日志才能正常的输出在LogCat和记录在本地。<br>

`注意`：打正式包的时候可要记住关闭所有设置开关哦，不然在用户手机上产生一些垃圾日志可不好<br>


2、如何输出日志，具体我就不去写了，入口类是Logs，下面附上Logs.java的方法代码，具体实现就不附带了，大家就知道里面有些啥方法了，知道该咋调用。

```Java
/**
 * @author Aiven
 * @date 2014-6-3  下午6:12:53
 * @email aiven163@sina.com
 * @Description 日志记录输出工具
 */
public class Logs {

    /**
     * 输出异常信息
     *
     * @param e
     * @Description
     * @author Aiven
     */
    public static void logE(Exception e) {
       
    }

    /**
     * 通过Logcat输出日志信息
     *
     * @param tag
     * @param msg
     */
    public static void logError(String tag, String msg) {
       
    }

    /**
     * 通过Logcat输出日志信息
     *
     * @param tag
     * @param msg
     * @Description
     * @author Aiven
     */
    public static void logI(String tag, String msg) {
       
    }

    /**
     * 通过Logcat输出日志信息
     *
     * @param tag
     * @param msg
     * @Description
     * @author Aiven
     */
    public static void logV(String tag, String msg) {
        
    }

    /**
     * 通过Logcat输出日志信息
     *
     * @param tag
     * @param msg
     * @Description
     * @author Aiven
     */
    public static void logW(String tag, String msg) {
        
    }

    /**
     * 通过Logcat输出日志信息
     *
     * @param tag
     * @param msg
     * @Description
     * @author Aiven
     */
    public static void logD(String tag, String msg) {
       
    }

    /**
     * 通过打印模式输出日志
     *
     * @param tag
     * @param msg
     * @Description
     * @author Aiven
     */
    public static void logPrint(String tag, String msg) {
        
    }

    /**
     * 通过打印模式输出日志
     *
     * @param tag
     * @param msg
     * @Description
     * @author Aiven
     */
    public static void logPrint(String tag, Object msg) {
        
    }

    /**
     * 通过打印模式输出日志
     *
     * @param msg
     * @Description
     * @author Aiven
     */
    public static void logPrint(Object msg) {
       
    }

   /**
     * 输出崩溃日志，将会在输出在根目录中的crash目录中
     *
     * @param msg
     * @Description
     * @author Aiven
     */
    public static void logCrash(String tag, String msg) {
       
    }

    public static void clearAllLogs() {
       
    }
}
```

