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

package com.shroggle.presentation.site.render;

import com.shroggle.logic.site.page.PageManager;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.junit.Test;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.presentation.site.forms.FormPageAdditionalParameters;
import com.shroggle.entity.*;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import net.sourceforge.stripes.mock.MockHttpServletResponse;
import net.sourceforge.stripes.mock.MockServletContext;

import javax.servlet.ServletException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class RenderFormPageBreakTest {

    RenderFormPageBreak renderFormPageBreak = new RenderFormPageBreak();

    @Test(expected = WidgetNotFoundException.class)
    public void testExecuteWithoutWidget() throws IOException, ServletException {
        final RenderContext renderContext = new RenderContext(new MockHttpServletRequest("", ""),
                new MockHttpServletResponse(), new MockServletContext(""), new HashMap<ItemType, String>(), false);

        renderFormPageBreak.execute(0, -1, 0, 0, new ArrayList<FormPageAdditionalParameters>(), renderContext);
    }

    @Test
    public void testExecute_CUSTOM_FORM() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

        final Widget widget = TestUtil.createWidgetCustomForm(customForm.getFormId());
        new PageManager(TestUtil.createPage(site)).addWidget(widget);

        final RenderContext renderContext = new RenderContext(new MockHttpServletRequest("", ""),
                new MockHttpServletResponse(), new MockServletContext(""), new HashMap<ItemType, String>(), false);

        final List<FormPageAdditionalParameters> customParameters = new ArrayList<FormPageAdditionalParameters>();
        final FormPageAdditionalParameters customParameter1 = new FormPageAdditionalParameters();
        customParameter1.setParameterName("customParameter");
        customParameter1.setParameterValue("customValue");
        customParameters.add(customParameter1);
        final FormPageAdditionalParameters customParameter2 = new FormPageAdditionalParameters();
        customParameter2.setParameterName("null");
        customParameter2.setParameterValue("customValue");
        customParameters.add(customParameter2);
        final FormPageAdditionalParameters customParameter3 = new FormPageAdditionalParameters();
        customParameter3.setParameterName("customParameter");
        customParameter3.setParameterValue("null");
        customParameters.add(customParameter3);
        final FormPageAdditionalParameters customParameter4 = new FormPageAdditionalParameters();
        customParameter4.setParameterName(null);
        customParameter4.setParameterValue("customValue");
        customParameters.add(customParameter4);
        final FormPageAdditionalParameters customParameter5 = new FormPageAdditionalParameters();
        customParameter5.setParameterName("customParameter");
        customParameter5.setParameterValue(null);
        customParameters.add(customParameter5);

        renderFormPageBreak.execute(widget.getWidgetId(), customForm.getFormId(), 1, filledForm.getFilledFormId(),
                customParameters, renderContext);
        Assert.assertEquals("&pageBreaksToPass=1&filledFormToUpdateId=1&customParameter=customValue",
                renderContext.getParameterMap().get(ItemType.CUSTOM_FORM));
    }

    @Test
    public void testExecute_CONTACT_US() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        final DraftContactUs contactUs = TestUtil.createContactUs(site);
        final FilledForm filledForm = TestUtil.createFilledForm(contactUs);
        final Widget widget = TestUtil.createWidgetCustomForm(contactUs.getFormId());
        new PageManager(TestUtil.createPage(site)).addWidget(widget);
        final RenderContext renderContext = new RenderContext(new MockHttpServletRequest("", ""),
                new MockHttpServletResponse(), new MockServletContext(""), new HashMap<ItemType, String>(), false);

        final List<FormPageAdditionalParameters> customParameters = new ArrayList<FormPageAdditionalParameters>();
        final FormPageAdditionalParameters customParameter1 = new FormPageAdditionalParameters();
        customParameter1.setParameterName("customParameter");
        customParameter1.setParameterValue("customValue");
        customParameters.add(customParameter1);
        final FormPageAdditionalParameters customParameter2 = new FormPageAdditionalParameters();
        customParameter2.setParameterName("null");
        customParameter2.setParameterValue("customValue");
        customParameters.add(customParameter2);
        final FormPageAdditionalParameters customParameter3 = new FormPageAdditionalParameters();
        customParameter3.setParameterName("customParameter");
        customParameter3.setParameterValue("null");
        customParameters.add(customParameter3);
        final FormPageAdditionalParameters customParameter4 = new FormPageAdditionalParameters();
        customParameter4.setParameterName(null);
        customParameter4.setParameterValue("customValue");
        customParameters.add(customParameter4);
        final FormPageAdditionalParameters customParameter5 = new FormPageAdditionalParameters();
        customParameter5.setParameterName("customParameter");
        customParameter5.setParameterValue(null);
        customParameters.add(customParameter5);

        renderFormPageBreak.execute(widget.getWidgetId(), contactUs.getFormId(), 1, filledForm.getFilledFormId(),
                customParameters, renderContext);
        Assert.assertEquals("&pageBreaksToPass=1&filledFormToUpdateId=1&customParameter=customValue",
                renderContext.getParameterMap().get(ItemType.CONTACT_US));
    }

    @Test
    public void testExecute_REGISTRATION_FORM() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm(site);
        final FilledForm filledForm = TestUtil.createFilledForm(registrationForm);
        final Widget widget = TestUtil.createWidgetCustomForm(registrationForm.getFormId());
        new PageManager(TestUtil.createPage(site)).addWidget(widget);
        final RenderContext renderContext = new RenderContext(new MockHttpServletRequest("", ""),
                new MockHttpServletResponse(), new MockServletContext(""), new HashMap<ItemType, String>(), false);

        final List<FormPageAdditionalParameters> customParameters = new ArrayList<FormPageAdditionalParameters>();
        final FormPageAdditionalParameters customParameter1 = new FormPageAdditionalParameters();
        customParameter1.setParameterName("customParameter");
        customParameter1.setParameterValue("customValue");
        customParameters.add(customParameter1);
        final FormPageAdditionalParameters customParameter2 = new FormPageAdditionalParameters();
        customParameter2.setParameterName("null");
        customParameter2.setParameterValue("customValue");
        customParameters.add(customParameter2);
        final FormPageAdditionalParameters customParameter3 = new FormPageAdditionalParameters();
        customParameter3.setParameterName("customParameter");
        customParameter3.setParameterValue("null");
        customParameters.add(customParameter3);
        final FormPageAdditionalParameters customParameter4 = new FormPageAdditionalParameters();
        customParameter4.setParameterName(null);
        customParameter4.setParameterValue("customValue");
        customParameters.add(customParameter4);
        final FormPageAdditionalParameters customParameter5 = new FormPageAdditionalParameters();
        customParameter5.setParameterName("customParameter");
        customParameter5.setParameterValue(null);
        customParameters.add(customParameter5);

        renderFormPageBreak.execute(widget.getWidgetId(), registrationForm.getFormId(), 1, filledForm.getFilledFormId(),
                customParameters, renderContext);
        Assert.assertEquals("&pageBreaksToPass=1&filledFormToUpdateId=1&customParameter=customValue",
                renderContext.getParameterMap().get(ItemType.REGISTRATION));
    }

    @Test
    public void testExecute_CHILD_SITE_REGISTRATION() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        final DraftChildSiteRegistration childSiteRegistrationForm = TestUtil.createChildSiteRegistration(site);
        final FilledForm filledForm = TestUtil.createFilledForm(childSiteRegistrationForm);
        final Widget widget = TestUtil.createWidgetCustomForm(childSiteRegistrationForm.getFormId());
        new PageManager(TestUtil.createPage(site)).addWidget(widget);
        final RenderContext renderContext = new RenderContext(new MockHttpServletRequest("", ""),
                new MockHttpServletResponse(), new MockServletContext(""), new HashMap<ItemType, String>(), false);

        final List<FormPageAdditionalParameters> customParameters = new ArrayList<FormPageAdditionalParameters>();
        final FormPageAdditionalParameters customParameter1 = new FormPageAdditionalParameters();
        customParameter1.setParameterName("customParameter");
        customParameter1.setParameterValue("customValue");
        customParameters.add(customParameter1);
        final FormPageAdditionalParameters customParameter2 = new FormPageAdditionalParameters();
        customParameter2.setParameterName("null");
        customParameter2.setParameterValue("customValue");
        customParameters.add(customParameter2);
        final FormPageAdditionalParameters customParameter3 = new FormPageAdditionalParameters();
        customParameter3.setParameterName("customParameter");
        customParameter3.setParameterValue("null");
        customParameters.add(customParameter3);
        final FormPageAdditionalParameters customParameter4 = new FormPageAdditionalParameters();
        customParameter4.setParameterName(null);
        customParameter4.setParameterValue("customValue");
        customParameters.add(customParameter4);
        final FormPageAdditionalParameters customParameter5 = new FormPageAdditionalParameters();
        customParameter5.setParameterName("customParameter");
        customParameter5.setParameterValue(null);
        customParameters.add(customParameter5);

        renderFormPageBreak.execute(widget.getWidgetId(), childSiteRegistrationForm.getFormId(), 1, filledForm.getFilledFormId(),
                customParameters, renderContext);
        Assert.assertEquals("&pageBreaksToPass=1&filledFormToUpdateId=1&customParameter=customValue",
                renderContext.getParameterMap().get(ItemType.CHILD_SITE_REGISTRATION));
    }

    @Test
    public void testExecuteForAddRecord_FOR_ALL_FORM_TYPES() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

        final List<FormPageAdditionalParameters> customParameters = new ArrayList<FormPageAdditionalParameters>();
        final FormPageAdditionalParameters customParameter1 = new FormPageAdditionalParameters();
        customParameter1.setParameterName("customParameter");
        customParameter1.setParameterValue("customValue");
        customParameters.add(customParameter1);
        final FormPageAdditionalParameters customParameter2 = new FormPageAdditionalParameters();
        customParameter2.setParameterName("null");
        customParameter2.setParameterValue("customValue");
        customParameters.add(customParameter2);
        final FormPageAdditionalParameters customParameter3 = new FormPageAdditionalParameters();
        customParameter3.setParameterName("customParameter");
        customParameter3.setParameterValue("null");
        customParameters.add(customParameter3);
        final FormPageAdditionalParameters customParameter4 = new FormPageAdditionalParameters();
        customParameter4.setParameterName(null);
        customParameter4.setParameterValue("customValue");
        customParameters.add(customParameter4);
        final FormPageAdditionalParameters customParameter5 = new FormPageAdditionalParameters();
        customParameter5.setParameterName("customParameter");
        customParameter5.setParameterValue(null);
        customParameters.add(customParameter5);

        String response = renderFormPageBreak.executeForAddRecord(ItemType.CUSTOM_FORM, 1,
                filledForm.getFilledFormId(), customForm.getFormId(), customParameters);
        Assert.assertEquals("/site/showCustomForm.action?formId=457&showFromAddRecord=true&pageBreaksToPass=1" +
                "&filledFormToUpdateId=1&customParameter=customValue", response);

        response = renderFormPageBreak.executeForAddRecord(ItemType.CHILD_SITE_REGISTRATION, 1,
                filledForm.getFilledFormId(), customForm.getFormId(), customParameters);
        Assert.assertEquals("/site/showChildSiteRegistration.action?formId=457&showFromAddRecord=true&pageBreaksToPass=1" +
                "&filledFormToUpdateId=1&customParameter=customValue", response);

        response = renderFormPageBreak.executeForAddRecord(ItemType.REGISTRATION, 1,
                filledForm.getFilledFormId(), customForm.getFormId(), customParameters);
        Assert.assertEquals("/site/showRegistrationForm.action?formId=457&showFromAddRecord=true&pageBreaksToPass=1" +
                "&filledFormToUpdateId=1&customParameter=customValue", response);

        response = renderFormPageBreak.executeForAddRecord(ItemType.CONTACT_US, 1,
                filledForm.getFilledFormId(), customForm.getFormId(), customParameters);
        Assert.assertEquals("/site/showContactUs.action?formId=457&showFromAddRecord=true&pageBreaksToPass=1" +
                "&filledFormToUpdateId=1&customParameter=customValue", response);
    }
    
    @Test
    public void testResetForAddRecord_FOR_ALL_FORM_TYPES() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);

        String response = renderFormPageBreak.resetForAddRecord(ItemType.CUSTOM_FORM, customForm.getFormId());
        Assert.assertEquals("/site/showCustomForm.action?formId=457&showFromAddRecord=true&cameFromReset=true", response);

        response = renderFormPageBreak.resetForAddRecord(ItemType.CHILD_SITE_REGISTRATION, customForm.getFormId());
        Assert.assertEquals("/site/showChildSiteRegistration.action?formId=457&showFromAddRecord=true&cameFromReset=true", response);

        response = renderFormPageBreak.resetForAddRecord(ItemType.REGISTRATION, customForm.getFormId());
        Assert.assertEquals("/site/showRegistrationForm.action?formId=457&showFromAddRecord=true&cameFromReset=true", response);

        response = renderFormPageBreak.resetForAddRecord(ItemType.CONTACT_US, customForm.getFormId());
        Assert.assertEquals("/site/showContactUs.action?formId=457&showFromAddRecord=true&cameFromReset=true", response);
    }
    
    @Test
    public void testReset_CUSTOM_FORM() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final Widget widget = TestUtil.createWidgetCustomForm(customForm.getFormId());
        new PageManager(TestUtil.createPage(site)).addWidget(widget);
        final RenderContext renderContext = new RenderContext(new MockHttpServletRequest("", ""),
                new MockHttpServletResponse(), new MockServletContext(""), new HashMap<ItemType, String>(), false);

        renderFormPageBreak.reset(widget.getWidgetId(), customForm.getFormId(), renderContext);
        Assert.assertEquals("&cameFromReset=true", renderContext.getParameterMap().get(ItemType.CUSTOM_FORM));
    }

    @Test
    public void testReset_CONTACT_US() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        final DraftContactUs contactUsForm = TestUtil.createContactUs(site);
        final Widget widget = TestUtil.createWidgetCustomForm(contactUsForm.getFormId());
        new PageManager(TestUtil.createPage(site)).addWidget(widget);
        final RenderContext renderContext = new RenderContext(new MockHttpServletRequest("", ""),
                new MockHttpServletResponse(), new MockServletContext(""), new HashMap<ItemType, String>(), false);

        renderFormPageBreak.reset(widget.getWidgetId(), contactUsForm.getFormId(), renderContext);
        Assert.assertEquals("&cameFromReset=true", renderContext.getParameterMap().get(ItemType.CONTACT_US));
    }

    @Test
    public void testReset_REGISTRATION_FORM() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm(site);
        final Widget widget = TestUtil.createWidgetCustomForm(registrationForm.getFormId());
        new PageManager(TestUtil.createPage(site)).addWidget(widget);
        final RenderContext renderContext = new RenderContext(new MockHttpServletRequest("", ""),
                new MockHttpServletResponse(), new MockServletContext(""), new HashMap<ItemType, String>(), false);

        renderFormPageBreak.reset(widget.getWidgetId(), registrationForm.getFormId(), renderContext);
        Assert.assertEquals("&cameFromReset=true", renderContext.getParameterMap().get(ItemType.REGISTRATION));
    }

    @Test
    public void testReset_CHILD_SITE_REGISTRATION() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        final DraftChildSiteRegistration childSiteRegistrationForm = TestUtil.createChildSiteRegistration(site);
        final Widget widget = TestUtil.createWidgetCustomForm(childSiteRegistrationForm.getFormId());
        new PageManager(TestUtil.createPage(site)).addWidget(widget);
        final RenderContext renderContext = new RenderContext(new MockHttpServletRequest("", ""),
                new MockHttpServletResponse(), new MockServletContext(""), new HashMap<ItemType, String>(), false);

        renderFormPageBreak.reset(widget.getWidgetId(), childSiteRegistrationForm.getFormId(), renderContext);
        Assert.assertEquals("&cameFromReset=true", renderContext.getParameterMap().get(ItemType.CHILD_SITE_REGISTRATION));
    }

}
