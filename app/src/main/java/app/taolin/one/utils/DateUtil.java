package app.taolin.one.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Taolin on 16/5/27.
 */

public class DateUtil {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private static final SimpleDateFormat displayDateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
    private static final SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.US);
    private static final SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMM, yyyy", Locale.US);

    private static final long DAY = 24 * 3600 * 1000;

    public static String getDateString(int diffDays) {
        final long milliseconds = System.currentTimeMillis() - diffDays * DAY;
        return dateFormat.format(new Date(milliseconds));
    }

    public static String getDateString() {
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
}
