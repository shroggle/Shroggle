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
import com.shroggle.exception.*;
import com.shroggle.logic.groups.GroupsTime;
import com.shroggle.logic.groups.GroupsTimeManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UsersGroupManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RunWith(TestRunnerWithMockServices.class)
public class CreateVisitorServiceTest {

    @Before
    public void setRequest() {
        MockWebContext webContext = (MockWebContext) service.getContext();
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest("", "");
        mockHttpServletRequest.setCookies(new Cookie[0]);
        webContext.setHttpServletRequest(mockHttpServletRequest);
    }

    @Test
    public void execute() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm();

        final Group group = TestUtil.createGroup("group1", site);
        registrationForm.setGroupsWithTime(GroupsTimeManager.valueOf(new ArrayList<GroupsTime>(){{
            add(new GroupsTime(group.getGroupId(), null));
        }}));

        final String email = "qwe@qwe.qwe";

        final CreateVisitorRequest request = new CreateVisitorRequest();
        request.setEmail(email);
        request.setPassword("aaa");
        request.setConfirmPassword("aaa");
        request.setWidgetId(widget.getWidgetId());
        request.setVerificationCode("aaaaaa");
        request.setFilledFormItems(TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS));
        request.setFormId(registrationForm.getFormId());
        ServiceLocator.getSessionStorage().setNoBotCode(null, "registration" + widget.getWidgetId(), "aaaaaa");

        CreateVisitorResponse response = service.execute(request);
        Assert.assertEquals("SuccessfullyRegistered", response.getCode());
        Assert.assertNull(response.getNextPageHtml());

        User user = persistance.getUserByEmail(email);
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getRegistrationDate());
        Assert.assertNotNull(user.getActiveted());
        Assert.assertEquals(request.getEmail(), user.getEmail());
        Assert.assertEquals(request.getPassword(), user.getPassword());
        Assert.assertTrue(new UsersGroupManager(user).hasAccessToGroup(group.getGroupId()));

        final UserOnSiteRight userOnSiteRight = persistance.getUserOnSiteRightById(new UserOnSiteRightId(user, site));
        Assert.assertNotNull(userOnSiteRight);
        Assert.assertTrue(userOnSiteRight.isActive());
        Assert.assertEquals(1, userOnSiteRight.getFilledRegistrationFormIds().size());
        FilledForm createdFilledRegistrationForm = persistance.getFilledFormById(userOnSiteRight.getFilledRegistrationFormIds().get(0));
        Assert.assertEquals(FormType.REGISTRATION, createdFilledRegistrationForm.getType());

        List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS);

        Assert.assertEquals(filledFormItems.size(), createdFilledRegistrationForm.getFilledFormItems().size());
        for (int i = 0; i < createdFilledRegistrationForm.getFilledFormItems().size(); i++) {
            Assert.assertEquals(filledFormItems.get(i).getItemName(), createdFilledRegistrationForm.getFilledFormItems().get(i).getItemName());
            for (int j = 0; j < createdFilledRegistrationForm.getFilledFormItems().get(i).getValues().size(); j++) {
                Assert.assertEquals(filledFormItems.get(i).getValues().get(j), createdFilledRegistrationForm.getFilledFormItems().get(i).getValues().get(j));
            }
        }
        Assert.assertNotNull(persistance.getUserOnSiteRightById(new UserOnSiteRightId(user, site)));
        Assert.assertEquals(user.getUserId(), new UsersManager().getLogined().getUserId());
    }
    
    @Test
    public void executeWithFillOutCompletely() throws ServletException, IOException {
        final String email = "qwe@qwe.qwe";

        final User user = TestUtil.createUserAndLogin(email);
        final Site otherSite = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, otherSite);

        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm();

        final Group group = TestUtil.createGroup("group1", site);
        registrationForm.setGroupsWithTime(GroupsTimeManager.valueOf(new ArrayList<GroupsTime>(){{
            add(new GroupsTime(group.getGroupId(), null));
        }}));


        final CreateVisitorRequest request = new CreateVisitorRequest();
        request.setEmail(email);
        request.setFillOutFormCompletely(true);
        //Edit details should be true, in order that service will not check the password.
        request.setEditDetails(true);
        request.setWidgetId(widget.getWidgetId());
        request.setVerificationCode("aaaaaa");
        request.setFilledFormItems(TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS));
        request.setFormId(registrationForm.getFormId());
        ServiceLocator.getSessionStorage().setNoBotCode(null, "registration" + widget.getWidgetId(), "aaaaaa");

        CreateVisitorResponse response = service.execute(request);
        Assert.assertEquals("SuccessfullyEdited", response.getCode());
        Assert.assertNull(response.getNextPageHtml());

        final User updatedUser = persistance.getUserByEmail(email);
        final UserOnSiteRight userOnSiteRight = persistance.getUserOnSiteRightById(new UserOnSiteRightId(updatedUser, site));
        Assert.assertNotNull(userOnSiteRight);
        Assert.assertTrue(userOnSiteRight.isActive());
        Assert.assertEquals(1, userOnSiteRight.getFilledRegistrationFormIds().size());
        FilledForm createdFilledRegistrationForm = persistance.getFilledFormById(userOnSiteRight.getFilledRegistrationFormIds().get(0));
        Assert.assertEquals(FormType.REGISTRATION, createdFilledRegistrationForm.getType());

        List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS);

        Assert.assertEquals(filledFormItems.size(), createdFilledRegistrationForm.getFilledFormItems().size());
        for (int i = 0; i < createdFilledRegistrationForm.getFilledFormItems().size(); i++) {
            Assert.assertEquals(filledFormItems.get(i).getItemName(), createdFilledRegistrationForm.getFilledFormItems().get(i).getItemName());
            for (int j = 0; j < createdFilledRegistrationForm.getFilledFormItems().get(i).getValues().size(); j++) {
                Assert.assertEquals(filledFormItems.get(i).getValues().get(j),
                        createdFilledRegistrationForm.getFilledFormItems().get(i).getValues().get(j));
            }
        }

        Assert.assertEquals(updatedUser.getUserId(), new UsersManager().getLogined().getUserId());
    }

    @Test
    public void executeForNetworkRegistration() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm();
        registrationForm.setSiteId(site.getSiteId());
        registrationForm.setNetworkRegistration(true);
        final Group group = TestUtil.createGroup("group1", site);
        registrationForm.setGroupsWithTime(GroupsTimeManager.valueOf(new ArrayList<GroupsTime>(){{
            add(new GroupsTime(group.getGroupId(), null));
        }}));

        final String email = "qwe@qwe.qwe";

        final CreateVisitorRequest request = new CreateVisitorRequest();
        request.setEmail(email);
        request.setPassword("aaa");
        request.setConfirmPassword("aaa");
        request.setWidgetId(widget.getWidgetId());
        request.setVerificationCode("aaaaaa");
        request.setFilledFormItems(TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS));
        request.setFormId(registrationForm.getFormId());
        ServiceLocator.getSessionStorage().setNoBotCode(null, "registration" + widget.getWidgetId(), "aaaaaa");

        CreateVisitorResponse response = service.execute(request);
        Assert.assertEquals("SuccessfullyRegistered", response.getCode());
        Assert.assertNull(response.getNextPageHtml());

        User user = persistance.getUserByEmail(email);
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getRegistrationDate());
        Assert.assertNotNull(user.getActiveted());
        Assert.assertEquals(request.getEmail(), user.getEmail());
        Assert.assertEquals(request.getPassword(), user.getPassword());
        Assert.assertTrue(new UsersGroupManager(user).hasAccessToGroup(group.getGroupId()));

        final UserOnSiteRight userOnSiteRight = persistance.getUserOnSiteRightById(new UserOnSiteRightId(user, site));
        Assert.assertNotNull(userOnSiteRight);
        Assert.assertTrue(userOnSiteRight.isActive());
        Assert.assertEquals(1, userOnSiteRight.getFilledRegistrationFormIds().size());
        FilledForm createdFilledRegistrationForm = persistance.getFilledFormById(userOnSiteRight.getFilledRegistrationFormIds().get(0));
        Assert.assertEquals(FormType.REGISTRATION, createdFilledRegistrationForm.getType());
        Assert.assertEquals(true, createdFilledRegistrationForm.isNetworkRegistration());

        List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS);

        Assert.assertEquals(filledFormItems.size(), createdFilledRegistrationForm.getFilledFormItems().size());
        for (int i = 0; i < createdFilledRegistrationForm.getFilledFormItems().size(); i++) {
            Assert.assertEquals(filledFormItems.get(i).getItemName(), createdFilledRegistrationForm.getFilledFormItems().get(i).getItemName());
            for (int j = 0; j < createdFilledRegistrationForm.getFilledFormItems().get(i).getValues().size(); j++) {
                Assert.assertEquals(filledFormItems.get(i).getValues().get(j),
                        createdFilledRegistrationForm.getFilledFormItems().get(i).getValues().get(j));
            }
        }
        Assert.assertNotNull(persistance.getUserOnSiteRightById(new UserOnSiteRightId(user, site)));
    }

    @Test
    public void executeWithPageBreaks() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm();

        final List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        formItems.add(TestUtil.createFormItem(FormItemName.FIRST_NAME, 0));
        formItems.add(TestUtil.createFormItem(FormItemName.LAST_NAME, 1));
        formItems.add(TestUtil.createFormItem(FormItemName.PAGE_BREAK, 2));
        formItems.add(TestUtil.createFormItem(FormItemName.ACADEMIC_DEGREE, 3));
        formItems.add(TestUtil.createFormItem(FormItemName.PAGE_BREAK, 4));
        formItems.add(TestUtil.createFormItem(FormItemName.ADOPTED, 5));

        registrationForm.setFormItems(formItems);

        final String email = "qwe@qwe.qwe";

        CreateVisitorRequest request = new CreateVisitorRequest();
        request.setEmail(email);
        request.setPassword("aaa");
        request.setConfirmPassword("aaa");
        request.setWidgetId(widget.getWidgetId());
        request.setPageBreaksToPass(1);
        request.setRequestNextPage(true);
        request.setUserId(user.getUserId());
        request.setVerificationCode("aaaaaa");
        request.setFilledFormItems(TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS));
        request.setFormId(registrationForm.getFormId());
        ServiceLocator.getSessionStorage().setNoBotCode(null, "registration" + widget.getWidgetId(), "aaaaaa");

        final Group group = TestUtil.createGroup("group1", site);
        registrationForm.setGroupsWithTime(GroupsTimeManager.valueOf(new ArrayList<GroupsTime>(){{
            add(new GroupsTime(group.getGroupId(), null));
        }}));

        CreateVisitorResponse response = service.execute(request);
        Assert.assertEquals("SuccessfullyRegistered", response.getCode());
        Assert.assertNotNull(response.getNextPageHtml());

        User userByEmail = persistance.getUserByEmail(email);
        Assert.assertNotNull(userByEmail);
        Assert.assertNotNull(userByEmail.getRegistrationDate());
        Assert.assertNotNull(userByEmail.getActiveted());
        Assert.assertEquals(request.getEmail(), userByEmail.getEmail());
        Assert.assertEquals(request.getPassword(), userByEmail.getPassword());
        Assert.assertFalse(new UsersGroupManager(user).hasAccessToGroup(group.getGroupId()));
        Assert.assertEquals(0, new UsersGroupManager(user).getAccessibleGroupsId().size());

        final UserOnSiteRight userOnSiteRight = persistance.getUserOnSiteRightById(new UserOnSiteRightId(userByEmail, site));
        Assert.assertNotNull(userOnSiteRight);
        Assert.assertTrue(userOnSiteRight.isActive());
        Assert.assertEquals(1, userOnSiteRight.getFilledRegistrationFormIds().size());
        FilledForm createdFilledRegistrationForm = persistance.getFilledFormById(userOnSiteRight.getFilledRegistrationFormIds().get(0));
        Assert.assertEquals(FormType.REGISTRATION, createdFilledRegistrationForm.getType());

        List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS);

        Assert.assertEquals(filledFormItems.size(), createdFilledRegistrationForm.getFilledFormItems().size());
        for (int i = 0; i < createdFilledRegistrationForm.getFilledFormItems().size(); i++) {
            Assert.assertEquals(filledFormItems.get(i).getItemName(), createdFilledRegistrationForm.getFilledFormItems().get(i).getItemName());
            for (int j = 0; j < createdFilledRegistrationForm.getFilledFormItems().get(i).getValues().size(); j++) {
                Assert.assertEquals(filledFormItems.get(i).getValues().get(j),
                        createdFilledRegistrationForm.getFilledFormItems().get(i).getValues().get(j));
            }
        }
        Assert.assertNotNull(persistance.getUserOnSiteRightById(new UserOnSiteRightId(userByEmail, site)));

        request = new CreateVisitorRequest();
        request.setFilledFormItems(TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS));
        request.setPageBreaksToPass(2);
        request.setRequestNextPage(true);
        request.setFormId(registrationForm.getFormId());
        request.setFilledFormId(createdFilledRegistrationForm.getFilledFormId());
        request.setUserId(user.getUserId());

        final Group group2 = TestUtil.createGroup("group2", site);
        registrationForm.setGroupsWithTime(GroupsTimeManager.valueOf(new ArrayList<GroupsTime>(){{
            add(new GroupsTime(group2.getGroupId(), null));
        }}));

        response = service.execute(request);
        Assert.assertEquals("SuccessfullyRegistered", response.getCode());
        Assert.assertNotNull(response.getNextPageHtml());

        request = new CreateVisitorRequest();
        request.setFilledFormItems(TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS));
        request.setPageBreaksToPass(3);
        request.setRequestNextPage(true);
        request.setFormId(registrationForm.getFormId());
        request.setUserId(user.getUserId());
        request.setFilledFormId(createdFilledRegistrationForm.getFilledFormId());
        Assert.assertFalse(new UsersGroupManager(user).hasAccessToGroup(group.getGroupId()));
        Assert.assertEquals(0, new UsersGroupManager(user).getAccessibleGroupsId().size());

        response = service.execute(request);
        Assert.assertEquals("SuccessfullyRegistered", response.getCode());
        Assert.assertNull(response.getNextPageHtml());
    }

    @Test
    public void executeWithPageBreaksFromAddRecord() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm(site);

        final List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        formItems.add(TestUtil.createFormItem(FormItemName.FIRST_NAME, 0));
        formItems.add(TestUtil.createFormItem(FormItemName.LAST_NAME, 1));
        formItems.add(TestUtil.createFormItem(FormItemName.PAGE_BREAK, 2));
        formItems.add(TestUtil.createFormItem(FormItemName.ACADEMIC_DEGREE, 3));
        formItems.add(TestUtil.createFormItem(FormItemName.PAGE_BREAK, 4));
        formItems.add(TestUtil.createFormItem(FormItemName.ADOPTED, 5));

        registrationForm.setFormItems(formItems);

        final String email = "qwe@qwe.qwe";

        final Group group = TestUtil.createGroup("group1", site);
        registrationForm.setGroupsWithTime(GroupsTimeManager.valueOf(new ArrayList<GroupsTime>(){{
            add(new GroupsTime(group.getGroupId(), null));
        }}));

        CreateVisitorRequest request = new CreateVisitorRequest();
        request.setEmail(email);
        request.setPassword("aaa");
        request.setConfirmPassword("aaa");
        request.setWidgetId(0);
        request.setPageBreaksToPass(1);
        request.setRequestNextPage(true);
        request.setVerificationCode("aaaaaa");
        request.setFilledFormItems(TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS));
        request.setShowFromAddRecord(true);
        request.setFormId(registrationForm.getFormId());
        ServiceLocator.getSessionStorage().setNoBotCode(null, "registration" + widget.getWidgetId(), "aaaaaa");

        CreateVisitorResponse response = service.execute(request);
        Assert.assertEquals("SuccessfullyRegistered", response.getCode());
        Assert.assertEquals("/site/showRegistrationForm.action?formId=" + request.getFormId()
                + "&showFromAddRecord=true"
                + "&pageBreaksToPass=" + request.getPageBreaksToPass()
                + "&filledFormToUpdateId=" + response.getFilledFormId()
                + "&registrationUserId=2", response.getNextPageHtml());

        User userByEmail = persistance.getUserByEmail(email);
        Assert.assertNotNull(userByEmail);
        Assert.assertNotNull(userByEmail.getRegistrationDate());
        Assert.assertNotNull(userByEmail.getActiveted());
        Assert.assertEquals(request.getEmail(), userByEmail.getEmail());
        Assert.assertEquals(request.getPassword(), userByEmail.getPassword());
        Assert.assertFalse(new UsersGroupManager(user).hasAccessToGroup(group.getGroupId()));
        Assert.assertEquals(0, new UsersGroupManager(user).getAccessibleGroupsId().size());

        final UserOnSiteRight userOnSiteRight = persistance.getUserOnSiteRightById(new UserOnSiteRightId(userByEmail, site));
        Assert.assertNotNull(userOnSiteRight);
        Assert.assertTrue(userOnSiteRight.isActive());
        Assert.assertEquals(1, userOnSiteRight.getFilledRegistrationFormIds().size());
        FilledForm createdFilledRegistrationForm = persistance.getFilledFormById(userOnSiteRight.getFilledRegistrationFormIds().get(0));
        Assert.assertEquals(FormType.REGISTRATION, createdFilledRegistrationForm.getType());

        List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS);

        Assert.assertEquals(filledFormItems.size(), createdFilledRegistrationForm.getFilledFormItems().size());
        for (int i = 0; i < createdFilledRegistrationForm.getFilledFormItems().size(); i++) {
            Assert.assertEquals(filledFormItems.get(i).getItemName(), createdFilledRegistrationForm.getFilledFormItems().get(i).getItemName());
            for (int j = 0; j < createdFilledRegistrationForm.getFilledFormItems().get(i).getValues().size(); j++) {
                Assert.assertEquals(filledFormItems.get(i).getValues().get(j),
                        createdFilledRegistrationForm.getFilledFormItems().get(i).getValues().get(j));
            }
        }
        Assert.assertNotNull(persistance.getUserOnSiteRightById(new UserOnSiteRightId(userByEmail, site)));

        request = new CreateVisitorRequest();
        request.setFilledFormItems(TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS));
        request.setPageBreaksToPass(2);
        request.setRequestNextPage(true);
        request.setFormId(registrationForm.getFormId());
        request.setUserId(user.getUserId());
        request.setShowFromAddRecord(true);
        request.setFilledFormId(createdFilledRegistrationForm.getFilledFormId());

        final Group group2 = TestUtil.createGroup("group2", site);
        registrationForm.setGroupsWithTime(GroupsTimeManager.valueOf(new ArrayList<GroupsTime>(){{
            add(new GroupsTime(group2.getGroupId(), null));
        }}));

        response = service.execute(request);
        Assert.assertEquals("SuccessfullyRegistered", response.getCode());
        Assert.assertEquals("/site/showRegistrationForm.action?formId=" + request.getFormId()
                + "&showFromAddRecord=true"
                + "&pageBreaksToPass=" + request.getPageBreaksToPass()
                + "&filledFormToUpdateId=" + response.getFilledFormId() + "&registrationUserId=1", response.getNextPageHtml());

        request = new CreateVisitorRequest();
        request.setFilledFormItems(TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS));
        request.setPageBreaksToPass(3);
        request.setRequestNextPage(true);
        request.setFormId(registrationForm.getFormId());
        request.setFilledFormId(createdFilledRegistrationForm.getFilledFormId());
        request.setShowFromAddRecord(true);
        request.setUserId(user.getUserId());
        Assert.assertFalse(new UsersGroupManager(user).hasAccessToGroup(group.getGroupId()));
        Assert.assertEquals(0, new UsersGroupManager(user).getAccessibleGroupsId().size());

        response = service.execute(request);
        Assert.assertEquals("SuccessfullyRegistered", response.getCode());
        Assert.assertNull(response.getNextPageHtml());
    }

    @Test
    public void executeFromAddRecord() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        final User oldUser = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm(site);
        final String email = "qwe@qwe.qwe";

        final CreateVisitorRequest request = new CreateVisitorRequest();
        request.setEmail(email);
        request.setPassword("aaa");
        request.setConfirmPassword("aaa");
        request.setShowFromAddRecord(true);
        request.setVerificationCode("aaaaaa");
        request.setFilledFormItems(TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS));
        request.setFormId(registrationForm.getFormId());

        final Group group = TestUtil.createGroup("group1", site);
        registrationForm.setGroupsWithTime(GroupsTimeManager.valueOf(new ArrayList<GroupsTime>(){{
            add(new GroupsTime(group.getGroupId(), null));
        }}));

        CreateVisitorResponse response = service.execute(request);
        Assert.assertEquals("SuccessfullyRegistered", response.getCode());
        Assert.assertNull(response.getNextPageHtml());

        User user = persistance.getUserByEmail(email);
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getRegistrationDate());
        Assert.assertNotNull(user.getActiveted());
        Assert.assertEquals(request.getEmail(), user.getEmail());
        Assert.assertEquals(request.getPassword(), user.getPassword());
        Assert.assertTrue(new UsersGroupManager(user).hasAccessToGroup(group.getGroupId()));
        Assert.assertEquals(1, new UsersGroupManager(user).getAccessibleGroupsId().size());

        final UserOnSiteRight userOnSiteRight = persistance.getUserOnSiteRightById(new UserOnSiteRightId(user, site));
        Assert.assertNotNull(userOnSiteRight);
        Assert.assertTrue(userOnSiteRight.isActive());
        Assert.assertEquals(1, userOnSiteRight.getFilledRegistrationFormIds().size());
        FilledForm createdFilledRegistrationForm = persistance.getFilledFormById(userOnSiteRight.getFilledRegistrationFormIds().get(0));
        Assert.assertEquals(FormType.REGISTRATION, createdFilledRegistrationForm.getType());

        List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS);

        Assert.assertEquals(filledFormItems.size(), createdFilledRegistrationForm.getFilledFormItems().size());
        for (int i = 0; i < createdFilledRegistrationForm.getFilledFormItems().size(); i++) {
            Assert.assertEquals(filledFormItems.get(i).getItemName(), createdFilledRegistrationForm.getFilledFormItems().get(i).getItemName());
            for (int j = 0; j < createdFilledRegistrationForm.getFilledFormItems().get(i).getValues().size(); j++) {
                Assert.assertEquals(filledFormItems.get(i).getValues().get(j),
                        createdFilledRegistrationForm.getFilledFormItems().get(i).getValues().get(j));
            }
        }
        Assert.assertNotNull(persistance.getUserOnSiteRightById(new UserOnSiteRightId(user, site)));
        Assert.assertEquals(oldUser.getUserId(), new UsersManager().getLogined().getUserId());
    }

    @Test
    public void executeDataForInvited() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        final User invitedUser = TestUtil.createUser("f@f.com");
        final UserOnSiteRight userOnSiteRight = TestUtil.createUserOnSiteRightActiveAdmin(invitedUser, site);
        userOnSiteRight.setActive(false);
        userOnSiteRight.setVisitorStatus(VisitorStatus.PENDING);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm();

        final Group group = TestUtil.createGroup("group1", site);
        registrationForm.setGroupsWithTime(GroupsTimeManager.valueOf(new ArrayList<GroupsTime>(){{
            add(new GroupsTime(group.getGroupId(), null));
        }}));

        final CreateVisitorRequest request = new CreateVisitorRequest();
        request.setEmail(invitedUser.getEmail());
        request.setPassword("aaa");
        request.setInviteForSiteId(site.getSiteId());
        request.setConfirmPassword("aaa");
        request.setWidgetId(widget.getWidgetId());
        request.setVerificationCode("aaaaaa");
        request.setFormId(registrationForm.getFormId());
        request.setFilledFormItems(TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS));
        sessionStorage.setNoBotCode(null, "registration" + widget.getWidgetId(), "aaaaaa");

        CreateVisitorResponse response = service.execute(request);
        Assert.assertEquals("InvitedRegistered", response.getCode());
        Assert.assertNull(response.getNextPageHtml());

        Assert.assertNotNull(invitedUser);
        Assert.assertNotNull(invitedUser.getRegistrationDate());
        Assert.assertNotNull(invitedUser.getActiveted());
        Assert.assertEquals(request.getEmail(), invitedUser.getEmail());
        Assert.assertEquals(request.getPassword(), invitedUser.getPassword());
        Assert.assertNotNull(userOnSiteRight);
        Assert.assertTrue(userOnSiteRight.isActive());
        
        Assert.assertTrue(new UsersGroupManager(invitedUser).hasAccessToGroup(group.getGroupId()));
    }

    @Test(expected = ExpiredVisitorAttemptToRegisterException.class)
    public void executeDataForInvitedWithNotPending() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        final User invitedUser = TestUtil.createUser("f@f.com");
        final UserOnSiteRight userOnSiteRight = TestUtil.createUserOnSiteRightActiveAdmin(invitedUser, site);
        userOnSiteRight.setActive(false);
        userOnSiteRight.setVisitorStatus(VisitorStatus.EXPIRED);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm();

        final CreateVisitorRequest request = new CreateVisitorRequest();
        request.setEmail(invitedUser.getEmail());
        request.setPassword("aaa");
        request.setInviteForSiteId(site.getSiteId());
        request.setConfirmPassword("aaa");
        request.setWidgetId(widget.getWidgetId());
        request.setVerificationCode("aaaaaa");
        request.setFormId(registrationForm.getFormId());
        request.setFilledFormItems(TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS));
        sessionStorage.setNoBotCode(null, "registration" + widget.getWidgetId(), "aaaaaa");

        service.execute(request);
    }

    @Test(expected = VisitorWithNullOrEmptyEmailException.class)
    public void executeWithoutLogin() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm();

        CreateVisitorRequest createVisitorRequest = new CreateVisitorRequest();
        createVisitorRequest.setEmail("");
        createVisitorRequest.setPassword("aaa");
        createVisitorRequest.setConfirmPassword("aaa");
        createVisitorRequest.setVerificationCode("aaaaaa");
        createVisitorRequest.setWidgetId(widget.getWidgetId());
        createVisitorRequest.setFormId(registrationForm.getFormId());
        ServiceLocator.getSessionStorage().setNoBotCode(null, "registration" + widget.getWidgetId(), "aaaaaa");
        service.execute(createVisitorRequest);
    }

    @Test(expected = VisitorWithNullPasswordException.class)
    public void executeWithoutPassword() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm();

        CreateVisitorRequest createVisitorRequest = new CreateVisitorRequest();
        createVisitorRequest.setEmail("qwe@qwe.qwe");
        createVisitorRequest.setPassword("");
        createVisitorRequest.setConfirmPassword("aaa");
        createVisitorRequest.setVerificationCode("aaaaaa");
        createVisitorRequest.setWidgetId(widget.getWidgetId());
        createVisitorRequest.setFormId(registrationForm.getFormId());
        ServiceLocator.getSessionStorage().setNoBotCode(null, "registration" + widget.getWidgetId(), "aaaaaa");
        service.execute(createVisitorRequest);
    }

    @Test(expected = VisitorWithNotEqualsPasswordAndConfirmPaswordException.class)
    public void executeWithNotEqualPasswordAndConfirmPassword() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm();

        CreateVisitorRequest createVisitorRequest = new CreateVisitorRequest();
        createVisitorRequest.setEmail("qwe@qwe.qwe");
        createVisitorRequest.setPassword("aaa");
        createVisitorRequest.setConfirmPassword("bbb");
        createVisitorRequest.setVerificationCode("aaaaaa");
        createVisitorRequest.setWidgetId(widget.getWidgetId());
        createVisitorRequest.setFormId(registrationForm.getFormId());
        ServiceLocator.getSessionStorage().setNoBotCode(null, "registration" + widget.getWidgetId(), "aaaaaa");
        service.execute(createVisitorRequest);
    }

    @Test
    public void updateVisitorWithEditDetails() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        final Site site2 = TestUtil.createSite();
        final User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm();

        CreateVisitorRequest request = new CreateVisitorRequest();

        //First, create user and try to create user with same user name
        User user2 = TestUtil.createUserAndUserOnSiteRight(site2);
        user2.setEmail("qwe@qwe.qwe");
        user2.setPassword("aaa");
        persistance.putUser(user2);
        TestUtil.createFilledRegistrationForm(user2, site, registrationForm.getId());

        TestUtil.loginUser(user);

        request.setFormId(registrationForm.getFormId());
        request.setEmail("qwe@qwe.qwe");
        request.setVerificationCode("aaaaaa");
        request.setWidgetId(widget.getWidgetId());
        request.setPassword("aaa");
        request.setUserId(user2.getUserId());
        request.setEditDetails(true);

        //Constructing new filled form items.
        final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
        FilledFormItem oldItemUpdate = new FilledFormItem();
        oldItemUpdate.setFormItemName(FormItemName.ACADEMIC_DEGREE);
        oldItemUpdate.setItemName("name0");
        List<String> values = new ArrayList<String>();
        values.add("newValue");
        oldItemUpdate.setValues(values);
        filledFormItems.add(oldItemUpdate);

        FilledFormItem newItem = new FilledFormItem();
        newItem.setFormItemName(FormItemName.NAME);
        newItem.setItemName("newItem");
        values = new ArrayList<String>();
        values.add("newValue");
        newItem.setValues(values);
        filledFormItems.add(newItem);

        request.setFilledFormItems(filledFormItems);
        ServiceLocator.getSessionStorage().setNoBotCode(null, "registration" + widget.getWidgetId(), "aaaaaa");

        CreateVisitorResponse response = service.execute(request);
        Assert.assertEquals("SuccessfullyEdited", response.getCode());
        Assert.assertNull(response.getNextPageHtml());

        User updatedUser = persistance.getUserByEmail("qwe@qwe.qwe");
        final UserOnSiteRight visitorOnSiteRight = persistance.getUserOnSiteRightById(new UserOnSiteRightId(updatedUser, site));
        Assert.assertNotNull(updatedUser);
        Assert.assertNotNull(visitorOnSiteRight);
        Assert.assertEquals(request.getEmail(), updatedUser.getEmail());
        Assert.assertEquals("aaa", updatedUser.getPassword());
        Assert.assertEquals(1, visitorOnSiteRight.getFilledRegistrationFormIds().size());
        FilledForm createdFilledRegistrationForm = persistance.getFilledFormById(visitorOnSiteRight.getFilledRegistrationFormIds().get(0));
        Assert.assertEquals(FormType.REGISTRATION, createdFilledRegistrationForm.getType());

        final International international = ServiceLocator.getInternationStorage().get("formTable", Locale.US);
        //Constructing filled form items after update.
        final List<FilledFormItem> filledFormItemsAfterUpdate = new ArrayList<FilledFormItem>();
        FilledFormItem item1 = new FilledFormItem();
        item1.setItemName(international.get(FormItemName.FIRST_NAME + "_FN"));
        values = new ArrayList<String>();
        values.add("value");
        item1.setValues(values);
        filledFormItemsAfterUpdate.add(item1);

        FilledFormItem item2 = new FilledFormItem();
        item2.setItemName(international.get(FormItemName.ADDRESS + "_FN"));
        values = new ArrayList<String>();
        values.add("value");
        item2.setValues(values);
        filledFormItemsAfterUpdate.add(item2);

        FilledFormItem item3 = new FilledFormItem();
        item3.setItemName("name0");
        values = new ArrayList<String>();
        values.add("newValue");
        item3.setValues(values);
        filledFormItemsAfterUpdate.add(item3);

        FilledFormItem item4 = new FilledFormItem();
        item4.setItemName("newItem");
        values = new ArrayList<String>();
        values.add("newValue");
        item4.setValues(values);
        filledFormItemsAfterUpdate.add(item4);

        Assert.assertEquals(filledFormItemsAfterUpdate.size(), createdFilledRegistrationForm.getFilledFormItems().size());
        for (int i = 0; i < createdFilledRegistrationForm.getFilledFormItems().size(); i++) {
            Assert.assertEquals(filledFormItemsAfterUpdate.get(i).getItemName(), createdFilledRegistrationForm.getFilledFormItems().get(i).getItemName());
            for (int j = 0; j < createdFilledRegistrationForm.getFilledFormItems().get(i).getValues().size(); j++) {
                Assert.assertEquals(filledFormItemsAfterUpdate.get(i).getValues().get(j),
                        createdFilledRegistrationForm.getFilledFormItems().get(i).getValues().get(j));
            }
        }

        Assert.assertNotNull(persistance.getUserOnSiteRightById(new UserOnSiteRightId(updatedUser, site)));
    }

    @Test
    public void updateVisitorWithAddingToSite() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        final Site site2 = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site2);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm();

        CreateVisitorRequest request = new CreateVisitorRequest();

        //First, create user and try to create user with same user name
        User user = TestUtil.createUserAndUserOnSiteRight(site);
        user.setEmail("qwe@qwe.qwe");
        user.setPassword("aaa");
        persistance.putUser(user);
        TestUtil.createFilledRegistrationForm(user, site, registrationForm.getFormId());

        TestUtil.loginUser(user);

        request.setEmail("qwe@qwe.qwe");
        request.setPassword("aaa");
        request.setConfirmPassword("aaa");
        request.setVerificationCode("aaaaaa");
        request.setFormId(registrationForm.getFormId());
        request.setWidgetId(widget.getWidgetId());

        //Constructing new filled form items.
        final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
        FilledFormItem oldItemUpdate = new FilledFormItem();
        oldItemUpdate.setItemName("name0");
        oldItemUpdate.setFormItemName(FormItemName.NAME);
        List<String> values = new ArrayList<String>();
        values.add("newValue");
        oldItemUpdate.setValues(values);
        filledFormItems.add(oldItemUpdate);

        FilledFormItem newItem = new FilledFormItem();
        newItem.setFormItemName(FormItemName.ACADEMIC_DEGREE);
        newItem.setItemName("newItem");
        values = new ArrayList<String>();
        values.add("newValue");
        newItem.setValues(values);
        filledFormItems.add(newItem);

        request.setFilledFormItems(filledFormItems);
        ServiceLocator.getSessionStorage().setNoBotCode(null, "registration" + widget.getWidgetId(), "aaaaaa");

        CreateVisitorResponse response = service.execute(request);
        Assert.assertEquals("SuccessfullyRegistered", response.getCode());
        Assert.assertNull(response.getNextPageHtml());

        User updatedUser = persistance.getUserByEmail("qwe@qwe.qwe");
        final UserOnSiteRight visitorOnSiteRight = persistance.getUserOnSiteRightById(new UserOnSiteRightId(updatedUser, site2));
        Assert.assertNotNull(updatedUser);
        Assert.assertNotNull(visitorOnSiteRight);
        Assert.assertEquals(request.getEmail(), updatedUser.getEmail());
        Assert.assertEquals("aaa", updatedUser.getPassword());
        Assert.assertEquals(1, visitorOnSiteRight.getFilledRegistrationFormIds().size());
        FilledForm createdFilledRegistrationForm = persistance.getFilledFormById(visitorOnSiteRight.getFilledRegistrationFormIds().get(0));
        Assert.assertEquals(FormType.REGISTRATION, createdFilledRegistrationForm.getType());

        //Constructing filled form items after update.
        final List<FilledFormItem> filledFormItemsAfterUpdate = new ArrayList<FilledFormItem>();
        FilledFormItem item1 = new FilledFormItem();
        item1.setItemName("name0");
        values = new ArrayList<String>();
        values.add("newValue");
        item1.setValues(values);
        filledFormItemsAfterUpdate.add(item1);

        FilledFormItem item3 = new FilledFormItem();
        item3.setItemName("newItem");
        values = new ArrayList<String>();
        values.add("newValue");
        item3.setValues(values);
        filledFormItemsAfterUpdate.add(item3);

        Assert.assertEquals(filledFormItemsAfterUpdate.size(), createdFilledRegistrationForm.getFilledFormItems().size());
        for (int i = 0; i < createdFilledRegistrationForm.getFilledFormItems().size(); i++) {
            Assert.assertEquals(filledFormItemsAfterUpdate.get(i).getItemName(), createdFilledRegistrationForm.getFilledFormItems().get(i).getItemName());
            for (int j = 0; j < createdFilledRegistrationForm.getFilledFormItems().get(i).getValues().size(); j++) {
                Assert.assertEquals(filledFormItemsAfterUpdate.get(i).getValues().get(j),
                        createdFilledRegistrationForm.getFilledFormItems().get(i).getValues().get(j));
            }
        }

        Assert.assertNotNull(persistance.getUserOnSiteRightById(new UserOnSiteRightId(updatedUser, site)));
        Assert.assertNotNull(persistance.getUserOnSiteRightById(new UserOnSiteRightId(updatedUser, site2)));
    }

    @Test(expected = VisitorWithNotUniqueLogin.class)
    public void updateVisitorWithAddingToSiteWithWrongPassword() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        final Site site2 = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site2);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        CreateVisitorRequest request = new CreateVisitorRequest();

        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm();

        //First, create user and try to create user with same user name
        User user = TestUtil.createUserAndUserOnSiteRight(site);
        user.setEmail("qwe@qwe.qwe");
        user.setPassword("aaa");
        persistance.putUser(user);
        TestUtil.createFilledRegistrationForm(user, site, registrationForm.getFormId());

        TestUtil.loginUser(user);

        request.setEmail("qwe@qwe.qwe");
        request.setPassword("bbb");
        request.setConfirmPassword("bbb");
        request.setVerificationCode("aaaaaa");
        request.setWidgetId(widget.getWidgetId());
        request.setFormId(registrationForm.getFormId());

        ServiceLocator.getSessionStorage().setNoBotCode(null, "registration" + widget.getWidgetId(), "aaaaaa");

        service.execute(request);
    }

    @Test(expected = VisitorWithNotEqualsPasswordAndConfirmPaswordException.class)
    public void updateVisitorWithAddingToSiteWithNotEqualsPassword() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        final Site site2 = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site2);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm();

        CreateVisitorRequest request = new CreateVisitorRequest();

        //First, create user and try to create user with same user name
        User user = TestUtil.createUserAndUserOnSiteRight(site);
        user.setEmail("qwe@qwe.qwe");
        user.setPassword("aaa");
        persistance.putUser(user);
        TestUtil.createFilledRegistrationForm(user, site, registrationForm.getFormId());

        TestUtil.loginUser(user);

        request.setEmail("qwe@qwe.qwe");
        request.setPassword("bbb");
        request.setConfirmPassword("bbbb");
        request.setVerificationCode("aaaaaa");
        request.setWidgetId(widget.getWidgetId());
        request.setFormId(registrationForm.getFormId());

        ServiceLocator.getSessionStorage().setNoBotCode(null, "registration" + widget.getWidgetId(), "aaaaaa");

        service.execute(request);
    }

    @Test(expected = VisitorWithNotEqualsPasswordAndConfirmPaswordException.class)
    public void tryToRegisterWithAlreadyRegistered() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm();

        CreateVisitorRequest request = new CreateVisitorRequest();

        //First, create user and try to create user with same user name
        User user = TestUtil.createUserAndUserOnSiteRight(site);
        user.setEmail("qwe@qwe.qwe");
        user.setPassword("aaa");
        persistance.putUser(user);
        TestUtil.createFilledRegistrationForm(user, site, registrationForm.getFormId());

        TestUtil.loginUser(user);

        request.setEmail("qwe@qwe.qwe");
        request.setPassword("bbb");
        request.setConfirmPassword("bbbb");
        request.setVerificationCode("aaaaaa");
        request.setWidgetId(widget.getWidgetId());
        request.setFormId(registrationForm.getFormId());

        ServiceLocator.getSessionStorage().setNoBotCode(null, "registration" + widget.getWidgetId(), "aaaaaa");

        service.execute(request);
    }

    @Test
    public void executeWithVisitorRegisteringTwiceOnDifferentForms() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm();
        final DraftRegistrationForm registrationForm2 = TestUtil.createRegistrationForm();

        CreateVisitorRequest createVisitorRequest = new CreateVisitorRequest();

        //First, create user and try to create user with same user name
        User user = TestUtil.createVisitorForSite(site, registrationForm.getFormId());
        user.setEmail("qwe@qwe.qwe");
        user.setPassword("aaa");
        persistance.putUser(user);

        createVisitorRequest.setEmail("qwe@qwe.qwe");
        createVisitorRequest.setPassword("aaa");
        createVisitorRequest.setConfirmPassword("aaa");
        createVisitorRequest.setVerificationCode("aaaaaa");
        createVisitorRequest.setWidgetId(widget.getWidgetId());
        createVisitorRequest.setFormId(registrationForm2.getFormId());
        createVisitorRequest.setFilledFormItems(TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS));
        ServiceLocator.getSessionStorage().setNoBotCode(null, "registration" + widget.getWidgetId(), "aaaaaa");

        service.execute(createVisitorRequest);

        List<Integer> filledRegistrationFormIds =
                persistance.getUserOnSiteRightByUserAndSiteId(user.getUserId(), site.getSiteId()).getFilledRegistrationFormIds();
        Assert.assertEquals(2, filledRegistrationFormIds.size());
        Assert.assertEquals(registrationForm.getFormId(), persistance.getFilledFormById(filledRegistrationFormIds.get(0)).getFormId());
        Assert.assertEquals(registrationForm2.getFormId(), persistance.getFilledFormById(filledRegistrationFormIds.get(1)).getFormId());
    }

    @Test(expected = VisitorWithNotUniqueLogin.class)
    public void executeWithVisitorRegisteringTwiceOnSameForm() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm();

        CreateVisitorRequest createVisitorRequest = new CreateVisitorRequest();

        //First, create user and try to create user with same user name
        User user = TestUtil.createVisitorForSite(site, registrationForm.getFormId());
        user.setEmail("qwe@qwe.qwe");
        user.setPassword("aaa");
        persistance.putUser(user);

        createVisitorRequest.setEmail("qwe@qwe.qwe");
        createVisitorRequest.setPassword("aaa");
        createVisitorRequest.setConfirmPassword("aaa");
        createVisitorRequest.setVerificationCode("aaaaaa");
        createVisitorRequest.setWidgetId(widget.getWidgetId());
        createVisitorRequest.setFormId(registrationForm.getFormId());
        createVisitorRequest.setFilledFormItems(TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS));
        ServiceLocator.getSessionStorage().setNoBotCode(null, "registration" + widget.getWidgetId(), "aaaaaa");

        service.execute(createVisitorRequest);
    }

    @Test(expected = NotValidVisitorEmailException.class)
    public void executeWithNotValidEmail() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm();

        CreateVisitorRequest createVisitorRequest = new CreateVisitorRequest();

        createVisitorRequest.setEmail("qwe");
        createVisitorRequest.setPassword("aaa");
        createVisitorRequest.setConfirmPassword("aaa");
        createVisitorRequest.setVerificationCode("aaaaaa");
        createVisitorRequest.setWidgetId(widget.getWidgetId());
        createVisitorRequest.setFormId(registrationForm.getFormId());
        ServiceLocator.getSessionStorage().setNoBotCode(null, "registration" + widget.getWidgetId(), "aaaaaa");
        service.execute(createVisitorRequest);
    }    

    @Test(expected = WrongVerificationCodeException.class)
    public void executeWithWrongVerificationCode() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        final Site site2 = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site2);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm();

        CreateVisitorRequest request = new CreateVisitorRequest();

        request.setEmail("qwe@qwe.qwe");
        request.setPassword("aaa");
        request.setConfirmPassword("aaa");
        request.setWidgetId(0);
        request.setFormId(registrationForm.getFormId());
        request.setFilledFormItems(TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS));
        ServiceLocator.getSessionStorage().setNoBotCode(null, "registration0", "aaaaaa");

        service.execute(request);
    }

    @Test(expected = RegistrationFormNotFoundException.class)
    public void executeWithNotFoundForm() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        final Site site2 = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site2);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        CreateVisitorRequest request = new CreateVisitorRequest();

        request.setEmail("qwe@qwe.qwe");
        request.setPassword("aaa");
        request.setConfirmPassword("aaa");
        request.setWidgetId(0);
        request.setVerificationCode("aaaaaa");
        request.setFilledFormItems(TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS));
        ServiceLocator.getSessionStorage().setNoBotCode(null, "registration0", "aaaaaa");

        service.execute(request);
    }

    private final CreateVisitorService service = new CreateVisitorService();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();

}
