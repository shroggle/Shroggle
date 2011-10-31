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
package com.shroggle.logic.user.dashboard.keywordManager;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.Page;
import com.shroggle.entity.Site;
import com.shroggle.presentation.site.RenderedPageHtmlProviderMock;
import com.shroggle.util.ServiceLocator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class KeywordManagerTest {

    @Test
    public void testIsPresent() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site);
        final Page page = TestUtil.createPage(site);

        RenderedPageHtmlProviderMock renderedPageHtmlProviderMock = (RenderedPageHtmlProviderMock)
                ServiceLocator.getRenderedPageHtmlProvider();
        renderedPageHtmlProviderMock.addHtml(page, allHtml);

        KeywordManager keywordManager = new KeywordManager(page.getPageId());
        Assert.assertTrue(keywordManager.isPresent(SEOTerm.META_KEYWORDS));
        Assert.assertTrue(keywordManager.isPresent(SEOTerm.META_DESCRIPTION));
        Assert.assertTrue(keywordManager.isPresent(SEOTerm.PAGE_HEADER));
        Assert.assertTrue(keywordManager.isPresent(SEOTerm.PAGE_TITLE));
        Assert.assertTrue(keywordManager.isPresent(SEOTerm.PAGE_URL));
        Assert.assertTrue(keywordManager.isPresent(SEOTerm.PAGE_CONTENT));

        renderedPageHtmlProviderMock.addHtml(page, noHtml);
        keywordManager = new KeywordManager(page.getPageId());

        Assert.assertFalse(keywordManager.isPresent(SEOTerm.META_KEYWORDS));
        Assert.assertFalse(keywordManager.isPresent(SEOTerm.META_DESCRIPTION));
        Assert.assertFalse(keywordManager.isPresent(SEOTerm.PAGE_HEADER));
        Assert.assertFalse(keywordManager.isPresent(SEOTerm.PAGE_TITLE));
        Assert.assertTrue(keywordManager.isPresent(SEOTerm.PAGE_URL));
        Assert.assertTrue(keywordManager.isPresent(SEOTerm.PAGE_CONTENT));
    }

    @Test
    public void testGetContent() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        site.setSubDomain("site");
        TestUtil.createUserAndUserOnSiteRightAndLogin(site);
        final Page page = TestUtil.createPage(site);
        page.getPageSettings().setUrl("url");

        RenderedPageHtmlProviderMock renderedPageHtmlProviderMock = (RenderedPageHtmlProviderMock)
                ServiceLocator.getRenderedPageHtmlProvider();
        renderedPageHtmlProviderMock.addHtml(page, allHtml);

        KeywordManager keywordManager = new KeywordManager(page.getPageId());
        Assert.assertEquals("keyword1, keyword2", keywordManager.getContent(SEOTerm.META_KEYWORDS, false));
        Assert.assertEquals("desc", keywordManager.getContent(SEOTerm.META_DESCRIPTION, false));
        Assert.assertEquals("header", keywordManager.getContent(SEOTerm.PAGE_HEADER, false));
        Assert.assertEquals("title", keywordManager.getContent(SEOTerm.PAGE_TITLE, false));
        Assert.assertEquals("http://site.shroggle.com/url", keywordManager.getContent(SEOTerm.PAGE_URL, false));
        Assert.assertEquals("", keywordManager.getContent(SEOTerm.PAGE_CONTENT, false));

        renderedPageHtmlProviderMock.addHtml(page, noHtml);
        keywordManager = new KeywordManager(page.getPageId());

        Assert.assertEquals(keywordManager.NOT_FOUND, keywordManager.getContent(SEOTerm.META_KEYWORDS, false));
        Assert.assertEquals(keywordManager.NOT_FOUND, keywordManager.getContent(SEOTerm.META_DESCRIPTION, false));
        Assert.assertEquals(keywordManager.NOT_FOUND, keywordManager.getContent(SEOTerm.PAGE_HEADER, false));
        Assert.assertEquals(keywordManager.NOT_FOUND, keywordManager.getContent(SEOTerm.PAGE_TITLE, false));
        Assert.assertEquals("http://site.shroggle.com/url", keywordManager.getContent(SEOTerm.PAGE_URL, false));
        Assert.assertEquals("", keywordManager.getContent(SEOTerm.PAGE_CONTENT, false));
    }

    @Test
    public void testGetPageContentText() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        site.setSubDomain("site");
        TestUtil.createUserAndUserOnSiteRightAndLogin(site);
        final Page page = TestUtil.createPage(site);
        page.getPageSettings().setUrl("url");

        RenderedPageHtmlProviderMock renderedPageHtmlProviderMock = (RenderedPageHtmlProviderMock)
                ServiceLocator.getRenderedPageHtmlProvider();
        renderedPageHtmlProviderMock.addHtml(page, allHtml);

        KeywordManager keywordManager = new KeywordManager(page.getPageId());
        Assert.assertEquals("header asdada asdad", keywordManager.getPageContentText());

        renderedPageHtmlProviderMock.addHtml(page, noHtml);

        keywordManager = new KeywordManager(page.getPageId());
        Assert.assertEquals("", keywordManager.getPageContentText());
    }

    @Test
    public void testCalculateDensity() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        site.setSubDomain("site");
        TestUtil.createUserAndUserOnSiteRightAndLogin(site);
        final Page page = TestUtil.createPage(site);
        page.getPageSettings().setUrl("url");

        RenderedPageHtmlProviderMock renderedPageHtmlProviderMock = (RenderedPageHtmlProviderMock)
                ServiceLocator.getRenderedPageHtmlProvider();
        renderedPageHtmlProviderMock.addHtml(page, densityHtml);

        KeywordManager keywordManager = new KeywordManager(page.getPageId());
        Assert.assertEquals("100.0%", keywordManager.calculateKeywordDensity(SEOTerm.META_KEYWORDS));
        Assert.assertEquals("50.0%", keywordManager.calculateKeywordDensity(SEOTerm.META_DESCRIPTION));
        Assert.assertEquals("0.0%", keywordManager.calculateKeywordDensity(SEOTerm.PAGE_HEADER));
        Assert.assertEquals("0.0%", keywordManager.calculateKeywordDensity(SEOTerm.PAGE_TITLE));
        Assert.assertEquals("0.0%", keywordManager.calculateKeywordDensity(SEOTerm.PAGE_URL));
        Assert.assertEquals("33.33%", keywordManager.calculateKeywordDensity(SEOTerm.PAGE_CONTENT));
    }

    @Test
    public void testCalculateDensityWithRepetedKeywords() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        site.setSubDomain("site");
        TestUtil.createUserAndUserOnSiteRightAndLogin(site);
        final Page page = TestUtil.createPage(site);
        page.getPageSettings().setUrl("url");

        RenderedPageHtmlProviderMock renderedPageHtmlProviderMock = (RenderedPageHtmlProviderMock)
                ServiceLocator.getRenderedPageHtmlProvider();
        renderedPageHtmlProviderMock.addHtml(page, densityWithRepetedKeywordsHtml);

        KeywordManager keywordManager = new KeywordManager(page.getPageId());
        Assert.assertEquals("100.0%", keywordManager.calculateKeywordDensity(SEOTerm.META_KEYWORDS));
        Assert.assertEquals("50.0%", keywordManager.calculateKeywordDensity(SEOTerm.META_DESCRIPTION));
        Assert.assertEquals("0.0%", keywordManager.calculateKeywordDensity(SEOTerm.PAGE_HEADER));
        Assert.assertEquals("0.0%", keywordManager.calculateKeywordDensity(SEOTerm.PAGE_TITLE));
        Assert.assertEquals("0.0%", keywordManager.calculateKeywordDensity(SEOTerm.PAGE_URL));
        Assert.assertEquals("16.67%", keywordManager.calculateKeywordDensity(SEOTerm.PAGE_CONTENT));
    }

    @Test
    public void testCalculateDensityWithoutKeywords() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        site.setSubDomain("site");
        TestUtil.createUserAndUserOnSiteRightAndLogin(site);
        final Page page = TestUtil.createPage(site);
        page.getPageSettings().setUrl("url");

        RenderedPageHtmlProviderMock renderedPageHtmlProviderMock = (RenderedPageHtmlProviderMock)
                ServiceLocator.getRenderedPageHtmlProvider();
        renderedPageHtmlProviderMock.addHtml(page, densityWithoutKeywordsHtml);

        KeywordManager keywordManager = new KeywordManager(page.getPageId());
        Assert.assertEquals(keywordManager.UNDEFINED, keywordManager.calculateKeywordDensity(SEOTerm.META_KEYWORDS));
        Assert.assertEquals(keywordManager.UNDEFINED, keywordManager.calculateKeywordDensity(SEOTerm.META_DESCRIPTION));
        Assert.assertEquals(keywordManager.UNDEFINED, keywordManager.calculateKeywordDensity(SEOTerm.PAGE_HEADER));
        Assert.assertEquals(keywordManager.UNDEFINED, keywordManager.calculateKeywordDensity(SEOTerm.PAGE_TITLE));
        Assert.assertEquals(keywordManager.UNDEFINED, keywordManager.calculateKeywordDensity(SEOTerm.PAGE_URL));
        Assert.assertEquals(keywordManager.UNDEFINED, keywordManager.calculateKeywordDensity(SEOTerm.PAGE_CONTENT));
    }

    @Test
    public void testUniquenessForUniqueHtmls() {
        final Site site = TestUtil.createSite();
        site.setSubDomain("site");
        TestUtil.createUserAndUserOnSiteRightAndLogin(site);
        final Page page = TestUtil.createPage(site);
        page.getPageSettings().setUrl("url");

        final Page page2 = TestUtil.createPage(site);
        page2.getPageSettings().setUrl("url2");

        RenderedPageHtmlProviderMock renderedPageHtmlProviderMock = (RenderedPageHtmlProviderMock)
                ServiceLocator.getRenderedPageHtmlProvider();
        renderedPageHtmlProviderMock.addHtml(page, allHtml);
        renderedPageHtmlProviderMock.addHtml(page2, allHtml2);

        KeywordManager keywordManager = new KeywordManager(page.getPageId());
        Assert.assertTrue(keywordManager.isUnique(SEOTerm.META_KEYWORDS));
        Assert.assertTrue(keywordManager.isUnique(SEOTerm.META_DESCRIPTION));
        Assert.assertTrue(keywordManager.isUnique(SEOTerm.PAGE_HEADER));
        Assert.assertTrue(keywordManager.isUnique(SEOTerm.PAGE_TITLE));
        Assert.assertTrue(keywordManager.isUnique(SEOTerm.PAGE_URL));
        Assert.assertTrue(keywordManager.isUnique(SEOTerm.PAGE_CONTENT));
    }

    @Test
    public void testUniquenessForNotUniqueHtmls() {
        final Site site = TestUtil.createSite();
        site.setSubDomain("site");
        TestUtil.createUserAndUserOnSiteRightAndLogin(site);
        final Page page = TestUtil.createPage(site);
        page.getPageSettings().setUrl("url");

        final Page page2 = TestUtil.createPage(site);
        page2.getPageSettings().setUrl("url");

        RenderedPageHtmlProviderMock renderedPageHtmlProviderMock = (RenderedPageHtmlProviderMock)
                ServiceLocator.getRenderedPageHtmlProvider();
        renderedPageHtmlProviderMock.addHtml(page, allHtml);
        renderedPageHtmlProviderMock.addHtml(page2, allHtml);

        KeywordManager keywordManager = new KeywordManager(page.getPageId());
        Assert.assertFalse(keywordManager.isUnique(SEOTerm.META_KEYWORDS));
        Assert.assertFalse(keywordManager.isUnique(SEOTerm.META_DESCRIPTION));
        Assert.assertFalse(keywordManager.isUnique(SEOTerm.PAGE_HEADER));
        Assert.assertFalse(keywordManager.isUnique(SEOTerm.PAGE_TITLE));
        Assert.assertFalse(keywordManager.isUnique(SEOTerm.PAGE_URL));
        Assert.assertFalse(keywordManager.isUnique(SEOTerm.PAGE_CONTENT));
    }

    @Test
    public void testNormalize() throws Exception {
        String metaTagContent = null;
        Assert.assertEquals(null, KeywordManager.normalizeKeywords(metaTagContent));

        metaTagContent = "";
        Assert.assertEquals("", KeywordManager.normalizeKeywords(metaTagContent));

        metaTagContent = "META123";
        Assert.assertEquals("meta123", KeywordManager.normalizeKeywords(metaTagContent));

        metaTagContent = "meta123@#!@#";
        Assert.assertEquals("meta123", KeywordManager.normalizeKeywords(metaTagContent));

        metaTagContent = "meta123@#!@#   ,  qweqe";
        Assert.assertEquals("meta123, qweqe", KeywordManager.normalizeKeywords(metaTagContent));

        metaTagContent = "meta123@#!@#   ,  qweqe. qweqeeqe+qweqeqee,qweasd";
        Assert.assertEquals("meta123, qweqe, qweqeeqe, qweqeqee, qweasd", KeywordManager.normalizeKeywords(metaTagContent));
    }

    private String noHtml = "<html>" +
            "<head>" +
            "</head>" +
            "<body>" +
            "    <span> </span>" +
            "</body>" +
            "</html>";
    private String allHtml = "<html>" +
            "<head>" +
            "<meta name='keywords' content='keyword1, keyword2'/>" +
            "<meta name='description' content='desc'/>" +
            "<meta name='title' content='title'/>" +
            "</head>" +
            "<body>" +
            "<h1>header</h1>" +
            "    <span> asdada asdad </span>" +
            "</body>" +
            "</html>";
    private String allHtml2 = "<html>" +
            "<head>" +
            "<meta name='keywords' content='keyword3'/>" +
            "<meta name='description' content='desc2'/>" +
            "<meta name='title' content='title2'/>" +
            "</head>" +
            "<body>" +
            "<h1>header2</h1>" +
            "    <span> asdada asdad2 </span>" +
            "</body>" +
            "</html>";
    private String densityHtml = "<html>" +
            "<head>" +
            "<meta name='keywords' content='keyword1, keyword2'/>" +
            "<meta name='description' content='desc keyword1'/>" +
            "<meta name='title' content='title'/>" +
            "</head>" +
            "<body>" +
            "<h1>header</h1>" +
            "    <span>   dasdasds keyword2 keyword1 asdasd asdsad </span>" +
            "</body>" +
            "</html>";
    private String densityWithRepetedKeywordsHtml = "<html>" +
            "<head>" +
            "<meta name='keywords' content='keyword1 keyword1'/>" +
            "<meta name='description' content='desc keyword1'/>" +
            "<meta name='title' content='title'/>" +
            "</head>" +
            "<body>" +
            "<h1>header</h1>" +
            "    <span>   dasdasds keyword2 keyword1 asdasd asdsad </span>" +
            "</body>" +
            "</html>";
    private String densityWithoutKeywordsHtml = "<html>" +
            "<head>" +
            "<meta name='description' content='desc keyword1'/>" +
            "<meta name='title' content='title'/>" +
            "</head>" +
            "<body>" +
            "<h1>header</h1>" +
            "    <span>   dasdasds keyword2 keyword1 asdasd asdsad </span>" +
            "</body>" +
            "</html>";

}
