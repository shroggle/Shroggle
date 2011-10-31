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
package com.shroggle.entity;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.shroggle.TestRunnerWithMockServices;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class SiteTest {

    @Test
    public void testCreate() {
        final Site site = new Site();
        Assert.assertNotNull(site.getSitePaymentSettings());
        Assert.assertNotNull(site.getSitePaymentSettings().getSiteStatus());
    }

    @Test
    public void getThemeId() {
        Assert.assertNotNull(new Site().getThemeId());
    }

    @Test
    public void getShowRight() {
        Assert.assertEquals(AccessForRender.UNLIMITED, new Site().getAccessibleSettings().getAccess());
    }

    @Test
    public void getType() {
        Assert.assertEquals(SiteType.COMMON, new Site().getType());
    }

}
