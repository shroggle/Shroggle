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
import org.junit.Before;
import org.junit.Test;

/**
 * @author Artem Stasuk
 */
public class HibernateSitePersistanceOthersTest extends HibernatePersistanceTestBase {

    @Before
    public void before() {
        super.before();

        Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("f");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        site.setSubDomain("aa");
        persistance.putSite(site);
        siteId = site.getSiteId();

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);
        pageVersion.setName("a");

        DraftBlog blog = new DraftBlog();
        blog.setName("aa");
        persistance.putItem(blog);

        WidgetItem widgetItem = TestUtil.createWidgetItem();
        widgetItem.setDraftItem(blog);
        persistance.putWidget(widgetItem);
        pageVersion.addWidget(widgetItem);
        widgetItemId = widgetItem.getWidgetId();

        siteOnBlogRight = new SiteOnItem();
        siteOnBlogRight.getId().setItem(blog);
        siteOnBlogRight.getId().setSite(site);
        persistance.putSiteOnItem(siteOnBlogRight);

        WidgetItem widgetItem2 = TestUtil.createWidgetItem();
        persistance.putWidget(widgetItem2);
        pageVersion.addWidget(widgetItem2);
        widgetItemId2 = widgetItem2.getWidgetId();
        
    }

    @Test
    public void removeSiteOnBlogRight() {
        Assert.assertNotNull(persistance.getWidget(widgetItemId));
        Assert.assertNotNull(persistance.getWidget(widgetItemId2));
        Assert.assertEquals(1, persistance.getSiteOnItemsBySite(siteId).size());

        persistance.removeSiteOnItemRight(
                persistance.getSiteOnItemRightBySiteIdItemIdAndType(
                        siteOnBlogRight.getSite().getSiteId(), siteOnBlogRight.getItem().getId(),
                        siteOnBlogRight.getItem().getItemType()));

        Assert.assertNotNull(persistance.getWidget(widgetItemId2));
        Assert.assertEquals(0, persistance.getSiteOnItemsBySite(siteId).size());

        HibernateManager.get().clear();

        Assert.assertNull("Must will be removed!", persistance.getWidget(widgetItemId));
    }

    private SiteOnItem siteOnBlogRight;
    private int widgetItemId;
    private int widgetItemId2;
    private int siteId;

}
