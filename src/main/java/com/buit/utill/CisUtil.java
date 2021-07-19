package com.buit.utill;

import com.buit.commons.BaseException;
import com.buit.drug.response.DrugsTypkDetailResp;
import com.buit.drug.service.DrugsTypkOutSer;
import com.buit.system.request.FeeYpxzApiReq;
import com.buit.system.request.PubFyxzApiReq;
import com.buit.system.response.FeeSfdlzfbl;
import com.buit.system.response.FeeYlsfxmOutResp;
import com.buit.system.service.FeeSfdlzfblOutSer;
import com.buit.system.service.FeeYlsfxmOutSer;
import com.buit.system.service.FeeYpxzService;
import com.buit.system.service.PubFyxzService;
import com.buit.system.utill.ObjectToTypes;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名：CisUtil
 * 描述：
 *
 * @author : liushijie
 * 2020/10/14
 **/
@Service
public class CisUtil {

    @DubboReference
    private DrugsTypkOutSer drugsTypkOutSer;
    @DubboReference
    private FeeYlsfxmOutSer feeYlsfxmOutSer;
    @DubboReference
    private PubFyxzService pubFyxzService;
    @DubboReference
    private FeeYpxzService feeYpxzDao;
    @DubboReference
    private FeeSfdlzfblOutSer feeSfdlzfblOutSer;

    /**
     * @name: listToString
     * @description: list集合转string
     * @return: java.lang.String
     * @date: 2020/8/28 9:41
     * @auther: 老花生
     *
     */
    public static String listToString(List list){
        if(list == null) {
            return "";
        }
        StringBuilder b = new StringBuilder();
        int iMax = list.size() - 1;
        for (int i = 0; i<list.size(); i++) {
            b.append(list.get(i));
            if(i == iMax){
                break;
            }
            b.append(", ");
        }
        return b.toString();
    }

    /**
     * 获取费用归并
     *
     * @param al_yplx
     * @param al_fyxh
     * @return
     */
    public Integer getfygb(Integer al_yplx, Integer al_fyxh) {
        Integer al_fygb;
        if (al_yplx == 1 || al_yplx == 2 || al_yplx == 3) {// 如果不是费用,先查询有吴账簿类别,没有账簿类别则按药品类型分
            DrugsTypkDetailResp drugsTypk = drugsTypkOutSer.getDrugsTypkById(al_fyxh);
            if (drugsTypk != null && drugsTypk.getZblb() != null && drugsTypk.getZblb().intValue() > 0) {
                al_fygb = drugsTypk.getZblb();
            } else {
                throw BaseException.create("ERROR_REG_0011");
            }
        } else {
            FeeYlsfxmOutResp feeYlsfxm = feeYlsfxmOutSer.getById(al_fyxh);
            al_fygb = feeYlsfxm.getFygb();
        }
        return al_fygb;
    }

    /**
     * @name: getPayProportion
     * @description: 获取费用自付比例
     * @param brxz 病人性质
     * @param fygb 费用归并
     * @param type 药品传药品类型1,2,3,费用传0
     * @param ypxh 费用序号
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     * @date: 2020/9/20 13:40
     * @auther: 朱智
     *
     */
    public Map<String, Object> getPayProportion(Object brxz, Object fygb, Object type, Object ypxh) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("BRXZ", brxz);// 病人性质
        params.put("FYGB", fygb);// 费用归并
        params.put("TYPE", type);// 药品类型 0=项目
        params.put("FYXH", ypxh);// 费用序号
        return getPayProportion(params);
    }

    /**
     * 获取费用自负比例
     *
     * @param body
     * @return
     */
    public Map<String, Object> getPayProportion(Map<String, Object> body) {
        Integer al_yplx = Integer.parseInt(body.get("TYPE") + "");// 药品传药品类型1,2,3,费用传0
        Object al_brxz = body.get("BRXZ");// 病人性质
        Object al_fyxh = body.get("FYXH");// 费用序号
        Object al_fygb = body.get("FYGB");// 费用归并
        Map<String, Object> params = new HashMap<String, Object>(16);
        params.put("BRXZ", al_brxz);
        params.put("FYXH", al_fyxh);
        Map<String, Object> map = new HashMap<String, Object>(16);
        if (al_yplx == 0) {
            PubFyxzApiReq pubFyxz = new PubFyxzApiReq();
            pubFyxz.setBrxz(Integer.valueOf(params.get("BRXZ").toString()));
            pubFyxz.setFyxh(Integer.valueOf(params.get("FYXH").toString()));
            // 查询费用禁用信息
            map = pubFyxzService.getFyjyInfo(pubFyxz);
        } else {
            FeeYpxzApiReq feeYpxz = new FeeYpxzApiReq();
            feeYpxz.setBrxz(Integer.valueOf(params.get("BRXZ").toString()));
            feeYpxz.setYpxh(Integer.valueOf(params.get("FYXH").toString()));
            // 查询药品禁用信息
            map = feeYpxzDao.getYpjyInfo(feeYpxz);
        }
        if (map != null) {
            if (map.get("FYXE") == null || map.get("FYXE") == "") {
                map.put("FYXE", 0.0);
            }
            if (map.get("CXBL") == null || map.get("CXBL") == "") {
                map.put("CXBL", 0.0);
            }
            map.put("CXBL", ObjectToTypes.parseBigDecimal(map.get("CXBL")).divide(new BigDecimal(100), 2,
                    BigDecimal.ROUND_HALF_UP));
            return map;
        }
        params.clear();
        params.put("BRXZ", al_brxz);
        params.put("FYGB", al_fygb);

        FeeSfdlzfbl feeSfdlzfbl = new FeeSfdlzfbl();
        feeSfdlzfbl.setBrxz(Integer.valueOf(params.get("BRXZ").toString()));
        feeSfdlzfbl.setSfxm(Integer.valueOf(params.get("FYGB").toString()));
        Map<String, Object> zfbl_map = feeSfdlzfblOutSer.getZfblInfo(feeSfdlzfbl);
        if (zfbl_map == null) {
            zfbl_map = new HashMap<String, Object>(16);
            zfbl_map.put("ZFBL", 1);
        }
        return zfbl_map;
    }
}
