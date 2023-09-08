package cn.huanzi.qch.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;

import java.lang.reflect.Parameter;

/**
 * 该拦截器使得json格式的数据也能作为action的参数
 */
public class JsonInterceptor implements Interceptor {
    private static final String jsonType = "application/json";

    @Override
    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        String contentType = controller.getRequest().getContentType();
        Parameter[] parameters = inv.getMethod().getParameters();

        // 判断contentType 是否包含 application/json
        if (contentType != null && contentType.toLowerCase().contains(jsonType)) {
            for (int i = 0; i < parameters.length; i++) {
                //形参是否使用@JsonBody
                if (parameters[i].getAnnotation(JsonBody.class) != null) {
                    Class<?> T = parameters[i].getType();
                    Object result = null;
                    try {
                        //json字符串转对象
                        result = JsonKit.parse(controller.getRawData(), T);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // 替换原先的参数
                    inv.setArg(i, result);
                }
            }
        }
        inv.invoke();
    }
}