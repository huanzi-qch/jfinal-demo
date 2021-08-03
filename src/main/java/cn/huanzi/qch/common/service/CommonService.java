package cn.huanzi.qch.common.service;

import cn.huanzi.qch.common.model.PageCondition;
import cn.huanzi.qch.common.model.Result;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;

/**
 * 通用Service
 * @param <E>实体类
 */
public interface CommonService<E> {
    /**
     * 分页
     */
    public Result<Page<E>> page(PageCondition pageCondition, E e);

    /**
     * 获取所有
     */
    public Result<List<E>> list(E e);

    /**
     * 根据id获取
     */
    public Result<E> get(String id);

    /**
     * 保存，新增/更新
     */
    public Result<E> save(E e);

    /**
     * 根据id删除
     */
    public Result<String> delete(String id);
}
