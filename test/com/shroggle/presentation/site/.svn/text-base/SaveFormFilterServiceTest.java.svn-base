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
package com.shroggle.presentation.site;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.FormFilterNotFoundException;
import com.shroggle.exception.FormFilterNotUniqueNameException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.exception.FormNotFoundException;
import com.shroggle.logic.form.filter.FormFilterEdit;
import com.shroggle.logic.form.filter.FormFilterRuleEdit;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@RunWith(TestRunnerWithMockServices.class)
public class SaveFormFilterServiceTest {

    @Test
    public void executeNew() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final DraftCustomForm form = TestUtil.createCustomForm(site.getSiteId(), "custom form");

        final FormFilterEdit request = new FormFilterEdit();
        request.setName("Filter name1");
        request.setId(null);
        request.setFormId(form.getFormId());

        List<FormFilterRuleEdit> rules = new ArrayList<FormFilterRuleEdit>();
        FormFilterRuleEdit rule = new FormFilterRuleEdit();
        rule.setCriteria(new ArrayList<String>());
        rule.setFormItemId(form.getFormItems().get(0).getFormItemId());
        rules.add(rule);

        request.setRules(rules);

        final int formFilterId = service.execute(request);
        final DraftFormFilter formFilter = persistance.getFormFilterById(formFilterId);
        Assert.assertNotNull(formFilter);
        Assert.assertEquals("Filter name1", formFilter.getName());
        Assert.assertEquals(rules.size(), formFilter.getRules().size());
        Assert.assertEquals(rules.get(0).isInclude(), formFilter.getRules().get(0).isInclude());
        Assert.assertEquals(rules.get(0).getCriteria(), formFilter.getRules().get(0).getCriteria());
        Assert.assertEquals(rules.get(0).getFormItemId(), formFilter.getRules().get(0).getFormItemId());
        Assert.assertEquals(form, formFilter.getForm());
    }

    @Test(expected = FormNotFoundException.class)
    public void executeNewWithNotFoundForm() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final DraftCustomForm form = TestUtil.createCustomForm(site.getSiteId(), "custom form");

        final FormFilterEdit request = new FormFilterEdit();
        request.setName("Filter name1");
        request.setId(null);

        List<FormFilterRuleEdit> rules = new ArrayList<FormFilterRuleEdit>();
        FormFilterRuleEdit rule = new FormFilterRuleEdit();
        rule.setCriteria(new ArrayList<String>());
        rule.setFormItemId(form.getFormItems().get(0).getFormItemId());
        rules.add(rule);

        request.setRules(rules);

        service.execute(request);
    }

    @Test(expected = FormNotFoundException.class)
    public void executeNewFotNotMy() {
        TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);
        final DraftCustomForm form = TestUtil.createCustomForm(site.getSiteId(), "custom form");

        final FormFilterEdit request = new FormFilterEdit();
        request.setName("Filter name1");
        request.setId(null);
        request.setFormId(form.getFormId());

        List<FormFilterRuleEdit> rules = new ArrayList<FormFilterRuleEdit>();
        FormFilterRuleEdit rule = new FormFilterRuleEdit();
        rule.setCriteria(new ArrayList<String>());
        rule.setFormItemId(form.getFormItems().get(0).getFormItemId());
        rules.add(rule);

        request.setRules(rules);

        service.execute(request);
    }

    @Test
    public void executeFormFilterEdit() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final DraftCustomForm form = TestUtil.createCustomForm(site.getSiteId(), "custom form");

        final DraftFormFilter formFilter = TestUtil.createFormFilter(
                form, "name", new DraftFormFilterRule());

        final FormFilterEdit request = new FormFilterEdit();
        request.setName("Edited filter1");
        request.setId(formFilter.getFormFilterId());
        request.setFormId(formFilter.getForm().getId());

        final FormFilterRuleEdit ruleEdit = new FormFilterRuleEdit();
        ruleEdit.setCriteria(Arrays.asList("d", "g"));
        ruleEdit.setInclude(false);
        ruleEdit.setFormItemId(2);
        request.getRules().add(ruleEdit);

        final int formFilterId = service.execute(request);
        Assert.assertEquals(formFilter.getFormFilterId(), formFilterId);
        Assert.assertEquals("Edited filter1", formFilter.getName());
        Assert.assertTrue(formFilter.getRules().size() == 1);
        final DraftFormFilterRule rule0 = formFilter.getRules().get(0);
        Assert.assertEquals(2, rule0.getFormItemId());
        Assert.assertEquals(Arrays.asList("d", "g"), rule0.getCriteria());
        Assert.assertEquals(false, rule0.isInclude());
        Assert.assertEquals(form, formFilter.getForm());
    }

    @Test(expected = FormFilterNotUniqueNameException.class)
    public void executeNewWithNotUnqiueName() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final DraftCustomForm form = TestUtil.createCustomForm(site.getSiteId(), "custom form");
        TestUtil.createFormFilter(form, "name");

        final FormFilterEdit request = new FormFilterEdit();
        request.setName("name");
        request.setFormId(form.getFormId());

        service.execute(request);
    }

    @Test
    public void executeEdit() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final DraftCustomForm form = TestUtil.createCustomForm(site.getSiteId(), "custom form");
        final DraftFormFilter formFilter = TestUtil.createFormFilter(form, "1");
        TestUtil.createFormFilter(form, "name1");

        final FormFilterEdit request = new FormFilterEdit();
        request.setName("f");
        request.setId(formFilter.getFormFilterId());
        request.setFormId(form.getFormId());

        service.execute(request);

        Assert.assertEquals("f", formFilter.getName());
    }

    @Test(expected = FormFilterNotFoundException.class)
    public void executeEditNotMy() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);
        final DraftCustomForm form = TestUtil.createCustomForm(site.getSiteId(), "custom form");
        final DraftFormFilter formFilter = TestUtil.createFormFilter(form, "1");
        TestUtil.createUserAndLogin();

        final FormFilterEdit request = new FormFilterEdit();
        request.setName("f");
        request.setId(formFilter.getFormFilterId());
        request.setFormId(form.getFormId());

        service.execute(request);
    }

    @Test(expected = FormFilterNotUniqueNameException.class)
    public void executeEditDublicateFormFilter() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final DraftCustomForm form = TestUtil.createCustomForm(site.getSiteId(), "custom form");
        final DraftFormFilter formFilter = TestUtil.createFormFilter(form, "name");
        TestUtil.createFormFilter(form, "name1");

        final FormFilterEdit request = new FormFilterEdit();
        request.setName("name1");
        request.setId(formFilter.getFormFilterId());
        request.setFormId(form.getFormId());

        service.execute(request);
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeForNullEdit() {
        service.execute(null);
    }

    private final SaveFormFilterService service = new SaveFormFilterService();
    private final Persistance persistance = ServiceLocator.getPersistance();

}
