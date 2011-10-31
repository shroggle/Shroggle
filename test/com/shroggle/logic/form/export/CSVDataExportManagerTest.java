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
import com.shroggle.exception.FormNotFoundException;
import com.shroggle.util.ServiceLocator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class CSVDataExportManagerTest {

    @Before
    public void before(){
        ServiceLocator.getConfigStorage().get().setCsvPathSeparator(";");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_createWithoutCustomizeDataExport() throws Exception {
        new CSVDataExportManager(null, null);
    }

    @Test(expected = FormNotFoundException.class)
    public void test_createWithoutForm() throws Exception {
        final CSVDataExport csvDataExport = TestUtil.createCSVDataExport();
        new CSVDataExportManager(csvDataExport, null);
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


        final CSVDataExport csvDataExport = TestUtil.createCSVDataExport();
        csvDataExport.addField(TestUtil.createCustomizeDataExportField(0, formItem1.getFormItemId()));
        csvDataExport.addField(TestUtil.createCustomizeDataExportField(1, formItem2.getFormItemId()));
        csvDataExport.addField(TestUtil.createCustomizeDataExportField(2, formItem4.getFormItemId()));


        final CSVDataExportManager manager = new CSVDataExportManager(csvDataExport, form);
        manager.normalizeFields();


        Assert.assertEquals(4, manager.getFields().size());
        Assert.assertEquals(0, manager.getFields().get(0).getPosition());
        Assert.assertEquals(1, manager.getFields().get(1).getPosition());
        Assert.assertEquals(2, manager.getFields().get(2).getPosition());
        Assert.assertEquals(3, manager.getFields().get(3).getPosition());

        Assert.assertEquals(formItem1.getItemName(), manager.getFields().get(0).getFormItemName());
        Assert.assertEquals(formItem2.getItemName(), manager.getFields().get(1).getFormItemName());
        Assert.assertEquals(formItem4.getItemName(), manager.getFields().get(2).getFormItemName());
        Assert.assertEquals(formItem3.getItemName(), manager.getFields().get(3).getFormItemName());// This item added after normalization.
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


        final CSVDataExport csvDataExport = TestUtil.createCSVDataExport();
        csvDataExport.addField(TestUtil.createCustomizeDataExportField(0, formItem1.getFormItemId()));
        csvDataExport.addField(TestUtil.createCustomizeDataExportField(1, formItem2.getFormItemId()));
        csvDataExport.addField(TestUtil.createCustomizeDataExportField(2, 158));


        final CSVDataExportManager manager = new CSVDataExportManager(csvDataExport, form);
        manager.normalizeFields();


        Assert.assertEquals(2, manager.getFields().size());// Odd item removed after normalization.
        Assert.assertEquals(0, manager.getFields().get(0).getPosition());
        Assert.assertEquals(1, manager.getFields().get(1).getPosition());

        Assert.assertEquals(formItem1.getItemName(), manager.getFields().get(0).getFormItemName());
        Assert.assertEquals(formItem2.getItemName(), manager.getFields().get(1).getFormItemName());
    }

    @Test
    public void testGetFields() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Form form = TestUtil.createContactUsForm();
        final CSVDataExport csvDataExport = TestUtil.createCSVDataExport();

        csvDataExport.addField(TestUtil.createCustomizeDataExportField(1));
        csvDataExport.addField(TestUtil.createCustomizeDataExportField(2));
        csvDataExport.addField(TestUtil.createCustomizeDataExportField(0));

        final CSVDataExportManager manager = new CSVDataExportManager(csvDataExport, form);
        Assert.assertEquals(3, manager.getFields().size());
        Assert.assertEquals(0, manager.getFields().get(0).getPosition());
        Assert.assertEquals(1, manager.getFields().get(1).getPosition());
        Assert.assertEquals(2, manager.getFields().get(2).getPosition());
    }

    @Test
    public void testCreateCSV() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final DraftContactUs form = TestUtil.createContactUsForm();
        form.setFormItems(new ArrayList<FormItem>());
        final FormItem formItem1 = TestUtil.createFormItem(FormItemName.NAME, form, 0);
        final FormItem formItem2 = TestUtil.createFormItem(FormItemName.FIRST_NAME, form, 1);

        formItem1.setItemName("formItem1");
        formItem2.setItemName("formItem2");

        final CSVDataExport csvDataExport = TestUtil.createCSVDataExport();
        csvDataExport.addField(TestUtil.createCustomizeDataExportField(0, formItem1.getFormItemId()));
        csvDataExport.addField(TestUtil.createCustomizeDataExportField(1, formItem2.getFormItemId()));
        csvDataExport.getFields().get(0).setInclude(true);
        csvDataExport.getFields().get(1).setInclude(true);
        csvDataExport.getFields().get(0).setCustomizeHeader("Field1 name");
        csvDataExport.getFields().get(1).setCustomizeHeader("Field2 name");

        final FilledForm filledForm = TestUtil.createFilledForm(form.getFormId());
        final FilledFormItem item1 = TestUtil.createFilledFormItem(formItem1.getFormItemId(), FormItemName.NAME, "value1");
        final FilledFormItem item2 = TestUtil.createFilledFormItem(formItem2.getFormItemId(), FormItemName.NAME, "value2");
        filledForm.addFilledFormItem(item1);
        filledForm.addFilledFormItem(item2);


        final FilledForm filledForm2 = TestUtil.createFilledForm(form.getFormId());
        final FilledFormItem item11 = TestUtil.createFilledFormItem(formItem1.getFormItemId(), FormItemName.NAME, "value11");
        final FilledFormItem item22 = TestUtil.createFilledFormItem(formItem2.getFormItemId(), FormItemName.NAME, "value22");
        filledForm2.addFilledFormItem(item11);
        filledForm2.addFilledFormItem(item22);

        final CSVDataExportManager manager = new CSVDataExportManager(csvDataExport, form);
        Assert.assertEquals("Field1 name" + manager.getPathSeparator() + "Field2 name\n" + "value1" + manager.getPathSeparator() + "value2\nvalue11" + manager.getPathSeparator() + "value22\n", manager.createCSV(Arrays.asList(filledForm, filledForm2)));
    }

    @Test
    public void testCreateCSV_withNotAllFieldsIncluded() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final DraftContactUs form = TestUtil.createContactUsForm();
        form.setFormItems(new ArrayList<FormItem>());
        final FormItem formItem1 = TestUtil.createFormItem(FormItemName.NAME, form, 0);
        final FormItem formItem2 = TestUtil.createFormItem(FormItemName.FIRST_NAME, form, 1);

        formItem1.setItemName("formItem1");
        formItem2.setItemName("formItem2");

        final CSVDataExport csvDataExport = TestUtil.createCSVDataExport();
        csvDataExport.addField(TestUtil.createCustomizeDataExportField(0, formItem1.getFormItemId()));
        csvDataExport.addField(TestUtil.createCustomizeDataExportField(1, formItem2.getFormItemId()));
        csvDataExport.getFields().get(0).setInclude(false);
        csvDataExport.getFields().get(1).setInclude(true);
        csvDataExport.getFields().get(0).setCustomizeHeader("Field1 name");
        csvDataExport.getFields().get(1).setCustomizeHeader("Field2 name");

        final FilledForm filledForm = TestUtil.createFilledForm(form.getFormId());
        final FilledFormItem item1 = TestUtil.createFilledFormItem(formItem1.getFormItemId(), FormItemName.NAME, "value1");
        final FilledFormItem item2 = TestUtil.createFilledFormItem(formItem2.getFormItemId(), FormItemName.NAME, "value2");
        filledForm.addFilledFormItem(item1);
        filledForm.addFilledFormItem(item2);


        final FilledForm filledForm2 = TestUtil.createFilledForm(form.getFormId());
        final FilledFormItem item11 = TestUtil.createFilledFormItem(formItem1.getFormItemId(), FormItemName.NAME, "value11");
        final FilledFormItem item22 = TestUtil.createFilledFormItem(formItem2.getFormItemId(), FormItemName.NAME, "value22");
        filledForm2.addFilledFormItem(item11);
        filledForm2.addFilledFormItem(item22);

        final CSVDataExportManager manager = new CSVDataExportManager(csvDataExport, form);
        Assert.assertEquals("Field2 name\nvalue2\nvalue22\n", manager.createCSV(Arrays.asList(filledForm, filledForm2)));
    }

    @Test
    public void testCreateCSV_empty() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final DraftContactUs form = TestUtil.createContactUsForm();
        form.setFormItems(new ArrayList<FormItem>());
        final FormItem formItem1 = TestUtil.createFormItem(FormItemName.NAME, form, 0);
        final FormItem formItem2 = TestUtil.createFormItem(FormItemName.FIRST_NAME, form, 1);

        formItem1.setItemName("formItem1");
        formItem2.setItemName("formItem2");

        final CSVDataExport csvDataExport = TestUtil.createCSVDataExport();
        csvDataExport.addField(TestUtil.createCustomizeDataExportField(0, formItem1.getFormItemId()));
        csvDataExport.addField(TestUtil.createCustomizeDataExportField(1, formItem2.getFormItemId()));
        csvDataExport.getFields().get(0).setInclude(false);
        csvDataExport.getFields().get(1).setInclude(false);

        final FilledForm filledForm = TestUtil.createFilledForm(form.getFormId());
        final FilledFormItem item1 = TestUtil.createFilledFormItem(formItem1.getFormItemId(), FormItemName.NAME, "value1");
        final FilledFormItem item2 = TestUtil.createFilledFormItem(formItem2.getFormItemId(), FormItemName.NAME, "value2");
        filledForm.addFilledFormItem(item1);
        filledForm.addFilledFormItem(item2);


        final FilledForm filledForm2 = TestUtil.createFilledForm(form.getFormId());
        final FilledFormItem item11 = TestUtil.createFilledFormItem(formItem1.getFormItemId(), FormItemName.NAME, "value11");
        final FilledFormItem item22 = TestUtil.createFilledFormItem(formItem2.getFormItemId(), FormItemName.NAME, "value22");
        filledForm2.addFilledFormItem(item11);
        filledForm2.addFilledFormItem(item22);

        final CSVDataExportManager manager = new CSVDataExportManager(csvDataExport, form);
        Assert.assertEquals("", manager.createCSV(null));
    }
}
