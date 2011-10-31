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
import com.shroggle.logic.groups.GroupsTime;
import com.shroggle.logic.groups.GroupsTimeManager;
import com.shroggle.logic.site.WidgetManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.taxRates.TaxRatesUSManager;
import com.shroggle.logic.user.UsersGroupManager;
import com.shroggle.util.ServiceLocator;
import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

public class HibernatePersistanceTest extends HibernatePersistanceTestBase {

    @Test
    public void putSite() {
        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("f");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        site.setSubDomain("aa");
        persistance.putSite(site);


        Assert.assertTrue(site.getSiteId() > 0);
        SitePaymentSettings sitePaymentSettings = persistance.getSitePaymentSettingsById(site.getSitePaymentSettings().getSitePaymentSettingsId());
        Assert.assertNotNull(sitePaymentSettings);
        Assert.assertTrue(site.getSitePaymentSettings().getSitePaymentSettingsId() > 0);
    }


    @Test
    public void getUserByEmail() {
        User user = new User();
        user.setEmail("aa");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        User user2 = new User();
        user2.setRegistrationDate(new Date());
        user2.setEmail("aa1");
        persistance.putUser(user2);

        User findUser1 = persistance.getUserByEmail("aa");
        User findUser2 = persistance.getUserByEmail("aa1");
        Assert.assertEquals(user.getUserId(), findUser1.getUserId());
        Assert.assertEquals(user2.getUserId(), findUser2.getUserId());
        Assert.assertEquals(user.getRegistrationDate(), findUser1.getRegistrationDate());
        Assert.assertEquals(user2.getRegistrationDate(), findUser2.getRegistrationDate());
        Assert.assertNull(persistance.getUserByEmail("aaa"));
    }

    @Test
    public void removeUser() {
        User user = new User();
        user.setEmail("aa");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);


        User user2 = new User();
        user2.setRegistrationDate(new Date());
        user2.setEmail("aa1");
        persistance.putUser(user2);

        List<Site> sites = new ArrayList<Site>();
        for (int i = 0; i < 5; i++) {
            Site site = new Site();
            site.getSitePaymentSettings().setUserId(-1);
            site.setTitle("title" + i);
            site.setSubDomain("1" + i);
            site.setCreationDate(new Date());
            ThemeId id = new ThemeId();
            id.setTemplateDirectory("" + i);
            id.setThemeCss("" + i);
            site.setThemeId(id);
            persistance.putSite(site);
            sites.add(site);
        }

        CreditCard creditCard = new CreditCard();
        creditCard.setUser(user);
        user.addCreditCard(creditCard);
        persistance.putCreditCard(creditCard);

        for (int i = 0; i < 5; i++) {
            sites.get(i).getSitePaymentSettings().setCreditCard(creditCard);
        }


        for (int i = 0; i < 5; i++) {
            UserOnSiteRight userOnUserRight = new UserOnSiteRight();
            userOnUserRight.setActive(true);
            user.addUserOnSiteRight(userOnUserRight);
            userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
            user.addUserOnSiteRight(userOnUserRight);
            sites.get(i).addUserOnSiteRight(userOnUserRight);
            persistance.putUserOnSiteRight(userOnUserRight);
        }

        for (int i = 0; i < 5; i++) {
            UserOnSiteRight userOnUserRight = new UserOnSiteRight();
            userOnUserRight.setActive(true);
            user2.addUserOnSiteRight(userOnUserRight);
            userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
            user2.addUserOnSiteRight(userOnUserRight);
            sites.get(i).addUserOnSiteRight(userOnUserRight);
            persistance.putUserOnSiteRight(userOnUserRight);
        }
        HibernateManager.get().flush();

        for (int i = 0; i < 5; i++) {
            Assert.assertEquals(sites.get(i).getUserOnSiteRights().size(), 2);
            Assert.assertNotNull(sites.get(i).getSitePaymentSettings().getCreditCard());
        }

        persistance.removeCreditCard(user.getCreditCards().get(0));
        HibernateManager.get().flush();

        HibernateManager.get().refresh(user);
        for (int i = 0; i < 5; i++) {
            HibernateManager.get().refresh(sites.get(i));
            HibernateManager.get().refresh(sites.get(i).getSitePaymentSettings());
        }

        List<UserOnSiteRight> rights = user.getUserOnSiteRights();
        while (rights.size() != 0) {
            persistance.removeUserOnSiteRight(rights.get(0));
        }
        HibernateManager.get().flush();

        persistance.removeUser(user);
        HibernateManager.get().flush();
        for (int i = 0; i < 5; i++) {
            Assert.assertEquals(sites.get(i).getUserOnSiteRights().size(), 1);
            Assert.assertNull(sites.get(i).getSitePaymentSettings().getCreditCard());
        }
        Assert.assertNull(persistance.getUserById(user.getUserId()));
    }

    @Test
    public void searchSites() {
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

        Site site2 = new Site();
        site2.getSitePaymentSettings().setUserId(-1);
        site2.setTitle("title2");
        site2.setSubDomain("2");
        site2.setCreationDate(new Date());
        id.setTemplateDirectory("");
        id.setThemeCss("");
        site2.setThemeId(id);
        persistance.putSite(site2);

        Assert.assertEquals(1, persistance.searchSites("title1", "1").size());
        Assert.assertEquals(1, persistance.searchSites("", "1").size());
        Assert.assertEquals(2, persistance.searchSites("tit", "").size());
        Assert.assertEquals(0, persistance.searchSites("titvcble1", "1xvb").size());
    }

    @Test
    public void getVisitorByLoginAndSiteId() {
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

        Site site2 = new Site();
        site2.getSitePaymentSettings().setUserId(-1);
        site2.setTitle("title2");
        site2.setSubDomain("2");
        site2.setCreationDate(new Date());
        id.setTemplateDirectory("");
        id.setThemeCss("");
        site2.setThemeId(id);
        persistance.putSite(site2);

        User user = new User();
        user.setEmail("login1");
        persistance.putUser(user);

        UserOnSiteRight visitorOnSiteRight = new UserOnSiteRight();
        user.addUserOnSiteRight(visitorOnSiteRight);
        site1.addUserOnSiteRight(visitorOnSiteRight);
        visitorOnSiteRight.setVisitorStatus(VisitorStatus.REGISTERED);
        persistance.putUserOnSiteRight(visitorOnSiteRight);

        User visitor2 = new User();
        visitor2.setEmail("login2");
        persistance.putUser(visitor2);

        UserOnSiteRight visitorOnSiteRight2 = new UserOnSiteRight();
        visitor2.addUserOnSiteRight(visitorOnSiteRight2);
        site2.addUserOnSiteRight(visitorOnSiteRight2);
        visitorOnSiteRight2.setVisitorStatus(VisitorStatus.REGISTERED);
        persistance.putUserOnSiteRight(visitorOnSiteRight2);

        User findVisitor1 = persistance.getUserByEmail("login1");
        User findVisitor2 = persistance.getUserByEmail("login2");
        User findVisitor3 = persistance.getUserById(visitor2.getUserId());
        Assert.assertEquals(user.getUserId(), findVisitor1.getUserId());
        Assert.assertEquals(visitor2.getUserId(), findVisitor2.getUserId());
        Assert.assertEquals(visitor2.getUserId(), findVisitor3.getUserId());
        Assert.assertEquals(user.getEmail(), findVisitor1.getEmail());
        Assert.assertEquals(visitor2.getEmail(), findVisitor2.getEmail());
        Assert.assertNull(persistance.getUserByEmail("aaa"));

        Assert.assertNotNull(persistance.getUserOnSiteRightById(new UserOnSiteRightId(user, site1)));
        Assert.assertNotNull(persistance.getUserOnSiteRightById(new UserOnSiteRightId(visitor2, site2)));
    }

    @Test
    public void getWidgetsByBorderId() {
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

        Page page1 = TestUtil.createPage(site1);

        PageManager pageVersion1 = new PageManager(page1);


        Border border = new Border();
        persistance.putBorder(border);

        WidgetItem widget1 = new WidgetItem();
        widget1.setBorderId(border.getId());
        persistance.putWidget(widget1);
        pageVersion1.addWidget(widget1);

        Assert.assertEquals(widget1.getWidgetId(), persistance.getWidget(widget1.getWidgetId()).getWidgetId());

        WidgetItem widget2 = new WidgetItem();
        widget2.setBorderId(border.getId());
        persistance.putWidget(widget2);
        pageVersion1.addWidget(widget2);

        Assert.assertEquals(widget2.getWidgetId(), persistance.getWidget(widget2.getWidgetId()).getWidgetId());


        Site site2 = new Site();
        site2.getSitePaymentSettings().setUserId(-1);
        site2.setTitle("title2");
        site2.setSubDomain("2");
        site2.setCreationDate(new Date());
        id.setTemplateDirectory("");
        id.setThemeCss("");
        site2.setThemeId(id);
        persistance.putSite(site2);

        Page page2 = TestUtil.createPage(site2);

        PageManager pageVersion2 = new PageManager(page2);


        WidgetItem widget3 = new WidgetItem();
        persistance.putWidget(widget3);
        pageVersion2.addWidget(widget3);

        //List<Widget> findWidgets1 = persistance.getWidgetsByBorderId(border.getBorderBackgroundId());
        // Assert.assertEquals(2, findWidgets1.size());
    }

    @Test
    public void getWidgetsByTypeAndUserId() {
        User user1 = new User();
        user1.setPassword("1");
        user1.setEmail("a");
        user1.setRegistrationDate(new Date());
        persistance.putUser(user1);

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

        UserOnSiteRight userOnSiteRight1 = new UserOnSiteRight();
        userOnSiteRight1.setActive(true);
        user1.addUserOnSiteRight(userOnSiteRight1);
        userOnSiteRight1.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        site1.addUserOnSiteRight(userOnSiteRight1);
        persistance.putUserOnSiteRight(userOnSiteRight1);

        Page page1 = TestUtil.createPage(site1);

        PageManager pageVersion1 = new PageManager(page1);

        WidgetItem widget1 = new WidgetItem();
        persistance.putWidget(widget1);
        pageVersion1.addWidget(widget1);

        User user2 = new User();
        user2.setPassword("2");
        user2.setEmail("a2");
        user2.setRegistrationDate(new Date());
        persistance.putUser(user2);

        Site site2 = new Site();
        site2.getSitePaymentSettings().setUserId(-1);
        site2.setTitle("title2");
        site2.setSubDomain("2");
        site2.setCreationDate(new Date());
        id.setTemplateDirectory("");
        id.setThemeCss("");
        site2.setThemeId(id);
        persistance.putSite(site2);

        UserOnSiteRight userOnUserRight2 = new UserOnSiteRight();
        userOnUserRight2.setActive(true);
        user2.addUserOnSiteRight(userOnUserRight2);
        userOnUserRight2.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        site2.addUserOnSiteRight(userOnUserRight2);
        persistance.putUserOnSiteRight(userOnUserRight2);

        Page page2 = TestUtil.createPage(site2);

        PageManager pageVersion2 = new PageManager(page2);

        WidgetItem widget2 = new WidgetItem();
        persistance.putWidget(widget2);
        pageVersion2.addWidget(widget2);

        Widget findWidgets1 = persistance.getWidget(widget1.getWidgetId());
        Widget findWidgets2 = persistance.getWidget(widget2.getWidgetId());

        Assert.assertEquals(widget1, findWidgets1);
        Assert.assertEquals(widget2, findWidgets2);
    }

    @Test
    public void putRegistrationForm() {
        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("title1");
        site.setSubDomain("1");
        site.setCreationDate(new Date());
        ThemeId id = new ThemeId();
        id.setTemplateDirectory("");
        id.setThemeCss("");
        site.setThemeId(id);
        persistance.putSite(site);

        DraftRegistrationForm registrationForm = new DraftRegistrationForm();
        registrationForm.setName("name1");
        registrationForm.setSiteId(site.getSiteId());
        persistance.putRegistrationForm(registrationForm);

        final DraftRegistrationForm findRegistrationForm1 =
                persistance.getRegistrationFormById(registrationForm.getFormId());
        final DraftRegistrationForm findRegistrationForm2 =
                persistance.getRegistrationFormByNameAndSiteId(registrationForm.getName(), site.getSiteId());

        Assert.assertEquals(findRegistrationForm1, findRegistrationForm1);
        Assert.assertEquals(findRegistrationForm1, findRegistrationForm2);
        Assert.assertEquals(findRegistrationForm1, findRegistrationForm2);
        Assert.assertNull(persistance.getRegistrationFormByNameAndSiteId("", -1));
    }

    @Test
    public void putEmailUpdateRequest() {
        EmailUpdateRequest emailUpdateRequest = new EmailUpdateRequest();
        emailUpdateRequest.setUpdateId("1");
        persistance.putEmailUpdateRequest(emailUpdateRequest);
        Assert.assertEquals(emailUpdateRequest.getUpdateId(), persistance.getEmailUpdateRequestById(emailUpdateRequest.getUpdateId()).getUpdateId());
    }

    @Test
    public void getForumPostById() {
        ForumPost post = new ForumPost();
        post.setDateCreated(new Date(1000000000L));

        DraftForum forum = new DraftForum();
        forum.setSiteId(2);
        forum.setName("forumName");
        persistance.putItem(forum);

        SubForum subForum = new SubForum();
        subForum.setSubForumDescription("description");
        subForum.setSubForumName("name");
        subForum.setForum(forum);
        persistance.putSubForum(subForum);


        DraftForum forum2 = new DraftForum();
        forum2.setSiteId(2);
        forum2.addSubForum(subForum);
        forum2.setName("forumName2");
        persistance.putItem(forum2);


        Assert.assertEquals(subForum.getSubForumId(), persistance.getSubForumsByForumId(forum2.getId()).get(0).getSubForumId());

        ForumThread thread = new ForumThread();
        thread.setSubForum(subForum);
        thread.setUpdateDate(new Date());
        thread.setThreadName("setThreadName");
        persistance.putForumThread(thread);

        post.setThread(thread);
        persistance.putForumPost(post);

        Assert.assertEquals(post.getForumPostId(), persistance.getForumPostById(post.getForumPostId()).getForumPostId());
    }

    @Test
    public void putPageVisitor() {
        PageVisitor pageVisitor = new PageVisitor();
        persistance.putPageVisitor(pageVisitor);
        Assert.assertEquals(pageVisitor.getPageVisitorId(), persistance.getPageVisitorById(pageVisitor.getPageVisitorId()).getPageVisitorId());
    }

    @Test
    public void getFlvVideoById() {
        FlvVideo flvVideo = new FlvVideo();
        persistance.putFlvVideo(flvVideo);
        Assert.assertEquals(flvVideo.getFlvVideoId(), persistance.getFlvVideo(flvVideo.getFlvVideoId()).getFlvVideoId());
    }

    @Test
    public void getFlvVideoBySizeAndSourceVideoId() {
        FlvVideo flvVideo1 = new FlvVideo();
        flvVideo1.setWidth(100);
        flvVideo1.setHeight(200);
        flvVideo1.setSourceVideoId(1);
        persistance.putFlvVideo(flvVideo1);

        FlvVideo flvVideo2 = new FlvVideo();
        flvVideo2.setWidth(101);
        flvVideo2.setHeight(210);
        flvVideo2.setSourceVideoId(2);
        persistance.putFlvVideo(flvVideo2);

        FlvVideo flvVideo3 = new FlvVideo();
        flvVideo3.setWidth(10);
        flvVideo3.setHeight(20);
        flvVideo3.setSourceVideoId(3);
        persistance.putFlvVideo(flvVideo3);


        Assert.assertEquals(flvVideo1.getFlvVideoId(), persistance.getFlvVideo(1, 100, 200, FlvVideo.DEFAULT_VIDEO_QUALITY).getFlvVideoId());
        Assert.assertEquals(flvVideo2.getFlvVideoId(), persistance.getFlvVideo(2, 101, 210, FlvVideo.DEFAULT_VIDEO_QUALITY).getFlvVideoId());
        Assert.assertEquals(flvVideo3.getFlvVideoId(), persistance.getFlvVideo(3, 10, 20, FlvVideo.DEFAULT_VIDEO_QUALITY).getFlvVideoId());
        Assert.assertNull(persistance.getFlvVideo(1, 10, 200, FlvVideo.DEFAULT_VIDEO_QUALITY));
        Assert.assertNull(persistance.getFlvVideo(3, 100, 200, FlvVideo.DEFAULT_VIDEO_QUALITY));
        Assert.assertNull(persistance.getFlvVideo(2, 100, 200, FlvVideo.DEFAULT_VIDEO_QUALITY));
    }

    @Test
    public void getFlvVideoBySizeQualityAndSourceVideoId() {
        FlvVideo flvVideo1 = new FlvVideo();
        flvVideo1.setWidth(100);
        flvVideo1.setHeight(200);
        flvVideo1.setSourceVideoId(1);
        flvVideo1.setQuality(1);
        persistance.putFlvVideo(flvVideo1);

        FlvVideo flvVideo11 = new FlvVideo();
        flvVideo11.setWidth(100);
        flvVideo11.setHeight(200);
        flvVideo11.setSourceVideoId(1);
        flvVideo11.setQuality(31);
        persistance.putFlvVideo(flvVideo11);

        FlvVideo flvVideo2 = new FlvVideo();
        flvVideo2.setWidth(101);
        flvVideo2.setHeight(210);
        flvVideo2.setSourceVideoId(2);
        persistance.putFlvVideo(flvVideo2);

        FlvVideo flvVideo3 = new FlvVideo();
        flvVideo3.setWidth(10);
        flvVideo3.setHeight(20);
        flvVideo3.setSourceVideoId(3);
        persistance.putFlvVideo(flvVideo3);

        Assert.assertEquals(flvVideo1, persistance.getFlvVideo(1, 100, 200, 1));
        Assert.assertEquals(flvVideo11, persistance.getFlvVideo(1, 100, 200, 31));
        Assert.assertEquals(flvVideo2, persistance.getFlvVideo(2, 101, 210, FlvVideo.DEFAULT_VIDEO_QUALITY));
        Assert.assertEquals(flvVideo3, persistance.getFlvVideo(3, 10, 20, FlvVideo.DEFAULT_VIDEO_QUALITY));
        Assert.assertNull(persistance.getFlvVideo(1, 10, 200, 31));
        Assert.assertNull(persistance.getFlvVideo(1, 10, 200, 7));
        Assert.assertNull(persistance.getFlvVideo(1, 10, 200, 1));
        Assert.assertNull(persistance.getFlvVideo(3, 100, 200, 31));
        Assert.assertNull(persistance.getFlvVideo(3, 100, 200, 7));
        Assert.assertNull(persistance.getFlvVideo(3, 100, 200, 1));

        Assert.assertNull(persistance.getFlvVideo(2, 100, 200, 31));
        Assert.assertNull(persistance.getFlvVideo(2, 100, 200, 7));
        Assert.assertNull(persistance.getFlvVideo(2, 100, 200, 1));
    }

    @Test
    public void getFlvVideoBySizeQualityAndSourceVideoId_withNullWidthOrHeight() {
        FlvVideo flvVideo1 = new FlvVideo();
        flvVideo1.setWidth(null);
        flvVideo1.setHeight(200);
        flvVideo1.setSourceVideoId(1);
        flvVideo1.setQuality(1);
        persistance.putFlvVideo(flvVideo1);

        FlvVideo flvVideo2 = new FlvVideo();
        flvVideo2.setWidth(100);
        flvVideo2.setHeight(null);
        flvVideo2.setSourceVideoId(1);
        flvVideo2.setQuality(31);
        persistance.putFlvVideo(flvVideo2);

        FlvVideo flvVideo3 = new FlvVideo();
        flvVideo3.setWidth(null);
        flvVideo3.setHeight(null);
        flvVideo3.setSourceVideoId(2);
        persistance.putFlvVideo(flvVideo3);

        Assert.assertEquals(flvVideo1, persistance.getFlvVideo(1, null, 200, 1));
        Assert.assertEquals(flvVideo2, persistance.getFlvVideo(1, 100, null, 31));
        Assert.assertEquals(flvVideo3, persistance.getFlvVideo(2, null, null, FlvVideo.DEFAULT_VIDEO_QUALITY));
    }

    @Test
    public void putVisit() {
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

        Page page1 = TestUtil.createPage(site1);
        Assert.assertEquals(page1.getPageId(), persistance.getPage(page1.getPageId()).getPageId());

        PageManager pageVersion1 = new PageManager(page1);
        pageVersion1.setCreationDate(new Date(10000000L));

        PageVisitor pageVisitor = new PageVisitor();
        persistance.putPageVisitor(pageVisitor);

        Visit visit = new Visit();
        visit.setVisitCount(1000);
        visit.setVisitCreationDate(new Date(11111111111L));
        visit.setPageVisitor(pageVisitor);
        visit.setVisitedPage(page1);
        persistance.putVisit(visit);
        Assert.assertEquals(visit.getVisitId(), persistance.getVisitByPageIdAndUserId(page1.getPageId(), visit.getVisitId()).getVisitId());
    }

    @Test
    public void putMenu() {
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

        final DraftMenu menu = new DraftMenu();
        menu.setSiteId(site1.getSiteId());
        menu.setName("menuName1");
        persistance.putMenu(menu);
        Assert.assertEquals(menu.getId(), persistance.getMenuById(menu.getId()).getId());

        final WidgetItem widgetItem = TestUtil.createWidgetItemWithPageAndPageVersion(site1);
        widgetItem.setDraftItem(menu);

        final WidgetItem widgetItem2 = TestUtil.createWidgetItemWithPageAndPageVersion(site1);
        widgetItem2.setDraftItem(menu);

        final WidgetItem widgetItem3 = TestUtil.createWidgetItemWithPageAndPageVersion(site1);
        widgetItem3.setDraftItem(TestUtil.createMenu());
        HibernateManager.get().flush();

        persistance.removeDraftItem(menu);

        HibernateManager.get().flush();
        HibernateManager.get().refresh(widgetItem3);

        Assert.assertNull(persistance.getMenuById(menu.getId()));
        Assert.assertNull(persistance.getWidget(widgetItem.getWidgetId()));
        Assert.assertNull(persistance.getWidget(widgetItem2.getWidgetId()));
        Assert.assertNotNull(widgetItem3.getDraftItem());
    }

    @Test
    public void getMenusBySiteId() {
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

        DraftMenu menu = new DraftMenu();
        menu.setName("menuName1");
        menu.setSiteId(site1.getSiteId());
        persistance.putMenu(menu);

        DraftMenu menu2 = new DraftMenu();
        menu2.setName("menuName2");
        menu2.setSiteId(site1.getSiteId());
        persistance.putMenu(menu2);

        DraftMenu menu3 = new DraftMenu();
        menu3.setName("menuName3");
        menu3.setSiteId(site1.getSiteId());
        persistance.putMenu(menu3);

        Assert.assertEquals(3, persistance.getMenusBySiteId(site1.getSiteId()).size());
    }

    @Test
    public void getMenusWithDefaultStructureBySiteId() {
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

        DraftMenu menu = new DraftMenu();
        menu.setName("menuName1");
        menu.setMenuStructure(MenuStructureType.DEFAULT);
        menu.setSiteId(site1.getSiteId());
        persistance.putMenu(menu);

        DraftMenu menu2 = new DraftMenu();
        menu2.setName("menuName2");
        menu2.setMenuStructure(MenuStructureType.OWN);
        menu2.setSiteId(site1.getSiteId());
        persistance.putMenu(menu2);

        DraftMenu menu3 = new DraftMenu();
        menu3.setName("menuName3");
        menu3.setMenuStructure(MenuStructureType.DEFAULT);
        menu3.setSiteId(site1.getSiteId());
        persistance.putMenu(menu3);

        final List<DraftMenu> menus = persistance.getMenusWithDefaultStructureBySiteId(site1.getSiteId());
        Assert.assertEquals(2, menus.size());
        Assert.assertEquals(true, menus.contains(menu));
        Assert.assertEquals(true, menus.contains(menu3));
    }

    @Test
    public void put_get_removeStyle() {
        Style style = new Style();

        persistance.putStyle(style);

        Style newStyle = persistance.getStyleById(style.getStyleId());
        Assert.assertEquals(style.getStyleId(), newStyle.getStyleId());

        persistance.removeStyle(style);

        newStyle = persistance.getStyleById(style.getStyleId());
        Assert.assertNull(newStyle);
    }

    @Test
    public void getForumByNameAndSiteId() {
        ForumPost post = new ForumPost();
        post.setDateCreated(new Date(1000000000L));

        DraftForum forum = new DraftForum();
        forum.setSiteId(2);
        forum.setName("forumName");
        persistance.putItem(forum);

        DraftForum forum2 = new DraftForum();
        forum2.setSiteId(2);
        forum2.setName("forumName2");
        persistance.putItem(forum2);

        Assert.assertEquals(forum2.getId(), persistance.getForumByNameAndSiteId("forumName2", 2).getId());
        Assert.assertNull(persistance.getForumByNameAndSiteId("forumName2", -1));
    }

    @Test
    public void getRegistrationFormsByUser() {

        DraftRegistrationForm form = new DraftRegistrationForm();
        form.setName("name1");
        form.setSiteId(1);
        persistance.putRegistrationForm(form);

        DraftRegistrationForm form2 = new DraftRegistrationForm();
        form2.setName("name1");
        form2.setSiteId(1);
        persistance.putRegistrationForm(form2);

        DraftRegistrationForm form3 = new DraftRegistrationForm();
        form3.setName("name1");
        form3.setSiteId(1);
        persistance.putRegistrationForm(form3);
    }

    @Test
    public void getLastBlogPost() {
        DraftBlog blog = new DraftBlog();
        blog.setName("blogName");
        blog.setSiteId(1);
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blogPost.setCreationDate(new Date(111111111L));
        blogPost.setBlog(blog);
        persistance.putBlogPost(blogPost);

        BlogPost blogPost2 = new BlogPost();
        blogPost2.setCreationDate(new Date(112221111111L));
        blogPost2.setBlog(blog);
        persistance.putBlogPost(blogPost2);

        Comment comment = new Comment();
        persistance.putComment(comment);

        BlogPost blogPost3 = new BlogPost();
        blogPost3.addComment(comment);
        blogPost3.setCreationDate(new Date(33111113331111L));
        blogPost3.setBlog(blog);
        persistance.putBlogPost(blogPost3);

        Assert.assertEquals(blogPost3.getBlogPostId(), persistance.getLastBlogPost(blog.getId()).getBlogPostId());

        persistance.removeBlogPost(blogPost3);
        Assert.assertNull(persistance.getBlogPostById(blogPost3.getBlogPostId()));

        persistance.removeComment(comment);
        Assert.assertNull(persistance.getCommentById(comment.getCommentId()));

    }

    @Ignore
    @Test
    public void getLastForumPost() {
        DraftForum forum = new DraftForum();
        forum.setSiteId(2);
        forum.setName("forumName");
        persistance.putItem(forum);

        SubForum subForum = new SubForum();
        subForum.setSubForumDescription("description");
        subForum.setSubForumName("name");
        subForum.setForum(forum);
        persistance.putSubForum(subForum);

        ForumThread thread1 = new ForumThread();
        thread1.setSubForum(subForum);
        thread1.setUpdateDate(new Date());
        thread1.setThreadName("setThreadName1");
        persistance.putForumThread(thread1);
        Assert.assertEquals(thread1.getThreadId(), persistance.getForumThreadById(thread1.getThreadId()).getThreadId());

        ForumPost post = new ForumPost();
        post.setDateCreated(new Date(1000000000L));
        post.setThread(thread1);
        post.setDateCreated(new Date(1000000000L));
        persistance.putForumPost(post);

        ForumThread thread = new ForumThread();
        thread.setSubForum(subForum);
        thread.setUpdateDate(new Date());
        thread.setThreadName("setThreadName");
        persistance.putForumThread(thread);
        thread.addForumPost(post);

        SubForum subForum2 = new SubForum();
        subForum2.setSubForumDescription("description2");
        subForum2.setSubForumName("name2");
        subForum2.setForum(forum);
        subForum2.addForumThread(thread);
        persistance.putSubForum(subForum2);
        Assert.assertEquals(subForum2.getSubForumId(), persistance.getSubForumById(subForum2.getSubForumId()).getSubForumId());


        Assert.assertEquals(thread.getThreadId(), persistance.getForumThreads(subForum2.getSubForumId()).get(0).getThreadId());

        ForumPost post2 = new ForumPost();
        post2.setDateCreated(new Date(21000000000L));
        post2.setThread(thread);
        persistance.putForumPost(post2);

        ForumPost post3 = new ForumPost();
        post3.setDateCreated(new Date(31000000000L));
        post3.setThread(thread);
        persistance.putForumPost(post3);
        Assert.assertEquals(1, persistance.getForumPosts(thread.getThreadId()).size());


        Assert.assertEquals(post3.getForumPostId(), persistance.getLastForumPost(forum.getId()).getForumPostId());

        ForumPollVote forumPollVote = new ForumPollVote();
        forumPollVote.setThread(thread);
        forumPollVote.setAnswerNumber(11);
        forumPollVote.setRespondentId("22");
        persistance.putVote(forumPollVote);
        Assert.assertEquals(forumPollVote.getVoteId(), persistance.getForumThreadVoteByRespondentIdAndThreadId("22", thread.getThreadId()).getVoteId());

        persistance.removePost(post);
        Assert.assertNull(persistance.getForumPostById(post.getForumPostId()));


        persistance.removeThread(thread);
        Assert.assertNull(persistance.getForumThreadById(thread.getThreadId()));

        persistance.removeSubForum(subForum);
        Assert.assertNull(persistance.getSubForumById(subForum.getSubForumId()));

    }

    @Test
    public void getThreadVotesCount() {
        DraftForum forum = new DraftForum();
        forum.setSiteId(2);
        forum.setName("forumName");
        persistance.putItem(forum);

        SubForum subForum = new SubForum();
        subForum.setSubForumDescription("description");
        subForum.setSubForumName("name");
        subForum.setForum(forum);
        persistance.putSubForum(subForum);

        ForumThread thread1 = new ForumThread();
        thread1.setSubForum(subForum);
        thread1.setUpdateDate(new Date());
        thread1.setThreadName("setThreadName1");
        persistance.putForumThread(thread1);
        Assert.assertEquals(thread1.getThreadId(), persistance.getForumThreadById(thread1.getThreadId()).getThreadId());

        ForumPost post = new ForumPost();
        post.setDateCreated(new Date(1000000000L));
        post.setThread(thread1);
        post.setDateCreated(new Date(1000000000L));
        persistance.putForumPost(post);

        ForumPollVote forumPollVote = new ForumPollVote();
        forumPollVote.setThread(thread1);
        forumPollVote.setAnswerNumber(11);
        forumPollVote.setRespondentId("22");
        persistance.putVote(forumPollVote);
        Assert.assertEquals(forumPollVote.getVoteId(), persistance.getForumThreadVoteByRespondentIdAndThreadId("22", thread1.getThreadId()).getVoteId());
        Assert.assertNull(persistance.getForumThreadVoteByRespondentIdAndThreadId("-1", -1));

        Assert.assertEquals(1, persistance.getThreadVotesCount(thread1.getThreadId(), 11));
    }

    @Test
    public void getCreditCardById() {
        User account1 = new User();
        account1.setEmail("aa1");
        account1.setRegistrationDate(new Date());
        persistance.putUser(account1);

        CreditCard creditCard = new CreditCard();
        creditCard.setUser(account1);
        persistance.putCreditCard(creditCard);
        Assert.assertEquals(creditCard.getCreditCardId(), persistance.getCreditCardById(creditCard.getCreditCardId()).getCreditCardId());
    }

    @Test
    public void getForumThreadVoteByRespondentIdAndThreadId() {
        User account1 = new User();
        account1.setEmail("aa1");
        account1.setRegistrationDate(new Date());
        persistance.putUser(account1);


        CreditCard creditCard = new CreditCard();
        creditCard.setUser(account1);
        persistance.putCreditCard(creditCard);
        Assert.assertEquals(creditCard.getCreditCardId(), persistance.getCreditCardById(creditCard.getCreditCardId()).getCreditCardId());
    }

    @Test
    public void getBlogSummaryByNameAndSiteId() {
        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setSubDomain("aa1");
        site.setTitle("F");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("3");
        persistance.putSite(site);

        DraftBlogSummary blogSummary = new DraftBlogSummary();
        blogSummary.setSiteId(1);
        blogSummary.setName("name");
        persistance.putItem(blogSummary);
        Assert.assertEquals(blogSummary, persistance.getBlogSummaryByNameAndSiteId("name", site.getSiteId()));
        Assert.assertNull(persistance.getBlogSummaryByNameAndSiteId("", site.getSiteId()));
        Assert.assertNull(persistance.getBlogSummaryByNameAndSiteId("name", -1));
    }

    @Test
    public void getTaxItemByNameAndSiteId() {
        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setSubDomain("aa1");
        site.setTitle("F");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("3");
        persistance.putSite(site);

        DraftTaxRatesUS draftTaxRatesUS = new DraftTaxRatesUS();
        draftTaxRatesUS.setSiteId(1);
        draftTaxRatesUS.setName("name");
        persistance.putItem(draftTaxRatesUS);
        Assert.assertEquals(draftTaxRatesUS, persistance.getTaxRatesByNameAndSiteId("name", site.getSiteId()));
        Assert.assertNull(persistance.getTaxRatesByNameAndSiteId("", site.getSiteId()));
        Assert.assertNull(persistance.getTaxRatesByNameAndSiteId("name", -1));
    }

    @Test
    public void getImageById() {
        Image image = new Image();
        image.setName("");
        image.setSiteId(1);
        image.setSourceExtension("jpeg");
        image.setThumbnailExtension("jpeg");
        image.setCreated(new Date());
        persistance.putImage(image);
        Assert.assertEquals(image.getImageId(), persistance.getImageById(image.getImageId()).getImageId());

        persistance.removeImage(image);
        Assert.assertNull(persistance.getImageById(image.getImageId()));

    }

    @Test
    public void getFormFileById() {
        FormFile file = new FormFile();
        file.setSourceExtension("jpeg");
        file.setSourceName("1.jpeg");
        persistance.putFormFile(file);
        Assert.assertEquals(file.getFormFileId(), persistance.getFormFileById(file.getFormFileId()).getFormFileId());

        persistance.removeFormFile(file);
        Assert.assertNull(persistance.getFormFileById(file.getFormFileId()));

    }

    @Test
    public void getFormFiles() {
        Assert.assertEquals(0, persistance.getFormFiles().size());

        FormFile file = new FormFile();
        file.setSourceExtension("jpeg");
        file.setSourceName("1.jpeg");
        persistance.putFormFile(file);

        FormFile file2 = new FormFile();
        file2.setSourceExtension("jpeg");
        file2.setSourceName("1.jpeg");
        persistance.putFormFile(file2);

        FormFile file3 = new FormFile();
        file3.setSourceExtension("jpeg");
        file3.setSourceName("1.jpeg");
        persistance.putFormFile(file3);

        Assert.assertEquals(3, persistance.getFormFiles().size());
    }

    @Test
    public void getBackgroundImageById() {
        BackgroundImage image = new BackgroundImage();
        image.setSiteId(1);
        image.setSourceExtension("jpeg");
        image.setThumbnailExtension("jpeg");
        image.setCreated(new Date());
        persistance.putBackgroundImage(image);
        Assert.assertEquals(image.getBackgroundImageId(), persistance.getBackgroundImageById(image.getBackgroundImageId()).getBackgroundImageId());
    }

    @Test
    public void getImageForVideoById() {
        ImageForVideo image = new ImageForVideo();
        image.setSiteId(1);
        image.setSourceExtension("jpeg");
        image.setThumbnailExtension("jpeg");
        image.setCreated(new Date());
        persistance.putImageForVideo(image);
        Assert.assertEquals(image.getImageForVideoId(), persistance.getImageForVideoById(image.getImageForVideoId()).getImageForVideoId());
    }

    @Test
    public void getUserOnSiteRightByUserAndFilledFormId() {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        FilledForm filledForm = new FilledForm();
        filledForm.setFormDescription("gg");
        filledForm.setFormId(2);
        filledForm.setType(FormType.CUSTOM_FORM);
        persistance.putFilledForm(filledForm);

        Site site1 = new Site();
        site1.getSitePaymentSettings().setUserId(-1);
        site1.setTitle("title1");
        site1.setThemeId(new ThemeId());
        site1.setSubDomain("1");
        site1.setCreationDate(new Date());
        ThemeId id1 = new ThemeId();
        id1.setTemplateDirectory("");
        id1.setThemeCss("");
        site1.setThemeId(id1);
        persistance.putSite(site1);

        UserOnSiteRight userOnUserRight1 = new UserOnSiteRight();
        userOnUserRight1.addFilledRegistrationFormId(filledForm.getFilledFormId());
        userOnUserRight1.setActive(true);
        user.addUserOnSiteRight(userOnUserRight1);
        userOnUserRight1.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight1);
        site1.addUserOnSiteRight(userOnUserRight1);
        persistance.putUserOnSiteRight(userOnUserRight1);

        Site site2 = new Site();
        site2.getSitePaymentSettings().setUserId(-1);
        site2.setTitle("title2");
        site2.setThemeId(new ThemeId());
        site2.setSubDomain("2");
        site2.setCreationDate(new Date());
        ThemeId id2 = new ThemeId();
        id2.setTemplateDirectory("");
        id2.setThemeCss("");
        site2.setThemeId(id2);
        persistance.putSite(site2);

        UserOnSiteRight userOnUserRight2 = new UserOnSiteRight();
        userOnUserRight2.setFilledRegistrationFormIds(new ArrayList<Integer>());
        userOnUserRight2.setActive(true);
        user.addUserOnSiteRight(userOnUserRight2);
        userOnUserRight2.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight2);
        site2.addUserOnSiteRight(userOnUserRight2);
        persistance.putUserOnSiteRight(userOnUserRight2);

        Assert.assertEquals(userOnUserRight1, persistance.getUserOnSiteRightByUserAndFormId(user, filledForm.getFormId()));
    }

    @Test
    public void getImagesBySiteId() {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

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

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight);
        site.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        Image image = new Image();
        image.setName("");
        image.setSiteId(site.getSiteId());
        image.setSourceExtension("jpeg");
        image.setThumbnailExtension("jpeg");
        image.setCreated(new Date());
        persistance.putImage(image);

        Image image2 = new Image();
        image2.setName("");
        image2.setSiteId(site.getSiteId());
        image2.setSourceExtension("jpeg");
        image2.setThumbnailExtension("jpeg");
        image2.setCreated(new Date());
        persistance.putImage(image2);

        Assert.assertEquals(2, persistance.getImagesByOwnerSiteId(site.getSiteId()).size());
    }

    @Test
    public void getBackgroundImagesByUserId() {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight);

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
        site.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);


        BackgroundImage image = new BackgroundImage();
        image.setSiteId(site.getSiteId());
        image.setSourceExtension("jpeg");
        image.setThumbnailExtension("jpeg");
        image.setCreated(new Date());
        persistance.putBackgroundImage(image);

        BackgroundImage image2 = new BackgroundImage();
        image2.setSiteId(site.getSiteId());
        image2.setSourceExtension("jpeg");
        image2.setThumbnailExtension("jpeg");
        image2.setCreated(new Date());
        persistance.putBackgroundImage(image2);

        List<BackgroundImage> findBackgroundImage = persistance.getBackgroundImagesBySiteId(site.getSiteId());
        Assert.assertEquals(image, findBackgroundImage.get(0));
        Assert.assertEquals(2, findBackgroundImage.size());
    }

    @Test
    public void getImagesForVideoBySiteId() {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

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

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight);
        site.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        ImageForVideo image = new ImageForVideo();
        image.setSiteId(site.getSiteId());
        image.setSourceExtension("jpeg");
        image.setThumbnailExtension("jpeg");
        image.setCreated(new Date());
        persistance.putImageForVideo(image);

        ImageForVideo image2 = new ImageForVideo();
        image2.setSiteId(site.getSiteId());
        image2.setSourceExtension("jpeg");
        image2.setThumbnailExtension("jpeg");
        image2.setCreated(new Date());
        persistance.putImageForVideo(image2);

        List<ImageForVideo> findImageForVideo = persistance.getImagesForVideoBySiteId(site.getSiteId());
        Assert.assertEquals(image, findImageForVideo.get(0));
        Assert.assertEquals(2, findImageForVideo.size());
    }

    @Test
    public void getBlogsByUserId() {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight);

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
        site.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        DraftBlog blog = new DraftBlog();
        blog.setName("blogName");
        blog.setSiteId(site.getSiteId());
        persistance.putItem(blog);

        DraftBlog blog2 = new DraftBlog();
        blog2.setName("blogName2");
        blog2.setSiteId(site.getSiteId());
        persistance.putItem(blog2);

        List<DraftItem> findBlog = persistance.getDraftItemsByUserId(user.getUserId(), ItemType.BLOG);
        Assert.assertEquals(2, findBlog.size());
    }

    @Test
    public void getBlogsByUserIdWithSiteOnItemRight() {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

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

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight);
        site.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        DraftBlog blog = new DraftBlog();
        blog.setName("blogName");
        persistance.putItem(blog);

        SiteOnItem siteOnBlogRight = new SiteOnItem();
        siteOnBlogRight.setAcceptDate(new Date());
        siteOnBlogRight.getId().setItem(blog);
        siteOnBlogRight.getId().setSite(site);
        persistance.putSiteOnItem(siteOnBlogRight);

        List<DraftItem> findBlog = persistance.getDraftItemsByUserId(user.getUserId(), ItemType.BLOG);
        Assert.assertEquals(blog, findBlog.get(0));
        Assert.assertEquals(1, findBlog.size());
    }

    @Test
    public void getBlogsByUserIdWithSiteOnItemRightAndOwner() {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

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

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight);
        site.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        DraftBlog blog = new DraftBlog();
        blog.setName("blogName");
        blog.setSiteId(site.getSiteId());
        persistance.putItem(blog);

        SiteOnItem siteOnBlogRight = new SiteOnItem();
        siteOnBlogRight.setAcceptDate(new Date());
        siteOnBlogRight.getId().setItem(blog);
        siteOnBlogRight.getId().setSite(site);
        persistance.putSiteOnItem(siteOnBlogRight);

        List<DraftItem> findBlog = persistance.getDraftItemsByUserId(user.getUserId(), ItemType.BLOG);
        Assert.assertEquals(blog, findBlog.get(0));
        Assert.assertEquals(1, findBlog.size());
    }

    @Test
    public void getBlogsByUserIdWithSiteOnItemRightNotActivate() {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

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

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight);
        site.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        DraftBlog blog = new DraftBlog();
        blog.setName("blogName");
        persistance.putItem(blog);

        SiteOnItem siteOnBlogRight = new SiteOnItem();
        siteOnBlogRight.setAcceptDate(null);
        siteOnBlogRight.getId().setItem(blog);
        siteOnBlogRight.getId().setSite(site);
        persistance.putSiteOnItem(siteOnBlogRight);

        List<DraftItem> findBlog = persistance.getDraftItemsByUserId(user.getUserId(), ItemType.BLOG);
        Assert.assertEquals(0, findBlog.size());
    }

    @Test
    public void getCustomFormsByUserId() {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        UserOnSiteRight userOnSiteRight = new UserOnSiteRight();
        userOnSiteRight.setActive(true);
        user.addUserOnSiteRight(userOnSiteRight);
        userOnSiteRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnSiteRight);

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
        site.addUserOnSiteRight(userOnSiteRight);
        persistance.putUserOnSiteRight(userOnSiteRight);

        DraftCustomForm customForm = new DraftCustomForm();
        customForm.setName("customFormName");
        customForm.setSiteId(site.getSiteId());
        persistance.putCustomForm(customForm);

        DraftCustomForm customForm2 = new DraftCustomForm();
        customForm2.setName("customFormName2");
        customForm2.setSiteId(site.getSiteId());
        persistance.putCustomForm(customForm2);

        List<DraftItem> foundCustomForms = persistance.getDraftItemsByUserId(user.getUserId(), ItemType.CUSTOM_FORM);
        Assert.assertEquals(2, foundCustomForms.size());
    }

    @Test
    public void getCustomFormByUserIdWithSiteOnItemRight() {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

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

        UserOnSiteRight userOnSiteRight = new UserOnSiteRight();
        userOnSiteRight.setActive(true);
        user.addUserOnSiteRight(userOnSiteRight);
        userOnSiteRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnSiteRight);
        site.addUserOnSiteRight(userOnSiteRight);
        persistance.putUserOnSiteRight(userOnSiteRight);

        DraftCustomForm customForm = new DraftCustomForm();
        customForm.setName("blogName");
        persistance.putCustomForm(customForm);

        SiteOnItem siteOnCustomFormRight = new SiteOnItem();
        siteOnCustomFormRight.setAcceptDate(new Date());
        siteOnCustomFormRight.getId().setItem(customForm);
        siteOnCustomFormRight.getId().setSite(site);
        persistance.putSiteOnItem(siteOnCustomFormRight);

        List<DraftItem> foundCustomForms = persistance.getDraftItemsByUserId(user.getUserId(), ItemType.CUSTOM_FORM);
        Assert.assertEquals(customForm, foundCustomForms.get(0));
        Assert.assertEquals(1, foundCustomForms.size());
    }

    @Test
    public void getCustomFormByUserIdWithSiteOnItemRightAndOwner() {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

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

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight);
        site.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        DraftCustomForm customForm = new DraftCustomForm();
        customForm.setName("customForm");
        customForm.setSiteId(site.getSiteId());
        persistance.putCustomForm(customForm);

        SiteOnItem siteOnCustomFormRight = new SiteOnItem();
        siteOnCustomFormRight.setAcceptDate(new Date());
        siteOnCustomFormRight.getId().setItem(customForm);
        siteOnCustomFormRight.getId().setSite(site);
        persistance.putSiteOnItem(siteOnCustomFormRight);

        List<DraftItem> foundCustomForms = persistance.getDraftItemsByUserId(user.getUserId(), ItemType.CUSTOM_FORM);
        Assert.assertEquals(customForm, foundCustomForms.get(0));
        Assert.assertEquals(1, foundCustomForms.size());
    }

    @Test
    public void getCustomFormByUserIdWithSiteOnItemRightNotActivate() {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

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

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight);
        site.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        DraftCustomForm customForm = new DraftCustomForm();
        customForm.setName("customForm");
        persistance.putCustomForm(customForm);

        SiteOnItem siteOnCustomFormRight = new SiteOnItem();
        siteOnCustomFormRight.setAcceptDate(null);
        siteOnCustomFormRight.getId().setItem(customForm);
        siteOnCustomFormRight.getId().setSite(site);
        persistance.putSiteOnItem(siteOnCustomFormRight);

        List<DraftItem> foundCustomForms = persistance.getDraftItemsByUserId(user.getUserId(), ItemType.CUSTOM_FORM);
        Assert.assertEquals(0, foundCustomForms.size());
    }

    @Test
    public void getContactUsByUserIdWithSiteOnItemRight() {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

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

        UserOnSiteRight userOnSiteRight = new UserOnSiteRight();
        userOnSiteRight.setActive(true);
        user.addUserOnSiteRight(userOnSiteRight);
        userOnSiteRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnSiteRight);
        site.addUserOnSiteRight(userOnSiteRight);
        persistance.putUserOnSiteRight(userOnSiteRight);

        DraftContactUs contactUs = new DraftContactUs();
        contactUs.setName("contactUs");
        persistance.putContactUs(contactUs);

        SiteOnItem siteOnContactUsRight = new SiteOnItem();
        siteOnContactUsRight.setAcceptDate(new Date());
        siteOnContactUsRight.getId().setItem(contactUs);
        siteOnContactUsRight.getId().setSite(site);
        persistance.putSiteOnItem(siteOnContactUsRight);

        List<DraftItem> foundContactUses = persistance.getDraftItemsByUserId(user.getUserId(), ItemType.CONTACT_US);
        Assert.assertEquals(contactUs, foundContactUses.get(0));
        Assert.assertEquals(1, foundContactUses.size());
    }

    @Test
    public void getContactUsByUserIdWithSiteOnItemRightAndOwner() {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

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

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight);
        site.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        DraftContactUs customForm = new DraftContactUs();
        customForm.setName("customForm");
        customForm.setSiteId(site.getSiteId());
        persistance.putContactUs(customForm);

        SiteOnItem siteOnContactUsRight = new SiteOnItem();
        siteOnContactUsRight.setAcceptDate(new Date());
        siteOnContactUsRight.getId().setItem(customForm);
        siteOnContactUsRight.getId().setSite(site);
        persistance.putSiteOnItem(siteOnContactUsRight);

        List<DraftItem> foundContactUses = persistance.getDraftItemsByUserId(user.getUserId(), ItemType.CONTACT_US);
        Assert.assertEquals(customForm, foundContactUses.get(0));
        Assert.assertEquals(1, foundContactUses.size());
    }

    @Test
    public void getContactUsByUserIdWithSiteOnItemRightNotActivate() {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

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

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight);
        site.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        DraftContactUs contactUs = new DraftContactUs();
        contactUs.setName("contactUs");
        persistance.putContactUs(contactUs);

        SiteOnItem siteOnContactUsRight = new SiteOnItem();
        siteOnContactUsRight.setAcceptDate(null);
        siteOnContactUsRight.getId().setItem(contactUs);
        siteOnContactUsRight.getId().setSite(site);
        persistance.putSiteOnItem(siteOnContactUsRight);

        List<DraftItem> foundContactUses = persistance.getDraftItemsByUserId(user.getUserId(), ItemType.CONTACT_US);
        Assert.assertEquals(0, foundContactUses.size());
    }

    @Test
    public void getRegistrationFormByUserIdWithSiteOnItemRight() {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

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

        UserOnSiteRight userOnSiteRight = new UserOnSiteRight();
        userOnSiteRight.setActive(true);
        user.addUserOnSiteRight(userOnSiteRight);
        userOnSiteRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnSiteRight);
        site.addUserOnSiteRight(userOnSiteRight);
        persistance.putUserOnSiteRight(userOnSiteRight);

        DraftRegistrationForm registrationForm = new DraftRegistrationForm();
        registrationForm.setName("registrationForm");
        persistance.putRegistrationForm(registrationForm);

        SiteOnItem siteOnRegistrationFormRight = new SiteOnItem();
        siteOnRegistrationFormRight.setAcceptDate(new Date());
        siteOnRegistrationFormRight.getId().setItem(registrationForm);
        siteOnRegistrationFormRight.getId().setSite(site);
        persistance.putSiteOnItem(siteOnRegistrationFormRight);

        List<DraftItem> foundRegistrationForm = persistance.getDraftItemsByUserId(user.getUserId(), ItemType.REGISTRATION);
        Assert.assertEquals(registrationForm, foundRegistrationForm.get(0));
        Assert.assertEquals(1, foundRegistrationForm.size());
    }

    @Test
    public void getRegistrationFormByUserIdWithSiteOnItemRightAndOwner() {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

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

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight);
        site.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        DraftRegistrationForm registrationForm = new DraftRegistrationForm();
        registrationForm.setName("customForm");
        registrationForm.setSiteId(site.getSiteId());
        persistance.putRegistrationForm(registrationForm);

        SiteOnItem siteOnRegistrationFormRight = new SiteOnItem();
        siteOnRegistrationFormRight.setAcceptDate(new Date());
        siteOnRegistrationFormRight.getId().setItem(registrationForm);
        siteOnRegistrationFormRight.getId().setSite(site);
        persistance.putSiteOnItem(siteOnRegistrationFormRight);

        List<DraftItem> foundRegistrationForms = persistance.getDraftItemsByUserId(user.getUserId(), ItemType.REGISTRATION);
        Assert.assertEquals(registrationForm, foundRegistrationForms.get(0));
        Assert.assertEquals(1, foundRegistrationForms.size());
    }

    @Test
    public void getRegistrationFormByUserIdWithSiteOnItemRightNotActivate() {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

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

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight);
        site.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        DraftRegistrationForm registrationForm = new DraftRegistrationForm();
        registrationForm.setName("registrationForm");
        persistance.putRegistrationForm(registrationForm);

        SiteOnItem siteOnRegistrationFormRight = new SiteOnItem();
        siteOnRegistrationFormRight.setAcceptDate(null);
        siteOnRegistrationFormRight.getId().setItem(registrationForm);
        siteOnRegistrationFormRight.getId().setSite(site);
        persistance.putSiteOnItem(siteOnRegistrationFormRight);

        List<DraftItem> foundRegistrationForms = persistance.getDraftItemsByUserId(user.getUserId(), ItemType.REGISTRATION);
        Assert.assertEquals(0, foundRegistrationForms.size());
    }

    @Test
    public void getChildSiteRegistrationByUserIdWithSiteOnItemRight() {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

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

        UserOnSiteRight userOnSiteRight = new UserOnSiteRight();
        userOnSiteRight.setActive(true);
        user.addUserOnSiteRight(userOnSiteRight);
        userOnSiteRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnSiteRight);
        site.addUserOnSiteRight(userOnSiteRight);
        persistance.putUserOnSiteRight(userOnSiteRight);

        DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        childSiteRegistration.setName("childSiteRegistration");
        childSiteRegistration.setTermsAndConditions("");
        childSiteRegistration.setEmailText("");
        childSiteRegistration.setWelcomeText("");
        persistance.putItem(childSiteRegistration);

        SiteOnItem siteOnItem = new SiteOnItem();
        siteOnItem.setAcceptDate(new Date());
        siteOnItem.getId().setSite(site);
        siteOnItem.getId().setItem(childSiteRegistration);
        persistance.putSiteOnItem(siteOnItem);

        List<DraftItem> foundChildSiteRegistrations = persistance.getDraftItemsByUserId(user.getUserId(), ItemType.CHILD_SITE_REGISTRATION);
        Assert.assertEquals(childSiteRegistration, foundChildSiteRegistrations.get(0));
        Assert.assertEquals(1, foundChildSiteRegistrations.size());
    }

    @Test
    public void getChildSiteRegistrationByUserIdWithSiteOnItemRightAndOwner() {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

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

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight);
        site.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        childSiteRegistration.setName("childSiteRegistration");
        childSiteRegistration.setTermsAndConditions("");
        childSiteRegistration.setEmailText("");
        childSiteRegistration.setWelcomeText("");
        childSiteRegistration.setSiteId(site.getSiteId());
        persistance.putItem(childSiteRegistration);

        SiteOnItem siteOnItem = new SiteOnItem();
        siteOnItem.setAcceptDate(new Date());
        siteOnItem.getId().setSite(site);
        siteOnItem.getId().setItem(childSiteRegistration);
        persistance.putSiteOnItem(siteOnItem);

        List<DraftItem> foundChildSiteRegistrations = persistance.getDraftItemsByUserId(user.getUserId(), ItemType.CHILD_SITE_REGISTRATION);
        Assert.assertEquals(childSiteRegistration, foundChildSiteRegistrations.get(0));
        Assert.assertEquals(1, foundChildSiteRegistrations.size());
    }

    @Test
    public void getChildSiteRegistrationByUserIdWithSiteOnItemRightNotActivate() {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

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

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight);
        site.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        childSiteRegistration.setName("childSiteRegistration");
        childSiteRegistration.setTermsAndConditions("");
        childSiteRegistration.setEmailText("");
        childSiteRegistration.setWelcomeText("");
        persistance.putItem(childSiteRegistration);

        SiteOnItem siteOnItem = new SiteOnItem();
        siteOnItem.setAcceptDate(null);
        siteOnItem.getId().setSite(site);
        siteOnItem.getId().setItem(childSiteRegistration);
        persistance.putSiteOnItem(siteOnItem);

        List<DraftItem> foundChildSiteRegistrations = persistance.getDraftItemsByUserId(user.getUserId(), ItemType.CHILD_SITE_REGISTRATION);
        Assert.assertEquals(0, foundChildSiteRegistrations.size());
    }

    @Test
    public void getOrderFormsByUserIdWithSiteOnItemRight() {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

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

        UserOnSiteRight userOnSiteRight = new UserOnSiteRight();
        userOnSiteRight.setActive(true);
        user.addUserOnSiteRight(userOnSiteRight);
        userOnSiteRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnSiteRight);
        site.addUserOnSiteRight(userOnSiteRight);
        persistance.putUserOnSiteRight(userOnSiteRight);

        DraftCustomForm orderForm = new DraftCustomForm();
        orderForm.setName("orderForm");
        orderForm.setType(FormType.ORDER_FORM);
        persistance.putCustomForm(orderForm);

        SiteOnItem siteOnItem = new SiteOnItem();
        siteOnItem.setAcceptDate(new Date());
        siteOnItem.getId().setSite(site);
        siteOnItem.getId().setItem(orderForm);
        persistance.putSiteOnItem(siteOnItem);

        List<DraftItem> foundChildSiteRegistrations = persistance.getDraftItemsByUserId(user.getUserId(), ItemType.ORDER_FORM);
        Assert.assertEquals(orderForm, foundChildSiteRegistrations.get(0));
        Assert.assertEquals(1, foundChildSiteRegistrations.size());
    }

    @Test
    public void getOrderFormsByUserIdWithSiteOnItemRightAndOwner() {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

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

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight);
        site.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        DraftCustomForm orderForm = new DraftCustomForm();
        orderForm.setName("orderForm");
        orderForm.setType(FormType.ORDER_FORM);
        persistance.putCustomForm(orderForm);

        SiteOnItem siteOnItem = new SiteOnItem();
        siteOnItem.setAcceptDate(new Date());
        siteOnItem.getId().setSite(site);
        siteOnItem.getId().setItem(orderForm);
        persistance.putSiteOnItem(siteOnItem);

        List<DraftItem> foundChildSiteRegistrations = persistance.getDraftItemsByUserId(user.getUserId(), ItemType.ORDER_FORM);
        Assert.assertEquals(orderForm, foundChildSiteRegistrations.get(0));
        Assert.assertEquals(1, foundChildSiteRegistrations.size());
    }

    @Test
    public void getOrderFormsByUserIdWithSiteOnItemRightNotActivate() {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

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

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight);
        site.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        DraftCustomForm orderForm = new DraftCustomForm();
        orderForm.setName("orderForm");
        orderForm.setType(FormType.ORDER_FORM);
        persistance.putCustomForm(orderForm);

        SiteOnItem siteOnItem = new SiteOnItem();
        siteOnItem.setAcceptDate(null);
        siteOnItem.getId().setSite(site);
        siteOnItem.getId().setItem(orderForm);
        persistance.putSiteOnItem(siteOnItem);

        List<DraftItem> foundChildSiteRegistrations = persistance.getDraftItemsByUserId(user.getUserId(), ItemType.ORDER_FORM);
        Assert.assertEquals(0, foundChildSiteRegistrations.size());
    }

    @Test
    public void testRemoveChildSiteSettingsWithSitePaymentSettingsWhichConnectedToSite() {
        final User user = TestUtil.createUser();

        Site parentSite = new Site();
        parentSite.getSitePaymentSettings().setUserId(-1);
        parentSite.setTitle("title1");
        parentSite.setThemeId(new ThemeId());
        parentSite.setSubDomain("1");
        parentSite.setCreationDate(new Date());
        ThemeId id = new ThemeId();
        id.setTemplateDirectory("");
        id.setThemeCss("");
        parentSite.setThemeId(id);
        persistance.putSite(parentSite);

        Site childSite = new Site();
        childSite.getSitePaymentSettings().setUserId(-1);
        childSite.setTitle("title2");
        childSite.setThemeId(new ThemeId());
        childSite.setSubDomain("2");
        childSite.setCreationDate(new Date());
        ThemeId themeId = new ThemeId();
        themeId.setTemplateDirectory("2");
        themeId.setThemeCss("2");
        childSite.setThemeId(themeId);
        persistance.putSite(childSite);

        DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        childSiteRegistration.setName("childSiteRegistration");
        childSiteRegistration.setTermsAndConditions("");
        childSiteRegistration.setEmailText("");
        childSiteRegistration.setWelcomeText("");
        persistance.putItem(childSiteRegistration);


        ChildSiteSettings settings = new ChildSiteSettings();
        settings.getSitePaymentSettings().setUserId(-1);
        settings.setWelcomeText("");
        settings.setChildSiteRegistration(childSiteRegistration);
        settings.setCanBePublishedMessageSent(true);
        settings.setSiteStatus(SiteStatus.SUSPENDED);
        settings.setCreatedDate(new Date());
        settings.setUserId(-1);
        settings.setParentSite(parentSite);
        settings.setSite(childSite);
        persistance.putChildSiteSettings(settings);
        childSite.setChildSiteSettings(settings);
        settings.getSitePaymentSettings().setPaymentMethod(PaymentMethod.PAYPAL);
        childSite.setSitePaymentSettings(settings.getSitePaymentSettings());
        user.addChildSiteSettingsId(settings.getId());

        Assert.assertNotNull(childSite.getChildSiteSettings());
        Assert.assertNull(parentSite.getChildSiteSettings());
        Assert.assertEquals(settings, persistance.getChildSiteSettingsById(settings.getChildSiteSettingsId()));

        HibernateManager.get().flush();
        persistance.removeChildSiteSettings(settings);

        HibernateManager.get().flush();
        HibernateManager.get().refresh(user);
        childSite = persistance.getSite(childSite.getSiteId());
        HibernateManager.get().refresh(childSite);
        Assert.assertNull(persistance.getChildSiteSettingsById(settings.getChildSiteSettingsId()));
        Assert.assertNull(childSite.getChildSiteSettings());
        Assert.assertNotNull(persistance.getSitePaymentSettingsById(childSite.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNotNull(childSite.getSitePaymentSettings());
        Assert.assertEquals(PaymentMethod.PAYPAL, childSite.getSitePaymentSettings().getPaymentMethod());
        Assert.assertFalse(user.getChildSiteSettingsId().contains(settings.getId()));
    }

    @Test
    public void testRemoveSiteWithChildSiteSettingsWithSitePaymentSettingsWhichConnectedToSite() {
        Site parentSite = new Site();
        parentSite.getSitePaymentSettings().setUserId(-1);
        parentSite.setTitle("title1");
        parentSite.setThemeId(new ThemeId());
        parentSite.setSubDomain("1");
        parentSite.setCreationDate(new Date());
        ThemeId id = new ThemeId();
        id.setTemplateDirectory("");
        id.setThemeCss("");
        parentSite.setThemeId(id);
        persistance.putSite(parentSite);

        Site childSite = new Site();
        childSite.getSitePaymentSettings().setUserId(-1);
        childSite.setTitle("title2");
        childSite.setThemeId(new ThemeId());
        childSite.setSubDomain("2");
        childSite.setCreationDate(new Date());
        ThemeId themeId = new ThemeId();
        themeId.setTemplateDirectory("2");
        themeId.setThemeCss("2");
        childSite.setThemeId(themeId);
        persistance.putSite(childSite);

        DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        childSiteRegistration.setName("childSiteRegistration");
        childSiteRegistration.setTermsAndConditions("");
        childSiteRegistration.setEmailText("");
        childSiteRegistration.setWelcomeText("");
        persistance.putItem(childSiteRegistration);


        ChildSiteSettings settings = new ChildSiteSettings();
        settings.getSitePaymentSettings().setUserId(-1);
        settings.setWelcomeText("");
        settings.setChildSiteRegistration(childSiteRegistration);
        settings.setCanBePublishedMessageSent(true);
        settings.setSiteStatus(SiteStatus.SUSPENDED);
        settings.setCreatedDate(new Date());
        settings.setUserId(-1);
        settings.setParentSite(parentSite);
        settings.setSite(childSite);
        persistance.putChildSiteSettings(settings);
        childSite.setChildSiteSettings(settings);
        settings.getSitePaymentSettings().setPaymentMethod(PaymentMethod.PAYPAL);
        childSite.setSitePaymentSettings(settings.getSitePaymentSettings());


        Assert.assertNotNull(childSite.getChildSiteSettings());
        Assert.assertNull(parentSite.getChildSiteSettings());
        Assert.assertEquals(settings, persistance.getChildSiteSettingsById(settings.getChildSiteSettingsId()));

        HibernateManager.get().flush();
        persistance.removeSite(childSite);

        HibernateManager.get().flush();
        Assert.assertNull(persistance.getSite(childSite.getSiteId()));
        Assert.assertNull(persistance.getChildSiteSettingsById(settings.getChildSiteSettingsId()));
        Assert.assertNull(persistance.getSitePaymentSettingsById(childSite.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(persistance.getSitePaymentSettingsById(settings.getSitePaymentSettings().getSitePaymentSettingsId()));
    }

    @Test
    public void put_get_remove_ChildSiteSettings() {
        Site parentSite = new Site();
        parentSite.getSitePaymentSettings().setUserId(-1);
        parentSite.setTitle("title1");
        parentSite.setThemeId(new ThemeId());
        parentSite.setSubDomain("1");
        parentSite.setCreationDate(new Date());
        ThemeId id = new ThemeId();
        id.setTemplateDirectory("");
        id.setThemeCss("");
        parentSite.setThemeId(id);
        persistance.putSite(parentSite);

        Site childSite = new Site();
        childSite.getSitePaymentSettings().setUserId(-1);
        childSite.setTitle("title2");
        childSite.setThemeId(new ThemeId());
        childSite.setSubDomain("2");
        childSite.setCreationDate(new Date());
        ThemeId themeId = new ThemeId();
        themeId.setTemplateDirectory("2");
        themeId.setThemeCss("2");
        childSite.setThemeId(themeId);
        persistance.putSite(childSite);

        DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        childSiteRegistration.setName("childSiteRegistration");
        childSiteRegistration.setTermsAndConditions("");
        childSiteRegistration.setEmailText("");
        childSiteRegistration.setWelcomeText("");
        persistance.putItem(childSiteRegistration);


        ChildSiteSettings settings = new ChildSiteSettings();
        settings.getSitePaymentSettings().setUserId(-1);
        settings.setWelcomeText("");
        settings.setChildSiteRegistration(childSiteRegistration);
        settings.setCanBePublishedMessageSent(true);
        settings.setSiteStatus(SiteStatus.SUSPENDED);
        settings.setCreatedDate(new Date());
        settings.setUserId(-1);
        settings.setParentSite(parentSite);
        settings.setSite(childSite);
        persistance.putChildSiteSettings(settings);
        childSite.setChildSiteSettings(settings);


        Assert.assertNotNull(childSite.getChildSiteSettings());
        Assert.assertNull(parentSite.getChildSiteSettings());
        Assert.assertEquals(settings, persistance.getChildSiteSettingsById(settings.getChildSiteSettingsId()));

        HibernateManager.get().flush();
        persistance.removeChildSiteSettings(settings);

        HibernateManager.get().flush();
        childSite = persistance.getSite(childSite.getSiteId());
        HibernateManager.get().refresh(childSite);
        Assert.assertNull(persistance.getChildSiteSettingsById(settings.getChildSiteSettingsId()));
        Assert.assertNull(childSite.getChildSiteSettings());
    }

    @Test
    public void getBlogSummariesFromAllAvailableUsers_SitesByUserId() {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight);

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
        site.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        DraftBlogSummary blogSummary = new DraftBlogSummary();
        blogSummary.setName("blogSummary");
        blogSummary.setSiteId(1);
        persistance.putItem(blogSummary);

        DraftBlogSummary blogSummary2 = new DraftBlogSummary();
        blogSummary2.setName("backgroundName2");
        blogSummary2.setSiteId(1);
        persistance.putItem(blogSummary2);

        final List<DraftItem> blogSummaries =
                persistance.getDraftItemsByUserId(user.getUserId(), ItemType.BLOG_SUMMARY);
//        Assert.assertEquals(blogSummary, blogSummaries.get(0));
        Assert.assertEquals(2, blogSummaries.size());
    }

    @Test
    public void getRegistrationFormsFromAllAvailableUsers_SitesByUserId() {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight);

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
        site.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        DraftRegistrationForm registrationForm = new DraftRegistrationForm();
        registrationForm.setName("1");
        registrationForm.setSiteId(site.getSiteId());
        persistance.putRegistrationForm(registrationForm);

        DraftRegistrationForm registrationForm2 = new DraftRegistrationForm();
        registrationForm2.setName("2");
        registrationForm2.setSiteId(site.getSiteId());
        persistance.putRegistrationForm(registrationForm2);

        List<DraftItem> foundRegistrationForms = persistance.getDraftItemsByUserId(user.getUserId(), ItemType.REGISTRATION);

        Assert.assertTrue(foundRegistrationForms.contains(registrationForm));
        Assert.assertTrue(foundRegistrationForms.contains(registrationForm2));
        Assert.assertEquals(2, foundRegistrationForms.size());
    }

    @Test
    public void getVideosFromAllAvailableUsers_SitesByUserId() {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight);

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
        site.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        Video video = new Video();
        video.setSourceExtension("avi");
        video.setSourceName("1.avi");
        video.setSiteId(site.getSiteId());
        persistance.putVideo(video);

        Video video2 = new Video();
        video2.setSourceExtension("avi");
        video2.setSourceName("1.avi");
        video2.setSiteId(site.getSiteId());
        persistance.putVideo(video2);

        List<Video> videos = persistance.getVideosBySiteId(site.getSiteId());
        Assert.assertEquals(video, videos.get(0));
        Assert.assertEquals(2, videos.size());
        Assert.assertEquals(video.getVideoId(), persistance.getVideoById(video.getVideoId()).getVideoId());
    }

    @Test
    public void getVideos() {
        Video video = new Video();
        video.setSourceExtension("avi");
        video.setSourceName("1.avi");
        persistance.putVideo(video);

        Video video2 = new Video();
        video2.setSourceExtension("avi");
        video2.setSourceName("1.avi");
        persistance.putVideo(video2);

        List<Video> videos = persistance.getVideos();
        Assert.assertEquals(video, videos.get(0));
        Assert.assertEquals(2, videos.size());
        Assert.assertEquals(video.getVideoId(), persistance.getVideoById(video.getVideoId()).getVideoId());
    }


    @Test
    public void getSitesByUserIdSyteTypesAndAccessLevel() {
        User user = TestUtil.createUser("a");
        Site site1 = TestUtil.createSite("title1", "url1");
        Site site2 = TestUtil.createSite("title2", "url2");
        Site site3 = TestUtil.createSite("title3", "url3");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user, site2, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightActive(user, site3, SiteAccessLevel.ADMINISTRATOR);

        List<Site> userSites = persistance.getSites(user.getUserId(), new SiteAccessLevel[]{SiteAccessLevel.ADMINISTRATOR});
        Assert.assertEquals(2, userSites.size());

        userSites = persistance.getSites(user.getUserId(), new SiteAccessLevel[]{SiteAccessLevel.EDITOR});
        Assert.assertEquals(1, userSites.size());
    }

    @Test
    public void getSitesByUserId() {
        User user = new User();
        user.setPassword("1");
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("title1");
        site.setSubDomain("1");
        site.setCreationDate(new Date());
        ThemeId id = new ThemeId();
        id.setTemplateDirectory("");
        id.setThemeCss("");
        site.setThemeId(id);
        persistance.putSite(site);

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        site.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);


        Site visitorSite = new Site();
        visitorSite.getSitePaymentSettings().setUserId(-1);
        visitorSite.setTitle("title2");
        visitorSite.setSubDomain("2");
        visitorSite.setCreationDate(new Date());
        id = new ThemeId();
        id.setTemplateDirectory("setTemplateDirectory");
        id.setThemeCss("setThemeCss");
        visitorSite.setThemeId(id);
        persistance.putSite(visitorSite);

        UserOnSiteRight userOnUserRightForVisitorSite = new UserOnSiteRight();
        userOnUserRightForVisitorSite.setActive(true);
        user.addUserOnSiteRight(userOnUserRightForVisitorSite);
        userOnUserRightForVisitorSite.setSiteAccessType(SiteAccessLevel.VISITOR);
        visitorSite.addUserOnSiteRight(userOnUserRightForVisitorSite);
        persistance.putUserOnSiteRight(userOnUserRightForVisitorSite);


        Site siteWithNONERights = new Site();
        siteWithNONERights.getSitePaymentSettings().setUserId(-1);
        siteWithNONERights.setTitle("title3");
        siteWithNONERights.setSubDomain("3");
        siteWithNONERights.setCreationDate(new Date());
        id = new ThemeId();
        id.setTemplateDirectory("setTemplateDirectory3");
        id.setThemeCss("setThemeCss3");
        siteWithNONERights.setThemeId(id);
        persistance.putSite(siteWithNONERights);

        Site siteWithoutights = new Site();
        siteWithoutights.getSitePaymentSettings().setUserId(-1);
        siteWithoutights.setTitle("title4");
        siteWithoutights.setSubDomain("4");
        siteWithoutights.setCreationDate(new Date());
        id = new ThemeId();
        id.setTemplateDirectory("setTemplateDirectory4");
        id.setThemeCss("setThemeCss4");
        siteWithoutights.setThemeId(id);
        persistance.putSite(siteWithoutights);


        List<Site> userSites = persistance.getSites(user.getUserId(), SiteAccessLevel.getUserAccessLevels());
        Assert.assertEquals(1, userSites.size());
        Assert.assertEquals(site, userSites.get(0));
    }

    @Test
    public void getBlueprintsByUserId() {
        User user = new User();
        user.setPassword("1");
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("title1");
        site.setSubDomain("1");
        site.setType(SiteType.BLUEPRINT);
        site.setCreationDate(new Date());
        ThemeId id = new ThemeId();
        id.setTemplateDirectory("");
        id.setThemeCss("");
        site.setThemeId(id);
        persistance.putSite(site);

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        site.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        List<Site> userSites = persistance.getSites(
                user.getUserId(), SiteAccessLevel.getUserAccessLevels(), SiteType.BLUEPRINT);
        Assert.assertEquals(site, userSites.get(0));
        Assert.assertEquals(1, userSites.size());
    }

    @Test
    public void getSitesByUserIdWithoutActive() {
        User user = new User();
        user.setPassword("1");
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("title1");
        site.setSubDomain("1");
        site.setCreationDate(new Date());
        ThemeId id = new ThemeId();
        id.setTemplateDirectory("");
        id.setThemeCss("");
        site.setThemeId(id);
        persistance.putSite(site);

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(false);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        site.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        Assert.assertEquals(0, persistance.getSites(user.getUserId(), SiteAccessLevel.getUserAccessLevels()).size());
    }

    @Test
    public void getContactUsById() {
        final Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setSubDomain("f");
        site.setTitle("G");
        site.getThemeId().setTemplateDirectory("a");
        site.getThemeId().setThemeCss("3");
        persistance.putSite(site);

        DraftContactUs contactUs = new DraftContactUs();
        contactUs.setShowDescription(true);
        contactUs.setEmail("aa");
        contactUs.setName("name");
        //contactUs.setType(FormType.CONTACT_US);
        persistance.putContactUs(contactUs);

        Assert.assertEquals(contactUs, persistance.getContactUsById(contactUs.getFormId()));
    }

    @Test
    public void getContactUsByNameAndSiteId() {
        final Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setSubDomain("f");
        site.setTitle("G");
        site.getThemeId().setTemplateDirectory("a");
        site.getThemeId().setThemeCss("3");
        persistance.putSite(site);

        DraftContactUs contactUs = new DraftContactUs();
        contactUs.setShowDescription(true);
        contactUs.setEmail("aa");
        contactUs.setName("name");
        contactUs.setSiteId(site.getSiteId());
        //contactUs.setType(FormType.CONTACT_US);
        persistance.putContactUs(contactUs);

        Assert.assertEquals(contactUs, persistance.getContactUsByNameAndSiteId("name", site.getSiteId()));
        Assert.assertNull(persistance.getContactUsByNameAndSiteId("a", site.getSiteId()));
    }

    @Test
    public void getContactUsByNameAndSiteIdWithNotMySite() {
        final Site site1 = new Site();
        site1.getSitePaymentSettings().setUserId(-1);
        site1.setSubDomain("f");
        site1.setTitle("G");
        site1.getThemeId().setTemplateDirectory("a");
        site1.getThemeId().setThemeCss("3");
        persistance.putSite(site1);

        final Site site2 = new Site();
        site2.getSitePaymentSettings().setUserId(-1);
        site2.setSubDomain("f1");
        site2.setTitle("G");
        site2.getThemeId().setTemplateDirectory("a");
        site2.getThemeId().setThemeCss("3");
        persistance.putSite(site2);

        DraftContactUs contactUs = new DraftContactUs();
        contactUs.setShowDescription(true);
        contactUs.setEmail("aa");
        contactUs.setName("name");
        contactUs.setSiteId(site1.getSiteId());
        //contactUs.setType(FormType.CONTACT_US);
        persistance.putContactUs(contactUs);

        Assert.assertNull(persistance.getContactUsByNameAndSiteId("name", site2.getSiteId()));
        Assert.assertNull(persistance.getContactUsByNameAndSiteId("a", site2.getSiteId()));
    }

    @Test
    public void getAllAvailableContactUsBySiteId() {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

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

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight);
        site.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        DraftContactUs contactUs = new DraftContactUs();
        contactUs.setShowDescription(true);
        contactUs.setEmail("aa");
        contactUs.setName("name");
        contactUs.setSiteId(site.getSiteId());
        //contactUs.setType(FormType.CONTACT_US);
        persistance.putContactUs(contactUs);

        DraftContactUs contactUs2 = new DraftContactUs();
        contactUs2.setShowDescription(true);
        contactUs2.setEmail("aasadas");
        contactUs2.setName("name");
        contactUs2.setSiteId(site.getSiteId());
        //contactUs2.setType(FormType.CONTACT_US);
        persistance.putContactUs(contactUs2);

        Assert.assertEquals(2, persistance.getDraftItemsByUserId(user.getUserId(), ItemType.CONTACT_US).size());
    }

    @Test
    public void putChildSiteRegistration() {
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


        DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        childSiteRegistration.setDescription("comment");
        childSiteRegistration.setName("name");
        childSiteRegistration.setTermsAndConditions("");
        childSiteRegistration.setEmailText("");
        childSiteRegistration.setWelcomeText("");
        //childSiteRegistration.setNetworkSettingsId(1);
        //childSiteRegistration.setSite(site);
        //childSiteRegistration.setType(FormType.CHILD_SITE_REGISTRATION);
        persistance.putItem(childSiteRegistration);

        DraftChildSiteRegistration findChildSiteRegistration = persistance.getChildSiteRegistrationById(childSiteRegistration.getFormId());
        //Assert.assertEquals(findChildSiteRegistration.getSite().getSiteId(), childSiteRegistration.getSite().getSiteId());
        Assert.assertEquals(findChildSiteRegistration.getName(), childSiteRegistration.getName());
        Assert.assertEquals(findChildSiteRegistration.getDescription(), childSiteRegistration.getDescription());
        // Assert.assertEquals(findChildSiteRegistration.getNetworkSettingsId(), childSiteRegistration.getNetworkSettingsId());
    }

    @Test
    public void getChildSiteRegistrationByUserId() {
        User user = new User();
        user.setEmail("aa");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

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

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight);
        site1.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        Site site2 = new Site();
        site2.getSitePaymentSettings().setUserId(-1);
        site2.setTitle("title2");
        site2.setSubDomain("2");
        site2.setCreationDate(new Date());
        ThemeId id2 = new ThemeId();
        id2.setTemplateDirectory("2");
        id2.setThemeCss("2");
        site2.setThemeId(id2);
        persistance.putSite(site2);

        UserOnSiteRight userOnUserRight2 = new UserOnSiteRight();
        userOnUserRight2.setActive(false);
        user.addUserOnSiteRight(userOnUserRight2);
        userOnUserRight2.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight2);
        site2.addUserOnSiteRight(userOnUserRight2);
        persistance.putUserOnSiteRight(userOnUserRight2);


        DraftChildSiteRegistration childSiteRegistration1 = new DraftChildSiteRegistration();
        childSiteRegistration1.setDescription("Comment1");
        childSiteRegistration1.setName("Name1");
        childSiteRegistration1.setTermsAndConditions("");
        childSiteRegistration1.setEmailText("");
        childSiteRegistration1.setWelcomeText("");
        //childSiteRegistration1.setNetworkSettingsId(1);
        //childSiteRegistration1.setSite(site1);
        //childSiteRegistration1.setType(FormType.CHILD_SITE_REGISTRATION);
        persistance.putItem(childSiteRegistration1);

        DraftChildSiteRegistration childSiteRegistration2 = new DraftChildSiteRegistration();
        childSiteRegistration2.setDescription("Comment2");
        childSiteRegistration2.setName("Name2");
        childSiteRegistration2.setTermsAndConditions("");
        childSiteRegistration2.setEmailText("");
        childSiteRegistration2.setWelcomeText("");
        // childSiteRegistration2.setNetworkSettingsId(1);
        //childSiteRegistration2.setSite(site1);
        //childSiteRegistration2.setType(FormType.CHILD_SITE_REGISTRATION);
        persistance.putItem(childSiteRegistration2);


        DraftChildSiteRegistration childSiteRegistration3 = new DraftChildSiteRegistration();
        childSiteRegistration3.setDescription("Comment3");
        childSiteRegistration3.setName("Name3");
        childSiteRegistration3.setTermsAndConditions("");
        childSiteRegistration3.setEmailText("");
        childSiteRegistration3.setWelcomeText("");
        //childSiteRegistration3.setNetworkSettingsId(1);
        //childSiteRegistration3.setSite(site2);
        //childSiteRegistration3FormImage.setType(FormType.CHILD_SITE_REGISTRATION);
        persistance.putItem(childSiteRegistration3);

        //List<ChildSiteRegistration> childSiteRegistrations = persistance.getChildSiteRegistrationBySiteId(site1.getSiteId());
        //Assert.assertEquals(childSiteRegistrations.size(), 2);
    }

    @Test
    public void getFilledFormsNumberByFormId() {
        FilledForm filledForm = new FilledForm();
        filledForm.setFormDescription("gg");
        filledForm.setFormId(2);
        filledForm.setType(FormType.CUSTOM_FORM);
        persistance.putFilledForm(filledForm);

        FilledForm filledForm1 = new FilledForm();
        filledForm1.setFormDescription("gg1");
        filledForm1.setFormId(2);
        filledForm1.setType(FormType.CUSTOM_FORM);
        persistance.putFilledForm(filledForm1);

        FilledForm filledForm2 = new FilledForm();
        filledForm2.setFormDescription("gg2");
        filledForm2.setFormId(2);
        filledForm2.setType(FormType.CUSTOM_FORM);
        persistance.putFilledForm(filledForm2);

        FilledForm filledForm3 = new FilledForm();
        filledForm3.setFormDescription("gg3");
        filledForm3.setFormId(2);
        filledForm3.setType(FormType.CUSTOM_FORM);
        persistance.putFilledForm(filledForm3);

        FilledForm filledForm4 = new FilledForm();
        filledForm4.setFormDescription("gg3");
        filledForm4.setFormId(-1);
        filledForm4.setType(FormType.CUSTOM_FORM);
        persistance.putFilledForm(filledForm4);

        FilledFormItem filledFormItem1 = new FilledFormItem();
        filledFormItem1.setFormItemName(FormItemName.NAME);
        filledFormItem1.getValues().add("g");
        filledFormItem1.setFormItemId(1);
        filledForm.getFilledFormItems().add(filledFormItem1);
        filledFormItem1.setFilledForm(filledForm);
        persistance.putFilledFormItem(filledFormItem1);

        Assert.assertEquals(4, persistance.getFilledFormsNumberByFormId(2));
    }

    @Test
    public void getMaxFilledFormIdByFormId() {
        FilledForm filledForm = new FilledForm();
        filledForm.setFormDescription("gg");
        filledForm.setFormId(2);
        filledForm.setType(FormType.CUSTOM_FORM);
        persistance.putFilledForm(filledForm);

        FilledForm filledForm1 = new FilledForm();
        filledForm1.setFormDescription("gg1");
        filledForm1.setFormId(2);
        filledForm1.setType(FormType.CUSTOM_FORM);
        persistance.putFilledForm(filledForm1);

        FilledForm filledForm2 = new FilledForm();
        filledForm2.setFormDescription("gg2");
        filledForm2.setFormId(2);
        filledForm2.setType(FormType.CUSTOM_FORM);
        persistance.putFilledForm(filledForm2);

        FilledForm filledForm3 = new FilledForm();
        filledForm3.setFormDescription("gg3");
        filledForm3.setFormId(2);
        filledForm3.setType(FormType.CUSTOM_FORM);
        persistance.putFilledForm(filledForm3);

        FilledForm filledForm4 = new FilledForm();
        filledForm4.setFormDescription("gg3");
        filledForm4.setFormId(3);
        filledForm4.setType(FormType.CUSTOM_FORM);
        persistance.putFilledForm(filledForm4);

        FilledFormItem filledFormItem1 = new FilledFormItem();
        filledFormItem1.setFormItemName(FormItemName.NAME);
        filledFormItem1.getValues().add("g");
        filledFormItem1.setFormItemId(1);
        filledForm.getFilledFormItems().add(filledFormItem1);
        filledFormItem1.setFilledForm(filledForm);
        persistance.putFilledFormItem(filledFormItem1);

        Assert.assertEquals((Integer) 4, persistance.getMaxFilledFormIdByFormId(2));

        FilledForm filledForm5 = new FilledForm();
        filledForm5.setFormDescription("gg3");
        filledForm5.setFormId(2);
        filledForm5.setType(FormType.CUSTOM_FORM);
        persistance.putFilledForm(filledForm5);


        Assert.assertEquals((Integer) 6, persistance.getMaxFilledFormIdByFormId(2));
    }

    @Test
    public void getMaxFilledFormIdByFormIdWithoutFilled() {
        Assert.assertNull(persistance.getMaxFilledFormIdByFormId(2));
    }


    @Test
    public void testGetManageVotesListBySiteId() {
        User user = new User();
        user.setEmail("aa");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

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

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight);
        site1.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        Site site2 = new Site();
        site2.getSitePaymentSettings().setUserId(-1);
        site2.setTitle("title2");
        site2.setSubDomain("2");
        site2.setCreationDate(new Date());
        ThemeId id2 = new ThemeId();
        id2.setTemplateDirectory("2");
        id2.setThemeCss("2");
        site2.setThemeId(id2);
        persistance.putSite(site2);

        UserOnSiteRight userOnUserRight2 = new UserOnSiteRight();
        userOnUserRight2.setActive(false);
        user.addUserOnSiteRight(userOnUserRight2);
        userOnUserRight2.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight2);
        site2.addUserOnSiteRight(userOnUserRight2);
        persistance.putUserOnSiteRight(userOnUserRight2);


        DraftManageVotes manageVotes1 = new DraftManageVotes();
        manageVotes1.setSiteId(site1.getSiteId());
        persistance.putItem(manageVotes1);

        DraftManageVotes manageVotes2 = new DraftManageVotes();
        manageVotes2.setSiteId(site1.getSiteId());
        persistance.putItem(manageVotes2);

        DraftManageVotes manageVotes3 = new DraftManageVotes();
        manageVotes3.setSiteId(site2.getSiteId());
        persistance.putItem(manageVotes3);


        List<DraftManageVotes> manageVotesForSite1 = persistance.getManageVotesListBySiteId(site1.getSiteId());
        Assert.assertEquals(2, manageVotesForSite1.size());
        Assert.assertEquals(manageVotes1.getId(), manageVotesForSite1.get(0).getId());
        Assert.assertEquals(manageVotes2.getId(), manageVotesForSite1.get(1).getId());

        List<DraftManageVotes> manageVotesForSite2 = persistance.getManageVotesListBySiteId(site2.getSiteId());
        Assert.assertEquals(1, manageVotesForSite2.size());
        Assert.assertEquals(manageVotes3.getId(), manageVotesForSite2.get(0).getId());
    }

    @Test
    public void testPutGetVote() {
        Vote vote = new Vote();
        vote.setVoteValue(1);
        vote.setVoteDate(new Date());
        vote.setGalleryId(1);
        vote.setFilledFormId(1);
        vote.setUserId(null);

        persistance.putVote(vote);
        Vote newVote = persistance.getVoteById(vote.getVoteId());

        Assert.assertEquals(vote.getVoteId(), newVote.getVoteId());
        Assert.assertEquals(vote.getUserId(), newVote.getUserId());
        Assert.assertEquals(vote.getGalleryId(), newVote.getGalleryId());
        Assert.assertEquals(vote.getFilledFormId(), newVote.getFilledFormId());
        Assert.assertEquals(vote.getVoteValue(), newVote.getVoteValue());
    }

    @Test
    public void testGetVotesByFilledFormId() {
        Vote vote1 = new Vote();
        vote1.setFilledFormId(5);
        vote1.setVoteDate(new Date());
        persistance.putVote(vote1);

        Vote vote2 = new Vote();
        vote2.setFilledFormId(5);
        vote2.setVoteDate(new Date());
        persistance.putVote(vote2);

        Vote vote3 = new Vote();
        vote3.setFilledFormId(5);
        vote3.setVoteDate(new Date());
        persistance.putVote(vote3);

        Assert.assertEquals(3L, ((HibernatePersistance) persistance).getVotesCount().longValue());

        persistance.removeVotesByFilledFormId(5);

        Assert.assertEquals(0L, ((HibernatePersistance) persistance).getVotesCount().longValue());
    }

    @Test
    public void testSetAllWinnerVotesToFalse() {
        Vote vote1 = new Vote();
        vote1.setFilledFormId(5);
        vote1.setUserId(3);
        vote1.setGalleryId(1);
        vote1.setWinner(true);
        vote1.setVoteDate(new Date());
        persistance.putVote(vote1);

        Vote vote2 = new Vote();
        vote2.setFilledFormId(5);
        vote2.setUserId(2);
        vote2.setGalleryId(2);
        vote2.setWinner(true);
        vote2.setVoteDate(new Date());
        persistance.putVote(vote2);

        Vote vote3 = new Vote();
        vote3.setFilledFormId(5);
        vote3.setUserId(2);
        vote3.setGalleryId(1);
        vote3.setWinner(true);
        vote3.setVoteDate(new Date());
        persistance.putVote(vote3);

        Vote vote4 = new Vote();
        vote4.setFilledFormId(5);
        vote4.setUserId(2);
        vote4.setGalleryId(1);
        vote4.setWinner(false);
        vote4.setVoteDate(new Date());
        persistance.putVote(vote4);

        persistance.setAllWinnerVotesToFalse(2, 1);

        HibernateManager.get().refresh(vote1);
        HibernateManager.get().refresh(vote2);
        HibernateManager.get().refresh(vote3);
        HibernateManager.get().refresh(vote4);

        Assert.assertTrue(persistance.getVoteById(vote1.getVoteId()).isWinner());
        Assert.assertTrue(persistance.getVoteById(vote2.getVoteId()).isWinner());
        Assert.assertFalse(persistance.getVoteById(vote3.getVoteId()).isWinner());
        Assert.assertFalse(persistance.getVoteById(vote4.getVoteId()).isWinner());
    }

    @Test
    public void getVoteByUserIdGalleryIdFilledFormIdAndStartEndDates() {
        Calendar calendarStartDate = new GregorianCalendar(2009, 10, 10);
        Calendar calendarEndDate = new GregorianCalendar(2009, 10, 20);

        Vote vote1 = new Vote();
        vote1.setUserId(2);
        vote1.setGalleryId(1);
        vote1.setFilledFormId(5);
        vote1.setVoteDate(new Date());
        vote1.setStartDate(calendarStartDate.getTime());
        vote1.setEndDate(calendarEndDate.getTime());
        persistance.putVote(vote1);

        Vote vote2 = new Vote();
        vote2.setUserId(2);
        vote2.setGalleryId(1);
        vote2.setFilledFormId(5);
        vote2.setVoteDate(new Date());
        vote2.setStartDate(new Date(calendarStartDate.getTimeInMillis() + 100000000L));
        vote2.setEndDate(calendarEndDate.getTime());
        persistance.putVote(vote2);

        Vote vote3 = new Vote();
        vote3.setUserId(3);
        vote3.setGalleryId(1);
        vote3.setFilledFormId(5);
        vote3.setVoteDate(new Date());
        persistance.putVote(vote3);

        Vote vote4 = new Vote();
        vote4.setUserId(2);
        vote4.setGalleryId(3);
        vote4.setFilledFormId(5);
        vote4.setVoteDate(new Date());
        vote4.setStartDate(calendarStartDate.getTime());
        vote4.setEndDate(calendarEndDate.getTime());
        persistance.putVote(vote4);

        Vote vote5 = new Vote();
        vote5.setUserId(2);
        vote5.setGalleryId(3);
        vote5.setFilledFormId(5);
        vote5.setVoteDate(new Date());
        persistance.putVote(vote5);

        List<Vote> votes = persistance.getVotesByStartEndDates(2, 1, calendarStartDate.getTime(), calendarEndDate.getTime(), 5);
        Assert.assertEquals(1, votes.size());
        Assert.assertEquals(vote1, votes.get(0));
    }


    @Test
    public void getVoteByUserIdGalleryIdFilledFormIdAndStartEndDatesByNullStartDate() {
        Date calendarStartDate = null;
        Calendar calendarEndDate = new GregorianCalendar(2009, 10, 20);

        Vote vote1 = new Vote();
        vote1.setUserId(2);
        vote1.setGalleryId(1);
        vote1.setFilledFormId(5);
        vote1.setVoteDate(new Date());
        vote1.setStartDate(calendarStartDate);
        vote1.setEndDate(calendarEndDate.getTime());
        persistance.putVote(vote1);

        Vote vote2 = new Vote();
        vote2.setUserId(2);
        vote2.setGalleryId(1);
        vote2.setFilledFormId(5);
        vote2.setVoteDate(new Date());
        vote2.setStartDate(calendarStartDate);
        vote2.setEndDate(new Date(calendarEndDate.getTimeInMillis() + 100000000L));
        persistance.putVote(vote2);

        Vote vote3 = new Vote();
        vote3.setUserId(3);
        vote3.setGalleryId(1);
        vote3.setFilledFormId(5);
        vote3.setVoteDate(new Date());
        persistance.putVote(vote3);

        Vote vote4 = new Vote();
        vote4.setUserId(2);
        vote4.setGalleryId(3);
        vote4.setFilledFormId(5);
        vote4.setVoteDate(new Date());
        vote4.setStartDate(calendarStartDate);
        vote4.setEndDate(calendarEndDate.getTime());
        persistance.putVote(vote4);

        Vote vote5 = new Vote();
        vote5.setUserId(2);
        vote5.setGalleryId(3);
        vote5.setFilledFormId(5);
        vote5.setVoteDate(new Date());
        persistance.putVote(vote5);

        List<Vote> votes = persistance.getVotesByStartEndDates(2, 1, calendarStartDate, calendarEndDate.getTime(), 5);
        Assert.assertEquals(1, votes.size());
        Assert.assertEquals(vote1, votes.get(0));
    }

    @Test
    public void getVotesByTimeInterval() {
        Vote vote1 = new Vote();
        vote1.setUserId(2);
        vote1.setGalleryId(1);
        vote1.setFilledFormId(5);
        vote1.setVoteDate((new GregorianCalendar(2009, 10, 10)).getTime());
        persistance.putVote(vote1);

        Vote vote2 = new Vote();
        vote2.setUserId(2);
        vote2.setGalleryId(1);
        vote2.setFilledFormId(5);
        vote2.setVoteDate((new GregorianCalendar(2009, 10, 21)).getTime());
        persistance.putVote(vote2);

        Vote vote3 = new Vote();
        vote3.setUserId(2);
        vote3.setGalleryId(1);
        vote3.setFilledFormId(5);
        vote3.setVoteDate((new GregorianCalendar(2009, 10, 15)).getTime());
        persistance.putVote(vote3);

        Vote vote4 = new Vote();
        vote4.setUserId(2);
        vote4.setGalleryId(3);
        vote4.setFilledFormId(5);
        vote4.setVoteDate((new GregorianCalendar(2009, 10, 30)).getTime());
        persistance.putVote(vote4);

        Vote vote5 = new Vote();
        vote5.setUserId(2);
        vote5.setGalleryId(3);
        vote5.setFilledFormId(5);
        vote5.setVoteDate((new GregorianCalendar(2002, 10, 15)).getTime());
        persistance.putVote(vote5);

        Calendar calendarStartDate = new GregorianCalendar(2009, 10, 10);
        Calendar calendarEndDate = new GregorianCalendar(2009, 10, 20);
        List<Vote> votes = persistance.getVotesByTimeInterval(2, 1, calendarStartDate.getTime(), calendarEndDate.getTime(), 5);
        Assert.assertEquals(2, votes.size());
        Assert.assertEquals(vote1, votes.get(0));
        Assert.assertEquals(vote3, votes.get(1));
    }


    @Test
    public void getVotesByTimeIntervalByNullEndDate() {
        Vote vote1 = new Vote();
        vote1.setUserId(2);
        vote1.setGalleryId(1);
        vote1.setFilledFormId(5);
        vote1.setVoteDate((new GregorianCalendar(2009, 10, 10)).getTime());
        persistance.putVote(vote1);

        Vote vote2 = new Vote();
        vote2.setUserId(2);
        vote2.setGalleryId(1);
        vote2.setFilledFormId(5);
        vote2.setVoteDate((new GregorianCalendar(2009, 10, 21)).getTime());
        persistance.putVote(vote2);

        Vote vote3 = new Vote();
        vote3.setUserId(2);
        vote3.setGalleryId(1);
        vote3.setFilledFormId(5);
        vote3.setVoteDate((new GregorianCalendar(2009, 10, 15)).getTime());
        persistance.putVote(vote3);

        Vote vote4 = new Vote();
        vote4.setUserId(2);
        vote4.setGalleryId(3);
        vote4.setFilledFormId(5);
        vote4.setVoteDate((new GregorianCalendar(2009, 10, 30)).getTime());
        persistance.putVote(vote4);

        Vote vote5 = new Vote();
        vote5.setUserId(2);
        vote5.setGalleryId(3);
        vote5.setFilledFormId(5);
        vote5.setVoteDate((new GregorianCalendar(2002, 10, 15)).getTime());
        persistance.putVote(vote5);


        Calendar calendarStartDate = new GregorianCalendar(2009, 10, 10);
        List<Vote> votes = persistance.getVotesByTimeInterval(2, 1, calendarStartDate.getTime(), null, 5);
        Assert.assertEquals(3, votes.size());
        Assert.assertEquals(vote1, votes.get(0));
        Assert.assertEquals(vote2, votes.get(1));
        Assert.assertEquals(vote3, votes.get(2));
    }

    @Test
    public void testPutGetFormVideo() {
        FormVideo formVideo = new FormVideo();
        formVideo.setImageId(1);
        formVideo.setVideoId(1);
        formVideo.setQuality(1);
        Assert.assertNull(persistance.getFormVideoById(formVideo.getFormVideoId()));
        persistance.putFormVideo(formVideo);

        FormVideo newFormVideo = persistance.getFormVideoById(formVideo.getFormVideoId());
        Assert.assertNotNull(newFormVideo);
        Assert.assertEquals(formVideo, newFormVideo);
    }

    @Test
    public void testGetFilledFormItemByFormItemId() {
        DraftCustomForm form = new DraftCustomForm();
        form.setName("name");
        form.setCreated(new Date());
        persistance.putCustomForm(form);

        DraftFormItem formItem = new DraftFormItem();
        formItem.setFormItemName(FormItemName.VIDEO_FILE_UPLOAD);
        formItem.setDraftForm(form);


        FilledForm filledForm = new FilledForm();
        filledForm.setFormDescription("gg3");
        filledForm.setFormId(form.getFormId());
        filledForm.setType(FormType.CUSTOM_FORM);
        persistance.putFilledForm(filledForm);

        FilledFormItem filledFormItem1 = new FilledFormItem();
        filledFormItem1.setFormItemName(FormItemName.NAME);
        filledFormItem1.getValues().add("g");
        filledFormItem1.setFormItemId(formItem.getFormItemId());
        filledForm.getFilledFormItems().add(filledFormItem1);
        filledFormItem1.setFilledForm(filledForm);
        persistance.putFilledFormItem(filledFormItem1);

        FilledFormItem filledFormItem2 = new FilledFormItem();
        filledFormItem2.setFormItemName(FormItemName.NAME);
        filledFormItem2.getValues().add("g");
        filledFormItem2.setFormItemId(formItem.getFormItemId());
        filledForm.getFilledFormItems().add(filledFormItem2);
        filledFormItem2.setFilledForm(filledForm);
        persistance.putFilledFormItem(filledFormItem2);

        FilledFormItem filledFormItem3 = new FilledFormItem();
        filledFormItem3.setFormItemName(FormItemName.NAME);
        filledFormItem3.getValues().add("g");
        filledFormItem3.setFormItemId(-1);
        filledForm.getFilledFormItems().add(filledFormItem3);
        filledFormItem3.setFilledForm(filledForm);
        persistance.putFilledFormItem(filledFormItem3);

        List<FilledFormItem> filledFormItems = persistance.getFilledFormItemByFormItemId(formItem.getFormItemId());
        Assert.assertEquals(2, filledFormItems.size());
        Assert.assertEquals(0, persistance.getFilledFormItemByFormItemId(null).size());
    }

    @Test
    public void testgetAllFormVideos() {
        for (int i = 0; i < 5; i++) {
            FormVideo formVideo = new FormVideo();
            persistance.putFormVideo(formVideo);
        }
        Assert.assertEquals(5, persistance.getAllFormVideos().size());
    }


    @Test
    public void testgetAllForms() {
        DraftContactUs contactUs = new DraftContactUs();
        contactUs.setShowDescription(true);
        contactUs.setEmail("aa");
        contactUs.setName("name");
        contactUs.setSiteId(1);
        persistance.putContactUs(contactUs);

        DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        childSiteRegistration.setDescription("comment");
        childSiteRegistration.setName("name");
        childSiteRegistration.setTermsAndConditions("");
        childSiteRegistration.setEmailText("");
        childSiteRegistration.setWelcomeText("");
        persistance.putItem(childSiteRegistration);

        DraftCustomForm customForm = new DraftCustomForm();
        customForm.setName("customForm");
        customForm.setSiteId(1);
        persistance.putCustomForm(customForm);

        DraftRegistrationForm registrationForm = new DraftRegistrationForm();
        registrationForm.setName("1");
        registrationForm.setSiteId(1);
        persistance.putRegistrationForm(registrationForm);

        Assert.assertEquals(4, persistance.getAllForms().size());
    }

    @Test
    public void testGetUserOnSiteRightsBySiteIdAndAccessLevels() {
        User user1WithAdminRight = new User();
        user1WithAdminRight.setPassword("1");
        user1WithAdminRight.setEmail("a1");
        user1WithAdminRight.setRegistrationDate(new Date());
        persistance.putUser(user1WithAdminRight);

        User user2WithAdminRight = new User();
        user2WithAdminRight.setPassword("222");
        user2WithAdminRight.setEmail("a222");
        user2WithAdminRight.setRegistrationDate(new Date());
        persistance.putUser(user2WithAdminRight);

        User user1WithVisitorRight = new User();
        user1WithVisitorRight.setPassword("2");
        user1WithVisitorRight.setEmail("a2");
        user1WithVisitorRight.setRegistrationDate(new Date());
        persistance.putUser(user1WithVisitorRight);

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

        UserOnSiteRight userRight1 = new UserOnSiteRight();
        userRight1.setActive(true);
        user1WithAdminRight.addUserOnSiteRight(userRight1);
        userRight1.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        site1.addUserOnSiteRight(userRight1);
        persistance.putUserOnSiteRight(userRight1);

        UserOnSiteRight userRight2 = new UserOnSiteRight();
        userRight2.setActive(true);
        user2WithAdminRight.addUserOnSiteRight(userRight2);
        userRight2.setSiteAccessType(SiteAccessLevel.EDITOR);
        site1.addUserOnSiteRight(userRight2);
        persistance.putUserOnSiteRight(userRight2);


        UserOnSiteRight visitorRight1 = new UserOnSiteRight();
        visitorRight1.setActive(true);
        user1WithVisitorRight.addUserOnSiteRight(visitorRight1);
        visitorRight1.setSiteAccessType(SiteAccessLevel.VISITOR);
        site1.addUserOnSiteRight(visitorRight1);
        persistance.putUserOnSiteRight(visitorRight1);


        List<UserOnSiteRight> userOnSiteRights = persistance.getUserOnSiteRights(site1.getSiteId(), SiteAccessLevel.getUserAccessLevels());
        Assert.assertEquals(2, userOnSiteRights.size());
        Assert.assertEquals(userRight1, userOnSiteRights.get(0));
        Assert.assertEquals(userRight2, userOnSiteRights.get(1));
    }

    @Test
    public void testGetUsersBySiteIdAndAccessLevels() {
        User user1WithAdminRight = new User();
        user1WithAdminRight.setPassword("1");
        user1WithAdminRight.setEmail("a1");
        user1WithAdminRight.setRegistrationDate(new Date());
        persistance.putUser(user1WithAdminRight);

        User user2WithAdminRight = new User();
        user2WithAdminRight.setPassword("222");
        user2WithAdminRight.setEmail("a222");
        user2WithAdminRight.setRegistrationDate(new Date());
        persistance.putUser(user2WithAdminRight);

        User user1WithVisitorRight = new User();
        user1WithVisitorRight.setPassword("2");
        user1WithVisitorRight.setEmail("a2");
        user1WithVisitorRight.setRegistrationDate(new Date());
        persistance.putUser(user1WithVisitorRight);

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

        UserOnSiteRight userRight1 = new UserOnSiteRight();
        userRight1.setActive(true);
        user1WithAdminRight.addUserOnSiteRight(userRight1);
        userRight1.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        site1.addUserOnSiteRight(userRight1);
        persistance.putUserOnSiteRight(userRight1);

        UserOnSiteRight userRight2 = new UserOnSiteRight();
        userRight2.setActive(true);
        user2WithAdminRight.addUserOnSiteRight(userRight2);
        userRight2.setSiteAccessType(SiteAccessLevel.EDITOR);
        site1.addUserOnSiteRight(userRight2);
        persistance.putUserOnSiteRight(userRight2);


        UserOnSiteRight visitorRight1 = new UserOnSiteRight();
        visitorRight1.setActive(true);
        user1WithVisitorRight.addUserOnSiteRight(visitorRight1);
        visitorRight1.setSiteAccessType(SiteAccessLevel.VISITOR);
        site1.addUserOnSiteRight(visitorRight1);
        persistance.putUserOnSiteRight(visitorRight1);


        List<User> users = persistance.getUsersWithActiveRights(site1.getSiteId(), SiteAccessLevel.getUserAccessLevels());
        Assert.assertEquals(2, users.size());
        Assert.assertEquals(user1WithAdminRight, users.get(0));
        Assert.assertEquals(user2WithAdminRight, users.get(1));
    }

    @Test
    public void testGetUsersWithAllRightsBySiteIdAndAccessLevels() {
        User user1WithAdminRight = new User();
        user1WithAdminRight.setPassword("1");
        user1WithAdminRight.setEmail("a1");
        user1WithAdminRight.setRegistrationDate(new Date());
        persistance.putUser(user1WithAdminRight);

        User user2WithAdminRight = new User();
        user2WithAdminRight.setPassword("222");
        user2WithAdminRight.setEmail("a222");
        user2WithAdminRight.setRegistrationDate(new Date());
        persistance.putUser(user2WithAdminRight);

        User user1WithVisitorRight = new User();
        user1WithVisitorRight.setPassword("2");
        user1WithVisitorRight.setEmail("a2");
        user1WithVisitorRight.setRegistrationDate(new Date());
        persistance.putUser(user1WithVisitorRight);

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

        UserOnSiteRight userRight1 = new UserOnSiteRight();
        userRight1.setActive(false);
        user1WithAdminRight.addUserOnSiteRight(userRight1);
        userRight1.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        site1.addUserOnSiteRight(userRight1);
        persistance.putUserOnSiteRight(userRight1);

        UserOnSiteRight userRight2 = new UserOnSiteRight();
        userRight2.setActive(true);
        user2WithAdminRight.addUserOnSiteRight(userRight2);
        userRight2.setSiteAccessType(SiteAccessLevel.EDITOR);
        site1.addUserOnSiteRight(userRight2);
        persistance.putUserOnSiteRight(userRight2);


        UserOnSiteRight visitorRight1 = new UserOnSiteRight();
        visitorRight1.setActive(true);
        user1WithVisitorRight.addUserOnSiteRight(visitorRight1);
        visitorRight1.setSiteAccessType(SiteAccessLevel.VISITOR);
        site1.addUserOnSiteRight(visitorRight1);
        persistance.putUserOnSiteRight(visitorRight1);


        List<User> users = persistance.getUsersWithRightsToSite(site1.getSiteId(), SiteAccessLevel.getUserAccessLevels());
        Assert.assertEquals(2, users.size());
        Assert.assertEquals(user1WithAdminRight, users.get(0));
        Assert.assertEquals(user2WithAdminRight, users.get(1));
    }

    @Test
    public void testPutGetCoordinate() throws Exception {
        Coordinate coordinate = new Coordinate(Country.UA, 10.10, -20.20, "01000");
        persistance.putCoordinate(coordinate);
        Coordinate newCoordinate = persistance.getCoordinate("01000", Country.UA);
        Assert.assertNotNull(newCoordinate);
        Assert.assertEquals("01000", newCoordinate.getZip());
        Assert.assertEquals(Country.UA, newCoordinate.getCountry());
        Assert.assertEquals(10.10, newCoordinate.getLatitude(), 2);
        Assert.assertEquals(-20.20, newCoordinate.getLongitude(), 2);
    }

    @Test
    public void testPutGetCoordinate_withoutCountry() throws Exception {
        persistance.putCoordinate(new Coordinate(null, 10.10, -20.20, "01000"));
        persistance.putCoordinate(new Coordinate(null, 10.10, -20.20, "01000"));
        persistance.putCoordinate(new Coordinate(null, 10.10, -20.20, "01000"));

        Assert.assertNull(persistance.getCoordinate("01000", null));
    }

    @Test
    public void testPutGetCoordinate_getByWrongCountry() throws Exception {
        Coordinate coordinate = new Coordinate(Country.UA, 10.10, -20.20, "01000");
        persistance.putCoordinate(coordinate);
        Coordinate newCoordinate = persistance.getCoordinate("01000", Country.US);
        Assert.assertNull(newCoordinate);
    }

    @Test
    public void testGetGalleryVideoRanges() {
        final GalleryVideoRange videoRange1 = new GalleryVideoRange();
        videoRange1.setFilledFormId(1);
        videoRange1.setFinish(10);
        videoRange1.setStart(1);
        videoRange1.setGalleryId(2);
        videoRange1.setTotal(12);
        persistance.putGalleryVideoRange(videoRange1);


        final GalleryVideoRange videoRange2 = new GalleryVideoRange();
        videoRange2.setFilledFormId(11);
        videoRange2.setFinish(101);
        videoRange2.setStart(11);
        videoRange2.setGalleryId(21);
        videoRange2.setTotal(121);
        persistance.putGalleryVideoRange(videoRange2);


        final List<GalleryVideoRange> galleryVideoRanges = persistance.getGalleryVideoRanges(Arrays.asList(videoRange1.getRangeId(), videoRange2.getRangeId(), 125));
        Assert.assertEquals(2, galleryVideoRanges.size());
        Assert.assertTrue(galleryVideoRanges.contains(videoRange1));
        Assert.assertTrue(galleryVideoRanges.contains(videoRange2));
    }

    @Test
    public void testgetSitesWithNotEmptyIncomeSettings() {
        IncomeSettings incomeSettings1 = new IncomeSettings();
        incomeSettings1.setPaypalAddress("fff");
        incomeSettings1.setSum(1);
        persistance.putIncomeSettings(incomeSettings1);

        Site siteWithNotEmptyIncomeSettings1 = new Site();
        siteWithNotEmptyIncomeSettings1.getSitePaymentSettings().setUserId(-1);
        siteWithNotEmptyIncomeSettings1.setIncomeSettings(incomeSettings1);
        siteWithNotEmptyIncomeSettings1.setTitle("title1");
        siteWithNotEmptyIncomeSettings1.setSubDomain("1");
        siteWithNotEmptyIncomeSettings1.setCreationDate(new Date());
        ThemeId id1 = new ThemeId();
        id1.setTemplateDirectory("");
        id1.setThemeCss("");
        siteWithNotEmptyIncomeSettings1.setThemeId(id1);
        persistance.putSite(siteWithNotEmptyIncomeSettings1);

        IncomeSettings incomeSettings2 = new IncomeSettings();
        incomeSettings2.setPaypalAddress("gg");
        incomeSettings2.setSum(21);
        persistance.putIncomeSettings(incomeSettings2);

        Site siteWithNotEmptyIncomeSettings2 = new Site();
        siteWithNotEmptyIncomeSettings2.getSitePaymentSettings().setUserId(-1);
        siteWithNotEmptyIncomeSettings2.setIncomeSettings(incomeSettings2);
        siteWithNotEmptyIncomeSettings2.setTitle("title2");
        siteWithNotEmptyIncomeSettings2.setSubDomain("2");
        siteWithNotEmptyIncomeSettings2.setCreationDate(new Date());
        ThemeId id2 = new ThemeId();
        id2.setTemplateDirectory("");
        id2.setThemeCss("");
        siteWithNotEmptyIncomeSettings2.setThemeId(id2);
        persistance.putSite(siteWithNotEmptyIncomeSettings2);

        IncomeSettings incomeSettings3 = new IncomeSettings();
        incomeSettings3.setPaypalAddress("444");
        incomeSettings3.setSum(0);
        persistance.putIncomeSettings(incomeSettings3);

        Site siteWithEmptyIncomeSettings = new Site();
        siteWithEmptyIncomeSettings.getSitePaymentSettings().setUserId(-1);
        siteWithEmptyIncomeSettings.setIncomeSettings(incomeSettings3);
        siteWithEmptyIncomeSettings.setTitle("title3");
        siteWithEmptyIncomeSettings.setSubDomain("3");
        siteWithEmptyIncomeSettings.setCreationDate(new Date());
        ThemeId id3 = new ThemeId();
        id3.setTemplateDirectory("");
        id3.setThemeCss("");
        siteWithEmptyIncomeSettings.setThemeId(id3);
        persistance.putSite(siteWithEmptyIncomeSettings);


        Site siteWithoutIncomeSettings = new Site();
        siteWithoutIncomeSettings.getSitePaymentSettings().setUserId(-1);
        siteWithoutIncomeSettings.setIncomeSettings(incomeSettings3);
        siteWithoutIncomeSettings.setTitle("title4");
        siteWithoutIncomeSettings.setSubDomain("4");
        siteWithoutIncomeSettings.setCreationDate(new Date());
        ThemeId id4 = new ThemeId();
        id4.setTemplateDirectory("");
        id4.setThemeCss("");
        siteWithoutIncomeSettings.setThemeId(id4);
        persistance.putSite(siteWithoutIncomeSettings);

        final List<Site> sites = persistance.getSitesWithNotEmptyIncomeSettings();
        Assert.assertEquals(2, sites.size());
        Assert.assertTrue(sites.contains(siteWithNotEmptyIncomeSettings1));
        Assert.assertTrue(sites.contains(siteWithNotEmptyIncomeSettings2));
    }

    @Test
    public void testGetPaymentSettingsOwner() throws Exception {
        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("title1");
        site.setSubDomain("1");
        site.setCreationDate(new Date());
        ThemeId id1 = new ThemeId();
        id1.setTemplateDirectory("");
        id1.setThemeCss("");
        site.setThemeId(id1);
        persistance.putSite(site);

        Site site2 = new Site();
        site2.getSitePaymentSettings().setUserId(-1);
        site2.setTitle("title2");
        site2.setSubDomain("2");
        site2.setCreationDate(new Date());
        ThemeId id2 = new ThemeId();
        id2.setTemplateDirectory("");
        id2.setThemeCss("");
        site2.setThemeId(id2);
        persistance.putSite(site2);

        ChildSiteSettings settings = new ChildSiteSettings();
        settings.getSitePaymentSettings().setUserId(-1);
        settings.setWelcomeText("");
        settings.setCanBePublishedMessageSent(true);
        settings.setSiteStatus(SiteStatus.SUSPENDED);
        settings.setCreatedDate(new Date());
        settings.setUserId(-1);
        persistance.putChildSiteSettings(settings);

        ChildSiteSettings settings2 = new ChildSiteSettings();
        settings2.getSitePaymentSettings().setUserId(-1);
        settings2.setWelcomeText("");
        settings2.setCanBePublishedMessageSent(true);
        settings2.setSiteStatus(SiteStatus.SUSPENDED);
        settings2.setCreatedDate(new Date());
        settings2.setUserId(-1);
        persistance.putChildSiteSettings(settings2);

        PaymentSettingsOwner paymentSettingsOwner;
        paymentSettingsOwner = persistance.getPaymentSettingsOwner(site.getSiteId(), PaymentSettingsOwnerType.SITE);
        Assert.assertEquals(site, paymentSettingsOwner);

        paymentSettingsOwner = persistance.getPaymentSettingsOwner(site2.getSiteId(), PaymentSettingsOwnerType.SITE);
        Assert.assertEquals(site2, paymentSettingsOwner);

        paymentSettingsOwner = persistance.getPaymentSettingsOwner(settings.getChildSiteSettingsId(), PaymentSettingsOwnerType.CHILD_SITE_SETTINGS);
        Assert.assertEquals(settings, paymentSettingsOwner);

        paymentSettingsOwner = persistance.getPaymentSettingsOwner(settings2.getChildSiteSettingsId(), PaymentSettingsOwnerType.CHILD_SITE_SETTINGS);
        Assert.assertEquals(settings2, paymentSettingsOwner);

        Assert.assertNull(persistance.getPaymentSettingsOwner(null, PaymentSettingsOwnerType.CHILD_SITE_SETTINGS));
        Assert.assertNull(persistance.getPaymentSettingsOwner(settings.getChildSiteSettingsId(), null));
        Assert.assertNull(persistance.getPaymentSettingsOwner(null, null));
    }

    @Test
    public void testPutGetMenuItem() {
        final DraftMenu menu = new DraftMenu();
        persistance.putMenu(menu);


        MenuItem menuItem1 = new DraftMenuItem(1, true, menu);
        persistance.putMenuItem(menuItem1);
        menuItem1.setParent(null);

        MenuItem menuItem2 = new DraftMenuItem(2, true, menu);
        persistance.putMenuItem(menuItem2);
        menuItem2.setParent(null);

        HibernateManager.get().flush();

        Assert.assertEquals(2, menu.getMenuItems().size());
        Assert.assertEquals(menuItem1, persistance.getDraftMenuItem(menu.getMenuItems().get(0).getId()));
        Assert.assertEquals(menuItem2, persistance.getDraftMenuItem(menu.getMenuItems().get(1).getId()));


        MenuItem menuItem3 = new DraftMenuItem(3, true, menu);
        persistance.putMenuItem(menuItem3);
        menuItem3.setParent(null);

        MenuItem menuItem4 = new DraftMenuItem(4, true, menu);
        persistance.putMenuItem(menuItem4);
        menuItem4.setParent(null);

        menu.removeChild(menuItem1);
        menu.removeChild(menuItem2);
        menuItem3.setParent(null);
        menuItem4.setParent(null);

        HibernateManager.get().flush();
        HibernateManager.get().refresh(menu);
        HibernateManager.get().refresh(menuItem1);
        HibernateManager.get().refresh(menuItem2);

        Assert.assertEquals(2, menu.getMenuItems().size());
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(menuItem3.getId());
        ids.add(menuItem4.getId());
        Assert.assertTrue(ids.contains(persistance.getDraftMenuItem(menu.getMenuItems().get(0).getId()).getId()));
        Assert.assertTrue(ids.contains(persistance.getDraftMenuItem(menu.getMenuItems().get(1).getId()).getId()));

        Assert.assertNotNull("We have to manualy remove unused items from database", persistance.getDraftMenuItem(menuItem1.getId()));
        Assert.assertNotNull("We have to manualy remove unused items from database", persistance.getDraftMenuItem(menuItem2.getId()));
    }


    @Test
    public void testMenuItemsStructure() {
        DraftMenu menu = new DraftMenu();
        persistance.putMenu(menu);
        final MenuItem parent = new DraftMenuItem(1, true, menu);
        persistance.putMenuItem(parent);
        final MenuItem child = new DraftMenuItem(2, true, menu);
        persistance.putMenuItem(child);

        child.setParent(parent);

        HibernateManager.get().flush();
        HibernateManager.get().refresh(parent);
        HibernateManager.get().refresh(child);

        Assert.assertEquals(parent, child.getParent());
        Assert.assertEquals(1, parent.getChildren().size());
        Assert.assertEquals(child, parent.getChildren().get(0));

        // Adding new parent.
        final MenuItem newParent = new DraftMenuItem();
        persistance.putMenuItem(newParent);
        child.setParent(newParent);

        HibernateManager.get().flush();
        HibernateManager.get().refresh(parent);
        HibernateManager.get().refresh(newParent);
        HibernateManager.get().refresh(child);

        Assert.assertEquals("Child has new parent.", newParent, child.getParent());
        Assert.assertEquals("New parent has one child.", 1, newParent.getChildren().size());
        Assert.assertEquals("Child from newParent children = child", child, newParent.getChildren().get(0));
        Assert.assertEquals("Old parent has no children now.", 0, parent.getChildren().size());
    }

    @Test
    public void testSetParent_bigStructure() {
        final DraftMenu menu = new DraftMenu();
        persistance.putMenu(menu);

        final MenuItem element1 = new DraftMenuItem(1, true, menu);
        final MenuItem element2 = new DraftMenuItem(2, true, menu);
        final MenuItem element3 = new DraftMenuItem(3, true, menu);
        final MenuItem element4 = new DraftMenuItem(4, true, menu);
        final MenuItem element5 = new DraftMenuItem(5, true, menu);
        final MenuItem element6 = new DraftMenuItem(6, true, menu);
        final MenuItem element7 = new DraftMenuItem(7, true, menu);
        final MenuItem element8 = new DraftMenuItem(8, true, menu);

        persistance.putMenuItem(element1);
        persistance.putMenuItem(element2);
        persistance.putMenuItem(element3);
        persistance.putMenuItem(element4);
        persistance.putMenuItem(element5);
        persistance.putMenuItem(element6);
        persistance.putMenuItem(element7);
        persistance.putMenuItem(element8);

        element1.setParent(null);
        element2.setParent(element1);

        element3.setParent(element2);
        element7.setParent(element2);
        element8.setParent(element2);

        element4.setParent(element3);

        element5.setParent(element4);
        element6.setParent(element4);

        HibernateManager.get().flush();
        HibernateManager.get().refresh(menu);
        HibernateManager.get().refresh(element1);
        HibernateManager.get().refresh(element2);
        HibernateManager.get().refresh(element3);
        HibernateManager.get().refresh(element4);
        HibernateManager.get().refresh(element5);
        HibernateManager.get().refresh(element6);
        HibernateManager.get().refresh(element7);
        HibernateManager.get().refresh(element8);

        /*-----------------------------------------Checking initial structure-----------------------------------------*/
        Assert.assertEquals(1, menu.getMenuItems().size());
        Assert.assertEquals(true, menu.getMenuItems().contains(element1));

        Assert.assertEquals(null, element1.getParent());

        Assert.assertEquals(element1, element2.getParent());

        Assert.assertEquals(element2, element3.getParent());
        Assert.assertEquals(element2, element7.getParent());
        Assert.assertEquals(element2, element8.getParent());

        Assert.assertEquals(element3, element4.getParent());

        Assert.assertEquals(element4, element5.getParent());
        Assert.assertEquals(element4, element6.getParent());


        Assert.assertEquals(1, element1.getChildren().size());
        Assert.assertEquals(true, element1.getChildren().contains(element2));

        Assert.assertEquals(3, element2.getChildren().size());
        Assert.assertEquals(true, element2.getChildren().contains(element3));
        Assert.assertEquals(true, element2.getChildren().contains(element7));
        Assert.assertEquals(true, element2.getChildren().contains(element8));

        Assert.assertEquals(1, element3.getChildren().size());
        Assert.assertEquals(true, element3.getChildren().contains(element4));

        Assert.assertEquals(2, element4.getChildren().size());
        Assert.assertEquals(true, element4.getChildren().contains(element5));
        Assert.assertEquals(true, element4.getChildren().contains(element6));

        Assert.assertEquals(0, element5.getChildren().size());
        Assert.assertEquals(0, element6.getChildren().size());
        Assert.assertEquals(0, element7.getChildren().size());
        Assert.assertEquals(0, element8.getChildren().size());
        /*-----------------------------------------Checking initial structure-----------------------------------------*/

        //Changing tree structure
        element4.setParent(element1);

        HibernateManager.get().flush();
        HibernateManager.get().refresh(menu);
        HibernateManager.get().refresh(element1);
        HibernateManager.get().refresh(element2);
        HibernateManager.get().refresh(element3);
        HibernateManager.get().refresh(element4);
        HibernateManager.get().refresh(element5);
        HibernateManager.get().refresh(element6);
        HibernateManager.get().refresh(element7);
        HibernateManager.get().refresh(element8);

        /*-------------------------------------------Checking new structure-------------------------------------------*/
        Assert.assertEquals(1, menu.getMenuItems().size());
        Assert.assertEquals(true, menu.getMenuItems().contains(element1));

        Assert.assertEquals(null, element1.getParent());

        Assert.assertEquals(element1, element2.getParent());
        Assert.assertEquals(element1, element4.getParent());

        Assert.assertEquals(element2, element3.getParent());
        Assert.assertEquals(element2, element7.getParent());
        Assert.assertEquals(element2, element8.getParent());

        Assert.assertEquals(element4, element5.getParent());
        Assert.assertEquals(element4, element6.getParent());


        Assert.assertEquals(2, element1.getChildren().size());
        Assert.assertEquals(true, element1.getChildren().contains(element2));
        Assert.assertEquals(true, element1.getChildren().contains(element4));

        Assert.assertEquals(3, element2.getChildren().size());
        Assert.assertEquals(true, element2.getChildren().contains(element3));
        Assert.assertEquals(true, element2.getChildren().contains(element7));
        Assert.assertEquals(true, element2.getChildren().contains(element8));

        Assert.assertEquals(0, element3.getChildren().size());

        Assert.assertEquals(2, element4.getChildren().size());
        Assert.assertEquals(true, element4.getChildren().contains(element5));
        Assert.assertEquals(true, element4.getChildren().contains(element6));

        Assert.assertEquals(0, element5.getChildren().size());
        Assert.assertEquals(0, element6.getChildren().size());
        Assert.assertEquals(0, element7.getChildren().size());
        Assert.assertEquals(0, element8.getChildren().size());
        /*-------------------------------------------Checking new structure-------------------------------------------*/
    }

    @Test
    public void testRemoveItem() {
        final DraftMenu menu = new DraftMenu();
        persistance.putMenu(menu);

        final MenuItem element1 = new DraftMenuItem(1, true, menu);
        final MenuItem element2 = new DraftMenuItem(2, true, menu);
        final MenuItem element3 = new DraftMenuItem(3, true, menu);
        final MenuItem element4 = new DraftMenuItem(4, true, menu);
        final MenuItem element5 = new DraftMenuItem(5, true, menu);
        final MenuItem element6 = new DraftMenuItem(6, true, menu);
        final MenuItem element7 = new DraftMenuItem(7, true, menu);
        final MenuItem element8 = new DraftMenuItem(8, true, menu);

        persistance.putMenuItem(element1);
        persistance.putMenuItem(element2);
        persistance.putMenuItem(element3);
        persistance.putMenuItem(element4);
        persistance.putMenuItem(element5);
        persistance.putMenuItem(element6);
        persistance.putMenuItem(element7);
        persistance.putMenuItem(element8);

        element1.setParent(null);
        element2.setParent(element1);

        element3.setParent(element2);
        element7.setParent(element2);
        element8.setParent(element2);

        element4.setParent(element3);

        element5.setParent(element4);
        element6.setParent(element4);

        HibernateManager.get().flush();
        HibernateManager.get().refresh(menu);
        HibernateManager.get().refresh(element1);
        HibernateManager.get().refresh(element2);
        HibernateManager.get().refresh(element3);
        HibernateManager.get().refresh(element4);
        HibernateManager.get().refresh(element5);
        HibernateManager.get().refresh(element6);
        HibernateManager.get().refresh(element7);
        HibernateManager.get().refresh(element8);


        persistance.removeMenuItem(element3);

        HibernateManager.get().flush();
        HibernateManager.get().refresh(menu);
        HibernateManager.get().refresh(element1);
        HibernateManager.get().refresh(element2);

        Assert.assertNotNull(persistance.getDraftMenuItem(element1.getId()));
        Assert.assertNotNull(persistance.getDraftMenuItem(element2.getId()));
        Assert.assertNotNull(persistance.getDraftMenuItem(element7.getId()));
        Assert.assertNotNull(persistance.getDraftMenuItem(element8.getId()));

        MenuItem removedItem = persistance.getDraftMenuItem(element3.getId());
        Assert.assertNull(removedItem);
        removedItem = persistance.getDraftMenuItem(element3.getId());
        Assert.assertNull(removedItem);
        removedItem = persistance.getDraftMenuItem(element4.getId());
        Assert.assertNull(removedItem);
        removedItem = persistance.getDraftMenuItem(element5.getId());
        Assert.assertNull(removedItem);
        removedItem = persistance.getDraftMenuItem(element6.getId());
        Assert.assertNull(removedItem);

        Assert.assertEquals(2, element2.getChildren().size());
        Assert.assertEquals(true, element2.getChildren().contains(element7));
        Assert.assertEquals(true, element2.getChildren().contains(element8));

        Assert.assertEquals(0, element7.getChildren().size());
        Assert.assertEquals(0, element8.getChildren().size());

        Assert.assertEquals(1, element1.getChildren().size());
        Assert.assertEquals(true, element1.getChildren().contains(element2));
    }

    @Test
    public void testRemoveItem_rootElement() {
        final DraftMenu menu = new DraftMenu();
        persistance.putMenu(menu);

        final MenuItem element1 = new DraftMenuItem(1, true, menu);
        final MenuItem element2 = new DraftMenuItem(2, true, menu);
        final MenuItem element3 = new DraftMenuItem(3, true, menu);
        final MenuItem element4 = new DraftMenuItem(4, true, menu);
        final MenuItem element5 = new DraftMenuItem(5, true, menu);
        final MenuItem element6 = new DraftMenuItem(6, true, menu);
        final MenuItem element7 = new DraftMenuItem(7, true, menu);
        final MenuItem element8 = new DraftMenuItem(8, true, menu);

        persistance.putMenuItem(element1);
        persistance.putMenuItem(element2);
        persistance.putMenuItem(element3);
        persistance.putMenuItem(element4);
        persistance.putMenuItem(element5);
        persistance.putMenuItem(element6);
        persistance.putMenuItem(element7);
        persistance.putMenuItem(element8);

        element1.setParent(null);
        element2.setParent(element1);

        element3.setParent(element2);
        element7.setParent(element2);
        element8.setParent(element2);

        element4.setParent(element3);

        element5.setParent(element4);
        element6.setParent(element4);

        HibernateManager.get().flush();
        HibernateManager.get().refresh(menu);
        HibernateManager.get().refresh(element1);
        HibernateManager.get().refresh(element2);
        HibernateManager.get().refresh(element3);
        HibernateManager.get().refresh(element4);
        HibernateManager.get().refresh(element5);
        HibernateManager.get().refresh(element6);
        HibernateManager.get().refresh(element7);
        HibernateManager.get().refresh(element8);


        persistance.removeMenuItem(element1);

        HibernateManager.get().flush();
        HibernateManager.get().refresh(menu);

        Assert.assertNull(persistance.getDraftMenuItem(element1.getId()));
        Assert.assertNull(persistance.getDraftMenuItem(element2.getId()));
        Assert.assertNull(persistance.getDraftMenuItem(element3.getId()));
        Assert.assertNull(persistance.getDraftMenuItem(element4.getId()));
        Assert.assertNull(persistance.getDraftMenuItem(element5.getId()));
        Assert.assertNull(persistance.getDraftMenuItem(element6.getId()));
        Assert.assertNull(persistance.getDraftMenuItem(element7.getId()));
        Assert.assertNull(persistance.getDraftMenuItem(element8.getId()));

        Assert.assertEquals(0, menu.getMenuItems().size());
    }

    @Test
    public void testRemoveMenu() {
        final DraftMenu menu = new DraftMenu();
        persistance.putMenu(menu);

        final MenuItem element1 = new DraftMenuItem(1, true, menu);
        final MenuItem element2 = new DraftMenuItem(2, true, menu);
        final MenuItem element3 = new DraftMenuItem(3, true, menu);
        final MenuItem element4 = new DraftMenuItem(4, true, menu);
        final MenuItem element5 = new DraftMenuItem(5, true, menu);
        final MenuItem element6 = new DraftMenuItem(6, true, menu);
        final MenuItem element7 = new DraftMenuItem(7, true, menu);
        final MenuItem element8 = new DraftMenuItem(8, true, menu);

        persistance.putMenuItem(element1);
        persistance.putMenuItem(element2);
        persistance.putMenuItem(element3);
        persistance.putMenuItem(element4);
        persistance.putMenuItem(element5);
        persistance.putMenuItem(element6);
        persistance.putMenuItem(element7);
        persistance.putMenuItem(element8);

        element1.setParent(null);
        element2.setParent(element1);

        element3.setParent(element2);
        element7.setParent(element2);
        element8.setParent(element2);

        element4.setParent(element3);

        element5.setParent(element4);
        element6.setParent(element4);

        HibernateManager.get().flush();
        HibernateManager.get().refresh(menu);
        HibernateManager.get().refresh(element1);
        HibernateManager.get().refresh(element2);
        HibernateManager.get().refresh(element3);
        HibernateManager.get().refresh(element4);
        HibernateManager.get().refresh(element5);
        HibernateManager.get().refresh(element6);
        HibernateManager.get().refresh(element7);
        HibernateManager.get().refresh(element8);

        persistance.removeDraftItem(menu);

        HibernateManager.get().flush();

        Assert.assertNull(persistance.getMenuById(menu.getId()));
        Assert.assertNull(persistance.getDraftMenuItem(element1.getId()));
        Assert.assertNull(persistance.getDraftMenuItem(element2.getId()));
        Assert.assertNull(persistance.getDraftMenuItem(element3.getId()));
        Assert.assertNull(persistance.getDraftMenuItem(element4.getId()));
        Assert.assertNull(persistance.getDraftMenuItem(element5.getId()));
        Assert.assertNull(persistance.getDraftMenuItem(element6.getId()));
        Assert.assertNull(persistance.getDraftMenuItem(element7.getId()));
        Assert.assertNull(persistance.getDraftMenuItem(element8.getId()));
    }

    @Test
    public void testRemoveSiteItem() {
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

        final DraftCustomForm customForm = new DraftCustomForm();
        customForm.setSiteId(site1.getSiteId());
        customForm.setName("forumName");
        persistance.putItem(customForm);

        final WidgetItem widget = TestUtil.createWidgetItemWithPageAndPageVersion(site1);
        widget.setDraftItem(customForm);
        HibernateManager.get().flush();

        persistance.removeDraftItem(customForm);
        HibernateManager.get().flush();

        Assert.assertNull(persistance.getCustomFormById(customForm.getId()));
        Assert.assertNull(persistance.getWidget(widget.getWidgetId()));
    }

    @Test
    public void testGetMenuItems() {
        final DraftMenu menu = new DraftMenu();
        persistance.putMenu(menu);

        final MenuItem element1 = new DraftMenuItem(1, true, menu);
        final MenuItem element2 = new DraftMenuItem(2, true, menu);
        final MenuItem element3 = new DraftMenuItem(3, true, menu);
        final MenuItem element4 = new DraftMenuItem(4, true, menu);
        final MenuItem element5 = new DraftMenuItem(1, true, menu);
        final MenuItem element6 = new DraftMenuItem(6, true, menu);
        final MenuItem element7 = new DraftMenuItem(7, true, menu);
        final MenuItem element8 = new DraftMenuItem(1, true, menu);

        persistance.putMenuItem(element1);
        persistance.putMenuItem(element2);
        persistance.putMenuItem(element3);
        persistance.putMenuItem(element4);
        persistance.putMenuItem(element5);
        persistance.putMenuItem(element6);
        persistance.putMenuItem(element7);
        persistance.putMenuItem(element8);

        element1.setParent(null);
        element2.setParent(element1);

        element3.setParent(element2);
        element7.setParent(element2);
        element8.setParent(element2);

        element4.setParent(element3);

        element5.setParent(element4);
        element6.setParent(element4);

        HibernateManager.get().flush();
        HibernateManager.get().refresh(menu);
        HibernateManager.get().refresh(element1);
        HibernateManager.get().refresh(element2);
        HibernateManager.get().refresh(element3);
        HibernateManager.get().refresh(element4);
        HibernateManager.get().refresh(element5);
        HibernateManager.get().refresh(element6);
        HibernateManager.get().refresh(element7);
        HibernateManager.get().refresh(element8);


        final List<DraftMenuItem> menuItems = persistance.getMenuItems(1);


        Assert.assertEquals(3, menuItems.size());
        Assert.assertEquals(true, menuItems.contains(element1));
        Assert.assertEquals(true, menuItems.contains(element5));
        Assert.assertEquals(true, menuItems.contains(element8));
    }

    @Test
    public void testSetMenuItemsIncludeInMenu() {
        final MenuItem menuItem1 = TestUtil.createMenuItem(1, null, true);
        final MenuItem menuItem2 = TestUtil.createMenuItem(2, null, true);
        final MenuItem menuItem3 = TestUtil.createMenuItem(3, null, true);
        final MenuItem menuItem4 = TestUtil.createMenuItem(4, null, true);
        final MenuItem menuItem5 = TestUtil.createMenuItem(5, null, true);


        persistance.setMenuItemsIncludeInMenu(Arrays.asList(menuItem1.getId(), menuItem2.getId(), menuItem3.getId()), false);


        HibernateManager.get().flush();
        HibernateManager.get().refresh(menuItem1);
        HibernateManager.get().refresh(menuItem2);
        HibernateManager.get().refresh(menuItem3);
        HibernateManager.get().refresh(menuItem4);
        HibernateManager.get().refresh(menuItem5);

        Assert.assertFalse(menuItem1.isIncludeInMenu());
        Assert.assertFalse(menuItem2.isIncludeInMenu());
        Assert.assertFalse(menuItem3.isIncludeInMenu());
        Assert.assertTrue(menuItem4.isIncludeInMenu());
        Assert.assertTrue(menuItem5.isIncludeInMenu());

        persistance.setMenuItemsIncludeInMenu(Arrays.asList(menuItem1.getId(), menuItem2.getId()), true);
        persistance.setMenuItemsIncludeInMenu(Arrays.asList(menuItem4.getId(), menuItem5.getId()), false);

        HibernateManager.get().flush();
        HibernateManager.get().refresh(menuItem1);
        HibernateManager.get().refresh(menuItem2);
        HibernateManager.get().refresh(menuItem3);
        HibernateManager.get().refresh(menuItem4);
        HibernateManager.get().refresh(menuItem5);

        Assert.assertTrue(menuItem1.isIncludeInMenu());
        Assert.assertTrue(menuItem2.isIncludeInMenu());
        Assert.assertFalse(menuItem3.isIncludeInMenu());
        Assert.assertFalse(menuItem4.isIncludeInMenu());
        Assert.assertFalse(menuItem5.isIncludeInMenu());
    }

    @Test
    public void testPutGetMenuImage() {
        final MenuImage menuImage = new MenuImage();
        menuImage.setName("image");
        menuImage.setExtension("jpeg");
        menuImage.setSiteId(123);

        persistance.putMenuImage(menuImage);
        HibernateManager.get().flush();

        final MenuImage newMenuImage = persistance.getMenuImageById(menuImage.getMenuImageId());

        Assert.assertNotNull(newMenuImage);
        Assert.assertEquals("image", newMenuImage.getName());
        Assert.assertEquals("jpeg", newMenuImage.getExtension());
        Assert.assertEquals(123, newMenuImage.getSiteId());
    }

    @Test
    public void testPutGetGroup() {
        final Site site = TestUtil.createSite();
        Assert.assertEquals(0, site.getOwnGroups().size());

        final Group group = TestUtil.createGroup("name", site);
        final Group group2 = TestUtil.createGroup("name2", site);

        DraftRegistrationForm registrationForm = new DraftRegistrationForm();
        registrationForm.setName("name1");
        registrationForm.setSiteId(site.getSiteId());
        persistance.putRegistrationForm(registrationForm);

        final GroupsTime groupsTime1 = new GroupsTime(group.getGroupId(), null);
        final GroupsTime groupsTime2 = new GroupsTime(group2.getGroupId(), null);

        final List<GroupsTime> groupsTimes = new ArrayList<GroupsTime>() {{
            add(groupsTime1);
            add(groupsTime2);
        }};
        registrationForm.setGroupsWithTime(GroupsTimeManager.valueOf(groupsTimes));

        HibernateManager.get().flush();

        HibernateManager.get().refresh(site);
        HibernateManager.get().refresh(group);
        HibernateManager.get().refresh(registrationForm);

        Assert.assertEquals(2, site.getOwnGroups().size());
        Assert.assertEquals(group, site.getOwnGroups().get(0));
        Assert.assertEquals(group2, site.getOwnGroups().get(1));
        Assert.assertEquals(2, GroupsTimeManager.valueOf(registrationForm.getGroupsWithTime()).size());
        for (GroupsTime groupsTime : GroupsTimeManager.valueOf(registrationForm.getGroupsWithTime())) {
            Assert.assertEquals(true, (groupsTime.getGroupId() == group.getGroupId() || groupsTime.getGroupId() == group2.getGroupId()));
        }
        Assert.assertEquals("name", persistance.getGroup(group.getGroupId()).getName());
        HibernateManager.get().flush();

        persistance.removeGroup(group);

        HibernateManager.get().flush();
        HibernateManager.get().refresh(site);
        HibernateManager.get().refresh(registrationForm);

        Assert.assertEquals(1, site.getOwnGroups().size());
        Assert.assertEquals(1, GroupsTimeManager.valueOf(registrationForm.getGroupsWithTime()).size());
        Assert.assertEquals(group2.getGroupId(), GroupsTimeManager.valueOf(registrationForm.getGroupsWithTime()).get(0).getGroupId());
    }

    @Test
    public void testPutGetGroup_withUserWithAccesToGroup() {
        final Site site = TestUtil.createSite();
        Assert.assertEquals(0, site.getOwnGroups().size());

        final Group group = TestUtil.createGroup("name", site);

        persistance.putGroup(group);

        final User user = TestUtil.createUser();
        Assert.assertEquals(0, new UsersGroupManager(user).getAccessibleGroupsId().size());

        HibernateManager.get().flush();

        final UsersGroup usersGroup = new UsersGroup();
        usersGroup.setExpirationDate(null);
        usersGroup.setId(user, group);
        persistance.putUsersGroup(usersGroup);


        HibernateManager.get().flush();
        HibernateManager.get().refresh(site);
        HibernateManager.get().refresh(user);
        HibernateManager.get().refresh(group);

        Assert.assertEquals(1, site.getOwnGroups().size());
        Assert.assertEquals(1, new UsersGroupManager(user).getAccessibleGroupsId().size());
        Assert.assertEquals(group, site.getOwnGroups().get(0));

        Assert.assertEquals("name", persistance.getGroup(group.getGroupId()).getName());


        persistance.removeGroup(group);

        HibernateManager.get().flush();
        HibernateManager.get().refresh(site);
        HibernateManager.get().refresh(user);

        Assert.assertEquals(0, site.getOwnGroups().size());
        Assert.assertEquals(0, new UsersGroupManager(user).getAccessibleGroupsId().size());
    }

    @Test
    public void testGetUsersWithAccessToGroup() {
        final Site site = TestUtil.createSite();
        Assert.assertEquals(0, site.getOwnGroups().size());

        final Group group = TestUtil.createGroup("name", site);

        persistance.putGroup(group);

        final User userWithAcces = TestUtil.createUser("email1");
        TestUtil.createUser("email2");
        TestUtil.createUser("email3");
        TestUtil.createUser("email4");
        Assert.assertEquals(0, new UsersGroupManager(userWithAcces).getAccessibleGroupsId().size());

        HibernateManager.get().flush();

        final UsersGroup usersGroup = new UsersGroup();
        usersGroup.setExpirationDate(null);
        usersGroup.setId(userWithAcces, group);
        persistance.putUsersGroup(usersGroup);


        HibernateManager.get().flush();
        HibernateManager.get().refresh(site);
        HibernateManager.get().refresh(userWithAcces);
        HibernateManager.get().refresh(group);


        final List<User> users = ((HibernatePersistance) persistance).getUsersWithAccessToGroup(group.getGroupId());
        Assert.assertEquals(1, users.size());
        Assert.assertEquals(userWithAcces, users.get(0));
        Assert.assertEquals(1, persistance.getUsersCountWithAccessToGroup(group.getGroupId()));
    }

    @Test
    public void testGetGroups() {
        final Site site = TestUtil.createSite();
        Assert.assertEquals(0, site.getOwnGroups().size());

        final Group group1 = TestUtil.createGroup("name1", site);
        final Group group2 = TestUtil.createGroup("name2", site);
        final Group group3 = TestUtil.createGroup("name3", site);

        final List<Group> groups = persistance.getGroups(Arrays.asList(group1.getGroupId(), group2.getGroupId()));
        Assert.assertEquals(2, groups.size());
        Assert.assertEquals(true, groups.contains(group1));
        Assert.assertEquals(true, groups.contains(group2));
        Assert.assertEquals(false, groups.contains(group3));
    }

    @Test
    public void testGetUsersByUsersId() {
        final User user1 = TestUtil.createUser("userEmail1");
        final User user2 = TestUtil.createUser("userEmail2");
        final User user3 = TestUtil.createUser("userEmail3");


        final List<User> users = persistance.getUsersByUsersId(Arrays.asList(user1.getUserId(), user2.getUserId()));
        Assert.assertEquals(2, users.size());
        Assert.assertEquals(true, users.contains(user1));
        Assert.assertEquals(true, users.contains(user2));
        Assert.assertEquals(false, users.contains(user3));
    }

    @Test
    public void testGetSiteOnItemsBySite() {
        final Site site = TestUtil.createSite("title", "url");
        final DraftItem item1 = TestUtil.createDraftImage(site);
        final DraftItem item2 = TestUtil.createDraftImage(site);
        final DraftItem item3 = TestUtil.createDraftImage(site);
        SiteOnItem siteOnItem1 = TestUtil.createSiteOnItemRight(site, item1);
        SiteOnItem siteOnItem2 = TestUtil.createSiteOnItemRight(site, item2);
        SiteOnItem siteOnItem3 = TestUtil.createSiteOnItemRight(site, item3);


        final Site site2 = TestUtil.createSite("title2", "url2");
        final DraftItem item12 = TestUtil.createDraftImage(site2);
        final DraftItem item22 = TestUtil.createDraftImage(site2);
        final DraftItem item32 = TestUtil.createDraftImage(site2);
        SiteOnItem siteOnItem12 = TestUtil.createSiteOnItemRight(site2, item12);
        SiteOnItem siteOnItem22 = TestUtil.createSiteOnItemRight(site2, item22);
        SiteOnItem siteOnItem32 = TestUtil.createSiteOnItemRight(site2, item32);

        final List<SiteOnItem> siteOnItems = persistance.getSiteOnItemsBySite(site.getSiteId());
        Assert.assertEquals(3, siteOnItems.size());

        Assert.assertEquals(true, siteOnItems.contains(siteOnItem1));
        Assert.assertEquals(true, siteOnItems.contains(siteOnItem2));
        Assert.assertEquals(true, siteOnItems.contains(siteOnItem3));
        Assert.assertEquals(false, siteOnItems.contains(siteOnItem12));
        Assert.assertEquals(false, siteOnItems.contains(siteOnItem22));
        Assert.assertEquals(false, siteOnItems.contains(siteOnItem32));

        final List<DraftItem> items = new ArrayList<DraftItem>();
        for (SiteOnItem siteOnItem : siteOnItems) {
            items.add(siteOnItem.getId().getItem());
        }
        Assert.assertEquals(true, items.contains(item1));
        Assert.assertEquals(true, items.contains(item2));
        Assert.assertEquals(true, items.contains(item3));
        Assert.assertEquals(false, items.contains(item12));
        Assert.assertEquals(false, items.contains(item22));
        Assert.assertEquals(false, items.contains(item32));
    }

    /*@Test
    public void testALotOfWidgetItems() {
        // I`ll create here 100 page versions with 100 widgets in each and then I`ll try to get all this page versions.
        final Site site = TestUtil.createSite();

        for (int i = 0; i < 100; i++) {
            final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
            for (int j = 0; j < 100; j++) {
                final WidgetItem widgetItem = TestUtil.createWidgetItem();
                widgetItem.setPageVersion(pageVersion);
                pageVersion.addWidget(widgetItem);
                pageVersion.setType(PageVersionType.WORK);
                persistance.putWidget(widgetItem);
            }
        }
        HibernateManager.get().flush();
        final long startTime = System.currentTimeMillis();
        final List<Widget> widgets = persistance.getWidgetsOnLastPageVersions(site.getSiteId());
        System.out.println("Total time = " + (System.currentTimeMillis() - startTime));
        Assert.assertEquals(100, widgets.size());
    } */

    /* @Test
    public void testALotOfWidgetItems_withItems() {
        // I`ll create here 100 page versions with 100 widgets in each and then I`ll try to get all this page versions.
        final Site site = TestUtil.createSite();

        for (int i = 0; i < 100; i++) {
            final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
            for (int j = 0; j < 100; j++) {
                final WidgetItem widgetItem = TestUtil.createWidgetItem();
                widgetItem.setPageVersion(pageVersion);
                pageVersion.addWidget(widgetItem);
                pageVersion.setType(PageVersionType.WORK);
                persistance.putWidget(widgetItem);

                final DraftItem item = TestUtil.createDraftImage(site);
                widgetItem.setDraftItem(item);
            }
        }
        HibernateManager.get().flush();
        final long startTime = System.currentTimeMillis();
        final List<WidgetItem> widgets = persistance.getWidgetsOnLastPageVersions(site.getSiteId());// Total time = 4762, 3978
        final long totalTime = (System.currentTimeMillis() - startTime);
        System.out.println("Total time get = " + totalTime);
        Assert.assertEquals(10000, widgets.size());

        final long startTimeView = System.currentTimeMillis();
        for (WidgetItem widget : widgets) {
            Assert.assertNotNull(widget.getDraftItem());
        }
        final long totalTimeView = (System.currentTimeMillis() - startTimeView);
        System.out.println("Total time view = " + totalTimeView);
        System.out.println("Total time = " + (totalTimeView + totalTime));
    }*/

    @Test
    public void testFewWidgettsWithSameItem() {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final WidgetItem widgetItem1 = TestUtil.createWidgetItem(pageVersion);
        final WidgetItem widgetItem2 = TestUtil.createWidgetItem(pageVersion);
        final DraftItem item = TestUtil.createBlog(site, "Blog");
        widgetItem1.setDraftItem(item);
        widgetItem2.setDraftItem(item);


        HibernateManager.get().flush();


        Assert.assertEquals(item, widgetItem1.getDraftItem());
        Assert.assertEquals(item, widgetItem2.getDraftItem());


        widgetItem2.setDraftItem(null);
        HibernateManager.get().flush();


        Assert.assertEquals(item, widgetItem1.getDraftItem());
        Assert.assertEquals(null, widgetItem2.getDraftItem());
    }

    @Test
    public void testGetUserOnSiteRightByUserAndSiteId() {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUser();
        final UserOnSiteRight userOnSiteRight = TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final UserOnSiteRight newUserOnSiteRight = persistance.getUserOnSiteRightByUserAndSiteId(user.getUserId(), site.getSiteId());
        Assert.assertNotNull(newUserOnSiteRight);
        Assert.assertEquals(userOnSiteRight, newUserOnSiteRight);
    }

    @Test
    public void testGetUserOnSiteRightByUserAndSiteId_withoutRights() {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUser();
        final UserOnSiteRight newUserOnSiteRight = persistance.getUserOnSiteRightByUserAndSiteId(user.getUserId(), site.getSiteId());
        Assert.assertNull(newUserOnSiteRight);
    }

    @Test
    public void testGetAccessibleElement() {
        final Site site = TestUtil.createSite("", "");
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final Widget widget = TestUtil.createForumWidget();

        HibernateManager.get().flush();

        Assert.assertEquals(site, persistance.getAccessibleElement(site.getSiteId(), AccessibleElementType.SITE));
        Assert.assertEquals(pageVersion.getPage(), ((PageManager) persistance.getAccessibleElement(pageVersion.getPageId(), AccessibleElementType.PAGE)).getPage());
        Assert.assertEquals(widget, ((WidgetManager) persistance.getAccessibleElement(widget.getWidgetId(), AccessibleElementType.WIDGET)).getWidget());
    }

    @Test
    public void testPutGetPurchaseMailLog() throws Exception {
        final PurchaseMailLog log = new PurchaseMailLog();
        log.setEmailText("text");
        log.setErrorsWhileSendingMail(true);
        log.setPurchaseComplete(true);
        log.setUserNotFound(true);
        log.setNewExpirationDate(new Date());

        persistance.putPurchaseMailLog(log);

        HibernateManager.get().flush();
        HibernateManager.get().refresh(log);

        final PurchaseMailLog foundedLog = persistance.getPurchaseMailLog(log.getId());
        Assert.assertNotNull(foundedLog);
        Assert.assertEquals(log, foundedLog);
    }

    @Test
    public void testPutRemoveWorkMenu() throws Exception {
        final WorkMenu menu1 = new WorkMenu();
        final MenuItem menuItem1 = new WorkMenuItem(1, true, menu1);
        menuItem1.setParent(null);
        persistance.putMenu(menu1);
        persistance.putMenuItem(menuItem1);

        final DraftMenu menu2 = new DraftMenu();
        final MenuItem menuItem2 = new DraftMenuItem(1, true, menu2);
        menuItem2.setParent(null);
        persistance.putMenu(menu2);
        persistance.putMenuItem(menuItem2);
        HibernateManager.get().flush();

        Assert.assertEquals(menu1.getId(), menu2.getId());

        persistance.removeWorkItem(menu1);
        HibernateManager.get().flush();

        Assert.assertEquals(null, persistance.getWorkItem(menu2.getId()));
        Assert.assertEquals(null, persistance.getWorkMenuItem(menu2.getMenuItems().get(0).getId()));

        Assert.assertEquals(menu2, persistance.getDraftItem(menu2.getId()));
        Assert.assertEquals(menuItem2, persistance.getDraftMenuItem(menu2.getMenuItems().get(0).getId()));


        persistance.removeDraftItem(menu2);
        HibernateManager.get().flush();
        Assert.assertEquals(null, persistance.getDraftItem(menu2.getId()));
        Assert.assertEquals(0, menu2.getMenuItems().size());
    }

    @Test
    public void putGetUsersGroup() {
        final User user = TestUtil.createUser();

        final Site site = TestUtil.createSite();
        final Group group = TestUtil.createGroup("name", site);
        Assert.assertEquals(0, new UsersGroupManager(user).getAccessibleGroupsId().size());
        Assert.assertEquals(false, new UsersGroupManager(user).hasAccessToGroup(group.getGroupId()));

        final UsersGroup usersGroup = new UsersGroup();
        usersGroup.setExpirationDate(null);
        usersGroup.setId(user, group);
        user.addAccessToGroup(usersGroup);
        persistance.putUsersGroup(usersGroup);

        HibernateManager.get().flush();

        final UsersGroup usersGroup2 = persistance.getUsersGroup(new UsersGroupId(user, group));
        Assert.assertNotNull(usersGroup2);
        Assert.assertEquals(null, usersGroup2.getExpirationDate());
        Assert.assertEquals(group.getGroupId(), usersGroup2.getGroupId());
        Assert.assertEquals(user.getUserId(), usersGroup2.getUserId());
        Assert.assertEquals(new UsersGroupId(user, group), usersGroup2.getId());

        final UsersGroupManager usersGroupManager = new UsersGroupManager(user);

        Assert.assertEquals(1, usersGroupManager.getAccessibleGroupsId().size());
        Assert.assertEquals(true, usersGroupManager.hasAccessToGroup(group.getGroupId()));


        usersGroupManager.removeAccessToGroup(group.getGroupId());
        HibernateManager.get().flush();
        HibernateManager.get().refresh(user);

        Assert.assertEquals(0, usersGroupManager.getAccessibleGroupsId().size());
        Assert.assertEquals(false, usersGroupManager.hasAccessToGroup(group.getGroupId()));
        Assert.assertEquals(false, usersGroupManager.hasAccessToGroup(group.getGroupId()));
        Assert.assertEquals(null, persistance.getUsersGroup(new UsersGroupId(user, group)));
    }

    @Test
    public void testPutPageSettings_draft() throws Exception {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);

        final DraftPageSettings draftPageSettings = new DraftPageSettings();
        draftPageSettings.setPage(page);
        draftPageSettings.setCreationDate(new Date());
        persistance.putPageSettings(draftPageSettings);

        page.setPageSettings(draftPageSettings);
        persistance.putPage(page);

        HibernateManager.get().flush();

        Assert.assertNotNull(persistance.getDraftPageSettings(draftPageSettings.getPageSettingsId()));
        Assert.assertNull(new PageManager(page).getWorkPageSettings());

        final WorkPageSettings workPageSettings = new WorkPageSettings();
        workPageSettings.setPage(page);
        workPageSettings.setPageSettingsId(draftPageSettings.getPageSettingsId());
        workPageSettings.setCreationDate(new Date());
        persistance.putPageSettings(workPageSettings);

        HibernateManager.get().flush();

        Assert.assertNotNull(persistance.getDraftPageSettings(draftPageSettings.getPageSettingsId()));
        Assert.assertNotNull(new PageManager(page).getWorkPageSettings());
    }

    @Test
    public void testRemovePage() throws Exception {
        final Site site = new Site();
        site.getSitePaymentSettings().setUserId(1);
        site.setThemeId(new ThemeId("", ""));
        site.setTitle("title");
        ServiceLocator.getPersistance().putSite(site);

        final Page page = new Page();
        site.addPage(page);

        final DraftPageSettings draftPageSettings = new DraftPageSettings();
        persistance.putPageSettings(draftPageSettings);
        page.setPageSettings(draftPageSettings);
        persistance.putPage(page);
        draftPageSettings.setPage(page);

        HibernateManager.get().flush();

        Assert.assertNotNull(persistance.getDraftPageSettings(draftPageSettings.getPageSettingsId()));
        Assert.assertNull(new PageManager(page).getWorkPageSettings());

        final WorkPageSettings workPageSettings = new WorkPageSettings();
        workPageSettings.setPage(page);
        workPageSettings.setPageSettingsId(draftPageSettings.getPageSettingsId());
        persistance.putPageSettings(workPageSettings);


        HibernateManager.get().flush();
        Assert.assertNotNull(persistance.getDraftPageSettings(draftPageSettings.getPageSettingsId()));
        Assert.assertNotNull(new PageManager(page).getWorkPageSettings());
        HibernateManager.get().flush();

        persistance.removePage(page);
        HibernateManager.get().flush();

        Assert.assertNull(persistance.getPage(page.getPageId()));
        Assert.assertNull(persistance.getDraftPageSettings(draftPageSettings.getPageSettingsId()));
        Assert.assertNull(persistance.getWorkPageSettings(draftPageSettings.getPageSettingsId()));
    }

    @Test
    public void testRemovePage_withoutWorkPageSettings() throws Exception {
        final Site site = new Site();
        site.getSitePaymentSettings().setUserId(1);
        site.setThemeId(new ThemeId("", ""));
        site.setTitle("title");
        ServiceLocator.getPersistance().putSite(site);

        final Page page = new Page();
        site.addPage(page);

        final DraftPageSettings draftPageSettings = new DraftPageSettings();
        persistance.putPageSettings(draftPageSettings);
        page.setPageSettings(draftPageSettings);
        persistance.putPage(page);
        draftPageSettings.setPage(page);

        HibernateManager.get().flush();

        Assert.assertNotNull(persistance.getDraftPageSettings(draftPageSettings.getPageSettingsId()));
        Assert.assertNull(new PageManager(page).getWorkPageSettings());
        HibernateManager.get().flush();

        persistance.removePage(page);
        HibernateManager.get().flush();

        Assert.assertNull(persistance.getPage(page.getPageId()));
        Assert.assertNull(persistance.getDraftPageSettings(draftPageSettings.getPageSettingsId()));
        Assert.assertNull(persistance.getWorkPageSettings(draftPageSettings.getPageSettingsId()));
    }

    @Test
    public void testGetWidgets() throws Exception {
        final Widget widget1 = TestUtil.createForumWidget();
        final Widget widget2 = TestUtil.createForumWidget();
        final Widget widget3 = TestUtil.createForumWidget();
        final Widget widget4 = TestUtil.createForumWidget();

        final List<Widget> widgets = persistance.getWidgets(Arrays.asList(widget1.getWidgetId(), widget3.getWidgetId(), widget4.getWidgetId()));
        Assert.assertEquals(3, widgets.size());
        Assert.assertEquals(true, widgets.contains(widget1));
        Assert.assertEquals(false, widgets.contains(widget2));
        Assert.assertEquals(true, widgets.contains(widget3));
        Assert.assertEquals(true, widgets.contains(widget4));
    }

    @Test
    public void testRemovePageSettings() throws Exception {
        final Page page = TestUtil.createPageAndSite();
        DraftPageSettings draftPageSettings = page.getPageSettings();

        Widget widget = new WidgetItem();
        persistance.putWidget(widget);
        draftPageSettings.addWidget(widget);
        HibernateManager.get().flush();


        persistance.removePageSettings(draftPageSettings);
        HibernateManager.get().flush();


        Assert.assertNull(persistance.getDraftPageSettings(draftPageSettings.getPageSettingsId()));
        Assert.assertNull(persistance.getWidget(widget.getWidgetId()));
    }

    @Test
    public void testPutGetRemoveIcon() throws Exception {
        final Icon icon = new Icon();
        icon.setName("favicon");
        icon.setExtension(".ico");
        icon.setWidth(16);
        icon.setHeight(16);

        persistance.putIcon(icon);
        HibernateManager.get().flush();

        Assert.assertNotNull(persistance.getIcon(icon.getIconId()));
        Assert.assertEquals(icon, persistance.getIcon(icon.getIconId()));


        persistance.removeIcon(icon);
        HibernateManager.get().flush();


        Assert.assertNull(persistance.getIcon(icon.getIconId()));
    }

    @Test
    public void testPutGetRemoveIcon_withSite() throws Exception {
        final Site site = TestUtil.createSite();

        final Icon icon = new Icon();
        icon.setName("favicon");
        icon.setExtension(".ico");
        icon.setWidth(16);
        icon.setHeight(16);

        persistance.putIcon(icon);
        site.setIcon(icon);
        HibernateManager.get().flush();

        Assert.assertNotNull(persistance.getIcon(icon.getIconId()));
        Assert.assertEquals(icon, persistance.getIcon(icon.getIconId()));


        persistance.removeIcon(icon);
        HibernateManager.get().flush();
        HibernateManager.get().refresh(site);


        Assert.assertNull(persistance.getIcon(icon.getIconId()));
        Assert.assertNull(site.getIcon());
    }

    @Test
    public void testGetPaymentLogsByUsersId() throws Exception {
        final PaymentLog paymentLog1 = TestUtil.createPaymentLog(11);
        final PaymentLog paymentLog2 = TestUtil.createPaymentLog(12);
        final PaymentLog paymentLog3 = TestUtil.createPaymentLog(11);
        final List<PaymentLog> paymentLogs = persistance.getPaymentLogsByUsersId(11);
        Assert.assertEquals(2, paymentLogs.size());
        Assert.assertEquals(true, paymentLogs.contains(paymentLog1));
        Assert.assertEquals(false, paymentLogs.contains(paymentLog2));
        Assert.assertEquals(true, paymentLogs.contains(paymentLog3));
    }

    @Test
    public void testPutGetCustomizeDataExport() throws Exception {
        final CSVDataExport csvDataExport = new CSVDataExport();

        final CSVDataExportField CSVDataExportField1 = new CSVDataExportField();
        final CSVDataExportField CSVDataExportField2 = new CSVDataExportField();
        final CSVDataExportField CSVDataExportField3 = new CSVDataExportField();
        csvDataExport.addField(CSVDataExportField1);
        csvDataExport.addField(CSVDataExportField2);
        csvDataExport.addField(CSVDataExportField3);


        persistance.putCSVDataExport(csvDataExport);
        HibernateManager.get().flush();


        final CSVDataExport newCSVDataExport = persistance.getCSVDataExport(csvDataExport.getId());
        Assert.assertEquals(csvDataExport, newCSVDataExport);
        Assert.assertEquals(3, newCSVDataExport.getFields().size());
    }

    @Test
    public void testPutGetRemoveCustomizeDataExportField() throws Exception {
        final CSVDataExport csvDataExport = new CSVDataExport();
        persistance.putCSVDataExport(csvDataExport);

        final CSVDataExportField CSVDataExportField1 = new CSVDataExportField();
        final CSVDataExportField CSVDataExportField2 = new CSVDataExportField();
        final CSVDataExportField CSVDataExportField3 = new CSVDataExportField();

        CSVDataExportField1.setCustomizeDataExport(csvDataExport);
        CSVDataExportField2.setCustomizeDataExport(csvDataExport);
        CSVDataExportField3.setCustomizeDataExport(csvDataExport);


        persistance.putCSVDataExportField(CSVDataExportField1);
        persistance.putCSVDataExportField(CSVDataExportField2);
        persistance.putCSVDataExportField(CSVDataExportField3);
        HibernateManager.get().flush();


        Assert.assertEquals(CSVDataExportField1, persistance.getCSVDataExportField(CSVDataExportField1.getId()));
        Assert.assertEquals(CSVDataExportField2, persistance.getCSVDataExportField(CSVDataExportField2.getId()));
        Assert.assertEquals(CSVDataExportField3, persistance.getCSVDataExportField(CSVDataExportField3.getId()));


        persistance.removeCSVDataExportField(CSVDataExportField1);
        persistance.removeCSVDataExportField(CSVDataExportField2);
        persistance.removeCSVDataExportField(CSVDataExportField3);
        HibernateManager.get().flush();


        Assert.assertNull(persistance.getCSVDataExportField(CSVDataExportField1.getId()));
        Assert.assertNull(persistance.getCSVDataExportField(CSVDataExportField2.getId()));
        Assert.assertNull(persistance.getCSVDataExportField(CSVDataExportField3.getId()));
    }

    @Test
    public void testPutGetCustomizeManageRecords() throws Exception {
        final CustomizeManageRecords customizeManageRecords = new CustomizeManageRecords();
        customizeManageRecords.setFormId(2);
        customizeManageRecords.setUserId(3);

        final CustomizeManageRecordsField customizeManageRecordsField1 = new CustomizeManageRecordsField();
        final CustomizeManageRecordsField customizeManageRecordsField2 = new CustomizeManageRecordsField();
        final CustomizeManageRecordsField customizeManageRecordsField3 = new CustomizeManageRecordsField();
        customizeManageRecords.addField(customizeManageRecordsField1);
        customizeManageRecords.addField(customizeManageRecordsField2);
        customizeManageRecords.addField(customizeManageRecordsField3);


        persistance.putCustomizeManageRecords(customizeManageRecords);
        HibernateManager.get().flush();


        final CustomizeManageRecords newCustomizeManageRecords = persistance.getCustomizeManageRecords(customizeManageRecords.getId());
        Assert.assertEquals(customizeManageRecords, newCustomizeManageRecords);
        Assert.assertEquals(3, newCustomizeManageRecords.getFields().size());
    }

    @Test
    public void testPutGetCustomizeManageRecords_getByUserIdAndFormId() throws Exception {
        final CustomizeManageRecords customizeManageRecords = new CustomizeManageRecords();
        customizeManageRecords.setFormId(2);
        customizeManageRecords.setUserId(3);

        final CustomizeManageRecords customizeManageRecords2 = new CustomizeManageRecords();
        customizeManageRecords2.setFormId(22);
        customizeManageRecords2.setUserId(33);


        persistance.putCustomizeManageRecords(customizeManageRecords);
        persistance.putCustomizeManageRecords(customizeManageRecords2);
        HibernateManager.get().flush();


        final CustomizeManageRecords newCustomizeManageRecords = persistance.getCustomizeManageRecords(22, 33);
        Assert.assertEquals(customizeManageRecords2, newCustomizeManageRecords);
    }

    @Test
    public void testPutGetRemoveCustomizeManageRecordsField() throws Exception {
        final CustomizeManageRecords customizeManageRecords = new CustomizeManageRecords();
        persistance.putCustomizeManageRecords(customizeManageRecords);

        final CustomizeManageRecordsField customizeManageRecordsField1 = new CustomizeManageRecordsField();
        final CustomizeManageRecordsField customizeManageRecordsField2 = new CustomizeManageRecordsField();
        final CustomizeManageRecordsField customizeManageRecordsField3 = new CustomizeManageRecordsField();

        customizeManageRecordsField1.setCustomizeManageRecords(customizeManageRecords);
        customizeManageRecordsField2.setCustomizeManageRecords(customizeManageRecords);
        customizeManageRecordsField3.setCustomizeManageRecords(customizeManageRecords);


        persistance.putCustomizeManageRecordsField(customizeManageRecordsField1);
        persistance.putCustomizeManageRecordsField(customizeManageRecordsField2);
        persistance.putCustomizeManageRecordsField(customizeManageRecordsField3);
        HibernateManager.get().flush();


        Assert.assertEquals(customizeManageRecordsField1, persistance.getCustomizeManageRecordsField(customizeManageRecordsField1.getId()));
        Assert.assertEquals(customizeManageRecordsField2, persistance.getCustomizeManageRecordsField(customizeManageRecordsField2.getId()));
        Assert.assertEquals(customizeManageRecordsField3, persistance.getCustomizeManageRecordsField(customizeManageRecordsField3.getId()));


        persistance.removeCustomizeManageRecordsField(customizeManageRecordsField1);
        persistance.removeCustomizeManageRecordsField(customizeManageRecordsField2);
        persistance.removeCustomizeManageRecordsField(customizeManageRecordsField3);
        HibernateManager.get().flush();


        Assert.assertNull(persistance.getCustomizeManageRecordsField(customizeManageRecordsField1.getId()));
        Assert.assertNull(persistance.getCustomizeManageRecordsField(customizeManageRecordsField2.getId()));
        Assert.assertNull(persistance.getCustomizeManageRecordsField(customizeManageRecordsField3.getId()));
    }

    @Test
    public void testPutGetTaxRates() throws Exception {
        final DraftTaxRateUS taxRate = new DraftTaxRateUS(States_US.AL);
        Assert.assertEquals(States_US.AL, taxRate.getState());
        Assert.assertEquals(4.0, taxRate.getTaxRate());

        final DraftTaxRatesUS taxRates = new DraftTaxRatesUS();
        final TaxRatesUSManager manager = new TaxRatesUSManager(taxRates);
        taxRates.addTaxRate(taxRate);


        ServiceLocator.getPersistance().putItem(taxRates);
        HibernateManager.get().flush();


        DraftTaxRatesUS savedTaxRate = (DraftTaxRatesUS) ServiceLocator.getPersistance().getDraftItem(taxRates.getId());
        Assert.assertEquals(1, savedTaxRate.getTaxRates().size());
        Assert.assertEquals(States_US.AL, savedTaxRate.getTaxRates().get(0).getState());
        Assert.assertEquals(4.0, savedTaxRate.getTaxRates().get(0).getTaxRate());


        // Adding one more tax rate
        final DraftTaxRateUS taxRate2 = new DraftTaxRateUS(States_US.VA);
        taxRates.addTaxRate(taxRate2);
        ServiceLocator.getPersistance().putTaxRate(taxRate2);
        HibernateManager.get().flush();

        Assert.assertEquals(States_US.VA, taxRate2.getState());
        Assert.assertEquals(5.0, taxRate2.getTaxRate());


        savedTaxRate = (DraftTaxRatesUS) ServiceLocator.getPersistance().getDraftItem(taxRates.getId());
        Assert.assertEquals(2, savedTaxRate.getTaxRates().size());
        // First tax rate
        Assert.assertEquals(States_US.AL, savedTaxRate.getTaxRates().get(0).getState());
        Assert.assertEquals(4.0, savedTaxRate.getTaxRates().get(0).getTaxRate());

        // Second tax rate
        Assert.assertEquals(States_US.VA, savedTaxRate.getTaxRates().get(1).getState());
        Assert.assertEquals(5.0, savedTaxRate.getTaxRates().get(1).getTaxRate());
    }

    @Test
    public void testPutGetItemSize() throws Exception {
        final ItemSize itemSize = new ItemSize();
        persistance.putItemSize(itemSize);
        HibernateManager.get().flush();

        Assert.assertEquals(itemSize, persistance.getItemSize(itemSize.getId()));
    }


    @Test
    public void testPutGetFontsAndColors() throws Exception {
        final FontsAndColors fontsAndColors = new FontsAndColors();
        persistance.putFontsAndColors(fontsAndColors);
        HibernateManager.get().flush();

        Assert.assertEquals(fontsAndColors, persistance.getFontsAndColors(fontsAndColors.getId()));

        final FontsAndColorsValue value = new FontsAndColorsValue();
        value.setDescription("description");
        value.setName("name");
        value.setSelector("selector");
        value.setValue("value");
        fontsAndColors.addCssValue(value);
        persistance.putFontsAndColorsValue(value);
        HibernateManager.get().flush();

        Assert.assertEquals(fontsAndColors, persistance.getFontsAndColors(fontsAndColors.getId()));
        Assert.assertEquals(1, persistance.getFontsAndColors(fontsAndColors.getId()).getCssValues().size());
        Assert.assertEquals(value, persistance.getFontsAndColors(fontsAndColors.getId()).getCssValues().get(0));


        persistance.removeFontsAndColors(fontsAndColors);
        HibernateManager.get().flush();

        Assert.assertEquals(null, persistance.getFontsAndColors(fontsAndColors.getId()));
    }

    @Test
    public void testGetLastUpdatedDate() throws Exception {
        final Site site = TestUtil.createSite();
        final PageManager pageManager1 = TestUtil.createPageVersion(TestUtil.createPage(site));
        final PageManager pageManager2 = TestUtil.createPageVersion(TestUtil.createPage(site));
        final PageManager pageManager3 = TestUtil.createPageVersion(TestUtil.createPage(site));

        pageManager1.setChanged(true);
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
        pageManager2.setChanged(true);
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
        pageManager3.setChanged(true);
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }

        Assert.assertEquals(pageManager3.getUpdated(), persistance.getLastUpdatedDate(site.getSiteId()));
    }

    @Test
    public void testGetLastUpdatedDate_withoutPages() throws Exception {
        final Site site = TestUtil.createSite();
        Assert.assertEquals(site.getCreationDate().getTime() / 1000, persistance.getLastUpdatedDate(site.getSiteId()).getTime() / 1000);
    }

    @Test
    public void getMenusByUserId() {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight);

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

        final DraftMenu defaultMenu = new DraftMenu();
        defaultMenu.setName("Default menu");
        defaultMenu.setDefaultSiteMenu(true);
        site.setMenu(defaultMenu);
        defaultMenu.setSiteId(site.getSiteId());
        persistance.putMenu(defaultMenu);

        site.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        final DraftMenu menu1 = new DraftMenu();
        menu1.setName("menu1");
        menu1.setSiteId(site.getSiteId());
        persistance.putMenu(menu1);

        final DraftMenu menu2 = new DraftMenu();
        menu2.setName("menu2");
        menu2.setSiteId(site.getSiteId());
        persistance.putMenu(menu2);

        List<DraftItem> menus = persistance.getDraftItemsByUserId(user.getUserId(), ItemType.MENU);
        Assert.assertEquals(2, menus.size());
    }

    @Test
    public void testGetDraftItemsBySiteId() throws Exception {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight);

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

        final DraftMenu defaultMenu = new DraftMenu();
        defaultMenu.setName("Default menu");
        defaultMenu.setDefaultSiteMenu(true);
        site.setMenu(defaultMenu);
        defaultMenu.setSiteId(site.getSiteId());
        persistance.putMenu(defaultMenu);

        site.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        final DraftMenu menu1 = new DraftMenu();
        menu1.setName("menu1");
        menu1.setSiteId(site.getSiteId());
        persistance.putMenu(menu1);

        final DraftMenu menu2 = new DraftMenu();
        menu2.setName("menu2");
        menu2.setSiteId(site.getSiteId());
        persistance.putMenu(menu2);

        List<DraftItem> menus = persistance.getDraftItemsBySiteId(site.getSiteId(), ItemType.MENU);
        Assert.assertEquals(2, menus.size());
    }

    @Test
    public void testGetDraftItemsBySiteId_withoutExcludedItems() throws Exception {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight);

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

        final DraftMenu defaultMenu = new DraftMenu();
        defaultMenu.setName("Default menu");
        defaultMenu.setDefaultSiteMenu(true);
        site.setMenu(defaultMenu);
        defaultMenu.setSiteId(site.getSiteId());
        persistance.putMenu(defaultMenu);

        site.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        final DraftMenu menu1 = new DraftMenu();
        menu1.setName("menu1");
        menu1.setSiteId(site.getSiteId());
        persistance.putMenu(menu1);

        final DraftMenu menu2 = new DraftMenu();
        menu2.setName("menu2");
        menu2.setSiteId(site.getSiteId());
        persistance.putMenu(menu2);

        final DraftImage image = new DraftImage();
        image.setName("menu2");
        image.setSiteId(site.getSiteId());
        persistance.putItem(image);


        List<DraftItem> menus = persistance.getDraftItemsBySiteId(site.getSiteId(), ItemType.ALL_ITEMS);
        Assert.assertEquals(3, menus.size());
    }

    @Test
    public void testGetDraftItemsBySiteId_withExcludedItems() throws Exception {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight);

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

        final DraftMenu defaultMenu = new DraftMenu();
        defaultMenu.setName("Default menu");
        defaultMenu.setDefaultSiteMenu(true);
        site.setMenu(defaultMenu);
        defaultMenu.setSiteId(site.getSiteId());
        persistance.putMenu(defaultMenu);

        site.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        final DraftMenu menu1 = new DraftMenu();
        menu1.setName("menu1");
        menu1.setSiteId(site.getSiteId());
        persistance.putMenu(menu1);

        final DraftMenu menu2 = new DraftMenu();
        menu2.setName("menu2");
        menu2.setSiteId(site.getSiteId());
        persistance.putMenu(menu2);

        final DraftImage image = new DraftImage();
        image.setName("menu2");
        image.setSiteId(site.getSiteId());
        persistance.putItem(image);


        List<DraftItem> menus = persistance.getDraftItemsBySiteId(site.getSiteId(), ItemType.ALL_ITEMS, ItemType.IMAGE);
        Assert.assertEquals(2, menus.size());
    }

    @Test
    public void testPutGetRemoveFormExportTask() throws Exception {
        final FormExportTask formExportTask = new FormExportTask();
        formExportTask.setName("name");
        persistance.putFormExportTask(formExportTask);

        HibernateManager.get().flush();


        Assert.assertEquals(formExportTask, persistance.getFormExportTask(formExportTask.getId()));


        persistance.removeFormExportTask(formExportTask);
        HibernateManager.get().flush();
        Assert.assertNull(persistance.getFormExportTask(formExportTask.getId()));
    }

    @Test
    public void testGetFormExportTasksByFormId() throws Exception {
        final FormExportTask formExportTask1 = new FormExportTask();
        formExportTask1.setFormId(1);
        formExportTask1.setName("name1");
        persistance.putFormExportTask(formExportTask1);

        final FormExportTask formExportTask2 = new FormExportTask();
        formExportTask2.setFormId(2);
        formExportTask2.setName("name2");
        persistance.putFormExportTask(formExportTask2);

        final FormExportTask formExportTask3 = new FormExportTask();
        formExportTask3.setFormId(1);
        formExportTask3.setName("name3");
        persistance.putFormExportTask(formExportTask3);


        final List<FormExportTask> formExportTasks = persistance.getFormExportTasksByFormId(1);
        Assert.assertEquals(2, formExportTasks.size());
        Assert.assertTrue(formExportTasks.contains(formExportTask1));
        Assert.assertFalse(formExportTasks.contains(formExportTask2));
        Assert.assertTrue(formExportTasks.contains(formExportTask3));
    }

    @Test
    public void testGetFormExportTasksBySiteId() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftCustomForm customForm = new DraftCustomForm();
        customForm.setSiteId(site.getSiteId());
        persistance.putItem(customForm);

        final FormExportTask formExportTask1 = new FormExportTask();
        formExportTask1.setFormId(customForm.getId());
        formExportTask1.setName("name1");
        persistance.putFormExportTask(formExportTask1);

        final FormExportTask formExportTask2 = new FormExportTask();
        formExportTask2.setFormId(1232);
        formExportTask2.setName("name2");
        persistance.putFormExportTask(formExportTask2);

        final FormExportTask formExportTask3 = new FormExportTask();
        formExportTask3.setFormId(customForm.getId());
        formExportTask3.setName("name3");
        persistance.putFormExportTask(formExportTask3);


        final List<FormExportTask> formExportTasks = persistance.getFormExportTasksBySiteId(site.getSiteId());
        Assert.assertEquals(2, formExportTasks.size());
        Assert.assertTrue(formExportTasks.contains(formExportTask1));
        Assert.assertFalse(formExportTasks.contains(formExportTask2));
        Assert.assertTrue(formExportTasks.contains(formExportTask3));
    }

    @Test
    public void testPutGetGoogleBaseDataMappedByFilledForm() throws Exception {
        final GoogleBaseDataExportMappedByFilledFormId dataExportMappedByFilledFormId = new GoogleBaseDataExportMappedByFilledFormId();
        dataExportMappedByFilledFormId.setFilledFormId(123);
        dataExportMappedByFilledFormId.setGoogleBaseItemId("");


        persistance.putGoogleBaseDataExportMappedByFilledFormId(dataExportMappedByFilledFormId);
        HibernateManager.get().flush();

        Assert.assertEquals(dataExportMappedByFilledFormId, persistance.getGoogleBaseDataExportMappedByFilledFormIdByFilledFormId(123));
    }

    @Test
    public void testGetAllFormExportTasks() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftCustomForm customForm = new DraftCustomForm();
        customForm.setSiteId(site.getSiteId());
        persistance.putItem(customForm);

        final FormExportTask formExportTask1 = new FormExportTask();
        formExportTask1.setFormId(customForm.getId());
        formExportTask1.setName("name1");
        persistance.putFormExportTask(formExportTask1);

        final FormExportTask formExportTask2 = new FormExportTask();
        formExportTask2.setFormId(1232);
        formExportTask2.setName("name2");
        persistance.putFormExportTask(formExportTask2);

        final FormExportTask formExportTask3 = new FormExportTask();
        formExportTask3.setFormId(customForm.getId());
        formExportTask3.setName("name3");
        persistance.putFormExportTask(formExportTask3);


        final List<FormExportTask> formExportTasks = persistance.getAllFormExportTasks();
        Assert.assertEquals(3, formExportTasks.size());
        Assert.assertTrue(formExportTasks.contains(formExportTask1));
        Assert.assertTrue(formExportTasks.contains(formExportTask2));
        Assert.assertTrue(formExportTasks.contains(formExportTask3));
    }

    @Test
    public void testSelectPageSettingsWithHtmlOrCss() throws Exception {
        final Site site = TestUtil.createSite();
        site.getLoginPage().getPageSettings().setHtml(null);
        site.getLoginPage().getPageSettings().setCss(null);

        final Page page = new Page();
        page.setSite(site);
        persistance.putPage(page);
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(page);
        final WorkPageSettings workPageSettings = TestUtil.createWorkPageSettings(draftPageSettings);
        draftPageSettings.setHtml("html");
        workPageSettings.setCss("css");


        final Page page2 = new Page();
        page2.setSite(site);
        persistance.putPage(page2);
        final DraftPageSettings draftPageSettings2 = TestUtil.createPageSettings(page2);
        final WorkPageSettings workPageSettings2 = TestUtil.createWorkPageSettings(draftPageSettings2);
        draftPageSettings2.setHtml("html2");


        Assert.assertEquals(3, persistance.getPageSettingsWithHtmlOrCss().size());
    }
}

