package com.buit.apply.dao;

import com.buit.apply.model.CisJcsq01;
import com.buit.apply.request.CisJcsq01QueryReq;
import com.buit.apply.request.QueryAuxiliaryJcReportListReq;
import com.buit.apply.request.YjyySaveReq;
import com.buit.apply.response.*;
import com.buit.commons.EntityDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 检查申请单<br>
 *
 * @author 老花生
 */
@Mapper
public interface CisJcsq01Dao extends EntityDao<CisJcsq01, Integer> {

	/**
	 * 检查申请单数量
	 *
	 * @param jc01Params
	 * @return
	 */
	Long getJcSqdCount(Map<String, Object> jc01Params);

	/**
	 * 按条件查询方法门诊
	 *
	 * @param req
	 * @return
	 */
	List<CisJcsq01PageResp> findByEntityMz(Map req);

	/**
	 * 按条件查询方法住院
	 *
	 * @param req
	 * @return
	 */
	List<CisJcsq01PageResp> findByEntityZy(Map req);

	/**
	 * 门诊或住院医技预约保存
	 *
	 * @param req
	 */
	void updateAppointment(YjyySaveReq req);

	/**
	 * 医技取消(门诊或者住院)
	 *
	 * @param jcxh
	 */
	void cancelYjMzOrZy(@Param("jcxh") Integer jcxh);

	/**
	 * 医技取消(体检)
	 *
	 * @param jcxh
	 */
	void cancelYjTj(@Param("jcxh") Integer jcxh);

	/**
	 * 更新检查状态
	 *
	 * @author wy
	 * @param map
	 */
	void updateJczt(Map<String, Object> map);

	/**
	 * 根据检查序号更新检查状态
	 *
	 * @author wy
	 * @param map
	 */
	void updateJcztByJcxh(Map<String, Object> map);

	/**
	 * 医嘱删除检查 @Title: deleteInspectByJlxh @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param @param jlxh 设定文件 @return void 返回类型 @author
	 * 龚方舟 @throws
	 */
	void deleteInspectByJlxh(Long jlxh);

	/**
	 * 查询诊间医技预病人信息-门诊
	 * @param searchType
	 * @param searchValue
	 * @return
	 */
	List<LoadPatientInfoResp> loadPatientInfoMz(@Param("searchType") Integer searchType,
												@Param("searchValue") String searchValue);

	/**
	 * 查询诊间医技预病人信息-住院
	 *
	 * @param searchValue
	 * @return
	 */
	List<LoadPatientInfoResp> loadPatientInfoZy(@Param("searchValue") String searchValue);

	/**
	 * 查询诊间医技预病人信息-体检
	 *
	 * @param searchValue
	 * @return
	 */
	List<LoadPatientInfoResp> loadPatientInfoTj(@Param("searchValue") String searchValue);

	/**
	 * 根据检查序号删除
	 *
	 * @param map
	 */
	void deleteByJcxh(Map<String, Object> map);

	/**
	 * 查询检查申请单集合
	 *
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> queryJcsqdList(Map<String, Object> param);

	/**
	 * 查询门诊病人信息
	 *
	 * @param jzxh
	 * @return
	 */
	Map<String, Object> queryMzBrxx(Integer jzxh);

	/**
	 * 查询住院病人信息
	 *
	 * @param zyh
	 * @return
	 */
	Map<String, Object> queryZyBrxx(String zyh);

	/**
	 * 查询体检病人信息
	 *
	 * @param tjbh
	 * @return
	 */
	Map<String, Object> queryTjBrxx(String tjbh);

	/**
	 * 住院检查申请单查询
	 * @param cisJcsq01QueryReq
	 * @return
	 */
	List<CisJcsq01QueryResp> queryZyJcSqdList(CisJcsq01QueryReq cisJcsq01QueryReq);

	/**
	 * 门诊打印信息
	 * @param parameters
	 * @return
	 */
	Map<String, Object> queryMzPrintInfo(Map<String, Object> parameters);

	/**
	 * 门诊查询检查申请单数据
	 * @param jcxh
	 * @return
	 */
	Map<String, Object> queryMzPrintInfoToJcSqd(@Param("jcxh") Integer jcxh);

	/**
	 * 住院打印信息
	 * @param parameters
	 * @return
	 */
	Map<String, Object> queryZyPrintInfo(Map<String, Object> parameters);

	/**
	 * 根据医技序号修改检查状态
	 *
	 * @param mapPar
	 */
	void updateJcztByYjxh(Map<String, Object> mapPar);

	/**
	 * 辅助报告检查列表
	 * @param req
	 * @return
	 */
	List<QueryAuxiliaryJcReportListResp> queryAuxiliaryJcReportList(QueryAuxiliaryJcReportListReq req);

	/**
	 * 根据医技序号修改检查状态
	 *
	 * @param mapPar
	 */
	void updateJcztByOneYjxh(Map<String, Object> mapPar);

	/**
	 * 病例检查申请打印数据
	 * @param jcxh
	 * @return
	 */
	BljcsqPrintResponse getBljcsqPrintData(Integer jcxh);

	/**
	 * 检查申请打印数据
	 * @param jcxh
	 * @return
	 */
	JcsqdPrintResponse getJcsqPrintData(Integer jcxh);
}
