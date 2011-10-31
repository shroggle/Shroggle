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
package com.shroggle.presentation.form.customization;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.FormNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
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
public class ShowCustomizeManageRecordsWindowServiceTest {

    final ShowCustomizeManageRecordsWindowService service = new ShowCustomizeManageRecordsWindowService();

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test(expected = UserNotLoginedException.class)
    public void testExecute_withoutLoginedUser() throws Exception {
        service.execute(-1);
    }

    @Test(expected = FormNotFoundException.class)
    public void testExecute_withoutForm() throws Exception {
        TestUtil.createUserAndLogin();
        service.execute(-1);
    }

    @Test
    public void testExecute_withCustomizeManageRecords() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final DraftContactUs form = TestUtil.createContactUsForm();
        form.setFormItems(new ArrayList<FormItem>());
        final FormItem formItem1 = TestUtil.createFormItem(FormItemName.NAME, form, 0);
        final FormItem formItem2 = TestUtil.createFormItem(FormItemName.FIRST_NAME, form, 1);

        formItem1.setItemName("formItem1");
        formItem2.setItemName("formItem2");


        final CustomizeManageRecords CustomizeManageRecords = TestUtil.createCustomizeManageRecords(form.getId(), user.getUserId());
        CustomizeManageRecords.addField(TestUtil.createCustomizeManageRecordsField(0, formItem1.getFormItemId()));
        CustomizeManageRecords.addField(TestUtil.createCustomizeManageRecordsField(1, formItem2.getFormItemId()));
        CustomizeManageRecords.addField(TestUtil.createCustomizeManageRecordsField(2, 158));


        service.execute(form.getFormId());

        Assert.assertNotNull(service.getCustomizeManageRecordsManager());
        Assert.assertEquals(2, service.getCustomizeManageRecordsManager().getFields().size());
        Assert.assertEquals(form.getFormId(), service.getCustomizeManageRecordsManager().getFormId());
        Assert.assertEquals(form.getDescription(), service.getCustomizeManageRecordsManager().getFormDescription());
        Assert.assertEquals(form.getFilters(), service.getCustomizeManageRecordsManager().getFormFilters());
        Assert.assertEquals(form.getName(), service.getCustomizeManageRecordsManager().getFormName());
        Assert.assertEquals(user.getUserId(), service.getCustomizeManageRecordsManager().getUserId());
    }

    @Test
    public void testExecute_withoutCustomizeManageRecords() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final DraftContactUs form = TestUtil.createContactUsForm();
        form.setFormItems(new ArrayList<FormItem>());
        final FormItem formItem1 = TestUtil.createFormItem(FormItemName.NAME, form, 0);
        final FormItem formItem2 = TestUtil.createFormItem(FormItemName.FIRST_NAME, form, 1);
        final FormItem formItem3 = TestUtil.createFormItem(FormItemName.LAST_NAME, form, 2);

        formItem1.setItemName("formItem1");
        formItem2.setItemName("formItem2");
        formItem3.setItemName("formItem3");


        service.execute(form.getFormId());

        Assert.assertNotNull(service.getCustomizeManageRecordsManager());
        Assert.assertEquals(3, service.getCustomizeManageRecordsManager().getFields().size());
        Assert.assertEquals(true, service.getCustomizeManageRecordsManager().getFields().get(0).isInclude());
        Assert.assertEquals(true, service.getCustomizeManageRecordsManager().getFields().get(1).isInclude());
        Assert.assertEquals(false, service.getCustomizeManageRecordsManager().getFields().get(2).isInclude());
        Assert.assertEquals(form.getFormId(), service.getCustomizeManageRecordsManager().getFormId());
        Assert.assertEquals(form.getDescription(), service.getCustomizeManageRecordsManager().getFormDescription());
        Assert.assertEquals(form.getFilters(), service.getCustomizeManageRecordsManager().getFormFilters());
        Assert.assertEquals(form.getName(), service.getCustomizeManageRecordsManager().getFormName());
        Assert.assertEquals(user.getUserId(), service.getCustomizeManageRecordsManager().getUserId());
    }
}
