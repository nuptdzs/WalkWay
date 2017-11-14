package com.zk.library.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by dzs on 2017/7/3.
 */

public class DateUtils {
    public static String formatDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
    public DateUtils() {
    }

    public static String formatSystemDate(String pattern) {
        return formatDate((Date)null, pattern);
    }

    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern(pattern);
        String result;
        if(date == null) {
            result = dateFormat.format(new Date());
        } else {
            result = dateFormat.format(date);
        }

        return result;
    }

    public static String formatDateStr(long milliseconds, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern(pattern);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(milliseconds);
        return dateFormat.format(c.getTime());
    }

    public static String formatDateStrToPattern(String date, String pattern, String targetPattern) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern(pattern);
        Date d = dateFormat.parse(date);
        date = formatDate(d, targetPattern);
        return date;
    }

    public static int getWeekOfDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(7);
    }

    public static int getWeekOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(7);
    }
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static int getDayOfWeekInChina() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int week = calendar.get(7);
        int dayIndex;
        if(week == 1) {
            dayIndex = 7;
        } else {
            dayIndex = week - 1;
        }

        return dayIndex;
    }

    public static int getDayOfWeekInChina(String date, String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nextDate(date, 0, pattern));
        int week = calendar.get(7);
        int dayIndex;
        if(week == 1) {
            dayIndex = 7;
        } else {
            dayIndex = week - 1;
        }

        return dayIndex;
    }

    public static int getDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(5);
    }

    public static int getDayOfMonth(String date, String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nextDate(date, 0, pattern));
        return calendar.get(5);
    }

    public static long dateOffset(String firstDate, String secDate, String formatStr) {
        long offsetDay = 0L;
        if(!StringUtils.isEmpty(firstDate) && !StringUtils.isEmpty(secDate) && !StringUtils.isEmpty(formatStr)) {
            SimpleDateFormat format = new SimpleDateFormat(formatStr);

            try {
                Date e = format.parse(firstDate);
                Date date1 = format.parse(secDate);
                offsetDay = (e.getTime() - date1.getTime()) / 86400000L;
            } catch (Exception var8) {
            }

            return offsetDay;
        } else {
            return 0L;
        }
    }

    public static String nextDay(String firstDate, int day, String formatStr) {
        String nextDay = "";
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(formatStr);

        try {
            Date e = format.parse(firstDate);
            calendar.setTime(e);
            calendar.add(6, day);
            nextDay = formatDate(calendar.getTime(), formatStr);
        } catch (Exception var7) {
        }

        return nextDay;
    }

    public static Date nextDate(String firstDate, int day, String formatStr) {
        Date date = null;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(formatStr);

        try {
            Date e = format.parse(firstDate);
            calendar.setTime(e);
            calendar.add(6, day);
            date = calendar.getTime();
        } catch (Exception var7) {
        }

        return date;
    }
}
