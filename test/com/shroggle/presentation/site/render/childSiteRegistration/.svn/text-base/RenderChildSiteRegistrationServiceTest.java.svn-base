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
package com.shroggle.presentation.site.render.childSiteRegistration;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.logic.form.FormData;
import com.shroggle.entity.*;
import com.shroggle.presentation.MockWebContext;
import net.sourceforge.stripes.mock.MockHttpServletRequest;

import javax.servlet.ServletException;
import java.io.IOException;

import junit.framework.Assert;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class RenderChildSiteRegistrationServiceTest {

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void testShowForm() throws IOException, ServletException {
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("", "");
        Widget widget = TestUtil.createWidgetChildSiteRegistration(childSiteRegistration.getFormId());

        service.showForm(widget.getWidgetId());

        final Integer widgetId = (Integer) service.getContext().getHttpServletRequest().getAttribute("widgetId");
        final int siteId = (Integer) service.getContext().getHttpServletRequest().getAttribute("siteId");
        final FormData form = (FormData) service.getContext().getHttpServletRequest().getAttribute("form");
        final User user = (User) service.getContext().getHttpServletRequest().getAttribute("user");

        Assert.assertNotNull(widgetId);
        Assert.assertEquals(widget.getWidgetId(), widgetId.intValue());
        Assert.assertNotNull(form);
        Assert.assertEquals(childSiteRegistration.getSiteId(), siteId);
        Assert.assertNull(user);
        Assert.assertEquals(childSiteRegistration.getFormId(), form.getFormId());

        Assert.assertNotNull(service.getContext().getHttpServletRequest().getAttribute("form"));
        Assert.assertNull(service.getContext().getHttpServletRequest().getAttribute("prefilledForm"));
        Assert.assertEquals(widgetId, service.getContext().getHttpServletRequest().getAttribute("widgetId"));
        Assert.assertNull(service.getContext().getHttpServletRequest().getAttribute("loginedUser"));
        Assert.assertNull(service.getContext().getHttpServletRequest().getAttribute("forcePrefill"));
        Assert.assertEquals(siteId, service.getContext().getHttpServletRequest().getAttribute("siteId"));
    }

    @Test
    public void testShowForm_withLoginedVisitor() throws IOException, ServletException {
        User user = TestUtil.createUserAndLogin("");
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("", "");
        Widget widget = TestUtil.createWidgetChildSiteRegistration(childSiteRegistration.getFormId());

        service.showForm(widget.getWidgetId());

        final Integer widgetId = (Integer) service.getContext().getHttpServletRequest().getAttribute("widgetId");
        final int siteId = (Integer) service.getContext().getHttpServletRequest().getAttribute("siteId");
        final FormData form = (FormData) service.getContext().getHttpServletRequest().getAttribute("form");
        final User userFromRequest = (User) service.getContext().getHttpServletRequest().getAttribute("loginedUser");

        Assert.assertNotNull(widgetId);
        Assert.assertEquals(widget.getWidgetId(), widgetId.intValue());
        Assert.assertNotNull(form);
        Assert.assertEquals(childSiteRegistration.getSiteId(), siteId);
        Assert.assertNotNull(userFromRequest);
        Assert.assertEquals(user, userFromRequest);

        Assert.assertEquals(childSiteRegistration.getFormId(), form.getFormId());
        Assert.assertNotNull(service.getContext().getHttpServletRequest().getAttribute("form"));
        Assert.assertNull(service.getContext().getHttpServletRequest().getAttribute("prefilledForm"));
        Assert.assertEquals(widgetId, service.getContext().getHttpServletRequest().getAttribute("widgetId"));
        Assert.assertNotNull(service.getContext().getHttpServletRequest().getAttribute("loginedUser"));
        Assert.assertNull(service.getContext().getHttpServletRequest().getAttribute("forcePrefill"));
        Assert.assertEquals(siteId, service.getContext().getHttpServletRequest().getAttribute("siteId"));
    }

    @Test
    public void testEditFilledForm() throws IOException, ServletException {
        User user = TestUtil.createUserAndLogin("");
        DraftChildSiteRegistration form = TestUtil.createChildSiteRegistration("", "");
        Widget widget = TestUtil.createWidgetChildSiteRegistration(form.getFormId());

        FilledForm filledForm = TestUtil.createFilledForm(form.getFormId());
        filledForm.setUser(user);

        service.editFilledForm(widget.getWidgetId(), filledForm.getFilledFormId());

        Assert.assertNotNull(service.getContext().getHttpServletRequest().getAttribute("form"));
        Assert.assertEquals(filledForm.getFilledFormId(), service.getContext().getHttpServletRequest().getAttribute("filledFormId"));
        Assert.assertEquals(widget.getWidgetId(), service.getContext().getHttpServletRequest().getAttribute("widgetId"));
        Assert.assertNotNull(service.getContext().getHttpServletRequest().getAttribute("loginedUser"));
        Assert.assertEquals(user, service.getContext().getHttpServletRequest().getAttribute("loginedUser"));
        Assert.assertEquals(form.getSiteId(), service.getContext().getHttpServletRequest().getAttribute("siteId"));

        final Boolean editDetails = (Boolean) service.getContext().getHttpServletRequest().getAttribute("editDetails");
        final RenderChildSiteRegistrationService serviceFromRequest = (RenderChildSiteRegistrationService) service.getContext().getHttpServletRequest().getAttribute("service");

        Assert.assertNotNull(editDetails);
        Assert.assertNotNull(serviceFromRequest);

        Assert.assertTrue(editDetails);
        Assert.assertEquals(service, serviceFromRequest);
    }

    private final RenderChildSiteRegistrationService service = new RenderChildSiteRegistrationService();
}
