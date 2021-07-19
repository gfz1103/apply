/*
 * Copyright (c) 2019
 * User:mleo
 * File:RedisUtil.java
 * Date:2019/01/11 11:24:11
 */

package com.buit.utill;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.buit.commons.BaseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

/**
 * @author All
 */
@Component
public class RedisFactory {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 获取主键ID
     * @param dbName    数据库名称
     * @param tableName 表名称
     * @return 主键ID
     */
    public Integer getTableKey(String dbName,String tableName) {
        Long id =  redisTemplate.opsForValue().increment(getRedisKey(dbName, tableName));
        return id.intValue();
    }

    /**
     * 获取 RedisKey
     * @param dbName    数据库名称
     * @param tableName 表名称
     * @return RedisKey
     */
    private String getRedisKey(String dbName,String tableName){
        return dbName.concat(".").concat(tableName);
    }


    /**
     * 递减
     * @param key
     * @param value
     * @return
     */
    public Integer decrement(String key, Integer value){
        return redisTemplate.opsForValue().decrement(key, value.longValue()).intValue();
    }


    /**
     * 追加
     * @param key
     * @param value
     * @return
     */
    public Long increment(String key, Integer value){
        return redisTemplate.opsForValue().increment(key, value.longValue());
    }


    /**
     * 保存值
     *
     * @param key     键
     * @param value   值
     * @param seconds 秒
     */
    public void setKey(String key, String value, Long seconds) {
        redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
    }
    /**
     * 保存值
     *
     * @param key     键
     * @param value   值
     * @param time
     */
    public void setKey(String key, String value, Long time,TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }
    /**
     * 设置失效时间
     *
     * @param key     键
     * @param seconds 秒
     */
    public void setExpire(String key, Long seconds) {
        redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }
    /**
     * 保存值 并附带过期时间
     *
     * @param key     键
     * @param value   值
     * @param seconds 秒 
     */
    /**
     * 保存值 
     * @param key     键
     * @param value   值
     */
    public <T> void setKey(String key, T value, Long seconds) {
        String json = BeanUtil.beanToString(value);
        redisTemplate.opsForValue().set(key, json, seconds, TimeUnit.SECONDS);
    }
    public <T> void setKey(String key, T value) {
        String json = BeanUtil.beanToString(value);
        redisTemplate.opsForValue().set(key, json);
    }
    public <T> T getValue(String key,Class<T> clazz) {
        String json = redisTemplate.opsForValue().get(key);
        try {
            if(StringUtils.isEmpty(json)){
                return null;
            }
            return objectMapper.readValue(json,clazz);
        } catch (IOException e) {
            throw BaseException.create("ERROR_JSON_0001");
        }
    }

    /**
     * redis读取List<T>
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getValueList(String key,Class<T> clazz) {
        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
        String json = redisTemplate.opsForValue().get(key);
        try {
            return objectMapper.readValue(json,listType);
        } catch (IOException e) {
            throw BaseException.create("ERROR_JSON_0001");
        }
    }
    /**
     * 保存值
     *
     * @param key     键
     * @param value   值
     */
    public void setKey(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }
    /**
     * 保存值
     *
     * @param key     键
     * @param value   值
     */
    public void setKey(String key, Integer value) {
        redisTemplate.opsForValue().set(key, String.valueOf(value));
    }

    /**
     * 获取值
     *
     * @param key    键
     */
    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    /**
     * 获取值
     *
     * @param key    键
     */


    /**
     * 判断指定 key 是否存在
     *
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除指定 key
     *
     * @param key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }


}
