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
package com.shroggle.presentation.site;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.PageVisitor;
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.exception.PageNotFoundException;
import com.shroggle.exception.VisitNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.ActionUtil;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.presentation.MockWebContextBuilder;
import com.shroggle.util.ServiceLocator;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import net.sourceforge.stripes.mock.MockHttpServletResponse;
import org.directwebremoting.WebContextFactory;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.Date;

/**
 * Author: dmitry.solomadin
 * Date: 22.01.2009
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class TrackVisitorServiceTest {

    private TrackVisitorService service = new TrackVisitorService();

    @Test
    public void trackWithoutCookies() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        MockWebContextBuilder mockWebContextBuilder = new MockWebContextBuilder();
        WebContextFactory.setWebContextBuilder(mockWebContextBuilder);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest("", "");
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        Cookie[] cookies = new Cookie[0];
        mockHttpServletRequest.setCookies(cookies);
        mockWebContext.setHttpServletRequest(mockHttpServletRequest);
        mockWebContext.setHttpServletResponse(mockHttpServletResponse);

        TrackVisitorRequest request = new TrackVisitorRequest();
        request.setBrowser("mozilla");
        request.setOs("windows");
        request.setReferrerUrl("ref_url?q=key_word");
        request.setScreenResolution("scr_res");

        String response = service.track(request, pageVersion.getPageId());
        Assert.assertEquals("ok", response);
        PageVisitor pageVisitor = ServiceLocator.getPersistance().getPageVisitorById(
                Integer.parseInt(ActionUtil.findCookie(mockHttpServletResponse.getCookies(), "sh_pvid").getValue()));
        Assert.assertNotNull(pageVisitor);
        Assert.assertTrue(!pageVisitor.getVisits().isEmpty());
        Assert.assertEquals(1, pageVisitor.getVisits().get(0).getVisitCount());
        Assert.assertEquals(pageVersion.getPage(), pageVisitor.getVisits().get(0).getVisitedPage());
        Assert.assertEquals(1, (int) pageVisitor.getVisits().get(0).getReferrerURLValueByKey("ref_url?q=key_word"));
        Assert.assertEquals(1, (int) pageVisitor.getVisits().get(0).getReferrerTermValueByKey("key_word"));
    }

    @Test
    public void trackWithEmptyPageVisitor() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        final PageVisitor pageVisitor = TestUtil.createPageVisitor();

        MockWebContextBuilder mockWebContextBuilder = new MockWebContextBuilder();
        WebContextFactory.setWebContextBuilder(mockWebContextBuilder);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest("", "");
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("sh_pvid", "" + pageVisitor.getPageVisitorId());
        mockHttpServletRequest.setCookies(cookies);
        mockWebContext.setHttpServletRequest(mockHttpServletRequest);
        mockWebContext.setHttpServletResponse(mockHttpServletResponse);

        TrackVisitorRequest request = new TrackVisitorRequest();
        request.setBrowser("mozilla");
        request.setOs("windows");
        request.setReferrerUrl("ref_url");
        request.setScreenResolution("scr_res");

        String response = service.track(request, pageVersion.getPageId());
        Assert.assertEquals("ok", response);
        Assert.assertTrue(!pageVisitor.getVisits().isEmpty());
        Assert.assertEquals(1, pageVisitor.getVisits().get(0).getVisitCount());
        Assert.assertEquals(pageVersion.getPage(), pageVisitor.getVisits().get(0).getVisitedPage());
        Assert.assertEquals(1, (int) pageVisitor.getVisits().get(0).getReferrerURLValueByKey("ref_url"));
    }

    @Test
    public void trackWithJustVisitedVisitor() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        final PageVisitor pageVisitor = TestUtil.createPageVisitor();
        TestUtil.createVisitForPageVisitor(pageVisitor, pageVersion.getPage());

        MockWebContextBuilder mockWebContextBuilder = new MockWebContextBuilder();
        WebContextFactory.setWebContextBuilder(mockWebContextBuilder);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest("", "");
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("sh_pvid", "" + pageVisitor.getPageVisitorId());
        mockHttpServletRequest.setCookies(cookies);
        mockWebContext.setHttpServletRequest(mockHttpServletRequest);
        mockWebContext.setHttpServletResponse(mockHttpServletResponse);

        TrackVisitorRequest request = new TrackVisitorRequest();
        request.setBrowser("mozilla");
        request.setOs("windows");
        request.setReferrerUrl("ref_url");
        request.setScreenResolution("scr_res");

        String response = service.track(request, pageVersion.getPageId());
        Assert.assertEquals("ok", response);
        Assert.assertTrue(!pageVisitor.getVisits().isEmpty());
        Assert.assertEquals(2, pageVisitor.getVisits().get(0).getVisitCount());
        Assert.assertEquals(pageVersion.getPage(), pageVisitor.getVisits().get(0).getVisitedPage());
        Assert.assertEquals(2, (int) pageVisitor.getVisits().get(0).getReferrerURLValueByKey("ref_url"));
    }

    @Test
    public void trackWithOneMonthVisitedVisitor() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        final PageVisitor pageVisitor = TestUtil.createPageVisitor();

        Date currentDateMinusMonth = new Date();
        long second = 1000;
        long minute = second * 60;
        long hour = minute * 60;
        long day = 24 * hour;
        long month = 31 * day;
        currentDateMinusMonth.setTime(currentDateMinusMonth.getTime() - month);

        TestUtil.createVisitForPageVisitor(pageVisitor, pageVersion.getPage(), currentDateMinusMonth);

        MockWebContextBuilder mockWebContextBuilder = new MockWebContextBuilder();
        WebContextFactory.setWebContextBuilder(mockWebContextBuilder);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest("", "");
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("sh_pvid", "" + pageVisitor.getPageVisitorId());
        mockHttpServletRequest.setCookies(cookies);
        mockWebContext.setHttpServletRequest(mockHttpServletRequest);
        mockWebContext.setHttpServletResponse(mockHttpServletResponse);

        TrackVisitorRequest request = new TrackVisitorRequest();
        request.setBrowser("mozilla");
        request.setOs("windows");
        request.setReferrerUrl("ref_url");
        request.setScreenResolution("scr_res");

        String response = service.track(request, pageVersion.getPageId());
        Assert.assertEquals("ok", response);
        Assert.assertTrue(!pageVisitor.getVisits().isEmpty());
        Assert.assertEquals(1, pageVisitor.getVisits().get(0).getVisitCount());
        Assert.assertEquals(pageVersion.getPage(), pageVisitor.getVisits().get(0).getVisitedPage());
        Assert.assertEquals(1, (int) pageVisitor.getVisits().get(0).getReferrerURLValueByKey("ref_url"));
        Assert.assertEquals(1, pageVisitor.getVisits().get(1).getVisitCount());
        Assert.assertEquals(pageVersion.getPage(), pageVisitor.getVisits().get(1).getVisitedPage());
        Assert.assertEquals(1, (int) pageVisitor.getVisits().get(1).getReferrerURLValueByKey("ref_url"));
    }

    @Test
    public void trackWithJustVisitedVisitorForOtherPage() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final PageManager pageVersion2 = TestUtil.createPageVersionAndPage(site);

        final PageVisitor pageVisitor = TestUtil.createPageVisitor();
        TestUtil.createVisitForPageVisitor(pageVisitor, pageVersion2.getPage());

        MockWebContextBuilder mockWebContextBuilder = new MockWebContextBuilder();
        WebContextFactory.setWebContextBuilder(mockWebContextBuilder);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest("", "");
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("sh_pvid", "" + pageVisitor.getPageVisitorId());
        mockHttpServletRequest.setCookies(cookies);
        mockWebContext.setHttpServletRequest(mockHttpServletRequest);
        mockWebContext.setHttpServletResponse(mockHttpServletResponse);

        TrackVisitorRequest request = new TrackVisitorRequest();
        request.setBrowser("mozilla");
        request.setOs("windows");
        request.setReferrerUrl("ref_url");
        request.setScreenResolution("scr_res");

        String response = service.track(request, pageVersion.getPageId());
        Assert.assertEquals("ok", response);
        Assert.assertTrue(!pageVisitor.getVisits().isEmpty());
        Assert.assertEquals(1, pageVisitor.getVisits().get(0).getVisitCount());
        Assert.assertEquals(pageVersion2.getPage(), pageVisitor.getVisits().get(0).getVisitedPage());
        Assert.assertEquals(1, (int) pageVisitor.getVisits().get(0).getReferrerURLValueByKey("ref_url"));
        Assert.assertEquals(1, pageVisitor.getVisits().get(1).getVisitCount());
        Assert.assertEquals(pageVersion.getPage(), pageVisitor.getVisits().get(1).getVisitedPage());
        Assert.assertEquals(1, (int) pageVisitor.getVisits().get(1).getReferrerURLValueByKey("ref_url"));
    }

    @Test(expected = PageNotFoundException.class)
    public void trackWithoutPageVersion() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        MockWebContextBuilder mockWebContextBuilder = new MockWebContextBuilder();
        WebContextFactory.setWebContextBuilder(mockWebContextBuilder);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest("", "");
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        Cookie[] cookies = new Cookie[0];
        mockHttpServletRequest.setCookies(cookies);
        mockWebContext.setHttpServletRequest(mockHttpServletRequest);
        mockWebContext.setHttpServletResponse(mockHttpServletResponse);

        TrackVisitorRequest request = new TrackVisitorRequest();
        request.setBrowser("mozilla");
        request.setOs("windows");
        request.setReferrerUrl("ref_url");
        request.setScreenResolution("scr_res");

        service.track(request, 0);
    }

    @Test(expected = PageNotFoundException.class)
    public void trackWithoutPage() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
//        final PageManager pageVersion = new PageManager(TestUtil.createPage());

        MockWebContextBuilder mockWebContextBuilder = new MockWebContextBuilder();
        WebContextFactory.setWebContextBuilder(mockWebContextBuilder);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest("", "");
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        Cookie[] cookies = new Cookie[0];
        mockHttpServletRequest.setCookies(cookies);
        mockWebContext.setHttpServletRequest(mockHttpServletRequest);
        mockWebContext.setHttpServletResponse(mockHttpServletResponse);

        TrackVisitorRequest request = new TrackVisitorRequest();
        request.setBrowser("mozilla");
        request.setOs("windows");
        request.setReferrerUrl("ref_url");
        request.setScreenResolution("scr_res");

        service.track(request, -1);
    }

    @Test
    public void updateTime() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        final PageVisitor pageVisitor = TestUtil.createPageVisitor();
        TestUtil.createVisitForPageVisitor(pageVisitor, pageVersion.getPage());

        MockWebContextBuilder mockWebContextBuilder = new MockWebContextBuilder();
        WebContextFactory.setWebContextBuilder(mockWebContextBuilder);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest("", "");
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("sh_pvid", "" + pageVisitor.getPageVisitorId());
        mockHttpServletRequest.setCookies(cookies);
        mockWebContext.setHttpServletRequest(mockHttpServletRequest);
        mockWebContext.setHttpServletResponse(mockHttpServletResponse);

        String response = service.updateTime(pageVersion.getPageId(), 100);
        Assert.assertEquals("ok", response);
        Assert.assertNotNull(pageVisitor);
        Assert.assertTrue(!pageVisitor.getVisits().isEmpty());
        Assert.assertEquals(1, pageVisitor.getVisits().get(0).getVisitCount());
        Assert.assertEquals(101, pageVisitor.getVisits().get(0).getOverallTimeOfVisit());
    }

    @Test(expected = VisitNotFoundException.class)
    public void updateTimeWithEmptyPageVisitor() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        final PageVisitor pageVisitor = TestUtil.createPageVisitor();

        MockWebContextBuilder mockWebContextBuilder = new MockWebContextBuilder();
        WebContextFactory.setWebContextBuilder(mockWebContextBuilder);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest("", "");
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("sh_pvid", "" + pageVisitor.getPageVisitorId());
        mockHttpServletRequest.setCookies(cookies);
        mockWebContext.setHttpServletRequest(mockHttpServletRequest);
        mockWebContext.setHttpServletResponse(mockHttpServletResponse);

        service.updateTime(pageVersion.getPageId(), 100);
    }

    @Test
    public void updateTimeWithoutCookie() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        final PageVisitor pageVisitor = TestUtil.createPageVisitor();
        TestUtil.createVisitForPageVisitor(pageVisitor, pageVersion.getPage());

        MockWebContextBuilder mockWebContextBuilder = new MockWebContextBuilder();
        WebContextFactory.setWebContextBuilder(mockWebContextBuilder);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest("", "");
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        Cookie[] cookies = new Cookie[0];
        mockHttpServletRequest.setCookies(cookies);
        mockWebContext.setHttpServletRequest(mockHttpServletRequest);
        mockWebContext.setHttpServletResponse(mockHttpServletResponse);

        String response = service.updateTime(pageVersion.getPageId(), 100);
        Assert.assertEquals("ok", response);
    }

    @Test(expected = PageNotFoundException.class)
    public void updateTimeWithoutPageVersion() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        final PageVisitor pageVisitor = TestUtil.createPageVisitor();
        TestUtil.createVisitForPageVisitor(pageVisitor, pageVersion.getPage());

        MockWebContextBuilder mockWebContextBuilder = new MockWebContextBuilder();
        WebContextFactory.setWebContextBuilder(mockWebContextBuilder);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest("", "");
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("sh_pvid", "" + pageVisitor.getPageVisitorId());
        mockHttpServletRequest.setCookies(cookies);
        mockWebContext.setHttpServletRequest(mockHttpServletRequest);
        mockWebContext.setHttpServletResponse(mockHttpServletResponse);

        service.updateTime(0, 100);
    }

    @Test(expected = PageNotFoundException.class)
    public void updateTimeWithoutPage() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final PageVisitor pageVisitor = TestUtil.createPageVisitor();

        MockWebContextBuilder mockWebContextBuilder = new MockWebContextBuilder();
        WebContextFactory.setWebContextBuilder(mockWebContextBuilder);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest("", "");
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("sh_pvid", "" + pageVisitor.getPageVisitorId());
        mockHttpServletRequest.setCookies(cookies);
        mockWebContext.setHttpServletRequest(mockHttpServletRequest);
        mockWebContext.setHttpServletResponse(mockHttpServletResponse);

        service.updateTime(-1, 100);
    }
}
