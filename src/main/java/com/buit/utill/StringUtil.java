package com.buit.utill;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 字符串做加一操作
 * @author all
 */
public class StringUtil {
    public static String addOne(String testStr) {
        //根据不是数字的字符拆分字符串
        String[] strs = testStr.split("[^0-9]");
        //取出最后一组数字
        String numStr = strs[strs.length - 1];
        //如果最后一组没有数字(也就是不以数字结尾)，抛NumberFormatException异常
        if (numStr != null && numStr.length() > 0) {
            //取出字符串的长度
            int n = numStr.length();
            //将该数字加一
            int num = Integer.parseInt(numStr) + 1;
            String added = String.valueOf(num);
            n = Math.min(n, added.length());
            //拼接字符串
            return testStr.subSequence(0, testStr.length() - n) + added;
        } else {
            throw new NumberFormatException();
        }
    }

    /**
     * @desc 1.0~1之间的BigDecimal小数，格式化后失去前面的0,则前面直接加上0。
     * 2.传入的参数等于0，则直接返回字符串"0.00"
     * 3.大于1的小数，直接格式化返回字符串
     * @param obj
     * @return
     */
    public static String formatToNumber(BigDecimal obj) {
        DecimalFormat df = new DecimalFormat("#.00");
        if(obj.compareTo(BigDecimal.ZERO)==0) {
            return "0.00";
        }else if(obj.compareTo(BigDecimal.ZERO)>0&&obj.compareTo(new BigDecimal(1))<0){
            return "0"+df.format(obj).toString();
        }else {
            return df.format(obj).toString();
        }
    }

    /**
     * 输入的字符是否是汉字
     *
     * @param a
     *            char
     * @return boolean
     */
    public static boolean isChinese(char a) {
        int v = (int) a;
        return (v >= 19968 && v <= 171941);
    }

    public static boolean containsChinese(String s) {
        if (null == s || "".equals(s.trim())){
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            if (isChinese(s.charAt(i))){
                return true;
            }
        }
        return false;
    }


    public static  void main(String[] atgs){
        System.out.println(formatToNumber(new BigDecimal(0)));

    }
}
