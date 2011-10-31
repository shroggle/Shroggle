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
public class RenderCopyrightMetaTagTest {

    @Test
    public void testExecuteCopyrightInPage() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.getSeoSettings().setCopyrightMetaTag("copyright_2010");
        site.getSeoSettings().setCopyrightMetaTag(null);

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!-- PAGE_HEADER -->");
        final RenderCopyrightMetaTag renderCopyrightMetaTag = new RenderCopyrightMetaTag(pageVersion);
        renderCopyrightMetaTag.execute(null, stringBuilder);

        Assert.assertEquals("<meta name=\"copyright\" content=\"copyright_2010\"/>\n" +
                "<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test
    public void testExecuteCopyrightNullInPagePresentInSite() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.getSeoSettings().setCopyrightMetaTag(null);
        site.getSeoSettings().setCopyrightMetaTag("copyright_2010");

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!-- PAGE_HEADER -->");
        final RenderCopyrightMetaTag renderCopyrightMetaTag = new RenderCopyrightMetaTag(pageVersion);
        renderCopyrightMetaTag.execute(null, stringBuilder);

        Assert.assertEquals("<meta name=\"copyright\" content=\"copyright_2010\"/>\n" +
                "<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test
    public void testExecuteCopyrightEmptyInPagePresentInSite() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.getSeoSettings().setCopyrightMetaTag("");
        site.getSeoSettings().setCopyrightMetaTag("copyright_2010");

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!-- PAGE_HEADER -->");
        final RenderCopyrightMetaTag renderCopyrightMetaTag = new RenderCopyrightMetaTag(pageVersion);
        renderCopyrightMetaTag.execute(null, stringBuilder);

        Assert.assertEquals("<meta name=\"copyright\" content=\"copyright_2010\"/>\n" +
                "<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test
    public void testExecuteCopyrightInPageAndInSite() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.getSeoSettings().setCopyrightMetaTag("copyright_2010");
        site.getSeoSettings().setCopyrightMetaTag("copyright_2011");

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!-- PAGE_HEADER -->");
        final RenderCopyrightMetaTag renderCopyrightMetaTag = new RenderCopyrightMetaTag(pageVersion);
        renderCopyrightMetaTag.execute(null, stringBuilder);

        Assert.assertEquals("<meta name=\"copyright\" content=\"copyright_2010\"/>\n" +
                "<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test
    public void testExecuteWithoutCopyright() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.getSeoSettings().setCopyrightMetaTag(null);
        site.getSeoSettings().setCopyrightMetaTag(null);

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!-- PAGE_HEADER -->");
        final RenderCopyrightMetaTag renderCopyrightMetaTag = new RenderCopyrightMetaTag(pageVersion);
        renderCopyrightMetaTag.execute(null, stringBuilder);

        Assert.assertEquals("<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test
    public void testExecuteWithEmptyCopyright() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.getSeoSettings().setCopyrightMetaTag("");

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!-- PAGE_HEADER -->");
        final RenderCopyrightMetaTag renderCopyrightMetaTag = new RenderCopyrightMetaTag(pageVersion);
        renderCopyrightMetaTag.execute(null, stringBuilder);

        Assert.assertEquals("<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIntializationWithNullPageVersion() {
        new RenderCopyrightMetaTag(null);
    }

}
