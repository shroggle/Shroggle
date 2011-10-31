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
package com.shroggle.presentation.site.accessibilityForRender;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class ConfigureAccessibleSettingsServiceTest {

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void execute() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);

        service.execute(pageVersion.getPageId(), AccessibleElementType.PAGE, false);

        Assert.assertEquals(AccessForRender.UNLIMITED, service.getAccessibleForRender().getAccessibleSettings().getAccess());
        Assert.assertEquals(pageVersion.getPageId(), service.getAccessibleForRender().getId());
        Assert.assertNotNull(service.getPageTitle());
        Assert.assertTrue(service.isShowForPage());
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutUser() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);

        service.execute(pageVersion.getPageId(), AccessibleElementType.PAGE, false);
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ConfigureAccessibleSettingsService service = new ConfigureAccessibleSettingsService();

}
