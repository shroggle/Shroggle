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
package com.shroggle.presentation.manageRegistrants;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.*;
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.logic.groups.GroupsTime;
import com.shroggle.logic.user.UsersGroupManager;
import com.shroggle.logic.visitor.VisitorInfoGetter;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.presentation.site.GuestInvitationRequest;
import com.shroggle.presentation.site.RegisteredVisitorInfo;
import com.shroggle.util.DateUtil;
import com.shroggle.util.MD5;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.TimeInterval;
import com.shroggle.util.mail.MockMailSender;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author dmitry.solomadin
 */
@RunWith(TestRunnerWithMockServices.class)
public class InviteGuestServiceTest {

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void show() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        final Group group = TestUtil.createGroup("group1", site);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm(site.getSiteId());

        TestUtil.createRegistrationForm(123);

        String response = service.show(site.getSiteId());

        Assert.assertEquals("/account/manageRegistrants/inviteGuest.jsp", response);
        Assert.assertEquals(site.getSiteId(), service.getSiteId());
        Assert.assertEquals(1, service.getGroups().size());
        Assert.assertEquals(group, service.getGroups().get(0));
        Assert.assertEquals("Invited", service.getDefaultGroupName());
        Assert.assertEquals(1, service.getRegistrationForms().size());
        Assert.assertTrue(service.getRegistrationForms().contains(registrationForm));
    }

    @Test(expected = UserNotLoginedException.class)
    public void showWithoutLoginedUser() throws ServletException, IOException {
        final Site site = TestUtil.createSite();

        service.show(site.getSiteId());
    }

    @Test(expected = SiteNotFoundException.class)
    public void showWithoutSite() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        service.show(0);
    }

    @Test
    public void sendGuestInvitation() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        site.setSubDomain("a");

        final Group group1 = TestUtil.createGroup("group1", site);
        final Group group2 = TestUtil.createGroup("group2", site);
        final Group group3 = TestUtil.createGroup("group4", site);

        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm(site.getSiteId(), "My form name");
        registrationForm.setId(123123);

        final GuestInvitationRequest request = new GuestInvitationRequest();
        request.setSiteId(site.getSiteId());
        request.setFormId(registrationForm.getFormId());
        request.setInvitationText("Hi there :)");
        request.setEmail("qwe@qwe.qwe");
        request.setFirstName("First name");
        request.setLastName("Last name");

        GroupsTime groupsTimeInterval1 = new GroupsTime(group1.getGroupId(), TimeInterval.ONE_DAY);
        GroupsTime groupsTimeInterval2 = new GroupsTime(group2.getGroupId(), TimeInterval.ONE_YEAR);
        request.setGroupsWithTimeInterval(Arrays.asList(groupsTimeInterval1, groupsTimeInterval2));

        final RegisteredVisitorInfo response = service.sendGuestInvitation(request);

        Assert.assertEquals("First name", response.getFirstName());
        Assert.assertEquals("Last name", response.getLastName());
        Assert.assertEquals("qwe@qwe.qwe", response.getEmail());
        Assert.assertEquals("My form name", response.getForms().get(0).getName());
        Assert.assertEquals(VisitorInfoGetter.getManageRegistrantsInternational().get(VisitorStatus.PENDING + "_STATUS"),
                response.getStatus());
        Assert.assertEquals(true, response.isInvited());

        final MockMailSender mockMailSender = (MockMailSender) ServiceLocator.getMailSender();
        Assert.assertEquals("qwe@qwe.qwe", mockMailSender.getMails().get(0).getTo());
        final String fullMailBody = mockMailSender.getMails().get(0).getText();
        final Pattern p = Pattern.compile("http://.+ ");
        final Matcher m = p.matcher(fullMailBody);
        String registrationLink = "";
        if (m.find()) {
            registrationLink = m.group().trim();
        }

        //Checking link validity.
        Assert.assertEquals("http://testApplicationUrl/site/showRegistrationForm.action" +
                "?formId=" + registrationForm.getFormId() +
                "&visitorId=" + response.getVisitorId() +
                "&confirmCode=" + MD5.crypt(request.getEmail() + response.getVisitorId()) +
                "&showForInvited=true", registrationLink);

        final User invitedUser = ServiceLocator.getPersistance().getUserById(response.getVisitorId());
        Assert.assertNotNull(invitedUser);

        final FilledForm filledForm = FilledFormManager.getFirstRegistrationFilledFormForSite(invitedUser, site);
        Assert.assertNotNull(filledForm);
        Assert.assertEquals("qwe@qwe.qwe", FilledFormManager.getFilledFormItemValueByItemName(filledForm, FilledFormManager.getFormInternational().get(FormItemName.EMAIL + "_FN")));
        Assert.assertEquals("First name", FilledFormManager.getFilledFormItemValueByItemName(filledForm, FilledFormManager.getFormInternational().get(FormItemName.FIRST_NAME + "_FN")));
        Assert.assertEquals("Last name", FilledFormManager.getFilledFormItemValueByItemName(filledForm, FilledFormManager.getFormInternational().get(FormItemName.LAST_NAME + "_FN")));
        final UsersGroupManager usersGroupManager = new UsersGroupManager(invitedUser);
        Assert.assertEquals(true, usersGroupManager.hasAccessToGroup(group1.getGroupId()));
        Assert.assertEquals(true, usersGroupManager.hasAccessToGroup(group2.getGroupId()));
        for (UsersGroup usersGroup : invitedUser.getUsersGroups()) {
            if (usersGroup.getGroupId() == group1.getGroupId()) {
                final long expirationTime = usersGroup.getExpirationDate().getTime();
                final long expectedExpirationTime = new Date(System.currentTimeMillis() + TimeInterval.ONE_DAY.getMillis()).getTime();
                Assert.assertEquals(DateUtil.toMonthDayAndYear(new Date(expirationTime)), DateUtil.toMonthDayAndYear(new Date(expectedExpirationTime)));
            } 
            if (usersGroup.getGroupId() == group2.getGroupId()) {
                final long expirationTime = usersGroup.getExpirationDate().getTime();
                final long expectedExpirationTime = new Date(System.currentTimeMillis() + TimeInterval.ONE_YEAR.getMillis()).getTime();
                Assert.assertEquals(DateUtil.toMonthDayAndYear(new Date(expirationTime)), DateUtil.toMonthDayAndYear(new Date(expectedExpirationTime)));
            }
        }
        Assert.assertEquals(false, usersGroupManager.hasAccessToGroup(group3.getGroupId()));

        UserOnSiteRight visitorOnSiteRight = ServiceLocator.getPersistance().getUserOnSiteRightById(new UserOnSiteRightId(invitedUser, site));

        Assert.assertNotNull(visitorOnSiteRight);
        Assert.assertEquals(VisitorStatus.PENDING, visitorOnSiteRight.getVisitorStatus());
        Assert.assertEquals(filledForm, ServiceLocator.getPersistance().getFilledFormById(
                visitorOnSiteRight.getFilledRegistrationFormIds().get(0)));
    }

    @Test
    public void sendGuestTwoInvitationOnDifferentSites() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        site.setSubDomain("a");
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm(site.getSiteId(), "My form name");

        GuestInvitationRequest request = new GuestInvitationRequest();
        request.setSiteId(site.getSiteId());
        request.setFormId(registrationForm.getFormId());
        request.setInvitationText("Hi there :)");
        request.setEmail("qwe@qwe.qwe");
        request.setFirstName("First name");
        request.setLastName("Last name");

        RegisteredVisitorInfo response = service.sendGuestInvitation(request);

        Assert.assertEquals("First name", response.getFirstName());
        Assert.assertEquals("Last name", response.getLastName());
        Assert.assertEquals("qwe@qwe.qwe", response.getEmail());
        Assert.assertEquals("My form name", response.getForms().get(0).getName());
        Assert.assertEquals(VisitorInfoGetter.getManageRegistrantsInternational().get(VisitorStatus.PENDING + "_STATUS"),
                response.getStatus());
        Assert.assertEquals(true, response.isInvited());

        final MockMailSender mockMailSender = (MockMailSender) ServiceLocator.getMailSender();
        Assert.assertEquals("qwe@qwe.qwe", mockMailSender.getMails().get(0).getTo());

        User invitedUser = ServiceLocator.getPersistance().getUserById(response.getVisitorId());
        Assert.assertNotNull(invitedUser);

        FilledForm filledForm = FilledFormManager.getFirstRegistrationFilledFormForSite(invitedUser, site);
        Assert.assertNotNull(filledForm);
        Assert.assertEquals("qwe@qwe.qwe", FilledFormManager.getFilledFormItemValueByItemName(filledForm, FilledFormManager.getFormInternational().get(FormItemName.EMAIL + "_FN")));
        Assert.assertEquals("First name", FilledFormManager.getFilledFormItemValueByItemName(filledForm, FilledFormManager.getFormInternational().get(FormItemName.FIRST_NAME + "_FN")));
        Assert.assertEquals("Last name", FilledFormManager.getFilledFormItemValueByItemName(filledForm, FilledFormManager.getFormInternational().get(FormItemName.LAST_NAME + "_FN")));

        UserOnSiteRight visitorOnSiteRight = ServiceLocator.getPersistance().getUserOnSiteRightById(new UserOnSiteRightId(invitedUser, site));

        Assert.assertNotNull(visitorOnSiteRight);
        Assert.assertEquals(VisitorStatus.PENDING, visitorOnSiteRight.getVisitorStatus());
        Assert.assertEquals(filledForm, ServiceLocator.getPersistance().getFilledFormById(
                visitorOnSiteRight.getFilledRegistrationFormIds().get(0)));

        //SECOND INVITATION

        final Site site2 = TestUtil.createSite();
        site2.setSubDomain("a2");

        request = new GuestInvitationRequest();
        request.setSiteId(site2.getSiteId());
        request.setFormId(registrationForm.getFormId());
        request.setInvitationText("Hi there :)");
        request.setEmail("qwe@qwe.qwe");
        request.setFirstName("First name");
        request.setLastName("Last name");

        response = service.sendGuestInvitation(request);

        Assert.assertEquals("First name", response.getFirstName());
        Assert.assertEquals("Last name", response.getLastName());
        Assert.assertEquals("qwe@qwe.qwe", response.getEmail());
        Assert.assertEquals("My form name", response.getForms().get(0).getName());
        Assert.assertEquals(VisitorInfoGetter.getManageRegistrantsInternational().get(VisitorStatus.PENDING + "_STATUS"),
                response.getStatus());
        Assert.assertEquals(true, response.isInvited());

        Assert.assertEquals("qwe@qwe.qwe", mockMailSender.getMails().get(0).getTo());

        invitedUser = ServiceLocator.getPersistance().getUserById(response.getVisitorId());
        Assert.assertNotNull(invitedUser);

        filledForm = FilledFormManager.getFirstRegistrationFilledFormForSite(invitedUser, site);
        Assert.assertNotNull(filledForm);
        Assert.assertEquals("qwe@qwe.qwe", FilledFormManager.getFilledFormItemValueByItemName(filledForm, FilledFormManager.getFormInternational().get(FormItemName.EMAIL + "_FN")));
        Assert.assertEquals("First name", FilledFormManager.getFilledFormItemValueByItemName(filledForm, FilledFormManager.getFormInternational().get(FormItemName.FIRST_NAME + "_FN")));
        Assert.assertEquals("Last name", FilledFormManager.getFilledFormItemValueByItemName(filledForm, FilledFormManager.getFormInternational().get(FormItemName.LAST_NAME + "_FN")));

        visitorOnSiteRight = ServiceLocator.getPersistance().getUserOnSiteRightById(new UserOnSiteRightId(invitedUser, site));

        Assert.assertNotNull(visitorOnSiteRight);
        Assert.assertEquals(VisitorStatus.PENDING, visitorOnSiteRight.getVisitorStatus());
        Assert.assertEquals(filledForm, ServiceLocator.getPersistance().getFilledFormById(
                visitorOnSiteRight.getFilledRegistrationFormIds().get(0)));
    }

    @Test(expected = UserNotLoginedException.class)
    public void sendGuestInvitationWithoutLoginedUser() throws ServletException, IOException {
        service.sendGuestInvitation(null);
    }

    @Test(expected = IncorrectEmailException.class)
    public void sendGuestInvitationWithIncorrectEmail() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        site.setSubDomain("a");
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm(site.getSiteId(), "My form name");

        final GuestInvitationRequest request = new GuestInvitationRequest();
        request.setSiteId(site.getSiteId());
        request.setFormId(registrationForm.getFormId());
        request.setInvitationText("Hi there :)");
        request.setEmail("qwe");
        request.setFirstName("First name");
        request.setLastName("Last name");

        service.sendGuestInvitation(request);
    }

    @Test(expected = VisitorWithNotUniqueLogin.class)
    public void sendGuestInvitationWithNotUniqueEmail() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        site.setSubDomain("a");
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.GUEST);
        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm(site.getSiteId(), "My form name");
        final User user = TestUtil.createUserAndUserOnSiteRight(site);
        user.setEmail("qwe@qwe.qwe");

        final GuestInvitationRequest request = new GuestInvitationRequest();
        request.setSiteId(site.getSiteId());
        request.setFormId(registrationForm.getFormId());
        request.setInvitationText("Hi there :)");
        request.setEmail("qwe@qwe.qwe");
        request.setFirstName("First name");
        request.setLastName("Last name");

        service.sendGuestInvitation(request);
    }

    @Test(expected = VisitorWithNotUniqueLogin.class)
    public void sendGuestInvitationWithUserNotUniqueEmail() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        site.setSubDomain("a");
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm(site.getSiteId(), "My form name");
        final User user = TestUtil.createUserAndUserOnSiteRight(site);
        user.setEmail("qwe@qwe.qwe");

        final GuestInvitationRequest request = new GuestInvitationRequest();
        request.setSiteId(site.getSiteId());
        request.setFormId(registrationForm.getFormId());
        request.setInvitationText("Hi there :)");
        request.setEmail("qwe@qwe.qwe");
        request.setFirstName("First name");
        request.setLastName("Last name");

        service.sendGuestInvitation(request);
    }

    @Test(expected = RegistrationFormNotFoundException.class)
    public void sendGuestInvitationWithotRegistrationForm() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        site.setSubDomain("a");
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final GuestInvitationRequest request = new GuestInvitationRequest();
        request.setSiteId(site.getSiteId());
        request.setFormId(0);
        request.setInvitationText("Hi there :)");
        request.setEmail("qwe@qwe.qwe");
        request.setFirstName("First name");
        request.setLastName("Last name");

        service.sendGuestInvitation(request);
    }

    @Test(expected = SiteNotFoundException.class)
    public void sendGuestInvitationWithoutSite() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        site.setSubDomain("a");
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm(site.getSiteId(), "My form name");

        final GuestInvitationRequest request = new GuestInvitationRequest();
        request.setFormId(registrationForm.getFormId());
        request.setInvitationText("Hi there :)");
        request.setEmail("qwe@qwe.qwe");
        request.setFirstName("First name");
        request.setLastName("Last name");

        service.sendGuestInvitation(request);
    }

    private final InviteGuestService service = new InviteGuestService();

}
