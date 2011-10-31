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
import com.shroggle.entity.DraftRegistrationForm;
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.WidgetItem;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.presentation.TestAction;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(TestRunnerWithMockServices.class)
public class WidgetRegistrationActionTest extends TestAction<WidgetRegistrationAction> {

    public WidgetRegistrationActionTest() {
        super(WidgetRegistrationAction.class, true);
    }

    @Test
    public void execute() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        DraftRegistrationForm registrationForm = new DraftRegistrationForm();
        registrationForm.setSiteId(site.getSiteId());
        registrationForm.setName("formName");
        persistance.putRegistrationForm(registrationForm);
        widget.setDraftItem(registrationForm);

        actionOrService.setWidgetId(widget.getWidgetId());
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.execute();

        Assert.assertEquals("/site/render/widgetRegistration.jsp", resolutionMock.getForwardToUrl());

        Assert.assertEquals(registrationForm.getFormId(), actionOrService.getFormData().getFormId());
    }

    @Test
    public void executeWithFilledInThisSession() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        DraftRegistrationForm registrationForm = new DraftRegistrationForm();
        registrationForm.setSiteId(site.getSiteId());
        registrationForm.setName("formName");
        persistance.putRegistrationForm(registrationForm);
        widget.setDraftItem(registrationForm);

        sessionStorage.setRegistrationFormFilledInThisSession(null, registrationForm.getFormId());

        actionOrService.setWidgetId(widget.getWidgetId());
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.execute();

        Assert.assertEquals("/site/render/widgetRegistration.jsp", resolutionMock.getForwardToUrl());
        Assert.assertEquals(registrationForm.getFormId(), actionOrService.getFormData().getFormId());
        Assert.assertTrue(actionOrService.isFilledInThisSession());
    }

    @Test
    public void executeWithReturnToLoginWithoutRegistrationForm() throws Throwable {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        DraftRegistrationForm registrationForm = new DraftRegistrationForm();
        registrationForm.setName("formName");
        persistance.putRegistrationForm(registrationForm);
        site.setDefaultFormId(0);
        widget.setDraftItem(registrationForm);

        actionOrService.setWidgetId(widget.getWidgetId());
        actionOrService.setReturnToLogin(true);
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.execute();

        Assert.assertEquals(
                "/site/render/renderWidgetNotConfigure.jsp?widgetType=Registration",
                resolutionMock.getForwardToUrl());
    }

}
