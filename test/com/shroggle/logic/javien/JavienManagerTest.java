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
package com.shroggle.logic.javien;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.ConfigJavien;
import com.shroggle.util.config.ConfigJavienData;
import junit.framework.Assert;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class JavienManagerTest {

    @Before
    public void before() {
        ConfigJavien configJavien = ServiceLocator.getConfigStorage().get().getJavien();
        Assert.assertNotNull(configJavien);

        ConfigJavienData configJavienDataTest = new ConfigJavienData();
        configJavienDataTest.setUrl("testUrl");
        configJavienDataTest.setAdminLogin("testLogin");
        configJavienDataTest.setAdminPassword("testPassword");
        configJavienDataTest.setMerchantName("testMerchantName");

        ConfigJavienData configJavienDataReal = new ConfigJavienData();
        configJavienDataReal.setUrl("realUrl");
        configJavienDataReal.setAdminLogin("realLogin");
        configJavienDataReal.setAdminPassword("realPassword");
        configJavienDataReal.setMerchantName("realMerchantName");

        configJavien.setRealData(configJavienDataReal);
        configJavien.setTestData(configJavienDataTest);
    }

    @Test
    public void testGetJavienUrl_useTestTrue() {
        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setUseTestPaymentSystem(true);
        Assert.assertEquals("testUrl", JavienManager.getJavienUrl());
    }

    @Test
    public void testGetJavienUrl_useTestFalse() {
        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setUseTestPaymentSystem(false);
        Assert.assertEquals("realUrl", JavienManager.getJavienUrl());
    }

    @Test
    public void testGetMerchantName_test() {
        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setUseTestPaymentSystem(true);
        Assert.assertEquals("testLogin", JavienManager.getAdminLogin());
    }

    @Test
    public void testGetAdminLogin_test() {
        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setUseTestPaymentSystem(true);
        Assert.assertEquals("testPassword", JavienManager.getAdminPassword());
    }

    @Test
    public void testGetAdminPassword_test() {
        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setUseTestPaymentSystem(true);
        Assert.assertEquals("testMerchantName", JavienManager.getMerchantName());
    }


    @Test
    public void testGetMerchantName_real() {
        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setUseTestPaymentSystem(false);
        Assert.assertEquals("realLogin", JavienManager.getAdminLogin());
    }

    @Test
    public void testGetAdminLogin_real() {
        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setUseTestPaymentSystem(false);
        Assert.assertEquals("realPassword", JavienManager.getAdminPassword());
    }

    @Test
    public void testGetAdminPassword_real() {
        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setUseTestPaymentSystem(false);
        Assert.assertEquals("realMerchantName", JavienManager.getMerchantName());
    }
}
