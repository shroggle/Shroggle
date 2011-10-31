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
import com.shroggle.util.ServiceLocator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class CustomizeManageRecordsFieldManagerTest {

    @Test
    public void testConstructByFormItem() throws Exception {
        final FormItem formItem = TestUtil.createFormItem(FormItemName.NAME, 123);
        final CustomizeManageRecordsField field = CustomizeManageRecordsFieldManager.constructByFormItem(formItem);


        Assert.assertNotNull(field);
        Assert.assertEquals(formItem.getFormItemId(), field.getFormItemId());
        Assert.assertEquals(formItem.getPosition(), field.getPosition());
        Assert.assertEquals(null, field.getCustomizeManageRecords());    
        Assert.assertEquals(false, field.isInclude());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_createWithoutField() throws Exception {
        new CustomizeManageRecordsFieldManager(null);
    }

    @Test
    public void testRemoveField() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Form form = TestUtil.createContactUsForm();
        final CustomizeManageRecords CustomizeManageRecords = TestUtil.createCustomizeManageRecords(form.getId(), user.getUserId());
        final CustomizeManageRecordsField field = TestUtil.createCustomizeManageRecordsField(0);
        CustomizeManageRecords.addField(field);

        final CustomizeManageRecordsFieldManager manager = new CustomizeManageRecordsFieldManager(field);
        manager.removeField();

        Assert.assertEquals(null, ServiceLocator.getPersistance().getCustomizeManageRecordsField(field.getId()));
        Assert.assertEquals(0, CustomizeManageRecords.getFields().size());
    }

    @Test
    public void testGetId() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Form form = TestUtil.createContactUsForm();
        final CustomizeManageRecords CustomizeManageRecords = TestUtil.createCustomizeManageRecords(form.getId(), user.getUserId());
        final CustomizeManageRecordsField field = TestUtil.createCustomizeManageRecordsField(0);
        CustomizeManageRecords.addField(field);

        final CustomizeManageRecordsFieldManager manager = new CustomizeManageRecordsFieldManager(field);
        Assert.assertEquals(field.getId(), manager.getId());
    }

    @Test
    public void testIsFirst() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Form form = TestUtil.createContactUsForm();
        final CustomizeManageRecords CustomizeManageRecords = TestUtil.createCustomizeManageRecords(form.getId(), user.getUserId());
        final CustomizeManageRecordsField field = TestUtil.createCustomizeManageRecordsField(0);
        CustomizeManageRecords.addField(field);

        final CustomizeManageRecordsField field2 = TestUtil.createCustomizeManageRecordsField(1);
        CustomizeManageRecords.addField(field2);

        final CustomizeManageRecordsField field3 = TestUtil.createCustomizeManageRecordsField(2);
        CustomizeManageRecords.addField(field3);

        Assert.assertEquals(true, new CustomizeManageRecordsFieldManager(field).isFirst());
        Assert.assertEquals(false, new CustomizeManageRecordsFieldManager(field2).isFirst());
        Assert.assertEquals(false, new CustomizeManageRecordsFieldManager(field3).isFirst());
    }

    @Test
    public void testIsLast() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Form form = TestUtil.createContactUsForm();
        final CustomizeManageRecords CustomizeManageRecords = TestUtil.createCustomizeManageRecords(form.getId(), user.getUserId());
        final CustomizeManageRecordsField field = TestUtil.createCustomizeManageRecordsField(0);
        CustomizeManageRecords.addField(field);

        final CustomizeManageRecordsField field2 = TestUtil.createCustomizeManageRecordsField(1);
        CustomizeManageRecords.addField(field2);

        final CustomizeManageRecordsField field3 = TestUtil.createCustomizeManageRecordsField(2);
        CustomizeManageRecords.addField(field3);

        Assert.assertEquals(false, new CustomizeManageRecordsFieldManager(field).isLast());
        Assert.assertEquals(false, new CustomizeManageRecordsFieldManager(field2).isLast());
        Assert.assertEquals(true, new CustomizeManageRecordsFieldManager(field3).isLast());
    }

    @Test
    public void testGetPosition() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Form form = TestUtil.createContactUsForm();
        final CustomizeManageRecords CustomizeManageRecords = TestUtil.createCustomizeManageRecords(form.getId(), user.getUserId());
        final CustomizeManageRecordsField field = TestUtil.createCustomizeManageRecordsField(0);
        CustomizeManageRecords.addField(field);

        final CustomizeManageRecordsFieldManager manager = new CustomizeManageRecordsFieldManager(field);
        Assert.assertEquals(field.getPosition(), manager.getPosition());
    }

    @Test
    public void testGetFormItemId() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Form form = TestUtil.createContactUsForm();
        final CustomizeManageRecords CustomizeManageRecords = TestUtil.createCustomizeManageRecords(form.getId(), user.getUserId());
        final CustomizeManageRecordsField field = TestUtil.createCustomizeManageRecordsField(0);
        CustomizeManageRecords.addField(field);

        final CustomizeManageRecordsFieldManager manager = new CustomizeManageRecordsFieldManager(field);
        Assert.assertEquals(field.getFormItemId(), manager.getFormItemId());
    }

    @Test
    public void testGetFormItemName() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Form form = TestUtil.createContactUsForm();
        final FormItem formItem = TestUtil.createFormItem(FormItemName.NAME, 1);
        formItem.setItemName("aaaaaaaa");
        final CustomizeManageRecords CustomizeManageRecords = TestUtil.createCustomizeManageRecords(form.getId(), user.getUserId());
        final CustomizeManageRecordsField field = TestUtil.createCustomizeManageRecordsField(0, formItem.getFormItemId());
        CustomizeManageRecords.addField(field);

        final CustomizeManageRecordsFieldManager manager = new CustomizeManageRecordsFieldManager(field);
        Assert.assertEquals(formItem.getItemName(), manager.getItemName());
    }

    @Test
    public void testGetFormItemName_withoutItem() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Form form = TestUtil.createContactUsForm();
        final CustomizeManageRecords CustomizeManageRecords = TestUtil.createCustomizeManageRecords(form.getId(), user.getUserId());
        final CustomizeManageRecordsField field = TestUtil.createCustomizeManageRecordsField(0, -1);
        CustomizeManageRecords.addField(field);

        final CustomizeManageRecordsFieldManager manager = new CustomizeManageRecordsFieldManager(field);
        Assert.assertEquals("", manager.getItemName());
    }

    @Test
    public void testIsInclude() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Form form = TestUtil.createContactUsForm();
        final CustomizeManageRecords CustomizeManageRecords = TestUtil.createCustomizeManageRecords(form.getId(), user.getUserId());
        final CustomizeManageRecordsField field = TestUtil.createCustomizeManageRecordsField(0);
        CustomizeManageRecords.addField(field);

        final CustomizeManageRecordsFieldManager manager = new CustomizeManageRecordsFieldManager(field);
        Assert.assertEquals(field.isInclude(), manager.isInclude());
    }
}
