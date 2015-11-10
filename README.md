# AndroidLogsManager

* 作者：Aiven <br/>
* 联系方式：aiven163@sina.com <br/>

#Description

这是一个Android开发日志管理库，在项目中，能够通过Logcat输出日志信息，同时可以将日志已经错误信息记录到指定的目录文件夹下面。
日志记录规则是按照日期生成的txt文件。每日生成一个错误日志文件<br/>

##### 当前最新版本V：`1.1.0`

#使用方式如下

* 如果你使用Eclipse，请下载源码，作为一个library 项目引用<br/>
* 如果你使用的是AndroidStudio则可以再的App项目的build.gradle文件中添加下面代码即可，不用在下载源码和jar包<br/>
    `compile 'com.aiven.log:ALog:1.1.0'`
    

1、首先在我们的Application的onCreate方法中添加以下代码，如果你没有重写Application类的话，可以在你APP的启动Activity的onCreate()方法中添加如下代码。<br>


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
     * 清除本地所有的日志文件
     */
    public static void clearLogFiles() {
        
    }
    
}
```


# 版本追踪<br/>

 * 1.0.1 <br/>
   修改bug,修改日志栈长度限制，防止突然间增加超过三十条日志，在写日志来不及的时候导致阻塞队列溢出问题。
 
 * 1.0.2 <br/>
   增加全局异常崩溃日志抓取记录，将会在输出在根目录中的crash目录中。具体配置如下
   

  配置好后可以获取得到系统的日志，如果要传到服务器，目前暂时只支持测试版本记录该日志，正式版本后续更新...

 * 1.0.3 <br/>
   可配置即使关闭Debug和recordable后仍然记录crash日志文件，主要解决发布出去的版本可以只收集崩溃日志，而不产生其他日志文件 <br/>
    <br/><br/>


###大改动
    
 * 1.1.0 <br/>
    在这个版本改动比较大，所以直接跳到了1.1.0。主要把普通日志，异常日志，崩溃日志
分别放到了不同的目录文件中。配置也变得简单，具体如下：<br/>
   
   ```Java
   String path = Environment.getExternalStorageDirectory().getAbsolutePath() 
                + File.separator + "log";
    LogManager.getInstance().initLogManager(LogManager.PRINT_AND_SAVE, path)
                         .openGlobalCrashHanler(this);
        
     ```
     <br/>
     其中 
     ```Java 
        openGlobalCrashHanler(this);//这个this 其实是一个Application
     ```
     这个方法是开启全局崩溃日志抓取功能，值得注意的是，这里传入的参数是一个Application。不能传入一个Activity的Context。这样是为了方式Activity被一直引用而无法销毁的原因。
     <br/><br/><br/>
     
    具体说明一下：首先配置一个存放日志的根目录路径。然后初始化日志管理器，参数说明：<br/>
     
     第一个：类型<br/>
     主要有四个值：
      ```java
          LogManager.CLOSE;                 //关闭状态
          LogManager.ONLY_PRINT_LOG;        //仅仅只在开发时LogCat中输出所有的日志
          LogManager.PRINT_AND_SAVE;        //打印并且保存日志（所有的，包括崩溃）
          LogManager.ONLY_COLLECT_CRASH;    //仅仅只收集崩溃日志
     ```
         
     第二个：根目录地址<br/><br/><br/>
     
     
     这样就配置完了，可以用了，是不是简单了很多
     
     
     


