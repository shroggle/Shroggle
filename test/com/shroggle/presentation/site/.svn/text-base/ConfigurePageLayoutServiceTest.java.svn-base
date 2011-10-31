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
import com.shroggle.exception.PageNotFoundException;
import com.shroggle.exception.TemplateNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.presentation.site.page.ConfigurePageLayoutService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystemMock;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Stasuk Artem
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class ConfigurePageLayoutServiceTest {

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        service.execute(pageVersion.getPageId());
    }

    @Test(expected = PageNotFoundException.class)
    public void executeWithoutPageVersion() throws IOException, ServletException {
        TestUtil.createUserAndLogin();

        service.execute(-1);
    }

    @Test(expected = PageNotFoundException.class)
    public void executeWithOtherLogin() throws IOException, ServletException {
        TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        service.execute(pageVersion.getPageId());
    }

    @Test(expected = TemplateNotFoundException.class)
    public void executeWithNotFoundTemplate() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        service.execute(pageVersion.getPageId());
    }

    @Test
    public void execute() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        site.setSubDomain("tf");
        site.getThemeId().setTemplateDirectory("a");
        site.getThemeId().setThemeCss("test.css");

        Template template = new Template();
        template.setDirectory("a");
        Layout layout = new Layout();
        layout.setThumbnailFile("layout.png");
        template.getLayouts().add(layout);
        layout.setTemplate(template);
        fileSystemMock.putTemplate(template);
        fileSystemMock.setLayoutThumbnailPath(layout, "gg");

        Theme theme1 = new Theme();
        theme1.setTemplate(template);
        template.getThemes().add(theme1);
        fileSystemMock.setThemeColorTileUrl(theme1, "gge");
        Theme theme2 = new Theme();
        theme2.setTemplate(template);
        template.getThemes().add(theme2);
        fileSystemMock.setThemeColorTileUrl(theme2, "gge1");

        Template otherTemplate = new Template();
        otherTemplate.setDirectory("aa");
        fileSystemMock.putTemplate(otherTemplate);

        Theme otherTheme = new Theme();
        otherTemplate.getThemes().add(otherTheme);

        service.execute(pageVersion.getPageId());

        Assert.assertEquals(1, service.getLayouts().size());
        Assert.assertNull(service.getSelectLayoutFile());
        Assert.assertNotNull(service.getLayoutThumbnails());
        Assert.assertEquals(1, service.getLayoutThumbnails().size());
        Assert.assertEquals("gg", service.getLayoutThumbnails().get(layout));

        Assert.assertNotNull(service.getPageTitle());

        Assert.assertNotNull("Where themes list?", service.getThemes());
        Assert.assertEquals("Why themes list empty?", 2, service.getThemes().size());
        Assert.assertEquals(theme1, service.getThemes().get(0));
        Assert.assertEquals(theme2, service.getThemes().get(1));
        Assert.assertNotNull(service.getThemeColorTiles());
        Assert.assertEquals(2, service.getThemeColorTiles().size());
        Assert.assertEquals("test.css", service.getSelectThemeFile());
        Assert.assertEquals("gge", service.getThemeColorTiles().get(theme1));
        Assert.assertEquals("gge1", service.getThemeColorTiles().get(theme2));
    }

    @Test
    public void executeWithSelectTheme() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        site.setSubDomain("tf");
        site.getThemeId().setTemplateDirectory("a");
        site.getThemeId().setThemeCss("test.css");

        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.setThemeId(new ThemeId("a", "test.css"));

        final Template template = new Template();
        template.setDirectory("a");
        fileSystemMock.putTemplate(template);

        final Layout layout = new Layout();
        layout.setThumbnailFile("layout.png");
        template.getLayouts().add(layout);
        layout.setTemplate(template);
        fileSystemMock.setLayoutThumbnailPath(layout, "gg");

        final Theme theme = new Theme();
        theme.setTemplate(template);
        template.getThemes().add(theme);
        fileSystemMock.setThemeColorTileUrl(theme, "f");

        service.execute(pageVersion.getPageId());

        Assert.assertNull(service.getSelectLayoutFile());
        Assert.assertNotNull(service.getLayoutThumbnails());
        Assert.assertEquals(1, service.getLayoutThumbnails().size());
        Assert.assertEquals("gg", service.getLayoutThumbnails().get(layout));

        Assert.assertNotNull(service.getPageTitle());

        Assert.assertNotNull("Where are themes list?", service.getThemes());
        Assert.assertEquals("Why themes list empty?", 1, service.getThemes().size());
        Assert.assertEquals(theme, service.getThemes().get(0));
        Assert.assertNotNull(service.getThemeColorTiles());
        Assert.assertEquals(1, service.getThemeColorTiles().size());
        Assert.assertEquals("test.css", service.getSelectThemeFile());
        Assert.assertEquals("f", service.getThemeColorTiles().get(theme));
    }

    @Test
    public void executeWithSiteTitle() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        site.setSubDomain("tf");
        site.getThemeId().setTemplateDirectory("a");
        site.getThemeId().setThemeCss("test.css");
        site.setTitle("title");
        site.setSubDomain("tf");
        site.getThemeId().setTemplateDirectory("a");
        site.getThemeId().setThemeCss("test.css");

        Template template1 = new Template();
        template1.setDirectory("a");
        Layout layout = new Layout();
        layout.setThumbnailFile("layout.png");
        template1.getLayouts().add(layout);
        layout.setTemplate(template1);
        fileSystemMock.putTemplate(template1);
        Template template2 = new Template();
        template2.setDirectory("aa");
        fileSystemMock.putTemplate(template2);

        service.execute(pageVersion.getPageId());

        Assert.assertNotNull(service.getPageTitle());
    }

    @Test
    public void executeWithoutPageType() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        site.getThemeId().setTemplateDirectory("a");
        site.getThemeId().setThemeCss("test.css");

        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.setLayoutFile("tt.ff");

        Template template1 = new Template();
        template1.setDirectory("a");
        Layout layout = new Layout();
        layout.setThumbnailFile("layout.png");
        template1.getLayouts().add(layout);
        fileSystemMock.setLayoutThumbnailPath(layout, "f");
        layout.setTemplate(template1);
        fileSystemMock.putTemplate(template1);
        Template template2 = new Template();
        template2.setDirectory("aa");
        fileSystemMock.putTemplate(template2);

        service.execute(pageVersion.getPageId());

        Assert.assertNotNull(service.getLayoutThumbnails());
        Assert.assertEquals(1, service.getLayoutThumbnails().size());
        Assert.assertEquals("f", service.getLayoutThumbnails().get(layout));

    }

    private final FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
    private final ConfigurePageLayoutService service = new ConfigurePageLayoutService();

}