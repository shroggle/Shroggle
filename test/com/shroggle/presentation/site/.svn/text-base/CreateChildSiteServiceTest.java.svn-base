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
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class CreateChildSiteServiceTest {

    private CreateChildSiteService service = new CreateChildSiteService();

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) new CreateChildSiteService().getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void testExecute() throws Exception {
        final User user = TestUtil.createUser("email@email.com");
        user.setPassword("a");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        final Site site = TestUtil.createSite();
        site.setTitle("siteTitle");
        Page page = TestUtil.createPage(site);
        PageManager pageVersion = TestUtil.createPageVersion(page);
        final DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment", site);
        //childSiteRegistration.setPayAfterRegistration(false);

        final List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME, FormItemName.BILLING_ADDRESS);
        filledFormItems.get(0).setValue("newFirstName");
        filledFormItems.get(1).setValue("newLastName");
        filledFormItems.get(2).setValue("newAdress");

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        ServiceLocator.getSessionStorage().setNoBotCode(null, "childSiteRegistration" + widget.getWidgetId(), "aaa");

        final CreateChildSiteRequest request = new CreateChildSiteRequest();
        request.setShowFromAddRecord(false);
        request.setVerificationCode("aaa");
        request.setWidgetId(widget.getWidgetId());
        request.setFormId(childSiteRegistration.getFormId());
        request.setEmail(user.getEmail());
        request.setPassword(user.getPassword());
        request.setConfirmPassword(user.getPassword());
        request.setFilledFormItems(filledFormItems);

        Assert.assertEquals(0, user.getChildSiteSettingsId().size());
        Assert.assertEquals(0, user.getFilledForms().size());
        Assert.assertNull(site.getChildSiteSettings());

        final UserRightManager userRightManager = new UserRightManager(user);

        Assert.assertNull(userRightManager.toSite(site));
        Assert.assertEquals("firstName", user.getFirstName());
        Assert.assertEquals("lastName", user.getLastName());
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getTelephone()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getTelephone2()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getPostalCode()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getBillingAddress1()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getBillingAddress2()));

        CreateChildSiteResponse response = service.execute(request);

        Assert.assertEquals(SiteAccessLevel.VISITOR, userRightManager.toSite(site).getSiteAccessType());
        Assert.assertEquals("newFirstName", user.getFirstName());
        Assert.assertEquals("newLastName", user.getLastName());
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getTelephone()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getTelephone2()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getPostalCode()));
        Assert.assertEquals("newAdress", user.getBillingAddress1());
        Assert.assertEquals("newAdress", user.getBillingAddress2());

        Assert.assertNull(response.getNextPageHtml());

        Assert.assertEquals(1, user.getFilledForms().size());
        ChildSiteSettings childSiteSettings = persistance.getChildSiteSettingsById(user.getChildSiteSettingsId().get(0));

        Assert.assertEquals(1, user.getChildSiteSettingsId().size());
        FilledForm filledForm = user.getFilledForms().get(0);
        Assert.assertEquals(3, filledForm.getFilledFormItems().size());
        Assert.assertEquals("newFirstName", filledForm.getFilledFormItems().get(0).getValues().get(0));
        Assert.assertEquals("newLastName", filledForm.getFilledFormItems().get(1).getValues().get(0));
        Assert.assertEquals("newAdress", filledForm.getFilledFormItems().get(2).getValues().get(0));
        Assert.assertEquals(new Integer(childSiteSettings.getChildSiteSettingsId()), filledForm.getChildSiteSettingsId());
        Assert.assertEquals(response.getFilledFormId(), filledForm.getFilledFormId());

        Assert.assertNotNull(childSiteSettings.getParentSite().getSiteId());
        Assert.assertEquals(childSiteSettings.getParentSite().getSiteId(), site.getSiteId());
        Assert.assertNull(childSiteSettings.getSite());
        Assert.assertNotNull(childSiteSettings.getChildSiteRegistration());
        Assert.assertEquals(childSiteSettings.getChildSiteRegistration().getFormId(), childSiteRegistration.getFormId());
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getCreditCard());
    }


    @Test
    public void testExecute_forExistingUser() throws Exception {
        final User user = TestUtil.createUser("email@email.com");
        user.setPassword("a");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        final Site site = TestUtil.createSite();
        site.setTitle("siteTitle");
        Page page = TestUtil.createPage(site);
        PageManager pageVersion = TestUtil.createPageVersion(page);
        final DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment", site);
        //childSiteRegistration.setPayAfterRegistration(false);

        final List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME, FormItemName.BILLING_ADDRESS);
        filledFormItems.get(0).setValue("newFirstName");
        filledFormItems.get(1).setValue("newLastName");
        filledFormItems.get(2).setValue("newAdress");

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        ServiceLocator.getSessionStorage().setNoBotCode(null, "childSiteRegistration" + widget.getWidgetId(), "aaa");

        final CreateChildSiteRequest request = new CreateChildSiteRequest();
        request.setShowFromAddRecord(false);
        request.setVerificationCode("aaa");
        request.setWidgetId(widget.getWidgetId());
        request.setFormId(childSiteRegistration.getFormId());
        request.setEmail(user.getEmail());
        request.setPassword(user.getPassword());
        request.setConfirmPassword(user.getPassword());
        request.setFilledFormItems(filledFormItems);

        Assert.assertEquals(0, user.getChildSiteSettingsId().size());
        Assert.assertEquals(0, user.getFilledForms().size());
        Assert.assertNull(site.getChildSiteSettings());

        final UserRightManager userRightManager = new UserRightManager(user);

        Assert.assertNull(userRightManager.toSite(site));
        Assert.assertEquals("firstName", user.getFirstName());
        Assert.assertEquals("lastName", user.getLastName());
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getTelephone()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getTelephone2()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getPostalCode()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getBillingAddress1()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getBillingAddress2()));

        CreateChildSiteResponse response = service.execute(request);

        Assert.assertEquals(SiteAccessLevel.VISITOR, userRightManager.toSite(site).getSiteAccessType());
        Assert.assertEquals("newFirstName", user.getFirstName());
        Assert.assertEquals("newLastName", user.getLastName());
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getTelephone()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getTelephone2()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getPostalCode()));
        Assert.assertEquals("newAdress", user.getBillingAddress1());
        Assert.assertEquals("newAdress", user.getBillingAddress2());

        Assert.assertNull(response.getNextPageHtml());

        Assert.assertEquals(1, user.getFilledForms().size());
        ChildSiteSettings childSiteSettings = persistance.getChildSiteSettingsById(user.getChildSiteSettingsId().get(0));

        Assert.assertEquals(1, user.getChildSiteSettingsId().size());
        FilledForm filledForm = user.getFilledForms().get(0);
        Assert.assertEquals(3, filledForm.getFilledFormItems().size());
        Assert.assertEquals("newFirstName", filledForm.getFilledFormItems().get(0).getValues().get(0));
        Assert.assertEquals("newLastName", filledForm.getFilledFormItems().get(1).getValues().get(0));
        Assert.assertEquals("newAdress", filledForm.getFilledFormItems().get(2).getValues().get(0));
        Assert.assertEquals(new Integer(childSiteSettings.getChildSiteSettingsId()), filledForm.getChildSiteSettingsId());
        Assert.assertEquals(response.getFilledFormId(), filledForm.getFilledFormId());

        Assert.assertNotNull(childSiteSettings.getParentSite().getSiteId());
        Assert.assertEquals(childSiteSettings.getParentSite().getSiteId(), site.getSiteId());
        Assert.assertNull(childSiteSettings.getSite());
        Assert.assertNotNull(childSiteSettings.getChildSiteRegistration());
        Assert.assertEquals(childSiteSettings.getChildSiteRegistration().getFormId(), childSiteRegistration.getFormId());
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getCreditCard());
    }


    @Test
    public void testExecuteWithPageBreaks() throws Exception {
        final User user = TestUtil.createUser("email@email.com");
        user.setPassword("a");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        final Site site = TestUtil.createSite();
        site.setTitle("siteTitle");
        Page page = TestUtil.createPage(site);
        PageManager pageVersion = TestUtil.createPageVersion(page);
        final DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment", site);
        final List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        formItems.add(TestUtil.createFormItem(FormItemName.FIRST_NAME, 0));
        formItems.add(TestUtil.createFormItem(FormItemName.LAST_NAME, 1));
        formItems.add(TestUtil.createFormItem(FormItemName.PAGE_BREAK, 2));
        formItems.add(TestUtil.createFormItem(FormItemName.BILLING_ADDRESS, 3));
        childSiteRegistration.setFormItems(formItems);

//        childSiteRegistration.setPayAfterRegistration(false);

        List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME, FormItemName.BILLING_ADDRESS);
        filledFormItems.get(0).setValue("newFirstName");
        filledFormItems.get(1).setValue("newLastName");
        filledFormItems.get(2).setValue("newAdress");

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        ServiceLocator.getSessionStorage().setNoBotCode(null, "childSiteRegistration" + widget.getWidgetId(), "aaa");

        CreateChildSiteRequest request = new CreateChildSiteRequest();
        request.setShowFromAddRecord(false);
        request.setVerificationCode("aaa");
        request.setWidgetId(widget.getWidgetId());
        request.setFormId(childSiteRegistration.getFormId());
        request.setRequestNextPage(true);
        request.setPageBreaksToPass(1);
        request.setEmail(user.getEmail());
        request.setPassword(user.getPassword());
        request.setConfirmPassword(user.getPassword());
        request.setFilledFormItems(filledFormItems);

        Assert.assertEquals(0, user.getChildSiteSettingsId().size());
        Assert.assertEquals(0, user.getFilledForms().size());
        Assert.assertNull(site.getChildSiteSettings());

        final UserRightManager userRightManager = new UserRightManager(user);

        Assert.assertNull(userRightManager.toSite(site));
        Assert.assertEquals("firstName", user.getFirstName());
        Assert.assertEquals("lastName", user.getLastName());
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getTelephone()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getTelephone2()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getPostalCode()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getBillingAddress1()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getBillingAddress2()));

        CreateChildSiteResponse response = service.execute(request);

        Assert.assertEquals(SiteAccessLevel.VISITOR, userRightManager.toSite(site).getSiteAccessType());
        Assert.assertEquals("newFirstName", user.getFirstName());
        Assert.assertEquals("newLastName", user.getLastName());
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getTelephone()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getTelephone2()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getPostalCode()));
        Assert.assertEquals("newAdress", user.getBillingAddress1());
        Assert.assertEquals("newAdress", user.getBillingAddress2());

        Assert.assertNotNull(response.getNextPageHtml());

        Assert.assertEquals(1, user.getFilledForms().size());
        ChildSiteSettings childSiteSettings = persistance.getChildSiteSettingsById(user.getChildSiteSettingsId().get(0));

        Assert.assertEquals(1, user.getChildSiteSettingsId().size());
        FilledForm filledForm = user.getFilledForms().get(0);
        Assert.assertEquals(3, filledForm.getFilledFormItems().size());
        Assert.assertEquals("newFirstName", filledForm.getFilledFormItems().get(0).getValues().get(0));
        Assert.assertEquals("newLastName", filledForm.getFilledFormItems().get(1).getValues().get(0));
        Assert.assertEquals("newAdress", filledForm.getFilledFormItems().get(2).getValues().get(0));
        Assert.assertEquals(new Integer(childSiteSettings.getChildSiteSettingsId()), filledForm.getChildSiteSettingsId());
        Assert.assertEquals(response.getFilledFormId(), filledForm.getFilledFormId());

        Assert.assertNotNull(childSiteSettings.getParentSite().getSiteId());
        Assert.assertEquals(childSiteSettings.getParentSite().getSiteId(), site.getSiteId());
        Assert.assertNull(childSiteSettings.getSite());
        Assert.assertNotNull(childSiteSettings.getChildSiteRegistration());
        Assert.assertEquals(childSiteSettings.getChildSiteRegistration().getFormId(), childSiteRegistration.getFormId());
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getCreditCard());

        filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME, FormItemName.BILLING_ADDRESS);
        filledFormItems.get(0).setValue("newFirstName1");
        filledFormItems.get(1).setValue("newLastName1");
        filledFormItems.get(2).setValue("newAdress1");

        request = new CreateChildSiteRequest();
        request.setShowFromAddRecord(false);
        request.setWidgetId(widget.getWidgetId());
        request.setFormId(childSiteRegistration.getFormId());
        request.setRequestNextPage(true);
        request.setPageBreaksToPass(2);
        request.setUserId(user.getUserId());
        request.setFilledFormId(response.getFilledFormId());
        request.setSettingsId(childSiteSettings.getChildSiteSettingsId());
        request.setFilledFormItems(filledFormItems);

        response = service.execute(request);
        Assert.assertNull(response.getNextPageHtml());
        Assert.assertEquals("newFirstName1", filledForm.getFilledFormItems().get(0).getValues().get(0));
        Assert.assertEquals("newLastName1", filledForm.getFilledFormItems().get(1).getValues().get(0));
        Assert.assertEquals("newAdress1", filledForm.getFilledFormItems().get(2).getValues().get(0));
    }
    
    @Test
    public void testExecuteWithPageBreaksForEditFilledForm() throws Exception {
        final User user = TestUtil.createUser("email@email.com");
        user.setPassword("a");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        final Site site = TestUtil.createSite();
        site.setTitle("siteTitle");
        Page page = TestUtil.createPage(site);
        PageManager pageVersion = TestUtil.createPageVersion(page);
        final DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment", site);
        final List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        formItems.add(TestUtil.createFormItem(FormItemName.FIRST_NAME, 0));
        formItems.add(TestUtil.createFormItem(FormItemName.LAST_NAME, 1));
        formItems.add(TestUtil.createFormItem(FormItemName.PAGE_BREAK, 2));
        formItems.add(TestUtil.createFormItem(FormItemName.BILLING_ADDRESS, 3));
        childSiteRegistration.setFormItems(formItems);

        List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME, FormItemName.BILLING_ADDRESS);
        filledFormItems.get(0).setValue("newFirstName");
        filledFormItems.get(1).setValue("newLastName");
        filledFormItems.get(2).setValue("newAdress");

        final FilledForm filledForm = TestUtil.createFilledChildSiteRegistrationForm(user);
        filledForm.setFilledFormItems(filledFormItems);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        ServiceLocator.getSessionStorage().setNoBotCode(null, "childSiteRegistration" + widget.getWidgetId(), "aaa");

        CreateChildSiteRequest request = new CreateChildSiteRequest();
        request.setShowFromAddRecord(false);
        request.setVerificationCode("aaa");
        request.setWidgetId(widget.getWidgetId());
        request.setFormId(childSiteRegistration.getFormId());
        request.setRequestNextPage(true);
        request.setPageBreaksToPass(1);
        request.setEditDetails(true);
        request.setEmail(user.getEmail());
        request.setPassword(user.getPassword());
        request.setFilledFormId(filledForm.getFilledFormId());
        request.setConfirmPassword(user.getPassword());
        request.setUserId(user.getUserId());
        request.setFilledFormItems(filledFormItems);

        Assert.assertEquals(0, user.getChildSiteSettingsId().size());
        Assert.assertEquals(1, user.getFilledForms().size());
        Assert.assertNull(site.getChildSiteSettings());

        final UserRightManager userRightManager = new UserRightManager(user);

        Assert.assertNull(userRightManager.toSite(site));
        Assert.assertEquals("firstName", user.getFirstName());
        Assert.assertEquals("lastName", user.getLastName());
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getTelephone()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getTelephone2()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getPostalCode()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getBillingAddress1()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getBillingAddress2()));

        CreateChildSiteResponse response = service.execute(request);

        Assert.assertEquals(filledForm, service.getContext().getHttpServletRequest().getAttribute("prefilledForm"));
        Assert.assertEquals("newFirstName", user.getFirstName());
        Assert.assertEquals("newLastName", user.getLastName());
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getTelephone()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getTelephone2()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getPostalCode()));
        Assert.assertEquals("newAdress", user.getBillingAddress1());
        Assert.assertEquals("newAdress", user.getBillingAddress2());

        Assert.assertNotNull(response.getNextPageHtml());

        Assert.assertEquals(1, user.getFilledForms().size());

        FilledForm filledFomr1 = user.getFilledForms().get(0);
        Assert.assertEquals(3, filledFomr1.getFilledFormItems().size());
        Assert.assertEquals("newFirstName", filledFomr1.getFilledFormItems().get(0).getValues().get(0));
        Assert.assertEquals("newLastName", filledFomr1.getFilledFormItems().get(1).getValues().get(0));
        Assert.assertEquals("newAdress", filledFomr1.getFilledFormItems().get(2).getValues().get(0));
        Assert.assertEquals(response.getFilledFormId(), filledFomr1.getFilledFormId());

        filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME, FormItemName.BILLING_ADDRESS);
        filledFormItems.get(0).setValue("newFirstName1");
        filledFormItems.get(1).setValue("newLastName1");
        filledFormItems.get(2).setValue("newAdress1");

        request = new CreateChildSiteRequest();
        request.setShowFromAddRecord(false);
        request.setWidgetId(widget.getWidgetId());
        request.setFormId(childSiteRegistration.getFormId());
        request.setRequestNextPage(true);
        request.setEditDetails(true);
        request.setPageBreaksToPass(2);
        request.setUserId(user.getUserId());
        request.setFilledFormId(response.getFilledFormId());
        request.setFilledFormItems(filledFormItems);

        response = service.execute(request);
        Assert.assertNull(response.getNextPageHtml());
        Assert.assertEquals("newFirstName1", filledFomr1.getFilledFormItems().get(0).getValues().get(0));
        Assert.assertEquals("newLastName1", filledFomr1.getFilledFormItems().get(1).getValues().get(0));
        Assert.assertEquals("newAdress1", filledFomr1.getFilledFormItems().get(2).getValues().get(0));
    }

    @Test
    public void testExecuteWithPageBreaksFromAddRecord() throws Exception {
        final User user = TestUtil.createUser("email@email.com");
        user.setPassword("a");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        final Site site = TestUtil.createSite();
        site.setTitle("siteTitle");
        Page page = TestUtil.createPage(site);
        PageManager pageVersion = TestUtil.createPageVersion(page);
        final DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment", site);
        final List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        formItems.add(TestUtil.createFormItem(FormItemName.FIRST_NAME, 0));
        formItems.add(TestUtil.createFormItem(FormItemName.LAST_NAME, 1));
        formItems.add(TestUtil.createFormItem(FormItemName.PAGE_BREAK, 2));
        formItems.add(TestUtil.createFormItem(FormItemName.BILLING_ADDRESS, 3));
        childSiteRegistration.setFormItems(formItems);

//        childSiteRegistration.setPayAfterRegistration(false);

        List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME, FormItemName.BILLING_ADDRESS);
        filledFormItems.get(0).setValue("newFirstName");
        filledFormItems.get(1).setValue("newLastName");
        filledFormItems.get(2).setValue("newAdress");

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        ServiceLocator.getSessionStorage().setNoBotCode(null, "childSiteRegistration" + widget.getWidgetId(), "aaa");

        CreateChildSiteRequest request = new CreateChildSiteRequest();
        request.setShowFromAddRecord(false);
        request.setVerificationCode("aaa");
        request.setWidgetId(0);
        request.setFormId(childSiteRegistration.getFormId());
        request.setRequestNextPage(true);
        request.setPageBreaksToPass(1);
        request.setEmail(user.getEmail());
        request.setPassword(user.getPassword());
        request.setShowFromAddRecord(true);
        request.setConfirmPassword(user.getPassword());
        request.setFilledFormItems(filledFormItems);

        Assert.assertEquals(0, user.getChildSiteSettingsId().size());
        Assert.assertEquals(0, user.getFilledForms().size());
        Assert.assertNull(site.getChildSiteSettings());

        final UserRightManager userRightManager = new UserRightManager(user);

        Assert.assertNull(userRightManager.toSite(site));
        Assert.assertEquals("firstName", user.getFirstName());
        Assert.assertEquals("lastName", user.getLastName());
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getTelephone()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getTelephone2()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getPostalCode()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getBillingAddress1()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getBillingAddress2()));

        CreateChildSiteResponse response = service.execute(request);

        Assert.assertEquals(1, service.getContext().getHttpServletRequest().getAttribute("settingsId"));
        Assert.assertEquals(1, service.getContext().getHttpServletRequest().getAttribute("childSiteUserId"));
        Assert.assertEquals("/site/showChildSiteRegistration.action?formId=" + request.getFormId()
                + "&showFromAddRecord=true"
                + "&pageBreaksToPass=" + request.getPageBreaksToPass()
                + "&filledFormToUpdateId=" + response.getFilledFormId(), response.getNextPageHtml());

        Assert.assertEquals(SiteAccessLevel.VISITOR, userRightManager.toSite(site).getSiteAccessType());
        Assert.assertEquals("newFirstName", user.getFirstName());
        Assert.assertEquals("newLastName", user.getLastName());
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getTelephone()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getTelephone2()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getPostalCode()));
        Assert.assertEquals("newAdress", user.getBillingAddress1());
        Assert.assertEquals("newAdress", user.getBillingAddress2());

        Assert.assertEquals(1, user.getFilledForms().size());
        ChildSiteSettings childSiteSettings = persistance.getChildSiteSettingsById(user.getChildSiteSettingsId().get(0));

        Assert.assertEquals(1, user.getChildSiteSettingsId().size());
        FilledForm filledForm = user.getFilledForms().get(0);
        Assert.assertEquals(3, filledForm.getFilledFormItems().size());
        Assert.assertEquals("newFirstName", filledForm.getFilledFormItems().get(0).getValues().get(0));
        Assert.assertEquals("newLastName", filledForm.getFilledFormItems().get(1).getValues().get(0));
        Assert.assertEquals("newAdress", filledForm.getFilledFormItems().get(2).getValues().get(0));
        Assert.assertEquals(new Integer(childSiteSettings.getChildSiteSettingsId()), filledForm.getChildSiteSettingsId());
        Assert.assertEquals(response.getFilledFormId(), filledForm.getFilledFormId());

        Assert.assertNotNull(childSiteSettings.getParentSite().getSiteId());
        Assert.assertEquals(childSiteSettings.getParentSite().getSiteId(), site.getSiteId());
        Assert.assertNull(childSiteSettings.getSite());
        Assert.assertNotNull(childSiteSettings.getChildSiteRegistration());
        Assert.assertEquals(childSiteSettings.getChildSiteRegistration().getFormId(), childSiteRegistration.getFormId());
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getCreditCard());

        filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME, FormItemName.BILLING_ADDRESS);
        filledFormItems.get(0).setValue("newFirstName1");
        filledFormItems.get(1).setValue("newLastName1");
        filledFormItems.get(2).setValue("newAdress1");

        request = new CreateChildSiteRequest();
        request.setShowFromAddRecord(true);
        request.setWidgetId(0);
        request.setFormId(childSiteRegistration.getFormId());
        request.setRequestNextPage(true);
        request.setPageBreaksToPass(2);
        request.setUserId(user.getUserId());
        request.setFilledFormId(response.getFilledFormId());
        request.setSettingsId(childSiteSettings.getChildSiteSettingsId());
        request.setFilledFormItems(filledFormItems);

        response = service.execute(request);
        Assert.assertNull(response.getNextPageHtml());
        Assert.assertEquals("newFirstName1", filledForm.getFilledFormItems().get(0).getValues().get(0));
        Assert.assertEquals("newLastName1", filledForm.getFilledFormItems().get(1).getValues().get(0));
        Assert.assertEquals("newAdress1", filledForm.getFilledFormItems().get(2).getValues().get(0));
    }

    @Test
    public void testSaveEditedFilledForm() throws Exception {
        final User user = TestUtil.createUserAndLogin("");
        DraftChildSiteRegistration form = TestUtil.createChildSiteRegistration("", "");
        final PageManager pageManager = new PageManager(TestUtil.createPageAndSite());

        Widget widget = TestUtil.createWidgetChildSiteRegistration(form.getFormId());
        pageManager.addWidget(widget);
        FilledForm filledForm = TestUtil.createFilledForm(form.getFormId());

        List<FilledFormItem> items = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME, FormItemName.ADDRESS, FormItemName.YOUR_OWN_DOMAIN_NAME);
        filledForm.setFilledFormItems(items);
        FilledFormItem item;
        item = FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.FIRST_NAME);
        item.setValue("first name");
        item = FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.LAST_NAME);
        item.setValue("last name");
        item = FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.ADDRESS);
        item.setValue("adress");
        item = FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.YOUR_OWN_DOMAIN_NAME);
        item.setValue("domain name");

        Assert.assertEquals(FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.FIRST_NAME).getValues().get(0), "first name");
        Assert.assertEquals(FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.LAST_NAME).getValues().get(0), "last name");
        Assert.assertEquals(FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.ADDRESS).getValues().get(0), "adress");
        Assert.assertEquals(FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.YOUR_OWN_DOMAIN_NAME).getValues().get(0), "domain name");

        List<FilledFormItem> newItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME, FormItemName.ADDRESS, FormItemName.YOUR_OWN_DOMAIN_NAME);

        FilledFormItem newItem;
        newItem = TestUtil.createFilledFormItem(FormItemName.FIRST_NAME);
        newItem.setValue("new first name");
        newItems.add(newItem);
        newItem = TestUtil.createFilledFormItem(FormItemName.LAST_NAME);
        newItem.setValue("new last name");
        newItems.add(newItem);
        newItem = TestUtil.createFilledFormItem(FormItemName.ADDRESS);
        newItem.setValue("new adress");
        newItems.add(newItem);
        newItem = TestUtil.createFilledFormItem(FormItemName.YOUR_OWN_DOMAIN_NAME);
        newItem.setValue("new domain name");
        newItems.add(newItem);
        newItem = TestUtil.createFilledFormItem(FormItemName.ADOPTIVE_FATHER_NAME);
        newItem.setValue("dasfasdfasdf");
        newItems.add(newItem);

        final CreateChildSiteRequest request = new CreateChildSiteRequest();
        request.setWidgetId(widget.getWidgetId());
        request.setFilledFormId(filledForm.getFilledFormId());
        request.setFilledFormItems(newItems);
        request.setEditDetails(true);
        request.setUserId(user.getUserId());
        request.setFormId(form.getFormId());
        request.setSettingsId(-1);

        CreateChildSiteResponse response = service.execute(request);

        Assert.assertNotNull(response.getNextPageHtml());

        Assert.assertEquals(FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.FIRST_NAME).getValues().get(0), "new first name");
        Assert.assertEquals(FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.LAST_NAME).getValues().get(0), "new last name");
        Assert.assertEquals(FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.ADDRESS).getValues().get(0), "new adress");
        Assert.assertEquals(FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.YOUR_OWN_DOMAIN_NAME).getValues().get(0), "new domain name");
        Assert.assertEquals(FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.ADOPTIVE_FATHER_NAME).getValues().get(0), "dasfasdfasdf");
    }

    @Test
    public void testExecute_withAdministratorRights() throws Exception {
        final User user = TestUtil.createUser("email@email.com");
        user.setPassword("a");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        final Site site = TestUtil.createSite();
        site.setTitle("siteTitle");

        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        Page page = TestUtil.createPage(site);
        PageManager pageVersion = TestUtil.createPageVersion(page);
        final DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment", site);
//        childSiteRegistration.setPayAfterRegistration(false);

        final List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME, FormItemName.BILLING_ADDRESS);
        filledFormItems.get(0).setValue("newFirstName");
        filledFormItems.get(1).setValue("newLastName");
        filledFormItems.get(2).setValue("newAdress");


        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        ServiceLocator.getSessionStorage().setNoBotCode(null, "childSiteRegistration" + widget.getWidgetId(), "aaa");

        final CreateChildSiteRequest request = new CreateChildSiteRequest();
        request.setShowFromAddRecord(false);
        request.setVerificationCode("aaa");
        request.setWidgetId(widget.getWidgetId());
        request.setFormId(childSiteRegistration.getFormId());
        request.setEmail(user.getEmail());
        request.setPassword(user.getPassword());
        request.setConfirmPassword(user.getPassword());
        request.setFilledFormItems(filledFormItems);

        Assert.assertEquals(0, user.getChildSiteSettingsId().size());
        Assert.assertEquals(0, user.getFilledForms().size());
        Assert.assertNull(site.getChildSiteSettings());

        final UserRightManager userRightManager = new UserRightManager(user);

        Assert.assertNotNull(userRightManager.toSite(site));
        Assert.assertEquals(SiteAccessLevel.ADMINISTRATOR, userRightManager.toSite(site).getSiteAccessType());
        Assert.assertEquals("firstName", user.getFirstName());
        Assert.assertEquals("lastName", user.getLastName());
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getTelephone()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getTelephone2()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getPostalCode()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getBillingAddress1()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getBillingAddress2()));


        CreateChildSiteResponse response = service.execute(request);

        Assert.assertEquals(SiteAccessLevel.ADMINISTRATOR, userRightManager.toSite(site).getSiteAccessType());
        Assert.assertEquals("newFirstName", user.getFirstName());
        Assert.assertEquals("newLastName", user.getLastName());
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getTelephone()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getTelephone2()));
        Assert.assertEquals("", StringUtil.getEmptyOrString(user.getPostalCode()));
        Assert.assertEquals("newAdress", user.getBillingAddress1());
        Assert.assertEquals("newAdress", user.getBillingAddress2());

        Assert.assertNull(response.getNextPageHtml());

        Assert.assertEquals(1, user.getFilledForms().size());
        ChildSiteSettings childSiteSettings = persistance.getChildSiteSettingsById(user.getChildSiteSettingsId().get(0));

        Assert.assertEquals(1, user.getChildSiteSettingsId().size());
        FilledForm filledForm = user.getFilledForms().get(0);
        Assert.assertEquals(3, filledForm.getFilledFormItems().size());
        Assert.assertEquals("newFirstName", filledForm.getFilledFormItems().get(0).getValues().get(0));
        Assert.assertEquals("newLastName", filledForm.getFilledFormItems().get(1).getValues().get(0));
        Assert.assertEquals("newAdress", filledForm.getFilledFormItems().get(2).getValues().get(0));
        Assert.assertEquals(new Integer(childSiteSettings.getChildSiteSettingsId()), filledForm.getChildSiteSettingsId());
        Assert.assertEquals(response.getFilledFormId(), filledForm.getFilledFormId());

        Assert.assertNotNull(childSiteSettings.getParentSite().getSiteId());
        Assert.assertEquals(childSiteSettings.getParentSite().getSiteId(), site.getSiteId());
        Assert.assertNull(childSiteSettings.getSite());
        Assert.assertNotNull(childSiteSettings.getChildSiteRegistration());
        Assert.assertEquals(childSiteSettings.getChildSiteRegistration().getFormId(), childSiteRegistration.getFormId());
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getCreditCard());
    }


    @Test
    public void testExecute_withNullWidget() throws Exception {
        final User user = TestUtil.createUser("email@email.com");
        user.setPassword("a");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        final Site site = TestUtil.createSite();
        site.setTitle("siteTitle");
        Page page = TestUtil.createPage(site);
        TestUtil.createPageVersion(page);
        final DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment", site);
//        childSiteRegistration.setPayAfterRegistration(false);

        final List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME, FormItemName.ADDRESS);
        filledFormItems.get(0).setValue("firstName");
        filledFormItems.get(1).setValue("lastName");
        filledFormItems.get(2).setValue("adress");

        ServiceLocator.getSessionStorage().setNoBotCode(null, "childSiteRegistration-1", "aaa");

        final CreateChildSiteRequest request = new CreateChildSiteRequest();
        request.setShowFromAddRecord(false);
        request.setVerificationCode("aaa");
        request.setWidgetId(-1);
        request.setFormId(childSiteRegistration.getFormId());
        request.setEmail(user.getEmail());
        request.setPassword(user.getPassword());
        request.setConfirmPassword(user.getPassword());
        request.setFilledFormItems(filledFormItems);

        Assert.assertEquals(0, user.getChildSiteSettingsId().size());
        Assert.assertEquals(0, user.getFilledForms().size());
        Assert.assertNull(site.getChildSiteSettings());


        CreateChildSiteResponse response = service.execute(request);

        Assert.assertNull(response.getNextPageHtml());

        Assert.assertEquals(1, user.getFilledForms().size());
        ChildSiteSettings childSiteSettings = persistance.getChildSiteSettingsById(user.getChildSiteSettingsId().get(0));

        Assert.assertEquals(1, user.getChildSiteSettingsId().size());
        FilledForm filledForm = user.getFilledForms().get(0);
        Assert.assertEquals(3, filledForm.getFilledFormItems().size());
        Assert.assertEquals("firstName", filledForm.getFilledFormItems().get(0).getValues().get(0));
        Assert.assertEquals("lastName", filledForm.getFilledFormItems().get(1).getValues().get(0));
        Assert.assertEquals("adress", filledForm.getFilledFormItems().get(2).getValues().get(0));
        Assert.assertEquals(new Integer(childSiteSettings.getChildSiteSettingsId()), filledForm.getChildSiteSettingsId());
        Assert.assertEquals(response.getFilledFormId(), filledForm.getFilledFormId());

        Assert.assertNotNull(childSiteSettings.getParentSite().getSiteId());
        Assert.assertEquals(childSiteSettings.getParentSite().getSiteId(), site.getSiteId());
        Assert.assertNull(childSiteSettings.getSite());
        Assert.assertNotNull(childSiteSettings.getChildSiteRegistration());
        Assert.assertEquals(childSiteSettings.getChildSiteRegistration().getFormId(), childSiteRegistration.getFormId());
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getCreditCard());
    }

    /* @Test
    public void testExecute_withPayAfterRegistration() throws Exception {
        final User user = TestUtil.createUser("email@email.com");
        user.setPassword("a");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        final Site site = TestUtil.createSite();
        site.setTitle("siteTitle");
        Page page = TestUtil.createPage(site);
        PageManager pageVersion = TestUtil.createPageVersion(page);
        final ChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment", site);
//        childSiteRegistration.setPayAfterRegistration(true);

        final List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME, FormItemName.ADDRESS);
        filledFormItems.get(0).setItemValues("firstName");
        filledFormItems.get(1).setItemValues("lastName");
        filledFormItems.get(2).setItemValues("adress");


        persistance.putWidget(widget);
        pageVersion.addWidget(widget);
        pageVersion.addWidget(widget);

        ServiceLocator.getSessionStorage().setNoBotCode(null, "childSiteRegistration" + widget.getWidgetId(), "aaa");

        final CreateChildSiteRequest request = new CreateChildSiteRequest();
        request.setShowFromAddRecord(false);
        request.setVerificationCode("aaa");
        request.setWidgetId(widget.getWidgetId());
        request.setFormId1(childSiteRegistration.getFormId());
        request.setEmail(user.getEmail());
        request.setPassword(user.getPassword());
        request.setConfirmPassword(user.getPassword());
        request.setFilledFormItems(filledFormItems);

        Assert.assertEquals(0, user.getChildSiteSettingsId().size());
        Assert.assertEquals(0, user.getFilledForms().size());
        Assert.assertNull(site.getChildSiteSettings());


        CreateChildSiteResponse response = new CreateChildSiteService().execute(request);
        Assert.assertNotNull(response.getNextPageHtml());

        Assert.assertEquals(1, user.getFilledForms().size());
        ChildSiteSettings childSiteSettings = persistance.getChildSiteSettingsById(user.getChildSiteSettingsId().get(0));

        Assert.assertEquals(1, user.getChildSiteSettingsId().size());
        FilledForm filledForm = user.getFilledForms().get(0);
        Assert.assertEquals(3, filledForm.getFilledFormItems().size());
        Assert.assertEquals("firstName", filledForm.getFilledFormItems().get(0).getItemValues().get(0));
        Assert.assertEquals("lastName", filledForm.getFilledFormItems().get(1).getItemValues().get(0));
        Assert.assertEquals("adress", filledForm.getFilledFormItems().get(2).getItemValues().get(0));
        Assert.assertEquals(new Integer(childSiteSettings.getChildSiteSettingsId()), filledForm.getChildSiteSettingsId());
        Assert.assertEquals(response.getFilledFormId(), filledForm.getFilledFormId());

        Assert.assertNotNull(childSiteSettings.getParentSite().getSiteId());
        Assert.assertEquals(childSiteSettings.getParentSite().getSiteId(), site.getSiteId());
        Assert.assertNull(childSiteSettings.getSite());
        Assert.assertEquals(0, childSiteSettings.getCreditCardId());
        Assert.assertNotNull(childSiteSettings.getChildSiteRegistration());
        Assert.assertEquals(childSiteSettings.getChildSiteRegistration().getFormId(), childSiteRegistration.getFormId());
    }*/

    @Test
    public void testCreateChildSiteUser_withoutRightsAndUser() {
        final Site site = TestUtil.createSite();
        final CreateChildSiteService service = new CreateChildSiteService();

        Assert.assertEquals(0, site.getUserOnSiteRights().size());

        final User user = service.createChildSiteUser("email@email.com", "password", site);


        Assert.assertNotNull(user);
        Assert.assertEquals("email@email.com", user.getEmail());
        Assert.assertEquals("password", user.getPassword());
        Assert.assertEquals(1, site.getUserOnSiteRights().size());
        Assert.assertEquals(user, site.getUserOnSiteRights().get(0).getId().getUser());
    }


    @Test
    public void testCreateChildSiteUser_withUserWithoutRights() {
        final Site site = TestUtil.createSite();
        User user = TestUtil.createUser();
        user.setEmail("email@email.com");
        user.setPassword("password");
        final CreateChildSiteService service = new CreateChildSiteService();

        Assert.assertEquals(0, site.getUserOnSiteRights().size());

        final User newUser = service.createChildSiteUser(user.getEmail(), user.getPassword(), site);


        Assert.assertNotNull(newUser);
        Assert.assertEquals(newUser, user);
        Assert.assertEquals(1, site.getUserOnSiteRights().size());
        Assert.assertEquals(newUser, site.getUserOnSiteRights().get(0).getId().getUser());
    }


    @Test
    public void testCreateChildSiteUser_withUserAndRights() {
        final Site site = TestUtil.createSite();
        User user = TestUtil.createUser();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        user.setEmail("email@email.com");
        user.setPassword("password");
        final CreateChildSiteService service = new CreateChildSiteService();

        Assert.assertEquals(1, site.getUserOnSiteRights().size());
        Assert.assertEquals(user, site.getUserOnSiteRights().get(0).getId().getUser());

        final User newUser = service.createChildSiteUser(user.getEmail(), user.getPassword(), site);


        Assert.assertNotNull(newUser);
        Assert.assertEquals(newUser, user);
        Assert.assertEquals(1, site.getUserOnSiteRights().size());
        Assert.assertEquals(newUser, site.getUserOnSiteRights().get(0).getId().getUser());
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
}
