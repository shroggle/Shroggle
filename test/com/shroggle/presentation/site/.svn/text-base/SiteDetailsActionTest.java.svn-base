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

package com.shroggle.presentation.site;

import com.shroggle.TestUtil;
import com.shroggle.entity.Page;
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.User;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.presentation.StartAction;
import com.shroggle.presentation.TestAction;
import junit.framework.Assert;
import org.junit.Test;

public class SiteDetailsActionTest extends TestAction<SiteDetailsAction> {

    public SiteDetailsActionTest() {
        super(SiteDetailsAction.class);
    }

    //@todo: fix and write test's. Solomadin

    @Test
    public void show() {
        configStorage.get().setAdminLogin("admin");
        configStorage.get().setAdminPassword("111");

        final User user = TestUtil.createUserAndLogin("admin");
        final Site site = TestUtil.createSite();
        site.setTitle("title1");
        site.setSubDomain("url1");
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        actionOrService.setSiteId(site.getSiteId());
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.show();

        Assert.assertEquals("/account/siteDetails.jsp", resolutionMock.getForwardToUrl());
        Assert.assertEquals(site.getSiteId(), actionOrService.getSiteId());
        Assert.assertEquals(site, actionOrService.getSite());
    }

    @Test
    public void showWithEmptyReferrerUrls() {
        configStorage.get().setAdminLogin("admin");
        configStorage.get().setAdminPassword("111");
        final User user = TestUtil.createUserAndLogin("admin");

        final Site site = TestUtil.createSite();
        site.setTitle("title1");
        site.setSubDomain("url1");

        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);
        pageVersion.setName("My page");
        pageVersion.setTitle("My page title");

        actionOrService.setSiteId(site.getSiteId());
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.show();

        Assert.assertEquals("/account/siteDetails.jsp", resolutionMock.getForwardToUrl());
        Assert.assertTrue(actionOrService.getReferringUrls().isEmpty());
    }

    @Test
    public void showWithNotAdminUser() {
        configStorage.get().setAdminLogin("admin");
        configStorage.get().setAdminPassword("111");

        TestUtil.createUserAndLogin();

        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.show();

        Assert.assertEquals(StartAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void showWithoutLogin() {
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.show();
        Assert.assertEquals(actionOrService, resolutionMock.getLoginInUserAction());
    }

}
