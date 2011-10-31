/*********************************************************************
 *                                                                   *
 * Copyright (c) 2007-2011 by Web-Deva.                              *
 * All rights reserved.                                              *
 *                                                                   *
 * This computer program is protected by copyright law and           *
 * international treaties. Unauthorized reproduction or distribution *
 * of this program, or any portion of it, may result in severe civil *
 * and criminal penalties, and will be prosecuted to the maximum     *
 * extent possible under the law.                                    *
 *                                                                   *
 *********************************************************************/

package com.shroggle.util;

import com.shroggle.exception.UnknownMonthException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {


    private final static DateFormat SHORTEST_DATE_FORMAT = new SimpleDateFormat("MM/dd/yy", Locale.US);
    private final static DateFormat SHORT_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
    private final static DateFormat MONTH_YEAR_FORMAT = new SimpleDateFormat("MMMM yyyy", Locale.US);
    private final static DateFormat MONTH_FORMAT = new SimpleDateFormat("MMMM", Locale.US);
    private final static DateFormat JAVIEN_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private final static DateFormat AUTHORIZE_NET_FORMAT = new SimpleDateFormat("MM/yyyy", Locale.US);
    private final static DateFormat COMMON_DATETIME_FORMAT = new SimpleDateFormat("MMM d yyyy HH:mm ", Locale.US);
    private final static DateFormat DATETIME_FORMAT_WITH_SEPARATORS = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
    private final static DateFormat BLOG_AND_BLOG_SUMMARY_FORMAT = new SimpleDateFormat("MM/dd/yy h:mm aaa", Locale.US);

    /**
     * Round given date to Calendars field inclusive. For example:
     * DateUtil.roundDateTo(date, Calendar.YEAR) -
     * with "date" = 2010-12-27 10:58:23:123 - return current 2010-01-01 0:0:0:0;
     * <p/>
     * DateUtil.roundDateTo(date, Calendar.MONTH) -
     * with "date" = 2010-12-27 10:58:23:123 - return current 2010-12-01 0:0:0:0
     * <p/>
     * DateUtil.roundDateTo(date, Calendar.SECOND) -
     * with "date" = 2010-12-27 10:58:23:123 - return current 2010-01-01 10:58:23:0
     *
     * @param oldDate - Date for rounding
     * @param roundTo - Calendar field to which date should be rounded (inclusive)
     * @return - rounded Date
     */
    public static Date roundDateTo(final Date oldDate, final int roundTo) {
        final Calendar date = Calendar.getInstance();
        date.setTime(oldDate);

        final Calendar roundedDate;
        switch (roundTo) {
            case Calendar.YEAR: {
                roundedDate = new GregorianCalendar(date.get(Calendar.YEAR), date.getMinimum(Calendar.MONTH), date.getMinimum(Calendar.DAY_OF_MONTH));
                break;
            }
            case Calendar.MONTH: {
                roundedDate = new GregorianCalendar(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.getMinimum(Calendar.DAY_OF_MONTH));
                break;
            }
            default: {
                roundedDate = new GregorianCalendar(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
                break;
            }
        }
        switch (roundTo) {
            case Calendar.HOUR: {
                roundedDate.set(Calendar.HOUR, date.get(Calendar.HOUR));
                break;
            }
            case Calendar.MINUTE: {
                roundedDate.set(Calendar.HOUR, date.get(Calendar.HOUR));
                roundedDate.set(Calendar.MINUTE, date.get(Calendar.MINUTE));
                break;
            }
            case Calendar.SECOND: {
                roundedDate.set(Calendar.HOUR, date.get(Calendar.HOUR));
                roundedDate.set(Calendar.MINUTE, date.get(Calendar.MINUTE));
                roundedDate.set(Calendar.SECOND, date.get(Calendar.SECOND));
                break;
            }
            case Calendar.MILLISECOND: {
                roundedDate.set(Calendar.HOUR, date.get(Calendar.HOUR));
                roundedDate.set(Calendar.MINUTE, date.get(Calendar.MINUTE));
                roundedDate.set(Calendar.SECOND, date.get(Calendar.SECOND));
                roundedDate.set(Calendar.MILLISECOND, date.get(Calendar.MILLISECOND));
                break;
            }
        }
        return roundedDate.getTime();
    }


    /**
     * @param currentTime - Current time
     * @return - Time to midnight in milliseconds
     */
    public static long getMillisToMidnight(long currentTime) {
        final Calendar tomorrowMidnight = Calendar.getInstance();
        tomorrowMidnight.setTime(roundDateTo(new Date(currentTime), Calendar.DAY_OF_MONTH));
        tomorrowMidnight.add(Calendar.DAY_OF_MONTH, 1);

        return tomorrowMidnight.getTimeInMillis() - currentTime;
    }

    public static String getDateForBlogAndBlogSummary(final Date date) {
        return BLOG_AND_BLOG_SUMMARY_FORMAT.format(date);
    }

    public static String getMonth(final Calendar calendar) {
        return MONTH_FORMAT.format(calendar.getTime());
    }

    public static long minutesToMilliseconds(final long minutes) {
        return minutes * 60 * 1000L;
    }

    public static String toMonthAndYear(final int year, int month) {
        final Calendar calendar = new GregorianCalendar(year, ++month, 0);
        return MONTH_YEAR_FORMAT.format(calendar.getTime());
    }

    public static int toDays(final long millis) {
        return (int) (millis / 1000L / 60 / 60 / 24);
    }

    public static String toMonthDayAndYear(Date time) {
        return SHORT_DATE_FORMAT.format(time);
    }

    public static Date getByMonthDayAndYear(String time) {
        try {
            return SHORT_DATE_FORMAT.parse(time);
        } catch (Exception e) {
            return null;
        }
    }

    public static String toDayMonthAndShortYear(Date time) {
        return SHORTEST_DATE_FORMAT.format(time);
    }

    public static String toCommonDateStr(Date time) {
        return COMMON_DATETIME_FORMAT.format(time);
    }

    public static String toDateStrWithSeparators(Date time) {
        return DATETIME_FORMAT_WITH_SEPARATORS.format(time);
    }

    public static Date getDateByString(final String date) {
        Calendar calendar;
        String[] dateArray = date.trim().split("/");
        if (dateArray.length != 3) {
            dateArray = date.trim().split(" ");
        }
        if (dateArray.length != 3) {
            return null;
        }
        int year, month, day;
        try {
            month = Integer.parseInt(dateArray[0].trim());
            day = Integer.parseInt(dateArray[1].trim());
            year = Integer.parseInt(dateArray[2].trim());
        } catch (Exception e) {
            return null;
        }
        calendar = new GregorianCalendar(year, month - 1, day);
        return calendar.getTime();
    }

    public static String getJavienDateFormat(final Date date) {
        return JAVIEN_FORMAT.format(date);
    }

    public static String getAuthorizeNetDateFormat(final Date date) {
        return AUTHORIZE_NET_FORMAT.format(date);
    }


    public static Date subtractMonthsFromDate(final Date date, final int monthsCount) {
        if (date == null) {
            throw new IllegalArgumentException("Date can`t be null!");
        }
        final Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, (calendar.get(Calendar.MONTH) - monthsCount));
        return calendar.getTime();
    }

    //String in format MMMMM/dd/yyyy hh:mm

    public static Date constructDateByString(final String s) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("MMMMM/dd/yyyy hh:mm", Locale.US);
        try {
            return dateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date constructDate(String hours, String minutes) {
        final String dateString = constructDateString(hours, minutes, false);
        return constructDateByString(dateString);
    }

    public static Date constructDate(String month, String day, String year, String hours, String minutes) {
        final String dateString = constructDateString(month, day, year, hours, minutes, false);
        return constructDateByString(dateString);
    }

    public static Date constructDate(String month, String day, String year) {
        final String dateString = constructDateString(month, day, year, false);
        return constructDateByString(dateString);
    }

    public static String constructDateString(String month, String day, String year, String hours, String minutes, final boolean fillWithDashes) {
        day = StringUtil.isNullOrEmpty(day) ? (fillWithDashes ? "&ndash;" : "1") : day;
        month = StringUtil.isNullOrEmpty(month) ? (fillWithDashes ? "&ndash;" : "January") :
                (isMonthInteger(month) ? convertFromIntegerMonthToString(month) : month);
        year = StringUtil.isNullOrEmpty(year) ? (fillWithDashes ? "&ndash;" : "1900") : year;
        hours = StringUtil.isNullOrEmpty(hours) ? (fillWithDashes ? "&ndash;" : "00") : hours;
        minutes = StringUtil.isNullOrEmpty(minutes) ? (fillWithDashes ? "&ndash;" : "00") : minutes;

        return month + "/" + day + "/" + year + " " + fixDateTime(hours) + ":" + fixDateTime(minutes) + "";
    }

    public static String constructDateString(String month, String day, String year, final boolean fillWithDashes) {
        day = StringUtil.isNullOrEmpty(day) ? (fillWithDashes ? "&ndash;" : "1") : day;
        month = StringUtil.isNullOrEmpty(month) ? (fillWithDashes ? "&ndash;" : "January") :
                (isMonthInteger(month) ? convertFromIntegerMonthToString(month) : month);
        year = StringUtil.isNullOrEmpty(year) ? (fillWithDashes ? "&ndash;" : "1900") : year;

        return month + "/" + day + "/" + year + (fillWithDashes ? "" : " 00:00");
    }

    public static String constructDateString(String hours, String minutes, final boolean fillWithDashes) {
        hours = StringUtil.isNullOrEmpty(hours) ? (fillWithDashes ? "&ndash;" : "00") : hours;
        minutes = StringUtil.isNullOrEmpty(minutes) ? (fillWithDashes ? "&ndash;" : "00") : minutes;

        return (fillWithDashes ? "" : "January/1/1900 ") + fixDateTime(hours) + ":" + fixDateTime(minutes) + "";
    }

    public static String fixDateTime(String timePart) {
        if (timePart != null) {
            if (timePart.length() == 1) {
                return "0" + timePart;
            } else {
                return timePart;
            }
        } else {
            return "";
        }
    }

    public static boolean isMonthInteger(final String intMonth) {
        try {
            final int parsedMonth = Integer.parseInt(intMonth);
            return parsedMonth >= 1 && parsedMonth <= 12;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static String convertFromIntegerMonthToString(final String intMonth) {
        if (intMonth.equals("01") || intMonth.equals("1")) {
            return "January";
        } else if (intMonth.equals("02") || intMonth.equals("2")) {
            return "February";
        } else if (intMonth.equals("03") || intMonth.equals("3")) {
            return "March";
        } else if (intMonth.equals("04") || intMonth.equals("4")) {
            return "April";
        } else if (intMonth.equals("05") || intMonth.equals("5")) {
            return "May";
        } else if (intMonth.equals("06") || intMonth.equals("6")) {
            return "June";
        } else if (intMonth.equals("07") || intMonth.equals("7")) {
            return "July";
        } else if (intMonth.equals("08") || intMonth.equals("8")) {
            return "August";
        } else if (intMonth.equals("09") || intMonth.equals("9")) {
            return "September";
        } else if (intMonth.equals("10")) {
            return "October";
        } else if (intMonth.equals("11")) {
            return "November";
        } else if (intMonth.equals("12")) {
            return "December";
        }

        throw new UnknownMonthException();
    }

    private DateUtil() {
    }
}