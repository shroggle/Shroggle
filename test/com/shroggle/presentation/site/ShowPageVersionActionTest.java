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
import com.shroggle.entity.*;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.site.WidgetManager;
import com.shroggle.logic.site.page.Doctype;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.filesystem.FileSystemMock;
import com.shroggle.util.html.HtmlGetterMock;
import com.shroggle.util.persistance.Persistance;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

/**
 * @author Stasuk Artem
 */
@RunWith(TestRunnerWithMockServices.class)
public class ShowPageVersionActionTest {

    @Before
    public void before() {
        action.setContext(new ActionBeanContext());
        action.getContext().setRequest(new MockHttpServletRequest("", ""));
    }


    @Test
    public void showWithShowRightOnWidget() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        site.setSubDomain("mySite");
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);
        site.setThemeId(new ThemeId("a", "b"));

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = TestUtil.createPageVersion(page);
        
        pageVersion.setCreationDate(new Date(System.currentTimeMillis() * 2));
        pageVersion.setHtml("<!-- MEDIA_BLOCK -->");

        WidgetItem widgetText = TestUtil.createTextWidget();
        WidgetManager widgetManager = new WidgetManager(widgetText);
        widgetManager.getAccessibleSettings().setAccess(AccessForRender.RESTRICTED);
        widgetManager.getAccessibleSettings().setAdministrators(true);
        persistance.putWidget(widgetText);
        pageVersion.addWidget(widgetText);

        action.setPageId(pageVersion.getPageId());
        action.setSiteShowOption(SiteShowOption.ON_USER_PAGES);
        action.execute();

        final HtmlGetterMock htmlGetterMock = (HtmlGetterMock) ServiceLocator.getHtmlGetter();
        Assert.assertEquals("/site/render/renderWidgetText.jsp", htmlGetterMock.getUrls().get(0));
        Assert.assertEquals("/site/render/renderWidget.jsp", htmlGetterMock.getUrls().get(1));
    }

      @Ignore
    @Test
    public void showWithPageVersionDraft() throws Exception {
        Site site = new Site();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);
        persistance.putSite(site);

        KeywordsGroup keywordsGroup1 = TestUtil.createKeywordsGroup(site);
        keywordsGroup1.setName("a");
        keywordsGroup1.setValue("k1,k2");

        KeywordsGroup keywordsGroup2 = TestUtil.createKeywordsGroup(site);
        keywordsGroup2.setName("b");
        keywordsGroup2.setValue("k3,k4");

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);
        
        site.setThemeId(new ThemeId("a", "b"));
        pageVersion.setLayoutFile("a");
        pageVersion.setHtml("<!-- PAGE_HEADER --><!-- MEDIA_BLOCK --><!-- MEDIA_BLOCK -->");

        WidgetComposit widgetComposit = new WidgetComposit();
        pageVersion.setName("bbb");
        persistance.putWidget(widgetComposit);
          pageVersion.addWidget(widgetComposit);

        Widget child1 = TestUtil.createTextWidget();
        widgetComposit.addChild(child1);
        persistance.putWidget(child1);
          pageVersion.addWidget(child1);

        Widget child2 = TestUtil.createTextWidget();
        child2.setPosition(1);
        widgetComposit.addChild(child2);
        persistance.putWidget(child2);
          pageVersion.addWidget(child2);

        KeywordsGroup pageKeywordsGroup1 = TestUtil.createKeywordsGroup(site);

        KeywordsGroup pageKeywordsGroup2 = TestUtil.createKeywordsGroup(site);
        pageVersion.addKeywordsGroup(pageKeywordsGroup2);

        action.setPageId(pageVersion.getPageId());
        action.setSiteShowOption(SiteShowOption.ON_USER_PAGES);

        ServiceLocator.getConfigStorage().get().setDisableResourceMergeAgent("a");
        final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest("", "");
        mockHttpServletRequest.addHeader("user-agent", "faa");
        action.setContext(new ActionBeanContext());
        action.getContext().setRequest(mockHttpServletRequest);
        action.execute();

        Assert.assertTrue(
                action.getPageVersionHtml().contains(
                        "<link rel=\"stylesheet\" type=\"text/css\" href=\"/site/templates/a/b\">\n" +
                                "<style id=\"widgetBorderBackgrounds\" type=\"text/css\">  </style>\n" +
                                "<meta name=\"generator\" content=\"Web-Deva.com\" />\n" +
                                "<meta name=\"keywords\" content=\"k1,k2,k3,k4\">\n" +
                                "<style id=\"cssParameterStyle\" type=\"text/css\">\n" +
                                "body blogPost {color: red;}\n" +
                                "\n" +
                                "#widget1001 blogPost {color: red;}\n" +
                                "\n" +
                                "</style>\n" +
                                "<!-- PAGE_HEADER -->No select widgets for this position!<script type=\"text/javascript\"> trackVisitor(101) </script>"));
    }

    @Test
    public void showWithPageVersionWork() throws Exception {
        Site site = new Site();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);
        persistance.putSite(site);

        Page page = TestUtil.createPage(site);
        page.setPageId(101);

        PageManager pageVersion = TestUtil.createPageVersion(page);
        
        site.setThemeId(new ThemeId("a", "b"));
        pageVersion.setLayoutFile("a");
        pageVersion.setHtml("1");

        action.setPageId(pageVersion.getPageId());
        action.setSiteShowOption(SiteShowOption.ON_USER_PAGES);
        action.execute();

        Assert.assertEquals(
                Doctype.TRANSITIONAL + "\n1<script type=\"text/javascript\"> trackVisitor(101) </script>",
                action.getPageVersionHtml());
    }

    @Test
    public void showWithShowRightOwnerForAnonym() throws Exception {
        Site site = new Site();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);
        persistance.putSite(site);

        Page loginPage = TestUtil.createPage(site);
        site.setLoginAdminPage(loginPage);
        persistance.putPage(loginPage);
        PageManager loginPageVersion = new PageManager(loginPage);
        loginPageVersion.setHtml("<!-- PAGE_TITLE --> FF1");


        Page page = TestUtil.createPage(site);


        PageManager pageVersion = TestUtil.createPageVersion(page);
        pageVersion.setTitle("H");
        pageVersion.setAccess(AccessForRender.RESTRICTED);
        pageVersion.setAdministrators(true);
        site.setThemeId(new ThemeId("a", "b"));
        pageVersion.setLayoutFile("a");
        pageVersion.setHtml("1");

        action.setPageId(pageVersion.getPageId());
        action.setSiteShowOption(SiteShowOption.ON_USER_PAGES);
        action.execute();

        Assert.assertEquals(
                Doctype.TRANSITIONAL + "\nH FF1<script type=\"text/javascript\"> trackVisitor(" + loginPage.getPageId() + ") </script>",
                action.getPageVersionHtml());
    }

    @Test
    public void showWithNotFoundShowRightOwnerForAnonym() throws Exception {
        Site site = new Site();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);
        persistance.putSite(site);

        Page page = TestUtil.createPage(site);
        page.setPageId(101);

        PageManager pageVersion = TestUtil.createPageVersion(page);

        pageVersion.setAccess(AccessForRender.RESTRICTED);
        pageVersion.setAdministrators(true);
        site.setThemeId(new ThemeId("a", "b"));
        pageVersion.setLayoutFile("a");
        pageVersion.setHtml("1");

        final FileSystemMock fileSystemMock = (FileSystemMock) fileSystem;
        fileSystemMock.setLoginPageDefaultHtml("FF");
        action.setPageId(pageVersion.getPageId());
        action.setSiteShowOption(SiteShowOption.ON_USER_PAGES);
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals("/site/pageNotFound.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void showWithNotFoundShowRightOwnerForVisitor() throws Exception {
        Site site = new Site();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);
        persistance.putSite(site);

        final User user = TestUtil.createUserAndLogin();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.VISITOR);

        Page page = TestUtil.createPage(site);
        page.setPageId(101);

        PageManager pageVersion = TestUtil.createPageVersion(page);

        pageVersion.setAccess(AccessForRender.RESTRICTED);
        pageVersion.setAdministrators(true);
        site.setThemeId(new ThemeId("a", "b"));
        pageVersion.setLayoutFile("a");
        pageVersion.setHtml("1");

        final FileSystemMock fileSystemMock = (FileSystemMock) fileSystem;
        fileSystemMock.setLoginPageDefaultHtml("FF");
        action.setPageId(pageVersion.getPageId());
        action.setSiteShowOption(SiteShowOption.ON_USER_PAGES);
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals("/site/pageNotFound.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void showWithShowRightVisitorForAnonym() throws Exception {
        Site site = new Site();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);
        persistance.putSite(site);

        Page loginPage = TestUtil.createPage(site);
        site.setLoginPage(loginPage);
        persistance.putPage(loginPage);
        new PageManager(loginPage).setHtml("FF");


        Page page = TestUtil.createPage(site);
        PageManager pageVersion = TestUtil.createPageVersion(page);
        pageVersion.setAccess(AccessForRender.RESTRICTED);
        pageVersion.setVisitors(true);
        site.setThemeId(new ThemeId("a", "b"));
        pageVersion.setLayoutFile("a");
        pageVersion.setHtml("1");

        final FileSystemMock fileSystemMock = (FileSystemMock) fileSystem;
        fileSystemMock.setLoginPageDefaultHtml("FF");
        action.setPageId(pageVersion.getPageId());
        action.setSiteShowOption(SiteShowOption.ON_USER_PAGES);
        action.execute();

        Assert.assertEquals(
                Doctype.TRANSITIONAL + "\nFF<script type=\"text/javascript\"> trackVisitor(" + loginPage.getPageId() + ") </script>",
                action.getPageVersionHtml());
    }

    @Test
    public void showWithNotFoundShowRightVisitorForAnonym() throws Exception {
        Site site = new Site();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);
        persistance.putSite(site);

        Page page = TestUtil.createPage(site);
        page.setPageId(101);

        PageManager pageVersion = TestUtil.createPageVersion(page);

        pageVersion.setAccess(AccessForRender.RESTRICTED);
        pageVersion.setVisitors(true);
        site.setThemeId(new ThemeId("a", "b"));
        pageVersion.setLayoutFile("a");
        pageVersion.setHtml("1");

        final FileSystemMock fileSystemMock = (FileSystemMock) fileSystem;
        fileSystemMock.setLoginPageDefaultHtml("FF");
        action.setPageId(pageVersion.getPageId());
        action.setSiteShowOption(SiteShowOption.ON_USER_PAGES);
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals("/site/pageNotFound.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void showWithoutShowRightOnSite() throws Exception {
        Site site = TestUtil.createSite();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);
        site.getAccessibleSettings().setAccess(AccessForRender.RESTRICTED);
        site.getAccessibleSettings().setVisitors(true);
        new PageManager(site.getLoginPage()).setHtml("A");

        Page page = TestUtil.createPage(site);
        PageManager pageVersion = TestUtil.createPageVersion(page);

        site.setThemeId(new ThemeId("a", "b"));
        pageVersion.setLayoutFile("a");
        pageVersion.setHtml("1");

        final FileSystemMock fileSystemMock = (FileSystemMock) fileSystem;
        fileSystemMock.setLoginPageDefaultHtml("FF");

        action.setPageId(pageVersion.getPageId());
        action.setSiteShowOption(SiteShowOption.ON_USER_PAGES);
        action.execute();

        Assert.assertEquals(
                Doctype.TRANSITIONAL + "\nA<script type=\"text/javascript\"> trackVisitor(" + site.getLoginPage().getPageId() + ") </script>",
                action.getPageVersionHtml());
    }

    @Test
    public void showWithNotFoundPageVersion() throws Exception {
        Site site = new Site();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);
        persistance.putSite(site);

        Page page = TestUtil.createPage(site);
        page.setPageId(101);

        PageManager pageVersion = TestUtil.createPageVersion(page);

        site.setThemeId(new ThemeId("a", "b"));
        pageVersion.setLayoutFile("a");
        pageVersion.setHtml("1");

        action.setPageId(pageVersion.getPageId());
        action.setSiteShowOption(SiteShowOption.ON_USER_PAGES);
        action.execute();

        Assert.assertEquals(
                Doctype.TRANSITIONAL + "\n1<script type=\"text/javascript\"> trackVisitor(101) </script>",
                action.getPageVersionHtml());
    }


    private final FileSystem fileSystem = ServiceLocator.getFileSystem();
    private final ShowPageVersionAction action = new ShowPageVersionAction();
    private final Persistance persistance = ServiceLocator.getPersistance();

}