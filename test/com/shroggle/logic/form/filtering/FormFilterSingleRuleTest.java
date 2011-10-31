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
package com.shroggle.logic.form.filtering;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.logic.form.FilledFormLogic;
import com.shroggle.logic.form.filtering.FormFilterSingleRule;
import com.shroggle.util.StringUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.entity.*;
import org.junit.runner.RunWith;
import org.junit.Test;
import junit.framework.Assert;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class FormFilterSingleRuleTest {

    @Test
    public void acceptWithNullRule() {
        final FormFilterSingleRule ruleProcessor = new FormFilterSingleRule(null);
        final FilledForm filledForm = new FilledForm();
        final FilledFormLogic filledFormLogic = new FilledFormLogic(filledForm);
        Assert.assertTrue(ruleProcessor.accept(filledFormLogic));
    }

    @Test
    public void acceptWithNullFilledForm() {
        final DraftFormFilterRule rule = new DraftFormFilterRule();
        rule.getCriteria().add("Email Address");
        final FormFilterSingleRule ruleProcessor = new FormFilterSingleRule(rule);
        Assert.assertFalse(ruleProcessor.accept(null));
    }

    @Test
    public void acceptWithNotFoundFormItem() {
        final DraftFormFilterRule rule = new DraftFormFilterRule();
        rule.getCriteria().add("Email Address");
        final FilledForm filledForm = new FilledForm();
        final FormFilterSingleRule ruleProcessor = new FormFilterSingleRule(rule);
        Assert.assertTrue(ruleProcessor.accept(new FilledFormLogic(filledForm)));
    }

    @Test
    public void acceptText() {
        final Site site = TestUtil.createSite();
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "f");

        final DraftFormFilterRule rule = new DraftFormFilterRule();
        rule.setFormItemId(form.getFormItems().get(0).getFormItemId());

        final FilledForm filledForm = TestUtil.createFilledForm(form);
        final FormFilterSingleRule ruleProcessor = new FormFilterSingleRule(rule);

        Assert.assertTrue(ruleProcessor.accept(new FilledFormLogic(filledForm)));
    }

    @Test
    public void acceptTextNotEquals() {
        final Site site = TestUtil.createSite();
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "f");

        final DraftFormFilterRule rule = new DraftFormFilterRule();
        rule.getCriteria().add("Email Address");
        rule.setFormItemId(form.getFormItems().get(0).getFormItemId());
        final FormFilterSingleRule ruleProcessor = new FormFilterSingleRule(rule);

        final FilledForm filledForm = TestUtil.createFilledForm(form);
        filledForm.getFilledFormItems().get(0).setValue("d");
        filledForm.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());

        Assert.assertFalse(ruleProcessor.accept(new FilledFormLogic(filledForm)));
    }

    @Test
    public void acceptTextRuleWithoutValue() {
        final Site site = TestUtil.createSite();
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "f");

        final DraftFormFilterRule rule = new DraftFormFilterRule();
        rule.setFormItemId(form.getFormItems().get(0).getFormItemId());
        final FormFilterSingleRule ruleProcessor = new FormFilterSingleRule(rule);

        final FilledForm filledForm = TestUtil.createFilledForm(form);
        filledForm.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());
        List<String> values = filledForm.getFilledFormItems().get(0).getValues();
        values.add("1GG");
        filledForm.getFilledFormItems().get(0).setValues(values);

        Assert.assertTrue(ruleProcessor.accept(new FilledFormLogic(filledForm)));
    }

    @Test
    public void acceptTextWithMoreValue() {
        final Site site = TestUtil.createSite();
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "f");

        final DraftFormFilterRule rule = new DraftFormFilterRule();
        rule.getCriteria().add("GG");
        rule.setFormItemId(form.getFormItems().get(0).getFormItemId());
        final FormFilterSingleRule ruleProcessor = new FormFilterSingleRule(rule);

        final FilledForm filledForm = TestUtil.createFilledForm(form);

        List<String> values = filledForm.getFilledFormItems().get(0).getValues();
        values.add("1GG");
        filledForm.getFilledFormItems().get(0).setValues(values);

        filledForm.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());

        Assert.assertFalse(ruleProcessor.accept(new FilledFormLogic(filledForm)));
    }

    @Test
    public void acceptTextWithMoreValueAndNotEqualsFirst() {
        final Site site = TestUtil.createSite();
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "f");

        final FilledForm filledForm = TestUtil.createFilledForm(form);
        filledForm.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());
        filledForm.getFilledFormItems().get(0).getValues().set(0, "GG");
        final List<String> newValues = new ArrayList<String>() {{
            add("GG");
            add("aaaaaa");
        }};
        filledForm.getFilledFormItems().get(0).setValues(newValues);

        final DraftFormFilterRule rule = new DraftFormFilterRule();
        rule.getCriteria().add("GG");
        rule.setFormItemId(form.getFormItems().get(0).getFormItemId());

        final FormFilterSingleRule ruleProcessor = new FormFilterSingleRule(rule);
        Assert.assertTrue(ruleProcessor.accept(new FilledFormLogic(filledForm)));
    }

    @Test
    public void acceptTextWithMoreValueAndContaint() {
        final Site site = TestUtil.createSite();
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "f");

        final FilledForm filledForm = TestUtil.createFilledForm(form);
        filledForm.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());

        final List<String> newValues = new ArrayList<String>() {{
            add("af GG efrwer");
            add("aaaaaa");
        }};
        filledForm.getFilledFormItems().get(0).setValues(newValues);

        final DraftFormFilterRule rule = new DraftFormFilterRule();
        rule.getCriteria().add("GG");
        rule.setFormItemId(form.getFormItems().get(0).getFormItemId());

        final FormFilterSingleRule ruleProcessor = new FormFilterSingleRule(rule);
        Assert.assertTrue(ruleProcessor.accept(new FilledFormLogic(filledForm)));
    }

    @Test
    public void acceptTextWithMoreValueAndEqualsSomeFirst() {
        final Site site = TestUtil.createSite();
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "f");

        final DraftFormFilterRule rule = new DraftFormFilterRule();
        rule.getCriteria().add("GG");
        rule.getCriteria().add("1");
        rule.getCriteria().add("12");
        rule.setFormItemId(form.getFormItems().get(0).getFormItemId());
        final FormFilterSingleRule ruleProcessor = new FormFilterSingleRule(rule);

        final FilledForm filledForm = TestUtil.createFilledForm(form);
        filledForm.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());
        final List<String> newValues = new ArrayList<String>() {{
            add("GG");
            add("1");
            add("12");
            add("aaaaaa");
        }};
        filledForm.getFilledFormItems().get(0).setValues(newValues);

        Assert.assertTrue(ruleProcessor.accept(new FilledFormLogic(filledForm)));
    }

    @Test
    public void acceptTextWithoutValues() {
        final Site site = TestUtil.createSite();
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "f");

        final DraftFormFilterRule rule = new DraftFormFilterRule();
        StringUtil.addMany(rule.getCriteria(), "GG", "1", "12");
        rule.setFormItemId(form.getFormItems().get(0).getFormItemId());
        final FormFilterSingleRule ruleProcessor = new FormFilterSingleRule(rule);

        final FilledForm filledForm = TestUtil.createFilledForm(form);
        filledForm.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());

        Assert.assertFalse(ruleProcessor.accept(new FilledFormLogic(filledForm)));
    }

    @Test
    public void acceptWithFileFilter_forOrdinaryResource_testExists() {
        final Site site = TestUtil.createSite();
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "f");

        final DraftFormFilterRule rule = new DraftFormFilterRule();
        rule.getCriteria().add("");
        rule.getCriteria().add("");
        rule.setInclude(true);
        rule.setFormItemId(form.getFormItems().get(0).getFormItemId());
        final FormFilterSingleRule ruleProcessor = new FormFilterSingleRule(rule);

        FormFile formFile = TestUtil.createFormFile("image", site.getSiteId());

        final FilledForm filledForm = TestUtil.createFilledForm(form);
        filledForm.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());
        filledForm.getFilledFormItems().get(0).setFormItemName(FormItemName.IMAGE_FILE_UPLOAD);
        filledForm.getFilledFormItems().get(0).setValue("" + formFile.getFormFileId());

        Assert.assertTrue(ruleProcessor.accept(new FilledFormLogic(filledForm)));
    }

    @Test
    public void acceptWithFileFilter_forOrdinaryResource_testWithGoodRange() {
        final Site site = TestUtil.createSite();
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "f");

        final DraftFormFilterRule rule = new DraftFormFilterRule();
        rule.getCriteria().add("0");
        rule.getCriteria().add("999999");
        rule.setInclude(true);
        rule.setFormItemId(form.getFormItems().get(0).getFormItemId());
        final FormFilterSingleRule ruleProcessor = new FormFilterSingleRule(rule);

        FormFile formFile = TestUtil.createFormFile("image", site.getSiteId());

        final FilledForm filledForm = TestUtil.createFilledForm(form);
        filledForm.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());
        filledForm.getFilledFormItems().get(0).setFormItemName(FormItemName.IMAGE_FILE_UPLOAD);
        filledForm.getFilledFormItems().get(0).setValue("" + formFile.getFormFileId());

        Assert.assertTrue(ruleProcessor.accept(new FilledFormLogic(filledForm)));
    }

    @Test
    public void acceptWithFileFilter_forOrdinaryResource_testWithBadRange() {
        final Site site = TestUtil.createSite();
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "f");

        final DraftFormFilterRule rule = new DraftFormFilterRule();
        rule.getCriteria().add("0");
        rule.getCriteria().add("1");
        rule.setInclude(true);
        rule.setFormItemId(form.getFormItems().get(0).getFormItemId());
        final FormFilterSingleRule ruleProcessor = new FormFilterSingleRule(rule);

        FormFile formFile = TestUtil.createFormFile("image", site.getSiteId());

        final FilledForm filledForm = TestUtil.createFilledForm(form);
        filledForm.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());
        filledForm.getFilledFormItems().get(0).setFormItemName(FormItemName.IMAGE_FILE_UPLOAD);
        final List<String> filledItemValues = filledForm.getFilledFormItems().get(0).getValues();
        filledItemValues.clear();
        filledItemValues.add("" + formFile.getFormFileId());
        filledForm.getFilledFormItems().get(0).setValues(filledItemValues);

        Assert.assertFalse(ruleProcessor.accept(new FilledFormLogic(filledForm)));
    }

    @Test
    public void acceptWithFileFilter_forOrdinaryResource_testWithResourceNotFound() {
        final Site site = TestUtil.createSite();
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "f");

        final DraftFormFilterRule rule = new DraftFormFilterRule();
        rule.getCriteria().add("0");
        rule.getCriteria().add("1");
        rule.setInclude(true);
        rule.setFormItemId(form.getFormItems().get(0).getFormItemId());
        final FormFilterSingleRule ruleProcessor = new FormFilterSingleRule(rule);

        TestUtil.createFormFile("image", site.getSiteId());

        final FilledForm filledForm = TestUtil.createFilledForm(form);
        filledForm.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());
        filledForm.getFilledFormItems().get(0).setFormItemName(FormItemName.IMAGE_FILE_UPLOAD);
        final List<String> filledItemValues = filledForm.getFilledFormItems().get(0).getValues();
        filledItemValues.clear();
        filledItemValues.add("123");
        filledForm.getFilledFormItems().get(0).setValues(filledItemValues);

        Assert.assertFalse(ruleProcessor.accept(new FilledFormLogic(filledForm)));
    }

    @Test
    public void acceptWithFileFilter_forOrdinaryResource_testWithFileNotFound() {
        final Site site = TestUtil.createSite();
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "f");

        final DraftFormFilterRule rule = new DraftFormFilterRule();
        rule.getCriteria().add("0");
        rule.getCriteria().add("1");
        rule.setInclude(true);
        rule.setFormItemId(form.getFormItems().get(0).getFormItemId());
        final FormFilterSingleRule ruleProcessor = new FormFilterSingleRule(rule);

        FormFile formFile = new FormFile();
        formFile.setSourceName("image");
        formFile.setSourceExtension("jpeg");
        formFile.setSiteId(site.getSiteId());
        ServiceLocator.getPersistance().putFormFile(formFile);

        final FilledForm filledForm = TestUtil.createFilledForm(form);
        filledForm.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());
        filledForm.getFilledFormItems().get(0).setFormItemName(FormItemName.IMAGE_FILE_UPLOAD);
        final List<String> filledItemValues = filledForm.getFilledFormItems().get(0).getValues();
        filledItemValues.clear();
        filledItemValues.add("" + formFile.getFormFileId());
        filledForm.getFilledFormItems().get(0).setValues(filledItemValues);

        Assert.assertFalse(ruleProcessor.accept(new FilledFormLogic(filledForm)));
    }

    @Test
    public void acceptWithDateFilter_HH_MM_DateFits() {
        final Site site = TestUtil.createSite();
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "f");

        final DraftFormFilterRule rule = new DraftFormFilterRule();
        rule.getCriteria().add("0");
        rule.getCriteria().add("0");
        rule.getCriteria().add("23");
        rule.getCriteria().add("60");
        rule.setInclude(true);
        rule.setFormItemId(form.getFormItems().get(0).getFormItemId());
        final FormFilterSingleRule ruleProcessor = new FormFilterSingleRule(rule);

        final FilledForm filledForm = TestUtil.createFilledForm(form);
        filledForm.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());
        filledForm.getFilledFormItems().get(0).setFormItemName(FormItemName.DURATION);

        final List<String> filledItemValues = filledForm.getFilledFormItems().get(0).getValues();
        filledItemValues.clear();
        filledItemValues.add("15");
        filledItemValues.add("35");
        filledForm.getFilledFormItems().get(0).setValues(filledItemValues);

        Assert.assertTrue(ruleProcessor.accept(new FilledFormLogic(filledForm)));

        filledItemValues.clear();
        filledItemValues.add("0");
        filledItemValues.add("0");
        filledForm.getFilledFormItems().get(0).setValues(filledItemValues);

        Assert.assertTrue(ruleProcessor.accept(new FilledFormLogic(filledForm)));
    }

    @Test
    public void acceptWithDateFilter_HH_MM_DateDoesntFits() {
        final Site site = TestUtil.createSite();
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "f");

        final DraftFormFilterRule rule = new DraftFormFilterRule();
        rule.getCriteria().add("0");
        rule.getCriteria().add("0");
        rule.getCriteria().add("4");
        rule.getCriteria().add("60");
        rule.setInclude(true);
        rule.setFormItemId(form.getFormItems().get(0).getFormItemId());
        final FormFilterSingleRule ruleProcessor = new FormFilterSingleRule(rule);

        final FilledForm filledForm = TestUtil.createFilledForm(form);
        filledForm.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());
        filledForm.getFilledFormItems().get(0).setFormItemName(FormItemName.DURATION);

        final List<String> filledItemValues = filledForm.getFilledFormItems().get(0).getValues();
        filledItemValues.clear();
        filledItemValues.add("15");
        filledItemValues.add("35");
        filledForm.getFilledFormItems().get(0).setValues(filledItemValues);

        Assert.assertFalse(ruleProcessor.accept(new FilledFormLogic(filledForm)));
    }

    @Test
    public void acceptWithDateFilter_DD_MM_YYYY_DateFits() {
        final Site site = TestUtil.createSite();
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "f");

        final DraftFormFilterRule rule = new DraftFormFilterRule();
        rule.getCriteria().add("January");
        rule.getCriteria().add("01");
        rule.getCriteria().add("1900");
        rule.getCriteria().add("December");
        rule.getCriteria().add("28");
        rule.getCriteria().add("2009");
        rule.setInclude(true);
        rule.setFormItemId(form.getFormItems().get(0).getFormItemId());
        final FormFilterSingleRule ruleProcessor = new FormFilterSingleRule(rule);

        final FilledForm filledForm = TestUtil.createFilledForm(form);
        filledForm.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());
        filledForm.getFilledFormItems().get(0).setFormItemName(FormItemName.BIRTH_DATE);

        final List<String> filledItemValues = filledForm.getFilledFormItems().get(0).getValues();
        filledItemValues.clear();
        filledItemValues.add("January");
        filledItemValues.add("01");
        filledItemValues.add("2001");
        filledForm.getFilledFormItems().get(0).setValues(filledItemValues);

        Assert.assertTrue(ruleProcessor.accept(new FilledFormLogic(filledForm)));
        filledItemValues.add("December");
        filledItemValues.add("28");
        filledItemValues.add("2009");
        filledForm.getFilledFormItems().get(0).setValues(filledItemValues);

        Assert.assertTrue(ruleProcessor.accept(new FilledFormLogic(filledForm)));
    }

    @Test
    public void acceptWithDateFilter_DD_MM_YYYY_DateFits_WithEmptyDate() {
        final Site site = TestUtil.createSite();
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "f");

        final DraftFormFilterRule rule = new DraftFormFilterRule();
        rule.getCriteria().add("January");
        rule.getCriteria().add("01");
        rule.getCriteria().add("1900");
        rule.getCriteria().add("December");
        rule.getCriteria().add("28");
        rule.getCriteria().add("2009");
        rule.setInclude(true);
        rule.setFormItemId(form.getFormItems().get(0).getFormItemId());
        final FormFilterSingleRule ruleProcessor = new FormFilterSingleRule(rule);

        final FilledForm filledForm = TestUtil.createFilledForm(form);
        filledForm.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());
        filledForm.getFilledFormItems().get(0).setFormItemName(FormItemName.BIRTH_DATE);

        final List<String> filledItemValues = filledForm.getFilledFormItems().get(0).getValues();
        filledItemValues.clear();
        filledForm.getFilledFormItems().get(0).setValues(filledItemValues);

        Assert.assertTrue(ruleProcessor.accept(new FilledFormLogic(filledForm)));
    }

    @Test
    public void acceptWithDateFilter_DD_MM_YYYY_DateFits_WithPartialEmptyDate() {
        final Site site = TestUtil.createSite();
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "f");

        final DraftFormFilterRule rule = new DraftFormFilterRule();
        rule.getCriteria().add("January");
        rule.getCriteria().add("01");
        rule.getCriteria().add("1900");
        rule.getCriteria().add("December");
        rule.getCriteria().add("28");
        rule.getCriteria().add("2009");
        rule.setInclude(true);
        rule.setFormItemId(form.getFormItems().get(0).getFormItemId());
        final FormFilterSingleRule ruleProcessor = new FormFilterSingleRule(rule);

        final FilledForm filledForm = TestUtil.createFilledForm(form);
        filledForm.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());
        filledForm.getFilledFormItems().get(0).setFormItemName(FormItemName.BIRTH_DATE);

        final List<String> filledItemValues = filledForm.getFilledFormItems().get(0).getValues();
        filledItemValues.clear();
        filledItemValues.add("");
        filledItemValues.add("");
        filledItemValues.add("2009");
        filledForm.getFilledFormItems().get(0).setValues(filledItemValues);

        Assert.assertTrue(ruleProcessor.accept(new FilledFormLogic(filledForm)));
    }

    @Test
    public void acceptWithDateFilter_DD_MM_YYYY_DateDoesntFits() {
        final Site site = TestUtil.createSite();
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "f");

        final DraftFormFilterRule rule = new DraftFormFilterRule();
        rule.getCriteria().add("January");
        rule.getCriteria().add("01");
        rule.getCriteria().add("1900");
        rule.getCriteria().add("December");
        rule.getCriteria().add("28");
        rule.getCriteria().add("2009");
        rule.setInclude(true);
        rule.setFormItemId(form.getFormItems().get(0).getFormItemId());
        final FormFilterSingleRule ruleProcessor = new FormFilterSingleRule(rule);

        final FilledForm filledForm = TestUtil.createFilledForm(form);
        filledForm.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());
        filledForm.getFilledFormItems().get(0).setFormItemName(FormItemName.BIRTH_DATE);

        final List<String> filledItemValues = filledForm.getFilledFormItems().get(0).getValues();
        filledItemValues.clear();
        filledItemValues.add("January");
        filledItemValues.add("01");
        filledItemValues.add("3009");
        filledForm.getFilledFormItems().get(0).setValues(filledItemValues);

        Assert.assertFalse(ruleProcessor.accept(new FilledFormLogic(filledForm)));
    }

    @Test
    public void acceptWithDateFilter_DD_MM_YYYY_HH_MM_DateFits() {
        final Site site = TestUtil.createSite();
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "f");

        final DraftFormFilterRule rule = new DraftFormFilterRule();
        rule.getCriteria().add("January");
        rule.getCriteria().add("01");
        rule.getCriteria().add("1900");
        rule.getCriteria().add("0");
        rule.getCriteria().add("0");
        rule.getCriteria().add("December");
        rule.getCriteria().add("28");
        rule.getCriteria().add("2009");
        rule.getCriteria().add("23");
        rule.getCriteria().add("60");
        rule.setInclude(true);
        rule.setFormItemId(form.getFormItems().get(0).getFormItemId());
        final FormFilterSingleRule ruleProcessor = new FormFilterSingleRule(rule);

        final FilledForm filledForm = TestUtil.createFilledForm(form);
        filledForm.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());
        filledForm.getFilledFormItems().get(0).setFormItemName(FormItemName.START_DATE_AND_TIME);

        final List<String> filledItemValues = filledForm.getFilledFormItems().get(0).getValues();
        filledItemValues.clear();
        filledItemValues.add("January");
        filledItemValues.add("01");
        filledItemValues.add("2001");
        filledItemValues.add("5");
        filledItemValues.add("35");
        filledForm.getFilledFormItems().get(0).setValues(filledItemValues);

        Assert.assertTrue(ruleProcessor.accept(new FilledFormLogic(filledForm)));
        filledItemValues.add("December");
        filledItemValues.add("28");
        filledItemValues.add("2009");
        filledItemValues.add("23");
        filledItemValues.add("60");
        filledForm.getFilledFormItems().get(0).setValues(filledItemValues);

        Assert.assertTrue(ruleProcessor.accept(new FilledFormLogic(filledForm)));
    }

    @Test
    public void acceptWithDateFilter_DD_MM_YYYY_HH_MM_DateDoesntFits() {
        final Site site = TestUtil.createSite();
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "f");

        final DraftFormFilterRule rule = new DraftFormFilterRule();
        rule.getCriteria().add("January");
        rule.getCriteria().add("01");
        rule.getCriteria().add("1900");
        rule.getCriteria().add("0");
        rule.getCriteria().add("0");
        rule.getCriteria().add("December");
        rule.getCriteria().add("28");
        rule.getCriteria().add("2009");
        rule.getCriteria().add("23");
        rule.getCriteria().add("60");
        rule.setInclude(true);
        rule.setFormItemId(form.getFormItems().get(0).getFormItemId());
        final FormFilterSingleRule ruleProcessor = new FormFilterSingleRule(rule);

        final FilledForm filledForm = TestUtil.createFilledForm(form);
        filledForm.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());
        filledForm.getFilledFormItems().get(0).setFormItemName(FormItemName.START_DATE_AND_TIME);

        final List<String> filledItemValues = filledForm.getFilledFormItems().get(0).getValues();
        filledItemValues.clear();
        filledItemValues.add("January");
        filledItemValues.add("01");
        filledItemValues.add("3010");
        filledItemValues.add("5");
        filledItemValues.add("35");
        filledForm.getFilledFormItems().get(0).setValues(filledItemValues);

        Assert.assertFalse(ruleProcessor.accept(new FilledFormLogic(filledForm)));
    }

    /* ADVANCED SEARCH FILTERING */
    @Test
    public void acceptWithOnlyNumbersFilter_SEP_OPTIONS() {
        final Site site = TestUtil.createSite();
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "f");

        final DraftAdvancedSearchOption rule = new DraftAdvancedSearchOption();
        rule.getCriteria().add("1");
        rule.setDisplayType(OptionDisplayType.RANGE_AS_SEP_OPTION);
        rule.setFormItemId(form.getFormItems().get(0).getFormItemId());
        final FormFilterSingleRule ruleProcessor = new FormFilterSingleRule(rule);

        final FilledForm filledForm1 = TestUtil.createFilledForm(form);
        filledForm1.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());
        filledForm1.getFilledFormItems().get(0).setFormItemName(FormItemName.SORT_ORDER);
        filledForm1.getFilledFormItems().get(0).setValue("1");

        final FilledForm filledForm2 = TestUtil.createFilledForm(form);
        filledForm2.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());
        filledForm2.getFilledFormItems().get(0).setFormItemName(FormItemName.SORT_ORDER);
        filledForm2.getFilledFormItems().get(0).setValue("2");

        final FilledForm filledForm3 = TestUtil.createFilledForm(form);
        filledForm3.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());
        filledForm3.getFilledFormItems().get(0).setFormItemName(FormItemName.SORT_ORDER);
        filledForm3.getFilledFormItems().get(0).setValue("3");

        Assert.assertTrue(ruleProcessor.accept(new FilledFormLogic(filledForm1)));
        Assert.assertFalse(ruleProcessor.accept(new FilledFormLogic(filledForm2)));
        Assert.assertFalse(ruleProcessor.accept(new FilledFormLogic(filledForm3)));
    }

    @Test
    public void acceptWithOnlyNumbersFilter_RANGE_FROM() {
        final Site site = TestUtil.createSite();
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "f");

        final DraftAdvancedSearchOption rule = new DraftAdvancedSearchOption();
        rule.getCriteria().add("2");
        rule.setDisplayType(OptionDisplayType.RANGE_AS_RANGE);
        rule.setFormItemId(form.getFormItems().get(0).getFormItemId());
        final FormFilterSingleRule ruleProcessor = new FormFilterSingleRule(rule);

        final FilledForm filledForm1 = TestUtil.createFilledForm(form);
        filledForm1.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());
        filledForm1.getFilledFormItems().get(0).setFormItemName(FormItemName.SORT_ORDER);
        filledForm1.getFilledFormItems().get(0).setValue("1");

        final FilledForm filledForm2 = TestUtil.createFilledForm(form);
        filledForm2.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());
        filledForm2.getFilledFormItems().get(0).setFormItemName(FormItemName.SORT_ORDER);
        filledForm2.getFilledFormItems().get(0).setValue("2");

        final FilledForm filledForm3 = TestUtil.createFilledForm(form);
        filledForm3.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());
        filledForm3.getFilledFormItems().get(0).setFormItemName(FormItemName.SORT_ORDER);
        filledForm3.getFilledFormItems().get(0).setValue("3");

        Assert.assertFalse(ruleProcessor.accept(new FilledFormLogic(filledForm1)));
        Assert.assertTrue(ruleProcessor.accept(new FilledFormLogic(filledForm2)));
        Assert.assertTrue(ruleProcessor.accept(new FilledFormLogic(filledForm3)));
    }

    @Test
    public void acceptWithOnlyNumbersFilter_RANGE_TILL() {
        final Site site = TestUtil.createSite();
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "f");

        final DraftAdvancedSearchOption rule = new DraftAdvancedSearchOption();
        rule.getCriteria().add(";2");
        rule.setDisplayType(OptionDisplayType.RANGE_AS_RANGE);
        rule.setFormItemId(form.getFormItems().get(0).getFormItemId());
        final FormFilterSingleRule ruleProcessor = new FormFilterSingleRule(rule);

        final FilledForm filledForm1 = TestUtil.createFilledForm(form);
        filledForm1.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());
        filledForm1.getFilledFormItems().get(0).setFormItemName(FormItemName.SORT_ORDER);
        filledForm1.getFilledFormItems().get(0).setValue("1");

        final FilledForm filledForm2 = TestUtil.createFilledForm(form);
        filledForm2.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());
        filledForm2.getFilledFormItems().get(0).setFormItemName(FormItemName.SORT_ORDER);
        filledForm2.getFilledFormItems().get(0).setValue("2");

        final FilledForm filledForm3 = TestUtil.createFilledForm(form);
        filledForm3.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());
        filledForm3.getFilledFormItems().get(0).setFormItemName(FormItemName.SORT_ORDER);
        filledForm3.getFilledFormItems().get(0).setValue("3");

        Assert.assertTrue(ruleProcessor.accept(new FilledFormLogic(filledForm1)));
        Assert.assertTrue(ruleProcessor.accept(new FilledFormLogic(filledForm2)));
        Assert.assertFalse(ruleProcessor.accept(new FilledFormLogic(filledForm3)));
    }

    @Test
    public void acceptWithOnlyNumbersFilter_RANGE_BETWEEN() {
        final Site site = TestUtil.createSite();
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "f");

        final DraftAdvancedSearchOption rule = new DraftAdvancedSearchOption();
        rule.getCriteria().add("2;3");
        rule.setDisplayType(OptionDisplayType.RANGE_AS_RANGE);
        rule.setFormItemId(form.getFormItems().get(0).getFormItemId());
        final FormFilterSingleRule ruleProcessor = new FormFilterSingleRule(rule);

        final FilledForm filledForm1 = TestUtil.createFilledForm(form);
        filledForm1.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());
        filledForm1.getFilledFormItems().get(0).setFormItemName(FormItemName.SORT_ORDER);
        filledForm1.getFilledFormItems().get(0).setValue("1");

        final FilledForm filledForm2 = TestUtil.createFilledForm(form);
        filledForm2.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());
        filledForm2.getFilledFormItems().get(0).setFormItemName(FormItemName.SORT_ORDER);
        filledForm2.getFilledFormItems().get(0).setValue("2");

        final FilledForm filledForm3 = TestUtil.createFilledForm(form);
        filledForm3.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());
        filledForm3.getFilledFormItems().get(0).setFormItemName(FormItemName.SORT_ORDER);
        filledForm3.getFilledFormItems().get(0).setValue("3");

        final FilledForm filledForm4 = TestUtil.createFilledForm(form);
        filledForm4.getFilledFormItems().get(0).setFormItemId(form.getFormItems().get(0).getFormItemId());
        filledForm4.getFilledFormItems().get(0).setFormItemName(FormItemName.SORT_ORDER);
        filledForm4.getFilledFormItems().get(0).setValue("4");

        Assert.assertFalse(ruleProcessor.accept(new FilledFormLogic(filledForm1)));
        Assert.assertTrue(ruleProcessor.accept(new FilledFormLogic(filledForm2)));
        Assert.assertTrue(ruleProcessor.accept(new FilledFormLogic(filledForm3)));
        Assert.assertFalse(ruleProcessor.accept(new FilledFormLogic(filledForm4)));
    }

}
