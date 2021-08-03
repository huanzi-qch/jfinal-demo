package cn.huanzi.qch.common;

import cn.huanzi.qch.blog.model.Blog;
import cn.huanzi.qch.user.model.User;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import java.util.HashMap;

/**
 * 数据表、主键、实体类关系映射
 * 需要手动维护
 */
public class _MappingKit {

	/**
	 * 表、实体、主键关系集合
	 * 方便SqlUtil工具类拼接查询sql
	 */
    public static HashMap<String,String> tableMapping = new HashMap<>();
    public static HashMap<String,String> primaryKeyMapping = new HashMap<>();

	public static void mapping(ActiveRecordPlugin arp) {
		arp.addMapping("blog", "id", Blog.class);
        tableMapping.put(Blog.class.getName(),"blog");
		primaryKeyMapping.put(Blog.class.getName(),"id");

		arp.addMapping("user", "user_id", User.class);
        tableMapping.put(User.class.getName(),"user");
		primaryKeyMapping.put(User.class.getName(),"user_id");
	}
}