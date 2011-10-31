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

package com.shroggle.util.persistance.hibernate;

import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.billingInfo.ChargeTypeManager;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.logic.site.item.ItemPosterReal;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UsersGroupManager;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class HibernateSitePersistanceTest extends HibernatePersistanceTestBase {

    @Test
    public void getVisitForPageByVisitor() {
        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("title1");
        site.setSubDomain("1");
        ThemeId id = new ThemeId();
        id.setTemplateDirectory("");
        id.setThemeCss("");
        site.setThemeId(id);
        persistance.putSite(site);

        Page page = TestUtil.createPage(site);

        PageVisitor pageVisitor = new PageVisitor();
        persistance.putPageVisitor(pageVisitor);

        Visit visit = new Visit();
        visit.setVisitCount(1000);
        visit.setVisitCreationDate(new Date(11111111111L));
        visit.setPageVisitor(pageVisitor);
        visit.setVisitedPage(page);
        persistance.putVisit(visit);

        Assert.assertEquals(visit, persistance.getVisitByPageIdAndUserId(page.getPageId(), visit.getVisitId()));
    }

    @Test
    public void getSiteItemByIdAndType() {
        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("title1");
        site.setThemeId(new ThemeId());
        site.setSubDomain("1");
        site.setCreationDate(new Date());
        ThemeId id = new ThemeId();
        id.setTemplateDirectory("");
        id.setThemeCss("");
        site.setThemeId(id);
        persistance.putSite(site);

        DraftGallery gallery = new DraftGallery();
        gallery.setName("name");
        gallery.setSiteId(site.getSiteId());
        persistance.putItem(gallery);

        DraftGallery findGallery = persistance.getDraftItem(
                gallery.getId());
        Assert.assertEquals(findGallery.getName(), gallery.getName());
        Assert.assertEquals(findGallery.getSiteId(), gallery.getSiteId());
        Assert.assertEquals(findGallery.getId(), gallery.getId());
    }

    @Test
    public void getPageVisitorsByUserId() {
        PageVisitor pageVisitor = new PageVisitor();
        pageVisitor.setUserId(23);
        persistance.putPageVisitor(pageVisitor);

        final List<PageVisitor> pageVisitors = persistance.getPageVisitorsByUserId(23);
        Assert.assertEquals(1, pageVisitors.size());
        Assert.assertEquals(pageVisitor, pageVisitors.get(0));
    }

    @Test
    public void getPageVisitorsByUserIdWithoutUser() {
        PageVisitor pageVisitor = new PageVisitor();
        persistance.putPageVisitor(pageVisitor);

        final List<PageVisitor> pageVisitors = persistance.getPageVisitorsByUserId(23);
        Assert.assertEquals(0, pageVisitors.size());
    }

    @Test
    public void getPageVisitorsByUserIdForNotFound() {
        final List<PageVisitor> pageVisitors = persistance.getPageVisitorsByUserId(23);
        Assert.assertEquals(0, pageVisitors.size());
    }

    @Test
    public void getVisitForPageByVisitorNotFoundPage() {
        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("title1");
        site.setSubDomain("1");
        ThemeId id = new ThemeId();
        id.setTemplateDirectory("");
        id.setThemeCss("");
        site.setThemeId(id);
        persistance.putSite(site);

        Page page = TestUtil.createPage(site);

        PageVisitor pageVisitor = new PageVisitor();
        persistance.putPageVisitor(pageVisitor);

        Visit visit = new Visit();
        visit.setVisitCount(1000);
        visit.setVisitCreationDate(new Date(11111111111L));
        visit.setPageVisitor(pageVisitor);
        visit.setVisitedPage(page);
        persistance.putVisit(visit);

        Assert.assertNull(persistance.getVisitByPageIdAndUserId(page.getPageId() + 1, visit.getVisitId()));
    }

    @Test
    public void getVisitForPageByVisitorNotFoundVisitor() {
        Site site1 = new Site();
        site1.getSitePaymentSettings().setUserId(-1);
        site1.setTitle("title1");
        site1.setSubDomain("1");
        site1.setCreationDate(new Date());
        ThemeId id = new ThemeId();
        id.setTemplateDirectory("");
        id.setThemeCss("");
        site1.setThemeId(id);
        persistance.putSite(site1);

        Page page = TestUtil.createPage(site1);
        Assert.assertEquals(page.getPageId(), persistance.getPage(page.getPageId()).getPageId());

        PageManager pageVersion1 = new PageManager(page);
        pageVersion1.setPage(page);
        pageVersion1.setCreationDate(new Date(10000000L));

        Assert.assertNull(persistance.getVisitByPageIdAndUserId(page.getPageId(), 1));
    }

    @Test
    public void putSite() {
        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("f");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        site.setSubDomain("aa");

        persistance.putSite(site);

        Site findSite = HibernateManager.get().find(Site.class, site.getSiteId());
        Assert.assertEquals("aa", findSite.getSubDomain());
    }

    @Test
    public void putChildSiteSettings() {
        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("f");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        site.setSubDomain("aa");
        persistance.putSite(site);

        ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        childSiteSettings.getSitePaymentSettings().setUserId(-1);
        childSiteSettings.setWelcomeText("");
        childSiteSettings.setCreatedDate(new Date());
        persistance.putChildSiteSettings(childSiteSettings);

        Assert.assertEquals(childSiteSettings,
                persistance.getChildSiteSettingsById(childSiteSettings.getChildSiteSettingsId()));
    }

    @Test
    public void getChildSiteSettingsCountByRegistrationIdWithoutChild() {
        DraftChildSiteRegistration otherChildSiteRegistration = new DraftChildSiteRegistration();
        otherChildSiteRegistration.setName("f1");
        otherChildSiteRegistration.setTermsAndConditions("");
        otherChildSiteRegistration.setWelcomeText("");
        otherChildSiteRegistration.setEmailText("");
        persistance.putItem(otherChildSiteRegistration);

        ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        childSiteSettings.getSitePaymentSettings().setUserId(-1);
        childSiteSettings.setWelcomeText("");
        childSiteSettings.setCreatedDate(new Date());
        childSiteSettings.setChildSiteRegistration(otherChildSiteRegistration);
        persistance.putChildSiteSettings(childSiteSettings);

        DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        childSiteRegistration.setName("f");
        childSiteRegistration.setTermsAndConditions("");
        childSiteRegistration.setWelcomeText("");
        childSiteRegistration.setEmailText("");
        persistance.putItem(childSiteRegistration);

        Assert.assertEquals(0,
                persistance.getChildSiteSettingsCountByRegistrationId(childSiteRegistration.getFormId()));
    }

    @Test
    public void getSiteByNetworkSubDomain() {
        final DraftChildSiteRegistration draftChildSiteRegistration = new DraftChildSiteRegistration();
        draftChildSiteRegistration.setName("f1");
        draftChildSiteRegistration.setTermsAndConditions("");
        draftChildSiteRegistration.setWelcomeText("");
        draftChildSiteRegistration.setEmailText("");
        draftChildSiteRegistration.setBrandedUrl("fff.com");
        persistance.putItem(draftChildSiteRegistration);

        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        childSiteSettings.getSitePaymentSettings().setUserId(-1);
        childSiteSettings.setWelcomeText("");
        childSiteSettings.setCreatedDate(new Date());
        childSiteSettings.setChildSiteRegistration(draftChildSiteRegistration);
        persistance.putChildSiteSettings(childSiteSettings);

        final WorkChildSiteRegistration workChildSiteRegistration = new WorkChildSiteRegistration();
        workChildSiteRegistration.setId(draftChildSiteRegistration.getId());
        workChildSiteRegistration.setName("f");
        workChildSiteRegistration.setTermsAndConditions("");
        workChildSiteRegistration.setWelcomeText("");
        workChildSiteRegistration.setEmailText("");
        workChildSiteRegistration.setBrandedUrl("fff.com");
        persistance.putItem(workChildSiteRegistration);

        final Site site = new Site();
        site.setBrandedSubDomain("aa");
        site.setTitle("1");
        site.setThemeId(new ThemeId("a", "b"));
        site.getSitePaymentSettings().setUserId(-1);
        site.setChildSiteSettings(childSiteSettings);
        persistance.putSite(site);

        Assert.assertNull(persistance.getSiteByBrandedSubDomain("", ""));
        Assert.assertNull(persistance.getSiteByBrandedSubDomain("a", "b"));
        Assert.assertEquals(site, persistance.getSiteByBrandedSubDomain("aa", "fff.com"));
    }

    @Test
    public void getSiteByNetworkSubDomainWithoutWork() {
        final DraftChildSiteRegistration draftChildSiteRegistration = new DraftChildSiteRegistration();
        draftChildSiteRegistration.setName("f1");
        draftChildSiteRegistration.setTermsAndConditions("");
        draftChildSiteRegistration.setWelcomeText("");
        draftChildSiteRegistration.setEmailText("");
        draftChildSiteRegistration.setBrandedUrl("fff.com");
        persistance.putItem(draftChildSiteRegistration);

        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        childSiteSettings.getSitePaymentSettings().setUserId(-1);
        childSiteSettings.setWelcomeText("");
        childSiteSettings.setCreatedDate(new Date());
        childSiteSettings.setChildSiteRegistration(draftChildSiteRegistration);
        persistance.putChildSiteSettings(childSiteSettings);

        final Site site = new Site();
        site.setBrandedSubDomain("aa");
        site.setTitle("1");
        site.setThemeId(new ThemeId("a", "b"));
        site.getSitePaymentSettings().setUserId(-1);
        site.setChildSiteSettings(childSiteSettings);
        persistance.putSite(site);

        Assert.assertNull(persistance.getSiteByBrandedSubDomain("", ""));
        Assert.assertNull(persistance.getSiteByBrandedSubDomain("a", "b"));
        Assert.assertNull(persistance.getSiteByBrandedSubDomain("aa", "fff.com"));
    }

    @Test
    public void putGallery() {
        DraftGallery gallery = new DraftGallery();
        gallery.setName("ggg");
        persistance.putItem(gallery);

        Assert.assertEquals(gallery, persistance.getDraftItem(
                gallery.getId()));
    }

    @Test
    public void getSiteOnItemRightBySiteIdItemIdAndTypeNotExists() {
        final Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("123");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("3");
        persistance.putSite(site);

        DraftGallery gallery = new DraftGallery();
        gallery.setName("gg");
        persistance.putItem(gallery);

        final SiteOnItem siteOnItemRight = persistance.getSiteOnItemRightBySiteIdItemIdAndType(
                site.getSiteId(), gallery.getId(), ItemType.GALLERY);
        Assert.assertNull(siteOnItemRight);
    }

    @Test
    public void getSiteOnItemRightBySiteIdItemIdAndType_childSiteRegistration() {
        final Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("123");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("3");
        persistance.putSite(site);

        DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        childSiteRegistration.setName("gg");
        childSiteRegistration.setTermsAndConditions("");
        childSiteRegistration.setEmailText("");
        childSiteRegistration.setWelcomeText("");
        childSiteRegistration.setSiteId(site.getSiteId());
        persistance.putItem(childSiteRegistration);

        SiteOnItem siteOnItem = new SiteOnItem();
        siteOnItem.setType(SiteOnItemRightType.READ);
        SiteOnItemId siteOnItemId = new SiteOnItemId();
        siteOnItemId.setSite(site);
        siteOnItemId.setItem(childSiteRegistration);
        siteOnItem.setId(siteOnItemId);
        persistance.putSiteOnItem(siteOnItem);

        final SiteOnItem siteOnItemRight = persistance.getSiteOnItemRightBySiteIdItemIdAndType(
                site.getSiteId(), childSiteRegistration.getId(), ItemType.CHILD_SITE_REGISTRATION);
        Assert.assertNotNull(siteOnItemRight);
        Assert.assertEquals(siteOnItemRight.getId().getItem(), childSiteRegistration);
    }

    @Test
    public void getSiteOnItemRightBySiteIdItemIdAndType() {
        final Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("123");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("3");
        persistance.putSite(site);

        DraftGallery gallery = new DraftGallery();
        persistance.putItem(gallery);

        SiteOnItem siteOnItemRight = gallery.createSiteOnItemRight(site);
        persistance.putSiteOnItem(siteOnItemRight);

        Assert.assertEquals(siteOnItemRight, persistance.getSiteOnItemRightBySiteIdItemIdAndType(
                site.getSiteId(), gallery.getId(), ItemType.GALLERY));
    }

    @Test
    public void getChildSiteSettingsCountByRegistrationId() {
        DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        childSiteRegistration.setName("f");
        childSiteRegistration.setTermsAndConditions("");
        childSiteRegistration.setWelcomeText("");
        childSiteRegistration.setEmailText("");
        persistance.putItem(childSiteRegistration);

        ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        childSiteSettings.getSitePaymentSettings().setUserId(-1);
        childSiteSettings.setWelcomeText("");
        childSiteSettings.setCreatedDate(new Date());
        childSiteSettings.setChildSiteRegistration(childSiteRegistration);
        persistance.putChildSiteSettings(childSiteSettings);

        Assert.assertEquals(1,
                persistance.getChildSiteSettingsCountByRegistrationId(childSiteRegistration.getFormId()));
    }

    @Test
    public void getSiteItemByIdAndTypeForBlog() {
        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("f");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        site.setSubDomain("aa");
        persistance.putSite(site);

        DraftBlog blog = new DraftBlog();
        blog.setName("f");
        persistance.putItem(blog);

        DraftItem siteItem = persistance.getDraftItem(blog.getId());
        Assert.assertEquals(blog, siteItem);
        Assert.assertNull(persistance.getWorkItem(blog.getId()));
    }

    @Test
    public void getSiteItemByIdAndTypeForForum() {
        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("f");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        site.setSubDomain("aa");
        persistance.putSite(site);

        DraftForum forum = new DraftForum();
        forum.setName("f");
        persistance.putItem(forum);

        DraftItem siteItem = persistance.getDraftItem(forum.getId());
        Assert.assertEquals(forum, siteItem);
        Assert.assertNull(persistance.getWorkItem(forum.getId()));
    }

    @Test
    public void getSites() {
        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("f");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        site.setSubDomain("aa");

        persistance.putSite(site);

        List<Site> sites = persistance.getAllSites();
        Assert.assertEquals(1, sites.size());
        Assert.assertEquals(site, sites.get(0));
    }

    @Test
    public void getChildSites() {
        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("f");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        site.setSubDomain("aa");
        persistance.putSite(site);

        Site site2 = new Site();
        site2.getSitePaymentSettings().setUserId(-1);
        site2.setTitle("f2");
        site2.getThemeId().setTemplateDirectory("f2");
        site2.getThemeId().setThemeCss("f2");
        site2.setSubDomain("aa2");
        persistance.putSite(site2);

        DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        childSiteRegistration.setName("f");
        childSiteRegistration.setTermsAndConditions("");
        childSiteRegistration.setWelcomeText("");
        childSiteRegistration.setEmailText("");
        persistance.putItem(childSiteRegistration);

        ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        childSiteSettings.getSitePaymentSettings().setUserId(-1);
        childSiteSettings.setWelcomeText("");
        childSiteSettings.setCreatedDate(new Date());
        childSiteSettings.setChildSiteRegistration(childSiteRegistration);
        persistance.putChildSiteSettings(childSiteSettings);

        Site childSite1 = new Site();
        childSite1.getSitePaymentSettings().setUserId(-1);
        childSite1.setTitle("f3");
        childSite1.getThemeId().setTemplateDirectory("3");
        childSite1.getThemeId().setThemeCss("3");
        childSite1.setSubDomain("aa3");
        childSite1.setChildSiteSettings(childSiteSettings);
        persistance.putSite(childSite1);

        Site childSite2 = new Site();
        childSite2.getSitePaymentSettings().setUserId(-1);
        childSite2.setTitle("f4");
        childSite2.getThemeId().setTemplateDirectory("f4");
        childSite2.getThemeId().setThemeCss("f4");
        childSite2.setSubDomain("aa4");
        childSite2.setChildSiteSettings(childSiteSettings);
        persistance.putSite(childSite2);


        List<Site> sites = persistance.getChildSites();
        Assert.assertEquals(4, persistance.getAllSites().size());
        Assert.assertEquals(2, sites.size());
        Assert.assertNotNull(sites.get(0).getChildSiteSettings());
        Assert.assertNotNull(sites.get(1).getChildSiteSettings());
    }

    @Test
    public void removeSite() {
        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("f");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        site.setSubDomain("aa");
        persistance.putSite(site);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);

        WidgetComposit widgetComposit = new WidgetComposit();
        persistance.putWidget(widgetComposit);
        pageVersion.addWidget(widgetComposit);

        WidgetItem widgetBlog = new WidgetItem();
        widgetComposit.addChild(widgetBlog);
        persistance.putWidget(widgetBlog);
        pageVersion.addWidget(widgetBlog);

        Page defaultLoginPage = TestUtil.createPage(site);
        site.setLoginPage(defaultLoginPage);

        Page defaultLoginAdminPage = TestUtil.createPage(site);
        site.setLoginPage(defaultLoginAdminPage);

        HibernateManager.get().flush();

        persistance.removeSite(site);
    }

    @Test
    public void removeSite_blueprintWithSavedIdInChildSiteRegistration() {
        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("f");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        site.setSubDomain("aa");
        persistance.putSite(site);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);

        WidgetComposit widgetComposit = new WidgetComposit();
        persistance.putWidget(widgetComposit);
        pageVersion.addWidget(widgetComposit);

        WidgetItem widgetBlog = new WidgetItem();
        widgetComposit.addChild(widgetBlog);
        pageVersion.addWidget(widgetBlog);
        persistance.putWidget(widgetBlog);

        Page defaultLoginPage = TestUtil.createPage(site);
        site.setLoginPage(defaultLoginPage);

        Page defaultLoginAdminPage = TestUtil.createPage(site);
        site.setLoginPage(defaultLoginAdminPage);

        final DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration("", "");
        registration.setBlueprintsId(Arrays.asList(site.getId()));
        new ItemPosterReal().publish(registration);

        HibernateManager.get().flush();


        persistance.removeSite(site);


        HibernateManager.get().flush();
        HibernateManager.get().refresh(registration);
        Assert.assertEquals(0, registration.getBlueprintsId().size());

        HibernateManager.get().refresh(new ItemManager(registration).getWorkItem());
        Assert.assertEquals(0, ((WorkChildSiteRegistration) new ItemManager(registration).getWorkItem()).getBlueprintsId().size());
    }

    @Test
    public void removeSite_withGroup() {
        final User user = TestUtil.createUser("email");
        final Site site = TestUtil.createSite();

        final Group group = TestUtil.createGroup("name", site);

        final UsersGroup usersGroup = new UsersGroup();
        usersGroup.setExpirationDate(null);
        usersGroup.setId(user, group);
        user.addAccessToGroup(usersGroup);
        persistance.putUsersGroup(usersGroup);


        HibernateManager.get().flush();
        final UsersGroupManager usersGroupManager = new UsersGroupManager(user);
        Assert.assertEquals(1, usersGroupManager.getAccessibleGroupsId().size());

        persistance.removeSite(site);

        HibernateManager.get().flush();
        HibernateManager.get().refresh(user);

        Assert.assertNull(persistance.getGroup(group.getGroupId()));
        Assert.assertEquals(0, usersGroupManager.getAccessibleGroupsId().size());
    }

    @Test
    public void removeBlueprintWithChild() {
        Site blueprint = new Site();
        blueprint.getSitePaymentSettings().setUserId(-1);
        blueprint.setTitle("f");
        blueprint.getThemeId().setTemplateDirectory("f");
        blueprint.getThemeId().setThemeCss("f");
        blueprint.setSubDomain("aa");
        persistance.putSite(blueprint);

        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("df");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        site.setSubDomain("aa1");
        blueprint.addBlueprintChild(site);
        persistance.putSite(site);

        HibernateManager.get().flush();

        persistance.removeSite(blueprint);

        HibernateManager.get().refresh(site);

        Assert.assertNull(site.getBlueprintParent());
    }

    @Test
    public void removeSiteWithBlog() {
        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("f");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        site.setSubDomain("aa");
        persistance.putSite(site);

        DraftBlog blog = new DraftBlog();
        blog.setName("f");
        blog.setSiteId(site.getSiteId());
        persistance.putItem(blog);

        HibernateManager.get().flush();

        persistance.removeSite(site);
        HibernateManager.get().refresh(blog);

        Assert.assertTrue(blog.getSiteId() < 0);
    }

    @Test
    public void removeSiteWithForum() {
        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("f");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        site.setSubDomain("aa");
        persistance.putSite(site);

        DraftForum forum = new DraftForum();
        forum.setName("f");
        forum.setSiteId(site.getSiteId());
        persistance.putItem(forum);

        HibernateManager.get().flush();

        persistance.removeSite(site);
        HibernateManager.get().refresh(forum);

        Assert.assertTrue(forum.getSiteId() < 0);
    }


    @Test
    public void removeSiteWithChildSite() {
        Site parentSite = new Site();
        parentSite.getSitePaymentSettings().setUserId(-1);
        parentSite.setTitle("f");
        parentSite.getThemeId().setTemplateDirectory("f");
        parentSite.getThemeId().setThemeCss("f");
        parentSite.setSubDomain("aa");
        persistance.putSite(parentSite);

        DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        childSiteRegistration.setName("f1");
        childSiteRegistration.setTermsAndConditions("");
        childSiteRegistration.setWelcomeText("");
        childSiteRegistration.setEmailText("");
        persistance.putItem(childSiteRegistration);

        childSiteRegistration.setSiteId(parentSite.getSiteId());
        parentSite.addChildSiteRegistrationId(childSiteRegistration.getFormId());

        ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        childSiteSettings.getSitePaymentSettings().setUserId(-1);
        childSiteSettings.setWelcomeText("");
        childSiteSettings.setCreatedDate(new Date());
        childSiteSettings.setChildSiteRegistration(childSiteRegistration);
        persistance.putChildSiteSettings(childSiteSettings);

        Site childSite = new Site();
        childSite.getSitePaymentSettings().setUserId(-1);
        childSite.setTitle("f2");
        childSite.getThemeId().setTemplateDirectory("f2");
        childSite.getThemeId().setThemeCss("f2");
        childSite.setSubDomain("aa2");
        persistance.putSite(childSite);

        PaymentMethod paymentMethod = PaymentMethod.AUTHORIZE_NET;

        SitePaymentSettings paymentSettings = new SitePaymentSettings();
        paymentSettings.setPrice(98217987);
        paymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);
        paymentSettings.setPaymentMethod(paymentMethod);
        paymentSettings.setUserId(-1);
        childSite.setSitePaymentSettings(paymentSettings);

        childSiteSettings.setParentSite(parentSite);
        childSiteSettings.setSite(childSite);
        childSite.setChildSiteSettings(childSiteSettings);


        HibernateManager.get().flush();

        persistance.removeSite(parentSite);
        //childSiteSettings = persistance.getChildSiteSettingsById(childSiteSettings.getChildSiteSettingsId());
        //HibernateManager.get().refresh(childSiteSettings);
        childSite = persistance.getSite(childSite.getSiteId());
        HibernateManager.get().refresh(childSite);

        Assert.assertNull(childSite.getChildSiteSettings());
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_MONTHLY_FEE).getPrice(), childSite.getSitePaymentSettings().getPrice());
        Assert.assertEquals(paymentMethod, childSite.getSitePaymentSettings().getPaymentMethod());
    }

    @Test
    public void getSiteByCustomUrl() {
        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        site.setSubDomain("f");
        site.setTitle("f");
        site.setCustomUrl("aa");

        persistance.putSite(site);

        Assert.assertEquals(site, persistance.getSiteByCustomUrl("aa"));
        Assert.assertNull(persistance.getSiteByCustomUrl("Aa"));
        Assert.assertNull(persistance.getSiteByCustomUrl("www.aa"));
    }

    @Test
    public void putPage() {
        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setSubDomain("aa");
        site.setTitle("f");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        persistance.putSite(site);

        TestUtil.createPage(site);
    }

    @Test
    public void getLastPageVersionByUrlAndSiteAndType() {
        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setSubDomain("aa");
        site.setTitle("f");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        persistance.putSite(site);

        Page page = TestUtil.createPage(site);

        Page otherPage = TestUtil.createPage(site);

        PageManager otherPageVersion = new PageManager(otherPage);
        otherPageVersion.setName("a");
        otherPageVersion.setUrl("1");
        otherPageVersion.setCreationDate(new Date(System.currentTimeMillis() * 2));

        TestUtil.createWorkPageSettings(page.getPageSettings());
        PageManager workPageVersion = new PageManager(page);
        workPageVersion.getWorkPageSettings().setName("a");
        workPageVersion.getWorkPageSettings().setUrl("ab");
        workPageVersion.getWorkPageSettings().setCreationDate(new Date(System.currentTimeMillis() * 2));

        PageManager oldPageVersion = new PageManager(page);
        oldPageVersion.setName("a");
        oldPageVersion.setUrl("ab");
        oldPageVersion.setCreationDate(new Date(System.currentTimeMillis() / 2));

        final Page findPage = persistance.getPageByUrlAndAndSiteIgnoreUrlCase(
                "1", site.getSiteId());
        Assert.assertNotNull("Can't find need page version!", findPage);
        Assert.assertEquals(otherPageVersion.getPage(), findPage);
    }

    @Test
    public void getLastPageVersionByUrlAndSiteAndType_withNotEqualsCase() {
        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setSubDomain("aa");
        site.setTitle("f");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        persistance.putSite(site);

        final Page page = TestUtil.createPage(site);
        new PageManager(page).setUrl("ab");

        final Page otherPage = TestUtil.createPage(site);
        new PageManager(otherPage).setUrl("AbOuT1");

        final Page findPage = persistance.getPageByUrlAndAndSiteIgnoreUrlCase("aBoUt1", site.getSiteId());
        Assert.assertNotNull("Can't find need page version!", findPage);
        Assert.assertEquals(otherPage, findPage);
    }

    @Test
    public void putWidgetCompositWithChildWidgetText() {
        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setSubDomain("aa");
        site.setTitle("f");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        persistance.putSite(site);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);
        pageVersion.setName("a");

        WidgetComposit parent = new WidgetComposit();
        parent.setPosition(0);
        persistance.putWidget(parent);
        pageVersion.addWidget(parent);

        Widget widget = new WidgetItem();
        widget.setPosition(0);
        parent.addChild(widget);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

    }

    @Test
    public void getWidgetsByPageVersion() {
        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("f");
        site.setSubDomain("aa");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        persistance.putSite(site);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);
        pageVersion.setName("a");

        WidgetComposit parent = new WidgetComposit();
        parent.setPosition(0);
        persistance.putWidget(parent);
        pageVersion.addWidget(parent);

        Widget child1 = new WidgetItem();
        child1.setPosition(0);
        parent.addChild(child1);
        persistance.putWidget(child1);
        pageVersion.addWidget(child1);

        Widget child2 = new WidgetItem();
        child2.setPosition(1);
        parent.addChild(child2);
        persistance.putWidget(child2);
        pageVersion.addWidget(child2);

        List<Widget> widgets = new PageManager(page).getWidgets();
        Assert.assertNotNull(widgets);
        Assert.assertEquals(3, widgets.size());
    }

    @Test
    public void getWidgetsOnLastPageVersionsByTypeAndSite() {
        //----------------------------------------------------Site1-----------------------------------------------------
        final Site site1 = new Site();
        site1.getSitePaymentSettings().setUserId(-1);
        site1.setSubDomain("site1");
        site1.setTitle("site1");
        site1.getThemeId().setTemplateDirectory("site1");
        site1.getThemeId().setThemeCss("site1");
        persistance.putSite(site1);

        final Page pageForSite1 = TestUtil.createPage(site1);

        final PageManager draftPageVersionForSite1 = new PageManager(pageForSite1);
        draftPageVersionForSite1.setName("draft");

        TestUtil.createWorkPageSettings(pageForSite1.getPageSettings());
        final PageManager workPageVersionForSite1 = new PageManager(pageForSite1);
        workPageVersionForSite1.getWorkPageSettings().setName("work");

        final WidgetItem widgetBlogWorkForSite1 = new WidgetItem();
        widgetBlogWorkForSite1.setCrossWidgetId(1);
        persistance.putWidget(widgetBlogWorkForSite1);
        workPageVersionForSite1.getWorkPageSettings().addWidget(widgetBlogWorkForSite1);

        final WidgetItem widgetBlogDraftForSite1 = new WidgetItem();
        widgetBlogDraftForSite1.setCrossWidgetId(1);
        persistance.putWidget(widgetBlogDraftForSite1);
        draftPageVersionForSite1.addWidget(widgetBlogDraftForSite1);

        //----------------------------------------------------Site1-----------------------------------------------------

        //----------------------------------------------------Site2-----------------------------------------------------
        final Site site2 = new Site();
        site2.getSitePaymentSettings().setUserId(-1);
        site2.setSubDomain("site2");
        site2.setTitle("site2");
        site2.getThemeId().setTemplateDirectory("site2");
        site2.getThemeId().setThemeCss("site2");
        persistance.putSite(site2);

        final Page pageForSite2 = TestUtil.createPage(site2);

        final PageManager draftPageVersionForSite2 = new PageManager(pageForSite2);
        draftPageVersionForSite2.setName("draft2");

        TestUtil.createWorkPageSettings(pageForSite2.getPageSettings());
        final PageManager workPageVersionForSite2 = new PageManager(pageForSite2);
        workPageVersionForSite2.getWorkPageSettings().setName("work2");

        final WidgetItem widgetBlogWorkForSite2 = new WidgetItem();
        widgetBlogWorkForSite2.setCrossWidgetId(2);
        persistance.putWidget(widgetBlogWorkForSite2);
        workPageVersionForSite2.getWorkPageSettings().addWidget(widgetBlogWorkForSite2);

        final WidgetItem widgetBlogDraft2ForSite2 = new WidgetItem();
        widgetBlogDraft2ForSite2.setCrossWidgetId(3);
        persistance.putWidget(widgetBlogDraft2ForSite2);
        draftPageVersionForSite2.addWidget(widgetBlogDraft2ForSite2);

        //----------------------------------------------------Site2-----------------------------------------------------

        List<Widget> widgets = persistance.getWidgetsBySitesId(Arrays.asList(site1.getSiteId(), site2.getSiteId()), SiteShowOption.getDraftOption());

        Assert.assertEquals(2, widgets.size());
        Assert.assertTrue(widgets.contains(widgetBlogDraftForSite1));
        Assert.assertFalse(widgets.contains(widgetBlogWorkForSite2));
        Assert.assertTrue(widgets.contains(widgetBlogDraft2ForSite2));
    }

    @Test
    public void testGetWidgetsByPageVersionTypeAndCrossWidgetIds() {
        //----------------------------------------------------Site1-----------------------------------------------------
        final Site site1 = new Site();
        site1.getSitePaymentSettings().setUserId(-1);
        site1.setSubDomain("site1");
        site1.setTitle("site1");
        site1.getThemeId().setTemplateDirectory("site1");
        site1.getThemeId().setThemeCss("site1");
        persistance.putSite(site1);

        final Page pageForSite1 = TestUtil.createPage(site1);

        final PageManager draftPageVersionForSite1 = new PageManager(pageForSite1);
        draftPageVersionForSite1.setName("draft");

        TestUtil.createWorkPageSettings(pageForSite1.getPageSettings());
        final PageManager workPageVersionForSite1 = new PageManager(pageForSite1);
        workPageVersionForSite1.getWorkPageSettings().setName("work");

        final WidgetItem widgetBlogWorkForSite1 = new WidgetItem();
        widgetBlogWorkForSite1.setCrossWidgetId(1);
        persistance.putWidget(widgetBlogWorkForSite1);
        workPageVersionForSite1.getWorkPageSettings().addWidget(widgetBlogWorkForSite1);

        final WidgetItem widgetBlogDraftForSite1 = new WidgetItem();
        widgetBlogDraftForSite1.setCrossWidgetId(1);
        persistance.putWidget(widgetBlogDraftForSite1);
        draftPageVersionForSite1.addWidget(widgetBlogDraftForSite1);

        final WidgetItem widgetBlogDraft2ForSite1 = new WidgetItem();
        widgetBlogDraft2ForSite1.setCrossWidgetId(2);
        persistance.putWidget(widgetBlogDraft2ForSite1);
        draftPageVersionForSite1.addWidget(widgetBlogDraft2ForSite1);
        //----------------------------------------------------Site1-----------------------------------------------------

        List<Widget> draftWidgets = persistance.getDraftWidgetsByCrossWidgetsId(Arrays.asList(1, 2));
        Assert.assertTrue(draftWidgets.contains(widgetBlogDraftForSite1));
        Assert.assertTrue(draftWidgets.contains(widgetBlogDraft2ForSite1));

        List<Widget> workWidgets = persistance.getWorkWidgetsByCrossWidgetsId(Arrays.asList(1, 2));
        Assert.assertTrue(workWidgets.contains(widgetBlogWorkForSite1));
    }


    @Test
    public void testGetWidgetsOnLastPageVersionByCrossWidgetIds() {
        //----------------------------------------------------Site1-----------------------------------------------------
        final Site site1 = new Site();
        site1.getSitePaymentSettings().setUserId(-1);
        site1.setSubDomain("site1");
        site1.setTitle("site1");
        site1.getThemeId().setTemplateDirectory("site1");
        site1.getThemeId().setThemeCss("site1");
        persistance.putSite(site1);

        final Page pageForSite1 = TestUtil.createPage(site1);

        final PageManager draftPageVersionForSite1 = new PageManager(pageForSite1);
        draftPageVersionForSite1.setName("draft");

        TestUtil.createWorkPageSettings(pageForSite1.getPageSettings());
        final PageManager workPageVersionForSite1 = new PageManager(pageForSite1);
        workPageVersionForSite1.getWorkPageSettings().setName("work");

        final WidgetItem widgetBlogWorkForSite1 = new WidgetItem();
        widgetBlogWorkForSite1.setCrossWidgetId(1);
        persistance.putWidget(widgetBlogWorkForSite1);
        workPageVersionForSite1.getWorkPageSettings().addWidget(widgetBlogWorkForSite1);

        final WidgetItem widgetBlogWork2ForSite1 = new WidgetItem();
        widgetBlogWork2ForSite1.setCrossWidgetId(2);
        persistance.putWidget(widgetBlogWork2ForSite1);
        workPageVersionForSite1.getWorkPageSettings().addWidget(widgetBlogWork2ForSite1);

        final WidgetItem widgetBlogDraft2ForSite1 = new WidgetItem();
        widgetBlogDraft2ForSite1.setCrossWidgetId(1);
        persistance.putWidget(widgetBlogDraft2ForSite1);
        draftPageVersionForSite1.addWidget(widgetBlogDraft2ForSite1);
        //----------------------------------------------------Site1-----------------------------------------------------

        List<Widget> draftWidgets = persistance.getWidgetsByCrossWidgetsId(Arrays.asList(1, 2), SiteShowOption.OUTSIDE_APP);
        Assert.assertTrue(draftWidgets.contains(widgetBlogWork2ForSite1));
        Assert.assertFalse(draftWidgets.contains(widgetBlogDraft2ForSite1));
    }

    @Test
    public void isExistLastPageVersionByNameAndSiteWithNotUnique() {
        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setSubDomain("aa");
        site.setTitle("f");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        persistance.putSite(site);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);
        pageVersion.setName("a");

        Assert.assertTrue(persistance.getPageByNameAndSite("a", site.getSiteId()) != null);
    }

    @Test
    public void isExistLastPageVersionByNameAndSiteWithExclude() {
        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setSubDomain("aa");
        site.setTitle("f");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        persistance.putSite(site);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);
        pageVersion.setName("a");

        Assert.assertTrue(persistance.getPageByNameAndSite("a", site.getSiteId()) != null);
    }

    @Test
    public void isExistLastPageVersionByNameAndSiteWithNotUniqueDraft() {
        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setSubDomain("aa");
        site.setTitle("f");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        persistance.putSite(site);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);
        pageVersion.setName("a");

//        PageManager newPageVersion = new PageManager();
//        newPageVersion.setType(PageVersionType.WORK);
//        newPageVersion.setName("a1");
//        page.addVersion(newPageVersion);
//        persistance.putPageVersion(newPageVersion);

        Assert.assertTrue(persistance.getPageByNameAndSite("a", site.getSiteId()) != null);
        Assert.assertFalse(persistance.getPageByNameAndSite("a1", site.getSiteId()) != null);
    }

    @Test
    public void isExistLastPageVersionByNameAndSiteWithNotUniqueNotWork() {
        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setSubDomain("aa");
        site.setTitle("f");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        persistance.putSite(site);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);
        pageVersion.setName("a1");

        TestUtil.createWorkPageSettings(page.getPageSettings());
        PageManager workPageVersion = new PageManager(page);
        workPageVersion.getWorkPageSettings().setName("a");

        Assert.assertTrue(persistance.getPageByNameAndSite("a", site.getSiteId()) != null);
        Assert.assertFalse(persistance.getPageByNameAndSite("2a", site.getSiteId()) != null);
    }

    @Test
    public void getLastPageVersionByAliaseUrl() {
        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setSubDomain("aa");
        site.setTitle("f");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        persistance.putSite(site);

        Page page = TestUtil.createPage(site);
        TestUtil.createWorkPageSettings(page.getPageSettings());
        PageManager pageVersion = new PageManager(page);
        pageVersion.getWorkPageSettings().setName("a");
        pageVersion.getWorkPageSettings().setOwnDomainName("gg");


        Assert.assertEquals(pageVersion.getPage(), persistance.getPageByOwnDomainName("gg"));
    }


    @Test
    public void getLastPageVersionByAliaseUrlInOtherCase() {
        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setSubDomain("aa");
        site.setTitle("f");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        persistance.putSite(site);

        Page page = TestUtil.createPage(site);

        TestUtil.createWorkPageSettings(page.getPageSettings());
        PageManager pageVersion = new PageManager(page);
        pageVersion.getWorkPageSettings().setName("a");
        pageVersion.getWorkPageSettings().setOwnDomainName("gG");

        Assert.assertNull(persistance.getPageByOwnDomainName("gg"));
    }

    @Test
    public void getLastPageVersionByNullAliaseUrl() {
        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setSubDomain("aa");
        site.setTitle("f");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        persistance.putSite(site);

        Page page = TestUtil.createPage(site);

        TestUtil.createWorkPageSettings(page.getPageSettings());
        PageManager pageVersion = new PageManager(page);
        pageVersion.getWorkPageSettings().setName("a");
        pageVersion.getWorkPageSettings().setOwnDomainName("gg");


        Assert.assertNull(persistance.getPageByOwnDomainName(null));
    }

    @Test
    public void getLastPageVersionByAliaseUrlNone() {
        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setSubDomain("aa");
        site.setTitle("f");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        persistance.putSite(site);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);
        pageVersion.setName("a");
        pageVersion.setOwnDomainName("gg");

        Assert.assertNotNull(persistance.getPageByOwnDomainName("gg"));
    }

    @Test
    public void getLastPageVersionByUrlAndSiteAndTypeNotFoundSite() {
        Assert.assertNull(persistance.getPageByUrlAndAndSiteIgnoreUrlCase("b", 1));
    }

    @Test
    public void getLastPageVersionByUrlAndSiteAndTypeWithoutPageVersions() {
        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setSubDomain("aa");
        site.setTitle("f");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        persistance.putSite(site);

        Site otherSite = new Site();
        otherSite.getSitePaymentSettings().setUserId(-1);
        otherSite.setTitle("f");
        otherSite.getThemeId().setTemplateDirectory("f");
        otherSite.getThemeId().setThemeCss("f");
        otherSite.setSubDomain("aa1");
        persistance.putSite(otherSite);

        Page otherSitePage = TestUtil.createPage(otherSite);

        PageManager otherSitePageVersion = new PageManager(otherSitePage);
        otherSitePageVersion.setName("a");
        otherSitePageVersion.setUrl("b");
        otherSitePageVersion.setCreationDate(new Date(System.currentTimeMillis() * 2));

        Assert.assertNull(persistance.getPageByUrlAndAndSiteIgnoreUrlCase("b", site.getSiteId()));
    }

    @Test
    public void getGalleryDataWidgetsOnLastPageVersions() {
        final Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setSubDomain("aa");
        site.setTitle("f");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        persistance.putSite(site);

        final Page needPage = TestUtil.createPage(site);

        final Page otherPage = TestUtil.createPage(site);

        final PageManager needPageVersion = new PageManager(needPage);
        needPageVersion.setName("a");

        final PageManager needOldPageVersion = new PageManager(needPage);
        needOldPageVersion.setCreationDate(new Date(System.currentTimeMillis() / 2));
        needOldPageVersion.setName("a");

        final PageManager otherPageVersion = new PageManager(otherPage);
        otherPageVersion.setCreationDate(new Date(System.currentTimeMillis() * 2));
        otherPageVersion.setName("a");

        final WidgetComposit needWidget = new WidgetComposit();
        persistance.putWidget(needWidget);
        needPageVersion.addWidget(needWidget);

        final WidgetComposit needOldWidget = new WidgetComposit();
        persistance.putWidget(needOldWidget);
        needOldPageVersion.addWidget(needOldWidget);

        final WidgetItem otherWidget = new WidgetItem();
        persistance.putWidget(otherWidget);
        otherPageVersion.addWidget(otherWidget);
        final DraftGalleryData draftGalleryData = new DraftGalleryData();
        persistance.putItem(draftGalleryData);
        otherWidget.setDraftItem(draftGalleryData);

        final List<WidgetItem> widgets = persistance.getGalleryDataWidgetsBySitesId(
                Arrays.asList(site.getSiteId()), SiteShowOption.getDraftOption());

        Assert.assertEquals(1, widgets.size());
        Assert.assertEquals(otherWidget, widgets.get(0));
    }


    @Test
    public void putWidgetCssParameterValue() {
        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        site.setSubDomain("aa");
        site.setTitle("f");
        persistance.putSite(site);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);
        pageVersion.setName("a");

        Widget widget = new WidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);
    }

    @Test
    public void getByUrlPrefixNotFound() {
        Assert.assertNull(persistance.getSiteBySubDomain("aa111"));
    }

    @Test
    public void getByUrlPrefix() {
        Site site1 = new Site();
        site1.getSitePaymentSettings().setUserId(-1);
        site1.setTitle("title1");
        site1.setSubDomain("1");
        site1.setCreationDate(new Date());
        ThemeId id = new ThemeId();
        id.setTemplateDirectory("");
        id.setThemeCss("");
        site1.setThemeId(id);
        persistance.putSite(site1);

        Assert.assertEquals(site1.getSiteId(), persistance.getSiteBySubDomain("1").getSiteId());
    }

    @Test
    public void getSiteTitlePageNamesForNotExist_byUser() {
        Assert.assertEquals(0, persistance.getSiteTitlePageNamesByUserId(-1).size());
    }

    @Test
    public void getSiteTitlePageNamesForNotExist_bySite() {
        Assert.assertEquals(0, persistance.getSiteTitlePageNamesBySiteId(-1).size());
    }

    @Test
    public void getSiteTitlePageNames() {
        final User user = TestUtil.createUser();

        Site site1 = TestUtil.createSite();
        site1.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);

        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);
        site1.setTitle("title1");
        site1.setSubDomain("1");

        Page page2 = TestUtil.createWorkPage(site1);
        page2.getPageSettings().setName("zz");
        persistance.getWorkPageSettings(page2.getPageId()).setName("z");

        Page page1 = TestUtil.createPage(site1);
        page1.getPageSettings().setName("a");

        Site site2 = TestUtil.createSite();
        site2.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        site2.setTitle("zoom");
        site2.setSubDomain("11");

        Page page22 = TestUtil.createWorkPage(site2);
        persistance.getWorkPageSettings(page22.getPageId()).setName("z");

        Page page21 = TestUtil.createWorkPage(site2);
        persistance.getWorkPageSettings(page21.getPageId()).setName("a");

        TestUtil.createPage(site2).getPageSettings().setName("bb");

        final List<SiteTitlePageName> result = persistance.getSiteTitlePageNamesByUserId(user.getUserId());

        Assert.assertEquals(2, result.size());
        Assert.assertEquals(page1.getPageId(), result.get(0).getPageId());
        Assert.assertEquals("a", result.get(0).getPageName());

        Assert.assertEquals(page2.getPageId(), result.get(1).getPageId());
        Assert.assertEquals("zz", result.get(1).getPageName());
    }

    @Test
    public void getSiteTitlePageNamesWithSiteIds() {
        final User user = TestUtil.createUser();

        Site site1 = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);
        site1.setTitle("title1");
        site1.setSubDomain("1");

        Page page2 = TestUtil.createWorkPage(site1);
        page2.getPageSettings().setName("zz");
        persistance.getWorkPageSettings(page2.getPageId()).setName("z");

        Page page1 = TestUtil.createPage(site1);
        page1.getPageSettings().setName("a");

        Site site2 = TestUtil.createSite();
        site2.setTitle("zoom");
        site2.setSubDomain("11");

        Page page22 = TestUtil.createWorkPage(site2);
        persistance.getWorkPageSettings(page22.getPageId()).setName("z");

        Page page21 = TestUtil.createWorkPage(site2);
        persistance.getWorkPageSettings(page21.getPageId()).setName("a");

        TestUtil.createPage(site2).getPageSettings().setName("bb");

        final List<SiteTitlePageName> result = persistance.getSiteTitlePageNamesBySiteId(site1.getSiteId());

        Assert.assertEquals(2, result.size());
        Assert.assertEquals(page1.getPageId(), result.get(0).getPageId());
        Assert.assertEquals("a", result.get(0).getPageName());

        Assert.assertEquals(page2.getPageId(), result.get(1).getPageId());
        Assert.assertEquals("zz", result.get(1).getPageName());
    }

}