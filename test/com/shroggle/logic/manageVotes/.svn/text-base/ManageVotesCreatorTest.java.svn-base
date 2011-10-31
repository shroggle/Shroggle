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
import com.shroggle.exception.ManageVotesNotFoundException;
import com.shroggle.exception.ManageVotesNotUniqueNameException;
import com.shroggle.exception.ManageVotesNullOrEmptyNameException;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.presentation.manageVotes.SaveManageVotesRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@RunWith(TestRunnerWithMockServices.class)
public class ManageVotesCreatorTest {

    private ManageVotesCreator manageVotesCreator;

    @Test
    public void executeFromSiteEditPage() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final WidgetItem widgetItem = TestUtil.createWidgetManageVotes(null);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.addWidget(widgetItem);

        //Creating existing manage votes
        final WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGallery.setCrossWidgetId(1008);
        final DraftGallery gallery = TestUtil.createGallery(site);
        widgetGallery.setDraftItem(gallery);

        List<DraftManageVotesSettings> manageVotesGallerySettingsList = new ArrayList<DraftManageVotesSettings>(){{
                add(TestUtil.createManageVotesGallerySettings(widgetGallery));
        }};
        final DraftManageVotes manageVotes = TestUtil.createManageVotes(site, manageVotesGallerySettingsList);
        widgetItem.setDraftItem(manageVotes);
        manageVotesGallerySettingsList.get(0).setManageVotes(manageVotes);

        SaveManageVotesRequest request = new SaveManageVotesRequest();
        request.setWidgetId(widgetItem.getWidgetId());
        request.setName("name");
        request.setDescription("description");
        request.setShowDescription(true);
        request.setPickAWinner(true);
        request.setShowVotingModulesFromCurrentSite(true);
        request.setManageVotesId(manageVotes.getId());

        manageVotesGallerySettingsList = new ArrayList<DraftManageVotesSettings>(){{
                add(TestUtil.createManageVotesGallerySettings(widgetGallery));
        }};
        request.setManageVotesGallerySettingsListUnchecked(manageVotesGallerySettingsList);

        final WidgetItem widgetGallery1 = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGallery1.setCrossWidgetId(1009);
        final DraftGallery gallery1 = TestUtil.createGallery(site);
        widgetGallery1.setDraftItem(gallery1);
        manageVotesGallerySettingsList = new ArrayList<DraftManageVotesSettings>(){{
                add(TestUtil.createManageVotesGallerySettings(widgetGallery1));
        }};
        request.setManageVotesGallerySettingsListChecked(manageVotesGallerySettingsList);

        manageVotesCreator = new ManageVotesCreator(new UserManager(user));
        manageVotesCreator.save(request, SiteShowOption.getDraftOption());

        Assert.assertNotNull(widgetItem.getDraftItem());
        final DraftManageVotes savedManageVotes = (DraftManageVotes)(widgetItem.getDraftItem());
        Assert.assertEquals("name", savedManageVotes.getName());
        Assert.assertEquals("description", savedManageVotes.getDescription());
        Assert.assertEquals(true, savedManageVotes.isShowDescription());
        Assert.assertEquals(true, savedManageVotes.isPickAWinner());
        Assert.assertEquals(true, savedManageVotes.isShowVotingModulesFromCurrentSite());
        Assert.assertEquals(1, savedManageVotes.getManageVotesGallerySettingsList().size());
        Assert.assertEquals(savedManageVotes, savedManageVotes.getManageVotesGallerySettingsList().get(0).getManageVotes());
        Assert.assertEquals(gallery1.getId(), ManageVotesGallerySettingsManager.getGalleryId(savedManageVotes.getManageVotesGallerySettingsList().get(0), SiteShowOption.getDraftOption()));
    }

    @Test
    public void executeFromManageItems() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        //Creating existing manage votes
        final WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGallery.setCrossWidgetId(1008);
        final DraftGallery gallery = TestUtil.createGallery(site);
        widgetGallery.setDraftItem(gallery);

        List<DraftManageVotesSettings> manageVotesGallerySettingsList = new ArrayList<DraftManageVotesSettings>(){{
                add(TestUtil.createManageVotesGallerySettings(widgetGallery));
        }};
        final DraftManageVotes manageVotes = TestUtil.createManageVotes(site, manageVotesGallerySettingsList);
        manageVotesGallerySettingsList.get(0).setManageVotes(manageVotes);

        SaveManageVotesRequest request = new SaveManageVotesRequest();
        request.setName("name");
        request.setDescription("description");
        request.setShowDescription(true);
        request.setPickAWinner(true);
        request.setShowVotingModulesFromCurrentSite(true);
        request.setManageVotesId(manageVotes.getId());

        manageVotesGallerySettingsList = new ArrayList<DraftManageVotesSettings>(){{
                add(TestUtil.createManageVotesGallerySettings(widgetGallery));
        }};
        request.setManageVotesGallerySettingsListUnchecked(manageVotesGallerySettingsList);

        final WidgetItem widgetGallery1 = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGallery1.setCrossWidgetId(1009);
        final DraftGallery gallery1 = TestUtil.createGallery(site);
        widgetGallery1.setDraftItem(gallery1);
        manageVotesGallerySettingsList = new ArrayList<DraftManageVotesSettings>(){{
                add(TestUtil.createManageVotesGallerySettings(widgetGallery1));
        }};
        request.setManageVotesGallerySettingsListChecked(manageVotesGallerySettingsList);

        manageVotesCreator = new ManageVotesCreator(new UserManager(user));
        manageVotesCreator.save(request, SiteShowOption.getDraftOption());

        Assert.assertEquals("name", manageVotes.getName());
        Assert.assertEquals("description", manageVotes.getDescription());
        Assert.assertEquals(true, manageVotes.isShowDescription());
        Assert.assertEquals(true, manageVotes.isPickAWinner());
        Assert.assertEquals(true, manageVotes.isShowVotingModulesFromCurrentSite());
        Assert.assertEquals(1, manageVotes.getManageVotesGallerySettingsList().size());
        Assert.assertEquals(manageVotes, manageVotes.getManageVotesGallerySettingsList().get(0).getManageVotes());
        Assert.assertEquals(gallery1.getId(), ManageVotesGallerySettingsManager.getGalleryId(manageVotes.getManageVotesGallerySettingsList().get(0), SiteShowOption.getDraftOption()));
    }

    @Test(expected = ManageVotesNotUniqueNameException.class)
    public void saveNotUnique() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final WidgetItem widgetItem = TestUtil.createWidgetManageVotes(null);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.addWidget(widgetItem);

        final DraftManageVotes manageVotes = TestUtil.createManageVotes(site);
        final DraftManageVotes manageVotes2 = TestUtil.createManageVotes(site);
        widgetItem.setDraftItem(manageVotes);
        manageVotes.setName("name");
        manageVotes2.setName("name2");

        SaveManageVotesRequest request = new SaveManageVotesRequest();
        request.setWidgetId(widgetItem.getWidgetId());
        request.setManageVotesId(manageVotes.getId());
        request.setName("name2");
        request.setDescription("description");
        request.setShowDescription(true);
        request.setPickAWinner(true);
        request.setShowVotingModulesFromCurrentSite(true);

        final WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGallery.setCrossWidgetId(1001);
        final DraftGallery gallery = TestUtil.createGallery(site);
        widgetGallery.setDraftItem(gallery);
        List<DraftManageVotesSettings> manageVotesGallerySettingsList = Arrays.asList(TestUtil.createManageVotesGallerySettings(widgetGallery));
        request.setManageVotesGallerySettingsListChecked(manageVotesGallerySettingsList);

        manageVotesCreator = new ManageVotesCreator(new UserManager(user));
        manageVotesCreator.save(request, SiteShowOption.getDraftOption());
    }

    @Test(expected = ManageVotesNotFoundException.class)
    public void saveWithoutManageVotes() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final WidgetItem widgetItem = TestUtil.createWidgetManageVotes(null);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.addWidget(widgetItem);

        SaveManageVotesRequest request = new SaveManageVotesRequest();
        request.setWidgetId(widgetItem.getWidgetId());
        request.setName("name");
        request.setDescription("description");
        request.setShowDescription(true);
        request.setPickAWinner(true);
        request.setShowVotingModulesFromCurrentSite(true);

        final WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGallery.setCrossWidgetId(1001);
        final DraftGallery gallery = TestUtil.createGallery(site);
        widgetGallery.setDraftItem(gallery);
        List<DraftManageVotesSettings> manageVotesGallerySettingsList = Arrays.asList(TestUtil.createManageVotesGallerySettings(widgetGallery));
        request.setManageVotesGallerySettingsListChecked(manageVotesGallerySettingsList);

        manageVotesCreator = new ManageVotesCreator(new UserManager(user));
        manageVotesCreator.save(request, SiteShowOption.getDraftOption());
    }

    @Test(expected = ManageVotesNullOrEmptyNameException.class)
    public void saveWithoutName() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final WidgetItem widgetItem = TestUtil.createWidgetManageVotes(null);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.addWidget(widgetItem);

        SaveManageVotesRequest request = new SaveManageVotesRequest();
        request.setWidgetId(widgetItem.getWidgetId());
        request.setDescription("description");
        request.setShowDescription(true);
        request.setPickAWinner(true);
        request.setShowVotingModulesFromCurrentSite(true);

        final WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGallery.setCrossWidgetId(1001);
        final DraftGallery gallery = TestUtil.createGallery(site);
        widgetGallery.setDraftItem(gallery);
        List<DraftManageVotesSettings> manageVotesGallerySettingsList = Arrays.asList(TestUtil.createManageVotesGallerySettings(widgetGallery));
        request.setManageVotesGallerySettingsListChecked(manageVotesGallerySettingsList);

        manageVotesCreator = new ManageVotesCreator(new UserManager(user));
        manageVotesCreator.save(request, SiteShowOption.getDraftOption());
    }

    @Test(expected = WidgetNotFoundException.class)
    public void saveWithoutWidget() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final WidgetItem widgetItem = TestUtil.createWidgetManageVotes(null);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.addWidget(widgetItem);

        SaveManageVotesRequest request = new SaveManageVotesRequest();
        request.setWidgetId(0);
        request.setDescription("description");
        request.setShowDescription(true);
        request.setPickAWinner(true);
        request.setShowVotingModulesFromCurrentSite(true);

        final WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGallery.setCrossWidgetId(1001);
        final DraftGallery gallery = TestUtil.createGallery(site);
        widgetGallery.setDraftItem(gallery);
        List<DraftManageVotesSettings> manageVotesGallerySettingsList = Arrays.asList(TestUtil.createManageVotesGallerySettings(widgetGallery));
        request.setManageVotesGallerySettingsListChecked(manageVotesGallerySettingsList);

        manageVotesCreator = new ManageVotesCreator(new UserManager(user));
        manageVotesCreator.save(request, SiteShowOption.getDraftOption());
    }

    @Test
    public void updateGallerySettings() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        final DraftManageVotes manageVotes = TestUtil.createManageVotes(site);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        final WidgetItem widgetGallery1 = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGallery1.setCrossWidgetId(1001);
        final DraftGallery gallery1 = TestUtil.createGallery(site);
        widgetGallery1.setDraftItem(gallery1);

        final WidgetItem widgetGallery2 = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGallery2.setCrossWidgetId(1002);
        final DraftGallery gallery2 = TestUtil.createGallery(site);
        widgetGallery2.setDraftItem(gallery2);

        final WidgetItem widgetGallery3 = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGallery3.setCrossWidgetId(1003);
        final DraftGallery gallery3 = TestUtil.createGallery(site);
        widgetGallery3.setDraftItem(gallery3);

        final List<DraftManageVotesSettings> oldItems = new ArrayList<DraftManageVotesSettings>();
        oldItems.add(TestUtil.createManageVotesGallerySettings(widgetGallery1));
        oldItems.get(0).setCustomName("old_name1");
        oldItems.get(0).setColorCode("old_color_code1");
        oldItems.get(0).setManageVotes(manageVotes);
        oldItems.add(TestUtil.createManageVotesGallerySettings(widgetGallery2));
        oldItems.get(1).setCustomName("old_name2");
        oldItems.get(1).setColorCode("old_color_code2");
        oldItems.get(1).setManageVotes(manageVotes);
        manageVotes.setManageVotesGallerySettingsList(oldItems);

        final List<DraftManageVotesSettings> newItems = new ArrayList<DraftManageVotesSettings>();
        newItems.add(TestUtil.createManageVotesGallerySettings(widgetGallery1));
        newItems.get(0).setCustomName("new_name1");
        newItems.get(0).setColorCode("new_color_code1");
        newItems.get(0).setManageVotes(manageVotes);
        newItems.add(TestUtil.createManageVotesGallerySettings(widgetGallery3));
        newItems.get(1).setCustomName("new_name3");
        newItems.get(1).setColorCode("new_color_code3");
        newItems.get(1).setManageVotes(manageVotes);

        manageVotesCreator = new ManageVotesCreator(new UserManager(user));
        manageVotesCreator.updateGallerySettings(manageVotes, newItems, new ArrayList<DraftManageVotesSettings>(), SiteShowOption.getDraftOption());

        Assert.assertEquals(3, manageVotes.getManageVotesGallerySettingsList().size());
        for (DraftManageVotesSettings setting : manageVotes.getManageVotesGallerySettingsList()) {
            if (ManageVotesGallerySettingsManager.getGalleryId(setting, SiteShowOption.getDraftOption()) == gallery1.getId()) {
                Assert.assertEquals("new_name1", setting.getCustomName());
                Assert.assertEquals("new_color_code1", setting.getColorCode());
            }

            if (ManageVotesGallerySettingsManager.getGalleryId(setting, SiteShowOption.getDraftOption()) == gallery2.getId()) {
                Assert.assertEquals("old_name2", setting.getCustomName());
                Assert.assertEquals("old_color_code2", setting.getColorCode());
            }

            if (ManageVotesGallerySettingsManager.getGalleryId(setting, SiteShowOption.getDraftOption()) == gallery3.getId()) {
                Assert.assertEquals("new_name3", setting.getCustomName());
                Assert.assertEquals("new_color_code3", setting.getColorCode());
            }
        }
    }
}
