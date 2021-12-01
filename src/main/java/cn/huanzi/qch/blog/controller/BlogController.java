package cn.huanzi.qch.blog.controller;

import cn.huanzi.qch.blog.model.Blog;
import cn.huanzi.qch.blog.service.BlogService;
import cn.huanzi.qch.blog.service.BlogServiceImpl;
import cn.huanzi.qch.common.controller.CommonController;
import cn.huanzi.qch.common.model.ErrorEnum;
import cn.huanzi.qch.common.model.PageCondition;
import cn.huanzi.qch.common.model.Result;
import cn.huanzi.qch.common.model.ServiceException;
import com.jfinal.aop.Inject;
import com.jfinal.core.Path;
import com.jfinal.i18n.I18n;
import com.jfinal.i18n.Res;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * 博客表 Controller
 *
 * 作者：Auto Generator By 'huanzi-qch'
 * 生成日期：2021-07-26 09:31:41
 */
@Path(value = "/blog",viewPath = "/blog")
public class BlogController extends CommonController<Blog,BlogServiceImpl> {
	private final Log log = Log.getLog(this.getClass());

	@Inject(BlogServiceImpl.class)
	BlogService blogService;

	public void index() {
		//国际化取值测试,locale参数：zh_CN/en_US
		setCookie("_locale","en_US",60*60);
		String locale = getCookie("_locale");
		Res res = I18n.use(StrKit.isBlank(locale) ? "zh_CN" : locale);
		log.info(res.get("msg"));

		PageCondition pageCondition = new PageCondition();
		pageCondition.setPageNumber(getInt("pageNumber",1));
		set("blog",blogService.page(pageCondition,Blog.dao));

		//Enjoy SQL 模板，加载外部SQL模板文件
		System.out.println("加载外部SQL模板文件");
		Kv data = Kv.by("data", Kv.by("u.user_name", "张三")).set("username", "张三");
		List<Record> testSqlTlf = Db.template("jfinal.test_sql_tlf", data).find();
		for (Record record : testSqlTlf) {
			System.out.println(record);
		}

		render("blog.html");
	}

	public void form() {
		//接参
		String id = get("id");

		Result<Blog> blogResult = blogService.get(id);
		set("blog", blogResult);
		if(blogResult.getData() != null){
			set("user",Blog.dao.getUser(blogResult.getData().getUserId()));
		}
		render("form.html");
	}

	public void errorTest(){
		throw new ServiceException(ErrorEnum.USER_NAME_IS_NOT_NULL);
	}

	public void errorTest2(){
		renderJson(blogService.errorTest2());
	}

	public void errorTest3(){
		renderJson(blogService.errorTest3());
	}
}



