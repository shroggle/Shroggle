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
public class RenderAuthorMetaTagTest {
    
    @Test
    public void testExecuteWithAuthorInPage() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.getSeoSettings().setAuthorMetaTag("first_name_last_name");
        site.getSeoSettings().setAuthorMetaTag(null);

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!-- PAGE_HEADER -->");
        final RenderAuthorMetaTag renderAuthorMetaTag = new RenderAuthorMetaTag(pageVersion);
        renderAuthorMetaTag.execute(null, stringBuilder);

        Assert.assertEquals("<meta name=\"Author\" content=\"first_name_last_name\"/>\n" +
                "<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test
    public void testExecuteWithAuthorNullInPagePresentInSite() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.getSeoSettings().setAuthorMetaTag(null);
        site.getSeoSettings().setAuthorMetaTag("first_name_last_name");

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!-- PAGE_HEADER -->");
        final RenderAuthorMetaTag renderAuthorMetaTag = new RenderAuthorMetaTag(pageVersion);
        renderAuthorMetaTag.execute(null, stringBuilder);

        Assert.assertEquals("<meta name=\"Author\" content=\"first_name_last_name\"/>\n" +
                "<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test
    public void testExecuteWithAuthorEmptyInPagePresentInSite() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.getSeoSettings().setAuthorMetaTag("");
        site.getSeoSettings().setAuthorMetaTag("first_name_last_name");

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!-- PAGE_HEADER -->");
        final RenderAuthorMetaTag renderAuthorMetaTag = new RenderAuthorMetaTag(pageVersion);
        renderAuthorMetaTag.execute(null, stringBuilder);

        Assert.assertEquals("<meta name=\"Author\" content=\"first_name_last_name\"/>\n" +
                "<!-- PAGE_HEADER -->", stringBuilder.toString());
    }
    
    @Test
    public void testExecuteWithAuthorInPageAndSite() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.getSeoSettings().setAuthorMetaTag("first_name_last_name");
        site.getSeoSettings().setAuthorMetaTag("first_name_last_name2");

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!-- PAGE_HEADER -->");
        final RenderAuthorMetaTag renderAuthorMetaTag = new RenderAuthorMetaTag(pageVersion);
        renderAuthorMetaTag.execute(null, stringBuilder);

        Assert.assertEquals("<meta name=\"Author\" content=\"first_name_last_name\"/>\n" +
                "<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test
    public void testExecuteWithoutAuthor() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.getSeoSettings().setAuthorMetaTag(null);
        site.getSeoSettings().setAuthorMetaTag(null);

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!-- PAGE_HEADER -->");
        final RenderAuthorMetaTag renderAuthorMetaTag = new RenderAuthorMetaTag(pageVersion);
        renderAuthorMetaTag.execute(null, stringBuilder);

        Assert.assertEquals("<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test
    public void testExecuteWithEmptyAuthor() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.getSeoSettings().setAuthorMetaTag("");

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!-- PAGE_HEADER -->");
        final RenderAuthorMetaTag renderAuthorMetaTag = new RenderAuthorMetaTag(pageVersion);
        renderAuthorMetaTag.execute(null, stringBuilder);

        Assert.assertEquals("<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIntializationWithNullPageVersion() {
        new RenderAuthorMetaTag(null);
    }

}
