package com.buit.apply.service;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.buit.apply.dao.OpZt02Dao;
import com.buit.apply.model.OpZt02;
import com.buit.apply.response.DrugsBody;
import com.buit.apply.response.OpZt02DetailResp;
import com.buit.apply.response.OpZt02ProjectResp;
import com.buit.apply.response.OpZt02Resp;
import com.buit.commons.PageQuery;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 组套外部接口实现
 * @ClassName: OpZt02ServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 龚方舟
 * @date 2020年10月14日 下午4:00:40
 *
 */
@DubboService(timeout = 10000)
public class OpZt02ServiceImpl implements OpZt02Service{

	@Autowired
	private OpZt02Dao opZt02Dao;
	
	@Override
	public List<Map<String, Object>> queryCommonDrugsInfoByJlbh(Map<String, Object> parameters) {
		return opZt02Dao.queryCommonDrugsInfoByJlbh(parameters);
	}

	@Override
	public OpZt02 getById(Integer id) {
		return opZt02Dao.getById(id);
	}

	@Override
	public List<Map<String, Object>> queryPackageDetailsByZtbh(Map<String, Object> parameters) {
		return opZt02Dao.queryPackageDetailsByZtbh(parameters);
	}

	@Override
	public List<Map<String, Object>> queryProjectPackageInfoByZtbh(Map<String, Object> parameters) {
		return opZt02Dao.queryProjectPackageInfoByZtbh(parameters);
	}

	@Override
	public List<OpZt02Resp> findByZtbhXm(Integer ztbh) {
		return opZt02Dao.findByZtbhXm(ztbh);
	}

	@Override
	public List<Map<String, Object>> getJlbhXmbhXmslFydjSb(Map<String, Object> param) {
		return opZt02Dao.getJlbhXmbhXmslFydjSb(param);
	}

	@Override
	public List<Map<String, Object>> getJlbhXmbhXmslFydjFSb(Map<String, Object> param) {
		return opZt02Dao.getJlbhXmbhXmslFydjFSb(param);
	}

	@Override
	public List<OpZt02DetailResp> queryDetailByZtbh(Integer ztbh) {
		return opZt02Dao.queryDetailByZtbh(ztbh);
	}

	@Override
	public List<OpZt02Resp> findByZtbh(Integer ztbh) {
		return opZt02Dao.findByZtbh(ztbh);
	}

	@Override
	public void deleteByZtbh(Integer ztbh) {
		opZt02Dao.deleteByZtbh(ztbh);
	}

	@Override
	public void insert(Object obj) {
		opZt02Dao.insert(obj);
	}

	/**
	 * 根据组套编号查询药品组套信息
	 * @Title: getYpZtInfoByZtbh
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param parameters
	 * @param @return    设定文件
	 * @author 龚方舟
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> getYpZtInfoByZtbh(Map<String, Object> parameters) {
		return opZt02Dao.getYpZtInfoByZtbh(parameters);
	}

	/**
	 * 查询医技组套明细
	 * @Title: queryYjZtMx
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param parameters
	 * @param @return    设定文件
	 * @author 龚方舟
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> queryYjZtMx(Map<String, Object> parameters) {
		return opZt02Dao.queryYjZtMx(parameters);
	}

	/**
	 * 查询项目编号、医嘱名称、费用单价
	 * @Title: queryXmbhYzmcFydj
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param detailsParameter
	 * @param @return    设定文件
	 * @author 龚方舟
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> queryXmbhYzmcFydj(Map<String, Object> detailsParameter) {
		return opZt02Dao.queryXmbhYzmcFydj(detailsParameter);
	}

	/**
	 * 根据组套编号获取组套信息
	 * @Title: getZtInfoByZtbh
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param param
	 * @param @return    设定文件
	 * @author 龚方舟
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> getZtInfoByZtbh(Map<String, Object> param) {
		return opZt02Dao.getZtInfoByZtbh(param);
	}

	@Override
	public PageInfo<DrugsBody> queryCommonDrugsByQueryPage(Integer ygdm, Integer ksdm, 
			Integer jgid, String pydm, PageQuery page) {
		PageInfo<DrugsBody> pageInfo = PageHelper.startPage(
        		page.getPageNum(), page.getPageSize()).doSelectPageInfo(
                () -> opZt02Dao.queryCommonDrugs(ygdm, ksdm, jgid, pydm)
        );
		return pageInfo;
	}

	@Override
	public PageInfo<OpZt02ProjectResp> queryCommonProjectByQueryPage(Integer ygdm, Integer ksdm, Integer jgid,
			String pydm, PageQuery page) {
		PageInfo<OpZt02ProjectResp> pageInfo = PageHelper.startPage(
        		page.getPageNum(), page.getPageSize()).doSelectPageInfo(
                () -> opZt02Dao.queryCommonProject(ygdm, ksdm, jgid, pydm)
        );
		return pageInfo;
	}

	@Override
	public PageInfo<OpZt02Resp> findByZtbhXmByQueryPage(Integer ztbh, PageQuery page) {
		PageInfo<OpZt02Resp> pageInfo = PageHelper.startPage(
        		page.getPageNum(), page.getPageSize()).doSelectPageInfo(
                () -> opZt02Dao.findByZtbhXm(ztbh)
        );
		return pageInfo;
	}

	@Override
	public void deleteById(Integer id) {
		opZt02Dao.deleteById(id);
	}

}
