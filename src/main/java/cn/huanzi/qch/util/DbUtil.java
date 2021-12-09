package cn.huanzi.qch.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 原生jdbc操作数据库工具类
 * https://www.cnblogs.com/huanzi-qch/p/15474928.html
 */
public class DbUtil {

    //数据库连接：地址、用户名、密码
    private final String url;
    private final String username;
    private final String password;

    //Connection连接实例
    private Connection connection;

    public DbUtil(String url, String username, String password){
        this.url = url;
        this.username = username;
        this.password = password;
    }
    public DbUtil(String url, String username, String password, String driver){
        this(url,username,password);

        //加载驱动
        try {
            /*
                同时需要引入相关驱动依赖

                1、MySQL：
                com.mysql.cj.jdbc.Driver

                2、Oracle：
                oracle.jdbc.driver.OracleDriver

                3、pgsql：
                org.postgresql.Driver

             */
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取 Connection 连接
     */
    private Connection getConnection() {
        if(connection == null){
            try {
                connection= DriverManager.getConnection(url, username, password);
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("获取Connection连接异常...");
                e.printStackTrace();
            }
        }
        return connection;
    }

    /**
     * 设置是否自动提交事务
     * 当需要进行批量带事务的操作时，关闭自动提交手动管理事务，将会大大提高效率！
     */
    public void setAutoCommit(boolean autoCommit){
        try {
            this.getConnection().setAutoCommit(autoCommit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭自动提交事务时，需要手动管理事务提交、回滚
     */
    public void commit(){
        try {
            this.getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void rollback(){
        try {
            this.getConnection().rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭 Connection 连接
     */
    public void close(){
        if(connection != null){
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                System.err.println("关闭Connection连接异常...");
                e.printStackTrace();
            }
        }
    }

    /**
     * 查询
     * 查询语句
     */
    public ArrayList<HashMap<String,Object>> find(String sql, Object[] params) {
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();

        //获取连接
        Connection conn = this.getConnection();
        ResultSet rs;

        //语法糖
        //关闭PreparedStatement，会连带关闭ResultSet
        try(PreparedStatement ps = conn.prepareStatement(sql);) {
            //设置SQL、以及参数
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
            }

            //执行查询
            rs = ps.executeQuery();

            //获取查询结果
            ResultSetMetaData rm = rs.getMetaData();
            int columnCount = rm.getColumnCount();

            //封装结果集
            while (rs.next()) {
                HashMap<String, Object> map = new HashMap<>(columnCount);
                for (int i = 1; i <= columnCount; i++) {
                    String name = rm.getColumnName(i).toLowerCase();
                    Object value = rs.getObject(i);

                    map.put(name,value);
                }
                list.add(map);
            }

        } catch (Exception e) {
            System.err.println("执行 jdbcUtil.find() 异常...");
            e.printStackTrace();
        }

        return list;
    }
    public HashMap<String,Object> findOne(String sql, Object[] params){
        ArrayList<HashMap<String, Object>> list = this.find(sql, params);
        return list.size() > 0 ? list.get(0) : null;
    }
    public ArrayList<HashMap<String,Object>> find(String sql) {
        return this.find(sql,null);
    }
    public HashMap<String,Object> findOne(String sql) {
        return this.findOne(sql,null);
    }

    /**
     * 执行
     * 新增/删除/更新 等SQL语句
     */
    public int execute(String sql, Object[] params){
        int flag = 0;

        //获取连接
        Connection conn = this.getConnection();

        //语法糖
        try(PreparedStatement ps = conn.prepareStatement(sql);) {
            //设置SQL、以及参数
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
            }

            //执行
            flag = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("执行 jdbcUtil.update() 异常...");
            e.printStackTrace();
        }

        return flag;
    }
    public int execute(String sql){
        return this.execute(sql,null);
    }
}
