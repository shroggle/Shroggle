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
import com.shroggle.entity.ChildSiteSettings;
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.User;
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.presentation.TestAction;
import com.shroggle.presentation.account.dashboard.DashboardAction;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Artem Stasuk
 */
public class DiconnectFromNetworkActionTest extends TestAction<OptOutFromNetworkAction> {

    public DiconnectFromNetworkActionTest() {
        super(OptOutFromNetworkAction.class);
    }

    @Test
    public void executeWithNotFoundSite() {
        ResolutionMock resolutionMock = (ResolutionMock) actionOrService.execute();

        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeWithNotMySite() {
        final Site site = TestUtil.createSite();
        site.setChildSiteSettings(new ChildSiteSettings());
        actionOrService.setSiteId(site.getSiteId());
        ResolutionMock resolutionMock = (ResolutionMock) actionOrService.execute();

        Assert.assertNotNull(site.getChildSiteSettings());
        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeForChild() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        final Site parentSite = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(parentSite, SiteAccessLevel.ADMINISTRATOR);
        childSiteSettings.setParentSite(parentSite);
        site.setChildSiteSettings(childSiteSettings);
        
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        actionOrService.setSiteId(site.getSiteId());
        ResolutionMock resolutionMock = (ResolutionMock) actionOrService.execute();

        Assert.assertNull(site.getChildSiteSettings());
        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
        Assert.assertEquals(1, mailSender.getMails().size());
    }

    @Test
    public void executeForChildAndBasedBlueprint() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        final Site blueprint = TestUtil.createSite();
        blueprint.addBlueprintChild(site);
        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        final Site parentSite = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(parentSite, SiteAccessLevel.ADMINISTRATOR);
        childSiteSettings.setParentSite(parentSite);
        site.setChildSiteSettings(childSiteSettings);
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        actionOrService.setSiteId(site.getSiteId());
        ResolutionMock resolutionMock = (ResolutionMock) actionOrService.execute();

        Assert.assertNull(site.getChildSiteSettings());
        Assert.assertNull(site.getBlueprintParent());
        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

}
