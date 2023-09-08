package cn.huanzi.qch.handler;

import com.jfinal.handler.Handler;
import com.jfinal.log.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * webjar静态资源处理
 */
public class WebJarsHandler extends Handler {
    private final Log log = Log.getLog(this.getClass());

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        if (target.contains("/webjars/")) {
            //加前缀，从ClassLoader找到资源
            String path = target.replaceFirst("/webjars", "META-INF/resources/webjars");

            //复制文件流，写入到Response中
            try (OutputStream outputStream = response.getOutputStream();InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path);){
                if (inputStream != null) {

                    byte[] chars = new byte[1024];//缓存区大小，有初始化0值
                    int length;
                    while ((length = inputStream.read(chars)) != -1) {
                        //写入，写入部分数组
                        outputStream.write(chars, 0, length);//如果写入完整数组，会将未被填充的0值也一起写入
                    }

                    //刷新输入流
                    outputStream.flush();
                }else{
                    throw new IOException("inputStream is null");
                }
            } catch (IOException e) {
                log.error("无法从webjar中找到该静态资源 : " + path, e);
            }

            isHandled[0] = true;
        } else {
            this.next.handle(target, request, response, isHandled);
        }
    }
}
