package cn.huanzi.qch.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

import javax.servlet.http.HttpServletResponse;

/**
 * CORS安全跨域
 *
 * jfinal也有一个默认CORSInterceptor
 * com.jfinal.ext.cors.CORSInterceptor.java
 */
public class CORSInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation invocation) {
        HttpServletResponse response = invocation.getController().getResponse();

        response.addHeader("Access-Control-Allow-Origin","*");
        response.addHeader("Access-Control-Allow-Methods", "*");
        response.addHeader("Access-Control-Allow-Headers","*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.addHeader("Access-Control-Allow-Credentials", "true");

        invocation.invoke();
    }
}
