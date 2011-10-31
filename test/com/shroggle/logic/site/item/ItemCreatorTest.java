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
package com.shroggle.logic.site.item;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.Config;
import com.shroggle.util.international.International;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Locale;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class ItemCreatorTest {

    @Test
    public void testCreateItemFromUserSite() {
        final User user = TestUtil.createUser();
        TestUtil.loginUser(user);
        final Site site = TestUtil.createSite();
        final Site otherSite = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        TestUtil.createUserOnSiteRightActiveAdmin(user, otherSite);
        final WidgetItem widget = TestUtil.createWidgetItemWithPageAndPageVersion(site);

        Assert.assertNull(widget.getDraftItem());

        final DraftForm form = TestUtil.createCustomForm(otherSite);

        final ItemCreatorRequest request = new ItemCreatorRequest(form.getId(), false,
                ItemType.CUSTOM_FORM, site);

        widget.setDraftItem(ItemCreator.create(request));

        Assert.assertNotNull(widget.getDraftItem());
        DraftCustomForm customForm = (DraftCustomForm) widget.getDraftItem();
        Assert.assertNotNull(customForm);
        Assert.assertEquals(otherSite.getSiteId(), customForm.getSiteId());
        //Testing that our service will create EDIT rights for item.
        final SiteOnItem siteOnItemRight = ServiceLocator.getPersistance().getSiteOnItemRightBySiteIdItemIdAndType(
                site.getId(), customForm.getId(), ItemType.CUSTOM_FORM);
        Assert.assertNotNull(siteOnItemRight);
        Assert.assertEquals(SiteOnItemRightType.EDIT, siteOnItemRight.getType());
    }

    @Test
    public void testCreateItemNOTFromUserSiteWithoutRights() {
        final User user = TestUtil.createUser();
        TestUtil.loginUser(user);
        final Site site = TestUtil.createSite();
        final Site otherSite = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final WidgetItem widget = TestUtil.createWidgetItemWithPageAndPageVersion(site);

        Assert.assertNull(widget.getDraftItem());

        final DraftForm form = TestUtil.createCustomForm(otherSite);

        final ItemCreatorRequest request = new ItemCreatorRequest(form.getId(), false,
                ItemType.CUSTOM_FORM, site);

        final DraftItem item = ItemCreator.create(request);
        Assert.assertNull(item);
    }

    @Test
    public void testCreateItemNOTFromUserSiteWithRights() {
        final User user = TestUtil.createUser();
        TestUtil.loginUser(user);
        final Site site = TestUtil.createSite();
        final Site otherSite = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final WidgetItem widget = TestUtil.createWidgetItemWithPageAndPageVersion(site);

        Assert.assertNull(widget.getDraftItem());

        final DraftForm form = TestUtil.createCustomForm(otherSite);

        TestUtil.createSiteOnItemRight(site, form, SiteOnItemRightType.READ);

        final ItemCreatorRequest request = new ItemCreatorRequest(form.getId(), false,
                ItemType.CUSTOM_FORM, site);

        widget.setDraftItem(ItemCreator.create(request));

        Assert.assertNotNull(widget.getDraftItem());
        DraftCustomForm customForm = (DraftCustomForm) widget.getDraftItem();
        Assert.assertNotNull(customForm);
        Assert.assertEquals(otherSite.getSiteId(), customForm.getSiteId());

        final SiteOnItem siteOnItemRight = ServiceLocator.getPersistance().getSiteOnItemRightBySiteIdItemIdAndType(
                site.getId(), customForm.getId(), ItemType.CUSTOM_FORM);
        Assert.assertNotNull(siteOnItemRight);
        Assert.assertEquals(SiteOnItemRightType.READ, siteOnItemRight.getType());
    }

    @Test
    public void testCreate_AdvancedSearch() {
        Site site = TestUtil.createSite();
        WidgetItem widget = TestUtil.createWidgetItemWithPageAndPageVersion(site);

        Assert.assertNull(widget.getDraftItem());

        widget.setDraftItem(ItemCreator.create(new ItemCreatorRequest(ItemType.ADVANCED_SEARCH, site)));

        Assert.assertNotNull(widget.getDraftItem());
        DraftAdvancedSearch advancedSearch = (DraftAdvancedSearch) widget.getDraftItem();
        Assert.assertNotNull(advancedSearch);
        Assert.assertEquals(site.getSiteId(), advancedSearch.getSiteId());
        Assert.assertEquals(-1, advancedSearch.getGalleryId());
        Assert.assertEquals("Advanced Search1", advancedSearch.getName());
    }


    @Test
    public void testCreate_BlogSummary() {
        Site site = TestUtil.createSite();
        WidgetItem widget = TestUtil.createWidgetItemWithPageAndPageVersion(site);

        Assert.assertNull(widget.getDraftItem());

        widget.setDraftItem(ItemCreator.create(new ItemCreatorRequest(ItemType.BLOG_SUMMARY, site)));

        Assert.assertNotNull(widget.getDraftItem());
        DraftBlogSummary blogSummary = (DraftBlogSummary) (widget.getDraftItem());
        Assert.assertNotNull(blogSummary);
        Assert.assertEquals(site.getSiteId(), blogSummary.getSiteId());
        Assert.assertEquals("Blog Summary1", blogSummary.getName());
    }

    @Test
    public void testCreate_ContactUs() {
        User user = TestUtil.createUser();
        user.setEmail("cool email");
        TestUtil.loginUser(user);
        Site site = TestUtil.createSite();
        WidgetItem widget = TestUtil.createWidgetItemWithPageAndPageVersion(site);

        Assert.assertNull(widget.getDraftItem());

        widget.setDraftItem(ItemCreator.create(new ItemCreatorRequest(ItemType.CONTACT_US, site)));

        Assert.assertNotNull(widget.getDraftItem());
        DraftContactUs draftContactUs = (DraftContactUs) (widget.getDraftItem());
        Assert.assertNotNull(draftContactUs);
        Assert.assertEquals(site.getSiteId(), draftContactUs.getSiteId());
        Assert.assertEquals("Contact Us1", draftContactUs.getName());
        Assert.assertEquals("cool email", draftContactUs.getEmail());
    }

    @Test
    public void testCreate_ContactUs_WithoutLoginedUser() {
        Site site = TestUtil.createSite();
        WidgetItem widget = TestUtil.createWidgetItemWithPageAndPageVersion(site);

        Assert.assertNull(widget.getDraftItem());

        widget.setDraftItem(ItemCreator.create(new ItemCreatorRequest(ItemType.CONTACT_US, site)));

        Assert.assertNotNull(widget.getDraftItem());
        DraftContactUs draftContactUs = (DraftContactUs) (widget.getDraftItem());
        Assert.assertNotNull(draftContactUs);
        Assert.assertEquals(site.getSiteId(), draftContactUs.getSiteId());
        Assert.assertEquals("Contact Us1", draftContactUs.getName());
        Assert.assertEquals("", draftContactUs.getEmail());
    }

    @Test
    public void testCreate_CustomForm() {
        Site site = TestUtil.createSite();
        Page page = TestUtil.createPage(site);
        PageManager pageVersion = TestUtil.createPageVersion(page);
        WidgetItem widget = TestUtil.createWidgetCustomForm(null);
        pageVersion.addWidget(widget);

        junit.framework.Assert.assertNull(widget.getDraftItem());

        widget.setDraftItem(ItemCreator.create(new ItemCreatorRequest(ItemType.CUSTOM_FORM, site)));

        junit.framework.Assert.assertNotNull(widget.getDraftItem());
        DraftCustomForm form = (DraftCustomForm) (widget.getDraftItem());
        junit.framework.Assert.assertNotNull(form);
        junit.framework.Assert.assertEquals(site.getSiteId(), form.getSiteId());
        junit.framework.Assert.assertEquals("Forms & Registrations1", form.getName());
        junit.framework.Assert.assertNotNull(form.getFormItems());
        junit.framework.Assert.assertEquals(2, form.getFormItems().size());
    }

    // todo we must check here all those gallery default settings. For all three types of gallery (GALLERY, VOTING, GALLERY_DATA)

    @Test
    public void testCreate_Gallery() {
        User user = TestUtil.createUserAndLogin();
        Site site = TestUtil.createSite();
        DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm();
        site.setDefaultFormId(registrationForm.getFormId());
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        Page page = TestUtil.createPage(site);
        PageManager pageVersion = TestUtil.createPageVersion(page);
        WidgetItem widget = TestUtil.createWidgetCustomForm(null);
        pageVersion.addWidget(widget);

        Assert.assertNull(widget.getDraftItem());

        widget.setDraftItem(ItemCreator.create(new ItemCreatorRequest(ItemType.VOTING, site)));

        Assert.assertNotNull(widget.getDraftItem());
        DraftGallery gallery = (DraftGallery) widget.getDraftItem();
        Assert.assertNotNull(gallery);
        Assert.assertEquals(site.getSiteId(), gallery.getSiteId());
        Assert.assertEquals("Gallery1", gallery.getName());
        Assert.assertNotNull(gallery.getVoteSettings().getRegistrationFormIdForVoters());
        Assert.assertEquals(site.getDefaultFormId(), gallery.getVoteSettings().getRegistrationFormIdForVoters().intValue());
    }

    @Test
    public void testCreate_Login() {
        Site site = TestUtil.createSite();
        Page page = TestUtil.createPage(site);
        PageManager pageVersion = TestUtil.createPageVersion(page);
        WidgetItem widget = TestUtil.createWidgetCustomForm(null);
        pageVersion.addWidget(widget);

        Assert.assertNull(widget.getDraftItem());

        widget.setDraftItem(ItemCreator.create(new ItemCreatorRequest(ItemType.LOGIN, site)));

        Assert.assertNotNull(widget.getDraftItem());
    }

    @Test
    public void testCreate_ManageVotes() {
        Site site = TestUtil.createSite();
        WidgetItem widget = TestUtil.createWidgetItemWithPageAndPageVersion(site);

        Assert.assertNull(widget.getDraftItem());

        widget.setDraftItem(ItemCreator.create(new ItemCreatorRequest(ItemType.MANAGE_VOTES, site)));

        Assert.assertNotNull(widget.getDraftItem());
        DraftManageVotes manageVotes = (DraftManageVotes) widget.getDraftItem();
        Assert.assertNotNull(manageVotes);
        Assert.assertEquals(site.getSiteId(), manageVotes.getSiteId());
        Assert.assertEquals("Manage Your Votes1", manageVotes.getName());
    }

    @Test
    public void testCreate_Menu() {
        Site site = TestUtil.createSite();
        WidgetItem widget = TestUtil.createWidgetItemWithPageAndPageVersion(site);

        Assert.assertNull(widget.getDraftItem());

        widget.setDraftItem(ItemCreator.create(new ItemCreatorRequest(ItemType.MENU, site)));

        Assert.assertNotNull(widget.getDraftItem());
        DraftMenu draftMenu = (DraftMenu) widget.getDraftItem();
        Assert.assertNotNull(draftMenu);
        Assert.assertEquals(site.getSiteId(), draftMenu.getSiteId());
        Assert.assertEquals("Menu1", draftMenu.getName());
        Assert.assertEquals(MenuStructureType.DEFAULT, draftMenu.getMenuStructure());
    }

    @Test
    public void testCreate_TellFriend() {
        final Config config = ServiceLocator.getConfigStorage().get();
        config.setApplicationUrl("testApplicationUrl");
        config.setApplicationName("testApplicationName");

        Site site = TestUtil.createSite();
        WidgetItem widget = TestUtil.createWidgetItemWithPageAndPageVersion(site);

        Assert.assertNull(widget.getDraftItem());

        widget.setDraftItem(ItemCreator.create(new ItemCreatorRequest(ItemType.TELL_FRIEND, site)));

        Assert.assertNotNull(widget.getDraftItem());
        DraftTellFriend tellFriend = (DraftTellFriend) (widget.getDraftItem());
        Assert.assertNotNull(tellFriend);
        Assert.assertEquals(site.getSiteId(), tellFriend.getSiteId());
        Assert.assertEquals("Tell Friend1", tellFriend.getName());
        final International international = ServiceLocator.getInternationStorage().get("configureTellFriend", Locale.US);
        Assert.assertEquals(international.get("mailDefaultSubject"), tellFriend.getMailSubject());
        Assert.assertEquals("This message has been sent from your friend <`from email address`>, while visiting the " +
                "<site name> site. They think you might be interested in this page: <url of the page that the tell a " +
                "friend was on>. This site is powered by " + config.getApplicationName() + " http://" + config.getApplicationUrl() + ", push button easy web site " +
                "creation tools.", tellFriend.getMailText());
    }

    @Test
    public void testCreate_TaxRates() {
        Site site = TestUtil.createSite();
        WidgetItem widget = TestUtil.createWidgetItemWithPageAndPageVersion(site);

        Assert.assertNull(widget.getDraftItem());

        widget.setDraftItem(ItemCreator.create(new ItemCreatorRequest(ItemType.TAX_RATES, site)));

        Assert.assertNotNull(widget.getDraftItem());
        DraftTaxRatesUS taxRatesUS = (DraftTaxRatesUS) (widget.getDraftItem());
        Assert.assertNotNull(taxRatesUS);
        Assert.assertEquals(site.getSiteId(), taxRatesUS.getSiteId());
        Assert.assertEquals("Tax Rates1", taxRatesUS.getName());
        Assert.assertEquals(50, taxRatesUS.getTaxRates().size());
        for (DraftTaxRateUS draftTaxRateUS : taxRatesUS.getTaxRates()) {
            Assert.assertNotNull(draftTaxRateUS.getState());
            Assert.assertEquals(false, draftTaxRateUS.isIncluded());
        }
    }

    @Test
    public void testCreate_BlogSummary_copyExisting() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final WidgetItem widget = TestUtil.createWidgetItemWithPageAndPageVersion(site);
        final DraftBlogSummary draftBlogSummary = TestUtil.createBlogSummary(site, "Blog Summary");
        widget.setDraftItem(draftBlogSummary);

        final ItemCreatorRequest request = new ItemCreatorRequest(draftBlogSummary.getId(), true, ItemType.BLOG_SUMMARY, site);
        final DraftItem draftItem = ItemCreator.create(request);

        DraftBlogSummary blogSummary = (DraftBlogSummary) draftItem;
        Assert.assertNotNull(blogSummary);
        Assert.assertEquals(site.getSiteId(), blogSummary.getSiteId());
        Assert.assertEquals("_copy must be added to the original item name, or we will not be able to save this item " +
                "on editing ('Item name not unique' message will be shown). Tolik", "Blog Summary_copy1", blogSummary.getName());
    }
}
