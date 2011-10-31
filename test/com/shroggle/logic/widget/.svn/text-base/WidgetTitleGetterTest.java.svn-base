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
package com.shroggle.logic.widget;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Artem Stasuk (artem)
 *         </p>
 *         Date: 15 вер 2008
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class WidgetTitleGetterTest {

    @Test
    public void createWithNotConfigureBlogWidget() {
        Site site = new Site();
        site.setTitle("a1");
        site.setSubDomain("f");
        Page page = TestUtil.createPage(site);
        
        PageManager pageVersion = new PageManager(page);
        pageVersion.setTitle("a");
        pageVersion.setName("n");
        WidgetItem widgetBlog = TestUtil.createWidgetItem();
        pageVersion.addWidget(widgetBlog);

        WidgetTitle widgetTitle = new WidgetTitleGetter(widgetBlog);

        Assert.assertEquals("item undefined", widgetTitle.getWidgetTitle());
        Assert.assertEquals("a1", widgetTitle.getSiteTitle());
        Assert.assertEquals("a", widgetTitle.getPageVersionTitle());
    }

    @Test
    public void createWithNotConfigureForumWidget() {
        Site site = new Site();
        site.setTitle("a1");
        site.setSubDomain("f");
        Page page = TestUtil.createPage(site);
        
        PageManager pageVersion = new PageManager(page);
        pageVersion.setTitle("a");
        pageVersion.setName("n");
        
        WidgetItem widgetForum = TestUtil.createWidgetItem();
        pageVersion.addWidget(widgetForum);

        WidgetTitle widgetTitle = new WidgetTitleGetter(widgetForum);

        Assert.assertEquals("item undefined", widgetTitle.getWidgetTitle());
        Assert.assertEquals("a1", widgetTitle.getSiteTitle());
        Assert.assertEquals("a", widgetTitle.getPageVersionTitle());
    }

    @Test
    public void createWithConfigureBlogWidget() {
        Site site = new Site();
        site.setTitle("a1");
        site.setSubDomain("f");
        Page page = TestUtil.createPage(site);
        
        PageManager pageVersion = new PageManager(page);
        pageVersion.setTitle("a");
        pageVersion.setName("n");
        
        WidgetItem widgetBlog = TestUtil.createWidgetItem();
        pageVersion.addWidget(widgetBlog);
        DraftBlog blog = new DraftBlog();
        blog.setName("fff");
        persistance.putItem(blog);
        widgetBlog.setDraftItem(blog);

        WidgetTitle widgetTitle = new WidgetTitleGetter(widgetBlog);

        Assert.assertEquals("fff", widgetTitle.getWidgetTitle());
        Assert.assertEquals("a1", widgetTitle.getSiteTitle());
        Assert.assertEquals("a", widgetTitle.getPageVersionTitle());
    }

    @Test
    public void createNonConfiguredWithConfigureBlogSummaryWidget() {
        final Site site = new Site();
        site.setTitle("a1");
        site.setSubDomain("f");

        final Page page = TestUtil.createPage(site);
        

        final PageManager pageVersion = new PageManager(page);
        pageVersion.setTitle("a");
        pageVersion.setName("n");
        

        final WidgetItem widget = TestUtil.createWidgetItem();
        pageVersion.addWidget(widget);

        final DraftBlogSummary summary = new DraftBlogSummary();
        summary.setName("g");
        persistance.putItem(summary);
        widget.setDraftItem(summary);

        WidgetTitle widgetTitle = new WidgetTitleGetter(widget);

        Assert.assertEquals("g", widgetTitle.getWidgetTitle());
        Assert.assertEquals("a1", widgetTitle.getSiteTitle());
        Assert.assertEquals("a", widgetTitle.getPageVersionTitle());

//        widget.setBlogSummaryId(summary.getUserId());

        widgetTitle = new WidgetTitleGetter(widget, "default");

        Assert.assertEquals("g", widgetTitle.getWidgetTitle());
        Assert.assertEquals("a1", widgetTitle.getSiteTitle());
        Assert.assertEquals("a", widgetTitle.getPageVersionTitle());
    }

    @Test
    public void createWithConfigureForumWidget() {
        Site site = new Site();
        site.setTitle("a1");
        site.setSubDomain("f");
        Page page = TestUtil.createPage(site);
        
        PageManager pageVersion = new PageManager(page);
        pageVersion.setTitle("a");
        pageVersion.setName("n");
        
        WidgetItem widgetForum = TestUtil.createWidgetItem();
        pageVersion.addWidget(widgetForum);
        DraftForum forum = new DraftForum();
        forum.setName("fff");
        persistance.putItem(forum);
        widgetForum.setDraftItem(forum);

        WidgetTitle widgetTitle = new WidgetTitleGetter(widgetForum);

        Assert.assertEquals("fff", widgetTitle.getWidgetTitle());
    }

    @Test
    public void createWithNotFoundConfigureBlogWidget() {
        Site site = new Site();
        site.setTitle("a1");
        site.setSubDomain("f");
        Page page = TestUtil.createPage(site);
        
        PageManager pageVersion = new PageManager(page);
        pageVersion.setTitle("a");
        pageVersion.setName("n");
        
        WidgetItem widgetBlog = TestUtil.createWidgetItem();
        pageVersion.addWidget(widgetBlog);
        widgetBlog.setDraftItem(null);

        WidgetTitle widgetTitle = new WidgetTitleGetter(widgetBlog);
        Assert.assertEquals("item undefined", widgetTitle.getWidgetTitle());

        widgetTitle = new WidgetTitleGetter(widgetBlog, "default");
        Assert.assertEquals("default", widgetTitle.getWidgetTitle());
    }

    @Test
    public void createWithNotFoundConfigureForumWidget() {
        Site site = new Site();
        site.setTitle("a1");
        site.setSubDomain("f");
        Page page = TestUtil.createPage(site);
        
        PageManager pageVersion = new PageManager(page);
        pageVersion.setTitle("a");
        pageVersion.setName("n");
        
        WidgetItem widgetForum = TestUtil.createWidgetItem();
        pageVersion.addWidget(widgetForum);
        widgetForum.setDraftItem(null);

        WidgetTitle widgetTitle = new WidgetTitleGetter(widgetForum);

        Assert.assertEquals("item undefined", widgetTitle.getWidgetTitle());
    }

    @Test
    public void createWithNotFoundConfigureTextWidget() {
        Site site = new Site();
        site.setTitle("a1");
        site.setSubDomain("f");
        Page page = TestUtil.createPage(site);
        
        PageManager pageVersion = new PageManager(page);
        pageVersion.setTitle("a");
        pageVersion.setName("n");
        

        WidgetItem widgetItem = TestUtil.createTextWidget();
        pageVersion.addWidget(widgetItem);

        WidgetTitle widgetTitle = new WidgetTitleGetter(widgetItem);
        Assert.assertEquals("Text", widgetTitle.getWidgetTitle());

        widgetTitle = new WidgetTitleGetter(widgetItem, "default");
        Assert.assertEquals("Text", widgetTitle.getWidgetTitle());
    }

    @Test
    public void createWithNotFoundConfigureRegistrationWidget() {
        Site site = new Site();
        site.setTitle("a1");
        site.setSubDomain("f");
        Page page = TestUtil.createPage(site);
        
        PageManager pageVersion = new PageManager(page);
        pageVersion.setTitle("a");
        pageVersion.setName("n");
        
        WidgetItem widgetForum = TestUtil.createWidgetItem();
        pageVersion.addWidget(widgetForum);

        WidgetTitle widgetTitle = new WidgetTitleGetter(widgetForum);
        Assert.assertEquals("item undefined", widgetTitle.getWidgetTitle());

        widgetTitle = new WidgetTitleGetter(widgetForum, "default");
        Assert.assertEquals("default", widgetTitle.getWidgetTitle());
    }

    @Test
    public void createConfigureTextWidget() {
        Site site = new Site();
        site.setTitle("a1");
        site.setSubDomain("f");
        Page page = TestUtil.createPage(site);
        
        PageManager pageVersion = new PageManager(page);
        pageVersion.setTitle("a");
        pageVersion.setName("n");
        

        WidgetItem widgetItem = TestUtil.createTextWidget();
        final DraftText draftText = (DraftText)widgetItem.getDraftItem();//persistance.getDraftItem(widgetItem.getDraftItem());
        draftText.setName("text-text-text-...");
        draftText.setText("<p>text-text-text-text-text-text</p>");
        pageVersion.addWidget(widgetItem);

        WidgetTitle widgetTitle = new WidgetTitleGetter(widgetItem);

        Assert.assertEquals("text-text-text-...", widgetTitle.getWidgetTitle());
    }

    @Test
    public void getWidgetTitleInCSSforSiteMenu() {
        Site site = new Site();
        site.setTitle("a1");
        site.setSubDomain("f");
        Page page = TestUtil.createPage(site);
        
        PageManager pageVersion = new PageManager(page);
        pageVersion.setTitle("a");
        pageVersion.setName("n");
        
        WidgetItem widgetMenu = TestUtil.createWidgetItem();
        pageVersion.addWidget(widgetMenu);

        DraftMenu menu = new DraftMenu();
        menu.setName("menuName1");
        menu.setSiteId(site.getSiteId());
        persistance.putMenu(menu);
        widgetMenu.setDraftItem(menu);

        WidgetTitle widgetTitle = new WidgetTitleGetter(widgetMenu);

        Assert.assertEquals("menuName1", widgetTitle.getWidgetTitle());
    }

    @Test
    public void createWithCompositWidget() {
        Site site = new Site();
        site.setTitle("a1");
        site.setSubDomain("f");
        Page page = TestUtil.createPage(site);
        
        PageManager pageVersion = new PageManager(page);
        pageVersion.setTitle("a");
        pageVersion.setName("n");
        
        WidgetComposit widgetComposit = new WidgetComposit();
        pageVersion.addWidget(widgetComposit);

        WidgetTitle widgetTitle = new WidgetTitleGetter(widgetComposit);

        Assert.assertEquals("Media block", widgetTitle.getWidgetTitle());
    }

    private final Persistance persistance = ServiceLocator.getPersistance();

}
