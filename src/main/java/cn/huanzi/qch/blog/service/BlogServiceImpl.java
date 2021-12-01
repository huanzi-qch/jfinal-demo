package cn.huanzi.qch.blog.service;

import cn.huanzi.qch.common.service.CommonServiceImpl;
import cn.huanzi.qch.blog.model.Blog;
import com.jfinal.log.Log;

/**
 * 博客表 ServiceImpl
 *
 * 作者：Auto Generator By 'huanzi-qch'
 * 生成日期：2021-07-26 09:31:41
 */
public class BlogServiceImpl extends CommonServiceImpl<Blog> implements BlogService{
    private final Log log = Log.getLog(this.getClass());

    @Override
    public String errorTest2() {
        int i = 1/0;
        return "失败乃成功之母！";
    }

    @Override
    public String errorTest3() {
        throw new NullPointerException();
    }
}

