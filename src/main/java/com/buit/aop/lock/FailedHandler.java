package com.buit.aop.lock;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.buit.commons.BaseException;

import cn.hutool.json.JSONUtil;

/**
 * @Description
 * @Author yueyu
 * @Date 2020/7/10 11:07
 */
public interface FailedHandler {

    Object handler(List<String> keys,Object... args);

}

class DefaultFailed implements FailedHandler{

    static final Logger log = LoggerFactory.getLogger(DefaultFailed.class);

    @Override
    public Object handler(List<String> keys, Object... args) {
        log.error("锁获取失败[{}]", JSONUtil.toJsonStr(keys));
        throw BaseException.create("ERROR_LOCK_0001");
    }
}