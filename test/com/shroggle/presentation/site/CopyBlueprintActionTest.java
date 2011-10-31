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
import com.shroggle.entity.Site;
import com.shroggle.logic.site.SiteCopierBlueprintMock;
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.presentation.account.dashboard.DashboardAction;
import com.shroggle.util.ServiceLocator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class CopyBlueprintActionTest {

    @Test
    public void executeWithoutRights() {
        final Site blueprint = TestUtil.createBlueprint();

        action.setBlueprintId(blueprint.getId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(action, resolutionMock.getLoginInUserAction());
        Assert.assertFalse(siteCopierBlueprintMock.isCalled());
    }

    @Test
    public void executeWithoutId() {
        TestUtil.createUserAndLogin();

        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
        Assert.assertFalse(siteCopierBlueprintMock.isCalled());
    }

    @Test
    public void executeForNotBlueprint() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site);

        action.setBlueprintId(site.getId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
        Assert.assertFalse(siteCopierBlueprintMock.isCalled());
    }

    @Test
    public void execute() {
        final Site blueprint = TestUtil.createBlueprint();
        TestUtil.createUserAndUserOnSiteRightAndLogin(blueprint);

        action.setBlueprintId(blueprint.getId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
        Assert.assertEquals(blueprint, siteCopierBlueprintMock.getBlueprint());
        Assert.assertTrue("We should call copier!", siteCopierBlueprintMock.isCalled());
    }

    @Test
    public void executeWithNotFoundBlueprint() {
        TestUtil.createUserAndLogin();

        action.setBlueprintId(-1);
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
        Assert.assertFalse(siteCopierBlueprintMock.isCalled());
    }

    private final SiteCopierBlueprintMock siteCopierBlueprintMock =
            (SiteCopierBlueprintMock) ServiceLocator.getSiteCopierBlueprint();
    private CopyBlueprintAction action = new CopyBlueprintAction();

}