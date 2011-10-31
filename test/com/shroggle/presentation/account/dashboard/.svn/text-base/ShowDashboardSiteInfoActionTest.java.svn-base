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
package com.shroggle.presentation.account.dashboard;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.childSites.childSiteSettings.ChildSiteSettingsManager;
import com.shroggle.presentation.account.dashboard.siteInfo.DashboardSiteInfo;
import com.shroggle.util.ServiceLocator;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class ShowDashboardSiteInfoActionTest {


    @Before
    public void before() {
        final ActionBeanContext actionBeanContext = new ActionBeanContext();
        actionBeanContext.setRequest(new MockHttpServletRequest("", ""));
        action.setContext(actionBeanContext);
    }

    @Test
    public void testExecute() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site commonSite = TestUtil.createSite();

        TestUtil.createUserOnSiteRightActiveAdmin(user, commonSite);
        Assert.assertNull(ServiceLocator.getSessionStorage().getSelectedSiteInfoHashForDashboard(null));


        action.setSiteId(commonSite.getSiteId());
        action.setSiteType(DashboardSiteType.COMMON);
        action.execute();

        final DashboardSiteInfo siteInfo = (DashboardSiteInfo) action.getHttpServletRequest().getAttribute("dashboardSiteInfo");
        Assert.assertEquals(commonSite.getSiteId(), siteInfo.getSiteId());
        Assert.assertEquals(false, siteInfo.isChildSite());
        Assert.assertEquals(ServiceLocator.getSessionStorage().getSelectedSiteInfoHashForDashboard(null).intValue(), DashboardSiteInfo.newInstance(commonSite, DashboardSiteType.COMMON).hashCode());
    }

    @Test
    public void testExecute_withoutSiteButWithChildSiteSettings() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site commonSite = TestUtil.createSite();

        TestUtil.createUserOnSiteRightActiveAdmin(user, commonSite);
        Assert.assertNull(ServiceLocator.getSessionStorage().getSelectedSiteInfoHashForDashboard(null));

        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings();


        action.setSiteId(null);
        action.setChildSiteSettingsId(childSiteSettings.getId());
        action.setSiteType(DashboardSiteType.COMMON);
        action.execute();

        final DashboardSiteInfo siteInfo = (DashboardSiteInfo) action.getHttpServletRequest().getAttribute("dashboardSiteInfo");
        Assert.assertEquals(false, siteInfo.isSiteCreated());
        Assert.assertEquals(childSiteSettings.getId(), siteInfo.getChildSiteSettingsId());
        Assert.assertEquals(true, siteInfo.isChildSite());
        Assert.assertEquals(ServiceLocator.getSessionStorage().getSelectedSiteInfoHashForDashboard(null).intValue(), DashboardSiteInfo.newInstance(new ChildSiteSettingsManager(childSiteSettings)).hashCode());
    }

    @Test
    public void testExecute_forChildSite() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site commonSite = TestUtil.createSite();

        TestUtil.createUserOnSiteRightActiveAdmin(user, commonSite);
        Assert.assertNull(ServiceLocator.getSessionStorage().getSelectedSiteInfoHashForDashboard(null));


        action.setSiteId(commonSite.getSiteId());
        action.setSiteType(DashboardSiteType.CHILD);
        action.execute();

        final DashboardSiteInfo siteInfo = (DashboardSiteInfo) action.getHttpServletRequest().getAttribute("dashboardSiteInfo");
        Assert.assertEquals(commonSite.getSiteId(), siteInfo.getSiteId());
        Assert.assertEquals(true, siteInfo.isChildSite());
        Assert.assertEquals(ServiceLocator.getSessionStorage().getSelectedSiteInfoHashForDashboard(null).intValue(), DashboardSiteInfo.newInstance(commonSite, DashboardSiteType.CHILD).hashCode());
    }

    private final ShowDashboardSiteInfoAction action = new ShowDashboardSiteInfoAction();

}
