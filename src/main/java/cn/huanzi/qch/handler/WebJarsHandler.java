package cn.huanzi.qch.handler;

import com.jfinal.handler.Handler;
import com.jfinal.log.Log;
import org.apache.commons.io.IOUtils;

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
            String path = target.replaceFirst("webjars", "META-INF/resources/webjars");
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path);

            OutputStream outputStream = null;
            try {
                if (inputStream != null) {
                    outputStream = response.getOutputStream();
                    IOUtils.copy(inputStream, response.getOutputStream());
                }else{
                    throw new IOException("inputStream is null");
                }
            } catch (IOException e) {
                log.error("无法从webjar中找到该静态资源 : " + path, e);
            } finally {
                IOUtils.closeQuietly(inputStream);
                IOUtils.closeQuietly(outputStream);
            }
            isHandled[0] = true;
        } else {
            this.next.handle(target, request, response, isHandled);
        }
    }
}
