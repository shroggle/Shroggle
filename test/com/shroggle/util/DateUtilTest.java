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
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class DateUtilTest {

    @Test
    public void testRoundDateTo_YEAR() throws Exception {
        final Calendar calendar = new GregorianCalendar(2010, 11, 27);// January 27th 2010
        //10:58:23:123
        calendar.set(Calendar.HOUR, 10);
        calendar.set(Calendar.MINUTE, 58);
        calendar.set(Calendar.SECOND, 23);
        calendar.set(Calendar.MILLISECOND, 123);


        final Date tempRoundedDate = DateUtil.roundDateTo(calendar.getTime(), Calendar.YEAR);
        final Calendar roundedDate = Calendar.getInstance();
        roundedDate.setTime(tempRoundedDate);

        Assert.assertEquals(2010, roundedDate.get(Calendar.YEAR));
        Assert.assertEquals(0, roundedDate.get(Calendar.MONTH));
        Assert.assertEquals(1, roundedDate.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(0, roundedDate.get(Calendar.HOUR));
        Assert.assertEquals(0, roundedDate.get(Calendar.MINUTE));
        Assert.assertEquals(0, roundedDate.get(Calendar.SECOND));
        Assert.assertEquals(0, roundedDate.get(Calendar.MILLISECOND));
    }

    @Test
    public void testRoundDateTo_MONTH() throws Exception {
        final Calendar calendar = new GregorianCalendar(2010, 11, 27);// January 27th 2010
        //10:58:23:123
        calendar.set(Calendar.HOUR, 10);
        calendar.set(Calendar.MINUTE, 58);
        calendar.set(Calendar.SECOND, 23);
        calendar.set(Calendar.MILLISECOND, 123);


        final Date tempRoundedDate = DateUtil.roundDateTo(calendar.getTime(), Calendar.MONTH);
        final Calendar roundedDate = Calendar.getInstance();
        roundedDate.setTime(tempRoundedDate);

        Assert.assertEquals(2010, roundedDate.get(Calendar.YEAR));
        Assert.assertEquals(11, roundedDate.get(Calendar.MONTH));
        Assert.assertEquals(1, roundedDate.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(0, roundedDate.get(Calendar.HOUR));
        Assert.assertEquals(0, roundedDate.get(Calendar.MINUTE));
        Assert.assertEquals(0, roundedDate.get(Calendar.SECOND));
        Assert.assertEquals(0, roundedDate.get(Calendar.MILLISECOND));
    }

    @Test
    public void testRoundDateTo_SECOND() throws Exception {
        final Calendar calendar = new GregorianCalendar(2010, 11, 27);// January 27th 2010
        //10:58:23:123
        calendar.set(Calendar.HOUR, 10);
        calendar.set(Calendar.MINUTE, 58);
        calendar.set(Calendar.SECOND, 23);
        calendar.set(Calendar.MILLISECOND, 123);


        final Date tempRoundedDate = DateUtil.roundDateTo(calendar.getTime(), Calendar.SECOND);
        final Calendar roundedDate = Calendar.getInstance();
        roundedDate.setTime(tempRoundedDate);

        Assert.assertEquals(2010, roundedDate.get(Calendar.YEAR));
        Assert.assertEquals(11, roundedDate.get(Calendar.MONTH));
        Assert.assertEquals(27, roundedDate.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(10, roundedDate.get(Calendar.HOUR));
        Assert.assertEquals(58, roundedDate.get(Calendar.MINUTE));
        Assert.assertEquals(23, roundedDate.get(Calendar.SECOND));
        Assert.assertEquals(0, roundedDate.get(Calendar.MILLISECOND));
    }

    @Test
    public void getMillisToMidnight() {
        Calendar testDate = new GregorianCalendar(2009, 4, 7, 22, 10, 10);
        long atFourAM = DateUtil.getMillisToMidnight(testDate.getTimeInMillis());
        Assert.assertEquals((60 * 60 + 49 * 60 + 50) * 1000, atFourAM); // one hour, 49 minutes and 50 sec.
    }

    @Test
    public void getMillisToMidnight_oneHour() {
        Calendar testDate = new GregorianCalendar(2009, 4, 7, 3, 10, 10);
        long atFourAM = DateUtil.getMillisToMidnight(testDate.getTimeInMillis());
        Assert.assertEquals((20 * 60 * 60 * 1000L) + (49 * 60 + 50) * 1000L, atFourAM); // 20 hours 49 minutes and 50 sec.
    }

    @Test
    public void getMillisToMidnight_twoHours() {
        Calendar testDate = new GregorianCalendar(2009, 4, 7, 22, 0, 0);
        long atFourAM = DateUtil.getMillisToMidnight(testDate.getTimeInMillis());
        Assert.assertEquals(2 * 3600 * 1000L, atFourAM); // two hours
    }

    @Test
    public void getMillisToMidnight_midnight() {
        Calendar testDate = new GregorianCalendar(2009, 4, 7, 0, 0, 0);
        long atFourAM = DateUtil.getMillisToMidnight(testDate.getTimeInMillis());
        Assert.assertEquals(24 * 3600 * 1000L, atFourAM); // 24 hours
    }

    @Test
    public void getMillisToMidnight_fourAMandOneMinute() {
        Calendar testDate = new GregorianCalendar(2009, 4, 7, 0, 1, 0);
        long atFourAM = DateUtil.getMillisToMidnight(testDate.getTimeInMillis());
        Assert.assertEquals(23 * 60 * 60 * 1000L + 59 * 60 * 1000L, atFourAM); // almost one day
    }

    @Test
    public void getMonth_calendar() {
        Assert.assertEquals("January", DateUtil.getMonth(new GregorianCalendar(2009, 0, 1)));
        Assert.assertEquals("February", DateUtil.getMonth(new GregorianCalendar(2009, 1, 1)));
        Assert.assertEquals("March", DateUtil.getMonth(new GregorianCalendar(2009, 2, 1)));
        Assert.assertEquals("April", DateUtil.getMonth(new GregorianCalendar(2009, 3, 1)));
        Assert.assertEquals("May", DateUtil.getMonth(new GregorianCalendar(2009, 4, 1)));
        Assert.assertEquals("June", DateUtil.getMonth(new GregorianCalendar(2009, 5, 1)));
        Assert.assertEquals("July", DateUtil.getMonth(new GregorianCalendar(2009, 6, 1)));
        Assert.assertEquals("August", DateUtil.getMonth(new GregorianCalendar(2009, 7, 1)));
        Assert.assertEquals("September", DateUtil.getMonth(new GregorianCalendar(2009, 8, 1)));
        Assert.assertEquals("October", DateUtil.getMonth(new GregorianCalendar(2009, 9, 1)));
        Assert.assertEquals("November", DateUtil.getMonth(new GregorianCalendar(2009, 10, 1)));
        Assert.assertEquals("December", DateUtil.getMonth(new GregorianCalendar(2009, 11, 1)));
    }

    @Test
    public void getDateByString() throws Exception {
        Calendar calendar = new GregorianCalendar();
        calendar.set(2001, 0, 12);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        //Date format = "MM/dd/yyyy"
        Date date1 = DateUtil.getDateByString("01 12 2001");
        Date date2 = DateUtil.getDateByString("01/12/2001");
        Date date3 = DateUtil.getDateByString("12/32/2001");
        Date date4 = DateUtil.getDateByString("01/01/2002");
        Assert.assertEquals(date1, date2);
        Assert.assertEquals(date3, date4);
        Assert.assertEquals(calendar.getTime(), date1);
        Assert.assertEquals(calendar.getTime(), date2);
        Assert.assertNull(DateUtil.getDateByString("01 12 210ad0"));
    }


    @Test
    public void twentyFourHours() {
        Assert.assertEquals((24 * 60 * 60 * 1000L), TimeInterval.ONE_DAY.getMillis());
    }

    @Test
    public void test_ONE_DAY() {
        Assert.assertEquals((24 * 60 * 60 * 1000L), TimeInterval.ONE_DAY.getMillis());
    }

    @Test
    public void test_TWO_DAYS() {
        Assert.assertEquals((2 * 24 * 60 * 60 * 1000L), TimeInterval.TWO_DAYS.getMillis());
    }

    @Test
    public void test_THREE_DAYS() {
        Assert.assertEquals((3 * 24 * 60 * 60 * 1000L), TimeInterval.THREE_DAYS.getMillis());
    }

    @Test
    public void test_FIVE_DAYS() {
        Assert.assertEquals((5 * 24 * 60 * 60 * 1000L), TimeInterval.FIVE_DAYS.getMillis());
    }

    @Test
    public void test_ONE_WEEK() {
        Assert.assertEquals((7 * 24 * 60 * 60 * 1000L), TimeInterval.ONE_WEEK.getMillis());
    }

    @Test
    public void test_TEN_DAYS() {
        Assert.assertEquals((10 * 24 * 60 * 60 * 1000L), TimeInterval.TEN_DAYS.getMillis());
    }

    @Test
    public void test_TWO_WEEKS() {
        Assert.assertEquals((14 * 24 * 60 * 60 * 1000L), TimeInterval.TWO_WEEKS.getMillis());
    }

    @Test
    public void test_TWENTY_DAYS() {
        Assert.assertEquals((20 * 24 * 60 * 60 * 1000L), TimeInterval.TWENTY_DAYS.getMillis());
    }

    @Test
    public void test_THREE_WEEKS() {
        Assert.assertEquals((21 * 24 * 60 * 60 * 1000L), TimeInterval.THREE_WEEKS.getMillis());
    }

    @Test
    public void test_FOUR_WEEKS() {
        Assert.assertEquals((28 * 24 * 60 * 60 * 1000L), TimeInterval.FOUR_WEEKS.getMillis());
    }

    @Test
    public void test_ONE_MONTH() {
        Assert.assertEquals((30 * 24 * 60 * 60 * 1000L), TimeInterval.ONE_MONTH.getMillis());
    }

    @Test
    public void test_TWO_MONTHS() {
        Assert.assertEquals((2 * 30 * 24 * 60 * 60 * 1000L), TimeInterval.TWO_MONTHS.getMillis());
    }

    @Test
    public void test_THREE_MONTHS() {
        Assert.assertEquals((3 * 30 * 24 * 60 * 60 * 1000L), TimeInterval.THREE_MONTHS.getMillis());
    }

    @Test
    public void test_SIX_MONTHS() {
        Assert.assertEquals((6 * 30 * 24 * 60 * 60 * 1000L), TimeInterval.SIX_MONTHS.getMillis());
    }

    @Test
    public void test_ONE_YEAR() {
        Assert.assertEquals((365 * 24 * 60 * 60 * 1000L), TimeInterval.ONE_YEAR.getMillis());
    }

    @Test
    public void minutesToMilliseconds() {
        final int MINUTES = 100;
        Assert.assertEquals((MINUTES * 60 * 1000L), DateUtil.minutesToMilliseconds(MINUTES));
    }

    @Test
    public void minutesToMilliseconds_big() {
        final int MINUTES = 43200;
        Assert.assertEquals((MINUTES * 60 * 1000L), DateUtil.minutesToMilliseconds(MINUTES));
    }

    @Test
    public void toMonthAndYear() {
        String date1;

        date1 = DateUtil.toMonthAndYear(2001, 0);
        Assert.assertEquals("January 2001", date1);

        date1 = DateUtil.toMonthAndYear(2010, 11);
        Assert.assertEquals("December 2010", date1);

        date1 = DateUtil.toMonthAndYear(2010, 12);
        Assert.assertEquals("January 2011", date1);
    }

    @Test
    public void toDays() {
        long date1;

        date1 = DateUtil.toDays((30 * 24 * 60 * 60 * 1000L));
        Assert.assertEquals(30, date1);

        date1 = DateUtil.toDays((24 * 60 * 60 * 1000L));
        Assert.assertEquals(1, date1);

        date1 = DateUtil.toDays((60 * 60 * 1000L));
        Assert.assertEquals(0, date1);
    }

    @Test
    public void testGetJavienDateFormat() {
        Calendar calendar = new GregorianCalendar();
        calendar.set(2001, 0, 12);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Assert.assertEquals("2001-01-12", DateUtil.getJavienDateFormat(calendar.getTime()));
    }

    @Test
    public void testCreateDateWithZeroHoursMinutesSecondsAndMillis_Date() {
        Calendar calendar = new GregorianCalendar();
        calendar.set(2001, 10, 12);
        calendar.set(Calendar.HOUR_OF_DAY, 5);
        calendar.set(Calendar.MINUTE, 54);
        calendar.set(Calendar.SECOND, 27);
        calendar.set(Calendar.MILLISECOND, 234);
        Date newDate = DateUtil.roundDateTo(calendar.getTime(), Calendar.DAY_OF_MONTH);
        Calendar newCalendar = new GregorianCalendar();
        newCalendar.setTime(newDate);
        Assert.assertEquals(2001, newCalendar.get(Calendar.YEAR));
        Assert.assertEquals(10, newCalendar.get(Calendar.MONTH));
        Assert.assertEquals(12, newCalendar.get(Calendar.DAY_OF_MONTH));

        Assert.assertEquals(0, newCalendar.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(0, newCalendar.get(Calendar.MINUTE));
        Assert.assertEquals(0, newCalendar.get(Calendar.SECOND));
        Assert.assertEquals(0, newCalendar.get(Calendar.MILLISECOND));
    }

    @Test
    public void constructDateByString_rightDate() {
        final String dateString = "March/15/1988 15:56";
        final Date date = DateUtil.constructDateByString(dateString);
        final Calendar c = new GregorianCalendar();
        c.setTime(date);

        Assert.assertNotNull(date);
        Assert.assertEquals(Calendar.MARCH, c.get(Calendar.MONTH));
        Assert.assertEquals(15, c.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(1988, c.get(Calendar.YEAR));
        Assert.assertEquals(15, c.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(56, c.get(Calendar.MINUTE));
    }

    @Test
    public void constructDateByString_badDate() {
        final String dateString = "Ahuenniy_mesyac/15/1988 15:56";
        final Date date = DateUtil.constructDateByString(dateString);

        Assert.assertNull(date);
    }

    @Test
    public void constructDate_HH_MM_withRightParameters() {
        final Date date = DateUtil.constructDate("15", "56");
        final Calendar c = new GregorianCalendar();
        c.setTime(date);

        Assert.assertNotNull(date);
        Assert.assertEquals(Calendar.JANUARY, c.get(Calendar.MONTH));
        Assert.assertEquals(1, c.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(1900, c.get(Calendar.YEAR));
        Assert.assertEquals(15, c.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(56, c.get(Calendar.MINUTE));
    }

    @Test
    public void constructDate_HH_MM_withEmptyParameters() {
        final Date date = DateUtil.constructDate("", "");
        final Calendar c = new GregorianCalendar();
        c.setTime(date);

        Assert.assertNotNull(date);
        Assert.assertEquals(Calendar.JANUARY, c.get(Calendar.MONTH));
        Assert.assertEquals(1, c.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(1900, c.get(Calendar.YEAR));
        Assert.assertEquals(0, c.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(0, c.get(Calendar.MINUTE));
    }

    @Test
    public void constructDate_DD_MM_YYYY_withRightParameters() {
        final Date date = DateUtil.constructDate("December", "17", "1988");
        final Calendar c = new GregorianCalendar();
        c.setTime(date);

        Assert.assertNotNull(date);
        Assert.assertEquals(Calendar.DECEMBER, c.get(Calendar.MONTH));
        Assert.assertEquals(17, c.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(1988, c.get(Calendar.YEAR));
        Assert.assertEquals(0, c.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(0, c.get(Calendar.MINUTE));
    }

    @Test
    public void constructDate_DD_MM_YYYY_withEmptyParameters() {
        final Date date = DateUtil.constructDate("", "", "");
        final Calendar c = new GregorianCalendar();
        c.setTime(date);

        Assert.assertNotNull(date);
        Assert.assertEquals(Calendar.JANUARY, c.get(Calendar.MONTH));
        Assert.assertEquals(1, c.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(1900, c.get(Calendar.YEAR));
        Assert.assertEquals(0, c.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(0, c.get(Calendar.MINUTE));
    }

    @Test
    public void constructDate_DD_MM_YYYY_HH_MM_withRightParameters() {
        final Date date = DateUtil.constructDate("December", "17", "1988", "15", "56");
        final Calendar c = new GregorianCalendar();
        c.setTime(date);

        Assert.assertNotNull(date);
        Assert.assertEquals(Calendar.DECEMBER, c.get(Calendar.MONTH));
        Assert.assertEquals(17, c.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(1988, c.get(Calendar.YEAR));
        Assert.assertEquals(15, c.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(56, c.get(Calendar.MINUTE));
    }

    @Test
    public void constructDate_DD_MM_YYYY_HH_MM_withEmptyParameters() {
        final Date date = DateUtil.constructDate("", "", "", "", "");
        final Calendar c = new GregorianCalendar();
        c.setTime(date);

        Assert.assertNotNull(date);
        Assert.assertEquals(Calendar.JANUARY, c.get(Calendar.MONTH));
        Assert.assertEquals(1, c.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(1900, c.get(Calendar.YEAR));
        Assert.assertEquals(0, c.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(0, c.get(Calendar.MINUTE));
    }

    @Test
    public void constructDateString_DD_MM_YYYY() {
        String month = "12";
        String day = "17";
        String year = "1988";

        Assert.assertEquals("December/17/1988 00:00", DateUtil.constructDateString(month, day, year, false));
        Assert.assertEquals("&ndash;/17/1988", DateUtil.constructDateString("", day, year, true));
        Assert.assertEquals("January/17/1988 00:00", DateUtil.constructDateString("", day, year, false));
    }

    @Test
    public void constructDateString_DD_MM_YYYY_HH_MM() {
        String month = "12";
        String day = "17";
        String year = "1988";
        String hours = "15";
        String minutes = "35";

        Assert.assertEquals("December/17/1988 15:35", DateUtil.constructDateString(month, day, year, hours, minutes, false));
        Assert.assertEquals("&ndash;/17/1988 &ndash;:&ndash;", DateUtil.constructDateString("", day, year, "", "", true));
        Assert.assertEquals("January/17/1988 00:00", DateUtil.constructDateString("", day, year, "", "", false));
    }

    @Test
    public void constructDateString_HH_MM() {
        String hours = "15";
        String minutes = "35";

        Assert.assertEquals("January/1/1900 15:35", DateUtil.constructDateString(hours, minutes, false));
        Assert.assertEquals("&ndash;:&ndash;", DateUtil.constructDateString("", "", true));
        Assert.assertEquals("15:35", DateUtil.constructDateString(hours, minutes, true));
        Assert.assertEquals("January/1/1900 00:00", DateUtil.constructDateString("", "", false));
    }

    @Test
    public void testIsMonthInteger() {
        Assert.assertFalse(DateUtil.isMonthInteger("test"));
        Assert.assertFalse(DateUtil.isMonthInteger("13"));
        Assert.assertFalse(DateUtil.isMonthInteger("0"));
        Assert.assertTrue(DateUtil.isMonthInteger("1"));
        Assert.assertTrue(DateUtil.isMonthInteger("12"));
        Assert.assertTrue(DateUtil.isMonthInteger("03"));
    }

    @Test
    public void testConvertFromIntegerMonthToString() {
        Assert.assertEquals("January", DateUtil.convertFromIntegerMonthToString("01"));
        Assert.assertEquals("January", DateUtil.convertFromIntegerMonthToString("1"));
        Assert.assertEquals("February", DateUtil.convertFromIntegerMonthToString("02"));
        Assert.assertEquals("February", DateUtil.convertFromIntegerMonthToString("2"));
        Assert.assertEquals("March", DateUtil.convertFromIntegerMonthToString("03"));
        Assert.assertEquals("March", DateUtil.convertFromIntegerMonthToString("3"));
        Assert.assertEquals("April", DateUtil.convertFromIntegerMonthToString("04"));
        Assert.assertEquals("April", DateUtil.convertFromIntegerMonthToString("4"));
        Assert.assertEquals("May", DateUtil.convertFromIntegerMonthToString("05"));
        Assert.assertEquals("May", DateUtil.convertFromIntegerMonthToString("5"));
        Assert.assertEquals("June", DateUtil.convertFromIntegerMonthToString("06"));
        Assert.assertEquals("June", DateUtil.convertFromIntegerMonthToString("6"));
        Assert.assertEquals("July", DateUtil.convertFromIntegerMonthToString("07"));
        Assert.assertEquals("July", DateUtil.convertFromIntegerMonthToString("7"));
        Assert.assertEquals("August", DateUtil.convertFromIntegerMonthToString("08"));
        Assert.assertEquals("August", DateUtil.convertFromIntegerMonthToString("8"));
        Assert.assertEquals("September", DateUtil.convertFromIntegerMonthToString("09"));
        Assert.assertEquals("September", DateUtil.convertFromIntegerMonthToString("9"));
        Assert.assertEquals("October", DateUtil.convertFromIntegerMonthToString("10"));
        Assert.assertEquals("November", DateUtil.convertFromIntegerMonthToString("11"));
        Assert.assertEquals("December", DateUtil.convertFromIntegerMonthToString("12"));
    }

    @Test(expected = UnknownMonthException.class)
    public void testConvertFromIntegerMonthToString_withNonExistingMonth() {
        Assert.assertEquals("December", DateUtil.convertFromIntegerMonthToString("13"));
    }

    @Test
    public void testGetDateStartsFormCurrentMinusMonthCount() {
        final Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.YEAR, 2010);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        final Date currentDateMinusOneMonth = DateUtil.subtractMonthsFromDate(calendar.getTime(), 1);
        final Calendar newCalendar = new GregorianCalendar();
        newCalendar.setTime(currentDateMinusOneMonth);

        Assert.assertEquals(2009, newCalendar.get(Calendar.YEAR));
        Assert.assertEquals(11, newCalendar.get(Calendar.MONTH));
    }

    @Test
    public void testGetDateStartsFormCurrentMinusMonthCount_withNegativeMonthCount() {
        final Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.YEAR, 2010);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        final Date currentDateMinusOneMonth = DateUtil.subtractMonthsFromDate(calendar.getTime(), -1);
        final Calendar newCalendar = new GregorianCalendar();
        newCalendar.setTime(currentDateMinusOneMonth);

        Assert.assertEquals(2010, newCalendar.get(Calendar.YEAR));
        Assert.assertEquals(1, newCalendar.get(Calendar.MONTH));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetDateStartsFormCurrentMinusMonthCount_withoutDate() {
        DateUtil.subtractMonthsFromDate(null, 1);
    }


    @Test
    public void testGetByDateMonthAndYear() throws Exception {
        final Date date = DateUtil.getByMonthDayAndYear("04/05/2010");

        Assert.assertEquals(date, new GregorianCalendar(2010, Calendar.APRIL, 5).getTime());
    }
}