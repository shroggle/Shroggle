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
package com.shroggle.presentation.account.dashboard.siteInfo;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.ChildSiteSettings;
import com.shroggle.entity.Site;
import com.shroggle.logic.childSites.childSiteSettings.ChildSiteSettingsManager;
import com.shroggle.presentation.account.dashboard.DashboardSiteType;
import com.shroggle.util.DateUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class DashboardSiteInfoForNotCreatedSiteTest {

    @Test
    public void testIsSelected() throws Exception {
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings();
        final ChildSiteSettingsManager manager = new ChildSiteSettingsManager(childSiteSettings);

        final DashboardSiteInfo dashboardSiteInfo = new DashboardSiteInfoForNotCreatedSite(manager);

        Assert.assertEquals(true, dashboardSiteInfo.isSelected(dashboardSiteInfo.hashCode()));
    }

    @Test
    public void testIsSelected_wrongHash() throws Exception {
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings();
        final ChildSiteSettingsManager manager = new ChildSiteSettingsManager(childSiteSettings);

        final DashboardSiteInfo dashboardSiteInfo = new DashboardSiteInfoForNotCreatedSite(manager);

        Assert.assertEquals(false, dashboardSiteInfo.isSelected(-1));
    }

    @Test
    public void testGetParentSiteId() throws Exception {
        final Site parentSite = TestUtil.createNetworkSite();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, null);
        final ChildSiteSettingsManager manager = new ChildSiteSettingsManager(childSiteSettings);

        final DashboardSiteInfo dashboardSiteInfo = new DashboardSiteInfoForNotCreatedSite(manager);

        Assert.assertEquals(parentSite.getId(), dashboardSiteInfo.getParentSiteId());
    }

    @Test
    public void testGetChildSiteSettingsId() throws Exception {
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings();
        final ChildSiteSettingsManager manager = new ChildSiteSettingsManager(childSiteSettings);

        final DashboardSiteInfo dashboardSiteInfo = new DashboardSiteInfoForNotCreatedSite(manager);

        Assert.assertEquals(childSiteSettings.getId(), dashboardSiteInfo.getChildSiteSettingsId());
    }

    @Test
    public void testGetCreatedDateAsString() throws Exception {
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings();
        childSiteSettings.setCreatedDate(new Date());
        final ChildSiteSettingsManager manager = new ChildSiteSettingsManager(childSiteSettings);

        final DashboardSiteInfo dashboardSiteInfo = new DashboardSiteInfoForNotCreatedSite(manager);

        Assert.assertEquals(DateUtil.toCommonDateStr(childSiteSettings.getCreatedDate()), dashboardSiteInfo.getCreatedDateAsString());
    }

    @Test
    public void testIsSiteCreated() throws Exception {
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings();
        final ChildSiteSettingsManager manager = new ChildSiteSettingsManager(childSiteSettings);

        final DashboardSiteInfo dashboardSiteInfo = new DashboardSiteInfoForNotCreatedSite(manager);

        Assert.assertEquals(false, dashboardSiteInfo.isSiteCreated());
    }

    @Test
    public void testGetSiteType() throws Exception {
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings();
        final ChildSiteSettingsManager manager = new ChildSiteSettingsManager(childSiteSettings);

        final DashboardSiteInfo dashboardSiteInfo = new DashboardSiteInfoForNotCreatedSite(manager);

        Assert.assertEquals(DashboardSiteType.CHILD, dashboardSiteInfo.getSiteType());
    }

    @Test
    public void testIsChildSite() throws Exception {
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings();
        final ChildSiteSettingsManager manager = new ChildSiteSettingsManager(childSiteSettings);

        final DashboardSiteInfo dashboardSiteInfo = new DashboardSiteInfoForNotCreatedSite(manager);

        Assert.assertEquals(true, dashboardSiteInfo.isChildSite());
    }

    @Test
    public void testGetName() throws Exception {
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings();
        final ChildSiteSettingsManager manager = new ChildSiteSettingsManager(childSiteSettings);

        final DashboardSiteInfo dashboardSiteInfo = new DashboardSiteInfoForNotCreatedSite(manager);

        Assert.assertEquals(" - inactive", dashboardSiteInfo.getName());
    }

    @Test
    public void testGetSiteId() throws Exception {
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings();
        final ChildSiteSettingsManager manager = new ChildSiteSettingsManager(childSiteSettings);

        final DashboardSiteInfo dashboardSiteInfo = new DashboardSiteInfoForNotCreatedSite(manager);

        Assert.assertEquals(-1, dashboardSiteInfo.getSiteId());
    }

    @Test
    public void testIsEditable() throws Exception {
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings();
        final ChildSiteSettingsManager manager = new ChildSiteSettingsManager(childSiteSettings);

        final DashboardSiteInfo dashboardSiteInfo = new DashboardSiteInfoForNotCreatedSite(manager);

        Assert.assertEquals(true, dashboardSiteInfo.isEditable());
    }
}
