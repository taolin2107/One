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
    private static final DateFormat requestDateFormat = new SimpleDateFormat("yyyy-MM", Locale.US);

    private static final DateFormat shortTime = new SimpleDateFormat("HH:mm", Locale.US);
    private static final String UPDATE_TIME_EVERYDAY = "21:30";
    private static final long DAY = 24 * 3600 * 1000;

    public static String getDateString(int diffDays) {
        Date current = new Date();
        if (shortTime.format(current).compareTo(UPDATE_TIME_EVERYDAY) < 0) {
            diffDays += 1;
        }
        final long milliseconds = current.getTime() - diffDays * DAY;
        return dateFormat.format(new Date(milliseconds));
    }

    public static String getDisplayDate(String dateString) {
        try {
            return displayDateFormat.format(dateFormat.parse(dateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return displayDateFormat.format(new Date());
    }

    public static Date getDate(String dateString) {
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static String getDay(Date date) {
        return dayFormat.format(date);
    }

    public static String getMonthYear(Date date) {
        return monthYearFormat.format(date);
    }

    public static String getRequestDate(Date date) {
        return requestDateFormat.format(date);
    }
}
