package cn.huanzi.qch.common.service;

import cn.huanzi.qch.common._MappingKit;
import cn.huanzi.qch.common.model.PageCondition;
import cn.huanzi.qch.common.model.Result;
import cn.huanzi.qch.util.SqlUtil;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 通用ServiceImpl
 * @param <E>实体类
 */
public class CommonServiceImpl<E extends Model> implements CommonService<E> {
    private Class<E> entityClass;//实体类类型
    private E entity;//实体类

    public CommonServiceImpl() {
        try {
            Type[] types = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
            this.entityClass = (Class<E>) types[0];
            entity = this.entityClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 分页
     */
    public Result<Page<E>> page(PageCondition pageCondition, E e) {
        //根据实体直接拼接全部SQL
        StringBuilder sql = SqlUtil.joinSqlByEntity(e);

        //排序sql
        sql.append(pageCondition.getOrderBySql());

        //设置分页参数、以及查询sql
        return Result.of(entity.paginateByFullSql(pageCondition.getPageNumber(), pageCondition.getPageSize(), SqlUtil.toTotalRowSql(sql.toString()), sql.toString()));
    }

    /**
     * 获取所有
     */
    public Result<List<E>> list(E e){
        SqlPara sqlPara = new SqlPara();

        //根据实体直接拼接全部SQL
        StringBuilder sql = SqlUtil.joinSqlByEntity(e);

        sqlPara.setSql(sql.toString());

        //设置查询sql
        return Result.of(entity.find(sql.toString()));
    }

    /**
     * 根据id获取
     */
    public Result<E> get(String id){
        return Result.of((E)entity.findById(id));
    }

    /**
     * 保存，新增/更新
     */
    public Result<E> save(E e){
        String key = _MappingKit.primaryKeyMapping.get(entityClass.getName());
        String id = e.get(key).toString();

        //存在则更新、不存在则新增
        if(this.get(id).getData() == null){
            e.save();
        }else{
            e.update();
        }
        return Result.of(e);
    }

    /**
     * 根据id删除
     */
    public Result<String> delete(String id){
        entity.deleteById(id);
        return Result.of(id);
    }
}
