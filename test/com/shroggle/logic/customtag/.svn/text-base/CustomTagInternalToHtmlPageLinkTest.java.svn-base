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
package com.shroggle.logic.customtag;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.DraftPageSettings;
import com.shroggle.entity.Page;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.entity.WorkPageSettings;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class CustomTagInternalToHtmlPageLinkTest {

    @Test
    public void executeForNotA() {
        final HtmlTagMock htmlTagMock = new HtmlTagMock();
        htmlTagMock.setName("h1");
        htmlTagMock.setBody("111");

        customTag.execute(htmlTagMock, SiteShowOption.INSIDE_APP);

        Assert.assertEquals("111", htmlTagMock.getBody());
        Assert.assertEquals(0, htmlTagMock.getAttributes().size());
    }

    @Test
    public void executeForNotFoundPage() {
        final HtmlTagMock htmlTagMock = new HtmlTagMock();
        htmlTagMock.setName("a");
        htmlTagMock.setAttribute("pageId", "" + 12);
        htmlTagMock.setBody("111");

        customTag.execute(htmlTagMock, SiteShowOption.INSIDE_APP);

        Assert.assertEquals("111", htmlTagMock.getBody());
        Assert.assertEquals(null, htmlTagMock.getAttribute("href"));
    }

    @Test
    public void executeForNotPageId() {
        final HtmlTagMock htmlTagMock = new HtmlTagMock();
        htmlTagMock.setName("a");
        htmlTagMock.setAttribute("pageId", "A");
        htmlTagMock.setBody("111");

        customTag.execute(htmlTagMock, SiteShowOption.INSIDE_APP);

        Assert.assertEquals("111", htmlTagMock.getBody());
        Assert.assertEquals(null, htmlTagMock.getAttribute("href"));
    }

    @Test
    public void executeWithInsideAppForDraft() {
        final Page page = TestUtil.createPageAndSite();
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(page);
        draftPageSettings.setName("aag");
        draftPageSettings.setUrl("agg");

        final HtmlTagMock htmlTagMock = new HtmlTagMock();
        htmlTagMock.setName("a");
        htmlTagMock.setAttribute("pageId", "" + page.getPageId());
        htmlTagMock.setBody("111");

        customTag.execute(htmlTagMock, SiteShowOption.INSIDE_APP);

        Assert.assertEquals("111", htmlTagMock.getBody());
        Assert.assertEquals("http://null.shroggle.com/agg", htmlTagMock.getAttribute("href"));
    }

    @Test
    public void executeWithInsideAppForWork() {
        final Page page = TestUtil.createPageAndSite();
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(page);
        final WorkPageSettings workPageSettings = TestUtil.createWorkPageSettings(draftPageSettings);
        workPageSettings.setName("aag");
        workPageSettings.setUrl("agg");

        final HtmlTagMock htmlTagMock = new HtmlTagMock();
        htmlTagMock.setName("a");
        htmlTagMock.setAttribute("pageId", "" + page.getPageId());
        htmlTagMock.setBody("111");

        customTag.execute(htmlTagMock, SiteShowOption.INSIDE_APP);

        Assert.assertEquals("111", htmlTagMock.getBody());
        Assert.assertEquals("http://null.shroggle.com/null", htmlTagMock.getAttribute("href"));
    }

    @Test
    public void executeWithOutsideAppForWork() {
        final Page page = TestUtil.createPageAndSite();
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(page);
        final WorkPageSettings workPageSettings = TestUtil.createWorkPageSettings(draftPageSettings);
        workPageSettings.setName("aag");
        workPageSettings.setUrl("agg");

        final HtmlTagMock htmlTagMock = new HtmlTagMock();
        htmlTagMock.setName("a");
        htmlTagMock.setAttribute("pageId", "" + page.getPageId());
        htmlTagMock.setBody("111");

        customTag.execute(htmlTagMock, SiteShowOption.OUTSIDE_APP);

        Assert.assertEquals("111", htmlTagMock.getBody());
        Assert.assertEquals("http://null.shroggle.com/agg", htmlTagMock.getAttribute("href"));
    }

    private final CustomTag customTag = new CustomTagInternalToHtmlPageLink();

}
