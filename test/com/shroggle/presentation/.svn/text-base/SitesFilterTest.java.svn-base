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

package com.shroggle.presentation;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.SiteByUrlGetterMock;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.Config;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.context.ContextStorage;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.filesystem.FileSystemMock;
import com.shroggle.util.persistance.Persistance;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import net.sourceforge.stripes.mock.MockHttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author Stasuk Artem
 */
@RunWith(TestRunnerWithMockServices.class)
public class SitesFilterTest {

    @Test
    public void doFilterWithUrlPrefixWithoutAny() throws IOException, ServletException {
        Site site = new Site();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);
        site.setSubDomain("mySite");
        persistance.putSite(site);
        site.setMenu(TestUtil.createMenu(site));
        siteByUrlGetterMock.setSite(site);

        SitesFilter sitesFilter = new SitesFilter();
        sitesFilter.init(null);
        final MockHttpServletRequest request = new MockHttpServletRequest("", "");
        final MockHttpServletResponse response = new MockHttpServletResponse();
        request.setServerName("mySite.localhost");
        request.setProtocol("http");
        sitesFilter.doFilter(request, response, null);
        sitesFilter.destroy();

        assertEquals(404, response.getStatus());
        TestUtil.assertIntAndBigInt(site.getSiteId(), contextStorage.get().getSiteId());
    }

    @Test
    public void doFilterWithUrlPrefixWithoutWork() throws IOException, ServletException {
        Site site = new Site();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);
        site.setSubDomain("mySite");
        persistance.putSite(site);
        siteByUrlGetterMock.setSite(site);

        Page page = TestUtil.createPage(site);

        final DraftMenu menu = TestUtil.createMenu(site);
        site.setMenu(menu);
        final MenuItem menuItem = TestUtil.createMenuItem(page.getPageId(), menu);
        menuItem.setParent(null);

        PageManager pageVersion = new PageManager(page);
        pageVersion.setUrl("myBlog");
        pageVersion.setName("myBlogAAA");

        SitesFilter sitesFilter = new SitesFilter();
        sitesFilter.init(null);
        final MockHttpServletRequest request = new MockHttpServletRequest("", "");
        final MockHttpServletResponse response = new MockHttpServletResponse();
        request.setServerName("mySite.localhost");
        request.setProtocol("http");
        sitesFilter.doFilter(request, response, null);
        sitesFilter.destroy();

        assertEquals(200, response.getStatus());
        TestUtil.assertIntAndBigInt(site.getSiteId(), contextStorage.get().getSiteId());
    }

    @Test
    public void doFilterWithUrlPrefixWithWork() throws IOException, ServletException {
        Site site = new Site();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);
        site.setSubDomain("mySite");
        persistance.putSite(site);
        siteByUrlGetterMock.setSite(site);

        Page page = TestUtil.createPage(site);

        final DraftMenu menu = TestUtil.createMenu(site);
        site.setMenu(menu);
        final MenuItem menuItem = TestUtil.createMenuItem(page.getPageId(), menu);
        menuItem.setParent(null);
        TestUtil.createWorkPageSettings(page.getPageSettings());
        PageManager pageVersion = new PageManager(page);
        pageVersion.getWorkPageSettings().setUrl("myBlog");
        pageVersion.getWorkPageSettings().setName("myBlogAAA");

        SitesFilter sitesFilter = new SitesFilter();
        sitesFilter.init(null);
        MockHttpServletRequest request = new MockHttpServletRequest("", "");
        request.setServerName("mySite.localhost");
        request.setProtocol("http");
        final MockFilterChain mockFilterChain = new MockFilterChain();
        sitesFilter.doFilter(request, null, mockFilterChain);

        assertFalse(mockFilterChain.isVisitDoFilter());
        TestUtil.assertIntAndBigInt(site.getSiteId(), contextStorage.get().getSiteId());

        sitesFilter.destroy();
    }

    @Test
    public void doFilterWithUrlPrefixStartsWithWww() throws IOException, ServletException {
        Site site = new Site();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);
        site.setSubDomain("mySite");
        persistance.putSite(site);
        siteByUrlGetterMock.setSite(site);

        Page page = TestUtil.createPage(site);

        final DraftMenu menu = TestUtil.createMenu(site);
        site.setMenu(menu);
        final MenuItem menuItem = TestUtil.createMenuItem(page.getPageId(), menu);
        menuItem.setParent(null);
        TestUtil.createWorkPageSettings(page.getPageSettings());
        PageManager pageVersion = new PageManager(page);
        pageVersion.getWorkPageSettings().setUrl("myBlog");
        pageVersion.getWorkPageSettings().setName("myBlogAAA");

        SitesFilter sitesFilter = new SitesFilter();
        sitesFilter.init(null);
        MockHttpServletRequest request = new MockHttpServletRequest("", "");
        request.setServerName("www.mySite.localhost");
        request.setProtocol("http");
        final MockFilterChain mockFilterChain = new MockFilterChain();
        sitesFilter.doFilter(request, null, mockFilterChain);

        assertFalse(mockFilterChain.isVisitDoFilter());
        TestUtil.assertIntAndBigInt(site.getSiteId(), contextStorage.get().getSiteId());

        sitesFilter.destroy();
    }

    @Test
    public void doFilterWithUrlPrefixAndNullUrlPrefix() throws IOException, ServletException {
        Site site = new Site();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);
        site.setSubDomain(null);
        persistance.putSite(site);

        Page page = TestUtil.createPage(site);
        TestUtil.createWorkPageSettings(page.getPageSettings());
        PageManager pageVersion = new PageManager(page);
        pageVersion.getWorkPageSettings().setUrl("myBlog");
        pageVersion.getWorkPageSettings().setName("myBlogAAA");

        SitesFilter sitesFilter = new SitesFilter();
        sitesFilter.init(null);
        MockHttpServletRequest request = new MockHttpServletRequest("", "");
        request.setServerName("mySite.localhost");
        request.setProtocol("http");
        final MockFilterChain mockFilterChain = new MockFilterChain();
        sitesFilter.doFilter(request, null, mockFilterChain);

        assertTrue(mockFilterChain.isVisitDoFilter());
        assertNull(contextStorage.get().getSiteId());

        sitesFilter.destroy();
    }

    @Test
    public void doFilterWithUrlPrefixAndNotFoundPageUrl() throws IOException, ServletException {
        Site site = new Site();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);
        site.setSubDomain("mySite");
        persistance.putSite(site);
        siteByUrlGetterMock.setSite(site);

        SitesFilter sitesFilter = new SitesFilter();
        sitesFilter.init(null);
        MockHttpServletRequest request = new MockHttpServletRequest("a", "b");
        final MockHttpServletResponse response = new MockHttpServletResponse();
        request.setServerName("mySite.localhost");
        request.setProtocol("http");
        request.setQueryString("myBlog");
        sitesFilter.doFilter(request, response, null);
        sitesFilter.destroy();

        assertEquals(404, response.getStatus());
        TestUtil.assertIntAndBigInt(site.getSiteId(), contextStorage.get().getSiteId());
    }

    @Test
    public void doFilterWithNotFoundUrlPrefix() throws IOException, ServletException {
        Site site = new Site();
        site.setSubDomain("mySite");
        persistance.putSite(site);

        SitesFilter sitesFilter = new SitesFilter();
        sitesFilter.init(null);
        MockHttpServletRequest request = new MockHttpServletRequest("a", "b");
        request.setServerName("mySite1.localhost");
        request.setProtocol("http");
        sitesFilter.doFilter(request, null, new FilterChain() {

            public void doFilter(
                    ServletRequest request, ServletResponse response)
                    throws IOException, ServletException {

            }

        });

        sitesFilter.destroy();
        assertNull(contextStorage.get().getSiteId());
    }

    @Test
    public void doFilterWithSimpleResourcesUrl() throws IOException, ServletException {
        FileSystemMock fileSystemMock = (FileSystemMock) fileSystem;
        fileSystemMock.addSitesResourcesUrl("myBlog");

        Site site = new Site();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);
        site.setSubDomain("mySite");
        persistance.putSite(site);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);
        pageVersion.setUrl("myBlog");
        pageVersion.setName("myBlogAAA");

        SitesFilter sitesFilter = new SitesFilter();
        sitesFilter.init(null);
        MockHttpServletRequest request = new MockHttpServletRequest("myBlog", "");
        request.setServerName("mySite.localhost");
        request.setProtocol("http");
        sitesFilter.doFilter(request, null, new FilterChain() {

            public void doFilter(
                    ServletRequest request, ServletResponse response)
                    throws IOException, ServletException {

            }

        });

        assertNull(request.getForwardUrl());

        sitesFilter.destroy();
    }

    @Test
    public void doFilterWithResourcesUrl() throws IOException, ServletException {
        FileSystemMock fileSystemMock = (FileSystemMock) fileSystem;
        fileSystemMock.addSitesResourcesUrl("test/default/image.gif");

        Site site = new Site();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);
        site.setSubDomain("mySite");
        persistance.putSite(site);


        SitesFilter sitesFilter = new SitesFilter();
        sitesFilter.init(null);
        MockHttpServletRequest request = new MockHttpServletRequest("test/default/image.gif", "");
        request.setServerName("mySite.localhost");
        request.setProtocol("http");
        sitesFilter.doFilter(request, null, new FilterChain() {

            public void doFilter(
                    ServletRequest request, ServletResponse response)
                    throws IOException, ServletException {

            }

        });

        assertNull(request.getForwardUrl());

        sitesFilter.destroy();
    }

    @Test
    public void doFilterWithUrlPrefixAndPageUrlForWork() throws Exception {
        Site site = new Site();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);
        site.setSubDomain("mySite");
        persistance.putSite(site);
        siteByUrlGetterMock.setSite(site);

        Page page = TestUtil.createPage(site);

        TestUtil.createWorkPageSettings(page.getPageSettings());
        PageManager pageVersion = new PageManager(page);
        pageVersion.getWorkPageSettings().setUrl("myblog");
        pageVersion.getWorkPageSettings().setName("myBlogAAA");

        SitesFilter sitesFilter = new SitesFilter();
        sitesFilter.init(null);
        MockHttpServletRequest request = new MockHttpServletRequest("myBlog", "");
        request.setServerName("mySite.localhost");
        request.setProtocol("http");
        sitesFilter.doFilter(request, null, null);

        assertEquals(
                "/site/showPageVersion.action?pageId=" + page.getPageId() + "&siteShowOption=" + SiteShowOption.getWorkOption(),
                request.getForwardUrl());

        sitesFilter.destroy();
    }

    @Test
    public void doFilterWithUrlPrefixAndPageUrlForWork_withUpperCaseUrl() throws Exception {
        Site site = new Site();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);
        site.setSubDomain("mySite");
        persistance.putSite(site);
        siteByUrlGetterMock.setSite(site);

        Page page = TestUtil.createPage(site);

        TestUtil.createWorkPageSettings(page.getPageSettings());
        PageManager pageVersion = new PageManager(page);
        pageVersion.getWorkPageSettings().setUrl("MYBLOG");

        SitesFilter sitesFilter = new SitesFilter();
        sitesFilter.init(null);
        MockHttpServletRequest request = new MockHttpServletRequest("myBlog", "");
        request.setServerName("mySite.localhost");
        request.setProtocol("http");
        sitesFilter.doFilter(request, null, null);

        assertEquals(
                "/site/showPageVersion.action?pageId=" + page.getPageId() + "&siteShowOption=" + SiteShowOption.getWorkOption(),
                request.getForwardUrl());

        sitesFilter.destroy();
    }

    @Test
    public void doFilterWithUrlPrefixAndPageUrlForWorkInOtherCase() throws Exception {
        Site site = new Site();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);
        site.setSubDomain("mySite");
        persistance.putSite(site);
        siteByUrlGetterMock.setSite(site);

        Page page = TestUtil.createPage(site);

        TestUtil.createWorkPageSettings(page.getPageSettings());
        PageManager pageVersion = new PageManager(page);
        pageVersion.getWorkPageSettings().setUrl("myblog");
        pageVersion.getWorkPageSettings().setName("myBlogAAA");

        SitesFilter sitesFilter = new SitesFilter();
        sitesFilter.init(null);
        MockHttpServletRequest request = new MockHttpServletRequest("myBLog", "");
        request.setServerName("mySite.localhost");
        request.setProtocol("http");
        sitesFilter.doFilter(request, null, null);

        assertEquals(
                "/site/showPageVersion.action?pageId=" + page.getPageId() + "&siteShowOption=" + SiteShowOption.getWorkOption(),
                request.getForwardUrl());

        sitesFilter.destroy();
    }

    @Test
    public void doFilterWithUrlPrefixAndPageUrlNotLastWork() throws Exception {
        Site site = new Site();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);
        site.setSubDomain("mySite");
        persistance.putSite(site);
        siteByUrlGetterMock.setSite(site);

        Page page = TestUtil.createPage(site);

        TestUtil.createWorkPageSettings(page.getPageSettings());
        PageManager newPageVersion = new PageManager(page);
        newPageVersion.getWorkPageSettings().setUrl("myBlog1");
        newPageVersion.getWorkPageSettings().setCreationDate(new Date(System.currentTimeMillis() * 2));
        newPageVersion.getWorkPageSettings().setName("myBlogAAA1");

        SitesFilter sitesFilter = new SitesFilter();
        sitesFilter.init(null);
        MockHttpServletRequest request = new MockHttpServletRequest("myBlog", "");
        final MockHttpServletResponse response = new MockHttpServletResponse();
        request.setServerName("mySite.localhost");
        request.setProtocol("http");
        sitesFilter.doFilter(request, response, null);
        sitesFilter.destroy();

        assertEquals(404, response.getStatus());
    }

    @Test
    public void doFilterWithUrlPrefixAndPageUrlForDraft() throws Exception {
        Site site = new Site();
        site.setSubDomain("mySite");
        persistance.putSite(site);
        siteByUrlGetterMock.setSite(site);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);
        pageVersion.setUrl("myblog");
        pageVersion.setName("myBlogAAA");

        SitesFilter sitesFilter = new SitesFilter();
        sitesFilter.init(null);
        MockHttpServletRequest request = new MockHttpServletRequest("myBlog", "");
        request.setServerName("mySite.localhost");
        request.setProtocol("http");
        sitesFilter.doFilter(request, null, null);

        assertEquals(
                "/site/siteUnderConstruction.action?siteUrl=mySite&siteShowOption=" + SiteShowOption.getWorkOption(),
                request.getForwardUrl());

        sitesFilter.destroy();
    }


    @Test
    public void doFilterWithUrlPrefixAndPageUrlEndOnSlash() throws Exception {
        Site site = new Site();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);
        site.setSubDomain("mySite");
        persistance.putSite(site);
        siteByUrlGetterMock.setSite(site);

        Page page = TestUtil.createPage(site);

        TestUtil.createWorkPageSettings(page.getPageSettings());
        PageManager pageVersion = new PageManager(page);
        pageVersion.getWorkPageSettings().setUrl("myblog");
        pageVersion.getWorkPageSettings().setName("myBlogAAA");

        SitesFilter sitesFilter = new SitesFilter();
        sitesFilter.init(null);
        MockHttpServletRequest request = new MockHttpServletRequest("myBlog/", "");
        request.setServerName("mySite.localhost");
        request.setProtocol("http");
        sitesFilter.doFilter(request, null, null);

        assertEquals(
                "Site filer can't find page when it name ends on slash, as example: a.com/test/",
                "/site/showPageVersion.action?pageId=" + page.getPageId() + "&siteShowOption=" + SiteShowOption.getWorkOption(),
                request.getForwardUrl());

        sitesFilter.destroy();
    }

    /*@Test
    public void doFilterWithAliaseUrl() throws Exception {
        Site site = new Site();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);
        site.setSubDomain("mySite");
        site.setOwnDomainName("ff.com");
        persistance.putSite(site);

        Page page = TestUtil.createPage(site);

        final Menu menu = TestUtil.createMenu(site);
        site.setMenu(menu);
        final NewMenuItem menuItem = TestUtil.createMenuItem(page.getPageId(), menu);
        menuItem.setParent(null);

        PageManager pageVersion = new PageManager();
        pageVersion.setType(PageVersionType.WORK);
        page.addVersion(pageVersion);
        pageVersion.setUrl("myBlog");
        pageVersion.setName("myBlogAAA");

        SitesFilter sitesFilter = new SitesFilter();
        sitesFilter.init(null);
        MockHttpServletRequest request = new MockHttpServletRequest("", "");
        request.setServerName("ff.com");
        request.setProtocol("http");
        MockHttpServletResponse response = new MockHttpServletResponse();
        sitesFilter.doFilter(request, response, null);

        assertEquals(
                "Site filer can't find page when it name ends on slash, as example: a.com/test/",
                "/site/showPageVersion.action?pageId=" + page.getPageId() + "&siteShowOption=" + SiteShowOption.getWorkOption(),
                request.getForwardUrl());

        sitesFilter.destroy();
    }*/

    @Test
    public void doFilterWithAliaseUrlStartsWithWww() throws Exception {
        Site site = new Site();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);
        site.setSubDomain("mySite");
        site.setCustomUrl("ff.com");
        persistance.putSite(site);
        siteByUrlGetterMock.setSite(site);

        Page page = TestUtil.createPage(site);

        final DraftMenu menu = TestUtil.createMenu(site);
        site.setMenu(menu);
        final MenuItem menuItem = TestUtil.createMenuItem(page.getPageId(), menu);
        menuItem.setParent(null);

        TestUtil.createWorkPageSettings(page.getPageSettings());
        PageManager pageVersion = new PageManager(page);
        pageVersion.getWorkPageSettings().setUrl("myBlog");
        pageVersion.getWorkPageSettings().setName("myBlogAAA");

        SitesFilter sitesFilter = new SitesFilter();
        sitesFilter.init(null);
        MockHttpServletRequest request = new MockHttpServletRequest("", "");
        request.setServerName("www.ff.com");
        request.setProtocol("http");
        sitesFilter.doFilter(request, null, null);

        assertEquals(
                "Site filer can't find page when it name ends on slash, as example: a.com/test/",
                "/site/showPageVersion.action?pageId=" + page.getPageId() + "&siteShowOption=" + SiteShowOption.getWorkOption(),
                request.getForwardUrl());

        sitesFilter.destroy();
    }

    @Test
    public void doFilterWithNotActiveSite() throws Exception {
        final Config config = ServiceLocator.getConfigStorage().get();
        config.getBillingInfoProperties().setCheckSitesBillingInfo(true);
        config.getBillingInfoProperties().setMustBePaidBeforePublishing(true);

        Site site = new Site();
        new SiteManager(site).setSiteStatus(SiteStatus.SUSPENDED);
        site.setSubDomain("mySite");
        site.setCustomUrl("ff.com");
        persistance.putSite(site);
        siteByUrlGetterMock.setSite(site);

        Page page = TestUtil.createPage(site);

        TestUtil.createWorkPageSettings(page.getPageSettings());
        PageManager pageVersion = new PageManager(page);
        pageVersion.getWorkPageSettings().setUrl("myBlog");
        pageVersion.getWorkPageSettings().setName("myBlogAAA");

        SitesFilter sitesFilter = new SitesFilter();
        sitesFilter.init(null);
        MockHttpServletRequest request = new MockHttpServletRequest("", "");
        request.setServerName("mySite.localhost");
        request.setProtocol("http");
        sitesFilter.doFilter(request, null, null);

        assertEquals(
                "Site filter show not active site... Brrrr! Users are happy!",
                "/site/siteUnderConstruction.action?siteUrl=mySite&siteShowOption=" + SiteShowOption.getWorkOption(),
                request.getForwardUrl());

        sitesFilter.destroy();
    }

    @Test
    public void doFilterWithUrlThatInNotUserSiteUrls() throws Exception {
        configStorage.get().getNotUserSiteUrls().add("www.mySite.localhost");

        SitesFilter sitesFilter = new SitesFilter();
        sitesFilter.init(null);
        MockHttpServletRequest request = new MockHttpServletRequest("myBlog/", "");
        request.setServerName("www.mySite.localhost");
        request.setProtocol("http");
        final MockFilterChain mockFilterChain = new MockFilterChain();
        sitesFilter.doFilter(request, null, mockFilterChain);
        sitesFilter.destroy();

        assertTrue("Filter don't known that this url not user!", mockFilterChain.isVisitDoFilter());
    }

    @Test
    public void doFilterWithUrlThatInNotUserSiteUrlsAndWithExistSiteOnThisUrl() throws Exception {
        Site site = new Site();
        site.setSubDomain("mySite");
        persistance.putSite(site);

        Page page = TestUtil.createPage(site);

        TestUtil.createWorkPageSettings(page.getPageSettings());
        PageManager pageVersion = new PageManager(page);
        pageVersion.getWorkPageSettings().setUrl("myBlog");
        pageVersion.getWorkPageSettings().setName("myBlogAAA");

        configStorage.get().getNotUserSiteUrls().add("www.mySite.localhost");

        SitesFilter sitesFilter = new SitesFilter();
        sitesFilter.init(null);
        MockHttpServletRequest request = new MockHttpServletRequest("myBlog/", "");
        request.setServerName("www.mySite.localhost");
        request.setProtocol("http");
        final MockFilterChain mockFilterChain = new MockFilterChain();
        sitesFilter.doFilter(request, null, mockFilterChain);
        sitesFilter.destroy();

        assertTrue("Filter don't known that this url not user!", mockFilterChain.isVisitDoFilter());
    }

    @Test
    public void doFilterWithUrlThatInNotUserSiteUrls_withouWww() throws Exception {
        configStorage.get().getNotUserSiteUrls().add("mySite.shroggle");

        SitesFilter sitesFilter = new SitesFilter();
        sitesFilter.init(null);
        MockHttpServletRequest request = new MockHttpServletRequest("/myBlog", "");
        request.setServerName("mySite.shroggle");
        request.setProtocol("http");
        request.setServerPort(8080);
        request.setQueryString("a=1&b=2");
        final MockFilterChain mockFilterChain = new MockFilterChain();

        MockHttpServletResponse response = new MockHttpServletResponse();
        sitesFilter.doFilter(request, response, mockFilterChain);
        sitesFilter.destroy();

        assertFalse("Filter don't known that this url not user!", mockFilterChain.isVisitDoFilter());

        assertEquals(HttpServletResponse.SC_MOVED_PERMANENTLY, response.getStatus());
        assertEquals("close", response.getHeaderMap().get("Connection").get(0));
        assertEquals("http://www.mySite.shroggle:8080/myBlog?a=1&b=2", response.getHeaderMap().get("Location").get(0));
    }

    @Test
    public void doFilterWithUrlThatNotInUserSiteUrls_withouWww() throws Exception {
        //configStorage.get().getNotUserSiteUrls().add("mySite.shroggle");

        SitesFilter sitesFilter = new SitesFilter();
        sitesFilter.init(null);
        MockHttpServletRequest request = new MockHttpServletRequest("/myBlog", "");
        request.setServerName("mySite.shroggle");
        request.setProtocol("http");
        request.setServerPort(8080);
        request.setQueryString("a=1&b=2");
        final MockFilterChain mockFilterChain = new MockFilterChain();

        MockHttpServletResponse response = new MockHttpServletResponse();
        sitesFilter.doFilter(request, response, mockFilterChain);
        sitesFilter.destroy();

        assertFalse("Filter don't known that this url not user!", mockFilterChain.isVisitDoFilter());

        assertEquals(HttpServletResponse.SC_MOVED_PERMANENTLY, response.getStatus());
        assertEquals("close", response.getHeaderMap().get("Connection").get(0));
        assertEquals("http://www.mySite.shroggle:8080/myBlog?a=1&b=2", response.getHeaderMap().get("Location").get(0));
    }

    @Test
    public void doFilterWithIP() throws Exception {
        SitesFilter sitesFilter = new SitesFilter();
        sitesFilter.init(null);
        MockHttpServletRequest request = new MockHttpServletRequest("/", "");
        request.setServerName("127.0.0.5");
        request.setProtocol("http");
        request.setServerPort(8080);
        final MockFilterChain mockFilterChain = new MockFilterChain();

        MockHttpServletResponse response = new MockHttpServletResponse();
        sitesFilter.doFilter(request, response, mockFilterChain);
        sitesFilter.destroy();

        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
    }

    @Before
    public void startUp() {
        ServiceLocator.getConfigStorage().get().setUserSitesUrl("localhost");
    }

    private final SiteByUrlGetterMock siteByUrlGetterMock = (SiteByUrlGetterMock) ServiceLocator.getSiteByUrlGetter();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
    private final FileSystem fileSystem = ServiceLocator.getFileSystem();
    private final ContextStorage contextStorage = ServiceLocator.getContextStorage();

}
