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
import com.shroggle.exception.PageBreakBeforeRequiredFieldsException;
import com.shroggle.exception.RegistrationFormNotFoundException;
import com.shroggle.exception.RegistrationFormNotUniqueNameException;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.logic.groups.GroupsTime;
import com.shroggle.logic.groups.GroupsTimeManager;
import com.shroggle.logic.registration.RegistrationFormCreator;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.presentation.registration.SaveRegistrationRequest;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author dmitry.solomadin
 *         Date: 22.01.2009
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class RegistrationFormCreatorTest {
    private final International international = ServiceLocator.getInternationStorage().get("formTable", Locale.US);
    private final Persistance persistance = ServiceLocator.getPersistance();

    @Test
    public void edit() throws ServletException, IOException {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        widget.setPosition(0);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        final Group group = TestUtil.createGroup("group1", site);

        DraftRegistrationForm registrationForm = new DraftRegistrationForm();
        registrationForm.setName("asd");
        registrationForm.setDescription("asd");
        registrationForm.setSiteId(site.getSiteId());
        persistance.putRegistrationForm(registrationForm);
        widget.setDraftItem(registrationForm);

        final SaveRegistrationRequest request = new SaveRegistrationRequest();

        request.setWidgetId(widget.getWidgetId());
        request.setFormName("RegistrationName1");
        request.setFormHeader("RegistrationHeader1");
        request.setDefaultForm(true);
        request.setShowHeader(true);
        request.setFormId(widget.getDraftItem().getId());
        request.setFormItems(TestUtil.<DraftFormItem>createFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME));
        final List<GroupsTime> groupsWithTime = new ArrayList<GroupsTime>() {{
            add(new GroupsTime(group.getGroupId(), null));
        }};
        request.setGroupsWithTime(groupsWithTime);

        new RegistrationFormCreator(new UserManager(user)).save(request);

        Assert.assertEquals("RegistrationName1", registrationForm.getName());
        Assert.assertEquals("RegistrationHeader1", registrationForm.getDescription());
        Assert.assertTrue(registrationForm.isShowDescription());
        Assert.assertEquals(widget.getSite().getDefaultFormId(), registrationForm.getFormId());
        Assert.assertEquals(FormType.REGISTRATION, registrationForm.getType());
        Assert.assertEquals(1, GroupsTimeManager.valueOf(registrationForm.getGroupsWithTime()).size());
        Assert.assertEquals(group.getGroupId(), GroupsTimeManager.valueOf(registrationForm.getGroupsWithTime()).get(0).getGroupId());

        final List<DraftFormItem> testFormItems = TestUtil.createFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME);
        for (int i = 0; i < testFormItems.size(); i++) {
            Assert.assertEquals(i, registrationForm.getFormItems().get(i).getPosition());
            Assert.assertEquals(international.get(testFormItems.get(i).getFormItemName().toString() + "_FN"), registrationForm.getFormItems().get(i).getItemName());
            Assert.assertEquals(testFormItems.get(i).getFormItemName(), registrationForm.getFormItems().get(i).getFormItemName());
            Assert.assertFalse(registrationForm.getFormItems().get(i).isRequired());
        }
    }

    @Test(expected = PageBreakBeforeRequiredFieldsException.class)
    public void setWithPageBreakBeforeMandatory() throws ServletException, IOException {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        widget.setPosition(0);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        DraftRegistrationForm registrationForm = new DraftRegistrationForm();
        registrationForm.setName("asd");
        registrationForm.setDescription("asd");
        registrationForm.setSiteId(site.getSiteId());
        persistance.putRegistrationForm(registrationForm);
        widget.setDraftItem(registrationForm);

        final SaveRegistrationRequest request = new SaveRegistrationRequest();

        request.setWidgetId(widget.getWidgetId());
        request.setFormName("RegistrationName1");
        request.setFormHeader("RegistrationHeader1");
        request.setDefaultForm(true);
        request.setShowHeader(true);
        request.setFormId(widget.getDraftItem().getId());
        request.setFormItems(TestUtil.<DraftFormItem>createFormItems(FormItemName.FIRST_NAME, FormItemName.PAGE_BREAK, FormItemName.REGISTRATION_EMAIL));

        new RegistrationFormCreator(new UserManager(user)).save(request);
    }

    @Test
    public void tryToSetNotDefaultForm() throws ServletException, IOException {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        widget.setPosition(0);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        DraftRegistrationForm registrationForm = new DraftRegistrationForm();
        registrationForm.setName("asd");
        registrationForm.setDescription("asd");
        registrationForm.setSiteId(site.getSiteId());
        persistance.putRegistrationForm(registrationForm);
        widget.setDraftItem(registrationForm);
        site.setDefaultFormId(registrationForm.getFormId());

        final SaveRegistrationRequest request = new SaveRegistrationRequest();

        request.setWidgetId(widget.getWidgetId());
        request.setFormName("RegistrationName1");
        request.setFormHeader("RegistrationHeader1");
        request.setDefaultForm(false);
        request.setShowHeader(true);
        request.setFormId(widget.getDraftItem().getId());
        request.setFormItems(TestUtil.<DraftFormItem>createFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME));

        new RegistrationFormCreator(new UserManager(user)).save(request);

        Assert.assertEquals("RegistrationName1", registrationForm.getName());
        Assert.assertEquals("RegistrationHeader1", registrationForm.getDescription());
        Assert.assertTrue(registrationForm.isShowDescription());
        Assert.assertEquals(widget.getSite().getDefaultFormId(), registrationForm.getId());
        Assert.assertEquals(FormType.REGISTRATION, registrationForm.getType());

        final List<DraftFormItem> testFormItems = TestUtil.createFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME);
        for (int i = 0; i < TestUtil.createFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME).size(); i++) {
            Assert.assertEquals(i, registrationForm.getFormItems().get(i).getPosition());
            Assert.assertEquals(international.get(testFormItems.get(i).getFormItemName().toString() + "_FN"), registrationForm.getFormItems().get(i).getItemName());
            Assert.assertEquals(testFormItems.get(i).getFormItemName(), registrationForm.getFormItems().get(i).getFormItemName());
            Assert.assertFalse(registrationForm.getFormItems().get(i).isRequired());
        }
    }

    @Test
    public void setTwoDefaultForm() throws ServletException, IOException {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        widget.setPosition(0);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        DraftRegistrationForm registrationForm = new DraftRegistrationForm();
        registrationForm.setName("registrationFormName1");
        registrationForm.setDescription("asd");
        registrationForm.setSiteId(site.getSiteId());
        persistance.putRegistrationForm(registrationForm);

        final SaveRegistrationRequest request = new SaveRegistrationRequest();

        request.setWidgetId(widget.getWidgetId());
        request.setFormName("RegistrationName1");
        request.setFormHeader("RegistrationHeader1");
        request.setDefaultForm(true);
        request.setShowHeader(true);
        request.setFormId(registrationForm.getFormId());
        request.setFormItems(TestUtil.<DraftFormItem>createFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME));

        new RegistrationFormCreator(new UserManager(user)).save(request);

        Assert.assertEquals("RegistrationName1", registrationForm.getName());
        Assert.assertEquals("RegistrationHeader1", registrationForm.getDescription());
        Assert.assertTrue(registrationForm.isShowDescription());
        Assert.assertEquals(widget.getSite().getDefaultFormId(), registrationForm.getFormId());
        Assert.assertEquals(FormType.REGISTRATION, registrationForm.getType());

        final List<DraftFormItem> testFormItems = TestUtil.createFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME);
        for (int i = 0; i < TestUtil.createFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME).size(); i++) {
            Assert.assertEquals(i, registrationForm.getFormItems().get(i).getPosition());
            Assert.assertEquals(international.get(testFormItems.get(i).getFormItemName().toString() + "_FN"), registrationForm.getFormItems().get(i).getItemName());
            Assert.assertEquals(testFormItems.get(i).getFormItemName(), registrationForm.getFormItems().get(i).getFormItemName());
            Assert.assertFalse(registrationForm.getFormItems().get(i).isRequired());
        }

        DraftRegistrationForm registrationForm2 = new DraftRegistrationForm();
        registrationForm2.setName("registrationFormName2");
        registrationForm2.setSiteId(site.getSiteId());
        registrationForm2.setDescription("asd");
        persistance.putRegistrationForm(registrationForm2);

        request.setWidgetId(widget.getWidgetId());
        request.setFormName("RegistrationName2");
        request.setFormHeader("RegistrationHeader2");
        request.setDefaultForm(true);
        request.setShowHeader(true);
        request.setFormId(registrationForm2.getFormId());
        request.setFormItems(TestUtil.<DraftFormItem>createFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME));

        new RegistrationFormCreator(new UserManager(user)).save(request);

        Assert.assertEquals(widget.getSite().getDefaultFormId(), registrationForm2.getFormId());
    }

    @Test(expected = WidgetNotFoundException.class)
    public void setWithNotFoundWidget() throws ServletException, IOException {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        widget.setPosition(0);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        DraftRegistrationForm registrationForm = new DraftRegistrationForm();
        registrationForm.setName("registrationFormName1");
        registrationForm.setSiteId(site.getSiteId());
        registrationForm.setDescription("asd");
        persistance.putRegistrationForm(registrationForm);

        final SaveRegistrationRequest request = new SaveRegistrationRequest();

        request.setWidgetId(0);
        request.setFormName("RegistrationName1");
        request.setFormHeader("RegistrationHeader1");
        request.setDefaultForm(true);
        request.setShowHeader(true);
        request.setFormId(registrationForm.getFormId());

        new RegistrationFormCreator(new UserManager(user)).save(request);
    }

    @Test(expected = WidgetNotFoundException.class)
    public void setWithNotMy() throws ServletException, IOException {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        final Site site2 = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site2);

        WidgetItem widget = TestUtil.createWidgetItem();
        widget.setPosition(0);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        DraftRegistrationForm registrationForm = new DraftRegistrationForm();
        registrationForm.setName("registrationFormName1");
        registrationForm.setDescription("asd");
        registrationForm.setSiteId(site.getSiteId());
        persistance.putRegistrationForm(registrationForm);

        final SaveRegistrationRequest request = new SaveRegistrationRequest();

        request.setWidgetId(widget.getWidgetId());
        request.setFormName("RegistrationName1");
        request.setFormHeader("RegistrationHeader1");
        request.setDefaultForm(true);
        request.setShowHeader(true);
        request.setFormId(registrationForm.getFormId());

        new RegistrationFormCreator(new UserManager(user)).save(request);
    }

    @Test(expected = RegistrationFormNotFoundException.class)
    public void setWithNotFoundRegistrationForm() throws ServletException, IOException {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        widget.setPosition(0);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        DraftRegistrationForm registrationForm = new DraftRegistrationForm();
        registrationForm.setName("registrationFormName1");
        registrationForm.setDescription("asd");
        registrationForm.setSiteId(site.getSiteId());
        persistance.putRegistrationForm(registrationForm);

        final SaveRegistrationRequest request = new SaveRegistrationRequest();

        request.setWidgetId(widget.getWidgetId());
        request.setFormName("RegistrationName1");
        request.setFormHeader("RegistrationHeader1");
        request.setDefaultForm(true);
        request.setShowHeader(true);
        request.setFormId(0);

        new RegistrationFormCreator(new UserManager(user)).save(request);
    }

    @Test(expected = RegistrationFormNotUniqueNameException.class)
    public void createNotUniqueName() throws ServletException, IOException {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm(site.getSiteId());
        WidgetItem widget = TestUtil.createWidgetItem();
        widget.setPosition(0);
        widget.setDraftItem(registrationForm);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        final SaveRegistrationRequest request = new SaveRegistrationRequest();

        request.setWidgetId(widget.getWidgetId());
        request.setFormName("RegistrationName1");
        request.setFormHeader("RegistrationHeader1");
        request.setDefaultForm(true);
        request.setShowHeader(true);
        request.setFormItems(TestUtil.<DraftFormItem>createFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME));
        request.setFormId(registrationForm.getFormId());

        new RegistrationFormCreator(new UserManager(user)).save(request);

        registrationForm = (DraftRegistrationForm) (widget.getDraftItem());

        Assert.assertEquals("RegistrationName1", registrationForm.getName());
        Assert.assertEquals("RegistrationHeader1", registrationForm.getDescription());
        Assert.assertTrue(registrationForm.isShowDescription());
        Assert.assertEquals(widget.getSite().getDefaultFormId(), widget.getDraftItem().getId());
        Assert.assertEquals(FormType.REGISTRATION, registrationForm.getType());

        final List<DraftFormItem> testFormItems = TestUtil.createFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME);
        for (int i = 0; i < testFormItems.size(); i++) {
            Assert.assertEquals(i, registrationForm.getFormItems().get(i).getPosition());
            Assert.assertEquals(international.get(testFormItems.get(i).getFormItemName().toString() + "_FN"), registrationForm.getFormItems().get(i).getItemName());
            Assert.assertEquals(testFormItems.get(i).getFormItemName(), registrationForm.getFormItems().get(i).getFormItemName());
            Assert.assertFalse(registrationForm.getFormItems().get(i).isRequired());
        }

        registrationForm = TestUtil.createRegistrationForm(site.getSiteId());

        request.setWidgetId(widget.getWidgetId());
        request.setFormName("RegistrationName1");
        request.setFormHeader("RegistrationHeader2");
        request.setDefaultForm(true);
        request.setShowHeader(true);
        request.setFormItems(TestUtil.<DraftFormItem>createFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME));
        request.setFormId(registrationForm.getFormId());

        new RegistrationFormCreator(new UserManager(user)).save(request);
    }
}
