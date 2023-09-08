package cn.huanzi.qch.config;

import cn.huanzi.qch.common._MappingKit;
import cn.huanzi.qch.handler.MyActionHandler;
import cn.huanzi.qch.handler.WebJarsHandler;
import cn.huanzi.qch.interceptor.CORSInterceptor;
import cn.huanzi.qch.interceptor.GlobalExceptionInterceptor;
import cn.huanzi.qch.interceptor.JsonInterceptor;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.URLUtil;
import com.jfinal.config.*;
import com.jfinal.i18n.I18nInterceptor;
import com.jfinal.json.JFinalJson;
import com.jfinal.json.MixedJsonFactory;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.cron4j.Cron4jPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import com.jfinal.template.source.ClassPathSource;

import javax.sql.DataSource;
import java.io.File;
import java.util.Objects;

/**
 * API 引导式配置
 */
public class AppConfig extends JFinalConfig {
	private final Log log = Log.getLog(this.getClass());
	public static Prop p;

	public AppConfig(){
		AppConfig.loadConfig();

		log.info("当前配置文件：profile.active=%s",p.get("config.name"));
	}

	public static DataSource getDataSource() {
		AppConfig.loadConfig();
		DruidPlugin druidPlugin = AppConfig.createDruidPlugin();
		druidPlugin.start();
		return druidPlugin.getDataSource();
	}

	/**
	 * 根据profile.active加载配置文件
	 */
	public static void loadConfig() {
		String active = PropKit.use("undertow.txt").get("profile.active");
		if ("pro".equals(active)) {
			p = PropKit.use("config-pro.properties");
		}else if ("dev".equals(active)) {
			p = PropKit.use("config-dev.properties");
		}else {
			p = PropKit.use("config-dev.properties");
		}
	}
	
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {

		//是否开发模式
		me.setDevMode(p.getBoolean("devMode", false));
		
		/*
		  支持 Controller、Interceptor、Validator 之中使用 @Inject 注入业务层，并且自动实现 AOP
		  注入动作支持任意深度并自动处理循环注入
		 */
		me.setInjectDependency(true);
		
		// 配置对超类中的属性进行注入
		me.setInjectSuperClass(true);

		//Json
		me.setJsonFactory(new MixedJsonFactory());

		//Model、Record 字段名转换为驼峰格式
		JFinalJson.setModelAndRecordFieldNameToCamelCase();

		//该方法用于去除 null 值字段的转换
		JFinalJson.setSkipNullValueField(false);

		//i18n
		me.setI18nDefaultBaseName("i18n");

		//404/500页面
		me.setError500View("/common/500.html");
//		me.setError404View("/common/404.html");
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		// 扫描仅会在该包以及该包的子包下进行
		me.scan("cn.huanzi.qch.");

		//该方法用于配置是否要将控制器父类中的 public方法映射成 action
		me.setMappingSuperClass(true);

		// 此处配置 Routes 级别的拦截器，可配置多个
		me.addInterceptor(new GlobalExceptionInterceptor());
		me.addInterceptor(new CORSInterceptor());
//		me.addInterceptor(new AccessAuthorityInterceptor());
	}
	
	public void configEngine(Engine me) {
		//页面路径
		me.setBaseTemplatePath(PathKit.getWebRootPath()+"/view/");

		me.addSharedFunction("/common/_layout.html");
		me.addSharedFunction("/common/_paginate.html");
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		//定时器，使用外部配置
		Cron4jPlugin cp =new Cron4jPlugin("cron4j.properties", "cron4j");
		me.add(cp);

		// 配置 druid 数据库连接池插件
		DruidPlugin druidPlugin = AppConfig.createDruidPlugin();
		me.add(druidPlugin);
		
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);

		//打印执行的SQL
		arp.setShowSql(true);

		//是否开发模式
		arp.setDevMode(p.getBoolean("devMode", false));

		//扫描资源文件夹里面的sqltlf文件夹
		scanFileAddSqlTemplate(new File(URLUtil.decode(ResourceUtil.getResourceIter("sqltlf").next().getFile())),arp);

		// 所有映射在 MappingKit 中自动化搞定
		_MappingKit.mapping(arp);
		me.add(arp);
	}

	//递归扫描文件夹，加载sql模板
	private void scanFileAddSqlTemplate(File file,ActiveRecordPlugin arp){
		if (file.isFile()) {
			String fileName = file.getName();
			if (fileName.endsWith(".sql")) {
				String filePath = file.getPath().split("classes\\\\")[1];
				log.info("Enjoy SQL 模板：%s，加载成功！",filePath);
				arp.addSqlTemplate(new ClassPathSource(filePath));
			}
		} else if (file.isDirectory()) {
			for (File listFile : Objects.requireNonNull(file.listFiles())) {
				scanFileAddSqlTemplate(listFile,arp);
			}
		}
	}
	
	public static DruidPlugin createDruidPlugin() {
		return new DruidPlugin(p.get("jdbcUrl"), p.get("user"), p.get("password"));
	}
	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		//i18n
		me.add(new I18nInterceptor());
		//json 拦截,使得json参数也能作为action的参数，放在方法参数上
		me.addGlobalActionInterceptor(new JsonInterceptor());
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		me.add(new MyActionHandler());
		me.add(new WebJarsHandler());
	}
}
