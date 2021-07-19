
package com.buit.apply.request;

import com.buit.commons.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * 类名称：CisJcsq01<br>
 * 类描述：检查申请单<br>
 * @author 老花生
 */
@ApiModel(value="检查申请单")
public class CisJcsq01Req  extends  PageQuery{
    @ApiModelProperty(value="检查序号")
    private Integer jcxh;
    @ApiModelProperty(value="病人ID")
    private Integer brid;
    @ApiModelProperty(value="医技类型")
    private Integer yjlx;
    @ApiModelProperty(value="申请类型(1=门诊 2=住院)")
    private String sqlx;
    @ApiModelProperty(value="检查号")
    private String jch;
    @ApiModelProperty(value="申请医师")
    private String sqys;
    @ApiModelProperty(value="申请时间")
    private Timestamp sqsj;
    @ApiModelProperty(value="预约日期")
    private Timestamp yyrq;
    @ApiModelProperty(value="预约时间")
    private String yysj;
    @ApiModelProperty(value="检查目的")
    private String jcmd;
    @ApiModelProperty(value="检查费用")
    private BigDecimal jcfy;
    @ApiModelProperty(value="其他检查结果影像学及化验")
    private String qtjcjg;
    @ApiModelProperty(value="过敏反应相关因素")
    private String gmfy;
    @ApiModelProperty(value="住院号")
    private Integer zyh;
    @ApiModelProperty(value="门诊号码")
    private Integer mzhm;
    @ApiModelProperty(value="检查部位")
    private String jcbw;
    @ApiModelProperty(value="检查项目")
    private String jcxm;
    @ApiModelProperty(value="症状及体征")
    private String tztz;
    @ApiModelProperty(value="就诊序号 | 就诊序号 类似于住院的住院号 每次就诊不一样")
    private Integer jzxh;
    @ApiModelProperty(value="检查地点")
    private String jcdd;
    @ApiModelProperty(value="医技序号(关联OP_YJCF01表主键)")
    private Integer yjxh;
    @ApiModelProperty(value="预约状态 0=未预约  1=已预约 3-体检预约取消")
    private Integer yyzt;
    @ApiModelProperty(value="检查状态 0暂存，1提交，2预约，3已检查，4已报告，5已审核，6再审，7打印，9退回")
    private Integer jczt;
    @ApiModelProperty(value="号源ID")
    private Integer hyid;
    @ApiModelProperty(value="检查类型")
    private String jclx;
    @ApiModelProperty(value="执行科室")
    private Integer zxks;
    @ApiModelProperty(value="体检号码")
    private Integer tjhm;
    @ApiModelProperty(value="体检预约使用（取V_TJ_BALANCE.XMID）")
    private String xmbh;
    @ApiModelProperty(value="ALT报告")
    private String alt;
    @ApiModelProperty(value="HBsAg报告")
    private String hbsag;
    @ApiModelProperty(value="是否无痛")
    private String sfwt;
    @ApiModelProperty(value="糖尿病史")
    private String ywtnbs;
    @ApiModelProperty(value="抗凝剂")
    private String knjsy;
    @ApiModelProperty(value="一次性活检钳")
    private String ycxhjq;
    @ApiModelProperty(value="胃镜食管镜检查史")
    private String wjsgj;
    @ApiModelProperty(value="上消化道手术史")
    private String sxhdsss;
    /**
     * 设置:检查序号
     */
    public void setJcxh(Integer value) {
        this.jcxh = value;
    }
    /**
     * 获取:检查序号
     */
    public Integer getJcxh() {
        return jcxh;
    }
    /**
     * 设置:病人ID
     */
    public void setBrid(Integer value) {
        this.brid = value;
    }
    /**
     * 获取:病人ID
     */
    public Integer getBrid() {
        return brid;
    }
    /**
     * 设置:医技类型
     */
    public void setYjlx(Integer value) {
        this.yjlx = value;
    }
    /**
     * 获取:医技类型
     */
    public Integer getYjlx() {
        return yjlx;
    }
    /**
     * 设置:申请类型(1=门诊 2=住院)
     */
    public void setSqlx(String value) {
        this.sqlx = value;
    }
    /**
     * 获取:申请类型(1=门诊 2=住院)
     */
    public String getSqlx() {
        return sqlx;
    }
    /**
     * 设置:检查号
     */
    public void setJch(String value) {
        this.jch = value;
    }
    /**
     * 获取:检查号
     */
    public String getJch() {
        return jch;
    }
    /**
     * 设置:申请医师
     */
    public void setSqys(String value) {
        this.sqys = value;
    }
    /**
     * 获取:申请医师
     */
    public String getSqys() {
        return sqys;
    }
    /**
     * 设置:申请时间
     */
    public void setSqsj(Timestamp value) {
        this.sqsj = value;
    }
    /**
     * 获取:申请时间
     */
    public Timestamp getSqsj() {
        return sqsj;
    }
    /**
     * 设置:预约日期
     */
    public void setYyrq(Timestamp value) {
        this.yyrq = value;
    }
    /**
     * 获取:预约日期
     */
    public Timestamp getYyrq() {
        return yyrq;
    }
    /**
     * 设置:预约时间
     */
    public void setYysj(String value) {
        this.yysj = value;
    }
    /**
     * 获取:预约时间
     */
    public String getYysj() {
        return yysj;
    }
    /**
     * 设置:检查目的
     */
    public void setJcmd(String value) {
        this.jcmd = value;
    }
    /**
     * 获取:检查目的
     */
    public String getJcmd() {
        return jcmd;
    }
    /**
     * 设置:检查费用
     */
    public void setJcfy(BigDecimal value) {
        this.jcfy = value;
    }
    /**
     * 获取:检查费用
     */
    public BigDecimal getJcfy() {
        return jcfy;
    }
    /**
     * 设置:其他检查结果影像学及化验
     */
    public void setQtjcjg(String value) {
        this.qtjcjg = value;
    }
    /**
     * 获取:其他检查结果影像学及化验
     */
    public String getQtjcjg() {
        return qtjcjg;
    }
    /**
     * 设置:过敏反应相关因素
     */
    public void setGmfy(String value) {
        this.gmfy = value;
    }
    /**
     * 获取:过敏反应相关因素
     */
    public String getGmfy() {
        return gmfy;
    }
    /**
     * 设置:住院号
     */
    public void setZyh(Integer value) {
        this.zyh = value;
    }
    /**
     * 获取:住院号
     */
    public Integer getZyh() {
        return zyh;
    }
    /**
     * 设置:门诊号码
     */
    public void setMzhm(Integer value) {
        this.mzhm = value;
    }
    /**
     * 获取:门诊号码
     */
    public Integer getMzhm() {
        return mzhm;
    }
    /**
     * 设置:检查部位
     */
    public void setJcbw(String value) {
        this.jcbw = value;
    }
    /**
     * 获取:检查部位
     */
    public String getJcbw() {
        return jcbw;
    }
    /**
     * 设置:检查项目
     */
    public void setJcxm(String value) {
        this.jcxm = value;
    }
    /**
     * 获取:检查项目
     */
    public String getJcxm() {
        return jcxm;
    }
    /**
     * 设置:症状及体征
     */
    public void setTztz(String value) {
        this.tztz = value;
    }
    /**
     * 获取:症状及体征
     */
    public String getTztz() {
        return tztz;
    }
    /**
     * 设置:就诊序号 | 就诊序号 类似于住院的住院号 每次就诊不一样
     */
    public void setJzxh(Integer value) {
        this.jzxh = value;
    }
    /**
     * 获取:就诊序号 | 就诊序号 类似于住院的住院号 每次就诊不一样
     */
    public Integer getJzxh() {
        return jzxh;
    }
    /**
     * 设置:检查地点
     */
    public void setJcdd(String value) {
        this.jcdd = value;
    }
    /**
     * 获取:检查地点
     */
    public String getJcdd() {
        return jcdd;
    }
    /**
     * 设置:医技序号(关联OP_YJCF01表主键)
     */
    public void setYjxh(Integer value) {
        this.yjxh = value;
    }
    /**
     * 获取:医技序号(关联OP_YJCF01表主键)
     */
    public Integer getYjxh() {
        return yjxh;
    }
    /**
     * 设置:预约状态 0=未预约  1=已预约 3-体检预约取消
     */
    public void setYyzt(Integer value) {
        this.yyzt = value;
    }
    /**
     * 获取:预约状态 0=未预约  1=已预约 3-体检预约取消
     */
    public Integer getYyzt() {
        return yyzt;
    }
    /**
     * 设置:检查状态 0暂存，1提交，2预约，3已检查，4已报告，5已审核，6再审，7打印，9退回
     */
    public void setJczt(Integer value) {
        this.jczt = value;
    }
    /**
     * 获取:检查状态 0暂存，1提交，2预约，3已检查，4已报告，5已审核，6再审，7打印，9退回
     */
    public Integer getJczt() {
        return jczt;
    }
    /**
     * 设置:号源ID
     */
    public void setHyid(Integer value) {
        this.hyid = value;
    }
    /**
     * 获取:号源ID
     */
    public Integer getHyid() {
        return hyid;
    }
    /**
     * 设置:检查类型
     */
    public void setJclx(String value) {
        this.jclx = value;
    }
    /**
     * 获取:检查类型
     */
    public String getJclx() {
        return jclx;
    }
    /**
     * 设置:执行科室
     */
    public void setZxks(Integer value) {
        this.zxks = value;
    }
    /**
     * 获取:执行科室
     */
    public Integer getZxks() {
        return zxks;
    }
    /**
     * 设置:体检号码
     */
    public void setTjhm(Integer value) {
        this.tjhm = value;
    }
    /**
     * 获取:体检号码
     */
    public Integer getTjhm() {
        return tjhm;
    }
    /**
     * 设置:体检预约使用（取V_TJ_BALANCE.XMID）
     */
    public void setXmbh(String value) {
        this.xmbh = value;
    }
    /**
     * 获取:体检预约使用（取V_TJ_BALANCE.XMID）
     */
    public String getXmbh() {
        return xmbh;
    }
    /**
     * 设置:ALT报告
     */
    public void setAlt(String value) {
        this.alt = value;
    }
    /**
     * 获取:ALT报告
     */
    public String getAlt() {
        return alt;
    }
    /**
     * 设置:HBsAg报告
     */
    public void setHbsag(String value) {
        this.hbsag = value;
    }
    /**
     * 获取:HBsAg报告
     */
    public String getHbsag() {
        return hbsag;
    }
    /**
     * 设置:是否无痛
     */
    public void setSfwt(String value) {
        this.sfwt = value;
    }
    /**
     * 获取:是否无痛
     */
    public String getSfwt() {
        return sfwt;
    }
    /**
     * 设置:糖尿病史
     */
    public void setYwtnbs(String value) {
        this.ywtnbs = value;
    }
    /**
     * 获取:糖尿病史
     */
    public String getYwtnbs() {
        return ywtnbs;
    }
    /**
     * 设置:抗凝剂
     */
    public void setKnjsy(String value) {
        this.knjsy = value;
    }
    /**
     * 获取:抗凝剂
     */
    public String getKnjsy() {
        return knjsy;
    }
    /**
     * 设置:一次性活检钳
     */
    public void setYcxhjq(String value) {
        this.ycxhjq = value;
    }
    /**
     * 获取:一次性活检钳
     */
    public String getYcxhjq() {
        return ycxhjq;
    }
    /**
     * 设置:胃镜食管镜检查史
     */
    public void setWjsgj(String value) {
        this.wjsgj = value;
    }
    /**
     * 获取:胃镜食管镜检查史
     */
    public String getWjsgj() {
        return wjsgj;
    }
    /**
     * 设置:上消化道手术史
     */
    public void setSxhdsss(String value) {
        this.sxhdsss = value;
    }
    /**
     * 获取:上消化道手术史
     */
    public String getSxhdsss() {
        return sxhdsss;
    }
}
