package cn.huanzi.qch.timer;

import cn.hutool.core.date.DateUtil;
import com.jfinal.log.Log;
import com.jfinal.plugin.cron4j.ITask;

/**
 * 定时器任务
 */
public class MyITask implements ITask {
    //日志输出
    private final Log log = Log.getLog(this.getClass());
    
    @Override
    public void stop() {
        log.info("%s，MyITask stop",DateUtil.date());
    }

    @Override
    public void run() {
        log.info("%s，MyITask每分钟执行一次 run",DateUtil.date());
    }
}
