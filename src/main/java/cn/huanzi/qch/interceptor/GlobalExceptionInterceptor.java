package cn.huanzi.qch.interceptor;

import cn.huanzi.qch.common.model.ErrorEnum;
import cn.huanzi.qch.common.model.Result;
import cn.huanzi.qch.common.model.ServiceException;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;

/**
 * Controller层全局异常处理
 * 特殊情况外，禁止捕获异常，所有异常都应交给这里处理
 */
public class GlobalExceptionInterceptor implements Interceptor{

    private static Log log = Log.getLog(GlobalExceptionInterceptor.class);

    public void intercept(Invocation inv) {
        Result result = null;

        try {
            inv.invoke();
        }
        //业务异常
        catch (ServiceException e){
            e.printStackTrace();
            result = Result.error(e.getErrorEnum());
        }
        //空指针、非法参数
        catch (NullPointerException | IllegalArgumentException e){
            e.printStackTrace();
            result = Result.error(ErrorEnum.INTERNAL_SERVER_ERROR);
        }

        //...

        //未知异常（放在最后）
        catch (Exception e){
            e.printStackTrace();
            result = Result.error(ErrorEnum.UNKNOWN);
        }

        if(StrKit.notNull(result)){
            inv.getController().renderJson(result);
        }
    }
}
