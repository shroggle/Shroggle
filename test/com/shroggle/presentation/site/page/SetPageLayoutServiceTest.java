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

package com.shroggle.presentation.site.page;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.LayoutNotFoundException;
import com.shroggle.exception.PageNotFoundException;
import com.shroggle.exception.ThemeNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystemMock;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stasuk Artem
 */
@RunWith(TestRunnerWithMockServices.class)
public class SetPageLayoutServiceTest {

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() {
        final PageManager pageVersion = TestUtil.createPageVersionPageAndSite();

        service.execute(pageVersion.getPageId(), "a", null);
    }

    @Test(expected = PageNotFoundException.class)
    public void executeWithoutPage() {
        TestUtil.createUserAndLogin();
        service.execute(-1, "a", null);
    }

    @Test(expected = PageNotFoundException.class)
    public void executeWithOtherLogin() {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserAndLogin();

        service.execute(pageVersion.getPageId(), "a", null);
    }

    @Test(expected = LayoutNotFoundException.class)
    public void executeWithNotFoundLayoutFile() {
        final Site site = TestUtil.createSite();
        site.setThemeId(new ThemeId("a", "b"));
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Template template = new Template();
        template.setDirectory("a");
        Layout layout = new Layout();
        template.getLayouts().add(layout);
        layout.setFile("f");
        layout.setTemplate(template);
        fileSystemMock.putTemplate(template);

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        service.execute(pageVersion.getPageId(), "a", null);

        Assert.assertEquals("a", pageVersion.getLayoutFile());
    }

    @Test
    public void executeWithoutThemeFile() {
        final Site site = TestUtil.createSite();
        site.getThemeId().setTemplateDirectory("a");

        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Template template1 = new Template();
        template1.setDirectory("a");
        Layout layout1 = new Layout();
        template1.getLayouts().add(layout1);
        layout1.setFile("f");
        layout1.setTemplate(template1);

        Layout layout2 = new Layout();
        template1.getLayouts().add(layout2);
        layout2.setFile("b");
        layout2.setTemplate(template1);
        fileSystemMock.addTemplateResource("a", "b", "");

        fileSystemMock.putTemplate(template1);
        Template template2 = new Template();
        template2.setDirectory("aa");
        fileSystemMock.putTemplate(template2);

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.setLayoutFile("f");
        pageVersion.setHtml("aa");
        pageVersion.setCss("aa1");

        service.execute(pageVersion.getPageId(), "b", null);

        Assert.assertNull(pageVersion.getThemeId());
        Assert.assertEquals("b", pageVersion.getLayoutFile());
        Assert.assertNull("Set page version layout not reset html!", pageVersion.getHtml());
        Assert.assertEquals("Set page version layout reset theme css!", "aa1", pageVersion.getCss());
    }

    @Test
    public void execute() {
        final Site site = TestUtil.createSite();
        site.getThemeId().setTemplateDirectory("a");

        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Template template1 = new Template();
        template1.setDirectory("a");
        Layout layout1 = new Layout();
        template1.getLayouts().add(layout1);
        layout1.setFile("f");
        layout1.setTemplate(template1);

        Theme theme = new Theme();
        theme.setFile("m");
        theme.setTemplate(template1);
        template1.getThemes().add(theme);

        Layout layout2 = new Layout();
        template1.getLayouts().add(layout2);
        layout2.setFile("b");
        layout2.setTemplate(template1);
        fileSystemMock.addTemplateResource("a", "b", "");

        fileSystemMock.putTemplate(template1);
        Template template2 = new Template();
        template2.setDirectory("aa");
        fileSystemMock.putTemplate(template2);

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.setLayoutFile("f");
        pageVersion.setHtml("aa");
        pageVersion.setCss("aa1");

        service.execute(pageVersion.getPageId(), "b", "m");

        Assert.assertEquals("b", pageVersion.getLayoutFile());
        Assert.assertEquals("a", pageVersion.getThemeId().getTemplateDirectory());
        Assert.assertEquals("m", pageVersion.getThemeId().getThemeCss());
        Assert.assertNull("Set page version layout not reset html!", pageVersion.getHtml());
        Assert.assertEquals("Set page version layout reset theme css!", "aa1", pageVersion.getCss());
    }

    @Test
    public void executeWithSpecialThemeInPage() {
        final Site site = TestUtil.createSite();
        site.getThemeId().setTemplateDirectory("a");

        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Template template1 = new Template();
        template1.setDirectory("a");
        Layout layout1 = new Layout();
        template1.getLayouts().add(layout1);
        layout1.setFile("f");
        layout1.setTemplate(template1);

        Theme theme = new Theme();
        theme.setFile("m");
        theme.setTemplate(template1);
        template1.getThemes().add(theme);

        Template template2 = new Template();
        template2.setDirectory("a2");

        Theme theme2 = new Theme();
        theme2.setFile("m");
        theme2.setTemplate(template2);
        template2.getThemes().add(theme2);

        Layout layout2 = new Layout();
        template2.getLayouts().add(layout2);
        layout2.setFile("f21");
        layout2.setTemplate(template2);

        Layout layout22 = new Layout();
        template2.getLayouts().add(layout22);
        layout22.setFile("f22");
        layout22.setTemplate(template2);

        fileSystemMock.putTemplate(template1);
        fileSystemMock.putTemplate(template2);
        fileSystemMock.addTemplateResource("a", "f22", "");

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.setLayoutFile("f");
        pageVersion.setHtml("aa");
        pageVersion.setCss("aa1");
        pageVersion.setThemeId(new ThemeId("a2", "f21"));

        service.execute(pageVersion.getPageId(), "f22", "m");

        Assert.assertEquals("f22", pageVersion.getLayoutFile());
        Assert.assertEquals("a", pageVersion.getThemeId().getTemplateDirectory());
        Assert.assertEquals("m", pageVersion.getThemeId().getThemeCss());
        Assert.assertNull("Set page version layout not reset html!", pageVersion.getHtml());
        Assert.assertEquals("Set page version layout reset theme css!", "aa1", pageVersion.getCss());
    }

    @Test
    public void executeWithSpecialThemeInPageWithSameLayout() {
        final Site site = TestUtil.createSite();
        site.getThemeId().setTemplateDirectory("a");

        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Template template1 = new Template();
        template1.setDirectory("a");
        Layout layout1 = new Layout();
        template1.getLayouts().add(layout1);
        layout1.setFile("f");
        layout1.setTemplate(template1);

        Theme theme = new Theme();
        theme.setFile("m");
        theme.setTemplate(template1);
        template1.getThemes().add(theme);

        Template template2 = new Template();
        template2.setDirectory("a2");

        Theme theme2 = new Theme();
        theme2.setFile("th2");
        theme2.setTemplate(template2);
        template2.getThemes().add(theme2);

        Theme theme21 = new Theme();
        theme21.setFile("th21");
        theme21.setTemplate(template2);
        template2.getThemes().add(theme21);

        Layout layout2 = new Layout();
        template2.getLayouts().add(layout2);
        layout2.setFile("f");
        layout2.setTemplate(template2);

        fileSystemMock.putTemplate(template1);
        fileSystemMock.putTemplate(template2);
        fileSystemMock.addTemplateResource("a", "f", "");
        fileSystemMock.addTemplateResource("a2", "f", "");
        fileSystemMock.addTemplateResource("a2", "m2", "");

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.setLayoutFile("f");
        pageVersion.setHtml("aa");
        pageVersion.setCss("aa1");
        pageVersion.setThemeId(new ThemeId("a2", "th2"));

        service.execute(pageVersion.getPageId(), "f", "th21");

        Assert.assertEquals("f", pageVersion.getLayoutFile());
        Assert.assertEquals("a", pageVersion.getThemeId().getTemplateDirectory());
        Assert.assertEquals("th21", pageVersion.getThemeId().getThemeCss());
        Assert.assertNull("Set page version layout not reset html!", pageVersion.getHtml());
        Assert.assertEquals("Set page version layout reset theme css!", "aa1", pageVersion.getCss());
    }

    @Test(expected = ThemeNotFoundException.class)
    public void executeWithNotFoundTheme() {
        final Site site = TestUtil.createSite();
        site.getThemeId().setTemplateDirectory("a");

        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Template template1 = new Template();
        template1.setDirectory("a");
        Layout layout1 = new Layout();
        template1.getLayouts().add(layout1);
        layout1.setFile("f");
        layout1.setTemplate(template1);

        Layout layout2 = new Layout();
        template1.getLayouts().add(layout2);
        layout2.setFile("b");
        layout2.setTemplate(template1);

        fileSystemMock.putTemplate(template1);
        Template template2 = new Template();
        template2.setDirectory("aa");
        fileSystemMock.putTemplate(template2);

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.setLayoutFile("f");
        pageVersion.setHtml("aa");
        pageVersion.setCss("aa1");

        service.execute(pageVersion.getPageId(), "b", "m");
    }

    @Test
    public void executeFormOneToTwoPosition() {
        final Site site = TestUtil.createSite();
        site.getThemeId().setTemplateDirectory("a");

        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Template template1 = new Template();
        template1.setDirectory("a");
        Layout layout1 = new Layout();
        layout1.setTemplate(template1);
        layout1.setFile("f");
        template1.getLayouts().add(layout1);
        fileSystemMock.addTemplateResource("a", "f", "");

        Layout layout2 = new Layout();
        layout2.setTemplate(template1);
        layout2.setFile("b");
        template1.getLayouts().add(layout2);
        fileSystemMock.addTemplateResource("a", "b", "<!-- MEDIA_BLOCK -->");

        fileSystemMock.putTemplate(template1);
        Template template2 = new Template();
        template2.setDirectory("aa");
        fileSystemMock.putTemplate(template2);

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        service.execute(pageVersion.getPageId(), "b", null);

        Assert.assertEquals("b", pageVersion.getLayoutFile());
        Assert.assertEquals(1, pageVersion.getWidgets().size());
    }

    private final FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
    private final SetPageLayoutService service = new SetPageLayoutService();

}