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
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.Template;
import com.shroggle.entity.ThemeId;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystemMock;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Stasuk Artem
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class ShowEditWidgetsActionTest {

    @Before
    public void before() {
        action.setContext(new ActionBeanContext());
        action.getContext().setRequest(new MockHttpServletRequest("a", "b"));
    }

    @Test
    public void showNotFoundPage() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final ResolutionMock resolutionMock = (ResolutionMock) action.view();

        Assert.assertEquals("Page not found!", action.getPageVersionHtml());
        Assert.assertEquals("/site/showEditPage.jsp", resolutionMock.getForwardToUrl());
        Assert.assertNull(ServiceLocator.getSessionStorage().getCurrentlyViewedPageId(action.getContext().getRequest().getSession(), site.getSiteId()));
    }

    @Test
    public void showWithOtherLogin() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndLogin();
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        action.setPageId(pageVersion.getPageId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.view();

        Assert.assertEquals("You are not owner of the page!", action.getPageVersionHtml());
        Assert.assertEquals("/site/showEditPage.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void showWithoutLogin() throws Exception {
        final PageManager pageVersion = TestUtil.createPageVersionPageAndSite();

        action.setPageId(pageVersion.getPageId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.view();

        Assert.assertEquals("You are not owner of the page!", action.getPageVersionHtml());
        Assert.assertEquals("/site/showEditPage.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void show() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.setHtml("F");

        action.setPageId(pageVersion.getPageId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.view();

        Assert.assertEquals("/site/showEditPage.jsp", resolutionMock.getForwardToUrl());
        Assert.assertEquals(pageVersion.getPage().getPageId(),
                (int) ServiceLocator.getSessionStorage().getCurrentlyViewedPageId(action.getContext().getRequest().getSession(), site.getSiteId()));
    }

    @Test
    public void showCoupleOfPages() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.setHtml("F");
        final PageManager pageVersion1 = TestUtil.createPageVersionAndPage(site);
        pageVersion1.setHtml("F");

        action.setPageId(pageVersion.getPageId());
        ResolutionMock resolutionMock = (ResolutionMock) action.view();

        Assert.assertEquals("/site/showEditPage.jsp", resolutionMock.getForwardToUrl());
        Assert.assertEquals(pageVersion.getPage().getPageId(),
                (int) ServiceLocator.getSessionStorage().getCurrentlyViewedPageId(action.getContext().getRequest().getSession(), site.getSiteId()));

        action.setPageId(pageVersion1.getPageId());
        resolutionMock = (ResolutionMock) action.view();

        Assert.assertEquals("/site/showEditPage.jsp", resolutionMock.getForwardToUrl());
        Assert.assertEquals(pageVersion1.getPage().getPageId(),
                (int) ServiceLocator.getSessionStorage().getCurrentlyViewedPageId(action.getContext().getRequest().getSession(), site.getSiteId()));
    }

    @Test
    public void showWithIncorrectLayoutAndThemeIdForPageVersion() throws Exception {
        Template template = new Template();
        template.setDirectory("f");
        fileSystemMock.putTemplate(template);
        fileSystemMock.addTemplateResource("f", "G", "");

        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.getPage().getSite().getThemeId().setTemplateDirectory("f");
        pageVersion.setThemeId(new ThemeId("f", "g"));
        pageVersion.setLayoutFile("G");

        action.setPageId(pageVersion.getPageId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.view();

        Assert.assertEquals("/site/showEditPage.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void showWithIncorrectLayout() throws Exception {
        Template template = new Template();
        template.setDirectory("f");
        fileSystemMock.putTemplate(template);
        fileSystemMock.addTemplateResource("f", "g", "");

        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.getPage().getSite().getThemeId().setTemplateDirectory("f");
        pageVersion.setLayoutFile("G");

        action.setPageId(pageVersion.getPageId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.view();

        Assert.assertEquals("/site/showEditPageWithIncorrectLayout.jsp", resolutionMock.getForwardToUrl());
    }

    private final ShowEditWidgetsAction action = new ShowEditWidgetsAction();
    private final FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();

}