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
package com.shroggle.logic.form.filter;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.util.StringUtil;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class FormFilterManagerTest {

    @Test
    public void getFilledFormsEmptyWithoutRule() {
        final DraftFormFilter formFilter = new DraftFormFilter();
        final FormFilterManager formFilterManager = new FormFilterManager(formFilter);
        final DraftForm form = new DraftContactUs();
        formFilter.setForm(form);
        Assert.assertTrue(formFilterManager.getFilledForms().isEmpty());
    }

    @Test
    public void getFilledFormsEmpty() {
        final DraftFormFilter formFilter = new DraftFormFilter();
        final DraftFormFilterRule formFilterRule = new DraftFormFilterRule();
        formFilter.addRule(formFilterRule);
        final FormFilterManager formFilterManager = new FormFilterManager(formFilter);
        final DraftForm form = new DraftContactUs();
        formFilter.setForm(form);
        Assert.assertTrue(formFilterManager.getFilledForms().isEmpty());
    }

    @Test
    public void getFilledFormsForCheckboxRule() {
        final Site site = TestUtil.createSite();
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "f");

        final DraftFormFilterRule rule = new DraftFormFilterRule();
        StringUtil.addMany(rule.getCriteria(), "f");
        rule.setFormItemId(form.getFormItems().get(0).getFormItemId());

        final FilledForm filledForm = TestUtil.createFilledForm(form);
        filledForm.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());
        filledForm.getFilledFormItems().get(0).getValues().clear();
        filledForm.getFilledFormItems().get(0).getValues().add("f");

        final DraftFormFilter formFilter = new DraftFormFilter();
        final DraftFormFilterRule formFilterRule = new DraftFormFilterRule();
        formFilter.addRule(formFilterRule);

        final FormFilterManager formFilterManager = new FormFilterManager(formFilter);
        formFilter.setForm(form);
        Assert.assertEquals(1, formFilterManager.getFilledForms().size());
    }

    @Test
    public void getFilledFormsForExcludeRule() {
        final Site site = TestUtil.createSite();
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "f");

        final DraftFormFilterRule rule = new DraftFormFilterRule();
        rule.setInclude(false);
        StringUtil.addMany(rule.getCriteria(), "f");
        rule.setFormItemId(form.getFormItems().get(0).getFormItemId());

        final DraftFormFilter formFilter = new DraftFormFilter();
        formFilter.addRule(rule);

        final FilledForm filledForm = TestUtil.createFilledForm(form);
        filledForm.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());
        filledForm.getFilledFormItems().get(0).setValue("f");

        final FilledForm filledForm1 = TestUtil.createFilledForm(form);
        filledForm1.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());
        filledForm1.getFilledFormItems().get(0).setValue("D");

        final FormFilterManager formFilterManager = new FormFilterManager(formFilter);
        formFilter.setForm(form);
        Assert.assertEquals(1, formFilterManager.getFilledForms().size());
        Assert.assertEquals(filledForm1, formFilterManager.getFilledForms().get(0));
    }

}
