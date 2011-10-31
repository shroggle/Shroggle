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
import com.shroggle.entity.*;
import com.shroggle.logic.site.WidgetManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.html.HtmlGetterMock;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Artem Stasuk
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class RenderWidgetsTest {

    @Before
    public void before() {
        htmlGetterMock = new HtmlGetterMock();
        ServiceLocator.setHtmlGetter(htmlGetterMock);
    }

    @Test
    public void executeForText() throws IOException, ServletException {
        PageManager pageVersion = TestUtil.createPageVersionPageAndSite();
        Render render = new RenderWidgets(pageVersion, SiteShowOption.INSIDE_APP);
        StringBuilder builder = new StringBuilder("<!-- MEDIA_BLOCK -->");

        htmlGetterMock.addHtml("#1");
        htmlGetterMock.addHtml("#2");


        final DraftText draftText = new DraftText();
        ServiceLocator.getPersistance().putItem(draftText);
        draftText.setText("GGH");

        final WidgetItem widgetItem = new WidgetItem();
        widgetItem.setDraftItem(draftText);
        pageVersion.addWidget(widgetItem);
        ServiceLocator.getPersistance().putWidget(widgetItem);


        MockHttpServletRequest mockRequest = new MockHttpServletRequest("", "");
        RenderContext context = new RenderContext(
                mockRequest, null, null, null, false);
        render.execute(context, builder);

        Assert.assertEquals("GGH", context.getAttribute("widgetText"));
        Assert.assertEquals("#2", builder.toString());
        Assert.assertEquals("/site/render/renderWidgetText.jsp", htmlGetterMock.getUrls().get(0));
        Assert.assertEquals("/site/render/renderWidget.jsp", htmlGetterMock.getUrls().get(1));
    }

    @Test
    public void executeForText_systemPageWithLoginedUser() throws IOException, ServletException {
        TestUtil.createUserAndLogin("email");
        PageManager pageVersion = TestUtil.createPageVersionPageAndSite();
        pageVersion.getPage().setSystem(true);
        Render render = new RenderWidgets(pageVersion, SiteShowOption.INSIDE_APP);
        StringBuilder builder = new StringBuilder("<!-- MEDIA_BLOCK -->");

        htmlGetterMock.addHtml("#1");
        htmlGetterMock.addHtml("#2");

        final DraftText draftText = new DraftText();
        ServiceLocator.getPersistance().putItem(draftText);
        draftText.setText("GGH");

        final WidgetItem widgetItem = new WidgetItem();
        widgetItem.setDraftItem(draftText);
        pageVersion.addWidget(widgetItem);
        ServiceLocator.getPersistance().putWidget(widgetItem);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest("", "");
        RenderContext context = new RenderContext(
                mockRequest, null, null, null, false);
        render.execute(context, builder);        

        Assert.assertEquals("Not enough privileges to see the page. Logout and login again.", context.getAttribute("widgetText"));
        Assert.assertEquals("#2", builder.toString());
        Assert.assertEquals("/site/render/renderWidgetText.jsp", htmlGetterMock.getUrls().get(0));
        Assert.assertEquals("/site/render/renderWidget.jsp", htmlGetterMock.getUrls().get(1));
    }

    /*@Test
    public void executeForGalleryDataWithNotSetGallery() throws IOException, ServletException {
        PageManager pageVersion = TestUtil.createPageVersionPageAndSite();
        Render render = new RenderWidgets(pageVersion, SiteShowOption.INSIDE_APP);
        StringBuilder builder = new StringBuilder("<!-- MEDIA_BLOCK -->");

        htmlGetterMock.addHtml("#1");
        htmlGetterMock.addHtml("#2");

        final DraftGalleryData draftGalleryData = new DraftGalleryData();
        ServiceLocator.getPersistance().putSiteItem(draftGalleryData);

        final WidgetItem widgetItem = TestUtil.createWidgetItem();
        widgetItem.setDraftItem(draftGalleryData);
        pageVersion.addWidget(widgetItem);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest("", "");
        RenderContext context = new RenderContext(
                mockRequest, null, null, null, false);
        render.execute(context, builder);

        Assert.assertEquals("#2", builder.toString());
        Assert.assertEquals("/site/render/renderWidgetNotConfigure.jsp?widgetType=Gallery", htmlGetterMock.getUrls().get(0));
        Assert.assertEquals("/site/render/renderWidget.jsp", htmlGetterMock.getUrls().get(1));
    }*/

    @Test
    public void executeForTextWithoutRight() throws IOException, ServletException {
        PageManager pageVersion = TestUtil.createPageVersionPageAndSite();
        Render render = new RenderWidgets(pageVersion, SiteShowOption.INSIDE_APP);
        StringBuilder builder = new StringBuilder("<!-- MEDIA_BLOCK -->");

        htmlGetterMock.addHtml("#1");
        htmlGetterMock.addHtml("#2");

        pageVersion.getDraftPageSettings().setPageSettingsId(12);
        WidgetItem widgetItem = TestUtil.createTextWidget();
        final WidgetManager widgetManager = new WidgetManager(widgetItem);
        widgetManager.getAccessibleSettings().setAccess(AccessForRender.RESTRICTED);
        widgetManager.getAccessibleSettings().setVisitors(true);
        final DraftText draftText = (DraftText)(widgetItem.getDraftItem());
        draftText.setText("GGH");
        pageVersion.addWidget(widgetItem);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest("", "");
        RenderContext context = new RenderContext(
                mockRequest, null, null, null, false);
        render.execute(context, builder);

        Assert.assertEquals("#2", builder.toString());
        Assert.assertEquals("/site/render/renderWidgetWithoutShowRight.jsp", htmlGetterMock.getUrls().get(0));
        Assert.assertEquals("/site/render/renderWidget.jsp", htmlGetterMock.getUrls().get(1));
    }

    @Test
    public void executeForTextWithAll() throws IOException, ServletException {
        PageManager pageVersion = TestUtil.createPageVersionPageAndSite();
        Render render = new RenderWidgets(pageVersion, SiteShowOption.INSIDE_APP);
        StringBuilder builder = new StringBuilder("<!-- MEDIA_BLOCK -->");

        htmlGetterMock.addHtml("#1");
        htmlGetterMock.addHtml("#2");

        WidgetItem widgetItem = TestUtil.createTextWidget();
        pageVersion.addWidget(widgetItem);

        final WidgetManager widgetManager = new WidgetManager(widgetItem);
        widgetManager.getAccessibleSettings().setAccess(AccessForRender.UNLIMITED);

        final DraftText draftText = (DraftText)(widgetItem.getDraftItem());
        draftText.setText("GGH");

        MockHttpServletRequest mockRequest = new MockHttpServletRequest("", "");
        RenderContext context = new RenderContext(
                mockRequest, null, null, null, false);
        render.execute(context, builder);

        Assert.assertEquals("#2", builder.toString());
        Assert.assertEquals("/site/render/renderWidgetText.jsp", htmlGetterMock.getUrls().get(0));
        Assert.assertEquals("/site/render/renderWidget.jsp", htmlGetterMock.getUrls().get(1));
    }

    /*@TestBase
    public void executeForSiteMenuWithAll() throws IOException, ServletException {
        Persistance persistance = ServiceLocator.getPersistance();

        Site site1 = new Site();
        site1.setTitle("title1");
        site1.setSiteId(1);
        site1.setSubDomain("1");
        site1.setCreationDate(new Date());
        ThemeId id = new ThemeId();
        id.setTemplateDirectory("");
        id.setThemeCss("");
        site1.setThemeId(id);
        persistance.putSite(site1);

        List<Integer> includedPages = new ArrayList<Integer>();

        Page[] page = new Page[10];
        PageManager[] pageVersion = new PageManager[10];
        for (int i = 0; i < 10; i++) {
            page[i] = TestUtil.createPage(site);
            page[i].setType(PageType.FLV_VIDEO);

            pageVersion[i] = new PageManager();
            pageVersion[i].setPage(page[i]);
            persistance.putPageVersion(pageVersion[i]);
            includedPages.add(pageVersion[i].getPageId());
        }

        ShowPageVersionRender render = new ShowPageVersionRenderWidgets();
        StringBuilder builder = new StringBuilder("<!-- MEDIA_BLOCK -->");

        htmlGetterMock.addHtml("#1");
        htmlGetterMock.addHtml("#2");

        Menu menu = new Menu();
        menu.setDraftVertical(true);
        menu.setItems(includedPages);
        menu.setSiteId(site1.getSiteId());
        persistance.putMenu(menu);

        WidgetItem widgetMenu = new WidgetItem();
        widgetMenu.setDraftItem(menu.getMenuId());
        pageVersion[0].addWidget(widgetMenu);
        widgetMenu.getAccess(AccessGroup.ALL);
        
        MockHttpServletRequest mockRequest = new MockHttpServletRequest("", "");
        ShowPageVersionRenderContext context = new ShowPageVersionRenderContext(
                mockRequest, null, null, pageVersion[0], false, null, null);
        render.execute(context, builder);

        //Assert.assertEquals("#2", builder.toString());
       // Assert.assertEquals("/site/render/renderWidgetText.jsp", htmlGetterMock.getUrls().get(0));
       // Assert.assertEquals("/site/render/renderWidget.jsp", htmlGetterMock.getUrls().get(1));
    }*/

    @Test
    public void executeForTextWithoutOwnerRight() throws IOException, ServletException {
        PageManager pageVersion = TestUtil.createPageVersionPageAndSite();
        Render render = new RenderWidgets(pageVersion, SiteShowOption.INSIDE_APP);
        StringBuilder builder = new StringBuilder("<!-- MEDIA_BLOCK -->");

        htmlGetterMock.addHtml("#1");
        htmlGetterMock.addHtml("#2");

        pageVersion.getDraftPageSettings().setPageSettingsId(12);

        final DraftText draftText = new DraftText();
        ServiceLocator.getPersistance().putItem(draftText);
        draftText.setText("GGH");

        final WidgetItem widgetItem = new WidgetItem();
        widgetItem.setDraftItem(draftText);
        pageVersion.addWidget(widgetItem);
        ServiceLocator.getPersistance().putWidget(widgetItem);

        final WidgetManager widgetManager = new WidgetManager(widgetItem);
        final AccessibleSettings accessibleSettings = new AccessibleSettings();
        accessibleSettings.setAccess(AccessForRender.RESTRICTED);
        accessibleSettings.setAdministrators(true);
        widgetManager.setAccessibleSettingsInCurrentPlace(accessibleSettings);


        MockHttpServletRequest mockRequest = new MockHttpServletRequest("", "");
        RenderContext context = new RenderContext(
                mockRequest, null, null, null, false);
        render.execute(context, builder);

        Assert.assertEquals("#2", builder.toString());
        Assert.assertEquals("/site/render/renderWidgetWithoutOwnerShowRight.jsp", htmlGetterMock.getUrls().get(0));
        Assert.assertEquals("/site/render/renderWidget.jsp", htmlGetterMock.getUrls().get(1));
    }

    private HtmlGetterMock htmlGetterMock;

}