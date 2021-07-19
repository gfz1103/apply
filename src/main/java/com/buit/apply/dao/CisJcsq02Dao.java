package com.buit.apply.dao;

import com.buit.apply.model.CisJcsq02;
import com.buit.apply.response.CisJcsq02ZlxmResp;
import com.buit.apply.response.JcxmDataResponse;
import com.buit.commons.EntityDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 检查申请单明细<br>
 * @author 老花生
 */
@Mapper
public interface CisJcsq02Dao extends EntityDao<CisJcsq02,Integer>{

	 /**
	  * 查询检查申请单诊疗项目信息
	  * @Title: queryYsJcDicZlxmInfo
	  * @Description: TODO(这里用一句话描述这个方法的作用)
	  * @param @param jcxh
	  * @param @param jgid
	  * @param @return    设定文件
	  * @return List<CisJcsq02ZlxmResp>    返回类型
	  * @author 龚方舟
	  * @throws
	  */
	 List<CisJcsq02ZlxmResp> queryYsJcDicZlxmInfo(@Param("jcxh") Integer jcxh, @Param("jgid") Integer jgid);


	/**
	 * 查询病理检查申请单项目数据
	 * @param jcxh
	 * @return
	 */
	List<JcxmDataResponse> getJcxmData(@Param("jcxh") Integer jcxh);

	/**
	 * 通过检查序号删除检查申请单项目数据
	 * @param jcxh
	 */
	void deleteByJcxh(@Param("jcxh") Integer jcxh);

	/**
	 * 通过检查序号和费用序号删除检查申请单项目数据
	 * @param jcxh
	 * @param fyxh
	 */
	void deleteByJcxhAndFyxh(@Param("jcxh") Integer jcxh,@Param("fyxh") Integer fyxh);

    /**
     * 通过检查序号查询检查申请的项目
     * @param jcxh
     * @return
     */
	int selectJcsq02(@Param("jcxh") Integer jcxh);

}
