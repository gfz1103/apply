
package com.buit.apply.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;


/**
 * 类名称：DicYjlxResp<br>
 * 类描述：医技类型维护-查询医技分类数结构返回<br>
 * @author 老花生
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value="医技类型维护-查询医技分类数结构返回")
public class DicYjlxResp {
    @ApiModelProperty(value="ID")
    private Integer id;
    @ApiModelProperty(value="医技类型名称")
    private String name;
    @ApiModelProperty(value="父ID")
    private Integer pid;
    @ApiModelProperty(value="排序")
    private Integer sort;
    @ApiModelProperty(value="注销标志(0=正常，1=注销)")
    private String zxbz;
    @ApiModelProperty(value="检查类型")
    private String jclx;
    @ApiModelProperty(value="子集数")
    private Integer chileNum;
    @ApiModelProperty(value = "子集集合")
    private List<DicYjlxResp> childrenList;
    /**
     * 设置:ID
     */
    public void setId(Integer value) {
        this.id = value;
    }
    /**
     * 获取:ID
     */
    public Integer getId() {
        return id;
    }
    /**
     * 设置:医技类型名称
     */
    public void setName(String value) {
        this.name = value;
    }
    /**
     * 获取:医技类型名称
     */
    public String getName() {
        return name;
    }
    /**
     * 设置:父ID
     */
    public void setPid(Integer value) {
        this.pid = value;
    }
    /**
     * 获取:父ID
     */
    public Integer getPid() {
        return pid;
    }
    /**
     * 设置:排序
     */
    public void setSort(Integer value) {
        this.sort = value;
    }
    /**
     * 获取:排序
     */
    public Integer getSort() {
        return sort;
    }
    /**
     * 设置:注销标志(0=正常，1=注销)
     */
    public void setZxbz(String value) {
        this.zxbz = value;
    }
    /**
     * 获取:注销标志(0=正常，1=注销)
     */
    public String getZxbz() {
        return zxbz;
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

    public Integer getChileNum() {
        return chileNum;
    }

    public void setChileNum(Integer chileNum) {
        this.chileNum = chileNum;
    }

    public List<DicYjlxResp> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<DicYjlxResp> childrenList) {
        this.childrenList = childrenList;
    }
}
