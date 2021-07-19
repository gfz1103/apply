package com.buit.utill;

import java.util.HashMap;
import java.util.Map;

/**
 * 快速创建map并放入数据
 * @author 老花生
 *
 */
public class ParamUtil extends HashMap<String, Object> implements Map<String, Object> {
    @Override
    public void putAll(Map<? extends String, ? extends Object> m) {
        if(m!=null){
            super.putAll(m);
        }
    }

    private static final long serialVersionUID = 1L;
    private ParamUtil(){}
    public static final ParamUtil instance(){
        return new ParamUtil();
    }
    @SuppressWarnings("unchecked")
    public static final ParamUtil instance(Object obj){
        ParamUtil p=instance();
        if(obj instanceof Map){
            p.putAll((Map<String, Object>)obj);
        }
        return p;
    }

    @Override
    public ParamUtil put(String key,Object value){
        super.put(key, value);
        return this;
    }
    public ParamUtil remove(String key){
        super.remove(key);
        return this;
    }
}
