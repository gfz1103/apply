package com.buit.apply.model;

/**
 * @Author weijing
 * @Date 2020-12-17 14:52
 * @Description
 **/
public class CisJcsq03 {
    //@ApiModelProperty(value="主键")
    /** 主键 */
    private Integer jcsqsjid;
    //@ApiModelProperty(value="机构id")
    /** 机构id */
    private Integer jgid;
    //@ApiModelProperty(value="检查序号(对应cis_jcsq01表主键)")
    /** 检查序号(对应cis_jcsq01表主键) */
    private Integer jcxh;

    //@ApiModelProperty(value="送检标本")
    /** 送检标本 */
    private String sjbb;

    //@ApiModelProperty(value="采取部位")
    /** 采取部位 */
    private String cqbw;

    //@ApiModelProperty(value="标本名称")
    /** 标本名称 */
    private String bbmc;

    public Integer getJcsqsjid() {
        return jcsqsjid;
    }

    public void setJcsqsjid(Integer jcsqsjid) {
        this.jcsqsjid = jcsqsjid;
    }

    public Integer getJgid() {
        return jgid;
    }

    public void setJgid(Integer jgid) {
        this.jgid = jgid;
    }

    public Integer getJcxh() {
        return jcxh;
    }

    public void setJcxh(Integer jcxh) {
        this.jcxh = jcxh;
    }

    public String getSjbb() {
        return sjbb;
    }

    public void setSjbb(String sjbb) {
        this.sjbb = sjbb;
    }

    public String getCqbw() {
        return cqbw;
    }

    public void setCqbw(String cqbw) {
        this.cqbw = cqbw;
    }

    public String getBbmc() {
        return bbmc;
    }

    public void setBbmc(String bbmc) {
        this.bbmc = bbmc;
    }
}
