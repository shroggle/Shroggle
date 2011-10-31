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

import com.shroggle.PersistanceMock;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.Site;
import com.shroggle.entity.WorkChildSiteRegistration;
import com.shroggle.util.ServiceLocator;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class SiteByUrlGetterRealTest {

    @Before
    public void before() {
        ServiceLocator.getConfigStorage().get().setUserSitesUrl("web-deva.com:80");
    }

    @Test
    public void getWithNull() {
        Assert.assertNull(siteByUrlGetter.get(null));
    }

    @Test
    public void getWithNotFound() {
        Assert.assertNull(siteByUrlGetter.get("aaaa"));
    }

    @Test
    public void getWithFoundBySubDomain() {
        final Site site1 = TestUtil.createSite();
        site1.setSubDomain("aaaa");

        final Site site2 = TestUtil.createSite();
        site2.setSubDomain("111");

        Assert.assertEquals("Where is my site?", site1, siteByUrlGetter.get("aaaa.web-deva.com"));
    }

    @Test
    public void getWithFoundBySubDomainWithWww() {
        final Site site1 = TestUtil.createSite();
        site1.setSubDomain("aaaa");

        final Site site2 = TestUtil.createSite();
        site2.setSubDomain("111");

        Assert.assertEquals("Where is my site?", site1, siteByUrlGetter.get("www.aaaa.web-deva.com"));
    }

    @Test
    public void getWithFoundBySubDomainWithOtherCase() {
        final Site site1 = TestUtil.createSite();
        site1.setSubDomain("aaaa");

        final Site site2 = TestUtil.createSite();
        site2.setSubDomain("111");

        Assert.assertEquals("Where is my site?", site1, siteByUrlGetter.get("AaAa.web-deva.com"));
    }

    @Test
    public void getWithFoundBySubDomainWithNotOwnDomain() {
        final Site site1 = TestUtil.createSite();
        site1.setSubDomain("aaaa");

        final Site site2 = TestUtil.createSite();
        site2.setSubDomain("111");

        Assert.assertNull("Why i see site!", siteByUrlGetter.get("www.aaaa.google.com"));
    }

    @Test
    public void getWithFoundByCustomUrl() {
        final Site site1 = TestUtil.createSite();
        site1.setSubDomain("aaaa");
        site1.setCustomUrl("ggg.com");

        Assert.assertEquals("Where is my site?", site1, siteByUrlGetter.get("ggg.com"));
    }

    @Test
    public void getWithFoundByCustomUrlWithWww() {
        final Site site1 = TestUtil.createSite();
        site1.setSubDomain("aaaa");
        site1.setCustomUrl("ggg.com");

        Assert.assertEquals("Where is my site?", site1, siteByUrlGetter.get("www.ggg.com"));
    }

    @Test
    public void getWithFoundByCustomUrlAndSubDomain() {
        final Site site1 = TestUtil.createSite();
        site1.setSubDomain("aaaa");

        final Site site2 = TestUtil.createSite();
        site2.setCustomUrl("aaaa.web-deva.com");

        Assert.assertEquals("Sub domain has priority on custom url!",site1, siteByUrlGetter.get("aaaa.web-deva.com"));
    }

    @Test
    public void getWithFoundByBrandedSubDomain() {
        final WorkChildSiteRegistration workChildSiteRegistration = new WorkChildSiteRegistration();
        workChildSiteRegistration.setBrandedUrl("xxx.com");
        persistanceMock.putItem(workChildSiteRegistration);

        final Site site1 = TestUtil.createSite();
        site1.setBrandedSubDomain("aaaa");
        persistanceMock.putSiteByBrandedSubDomain("aaaa", "xxx.com", site1);

        Assert.assertEquals(site1, siteByUrlGetter.get("aaaa.xxx.com"));
    }

    @Test
    public void getWithFoundByBrandedSubDomainAndOtherNetworkDomain() {
        final Site site1 = TestUtil.createSite();
        site1.setBrandedSubDomain("aaaa");
        persistanceMock.putSiteByBrandedSubDomain("aaaa", "xxx.com", site1);

        Assert.assertNull(siteByUrlGetter.get("aaaa.ggg.com"));
    }

    @Test
    public void getWithFoundBySubDomainAndNetworkSubDomain() {
        final Site site1 = TestUtil.createSite();
        site1.setBrandedSubDomain("aaaa");
        persistanceMock.putSiteByBrandedSubDomain("aaaa", "web-deva.com", site1);

        final Site site2 = TestUtil.createSite();
        site2.setSubDomain("aaaa");

        Assert.assertEquals(site2, siteByUrlGetter.get("aaaa.web-deva.com"));
    }

    @Test
    public void getWithFoundByBrandedSubDomainAndCustomUrl() {
        final Site site1 = TestUtil.createSite();
        site1.setBrandedSubDomain("aaaa");
        persistanceMock.putSiteByBrandedSubDomain("aaaa", "web-deva.com", site1);

        final Site site2 = TestUtil.createSite();
        site2.setCustomUrl("aaaa.web-deva.com");

        Assert.assertEquals(site1, siteByUrlGetter.get("aaaa.web-deva.com"));
    }

    @Test
    public void getWithFoundByBrandedSubDomainWithDot() {
        final Site site1 = TestUtil.createSite();
        site1.setBrandedSubDomain("aaaa");
        persistanceMock.putSiteByBrandedSubDomain("aaaa", "xxx.com", site1);

        Assert.assertNull(siteByUrlGetter.get("aaaa."));
    }

    private final SiteByUrlGetter siteByUrlGetter = new SiteByUrlGetterReal();
    private final PersistanceMock persistanceMock = (PersistanceMock) ServiceLocator.getPersistance();

}
