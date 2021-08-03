package cn.huanzi.qch.user.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * 用户表 BaseModel
 *
 * 作者：Auto Generator By 'huanzi-qch'
 * 生成日期：2021-07-29 17:32:50
 */
@SuppressWarnings("serial")
public abstract class BaseUser<M extends BaseUser<M>> extends Model<M> implements IBean {
    //用户id
    private String userId;
    public void setUserId(String userId) {
        this.userId = userId;
        set("user_id", this.userId);
    }
    public String getUserId() {
        this.userId = get("user_id");
        return this.userId;
    }

    //用户名称
    private String userName;
    public void setUserName(String userName) {
        this.userName = userName;
        set("user_name", this.userName);
    }
    public String getUserName() {
        this.userName = get("user_name");
        return this.userName;
    }


}

