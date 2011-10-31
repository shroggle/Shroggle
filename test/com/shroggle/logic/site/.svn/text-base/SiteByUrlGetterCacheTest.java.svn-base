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
package com.shroggle.logic.site;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.entity.Site;
import com.shroggle.util.ServiceLocator;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class SiteByUrlGetterCacheTest {

    @Test
    public void getNotFound() {
        final SiteByUrlGetterMock siteByUrlGetterMock = new SiteByUrlGetterMock();
        final SiteByUrlGetter siteByUrlGetter = new SiteByUrlGetterCache(siteByUrlGetterMock);
        Assert.assertNull(siteByUrlGetter.get("fff"));
    }

    @Test
    public void get() {
        final Site site = new Site();
        final SiteByUrlGetterMock siteByUrlGetterMock = new SiteByUrlGetterMock();
        siteByUrlGetterMock.setSite(site);
        final SiteByUrlGetter siteByUrlGetter = new SiteByUrlGetterCache(siteByUrlGetterMock);
        
        Assert.assertEquals(site, siteByUrlGetter.get("fff"));
        Assert.assertTrue(siteByUrlGetterMock.isCalled());
    }

    @Test
    public void getAfterChange() {
        final Site site = new Site();
        final SiteByUrlGetterMock siteByUrlGetterMock = new SiteByUrlGetterMock();
        siteByUrlGetterMock.setSite(site);

        final SiteByUrlGetter siteByUrlGetter = new SiteByUrlGetterCache(siteByUrlGetterMock);
        Assert.assertEquals(site, siteByUrlGetter.get("fff"));

        ServiceLocator.getPersistance().putSite(site);
        siteByUrlGetterMock.setSite(null);

        Assert.assertNull(siteByUrlGetter.get("fff"));
        Assert.assertTrue(siteByUrlGetterMock.isCalled());
    }

    @Test
    public void getAfterRemove() {
        final Site site = new Site();
        final SiteByUrlGetterMock siteByUrlGetterMock = new SiteByUrlGetterMock();
        siteByUrlGetterMock.setSite(site);

        final SiteByUrlGetter siteByUrlGetter = new SiteByUrlGetterCache(siteByUrlGetterMock);
        Assert.assertEquals(site, siteByUrlGetter.get("fff"));

        ServiceLocator.getPersistance().removeSite(site);
        siteByUrlGetterMock.setSite(null);

        Assert.assertNull(siteByUrlGetter.get("fff"));
        Assert.assertTrue(siteByUrlGetterMock.isCalled());
    }

    @Test
    public void getTwice() {
        final Site site = new Site();
        ServiceLocator.getPersistance().putSite(site);

        final SiteByUrlGetterMock siteByUrlGetterMock = new SiteByUrlGetterMock();
        siteByUrlGetterMock.setSite(site);

        final SiteByUrlGetter siteByUrlGetter = new SiteByUrlGetterCache(siteByUrlGetterMock);
        Assert.assertEquals(site, siteByUrlGetter.get("fff"));

        siteByUrlGetterMock.setSite(null);

        Assert.assertEquals(site, siteByUrlGetter.get("fff"));
        Assert.assertFalse(siteByUrlGetterMock.isCalled());
    }

}
