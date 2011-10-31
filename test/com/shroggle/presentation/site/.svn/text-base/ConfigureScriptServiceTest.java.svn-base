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
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author dmitry.solomadin
 */
@RunWith(TestRunnerWithMockServices.class)
public class ConfigureScriptServiceTest {

    private ConfigureScriptService service = new ConfigureScriptService();

    @Before
    public void before(){
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void showFromSiteEditPage() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widgetScript = TestUtil.createWidgetScript(pageVersion);

        service.execute(widgetScript.getWidgetId(), null);
        Assert.assertEquals(widgetScript.getDraftItem().getId(), service.getDraftScript().getId());
        Assert.assertNotNull(service.getWidgetTitle());
    }

    @Test
    public void showFromManageItems() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftScript draftScript = new DraftScript();
        ServiceLocator.getPersistance().putItem(draftScript);

        service.execute(null, draftScript.getId());
        Assert.assertEquals(draftScript.getId(), service.getDraftScript().getId());
        Assert.assertNull(service.getWidgetTitle());
    }

    @Test(expected = UserNotLoginedException.class)
    public void showWithoutLogin() throws Exception {
        WidgetItem widgetText = TestUtil.createTextWidget();

        service.execute(widgetText.getWidgetId(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void showWithNotFoundScriptItem() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        service.execute(null, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void showWithWidgetWithoutItem() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widgetScript = TestUtil.createWidgetScript(pageVersion);
        widgetScript.setDraftItem(null);

        service.execute(widgetScript.getWidgetId(), null);
    }

}
