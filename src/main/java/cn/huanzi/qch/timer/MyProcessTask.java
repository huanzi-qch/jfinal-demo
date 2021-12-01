package cn.huanzi.qch.timer;

import com.jfinal.log.Log;
import it.sauronsoftware.cron4j.ProcessTask;
import it.sauronsoftware.cron4j.TaskExecutionContext;

/**
 * 直接调度外部的应用程序
 * 除了可以对实现了Runnable接口的java类进行调度以外，还可以直接调度外部的应用程序
 * 例如windows或linux下的某个可执行程序，例如备份数据库：https://www.cnblogs.com/huanzi-qch/p/15210876.html
 */
public class MyProcessTask extends ProcessTask {
    //日志输出
    private final Log log = Log.getLog(this.getClass());

    //bat脚本文件
    private static final String batFile = "D:\\mysql_data_back\\data_back.bat";

    public MyProcessTask() {
        super(new String[]{ batFile , "start" });
    }

    @Override
    public void execute(TaskExecutionContext var1) throws RuntimeException {
        log.info("备份MySQL.jfinal_demo数据库！");
        super.execute(var1);
    }
}
