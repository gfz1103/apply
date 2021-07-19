package com.buit.utill;

/**
 * @author all
 */
public class StrUtil {
    public static String null2Str(Object obj){
        String str  =  obj==null? "" : String.valueOf(obj);
        return str;
    }

    public static String nullAndEmpty2Zero(Object obj){
        String str = null2Str(obj);
        return "".equals(str) ? "0" : str;
    }
}
