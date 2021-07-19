package com.buit.constans;

/**
* @ClassName: RedisKeyHead
* @Description: Redis的Key 值 头信息管理 防止头冲突 
* @author 神算子
* @date 2020年4月26日 下午4:05:43
 */
public enum RedisKeyHead {
	
	/*编码顺序值
	 */
	data_code("data_code"),
	/*编码顺序值*/
	loginRecoveryTime("loginRecoveryTime-"),	
	//用户登录次数过多导致锁定后，等待该时间后自动解锁 单位为分钟
	/*编码顺序值*/
	loginErrorTime("LoginErrorTime-"),
	//该时间内连续输入密码错误多次锁定用户 单位为分钟
	/*编码顺序值*/
	primaryData("primaryData"),	
	/*编码顺序值*/
	//该时间内连续输入密码错误多次锁定用户 单位为分钟
	
	/*编码顺序值*/
	//用户登录token
	token("JWT_TOKEN::"),

	//住院日终结账信息缓存
	zyCheckout("zy-checkout-");

    private String headKey;


    RedisKeyHead(String headKey) {
        this.headKey = headKey;
    }


    public String getHeadKey() {
        return headKey;
    }

    public void setHeadKey(String headKey) {
        this.headKey = headKey;
    }
}
