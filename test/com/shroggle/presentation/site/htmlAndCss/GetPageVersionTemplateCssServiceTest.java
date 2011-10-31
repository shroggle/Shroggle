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
public class GetPageVersionTemplateCssServiceTest {

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

        Assert.assertEquals("xxx", service.execute(pageVersion.getPageId()));
    }

    @Test
    public void executeWithPageVersionHtml() {
        final Site site = TestUtil.createSite();
        site.setThemeId(new ThemeId("td", "tcss"));
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.setCss("gg");
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

        Assert.assertEquals("xxx", service.execute(pageVersion.getPageId()));
    }

    @Test(expected = PageNotFoundException.class)
    public void executeWithNotMy() {
        TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        service.execute(pageVersion.getPageId());
    }

    @Test(expected = PageNotFoundException.class)
    public void executeWithNotFoundPageVersion() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        service.execute(-1);
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() {
        TestUtil.createUser();

        service.execute(1);
    }

    private final GetPageVersionTemplateCssService service = new GetPageVersionTemplateCssService();
    private final FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();

}