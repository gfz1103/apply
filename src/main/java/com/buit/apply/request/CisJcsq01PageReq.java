
package com.buit.apply.request;

import com.buit.commons.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Date;


/**
 * 类名称：CisJcsq01PageReq<br>
 * 类描述：检查申请单-分页查询请求<br>
 * @author 老花生
 */
@ApiModel(value="检查申请单-分页查询请求")
public class CisJcsq01PageReq extends  PageQuery{
    @ApiModelProperty(value="开始时间")
    private Date beginDate;
    @ApiModelProperty(value="结束时间")
    private Date endDate;
    @ApiModelProperty(value="申请类型(1=门诊 2=住院)")
    private String sqlx;
    @ApiModelProperty(value="住院号")
    private String zyh;
    @ApiModelProperty(value="就诊序号")
    private int jzxh;

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getSqlx() {
        return sqlx;
    }

    public void setSqlx(String sqlx) {
        this.sqlx = sqlx;
    }

    public String getZyh() {
        return zyh;
    }

    public void setZyh(String zyh) {
        this.zyh = zyh;
    }

    public int getJzxh() {
        return jzxh;
    }

    public void setJzxh(int jzxh) {
        this.jzxh = jzxh;
    }
}
