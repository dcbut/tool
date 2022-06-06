package com.dcxuexi.util.date;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Title: 日期操作类</p>
 * <p>Description: 完成一些对日期的计算工作</p>
 *
 * @version 1.0.0
 */
public class DateUtils {
    //默认显示日期的格式
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String YEAR_S_MONTH = "yyyy-MM";
    public static final String YEAR_MONTH = "yyyyMM";
    public static final String MONTH_DD = "MMdd";
    public static final String TIMEF_FORMAT = "yyyy-MM-dd HH:mm:ss";
    //默认显示日期时间毫秒格式
    public static final String MSEL_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    //默认显示简体中文日期的格式
    public static final String ZHCN_DATE_FORMAT = "yyyy年MM月dd日";
    //默认显示简体中文日期的格式
    public static final String ZHCN_DATE_MONTH_FORMAT = "yyyy年MM月";
    //默认显示简体中文日期时间的格式
    public static final String ZHCN_TIME_FORMAT = "yyyy年MM月dd日HH时mm分ss秒";
    //默认显示简体中文日期时间毫秒格式
    public static final String ZHCN_MSEL_FORMAT = "yyyy年MM月dd日HH时mm分ss秒SSS毫秒";
    //获取日期串格式
    public static final String DATE_STR_FORMAT = "yyyyMMdd";
    //获取日期时间串格式
    public static final String TIME_STR_FORMAT = "yyyyMMddHHmmss";

    public static final String TIME_STR_FORMAT_YYYYMMDDHHMM = "yyyyMMddHHmm";
    //获取日期时间毫秒串格式
    public static final String MSEL_STR_FORMAT = "yyyyMMddHHmmssSSS";

    //默认显示日期时间分钟格式
    public static final String MSEL_MIU_FORMAT = "yyyy-MM-dd HH:mm";

    public static final String MS_MIU_FORMAT = "yyyyMMddHH";

    public static final String YMDT = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ";
    public static final String YMDTZ = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String YMDTX = "yyyy-MM-dd'T'HH:mm:ss.SSSX";

    /**
     * 获取今天的日期，格式如：2006-11-09
     *
     * @return String - 返回今天的日期
     */
    public static String getToday() {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getDefault());
        return (sdf.format(now.getTime()));
    }

    /**
     * 获取今天的日期，格式自定
     *
     * @param pattern - 设定显示格式
     * @return String - 返回今天的日期
     */
    public static String getToday(String pattern) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(TimeZone.getDefault());
        return (sdf.format(now.getTime()));
    }

    /**
     * 得到当前时间的前/后若干天的时间
     * 例如当前时间2006-05-16 间隔天数30天，则返回2006-04-16
     *
     * @param days - 间隔天数
     * @return String - 返回当时的时间
     */
    public static String getInternalTimeByDay(int days) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getDefault());
        now.add(Calendar.DATE, days);
        return (sdf.format(now.getTime()));
    }

    /**
     * 得到当前时间的前/后若干天的时间
     * 例如当前时间2006-05-16 间隔天数30天，则返回2006-04-16
     *
     * @param days    - 间隔天数
     * @param pattern - 设定显示格式
     * @return String - 根据显示格式返回当时的时间
     */
    public static String getInternalTimeByDay(int days, String pattern) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(TimeZone.getDefault());
        now.add(Calendar.DATE, days);
        return (sdf.format(now.getTime()));
    }

    /**
     * 得到当前时间的前/后若干月的时间
     * 例如当前时间2006-05-16 间隔月数3月，则返回2006-02-16
     *
     * @param months - 间隔月数
     * @return - 返回当时的时间
     */
    public static String getInternalTimeByMonth(int months) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getDefault());
        now.add(Calendar.MONTH, months);
        return (sdf.format(now.getTime()));
    }

    /**
     * 得到当前时间的前/后若干月的时间
     * 例如当前时间2006-05-16 间隔月数3月，则返回2006-02-16
     *
     * @param months  - 间隔月数
     * @param pattern - 设定显示格式
     * @return - 根据显示格式返回当时的时间
     */
    public static String getInternalTimeByMonth(int months, String pattern) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(TimeZone.getDefault());
        now.add(Calendar.MONTH, months);
        return (sdf.format(now.getTime()));
    }

    /**
     * 得到中文日期
     *
     * @param dateStr - 日期串，格式为“yyyy-MM-dd”
     * @return String - 返回中文日期，格式为“yyyy年MM月dd日”
     */
    public static String chinaDate(String dateStr) {
        if (dateStr == null || dateStr.equals("null") || dateStr.equals("")) {
            return "";
        }
        Date d = getDate(dateStr, DATE_FORMAT);
        SimpleDateFormat sdf = new SimpleDateFormat(ZHCN_DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getDefault());
        return (sdf.format(d));
    }

    /**
     * 得到中文日期,自定设置格式
     *
     * @param dateStr    - 需要改变格式的时间串
     * @param inPattern  - 时间串的格式
     * @param outPattern - 改为时间串的格式
     * @return String - 根据outPattern格式返回时间
     */
    public static String alterDateByDynamic(String dateStr, String inPattern, String outPattern) {
        if (dateStr == null || dateStr.equals("null") || dateStr.equals("")) {
            return "";
        }
        Date d = getDate(dateStr, inPattern);
        SimpleDateFormat sdf = new SimpleDateFormat(outPattern);
        sdf.setTimeZone(TimeZone.getDefault());
        return (sdf.format(d));
    }

    /**
     * 比较当前日期和指定日期 return boolean
     * 如果当前日期在指定日期之后返回true否则返回flase
     *
     * @param dateStr 指定日期
     * @param pattern 指定日期的格式
     * @return boolean
     */
    public static boolean dateCompare(String dateStr, String pattern) {
        boolean bea = false;
        SimpleDateFormat sdf_d = new SimpleDateFormat(pattern);
        String isDate = sdf_d.format(new Date());
        Date date1;
        Date date0;
        try {
            date1 = sdf_d.parse(dateStr);
            date0 = sdf_d.parse(isDate);
            if (date0.after(date1)) {
                bea = true;
            }
        } catch (ParseException e) {
            bea = false;
        }
        return bea;
    }

    /**
     * 比较指定两日期,如果dateStr1晚于dateStr2则return true;
     *
     * @param dateStr1 指定日期
     * @param dateStr2 指定日期
     * @param pattern  指定日期的格式
     * @return boolean
     */
    public static boolean dateCompare(String dateStr1, String dateStr2, String pattern) {
        boolean bea = false;
        SimpleDateFormat sdf_d = new SimpleDateFormat(pattern);
        Date date1;
        Date date0;
        try {
            date1 = sdf_d.parse(dateStr1);
            date0 = sdf_d.parse(dateStr2);
            if (date0.after(date1)) {
                bea = true;
            }
        } catch (ParseException e) {
            bea = false;
        }
        return bea;
    }

    /**
     * 比较指定两日期,如果dateStr1大于dateStr2则return true;
     *
     * @param dateStr1 指定日期
     * @param dateStr2 指定日期
     * @param pattern  指定日期的格式
     * @return boolean
     */
    public static boolean dateCompareNew(String dateStr1, String dateStr2, String pattern) {
        boolean bea = false;
        SimpleDateFormat sdf_d = new SimpleDateFormat(pattern);
        Date date1;
        Date date0;
        try {
            date1 = sdf_d.parse(dateStr1);
            date0 = sdf_d.parse(dateStr2);
            if (date1.after(date0)) {
                bea = true;
            }
        } catch (ParseException e) {
            bea = false;
        }
        return bea;
    }

    /**
     * 设置间隔数后返回时间
     *
     * @param type 间隔类型 秒或者天  秒的类型为s,天的类型为d
     * @param 间隔数字 比如1秒或者一天
     * @return String 返回时间格式为“yyyy-MM-dd HH:mm:ss”
     */
    public static String dateAdd(String type, int i) {
        return dateAdd(type, i, TIMEF_FORMAT);
    }


    public static String dateAdd(String type, int i, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        String str = getToday(format);
        Calendar c = Calendar.getInstance(); // 当时的日期和时间
        if (type.equals("s")) {
            int s = c.get(Calendar.SECOND);
            s = s + i;
            c.set(Calendar.SECOND, s);
            str = df.format(c.getTime());
        } else if (type.equals("d")) {
            int d = c.get(Calendar.DAY_OF_MONTH); // 取出“日”数
            d = d + i;
            c.set(Calendar.DAY_OF_MONTH, d); // 将“日”数设置回去
            str = df.format(c.getTime());
        } else if (type.equals("h")) {
            int d = c.get(Calendar.HOUR_OF_DAY); // 取出“日”数
            d = d + i;
            c.set(Calendar.HOUR_OF_DAY, d); // 将“日”数设置回去
            str = df.format(c.getTime());
        } else if (type.equals("m")) {
            int d = c.get(Calendar.MINUTE); // 取出“分钟”数
            d = d + i;
            c.set(Calendar.MINUTE, d); // 将“日”数设置回去
            str = df.format(c.getTime());
        }
        return str;
    }


    /**
     * 得到当前日期，如"2001-03-16".
     *
     * @version 1.0
     * @author wanghaibo.
     */
    public static String curDate() {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        String DATE_FORMAT = "yyyy-MM-dd";
        //String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        //   String DATE_FORMAT = "yyyyMMdd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getDefault());
        return (sdf.format(now.getTime()));
    }

    /**
     * 得到当前详细日期、时间，如"2001-03-16 20:34:20".
     */
    public static String curTime() {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        //String DATE_FORMAT = "yyyy-MM-dd";
        String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        //String DATE_FORMAT = "yyyyMMdd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getDefault());
        return (sdf.format(now.getTime()));
    }

    /**
     * 得到当前详细日期、时间，如"2001-03-16 20:34:20".
     *
     * @version 1.0
     */
    public static String getTimeAfter(int n) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.add(Calendar.MINUTE, n);
        //String DATE_FORMAT = "yyyy-MM-dd";
        String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        //String DATE_FORMAT = "yyyyMMdd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getDefault());
        return (sdf.format(now.getTime()));
    }

    /**
     * 得到当前时间的前/后若干天的时间
     *
     * @param day - 间隔时间
     * @return - 返回当时的时间
     * 例如当前时间2003-05-16 间隔天数30天，则返回2003-04-16
     */
    public static String getInternalTime(int days) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        //   String DATE_FORMAT = "yyyy-MM-dd";
        String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        //  String DATE_FORMAT = "yyyyMMdd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getDefault());
        now.add(Calendar.DATE, days);
        return (sdf.format(now.getTime()));
    }

    /**
     * 得到当前时间的前/后若干天的时间
     *
     * @param currentTime - 当前时间
     * @param iHour       - 间隔时间
     * @return - 返回当时的时间
     * 例如当前时间2003-05-16 08:10:10 间隔时间3小时，则返回2003-05-16 05:10:10
     */
    public static String getTimeOut(String currentTime, int iHour) {
        String Time = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date result = sdf.parse(currentTime);
            Calendar cal = Calendar.getInstance();
            cal.setTime(result);
            cal.add(Calendar.HOUR, iHour);
            Time = sdf.format(cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return Time;
    }

    /**
     * 得到当前月底的前/后若干天的时间
     *
     * @param day - 间隔时间
     * @return - 返回当时的时间
     */
    public static String getInternalTimeByLastDay(int days) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        String DATE_FORMAT = "yyyy-MM-dd";
        //   String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        //  String DATE_FORMAT = "yyyyMMdd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getDefault());
        int maxDay = now.getActualMaximum(Calendar.DAY_OF_MONTH);
        now.set(Calendar.DATE, maxDay);
        now.add(Calendar.DATE, days);
        return (sdf.format(now.getTime()));
    }

    /**
     * 得到时间串
     *
     * @param dateStr String 时间字符串
     * @param fmt     String 时间格式
     * @return String 返回值
     */
    public static String getDateStr(String dateStr, String fmt) {
        try {
            if (dateStr == null || dateStr.equals("")) {
                return "";
            }
            SimpleDateFormat sdf = new SimpleDateFormat(fmt);
            //          java.text.DateFormat df = java.text.DateFormat.getDateTimeInstance();
            Date d = sdf.parse(dateStr);
            String newDate = sdf.format(d);
            return newDate;
        } catch (Exception e) {
            //log.debug("\n" + e.getMessage());
        }
        return "";
    }

    /**
     * 得到时间串
     *
     * @param dateStr String 时间字符串
     * @param fmt     String 时间格式
     * @return String 返回值
     */
    public static Date getDate(String dateStr) {
        try {
            if (dateStr == null || dateStr.equals("")) {
                return null;
            }
            //         Calendar now = Calendar.getInstance(TimeZone.getDefault());
            String DATE_FORMAT = "yyyyMMddHHmmss";
            //          java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            //          sdf.setTimeZone(TimeZone.getDefault());
            Date d = sdf.parse(dateStr);
            //          java.text.DateFormat df = java.text.DateFormat.getDateTimeInstance();
            //          java.util.Date  d= df.parse(dateStr);
            return d;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return 得到当前时间目录例如 030524
     */
    public static String getCurrTimeDir() {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        String DATE_FORMAT = "yyMMdd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getDefault());
        return (sdf.format(now.getTime()));
    }

    /**
     * @return 得到上个月月份 如200505
     */
    public static String getYesterM() {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.add(Calendar.MONTH, -1);
        String DATE_FORMAT = "yyyyMM";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getDefault());
        return (sdf.format(now.getTime()));
    }

    /**
     * @return 得到本年度年份 如2005
     */
    public static String getYear() {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        //now.add(Calendar.MONTH,-1);
        String DATE_FORMAT = "yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getDefault());
        return (sdf.format(now.getTime()));
    }

    /**
     * @return 得到本月月份 如09
     */
    public static String getMonth() {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.add(Calendar.MONTH, -1);
        String DATE_FORMAT = "MM";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getDefault());
        return (sdf.format(now.getTime()));
    }

    /**
     * 得到下一个月分，包括年，例如：
     * 2003－1 月份的上一个月份是2002－12
     *
     * @param year
     * @param month
     * @return
     */
    public static String[] getBeforeMonth(String year, String month) {
        String[] time = new String[2];
        if (month.equals("12")) {
            time[1] = "1";
            time[0] = String.valueOf(Integer.parseInt(year) + 1);
        } else {
            time[1] = String.valueOf(Integer.parseInt(month) + 1);
            time[0] = year;
        }
        return time;
    }

    /**
     * 得到上一个月
     *
     * @param year  年
     * @param month 月
     * @return String[] 0为年,1为月
     */
    public static String[] beforeMonth(String year, String month) {
        String[] time = new String[2];
        if (month.equals("1")) {
            time[1] = "12";
            time[0] = String.valueOf(Integer.parseInt(year) - 1);
        } else {
            time[1] = String.valueOf(Integer.parseInt(month) - 1);
            time[0] = year;
        }
        return time;
    }

    /**
     * 得到当前日期，按照页面日期控件格式，如"2001-3-16".
     *
     * @return String
     */
    public static String curSingleNumDate() {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        String DATE_FORMAT = "yyyy-M-d";
        //String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        //   String DATE_FORMAT = "yyyyMMdd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getDefault());
        return (sdf.format(now.getTime()));
    }

    /**
     * 取自当前日期后的第n天的日期
     *
     * @param day int 之后n天
     * @return String
     */
    public static String getDateAfter(int n) {
        String Time = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance(TimeZone.getDefault());
            cal.add(Calendar.DAY_OF_MONTH, n);
            Time = sdf.format(cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return Time;
    }

    /**
     * 得到半年前的日期
     *
     * @return String
     */
    public static String getHalfYearBeforeStr() {
        GregorianCalendar cal = new GregorianCalendar();
        /** @todo 取当前日期 */
        String month = "";
        int tMonth = cal.get(GregorianCalendar.MONTH) + 1;
        if (tMonth < 10) {
            month = "0" + tMonth;
        } else {
            month = "" + tMonth;
        }
        int tDay = cal.get(GregorianCalendar.DATE);
        String day = "";
        if (tDay < 10) {
            day = "0" + tDay;
        } else {
            day = "" + tDay;
        }
        //        String endDate = "" + cal.get(GregorianCalendar.YEAR) + month + day;
        /** @todo 取半年前日期 */
        cal.add(GregorianCalendar.MONTH, -6);
        tMonth = cal.get(GregorianCalendar.MONTH) + 1;
        if (tMonth < 10) {
            month = "0" + tMonth;
        } else {
            month = "" + tMonth;
        }
        tDay = cal.get(GregorianCalendar.DATE);
        day = "";
        if (tDay < 10) {
            day = "0" + tDay;
        } else {
            day = "" + tDay;
        }
        String beginDate = "" + cal.get(GregorianCalendar.YEAR) + month + day;
        return beginDate;
    }

    /**
     * 返回比当前日期晚几分钟的一个yyyy-MM-dd HH:mm:ss的日期串晚的分钟数可由输入参数minute控制
     *
     * @param minute
     * @return 返回延迟N分钟后的时间串
     */
    public static String FgetCurrentNextMinute(int minute) {
        String chargeStartTime = "";
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.MINUTE, minute);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            chargeStartTime = sdf.format(cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chargeStartTime;
    }

    /**
     * 得到当前分钟
     *
     * @return int
     */
    public static int getCurMin() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm");
        int currentTime = Integer.parseInt(simpleDateFormat.format(date));
        return currentTime;
    }

    /**
     * @param formatStr
     * @return
     */
    private static DateFormat getDateFormat(String formatStr) {

        if (formatStr.equalsIgnoreCase(DATE_FORMAT)) {
            return new SimpleDateFormat(DATE_FORMAT);
        } else if (formatStr.equalsIgnoreCase(TIMEF_FORMAT)) {
            return new SimpleDateFormat(TIMEF_FORMAT);
        } else if (formatStr.equalsIgnoreCase(ZHCN_DATE_FORMAT)) {
            return new SimpleDateFormat(ZHCN_DATE_FORMAT);
        } else if (formatStr.equalsIgnoreCase(ZHCN_TIME_FORMAT)) {
            return new SimpleDateFormat(ZHCN_TIME_FORMAT);
        } else {
            return new SimpleDateFormat(formatStr);
        }
    }

    //	public static Date getDate(String dateTimeStr)
    //	{				
    //		return getDate(dateTimeStr,DATATIMEF_STR);
    //	}

    /**
     * 按照默认formatStr的格式，转化dateTimeStr为Date类型
     * dateTimeStr必须是formatStr的形式
     *
     * @param dateTimeStr
     * @param formatStr
     * @return
     */
    public static Date getDate(String dateTimeStr, String formatStr) {
        try {
            if (dateTimeStr == null || dateTimeStr.equals("")) {
                return null;
            }
            DateFormat sdf = getDateFormat(formatStr);
            Date d = sdf.parse(dateTimeStr);
            return d;
        } catch (ParseException e) {
            //throw new LangException(e);
        }
        return null;
    }

    /**
     * 将Date转换成字符串“yyyy-mm-dd hh:mm:ss”的字符串
     *
     * @param date 日期
     * @return String 字符串
     */
    public static String dateToDateString(Date date) {
        return dateToDateString(date, TIMEF_FORMAT);
    }

    /**
     * 将Date转换成formatStr格式的字符串
     *
     * @param date
     * @param formatStr
     * @return
     */
    public static String dateToDateString(Date date, String formatStr) {
        DateFormat df = getDateFormat(formatStr);
        return df.format(date);
    }

    /**
     * 返回一个yyyy-MM-dd HH:mm:ss 形式的日期时间字符串中的HH:mm:ss
     *
     * @param dateTime
     * @return
     */
    public static String getTimeString(String dateTime) {
        return getTimeString(dateTime, TIMEF_FORMAT);
    }

    /**
     * 返回一个formatStr格式的日期时间字符串中的HH:mm:ss
     *
     * @param dateTime
     * @param formatStr
     * @return
     */
    public static String getTimeString(String dateTime, String formatStr) {
        Date d = getDate(dateTime, formatStr);
        String s = dateToDateString(d);
        return s.substring(TIMEF_FORMAT.indexOf('H'));
    }

    /**
     * 获取当前日期yyyy-MM-dd的形式
     *
     * @return
     */
    public static String getCurDate() {
        return dateToDateString(new Date(), DATE_FORMAT);
    }

    /**
     * 获取当前日期yyyy年MM月dd日的形式
     *
     * @return
     */
    public static String getCurZhCNDate() {
        return dateToDateString(new Date(), ZHCN_DATE_FORMAT);
    }

    /**
     * 获取当前日期时间yyyy-MM-dd HH:mm:ss的形式
     *
     * @return
     */
    public static String getCurDateTime() {
        return dateToDateString(new Date(), TIMEF_FORMAT);
    }

    /**
     * 获取当前日期时间yyyy年MM月dd日HH时mm分ss秒的形式
     *
     * @return
     */
    public static String getCurZhCNDateTime() {
        return dateToDateString(new Date(), ZHCN_TIME_FORMAT);
    }

    /**
     * 获取日期d的days天后的一个Date
     *
     * @param d
     * @param days
     * @return Date
     */
    public static Date getInternalDateByDay(Date d, int days) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.DATE, days);
        return now.getTime();
    }

    /**
     * 获取日期d的months月后的一个Date
     *
     * @param d
     * @param months
     * @return Date
     */
    public static Date getInternalDateByMon(Date d, int months) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.MONTH, months);
        return now.getTime();
    }

    /**
     * 获取日期d的years年后的一个Date
     *
     * @param d
     * @param years
     * @return Date
     */
    public static Date getInternalDateByYear(Date d, int years) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.YEAR, years);
        return now.getTime();
    }

    /**
     * 获取日期d的sec秒后的一个Date
     *
     * @param d
     * @param sec
     * @return Date
     */
    public static Date getInternalDateBySec(Date d, int sec) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.SECOND, sec);
        return now.getTime();
    }

    /**
     * 获取日期d的min分后的一个Date
     *
     * @param d
     * @param min
     * @return Date
     */
    public static Date getInternalDateByMin(Date d, int min) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.MINUTE, min);
        return now.getTime();
    }

    /**
     * 获取日期d的hours小时后的一个Date
     *
     * @param d
     * @param hours
     * @return Date
     */
    public static Date getInternalDateByHour(Date d, int hours) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.HOUR_OF_DAY, hours);
        return now.getTime();
    }

    /**
     * 根据一个日期字符串，返回日期格式，目前支持4种
     * 如果都不是，则返回null
     *
     * @param DateString
     * @return 返回日期格式，目前支持4种
     */
    public static String getFormateStr(String DateString) {
        String patternStr1 = "[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}"; //"yyyy-MM-dd"
        String patternStr2 = "[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}\\s[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}"; //"yyyy-MM-dd HH:mm:ss";
        String patternStr3 = "[0-9]{4}年[0-9]{1,2}月[0-9]{1,2}日"; //"yyyy年MM月dd日"
        String patternStr4 = "[0-9]{4}年[0-9]{1,2}月[0-9]{1,2}日[0-9]{1,2}时[0-9]{1,2}分[0-9]{1,2}秒"; //"yyyy年MM月dd日HH时mm分ss秒"
        Pattern p = Pattern.compile(patternStr1);
        Matcher m = p.matcher(DateString);
        boolean b = m.matches();
        if (b) {
            return DATE_FORMAT;
        }
        p = Pattern.compile(patternStr2);
        m = p.matcher(DateString);
        b = m.matches();
        if (b) {
            return TIMEF_FORMAT;
        }
        p = Pattern.compile(patternStr3);
        m = p.matcher(DateString);
        b = m.matches();
        if (b) {
            return ZHCN_DATE_FORMAT;
        }
        p = Pattern.compile(patternStr4);
        m = p.matcher(DateString);
        b = m.matches();
        if (b) {
            return ZHCN_TIME_FORMAT;
        }
        return null;
    }

    /**
     * 将一个"yyyy-MM-dd HH:mm:ss"字符串，转换成"yyyy年MM月dd日HH时mm分ss秒"的字符串
     *
     * @param dateStr
     * @return
     */
    public static String getZhCNDateTime(String dateStr) {
        Date d = getDate(dateStr);
        return dateToDateString(d, ZHCN_TIME_FORMAT);
    }

    /**
     * 将一个"yyyy-MM-dd"字符串，转换成"yyyy年MM月dd日"的字符串
     *
     * @param dateStr
     * @return
     */
    public static String getZhCNDate(String dateStr) {
        Date d = getDate(dateStr, DATE_FORMAT);
        return dateToDateString(d, ZHCN_DATE_FORMAT);
    }

    /**
     * 将dateStr从fmtFrom转换到fmtTo的格式
     *
     * @param dateStr
     * @param fmtFrom
     * @param fmtTo
     * @return
     */
    public static String getDateStr(String dateStr, String fmtFrom, String fmtTo) {
        Date d = getDate(dateStr, fmtFrom);
        return dateToDateString(d, fmtTo);
    }

    /**
     * 将小时数换算成返回以毫秒为单位的时间
     *
     * @param hours
     * @return
     */
    public static long getMicroSec(BigDecimal hours) {
        BigDecimal bd;
        bd = hours.multiply(new BigDecimal(3600 * 1000));
        return bd.longValue();
    }

    /**
     * 获取Date中的分钟
     *
     * @param d
     * @return
     */
    public static int getMin(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.MINUTE);
    }

    /**
     * 获取xxxx-xx-xx的日
     *
     * @param d
     * @return
     */
    public static int getDay(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取月份，1-12月
     *
     * @param d
     * @return
     */
    public static int getMonth(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取19xx,20xx形式的年
     *
     * @param d
     * @return
     */
    public static int getYear(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.YEAR);
    }

    /**
     * 得到d的上个月的年份+月份,如200505
     *
     * @return
     */
    public static String getYearMonthOfLastMon(Date d) {
        Date newdate = getInternalDateByMon(d, -1);
        String year = String.valueOf(getYear(newdate));
        String month = String.valueOf(getMonth(newdate));
        return year + month;
    }

    /**
     * 得到当前日期的年和月如200509
     *
     * @return String
     */
    public static String getCurYearMonth() {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        String DATE_FORMAT = "yyyyMM";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getDefault());
        return (sdf.format(now.getTime()));
    }

    /**
     * @param year
     * @param month
     * @return
     */
    public static Date getNextMonth(String year, String month) {
        String datestr = year + "-" + month + "-01";
        Date date = getDate(datestr, DATE_FORMAT);
        return getInternalDateByMon(date, 1);
    }

    /**
     * @param year
     * @param month
     * @return
     */
    public static Date getLastMonth(String year, String month) {
        String datestr = year + "-" + month + "-01";
        Date date = getDate(datestr, DATE_FORMAT);
        return getInternalDateByMon(date, -1);
    }

    /**
     * 得到日期d，按照页面日期控件格式，如"2001-3-16"
     *
     * @param d
     * @return
     */
    public static String getSingleNumDate(Date d) {
        return dateToDateString(d, DATE_FORMAT);
    }

    /**
     * 得到d半年前的日期,"yyyy-MM-dd"
     *
     * @param d
     * @return
     */
    public static String getHalfYearBeforeStr(Date d) {
        return dateToDateString(getInternalDateByMon(d, -6), DATE_FORMAT);
    }

    /**
     * 得到当前日期D的days天的前/后若干天的时间,<0表示之前，>0表示之后
     *
     * @param d
     * @param days
     * @return
     */
    public static String getInternalDateByLastDay(Date d, int days) {
        return dateToDateString(getInternalDateByDay(d, days), DATE_FORMAT);
    }

    public static java.sql.Date getSqlDate(String dateTimeStr) {
        //DateUtils.getTIME_STR_FORMAT
        java.sql.Date d = new java.sql.Date(getDate(dateTimeStr, TIME_STR_FORMAT).getTime());
        //		d.setHours(Integer.parseInt(dateTimeStr.substring(8,10)));
        //		d.setMinutes(Integer.parseInt(dateTimeStr.substring(10,12)));
        //		d.setSeconds(Integer.parseInt(dateTimeStr.substring(12,14)));
        return d;
    }

    /**
     * 获取上一个月
     *
     * @param ym      2013-09
     * @param month
     * @param pattern
     * @return
     */
    public static String getLastMonth(String ym, int month, String pattern) {
        Date date = getDate(ym, YEAR_S_MONTH);
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(date);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(TimeZone.getDefault());
        now.add(Calendar.MONTH, month);
        return sdf.format(now.getTime());
    }

    /**
     * 返回当月最后一天的日期
     */
    public static String getLastDayOfMonth(Date date) {
        Calendar calendar = convert(date);
        calendar.set(Calendar.DATE, calendar.getMaximum(Calendar.DATE));
        DateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return format.format(calendar.getTime());
    }

    /**
     * 将日期转换为日历
     *
     * @param date 日期
     * @return 日历
     */
    private static Calendar convert(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * date2 - date1 = 多少天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long getDiffersDays(String date1, String date2) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
            Date d1 = sdf.parse(date1);
            Date d2 = sdf.parse(date2);
            long daysBetween = (d2.getTime() - d1.getTime() + 1000000) / (3600 * 24 * 1000);
            return daysBetween;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /**
     * 计算两个日期之间相差的天数   (date2 - date1)
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int daysBetween(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 计算两个日期相差天数
     *
     * @param smdate
     * @param bdate
     * @return
     * @throws ParseException
     */
    public static int daysBetween(String smdate, String bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(smdate));
            long time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);
            return Integer.parseInt(String.valueOf(between_days));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据给定日期Date返回星期几
     *
     * @param date
     * @return
     */
    public static String getWeekOfDate(Date date) {
        String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();

        if (date != null) {
            calendar.setTime(date);
        }

        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        if (w < 0) {
            w = 0;
        }

        return weekOfDays[w];
    }

    /**
     * 根据给定日期(字符串形式)返回星期几
     *
     * @param date
     * @return
     */
    public static String getWeekOfDate(String date) {
        Date d = getDate(date, "yy-MM-dd");
        return getWeekOfDate(d);
    }

    /**
     * 返回指定日期的下一个日期
     *
     * @param d
     * @return
     */
    public static Date getNextDate(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.DAY_OF_MONTH, 1);
        return now.getTime();
    }

    /**
     * 返回指定日期的下一个日期
     *
     * @param d
     * @param dateFomat
     * @return
     */
    public static String getNextDate(String d, String dateFomat) {
        Date date = getNextDate(getDate(d, dateFomat));
        DateFormat df = new SimpleDateFormat(dateFomat);
        return df.format(date);
    }

    /**
     * 获取Date中的小时
     *
     * @param d
     * @return
     */
    public static int getHour(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 给定时间，返回小时
     *
     * @param d
     * @return
     */
    public static int getHour(String d) {
        return getHour(getDate(d, "hh:mm:ss"));
    }

    public static int getMin(String d) {
        return getMin(getDate(d, "hh:mm:ss"));
    }

    /**
     * 按指定格式返回日期字符串
     *
     * @param pattern
     * @return
     * @Title: formatDate
     * @return: String
     */
    public static String formatDate(String pattern) {
        return formatDate(new Date(), pattern);
    }

    /**
     * @param date
     * @param pattern
     * @return
     * @Title: formatDate
     * @return: String
     */
    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 格式化输出日期
     *
     * @param date   日期
     * @param format 格式
     * @return 返回字符型日期
     */
    public static String format(Date date, String format) {
        String result = "";
        try {
            if (date != null) {
                DateFormat df = new SimpleDateFormat(format);
                result = df.format(date);
            }
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * @param date
     * @return
     * @Title: getDateByStr
     * @return: Date
     */
    public static Date getDateByStr(String date) {
        return getDateByStr(date, TIMEF_FORMAT);
    }

    /**
     * @param date
     * @param pattern
     * @return
     * @Title: getDateByStr
     * @return: Date
     */
    public static Date getDateByStr(String date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * 补齐不足长度
     *
     * @param length 长度
     * @param number 数字
     * @return
     */
    public static String lpad(int length, Long number) {
        String f = "%0" + length + "d";
        //超过的话  就截取
        String nuber_str = number + "";
        if (nuber_str.length() > length) {
            return nuber_str = nuber_str.substring(nuber_str.length() - length);
        }
        return String.format(f, number);
    }

    /**
     * 计算两个日期相差 毫秒数
     *
     * @param smdate
     * @param bdate
     * @return
     * @throws ParseException
     */
    public static int millBetween(String smdate, String bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat(MSEL_FORMAT);
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(smdate));
            long time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1);
            return Integer.parseInt(String.valueOf(between_days));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /**
     * 计算两个日期相差 毫秒数
     *
     * @param smdate
     * @param bdate
     * @return
     * @throws ParseException
     */
    public static int dateBetween(String smdate, String bdate, String format, String type) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            long nd = 1000 * 24 * 60 * 60;//一天的毫秒数
            long nh = 1000 * 60 * 60;//一小时的毫秒数
            long nm = 1000 * 60;//一分钟的毫秒数
            long ns = 1000;//一秒钟的毫秒数

            Long diff = sdf.parse(bdate).getTime() - sdf.parse(smdate).getTime();

            long day = diff / nd;//计算差多少天
            long hour = diff / nh;//计算差多少小时
            long min = diff / nm;//计算差多少分钟
            long sec = diff / ns;//计算差多少秒//输出结果
            if (type.equals("h")) {
                return Integer.parseInt(String.valueOf(hour));
            } else if (type.equals("m")) {
                return Integer.parseInt(String.valueOf(min));
            } else if (type.equals("s")) {
                return Integer.parseInt(String.valueOf(sec));
            } else if (type.equals("d")) {
                return Integer.parseInt(String.valueOf(day));
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取上个月第一天
     *
     * @param date
     * @return
     */
    public static String getFirstDayOfLastMonth(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        Date theDate = calendar.getTime();

        //上个月第一天
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        return day_first;
    }

    /**
     * 获取上个月最后一天
     *
     * @param date
     * @return
     */
    public static String getLastDayOfTheLastMonth(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        //上个月最后一天
        calendar.add(Calendar.MONTH, 1);    //加一个月
        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
        String day_last = df.format(calendar.getTime());
        return day_last;
    }

    /**
     * 获取本个月最后一天
     *
     * @param date
     * @return
     */
    public static String getLastDayOfTheMonth(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        //上个月最后一天
        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
        String day_last = df.format(calendar.getTime());
        return day_last;
    }

    /**
     * 获取本个月最后一天
     *
     * @param date
     * @return
     */
    public static String getLastDayOfTheMonth() {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        //上个月最后一天
        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
        String day_last = df.format(calendar.getTime());
        return day_last;
    }

    public static String getISO8601Date(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    public static String getISODateFormat(String date, String par, String par2) {
        SimpleDateFormat sdf = new SimpleDateFormat(par);
        try {
            return new SimpleDateFormat(par2).format(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取两个日期之间相差的月份数
     *
     * @param str1
     * @param str2
     * @return
     */
    public static int getDiffMonth(String str1, String str2) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Date date1 = sdf.parse(str1);
            Date date2 = sdf.parse(str2);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date1);
            int month1 = cal.get(Calendar.MONTH);
            int year1 = cal.get(Calendar.YEAR);
            cal.setTime(date2);
            int month2 = cal.get(Calendar.MONTH);
            int year2 = cal.get(Calendar.YEAR);
            month1 = month1 + 12 * (year1 - year2);
            int diff = month2 - month1;
            return Math.abs(diff);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取给定日期所在月的最后一天
     *
     * @param str
     * @return
     */
    public static String getLastByStrDate(String str) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date = df.parse(str);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, 1);//月数加1
            calendar.set(Calendar.DATE, 1); // 设置为下月的第一天
            calendar.add(Calendar.DATE, -1); // 再减一天即为上个月最后一天
            String day_last = df.format(calendar.getTime());
            return day_last;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取指定时间所在的月份第一天的日期     YYYY-MM-DD
     *
     * @return
     * @auther husy
     * @date 2017年2月16日下午2:22:59
     */
    public static String getFirstDay(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(date));
            cal.set(Calendar.DAY_OF_MONTH, 1);
            return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取指定时间所在的月份最后一天的日期     YYYY-MM-DD
     *
     * @return
     * @auther husy
     * @date 2017年2月16日下午2:22:59
     */
    public static String getLastDay(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(date));
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.roll(Calendar.DAY_OF_MONTH, -1);
            return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取2个时间间隔的月数列表    YYYY-MM
     *
     * @param startDate
     * @param endDate
     * @return list
     * @auther cxs
     * @date 2017年6月02日下午2:22:59
     */
    public static List<String> getMonthBetween(String startDate, String endDate) {
        List<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月
        try {
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();

            cal1.setTime(sdf.parse(startDate));
            cal1.set(cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH), 1);

            cal2.setTime(sdf.parse(endDate));
            cal2.set(cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH), 2);

            Calendar curr = cal1;
            while (curr.before(cal2)) {
                result.add(sdf.format(curr.getTime()));
                curr.add(Calendar.MONTH, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取指定日期 前后多少天的日期
     *
     * @param days
     * @param startDate
     * @return
     * @throws ParseException
     */
    public static String getDateBetweenDays(int days, String startDate) {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//格式化为年月日
        try {
            now.setTime(sdf.parse(startDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        now.add(Calendar.DATE, days);
        return (sdf.format(now.getTime()));
    }

    /**
     * 返回2个日期中间的每一天日期
     *
     * @param dateFirst
     * @param dateSecond
     */
    public static List<String> getEveryDayByTwoDates(String dateFirst, String dateSecond) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<String> list = new ArrayList<String>();
        try {
            Date dateOne = dateFormat.parse(dateFirst);
            Date dateTwo = dateFormat.parse(dateSecond);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateOne);
            while (calendar.getTime().compareTo(dateTwo) <= 0) {
                list.add(dateFormat.format(calendar.getTime()));
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取指定日期所在周的第一天
     *
     * @param dataStr yyyy-MM-dd
     * @return
     * @throws ParseException
     */
    public static String getFirstOfWeek(String dataStr) {
        try {
            Calendar cal = Calendar.getInstance();

            cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(dataStr));

            int d = 0;
            if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
                d = -6;
            } else {
                d = 2 - cal.get(Calendar.DAY_OF_WEEK);
            }
            cal.add(Calendar.DAY_OF_WEEK, d);
            // 所在周开始日期
            String data = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
            return data;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 根据毫秒数获得格式化日期
     *
     * @param currentTimeMillis 毫秒数
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getDateByCurrentMillis(long currentTimeMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        date.setTime(currentTimeMillis);
        return simpleDateFormat.format(date);
    }

    /**
     * 获取精确到毫秒的时间戳
     *
     * @return
     **/
    public static Long getTimestamp() {
        Date date = new Date();
        if (null == date) {
            return (long) 0;
        }
        String timestamp = String.valueOf(date.getTime());
        return Long.valueOf(timestamp);
    }


    /**
     * 获取精确到秒的时间戳
     *
     * @return
     **/
    public static String getSecondTimestamp() {
        Date date = new Date();
        if (null == date) {
            return "0";
        }
        String timestamp = String.valueOf(date.getTime() / 1000);
        return timestamp;
    }

    /**
     * 获取下个月份
     *
     * @param date
     * @return
     */
    public static String getNextMonth(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 往后回退*个月
     *
     * @param date
     * @param i
     * @return
     * @date: 2019年9月18日下午3:53:28
     * @version:
     */
    public static Date backDateYear(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, i);
        return cal.getTime();
    }


}
