package cn.huanzi.qch.util;

import cn.huanzi.qch.common._MappingKit;
import cn.huanzi.qch.config.AppConfig;
import cn.hutool.core.util.StrUtil;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

import java.lang.reflect.Field;

/**
 * SQL工具类
 */
public class SqlUtil {

    /**
     * 根据实体直接拼接全部SQL
     */
    public static <E> StringBuilder joinSqlByEntity(E entity){
        //select 所有字段 from table
        StringBuilder stringBuilder = SqlUtil.appendFields(entity.getClass());

        //拼接查询字段
        SqlUtil.appendQueryColumns(stringBuilder,entity);

        return stringBuilder;
    }

    /**
     * 根据Class，拼接完整查询SQL
     */
    public static StringBuilder appendFields(Class clazz){
        StringBuilder sql = new StringBuilder("select ");

        //获取父类的字段
        for (Field field : clazz.getSuperclass().getDeclaredFields()) {
            //获取授权
            field.setAccessible(true);
            String fieldName = field.getName();//属性名称

            //拼接查询字段  驼峰属性转下划线
            sql.append(SqlUtil.translate(fieldName)).append(" ").append(",");
        }

        //处理逗号（删除最后一个字符）
        sql.deleteCharAt(sql.length() - 1);

        //表名
        if(_MappingKit.tableMapping.size() <= 0){
            _MappingKit.mapping(new ActiveRecordPlugin(AppConfig.getDataSource()));
        }
        String tableName = _MappingKit.tableMapping.get(clazz.getName());

        sql.append("from ").append(tableName).append(" where '1' = '1'");

        return sql;
    }

    /**
     * 根据实体对象，拼接完整查询SQL
     */
    public static <E> StringBuilder appendQueryColumns(StringBuilder sql,E entity){
        //获取父类的字段
        for (Field field : entity.getClass().getSuperclass().getDeclaredFields()) {
            //获取授权
            field.setAccessible(true);
            //属性名称
            String fieldName = field.getName();
            //属性的值
            Object fieldValue = null;
            try {
                fieldValue = field.get(entity);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            //值是否为空，父类属性存在
            if (null != fieldValue) {
                //拼接等值查询条件
                sql.append(" and ").append(SqlUtil.translate(fieldName)).append(" = '").append(SqlUtil.escapeSql(String.valueOf(fieldValue))).append("'");
            }
        }

        return sql;
    }

    /**
     * 实体属性转表字段，驼峰属性转下划线，并全部转小写
     */
    public static String translate(String fieldName){
        return StrUtil.toUnderlineCase(fieldName).toLowerCase();
    }

    /**
     * sql转义
     * 动态拼写SQL，需要进行转义防范SQL注入！
     */
    public static String escapeSql(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char src = str.charAt(i);
            switch (src) {
                case '\'':
                    sb.append("''");// hibernate转义多个单引号必须用两个单引号
                    break;
                case '\"':
                case '\\':
                    sb.append('\\');
                default:
                    sb.append(src);
                    break;
            }
        }
        return sb.toString();
    }

    /**
     * select ... from 替换成 select count(*) from
     */
    public static String toTotalRowSql(String findSql){
        return findSql.replaceFirst("select(.*)from","select count(*) from");
    }
}
