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
import com.shroggle.entity.PageVersionType;
import com.shroggle.entity.Site;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.ConfigStorage;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import net.sourceforge.stripes.mock.MockHttpServletResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class PagesFilterTest {

    @Before
    public void before() throws ServletException {
        pagesFilter.init(null);
        ServiceLocator.getConfigStorage().get().setUserSitesUrl("web-deva.com");
    }

    @After
    public void after() throws ServletException {
        pagesFilter.destroy();
    }

    @Test
    public void doFilterNotFound() throws IOException, ServletException {
        final MockHttpServletRequest request = new MockHttpServletRequest("", "");
        final MockHttpServletResponse response = new MockHttpServletResponse();
        request.setServerName("mySite.localhost");
        request.setProtocol("http");
        final MockFilterChain mockFilterChain = new MockFilterChain();
        pagesFilter.doFilter(request, response, mockFilterChain);

        Assert.assertTrue(mockFilterChain.isVisitDoFilter());
    }

    @Test
    public void doFilter() throws IOException, ServletException {

        final Site site = TestUtil.createSite();
        site.setSubDomain("remo");
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site, PageVersionType.WORK);
        pageVersion.getWorkPageSettings().setOwnDomainName("demo.test.com");
        pageVersion.getWorkPageSettings().setUrl("prosto");

        final MockHttpServletRequest request = new MockHttpServletRequest("", "");
        final MockHttpServletResponse response = new MockHttpServletResponse();
        request.setServerName("demo.test.com");
        request.setProtocol("http");
        pagesFilter.doFilter(request, response, null);

        Assert.assertEquals("http://remo.web-deva.com/prosto", response.getRedirectUrl());
    }

    @Test
    public void doFilterWithOtherCases() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        site.setSubDomain("remo");
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site, PageVersionType.WORK);
        pageVersion.getWorkPageSettings().setOwnDomainName("demo.test.com");
        pageVersion.getWorkPageSettings().setUrl("prosto");

        final MockHttpServletRequest request = new MockHttpServletRequest("", "");
        final MockHttpServletResponse response = new MockHttpServletResponse();
        request.setServerName("dEmO.test.com");
        request.setProtocol("http");
        pagesFilter.doFilter(request, response, null);

        Assert.assertEquals("http://remo.web-deva.com/prosto", response.getRedirectUrl());
    }

    @Test
    public void doFilterWithWww() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        site.setSubDomain("remo");
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site, PageVersionType.WORK);
        pageVersion.getWorkPageSettings().setOwnDomainName("demo.test.com");
        pageVersion.getWorkPageSettings().setUrl("prosto");

        final MockHttpServletRequest request = new MockHttpServletRequest("", "");
        final MockHttpServletResponse response = new MockHttpServletResponse();
        request.setServerName("www.dEmO.test.com");
        request.setProtocol("http");
        pagesFilter.doFilter(request, response, null);

        Assert.assertEquals("http://remo.web-deva.com/prosto", response.getRedirectUrl());
    }

    @Test
    public void doFilterInNotUserUrls() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        site.setSubDomain("remo");
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site, PageVersionType.WORK);
        pageVersion.getWorkPageSettings().setOwnDomainName("demo.test.com");
        pageVersion.getWorkPageSettings().setUrl("prosto");

        configStorage.get().getNotUserSiteUrls().add("demo.test.com");

        final MockHttpServletRequest request = new MockHttpServletRequest("", "");
        final MockHttpServletResponse response = new MockHttpServletResponse();
        request.setServerName("www.dEmO.test.com");
        request.setProtocol("http");
        final MockFilterChain mockFilterChain = new MockFilterChain();
        pagesFilter.doFilter(request, response, mockFilterChain);

        Assert.assertTrue(mockFilterChain.isVisitDoFilter());
    }

    private final PagesFilter pagesFilter = new PagesFilter();
    private final ConfigStorage configStorage = ServiceLocator.getConfigStorage();

}
