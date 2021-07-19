package com.buit.commons;


import cn.hutool.core.util.StrUtil;
import com.buit.utill.RedisFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.servlet.http.HttpServletRequest;
/**
* @ClassName: BaseSpringController
* @Description: 所有 的基础类
* @author 神算子
* @date 2020年4月26日 下午3:34:33
*
 */
public abstract class BaseSpringController extends BaseController{
	static final Logger logger = LoggerFactory.getLogger(BaseSpringController.class);

	/**
	 * 开发环境
	 */
	static final String DEVELOP_ENVIRONMENT = "dev";
	static final String ACTIVE = "spring.profiles.active";

	@Autowired  
    private Environment env;  
	@Autowired
    private HttpServletRequest request;
	@Autowired
    private RedisFactory redisFactory;

	/**
     * 获取登录用户信息
     */
    public SysUser getUser() {
        String token = request.getHeader("token");
        String refreshTokenKey = String.format("JWT_TOKEN::%s", token);		
        SysUser loginUser=redisFactory.getValue(refreshTokenKey, SysUser.class);

        if (null == loginUser) {
			//如果开发环境 为了测试方便
            if(env.getProperty(ACTIVE).startsWith(DEVELOP_ENVIRONMENT)) {
                loginUser =new SysUser();
                loginUser.setUserId(0);
                loginUser.setUserName("超级管理员");
                loginUser.setHospitalId(310107001);
                loginUser.setGroupId(1);
                loginUser.setDataVersion(1);
                loginUser.setDeptId(14);
                return loginUser;
             }else {
                 throw BaseException.create("ERROR_USER_0000");
             }
        }
        return loginUser;
    }
	/**
	 * 获取请求方IP
	 *
	 * @return 客户端Ip
	 */
	public String getIpAddress(){
		String xff = request.getHeader("REQUEST-IP");
		if (StrUtil.isEmpty(xff)) {
			throw BaseException.create("ERROR_IP_0001");
		}else{
			return xff;
		}
	}
}
