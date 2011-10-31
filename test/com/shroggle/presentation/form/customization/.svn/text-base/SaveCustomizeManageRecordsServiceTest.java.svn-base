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
import com.shroggle.util.ServiceLocator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class SaveCustomizeManageRecordsServiceTest {

    final SaveCustomizeManageRecordsService service = new SaveCustomizeManageRecordsService();

    @Test(expected = UserNotLoginedException.class)
    public void testExecute_withoutLoginedUser() throws Exception {
        service.execute(null);
    }

    @Test(expected = FormNotFoundException.class)
    public void testExecute_withoutForm() throws Exception {
        TestUtil.createUserAndLogin();
        final SaveCustomizeManageRecordsRequest request = new SaveCustomizeManageRecordsRequest();
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

        final SaveCustomizeManageRecordsRequest request = new SaveCustomizeManageRecordsRequest();
        request.setFormId(form.getFormId());
        request.setFields(new ArrayList<CustomizeManageRecordsField>() {{
            final CustomizeManageRecordsField field1 = new CustomizeManageRecordsField();
            field1.setCustomizeManageRecords(null);
            field1.setFormItemId(formItem1.getFormItemId());
            field1.setInclude(true);
            field1.setPosition(0);
            add(field1);


            final CustomizeManageRecordsField field2 = new CustomizeManageRecordsField();
            field2.setCustomizeManageRecords(null);
            field2.setFormItemId(formItem2.getFormItemId());
            field2.setInclude(false);
            field2.setPosition(1);
            add(field2);
        }});
        Assert.assertNull(ServiceLocator.getPersistance().getCustomizeManageRecords(form.getFormId(), user.getUserId()));


        service.execute(request);


        final CustomizeManageRecords customizeManageRecords = ServiceLocator.getPersistance().getCustomizeManageRecords(form.getFormId(), user.getUserId());
        Assert.assertNotNull(customizeManageRecords);
        Assert.assertEquals(form.getFormId(), customizeManageRecords.getFormId());
        Assert.assertEquals(user.getUserId(), customizeManageRecords.getUserId());
        Assert.assertEquals(2, customizeManageRecords.getFields().size());
        Assert.assertEquals(0, customizeManageRecords.getFields().get(0).getPosition());
        Assert.assertEquals(1, customizeManageRecords.getFields().get(1).getPosition());
        Assert.assertEquals(formItem1.getFormItemId(), customizeManageRecords.getFields().get(0).getFormItemId());
        Assert.assertEquals(formItem2.getFormItemId(), customizeManageRecords.getFields().get(1).getFormItemId());
        Assert.assertEquals(true, customizeManageRecords.getFields().get(0).isInclude());
        Assert.assertEquals(false, customizeManageRecords.getFields().get(1).isInclude());
        Assert.assertEquals(customizeManageRecords, customizeManageRecords.getFields().get(0).getCustomizeManageRecords());
        Assert.assertEquals(customizeManageRecords, customizeManageRecords.getFields().get(1).getCustomizeManageRecords());
    }

    @Test
    public void testExecute_forNew_withMoreThanMaxFieldsQuantity() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final DraftContactUs form = TestUtil.createContactUsForm();
        form.setFormItems(new ArrayList<FormItem>());
        final FormItem formItem1 = TestUtil.createFormItem(FormItemName.NAME, form, 0);
        final FormItem formItem2 = TestUtil.createFormItem(FormItemName.FIRST_NAME, form, 1);

        formItem1.setItemName("formItem1");
        formItem2.setItemName("formItem2");

        final SaveCustomizeManageRecordsRequest request = new SaveCustomizeManageRecordsRequest();
        request.setFormId(form.getFormId());
        request.setFields(new ArrayList<CustomizeManageRecordsField>() {{
            final CustomizeManageRecordsField field1 = new CustomizeManageRecordsField();
            field1.setCustomizeManageRecords(null);
            field1.setFormItemId(formItem1.getFormItemId());
            field1.setInclude(true);
            field1.setPosition(0);
            add(field1);


            final CustomizeManageRecordsField field2 = new CustomizeManageRecordsField();
            field2.setCustomizeManageRecords(null);
            field2.setFormItemId(formItem2.getFormItemId());
            field2.setInclude(true);
            field2.setPosition(1);
            add(field2);


            final CustomizeManageRecordsField field3 = new CustomizeManageRecordsField();
            field3.setCustomizeManageRecords(null);
            field3.setFormItemId(formItem1.getFormItemId());
            field3.setInclude(true);
            field3.setPosition(2);
            add(field3);


            final CustomizeManageRecordsField field4 = new CustomizeManageRecordsField();
            field4.setCustomizeManageRecords(null);
            field4.setFormItemId(formItem1.getFormItemId());
            field4.setInclude(true);
            field4.setPosition(3);
            add(field4);


            final CustomizeManageRecordsField field5 = new CustomizeManageRecordsField();
            field5.setCustomizeManageRecords(null);
            field5.setFormItemId(formItem1.getFormItemId());
            field5.setInclude(true);
            field5.setPosition(4);
            add(field5);


            final CustomizeManageRecordsField field6 = new CustomizeManageRecordsField();
            field6.setCustomizeManageRecords(null);
            field6.setFormItemId(formItem1.getFormItemId());
            field6.setInclude(true);
            field6.setPosition(5);
            add(field6);
        }});
        Assert.assertNull(ServiceLocator.getPersistance().getCustomizeManageRecords(form.getFormId(), user.getUserId()));


        service.execute(request);


        final CustomizeManageRecords customizeManageRecords = ServiceLocator.getPersistance().getCustomizeManageRecords(form.getFormId(), user.getUserId());
        Assert.assertNotNull(customizeManageRecords);
        Assert.assertEquals(form.getFormId(), customizeManageRecords.getFormId());
        Assert.assertEquals(user.getUserId(), customizeManageRecords.getUserId());
        Assert.assertEquals(6, customizeManageRecords.getFields().size());
        Assert.assertEquals(0, customizeManageRecords.getFields().get(0).getPosition());
        Assert.assertEquals(1, customizeManageRecords.getFields().get(1).getPosition());
        Assert.assertEquals(formItem1.getFormItemId(), customizeManageRecords.getFields().get(0).getFormItemId());
        Assert.assertEquals(formItem2.getFormItemId(), customizeManageRecords.getFields().get(1).getFormItemId());
        Assert.assertEquals(true, customizeManageRecords.getFields().get(0).isInclude());
        Assert.assertEquals(true, customizeManageRecords.getFields().get(1).isInclude());
        Assert.assertEquals(true, customizeManageRecords.getFields().get(2).isInclude());
        Assert.assertEquals(true, customizeManageRecords.getFields().get(3).isInclude());
        Assert.assertEquals(true, customizeManageRecords.getFields().get(4).isInclude());
        Assert.assertEquals(false, customizeManageRecords.getFields().get(5).isInclude());
        Assert.assertEquals(customizeManageRecords, customizeManageRecords.getFields().get(0).getCustomizeManageRecords());
        Assert.assertEquals(customizeManageRecords, customizeManageRecords.getFields().get(1).getCustomizeManageRecords());
    }

    @Test
    public void testExecute_forNew_withSpecialFields() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final DraftContactUs form = TestUtil.createContactUsForm();
        form.setFormItems(new ArrayList<FormItem>());
        final FormItem formItem1 = TestUtil.createFormItem(FormItemName.NAME, form, 0);
        final FormItem formItem2 = TestUtil.createFormItem(FormItemName.PAGE_BREAK, form, 1);

        formItem1.setItemName("formItem1");
        formItem2.setItemName("formItem2");

        final SaveCustomizeManageRecordsRequest request = new SaveCustomizeManageRecordsRequest();
        request.setFormId(form.getFormId());
        request.setFields(new ArrayList<CustomizeManageRecordsField>() {{
            final CustomizeManageRecordsField field1 = new CustomizeManageRecordsField();
            field1.setCustomizeManageRecords(null);
            field1.setFormItemId(formItem1.getFormItemId());
            field1.setInclude(true);
            field1.setPosition(0);
            add(field1);


            final CustomizeManageRecordsField field2 = new CustomizeManageRecordsField();
            field2.setCustomizeManageRecords(null);
            field2.setFormItemId(formItem2.getFormItemId());
            field2.setInclude(true);
            field2.setPosition(1);
            add(field2);
        }});
        Assert.assertNull(ServiceLocator.getPersistance().getCustomizeManageRecords(form.getFormId(), user.getUserId()));


        service.execute(request);


        final CustomizeManageRecords customizeManageRecords = ServiceLocator.getPersistance().getCustomizeManageRecords(form.getFormId(), user.getUserId());
        Assert.assertNotNull(customizeManageRecords);
        Assert.assertEquals(form.getFormId(), customizeManageRecords.getFormId());
        Assert.assertEquals(user.getUserId(), customizeManageRecords.getUserId());
        Assert.assertEquals(2, customizeManageRecords.getFields().size());
        Assert.assertEquals(0, customizeManageRecords.getFields().get(0).getPosition());
        Assert.assertEquals(1, customizeManageRecords.getFields().get(1).getPosition());
        Assert.assertEquals(formItem1.getFormItemId(), customizeManageRecords.getFields().get(0).getFormItemId());
        Assert.assertEquals(formItem2.getFormItemId(), customizeManageRecords.getFields().get(1).getFormItemId());
        Assert.assertEquals(true, customizeManageRecords.getFields().get(0).isInclude());
        Assert.assertEquals(false, customizeManageRecords.getFields().get(1).isInclude());
        Assert.assertEquals(customizeManageRecords, customizeManageRecords.getFields().get(0).getCustomizeManageRecords());
        Assert.assertEquals(customizeManageRecords, customizeManageRecords.getFields().get(1).getCustomizeManageRecords());
    }

    @Test
    public void testExecute_forExisting() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final DraftContactUs form = TestUtil.createContactUsForm();
        form.setFormItems(new ArrayList<FormItem>());
        final FormItem formItem1 = TestUtil.createFormItem(FormItemName.NAME, form, 0);
        final FormItem formItem2 = TestUtil.createFormItem(FormItemName.FIRST_NAME, form, 1);

        formItem1.setItemName("formItem1");
        formItem2.setItemName("formItem2");


        final CustomizeManageRecords customizeManageRecordsOld = TestUtil.createCustomizeManageRecords(form.getId(), user.getUserId());
        customizeManageRecordsOld.addField(TestUtil.createCustomizeManageRecordsField(0, formItem1.getFormItemId()));
        customizeManageRecordsOld.addField(TestUtil.createCustomizeManageRecordsField(1, formItem2.getFormItemId()));
        customizeManageRecordsOld.addField(TestUtil.createCustomizeManageRecordsField(2, -1));


        final SaveCustomizeManageRecordsRequest request = new SaveCustomizeManageRecordsRequest();
        request.setFormId(form.getFormId());
        request.setFields(new ArrayList<CustomizeManageRecordsField>() {{
            final CustomizeManageRecordsField field1 = new CustomizeManageRecordsField();
            field1.setCustomizeManageRecords(null);
            field1.setFormItemId(formItem1.getFormItemId());
            field1.setInclude(true);
            field1.setPosition(0);
            add(field1);


            final CustomizeManageRecordsField field2 = new CustomizeManageRecordsField();
            field2.setCustomizeManageRecords(null);
            field2.setFormItemId(formItem2.getFormItemId());
            field2.setInclude(false);
            field2.setPosition(1);
            add(field2);
        }});
        Assert.assertNotNull(ServiceLocator.getPersistance().getCustomizeManageRecords(form.getFormId(), user.getUserId()));


        service.execute(request);


        final CustomizeManageRecords customizeManageRecords = ServiceLocator.getPersistance().getCustomizeManageRecords(form.getFormId(), user.getUserId());
        Assert.assertNotNull(customizeManageRecords);
        Assert.assertEquals(form.getFormId(), customizeManageRecords.getFormId());
        Assert.assertEquals(user.getUserId(), customizeManageRecords.getUserId());
        Assert.assertEquals(2, customizeManageRecords.getFields().size());
        Assert.assertEquals(0, customizeManageRecords.getFields().get(0).getPosition());
        Assert.assertEquals(1, customizeManageRecords.getFields().get(1).getPosition());
        Assert.assertEquals(formItem1.getFormItemId(), customizeManageRecords.getFields().get(0).getFormItemId());
        Assert.assertEquals(formItem2.getFormItemId(), customizeManageRecords.getFields().get(1).getFormItemId());
        Assert.assertEquals(true, customizeManageRecords.getFields().get(0).isInclude());
        Assert.assertEquals(false, customizeManageRecords.getFields().get(1).isInclude());
        Assert.assertEquals(customizeManageRecords, customizeManageRecords.getFields().get(0).getCustomizeManageRecords());
        Assert.assertEquals(customizeManageRecords, customizeManageRecords.getFields().get(1).getCustomizeManageRecords());
    }
}
