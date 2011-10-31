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

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.PublicBlueprintsSettings;
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.User;
import com.shroggle.logic.site.SiteCopierFromActivatedBlueprintMock;
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.presentation.account.dashboard.DashboardAction;
import com.shroggle.util.ServiceLocator;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class SetSiteBlueprintActionTest {

    @Test
    public void showWithoutSite() {
        TestUtil.createUserAndLogin();

        final ResolutionMock resolutionMock = (ResolutionMock) action.show();

        Assert.assertFalse(siteCopierFromActivatedBlueprintMock.isCalled());
        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void showWithoutLogin() {
        final ResolutionMock resolutionMock = (ResolutionMock) action.show();

        Assert.assertFalse(siteCopierFromActivatedBlueprintMock.isCalled());
        Assert.assertEquals(action, resolutionMock.getLoginInUserAction());
    }

    @Test
    public void showWithNotMySite() {
        TestUtil.createUserAndLogin();
        action.setSiteId(TestUtil.createSite().getSiteId());

        final ResolutionMock resolutionMock = (ResolutionMock) action.show();

        Assert.assertFalse(siteCopierFromActivatedBlueprintMock.isCalled());
        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void showWithVisitorRights() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.VISITOR);
        action.setSiteId(site.getSiteId());

        final ResolutionMock resolutionMock = (ResolutionMock) action.show();

        Assert.assertFalse(siteCopierFromActivatedBlueprintMock.isCalled());
        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void showWithGuestRights() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.GUEST);
        action.setSiteId(site.getSiteId());

        final ResolutionMock resolutionMock = (ResolutionMock) action.show();

        Assert.assertFalse(siteCopierFromActivatedBlueprintMock.isCalled());
        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeWithoutBlueprintId() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        action.setSiteId(site.getSiteId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertFalse(siteCopierFromActivatedBlueprintMock.isCalled());
        Assert.assertEquals("/WEB-INF/pages/setSiteBlueprint.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void executeWithoutSiteId() {
        TestUtil.createUserAndLogin();

        final Site activatedBlueprint = TestUtil.createBlueprint();

        action.setActivatedBlueprintId(activatedBlueprint.getId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertFalse(siteCopierFromActivatedBlueprintMock.isCalled());
        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void execute() {
        final User user = TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        final Site activatedBlueprint = TestUtil.createBlueprint();
        activatedBlueprint.setPublicBlueprintsSettings(new PublicBlueprintsSettings());
        activatedBlueprint.getPublicBlueprintsSettings().setActivated(new Date());

        action.setSiteId(site.getSiteId());
        action.setActivatedBlueprintId(activatedBlueprint.getId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertTrue("All success but action didn't connect to blueprint!",
                siteCopierFromActivatedBlueprintMock.isCalled());
        Assert.assertEquals(site, siteCopierFromActivatedBlueprintMock.getSite());
        Assert.assertEquals(activatedBlueprint, siteCopierFromActivatedBlueprintMock.getBlueprint());
        Assert.assertEquals(SiteEditPageAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeWithNotActivatedBlueprint() {
        final User user = TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        final Site activatedBlueprint = TestUtil.createBlueprint();

        action.setSiteId(site.getSiteId());
        action.setActivatedBlueprintId(activatedBlueprint.getId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertFalse(siteCopierFromActivatedBlueprintMock.isCalled());
        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeWithoutRightOnSite() {
        TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        final Site activatedBlueprint = TestUtil.createBlueprint();

        action.setSiteId(site.getSiteId());
        action.setActivatedBlueprintId(activatedBlueprint.getId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertFalse(siteCopierFromActivatedBlueprintMock.isCalled());
        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeWithoutLogin() {
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertFalse(siteCopierFromActivatedBlueprintMock.isCalled());
        Assert.assertEquals(action, resolutionMock.getLoginInUserAction());
    }

    private final SetSiteBlueprintAction action = new SetSiteBlueprintAction();
    private final SiteCopierFromActivatedBlueprintMock siteCopierFromActivatedBlueprintMock =
            (SiteCopierFromActivatedBlueprintMock) ServiceLocator.getSiteCopierFromActivatedBlueprint();

}
