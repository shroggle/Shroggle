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
package com.shroggle.logic.childSites;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Balakirev Anatoliy
 */
public class ExpirationDateLogicTest {

    @Test
    public void testIsNetworkMembershipExpired_forEndDateEqualsCurrent() throws Exception {
        final Calendar currentDate = new GregorianCalendar(2010, 1, 10);
        final Calendar endDate = new GregorianCalendar(2010, 1, 10);
        Assert.assertEquals(false, ExpirationDateLogic.isNetworkMembershipExpired(currentDate.getTime(), endDate.getTime()));
    }

    @Test
    public void testIsNetworkMembershipExpired_forEndDateEqualsCurrentPlusOneDay() throws Exception {
        final Calendar currentDate = new GregorianCalendar(2010, 1, 10);
        final Calendar endDate = new GregorianCalendar(2010, 1, 11);
        Assert.assertEquals(false, ExpirationDateLogic.isNetworkMembershipExpired(currentDate.getTime(), endDate.getTime()));
    }

    @Test
    public void testIsNetworkMembershipExpired_forEndDateEqualsCurrentMinusOneDay() throws Exception {
        final Calendar currentDate = new GregorianCalendar(2010, 1, 10);
        final Calendar endDate = new GregorianCalendar(2010, 1, 9);
        Assert.assertEquals(true, ExpirationDateLogic.isNetworkMembershipExpired(currentDate.getTime(), endDate.getTime()));
    }

}
