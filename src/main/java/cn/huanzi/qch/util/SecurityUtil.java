package cn.huanzi.qch.util;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 一套简单的登录、鉴权工具
 */
public class SecurityUtil {

    /**
     * 单例模式-饿汉
     */
    private static final SecurityUtil instance = new SecurityUtil();
    private SecurityUtil (){}
    public static SecurityUtil getInstance() {
        return instance;
    }

    /**
     * 无需登录即可访问的URL
     * PS：建议从配置文件读取
     */
    private static final String[] URLS = {
            //登录页、登录请求、注销请求
            "/loginPage",
            "/login",
            "/logout",

            //静态资源，例如：js、css等
            "/assets/**",

            //一些特殊无需权限控制的地址、api
            "/portal/index",
    };

    /**
     * 用户角色信息一般情况下是不轻易更改，可以将结果存储到缓存对象
     */
    private static HashMap<String,List<Role>> userRoleMap = new HashMap<>(10);

    //查询数据库操作，应交由项目ORM框架负责
    private final DbUtil dbUtil = new DbUtil("jdbc:mysql://localhost/jfinal_demo","root","123456");

    /**
     * 鉴权中心
     * PS：返回值类型有待商榷
     */
    public String auc(HttpServletRequest request){
        //请求URL地址
        String requestUri = request.getRequestURI();

        SecurityUtil securityUtil = SecurityUtil.getInstance();

        //是否为无需登录即可访问URL
        if(SecurityUtil.checkUrl(requestUri,SecurityUtil.URLS)){
            //允许访问！
            return "SUCCEED";
        }

        //是否为登录用户
        SecurityUtil.User loginUser = securityUtil.getLoginUser(request);
        if(loginUser == null){
            //未登录或登录凭证过期！
            return "UNAUTHORIZED";
        }

        //该登录用户是否有权访问当前URL
        if(!SecurityUtil.checkUrl(requestUri,securityUtil.getRoleUrlByUserId(loginUser.getId()))){
            //抱歉，你无权限访问！
            return "FORBIDDEN";
        }

        //允许访问！
        return "SUCCEED";
    }

    /**
     * 检查requestUri是否包含在urls中
     */
    public static boolean checkUrl(String requestUri,String[] urls){
        //对/进行特殊处理
        if("/".equals(requestUri) && !Arrays.asList(urls).contains(requestUri)){
            return false;
        }

        String[] requestUris = requestUri.split("/");
        for (String url : urls) {
            if (check(requestUris, url.split("/"))) {
                return true;
            }
        }

        return false;
    }
    private static boolean check(String[] requestUris,String[] urls){
        for (int i1 = 0; i1 < requestUris.length; i1++) {
            //判断长度
            if (i1 >= urls.length){
                return false;
            }

            //处理/*、/**情况
            if("**".equals(urls[i1])){
                return true;
            }
            if("*".equals(urls[i1])){
                continue;
            }

            //处理带后缀
            if(requestUris[i1].contains(".") && urls[i1].contains(".")){
                String[] split = requestUris[i1].split("\\.");
                String[] split2 = urls[i1].split("\\.");

                // *.后缀的情况
                if("*".equals(split2[0]) && split[1].equals(split2[1])){
                    return true;
                }
            }

            //不相等
            if(!requestUris[i1].equals(urls[i1])){
                return false;
            }

        }

        return true;
    }

    /**
     * 从request设置、获取当前登录用户
     * PS：登录用户可以放在session中，也可以做做成jwt
     */
    public void setLoginUser(HttpServletRequest request,User loginUser){
        request.getSession().setAttribute("loginUser",loginUser);
    }
    public User getLoginUser(HttpServletRequest request){
        return (User)request.getSession().getAttribute("loginUser");
    }
    public List<Role> getLoginUserRole(HttpServletRequest request){
        User loginUser = this.getLoginUser(request);
        return loginUser != null ? getRoleByUserId(loginUser.getId()) : null;
    }

    /**
     * 根据用户id，获取用户允许访问URL
     */
    public String[] getRoleUrlByUserId(String userId){
        StringBuilder roleUrl = new StringBuilder();
        for (SecurityUtil.Role role : this.getRoleByUserId(userId)) {
            roleUrl.append(",").append(role.getRoleUrl());
        }
        return roleUrl.toString().split(",");
    }

    /**
     * 获取用户、用户角色
     * PS：这些查询数据库操作，应交由项目ORM框架负责
     */
    public User getUserByUserNameAndPassword(String username,String password){
        //PS：密码应该MD5加密后密文存储，匹配时先MD5加密后匹配，本例中存储的是明文，就不进行MD5加密了
        User user = null;
        HashMap<String, Object> map = dbUtil.findOne("select * from sys_user where user_name = ? and password = ?", new String[]{username, password});
        if(map != null){
            user = new User(map.get("id").toString(),map.get("nick_name").toString(),map.get("user_name").toString(),map.get("password").toString());
        }

        //关闭数据库连接
        dbUtil.close();

        return user;
    }
    public List<Role> getRoleByUserId(String userId){
        //先从缓存中获取
        List<Role> roles = userRoleMap.get(userId);
        if(roles != null){
            return roles;
        }

        //查询数据库
        List<Role> roleList = null;
        List<HashMap<String, Object>> list = dbUtil.find("select r.* from sys_role r join sys_user_role ur on r.id = ur.role_id where ur.user_id = ?", new String[]{userId});
        if(list != null){
            roleList = new ArrayList<>(list.size());
            for (HashMap<String, Object> map : list) {
                roleList.add(new Role(map.get("id").toString(),map.get("role_name").toString(),map.get("role_menu").toString(),map.get("role_url").toString()));
            }
        }

        //关闭数据库连接
        dbUtil.close();

        //放到缓存中
        userRoleMap.put(userId,roleList);

        return roleList;
    }

    /*
        3张基础表

        sys_user 系统用户表
            id        表id
            nick_name 昵称
            user_name 账号
            password  密码

        sys_role 系统角色表
            id        表id
            role_name 角色名称
            role_menu 角色菜单可视权限（可以不关联菜单，单独做成菜单管理直接与用户关联）
            role_url  角色URL访问权限

        sys_user_role 系统用户-角色关联表
            id      表id
            user_id 用户id
            role_id 角色id
     */
    public class User{
        private String id;//表id
        private String nickName;//昵称
        private String userName;//账号
        private String password;//密码

        public User(String id, String nickName, String userName, String password) {
            this.id = id;
            this.nickName = nickName;
            this.userName = userName;
            this.password = password;
        }

        public String getId() {
            return id;
        }

        public String getNickName() {
            return nickName;
        }

        public String getUserName() {
            return userName;
        }

        public String getPassword() {
            return password;
        }
    }
    public class Role{
        private String id;//表id
        private String RoleName;//角色名称
        private String RoleMenu;//角色菜单可视权限（可以不关联菜单，单独做成菜单管理直接与用户关联）
        private String RoleUrl;//角色URL访问权限

        public Role(String id, String roleName, String roleMenu, String roleUrl) {
            this.id = id;
            RoleName = roleName;
            RoleMenu = roleMenu;
            RoleUrl = roleUrl;
        }

        public String getId() {
            return id;
        }

        public String getRoleName() {
            return RoleName;
        }

        public String getRoleMenu() {
            return RoleMenu;
        }

        public String getRoleUrl() {
            return RoleUrl;
        }
    }
    public class UserRole{
        private String id;//表id
        private String UserId;//用户id
        private String RoleId;//角色id
    }
}
