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

import com.shroggle.logic.site.page.PageManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.presentation.site.render.customForm.SubmitCustomFormService;
import com.shroggle.presentation.site.render.customForm.SubmitCustomFormRequest;
import com.shroggle.presentation.site.render.customForm.SubmitCustomFormResponse;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.exception.WrongVerificationCodeException;
import com.shroggle.util.ServiceLocator;
import com.shroggle.entity.*;
import net.sourceforge.stripes.mock.MockHttpServletRequest;

import java.util.List;
import java.util.ArrayList;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class SubmitCustomFormServiceTest {
    private SubmitCustomFormService service = new SubmitCustomFormService();

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void execute() throws Exception {
        TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();

        final PageManager pageVersion = TestUtil.createPageVersionSiteUser();
        Widget widgetCustomForm = TestUtil.createCustomFormWidget(pageVersion);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);

        final List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME);
        filledFormItems.get(0).setValue("FN1");
        filledFormItems.get(1).setValue("LN1");

        final SubmitCustomFormRequest request = new SubmitCustomFormRequest();
        request.setFormId(customForm.getFormId());
        request.setWidgetId(widgetCustomForm.getWidgetId());
        request.setFilledFormItems(new ArrayList<FilledFormItem>());
        request.setVerificationCode("aaaaaa");
        request.setFilledFormItems(filledFormItems);
        ServiceLocator.getSessionStorage().setNoBotCode(null, "customForm" + widgetCustomForm.getWidgetId(), "aaaaaa");

        final SubmitCustomFormResponse response = service.execute(request);

        Assert.assertNull(response.getNextPageHtml());

        final FilledForm filledCustomForm = ServiceLocator.getPersistance().getFilledFormById(response.getFilledFormId());
        Assert.assertNotNull(filledCustomForm);
        Assert.assertEquals(filledFormItems.get(0).getValues(), filledCustomForm.getFilledFormItems().get(0).getValues());
        Assert.assertEquals(filledFormItems.get(1).getValues(), filledCustomForm.getFilledFormItems().get(1).getValues());
    }

    @Test
    public void executeWithPageBreaks() throws Exception {
        TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();

        final PageManager pageVersion = TestUtil.createPageVersionSiteUser();
        Widget widgetCustomForm = TestUtil.createCustomFormWidget(pageVersion);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);

        final List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        formItems.add(TestUtil.createFormItem(FormItemName.FIRST_NAME, 0));
        formItems.add(TestUtil.createFormItem(FormItemName.PAGE_BREAK, 1));
        formItems.add(TestUtil.createFormItem(FormItemName.LAST_NAME, 2));
        customForm.setFormItems(formItems);

        //FIRST PAGE
        List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME);
        filledFormItems.get(0).setValue("FN1");

        SubmitCustomFormRequest request = new SubmitCustomFormRequest();
        request.setFormId(customForm.getFormId());
        request.setWidgetId(widgetCustomForm.getWidgetId());
        request.setFilledFormItems(new ArrayList<FilledFormItem>());
        request.setRequestNextPage(true);
        request.setPageBreaksToPass(1);
        request.setVerificationCode("aaaaaa");
        request.setFilledFormItems(filledFormItems);
        ServiceLocator.getSessionStorage().setNoBotCode(null, "customForm" + widgetCustomForm.getWidgetId(), "aaaaaa");

        SubmitCustomFormResponse response = service.execute(request);

        Assert.assertNotNull(response.getNextPageHtml());

        FilledForm filledContactUsForm = ServiceLocator.getPersistance().getFilledFormById(response.getFilledFormId());
        Assert.assertNotNull(filledContactUsForm);
        Assert.assertEquals(filledFormItems.get(0).getValues(), filledContactUsForm.getFilledFormItems().get(0).getValues());

        //SECOND PAGE
        filledFormItems = TestUtil.createFilledFormItems(FormItemName.LAST_NAME);
        filledFormItems.get(0).setValue("LN1");

        request = new SubmitCustomFormRequest();
        request.setFormId(customForm.getFormId());
        request.setWidgetId(widgetCustomForm.getWidgetId());
        request.setFilledFormItems(new ArrayList<FilledFormItem>());
        request.setRequestNextPage(true);
        request.setFilledFormId(response.getFilledFormId());
        request.setPageBreaksToPass(2);
        request.setFilledFormItems(filledFormItems);

        response = service.execute(request);

        //Ok, now after we have filled out last form page we need to show first form page again with the
        //message about successfull form submit.
        Assert.assertNotNull(response.getNextPageHtml());
        Assert.assertTrue(response.isShowSuccessfullSubmitMessage());

        filledContactUsForm = ServiceLocator.getPersistance().getFilledFormById(response.getFilledFormId());
        Assert.assertEquals(filledFormItems.get(0).getValues(), filledContactUsForm.getFilledFormItems().get(1).getValues());
    }

    @Test
    public void executeWithPageBreaksFromAddRecord() throws Exception {
        TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();

        final DraftCustomForm customForm = TestUtil.createCustomForm(site);

        final List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        formItems.add(TestUtil.createFormItem(FormItemName.FIRST_NAME, 0));
        formItems.add(TestUtil.createFormItem(FormItemName.PAGE_BREAK, 1));
        formItems.add(TestUtil.createFormItem(FormItemName.LAST_NAME, 2));
        customForm.setFormItems(formItems);

        //FIRST PAGE
        List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME);
        filledFormItems.get(0).setValue("FN1");

        SubmitCustomFormRequest request = new SubmitCustomFormRequest();
        request.setFormId(customForm.getFormId());
        request.setWidgetId(0);
        request.setFilledFormItems(new ArrayList<FilledFormItem>());
        request.setRequestNextPage(true);
        request.setPageBreaksToPass(1);
        request.setVerificationCode("aaaaaa");
        request.setFilledFormItems(filledFormItems);
        request.setShowFromAddRecord(true);

        SubmitCustomFormResponse response = service.execute(request);
        Assert.assertEquals("/site/showCustomForm.action?formId=" + request.getFormId()
                        + "&showFromAddRecord=true"
                        + "&pageBreaksToPass=" + request.getPageBreaksToPass()
                        + "&filledFormToUpdateId=" + response.getFilledFormId(), response.getNextPageHtml());

        FilledForm filledContactUsForm = ServiceLocator.getPersistance().getFilledFormById(response.getFilledFormId());
        Assert.assertNotNull(filledContactUsForm);
        Assert.assertEquals(filledFormItems.get(0).getValues(), filledContactUsForm.getFilledFormItems().get(0).getValues());

        //SECOND PAGE
        filledFormItems = TestUtil.createFilledFormItems(FormItemName.LAST_NAME);
        filledFormItems.get(0).setValue("LN1");

        request = new SubmitCustomFormRequest();
        request.setFormId(customForm.getFormId());
        request.setWidgetId(0);
        request.setFilledFormItems(new ArrayList<FilledFormItem>());
        request.setShowFromAddRecord(true);
        request.setRequestNextPage(true);
        request.setFilledFormId(response.getFilledFormId());
        request.setPageBreaksToPass(2);
        request.setFilledFormItems(filledFormItems);

        response = service.execute(request);

        Assert.assertNull(response.getNextPageHtml());

        filledContactUsForm = ServiceLocator.getPersistance().getFilledFormById(response.getFilledFormId());
        Assert.assertEquals(filledFormItems.get(0).getValues(), filledContactUsForm.getFilledFormItems().get(1).getValues());
    }

    @Test(expected = WrongVerificationCodeException.class)
    public void executeWithWrongVerificationCode() throws Exception {
        TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();

        final PageManager pageVersion = TestUtil.createPageVersionSiteUser();
        Widget widgetCustomForm = TestUtil.createCustomFormWidget(pageVersion);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);

        final List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME);
        filledFormItems.get(0).setValue("FN1");
        filledFormItems.get(1).setValue("LN1");

        final SubmitCustomFormRequest request = new SubmitCustomFormRequest();
        request.setFormId(customForm.getFormId());
        request.setWidgetId(widgetCustomForm.getWidgetId());
        request.setFilledFormItems(new ArrayList<FilledFormItem>());
        request.setFilledFormItems(filledFormItems);
        ServiceLocator.getSessionStorage().setNoBotCode(null, "customForm" + widgetCustomForm.getWidgetId(), "aaaaaa");

        service.execute(request);
    }
    
    @Test(expected = WrongVerificationCodeException.class)
    public void executeWithoutCustomForm() throws Exception {
        TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();

        final PageManager pageVersion = TestUtil.createPageVersionSiteUser();
        Widget widgetCustomForm = TestUtil.createCustomFormWidget(pageVersion);

        final List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME);
        filledFormItems.get(0).setValue("FN1");
        filledFormItems.get(1).setValue("LN1");

        final SubmitCustomFormRequest request = new SubmitCustomFormRequest();
        request.setWidgetId(widgetCustomForm.getWidgetId());
        request.setFilledFormItems(new ArrayList<FilledFormItem>());
        request.setFilledFormItems(filledFormItems);
        ServiceLocator.getSessionStorage().setNoBotCode(null, "customForm" + widgetCustomForm.getWidgetId(), "aaaaaa");

        service.execute(request);
    }

}
