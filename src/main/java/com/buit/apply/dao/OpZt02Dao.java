package com.buit.apply.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.buit.apply.model.OpZt02;
import com.buit.apply.response.DrugsBody;
import com.buit.apply.response.OpZt02DetailResp;
import com.buit.apply.response.OpZt02ProjectResp;
import com.buit.apply.response.OpZt02Resp;
import com.buit.commons.EntityDao;

/**
 * 门诊_门诊医生组套明细<br>
 * @author 老花生
 */
@Mapper
public interface OpZt02Dao extends EntityDao<OpZt02,Integer> {

    /**
     * 按组套编号-返回列表(西药、成药、草药))
     * @param ztbh
     * @return
     */
    List<OpZt02Resp> findByZtbh(Integer ztbh);

    /**
     * 按组套编号-返回列表(项目)
     * @param ztbh
     * @return
     */
    List<OpZt02Resp> findByZtbhXm(Integer ztbh);

    /**
     * 查询记录编号、项目编号、数量、费用单价-商保
     * @param param
     * @return
     */
    List<Map<String, Object>> getJlbhXmbhXmslFydjSb(Map<String, Object> param);

    /**
     * 查询记录编号、项目编号、数量、费用单价-非商保,是否门诊使用
     * @param param
     * @return
     */
    List<Map<String, Object>> getJlbhXmbhXmslFydjFSb(Map<String, Object> param);

    /**
     * 根据组套编号删除组套明细
     * @param ztbh
     */
    void deleteByZtbh(Integer ztbh);

	/**
	 * 根据组套编号获取组套信息
	 *
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getZtInfoByZtbh(Map<String, Object> param);


   /**
    * 住院常用药查询
    * @Title: queryCommonDrugs
    * @Description: TODO(这里用一句话描述这个方法的作用)
    * @param @param ygdm
    * @param @param ksdm
    * @param @param jgid
    * @param @param pydm
    * @param @return    设定文件
    * @return List<OpZt02DrugsResp>    返回类型
    * @author 龚方舟
    * @throws
    */
    List<DrugsBody> queryCommonDrugs(@Param("ygdm") Integer ygdm, @Param("ksdm") Integer ksdm,
    		@Param("jgid") Integer jgid, @Param("pydm") String pydm);

    /**
     * 住院常用项目查询
     * @Title: queryCommonProject
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @param ygdm
     * @param @param ksdm
     * @param @param jgid
     * @param @param pydm
     * @param @return    设定文件
     * @return List<OpZt02ProjectResp>    返回类型
     * @author 龚方舟
     * @throws
     */
    List<OpZt02ProjectResp> queryCommonProject(@Param("ygdm") Integer ygdm, @Param("ksdm") Integer ksdm,
    		@Param("jgid") Integer jgid, @Param("pydm") String pydm);

    /**
     * 根据记录编号查询住院常用药信息
     * @Title: queryCommonDrugsInfoByJlbh
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @param jlbh
     * @param @return    设定文件
     * @return List<Map<String, Object>>    返回类型
     * @author 龚方舟
     * @throws
     */
    List<Map<String, Object>> queryCommonDrugsInfoByJlbh(Map<String, Object> parameters);

    /**
     * 根据组套编号查询医嘱项目组套信息
     * @Title: queryProjectPackageInfoByZtbh
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @param parameters
     * @param @return    设定文件
     * @return List<Map<String,Object>>    返回类型
     * @author 龚方舟
     * @throws
     */
    List<Map<String, Object>> queryProjectPackageInfoByZtbh(Map<String, Object> parameters);

    /**
     * 根据组套编号查询药品组套信息
     * @param parameters
     * @return
     */
    List<Map<String, Object>> getYpZtInfoByZtbh(Map<String, Object> parameters);


    /**
     * 查询组套项目明细信息
     * @Title: queryPackageDetailsByZtbh
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @param parameters
     * @param @return    设定文件
     * @return List<Map<String,Object>>    返回类型
     * @author 龚方舟
     * @throws
     */
    List<Map<String, Object>> queryPackageDetailsByZtbh(Map<String, Object> parameters);

    /**
     * 查询医技组套明细
     * @param parameters
     * @return
     */
    List<Map<String, Object>> queryYjZtMx(Map<String, Object> parameters);


	/**
	 * 根据组套编号查询明细
	 * @Title: queryDetailByZtbh
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param ztbh
	 * @param @return    设定文件
	 * @return List<OpZt02DetailResp>    返回类型
	 * @author 龚方舟
	 * @throws
	 */
	List<OpZt02DetailResp> queryDetailByZtbh(Integer ztbh);

    /**
     * 查询项目编号、医嘱名称、费用单价
     * @param detailsParameter
     */
    List<Map<String, Object>> queryXmbhYzmcFydj(Map<String, Object> detailsParameter);

}
