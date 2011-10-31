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

package com.shroggle.presentation.site.htmlAndCss;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.PageNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.site.page.Doctype;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystemMock;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stasuk Artem
 */
@RunWith(TestRunnerWithMockServices.class)
public class SavePageHtmlAndCssServiceTest {

    @Test
    public void execute() {
        final Site site = TestUtil.createSite();
        site.setThemeId(new ThemeId("td", "tcss"));
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.setName("2222");
        pageVersion.setUrl("a");
        pageVersion.setLayoutFile("e");

        final Theme theme = new Theme();
        theme.setFile("tcss");

        final Template template = new Template();
        template.getThemes().add(theme);
        template.setDirectory("td");
        fileSystemMock.putTemplate(template);

        fileSystemMock.addTemplateResource("td", "e", "f");
        fileSystemMock.addTemplateResource("td", "tcss", "xxx");

        service.execute(pageVersion.getPageId(), "a", "b");

        Assert.assertTrue("Save page html and theme css not changed page!", pageVersion.isChanged());
        Assert.assertEquals("Page layout changed!", "e", pageVersion.getLayoutFile());
        Assert.assertEquals("Save page version html and css set invalid html!", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n" +
                "a", pageVersion.getHtml());
        Assert.assertEquals("Save page version html and css set invalid theme css!", "b", pageVersion.getCss());
    }

    @Test
    public void executeWithTemplatedHtml() {
        final Site site = TestUtil.createSite();
        site.setThemeId(new ThemeId("td", "tcss"));
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.setName("2222");
        pageVersion.setUrl("a");
        pageVersion.setLayoutFile("e");

        final Theme theme = new Theme();
        theme.setFile("tcss");

        final Template template = new Template();
        template.getThemes().add(theme);
        template.setDirectory("td");
        fileSystemMock.putTemplate(template);

        fileSystemMock.addTemplateResource("td", "e", "a");
        fileSystemMock.addTemplateResource("td", "tcss", "b");

        service.execute(pageVersion.getPageId(), "a", "bx");

        Assert.assertTrue("Save page html and theme css not changed page!", pageVersion.isChanged());
        Assert.assertEquals("Page layout changed!", "e", pageVersion.getLayoutFile());
        Assert.assertNull("Same html must be reset!", pageVersion.getHtml());
        Assert.assertEquals("Same css must be reset!", "bx", pageVersion.getCss());
    }

    @Test
    public void executeWithTemplatedHtmlAndCssButFilesCantFind() {
        final Site site = TestUtil.createSite();
        site.setThemeId(new ThemeId("td", "tcss"));
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.setName("2222");
        pageVersion.setUrl("a");
        pageVersion.setLayoutFile("e");

        final Theme theme = new Theme();
        theme.setFile("tcss");

        final Template template = new Template();
        template.getThemes().add(theme);
        template.setDirectory("td");
        fileSystemMock.putTemplate(template);

        service.execute(pageVersion.getPageId(), "a", "bx");

        Assert.assertTrue("Save page html and theme css not changed page!", pageVersion.isChanged());
        Assert.assertEquals("Page layout changed!", "e", pageVersion.getLayoutFile());
        Assert.assertEquals("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">" +
                "\na", pageVersion.getHtml());
        Assert.assertEquals("bx", pageVersion.getCss());
    }

    @Test
    public void executeResetWithTemplatedHtmlAndCssButFilesCantFind() {
        final Site site = TestUtil.createSite();
        site.setThemeId(new ThemeId("td", "tcss"));
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.setName("2222");
        pageVersion.setHtml("a");
        pageVersion.setCss("bx");
        pageVersion.setUrl("a");
        pageVersion.setLayoutFile("e");

        final Theme theme = new Theme();
        theme.setFile("tcss");

        final Template template = new Template();
        template.getThemes().add(theme);
        template.setDirectory("td");
        fileSystemMock.putTemplate(template);

        service.execute(pageVersion.getPageId(), null, null);

        Assert.assertTrue("Save page html and theme css not changed page!", pageVersion.isChanged());
        Assert.assertEquals("Page layout changed!", "e", pageVersion.getLayoutFile());
        Assert.assertEquals(Doctype.TRANSITIONAL + "\na", pageVersion.getHtml());
        Assert.assertEquals("bx", pageVersion.getCss());
    }

    @Test
    public void executeWithTemplatedCss() {
        final Site site = TestUtil.createSite();
        site.setThemeId(new ThemeId("td", "tcss"));
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.setName("2222");
        pageVersion.setUrl("a");
        pageVersion.setLayoutFile("e");

        final Theme theme = new Theme();
        theme.setFile("tcss");

        final Template template = new Template();
        template.getThemes().add(theme);
        template.setDirectory("td");
        fileSystemMock.putTemplate(template);

        fileSystemMock.addTemplateResource("td", "e", "a");
        fileSystemMock.addTemplateResource("td", "tcss", "b");

        service.execute(pageVersion.getPageId(), "ba", "b");

        Assert.assertTrue("Save page html and theme css not changed page!", pageVersion.isChanged());
        Assert.assertEquals("Page layout changed!", "e", pageVersion.getLayoutFile());
        Assert.assertEquals("Same html must be reset!", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n" +
                "ba", pageVersion.getHtml());
        Assert.assertNull("Same css must be reset!", pageVersion.getCss());
    }

    @Test
    public void executeWithChangeWidgetCount() {
        final Site site = TestUtil.createSite();
        site.setThemeId(new ThemeId("td", "tcss"));
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.setName("2222");
        pageVersion.setUrl("a");
        pageVersion.setHtml("<!-- MEDIA_BLOCK --><!-- MEDIA_BLOCK -->");
        pageVersion.setLayoutFile("e");

        final Theme theme = new Theme();
        theme.setFile("tcss");

        final Template template = new Template();
        template.getThemes().add(theme);
        template.setDirectory("td");
        fileSystemMock.putTemplate(template);

        fileSystemMock.addTemplateResource("td", "e", "f");
        fileSystemMock.addTemplateResource("td", "tcss", "xxx");

        WidgetComposit widgetComposit0 = new WidgetComposit();
        widgetComposit0.setPosition(0);
        persistance.putWidget(widgetComposit0);
        pageVersion.addWidget(widgetComposit0);

        WidgetComposit widgetComposit1 = new WidgetComposit();
        widgetComposit1.setPosition(1);
        persistance.putWidget(widgetComposit1);
        pageVersion.addWidget(widgetComposit1);

        WidgetItem widgetItem = TestUtil.createWidgetItem();
        widgetItem.setPosition(0);
        widgetComposit1.addChild(widgetItem);
        persistance.putWidget(widgetItem);
        pageVersion.addWidget(widgetItem);

        service.execute(pageVersion.getPageId(), "<!-- MEDIA_BLOCK -->", "b");

        Assert.assertEquals("Save page version not normalize html!", 1, widgetComposit0.getChilds().size());
    }

    @Test
    public void executeWithChangeWidgetCountAndTemplatedHtml() {
        final Site site = TestUtil.createSite();
        site.setThemeId(new ThemeId("td", "tcss"));
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.setName("2222");
        pageVersion.setUrl("a");
        pageVersion.setHtml("<!-- MEDIA_BLOCK --><!-- MEDIA_BLOCK -->");
        pageVersion.setLayoutFile("e");

        final Theme theme = new Theme();
        theme.setFile("tcss");

        final Template template = new Template();
        template.getThemes().add(theme);
        template.setDirectory("td");
        fileSystemMock.putTemplate(template);

        fileSystemMock.addTemplateResource("td", "e", "<!-- MEDIA_BLOCK -->");
        fileSystemMock.addTemplateResource("td", "tcss", "xxx");

        WidgetComposit widgetComposit0 = new WidgetComposit();
        widgetComposit0.setPosition(0);
        persistance.putWidget(widgetComposit0);
        pageVersion.addWidget(widgetComposit0);

        WidgetComposit widgetComposit1 = new WidgetComposit();
        widgetComposit1.setPosition(1);
        persistance.putWidget(widgetComposit1);
        pageVersion.addWidget(widgetComposit1);

        WidgetItem widgetItem = TestUtil.createWidgetItem();
        widgetItem.setPosition(0);
        widgetComposit1.addChild(widgetItem);
        persistance.putWidget(widgetItem);
        pageVersion.addWidget(widgetItem);

        service.execute(pageVersion.getPageId(), "<!-- MEDIA_BLOCK -->", "b");

        Assert.assertEquals("Save page version not normailize html!", 1, widgetComposit0.getChilds().size());
    }

    @Test(expected = PageNotFoundException.class)
    public void executeWithNotFoundPageVersion() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        service.execute(-1, "", "");
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() {
        TestUtil.createUser();

        service.execute(1, "a", "");
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final SavePageHtmlAndCssService service = new SavePageHtmlAndCssService();
    private final FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();

}