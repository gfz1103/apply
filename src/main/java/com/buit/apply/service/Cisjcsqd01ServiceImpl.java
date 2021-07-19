package com.buit.apply.service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.util.StrUtil;
import com.buit.apply.response.*;
import com.buit.system.service.*;
import com.buit.utill.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.buit.apply.dao.CisJcsq01Dao;
import com.buit.apply.dao.CisJcsq02Dao;
import com.buit.apply.dao.CisJcsq03Dao;
import com.buit.apply.dao.OpSbhyDao;
import com.buit.apply.model.CisJcsq01;
import com.buit.apply.model.CisJcsq01Model;
import com.buit.apply.model.CisJcsq03;
import com.buit.apply.model.DicYjlx;
import com.buit.apply.request.CisJcsq01PageReq;
import com.buit.apply.request.CisJcsq01QueryReq;
import com.buit.apply.request.DicZlxmSqdReqYsJx02;
import com.buit.apply.request.JcSqdReq;
import com.buit.apply.request.QueryAuxiliaryJcReportListReq;
import com.buit.apply.request.YjyySaveReq;
import com.buit.cis.im.service.ImRyzdService;
import com.buit.commons.BaseException;
import com.buit.commons.PageQuery;
import com.buit.commons.SysUser;
import com.buit.constans.TableName;
import com.buit.op.model.OpYjcf02Zt;
import com.buit.op.request.OpYjcf01Req;
import com.buit.op.request.OpYjcf02Req;
import com.buit.op.service.OpBrzdService;
import com.buit.op.service.OpYjcf01Service;
import com.buit.op.service.OpYjcf02Service;
import com.buit.op.service.OpYjcf02ZtService;
import com.buit.system.model.DicYjlxModel;
import com.buit.system.model.DiccZlsfdz;
import com.buit.system.response.PubBrxzOut;
import com.buit.system.utill.ObjectToTypes;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import jodd.util.StringUtil;

/**
 * 类名：Cisjzsqd01ServiceImpl
 * 描述：检查申请单
 *
 * @author : liushijie
 * 2020/10/13
 **/
@DubboService(timeout = 10000)
public class Cisjcsqd01ServiceImpl implements Cisjcsqd01Service {
    static final Logger logger = LoggerFactory.getLogger(Cisjcsqd01ServiceImpl.class);


    @Autowired
    private CisJcsq01Dao cisJcsq01Dao;

    @Autowired
    private CisJcsq02Dao cisJcsq02Dao;

    @Autowired
    private CisJcsq03Dao cisJcsq03Dao;

    @DubboReference
    private OpYjcf01Service opYjcf01Service;

    @DubboReference
    private OpYjcf02Service opYjcf02Service;

    @DubboReference
    private SysXtcsCacheSer sysXtcsCacheSer;

    @DubboReference
    private ExportFileSer exportFileSer;

    @DubboReference
    private HrPersonnelService hrPersonnelService;

    @DubboReference
    private DicGbsj02Service dicGbsj02Servicel;

    @DubboReference
    private DicZlxmService dicZlxmService;

    @DubboReference
    private DicYjlxService dicYjlxService;

    @DubboReference
    private DicKszdOutSer dicKszdOutSer;

    @DubboReference
    private DicYljgOutSer dicYljgOutSer;

    @DubboReference
    private ImRyzdService imRyzdService;

    @DubboReference
    private OpBrzdService opBrzdService;

    @Autowired
    private OpSbhyDao opSbhyDao;

    @DubboReference
    private PubBrxzOutSer pubBrxzOutSer;
    @DubboReference
    private FeeYlsfxmOutSer feeYlsfxmOutSer;
    @Autowired
    private RedisFactory redisFactory;
    @DubboReference
    private DiccZlsfdzOutSer diccZlsfdzOutSer;
    @Autowired
    private CisUtil cisUtil;
    @DubboReference
    private OpYjcf02ZtService opYjcf02ZtService;
    @DubboReference
    private DicXzqhService dicXzqhService;

    /**
     * 根据条件分页查询
     *
     * @param req
     * @return
     */
    @Override
    public PageInfo<CisJcsq01PageResp> queryPage(CisJcsq01PageReq req) {
        PageInfo<CisJcsq01PageResp> pageInfo = null;
        Map par = cn.hutool.core.bean.BeanUtil.beanToMap(req);
        if (req.getBeginDate() != null) {
            par.put("beginDate", DateUtil.beginOfDay(req.getBeginDate()));
        }
        if (req.getEndDate() != null) {
            par.put("endDate", DateUtil.endOfDay(req.getEndDate()));
        }
        // 申请类型(1=门诊 2=住院)
        if ("1".equals(req.getSqlx())) {
            pageInfo = PageHelper.startPage(
                    req.getPageNum(), req.getPageSize()).doSelectPageInfo(
                    () -> cisJcsq01Dao.findByEntityMz(par)
            );
        } else {
            pageInfo = PageHelper.startPage(
                    req.getPageNum(), req.getPageSize()).doSelectPageInfo(
                    () -> cisJcsq01Dao.findByEntityZy(par)
            );
        }
        return pageInfo;
    }

    /**
     * 住院检查申请单查询
     *
     * @param req
     * @param page
     * @return
     */
    @Override
    public PageInfo<CisJcsq01QueryResp> queryZyJcSqdList(CisJcsq01QueryReq req, PageQuery page) {
        req.setSqlx(2);
        PageInfo<CisJcsq01QueryResp> pageInfo = PageHelper.startPage(
                page.getPageNum(), page.getPageSize()).doSelectPageInfo(
                () -> cisJcsq01Dao.queryZyJcSqdList(req)
        );
        return pageInfo;
    }


    /**
     * 辅助报告检查列表
     *
     * @param req
     * @return
     */
    @Override
    public List<QueryAuxiliaryJcReportListResp> queryAuxiliaryJcReportList(QueryAuxiliaryJcReportListReq req) {
        //req.setStartTime(DateUtil.beginOfDay(DateUtil.date().toSqlDate()).toTimestamp());
        //req.setEndTime(DateUtil.endOfDay(DateUtil.date().toSqlDate()).toTimestamp());
        return cisJcsq01Dao.queryAuxiliaryJcReportList(req);
    }

    /***
     * 检查申请单打印
     * @param jcxh  查询申请单信息
     * @param sqlx  申请类型(1=门诊 2=住院)
     * @param hospitalId    医疗机构ID
     * @return
     */
    @Override
    public Map<String, Object> jcsqdPrint(Integer jcxh, Integer sqlx, Integer hospitalId) {
//        String jasperName = "FsSqdForm.jasper";
        String jasperName = "HosCheckForm.jasper";
        Map<String, Object> params = getZydjPringParameters(jcxh, sqlx, hospitalId);
//        List<Map<String, Object>> list = new ArrayList<>();
        String url = exportFileSer.reportHtml(params, jasperName);
        return params;
    }

    /**
     * 辅助报告检查删除
     * @param jcxh
     * @param yjxh
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auxiliaryJcDel(Integer jcxh, Integer yjxh) {
        CisJcsq01 jcsq = cisJcsq01Dao.getById(jcxh);
        if(jcsq.getJczt() < 3){
            cisJcsq01Dao.deleteById(jcxh);
            opYjcf01Service.delById(yjxh);
        }else{
            throw BaseException.create("ERROR_DCTWORK_OP_0045");
        }
    }


    /**
     * 获取门诊检查申请单数据
     * @param jcxh
     * @param sqlx
     * @param hospitalId
     * @return
     */
    private Map<String, Object> getZydjPringParameters(Integer jcxh, Integer sqlx, Integer hospitalId) {
        //Map<String, Object> parameters = new HashMap<>();

        //查询检查申请单信息
        Map<String, Object> result = cisJcsq01Dao.queryMzPrintInfoToJcSqd(jcxh);

        //查询诊断信息
        List<String> zds = opBrzdService.queryBrzdByJzxh(result.get("JZXH").toString());
        String zdmc = CisUtil.listToString(zds);
        result.put("MQZD", zdmc);

//        parameters.put("jcxh", jcxh);
//        Map<String, Object> result = new HashMap<>();
//        if (sqlx.intValue() == 1) {
//            result = new CaseInsensitiveMap(cisJcsq01Dao.queryMzPrintInfo(parameters));
//        } else if (sqlx.intValue() == 2) {
//            result = new CaseInsensitiveMap(cisJcsq01Dao.queryZyPrintInfo(parameters));
//        }
//        DecimalFormat df = new DecimalFormat("######0.00");
//        // 检查费用
//        result.put("JCFY", df.format(MedicineUtils.parseDouble(result.get("JCFY"))) + "元");
//        // 显示科室名称
//        if (result.get("BRKS") != null && !result.get("BRKS").toString().equals("")) {
//            Map<String, Object> param = ParamUtil.instance().put("organizcode", hospitalId)
//                    .put("id", ObjectToTypes.parseInt(result.get("BRKS")));
//            List<DicKszdModel> ret = dicKszdOutSer.findByEntity(param);
//            String brks = "";
//            if (ret != null && !ret.isEmpty()) {
//                brks = ret.get(0).getOfficename();
//            }
//            result.put("BRKS", brks);
//        }
//        // 显示病人性别
//        if (result.get("BRXB") != null && !result.get("BRXB").toString().equals("")) {
//            result.put("BRXB", dictionaryConversion("LS0000000821",
//                    ObjectToTypes.parseString(result.get("BRXB"))));
//        }
//
//        // 显示申请医师
//        if (result.get("SQYS") != null && !result.get("SQYS").toString().equals("")) {
//            String jzys = "";
//            HrPersonnelModel hrPersonnel = hrPersonnelService.getPersonnelById(Integer.valueOf(result.get("SQYS").toString()));
//            if (hrPersonnel != null) {
//                jzys = hrPersonnel.getPersonname();
//            }
//            result.put("SQYS", jzys);
//        }
//
//        // 病人年龄
//        if (result.get("CSNY") != null && !result.get("CSNY").toString().equals("")) {
//            result.put("AGE", DateUtil.ageOfNow(result.get("CSNY").toString()));
//        }
//
//        // 查询诊断
//        if (sqlx.intValue() == 2) {
//            List<String> zds = imRyzdService.queryZdmcByZyh(result.get("ZYH").toString());
//            String zdmc = CisUtil.listToString(zds);
//            result.put("MQZD", zdmc);
//        } else if (sqlx.intValue() == 1) {
//            List<String> zds = opBrzdService.queryBrzdByJzxh(result.get("JZXH").toString());
//            String zdmc = CisUtil.listToString(zds);
//            result.put("MQZD", zdmc);
//        }
//
//        // 查询检查知情同意书，注意事项
//        String JCZQTYS = "";
//        String ZYSX = "";
//
//        List<CisJcsq02> jc02 = cisJcsq02Dao.findByEntity(parameters);
//        for (CisJcsq02 jc : jc02) {
//            Map<String, Object> m = BeanUtil.beanToMap(jc);
//            Map<String, Object> zysx = BeanUtil.beanToMap(dicZlxmService.getById(MedicineUtils.parseInt(m.get("fyxh"))));
//            if (zysx.get("jczqtys") != null) {
//                if (JCZQTYS.indexOf(zysx.get("jczqtys").toString()) == -1) {
//                    JCZQTYS += MedicineUtils.parseString(zysx.get("jczqtys")) + ";";
//                }
//            }
//            if (zysx.get("zysx") != null) {
//                if (ZYSX.indexOf(zysx.get("zysx").toString()) == -1) {
//                    ZYSX += MedicineUtils.parseString(zysx.get("zysx")) + ";";
//                }
//            }
//        }
//        if (JCZQTYS.length() > 0) {
//            JCZQTYS = JCZQTYS.substring(0, JCZQTYS.lastIndexOf(";"));
//        }
//        if (ZYSX.length() > 0) {
//            ZYSX = ZYSX.substring(0, ZYSX.lastIndexOf(";"));
//        }
//        result.put("JCZQTYS", JCZQTYS);
//        result.put("ZYSX", ZYSX);
//
//        // 以下内镜申请单8个是否字段
//        if (result.get("ALT") != null
//                && !result.get("ALT").toString().equals("")) {
//            result.put("ALT", dictionaryConversion("cs011",
//                    ObjectToTypes.parseString(result.get("ALT"))));
//        }
//        if (result.get("HBSAG") != null
//                && !result.get("HBSAG").toString().equals("")) {
//            result.put("HBSAG", dictionaryConversion("cs011",
//                    ObjectToTypes.parseString(result.get("HBSAG"))));
//        }
//        if (result.get("SFWT") != null
//                && !result.get("SFWT").toString().equals("")) {
//            result.put("SFWT", dictionaryConversion("cs011",
//                    ObjectToTypes.parseString(result.get("SFWT"))));
//        }
//        if (result.get("YWTNBS") != null
//                && !result.get("YWTNBS").toString().equals("")) {
//            result.put("YWTNBS", dictionaryConversion("cs011",
//                    ObjectToTypes.parseString(result.get("YWTNBS"))));
//        }
//        if (result.get("KNJSY") != null
//                && !result.get("KNJSY").toString().equals("")) {
//            result.put("KNJSY", dictionaryConversion("cs011",
//                    ObjectToTypes.parseString(result.get("KNJSY"))));
//        }
//        if (result.get("YCXHJQ") != null
//                && !result.get("YCXHJQ").toString().equals("")) {
//            result.put("YCXHJQ", dictionaryConversion("cs011",
//                    ObjectToTypes.parseString(result.get("YCXHJQ"))));
//        }
//        if (result.get("WJSGJ") != null
//                && !result.get("WJSGJ").toString().equals("")) {
//            result.put("WJSGJ", dictionaryConversion("cs011",
//                    ObjectToTypes.parseString(result.get("WJSGJ"))));
//        }
//        if (result.get("SXHDSSS") != null
//                && !result.get("SXHDSSS").toString().equals("")) {
//            result.put("SXHDSSS", dictionaryConversion("cs011",
//                    ObjectToTypes.parseString(result.get("SXHDSSS"))));
//        }
//
//        result.put("JGMC", dicYljgOutSer.getById(hospitalId).getHospitalName());
//
//        String title = "";
//        String wxts = "";
//        Integer jgid = hospitalId;
//        String YFLXDH = "";
//        /*
//         * if ("1".equals(sqlx)) { if(result.get("YYRQ") == null){ wxts =
//         * "付费时间：此申请单未付费，请务必在" + result.get("SQSJ").toString().substring(0, 10)+
//         * " 17:00 之前付费否则会导致不能正确预约。 \n" + "检查时间：请于"+
//         * result.get("SQSJ").toString().substring(0, 10) +
//         * " 17:00之前到1楼放射服务台"+""+"登记，等候检查。"; }else{ wxts = "付费时间：此申请单未付费，请务必在" +
//         * result.get("SQSJ").toString().substring(0, 10)+
//         * " 17:00 之前付费否则会导致不能正确预约。 \n" + "检查时间：请于"+ result.get("YYRQ") + " " +
//         * result.get("YYSJ")+ "到1楼放射服务台"+""+"登记，等候检查。"; } }else{ wxts = ""; }
//         */
//        String JCLX = MedicineUtils.parseString(result.get("JCLX"));
//        if (sqlx.intValue() == 1) {
//            if (result.get("YYRQ") == null) {
//                if ("1".equals(JCLX) || "2".equals(JCLX) || "3".equals(JCLX)
//                        || "4".equals(JCLX)) {
//                    wxts = "提示:请务必在"
//                            + result.get("SQSJ").toString().substring(0, 10)
//                            + " 17:00 前付费,否则会导致不能正确预约。\n" + "检查时间:请于"
//                            + result.get("SQSJ").toString().substring(0, 10)
//                            + " 17:00前到1楼放射登记台" + "" + "登记，等候检查。";
//                }
//                if ("5".equals(JCLX) || "10".equals(JCLX)) {
//                    wxts = "提示:请务必在"
//                            + result.get("SQSJ").toString().substring(0, 10)
//                            + " 17:00 前付费,否则会导致不能正确预约。\n" + "检查时间:请于"
//                            + result.get("SQSJ").toString().substring(0, 10)
//                            + " 17:00前到2楼功能科分诊台" + "" + "登记，等候检查。";
//                }
//                if ("20".equals(JCLX)) {
//                    wxts = "提示:请务必在"
//                            + result.get("SQSJ").toString().substring(0, 10)
//                            + " 17:00 前付费,否则会导致不能正确预约。\n" + "检查时间:请于"
//                            + result.get("SQSJ").toString().substring(0, 10)
//                            + " 17:00前到4楼内镜分诊台" + "" + "登记，等候检查。";
//                }
//            } else {
//                if ("1".equals(JCLX) || "2".equals(JCLX) || "3".equals(JCLX)
//                        || "4".equals(JCLX)) {
//                    wxts = "提示:请务必在"
//                            + result.get("SQSJ").toString().substring(0, 10)
//                            + " 17:00 前付费,否则会导致不能正确预约 。\n" + "检查时间:请于"
//                            + result.get("YYRQ") + " " + result.get("YYSJ")
//                            + "到1楼放射登记台" + "" + "登记，等候检查。";
//                }
//                if ("5".equals(JCLX) || "10".equals(JCLX)) {
//                    wxts = "提示:请务必在"
//                            + result.get("SQSJ").toString().substring(0, 10)
//                            + " 17:00 前付费,否则会导致不能正确预约。 \n" + "检查时间:请于"
//                            + result.get("YYRQ") + " " + result.get("YYSJ")
//                            + "到2楼功能科分诊台" + "" + "登记，等候检查。";
//                }
//                if ("20".equals(JCLX)) {
//                    wxts = "提示:请务必在"
//                            + result.get("SQSJ").toString().substring(0, 10)
//                            + " 17:00 前付费,否则会导致不能正确预约。\n" + "检查时间:请于"
//                            + result.get("YYRQ") + " " + result.get("YYSJ")
//                            + "到4楼内镜分诊台" + "" + "登记，等候检查。";
//                }
//            }
//        } else {
//            wxts = "";
//        }
//        // 院方联系电话
//        // 放射
//        if ("1".equals(JCLX) || "2".equals(JCLX) || "3".equals(JCLX)
//                || "4".equals(JCLX)) {
//            YFLXDH = sysXtcsCacheSer.getCsz(jgid, "SQDYFLXDH");
//            result.put("YFLXDH", YFLXDH);
//        }
//        // 超声和心电
//        if ("5".equals(JCLX) || "10".equals(JCLX)) {
//            YFLXDH = sysXtcsCacheSer.getCsz(jgid, "SQDCSXDLXDH");
//            result.put("YFLXDH", YFLXDH);
//        }
//        // 内镜
//        if ("20".equals(JCLX)) {
//            YFLXDH = sysXtcsCacheSer.getCsz(jgid, "SQDNJLXDH");
//            result.put("YFLXDH", YFLXDH);
//        }
//
//        Map<String, Object> preNode = new HashMap<String, Object>();
//
//        DicYjlxModel dicYjlx = dicYjlxService.getById(MedicineUtils.parseInt(result.get("YJLX")));
//
//        if (dicYjlx.getPid().intValue() == 3) {//内镜申请单打印的标题单独做判断
//            preNode = ParamUtil.instance().put("NAME", dicYjlx.getName());
//        } else {
//            preNode = ParamUtil.instance().put("NAME", dicYjlxService.getById(dicYjlx.getPid()).getName());
//        }
//        if (preNode != null && preNode.get("NAME") != null) {
//            title = preNode.get("NAME").toString();
//            if (title.lastIndexOf("申请单") >= 0) {
//                title = title.substring(0, title.lastIndexOf("申请单"));
//            }
//        }
//
//        result.put("title", title + "申请单");
//        result.put("WXTS", wxts);

        return result;
    }

    /**
     * @name: dictionaryConversion
     * @description: 字典项翻译
     * @param code	字典代码:例如LS0000000821
     * @param value 要翻译值
     * @return: java.lang.String 翻译后值
     * @date: 2020/8/26 20:09
     * @auther: 老花生
     *
     */
    public String dictionaryConversion(String code, String value){
        if(StringUtils.isBlank(code) || StringUtils.isBlank(value)){
            return null;
        }
        List<Map<String, String>> ret = dicGbsj02Servicel.getValueByPrimaryDataCode(code);
        for(Map<String, String> m : ret){
            if(value.equals(m.get("DATA_VALUE"))){
                return m.get("DATA_VALUE_REMARK");
            }
        }
        return null;
    }

    @Override
    public void deleteByJcxh(Map<String, Object> map) {
        cisJcsq01Dao.deleteByJcxh(map);
    }

	@Override
	public void jc01Insert(Object obj) {
		cisJcsq01Dao.insert(obj);
	}

	@Override
	public void jc01DeleteById(Integer id) {
		cisJcsq01Dao.deleteById(id);
	}

	@Override
	public void jc02Insert(Object obj) {
		cisJcsq02Dao.insert(obj);
	}

	@Override
	public void jc02DeleteById(Integer id) {
		cisJcsq02Dao.deleteById(id);
	}

	@Override
	public void deleteInspectByJlxh(Long jlxh) {
		cisJcsq01Dao.deleteInspectByJlxh(jlxh);
	}

	@Override
	public List<CisJcsq02ZlxmResp> queryYsJcDicZlxmInfo(Integer jcxh, Integer jgid) {
		return cisJcsq02Dao.queryYsJcDicZlxmInfo(jcxh, jgid);
	}

	@Override
	public CisJcsq01Model jc01getById(Integer id) {
		return BeanUtil.toBean(cisJcsq01Dao.getById(id), CisJcsq01Model.class) ;
	}

    @Override
    public List<LoadPatientInfoResp> loadPatientInfoMz(Integer searchType, String searchValue) {
        return cisJcsq01Dao.loadPatientInfoMz(searchType, searchValue);
    }

    @Override
    public List<LoadPatientInfoResp> loadPatientInfoZy(String searchValue) {
        return cisJcsq01Dao.loadPatientInfoZy(searchValue);
    }

    @Override
    public List<LoadPatientInfoResp> loadPatientInfoTj(String searchValue) {
        return cisJcsq01Dao.loadPatientInfoTj(searchValue);
    }

    @Override
    public List<Map<String, Object>> queryJcsqdList(Map<String, Object> map) {
        return cisJcsq01Dao.queryJcsqdList(map);
    }

    @Override
    public Map<String, Object> queryMzBrxx(Integer jzxh) {
        return cisJcsq01Dao.queryMzBrxx(jzxh);
    }

    @Override
    public Map<String, Object> queryZyBrxx(String zthm) {
        return cisJcsq01Dao.queryZyBrxx(zthm);
    }

    @Override
    public Map<String, Object> queryTjBrxx(String tjbh) {
        return cisJcsq01Dao.queryTjBrxx(tjbh);
    }

    @Override
    public void updateJcztByYjxh(Map<String, Object> map) {
        cisJcsq01Dao.updateJcztByYjxh(map);
    }

    @Override
    public void updateJczt(Map<String, Object> map) {
        cisJcsq01Dao.updateJczt(map);
    }

    @Override
    public void updateJcztByJcxh(Map<String, Object> map) {
        cisJcsq01Dao.updateJcztByJcxh(map);
    }

    @Override
    public void updateJcztByOneYjxh(Map<String, Object> map) {
        cisJcsq01Dao.updateJcztByOneYjxh(map);
    }

	@Override
	public void saveSqdFromMzorZy(YjyySaveReq req) {
		 cisJcsq01Dao.updateAppointment(req);
	}


	@Override
    public void cancelYjyy(Integer jcxh) {
        CisJcsq01 jc = cisJcsq01Dao.getById(jcxh);
        if(jc == null){
            throw BaseException.create("ERROR_DCTWORK_OP_0015");
        }
        //取消状态
        if("3".equals(jc.getSqlx())){
            cisJcsq01Dao.cancelYjTj(jcxh);
        }else{
            cisJcsq01Dao.cancelYjMzOrZy(jcxh);
        }

        //解冻号源
        opSbhyDao.unfreeze(jc.getHyid());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JcSqdResp saveMzSqd(JcSqdReq req, SysUser user) throws BaseException{
        JcSqdResp res = new JcSqdResp();

        int brxz = Integer.parseInt(req.getBrxz()); //获取病人性质

        CisJcsq01 zb = cn.hutool.core.bean.BeanUtil.toBean(req.getYsJx01(), CisJcsq01.class); // 检查主表

        List<CisJcsq03> cisJcsq03s = BeanUtil.toBeans(req.getYsJx01() != null ? req.getYsJx01().getCisJcsq03() : null, CisJcsq03.class);//检查申请送检标本表

        List<DicZlxmSqdReqYsJx02> details = req.getYsJx02List(); // 检查诊疗项目列表

        DicYjlxModel yjlx = dicYjlxService.getById(req.getYjlx()); // 检查类型

        int jgid = user.getHospitalId();//机构id
        int brid = req.getBrid();//病人ID
        String jzlsh = req.getJzlsh();//就诊流水号
        int brks = req.getBrks();//病人科室

        // 1.门诊医保病人，CT检查一天每人只能限一次一个部位
        if(yjlx != null){
            checkCt(brxz, brid, yjlx.getPid());
        }

        // 2.判断项目是否作废，若作废不能保存并给出提示和医保病人不能开自费项目
        checkInvalid(brxz, jgid, details);

        // 3.保存申请单
        saveJcsq(req, zb,cisJcsq03s,BeanUtil.toBean(yjlx, DicYjlx.class), details,jgid);

        // 4.保存检查组套信息
        Integer maxYjzh = opYjcf02Service.getMaxYjzh(req.getJzlsh(), user.getHospitalId());//查询最大的医技组号
        Map<String, Object> retZt = saveJcZt(brxz, details, jgid, jzlsh, brks,maxYjzh);
        List<Integer> yzztIds = (List<Integer>)retZt.get("yzztIds");
        List<OpYjcf02Req> jc02List = (List<OpYjcf02Req>)retZt.get("jc02List");

        // 5.调用医技保存
        //判断医技是否已经存在，已经存在将不能保存
//        for (OpYjcf02Req list:jc02List){
//            //查询医技是否已经存在
//            int status = opYjcf02Service.queryByYlxhAndJzlsh(user.getHospitalId(), list.getYlxh(), req.getJzlsh());
//            if (status > 0){
//                throw BaseException.create("ERROR_DCTWORK_OP_0046",new String[]{list.getXmmc()});
//            }
//        }
        for (DicZlxmSqdReqYsJx02 list :details){
            int status = opYjcf02Service.queryByYlxhAndJzlsh(user.getHospitalId(), list.getZlxmid(),req.getClinicId().toString());
            if (status > 0){
                throw BaseException.create("ERROR_DCTWORK_OP_0046",new String[]{list.getFymc()});
            }
        }
        Integer yjxh = saveYj(req, zb, jc02List, user);

        // 6.更新OP_YJCF01表SQID
        Map<String, Object> par = ParamUtil.instance().put("sqid", zb.getJcxh()).put("yjxh", yjxh);
        opYjcf01Service.updateSqid(par);

        // 7.更新OP_YJ02_ZT的YJXH值
        opYjcf02ZtService.updateYjxh(yzztIds, yjxh);

        res.setJcxh(zb.getJcxh());
        return res;
    }



    //门诊医保病人，CT检查一天每人只能限一次一个部位
    private void checkCt(int brxz, int brid, int pid) {
        if(pid == 26){	//CT检查
            PubBrxzOut out = new PubBrxzOut();
            out.setSjxz(6021);
            List<PubBrxzOut> brxzList = pubBrxzOutSer.findByEntity(out);

            for(PubBrxzOut pubBrxz : brxzList){
                if(brxz == pubBrxz.getBrxz().intValue()){	//医保病人
                    Map<String, Object> jc01Params = new HashMap<>(16);
                    jc01Params.put("brid", brid);
                    jc01Params.put("sqsj", DateUtil.parse(DateUtil.now(), DatePattern.NORM_DATE_PATTERN));
                    Long jc01Count = cisJcsq01Dao.getJcSqdCount(jc01Params);
                    if(jc01Count >= 1){
                        throw BaseException.create("ERROR_DCTWORK_OP_0011");
                    }
                }
            }
        }
    }

    //判断项目是否作废，若作废不能保存并给出提示和医保病人不能开自费项目
    private void checkInvalid(int brxz, int jgid, List<DicZlxmSqdReqYsJx02> details) {
        List<String> zfxmMc = new ArrayList<>();//项目是否作废
        List<String> ybzfMc = new ArrayList<>();//医保自费项目
        List<String> xmbczMc = new ArrayList<>();//项目不存在

        boolean b = (4001==brxz || 4001==brxz || 6023==brxz || 45==brxz || 39==brxz || 36==brxz
                || 35==brxz || 34==brxz || 33==brxz || 32==brxz || 28==brxz || 19==brxz || 29==brxz
        );
        for (DicZlxmSqdReqYsJx02 zlxm : details) {
            Map<String, Object> parameters = new HashMap<>(16);
            parameters.put("jgid", jgid);
            parameters.put("zlxmid", zlxm.getZlxmid());

            List<Map<String, Object>> xmList = feeYlsfxmOutSer.getFyxhFymcZfpbYbbm(parameters);
            if (CollUtil.isEmpty(xmList)){
                xmbczMc.add(ObjectToTypes.parseString(zlxm.getFymc()));
            }
            for (Map<String, Object> xm : xmList) {
                if ("1".equals(ObjectToTypes.parseString(xm.get("ZFPB")))) {
                    zfxmMc.add(ObjectToTypes.parseString(xm.get("FYMC")));
                }
                if (xm.get("YBBM") == null || StringUtil.isBlank(ObjectToTypes.parseString(xm.get("YBBM")))) {
                    ybzfMc.add(ObjectToTypes.parseString(xm.get("FYMC")));
                }
            }
        }

        if (xmbczMc.size() > 0){
            //当项目不存在时，给出提示
            throw BaseException.create("ERROR_DCTWORK_OP_0047",new String[]{xmbczMc.stream().collect(Collectors.joining(","))});
        }

        if (zfxmMc.size() > 0) {
            //作废项目
            throw BaseException.create("ERROR_DCTWORK_OP_0013",new String[]{zfxmMc.stream().collect(Collectors.joining(","))});
        }

        if( b && ybzfMc.size() > 0 ){
            //医保不能开自费项目
            throw BaseException.create("ERROR_DCTWORK_OP_0012",new String[]{ybzfMc.stream().collect(Collectors.joining(","))});
        }
    }

    /**
     * 保存申请单主表
     * @param req
     * @param zb    检查主表
     * @param sjbbs  送检标本
     * @param yjlx
     * @param details
     */
    private void saveJcsq(JcSqdReq req, CisJcsq01 zb,List<CisJcsq03> sjbbs, DicYjlx yjlx, List<DicZlxmSqdReqYsJx02> details,Integer jgid) {
        //如果选择日期和时间则变为已预约状态，并且更新号源状态
        //yyzt:预约状态 0=未预约  1=已预约 3-体检预约取消
        int yyzt = 0;
        //jxzt:检查状态 0暂存，1提交，2预约，3已检查，4已报告，5已审核，6再审，7打印，9退回 20 收费  21 退费
        int jxzt = 1;
        if(req.getYsJx01().getYyrq() != null && req.getYsJx01().getYysj() != null && req.getYsJx01().getHyid() != 0){
            yyzt = 1;
            jxzt = 2;
            // 更新设备号源使用状态
            opSbhyDao.updateSfyy(String.valueOf(zb.getHyid()));
        }

        //新增检查主表
        Integer key = redisFactory.getTableKey(TableName.DB_NAME, TableName.CIS_JCSQ01);
        zb.setJcxh(key);
        zb.setJclx(yjlx.getJclx());
        zb.setSqsj(com.buit.utill.DateUtil.getCurrentTimestamp());// 申请时间
        zb.setYyzt(yyzt);
        //zb.setJczt(2);
        zb.setZxks(ObjectToTypes.parseInt(details.get(0).getZxks()));// 执行科室
        zb.setJczt(jxzt);
        zb.setSqlx("1");
        if ("1".equals(zb.getSfbljc())){
            //生成联单号和病理号
            zb.setLdh(String.format("%08d", key));
            zb.setBlh(String.format("%06d", key));
        }
        zb.setJzlsh(req.getJzlsh());
        cisJcsq01Dao.insert(zb);

        //新增检查子表
        for (DicZlxmSqdReqYsJx02 detail : details) {
            detail.setJcxh(zb.getJcxh());
            detail.setFyxh(detail.getZlxmid());
            cisJcsq02Dao.insert(detail);
        }

        //新增送检标本表
        if (CollUtil.isNotEmpty(sjbbs)){
            sjbbs.stream().forEach(o ->{
                o.setJcsqsjid(redisFactory.getTableKey(TableName.DB_NAME, TableName.CIS_JCSQ03));
                o.setJgid(jgid);
                o.setJcxh(zb.getJcxh());
                //新增采样数据
                cisJcsq03Dao.insert(o);
            });
        }
    }

    //保存检查组套信息
    private Map<String, Object> saveJcZt(int brxz, List<DicZlxmSqdReqYsJx02> details, int jgid, String jzlsh, int brks,Integer yjzh) {
        Map<String, Object> ret = new HashMap();
        List<OpYjcf02Req> jc02List = new ArrayList<>();
        List<Integer> yzztIds = new ArrayList<>();// 记录保存的组套id
        for(DicZlxmSqdReqYsJx02 zlxm : details){
            // 保存门诊医嘱组套
            OpYjcf02Zt zt = new OpYjcf02Zt();

            //通过诊疗项目id（zlxmid）查询项目详细信息 todo

            // 赋值医嘱组套信息
            zt.setSbxh(redisFactory.getTableKey(TableName.DB_NAME, TableName.OP_YJ02_ZT));
            zt.setZtbz(1); // 组套标志(该条医嘱存是组套信息) 0非组套标志，1组套标志
            zt.setJgid(jgid); // 机构ID
            zt.setYjxh(1); // 医技序号
            zt.setYlxh(zlxm.getZlxmid()); // 医疗序号
            zt.setXmlx(5); // 项目类型=检查
            zt.setYjzx(0); // 项目类型=检查
            zt.setYldj(zlxm.getFydj()); // 医疗单价
            zt.setYlsl(BigDecimal.ONE); // 医疗数量
            zt.setHjje(zlxm.getFydj()); // 划价金额
            zt.setFygb(5); // 费用归并
            zt.setZfbl(BigDecimal.ONE); // 自负比例 组套不显示自负比例
            zt.setDzbl(BigDecimal.ONE); // 打折比例
            zt.setYjzh(yjzh); // 医技组号
            zt.setFymc(zlxm.getFymc()); // 检查项目名称
            zt.setJzlsh(jzlsh); // 就诊流水号
            opYjcf02ZtService.insert(zt);

            yzztIds.add(zt.getSbxh());

            // 保存门诊医嘱组套
            // 查询该诊疗项目对应的收费项目明细
            Map<String, Object> parameters = new HashMap<>(16);
            parameters.put("zlxmid", zlxm.getZlxmid());
            parameters.put("types", brxz == 6024? 1:2);//1商保、2非商保
            List<Map<String, Object>> fymxList = feeYlsfxmOutSer.getBxfy(parameters);

            Map<String, Object> map = ParamUtil.instance().put("zlxmid", zlxm.getZlxmid());
            List<DiccZlsfdz> xmfyList = diccZlsfdzOutSer.findByEntity(map);

            for (Map<String, Object> fymx : fymxList) {
                OpYjcf02Req yj02 = new OpYjcf02Req(); // 处置明细
                yj02.setZxks(zlxm.getZxks());// 赋值执行科室
                yj02.setZtbz(1);// 组套标志(该条医嘱存是组套信息) 0非组套标志，1组套标志
                yj02.setZtyzsbxh(zt.getSbxh());// 组套医嘱识别序号(OP_YJ02_ZT表SBXH)
                yj02.setYjzh(yjzh);// 医技组号
                yj02.setJgid(jgid); // 机构ID
                yj02.setYlxh(ObjectToTypes.parseInt(fymx.get("FYXH")));// 医疗序号=费用序号
                yj02.setXmlx(5);// 项目类型：4检验,5检查,6手术,7治疗,8护理,9饮食,10卫材,99其他
                yj02.setYldj(ObjectToTypes.parseBigDecimal(fymx.get("FYDJ")));// 医疗单价=费用单价
                yj02.setYjzx(0);// 医技主项
                yj02.setDzbl(BigDecimal.ONE);// 打折比例============================================
                yj02.setKsdm(brks);// 科室代码
                yj02.setFydw(ObjectToTypes.parseString(fymx.get("FYDW")));// 费用单位
                yj02.setYlsl(setylsl(fymx, xmfyList));//设置数量
                yj02.setHjje(ObjectToTypes.parseBigDecimal(fymx.get("FYDJ")).multiply(ObjectToTypes.parseBigDecimal(yj02.getYlsl())));// 合计金额=============================
                yj02.setFymc(ObjectToTypes.parseString(fymx.get("FYMC")));// 费用名称
                yj02.setFygb(cisUtil.getfygb(0, yj02.getYlxh()));// 费用归并
                yj02.setZfbl(ObjectToTypes.parseBigDecimal(
                        cisUtil.getPayProportion(brxz, yj02.getFygb(), 0, yj02.getYlxh()).get("ZFBL")));// 自负比例
                yj02.setXmmc(zlxm.getFymc());//诊疗项目名称
                jc02List.add(yj02);
            }
        }
        ret.put("yzztIds", yzztIds);
        ret.put("jc02List", jc02List);

        return ret;
    }

    //设置数量
    private BigDecimal setylsl(Map<String, Object> fyxh, List<DiccZlsfdz> xmfyList) {
        List<DiccZlsfdz> list = xmfyList.stream().filter(
                item -> item.getFyxh().intValue() == ObjectToTypes.parseInt(fyxh.get("FYXH"))).collect(Collectors.toList());
        if(list == null || list.isEmpty()){
            return BigDecimal.ONE;
        }
        return new BigDecimal(list.get(0).getFysl()==null?1:list.get(0).getFysl());
    }

    //调用医技保存
    private Integer saveYj(JcSqdReq req, CisJcsq01 zb, List<OpYjcf02Req> jc02List, SysUser user) {
        OpYjcf01Req yjReq = new OpYjcf01Req();
        yjReq.setClinicId(req.getClinicId());
        yjReq.setBrid(req.getBrid());
        yjReq.setBrxm(req.getBrxm());
        yjReq.setDjly(req.getDjly());
        yjReq.setGhgl(req.getGhgl());
        yjReq.setJzkh(req.getJzkh());
        yjReq.setSqid(zb.getJcxh());
        yjReq.setBodys(jc02List);
        yjReq.setJzlsh(req.getJzlsh());
        //yjReq.setKsdm(req.getBrks());//病人科室（开单科室）
        yjReq.setLy(5);
        Integer yjxh = opYjcf01Service.save(yjReq, user.getUserId(), user.getHospitalId());
        return yjxh;
    }

	@Override
	public List<CisJcsq01QueryResp> queryZyJcSqdListJcxh(CisJcsq01QueryReq req) {
		return cisJcsq01Dao.queryZyJcSqdList(req);
	}

    /**
     * 病理检查申请单打印数据
     * @param jcxh
     * @return
     */
    @Override
    public BljcsqPrintResponse getBljcsqPrintData(Integer jcxh) {
        BljcsqPrintResponse printData = cisJcsq01Dao.getBljcsqPrintData(jcxh);
        if (printData == null){
            return null;
        }
        //通讯地址
        printData.setTxdz(getZz(printData.getShengbm(),printData.getShibm(),printData.getXianbm(),printData.getXxdz()));
        //获取手术所见 暂无
        //获取送检标本
        printData.setBbList(getSjbb(jcxh));
        //获取检查项目
        printData.setJcxmList(getJcxm(jcxh));
        //计算年龄
        printData.setNl(getNl(printData.getCsrq()));

        return printData;
    }

    /**
     * 检查申请单打印数据
     * @param jcxh
     * @return
     */
    @Override
    public JcsqdPrintResponse getjcsqPrintData(Integer jcxh) {
        JcsqdPrintResponse jcsqPrintData = cisJcsq01Dao.getJcsqPrintData(jcxh);
        if (jcsqPrintData == null){
            return null;
        }

        //计算年龄
        jcsqPrintData.setNl(getNl(jcsqPrintData.getCsrq()));

        return jcsqPrintData;
    }

    /**
     * 地址
     * @param shengbm
     * @param shibm
     * @param xianbm
     * @param xxdz
     * @return
     */
    private String getZz(Integer shengbm,Integer shibm,Integer xianbm,String xxdz){
        String shengmc = "";
        String shimc = "";
        String xianmc = "";

        if (shengbm != null){
            shengmc = dicXzqhService.getSsxmc(shengbm);
        }
        if (shibm != null){
            shimc = dicXzqhService.getSsxmc(shibm);
        }
        if (xianbm != null){
            xianmc = dicXzqhService.getSsxmc(xianbm);
        }

        StringBuffer buffer = new StringBuffer();
        buffer.append(cn.hutool.core.util.StrUtil.isBlank(shengmc)?"":shengmc);
        buffer.append(cn.hutool.core.util.StrUtil.isBlank(shimc)?"":shimc);
        buffer.append(cn.hutool.core.util.StrUtil.isBlank(xianmc)?"":xianmc);
        buffer.append(StrUtil.isBlank(xxdz)?"":xxdz);

        return buffer.toString();
    }


    /**
     * 计算年龄
     * @param csrq
     * @return
     */
    private String getNl(Date csrq){
        //计算年龄
        if (csrq != null){
            Map<String, Object> personAge = DateUtils.getPersonAge(csrq, new Date());
            return personAge.get("ages").toString();
        }
        return null;
    }

    /**
     * 查询送检标本
     * @param jcxh
     * @return
     */
    private List<BbDataResponse> getSjbb(Integer jcxh){
        return cisJcsq03Dao.getsjbb(jcxh);
    }


    /**
     * 查询检查申请的检查项目
     * @param jcxh
     * @return
     */
    private List<JcxmDataResponse> getJcxm(Integer jcxh){
        return cisJcsq02Dao.getJcxmData(jcxh);
    }

	@Override
	public void jc03Insert(Object obj) {
		cisJcsq03Dao.insert(obj);
	}

	@Override
	public void jc03DeleteByJcxh(Integer jcxh, Integer jgid) {
		cisJcsq03Dao.deleteByJcxh(jcxh, jgid);
	}

    /**
     * 通过检查序号和费用序号删除检查申请相关表数据
     * @param jcxh
     * @param fyxh 为空时 删除整个检查申请所有数据 ；否则删除检查申请的单个项目
     */
    @Override
    public void deleteCzRelationJcsq(Integer jcxh, Integer fyxh,Integer jgid) {
        if (jcxh == null){
            logger.error("jcxh为空,无法删除检查申请相关数据");
            return;
        }
        // TODO: 2021/4/20 此处是否需要校验项目已经被执行

        if (fyxh == null){//删除整个申请单
            cisJcsq03Dao.deleteByJcxh(jcxh, jgid);//删除cisjcsq03
            cisJcsq02Dao.deleteByJcxh(jcxh);
            cisJcsq01Dao.deleteById(jcxh);
        }else {//删除单个项目
            cisJcsq02Dao.deleteByJcxhAndFyxh(jcxh, fyxh);

            //判断申请单下是否还存在其他项目
            int jcsq02 = cisJcsq02Dao.selectJcsq02(jcxh);
            if (jcsq02 == 0){
                cisJcsq03Dao.deleteByJcxh(jcxh, jgid);//删除cisjcsq03
                cisJcsq01Dao.deleteById(jcxh);
            }
        }
    }

}
