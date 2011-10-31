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
import com.shroggle.exception.ChildSiteRegistrationNameNotUnique;
import com.shroggle.exception.PageBreakBeforeRequiredFieldsException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.TimeInterval;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;


@RunWith(value = TestRunnerWithMockServices.class)
public class CreateChildSiteRegistrationServiceTest {

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    /*------------------------------------------------Form Settings tab-----------------------------------------------*/
    @Test
    public void executeFromManageItems() throws Exception {
        final Site site = TestUtil.createSite();
        User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.loginUser(user);

        DraftChildSiteRegistration registartion = TestUtil.createChildSiteRegistration("Name1", "Comment1", site);
        TestUtil.createChildSiteRegistration("Name2", "Comment2", site);
        TestUtil.createChildSiteRegistration("Name3", "Comment3", site);
        List<FormItemName> formItemNames = Arrays.asList(FormItemName.values());
        List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        for (int i = 0; i < 5; i++) {
            formItems.add(TestUtil.createFormItem(formItemNames.get(i), i));
        }

        request.setDescription("comment_changed");
        request.setName("name_changed");
        request.setFormItems(formItems);
        request.setFormId(registartion.getFormId());

        CheckDateResponse response = service.saveFormSettingsTab(request);

        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getWidgetInfo());
        Assert.assertNull(response.getWidgetInfo().getWidget());
        Assert.assertEquals("name_changed", registartion.getName());
        Assert.assertEquals("comment_changed", registartion.getDescription());
        Assert.assertEquals(true, registartion.isModified());
    }

    @Test
    public void executeFormSiteEditPage() throws Exception {
        final Site site = TestUtil.createSite();
        final PageManager page = TestUtil.createPageVersionAndPage(site);
        User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.loginUser(user);

        DraftChildSiteRegistration registartion = TestUtil.createChildSiteRegistration("Name1", "Comment1", site);
        TestUtil.createChildSiteRegistration("Name2", "Comment2", site);
        TestUtil.createChildSiteRegistration("Name3", "Comment3", site);
        List<FormItemName> formItemNames = Arrays.asList(FormItemName.values());
        List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        for (int i = 0; i < 5; i++) {
            formItems.add(TestUtil.createFormItem(formItemNames.get(i), i));
        }

        final WidgetItem widget = TestUtil.createWidgetItem();
        widget.setDraftItem(registartion);
        page.addWidget(widget);

        request.setDescription("comment_changed");
        request.setName("name_changed");
        request.setFormItems(formItems);
        request.setFormId(registartion.getFormId());
        request.setWidgetId(widget.getWidgetId());

        CheckDateResponse response = service.saveFormSettingsTab(request);

        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getWidgetInfo());
        Assert.assertEquals(widget.getWidgetId(), response.getWidgetInfo().getWidget().getWidgetId());
        Assert.assertEquals("name_changed", registartion.getName());
        Assert.assertEquals("comment_changed", registartion.getDescription());
        Assert.assertEquals(true, registartion.isModified());
    }

    @Test
    public void executeWithShowIncomeSettings() throws Exception {
        final Site site = TestUtil.createSite();
        User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.loginUser(user);

        DraftChildSiteRegistration registartion = TestUtil.createChildSiteRegistration("Name1", "Comment1", site);
        TestUtil.createChildSiteRegistration("Name2", "Comment2", site);
        TestUtil.createChildSiteRegistration("Name3", "Comment3", site);
        List<FormItemName> formItemNames = Arrays.asList(FormItemName.values());
        List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        for (int i = 0; i < 5; i++) {
            formItems.add(TestUtil.createFormItem(formItemNames.get(i), i));
        }

        request.setDescription("comment1");
        request.setName("name1");
        request.setFormItems(formItems);
        request.setFormId(registartion.getFormId());

        final CheckDateResponse response = service.saveFormSettingsTab(request);
        Assert.assertTrue(response.isShowIncomeSettingsWindow());
        Assert.assertNotNull(registartion);
        Assert.assertEquals(registartion.getFormId(), registartion.getFormId());
        Assert.assertEquals("name1", registartion.getName());
    }

    @Test
    public void executeWithoutShowIncomeSettings() throws Exception {
        final Site site = TestUtil.createSite();
        final IncomeSettings incomeSettings = new SiteManager(site).getOrCreateIncomeSettings();
        incomeSettings.setPaypalAddress("some address");
        User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.loginUser(user);

        DraftChildSiteRegistration registartion = TestUtil.createChildSiteRegistration("Name1", "Comment1", site);
        TestUtil.createChildSiteRegistration("Name2", "Comment2", site);
        TestUtil.createChildSiteRegistration("Name3", "Comment3", site);
        List<FormItemName> formItemNames = Arrays.asList(FormItemName.values());
        List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        for (int i = 0; i < 5; i++) {
            formItems.add(TestUtil.createFormItem(formItemNames.get(i), i));
        }

        request.setDescription("comment1");
        request.setName("name1");
        request.setFormItems(formItems);
        request.setFormId(registartion.getFormId());

        final CheckDateResponse response = service.saveFormSettingsTab(request);
        Assert.assertFalse(response.isShowIncomeSettingsWindow());
        Assert.assertNotNull(registartion);
        Assert.assertEquals(registartion.getFormId(), registartion.getFormId());
        Assert.assertEquals("name1", registartion.getName());
    }

    @Test
    public void executeWithoutIncomeSettings() throws Exception {
        final Site site = TestUtil.createSite();
        /*final IncomeSettings incomeSettings = new SiteManager(site).getOrCreateIncomeSettings();
        incomeSettings.setPaypalAddress("some address");*/
        final User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        user.setEmail("usersEmail");
        TestUtil.loginUser(user);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        DraftChildSiteRegistration registartion = TestUtil.createChildSiteRegistration("Name1", "Comment1", site);
        TestUtil.createChildSiteRegistration("Name2", "Comment2", site);
        TestUtil.createChildSiteRegistration("Name3", "Comment3", site);
        List<FormItemName> formItemNames = Arrays.asList(FormItemName.values());
        List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        for (int i = 0; i < 5; i++) {
            formItems.add(TestUtil.createFormItem(formItemNames.get(i), i));
        }

        WidgetItem widget = TestUtil.createTextWidget();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        request.setWidgetId(widget.getWidgetId());
        request.setDescription("comment1");
        request.setName("name1");
        request.setFormItems(formItems);
        request.setFormId(registartion.getFormId());

        final CheckDateResponse response = service.saveFormSettingsTab(request);

        Assert.assertTrue(response.isShowIncomeSettingsWindow());
        Assert.assertEquals("usersEmail", (String) service.getContext().getHttpServletRequest().getAttribute("paypalEmail"));
    }

    @Test(expected = PageBreakBeforeRequiredFieldsException.class)
    public void executeWithPageBreakBeforeMandatory() throws Exception {
        final Site site = TestUtil.createSite();
        User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.loginUser(user);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        TestUtil.createChildSiteRegistration("Name1", "Comment1", site);
        TestUtil.createChildSiteRegistration("Name2", "Comment2", site);
        TestUtil.createChildSiteRegistration("Name3", "Comment3", site);
        List<DraftFormItem> formItems = TestUtil.createFormItems(FormItemName.FIRST_NAME, FormItemName.PAGE_BREAK, FormItemName.REGISTRATION_EMAIL);

        WidgetItem widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        request.setWidgetId(widget.getWidgetId());
        request.setDescription("comment1");
        request.setName("name1");
        request.setFormItems(formItems);
        request.setFormId(-1);

        service.saveFormSettingsTab(request);
    }

    @Test(expected = ChildSiteRegistrationNameNotUnique.class)
    public void executeWithNotUniqueName() throws Exception {
        final Site site = TestUtil.createSite();
        User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.loginUser(user);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        DraftChildSiteRegistration registartion = TestUtil.createChildSiteRegistration("Name1", "Comment1", site);
        TestUtil.createChildSiteRegistration("Name2", "Comment2", site);
        TestUtil.createChildSiteRegistration("Name3", "Comment3", site);
        List<FormItemName> formItemNames = Arrays.asList(FormItemName.values());
        List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        for (int i = 0; i < 5; i++) {
            formItems.add(TestUtil.createFormItem(formItemNames.get(i), i));
        }

        WidgetItem widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        request.setWidgetId(widget.getWidgetId());
        request.setDescription("comment1");
        request.setName("Name2");
        request.setFormItems(formItems);
        request.setFormId(registartion.getFormId());

        service.saveFormSettingsTab(request);
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithUserNotLogined() throws Exception {
        service.saveFormSettingsTab(request);
    }
    /*------------------------------------------------Form Settings tab-----------------------------------------------*/

    /*----------------------------------------------Network Settings tab----------------------------------------------*/
    @Test
    public void executeFromManageItems_saveNetworkSettingsTab() throws Exception {
        final Site site = TestUtil.createSite();
        User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.loginUser(user);

        DraftChildSiteRegistration registartion = TestUtil.createChildSiteRegistration("Name1", "Comment1", site);
        TestUtil.createChildSiteRegistration("Name2", "Comment2", site);
        TestUtil.createChildSiteRegistration("Name3", "Comment3", site);
        List<FormItemName> formItemNames = Arrays.asList(FormItemName.values());
        List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        for (int i = 0; i < 5; i++) {
            formItems.add(TestUtil.createFormItem(formItemNames.get(i), i));
        }

        request.setName("name_changed");
        request.setFormItems(formItems);
        request.setFormId(registartion.getFormId());
        request.setPrice250mb(0.0);
        request.setPrice500mb(0.0);
        request.setPrice1gb(0.0);
        request.setPrice3gb(0.0);
        request.setFromEmail("from email address");

        request.setUseOwnAuthorize(true);
        request.setUseOwnPaypal(false);
        request.setAuthorizeLogin("getAuthorizeLogin");
        request.setAuthorizeTransactionKey("getAuthorizeTransactionKey");
        request.setPaypalApiUserName("getPaypalApiUserName");
        request.setPaypalApiPassword("getPaypalApiPassword");
        request.setPaypalSignature("getPaypalSignature");

        CheckDateResponse response = service.saveNetworkSettingsTab(request);

        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getWidgetInfo());
        Assert.assertNull(response.getWidgetInfo().getWidget());
        Assert.assertEquals("Name doesn`t changed in this tab.",
                "Name1", registartion.getName());

        Assert.assertEquals(true, registartion.isUseOwnAuthorize());
        Assert.assertEquals(false, registartion.isUseOwnPaypal());

        Assert.assertEquals("getAuthorizeLogin", registartion.getAuthorizeLogin());
        Assert.assertEquals("getAuthorizeTransactionKey", registartion.getAuthorizeTransactionKey());
        Assert.assertEquals("getPaypalApiUserName", registartion.getPaypalApiUserName());
        Assert.assertEquals("getPaypalApiPassword", registartion.getPaypalApiPassword());
        Assert.assertEquals("getPaypalSignature", registartion.getPaypalSignature());

        Assert.assertEquals(true, registartion.isModified());
    }

    @Test
    public void executeFormSiteEditPage_saveNetworkSettingsTab() throws Exception {
        final Site site = TestUtil.createSite();
        final PageManager page = TestUtil.createPageVersionAndPage(site);
        User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.loginUser(user);

        DraftChildSiteRegistration registartion = TestUtil.createChildSiteRegistration("Name1", "Comment1", site);
        TestUtil.createChildSiteRegistration("Name2", "Comment2", site);
        TestUtil.createChildSiteRegistration("Name3", "Comment3", site);
        List<FormItemName> formItemNames = Arrays.asList(FormItemName.values());
        List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        for (int i = 0; i < 5; i++) {
            formItems.add(TestUtil.createFormItem(formItemNames.get(i), i));
        }

        final WidgetItem widget = TestUtil.createWidgetItem();
        widget.setDraftItem(registartion);
        page.addWidget(widget);

        request.setName("name_changed");
        request.setFormItems(formItems);
        request.setFormId(registartion.getFormId());
        request.setWidgetId(widget.getWidgetId());
        request.setPrice250mb(0.0);
        request.setPrice500mb(0.0);
        request.setPrice1gb(0.0);
        request.setPrice3gb(0.0);
        request.setFromEmail("from email address");

        CheckDateResponse response = service.saveNetworkSettingsTab(request);

        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getWidgetInfo());
        Assert.assertEquals(widget.getWidgetId(), response.getWidgetInfo().getWidget().getWidgetId());
        Assert.assertEquals("Name doesn`t changed in this tab.",
                "Name1", registartion.getName());
        Assert.assertEquals("Description", registartion.getDescription());
        Assert.assertEquals(true, registartion.isModified());
    }

    @Test
    public void executeWithShowIncomeSettings_saveNetworkSettingsTab() throws Exception {
        final Site site = TestUtil.createSite();
        User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.loginUser(user);

        DraftChildSiteRegistration registartion = TestUtil.createChildSiteRegistration("Name1", "Comment1", site);
        TestUtil.createChildSiteRegistration("Name2", "Comment2", site);
        TestUtil.createChildSiteRegistration("Name3", "Comment3", site);
        List<FormItemName> formItemNames = Arrays.asList(FormItemName.values());
        List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        for (int i = 0; i < 5; i++) {
            formItems.add(TestUtil.createFormItem(formItemNames.get(i), i));
        }

        request.setDescription("comment1");
        request.setName("name1");
        request.setFormItems(formItems);
        request.setFormId(registartion.getFormId());
        request.setPrice250mb(0.0);
        request.setPrice500mb(0.0);
        request.setPrice1gb(0.0);
        request.setPrice3gb(0.0);

        final CheckDateResponse response = service.saveNetworkSettingsTab(request);
        Assert.assertTrue(response.isShowIncomeSettingsWindow());
        Assert.assertNotNull(registartion);
        Assert.assertEquals(registartion.getFormId(), registartion.getFormId());
        Assert.assertEquals("Name doesn`t changed in this tab.",
                "Name1", registartion.getName());
    }

    @Test
    public void executeWithoutShowIncomeSettings_saveNetworkSettingsTab() throws Exception {
        final Site site = TestUtil.createSite();
        final IncomeSettings incomeSettings = new SiteManager(site).getOrCreateIncomeSettings();
        incomeSettings.setPaypalAddress("some address");
        User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.loginUser(user);

        DraftChildSiteRegistration registartion = TestUtil.createChildSiteRegistration("Name1", "Comment1", site);
        TestUtil.createChildSiteRegistration("Name2", "Comment2", site);
        TestUtil.createChildSiteRegistration("Name3", "Comment3", site);
        List<FormItemName> formItemNames = Arrays.asList(FormItemName.values());
        List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        for (int i = 0; i < 5; i++) {
            formItems.add(TestUtil.createFormItem(formItemNames.get(i), i));
        }

        request.setDescription("comment1");
        request.setName("name1");
        request.setFormItems(formItems);
        request.setFormId(registartion.getFormId());
        request.setPrice250mb(0.0);
        request.setPrice500mb(0.0);
        request.setPrice1gb(0.0);
        request.setPrice3gb(0.0);

        final CheckDateResponse response = service.saveNetworkSettingsTab(request);
        Assert.assertFalse(response.isShowIncomeSettingsWindow());
        Assert.assertNotNull(registartion);
        Assert.assertEquals(registartion.getFormId(), registartion.getFormId());
        Assert.assertEquals("Name doesn`t changed in this tab.",
                "Name1", registartion.getName());
    }

    @Test
    public void executeWithoutIncomeSettings_saveNetworkSettingsTab() throws Exception {
        final Site site = TestUtil.createSite();
        /*final IncomeSettings incomeSettings = new SiteManager(site).getOrCreateIncomeSettings();
        incomeSettings.setPaypalAddress("some address");*/
        final User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        user.setEmail("usersEmail");
        TestUtil.loginUser(user);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        DraftChildSiteRegistration registartion = TestUtil.createChildSiteRegistration("Name1", "Comment1", site);
        TestUtil.createChildSiteRegistration("Name2", "Comment2", site);
        TestUtil.createChildSiteRegistration("Name3", "Comment3", site);
        List<FormItemName> formItemNames = Arrays.asList(FormItemName.values());
        List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        for (int i = 0; i < 5; i++) {
            formItems.add(TestUtil.createFormItem(formItemNames.get(i), i));
        }

        WidgetItem widget = TestUtil.createTextWidget();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        request.setWidgetId(widget.getWidgetId());
        request.setDescription("comment1");
        request.setName("name1");
        request.setFormItems(formItems);
        request.setFormId(registartion.getFormId());
        request.setPrice250mb(0.0);
        request.setPrice500mb(0.0);
        request.setPrice1gb(0.0);
        request.setPrice3gb(0.0);

        final CheckDateResponse response = service.saveNetworkSettingsTab(request);

        Assert.assertTrue(response.isShowIncomeSettingsWindow());
        Assert.assertEquals("usersEmail", (String) service.getContext().getHttpServletRequest().getAttribute("paypalEmail"));
    }

    @Test(expected = ChildSiteRegistrationNameNotUnique.class)
    public void executeWithNotUniqueName_saveNetworkSettingsTab() throws Exception {
        final Site site = TestUtil.createSite();
        User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.loginUser(user);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        DraftChildSiteRegistration registartion = TestUtil.createChildSiteRegistration("Name1", "Comment1", site);
        TestUtil.createChildSiteRegistration("Name2", "Comment2", site);
        TestUtil.createChildSiteRegistration("Name3", "Comment3", site);
        List<FormItemName> formItemNames = Arrays.asList(FormItemName.values());
        List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        for (int i = 0; i < 5; i++) {
            formItems.add(TestUtil.createFormItem(formItemNames.get(i), i));
        }

        WidgetItem widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        request.setWidgetId(widget.getWidgetId());
        request.setDescription("comment1");
        request.setName("Name2");
        request.setFormItems(formItems);
        request.setFormId(registartion.getFormId());

        service.saveNetworkSettingsTab(request);
    }

    @Test
    public void executeWithWrongStartDate_saveNetworkSettingsTab() throws Exception {
        final Site site = TestUtil.createSite();
        User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.loginUser(user);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        TestUtil.createChildSiteRegistration("Name1", "Comment1", site);
        TestUtil.createChildSiteRegistration("Name2", "Comment2", site);
        TestUtil.createChildSiteRegistration("Name3", "Comment3", site);
        List<FormItemName> formItemNames = Arrays.asList(FormItemName.values());
        List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        for (int i = 0; i < 5; i++) {
            formItems.add(TestUtil.createFormItem(formItemNames.get(i), i));
        }

        WidgetItem widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        request.setWidgetId(widget.getWidgetId());
        request.setDescription("comment1");
        request.setName("name1");
        request.setFormItems(formItems);
        request.setFormId(-1);
        request.setStartDate("01 12 210ad0");
        request.setEndDate("01 12 2100");
        request.setUseStartDate(true);
        request.setUseEndDate(true);

        CheckDateResponse response = service.saveNetworkSettingsTab(request);
        Assert.assertTrue(response.isWrongStartDate());
        Assert.assertFalse(response.isWrongEndDate());
        Assert.assertFalse(response.isEndBeforeCurrent());
        Assert.assertFalse(response.isEndBeforeStart());
    }

    @Test
    public void executeWithWrongEndDate_saveNetworkSettingsTab() throws Exception {
        final Site site = TestUtil.createSite();
        User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.loginUser(user);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        TestUtil.createChildSiteRegistration("Name1", "Comment1", site);
        TestUtil.createChildSiteRegistration("Name2", "Comment2", site);
        TestUtil.createChildSiteRegistration("Name3", "Comment3", site);
        List<FormItemName> formItemNames = Arrays.asList(FormItemName.values());
        List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        for (int i = 0; i < 5; i++) {
            formItems.add(TestUtil.createFormItem(formItemNames.get(i), i));
        }

        WidgetItem widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        request.setWidgetId(widget.getWidgetId());
        request.setDescription("comment1");
        request.setName("name1");
        request.setFormItems(formItems);
        request.setFormId(-1);
        request.setStartDate("01 12 2000");
        request.setEndDate("01 12 210ad0");
        request.setUseStartDate(true);
        request.setUseEndDate(true);

        CheckDateResponse response = service.saveNetworkSettingsTab(request);
        Assert.assertFalse(response.isWrongStartDate());
        Assert.assertTrue(response.isWrongEndDate());
        Assert.assertFalse(response.isEndBeforeCurrent());
        Assert.assertFalse(response.isEndBeforeStart());
    }

    @Test
    public void executeWithWrongStartEndDate_saveNetworkSettingsTab() throws Exception {
        final Site site = TestUtil.createSite();
        User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.loginUser(user);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        TestUtil.createChildSiteRegistration("Name1", "Comment1", site);
        TestUtil.createChildSiteRegistration("Name2", "Comment2", site);
        TestUtil.createChildSiteRegistration("Name3", "Comment3", site);
        List<FormItemName> formItemNames = Arrays.asList(FormItemName.values());
        List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        for (int i = 0; i < 5; i++) {
            formItems.add(TestUtil.createFormItem(formItemNames.get(i), i));
        }

        WidgetItem widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        request.setWidgetId(widget.getWidgetId());
        request.setDescription("comment1");
        request.setName("name1");
        request.setFormItems(formItems);
        request.setFormId(-1);
        request.setStartDate("01 12 20asdf00");
        request.setEndDate("01 12 210ad0");
        request.setUseStartDate(true);
        request.setUseEndDate(true);

        CheckDateResponse response = service.saveNetworkSettingsTab(request);
        Assert.assertTrue(response.isWrongStartDate());
        Assert.assertTrue(response.isWrongEndDate());
        Assert.assertFalse(response.isEndBeforeCurrent());
        Assert.assertFalse(response.isEndBeforeStart());
    }

    @Test
    public void executeWithEndDateBeforeCurrentDate_saveNetworkSettingsTab() throws Exception {
        final Site site = TestUtil.createSite();
        User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.loginUser(user);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        TestUtil.createChildSiteRegistration("Name1", "Comment1", site);
        TestUtil.createChildSiteRegistration("Name2", "Comment2", site);
        TestUtil.createChildSiteRegistration("Name3", "Comment3", site);
        List<FormItemName> formItemNames = Arrays.asList(FormItemName.values());
        List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        for (int i = 0; i < 5; i++) {
            formItems.add(TestUtil.createFormItem(formItemNames.get(i), i));
        }

        WidgetItem widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        request.setWidgetId(widget.getWidgetId());
        request.setDescription("comment1");
        request.setName("name1");
        request.setFormItems(formItems);
        request.setFormId(-1);
        request.setStartDate("01 12 2000");
        request.setEndDate("01 12 2000");
        request.setUseStartDate(true);
        request.setUseEndDate(true);

        CheckDateResponse response = service.saveNetworkSettingsTab(request);
        Assert.assertFalse(response.isWrongStartDate());
        Assert.assertFalse(response.isWrongEndDate());
        Assert.assertTrue(response.isEndBeforeCurrent());
        Assert.assertFalse(response.isEndBeforeStart());
    }


    @Test
    public void executeWithEndDateBeforeStartDate_saveNetworkSettingsTab() throws Exception {
        final Site site = TestUtil.createSite();
        User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.loginUser(user);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        TestUtil.createChildSiteRegistration("Name1", "Comment1", site);
        TestUtil.createChildSiteRegistration("Name2", "Comment2", site);
        TestUtil.createChildSiteRegistration("Name3", "Comment3", site);
        List<FormItemName> formItemNames = Arrays.asList(FormItemName.values());
        List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        for (int i = 0; i < 5; i++) {
            formItems.add(TestUtil.createFormItem(formItemNames.get(i), i));
        }

        WidgetItem widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        request.setWidgetId(widget.getWidgetId());
        request.setDescription("comment1");
        request.setName("name1");
        request.setFormItems(formItems);
        request.setFormId(-1);
        request.setStartDate("01 12 2001");
        request.setEndDate("01 12 2000");
        request.setUseStartDate(true);
        request.setUseEndDate(true);

        CheckDateResponse response = service.saveNetworkSettingsTab(request);
        Assert.assertFalse(response.isWrongStartDate());
        Assert.assertFalse(response.isWrongEndDate());
        Assert.assertTrue(response.isEndBeforeCurrent());
        Assert.assertTrue(response.isEndBeforeStart());
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithUserNotLogined_saveNetworkSettingsTab() throws Exception {
        service.saveNetworkSettingsTab(request);
    }
    /*----------------------------------------------Network Settings tab----------------------------------------------*/


    @Test
    public void testIsEndDateCorrect_oldDate() throws Exception {
        Assert.assertEquals(true, new CreateChildSiteRegistrationService().isEndDateCorrect("01/12/2001").isEndBeforeCurrent());
    }

    @Test
    public void testIsEndDateCorrect_currentDate() throws Exception {
        final String currentDateString = createStringDate(new Date());
        Assert.assertEquals(false, new CreateChildSiteRegistrationService().isEndDateCorrect(currentDateString).isEndBeforeCurrent());
    }

    @Test
    public void testIsEndDateCorrect_currentDatePlusOneDay() throws Exception {
        final String currentDateStringPlusOneDay = createStringDate(new Date(System.currentTimeMillis() + TimeInterval.ONE_DAY.getMillis()));
        Assert.assertEquals(false, new CreateChildSiteRegistrationService().isEndDateCorrect(currentDateStringPlusOneDay).isEndBeforeCurrent());
    }

    @Test
    public void testIsEndDateCorrect_currentDateMinusOneDay() throws Exception {
        final String currentDateStringMinusOneDay = createStringDate(new Date(System.currentTimeMillis() - TimeInterval.ONE_DAY.getMillis()));
        Assert.assertEquals(true, new CreateChildSiteRegistrationService().isEndDateCorrect(currentDateStringMinusOneDay).isEndBeforeCurrent());
    }

    private String createStringDate(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return new StringBuilder().append(calendar.get(Calendar.MONTH) + 1).append("/").append(calendar.get(Calendar.DAY_OF_MONTH)).append("/").append(calendar.get(Calendar.YEAR)).toString();
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final CreateChildSiteRegistrationRequest request = new CreateChildSiteRegistrationRequest();
    private final CreateChildSiteRegistrationService service = new CreateChildSiteRegistrationService();

}
