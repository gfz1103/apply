package com.buit.apply.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.buit.apply.dao.OpSbhyDao;
import com.buit.apply.request.DicZlxmRecentTimeApiReq;
import com.buit.apply.request.QueryYjyyDataReq;
import com.buit.apply.response.DicZlxmRecentTimeApiResp;
import com.buit.apply.response.QueryRecentTimeByHyrqResp;
import com.buit.apply.response.QueryYysjViewResp;
import com.buit.system.utill.ObjectToTypes;
import com.buit.utill.ParamUtil;

import cn.hutool.core.bean.BeanUtil;

/**
 * 设备号源外部接口实现
 * @ClassName: OpSbhyServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 龚方舟
 * @date 2020年10月14日 下午5:18:27
 *
 */
@DubboService(timeout = 10000)
public class OpSbhyServiceImpl implements OpSbhyService{
	
	@Autowired
	private OpSbhyDao opSbhyDao;

	@Override
	public List<Map<String, Object>> getHyInfo(Map<String, Object> param) {
		return opSbhyDao.getHyInfo(param);
	}

	@Override
	public void updateSfyy(String hyid) {
		opSbhyDao.updateSfyy(hyid);
	}

	@Override
	public void unfreeze(Integer hyid) {
		opSbhyDao.unfreeze(hyid);
	}

	@Override
	public List<QueryYysjViewResp> queryYysjView(String hyrq, Integer jclx, String hysj) {
		return opSbhyDao.queryYysjView(hyrq, jclx, hysj);
	}

	@Override
	public void updateNumSourceByJlxh(Long jlxh) {
		opSbhyDao.updateNumSourceByJlxh(jlxh);
	}

	@Override
	public void freezeSbhh(Integer hyid, Integer djys) {
		opSbhyDao.freezeSbhh(hyid, djys);
	}

	@Override
	public void unfreezeSbhh(Integer hyid) {
		opSbhyDao.unfreezeSbhh(hyid);
	}

	@Override
	public List<QueryRecentTimeByHyrqResp> queryRecentTimeByHyrq(Integer jklx, Date hyrq, String nowTime) {
		return opSbhyDao.queryRecentTimeByHyrq(jklx, hyrq, nowTime);
	}

	@Override
	public void updateSfyySfdjDjys(Map<String, Object> parameters) {
		opSbhyDao.updateSfyySfdjDjys(parameters);
	}

	@Override
	public List<Map<String, Object>> queryYyqk(Map<String, Object> param) {
		return opSbhyDao.queryYyqk(param);
	}

	@Override
	public List<Map<String, Object>> queryKyy(Map<String, Object> param) {
		return opSbhyDao.queryKyy(param);
	}

	@Override
	public List<Map<String, Object>> queryYyy(Map<String, Object> param) {
		return opSbhyDao.queryKyy(param);
	}

	@Override
	public void updateNumSourceBySqid(Integer sqid, Integer yzlx) {
		opSbhyDao.updateNumSourceBySqid(sqid, yzlx);
	}

	@Override
	public DicZlxmRecentTimeApiResp queryRecentTime(DicZlxmRecentTimeApiReq req) {
		Map<String, Object> result = new HashMap<>(16);

        //当前实际加30分钟
        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(System.currentTimeMillis()+1800000));
        // 接口类型
        String jklx = req.getJklx();
        Map<String, Object> param = ParamUtil.instance().put("JKLX", jklx).put("HYRQ", nowTime);
        //查询号源信息
        List<Map<String, Object>> list = opSbhyDao.getHyInfo(param);

        if (list != null && list.size() > 0) {
            result = list.get(0);
        }

        DicZlxmRecentTimeApiResp resp = BeanUtil.mapToBean(result, DicZlxmRecentTimeApiResp.class, false);

        return resp;
	}

	@Override
	public Map<String, Object> queryYjyyData(QueryYjyyDataReq req) {
		 String BRXMSQL = "";

	        Map<String, Object> param = new HashMap<>();
	        param.put("data",req.getDate());
	        param.put("data1", req.getDate1());
	        param.put("brxm", req.getBrxm());
	        List<Map<String, Object>> list = opSbhyDao.queryYyqk(param);

	        param.put("jczt", "1");//条件jczt>2
	        List<Map<String, Object>> yjclist = opSbhyDao.queryYyqk(param);

	        Map<String, Object> retmap=new HashMap<String, Object>();
	        String value="0";
	        for(Map<String, Object> map:list){
	            value=ObjectToTypes.parseString(map.get("value")+"(0)");
	            for(Map<String, Object> yjclistmap:yjclist){
	                if(ObjectToTypes.parseString(map.get("id")).equals(ObjectToTypes.parseString(yjclistmap.get("id")))){
	                    value=ObjectToTypes.parseString(map.get("value"))+"("+ObjectToTypes.parseString(yjclistmap.get("value"))+")";
	                }
	            }
	            retmap.put(ObjectToTypes.parseString(map.get("id")), value);
	        }

	        //可预约
	        List<Map<String, Object>>	kyyslist = opSbhyDao.queryKyy(param);
	        for(Map<String, Object> map:kyyslist){
	            retmap.put(ObjectToTypes.parseString(map.get("id")), map.get("value"));
	        }

	        //已预约
	        List<Map<String, Object>> yyyslist = opSbhyDao.queryYyy(param);
	        for(Map<String, Object> map:yyyslist){
	            retmap.put(ObjectToTypes.parseString(map.get("id")), map.get("value"));
	        }

	        return retmap;
	}

}
