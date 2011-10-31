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
package com.shroggle.presentation.form.export;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.FormNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.util.ServiceLocator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class SaveDataExportAndScheduleServiceTest {

    final SaveDataExportAndScheduleService service = new SaveDataExportAndScheduleService();

    @Test(expected = UserNotLoginedException.class)
    public void testExecute_withoutLoginedUser() throws Exception {
        service.execute(null);
    }

    @Test(expected = FormNotFoundException.class)
    public void testExecute_withoutForm() throws Exception {
        TestUtil.createUserAndLogin();
        final SaveDataExportAndScheduleRequest request = new SaveDataExportAndScheduleRequest();
        service.execute(request);
    }

    @Test
    public void testExecute_forNew() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final DraftContactUs form = TestUtil.createContactUsForm();
        form.setFormItems(new ArrayList<FormItem>());
        final FormItem formItem1 = TestUtil.createFormItem(FormItemName.NAME, form, 0);
        final FormItem formItem2 = TestUtil.createFormItem(FormItemName.FIRST_NAME, form, 1);

        formItem1.setItemName("formItem1");
        formItem2.setItemName("formItem2");

        final SaveDataExportAndScheduleRequest request = new SaveDataExportAndScheduleRequest();
        request.setFilterId(123);
        request.setFormId(form.getFormId());
        request.setName("name");
        request.setDataFormat(FormExportDataFormat.CSV);
        request.setFrequency(FormExportFrequency.DAILY);
        request.setGoogleBaseDataExport(new GoogleBaseDataExport());
        request.setStartDate("01/23/2010");
        request.setFields(new ArrayList<CSVDataExportField>() {{
            final CSVDataExportField field1 = new CSVDataExportField();
            field1.setCustomizeDataExport(null);
            field1.setCustomizeHeader("header1");
            field1.setFormItemId(11);
            field1.setInclude(true);
            field1.setPosition(0);
            add(field1);


            final CSVDataExportField field2 = new CSVDataExportField();
            field2.setCustomizeDataExport(null);
            field2.setCustomizeHeader("header2");
            field2.setFormItemId(21);
            field2.setInclude(false);
            field2.setPosition(1);
            add(field2);
        }});


        service.execute(request);


        final FormExportTask formExportTask = ServiceLocator.getPersistance().getFormExportTasksByFormId(form.getFormId()).get(0);
        Assert.assertNotNull(formExportTask);
        Assert.assertNotNull(formExportTask.getCsvDataExport());
        Assert.assertEquals(123, formExportTask.getFilterId().intValue());
        Assert.assertEquals(form.getFormId(), formExportTask.getFormId());
        Assert.assertEquals("name", formExportTask.getName());
        Assert.assertEquals(FormExportFrequency.DAILY, formExportTask.getFrequency());
        Assert.assertEquals(FormExportDataFormat.CSV, formExportTask.getDataFormat());
        Assert.assertEquals(123, formExportTask.getFilterId().intValue());

        Assert.assertEquals(2, formExportTask.getCsvDataExport().getFields().size());
        Assert.assertEquals(0, formExportTask.getCsvDataExport().getFields().get(0).getPosition());
        Assert.assertEquals(1, formExportTask.getCsvDataExport().getFields().get(1).getPosition());
        Assert.assertEquals("header1", formExportTask.getCsvDataExport().getFields().get(0).getCustomizeHeader());
        Assert.assertEquals("header2", formExportTask.getCsvDataExport().getFields().get(1).getCustomizeHeader());
        Assert.assertEquals(11, formExportTask.getCsvDataExport().getFields().get(0).getFormItemId());
        Assert.assertEquals(21, formExportTask.getCsvDataExport().getFields().get(1).getFormItemId());
        Assert.assertEquals(true, formExportTask.getCsvDataExport().getFields().get(0).isInclude());
        Assert.assertEquals(false, formExportTask.getCsvDataExport().getFields().get(1).isInclude());
        Assert.assertEquals(formExportTask.getCsvDataExport(), formExportTask.getCsvDataExport().getFields().get(0).getCustomizeDataExport());
        Assert.assertEquals(formExportTask.getCsvDataExport(), formExportTask.getCsvDataExport().getFields().get(1).getCustomizeDataExport());
    }

    @Test
    public void testExecute_forExisting() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final DraftContactUs form = TestUtil.createContactUsForm();
        form.setFormItems(new ArrayList<FormItem>());
        final FormItem formItem1 = TestUtil.createFormItem(FormItemName.NAME, form, 0);
        final FormItem formItem2 = TestUtil.createFormItem(FormItemName.FIRST_NAME, form, 1);
        final FormItem formItem3 = TestUtil.createFormItem(FormItemName.FIRST_NAME, form, 2);

        formItem1.setItemName("formItem1");
        formItem2.setItemName("formItem2");
        formItem3.setItemName("formItem3");


        final CSVDataExport CSVDataExportOld = TestUtil.createCSVDataExport();
        CSVDataExportOld.addField(TestUtil.createCustomizeDataExportField(0, formItem1.getFormItemId()));
        CSVDataExportOld.addField(TestUtil.createCustomizeDataExportField(1, formItem2.getFormItemId()));
        CSVDataExportOld.addField(TestUtil.createCustomizeDataExportField(2, formItem3.getFormItemId()));


        final SaveDataExportAndScheduleRequest request = new SaveDataExportAndScheduleRequest();
        request.setFilterId(123);
        request.setFormId(form.getFormId());
        request.setStartDate("01/23/2010");
        request.setFields(new ArrayList<CSVDataExportField>() {{
            final CSVDataExportField field1 = new CSVDataExportField();
            field1.setCustomizeDataExport(null);
            field1.setCustomizeHeader("header1");
            field1.setFormItemId(11);
            field1.setInclude(true);
            field1.setPosition(0);
            add(field1);


            final CSVDataExportField field2 = new CSVDataExportField();
            field2.setCustomizeDataExport(null);
            field2.setCustomizeHeader("header2");
            field2.setFormItemId(21);
            field2.setInclude(false);
            field2.setPosition(1);
            add(field2);
        }});


        service.execute(request);


        final FormExportTask formExportTask = ServiceLocator.getPersistance().getFormExportTasksByFormId(form.getFormId()).get(0);
        Assert.assertNotNull(formExportTask);
        Assert.assertEquals(123, formExportTask.getFilterId().intValue());
        Assert.assertEquals(form.getFormId(), formExportTask.getFormId());
        Assert.assertEquals(2, formExportTask.getCsvDataExport().getFields().size());
        Assert.assertEquals(0, formExportTask.getCsvDataExport().getFields().get(0).getPosition());
        Assert.assertEquals(1, formExportTask.getCsvDataExport().getFields().get(1).getPosition());
        Assert.assertEquals("header1", formExportTask.getCsvDataExport().getFields().get(0).getCustomizeHeader());
        Assert.assertEquals("header2", formExportTask.getCsvDataExport().getFields().get(1).getCustomizeHeader());
        Assert.assertEquals(11, formExportTask.getCsvDataExport().getFields().get(0).getFormItemId());
        Assert.assertEquals(21, formExportTask.getCsvDataExport().getFields().get(1).getFormItemId());
        Assert.assertEquals(true, formExportTask.getCsvDataExport().getFields().get(0).isInclude());
        Assert.assertEquals(false, formExportTask.getCsvDataExport().getFields().get(1).isInclude());
        Assert.assertEquals(formExportTask.getCsvDataExport(), formExportTask.getCsvDataExport().getFields().get(0).getCustomizeDataExport());
        Assert.assertEquals(formExportTask.getCsvDataExport(), formExportTask.getCsvDataExport().getFields().get(1).getCustomizeDataExport());
    }
}
