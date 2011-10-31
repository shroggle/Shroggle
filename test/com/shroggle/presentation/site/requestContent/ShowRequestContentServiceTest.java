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
package com.shroggle.presentation.site.requestContent;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.Config;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

/**
 * @author Artem Stasuk
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class ShowRequestContentServiceTest {

    @Before
    public void before() {
        final MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void execute() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        site.setTitle("a");
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Assert.assertNotNull(service.execute(site.getSiteId()));
        final List<Site> sites = service.getSites();
        Assert.assertNotNull(sites);
        Assert.assertEquals(1, sites.size());
        Assert.assertEquals(site, service.getTargetSite());
        Assert.assertEquals(site, sites.get(0));
        Assert.assertEquals(1, service.getTargetSites().size());
        Assert.assertEquals(service.getTargetSite(), service.getTargetSites().get(0));
    }

    @Test
    public void executeWithoutTarget() throws IOException, ServletException {
        final User user = TestUtil.createUserAndLogin();
        final Site site1 = TestUtil.createSite();
        site1.setTitle("z");
        site1.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        final Site site2 = TestUtil.createSite();
        site2.setTitle("a");
        site2.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        TestUtil.createUserOnSiteRightActive(user, site2, SiteAccessLevel.ADMINISTRATOR);

        Assert.assertNotNull(service.execute(null));
        final List<Site> sites = service.getSites();
        Assert.assertNotNull(sites);
        Assert.assertEquals(2, sites.size());
        Assert.assertEquals(site2, sites.get(0));
        Assert.assertEquals(site1, sites.get(1));

        Assert.assertNull(service.getTargetSite());
        final List<Site> targetSites = service.getSites();
        Assert.assertNotNull(targetSites);
        Assert.assertEquals(2, targetSites.size());
        Assert.assertEquals(site2, targetSites.get(0));
        Assert.assertEquals(site1, targetSites.get(1));
    }

    @Test
    public void executeForMany() throws IOException, ServletException {
        final User user = TestUtil.createUserAndLogin();
        final Site site0 = TestUtil.createSite();
        site0.setTitle("za");
        site0.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        TestUtil.createUserOnSiteRightActive(user, site0, SiteAccessLevel.ADMINISTRATOR);
        final Site site1 = TestUtil.createSite();
        site1.setTitle("a");
        site1.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        Assert.assertNotNull(service.execute(site0.getSiteId()));
        final List<Site> sites = service.getSites();
        Assert.assertEquals(2, sites.size());
        Assert.assertEquals(site1, sites.get(0));
        Assert.assertEquals(site0, sites.get(1));
    }

    @Test
    public void executeWithNotLive() throws IOException, ServletException {
        final Config config = ServiceLocator.getConfigStorage().get();
        config.getBillingInfoProperties().setCheckSitesBillingInfo(true);

        final Site site = TestUtil.createSite();
        new SiteManager(site).setSiteStatus(SiteStatus.SUSPENDED);
        site.setTitle("a");
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Assert.assertNotNull(service.execute(site.getSiteId()));
        Assert.assertEquals(0, service.getSites().size());
        Assert.assertEquals(site, service.getTargetSite());
    }

    @Test
    public void executeWithBlueprint() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        site.setType(SiteType.BLUEPRINT);
        site.setTitle("a");
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Assert.assertNotNull(service.execute(site.getSiteId()));
        Assert.assertEquals(0, service.getSites().size());
        Assert.assertEquals(site, service.getTargetSite());
    }

    @Test(expected = SiteNotFoundException.class)
    public void executeWithoutSite() throws IOException, ServletException {
        TestUtil.createUserAndLogin();

        service.execute(1);
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() throws IOException, ServletException {
        service.execute(1);
    }

    private final ShowRequestContentService service = new ShowRequestContentService();

}
