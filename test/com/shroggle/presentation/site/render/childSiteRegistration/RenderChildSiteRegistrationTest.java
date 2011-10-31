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
import com.shroggle.presentation.site.render.RenderContext;
import net.sourceforge.stripes.mock.MockHttpServletRequest;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import java.util.Arrays;

import junit.framework.Assert;

/**
 * @author Balakirev Anatoliy
 *         Date: 13.08.2009
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class RenderChildSiteRegistrationTest {

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) new RenderChildSiteRegistrationService().getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        context = new RenderChildSiteRegistrationService().createRenderContext(false);
    }

    @Test
    public void testExecute_withLoginedUser() throws IOException, ServletException {
        User user = TestUtil.createUserAndLogin("");
        DraftChildSiteRegistration form = TestUtil.createChildSiteRegistration("", "");
        FilledForm filledForm = TestUtil.createFilledForm(form.getFormId());
        filledForm.setUser(user);
        List<FilledFormItem> items = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME, FormItemName.ADDRESS, FormItemName.YOUR_OWN_DOMAIN_NAME);
        filledForm.setFilledFormItems(items);
        Widget widget = TestUtil.createWidgetChildSiteRegistration(form.getFormId());


        RenderChildSiteRegistration.execute(widget, context);


        final Integer widgetId = (Integer) context.getRequest().getAttribute("widgetId");
        final FormData formFromRequest = (FormData) context.getRequest().getAttribute("form");
        final List<FilledForm> filledForms = (List<FilledForm>) context.getRequest().getAttribute("filledForms");

        Assert.assertNotNull(widgetId);
        Assert.assertEquals(widget.getWidgetId(), widgetId.intValue());
        Assert.assertNotNull(formFromRequest);
        Assert.assertEquals(formFromRequest.getFormId(), form.getFormId());
        Assert.assertNotNull(filledForms);
        Assert.assertEquals(filledForms, Arrays.asList(filledForm));
    }


    @Test
    public void testExecute_withLoginedUser_withFormWithoutChildSiteSettingsId() throws IOException, ServletException {
        User user = TestUtil.createUserAndLogin("");
        DraftChildSiteRegistration form = TestUtil.createChildSiteRegistration("", "");
        FilledForm filledForm = TestUtil.createFilledForm(form.getFormId());
        filledForm.setChildSiteSettingsId(null);
        filledForm.setUser(user);
        List<FilledFormItem> items = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME, FormItemName.ADDRESS, FormItemName.YOUR_OWN_DOMAIN_NAME);
        filledForm.setFilledFormItems(items);
        Widget widget = TestUtil.createWidgetChildSiteRegistration(form.getFormId());


        RenderChildSiteRegistration.execute(widget, context);


        final Integer widgetId = (Integer) context.getRequest().getAttribute("widgetId");
        final FormData formFromRequest = (FormData) context.getRequest().getAttribute("form");
        final List<FilledForm> filledForms = (List<FilledForm>) context.getRequest().getAttribute("filledForms");

        Assert.assertNotNull(widgetId);
        Assert.assertEquals(widget.getWidgetId(), widgetId.intValue());
        Assert.assertNotNull(formFromRequest);
        Assert.assertEquals(formFromRequest.getFormId(), form.getFormId());
        Assert.assertNotNull(filledForms);
        Assert.assertEquals(filledForms, Arrays.asList(filledForm));
    }


    @Test
    public void testExecute_withLoginedUser_withFormWithChildSiteSettingsIdButWithoutChildSiteSettings() throws IOException, ServletException {
        User user = TestUtil.createUserAndLogin("");
        DraftChildSiteRegistration form = TestUtil.createChildSiteRegistration("", "");
        FilledForm filledForm = TestUtil.createFilledForm(form.getFormId());
        filledForm.setChildSiteSettingsId(123);
        filledForm.setUser(user);
        List<FilledFormItem> items = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME, FormItemName.ADDRESS, FormItemName.YOUR_OWN_DOMAIN_NAME);
        filledForm.setFilledFormItems(items);
        Widget widget = TestUtil.createWidgetChildSiteRegistration(form.getFormId());


        RenderChildSiteRegistration.execute(widget, context);


        final Integer widgetId = (Integer) context.getRequest().getAttribute("widgetId");
        final FormData formFromRequest = (FormData) context.getRequest().getAttribute("form");
        final List<FilledForm> filledForms = (List<FilledForm>) context.getRequest().getAttribute("filledForms");

        Assert.assertNotNull(widgetId);
        Assert.assertEquals(widget.getWidgetId(), widgetId.intValue());
        Assert.assertNotNull(formFromRequest);
        Assert.assertEquals(formFromRequest.getFormId(), form.getFormId());
        Assert.assertNotNull(filledForms);
        Assert.assertEquals(0, filledForms.size());
    }


    @Test
    public void testExecute_withLoginedUser_withoutItemIdInWidhet() throws IOException, ServletException {
        User user = TestUtil.createUserAndLogin("");
        DraftChildSiteRegistration form = TestUtil.createChildSiteRegistration("", "");
        FilledForm filledForm = TestUtil.createFilledForm(form.getFormId());
        filledForm.setUser(user);
        List<FilledFormItem> items = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME, FormItemName.ADDRESS, FormItemName.YOUR_OWN_DOMAIN_NAME);
        filledForm.setFilledFormItems(items);
        Widget widget = TestUtil.createWidgetChildSiteRegistration(null);


        RenderChildSiteRegistration.execute(widget, context);


        final Integer widgetId = (Integer) context.getRequest().getAttribute("widgetId");
        final DraftForm formFromRequest = (DraftForm) context.getRequest().getAttribute("form");
        final List<FilledForm> filledForms = (List<FilledForm>) context.getRequest().getAttribute("filledForms");

        Assert.assertNull(widgetId);
        Assert.assertNull(formFromRequest);
        Assert.assertNull(filledForms);
    }

    @Test
    public void testExecute_withoutLoginedUser() throws IOException, ServletException {
        DraftChildSiteRegistration form = TestUtil.createChildSiteRegistration("", "");
        form.setSiteId(1);
        FilledForm filledForm = TestUtil.createFilledForm(form.getFormId());
        List<FilledFormItem> items = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME, FormItemName.ADDRESS, FormItemName.YOUR_OWN_DOMAIN_NAME);
        filledForm.setFilledFormItems(items);
        Widget widget = TestUtil.createWidgetChildSiteRegistration(form.getFormId());


        RenderChildSiteRegistration.execute(widget, context);


        final Integer widgetId = (Integer) context.getRequest().getAttribute("widgetId");
        final int siteId = (Integer) context.getRequest().getAttribute("siteId");
        final FormData formFromRequest = (FormData) context.getRequest().getAttribute("form");
        final User user = (User) context.getRequest().getAttribute("user");

        Assert.assertNotNull(widgetId);
        Assert.assertEquals(widget.getWidgetId(), widgetId.intValue());
        Assert.assertNotNull(formFromRequest);
        Assert.assertEquals(formFromRequest.getFormId(), form.getFormId());
        Assert.assertNotNull(siteId);
        Assert.assertEquals(form.getSiteId(), siteId);

        Assert.assertNull(user);
    }

    @Test
    public void testExecute_withoutLoginedUser_withoutItemIdInWidhet() throws IOException, ServletException {
        DraftChildSiteRegistration form = TestUtil.createChildSiteRegistration("", "");
        form.setSiteId(1);
        FilledForm filledForm = TestUtil.createFilledForm(form.getFormId());
        List<FilledFormItem> items = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME, FormItemName.ADDRESS, FormItemName.YOUR_OWN_DOMAIN_NAME);
        filledForm.setFilledFormItems(items);
        Widget widget = TestUtil.createWidgetChildSiteRegistration(null);


        RenderChildSiteRegistration.execute(widget, context);


        final Integer widgetId = (Integer) context.getRequest().getAttribute("widgetId");
        final Integer siteId = (Integer) context.getRequest().getAttribute("siteId");
        final DraftForm formFromRequest = (DraftForm) context.getRequest().getAttribute("form");
        final User user = (User) context.getRequest().getAttribute("user");

        Assert.assertNull(widgetId);
        Assert.assertNull(formFromRequest);
        Assert.assertNull(siteId);
        Assert.assertNull(user);
    }


    private RenderContext context;
}