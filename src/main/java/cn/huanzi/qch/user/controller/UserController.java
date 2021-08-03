package cn.huanzi.qch.user.controller;

import cn.huanzi.qch.common.controller.CommonController;
import cn.huanzi.qch.common.model.PageCondition;
import cn.huanzi.qch.user.service.UserService;
import cn.huanzi.qch.user.service.UserServiceImpl;
import cn.huanzi.qch.user.model.User;
import com.jfinal.aop.Inject;
import com.jfinal.core.Path;
import com.jfinal.log.Log;

/**
 * 用户表 Controller
 *
 * 作者：Auto Generator By 'huanzi-qch'
 * 生成日期：2021-07-29 17:32:50
 */
@Path(value = "/user",viewPath = "/user")
public class UserController extends CommonController<User,UserServiceImpl> {
	private Log log = Log.getLog(UserController.class);

	@Inject(UserServiceImpl.class)
	UserService userService;

	public void index() {
	    set("user",userService.page(new PageCondition(),User.dao));
		render("user.html");
	}

	public void form() {
		//接参
		String id = get("id");

		set("user",userService.get(id));
		render("form.html");
	}
}



