package com.buit.utill;

import com.buit.commons.BaseException;
import com.buit.system.utill.AgeUtil;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 处理时间的常用工具类
 * @author all
 */
public  class DateUtils {

    public static  final String YEAR_MONTH_DAY="yyyy-MM-dd";
    public static final String YEAR_MONTH_DAY_HOUR_MINUTE_SECOND="yyyy-MM-dd HH:mm:ss";


    public static Timestamp getMonthStartTime(Timestamp timeStamp) {
        if(null == timeStamp){
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        calendar.setTimeInMillis(timeStamp.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    public static Timestamp getMonthEndTime(Timestamp timeStamp) {
        if(null == timeStamp){
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        calendar.setTimeInMillis(timeStamp.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取一天的最小时间
     * @param timeStamp
     * @param
     * @return
     */
    public static Timestamp getDailyStartTime(Long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        calendar.setTimeInMillis(timeStamp);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取一天的最大时间
     * @param timeStamp
     * @param
     * @return
     */
    public static Timestamp getDailyEndTime(Long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        calendar.setTimeInMillis(timeStamp);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 时间字符串转换成Timestamp
     * @param
     * @return
     * @throws ParseException
     */
    public static Timestamp convertTimestamp(String dateFormat,String s) throws ParseException {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
            Date date = simpleDateFormat.parse(s);
            long ts = date.getTime();
            return new Timestamp(ts);
    }

    /**
     * 获取当前时间 返回字符串
     * @return
     */
    public static String getCurrentDateStr() {
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        return dateFormat.format(date);
    }

    /**
     * 计算两个日期的差，参数null表示当前日期。
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getDifferDays(Date date1, Date date2) {
        if (date1 != null && date2 != null) {
            return (int) ((date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000));
        } else {
            return 0;
        }
    }

    /**
     * @param birthday
     * @param nowDate
     * @return Map<String, Object> 返回类型
     * @throws @Title: getPersonAge
     * @Description: TODO 年龄大于等于3*12个月的，用岁表示； 小于3*12个月而又大于等于1*12个月的，用岁月表示；
     *               小于12个月而又大于等于6个月的，用月表示； 小于6个月而大于等于29天的，用月天表示； 大于72小时小于29天的，用天表示；
     *               小于72小时的，用小时表示。
     * @author 龚方舟
     */
    public static Map<String, Object> getPersonAge(Date birthday, Date nowDate) {
        if (birthday == null) {
            throw BaseException.create("ERROR_DCTWORK_OP_0040");
        }
        Calendar now = Calendar.getInstance();
        Calendar birth = Calendar.getInstance();
        birth.setTime(birthday);
        if (nowDate != null) {
            now.setTime(nowDate);
        }

        int age = AgeUtil.calculateAge(birthday, nowDate);
        String reAge = age + "岁";
        if (age < 3 && age >= 1) {
            int month = getMonths(birthday, now.getTime());
            reAge = age + "岁";
            if ((month - 12 * age) > 0) {
                reAge = age + "岁" + (month - 12 * age) + "月";
            }
        } else if (age < 1) {
            int month = getMonths(birthday, now.getTime());
            if (month < 12 && month >= 6) {
                reAge = month + "月";
            } else {
                int day = getPeriod(birthday, null);
                if (day >= 29 && month > 0) {
                    if (now.get(Calendar.DAY_OF_MONTH) >= birth.get(Calendar.DAY_OF_MONTH)) {
                        day = now.get(Calendar.DAY_OF_MONTH) - birth.get(Calendar.DAY_OF_MONTH);
                    } else {
                        now.set(Calendar.MONTH, birth.get(Calendar.MONTH) + 1);
                        day = now.get(Calendar.DAY_OF_YEAR) - birth.get(Calendar.DAY_OF_YEAR);
                    }
                    reAge = month + "月";
                    if (day > 0) {
                        reAge = month + "月" + day + "天";
                    }
                } else {
                    if (day >= 4) {
                        if ((now.get(Calendar.DAY_OF_YEAR) - birth.get(Calendar.DAY_OF_YEAR)) > 0) {
                            day = now.get(Calendar.DAY_OF_YEAR) - birth.get(Calendar.DAY_OF_YEAR);
                        }
                        reAge = day - 1 + "天";
                    } else {
                        int hour = now.get(Calendar.HOUR_OF_DAY) - birth.get(Calendar.HOUR_OF_DAY);
                        reAge = hour + 24 * (day) + "小时";
                    }
                }
            }
        }
        HashMap<String, Object> resBody = new HashMap<String, Object>(16);
        resBody.put("age", age);
        resBody.put("ages", reAge);
        return resBody;
    }

    /**
     * @param date1 较早的一个日期
     * @param date2 较晚的一个日期
     * @return int 返回类型
     * @Title: getMonths
     * @Description: TODO 如果前面的日期小于后面的日期将返回-1。
     * @author 龚方舟
     */
    public static int getMonths(Date date1, Date date2) {
        Calendar beginDate = Calendar.getInstance();
        beginDate.setTime(date1);
        Calendar now = Calendar.getInstance();
        now.setTime(date2);
        if (beginDate.after(now)) {
            return -1;
        }
        int mon = now.get(Calendar.MONTH) - beginDate.get(Calendar.MONTH);
        if (now.get(Calendar.DAY_OF_MONTH) < beginDate.get(Calendar.DAY_OF_MONTH)) {
            if (now.getActualMaximum(Calendar.DAY_OF_MONTH) != now.get(Calendar.DAY_OF_MONTH)) {
                mon -= 1;
            }
        }
        if (now.get(Calendar.YEAR) == beginDate.get(Calendar.YEAR)) {
            return mon;
        }
        return 12 * (now.get(Calendar.YEAR) - beginDate.get(Calendar.YEAR)) + mon;
    }

    /**
     * 计算两个日期之间的天数，参数null表示当前日期。
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getPeriod(Date date1, Date date2) {
        if (date1 == null && date2 == null) {
            return 0;
        }
        if (date1 != null && date2 != null && date1.compareTo(date2) == 0) {
            return 0;
        }
        Calendar begin = Calendar.getInstance();
        if (date1 != null) {
            begin.setTime(date1);
        }
        Calendar end = Calendar.getInstance();
        if (date2 != null) {
            end.setTime(date2);
        }
        if (begin.after(end)) {
            Calendar temp = end;
            end = begin;
            begin = temp;
        }
        if (end.get(Calendar.YEAR) == begin.get(Calendar.YEAR)) {
            return end.get(Calendar.DAY_OF_YEAR) - begin.get(Calendar.DAY_OF_YEAR);
        }
        int years = end.get(Calendar.YEAR) - begin.get(Calendar.YEAR);
        int days = begin.getActualMaximum(Calendar.DAY_OF_YEAR) - begin.get(Calendar.DAY_OF_YEAR);
        for (int i = 0; i < years - 1; i++) {
            begin.add(Calendar.YEAR, 1);
            days += begin.getActualMaximum(Calendar.DAY_OF_YEAR);
        }
        days += end.get(Calendar.DAY_OF_YEAR);
        return days;
    }


    public static void main (String[] args){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(getMonthStartTime(timestamp));
        System.out.println(getMonthEndTime(timestamp));
    }

}
