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
import com.shroggle.presentation.form.ShowFormService;
import com.shroggle.entity.*;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.form.FormManager;
import com.shroggle.logic.form.FormItemManager;
import com.shroggle.logic.form.FormItemsManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Author: dmitry.solomadin
 * Date: 22.01.2009
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class ShowFormServiceTest {

    private ShowFormService service = new ShowFormService();
    private FormManager formManager = new FormManager();

    @Test
    public void fillFormItems() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm();

        service.fillFormItems(registrationForm.getType(), registrationForm.getFormId());
        final List<FormItemName> initFormItems = new ArrayList<FormItemName>();

        final List<FormItemName> allItems = Arrays.asList(FormItemName.values());
        for (FormItemName formItem : allItems) {
            if (formItem.getFormItemFilters().contains(FormItemFilter.BASIC)
                    || formItem.getFormItemFilters().contains(FormItemFilter.ALL_FILTERS)) {
                initFormItems.add(formItem);
            }
        }

        initFormItems.remove(FormItemName.REGISTRATION_EMAIL);
        initFormItems.remove(FormItemName.REGISTRATION_PASSWORD);
        Assert.assertEquals(initFormItems, service.getInitFormItems());

        List<DraftFormItem> defaultFormItems = new ArrayList<DraftFormItem>();
        defaultFormItems.add(FormItemManager.createFormItemByName(FormItemName.FIRST_NAME, 0, false));
        defaultFormItems.add(FormItemManager.createFormItemByName(FormItemName.LAST_NAME, 1, false));

        Assert.assertEquals(defaultFormItems.size(), service.getExistingFormItems().size());
        for (int i = 0; i < service.getExistingFormItems().size(); i++) {
            Assert.assertEquals(defaultFormItems.get(i).getFormItemName(), service.getExistingFormItems().get(i).getFormItemName());
            Assert.assertEquals(defaultFormItems.get(i).getItemName(), service.getExistingFormItems().get(i).getItemName());
            Assert.assertEquals(defaultFormItems.get(i).getPosition(), service.getExistingFormItems().get(i).getPosition());
            Assert.assertEquals(defaultFormItems.get(i).isRequired(), service.getExistingFormItems().get(i).isRequired());
        }
    }

    @Test(expected = UserNotLoginedException.class)
    public void fillFormItemsWithoutLoggedUser() throws Exception {
        DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm();

        service.fillFormItems(registrationForm.getType(), registrationForm.getFormId());
    }

    @Test
    public void getFormItemsSortedByPosition() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> registrationFormItems = new ArrayList<DraftFormItem>();
        registrationFormItems.add(FormItemManager.createFormItemByName(FormItemName.FIRST_NAME, 1, false));
        registrationFormItems.add(FormItemManager.createFormItemByName(FormItemName.LAST_NAME, 0, false));

        DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm();

        registrationForm.setFormItems(registrationFormItems);

        List<DraftFormItem> formItems = service.getFormItems(registrationForm.getFormId(), registrationForm.getType());

        Assert.assertEquals(registrationFormItems.size(), formItems.size());
        for (int i = 0; i < service.getExistingFormItems().size(); i++) {
            Assert.assertEquals(registrationFormItems.get(i).getFormItemName(), formItems.get(i).getFormItemName());
            Assert.assertEquals(registrationFormItems.get(i).getForm(), formItems.get(i).getForm());
            Assert.assertEquals(registrationFormItems.get(i).getItemName(), formItems.get(i).getItemName());
            Assert.assertEquals(registrationFormItems.get(i).getPosition(), formItems.get(i).getPosition());
            Assert.assertEquals(registrationFormItems.get(i).isRequired(), formItems.get(i).isRequired());
        }
    }

    @Test
    public void getFormItemsForRegistrationDefault() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> formItems = service.getFormItems(0, FormType.REGISTRATION);
        List<DraftFormItem> registrationFormItems = service.getDefaultRegistrationFormItems();

        Assert.assertEquals(registrationFormItems.size(), formItems.size());
        for (int i = 0; i < service.getExistingFormItems().size(); i++) {
            Assert.assertEquals(registrationFormItems.get(i).getFormItemName(), formItems.get(i).getFormItemName());
            Assert.assertEquals(registrationFormItems.get(i).getForm(), formItems.get(i).getForm());
            Assert.assertEquals(registrationFormItems.get(i).getItemName(), formItems.get(i).getItemName());
            Assert.assertEquals(registrationFormItems.get(i).getPosition(), formItems.get(i).getPosition());
            Assert.assertEquals(registrationFormItems.get(i).isRequired(), formItems.get(i).isRequired());
        }
    }

    @Test
    public void getFormItemsForDefault() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> formItems = service.getFormItems(0, FormType.CUSTOM_FORM);
        List<DraftFormItem> registrationFormItems = service.getDefaultFormItems();

        Assert.assertEquals(registrationFormItems.size(), formItems.size());
        for (int i = 0; i < service.getExistingFormItems().size(); i++) {
            Assert.assertEquals(registrationFormItems.get(i).getFormItemName(), formItems.get(i).getFormItemName());
            Assert.assertEquals(registrationFormItems.get(i).getForm(), formItems.get(i).getForm());
            Assert.assertEquals(registrationFormItems.get(i).getItemName(), formItems.get(i).getItemName());
            Assert.assertEquals(registrationFormItems.get(i).getPosition(), formItems.get(i).getPosition());
            Assert.assertEquals(registrationFormItems.get(i).isRequired(), formItems.get(i).isRequired());
        }
    }

    @Test
    public void getFormItemsDefaultContactUs() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> formItems = service.getFormItems(0, FormType.CONTACT_US);
        List<DraftFormItem> registrationFormItems = service.getDefaultContactUsFormItems();

        Assert.assertEquals(registrationFormItems.size(), formItems.size());
        for (int i = 0; i < service.getExistingFormItems().size(); i++) {
            Assert.assertEquals(registrationFormItems.get(i).getFormItemName(), formItems.get(i).getFormItemName());
            Assert.assertEquals(registrationFormItems.get(i).getForm(), formItems.get(i).getForm());
            Assert.assertEquals(registrationFormItems.get(i).getItemName(), formItems.get(i).getItemName());
            Assert.assertEquals(registrationFormItems.get(i).getPosition(), formItems.get(i).getPosition());
            Assert.assertEquals(registrationFormItems.get(i).isRequired(), formItems.get(i).isRequired());
        }
    }

    @Test(expected = UserNotLoginedException.class)
    public void getFormItemsWithoutLoginedUser() throws Exception {
        DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm();

        service.getFormItems(registrationForm.getFormId(), registrationForm.getType());
    }

    @Test
    public void fillFormItemsContactUsDefault() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        service.fillFormItems(FormType.CONTACT_US, null);
        final List<FormItemName> initFormItems = new ArrayList<FormItemName>();

        final List<FormItemName> allItems = Arrays.asList(FormItemName.values());
        for (FormItemName formItem : allItems) {
            if (formItem.getFormItemFilters().contains(FormItemFilter.BASIC)
                    || formItem.getFormItemFilters().contains(FormItemFilter.ALL_FILTERS)) {
                initFormItems.add(formItem);
            }
        }

        initFormItems.remove(FormItemName.REGISTRATION_EMAIL);
        initFormItems.remove(FormItemName.REGISTRATION_PASSWORD);
        Assert.assertEquals(initFormItems, service.getInitFormItems());

        List<DraftFormItem> defaultFormItems = service.getDefaultContactUsFormItems();

        Assert.assertEquals(defaultFormItems.size(), service.getExistingFormItems().size());
        for (int i = 0; i < service.getExistingFormItems().size(); i++) {
            Assert.assertEquals(defaultFormItems.get(i).getFormItemName(), service.getExistingFormItems().get(i).getFormItemName());
            Assert.assertEquals(defaultFormItems.get(i).getForm(), service.getExistingFormItems().get(i).getForm());
            Assert.assertEquals(defaultFormItems.get(i).getItemName(), service.getExistingFormItems().get(i).getItemName());
            Assert.assertEquals(defaultFormItems.get(i).getPosition(), service.getExistingFormItems().get(i).getPosition());
            Assert.assertEquals(defaultFormItems.get(i).isRequired(), service.getExistingFormItems().get(i).isRequired());
        }
    }

    @Test
    public void fillFormItemsDefault() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        service.fillFormItems(FormType.CUSTOM_FORM, null);
        final List<FormItemName> initFormItems = new ArrayList<FormItemName>();

        final List<FormItemName> allItems = Arrays.asList(FormItemName.values());
        for (FormItemName formItem : allItems) {
            if (formItem.getFormItemFilters().contains(FormItemFilter.BASIC)
                    || formItem.getFormItemFilters().contains(FormItemFilter.ALL_FILTERS)) {
                initFormItems.add(formItem);
            }
        }

        initFormItems.remove(FormItemName.REGISTRATION_EMAIL);
        initFormItems.remove(FormItemName.REGISTRATION_PASSWORD);
        Assert.assertEquals(initFormItems, service.getInitFormItems());

        List<DraftFormItem> defaultFormItems = service.getDefaultFormItems();

        Assert.assertEquals(defaultFormItems.size(), service.getExistingFormItems().size());
        for (int i = 0; i < service.getExistingFormItems().size(); i++) {
            Assert.assertEquals(defaultFormItems.get(i).getFormItemName(), service.getExistingFormItems().get(i).getFormItemName());
            Assert.assertEquals(defaultFormItems.get(i).getForm(), service.getExistingFormItems().get(i).getForm());
            Assert.assertEquals(defaultFormItems.get(i).getItemName(), service.getExistingFormItems().get(i).getItemName());
            Assert.assertEquals(defaultFormItems.get(i).getPosition(), service.getExistingFormItems().get(i).getPosition());
            Assert.assertEquals(defaultFormItems.get(i).isRequired(), service.getExistingFormItems().get(i).isRequired());
        }
    }

    @Test
    public void fillFormItemsForRegistrationDefault() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        service.fillFormItems(FormType.REGISTRATION, null);
        final List<FormItemName> initFormItems = new ArrayList<FormItemName>();

        final List<FormItemName> allItems = Arrays.asList(FormItemName.values());
        for (FormItemName formItem : allItems) {
            if (formItem.getFormItemFilters().contains(FormItemFilter.BASIC)
                    || formItem.getFormItemFilters().contains(FormItemFilter.ALL_FILTERS)) {
                initFormItems.add(formItem);
            }
        }

        initFormItems.remove(FormItemName.REGISTRATION_EMAIL);
        initFormItems.remove(FormItemName.REGISTRATION_PASSWORD);
        Assert.assertEquals(initFormItems, service.getInitFormItems());

        List<DraftFormItem> defaultFormItems = service.getDefaultRegistrationFormItems();

        Assert.assertEquals(defaultFormItems.size(), service.getExistingFormItems().size());
        for (int i = 0; i < service.getExistingFormItems().size(); i++) {
            Assert.assertEquals(defaultFormItems.get(i).getFormItemName(), service.getExistingFormItems().get(i).getFormItemName());
            Assert.assertEquals(defaultFormItems.get(i).getForm(), service.getExistingFormItems().get(i).getForm());
            Assert.assertEquals(defaultFormItems.get(i).getItemName(), service.getExistingFormItems().get(i).getItemName());
            Assert.assertEquals(defaultFormItems.get(i).getPosition(), service.getExistingFormItems().get(i).getPosition());
            Assert.assertEquals(defaultFormItems.get(i).isRequired(), service.getExistingFormItems().get(i).isRequired());
        }
    }

    @Test
    public void updateByFilterAll() throws Exception {
        final List<FormItemName> allItems = new ArrayList<FormItemName>();
        final List<YourFormTableFormItemInfo> filteredItems = new ArrayList<YourFormTableFormItemInfo>();
        final International international = ServiceLocator.getInternationStorage().get("formTable", Locale.US);
        allItems.addAll(Arrays.asList(FormItemName.values()));
        new FormItemsManager().removeItemsNotShownInInitTable(allItems);

        for (FormItemName item : allItems) {
            final YourFormTableFormItemInfo yourFormTableFormItemInfo = new YourFormTableFormItemInfo();
            yourFormTableFormItemInfo.setFormItemName(item.toString());
            yourFormTableFormItemInfo.setFieldName(international.get(item.toString() + "_FN"));
            yourFormTableFormItemInfo.setItemFieldType(FormItemManager.getItemFieldType(item));
            yourFormTableFormItemInfo.setItemDescription(FormItemManager.getItemDesc(item));
            filteredItems.add(yourFormTableFormItemInfo);
        }

        final List<YourFormTableFormItemInfo> returnList = service.updateByFilter(FormItemFilter.NO_FILTER);
        for (int i = 0; i < filteredItems.size(); i++) {
            Assert.assertEquals(filteredItems.get(i).getFieldName(), returnList.get(i).getFieldName());
            Assert.assertEquals(filteredItems.get(i).getItemDescription(), returnList.get(i).getItemDescription());
            Assert.assertEquals(filteredItems.get(i).getItemFieldType(), returnList.get(i).getItemFieldType());
            Assert.assertEquals(filteredItems.get(i).getFormItemName(), returnList.get(i).getFormItemName());
        }
    }
}
