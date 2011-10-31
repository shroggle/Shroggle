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
import com.shroggle.exception.WrongVerificationCodeException;
import com.shroggle.exception.ContactUsNotFoundException;
import com.shroggle.entity.*;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.mail.MockMailSender;
import com.shroggle.util.mail.Mail;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;


@RunWith(value = TestRunnerWithMockServices.class)
public class SendContactUsInfoServiceTest {

    private SendContactUsInfoService service = new SendContactUsInfoService();

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void execute() throws Exception {
        TestUtil.createUserAndLogin();
        final WidgetItem widgetContactUs = TestUtil.createContactUsWidget();
        final DraftContactUs contactUs = TestUtil.createContactUsForm();
        widgetContactUs.setDraftItem(contactUs);
        final Site site = TestUtil.createSite();
        contactUs.setSiteId(site.getSiteId());
        contactUs.setEmail("contactUsEmail");

        final List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME,
                FormItemName.CONTACT_US_MESSAGE, FormItemName.EMAIL);
        filledFormItems.get(0).setValue("FN1");
        filledFormItems.get(1).setValue("LN1");
        filledFormItems.get(2).setValue("aaaaaaaaaaaaaaaa aaaaaaaaaaaaaaa  aaaaaaaaaaa");
        filledFormItems.get(3).setValue("visitor@email.email");

        final SendContactUsInfoRequest request = new SendContactUsInfoRequest();
        request.setWidgetId(widgetContactUs.getWidgetId());
        request.setFilledFormItems(new ArrayList<FilledFormItem>());
        request.setVerificationCode("aaaaaa");
        request.setFilledFormItems(filledFormItems);
        request.setSiteShowOption(SiteShowOption.getDraftOption());
        ServiceLocator.getSessionStorage().setNoBotCode(null, "contactUs" + widgetContactUs.getWidgetId(), "aaaaaa");

        final SendContactUsInfoResponse response = service.execute(request);

        Assert.assertNull(response.getNextPageHtml());

        final FilledForm filledContactUsForm = ServiceLocator.getPersistance().getFilledFormById(response.getFilledFormId());
        Assert.assertNotNull(filledContactUsForm);
        Assert.assertEquals(filledFormItems.get(0).getValues(), filledContactUsForm.getFilledFormItems().get(0).getValues());
        Assert.assertEquals(filledFormItems.get(1).getValues(), filledContactUsForm.getFilledFormItems().get(1).getValues());
        Assert.assertEquals(filledFormItems.get(2).getValues(), filledContactUsForm.getFilledFormItems().get(2).getValues());
        final MockMailSender mailSender = (MockMailSender) ServiceLocator.getMailSender();
        //Asserting that mail WAS send after last form page.
        Assert.assertFalse(mailSender.getMails().isEmpty());
        Assert.assertEquals(1, mailSender.getMails().size());
        final Mail mail = mailSender.getMails().get(0);
        Assert.assertEquals("visitor@email.email", mail.getCc());
        Assert.assertEquals("visitor@email.email", mail.getReply());
        Assert.assertEquals(contactUs.getEmail(), mail.getTo());
    }

    @Test
    public void execute_withoutEmailField() throws Exception {
        TestUtil.createUserAndLogin();
        final WidgetItem widgetContactUs = TestUtil.createContactUsWidget();
        final DraftContactUs contactUs = TestUtil.createContactUsForm();
        widgetContactUs.setDraftItem(contactUs);
        final Site site = TestUtil.createSite();
        contactUs.setSiteId(site.getSiteId());

        final List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME,
                FormItemName.CONTACT_US_MESSAGE);
        filledFormItems.get(0).setValue("FN1");
        filledFormItems.get(1).setValue("LN1");
        filledFormItems.get(2).setValue("aaaaaaaaaaaaaaaa aaaaaaaaaaaaaaa  aaaaaaaaaaa");

        final SendContactUsInfoRequest request = new SendContactUsInfoRequest();
//        request.setFormId(contactUs.getFormId());
        request.setWidgetId(widgetContactUs.getWidgetId());
        request.setFilledFormItems(new ArrayList<FilledFormItem>());
        request.setVerificationCode("aaaaaa");
        request.setFilledFormItems(filledFormItems);
        request.setSiteShowOption(SiteShowOption.getDraftOption());
        ServiceLocator.getSessionStorage().setNoBotCode(null, "contactUs" + widgetContactUs.getWidgetId(), "aaaaaa");

        final SendContactUsInfoResponse response = service.execute(request);

        Assert.assertNull(response.getNextPageHtml());

        final FilledForm filledContactUsForm = ServiceLocator.getPersistance().getFilledFormById(response.getFilledFormId());
        Assert.assertNotNull(filledContactUsForm);
        Assert.assertEquals(filledFormItems.get(0).getValues(), filledContactUsForm.getFilledFormItems().get(0).getValues());
        Assert.assertEquals(filledFormItems.get(1).getValues(), filledContactUsForm.getFilledFormItems().get(1).getValues());
        Assert.assertEquals(filledFormItems.get(2).getValues(), filledContactUsForm.getFilledFormItems().get(2).getValues());
        final MockMailSender mailSender = (MockMailSender) ServiceLocator.getMailSender();
        //Asserting that mail WAS send after last form page.
        Assert.assertFalse(mailSender.getMails().isEmpty());
        Assert.assertEquals(1, mailSender.getMails().size());
        final Mail mail = mailSender.getMails().get(0);
        Assert.assertEquals(null, mail.getCc());
        Assert.assertEquals(null, mail.getReply());
    }

    @Test
    public void executeWithPageBreaks() throws Exception {
        TestUtil.createUserAndLogin();
        final WidgetItem widgetContactUs = TestUtil.createContactUsWidget();
        final DraftContactUs contactUs = TestUtil.createContactUsForm();
        widgetContactUs.setDraftItem(contactUs);
        final Site site = TestUtil.createSite();
        contactUs.setSiteId(site.getSiteId());

        final List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        formItems.add(TestUtil.createFormItem(FormItemName.FIRST_NAME, 0));
        formItems.add(TestUtil.createFormItem(FormItemName.PAGE_BREAK, 1));
        formItems.add(TestUtil.createFormItem(FormItemName.LAST_NAME, 2));
        formItems.add(TestUtil.createFormItem(FormItemName.CONTACT_US_MESSAGE, 3));
        contactUs.setFormItems(formItems);

        //FIRST PAGE
        List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.CONTACT_US_MESSAGE);
        filledFormItems.get(0).setValue("FN1");
        filledFormItems.get(1).setValue("aaaaaaaaaaaaaaaa aaaaaaaaaaaaaaa  aaaaaaaaaaa");

        SendContactUsInfoRequest request = new SendContactUsInfoRequest();
        request.setFormId(contactUs.getFormId());
        request.setWidgetId(widgetContactUs.getWidgetId());
        request.setFilledFormItems(new ArrayList<FilledFormItem>());
        request.setRequestNextPage(true);
        request.setPageBreaksToPass(1);
        request.setVerificationCode("aaaaaa");
        request.setFilledFormItems(filledFormItems);
        request.setSiteShowOption(SiteShowOption.getDraftOption());
        ServiceLocator.getSessionStorage().setNoBotCode(null, "contactUs" + widgetContactUs.getWidgetId(), "aaaaaa");

        SendContactUsInfoResponse response = service.execute(request);

        Assert.assertNotNull(response.getNextPageHtml());

        FilledForm filledContactUsForm = ServiceLocator.getPersistance().getFilledFormById(response.getFilledFormId());
        Assert.assertNotNull(filledContactUsForm);
        Assert.assertEquals(filledFormItems.get(0).getValues(), filledContactUsForm.getFilledFormItems().get(0).getValues());
        Assert.assertEquals(filledFormItems.get(1).getValues(), filledContactUsForm.getFilledFormItems().get(1).getValues());
        final MockMailSender mailSender = (MockMailSender) ServiceLocator.getMailSender();
        //Asserting that mail wasn't send after first form page.
        Assert.assertTrue(mailSender.getMails().isEmpty());

        //SECOND PAGE
        filledFormItems = TestUtil.createFilledFormItems(FormItemName.LAST_NAME);
        filledFormItems.get(0).setValue("LN1");

        request = new SendContactUsInfoRequest();
        request.setFormId(contactUs.getFormId());
        request.setWidgetId(widgetContactUs.getWidgetId());
        request.setFilledFormItems(new ArrayList<FilledFormItem>());
        request.setRequestNextPage(true);
        request.setFilledFormId(response.getFilledFormId());
        request.setPageBreaksToPass(2);
        request.setFilledFormItems(filledFormItems);
        request.setSiteShowOption(SiteShowOption.getDraftOption());

        response = service.execute(request);

        //Ok, now after we have filled out last form page we need to show first form page again with the
        //message about successfull form submit.
        Assert.assertNotNull(response.getNextPageHtml());
        Assert.assertTrue(response.isShowSuccessfullSubmitMessage());

        filledContactUsForm = ServiceLocator.getPersistance().getFilledFormById(response.getFilledFormId());
        Assert.assertEquals(filledFormItems.get(0).getValues(), filledContactUsForm.getFilledFormItems().get(2).getValues());
        //Asserting that mail WAS send after last form page.
        Assert.assertFalse(mailSender.getMails().isEmpty());
    }

    @Test
    public void executeWithPageBreaksFromAddRecord() throws Exception {
        TestUtil.createUserAndLogin();
        final DraftContactUs contactUs = TestUtil.createContactUsForm();
        final Site site = TestUtil.createSite();
        contactUs.setSiteId(site.getSiteId());

        final List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        formItems.add(TestUtil.createFormItem(FormItemName.FIRST_NAME, 0));
        formItems.add(TestUtil.createFormItem(FormItemName.PAGE_BREAK, 1));
        formItems.add(TestUtil.createFormItem(FormItemName.LAST_NAME, 2));
        contactUs.setFormItems(formItems);

        //FIRST PAGE
        List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME,
                FormItemName.CONTACT_US_MESSAGE);
        filledFormItems.get(0).setValue("FN1");
        filledFormItems.get(1).setValue("aaaaaaaaaaaaaaaa aaaaaaaaaaaaaaa  aaaaaaaaaaa");

        SendContactUsInfoRequest request = new SendContactUsInfoRequest();
        request.setFormId(contactUs.getFormId());
        request.setWidgetId(0);
        request.setFilledFormItems(new ArrayList<FilledFormItem>());
        request.setRequestNextPage(true);
        request.setPageBreaksToPass(1);
        request.setVerificationCode("aaaaaa");
        request.setFilledFormItems(filledFormItems);
        request.setShowFromAddRecord(true);
        request.setSiteShowOption(SiteShowOption.getDraftOption());

        SendContactUsInfoResponse response = service.execute(request);
        Assert.assertEquals("/site/showContactUs.action?formId=" + request.getFormId()
                + "&showFromAddRecord=true"
                + "&pageBreaksToPass=" + request.getPageBreaksToPass()
                + "&filledFormToUpdateId=" + response.getFilledFormId(), response.getNextPageHtml());

        FilledForm filledContactUsForm = ServiceLocator.getPersistance().getFilledFormById(response.getFilledFormId());
        Assert.assertNotNull(filledContactUsForm);
        Assert.assertEquals(filledFormItems.get(0).getValues(), filledContactUsForm.getFilledFormItems().get(0).getValues());
        Assert.assertEquals(filledFormItems.get(1).getValues(), filledContactUsForm.getFilledFormItems().get(1).getValues());
        final MockMailSender mailSender = (MockMailSender) ServiceLocator.getMailSender();
        //Asserting that mail wasn't send after first form page.
        Assert.assertTrue(mailSender.getMails().isEmpty());

        //SECOND PAGE
        filledFormItems = TestUtil.createFilledFormItems(FormItemName.LAST_NAME);
        filledFormItems.get(0).setValue("LN1");

        request = new SendContactUsInfoRequest();
        request.setFormId(contactUs.getFormId());
        request.setWidgetId(0);
        request.setFilledFormItems(new ArrayList<FilledFormItem>());
        request.setShowFromAddRecord(true);
        request.setRequestNextPage(true);
        request.setFilledFormId(response.getFilledFormId());
        request.setPageBreaksToPass(2);
        request.setFilledFormItems(filledFormItems);
        request.setSiteShowOption(SiteShowOption.getDraftOption());

        response = service.execute(request);

        Assert.assertNull(response.getNextPageHtml());

        filledContactUsForm = ServiceLocator.getPersistance().getFilledFormById(response.getFilledFormId());
        Assert.assertEquals(filledFormItems.get(0).getValues(), filledContactUsForm.getFilledFormItems().get(2).getValues());
        //Asserting that mail WAS send after last form page.
        Assert.assertFalse(mailSender.getMails().isEmpty());
    }

    @Test(expected = WrongVerificationCodeException.class)
    public void executeWithWrongVerificationCode() throws Exception {
        final WidgetItem widgetContactUs = TestUtil.createContactUsWidget();

        final SendContactUsInfoRequest request = new SendContactUsInfoRequest();
        request.setFilledFormItems(new ArrayList<FilledFormItem>());
        request.setVerificationCode("aaaaaa");
        request.setWidgetId(widgetContactUs.getWidgetId());
        request.setSiteShowOption(SiteShowOption.getDraftOption());
        ServiceLocator.getSessionStorage().setNoBotCode(null, "contactUs" + widgetContactUs.getWidgetId(), "dafadfsa");

        service.execute(request);
    }

    @Test(expected = ContactUsNotFoundException.class)
    public void executeWithoutContactUs() throws Exception {
        final SendContactUsInfoRequest request = new SendContactUsInfoRequest();
        request.setFilledFormItems(new ArrayList<FilledFormItem>());
        request.setVerificationCode("aaaaaa");
        request.setWidgetId(0);
        request.setSiteShowOption(SiteShowOption.getDraftOption());
        ServiceLocator.getSessionStorage().setNoBotCode(null, "contactUs0", "aaaaaa");

        service.execute(request);
    }


    @Test
    public void execute_forDraft_byWidget() throws Exception {
        TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();

        final WidgetItem widgetContactUs = TestUtil.createContactUsWidget();
        final DraftContactUs contactUs = TestUtil.createContactUsForm(site.getSiteId());
        widgetContactUs.setDraftItem(contactUs);
        contactUs.setEmail("contactUsDraftEmail");

        final WorkContactUs workContactUs = new WorkContactUs();
        workContactUs.setId(contactUs.getFormId());
        workContactUs.setEmail("contactUsWorkEmail");
        workContactUs.setSiteId(site.getSiteId());
        ServiceLocator.getPersistance().putItem(workContactUs);

        final List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME,
                FormItemName.CONTACT_US_MESSAGE, FormItemName.EMAIL);
        filledFormItems.get(0).setValue("FN1");
        filledFormItems.get(1).setValue("LN1");
        filledFormItems.get(2).setValue("aaaaaaaaaaaaaaaa aaaaaaaaaaaaaaa  aaaaaaaaaaa");
        filledFormItems.get(3).setValue("visitor@email.email");

        final SendContactUsInfoRequest request = new SendContactUsInfoRequest();
        request.setWidgetId(widgetContactUs.getWidgetId());
        request.setFilledFormItems(new ArrayList<FilledFormItem>());
        request.setVerificationCode("aaaaaa");
        request.setFilledFormItems(filledFormItems);
        request.setSiteShowOption(SiteShowOption.getDraftOption());
        ServiceLocator.getSessionStorage().setNoBotCode(null, "contactUs" + widgetContactUs.getWidgetId(), "aaaaaa");

        service.execute(request);

        final MockMailSender mailSender = (MockMailSender) ServiceLocator.getMailSender();
        //Asserting that mail WAS send after last form page.
        Assert.assertFalse(mailSender.getMails().isEmpty());
        Assert.assertEquals(1, mailSender.getMails().size());
        final Mail mail = mailSender.getMails().get(0);
        Assert.assertEquals("visitor@email.email", mail.getCc());
        Assert.assertEquals("visitor@email.email", mail.getReply());
        Assert.assertEquals("contactUsDraftEmail", mail.getTo());
    }


    @Test
    public void execute_forDraft_byFormId() throws Exception {
        TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();

        final WidgetItem widgetContactUs = TestUtil.createContactUsWidget();

        final DraftContactUs contactUs = TestUtil.createContactUsForm(site.getSiteId());
        widgetContactUs.setDraftItem(contactUs);
        contactUs.setEmail("contactUsDraftEmail");

        final WorkContactUs workContactUs = new WorkContactUs();
        workContactUs.setId(contactUs.getFormId());
        workContactUs.setEmail("contactUsWorkEmail");
        workContactUs.setSiteId(site.getSiteId());
        ServiceLocator.getPersistance().putItem(workContactUs);

        final List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME,
                FormItemName.CONTACT_US_MESSAGE, FormItemName.EMAIL);
        filledFormItems.get(0).setValue("FN1");
        filledFormItems.get(1).setValue("LN1");
        filledFormItems.get(2).setValue("aaaaaaaaaaaaaaaa aaaaaaaaaaaaaaa  aaaaaaaaaaa");
        filledFormItems.get(3).setValue("visitor@email.email");

        final SendContactUsInfoRequest request = new SendContactUsInfoRequest();
        request.setWidgetId(-1);
        request.setFormId(contactUs.getFormId());
        request.setFilledFormItems(new ArrayList<FilledFormItem>());
        request.setVerificationCode("aaaaaa");
        request.setFilledFormItems(filledFormItems);
        request.setSiteShowOption(SiteShowOption.getDraftOption());
        ServiceLocator.getSessionStorage().setNoBotCode(null, "contactUs-1", "aaaaaa");

        service.execute(request);

        final MockMailSender mailSender = (MockMailSender) ServiceLocator.getMailSender();
        //Asserting that mail WAS send after last form page.
        Assert.assertFalse(mailSender.getMails().isEmpty());
        Assert.assertEquals(1, mailSender.getMails().size());
        final Mail mail = mailSender.getMails().get(0);
        Assert.assertEquals("visitor@email.email", mail.getCc());
        Assert.assertEquals("visitor@email.email", mail.getReply());
        Assert.assertEquals("contactUsDraftEmail", mail.getTo());
    }


    @Test
    public void execute_forWork_byWidget() throws Exception {
        TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();

        final WidgetItem widgetContactUs = TestUtil.createContactUsWidget();
        final DraftContactUs contactUs = TestUtil.createContactUsForm(site.getSiteId());
        widgetContactUs.setDraftItem(contactUs);
        contactUs.setEmail("contactUsDraftEmail");

        final WorkContactUs workContactUs = new WorkContactUs();
        workContactUs.setId(contactUs.getFormId());
        workContactUs.setEmail("contactUsWorkEmail");
        workContactUs.setSiteId(site.getSiteId());
        ServiceLocator.getPersistance().putItem(workContactUs);

        final List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME,
                FormItemName.CONTACT_US_MESSAGE, FormItemName.EMAIL);
        filledFormItems.get(0).setValue("FN1");
        filledFormItems.get(1).setValue("LN1");
        filledFormItems.get(2).setValue("aaaaaaaaaaaaaaaa aaaaaaaaaaaaaaa  aaaaaaaaaaa");
        filledFormItems.get(3).setValue("visitor@email.email");

        final SendContactUsInfoRequest request = new SendContactUsInfoRequest();
        request.setWidgetId(widgetContactUs.getWidgetId());
        request.setFilledFormItems(new ArrayList<FilledFormItem>());
        request.setVerificationCode("aaaaaa");
        request.setFilledFormItems(filledFormItems);
        request.setSiteShowOption(SiteShowOption.getWorkOption());
        ServiceLocator.getSessionStorage().setNoBotCode(null, "contactUs" + widgetContactUs.getWidgetId(), "aaaaaa");

        service.execute(request);

        final MockMailSender mailSender = (MockMailSender) ServiceLocator.getMailSender();
        //Asserting that mail WAS send after last form page.
        Assert.assertFalse(mailSender.getMails().isEmpty());
        Assert.assertEquals(1, mailSender.getMails().size());
        final Mail mail = mailSender.getMails().get(0);
        Assert.assertEquals("visitor@email.email", mail.getCc());
        Assert.assertEquals("visitor@email.email", mail.getReply());
        Assert.assertEquals("contactUsWorkEmail", mail.getTo());
    }


    @Test
    public void execute_forWork_byFormId() throws Exception {
        TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();

        final WidgetItem widgetContactUs = TestUtil.createContactUsWidget();

        final DraftContactUs contactUs = TestUtil.createContactUsForm(site.getSiteId());
        widgetContactUs.setDraftItem(contactUs);
        contactUs.setEmail("contactUsDraftEmail");

        final WorkContactUs workContactUs = new WorkContactUs();
        workContactUs.setId(contactUs.getFormId());
        workContactUs.setEmail("contactUsWorkEmail");
        workContactUs.setSiteId(site.getSiteId());
        ServiceLocator.getPersistance().putItem(workContactUs);

        final List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME,
                FormItemName.CONTACT_US_MESSAGE, FormItemName.EMAIL);
        filledFormItems.get(0).setValue("FN1");
        filledFormItems.get(1).setValue("LN1");
        filledFormItems.get(2).setValue("aaaaaaaaaaaaaaaa aaaaaaaaaaaaaaa  aaaaaaaaaaa");
        filledFormItems.get(3).setValue("visitor@email.email");

        final SendContactUsInfoRequest request = new SendContactUsInfoRequest();
        request.setWidgetId(-1);
        request.setFormId(contactUs.getFormId());
        request.setFilledFormItems(new ArrayList<FilledFormItem>());
        request.setVerificationCode("aaaaaa");
        request.setFilledFormItems(filledFormItems);
        request.setSiteShowOption(SiteShowOption.getWorkOption());
        ServiceLocator.getSessionStorage().setNoBotCode(null, "contactUs-1", "aaaaaa");

        service.execute(request);

        final MockMailSender mailSender = (MockMailSender) ServiceLocator.getMailSender();
        //Asserting that mail WAS send after last form page.
        Assert.assertFalse(mailSender.getMails().isEmpty());
        Assert.assertEquals(1, mailSender.getMails().size());
        final Mail mail = mailSender.getMails().get(0);
        Assert.assertEquals("visitor@email.email", mail.getCc());
        Assert.assertEquals("visitor@email.email", mail.getReply());
        Assert.assertEquals("contactUsWorkEmail", mail.getTo());
    }


}
