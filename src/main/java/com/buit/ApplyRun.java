package com.buit;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.buit.utill.SpringContextUtil;

/**
* @ClassName: HisRun
* @Description: 启动方法
* @author 神算子
* @date 2020年4月26日 下午3:31:56
 */
@EnableDubbo
@SpringBootApplication
public class ApplyRun {
	public static void main(String[] args) {
		ConfigurableApplicationContext con=SpringApplication.run(ApplyRun.class, args);
		SpringContextUtil.setApplicationContext(con);
	}
}