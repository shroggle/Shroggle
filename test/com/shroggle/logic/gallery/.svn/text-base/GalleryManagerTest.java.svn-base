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
package com.shroggle.logic.gallery;

import com.shroggle.PersistanceMock;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.GalleryNameIncorrectException;
import com.shroggle.exception.GalleryNameNotUniqueException;
import com.shroggle.exception.GalleryNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.video.FlvVideoManager;
import com.shroggle.util.DateUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class GalleryManagerTest {

    @Test
    public void getLeftArrowUrlNotForArrow() {
        DraftGallery gallery = new DraftGallery();
        GalleryManager galleryManager = new GalleryManager(gallery);
        org.junit.Assert.assertNull(galleryManager.getLeftArrowUrl());
    }

    @Test
    public void getRightArrowUrlNotForArrow() {
        DraftGallery gallery = new DraftGallery();
        GalleryManager galleryManager = new GalleryManager(gallery);
        org.junit.Assert.assertNull(galleryManager.getRightArrowUrl());
    }

    @Test
    public void getLeftArrowUrl() {
        DraftGallery gallery = new DraftGallery();
        gallery.getDataPaginator().setDataPaginatorArrow("f");
        GalleryManager galleryManager = new GalleryManager(gallery);
        org.junit.Assert.assertEquals("f", galleryManager.getLeftArrowUrl());
    }

    @Test
    public void getLeftArrowUrlWithRightInUrl() {
        DraftGallery gallery = new DraftGallery();
        gallery.getDataPaginator().setDataPaginatorArrow("fRight");
        GalleryManager galleryManager = new GalleryManager(gallery);
        org.junit.Assert.assertEquals("fLeft", galleryManager.getLeftArrowUrl());
    }

    @Test
    public void getRightArrowUrl() {
        DraftGallery gallery = new DraftGallery();
        gallery.getDataPaginator().setDataPaginatorArrow("f");
        GalleryManager galleryManager = new GalleryManager(gallery);
        org.junit.Assert.assertEquals("f", galleryManager.getRightArrowUrl());
    }

    @Test
    public void getName() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        Assert.assertEquals("Gallery1", new GalleriesManager().getNew(site.getSiteId()).getName());
    }

    @Test
    public void isModified() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        Assert.assertEquals(false, new GalleriesManager().getNew(site.getSiteId()).isModified());
    }

    @Test
    public void getDescriptionForNull() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final DraftGallery gallery = new DraftGallery();
        gallery.setSiteId(site.getSiteId());
        gallery.setDescription(null);
        persistance.putItem(gallery);
        final GalleryManager galleryManager = new GalleriesManager().get(gallery.getId());
        Assert.assertEquals("", galleryManager.getNotesComments());
    }

    @Test
    public void getEditWithLabels() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final DraftGallery gallery = new DraftGallery();
        gallery.setSiteId(site.getSiteId());
        persistance.putItem(gallery);
        final DraftGalleryLabel label1 = new DraftGalleryLabel();
        label1.setPosition(1);
        label1.setAlign(GalleryAlign.CENTER);
        label1.setColumn(22);
        gallery.addLabel(label1);
        persistance.putGalleryLabel(label1);
        final DraftGalleryLabel label2 = new DraftGalleryLabel();
        label2.setPosition(0);
        gallery.addLabel(label2);
        persistance.putGalleryLabel(label2);
        final GalleryManager galleryManager = new GalleriesManager().get(gallery.getId());
        Assert.assertEquals(2, galleryManager.getEdit().getLabels().size());
        Assert.assertEquals(label2.getId().getFormItemId(), galleryManager.getEdit().getLabels().get(0).getId());
        Assert.assertEquals(label2.getAlign(), galleryManager.getEdit().getLabels().get(0).getAlign());
        Assert.assertEquals(label2.getColumn(), galleryManager.getEdit().getLabels().get(0).getColumn());
        Assert.assertEquals(label1.getId().getFormItemId(), galleryManager.getEdit().getLabels().get(1).getId());
        Assert.assertEquals(label1.getAlign(), galleryManager.getEdit().getLabels().get(1).getAlign());
        Assert.assertEquals(label1.getColumn(), galleryManager.getEdit().getLabels().get(1).getColumn());
        Assert.assertNotNull(galleryManager.getEdit().getVoteSettings());
        Assert.assertNotNull(galleryManager.getEdit().getVoteStars());
        Assert.assertNotNull(galleryManager.getEdit().getVoteLinks());
    }

    @Test
    public void getDescription() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final DraftGallery gallery = new DraftGallery();
        gallery.setSiteId(site.getSiteId());
        gallery.setDescription("g");
        persistance.putItem(gallery);
        final GalleryManager galleryManager = new GalleriesManager().get(gallery.getId());
        Assert.assertEquals("g", galleryManager.getNotesComments());
    }

    @Test
    public void getNameForExist() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final DraftGallery gallery = new DraftGallery();
        gallery.setName("4");
        gallery.setSiteId(site.getSiteId());
        persistance.putItem(gallery);

        Assert.assertEquals("4", new GalleriesManager().get(gallery.getId()).getName());
    }

    @Ignore
    @Test
    public void saveNew_DurationOfVoteLimited() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final GalleryEdit galleryEdit = new GalleryEdit();
        galleryEdit.setVotingStartDateString("10/12/2000");
        galleryEdit.setVotingEndDateString("10/30/2000");
        galleryEdit.setNotesComments("ff");
        galleryEdit.setName("g");

        final PaypalSettingsData paypalSettingsData = new PaypalSettingsData();
        paypalSettingsData.setFormItemIdWithFullPrice(2); // this id should be changed to real formItemId
        paypalSettingsData.setFormItemIdWithProductName(3); // this id should be changed to real formItemId
        galleryEdit.setPaypalSettings(paypalSettingsData);

        Date startDate = new Date(111111111111111111L);
        Date endDate = new Date(3242343242343453425L);
        VoteSettings voteSettings = new VoteSettings();
        voteSettings.setStartDate(startDate);
        voteSettings.setEndDate(endDate);
        voteSettings.setDurationOfVoteLimited(true);
        galleryEdit.setVoteSettings(voteSettings);
        final DraftGallery gallery = new DraftGallery();
        gallery.setSiteId(site.getSiteId());


        final GalleryManager galleryManager = new GalleryManager(gallery);
        galleryManager.save(galleryEdit);


        final DraftGallery findGallery = persistance.getDraftItem(galleryManager.getId());

        Assert.assertNotNull(findGallery);
        Assert.assertEquals("g", findGallery.getName());
        Assert.assertEquals("ff", findGallery.getDescription());
        Assert.assertEquals(DateUtil.getDateByString(galleryEdit.getVotingStartDateString()), findGallery.getVoteSettings().getStartDate());
        Assert.assertEquals(DateUtil.getDateByString(galleryEdit.getVotingEndDateString()), findGallery.getVoteSettings().getEndDate());

        final DraftForm form = persistance.getFormById(findGallery.getFormId1());

        final PaypalSettingsForGallery paypalSettings = findGallery.getPaypalSettings();
        Assert.assertEquals(form.getFormItems().get(2).getFormItemId(), paypalSettings.getFormItemIdWithPrice().intValue());
        Assert.assertEquals(form.getFormItems().get(3).getFormItemId(), paypalSettings.getFormItemIdWithProductName().intValue());
    }

    @Test
    public void save_withExistingForm() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final GalleryEdit galleryEdit = new GalleryEdit();
        galleryEdit.setVotingStartDateString("10/12/2000");
        galleryEdit.setVotingEndDateString("10/30/2000");
        galleryEdit.setNotesComments("ff");
        galleryEdit.setName("g");

        final PaypalSettingsData paypalSettingsData = new PaypalSettingsData();
        paypalSettingsData.setFormItemIdWithFullPrice(2); // this id should NOT be changed to real formItemId
        paypalSettingsData.setFormItemIdWithProductName(3); // this id should NOT be changed to real formItemId
        galleryEdit.setPaypalSettings(paypalSettingsData);

        galleryEdit.setVoteSettings(new VoteSettings());
        final DraftGallery gallery = new DraftGallery();
        gallery.setSiteId(site.getSiteId());

        final Form form = TestUtil.createCustomForm(site);
        gallery.setFormId1(form.getId());
        galleryEdit.setFormId(form.getId());

        final GalleryManager galleryManager = new GalleryManager(gallery);
        galleryManager.save(galleryEdit);


        final DraftGallery findGallery = persistance.getDraftItem(galleryManager.getId());

        Assert.assertNotNull(findGallery);
        Assert.assertEquals("g", findGallery.getName());
        Assert.assertEquals("ff", findGallery.getDescription());


        final PaypalSettingsForGallery paypalSettings = findGallery.getPaypalSettings();
        Assert.assertEquals(2, paypalSettings.getFormItemIdWithPrice().intValue());
        Assert.assertEquals(3, paypalSettings.getFormItemIdWithProductName().intValue());
    }


    @Test
    public void saveNew_DurationOfVoteNotLimited() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final GalleryEdit galleryEdit = new GalleryEdit();
        galleryEdit.setVotingStartDateString("10/12/2000");
        galleryEdit.setVotingEndDateString("10/30/2000");
        galleryEdit.setPaypalSettings(new PaypalSettingsData());
        galleryEdit.setNotesComments("ff");
        galleryEdit.setName("g");
        Date startDate = new Date(111111111111111111L);
        Date endDate = new Date(3242343242343453425L);
        VoteSettings voteSettings = new VoteSettings();
        voteSettings.setStartDate(startDate);
        voteSettings.setEndDate(endDate);
        voteSettings.setDurationOfVoteLimited(false);
        galleryEdit.setVoteSettings(voteSettings);
        final DraftGallery gallery = new DraftGallery();
        gallery.setSiteId(site.getSiteId());
        final GalleryManager galleryManager = new GalleryManager(gallery);
        galleryManager.save(galleryEdit);

        final DraftGallery findGallery = persistance.getDraftItem(
                galleryManager.getId());
        Assert.assertNotNull(findGallery);
        Assert.assertEquals("g", findGallery.getName());
        Assert.assertEquals("ff", findGallery.getDescription());
        Assert.assertEquals(null, findGallery.getVoteSettings().getStartDate());
        Assert.assertEquals(null, findGallery.getVoteSettings().getEndDate());
    }

    @Test(expected = GalleryNameNotUniqueException.class)
    public void saveNewWithNotUniqueName() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final DraftGallery otherGallery = new DraftGallery();
        otherGallery.setSiteId(site.getSiteId());
        otherGallery.setName("g");
        persistance.putItem(otherGallery);

        final GalleryEdit galleryEdit = new GalleryEdit();
        galleryEdit.setNotesComments("ff");
        galleryEdit.setName("g");

        final DraftGallery gallery = new DraftGallery();
        gallery.setSiteId(site.getSiteId());
        new GalleryManager(gallery).save(galleryEdit);
    }

    @Test(expected = GalleryNameNotUniqueException.class)
    public void saveExistWithNotUniqueName() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final DraftGallery gallery = new DraftGallery();
        gallery.setSiteId(site.getSiteId());
        gallery.setName("g");
        persistance.putItem(gallery);

        final DraftGallery existGallery = new DraftGallery();
        existGallery.setSiteId(site.getSiteId());
        existGallery.setName("g1");
        persistance.putItem(existGallery);

        final GalleryEdit galleryEdit = new GalleryEdit();
        galleryEdit.setNotesComments("ff");
        galleryEdit.setName("g");
        new GalleryManager(existGallery).save(galleryEdit);
    }

    @Test(expected = GalleryNameIncorrectException.class)
    public void saveExistWithNullName() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final DraftGallery gallery = new DraftGallery();
        gallery.setSiteId(site.getSiteId());
        gallery.setName("g1");
        persistance.putItem(gallery);

        final GalleryEdit galleryEdit = new GalleryEdit();
        galleryEdit.setNotesComments("ff");
        galleryEdit.setName(null);
        new GalleryManager(gallery).save(galleryEdit);
    }

    @Test(expected = GalleryNameIncorrectException.class)
    public void saveExistWithEmptyName() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final DraftGallery gallery = new DraftGallery();
        gallery.setSiteId(site.getSiteId());
        gallery.setName("g1");
        persistance.putItem(gallery);

        final GalleryEdit galleryEdit = new GalleryEdit();
        galleryEdit.setNotesComments("ff");
        galleryEdit.setName("");
        new GalleriesManager().get(gallery.getId()).save(galleryEdit);
    }

    @Test(expected = GalleryNameIncorrectException.class)
    public void saveNewWithNullName() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final GalleryEdit galleryEdit = new GalleryEdit();
        galleryEdit.setNotesComments("ff");
        galleryEdit.setName(null);
        new GalleriesManager().getNew(site.getSiteId()).save(galleryEdit);
    }

    @Test(expected = GalleryNameIncorrectException.class)
    public void saveNewWithEmptyName() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final GalleryEdit galleryEdit = new GalleryEdit();
        galleryEdit.setNotesComments("ff");
        galleryEdit.setName("");
        new GalleriesManager().getNew(site.getSiteId()).save(galleryEdit);
    }



    @Test
    public void showDataAndNavigation_DATA_ABOVE_NAVIGATION_BELOW() throws Exception {
        User user = TestUtil.createUserAndLogin("aa");
        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);


        gallery.setOrientation(GalleryOrientation.DATA_ABOVE_NAVIGATION_BELOW);

        GalleryManager galleryManager = new GalleryManager(gallery);
        Assert.assertTrue(galleryManager.showDataAndNavigation());
        Assert.assertFalse(galleryManager.showDataOnly());
        Assert.assertFalse(galleryManager.showNavigationOnly());
    }

    @Test
    public void showDataAndNavigation_NAVIGATION_ABOVE_DATA_BELOW() throws Exception {
        User user = TestUtil.createUserAndLogin("aa");
        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);


        gallery.setOrientation(GalleryOrientation.NAVIGATION_ABOVE_DATA_BELOW);

        GalleryManager galleryManager = new GalleryManager(gallery);
        Assert.assertTrue(galleryManager.showDataAndNavigation());
        Assert.assertFalse(galleryManager.showDataOnly());
        Assert.assertFalse(galleryManager.showNavigationOnly());
    }

    @Test
    public void showDataAndNavigation_DATA_LEFT_NAVIGATION_RIGHT() throws Exception {
        User user = TestUtil.createUserAndLogin("aa");
        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);


        gallery.setOrientation(GalleryOrientation.DATA_LEFT_NAVIGATION_RIGHT);

        GalleryManager galleryManager = new GalleryManager(gallery);
        Assert.assertTrue(galleryManager.showDataAndNavigation());
        Assert.assertFalse(galleryManager.showDataOnly());
        Assert.assertFalse(galleryManager.showNavigationOnly());
    }

    @Test
    public void showDataAndNavigation_NAVIGATION_LEFT_DATA_RIGHT() throws Exception {
        User user = TestUtil.createUserAndLogin("aa");
        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);


        gallery.setOrientation(GalleryOrientation.NAVIGATION_LEFT_DATA_RIGHT);

        GalleryManager galleryManager = new GalleryManager(gallery);
        Assert.assertTrue(galleryManager.showDataAndNavigation());
        Assert.assertFalse(galleryManager.showDataOnly());
        Assert.assertFalse(galleryManager.showNavigationOnly());
    }

    @Test
    public void showDataOnly_DATA_ONLY() throws Exception {
        User user = TestUtil.createUserAndLogin("aa");
        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);


        gallery.setOrientation(GalleryOrientation.DATA_ONLY);

        GalleryManager galleryManager = new GalleryManager(gallery);
        Assert.assertFalse(galleryManager.showDataAndNavigation());
        Assert.assertTrue(galleryManager.showDataOnly());
        Assert.assertFalse(galleryManager.showNavigationOnly());
    }


    @Test
    public void showNavigationOnly_NAVIGATION_ONLY() throws Exception {
        User user = TestUtil.createUserAndLogin("aa");
        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);


        gallery.setOrientation(GalleryOrientation.NAVIGATION_ONLY);

        GalleryManager galleryManager = new GalleryManager(gallery);
        Assert.assertFalse(galleryManager.showDataAndNavigation());
        Assert.assertFalse(galleryManager.showDataOnly());
        Assert.assertTrue(galleryManager.showNavigationOnly());
    }

    @Test
    public void showDataAndNavigationWithoutGallery() throws Exception {
        GalleryManager galleryManager = new GalleryManager(null);
        Assert.assertFalse(galleryManager.showDataAndNavigation());
        Assert.assertFalse(galleryManager.showDataOnly());
        Assert.assertFalse(galleryManager.showNavigationOnly());
    }

    @Test
    public void createNavigationWidth() throws Exception {
        User user = TestUtil.createUserAndLogin("aa");
        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setOrientation(GalleryOrientation.NAVIGATION_ONLY);
        gallery.setColumns(3);
        gallery.setCellHorizontalMargin(10);
        gallery.setCellWidth(10);

        GalleryManager galleryManager = new GalleryManager(gallery);
        final int paginatorWidth = 0;
        Assert.assertEquals(90, galleryManager.createNavigationWidth(paginatorWidth));
    }


    @Test
    public void createNavigationWidth_withPaginatorWidth() throws Exception {
        User user = TestUtil.createUserAndLogin("aa");
        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setOrientation(GalleryOrientation.NAVIGATION_ONLY);
        gallery.setColumns(3);
        gallery.setCellHorizontalMargin(10);
        gallery.setCellWidth(10);

        GalleryManager galleryManager = new GalleryManager(gallery);
        final int paginatorWidth = 10000;
        Assert.assertEquals(paginatorWidth, galleryManager.createNavigationWidth(paginatorWidth));
    }

    @Test
    public void createNavigationHeight() throws Exception {
        User user = TestUtil.createUserAndLogin("aa");
        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setOrientation(GalleryOrientation.NAVIGATION_ONLY);
        gallery.setRows(3);
        gallery.setCellVerticalMargin(10);
        gallery.setCellHeight(10);

        GalleryManager galleryManager = new GalleryManager(gallery);
        gallery.setNavigationPaginatorType(GalleryNavigationPaginatorType.SCROLL_HORIZONTALLY);
        Assert.assertEquals(118, galleryManager.createNavigationHeight());
    }

    @Test
    public void createNavigationHeightWithNullPaginatorType() throws Exception {
        User user = TestUtil.createUserAndLogin("aa");
        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setOrientation(GalleryOrientation.NAVIGATION_ONLY);
        gallery.setRows(3);
        gallery.setCellVerticalMargin(10);
        gallery.setCellHeight(10);

        GalleryManager galleryManager = new GalleryManager(gallery);
        gallery.setOrientation(null);
        Assert.assertEquals(118, galleryManager.createNavigationHeight());
    }

    @Test
    public void createNavigationHeightWithABOVEPaginatorType() throws Exception {
        User user = TestUtil.createUserAndLogin("aa");
        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setOrientation(GalleryOrientation.NAVIGATION_ONLY);
        gallery.setRows(3);
        gallery.setCellVerticalMargin(10);
        gallery.setCellHeight(10);

        gallery.setOrientation(GalleryOrientation.NAVIGATION_ABOVE_DATA_BELOW);
        GalleryManager galleryManager = new GalleryManager(gallery);
        Assert.assertEquals(118, galleryManager.createNavigationHeight());
    }

    @Test
    public void createNavigationHeightWithBELOWPaginatorType() throws Exception {
        User user = TestUtil.createUserAndLogin("aa");
        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setOrientation(GalleryOrientation.NAVIGATION_ONLY);
        gallery.setRows(3);
        gallery.setCellVerticalMargin(10);
        gallery.setCellHeight(10);

        GalleryManager galleryManager = new GalleryManager(gallery);
        gallery.setOrientation(GalleryOrientation.DATA_ABOVE_NAVIGATION_BELOW);
        Assert.assertEquals(118, galleryManager.createNavigationHeight());
    }

    @Test
    public void createInstance_bySiteId() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        GalleryManager galleryManager = GalleryManager.createInstance(-1, site.getSiteId());
        Assert.assertNotNull(galleryManager);
        Assert.assertNotNull(galleryManager.getName());
        Assert.assertNotNull(galleryManager.getId());
        Assert.assertEquals(-1, galleryManager.getId());
        Assert.assertEquals("Gallery1", galleryManager.getName());
    }

    @Test
    public void createInstance_byGalleryId() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        DraftGallery gallery = TestUtil.createGallery(site);

        GalleryManager galleryManager = GalleryManager.createInstance(gallery.getId(), -1);
        Assert.assertNotNull(galleryManager);
        Assert.assertNotNull(galleryManager.getName());
        Assert.assertNotNull(galleryManager.getId());
        Assert.assertEquals(gallery.getId(), galleryManager.getId());
        Assert.assertEquals(gallery.getName(), galleryManager.getName());
    }

    @Test
    public void createInstance_byGalleryIdAndSiteId() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        DraftGallery gallery = TestUtil.createGallery(site);

        final Site newSite = TestUtil.createSite();

        GalleryManager galleryManager = GalleryManager.createInstance(gallery.getId(), newSite.getSiteId());
        Assert.assertNotNull(galleryManager);
        Assert.assertNotNull(galleryManager.getName());
        Assert.assertNotNull(galleryManager.getId());
        Assert.assertEquals(gallery.getId(), galleryManager.getId());
        Assert.assertEquals(gallery.getName(), galleryManager.getName());
    }


    @Test(expected = GalleryNotFoundException.class)
    public void createInstance_byGalleryIdWithoutGallery() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        GalleryManager.createInstance(10, -1);
    }


    @Test
    public void createDefaultGalleryRequest() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        Page page = TestUtil.createPage(site);
        PageManager pageVersion = TestUtil.createPageVersion(page);
        Widget widget = TestUtil.createWidgetGalleryForPageVersion(pageVersion);

        SaveGalleryRequest request = GalleryManager.createDefaultGalleryRequest(widget.getWidgetId(), widget.getSite(), GalleryManager.CreateGalleryRequestType.GALLERY, null);
        Assert.assertNotNull(request);
        final PaypalSettingsData paypalSettings = request.getGallerySave().getPaypalSettings();
        Assert.assertNotNull(paypalSettings);
        Assert.assertEquals(-1, request.getGalleryId());
        Assert.assertEquals(widget.getWidgetId(), request.getWidgetGalleryId().intValue());
        GalleryEdit galleryEdit = request.getGallerySave();
        Assert.assertNotNull(galleryEdit);
        Assert.assertEquals(3, galleryEdit.getFirstSort());
        Assert.assertEquals(0, galleryEdit.getSecondSort());
        Assert.assertEquals("Gallery1", galleryEdit.getName());
        Assert.assertEquals("", galleryEdit.getNotesComments());
        Assert.assertEquals(null, galleryEdit.getFormId());
        Assert.assertEquals(GallerySortOrder.DESCENDING, galleryEdit.getFirstSortType());
        Assert.assertEquals(GallerySortOrder.ASCENDING, galleryEdit.getSecondSortType());
        Assert.assertEquals(GalleryNavigationPaginatorType.PREVIOUS_NEXT_WITH_NUMBERS, galleryEdit.getNavigationPaginatorType());
        Assert.assertEquals(GalleryDataPaginatorType.PREVIOUS_NEXT, galleryEdit.getDataPaginator().getDataPaginatorType());
        Assert.assertEquals(null, galleryEdit.getDataPaginator().getDataPaginatorArrow());
        Assert.assertEquals(GalleryOrientation.NAVIGATION_ABOVE_DATA_BELOW, galleryEdit.getOrientation());
        Assert.assertEquals("test1", galleryEdit.getOrientationLayout());
        Assert.assertEquals(1, galleryEdit.getRows());
        Assert.assertEquals(12, galleryEdit.getColumns());
        Assert.assertEquals(60, galleryEdit.getThumbnailWidth());
        Assert.assertEquals(50, galleryEdit.getThumbnailHeight());
        Assert.assertEquals(6, galleryEdit.getCellHorizontalMargin());
        Assert.assertEquals(6, galleryEdit.getCellVerticalMargin());
        Assert.assertEquals(66, galleryEdit.getCellWidth());
        Assert.assertEquals(56, galleryEdit.getCellHeight());
        Assert.assertEquals(null, galleryEdit.getDataPageId());
        Assert.assertEquals(-1, galleryEdit.getId());
        Assert.assertEquals(null, galleryEdit.getDataCrossWidgetId());
        Assert.assertEquals(0, galleryEdit.getCellBorderWidth());
        Assert.assertEquals(0, galleryEdit.getCellBorderWidth());
        Assert.assertEquals("transparent", galleryEdit.getBackgroundColor());
        Assert.assertEquals("transparent", galleryEdit.getBorderColor());
        Assert.assertEquals(null, galleryEdit.getBorderStyle());
        Assert.assertEquals(0, galleryEdit.getFormFilterId().intValue());

        List<GalleryItemEdit> items = galleryEdit.getItems();
        Assert.assertNotNull(items);
        Assert.assertEquals(1, items.size());
        Assert.assertEquals(1, items.get(0).getId());
        Assert.assertEquals("", items.get(0).getName());
        Assert.assertEquals(GalleryAlign.CENTER, items.get(0).getAlign());
        Assert.assertEquals(GalleryItemColumn.COLUMN_1, items.get(0).getColumn());
        Assert.assertEquals(800, items.get(0).getWidth().intValue());
        Assert.assertEquals(535, items.get(0).getHeight().intValue());


        List<GalleryLabelEdit> labels = galleryEdit.getLabels();
        Assert.assertNotNull(labels);
        Assert.assertEquals(1, labels.size());
        Assert.assertEquals(1, labels.get(0).getId());
        Assert.assertEquals(GalleryAlign.CENTER, labels.get(0).getAlign());
        Assert.assertEquals(0, labels.get(0).getColumn());
    }

    @Test
    public void saveInTransactionTest() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final GalleryEdit galleryEdit = new GalleryEdit();
        galleryEdit.setVoteSettings(new VoteSettings());
        galleryEdit.setNotesComments("ff");
        galleryEdit.setName("g");
        galleryEdit.setPaypalSettings(new PaypalSettingsData());
        final DraftGallery gallery = new DraftGallery();
        gallery.setSiteId(site.getSiteId());
        final SaveGalleryRequest request = new SaveGalleryRequest();
        request.setGallerySave(galleryEdit);
        request.setWidgetGalleryId(null);

        final GalleryManager galleryManager = GalleryManager.createInstance(request.getGalleryId(), site.getSiteId());
        galleryManager.saveInTransaction(request);

        final DraftGallery findGallery = persistance.getDraftItem(galleryManager.getId());
        Assert.assertNotNull(findGallery);
        Assert.assertEquals("g", findGallery.getName());
        Assert.assertEquals("ff", findGallery.getDescription());
        Assert.assertEquals(true, findGallery.isModified());
    }

    @Test
    public void testIsIncludesVotingModule_true() {
        DraftGallery gallery = new DraftGallery();
        gallery.setIncludesVotingModule(true);
        GalleryManager galleryManager = new GalleryManager(gallery);
        Assert.assertTrue(galleryManager.isIncludesVotingModule());
    }

    @Test
    public void testIsIncludesVotingModule_false() {
        DraftGallery gallery = new DraftGallery();
        gallery.setIncludesVotingModule(false);
        GalleryManager galleryManager = new GalleryManager(gallery);
        Assert.assertFalse(galleryManager.isIncludesVotingModule());
    }

    @Test
    public void testIsIncludesChildSiteLink_true() {
        DraftGallery gallery = new DraftGallery();
        ChildSiteLink childSiteLink = new ChildSiteLink();
        childSiteLink.setShowChildSiteLink(true);
        gallery.setChildSiteLink(childSiteLink);
        GalleryManager galleryManager = new GalleryManager(gallery);
        Assert.assertTrue(galleryManager.isIncludesChildSiteLink());
    }

    @Test
    public void testIsIncludesChildSiteLink_false() {
        DraftGallery gallery = new DraftGallery();
        ChildSiteLink childSiteLink = new ChildSiteLink();
        childSiteLink.setShowChildSiteLink(false);
        gallery.setChildSiteLink(childSiteLink);
        GalleryManager galleryManager = new GalleryManager(gallery);
        Assert.assertFalse(galleryManager.isIncludesChildSiteLink());
    }

    @Test
    public void testGetVideoItemsWithCorrectSize_withCorrectSize() {
        Site site = TestUtil.createSite();
        DraftGallery gallery = TestUtil.createGallery(site);
        DraftForm form = TestUtil.createCustomForm(site);
        DraftFormItem formItemVideo1 = TestUtil.createFormItem(FormItemName.VIDEO_FILE_UPLOAD, form, 0);
        DraftFormItem formItemImage = TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, form, 1);
        DraftFormItem formItemAudio = TestUtil.createFormItem(FormItemName.AUDIO_FILE_UPLOAD, form, 2);
        DraftFormItem formItemVideo2 = TestUtil.createFormItem(FormItemName.VIDEO_FILE_UPLOAD, form, 3);

        DraftGalleryItem galleryItemVideo1 = TestUtil.createGalleryItem(100, 100, gallery, formItemVideo1.getFormItemId());
        DraftGalleryItem galleryItemVideo2 = TestUtil.createGalleryItem(100, 100, gallery, formItemVideo2.getFormItemId());
        TestUtil.createGalleryItem(100, 100, gallery, formItemAudio.getFormItemId());
        TestUtil.createGalleryItem(100, 100, gallery, formItemImage.getFormItemId());

        GalleryManager manager = new GalleryManager(gallery);
        List<GalleryItem> galleryVideoItems = manager.getVideoItemsWithCorrectSize();
        Assert.assertEquals(2, galleryVideoItems.size());
        Assert.assertEquals(galleryItemVideo1, galleryVideoItems.get(0));
        Assert.assertEquals(galleryItemVideo2, galleryVideoItems.get(1));
    }


    @Test
    public void testGetVideoItemsWithCorrectSize_withCorrectSize_withNullInGalleryItemsWidthOrHeight() {
        Site site = TestUtil.createSite();
        DraftGallery gallery = TestUtil.createGallery(site);
        DraftForm form = TestUtil.createCustomForm(site);
        DraftFormItem formItemVideo1 = TestUtil.createFormItem(FormItemName.VIDEO_FILE_UPLOAD, form, 0);
        DraftFormItem formItemImage = TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, form, 1);
        DraftFormItem formItemAudio = TestUtil.createFormItem(FormItemName.AUDIO_FILE_UPLOAD, form, 2);
        DraftFormItem formItemVideo2 = TestUtil.createFormItem(FormItemName.VIDEO_FILE_UPLOAD, form, 3);

        DraftGalleryItem galleryItemVideo1 = TestUtil.createGalleryItem(100, 100, gallery, formItemVideo1.getFormItemId());
        DraftGalleryItem galleryItemVideo2 = TestUtil.createGalleryItem(100, 100, gallery, formItemVideo2.getFormItemId());
        TestUtil.createGalleryItem(null, 100, gallery, formItemVideo2.getFormItemId());
        TestUtil.createGalleryItem(100, null, gallery, formItemVideo2.getFormItemId());
        TestUtil.createGalleryItem(null, null, gallery, formItemVideo2.getFormItemId());
        TestUtil.createGalleryItem(100, 100, gallery, formItemAudio.getFormItemId());
        TestUtil.createGalleryItem(100, 100, gallery, formItemImage.getFormItemId());

        GalleryManager manager = new GalleryManager(gallery);
        List<GalleryItem> galleryVideoItems = manager.getVideoItemsWithCorrectSize();
        Assert.assertEquals(2, galleryVideoItems.size());
        Assert.assertEquals(galleryItemVideo1, galleryVideoItems.get(0));
        Assert.assertEquals(galleryItemVideo2, galleryVideoItems.get(1));
    }


    @Test
    public void testGetVideoItemsWithCorrectSize_withoutCorrectSize() {
        Site site = TestUtil.createSite();
        DraftGallery gallery = TestUtil.createGallery(site);
        DraftForm form = TestUtil.createCustomForm(site);
        DraftFormItem formItemVideo1 = TestUtil.createFormItem(FormItemName.VIDEO_FILE_UPLOAD, form, 0);
        DraftFormItem formItemImage = TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, form, 1);
        DraftFormItem formItemAudio = TestUtil.createFormItem(FormItemName.AUDIO_FILE_UPLOAD, form, 2);
        DraftFormItem formItemVideo2 = TestUtil.createFormItem(FormItemName.VIDEO_FILE_UPLOAD, form, 3);
        DraftFormItem formItemVideo3 = TestUtil.createFormItem(FormItemName.VIDEO_FILE_UPLOAD, form, 4);

        DraftGalleryItem galleryItemVideo1 = TestUtil.createGalleryItem(100, 100, gallery, formItemVideo1.getFormItemId());
        TestUtil.createGalleryItem(100, 0, gallery, formItemVideo2.getFormItemId());
        TestUtil.createGalleryItem(0, 100, gallery, formItemVideo3.getFormItemId());
        TestUtil.createGalleryItem(100, 100, gallery, formItemAudio.getFormItemId());
        TestUtil.createGalleryItem(100, 100, gallery, formItemImage.getFormItemId());

        GalleryManager manager = new GalleryManager(gallery);
        List<GalleryItem> galleryVideoItems = manager.getVideoItemsWithCorrectSize();
        Assert.assertEquals(1, galleryVideoItems.size());
        Assert.assertEquals(galleryItemVideo1, galleryVideoItems.get(0));
    }

    @Test
    public void testCreateVideoFLVByNewSize_withoutExistingLargerVideos() {
        Site site = TestUtil.createSite();
        DraftGallery gallery = TestUtil.createGallery(site);
        DraftForm form = TestUtil.createCustomForm(site);
        DraftFormItem formItemVideo1 = TestUtil.createFormItem(FormItemName.VIDEO_FILE_UPLOAD, form, 0);
        DraftFormItem formItemVideo2 = TestUtil.createFormItem(FormItemName.VIDEO_FILE_UPLOAD, form, 1);
        DraftFormItem formItemImage = TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, form, 2);
        DraftFormItem formItemAudio = TestUtil.createFormItem(FormItemName.AUDIO_FILE_UPLOAD, form, 3);


        DraftGalleryItem galleryItemVideo1 = TestUtil.createGalleryItem(123123, 18645600, gallery, formItemVideo1.getFormItemId());
        DraftGalleryItem galleryItemVideo2 = TestUtil.createGalleryItem(8797853, 876153, gallery, formItemVideo2.getFormItemId());
        TestUtil.createGalleryItem(100, 100, gallery, formItemAudio.getFormItemId());
        TestUtil.createGalleryItem(100, 100, gallery, formItemImage.getFormItemId());


        /*------------------------------------filled form items for formItemVideo1------------------------------------*/
        FilledForm filledForm = TestUtil.createFilledForm(form);
        FilledFormItem filledFormItem1 = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem1.setFormItemId(formItemVideo1.getFormItemId());
        Video video = TestUtil.createVideoForSite("name", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), null);
        filledFormItem1.setValue("" + formVideo.getFormVideoId());
        filledFormItem1.setFilledForm(filledForm);
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem1));

        FilledForm filledForm2 = TestUtil.createFilledForm(form);
        FilledFormItem filledFormItem2 = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem2.setFormItemId(formItemVideo1.getFormItemId());
        Video video2 = TestUtil.createVideoForSite("name", site.getSiteId());
        FormVideo formVideo2 = TestUtil.createFormVideo(video2.getVideoId(), null);
        filledFormItem2.setValue("" + formVideo2.getFormVideoId());
        filledFormItem2.setFilledForm(filledForm2);
        filledForm2.setFilledFormItems(Arrays.asList(filledFormItem2));
        /*------------------------------------filled form items for formItemVideo1------------------------------------*/


        /*------------------------------------filled form items for formItemVideo2------------------------------------*/
        FilledForm filledForm3 = TestUtil.createFilledForm(form);
        FilledFormItem filledFormItem3 = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem3.setFormItemId(formItemVideo2.getFormItemId());
        Video video3 = TestUtil.createVideoForSite("name", site.getSiteId());
        FormVideo formVideo3 = TestUtil.createFormVideo(video3.getVideoId(), null);
        filledFormItem3.setValue("" + formVideo3.getFormVideoId());
        filledFormItem3.setFilledForm(filledForm3);
        filledForm3.setFilledFormItems(Arrays.asList(filledFormItem3));

        FilledForm filledForm4 = TestUtil.createFilledForm(form);
        FilledFormItem filledFormItem4 = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem4.setFormItemId(formItemVideo2.getFormItemId());
        Video video4 = TestUtil.createVideoForSite("name", site.getSiteId());
        FormVideo formVideo4 = TestUtil.createFormVideo(video4.getVideoId(), null);
        filledFormItem4.setValue("" + formVideo4.getFormVideoId());
        filledFormItem4.setFilledForm(filledForm4);
        filledForm4.setFilledFormItems(Arrays.asList(filledFormItem4));
        /*------------------------------------filled form items for formItemVideo2------------------------------------*/

        final List<FlvVideo> oldVideo = new ArrayList<FlvVideo>(((PersistanceMock) persistance).getAllFlvVideo());
        GalleryManager manager = new GalleryManager(gallery);
        manager.createVideoFLVByNewSize();
        final List<FlvVideo> flvVideos = new ArrayList<FlvVideo>(((PersistanceMock) persistance).getAllFlvVideo());
        flvVideos.removeAll(oldVideo);
        Assert.assertEquals(8, flvVideos.size());

        Assert.assertEquals(galleryItemVideo1.getWidth(), flvVideos.get(0).getWidth());
        Assert.assertEquals(galleryItemVideo1.getHeight(), flvVideos.get(0).getHeight());

        Assert.assertEquals(Math.round(galleryItemVideo1.getWidth() * FlvVideoManager.LARGER_VIDEO_MULTIPLIER), flvVideos.get(1).getWidth().intValue());
        Assert.assertEquals(Math.round(galleryItemVideo1.getHeight() * FlvVideoManager.LARGER_VIDEO_MULTIPLIER), flvVideos.get(1).getHeight().intValue());

        Assert.assertEquals(galleryItemVideo1.getWidth(), flvVideos.get(2).getWidth());
        Assert.assertEquals(galleryItemVideo1.getHeight(), flvVideos.get(2).getHeight());

        Assert.assertEquals(Math.round(galleryItemVideo1.getWidth() * FlvVideoManager.LARGER_VIDEO_MULTIPLIER), flvVideos.get(3).getWidth().intValue());
        Assert.assertEquals(Math.round(galleryItemVideo1.getHeight() * FlvVideoManager.LARGER_VIDEO_MULTIPLIER), flvVideos.get(3).getHeight().intValue());

        Assert.assertEquals(galleryItemVideo2.getWidth(), flvVideos.get(4).getWidth());
        Assert.assertEquals(galleryItemVideo2.getHeight(), flvVideos.get(4).getHeight());

        Assert.assertEquals(Math.round(galleryItemVideo2.getWidth() * FlvVideoManager.LARGER_VIDEO_MULTIPLIER), flvVideos.get(5).getWidth().intValue());
        Assert.assertEquals(Math.round(galleryItemVideo2.getHeight() * FlvVideoManager.LARGER_VIDEO_MULTIPLIER), flvVideos.get(5).getHeight().intValue());

        Assert.assertEquals(galleryItemVideo2.getWidth(), flvVideos.get(6).getWidth());
        Assert.assertEquals(galleryItemVideo2.getHeight(), flvVideos.get(6).getHeight());

        Assert.assertEquals(Math.round(galleryItemVideo2.getWidth() * FlvVideoManager.LARGER_VIDEO_MULTIPLIER), flvVideos.get(7).getWidth().intValue());
        Assert.assertEquals(Math.round(galleryItemVideo2.getHeight() * FlvVideoManager.LARGER_VIDEO_MULTIPLIER), flvVideos.get(7).getHeight().intValue());


        Assert.assertEquals(video.getVideoId(), flvVideos.get(0).getSourceVideoId());
        Assert.assertEquals(video.getVideoId(), flvVideos.get(1).getSourceVideoId());
        Assert.assertEquals(video2.getVideoId(), flvVideos.get(2).getSourceVideoId());
        Assert.assertEquals(video2.getVideoId(), flvVideos.get(3).getSourceVideoId());
        Assert.assertEquals(video3.getVideoId(), flvVideos.get(4).getSourceVideoId());
        Assert.assertEquals(video3.getVideoId(), flvVideos.get(5).getSourceVideoId());
        Assert.assertEquals(video4.getVideoId(), flvVideos.get(6).getSourceVideoId());
        Assert.assertEquals(video4.getVideoId(), flvVideos.get(7).getSourceVideoId());
    }

    @Test
    public void testCreateVideoFLVByNewSize_withOneExistingNormalFlvVideoFile() {
        Site site = TestUtil.createSite();
        DraftGallery gallery = TestUtil.createGallery(site);
        DraftForm form = TestUtil.createCustomForm(site);
        DraftFormItem formItemVideo1 = TestUtil.createFormItem(FormItemName.VIDEO_FILE_UPLOAD, form, 0);
        DraftFormItem formItemVideo2 = TestUtil.createFormItem(FormItemName.VIDEO_FILE_UPLOAD, form, 1);
        DraftFormItem formItemImage = TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, form, 2);
        DraftFormItem formItemAudio = TestUtil.createFormItem(FormItemName.AUDIO_FILE_UPLOAD, form, 3);


        DraftGalleryItem galleryItemVideo1 = TestUtil.createGalleryItem(123123, 18645600, gallery, formItemVideo1.getFormItemId());
        DraftGalleryItem galleryItemVideo2 = TestUtil.createGalleryItem(8797853, 876153, gallery, formItemVideo2.getFormItemId());
        TestUtil.createGalleryItem(100, 100, gallery, formItemAudio.getFormItemId());
        TestUtil.createGalleryItem(100, 100, gallery, formItemImage.getFormItemId());


        /*------------------------------------filled form items for formItemVideo1------------------------------------*/
        FilledForm filledForm = TestUtil.createFilledForm(form);
        FilledFormItem filledFormItem1 = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem1.setFormItemId(formItemVideo1.getFormItemId());
        Video video = TestUtil.createVideoForSite("name", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), null);
        filledFormItem1.setValue("" + formVideo.getFormVideoId());
        filledFormItem1.setFilledForm(filledForm);
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem1));


        FilledForm filledForm2 = TestUtil.createFilledForm(form);
        FilledFormItem filledFormItem2 = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem2.setFormItemId(formItemVideo1.getFormItemId());
        Video video2 = TestUtil.createVideoForSite("name", site.getSiteId());
        FormVideo formVideo2 = TestUtil.createFormVideo(video2.getVideoId(), null);
        filledFormItem2.setValue("" + formVideo2.getFormVideoId());
        filledFormItem2.setFilledForm(filledForm2);
        filledForm2.setFilledFormItems(Arrays.asList(filledFormItem2));
        /*------------------------------------filled form items for formItemVideo1------------------------------------*/


        /*------------------------------------filled form items for formItemVideo2------------------------------------*/
        FilledForm filledForm3 = TestUtil.createFilledForm(form);
        FilledFormItem filledFormItem3 = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem3.setFormItemId(formItemVideo2.getFormItemId());
        Video video3 = TestUtil.createVideoForSite("name", site.getSiteId());
        FormVideo formVideo3 = TestUtil.createFormVideo(video3.getVideoId(), null);
        filledFormItem3.setValue("" + formVideo3.getFormVideoId());
        filledFormItem3.setFilledForm(filledForm3);
        filledForm3.setFilledFormItems(Arrays.asList(filledFormItem3));

        FilledForm filledForm4 = TestUtil.createFilledForm(form);
        FilledFormItem filledFormItem4 = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem4.setFormItemId(formItemVideo2.getFormItemId());
        Video video4 = TestUtil.createVideoForSite("name", site.getSiteId());
        FormVideo formVideo4 = TestUtil.createFormVideo(video4.getVideoId(), null);
        filledFormItem4.setValue("" + formVideo4.getFormVideoId());
        filledFormItem4.setFilledForm(filledForm4);
        filledForm4.setFilledFormItems(Arrays.asList(filledFormItem4));

        TestUtil.createVideoFLV(video4.getVideoId(), galleryItemVideo2.getWidth(), galleryItemVideo2.getHeight());

        /*------------------------------------filled form items for formItemVideo2------------------------------------*/


        final List<FlvVideo> oldVideo = new ArrayList<FlvVideo>(((PersistanceMock) persistance).getAllFlvVideo());
        GalleryManager manager = new GalleryManager(gallery);
        manager.createVideoFLVByNewSize();
        final List<FlvVideo> flvVideos = ((PersistanceMock) persistance).getAllFlvVideo();
        flvVideos.removeAll(oldVideo);

        Assert.assertEquals(7, flvVideos.size());

        Assert.assertEquals(galleryItemVideo1.getWidth(), flvVideos.get(0).getWidth());
        Assert.assertEquals(galleryItemVideo1.getHeight(), flvVideos.get(0).getHeight());

        Assert.assertEquals(Math.round(galleryItemVideo1.getWidth() * FlvVideoManager.LARGER_VIDEO_MULTIPLIER), flvVideos.get(1).getWidth().intValue());
        Assert.assertEquals(Math.round(galleryItemVideo1.getHeight() * FlvVideoManager.LARGER_VIDEO_MULTIPLIER), flvVideos.get(1).getHeight().intValue());

        Assert.assertEquals(galleryItemVideo1.getWidth(), flvVideos.get(2).getWidth());
        Assert.assertEquals(galleryItemVideo1.getHeight(), flvVideos.get(2).getHeight());

        Assert.assertEquals(Math.round(galleryItemVideo1.getWidth() * FlvVideoManager.LARGER_VIDEO_MULTIPLIER), flvVideos.get(3).getWidth().intValue());
        Assert.assertEquals(Math.round(galleryItemVideo1.getHeight() * FlvVideoManager.LARGER_VIDEO_MULTIPLIER), flvVideos.get(3).getHeight().intValue());

        Assert.assertEquals(galleryItemVideo2.getWidth(), flvVideos.get(4).getWidth());
        Assert.assertEquals(galleryItemVideo2.getHeight(), flvVideos.get(4).getHeight());

        Assert.assertEquals(Math.round(galleryItemVideo2.getWidth() * FlvVideoManager.LARGER_VIDEO_MULTIPLIER), flvVideos.get(5).getWidth().intValue());
        Assert.assertEquals(Math.round(galleryItemVideo2.getHeight() * FlvVideoManager.LARGER_VIDEO_MULTIPLIER), flvVideos.get(5).getHeight().intValue());

        Assert.assertEquals(Math.round(galleryItemVideo2.getWidth() * FlvVideoManager.LARGER_VIDEO_MULTIPLIER), flvVideos.get(6).getWidth().intValue());
        Assert.assertEquals(Math.round(galleryItemVideo2.getHeight() * FlvVideoManager.LARGER_VIDEO_MULTIPLIER), flvVideos.get(6).getHeight().intValue());


        Assert.assertEquals(video.getVideoId(), flvVideos.get(0).getSourceVideoId());
        Assert.assertEquals(video.getVideoId(), flvVideos.get(1).getSourceVideoId());
        Assert.assertEquals(video2.getVideoId(), flvVideos.get(2).getSourceVideoId());
        Assert.assertEquals(video2.getVideoId(), flvVideos.get(3).getSourceVideoId());
        Assert.assertEquals(video3.getVideoId(), flvVideos.get(4).getSourceVideoId());
        Assert.assertEquals(video3.getVideoId(), flvVideos.get(5).getSourceVideoId());
        Assert.assertEquals(video4.getVideoId(), flvVideos.get(6).getSourceVideoId());
    }


    @Test
    public void testCreateVideoFLVByNewSize_withOneExistingLargeFlvVideoFile() {
        Site site = TestUtil.createSite();
        DraftGallery gallery = TestUtil.createGallery(site);
        DraftForm form = TestUtil.createCustomForm(site);
        DraftFormItem formItemVideo1 = TestUtil.createFormItem(FormItemName.VIDEO_FILE_UPLOAD, form, 0);
        DraftFormItem formItemVideo2 = TestUtil.createFormItem(FormItemName.VIDEO_FILE_UPLOAD, form, 1);
        DraftFormItem formItemImage = TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, form, 2);
        DraftFormItem formItemAudio = TestUtil.createFormItem(FormItemName.AUDIO_FILE_UPLOAD, form, 3);


        DraftGalleryItem galleryItemVideo1 = TestUtil.createGalleryItem(123123, 18645600, gallery, formItemVideo1.getFormItemId());
        DraftGalleryItem galleryItemVideo2 = TestUtil.createGalleryItem(8797853, 876153, gallery, formItemVideo2.getFormItemId());
        TestUtil.createGalleryItem(100, 100, gallery, formItemAudio.getFormItemId());
        TestUtil.createGalleryItem(100, 100, gallery, formItemImage.getFormItemId());


        /*------------------------------------filled form items for formItemVideo1------------------------------------*/
        FilledForm filledForm = TestUtil.createFilledForm(form);
        FilledFormItem filledFormItem1 = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem1.setFormItemId(formItemVideo1.getFormItemId());
        Video video = TestUtil.createVideoForSite("name", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), null);
        filledFormItem1.setValue("" + formVideo.getFormVideoId());
        filledFormItem1.setFilledForm(filledForm);
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem1));


        FilledForm filledForm2 = TestUtil.createFilledForm(form);
        FilledFormItem filledFormItem2 = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem2.setFormItemId(formItemVideo1.getFormItemId());
        Video video2 = TestUtil.createVideoForSite("name", site.getSiteId());
        FormVideo formVideo2 = TestUtil.createFormVideo(video2.getVideoId(), null);
        filledFormItem2.setValue("" + formVideo2.getFormVideoId());
        filledFormItem2.setFilledForm(filledForm2);
        filledForm2.setFilledFormItems(Arrays.asList(filledFormItem2));
        /*------------------------------------filled form items for formItemVideo1------------------------------------*/


        /*------------------------------------filled form items for formItemVideo2------------------------------------*/
        FilledForm filledForm3 = TestUtil.createFilledForm(form);
        FilledFormItem filledFormItem3 = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem3.setFormItemId(formItemVideo2.getFormItemId());
        Video video3 = TestUtil.createVideoForSite("name", site.getSiteId());
        FormVideo formVideo3 = TestUtil.createFormVideo(video3.getVideoId(), null);
        filledFormItem3.setValue("" + formVideo3.getFormVideoId());
        filledFormItem3.setFilledForm(filledForm3);
        filledForm3.setFilledFormItems(Arrays.asList(filledFormItem3));

        FilledForm filledForm4 = TestUtil.createFilledForm(form);
        FilledFormItem filledFormItem4 = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem4.setFormItemId(formItemVideo2.getFormItemId());
        Video video4 = TestUtil.createVideoForSite("name", site.getSiteId());
        FormVideo formVideo4 = TestUtil.createFormVideo(video4.getVideoId(), null);
        filledFormItem4.setValue("" + formVideo4.getFormVideoId());
        filledFormItem4.setFilledForm(filledForm4);
        filledForm4.setFilledFormItems(Arrays.asList(filledFormItem4));

        TestUtil.createVideoFLV(video4.getVideoId(), Math.round(galleryItemVideo2.getWidth() * FlvVideoManager.LARGER_VIDEO_MULTIPLIER),
                Math.round(galleryItemVideo2.getHeight() * FlvVideoManager.LARGER_VIDEO_MULTIPLIER));

        /*------------------------------------filled form items for formItemVideo2------------------------------------*/


        final List<FlvVideo> oldVideo = new ArrayList<FlvVideo>(((PersistanceMock) persistance).getAllFlvVideo());
        GalleryManager manager = new GalleryManager(gallery);
        manager.createVideoFLVByNewSize();
        final List<FlvVideo> flvVideos = ((PersistanceMock) persistance).getAllFlvVideo();
        flvVideos.removeAll(oldVideo);

        Assert.assertEquals(7, flvVideos.size());

        Assert.assertEquals(galleryItemVideo1.getWidth(), flvVideos.get(0).getWidth());
        Assert.assertEquals(galleryItemVideo1.getHeight(), flvVideos.get(0).getHeight());

        Assert.assertEquals(Math.round(galleryItemVideo1.getWidth() * FlvVideoManager.LARGER_VIDEO_MULTIPLIER), flvVideos.get(1).getWidth().intValue());
        Assert.assertEquals(Math.round(galleryItemVideo1.getHeight() * FlvVideoManager.LARGER_VIDEO_MULTIPLIER), flvVideos.get(1).getHeight().intValue());

        Assert.assertEquals(galleryItemVideo1.getWidth(), flvVideos.get(2).getWidth());
        Assert.assertEquals(galleryItemVideo1.getHeight(), flvVideos.get(2).getHeight());

        Assert.assertEquals(Math.round(galleryItemVideo1.getWidth() * FlvVideoManager.LARGER_VIDEO_MULTIPLIER), flvVideos.get(3).getWidth().intValue());
        Assert.assertEquals(Math.round(galleryItemVideo1.getHeight() * FlvVideoManager.LARGER_VIDEO_MULTIPLIER), flvVideos.get(3).getHeight().intValue());

        Assert.assertEquals(galleryItemVideo2.getWidth(), flvVideos.get(4).getWidth());
        Assert.assertEquals(galleryItemVideo2.getHeight(), flvVideos.get(4).getHeight());

        Assert.assertEquals(Math.round(galleryItemVideo2.getWidth() * FlvVideoManager.LARGER_VIDEO_MULTIPLIER), flvVideos.get(5).getWidth().intValue());
        Assert.assertEquals(Math.round(galleryItemVideo2.getHeight() * FlvVideoManager.LARGER_VIDEO_MULTIPLIER), flvVideos.get(5).getHeight().intValue());

        Assert.assertEquals(galleryItemVideo2.getWidth(), flvVideos.get(6).getWidth());
        Assert.assertEquals(galleryItemVideo2.getHeight(), flvVideos.get(6).getHeight());


        Assert.assertEquals(video.getVideoId(), flvVideos.get(0).getSourceVideoId());
        Assert.assertEquals(video.getVideoId(), flvVideos.get(1).getSourceVideoId());
        Assert.assertEquals(video2.getVideoId(), flvVideos.get(2).getSourceVideoId());
        Assert.assertEquals(video2.getVideoId(), flvVideos.get(3).getSourceVideoId());
        Assert.assertEquals(video3.getVideoId(), flvVideos.get(4).getSourceVideoId());
        Assert.assertEquals(video3.getVideoId(), flvVideos.get(5).getSourceVideoId());
        Assert.assertEquals(video4.getVideoId(), flvVideos.get(6).getSourceVideoId());
    }

    @Test
    public void testGetRegistrationFormIdForVoters() {
        final Site site = TestUtil.createSite();
        final DraftGallery gallery = TestUtil.createGallery(site);
        gallery.getVoteSettings().setRegistrationFormIdForVoters(10);
        Assert.assertEquals(10, new GalleryManager(gallery).getRegistrationFormIdForVoters());
    }

    @Test
    public void testGetRegistrationFormIdForVoters_withoutVoteSettings() {
        final Site site = TestUtil.createSite();
        final DraftGallery gallery = TestUtil.createGallery(site);
        gallery.setVoteSettings(null);
        Assert.assertEquals(-1, new GalleryManager(gallery).getRegistrationFormIdForVoters());
    }

    @Test
    public void testGetRegistrationFormIdForVoters_withoutRegistrationFormIdForVoters() {
        final Site site = TestUtil.createSite();
        final DraftGallery gallery = TestUtil.createGallery(site);
        gallery.getVoteSettings().setRegistrationFormIdForVoters(null);
        Assert.assertEquals(-1, new GalleryManager(gallery).getRegistrationFormIdForVoters());
    }

    @Test
    public void testGetFilledForms() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createRegistrationForm();
        FilledForm filledForm1 = TestUtil.createFilledForm(form);
        FilledForm filledForm2 = TestUtil.createFilledForm(form);
        FilledForm filledForm3 = TestUtil.createFilledForm(form);
        FilledForm filledForm4 = TestUtil.createFilledForm(TestUtil.createRegistrationForm());
        DraftGallery gallery = TestUtil.createGallery(site.getSiteId(), "", "", form);
        GalleryManager galleryManager = new GalleryManager(gallery);
        List<FilledForm> filledForms = galleryManager.getFilledForms();
        Assert.assertEquals(3, filledForms.size());
        Assert.assertTrue(filledForms.contains(filledForm1));
        Assert.assertTrue(filledForms.contains(filledForm2));
        Assert.assertTrue(filledForms.contains(filledForm3));
        Assert.assertFalse(filledForms.contains(filledForm4));
    }


    @Test
    public void testGetFilledFormsIds() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createRegistrationForm();
        FilledForm filledForm1 = TestUtil.createFilledForm(form);
        FilledForm filledForm2 = TestUtil.createFilledForm(form);
        FilledForm filledForm3 = TestUtil.createFilledForm(form);
        FilledForm filledForm4 = TestUtil.createFilledForm(TestUtil.createRegistrationForm());
        DraftGallery gallery = TestUtil.createGallery(site.getSiteId(), "", "", form);
        GalleryManager galleryManager = new GalleryManager(gallery);
        List<Integer> filledFormIds = galleryManager.getFilledFormsIds();
        Assert.assertEquals(3, filledFormIds.size());
        Assert.assertTrue(filledFormIds.contains(filledForm1.getFilledFormId()));
        Assert.assertTrue(filledFormIds.contains(filledForm2.getFilledFormId()));
        Assert.assertTrue(filledFormIds.contains(filledForm3.getFilledFormId()));
        Assert.assertFalse(filledFormIds.contains(filledForm4.getFilledFormId()));
    }

    @Test
    public void testIsIncludesPaypalSettings() {
        DraftGallery gallery = TestUtil.createGallery(new Site());
        gallery.getPaypalSettings().setEnable(true);

        GalleryManager galleryManager = new GalleryManager(gallery);

        Assert.assertTrue(galleryManager.isIncludesPaypalSettings());
    }

    @Test
    public void testGetPaypalSettings() {
        DraftGallery gallery = TestUtil.createGallery(new Site());

        GalleryManager galleryManager = new GalleryManager(gallery);

        Assert.assertEquals(gallery.getPaypalSettings(), galleryManager.getPaypalSettings());
    }

    @Test
    public void testFindSuitableItemInUsersFormToConnect_email() {
        DraftGallery gallery = TestUtil.createGallery(new Site());

        GalleryManager galleryManager = new GalleryManager(gallery);

        DraftForm registrationForm = TestUtil.createRegistrationForm();

        final List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        DraftFormItem firstNameItem = TestUtil.createFormItem(FormItemName.FIRST_NAME, 0);
        ServiceLocator.getPersistance().putFormItem(firstNameItem);
        formItems.add(firstNameItem);

        DraftFormItem lastNameItem = TestUtil.createFormItem(FormItemName.LAST_NAME, 0);
        ServiceLocator.getPersistance().putFormItem(lastNameItem);
        formItems.add(lastNameItem);

        DraftFormItem emailItem = TestUtil.createFormItem(FormItemName.EMAIL, 0);
        ServiceLocator.getPersistance().putFormItem(emailItem);
        formItems.add(emailItem);

        registrationForm.setFormItems(formItems);

        final FormItem formItem = galleryManager.findSuitableItemInUsersFormToConnect(registrationForm.getId());
        Assert.assertEquals(emailItem.getFormItemId(), formItem.getFormItemId());
    }

    @Test
    public void testFindSuitableItemInUsersFormToConnect_firstName() {
        DraftGallery gallery = TestUtil.createGallery(new Site());

        GalleryManager galleryManager = new GalleryManager(gallery);

        DraftForm registrationForm = TestUtil.createRegistrationForm();

        final List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        DraftFormItem firstNameItem = TestUtil.createFormItem(FormItemName.FIRST_NAME, 0);
        ServiceLocator.getPersistance().putFormItem(firstNameItem);
        formItems.add(firstNameItem);

        DraftFormItem lastNameItem = TestUtil.createFormItem(FormItemName.LAST_NAME, 0);
        ServiceLocator.getPersistance().putFormItem(lastNameItem);
        formItems.add(lastNameItem);

        registrationForm.setFormItems(formItems);

        final FormItem formItem = galleryManager.findSuitableItemInUsersFormToConnect(registrationForm.getId());
        Assert.assertEquals(firstNameItem.getFormItemId(), formItem.getFormItemId());
    }

    @Test
    public void testFindSuitableItemInUsersFormToConnect_lastName() {
        DraftGallery gallery = TestUtil.createGallery(new Site());

        GalleryManager galleryManager = new GalleryManager(gallery);

        DraftForm registrationForm = TestUtil.createRegistrationForm();

        final List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        DraftFormItem lastNameItem = TestUtil.createFormItem(FormItemName.LAST_NAME, 0);
        ServiceLocator.getPersistance().putFormItem(lastNameItem);
        formItems.add(lastNameItem);

        DraftFormItem item = TestUtil.createFormItem(FormItemName.SCREEN_NAME_NICKNAME, 0);
        ServiceLocator.getPersistance().putFormItem(item);
        formItems.add(item);

        registrationForm.setFormItems(formItems);

        final FormItem formItem = galleryManager.findSuitableItemInUsersFormToConnect(registrationForm.getId());
        Assert.assertEquals(lastNameItem.getFormItemId(), formItem.getFormItemId());
    }
    
    @Test
    public void testFindSuitableItemInUsersFormToConnect_firstByPositionInList() {
        DraftGallery gallery = TestUtil.createGallery(new Site());

        GalleryManager galleryManager = new GalleryManager(gallery);

        DraftForm registrationForm = TestUtil.createRegistrationForm();

        final List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        DraftFormItem firstByPosition = TestUtil.createFormItem(FormItemName.NAME, 0);
        ServiceLocator.getPersistance().putFormItem(firstByPosition);
        formItems.add(firstByPosition);

        DraftFormItem item = TestUtil.createFormItem(FormItemName.SCREEN_NAME_NICKNAME, 0);
        ServiceLocator.getPersistance().putFormItem(item);
        formItems.add(item);

        registrationForm.setFormItems(formItems);

        final FormItem formItem = galleryManager.findSuitableItemInUsersFormToConnect(registrationForm.getId());
        Assert.assertEquals(firstByPosition.getFormItemId(), formItem.getFormItemId());
    }

    private final Persistance persistance = ServiceLocator.getPersistance();

}
