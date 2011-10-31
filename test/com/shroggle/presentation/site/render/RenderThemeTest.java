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

import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystemMock;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;


/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class RenderThemeTest {

    @Test
    public void execute() throws IOException, ServletException {
        fileSystemMock.addTemplateResource("td", "test.css", "some raw template css classes");

        Site site = new Site();
        site.getThemeId().setTemplateDirectory("td");
        site.getThemeId().setThemeCss("test.css");
        Page page = TestUtil.createPage(site);
        new RenderTheme(new PageManager(page), SiteShowOption.getDraftOption()).execute(new EmptyRenderContext(), html);

        Assert.assertEquals(
                "<!-- Raw template css -->\n" +
                        "<style type=\"text/css\">\n" +
                        "some raw template css classes\n" +
                        "</style>\n" +
                        "<style id=\"widgetBorderBackgrounds\" type=\"text/css\">  </style>\n" +
                        "<!-- PAGE_HEADER -->", html.toString());
    }

    @Test
    public void executeWithThemeId() throws IOException, ServletException {
        fileSystemMock.addTemplateResource("td", "test.css", "some raw template css classes");

        Site site = new Site();
        site.getThemeId().setTemplateDirectory("td1");
        site.getThemeId().setThemeCss("test1.css");
        Page page = TestUtil.createPage(site);
        final PageManager pageManager = new PageManager(page);
        pageManager.setThemeId(new ThemeId("td", "test.css"));

        new RenderTheme(pageManager, SiteShowOption.getDraftOption()).execute(new EmptyRenderContext(), html);

        Assert.assertEquals(
                "<!-- Raw template css -->\n" +
                        "<style type=\"text/css\">\n" +
                        "some raw template css classes\n" +
                        "</style>\n" +
                        "<style id=\"widgetBorderBackgrounds\" type=\"text/css\">  </style>\n" +
                        "<!-- PAGE_HEADER -->", html.toString());
    }

    @Test
    public void executeWithCss() throws IOException, ServletException {
        Site site = new Site();
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        Page page = TestUtil.createPage(site);

        final PageManager pageManager = new PageManager(page);
        pageManager.setCss("m");

        new RenderTheme(pageManager, SiteShowOption.getDraftOption()).execute(new EmptyRenderContext(), html);

        Assert.assertEquals(
                "<!-- User modified template css -->\n" +
                        "<style type=\"text/css\">\n" +
                        "m\n" +
                        "</style>\n" +
                        "<style id=\"widgetBorderBackgrounds\" type=\"text/css\">  </style>\n" +
                        "<!-- PAGE_HEADER -->", html.toString());
    }

    @Test
    public void testCreateClassName_Blog_fromSiteEditPage() {
        final RenderTheme renderTheme = new RenderTheme(null, SiteShowOption.getDraftOption());
        final Widget widget = TestUtil.createWidgetItem();
        ServiceLocator.getPersistance().putWidget(widget);

        Assert.assertEquals(" #widget" + widget.getWidgetId(), renderTheme.createClassName(widget, true));
    }

    @Test
    public void testCreateClassName_Blog_notFromSiteEditPage() {
        final RenderTheme renderTheme = new RenderTheme(null, SiteShowOption.getDraftOption());
        final Widget widget = TestUtil.createWidgetItem();
        ServiceLocator.getPersistance().putWidget(widget);

        Assert.assertEquals(" #widget" + widget.getWidgetId(), renderTheme.createClassName(widget, false));
    }

    @Test
    public void testCreateClassName_Image_notFromSiteEditPage() {
        final RenderTheme renderTheme = new RenderTheme(null, SiteShowOption.getDraftOption());
        final Widget widget = TestUtil.createWidgetItem();
        ServiceLocator.getPersistance().putWidget(widget);

        Assert.assertEquals(" #widget" + widget.getWidgetId(), renderTheme.createClassName(widget, false));
    }

    private final StringBuilder html = new StringBuilder("<!-- PAGE_HEADER -->");
    private final FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();

}
