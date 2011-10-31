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
import com.shroggle.entity.CodePlacement;
import com.shroggle.entity.Page;
import com.shroggle.entity.SEOHtmlCode;
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
public class RenderCustomHTMLCodeTest {

    @Test
    public void testExecuteWithHtmlCodeInPage() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.getSeoSettings().setHtmlCodeList(new ArrayList<SEOHtmlCode>(){{
            final SEOHtmlCode seoHtmlCodeAtBeginning = new SEOHtmlCode();
            seoHtmlCodeAtBeginning.setName("sample_code");
            seoHtmlCodeAtBeginning.setCode("<script> alert('ti loh! :)') </script>");
            seoHtmlCodeAtBeginning.setCodePlacement(CodePlacement.BEGINNING);
            add(seoHtmlCodeAtBeginning);

            final SEOHtmlCode seoHtmlCodeAtEnd = new SEOHtmlCode();
            seoHtmlCodeAtEnd.setName("sample_code");
            seoHtmlCodeAtEnd.setCode("<script> alert('vnature loh! :)') </script>");
            seoHtmlCodeAtEnd.setCodePlacement(CodePlacement.END);
            add(seoHtmlCodeAtEnd);
        }});

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(
                "<body>" +
                        "<div>" +
                        "</div>" +
                        "</body>"
        );
        final RenderCustomHTMLCode renderCustomHTMLCode = new RenderCustomHTMLCode(pageVersion);
        renderCustomHTMLCode.execute(null, stringBuilder);

        Assert.assertEquals("<body><script> alert('ti loh! :)') </script><div></div>" +
                "<script> alert('vnature loh! :)') </script></body>", stringBuilder.toString());
    }

    @Test
    public void testExecuteWithHtmlCodeInSite() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        site.getSeoSettings().setHtmlCodeList(new ArrayList<SEOHtmlCode>(){{
            final SEOHtmlCode seoHtmlCodeAtBeginning = new SEOHtmlCode();
            seoHtmlCodeAtBeginning.setName("sample_code_from_site");
            seoHtmlCodeAtBeginning.setCode("<script> alert('site_code1') </script>");
            seoHtmlCodeAtBeginning.setCodePlacement(CodePlacement.BEGINNING);
            add(seoHtmlCodeAtBeginning);

            final SEOHtmlCode seoHtmlCodeAtEnd = new SEOHtmlCode();
            seoHtmlCodeAtEnd.setName("sample_code_from_site2");
            seoHtmlCodeAtEnd.setCode("<script> alert('site_code2') </script>");
            seoHtmlCodeAtEnd.setCodePlacement(CodePlacement.END);
            add(seoHtmlCodeAtEnd);
        }});

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(
                "<body>" +
                        "<div>" +
                        "</div>" +
                        "</body>"
        );
        final RenderCustomHTMLCode renderCustomHTMLCode = new RenderCustomHTMLCode(pageVersion);
        renderCustomHTMLCode.execute(null, stringBuilder);

        Assert.assertEquals("<body><script> alert('site_code1') </script><div></div>" +
                "<script> alert('site_code2') </script></body>", stringBuilder.toString());
    }

    @Test
    public void testExecuteWithHtmlCodeInBothSiteAndPage() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.getSeoSettings().setHtmlCodeList(new ArrayList<SEOHtmlCode>(){{
            final SEOHtmlCode seoHtmlCodeAtBeginning = new SEOHtmlCode();
            seoHtmlCodeAtBeginning.setName("sample_code_from_site");
            seoHtmlCodeAtBeginning.setCode("<script> alert('page_code') </script>");
            seoHtmlCodeAtBeginning.setCodePlacement(CodePlacement.BEGINNING);
            add(seoHtmlCodeAtBeginning);
        }});
        site.getSeoSettings().setHtmlCodeList(new ArrayList<SEOHtmlCode>(){{
            final SEOHtmlCode seoHtmlCodeAtEnd = new SEOHtmlCode();
            seoHtmlCodeAtEnd.setName("sample_code_from_site2");
            seoHtmlCodeAtEnd.setCode("<script> alert('site_code') </script>");
            seoHtmlCodeAtEnd.setCodePlacement(CodePlacement.END);
            add(seoHtmlCodeAtEnd);
        }});

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(
                "<body>" +
                        "<div>" +
                        "</div>" +
                        "</body>"
        );
        final RenderCustomHTMLCode renderCustomHTMLCode = new RenderCustomHTMLCode(pageVersion);
        renderCustomHTMLCode.execute(null, stringBuilder);

        Assert.assertEquals("<body><script> alert('page_code') </script><div></div>" +
                "<script> alert('site_code') </script></body>", stringBuilder.toString());
    }
    
    @Test
    public void testExecuteWithTwoScriptsAtBeginning() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.getSeoSettings().setHtmlCodeList(new ArrayList<SEOHtmlCode>(){{
            final SEOHtmlCode seoHtmlCodeAtBeginning1 = new SEOHtmlCode();
            seoHtmlCodeAtBeginning1.setName("sample_code");
            seoHtmlCodeAtBeginning1.setCode("<script> alert('ti loh! :)') </script>");
            seoHtmlCodeAtBeginning1.setCodePlacement(CodePlacement.BEGINNING);
            add(seoHtmlCodeAtBeginning1);

            final SEOHtmlCode seoHtmlCodeAtBeginning2 = new SEOHtmlCode();
            seoHtmlCodeAtBeginning2.setName("sample_code");
            seoHtmlCodeAtBeginning2.setCode("<script> alert('vnature loh! :)') </script>");
            seoHtmlCodeAtBeginning2.setCodePlacement(CodePlacement.BEGINNING);
            add(seoHtmlCodeAtBeginning2);
        }});

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(
                "<body>" +
                        "<div>" +
                        "</div>" +
                        "</body>"
        );
        final RenderCustomHTMLCode renderCustomHTMLCode = new RenderCustomHTMLCode(pageVersion);
        renderCustomHTMLCode.execute(null, stringBuilder);

        Assert.assertEquals("<body><script> alert('vnature loh! :)') </script><script> alert('ti loh! :)') </script>" +
                "<div></div></body>", stringBuilder.toString());
    }

    @Test
    public void testExecuteWithTwoScriptsAtTheEnd() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.getSeoSettings().setHtmlCodeList(new ArrayList<SEOHtmlCode>(){{
            final SEOHtmlCode seoHtmlCodeAtEnd1 = new SEOHtmlCode();
            seoHtmlCodeAtEnd1.setName("sample_code");
            seoHtmlCodeAtEnd1.setCode("<script> alert('ti loh! :)') </script>");
            seoHtmlCodeAtEnd1.setCodePlacement(CodePlacement.END);
            add(seoHtmlCodeAtEnd1);

            final SEOHtmlCode seoHtmlCodeAtEnd2 = new SEOHtmlCode();
            seoHtmlCodeAtEnd2.setName("sample_code");
            seoHtmlCodeAtEnd2.setCode("<script> alert('vnature loh! :)') </script>");
            seoHtmlCodeAtEnd2.setCodePlacement(CodePlacement.END);
            add(seoHtmlCodeAtEnd2);
        }});

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(
                "<body>" +
                        "<div>" +
                        "</div>" +
                        "</body>"
        );
        final RenderCustomHTMLCode renderCustomHTMLCode = new RenderCustomHTMLCode(pageVersion);
        renderCustomHTMLCode.execute(null, stringBuilder);

        Assert.assertEquals("<body><div></div><script> alert('ti loh! :)') </script>" +
                "<script> alert('vnature loh! :)') </script></body>", stringBuilder.toString());
    }
    
    @Test
    public void testExecuteWithEmptyCustomCode() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.getSeoSettings().setHtmlCodeList(new ArrayList<SEOHtmlCode>(){{
            final SEOHtmlCode seoHtmlCode = new SEOHtmlCode();
            seoHtmlCode.setName("sample_code");
            seoHtmlCode.setCode("");
            seoHtmlCode.setCodePlacement(CodePlacement.BEGINNING);
            add(seoHtmlCode);
        }});

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(
                "<body>" +
                        "<div>" +
                        "</div>" +
                        "</body>"
        );
        final RenderCustomHTMLCode renderCustomHTMLCode = new RenderCustomHTMLCode(pageVersion);
        renderCustomHTMLCode.execute(null, stringBuilder);

        Assert.assertEquals("<body><div></div></body>", stringBuilder.toString());
    }

    @Test
    public void testExecuteWithNullCustomCode() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.getSeoSettings().setHtmlCodeList(new ArrayList<SEOHtmlCode>(){{
            final SEOHtmlCode seoHtmlCode = new SEOHtmlCode();
            seoHtmlCode.setName("sample_code");
            seoHtmlCode.setCode(null);
            seoHtmlCode.setCodePlacement(CodePlacement.BEGINNING);
            add(seoHtmlCode);
        }});

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(
                "<body>" +
                        "<div>" +
                        "</div>" +
                        "</body>"
        );
        final RenderCustomHTMLCode renderCustomHTMLCode = new RenderCustomHTMLCode(pageVersion);
        renderCustomHTMLCode.execute(null, stringBuilder);

        Assert.assertEquals("<body><div></div></body>", stringBuilder.toString());
    }

    @Test
    public void testExecuteWithoutCustomCode() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.getSeoSettings().setHtmlCodeList(null);

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(
                "<body>" +
                        "<div>" +
                        "</div>" +
                        "</body>"
        );
        final RenderCustomHTMLCode renderCustomHTMLCode = new RenderCustomHTMLCode(pageVersion);
        renderCustomHTMLCode.execute(null, stringBuilder);

        Assert.assertEquals("<body><div></div></body>", stringBuilder.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInitializeWithoutPageVersions() {
        new RenderCustomHTMLCode(null);
    }

}
