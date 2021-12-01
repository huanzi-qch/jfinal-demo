package cn.huanzi.qch.user.controller;

import cn.huanzi.qch.common.controller.CommonController;
import cn.huanzi.qch.common.model.PageCondition;
import cn.huanzi.qch.user.model.User;
import cn.huanzi.qch.user.service.UserService;
import cn.huanzi.qch.user.service.UserServiceImpl;
import cn.huanzi.qch.util.SecurityUtil;
import com.jfinal.aop.Inject;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Path;
import com.jfinal.log.Log;

import java.util.HashMap;
import java.util.List;

/**
 * 用户表 Controller
 *
 * 作者：Auto Generator By 'huanzi-qch'
 * 生成日期：2021-07-29 17:32:50
 */
@Path(value = "/user",viewPath = "/user")
public class UserController extends CommonController<User,UserServiceImpl> {
	private final Log log = Log.getLog(this.getClass());

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

	/**
	 * 简单登录、注销、获取登录用户
	 */
	@ActionKey("/login")
	public void login() {
		String username = get("username");
		String password = get("password");

		SecurityUtil securityUtil = SecurityUtil.getInstance();
		SecurityUtil.User user = securityUtil.getUserByUserNameAndPassword(username, password);
		if(user != null){
			securityUtil.setLoginUser(this.getRequest(),user);
			renderText("登录成功！");
		}else{
			renderText("账号或密码错误...");
		}
	}
	@ActionKey("/logout")
	public void logout() {
		SecurityUtil securityUtil = SecurityUtil.getInstance();
		SecurityUtil.User loginUser = securityUtil.getLoginUser(this.getRequest());
		securityUtil.setLoginUser(this.getRequest(),null);

		renderText("注销成功！");
	}
	@ActionKey("/getLoginUser")
	public void getLoginUser() {
		SecurityUtil securityUtil = SecurityUtil.getInstance();
		SecurityUtil.User loginUser = securityUtil.getLoginUser(this.getRequest());
		List<SecurityUtil.Role> loginUserRole = securityUtil.getLoginUserRole(this.getRequest());

		HashMap<String, Object> map = new HashMap<>(2);
		map.put("loginUser",loginUser);
		map.put("loginUserRole",loginUserRole);
		renderJson(map);
	}

	/**
	 * 登录、鉴权测试接口
	 */
	@ActionKey("/sys/xtgl")
	public void xtgl() {
		renderText("系统管理...");
	}
	@ActionKey("/sys/yhgl")
	public void yhgl() {
		renderText("用户管理...");
	}
	@ActionKey("/portal/mhgl")
	public void mhgl() {
		renderText("网站门户管理...");
	}
	@ActionKey("/portal/index")
	public void portalIndex() {
		renderText("网站门户首页...");
	}
}



