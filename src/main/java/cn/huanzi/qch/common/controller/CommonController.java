package cn.huanzi.qch.common.controller;


import cn.huanzi.qch.common.model.PageCondition;
import cn.huanzi.qch.common.service.CommonService;
import com.jfinal.aop.Aop;
import com.jfinal.core.Controller;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 通用Controller
 * @param <E>实体类
 * @param <S>实体ServiceImpl类
 */
public abstract class CommonController<E,S> extends Controller {
    private Class<E> entityClass;//实体类类型

    private Class<S> serviceImplClass;//ServiceImpl类类型

    private CommonService service;

    public CommonController(){
        Type[] types = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        this.entityClass = (Class<E>) types[0];
        this.serviceImplClass = (Class<S>) types[1];
        this.service = (CommonService) Aop.get(this.serviceImplClass);
    }

    /*
        前端调用示例：

        $.ajax({
            type:"POST",
            url:"http://localhost:10010/user/[page,list,save]",
            data:{
                "user.userId":"1",
                "pageCondition.pageNumber":1,
                "pageCondition.pageSize":10,
                "pageCondition.sidx":"userId",
                "pageCondition.sord":"asc",
            },
            dataType:"JSON",
            contentType:"application/x-www-form-urlencoded",
            success:function(data){
                console.log(data);
            },
            error:function(data){
                console.log("报错啦");
            }
        });

        http://172.16.12.156:10010/user/[find,delete]/1
	*/

    /**
     * 分页
     */
    public void page(){
        //接参
        E entity = getBean(entityClass);
        PageCondition pageCondition = getBean(PageCondition.class);

        renderJson(service.page(pageCondition,entity));
    }

    /**
     * 获取所有
     */
    public void list(){
        //接参
        E entity = getBean(entityClass);

        renderJson(service.list(entity));
    }

    /**
     * 根据id获取
     */
    public void get(){
        //接参
        String id = getPara(0);

        renderJson(service.get(id));
    }

    /**
     * 保存，新增/更新
     */
    public void save(){
        //接参
        E entity = getBean(entityClass);

        renderJson(service.save(entity));
    }

    /**
     * 根据id删除
     */
    public void delete(){
        //接参
        String id = getPara(0);

        renderJson(service.delete(id));
    }
}

