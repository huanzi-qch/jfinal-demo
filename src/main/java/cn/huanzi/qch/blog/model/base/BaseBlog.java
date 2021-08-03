package cn.huanzi.qch.blog.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * 博客表 BaseModel
 *
 * 作者：Auto Generator By 'huanzi-qch'
 * 生成日期：2021-07-26 09:31:41
 */
@SuppressWarnings("serial")
public abstract class BaseBlog<M extends BaseBlog<M>> extends Model<M> implements IBean {
    //博客id
    private Integer id;
    public void setId(Integer id) {
        this.id = id;
        set("id", this.id);
    }
    public Integer getId() {
        this.id = get("id");
        return this.id;
    }

    //博客标题
    private String title;
    public void setTitle(String title) {
        this.title = title;
        set("title", this.title);
    }
    public String getTitle() {
        this.title = get("title");
        return this.title;
    }

    //博客内容
    private String content;
    public void setContent(String content) {
        this.content = content;
        set("content", this.content);
    }
    public String getContent() {
        this.content = get("content");
        return this.content;
    }

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


}

