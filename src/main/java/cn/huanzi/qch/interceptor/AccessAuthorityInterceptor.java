package cn.huanzi.qch.interceptor;

import cn.huanzi.qch.common.model.ErrorEnum;
import cn.huanzi.qch.common.model.ServiceException;
import cn.huanzi.qch.util.SecurityUtil;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.log.Log;

import javax.servlet.http.HttpServletRequest;

/**
 * 访问权限拦截器
 */
public class AccessAuthorityInterceptor implements Interceptor {
    private static final Log log = Log.getLog(AccessAuthorityInterceptor.class);

    @Override
    public void intercept(Invocation invocation) {
        //请求头
        HttpServletRequest request = invocation.getController().getRequest();

        SecurityUtil securityUtil = SecurityUtil.getInstance();

        //鉴权中心
        String auc = securityUtil.auc(request);
        if("UNAUTHORIZED".equals(auc)){
            throw new ServiceException(ErrorEnum.UNAUTHORIZED);
        }
        if("FORBIDDEN".equals(auc)){
            throw new ServiceException(ErrorEnum.FORBIDDEN);
        }

        invocation.invoke();
    }
}
