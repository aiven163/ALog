# AndroidLogsManager

* 作者：Aiven <br/>
* 联系方式：aiven163@sina.com <br/>

#Description

这是一个Android开发日志管理库，在项目中，能够通过Logcat输出日志信息，同时可以将日志已经错误信息记录到指定的目录文件夹下面。
日志记录规则是按照日期生成的txt文件。每日生成一个错误日志文件<br/>

##### 当前最新版本V：`1.0.2`

#使用方式如下

* 如果你使用Eclipse，请下载源码，作为一个library 项目引用<br/>
* 如果你使用的是AndroidStudio则可以再的App项目的build.gradle文件中添加下面代码即可，不用在下载源码和jar包<br/>
    `compile 'com.aiven.log:ALog:1.0.0'`
    

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


# 版本追踪<br/>

 * 1.0.1 <br/>
   修改bug,修改日志栈长度限制，防止突然间增加超过三十条日志，在写日志来不及的时候导致阻塞队列溢出问题。
 
 * 1.0.2 <br/>
   增加全局异常崩溃日志抓取记录，将会在输出在根目录中的crash目录中。具体配置如下
   
   调用 LogConfg 类进行配置：<br/>
   ```Java 
    /**
     * 这是需要传入Application或Activity的getApplicationContext()返回的Context
     * @param context
     */
    public static void configGlobleCrash(Context context) {
        CrashHandler.getInstance(context);
    }
  ```
  配置好后可以获取得到系统的日志，如果要传到服务器，目前暂时只支持测试版本记录该日志，正式版本后续更新...


