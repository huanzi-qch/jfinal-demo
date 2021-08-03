package cn.huanzi.qch.common.model;

import cn.huanzi.qch.util.SqlUtil;
import com.alibaba.druid.util.StringUtils;

/**
 * 分页条件
 */
public class PageCondition {

    private int pageNumber = 1;//当前页码

    private int pageSize = 10;//页面大小

    private String sidx;//排序字段

    private String sord;//排序方式

    public PageCondition() {
    }

    public PageCondition(int pageNumber, int pageSize, String sidx, String sord) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sidx = sidx;
        this.sord = sord;
    }

    public int getPageNumber() {
        //处理非法值
        if(0 >= pageNumber){
            pageNumber = 1;
        }
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        //处理非法值
        if(0 >= pageSize){
            pageSize = 10;
        }
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    /**
     * 获取排序sql
     */
    public String getOrderBySql(){
        //处理排序
        if(!StringUtils.isEmpty(sidx) && !StringUtils.isEmpty(sord)){
            sord = "desc".equals(sord.toLowerCase()) ? "desc" : "asc";
            return " order by " + SqlUtil.translate(sidx) + " " + sord;
        }
        return "";
    }
}
