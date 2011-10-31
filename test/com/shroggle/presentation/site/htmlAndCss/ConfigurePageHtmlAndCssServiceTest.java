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
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.ThemeId;
import com.shroggle.exception.PageNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.site.page.Doctype;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystemMock;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class ConfigurePageHtmlAndCssServiceTest {

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() throws Exception {
        TestUtil.createUser();

        service.execute(1);
    }

    @Test
    public void execute() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.getPage().getSite().setSubDomain("f");
        pageVersion.getPage().getSite().setTitle("1");
        pageVersion.setHtml("a");
        pageVersion.setCss("aa");
        pageVersion.setName("m");

        service.execute(pageVersion.getPageId());

        Assert.assertEquals(pageVersion.getPageId(), service.getPageId());
        Assert.assertNotNull(service.getPageTitle());
        Assert.assertEquals("Return invalid page html!", Doctype.TRANSITIONAL + "\na", service.getPageVersionHtml());
        Assert.assertEquals("Return invalid page theme css!", "aa", service.getPageVersionThemeCss());
    }

    @Test
    public void executeWithPageVersionHtmlAndThemeCss() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.setHtml("c");
        pageVersion.setCss("d");

        service.execute(pageVersion.getPageId());

        Assert.assertEquals("Return invalid page html!", Doctype.TRANSITIONAL + "\nc", service.getPageVersionHtml());
        Assert.assertEquals("Return invalid page theme css!", "d", service.getPageVersionThemeCss());
    }

    @Test
    public void executeWithPageVersionThemeCss() throws Exception {
        fileSystemMock.addTemplateResource("a", "b", "1");

        final Site site = TestUtil.createSite();
        site.setThemeId(new ThemeId("a", "b"));
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.setLayoutFile("b");
        pageVersion.setCss("d");

        service.execute(pageVersion.getPageId());

        Assert.assertEquals("Return invalid page html!", Doctype.TRANSITIONAL + "\n1", service.getPageVersionHtml());
        Assert.assertEquals("Return invalid page theme css!", "d", service.getPageVersionThemeCss());
    }

    @Test
    public void executeWithPageVersionThemeHtml() throws Exception {
        fileSystemMock.addTemplateResource("a", "b", "1");

        final Site site = TestUtil.createSite();
        site.setThemeId(new ThemeId("a", "b"));
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.setHtml("d");

        service.execute(pageVersion.getPageId());

        Assert.assertEquals("Return invalid page html!", Doctype.TRANSITIONAL + "\nd", service.getPageVersionHtml());
        Assert.assertEquals("Return invalid page theme css!", "1", service.getPageVersionThemeCss());
    }

    @Test(expected = PageNotFoundException.class)
    public void executeWithoutPageVersion() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        service.execute(-1);
    }

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    private final FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
    private final ConfigurePageHtmlAndCssService service = new ConfigurePageHtmlAndCssService();

}