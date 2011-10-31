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
import com.shroggle.logic.form.export.DataExportAndScheduleModel;
import com.shroggle.presentation.MockWebContext;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class ShowCustomizeDataExportWindowServiceTest {

    final ShowDataExportAndScheduleService service = new ShowDataExportAndScheduleService();

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test(expected = UserNotLoginedException.class)
    public void testExecute_withoutLoginedUser() throws Exception {
        service.show(-1, null);
    }

    @Test(expected = FormNotFoundException.class)
    public void testExecute_withoutForm() throws Exception {
        TestUtil.createUserAndLogin();
        service.show(-1, null);
    }

    @Test
    public void testExecute_withCustomizeDataExport() throws Exception {
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


        service.show(form.getFormId(), null);
        DataExportAndScheduleModel model = (DataExportAndScheduleModel)service.getRequest().getAttribute("dataExportAndScheduleModel");
        
        Assert.assertEquals(2, model.getCSVFields().size());
        Assert.assertEquals(form.getFormId(), model.getFormId());
        Assert.assertEquals(form.getDescription(), model.getFormDescription());
        Assert.assertEquals(form.getFilters(), model.getFormFilters());
        Assert.assertEquals(form.getName(), model.getFormName());
    }

    @Test
    public void testExecute_withoutCustomizeDataExport() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final DraftContactUs form = TestUtil.createContactUsForm();
        form.setFormItems(new ArrayList<FormItem>());
        final FormItem formItem1 = TestUtil.createFormItem(FormItemName.NAME, form, 0);
        final FormItem formItem2 = TestUtil.createFormItem(FormItemName.FIRST_NAME, form, 1);

        formItem1.setItemName("formItem1");
        formItem2.setItemName("formItem2");


        service.show(form.getFormId(), null);
        DataExportAndScheduleModel model = (DataExportAndScheduleModel)service.getRequest().getAttribute("dataExportAndScheduleModel");

        Assert.assertEquals(2, model.getCSVFields().size());
        Assert.assertEquals(form.getFormId(), model.getFormId());
        Assert.assertEquals(form.getDescription(), model.getFormDescription());
        Assert.assertEquals(form.getFilters(), model.getFormFilters());
        Assert.assertEquals(form.getName(), model.getFormName());
    }
}
