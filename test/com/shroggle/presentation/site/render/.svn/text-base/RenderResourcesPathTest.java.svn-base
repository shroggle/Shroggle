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
import com.shroggle.entity.Page;
import com.shroggle.entity.Site;
import com.shroggle.entity.ThemeId;
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
public class RenderResourcesPathTest {

    @Test
    public void executeWithoutTag() throws IOException, ServletException {
        PageManager pageVersion = new PageManager(TestUtil.createPageAndSite());
        pageVersion.setThemeId(new ThemeId("f", "f1"));
        Render render = new RenderResourcesPath("f", pageVersion);
        StringBuilder html = new StringBuilder("none");
        render.execute(null, html);

        Assert.assertEquals("none", html.toString());
    }

    @Test
    public void executeWithThemeInPageVersion() throws IOException, ServletException {
        PageManager pageVersion = new PageManager(TestUtil.createPageAndSite());
        pageVersion.setThemeId(new ThemeId("t", "f1"));
        Render render = new RenderResourcesPath("f", pageVersion);
        StringBuilder html = new StringBuilder("<!-- TEMPLATE_RESOURCE --> a <!-- TEMPLATE_RESOURCE -->");
        render.execute(null, html);

        Assert.assertEquals("f/t a f/t", html.toString());
    }

    @Test
    public void executeWithThemeInSite() throws IOException, ServletException {
        Site site = new Site();
        Page page = TestUtil.createPage(site);
        PageManager pageVersion = new PageManager(page);
        site.setThemeId(new ThemeId("t", "f1"));
        Render render = new RenderResourcesPath("f", pageVersion);
        StringBuilder html = new StringBuilder("<!-- TEMPLATE_RESOURCE --> a <!-- TEMPLATE_RESOURCE -->");
        render.execute(null, html);

        Assert.assertEquals("f/t a f/t", html.toString());
    }

}
