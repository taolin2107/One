package app.taolin.one.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Taolin on 16/5/27.
 */

public class DateUtil {

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private static final DateFormat displayDateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
    private static final DateFormat dayFormat = new SimpleDateFormat("dd", Locale.US);
    private static final DateFormat monthYearFormat = new SimpleDateFormat("MMM, yyyy", Locale.US);
    private static final DateFormat oldDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

    private static final DateFormat shortTime = new SimpleDateFormat("HH:mm", Locale.US);
    private static final String UPDATE_TIME_EVERYDAY = "21:30";  //每天 21:30更新当天的内容
    private static final long DAY = 24 * 3600 * 1000;

    public static String getDateString(int diffDays) {
//        Date current = new Date();
//        if (shortTime.format(current).compareTo(UPDATE_TIME_EVERYDAY) < 0) {
//            diffDays += 1;
//        }
        final long milliseconds = System.currentTimeMillis() - diffDays * DAY;
        return dateFormat.format(new Date(milliseconds));
    }

    //修正老的文章日期差一天的问题
    public static String getOldFormatDate(String dateString) {
        try {
            return dateFormat.format(oldDateFormat.parse(dateString).getTime() + DAY);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormat.format(new Date());
    }

    public static String getDisplayDate(String dateString) {
        try {
            return displayDateFormat.format(dateFormat.parse(dateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return displayDateFormat.format(new Date());
    }

    public static String getDay(String dateString) {
        try {
            return dayFormat.format(dateFormat.parse(dateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return displayDateFormat.format(new Date());
    }

    public static String getMonthYear(String dateString) {
        try {
            return monthYearFormat.format(dateFormat.parse(dateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return displayDateFormat.format(new Date());
    }

    public static String getRequestDateString(Date date) {
        return dateFormat.format(date);
    }
}
