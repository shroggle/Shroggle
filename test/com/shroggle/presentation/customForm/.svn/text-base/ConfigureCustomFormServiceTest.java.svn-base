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
package com.shroggle.presentation.customForm;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.CustomFormNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Author: dmitry.solomadin
 * Date: 08.02.2009
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class ConfigureCustomFormServiceTest {

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ConfigureCustomFormService service = new ConfigureCustomFormService();

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void showFromSiteEditPage() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        DraftCustomForm customForm = TestUtil.createCustomForm(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);
        widget.setDraftItem(customForm);

        service.show(widget.getWidgetId(), null);
        Assert.assertEquals(site, service.getSite());
        Assert.assertNotNull(service.getInitFormItems());
        Assert.assertFalse(service.getInitFormItems().isEmpty());
        Assert.assertNotNull(service.getExistingFormItems());
        Assert.assertFalse(service.getExistingFormItems().isEmpty());
        Assert.assertEquals(widget, service.getWidgetCustomForm());
        Assert.assertEquals(customForm.getId(), service.getSelectedCustomForm().getId());
    }

    @Test
    public void showFromManageItems() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftCustomForm customForm = TestUtil.createCustomForm(site);

        service.show(null, customForm.getFormId());
        Assert.assertEquals(customForm.getSiteId(), service.getSite().getSiteId());
        Assert.assertEquals(null, service.getWidgetCustomForm());
        Assert.assertEquals(customForm, service.getSelectedCustomForm());
    }

    @Test(expected = CustomFormNotFoundException.class)
    public void showWithNotFoundForm() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        service.show(null, -1);
    }

    @Test(expected = CustomFormNotFoundException.class)
    public void showWithWidgetWithoutItem() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        service.show(widget.getWidgetId(), null);
    }

    @Test(expected = UserNotLoginedException.class)
    public void showWithUserNotLogined() throws Exception {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        service.show(widget.getWidgetId(), null);
    }

    @Test(expected = UserNotLoginedException.class)
    public void showWithNotFoundWidget() throws Exception {
        service.show(-1, null);
    }

    @Test(expected = WidgetNotFoundException.class)
    public void showWithNotMy() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndLogin();
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        service.show(widget.getWidgetId(), null);
    }

    @Test(expected = WidgetNotFoundException.class)
    public void executeWithNotFoundWidget() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        service.show(0, null);
    }

}
