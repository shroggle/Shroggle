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
package com.shroggle.logic.form.export;

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
public class CSVDataExportFieldManagerTest {

    @Test
    public void testConstructByFormItem() throws Exception {
        final FormItem formItem = TestUtil.createFormItem(FormItemName.NAME, 123);
        final CSVDataExportField field = CSVDataExportFieldManager.constructByFormItem(formItem);


        Assert.assertNotNull(field);
        Assert.assertEquals(formItem.getFormItemId(), field.getFormItemId());
        Assert.assertEquals(formItem.getItemName(), field.getCustomizeHeader());
        Assert.assertEquals(formItem.getPosition(), field.getPosition());
        Assert.assertEquals(null, field.getCustomizeDataExport());
        Assert.assertEquals(true, field.isInclude());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_createWithoutField() throws Exception {
        new CSVDataExportFieldManager(null);
    }

    @Test
    public void testRemoveField() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Form form = TestUtil.createContactUsForm();
        final CSVDataExport csvDataExport = TestUtil.createCSVDataExport();
        final CSVDataExportField field = TestUtil.createCustomizeDataExportField(0);
        csvDataExport.addField(field);

        final CSVDataExportFieldManager manager = new CSVDataExportFieldManager(field);
        manager.removeField();

        Assert.assertEquals(null, ServiceLocator.getPersistance().getCSVDataExportField(field.getId()));
        Assert.assertEquals(0, csvDataExport.getFields().size());
    }

    @Test
    public void testGetId() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Form form = TestUtil.createContactUsForm();
        final CSVDataExport csvDataExport = TestUtil.createCSVDataExport();
        final CSVDataExportField field = TestUtil.createCustomizeDataExportField(0);
        csvDataExport.addField(field);

        final CSVDataExportFieldManager manager = new CSVDataExportFieldManager(field);
        Assert.assertEquals(field.getId(), manager.getId());
    }

    @Test
    public void testIsFirst() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Form form = TestUtil.createContactUsForm();
        final CSVDataExport csvDataExport = TestUtil.createCSVDataExport();
        final CSVDataExportField field = TestUtil.createCustomizeDataExportField(0);
        csvDataExport.addField(field);

        final CSVDataExportField field2 = TestUtil.createCustomizeDataExportField(1);
        csvDataExport.addField(field2);

        final CSVDataExportField field3 = TestUtil.createCustomizeDataExportField(2);
        csvDataExport.addField(field3);

        Assert.assertEquals(true, new CSVDataExportFieldManager(field).isFirst());
        Assert.assertEquals(false, new CSVDataExportFieldManager(field2).isFirst());
        Assert.assertEquals(false, new CSVDataExportFieldManager(field3).isFirst());
    }

    @Test
    public void testIsLast() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Form form = TestUtil.createContactUsForm();
        final CSVDataExport csvDataExport = TestUtil.createCSVDataExport();
        final CSVDataExportField field = TestUtil.createCustomizeDataExportField(0);
        csvDataExport.addField(field);

        final CSVDataExportField field2 = TestUtil.createCustomizeDataExportField(1);
        csvDataExport.addField(field2);

        final CSVDataExportField field3 = TestUtil.createCustomizeDataExportField(2);
        csvDataExport.addField(field3);

        Assert.assertEquals(false, new CSVDataExportFieldManager(field).isLast());
        Assert.assertEquals(false, new CSVDataExportFieldManager(field2).isLast());
        Assert.assertEquals(true, new CSVDataExportFieldManager(field3).isLast());
    }

    @Test
    public void testGetPosition() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Form form = TestUtil.createContactUsForm();
        final CSVDataExport csvDataExport = TestUtil.createCSVDataExport();
        final CSVDataExportField field = TestUtil.createCustomizeDataExportField(0);
        csvDataExport.addField(field);

        final CSVDataExportFieldManager manager = new CSVDataExportFieldManager(field);
        Assert.assertEquals(field.getPosition(), manager.getPosition());
    }

    @Test
    public void testGetFormItemId() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Form form = TestUtil.createContactUsForm();
        final CSVDataExport csvDataExport = TestUtil.createCSVDataExport();
        final CSVDataExportField field = TestUtil.createCustomizeDataExportField(0);
        csvDataExport.addField(field);

        final CSVDataExportFieldManager manager = new CSVDataExportFieldManager(field);
        Assert.assertEquals(field.getFormItemId(), manager.getFormItemId());
    }

    @Test
    public void testGetCustomizeHeader() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Form form = TestUtil.createContactUsForm();
        final CSVDataExport csvDataExport = TestUtil.createCSVDataExport();
        final CSVDataExportField field = TestUtil.createCustomizeDataExportField(0);
        csvDataExport.addField(field);

        final CSVDataExportFieldManager manager = new CSVDataExportFieldManager(field);
        Assert.assertEquals(field.getCustomizeHeader(), manager.getCustomizeHeader());
    }

    @Test
    public void testGetFormItemName() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Form form = TestUtil.createContactUsForm();
        final FormItem formItem = TestUtil.createFormItem(FormItemName.NAME, 1);
        formItem.setItemName("aaaaaaaa");
        final CSVDataExport csvDataExport = TestUtil.createCSVDataExport();
        final CSVDataExportField field = TestUtil.createCustomizeDataExportField(0, formItem.getFormItemId());
        csvDataExport.addField(field);

        final CSVDataExportFieldManager manager = new CSVDataExportFieldManager(field);
        Assert.assertEquals(formItem.getItemName(), manager.getFormItemName());
    }

    @Test
    public void testGetFormItemName_withoutItem() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Form form = TestUtil.createContactUsForm();
        final CSVDataExport csvDataExport = TestUtil.createCSVDataExport();
        final CSVDataExportField field = TestUtil.createCustomizeDataExportField(0, -1);
        csvDataExport.addField(field);

        final CSVDataExportFieldManager manager = new CSVDataExportFieldManager(field);
        Assert.assertEquals("", manager.getFormItemName());
    }

    @Test
    public void testIsInclude() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Form form = TestUtil.createContactUsForm();
        final CSVDataExport csvDataExport = TestUtil.createCSVDataExport();
        final CSVDataExportField field = TestUtil.createCustomizeDataExportField(0);
        csvDataExport.addField(field);

        final CSVDataExportFieldManager manager = new CSVDataExportFieldManager(field);
        Assert.assertEquals(field.isInclude(), manager.isInclude());
    }
}
