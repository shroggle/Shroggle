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
import com.shroggle.presentation.form.export.SaveDataExportAndScheduleRequest;
import com.shroggle.util.DateUtil;
import com.shroggle.util.ServiceLocator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class FormExportTaskManagerTest {

    @Test
    public void testSave() throws Exception {
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


        FormExportTaskManager.save(request);


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
    public void testDelete() throws Exception {
        final FormExportTask formExportTask = TestUtil.createFormExportTask(TestUtil.createCustomForm(TestUtil.createSite()).getFormId(), "name");

        new FormExportTaskManager(formExportTask).delete();

        Assert.assertNull(ServiceLocator.getPersistance().getFormExportTask(formExportTask.getId()));
    }

    @Test
    public void testGetLastSuccessfulExport() throws Exception {
        final Date currentDate = new Date();

        final Calendar calendar1 = Calendar.getInstance();
        final Calendar calendar2 = Calendar.getInstance();
        final Calendar calendar3 = Calendar.getInstance();

        calendar1.setTime(currentDate);
        calendar2.setTime(currentDate);
        calendar3.setTime(currentDate);

        calendar1.set(Calendar.MONTH, 1);
        calendar2.set(Calendar.MONTH, 2);
        calendar3.set(Calendar.MONTH, 3);

        final FormExportTask formExportTask = TestUtil.createFormExportTask(TestUtil.createCustomForm(TestUtil.createSite()).getFormId(), "name");
        formExportTask.setHistory(Arrays.asList(calendar1.getTime(), calendar2.getTime(), calendar3.getTime()));

        Assert.assertEquals(calendar3.getTime(), new FormExportTaskManager(formExportTask).getLastSuccessfulExportDate());
    }

    @Test
    public void testGetLastSuccessfulExport_withoutHistory() throws Exception {
        final FormExportTask formExportTask = TestUtil.createFormExportTask(TestUtil.createCustomForm(TestUtil.createSite()).getFormId(), "name");
        Assert.assertEquals(null, new FormExportTaskManager(formExportTask).getLastSuccessfulExportDate());
    }

    @Test
    public void testGetLastSuccessfulExportString() throws Exception {
        final Date currentDate = new Date();

        final Calendar calendar1 = Calendar.getInstance();
        final Calendar calendar2 = Calendar.getInstance();
        final Calendar calendar3 = Calendar.getInstance();

        calendar1.setTime(currentDate);
        calendar2.setTime(currentDate);
        calendar3.setTime(currentDate);

        calendar1.set(Calendar.MONTH, 1);
        calendar2.set(Calendar.MONTH, 2);
        calendar3.set(Calendar.MONTH, 3);

        final FormExportTask formExportTask = TestUtil.createFormExportTask(TestUtil.createCustomForm(TestUtil.createSite()).getFormId(), "name");
        formExportTask.setHistory(Arrays.asList(calendar1.getTime(), calendar2.getTime(), calendar3.getTime()));

        Assert.assertEquals(DateUtil.toMonthDayAndYear(calendar3.getTime()), new FormExportTaskManager(formExportTask).getLastSuccessfulExportDateString());
    }

    @Test
    public void testGetCreatedString() throws Exception {
        final FormExportTask formExportTask = TestUtil.createFormExportTask(TestUtil.createCustomForm(TestUtil.createSite()).getFormId(), "name");
        Assert.assertEquals(DateUtil.toMonthDayAndYear(formExportTask.getCreated()), new FormExportTaskManager(formExportTask).getCreatedString());
    }

    @Test
    public void testGetLastSuccessfulExportString_withoutHistory() throws Exception {
        final FormExportTask formExportTask = TestUtil.createFormExportTask(TestUtil.createCustomForm(TestUtil.createSite()).getFormId(), "name");
        Assert.assertEquals("", new FormExportTaskManager(formExportTask).getLastSuccessfulExportDateString());
    }

    @Test
    public void testGetId() throws Exception {
        final FormExportTask formExportTask = TestUtil.createFormExportTask(TestUtil.createCustomForm(TestUtil.createSite()).getFormId(), "name");
        Assert.assertEquals(formExportTask.getId(), new FormExportTaskManager(formExportTask).getId());
    }

    @Test
    public void testGetFormId() throws Exception {
        final FormExportTask formExportTask = TestUtil.createFormExportTask(TestUtil.createCustomForm(TestUtil.createSite()).getFormId(), "name");
        Assert.assertEquals(formExportTask.getFormId(), new FormExportTaskManager(formExportTask).getFormId());
    }

    @Test
    public void testGetCustomizeDataExport() throws Exception {
        final FormExportTask formExportTask = TestUtil.createFormExportTask(TestUtil.createCustomForm(TestUtil.createSite()).getFormId(), "name");
        Assert.assertEquals(formExportTask.getCsvDataExport().getId(), new FormExportTaskManager(formExportTask).getCsvDataExport().getId());
    }

    @Test
    public void testGetName() throws Exception {
        final FormExportTask formExportTask = TestUtil.createFormExportTask(TestUtil.createCustomForm(TestUtil.createSite()).getFormId(), "name");
        Assert.assertEquals(formExportTask.getName(), new FormExportTaskManager(formExportTask).getName());
    }

    @Test
    public void testGetFrequency() throws Exception {
        final FormExportTask formExportTask = TestUtil.createFormExportTask(TestUtil.createCustomForm(TestUtil.createSite()).getFormId(), "name");
        Assert.assertEquals(formExportTask.getFrequency(), new FormExportTaskManager(formExportTask).getFrequency());
    }

    @Test
    public void testGetCreated() throws Exception {
        final FormExportTask formExportTask = TestUtil.createFormExportTask(TestUtil.createCustomForm(TestUtil.createSite()).getFormId(), "name");
        Assert.assertEquals(formExportTask.getCreated(), new FormExportTaskManager(formExportTask).getCreated());
    }

    @Test
    public void testGetHistory() throws Exception {
        final Date currentDate = new Date();

        final Calendar calendar1 = Calendar.getInstance();
        final Calendar calendar2 = Calendar.getInstance();
        final Calendar calendar3 = Calendar.getInstance();

        calendar1.setTime(currentDate);
        calendar2.setTime(currentDate);
        calendar3.setTime(currentDate);

        calendar1.set(Calendar.MONTH, 1);
        calendar2.set(Calendar.MONTH, 2);
        calendar3.set(Calendar.MONTH, 3);

        final FormExportTask formExportTask = TestUtil.createFormExportTask(TestUtil.createCustomForm(TestUtil.createSite()).getFormId(), "name");
        formExportTask.setHistory(Arrays.asList(calendar1.getTime(), calendar2.getTime(), calendar3.getTime()));
        Assert.assertEquals(formExportTask.getHistory(), new FormExportTaskManager(formExportTask).getHistory());
    }

    @Test
    public void testGetHistory_withoutHistory() throws Exception {
        final FormExportTask formExportTask = TestUtil.createFormExportTask(TestUtil.createCustomForm(TestUtil.createSite()).getFormId(), "name");
        Assert.assertEquals(true, new FormExportTaskManager(formExportTask).getHistory().isEmpty());
    }

    @Test
    public void testGetFilterId() throws Exception {
        TestUtil.createUserAndLogin();
        final Form form = TestUtil.createContactUsForm();
        final FormExportTask formExportTask = TestUtil.createFormExportTask(form.getId(), "name");
        formExportTask.setFilterId(123);

        final FormExportTaskManager manager = new FormExportTaskManager(formExportTask);
        Assert.assertEquals(123, manager.getFilterId().intValue());
    }

    @Test
    public void testGetFilterName() throws Exception {
        TestUtil.createUserAndLogin();
        final DraftContactUs form = TestUtil.createContactUsForm();
        final FormExportTask formExportTask = TestUtil.createFormExportTask(form.getId(), "name");

        final Filter filter = TestUtil.createFormFilter(form);
        filter.setName("Filter name");
        formExportTask.setFilterId(filter.getFormFilterId());

        final FormExportTaskManager manager = new FormExportTaskManager(formExportTask);
        Assert.assertEquals("Filter name", manager.getFilterName());
    }

    @Test
    public void testGetFilterName_withoutFilter() throws Exception {
        TestUtil.createUserAndLogin();
        final DraftContactUs form = TestUtil.createContactUsForm();
        final FormExportTask formExportTask = TestUtil.createFormExportTask(form.getId(), "name");

        final Filter filter = TestUtil.createFormFilter(form);
        filter.setName("Filter name");
        formExportTask.setFilterId(111);

        final FormExportTaskManager manager = new FormExportTaskManager(formExportTask);
        Assert.assertEquals("", manager.getFilterName());
    }

    @Test
    public void testGetLastSendDate() throws Exception {
         TestUtil.createUserAndLogin();
        final DraftContactUs form = TestUtil.createContactUsForm();
        final FormExportTask formExportTask = TestUtil.createFormExportTask(form.getId(), "name");

        final Filter filter = TestUtil.createFormFilter(form);
        filter.setName("Filter name");
        formExportTask.setFilterId(111);

        final List<Date> history = new ArrayList<Date>();
        history.add(new Date(1000));
        history.add(new Date(10000));
        history.add(new Date(100000));
        history.add(new Date(1000000));
        history.add(new Date(10000000));
        formExportTask.setHistory(history);

        final FormExportTaskManager manager = new FormExportTaskManager(formExportTask);
        Assert.assertEquals(new Date(10000000), manager.getLastSuccessfulExportDate());
    }

    @Test
    public void testGetLastSendDate_withEmptyHistory() throws Exception {
         TestUtil.createUserAndLogin();
        final DraftContactUs form = TestUtil.createContactUsForm();
        final FormExportTask formExportTask = TestUtil.createFormExportTask(form.getId(), "name");

        final Filter filter = TestUtil.createFormFilter(form);
        filter.setName("Filter name");
        formExportTask.setFilterId(111);

        formExportTask.setHistory(new ArrayList<Date>());

        final FormExportTaskManager manager = new FormExportTaskManager(formExportTask);
        Assert.assertEquals(null, manager.getLastSuccessfulExportDate());
    }

    @Test
    public void testGetLastSendDate_withNullHistory() throws Exception {
         TestUtil.createUserAndLogin();
        final DraftContactUs form = TestUtil.createContactUsForm();
        final FormExportTask formExportTask = TestUtil.createFormExportTask(form.getId(), "name");

        final Filter filter = TestUtil.createFormFilter(form);
        filter.setName("Filter name");
        formExportTask.setFilterId(111);

        formExportTask.setHistory(null);

        final FormExportTaskManager manager = new FormExportTaskManager(formExportTask);
        Assert.assertEquals(null, manager.getLastSuccessfulExportDate());
    }
}
