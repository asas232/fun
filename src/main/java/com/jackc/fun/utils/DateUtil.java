package com.jackc.fun.utils;

import org.apache.commons.lang3.RandomUtils;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @Description
 * @Author chenjie
 * @DATE 2019/6/28 16:04
 */
public class DateUtil {

    /**
     * 日期运算
     *
     * @param date
     *            源
     * @param part
     *            操作部份
     * @param value
     *            改变值
     * @return 计算后的日期
     */
    public static Date add(Date date, int part, int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(part, value);
        return calendar.getTime();
    }

    /**
     * 日期运算
     *
     *            源
     * @param part
     *            操作部份
     * @param value
     *            改变值
     * @return 计算后的日期
     */
    public static long add(long start, int part, int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(start);
        calendar.add(part, value);
        return calendar.getTimeInMillis();
    }

    /**
     * 获得N日后的时间
     *
     * @param date
     *            源
     * @param days
     *            天数
     * @return
     */
    public static Date getAfterDay(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }

    /**
     * 获得N日后的时间
     *
     * @param date
     *            源
     * @param days
     *            天数
     * @return
     */
    public static long getAfterDay(long date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTimeInMillis();
    }

    /**
     * 取两个日期的差值
     *
     * @param from
     *            开始时间
     * @param to
     *            结束时间
     * @param part
     *            Calendar.SECOND--相关多少秒,Calendar.MINUTE--相关多少分,Calendar.
     *            HOUR_OF_DAY-时,other-天
     * @return 差值
     */
    public static long getDiff(Date from, Date to, int part) {
        if (to == null || from == null)
            return 0;
        return getDiff(from.getTime(), to.getTime(), part);
    }

    /**
     * 取两个时间戳的差值
     *
     * @param fromTimeStamp
     *            开始时间
     * @param toTimeStamp
     *            结束时间
     * @param part
     *            Calendar.SECOND--相关多少秒,Calendar.MINUTE--相关多少分,Calendar.
     *            HOUR_OF_DAY-时,other-天
     * @return
     */
    public static long getDiff(long fromTimeStamp, long toTimeStamp, int part) {
        long d = toTimeStamp - fromTimeStamp;
        switch (part) {
            case Calendar.SECOND:
                return d / 1000;
            case Calendar.MINUTE:
                return d / 1000 / 60;
            case Calendar.HOUR_OF_DAY:
                return d / 1000 / 60 / 60;
            default:
                return d / 1000 / 60 / 60 / 24;
        }
    }

    public static String dateToStr(Date date,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dateStr = sdf.format(date);
        return dateStr;
    }

    public static void main(String[] args) {
        String path = "E:\\Temp\\"+DateUtil.dateToStr(new Date(), "yyyy.MM.dd_HH:mm:ss");
        String nc ="0."+RandomUtils.nextLong(00124123434363232L, 94539193545354354L);
        String url = "https://video{0}.myfreecams.com:443/NxServer/ngrp:mfc_{1}.f4v_mobile/playlist.m3u8?nc={2}";
        String cmd = "E:\\Temp\\ffmpeg.exe -hide_banner -v fatal -i {0} -c copy -vsync 2 -r 60 -b:v 500k {1}";
        url = MessageFormat.format(url,12,43,nc);
        System.out.println(url);
        cmd = MessageFormat.format(cmd,url,path);
        System.out.println(cmd);
    }

}
