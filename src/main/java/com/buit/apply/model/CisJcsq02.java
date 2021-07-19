package com.buit.apply.model;

import com.buit.commons.PageQuery;

import java.math.BigDecimal;

/**
 * 类名称：CisJcsq02<br> 
 * 类描述：检查申请单明细
 * @author 老花生 
 * @ApiModel(value="检查申请单明细")
 */
public class CisJcsq02  extends  PageQuery{
	//@ApiModelProperty(value="检查序号")
    /** 检查序号 */
    private Integer jcxh;
	//@ApiModelProperty(value="费用序号(现在存储诊疗项目主键ZLXMID)")
    /** 费用序号(现在存储诊疗项目主键ZLXMID) */
    private Integer fyxh;
	//@ApiModelProperty(value="费用单价")
    /** 费用单价 */
    private BigDecimal fydj;

    /** 设置:检查序号  */
    public void setJcxh(Integer value) {
        this.jcxh = value;
    }
    /** 获取:检查序号 */
    public Integer getJcxh() {
        return jcxh;
    }

    /** 设置:费用序号(现在存储诊疗项目主键ZLXMID)  */
    public void setFyxh(Integer value) {
        this.fyxh = value;
    }
    /** 获取:费用序号(现在存储诊疗项目主键ZLXMID) */
    public Integer getFyxh() {
        return fyxh;
    }

    /** 设置:费用单价  */
    public void setFydj(BigDecimal value) {
        this.fydj = value;
    }
    /** 获取:费用单价 */
    public BigDecimal getFydj() {
        return fydj;
    }


}
