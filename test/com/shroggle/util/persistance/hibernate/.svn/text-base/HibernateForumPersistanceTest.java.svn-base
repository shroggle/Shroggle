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
import com.shroggle.logic.site.page.PageManager;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class HibernateForumPersistanceTest extends HibernatePersistanceTestBase {

    @Test
    public void putSiteOnForumRight() {
        DraftForum forum = new DraftForum();
        forum.setName("aa");
        persistance.putItem(forum);

        Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("f");
        site.setSubDomain("g");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        persistance.putSite(site);

        SiteOnItem siteOnForumRight = new SiteOnItem();
        siteOnForumRight.getId().setItem(forum);
        siteOnForumRight.getId().setSite(site);
        persistance.putSiteOnItem(siteOnForumRight);
    }

    @Test
    public void putForum() {
        DraftForum forum = new DraftForum();
        forum.setName("name");

        persistance.putItem(forum);

        DraftForum findForum = HibernateManager.get().find(DraftForum.class, forum.getId());
        Assert.assertEquals(forum, findForum);
    }

    @Test
    public void removeForum() {
        DraftForum forum = new DraftForum();
        forum.setName("name");
        persistance.putItem(forum);
        persistance.removeDraftItem(forum);

        Assert.assertNull(persistance.getDraftItem(forum.getId()));
    }

    @Test
    public void removeForumFromForumWidget() {
        DraftForum forum = new DraftForum();
        forum.setName("name");
        persistance.putItem(forum);

        Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("f");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        site.setSubDomain("f");
        persistance.putSite(site);

        Page page = TestUtil.createPage(site);
        PageManager pageVersion = new PageManager(page);

        WidgetItem widgetForum = new WidgetItem();
        widgetForum.setDraftItem(forum);
        persistance.putWidget(widgetForum);
        pageVersion.addWidget(widgetForum);

        persistance.removeDraftItem(forum);

        Assert.assertNull(persistance.getDraftItem(forum.getId()));
        Assert.assertNotNull(widgetForum.getDraftItem());
    }

    @Test
    public void getForumsByUserId() {
        User user = new User();
        user.setPassword("1");
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        site.setSubDomain("$");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("G");
        site.setTitle("F");
        persistance.putSite(site);

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight);
        site.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        DraftForum forum = new DraftForum();
        forum.setSiteId(site.getSiteId());
        forum.setName("name");
        persistance.putItem(forum);

        List<DraftItem> userForums = persistance.getDraftItemsByUserId(user.getUserId(), ItemType.FORUM);
        Assert.assertEquals(forum, userForums.get(0));
    }

    @Test
    public void getForumsByUserIdWithoutRight() {
        User user = new User();
        user.setPassword("1");
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        site.setSubDomain("$");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("G");
        site.setTitle("F");
        persistance.putSite(site);

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(false);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight);
        site.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        DraftForum forum = new DraftForum();
        forum.setSiteId(site.getSiteId());
        forum.setName("name");
        persistance.putItem(forum);

        List<DraftItem> userForums = persistance.getDraftItemsByUserId(user.getUserId(), ItemType.FORUM);
        Assert.assertEquals(0, userForums.size());
    }

    @Test
    public void getForumsByUserIdWithoutActive() {
        User user = new User();
        user.setPassword("1");
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        site.setSubDomain("$");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("G");
        site.setTitle("F");
        persistance.putSite(site);

        DraftForum forum = new DraftForum();
        forum.setSiteId(site.getSiteId());
        forum.setName("name");
        persistance.putItem(forum);

        List<DraftItem> userForums = persistance.getDraftItemsByUserId(user.getUserId(), ItemType.FORUM);
        Assert.assertEquals(0, userForums.size());
    }

    @Test
    public void getForumsByUserIdForEmpty() {
        User user = new User();
        user.setPassword("1");
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        List<DraftItem> userForums = persistance.getDraftItemsByUserId(user.getUserId(), ItemType.FORUM);
        Assert.assertEquals(0, userForums.size());
    }

    @Test
    public void getLastPostThreadPost() {
        DraftForum forum = new DraftForum();
        forum.setName("name");
        persistance.putItem(forum);

        SubForum subForum = new SubForum();
        subForum.setSubForumName("ffff");
        subForum.setSubForumDescription("ffff1");
        forum.addSubForum(subForum);
        persistance.putSubForum(subForum);

        ForumThread forumThread1 = new ForumThread();
        forumThread1.setThreadName("f");
        forumThread1.setThreadDescription("f2");
        forumThread1.setUpdateDate(new Date());
        subForum.addForumThread(forumThread1);
        persistance.putForumThread(forumThread1);

        ForumPost forumPost1 = new ForumPost();
        forumPost1.setText("bbb");
        forumPost1.setDateCreated(new Date(System.currentTimeMillis() * 2));
        forumThread1.addForumPost(forumPost1);
        persistance.putForumPost(forumPost1);

        ForumPost forumPost2 = new ForumPost();
        forumPost2.setText("bbb");
        forumPost2.setDateCreated(new Date());
        forumThread1.addForumPost(forumPost2);
        persistance.putForumPost(forumPost2);

        ForumPost lastForumPost = persistance.getLastThreadPost(forumThread1.getThreadId());
        Assert.assertEquals(lastForumPost, forumPost1);
    }

    @Test
    public void getForumPostsByUserId() {
        final User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        DraftForum forum = new DraftForum();
        forum.setName("name");
        persistance.putItem(forum);

        SubForum subForum = new SubForum();
        subForum.setSubForumName("ffff");
        subForum.setSubForumDescription("ffff1");
        forum.addSubForum(subForum);
        persistance.putSubForum(subForum);

        ForumThread forumThread = new ForumThread();
        forumThread.setThreadName("f");
        forumThread.setThreadDescription("f2");
        forumThread.setUpdateDate(new Date());
        subForum.addForumThread(forumThread);
        persistance.putForumThread(forumThread);

        ForumPost forumPost = new ForumPost();
        forumPost.setText("bbb");
        forumPost.setAuthor(user);
        forumPost.setDateCreated(new Date(System.currentTimeMillis() * 2));
        forumThread.addForumPost(forumPost);
        persistance.putForumPost(forumPost);

        List<ForumPost> forumPosts = persistance.getForumPostsByUserId(user.getUserId());
        Assert.assertEquals(1, forumPosts.size());
        Assert.assertEquals(forumPost, forumPosts.get(0));
    }

    @Test
    public void getForumPostsByUserIdWithOther() {
        final User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        DraftForum forum = new DraftForum();
        forum.setName("name");
        persistance.putItem(forum);

        SubForum subForum = new SubForum();
        subForum.setSubForumName("ffff");
        subForum.setSubForumDescription("ffff1");
        forum.addSubForum(subForum);
        persistance.putSubForum(subForum);

        ForumThread forumThread = new ForumThread();
        forumThread.setThreadName("f");
        forumThread.setThreadDescription("f2");
        forumThread.setUpdateDate(new Date());
        subForum.addForumThread(forumThread);
        persistance.putForumThread(forumThread);

        ForumPost forumPost = new ForumPost();
        forumPost.setText("bbb");
        forumPost.setAuthor(user);
        forumPost.setDateCreated(new Date(System.currentTimeMillis() * 2));
        forumThread.addForumPost(forumPost);
        persistance.putForumPost(forumPost);

        List<ForumPost> forumPosts = persistance.getForumPostsByUserId(user.getUserId() + 9);
        Assert.assertEquals(0, forumPosts.size());
    }

    @Test
    public void getForumPostsByUserIdWithoutUser() {
        DraftForum forum = new DraftForum();
        forum.setName("name");
        persistance.putItem(forum);

        SubForum subForum = new SubForum();
        subForum.setSubForumName("ffff");
        subForum.setSubForumDescription("ffff1");
        forum.addSubForum(subForum);
        persistance.putSubForum(subForum);

        ForumThread forumThread = new ForumThread();
        forumThread.setThreadName("f");
        forumThread.setThreadDescription("f2");
        forumThread.setUpdateDate(new Date());
        subForum.addForumThread(forumThread);
        persistance.putForumThread(forumThread);

        ForumPost forumPost = new ForumPost();
        forumPost.setText("bbb");
        forumPost.setDateCreated(new Date(System.currentTimeMillis() * 2));
        forumThread.addForumPost(forumPost);
        persistance.putForumPost(forumPost);

        List<ForumPost> forumPosts = persistance.getForumPostsByUserId(1);
        Assert.assertEquals(0, forumPosts.size());
    }

    @Test
    public void getForumThreadsByUserId() {
        final User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        DraftForum forum = new DraftForum();
        forum.setName("name");
        persistance.putItem(forum);

        SubForum subForum = new SubForum();
        subForum.setSubForumName("ffff");
        subForum.setSubForumDescription("ffff1");
        forum.addSubForum(subForum);
        persistance.putSubForum(subForum);

        ForumThread forumThread1 = new ForumThread();
        forumThread1.setThreadName("f");
        forumThread1.setThreadDescription("f2");
        forumThread1.setUpdateDate(new Date());
        forumThread1.setAuthor(user);
        subForum.addForumThread(forumThread1);
        persistance.putForumThread(forumThread1);

        List<ForumThread> forumThreads = persistance.getForumThreadsByUserId(user.getUserId());
        Assert.assertEquals(1, forumThreads.size());
        Assert.assertEquals(forumThread1, forumThreads.get(0));
    }

    @Test
    public void getForumThreadsByUserIdWithoutAuthor() {
        DraftForum forum = new DraftForum();
        forum.setName("name");
        persistance.putItem(forum);

        SubForum subForum = new SubForum();
        subForum.setSubForumName("ffff");
        subForum.setSubForumDescription("ffff1");
        forum.addSubForum(subForum);
        persistance.putSubForum(subForum);

        ForumThread forumThread1 = new ForumThread();
        forumThread1.setThreadName("f");
        forumThread1.setThreadDescription("f2");
        forumThread1.setUpdateDate(new Date());
        subForum.addForumThread(forumThread1);
        persistance.putForumThread(forumThread1);

        List<ForumThread> forumThreads = persistance.getForumThreadsByUserId(1);
        Assert.assertEquals(0, forumThreads.size());
    }

    @Test
    public void getForumThreadsByUserIdWithNotFound() {
        final User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        DraftForum forum = new DraftForum();
        forum.setName("name");
        persistance.putItem(forum);

        SubForum subForum = new SubForum();
        subForum.setSubForumName("ffff");
        subForum.setSubForumDescription("ffff1");
        forum.addSubForum(subForum);
        persistance.putSubForum(subForum);

        ForumThread forumThread1 = new ForumThread();
        forumThread1.setThreadName("f");
        forumThread1.setThreadDescription("f2");
        forumThread1.setUpdateDate(new Date());
        forumThread1.setAuthor(user);
        subForum.addForumThread(forumThread1);
        persistance.putForumThread(forumThread1);

        List<ForumThread> forumThreads = persistance.getForumThreadsByUserId(user.getUserId() + 90);
        Assert.assertEquals(0, forumThreads.size());
    }

    @Test
    public void getLastSubForumPost() {
        User account = new User();
        account.setEmail("a");
        account.setRegistrationDate(new Date());
        persistance.putUser(account);

        DraftForum forum = new DraftForum();
        forum.setName("name");
        persistance.putItem(forum);

        SubForum subForum = new SubForum();
        subForum.setSubForumName("ffff");
        subForum.setSubForumDescription("ffff1");
        forum.addSubForum(subForum);
        persistance.putSubForum(subForum);

        ForumThread forumThread1 = new ForumThread();
        forumThread1.setThreadName("f");
        forumThread1.setThreadDescription("f2");
        forumThread1.setUpdateDate(new Date());
        subForum.addForumThread(forumThread1);
        persistance.putForumThread(forumThread1);

        ForumPost forumPost1 = new ForumPost();
        forumPost1.setText("bbb");
        forumPost1.setDateCreated(new Date(System.currentTimeMillis() * 2));
        forumThread1.addForumPost(forumPost1);
        persistance.putForumPost(forumPost1);

        ForumPost forumPost2 = new ForumPost();
        forumPost2.setText("bbb");
        forumPost2.setDateCreated(new Date());
        forumThread1.addForumPost(forumPost2);
        persistance.putForumPost(forumPost2);

        ForumPost lastForumPost = persistance.getLastSubForumPost(subForum.getSubForumId());
        Assert.assertEquals(lastForumPost, forumPost1);
    }

    @Test
    public void getFormFiltersByUserId_and_getFormFilterByNameAndUserId() {
        User user = new User();
        user.setPassword("1");
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        site.setSubDomain("$");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("G");
        site.setTitle("F");
        persistance.putSite(site);

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight);
        site.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        DraftForm form = new DraftCustomForm();
        form.setSiteId(site.getSiteId());
        form.setName("name");
        persistance.putItem(form);

        DraftFormFilter filter = new DraftFormFilter();
        filter.setForm(form);
        String filterName = "filter name "+System.currentTimeMillis();
        filter.setName(filterName);
        persistance.putFormFilter(filter);

        List<DraftFormFilter> userFitlers = persistance.getFormFiltersByUserId(user.getUserId());
        Assert.assertEquals(filter, userFitlers.get(0));

        DraftFormFilter namedUserFilter = persistance.getFormFilterByNameAndUserId(filterName, user.getUserId());
        Assert.assertEquals(filter, namedUserFilter);
    }

}