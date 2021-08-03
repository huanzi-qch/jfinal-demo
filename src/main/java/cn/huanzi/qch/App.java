package cn.huanzi.qch;

import cn.huanzi.qch.config.AppConfig;
import com.jfinal.server.undertow.UndertowServer;

/**
 * 启动入口，运行此main方法，即可启动项目
 */
public class App {

    public static void main(String[] args) {
        UndertowServer.start(AppConfig.class);
    }
}
