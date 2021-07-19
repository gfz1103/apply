package com.buit.apply.request;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * @ClassName CheckInventoryReq
 * @Description 类描述
 * @Author 老花生
 * @Date 2020/9/3 11:20
 */
@ApiModel(value="辅助报告检查列表-请求")
public class QueryAuxiliaryJcReportListReq {
    @NotNull(message = "病人id 不能为空")
    @ApiModelProperty(value="病人id")
    private Integer brid;
    @NotNull(message = "就诊序号 不能为空")
    @ApiModelProperty(value="就诊序号")
    private Integer jzxh;
    @ApiModelProperty(value="开始时间", hidden = true)
    private Timestamp startTime;
    @ApiModelProperty(value="结束时间", hidden = true)
    private Timestamp endTime;
    @ApiModelProperty(value="查询日期")
    private Date nowDate = DateUtil.date().toSqlDate();

    public Integer getBrid() {
        return brid;
    }

    public void setBrid(Integer brid) {
        this.brid = brid;
    }

    public Integer getJzxh() {
        return jzxh;
    }

    public void setJzxh(Integer jzxh) {
        this.jzxh = jzxh;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Date getNowDate() {
        return nowDate;
    }

    public void setNowDate(Date nowDate) {
        this.nowDate = nowDate;
    }
}
