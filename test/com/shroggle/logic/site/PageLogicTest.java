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
package com.shroggle.logic.site;

import com.shroggle.logic.site.page.PageManager;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.Assert;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.Page;
import com.shroggle.entity.Site;


import java.util.Date;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class PageLogicTest {

    @Test
    public void getNameWithoutVersion() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);

        Assert.assertEquals("", new PageManager(page).getName());
    }

    @Test
    public void getName() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        PageManager pageManager = new PageManager(page);
        pageManager.setName("fff");
        pageManager.setCreationDate(new Date(System.currentTimeMillis() * 2));

        Assert.assertEquals("fff", pageManager.getName());
    }

    @Test
    public void getNameWithNull() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageManager = new PageManager(page);
        pageManager.setUrl("g");
        pageManager.setName(null);

        Assert.assertEquals("", new PageManager(page).getName());
    }

    @Test
    public void getId() {
        final Page page = TestUtil.createPageAndSite();
        page.setPageId(12);

        Assert.assertEquals(12, new PageManager(page).getPageId());
    }

}
