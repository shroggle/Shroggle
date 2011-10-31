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
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.SiteType;
import com.shroggle.exception.PageNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.presentation.site.page.ConfigureBlueprintPagePermissionsService;
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
public class ConfigureBlueprintPagePermissionsServiceTest {

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        site.setType(SiteType.BLUEPRINT);
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
        site.setType(SiteType.BLUEPRINT);
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.setBlueprintNotEditable(true);

        service.execute(pageVersion.getPageId());
    }

    @Test
    public void execute() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        site.setType(SiteType.BLUEPRINT);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.setBlueprintNotEditable(true);

        service.execute(pageVersion.getPageId());

        Assert.assertEquals(true, service.isNotEditable());
    }

    @Test(expected = PageNotFoundException.class)
    public void executeWithNotBlueprint() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        site.setType(SiteType.COMMON);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        service.execute(pageVersion.getPageId());
    }

    private final ConfigureBlueprintPagePermissionsService service = new ConfigureBlueprintPagePermissionsService();

}