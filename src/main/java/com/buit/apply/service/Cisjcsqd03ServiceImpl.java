package com.buit.apply.service;

import com.buit.apply.dao.CisJcsq03Dao;
import com.buit.apply.response.CisJcsq03Resp;
import com.buit.utill.BeanUtil;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author weijing
 * @Date 2020-12-17 18:29
 * @Description
 **/
@DubboService(timeout = 10000)
public class Cisjcsqd03ServiceImpl implements Cisjcsqd03Service{
    @Autowired
    private CisJcsq03Dao cisJcsq03Dao;

    /**
     *通过检查序号和机构id查询检查申请送检标本数据
     * @param jgid
     * @param jcxh
     * @return
     */
    @Override
    public List<CisJcsq03Resp> queryCisJcsq03ByJcxh(Integer jgid, Integer jcxh) {
        return BeanUtil.toBeans(cisJcsq03Dao.queryCisJcsq03ByJcxh(jcxh,jgid),CisJcsq03Resp.class);
    }
}
