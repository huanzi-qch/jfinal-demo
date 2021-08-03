package cn.huanzi.qch.blog.model;

import cn.huanzi.qch.blog.model.base.BaseBlog;
import cn.huanzi.qch.common.model.Result;
import cn.huanzi.qch.user.model.User;
import cn.huanzi.qch.user.service.UserServiceImpl;
import com.jfinal.aop.Aop;

/**
 * 博客表 Model
 *
 * 作者：Auto Generator By 'huanzi-qch'
 * 生成日期：2021-07-26 09:31:41
 */
@SuppressWarnings("serial")
public class Blog extends BaseBlog<Blog> {
    public static final Blog dao = new Blog().dao();

    /**
     * 表关联操作在这里维护
     * User.userId = Blog.userId
     */
    public Result<User> getUser(String userId){
        UserServiceImpl userService = Aop.get(UserServiceImpl.class);
        return userService.get(userId);
    }
}

