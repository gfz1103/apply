package com.buit.apply.dao;

import com.buit.apply.model.CisJcsq03;
import com.buit.apply.response.BbDataResponse;
import com.buit.commons.EntityDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 检查申请送检标本表<br>
 * @author 韦靖
 */
@Mapper
public interface CisJcsq03Dao extends EntityDao<CisJcsq03,Integer>{


    /**
     * 通过检查序号查询检查申请送检标本数据
     * @param jcxh
     * @param jgid
     * @return
     */
    List<CisJcsq03> queryCisJcsq03ByJcxh(@Param("jcxh") Integer jcxh,@Param("jgid") Integer jgid);

    /**
     * 查询送检标本数据通过检查序号
     * @param jcxh
     * @return
     */
    List<BbDataResponse> getsjbb(@Param("jcxh") Integer jcxh);
    
    void deleteByJcxh(@Param("jcxh") Integer jcxh, @Param("jgid") Integer jgid);
}
