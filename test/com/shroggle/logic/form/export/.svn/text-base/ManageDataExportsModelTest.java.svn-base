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
import com.shroggle.entity.DraftForm;
import com.shroggle.entity.FormExportTask;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class ManageDataExportsModelTest {

    @Test(expected = UserNotLoginedException.class)
    public void createWithoutLoginedUser() {
        new ManageDataExportsModel(1);
    }

    @Test
    public void testGetFormExportTasks() throws Exception {
        TestUtil.createUserAndLogin();

        final FormExportTask formExportTask1 = TestUtil.createFormExportTask(1, "name1");
        final FormExportTask formExportTask2 = TestUtil.createFormExportTask(2, "name2");
        final FormExportTask formExportTask3 = TestUtil.createFormExportTask(1, "name3");

        final ManageDataExportsModel dataExportsModel = new ManageDataExportsModel(1);

        Assert.assertEquals(2, dataExportsModel.getFormExportTasks().size());
    }

    @Test
    public void testGetFormId() throws Exception {
        TestUtil.createUserAndLogin();
        final ManageDataExportsModel dataExportsModel = new ManageDataExportsModel(1);
        Assert.assertEquals(1, dataExportsModel.getFormId());
    }

    @Test
    public void testGetFormName_withoutForm() throws Exception {
        TestUtil.createUserAndLogin();
        final ManageDataExportsModel dataExportsModel = new ManageDataExportsModel(1);
        Assert.assertEquals("", dataExportsModel.getFormName());
    }

    @Test
    public void testGetFormName() throws Exception {
        TestUtil.createUserAndLogin();

        final DraftForm draftForm = TestUtil.createChildSiteRegistration(TestUtil.createSite());
        draftForm.setName("form name");
        final ManageDataExportsModel dataExportsModel = new ManageDataExportsModel(draftForm.getFormId());
        Assert.assertEquals("form name", dataExportsModel.getFormName());
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
}
