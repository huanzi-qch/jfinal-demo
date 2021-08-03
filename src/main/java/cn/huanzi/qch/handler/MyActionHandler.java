package cn.huanzi.qch.handler;

import cn.huanzi.qch.common.model.ErrorEnum;
import cn.huanzi.qch.common.model.Result;
import com.jfinal.core.Action;
import com.jfinal.core.JFinal;
import com.jfinal.handler.Handler;
import com.jfinal.kit.JsonKit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 自定义处理器
 */
public class MyActionHandler extends Handler {

    public MyActionHandler() {
    }

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        //应用路径
        request.setAttribute("ctx", request.getContextPath());

        Action action = JFinal.me().getAction(target, new String[]{null});

        boolean flag = false;
        List<String> allActionKeys = JFinal.me().getAllActionKeys();
        if(!allActionKeys.contains(target)){
            int i = target.lastIndexOf(47);
            if (i != -1) {
                String substring = target.substring(0, i);
                if (!allActionKeys.contains(substring) || action.getControllerPath().equals(substring)) {
                    flag = true;
                }
            }
        }

        /*
            404
            其他静态资源可直接访问，但.html页面禁止直接访问
         */
        if ((target.contains(".html") || !target.contains(".")) && flag) {
            try {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.print(JsonKit.toJson(Result.error(ErrorEnum.NOT_FOUND)));
                out.flush();
                out.close();
                response.flushBuffer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            this.next.handle(target, request, response, isHandled);
        }

    }
}
