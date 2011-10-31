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
package com.shroggle.logic.form.customization;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.FormNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class CustomizeManageRecordsManagerTest {

    @Test(expected = IllegalArgumentException.class)
    public void test_createWithoutCustomizeManageRecords() throws Exception {
        new CustomizeManageRecordsManager(null);
    }

    @Test(expected = FormNotFoundException.class)
    public void test_createWithoutForm() throws Exception {
        final CustomizeManageRecords customizeManageRecords = TestUtil.createCustomizeManageRecords(-1, -1);
        new CustomizeManageRecordsManager(customizeManageRecords);
    }

    @Test
    public void testNormalizeFields_withOneNewItem() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final DraftContactUs form = TestUtil.createContactUsForm();
        form.setFormItems(new ArrayList<FormItem>());
        final FormItem formItem1 = TestUtil.createFormItem(FormItemName.NAME, form, 0);
        final FormItem formItem2 = TestUtil.createFormItem(FormItemName.FIRST_NAME, form, 1);
        final FormItem formItem3 = TestUtil.createFormItem(FormItemName.LAST_NAME, form, 2);
        final FormItem formItem4 = TestUtil.createFormItem(FormItemName.ACADEMIC_DEGREE, form, 3);

        formItem1.setItemName("formItem1");
        formItem2.setItemName("formItem2");
        formItem3.setItemName("formItem3");
        formItem4.setItemName("formItem4");


        final CustomizeManageRecords customizeManageRecords = TestUtil.createCustomizeManageRecords(form.getId(), user.getUserId());
        customizeManageRecords.addField(TestUtil.createCustomizeManageRecordsField(0, formItem1.getFormItemId()));
        customizeManageRecords.addField(TestUtil.createCustomizeManageRecordsField(1, formItem2.getFormItemId()));
        customizeManageRecords.addField(TestUtil.createCustomizeManageRecordsField(2, formItem4.getFormItemId()));


        final CustomizeManageRecordsManager manager = new CustomizeManageRecordsManager(customizeManageRecords);
        manager.normalizeFields();


        Assert.assertEquals(4, manager.getFields().size());
        Assert.assertEquals(0, manager.getFields().get(0).getPosition());
        Assert.assertEquals(1, manager.getFields().get(1).getPosition());
        Assert.assertEquals(2, manager.getFields().get(2).getPosition());
        Assert.assertEquals(3, manager.getFields().get(3).getPosition());

        Assert.assertEquals(formItem1.getItemName(), manager.getFields().get(0).getItemName());
        Assert.assertEquals(formItem2.getItemName(), manager.getFields().get(1).getItemName());
        Assert.assertEquals(formItem4.getItemName(), manager.getFields().get(2).getItemName());
        Assert.assertEquals(formItem3.getItemName(), manager.getFields().get(3).getItemName());// This item added after normalization.
    }

    @Test
    public void testNormalizeFields_withOneOddItem() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final DraftContactUs form = TestUtil.createContactUsForm();
        form.setFormItems(new ArrayList<FormItem>());
        final FormItem formItem1 = TestUtil.createFormItem(FormItemName.NAME, form, 0);
        final FormItem formItem2 = TestUtil.createFormItem(FormItemName.FIRST_NAME, form, 1);

        formItem1.setItemName("formItem1");
        formItem2.setItemName("formItem2");


        final CustomizeManageRecords customizeManageRecords = TestUtil.createCustomizeManageRecords(form.getId(), user.getUserId());
        customizeManageRecords.addField(TestUtil.createCustomizeManageRecordsField(0, formItem1.getFormItemId()));
        customizeManageRecords.addField(TestUtil.createCustomizeManageRecordsField(1, formItem2.getFormItemId()));
        customizeManageRecords.addField(TestUtil.createCustomizeManageRecordsField(2, 158));


        final CustomizeManageRecordsManager manager = new CustomizeManageRecordsManager(customizeManageRecords);
        manager.normalizeFields();


        Assert.assertEquals(2, manager.getFields().size());// Odd item removed after normalization.
        Assert.assertEquals(0, manager.getFields().get(0).getPosition());
        Assert.assertEquals(1, manager.getFields().get(1).getPosition());

        Assert.assertEquals(formItem1.getItemName(), manager.getFields().get(0).getItemName());
        Assert.assertEquals(formItem2.getItemName(), manager.getFields().get(1).getItemName());
    }

    @Test
    public void testGetFields() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Form form = TestUtil.createContactUsForm();
        final CustomizeManageRecords customizeManageRecords = TestUtil.createCustomizeManageRecords(form.getId(), user.getUserId());

        customizeManageRecords.addField(TestUtil.createCustomizeManageRecordsField(1));
        customizeManageRecords.addField(TestUtil.createCustomizeManageRecordsField(2));
        customizeManageRecords.addField(TestUtil.createCustomizeManageRecordsField(0));

        final CustomizeManageRecordsManager manager = new CustomizeManageRecordsManager(customizeManageRecords);
        Assert.assertEquals(3, manager.getFields().size());
        Assert.assertEquals(0, manager.getFields().get(0).getPosition());
        Assert.assertEquals(1, manager.getFields().get(1).getPosition());
        Assert.assertEquals(2, manager.getFields().get(2).getPosition());
    }

    @Test
    public void testGetIncludedFields() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Form form = TestUtil.createContactUsForm();
        final CustomizeManageRecords customizeManageRecords = TestUtil.createCustomizeManageRecords(form.getId(), user.getUserId());

        CustomizeManageRecordsField field1 = TestUtil.createCustomizeManageRecordsField(1);
        CustomizeManageRecordsField field2 = TestUtil.createCustomizeManageRecordsField(2);
        CustomizeManageRecordsField field3 = TestUtil.createCustomizeManageRecordsField(0);
        customizeManageRecords.addField(field1);
        customizeManageRecords.addField(field2);
        customizeManageRecords.addField(field3);
        field1.setInclude(true);
        field2.setInclude(false);
        field3.setInclude(true);

        final CustomizeManageRecordsManager manager = new CustomizeManageRecordsManager(customizeManageRecords);
        Assert.assertEquals(2, manager.getIncludedFields().size());
        Assert.assertEquals(0, manager.getIncludedFields().get(0).getPosition());
        Assert.assertEquals(1, manager.getIncludedFields().get(1).getPosition());
    }

    @Test
    public void testGetUserId() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Form form = TestUtil.createContactUsForm();
        final CustomizeManageRecords customizeManageRecords = TestUtil.createCustomizeManageRecords(form.getId(), user.getUserId());

        final CustomizeManageRecordsManager manager = new CustomizeManageRecordsManager(customizeManageRecords);
        Assert.assertEquals(user.getUserId(), manager.getUserId());
    }

    @Test
    public void testGetFormId() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Form form = TestUtil.createContactUsForm();
        final CustomizeManageRecords customizeManageRecords = TestUtil.createCustomizeManageRecords(form.getId(), user.getUserId());

        final CustomizeManageRecordsManager manager = new CustomizeManageRecordsManager(customizeManageRecords);
        Assert.assertEquals(form.getId(), manager.getFormId());
    }

    @Test
    public void testGetFormFilters() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Form form = TestUtil.createContactUsForm();
        final CustomizeManageRecords customizeManageRecords = TestUtil.createCustomizeManageRecords(form.getId(), user.getUserId());

        final CustomizeManageRecordsManager manager = new CustomizeManageRecordsManager(customizeManageRecords);
        Assert.assertEquals(form.getFilters(), manager.getFormFilters());
    }

    @Test
    public void testGetFormName() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Form form = TestUtil.createContactUsForm();
        final CustomizeManageRecords customizeManageRecords = TestUtil.createCustomizeManageRecords(form.getId(), user.getUserId());

        final CustomizeManageRecordsManager manager = new CustomizeManageRecordsManager(customizeManageRecords);
        Assert.assertEquals(form.getName(), manager.getFormName());
    }

    @Test
    public void testGetFormDescription() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Form form = TestUtil.createContactUsForm();
        final CustomizeManageRecords customizeManageRecords = TestUtil.createCustomizeManageRecords(form.getId(), user.getUserId());

        final CustomizeManageRecordsManager manager = new CustomizeManageRecordsManager(customizeManageRecords);
        Assert.assertEquals(form.getDescription(), manager.getFormDescription());
    }
}
