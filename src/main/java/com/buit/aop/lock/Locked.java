package com.buit.aop.lock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description
 * @Author yueyu
 * @Date 2020/7/10 10:55
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Locked {

    /**
     * 用于加锁的key，可使用变量,格式：#[param.*prop]
     * param : 方法形参名称
     * prop : param对象属性
     * "*"表示此属性为数组,加锁时按照数组交叉生成key加锁,多组key加锁时为并且关系
     * */
    String[] value();

    /**
     * 获取锁时阻塞时间,单位秒
     * -1 表示一直阻塞
     * 嵌套使用时 当上级锁的block!=-1 则 此block配置无效，始终为1
     * block 不能为 0  否则始终会加锁失败
     * */
    long block() default 5;

    /**
     * 锁失效时间,单位秒
     * expire>0:方法执行完成或时间耗尽，expire<0:时间耗尽时失效
     * 嵌套使用时，expire 为上级失效时间与当前值的最大值
     * */
    long expire() default 20;

    /**
     * 获取锁失败的回调方法
     * */
    Class<? extends FailedHandler> failed() default DefaultFailed.class;
}
