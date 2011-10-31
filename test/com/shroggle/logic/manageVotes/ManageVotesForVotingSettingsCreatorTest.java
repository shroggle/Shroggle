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
package com.shroggle.logic.manageVotes;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageManager;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class ManageVotesForVotingSettingsCreatorTest {

    @Test
    public void testGetManageVotesForCurrentSite() {
        User user = TestUtil.createUser();
        Site site1 = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);
        final Page page1 = TestUtil.createPage(site1);
        final PageManager pageVersion1 = TestUtil.createPageVersion(page1);

        DraftManageVotes manageVotes1 = TestUtil.createManageVotes(site1);
        WidgetItem widgetManageVotes1 = TestUtil.createWidgetManageVotes(manageVotes1.getId());
        pageVersion1.addWidget(widgetManageVotes1);

        DraftManageVotes manageVotes2 = TestUtil.createManageVotes(site1);
        WidgetItem widgetManageVotes2 = TestUtil.createWidgetManageVotes(manageVotes2.getId());
        pageVersion1.addWidget(widgetManageVotes2);

        DraftManageVotes manageVotes3 = TestUtil.createManageVotes(site1);
        WidgetItem widgetManageVotes3 = TestUtil.createWidgetManageVotes(manageVotes3.getId());
        pageVersion1.addWidget(widgetManageVotes3);

        DraftManageVotes manageVotes4 = TestUtil.createManageVotes(site1);
        WidgetItem widgetManageVotes4 = TestUtil.createWidgetManageVotes(manageVotes4.getId());
        pageVersion1.addWidget(widgetManageVotes4);


        Site site2 = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site2, SiteAccessLevel.ADMINISTRATOR);
        final Page page2 = TestUtil.createPage(site2);
        final PageManager pageVersion2 = TestUtil.createPageVersion(page2);

        DraftManageVotes manageVotes5 = TestUtil.createManageVotes(site2);
        WidgetItem widgetManageVotes5 = TestUtil.createWidgetManageVotes(manageVotes5.getId());
        pageVersion2.addWidget(widgetManageVotes5);

        List<ManageVotesForVotingSettings> manageVotesForVotingSettingsList = ManageVotesForVotingSettingsCreator.executeForCurrentSite(site1.getSiteId(), SiteShowOption.getDraftOption());
        Assert.assertEquals(4, manageVotesForVotingSettingsList.size());
        Assert.assertEquals(widgetManageVotes1.getCrossWidgetId(), manageVotesForVotingSettingsList.get(0).getCrossWidgetId());
        Assert.assertEquals(widgetManageVotes2.getCrossWidgetId(), manageVotesForVotingSettingsList.get(1).getCrossWidgetId());
        Assert.assertEquals(widgetManageVotes3.getCrossWidgetId(), manageVotesForVotingSettingsList.get(2).getCrossWidgetId());
        Assert.assertEquals(widgetManageVotes4.getCrossWidgetId(), manageVotesForVotingSettingsList.get(3).getCrossWidgetId());

        Assert.assertEquals(manageVotes1.getName(), manageVotesForVotingSettingsList.get(0).getManageVotesName());
        Assert.assertEquals(site1.getTitle(), manageVotesForVotingSettingsList.get(0).getSiteName());
        Assert.assertEquals(pageVersion1.getName(), manageVotesForVotingSettingsList.get(0).getPageName());
        for (ManageVotesForVotingSettings settings : manageVotesForVotingSettingsList) {
            Assert.assertNotNull(settings);
            Assert.assertNotNull(settings.getCrossWidgetId());
            Assert.assertNotNull(settings.getManageVotesName());
            Assert.assertNotNull(settings.getSiteName());
            Assert.assertNotNull(settings.getPageName());
        }
    }

    @Test
    public void testGetManageVotesForCurrentSite_withOneWidgetWithoutItem() {
        User user = TestUtil.createUser();
        Site site1 = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);
        final Page page1 = TestUtil.createPage(site1);
        final PageManager pageVersion1 = TestUtil.createPageVersion(page1);

        DraftManageVotes manageVotes1 = TestUtil.createManageVotes(site1);
        WidgetItem widgetManageVotes1 = TestUtil.createWidgetManageVotes(manageVotes1.getId());
        pageVersion1.addWidget(widgetManageVotes1);

        DraftManageVotes manageVotes2 = TestUtil.createManageVotes(site1);
        WidgetItem widgetManageVotes2 = TestUtil.createWidgetManageVotes(manageVotes2.getId());
        pageVersion1.addWidget(widgetManageVotes2);

        DraftManageVotes manageVotes3 = TestUtil.createManageVotes(site1);
        WidgetItem widgetManageVotes3 = TestUtil.createWidgetManageVotes(manageVotes3.getId());
        pageVersion1.addWidget(widgetManageVotes3);

        DraftManageVotes manageVotes4 = TestUtil.createManageVotes(site1);
        WidgetItem widgetManageVotes4 = TestUtil.createWidgetManageVotes(manageVotes4.getId());
        pageVersion1.addWidget(widgetManageVotes4);


        WidgetItem widgetManageVotesWithoutItem = TestUtil.createWidgetManageVotes(null);
        pageVersion1.addWidget(widgetManageVotesWithoutItem);

        Site site2 = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site2, SiteAccessLevel.ADMINISTRATOR);
        final Page page2 = TestUtil.createPage(site2);
        final PageManager pageVersion2 = TestUtil.createPageVersion(page2);

        DraftManageVotes manageVotes5 = TestUtil.createManageVotes(site2);
        WidgetItem widgetManageVotes5 = TestUtil.createWidgetManageVotes(manageVotes5.getId());
        pageVersion2.addWidget(widgetManageVotes5);

        List<ManageVotesForVotingSettings> manageVotesForVotingSettingsList = ManageVotesForVotingSettingsCreator.executeForCurrentSite(site1.getSiteId(), SiteShowOption.getDraftOption());
        Assert.assertEquals(4, manageVotesForVotingSettingsList.size());
        Assert.assertEquals(widgetManageVotes1.getCrossWidgetId(), manageVotesForVotingSettingsList.get(0).getCrossWidgetId());
        Assert.assertEquals(widgetManageVotes2.getCrossWidgetId(), manageVotesForVotingSettingsList.get(1).getCrossWidgetId());
        Assert.assertEquals(widgetManageVotes3.getCrossWidgetId(), manageVotesForVotingSettingsList.get(2).getCrossWidgetId());
        Assert.assertEquals(widgetManageVotes4.getCrossWidgetId(), manageVotesForVotingSettingsList.get(3).getCrossWidgetId());
//        Assert.assertEquals(widgetManageVotesWithoutItem.getCrossWidgetId(), manageVotesForVotingSettingsList.get(4).getCrossWidgetId());

        Assert.assertEquals(manageVotes1.getName(), manageVotesForVotingSettingsList.get(0).getManageVotesName());
        Assert.assertEquals(site1.getTitle(), manageVotesForVotingSettingsList.get(0).getSiteName());
        Assert.assertEquals(pageVersion1.getName(), manageVotesForVotingSettingsList.get(0).getPageName());
        for (ManageVotesForVotingSettings settings : manageVotesForVotingSettingsList) {
            Assert.assertNotNull(settings);
            Assert.assertNotNull(settings.getCrossWidgetId());
            Assert.assertNotNull(settings.getManageVotesName());
            Assert.assertNotNull(settings.getSiteName());
            Assert.assertNotNull(settings.getPageName());
        }

//        Assert.assertEquals("not specified", manageVotesForVotingSettingsList.get(4).getManageVotesName());
//        Assert.assertEquals(site1.getTitle(), manageVotesForVotingSettingsList.get(4).getSiteName());
//        Assert.assertEquals(pageVersion1.getName(), manageVotesForVotingSettingsList.get(4).getPageName());
    }

    @Test
    public void testGetManageVotesForAllAvailableSites() {
         User user = TestUtil.createUser();
        Site site1 = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);
        final Page page1 = TestUtil.createPage(site1);
        final PageManager pageVersion1 = TestUtil.createPageVersion(page1);

        DraftManageVotes manageVotes1 = TestUtil.createManageVotes(site1);
        WidgetItem widgetManageVotes1 = TestUtil.createWidgetManageVotes(manageVotes1.getId());
        pageVersion1.addWidget(widgetManageVotes1);

        DraftManageVotes manageVotes2 = TestUtil.createManageVotes(site1);
        WidgetItem widgetManageVotes2 = TestUtil.createWidgetManageVotes(manageVotes2.getId());
        pageVersion1.addWidget(widgetManageVotes2);

        DraftManageVotes manageVotes3 = TestUtil.createManageVotes(site1);
        WidgetItem widgetManageVotes3 = TestUtil.createWidgetManageVotes(manageVotes3.getId());
        pageVersion1.addWidget(widgetManageVotes3);

        DraftManageVotes manageVotes4 = TestUtil.createManageVotes(site1);
        WidgetItem widgetManageVotes4 = TestUtil.createWidgetManageVotes(manageVotes4.getId());
        pageVersion1.addWidget(widgetManageVotes4);


        Site site2 = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site2, SiteAccessLevel.ADMINISTRATOR);
        final Page page2 = TestUtil.createPage(site2);
        final PageManager pageVersion2 = TestUtil.createPageVersion(page2);

        DraftManageVotes manageVotes5 = TestUtil.createManageVotes(site2);
        WidgetItem widgetManageVotes5 = TestUtil.createWidgetManageVotes(manageVotes5.getId());
        pageVersion2.addWidget(widgetManageVotes5);

        List<ManageVotesForVotingSettings> manageVotesForVotingSettingsList = ManageVotesForVotingSettingsCreator.executeForAllAvailableSites(site1.getSiteId(), SiteShowOption.getDraftOption());

        Assert.assertEquals(5, manageVotesForVotingSettingsList.size());
        Assert.assertEquals(widgetManageVotes1.getCrossWidgetId(), manageVotesForVotingSettingsList.get(0).getCrossWidgetId());
        Assert.assertEquals(widgetManageVotes2.getCrossWidgetId(), manageVotesForVotingSettingsList.get(1).getCrossWidgetId());
        Assert.assertEquals(widgetManageVotes3.getCrossWidgetId(), manageVotesForVotingSettingsList.get(2).getCrossWidgetId());
        Assert.assertEquals(widgetManageVotes4.getCrossWidgetId(), manageVotesForVotingSettingsList.get(3).getCrossWidgetId());
        Assert.assertEquals(widgetManageVotes5.getCrossWidgetId(), manageVotesForVotingSettingsList.get(4).getCrossWidgetId());

        Assert.assertEquals(manageVotes1.getName(), manageVotesForVotingSettingsList.get(0).getManageVotesName());
        Assert.assertEquals(site1.getTitle(), manageVotesForVotingSettingsList.get(0).getSiteName());
        Assert.assertEquals(pageVersion1.getName(), manageVotesForVotingSettingsList.get(0).getPageName());
        for (ManageVotesForVotingSettings settings : manageVotesForVotingSettingsList) {
            Assert.assertNotNull(settings);
            Assert.assertNotNull(settings.getCrossWidgetId());
            Assert.assertNotNull(settings.getManageVotesName());
            Assert.assertNotNull(settings.getSiteName());
            Assert.assertNotNull(settings.getPageName());
        }
    }
}
