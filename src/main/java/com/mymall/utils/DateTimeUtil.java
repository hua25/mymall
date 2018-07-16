package com.mymall.utils;


import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * Created by HUA on 2018/6/25.
 */
public class DateTimeUtil {

    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    //Joda-Time

    /**
     * str->Date，字符串转化为Date类型
     *
     * @param dateTimeStr
     * @param formatStr
     * @return
     */
    public static Date strToDate(String dateTimeStr, String formatStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatStr);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }

    public static Date strToDate(String dateTimeStr) {
        return DateTimeUtil.strToDate(dateTimeStr, DateTimeUtil.STANDARD_FORMAT);
    }

    /**
     * Date类型转化为字符串
     *
     * @param date
     * @param formatStr
     * @return
     */
    public static String dateTOStr(Date date, String formatStr) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatStr);
    }

    public static String dateTOStr(Date date) {
        return DateTimeUtil.dateTOStr(date, STANDARD_FORMAT);
    }


    public static void main(String[] args) {
        System.out.println(DateTimeUtil.dateTOStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
        System.out.println(DateTimeUtil.strToDate("2018-06-25 11:46:39", "yyyy-MM-dd HH:mm:ss"));
    }
}
