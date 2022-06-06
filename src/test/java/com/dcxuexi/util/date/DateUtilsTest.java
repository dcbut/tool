package com.dcxuexi.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtilsTest {
    public static void main(String[] args) throws ParseException {
//       System.out.println(getFirstDay("2017-09-01", "yyyy-MM-dd"));
//       System.out.println(getFirstOfWeek("2018-05-16"));
//       System.out.println(getToday(" HH:mm:ss"));

//       System.out.println(getEveryDayByTwoDates("2016-12-31","2018-01-01"));
//       long time3 = 1531292388381L ;
        System.out.println(DateUtils.getNextMonth(new Date(), DateUtils.YEAR_MONTH));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(DateUtils.backDateYear(new Date(), -4)));
    }
}
