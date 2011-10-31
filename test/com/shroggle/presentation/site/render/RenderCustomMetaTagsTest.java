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

import java.util.ArrayList;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class RenderCustomMetaTagsTest {

    @Test
    public void testExecuteWithMetaTagsInPage() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.getSeoSettings().setCustomMetaTagList(new ArrayList<String>() {{
            add("<meta name=\"custom\" content=\"custom_content\"/>\n");
        }});

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!-- PAGE_HEADER -->");
        final RenderCustomMetaTags renderCustomMetaTags = new RenderCustomMetaTags(pageVersion);
        renderCustomMetaTags.execute(null, stringBuilder);

        Assert.assertEquals("<meta name=\"custom\" content=\"custom_content\"/>\n" +
                "<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test
    public void testExecuteWithMetaTagsInSite() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        site.getSeoSettings().setCustomMetaTagList(new ArrayList<String>() {{
            add("<meta name=\"custom\" content=\"site_content\"/>\n");
        }});

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!-- PAGE_HEADER -->");
        final RenderCustomMetaTags renderCustomMetaTags = new RenderCustomMetaTags(pageVersion);
        renderCustomMetaTags.execute(null, stringBuilder);

        Assert.assertEquals("<meta name=\"custom\" content=\"site_content\"/>\n" +
                "<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test
    public void testExecuteWithMetaTagsInBothSiteAndPage() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.getSeoSettings().setCustomMetaTagList(new ArrayList<String>() {{
            add("<meta name=\"custom\" content=\"page_content\"/>\n");
        }});
        site.getSeoSettings().setCustomMetaTagList(new ArrayList<String>() {{
            add("<meta name=\"custom\" content=\"site_content\"/>\n");
        }});

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!-- PAGE_HEADER -->");
        final RenderCustomMetaTags renderCustomMetaTags = new RenderCustomMetaTags(pageVersion);
        renderCustomMetaTags.execute(null, stringBuilder);

        Assert.assertEquals("<meta name=\"custom\" content=\"page_content\"/>\n" +
                "<meta name=\"custom\" content=\"site_content\"/>\n" +
                "<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test
    public void testExecuteWithNullCustomMetaTag() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.getSeoSettings().setCustomMetaTagList(new ArrayList<String>() {{
            add(null);
        }});

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!-- PAGE_HEADER -->");
        final RenderCustomMetaTags renderCustomMetaTags = new RenderCustomMetaTags(pageVersion);
        renderCustomMetaTags.execute(null, stringBuilder);

        Assert.assertEquals("<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test
    public void testExecuteWithoutCustomMetaTags() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.getSeoSettings().setCustomMetaTagList(null);

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!-- PAGE_HEADER -->");
        final RenderCustomMetaTags renderCustomMetaTags = new RenderCustomMetaTags(pageVersion);
        renderCustomMetaTags.execute(null, stringBuilder);

        Assert.assertEquals("<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test
    public void testExecuteWithEmptyCustomMetaTag() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.getSeoSettings().setCustomMetaTagList(new ArrayList<String>() {{
            add("");
        }});

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!-- PAGE_HEADER -->");
        final RenderCustomMetaTags renderCustomMetaTags = new RenderCustomMetaTags(pageVersion);
        renderCustomMetaTags.execute(null, stringBuilder);

        Assert.assertEquals("<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIntializationWithNullPageVersion() {
        new RenderCopyrightMetaTag(null);
    }

}
