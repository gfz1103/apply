package com.buit.apply.dao;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import com.buit.apply.model.OpSbhy;
import com.buit.apply.response.QueryRecentTimeByHyrqResp;
import com.buit.apply.response.QueryYysjViewResp;
import com.buit.commons.EntityDao;


/**
 * 设备号源表<br>
 * @author 老花生
 */
@Mapper
public interface OpSbhyDao extends EntityDao<OpSbhy,Integer>{

    /**
     * 查询号源信息
     * @param param
     * @return
     */
    List<Map<String, Object>> getHyInfo(Map<String, Object> param);

    /**
     * 更新设备号源使用状态
     * @param hyid
     */
    void updateSfyy(@Param("hyid") String hyid);

    /**
     * 解冻号源
     * @param hyid
     */
    void unfreeze(@Param("hyid") Integer hyid);

    /**
     * 查询诊间医技预约时间View数据
     * @param hyrq
     * @param jclx
     * @param hysj
     * @return
     */
    List<QueryYysjViewResp> queryYysjView(@Param("hyrq") String hyrq, @Param("jclx") Integer jclx, @Param("hysj") String hysj);
    
    /**
	 * 释放检查浩源
	 * @Title: updateNumSourceByJlxh
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param jlxh    设定文件
	 * @return void    返回类型
	 * @author 龚方舟
	 * @throws
	 */
	void updateNumSourceByJlxh(Long jlxh);

    /**
     * 冻结设备号源
     * @param hyid
     * @param djys
     */
    void freezeSbhh(@Param("hyid")Integer hyid, @Param("djys")Integer djys);

    /**
     * 解冻设备号源
     * @param hyid
     */
    void unfreezeSbhh(@Param("hyid")Integer hyid);

    /**
     * 查询设备指定日期最近的号源
     * @param jklx
     * @param hyrq
     * @param nowTime
     * @return
     */
    List<QueryRecentTimeByHyrqResp> queryRecentTimeByHyrq(@Param("jklx") Integer jklx, @Param("hyrq")Date hyrq, @Param("nowTime")String nowTime);

    /**
     * 更新是否预约，是否冻结，冻结医生
     * @param put
     */
    void updateSfyySfdjDjys(Map<String, Object> parameters);

    /**
     * 查询预约情况
     * @param param
     * @return
     */
    List<Map<String, Object>> queryYyqk(Map<String, Object> param);

    /**
     * 可预约
     * @param param
     * @return
     */
    List<Map<String, Object>> queryKyy(Map<String, Object> param);

    /**
     * 已预约
     * @param param
     * @return
     */
    List<Map<String, Object>> queryYyy(Map<String, Object> param);
    
    /**
     * 根据申请id释放检查浩源
     * @Title: updateNumSourceBySqid
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @param sqid
     * @param @param yzlx    设定文件
     * @return void    返回类型
     * @author 龚方舟
     * @throws
     */
   	void updateNumSourceBySqid(@Param("sqid") Integer sqid, @Param("yzlx") Integer yzlx);
}
