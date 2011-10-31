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
package com.shroggle.logic.site.page;

import com.shroggle.TestUtil;
import com.shroggle.entity.Page;

import com.shroggle.entity.Site;
import com.shroggle.logic.site.page.pageversion.PageTitle;
import com.shroggle.logic.site.page.pageversion.PageTitleGetter;
import com.shroggle.TestRunnerWithMockServices;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Artem Stasuk (artem)
 *         </p>
 *         Date: 15 вер 2008
 */
@RunWith(TestRunnerWithMockServices.class)
public class PageVersionTitleGetterTest {

    @Test(expected = UnsupportedOperationException.class)
    public void createWithNullPageVersion() {
        new PageTitleGetter(null);
    }

    @Test
    public void create() {
        Site site = new Site();
        site.setTitle("a1");
        site.setSubDomain("f");
        Page page = TestUtil.createPage(site);
        PageManager pageVersion = new PageManager(page);
        pageVersion.setTitle("a");
        pageVersion.setName("n");
        final PageTitle pageTitle =
                new PageTitleGetter(pageVersion);

        Assert.assertEquals("a", pageTitle.getPageVersionTitle());
        Assert.assertEquals("a1", pageTitle.getSiteTitle());
    }
    
    @Test
    public void createWithNotSavedPage() {
        Site site = new Site();
        site.setTitle("a1");
        site.setSubDomain("f");
        Page page = TestUtil.createPage(site);
        page.setSaved(false);
        PageManager pageVersion = new PageManager(page);
        pageVersion.setTitle("a");
        pageVersion.setName("n");
        final PageTitle pageTitle =
                new PageTitleGetter(pageVersion);

        Assert.assertEquals("New page", pageTitle.getPageVersionTitle());
        Assert.assertEquals("a1", pageTitle.getSiteTitle());
    }

    @Test
    public void createWithNullSiteTitleAndNullPageVersionTitle() {
        Site site = new Site();
        site.setSubDomain("a");
        Page page = TestUtil.createPage(site);
        PageManager pageVersion = new PageManager(page);
        pageVersion.setName("b");
        final PageTitle pageTitle =
                new PageTitleGetter(pageVersion);

        Assert.assertEquals("b", pageTitle.getPageVersionTitle());
        Assert.assertEquals("a", pageTitle.getSiteTitle());
    }

    @Test
    public void createWithEmptySiteTitleAndEmptyPageVersionTitle() {
        Site site = new Site();
        site.setTitle(" ");
        site.setSubDomain("a");
        Page page = TestUtil.createPage(site);
        PageManager pageVersion = new PageManager(page);
        pageVersion.setTitle(" ");
        pageVersion.setName("b");
        final PageTitle pageTitle =
                new PageTitleGetter(pageVersion);

        Assert.assertEquals("b", pageTitle.getPageVersionTitle());
        Assert.assertEquals("a", pageTitle.getSiteTitle());
    }

}
