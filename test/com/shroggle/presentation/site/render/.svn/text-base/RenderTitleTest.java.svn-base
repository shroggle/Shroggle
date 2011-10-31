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
package com.shroggle.presentation.site.render;


import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.logic.site.page.PageManager;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class RenderTitleTest {

    @Test
    public void executeWithoutTitle() throws IOException, ServletException {
        PageManager pageVersion = new PageManager(TestUtil.createPageAndSite());
        pageVersion.setName("f");

        StringBuilder html = new StringBuilder("<!-- PAGE_TITLE -->");
        new RenderTitle(pageVersion, SiteShowOption.ON_USER_PAGES).execute(null, html);

        Assert.assertEquals("f", html.toString());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createWithNull() {
        new RenderTitle(null, SiteShowOption.ON_USER_PAGES);
    }

    @Test
    public void executeWithTitle() throws IOException, ServletException {
        PageManager pageVersion = new PageManager(TestUtil.createPageAndSite());
        pageVersion.setName("f");
        pageVersion.setTitle("G");

        StringBuilder html = new StringBuilder("<!-- PAGE_TITLE -->");
        new RenderTitle(pageVersion, SiteShowOption.ON_USER_PAGES).execute(null, html);

        Assert.assertEquals("G", html.toString());
    }

    @Test
    public void executeWithEmptyTitle() throws IOException, ServletException {
        PageManager pageVersion = new PageManager(TestUtil.createPageAndSite());
        pageVersion.setName("f");
        pageVersion.setTitle("");

        StringBuilder html = new StringBuilder("<!-- PAGE_TITLE -->");
        new RenderTitle(pageVersion, SiteShowOption.ON_USER_PAGES).execute(null, html);

        Assert.assertEquals("f", html.toString());
    }

    @Test
    public void executeWithoutTag() throws IOException, ServletException {
        PageManager pageVersion = new PageManager(TestUtil.createPageAndSite());
        pageVersion.setName("f");
        pageVersion.setTitle("G");

        StringBuilder html = new StringBuilder("");
        new RenderTitle(pageVersion, SiteShowOption.ON_USER_PAGES).execute(null, html);

        Assert.assertEquals("", html.toString());
    }

}
