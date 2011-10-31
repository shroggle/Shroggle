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
import com.shroggle.logic.site.page.PageManager;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class RenderRobotsMetaTagTest {

    @Test
    public void testExecuteRobotsInPage() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.getSeoSettings().setRobotsMetaTag("NOINDEX,FOLLOW");
        site.getSeoSettings().setRobotsMetaTag(null);

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!-- PAGE_HEADER -->");
        final RenderRobotsMetaTag renderRobotsMetaTag = new RenderRobotsMetaTag(pageVersion);
        renderRobotsMetaTag.execute(null, stringBuilder);

        Assert.assertEquals("<meta name=\"ROBOTS\" content=\"NOINDEX,FOLLOW\"/>\n" +
                "<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test
    public void testExecuteRobotsNullInPagePresentInSite() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.getSeoSettings().setRobotsMetaTag(null);
        site.getSeoSettings().setRobotsMetaTag("NOINDEX,FOLLOW");

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!-- PAGE_HEADER -->");
        final RenderRobotsMetaTag renderRobotsMetaTag = new RenderRobotsMetaTag(pageVersion);
        renderRobotsMetaTag.execute(null, stringBuilder);

        Assert.assertEquals("<meta name=\"ROBOTS\" content=\"NOINDEX,FOLLOW\"/>\n" +
                "<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test
    public void testExecuteRobotsEmptyInPagePresentInSite() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.getSeoSettings().setRobotsMetaTag("");
        site.getSeoSettings().setRobotsMetaTag("NOINDEX,FOLLOW");

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!-- PAGE_HEADER -->");
        final RenderRobotsMetaTag renderRobotsMetaTag = new RenderRobotsMetaTag(pageVersion);
        renderRobotsMetaTag.execute(null, stringBuilder);

        Assert.assertEquals("<meta name=\"ROBOTS\" content=\"NOINDEX,FOLLOW\"/>\n" +
                "<!-- PAGE_HEADER -->", stringBuilder.toString());
    }
    
    @Test
    public void testExecuteRobotsInSiteAndPage() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.getSeoSettings().setRobotsMetaTag("NOINDEX,FOLLOW");
        site.getSeoSettings().setRobotsMetaTag("NOINDEX,FOLLOW2");

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!-- PAGE_HEADER -->");
        final RenderRobotsMetaTag renderRobotsMetaTag = new RenderRobotsMetaTag(pageVersion);
        renderRobotsMetaTag.execute(null, stringBuilder);

        Assert.assertEquals("<meta name=\"ROBOTS\" content=\"NOINDEX,FOLLOW\"/>\n" +
                "<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test
    public void testExecuteWithoutRobots() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.getSeoSettings().setRobotsMetaTag(null);

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!-- PAGE_HEADER -->");
        final RenderRobotsMetaTag renderRobotsMetaTag = new RenderRobotsMetaTag(pageVersion);
        renderRobotsMetaTag.execute(null, stringBuilder);

        Assert.assertEquals("<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test
    public void testExecuteWithEmptyRobots() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.getSeoSettings().setRobotsMetaTag("");

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!-- PAGE_HEADER -->");
        final RenderRobotsMetaTag renderRobotsMetaTag = new RenderRobotsMetaTag(pageVersion);
        renderRobotsMetaTag.execute(null, stringBuilder);

        Assert.assertEquals("<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIntializationWithNullPageVersion() {
        new RenderRobotsMetaTag(null);
    }

}
