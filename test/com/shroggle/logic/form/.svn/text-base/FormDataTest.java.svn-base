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
package com.shroggle.logic.form;

import com.shroggle.util.TimeInterval;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import com.shroggle.entity.*;
import com.shroggle.TestUtil;
import com.shroggle.TestRunnerWithMockServices;

import java.util.Arrays;
import java.util.Date;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class FormDataTest {

    @Test
    public void testCreateByForm_Registration() {
        DraftForm form = TestUtil.createRegistrationForm();
        FormData formData = new FormData(form);
        Assert.assertNotNull(formData);
        Assert.assertEquals(form.getDescription(), formData.getDescription());
        Assert.assertEquals(form.getFormId(), formData.getFormId());
        Assert.assertEquals(form.getName(), formData.getFormName());
        Assert.assertEquals(form.isShowDescription(), formData.isShowDescription());
        Assert.assertEquals(form.getFormItems(), formData.getFormItems());

        /*--------------next fields only for child site registration form because of this they are empty--------------*/
        Assert.assertEquals(0.0, formData.getOneTimeFee(), 0);
        Assert.assertEquals(false, formData.isUseOneTimeFee());
        Assert.assertEquals(0.0, formData.getPrice250mb(), 0);
        Assert.assertEquals(null, formData.getStartDate());
        Assert.assertEquals(null, formData.getEndDate());
    }

    @Test
    public void testCreateByFilledForm_Registration() {
        DraftForm form = TestUtil.createRegistrationForm();
        form.setFormItems(null);
        FilledForm filledForm = TestUtil.createFilledForm(form);
        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.NAME);
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        FormData formData = new FormData(filledForm);

        Assert.assertNotNull(formData);
        Assert.assertEquals(filledForm.getFormDescription(), formData.getDescription());
        Assert.assertEquals(form.getFormId(), formData.getFormId());
        Assert.assertEquals(form.getName(), formData.getFormName());
        Assert.assertEquals(form.isShowDescription(), formData.isShowDescription());
        Assert.assertEquals(filledForm.getFilledFormItems().size(), formData.getFormItems().size());
        Assert.assertEquals(filledForm.getFilledFormItems().get(0).getFormItemName(), formData.getFormItems().get(0).getFormItemName());

        /*--------------next fields only for child site registration form because of this they are empty--------------*/
        Assert.assertEquals(0.0, formData.getOneTimeFee(), 0);
        Assert.assertEquals(false, formData.isUseOneTimeFee());
        Assert.assertEquals(0.0, formData.getPrice250mb(), 0);
        Assert.assertEquals(null, formData.getStartDate());
        Assert.assertEquals(null, formData.getEndDate());
    }

    @Test
    public void testCreateByForm_ChildSiteRegistration() {
        DraftChildSiteRegistration form = TestUtil.createChildSiteRegistration(new Site());
        form.setOneTimeFee(100.0);
        form.setUseOneTimeFee(true);
        form.setPrice250mb(205);
        form.setStartDate(new Date());
        form.setEndDate(new Date(System.currentTimeMillis() + TimeInterval.ONE_DAY.getMillis()));
        FormData formData = new FormData(form);
        Assert.assertNotNull(formData);
        Assert.assertEquals(form.getDescription(), formData.getDescription());
        Assert.assertEquals(form.getFormId(), formData.getFormId());
        Assert.assertEquals(form.getName(), formData.getFormName());
        Assert.assertEquals(form.isShowDescription(), formData.isShowDescription());
        Assert.assertEquals(form.getFormItems(), formData.getFormItems());

        Assert.assertEquals(form.getOneTimeFee(), formData.getOneTimeFee(), 0);
        Assert.assertEquals(form.isUseOneTimeFee(), formData.isUseOneTimeFee());
        Assert.assertEquals(form.getPrice250mb(), formData.getPrice250mb(), 0);
        Assert.assertEquals(form.getStartDate(), formData.getStartDate());
        Assert.assertEquals(form.getEndDate(), formData.getEndDate());
        Assert.assertEquals(false, formData.isRegistrationCanceled());
    }

    @Test
    public void testCreateByForm_ChildSiteRegistration_RegistrationCanceled() {
        DraftChildSiteRegistration form = TestUtil.createChildSiteRegistration(new Site());
        form.setOneTimeFee(100.0);
        form.setUseOneTimeFee(true);
        form.setPrice250mb(205);
        form.setStartDate(new Date());
        form.setEndDate(new Date(System.currentTimeMillis() - TimeInterval.ONE_DAY.getMillis()));
        FormData formData = new FormData(form);
        Assert.assertNotNull(formData);
        Assert.assertEquals(form.getDescription(), formData.getDescription());
        Assert.assertEquals(form.getFormId(), formData.getFormId());
        Assert.assertEquals(form.getName(), formData.getFormName());
        Assert.assertEquals(form.isShowDescription(), formData.isShowDescription());
        Assert.assertEquals(form.getFormItems(), formData.getFormItems());

        Assert.assertEquals(form.getOneTimeFee(), formData.getOneTimeFee(), 0);
        Assert.assertEquals(form.isUseOneTimeFee(), formData.isUseOneTimeFee());
        Assert.assertEquals(form.getPrice250mb(), formData.getPrice250mb(), 0);
        Assert.assertEquals(form.getStartDate(), formData.getStartDate());
        Assert.assertEquals(form.getEndDate(), formData.getEndDate());
        Assert.assertEquals(true, formData.isRegistrationCanceled());
    }
}
