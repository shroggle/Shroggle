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
import com.shroggle.logic.gallery.voting.VotingStarsData;
import com.shroggle.logic.site.page.PageManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@RunWith(TestRunnerWithMockServices.class)
public class ManageVotesGallerySettingsManagerTest {

    @Test
    public void getGalleryByGalleryWidgetId() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);

        final WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGallery.setCrossWidgetId(1001);

        final DraftGallery gallery = TestUtil.createGallery(site);
        widgetGallery.setDraftItem(gallery);

        final DraftManageVotesSettings manageVotesGallerySettings = TestUtil.createManageVotesGallerySettings(widgetGallery);

        Assert.assertEquals(gallery.getId(), ManageVotesGallerySettingsManager.getGalleryId(manageVotesGallerySettings, SiteShowOption.getDraftOption()));
    }

    @Test
    public void getGalleryByGalleryWidgetIdWithNoneExistentWidget() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);

        final WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGallery.setCrossWidgetId(1001);

        final DraftGallery gallery = TestUtil.createGallery(site);
        widgetGallery.setDraftItem(gallery);

        final DraftManageVotesSettings manageVotesGallerySettings = TestUtil.createManageVotesGallerySettings(widgetGallery);
        manageVotesGallerySettings.setGalleryCrossWidgetId(1002);

        Assert.assertEquals(0, ManageVotesGallerySettingsManager.getGalleryId(manageVotesGallerySettings, SiteShowOption.getDraftOption()));
    }

    @Test
    public void createGalleryLinkOnUserPages() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);

        final WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGallery.setCrossWidgetId(1000);

        final DraftGallery gallery = TestUtil.createGallery(site);
        widgetGallery.setDraftItem(gallery);

        final DraftManageVotesSettings settings = TestUtil.createManageVotesGallerySettings(widgetGallery);


        final ManageVotesGallerySettingsManager manager = new ManageVotesGallerySettingsManager(settings, SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals("showPageVersion.action?pageId=" + pageVersion.getPageId()
                + "&siteShowOption=" + SiteShowOption.ON_USER_PAGES,
                manager.createGalleryLink());
        Assert.assertEquals(settings.getColorCode(), manager.getColorCode());
        Assert.assertEquals(settings.getCustomName(), manager.getCustomName());
        Assert.assertEquals(gallery.getId(), manager.getGalleryId());
        Assert.assertEquals(gallery.getName(), manager.getGalleryName());
        Assert.assertEquals(gallery.isIncludesVotingModule(), manager.isIncludesVotingModule());
    }

    @Test
    public void createGalleryLinkOntsideApp() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createWorkPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.getWorkPageSettings().setUrl("coolurl");

        final WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGallery.setCrossWidgetId(1001);
        pageVersion.getWorkPageSettings().addWidget(widgetGallery);

        final DraftGallery gallery = TestUtil.createGallery(site);
        widgetGallery.setDraftItem(gallery);

        final DraftManageVotesSettings settings = TestUtil.createManageVotesGallerySettings(widgetGallery);

        final ManageVotesGallerySettingsManager manager = new ManageVotesGallerySettingsManager(settings, SiteShowOption.OUTSIDE_APP);

        Assert.assertEquals("/coolurl",
                manager.createGalleryLink());
        Assert.assertEquals(settings.getColorCode(), manager.getColorCode());
        Assert.assertEquals(settings.getCustomName(), manager.getCustomName());
        Assert.assertEquals(gallery.getId(), manager.getGalleryId());
        Assert.assertEquals(gallery.getName(), manager.getGalleryName());
        Assert.assertEquals(gallery.isIncludesVotingModule(), manager.isIncludesVotingModule());
    }

    @Test
    public void getRecordNameCorrectDataTest() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.setUrl("coolurl");

        final List<DraftFormItem> formItems = TestUtil.createFormItems(FormItemName.IMAGE_FILE_UPLOAD, FormItemName.FIRST_NAME);
        final DraftForm form = TestUtil.createCustomForm(site);
        form.setFormItems(formItems);
        final FilledForm filledForm = TestUtil.createFilledFormEmpty(form.getFormId());

        final List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.IMAGE_FILE_UPLOAD, FormItemName.FIRST_NAME);
        filledFormItems.get(1).setValue("fucking_name");
        filledForm.setFilledFormItems(filledFormItems);

        final WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGallery.setCrossWidgetId(1001);

        final DraftGallery gallery = TestUtil.createGallery(site);
        gallery.setFormId1(form.getFormId());
        widgetGallery.setDraftItem(gallery);

        final DraftManageVotesSettings settings = TestUtil.createManageVotesGallerySettings(widgetGallery);
        settings.setFormItemId(form.getFormItems().get(1).getFormItemId());
        final ManageVotesGallerySettingsManager manager = new ManageVotesGallerySettingsManager(settings, SiteShowOption.getDraftOption());
        Assert.assertEquals(settings.getColorCode(), manager.getColorCode());
        Assert.assertEquals(settings.getCustomName(), manager.getCustomName());
        Assert.assertEquals(gallery.getId(), manager.getGalleryId());
        Assert.assertEquals(gallery.getName(), manager.getGalleryName());
        Assert.assertEquals(gallery.isIncludesVotingModule(), manager.isIncludesVotingModule());

        Assert.assertEquals("fucking_name", manager.getRecordName(filledForm.getFilledFormId()));
    }

    @Test
    public void getRecordNameDefaultNameTest() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.setUrl("coolurl");

        final List<DraftFormItem> formItems = TestUtil.createFormItems(FormItemName.IMAGE_FILE_UPLOAD, FormItemName.FIRST_NAME);
        final DraftForm form = TestUtil.createCustomForm(site);
        form.setFormItems(formItems);
        final FilledForm filledForm = TestUtil.createFilledFormEmpty(form.getFormId());

        final List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.IMAGE_FILE_UPLOAD, FormItemName.FIRST_NAME);
        filledFormItems.get(1).setValue("");
        filledForm.setFilledFormItems(filledFormItems);

        final WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGallery.setCrossWidgetId(1001);

        final DraftGallery gallery = TestUtil.createGallery(site);
        gallery.setFormId1(form.getFormId());
        widgetGallery.setDraftItem(gallery);

        final DraftManageVotesSettings settings = TestUtil.createManageVotesGallerySettings(widgetGallery);
        settings.setFormItemId(form.getFormItems().get(1).getFormItemId());
        final ManageVotesGallerySettingsManager manager = new ManageVotesGallerySettingsManager(settings, SiteShowOption.getDraftOption());
        Assert.assertEquals(settings.getColorCode(), manager.getColorCode());
        Assert.assertEquals(settings.getCustomName(), manager.getCustomName());
        Assert.assertEquals(gallery.getId(), manager.getGalleryId());
        Assert.assertEquals(gallery.getName(), manager.getGalleryName());
        Assert.assertEquals(gallery.isIncludesVotingModule(), manager.isIncludesVotingModule());

        Assert.assertEquals("&lt;No name entered&gt;", manager.getRecordName(filledForm.getFilledFormId()));
    }

    @Test
    public void testCreateVotingStarsData() {
        User user = TestUtil.createUserAndLogin("email");
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.setUrl("coolurl");

        final List<DraftFormItem> formItems = TestUtil.createFormItems(FormItemName.IMAGE_FILE_UPLOAD, FormItemName.FIRST_NAME);
        final DraftForm form = TestUtil.createCustomForm(site);
        form.setFormItems(formItems);
        final FilledForm filledForm1 = TestUtil.createFilledFormEmpty(form.getFormId());
        final FilledForm filledForm2 = TestUtil.createFilledFormEmpty(form.getFormId());
        TestUtil.createFilledFormEmpty(form.getFormId());

        final WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGallery.setCrossWidgetId(1001);

        final DraftGallery gallery = TestUtil.createGallery(site);
        gallery.setFormId1(form.getFormId());
        widgetGallery.setDraftItem(gallery);

        Vote vote1 = TestUtil.createVote(gallery.getId(), 1, user.getUserId());
        vote1.setStartDate(null);
        vote1.setEndDate(null);
        vote1.setVoteValue(4);
        vote1.setFilledFormId(filledForm1.getFilledFormId());

        Vote vote2 = TestUtil.createVote(gallery.getId(), 2, user.getUserId());
        vote2.setVoteDate(new GregorianCalendar(2009, 10, 15).getTime());
        vote2.setStartDate(null);
        vote2.setEndDate(null);
        vote2.setVoteValue(5);
        vote1.setFilledFormId(filledForm2.getFilledFormId());

        final DraftManageVotesSettings settings = TestUtil.createManageVotesGallerySettings(widgetGallery);
        final ManageVotesGallerySettingsManager manager = new ManageVotesGallerySettingsManager(settings, SiteShowOption.getDraftOption());

        List<VotingStarsData> starsData = manager.getVotingStarsData();
        Assert.assertEquals(2, starsData.size());
        Assert.assertEquals(vote1.getVoteValue(), starsData.get(0).getVoteData().getVoteValue());
        Assert.assertEquals(vote2.getVoteValue(), starsData.get(1).getVoteData().getVoteValue());
    }
}

