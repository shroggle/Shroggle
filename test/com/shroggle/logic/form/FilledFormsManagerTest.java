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
package com.shroggle.logic.form;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.presentation.site.ManageFormRecordSortType;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class FilledFormsManagerTest {

    @Test
    public void selectBySearchKey() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final User user = TestUtil.createUserAndUserOnSiteRight(site);
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "formName");
        final DraftForm form2 = TestUtil.createCustomForm(site.getSiteId(), "formName");
        final International international = ServiceLocator.getInternationStorage().get("formTable", Locale.US);

        /*First filled form creation*/
        final List<FilledFormItem> form1Items = new ArrayList<FilledFormItem>();

        FilledFormItem firstNameItem = new FilledFormItem();
        firstNameItem.setItemName(international.get(FormItemName.FIRST_NAME.toString() + "_FN"));
        firstNameItem.setFormItemName(FormItemName.FIRST_NAME);
        List<String> values = new ArrayList<String>();
        values.add("first name");
        firstNameItem.setValues(values);

        form1Items.add(firstNameItem);

        FilledFormItem addressItem = new FilledFormItem();
        addressItem.setItemName(international.get(FormItemName.ADDRESS.toString() + "_FN"));
        addressItem.setFormItemName(FormItemName.ADDRESS);
        values = new ArrayList<String>();
        values.add("address");
        addressItem.setValues(values);

        form1Items.add(addressItem);

        TestUtil.createFilledContactUsForm(user, form1Items, form);

        /*Second filled form creation*/
        final List<FilledFormItem> form2Items = new ArrayList<FilledFormItem>();

        FilledFormItem lastNameItem = new FilledFormItem();
        lastNameItem.setItemName(international.get(FormItemName.LAST_NAME.toString() + "_FN"));
        lastNameItem.setFormItemName(FormItemName.LAST_NAME);
        values = new ArrayList<String>();
        values.add("last name");
        lastNameItem.setValues(values);

        form2Items.add(lastNameItem);

        FilledFormItem addressItem2 = new FilledFormItem();
        addressItem2.setItemName(international.get(FormItemName.ADDRESS.toString() + "_FN"));
        addressItem2.setFormItemName(FormItemName.ADDRESS);
        values = new ArrayList<String>();
        values.add("address2");
        addressItem2.setValues(values);

        form2Items.add(addressItem2);

        TestUtil.createFilledContactUsForm(user, form2Items, form);
        TestUtil.createFilledContactUsForm(user, form2Items, form2);

        final List<FilledForm> filledForms = ServiceLocator.getPersistance().getFilledFormsByFormId(form.getFormId());
        List<FilledForm> response = new FilledFormsManager(filledForms).retainBySearchKey("value").getFilledForms();
        Assert.assertEquals(0, response.size());
        response = new FilledFormsManager(filledForms).retainBySearchKey("address").getFilledForms();
        Assert.assertEquals(2, response.size());
        response = new FilledFormsManager(filledForms).retainBySearchKey("address2").getFilledForms();
        Assert.assertEquals(1, response.size());
        response = new FilledFormsManager(filledForms).retainBySearchKey("first name").getFilledForms();
        Assert.assertEquals(2, response.size());
        response = new FilledFormsManager(filledForms).retainBySearchKey("name").getFilledForms();
        Assert.assertEquals(2, response.size());
    }

    @Test
    public void sortForms() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final User user = TestUtil.createUserAndUserOnSiteRight(site);
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "formName");
        TestUtil.createCustomForm(site.getSiteId(), "formName");
        final International international = ServiceLocator.getInternationStorage().get("formTable", Locale.US);

        /*First filled form creation*/
        final List<FilledFormItem> form1Items = new ArrayList<FilledFormItem>();

        FilledFormItem firstNameItem = new FilledFormItem();
        firstNameItem.setItemName(international.get(FormItemName.FIRST_NAME.toString() + "_FN"));
        firstNameItem.setFormItemName(FormItemName.FIRST_NAME);
        List<String> values = new ArrayList<String>();
        values.add("A. First Name");
        firstNameItem.setValues(values);

        form1Items.add(firstNameItem);

        FilledFormItem lastNameItem = new FilledFormItem();
        lastNameItem.setItemName(international.get(FormItemName.LAST_NAME.toString() + "_FN"));
        lastNameItem.setFormItemName(FormItemName.LAST_NAME);
        values = new ArrayList<String>();
        values.add("B. Last Name");
        lastNameItem.setValues(values);

        form1Items.add(lastNameItem);

        final FilledForm filledForm1 = TestUtil.createFilledContactUsForm(user, form1Items, form);
        Thread.sleep(300);

        /*Second filled form creation*/
        final List<FilledFormItem> form2Items = new ArrayList<FilledFormItem>();

        FilledFormItem firstNameItem2 = new FilledFormItem();
        firstNameItem2.setItemName(international.get(FormItemName.FIRST_NAME.toString() + "_FN"));
        firstNameItem2.setFormItemName(FormItemName.FIRST_NAME);
        values = new ArrayList<String>();
        values.add("B. First Name");
        firstNameItem2.setValues(values);

        form2Items.add(firstNameItem2);

        FilledFormItem lastNameItem2 = new FilledFormItem();
        lastNameItem2.setItemName(international.get(FormItemName.LAST_NAME.toString() + "_FN"));
        lastNameItem2.setFormItemName(FormItemName.LAST_NAME);
        values = new ArrayList<String>();
        values.add("A. Last Name");
        lastNameItem2.setValues(values);

        form2Items.add(lastNameItem2);

        final FilledForm filledForm2 = TestUtil.createFilledContactUsForm(user, form2Items, form);
        Thread.sleep(300);

        /*Third filled form creation*/
        final List<FilledFormItem> form3Items = new ArrayList<FilledFormItem>();

        FilledFormItem firstNameItem3 = new FilledFormItem();
        firstNameItem3.setItemName(international.get(FormItemName.FIRST_NAME.toString() + "_FN"));
        firstNameItem3.setFormItemName(FormItemName.FIRST_NAME);
        values = new ArrayList<String>();
        values.add("C. First Name");
        firstNameItem3.setValues(values);

        form3Items.add(firstNameItem3);

        FilledFormItem lastNameItem3 = new FilledFormItem();
        lastNameItem3.setItemName(international.get(FormItemName.LAST_NAME.toString() + "_FN"));
        lastNameItem3.setFormItemName(FormItemName.LAST_NAME);
        values = new ArrayList<String>();
        values.add("C. Last Name");
        lastNameItem3.setValues(values);

        form3Items.add(lastNameItem3);

        final FilledForm filledForm3 = TestUtil.createFilledContactUsForm(user, form3Items, form);

        final List<FilledForm> filledForms = Arrays.asList(filledForm1, filledForm2, filledForm3);
        final FilledFormsManager filledFormsManager = new FilledFormsManager(filledForms);

        List<FilledForm> response = filledFormsManager.sort(ManageFormRecordSortType.CUSTOM_FIELD, FormManager.getFormItemByPosition(form, 0).getItemName()).getFilledForms();
        Assert.assertEquals(3, response.size());
        Assert.assertEquals(filledForm1.getFilledFormId(), response.get(0).getFilledFormId());
        Assert.assertEquals(filledForm2.getFilledFormId(), response.get(1).getFilledFormId());
        Assert.assertEquals(filledForm3.getFilledFormId(), response.get(2).getFilledFormId());


        response = filledFormsManager.setDescendingSort(true).sort(ManageFormRecordSortType.CUSTOM_FIELD, FormManager.getFormItemByPosition(form, 0).getItemName()).getFilledForms();
        Assert.assertEquals(filledForm1.getFilledFormId(), response.get(2).getFilledFormId());
        Assert.assertEquals(filledForm2.getFilledFormId(), response.get(1).getFilledFormId());
        Assert.assertEquals(filledForm3.getFilledFormId(), response.get(0).getFilledFormId());
        filledFormsManager.setDescendingSort(false);

        response = filledFormsManager.sort(ManageFormRecordSortType.CUSTOM_FIELD, FormManager.getFormItemByPosition(form, 1).getItemName()).getFilledForms();
        Assert.assertEquals(filledForm2.getFilledFormId(), response.get(0).getFilledFormId());
        Assert.assertEquals(filledForm1.getFilledFormId(), response.get(1).getFilledFormId());
        Assert.assertEquals(filledForm3.getFilledFormId(), response.get(2).getFilledFormId());

        response = filledFormsManager.sort(ManageFormRecordSortType.FILL_DATE).getFilledForms();
        Assert.assertEquals(filledForm1.getFilledFormId(), response.get(0).getFilledFormId());
        Assert.assertEquals(filledForm2.getFilledFormId(), response.get(1).getFilledFormId());
        Assert.assertEquals(filledForm3.getFilledFormId(), response.get(2).getFilledFormId());

        //Sorting by update date before updating any form
        response = filledFormsManager.sort(ManageFormRecordSortType.UPDATE_DATE).getFilledForms();
        Assert.assertEquals(filledForm1.getFilledFormId(), response.get(0).getFilledFormId());
        Assert.assertEquals(filledForm2.getFilledFormId(), response.get(1).getFilledFormId());
        Assert.assertEquals(filledForm3.getFilledFormId(), response.get(2).getFilledFormId());
    }

    @Test
    public void sortForms_withIntValues() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final User user = TestUtil.createUserAndUserOnSiteRight(site);
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "formName");
        TestUtil.createCustomForm(site.getSiteId(), "formName");
        final International international = ServiceLocator.getInternationStorage().get("formTable", Locale.US);

        /*First filled form creation*/
        final List<FilledFormItem> form1Items = new ArrayList<FilledFormItem>();

        FilledFormItem firstNameItem = new FilledFormItem();
        firstNameItem.setItemName(international.get(FormItemName.FIRST_NAME.toString() + "_FN"));
        firstNameItem.setFormItemName(FormItemName.FIRST_NAME);
        List<String> values = new ArrayList<String>();
        values.add("1");
        firstNameItem.setValues(values);

        form1Items.add(firstNameItem);

        FilledFormItem lastNameItem = new FilledFormItem();
        lastNameItem.setItemName(international.get(FormItemName.LAST_NAME.toString() + "_FN"));
        lastNameItem.setFormItemName(FormItemName.LAST_NAME);
        values = new ArrayList<String>();
        values.add("222222");
        lastNameItem.setValues(values);

        form1Items.add(lastNameItem);

        final FilledForm filledForm1 = TestUtil.createFilledContactUsForm(user, form1Items, form);
        Thread.sleep(300);

        /*Second filled form creation*/
        final List<FilledFormItem> form2Items = new ArrayList<FilledFormItem>();

        FilledFormItem firstNameItem2 = new FilledFormItem();
        firstNameItem2.setItemName(international.get(FormItemName.FIRST_NAME.toString() + "_FN"));
        firstNameItem2.setFormItemName(FormItemName.FIRST_NAME);
        values = new ArrayList<String>();
        values.add("3");
        firstNameItem2.setValues(values);

        form2Items.add(firstNameItem2);

        FilledFormItem lastNameItem2 = new FilledFormItem();
        lastNameItem2.setItemName(international.get(FormItemName.LAST_NAME.toString() + "_FN"));
        lastNameItem2.setFormItemName(FormItemName.LAST_NAME);
        values = new ArrayList<String>();
        values.add("4");
        lastNameItem2.setValues(values);

        form2Items.add(lastNameItem2);

        final FilledForm filledForm2 = TestUtil.createFilledContactUsForm(user, form2Items, form);
        Thread.sleep(300);

        /*Third filled form creation*/
        final List<FilledFormItem> form3Items = new ArrayList<FilledFormItem>();

        FilledFormItem firstNameItem3 = new FilledFormItem();
        firstNameItem3.setItemName(international.get(FormItemName.FIRST_NAME.toString() + "_FN"));
        firstNameItem3.setFormItemName(FormItemName.FIRST_NAME);
        values = new ArrayList<String>();
        values.add("5");
        firstNameItem3.setValues(values);

        form3Items.add(firstNameItem3);

        FilledFormItem lastNameItem3 = new FilledFormItem();
        lastNameItem3.setItemName(international.get(FormItemName.LAST_NAME.toString() + "_FN"));
        lastNameItem3.setFormItemName(FormItemName.LAST_NAME);
        values = new ArrayList<String>();
        values.add("6");
        lastNameItem3.setValues(values);

        form3Items.add(lastNameItem3);

        final FilledForm filledForm3 = TestUtil.createFilledContactUsForm(user, form3Items, form);

        final List<FilledForm> filledForms = Arrays.asList(filledForm1, filledForm2, filledForm3);
        final FilledFormsManager filledFormsManager = new FilledFormsManager(filledForms);

        List<FilledForm> response = filledFormsManager.sort(ManageFormRecordSortType.CUSTOM_FIELD, FormManager.getFormItemByPosition(form, 0).getItemName()).getFilledForms();
        Assert.assertEquals(3, response.size());
        Assert.assertEquals(filledForm1.getFilledFormId(), response.get(0).getFilledFormId());
        Assert.assertEquals(filledForm2.getFilledFormId(), response.get(1).getFilledFormId());
        Assert.assertEquals(filledForm3.getFilledFormId(), response.get(2).getFilledFormId());


        response = filledFormsManager.setDescendingSort(true).sort(ManageFormRecordSortType.CUSTOM_FIELD, FormManager.getFormItemByPosition(form, 0).getItemName()).getFilledForms();
        Assert.assertEquals(filledForm1.getFilledFormId(), response.get(2).getFilledFormId());
        Assert.assertEquals(filledForm2.getFilledFormId(), response.get(1).getFilledFormId());
        Assert.assertEquals(filledForm3.getFilledFormId(), response.get(0).getFilledFormId());
        filledFormsManager.setDescendingSort(false);

        response = filledFormsManager.sort(ManageFormRecordSortType.CUSTOM_FIELD, FormManager.getFormItemByPosition(form, 1).getItemName()).getFilledForms();
        Assert.assertEquals(filledForm2.getFilledFormId(), response.get(0).getFilledFormId());
        Assert.assertEquals(filledForm3.getFilledFormId(), response.get(1).getFilledFormId());
        Assert.assertEquals(filledForm1.getFilledFormId(), response.get(2).getFilledFormId());
    }
}
