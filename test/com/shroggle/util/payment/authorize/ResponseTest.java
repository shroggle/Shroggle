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
package com.shroggle.util.payment.authorize;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Balakirev Anatoliy
 */
public class ResponseTest {

    @Test
    public void testActivatePendingPaymentSettingsOwnerAPPROVED() {
        final Response response = new Response("1|1|1|This transaction has been approved.|V153VY|Y|2153317637|||29.99|CC|auth_capture||||||||||||||||||||||||||443B28F20702E3B14F3BA5BB7346BEC6||2||||||||||||||||||||||||||||", DELIMITER);
        Assert.assertEquals("This transaction has been approved.", response.getReasonText());
        Assert.assertEquals(ResponseCodes.APPROVED, response.getResponseCode());
        Assert.assertEquals("This transaction has been approved.", response.getDetailedReasonText());
    }

    @Test
    public void testActivatePendingPaymentSettingsOwnerDECLINE() {
        final Response response = new Response("2|1|1|This transaction has been approved.|V153VY|Y|2153317637|||29.99|CC|auth_capture||||||||||||||||||||||||||443B28F20702E3B14F3BA5BB7346BEC6||2||||||||||||||||||||||||||||", DELIMITER);
        Assert.assertEquals("This transaction has been approved.", response.getReasonText());
        Assert.assertEquals(ResponseCodes.DECLINE, response.getResponseCode());
        Assert.assertEquals("This transaction has been approved.", response.getDetailedReasonText());
    }

    @Test
    public void testActivatePendingPaymentSettingsOwnerERROR() {
        final Response response = new Response("3|1|1|This transaction has been approved.|V153VY|Y|2153317637|||29.99|CC|auth_capture||||||||||||||||||||||||||443B28F20702E3B14F3BA5BB7346BEC6||2||||||||||||||||||||||||||||", DELIMITER);
        Assert.assertEquals("This transaction has been approved.", response.getReasonText());
        Assert.assertEquals(ResponseCodes.ERROR, response.getResponseCode());
        Assert.assertEquals("This transaction has been approved.", response.getDetailedReasonText());
    }

    @Test
    public void testActivatePendingPaymentSettingsOwnerHELD_FOR_REVIEW() {
        final Response response = new Response("4|1|1|This transaction has been approved.|V153VY|Y|2153317637|||29.99|CC|auth_capture||||||||||||||||||||||||||443B28F20702E3B14F3BA5BB7346BEC6||2||||||||||||||||||||||||||||", DELIMITER);
        Assert.assertEquals("This transaction has been approved.", response.getReasonText());
        Assert.assertEquals(ResponseCodes.HELD_FOR_REVIEW, response.getResponseCode());
        Assert.assertEquals("This transaction has been approved.", response.getDetailedReasonText());
    }

    @Test
    public void testActivatePendingPaymentSettingsOwnerHELD_FOR_REVIEW_anotherMessage() {
        final Response response = new Response("4|1|24|This transaction has been approved.|V153VY|Y|2153317637|||29.99|CC|auth_capture||||||||||||||||||||||||||443B28F20702E3B14F3BA5BB7346BEC6||2||||||||||||||||||||||||||||", DELIMITER);
        Assert.assertEquals("This transaction has been approved.", response.getReasonText());
        Assert.assertEquals(ResponseCodes.HELD_FOR_REVIEW, response.getResponseCode());
        Assert.assertEquals("The Nova Bank Number or Terminal ID is incorrect. Call Merchant Service Provider.", response.getDetailedReasonText());
    }

    private final String DELIMITER = "|";
}
