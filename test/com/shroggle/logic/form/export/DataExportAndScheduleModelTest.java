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
import com.shroggle.entity.Form;
import com.shroggle.entity.FormExportTask;
import com.shroggle.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class DataExportAndScheduleModelTest {


    @Test
    public void testGetFormFilters() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Form form = TestUtil.createContactUsForm();
        final FormExportTask formExportTask = TestUtil.createFormExportTask(form.getId(), "name");

        final DataExportAndScheduleModel manager = new DataExportAndScheduleModel(form.getId());
        Assert.assertEquals(form.getFilters(), manager.getFormFilters());
    }

    @Test
    public void testGetFormName() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Form form = TestUtil.createContactUsForm();
        final FormExportTask formExportTask = TestUtil.createFormExportTask(form.getId(), "name");

        final DataExportAndScheduleModel manager = new DataExportAndScheduleModel(form.getId());
        Assert.assertEquals(form.getName(), manager.getFormName());
    }

    @Test
    public void testGetFormDescription() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Form form = TestUtil.createContactUsForm();
        final FormExportTask formExportTask = TestUtil.createFormExportTask(form.getId(), "name");

        final DataExportAndScheduleModel manager = new DataExportAndScheduleModel(form.getId());
        Assert.assertEquals(form.getDescription(), manager.getFormDescription());
    }
}
