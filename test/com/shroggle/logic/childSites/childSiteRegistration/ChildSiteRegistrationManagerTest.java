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
package com.shroggle.logic.childSites.childSiteRegistration;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.ChildSiteRegistrationNotFoundException;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class ChildSiteRegistrationManagerTest {

    @Test
    public void testCreateDefault() throws Exception {
        ServiceLocator.getConfigStorage().get().setApplicationUrl("testApplicationUrl");
        final Site site = TestUtil.createSite();
        final DraftChildSiteRegistration childSiteRegistration = ChildSiteRegistrationManager.createDefaultChildSiteRegistration(site.getSiteId());

        Assert.assertEquals("Child Site Registration1", childSiteRegistration.getName());
        Assert.assertEquals(childSiteRegistration.getName(), childSiteRegistration.getFooterText());
        Assert.assertEquals(ServiceLocator.getConfigStorage().get().getApplicationUrl(), childSiteRegistration.getFooterUrl());
    }

    @Test
    public void testGetNormalizedThanksForRegisteringText_withSavedText() throws Exception {
        final Site site = TestUtil.createSite();

        final DraftChildSiteRegistration childSiteRegistration = ChildSiteRegistrationManager.createDefaultChildSiteRegistration(site.getSiteId());
        childSiteRegistration.setName("childSiteRegistrationName");

        Assert.assertEquals("Thank you for registering with childSiteRegistrationName.<br><br>You have one more step to set up your account.<br><br>We have just sent you an `activation email` (to the email address that you provided). To complete your registration you will need to open the email we just sent you, and click on the `account activation link` link in the email.",
                new ChildSiteRegistrationManager(childSiteRegistration).getNormalizedThanksForRegisteringText());
    }


    @Test
    public void testGetNormalizedThanksForRegisteringText_withoutSavedText() throws Exception {
        final Site site = TestUtil.createSite();

        final DraftChildSiteRegistration childSiteRegistration = ChildSiteRegistrationManager.createDefaultChildSiteRegistration(site.getSiteId());
        childSiteRegistration.setThanksForRegisteringText(null);
        childSiteRegistration.setName("childSiteRegistrationName");

        Assert.assertEquals("Thank you for registering with childSiteRegistrationName.<br><br>You have one more step to set up your account.<br><br>We have just sent you an `activation email` (to the email address that you provided). To complete your registration you will need to open the email we just sent you, and click on the `account activation link` link in the email.",
                new ChildSiteRegistrationManager(childSiteRegistration).getNormalizedThanksForRegisteringText());
    }


    @Test
    public void testGetSavedThanksForRegisteringTextOrDefault_withSavedText() throws Exception {
        final Site site = TestUtil.createSite();

        final DraftChildSiteRegistration childSiteRegistration = ChildSiteRegistrationManager.createDefaultChildSiteRegistration(site.getSiteId());
        childSiteRegistration.setThanksForRegisteringText("aaaaaaaaaaaa");
        childSiteRegistration.setName("childSiteRegistrationName");

        Assert.assertEquals("aaaaaaaaaaaa",
                new ChildSiteRegistrationManager(childSiteRegistration).getSavedThanksForRegisteringTextOrDefault());
    }


    @Test
    public void testGetSavedThanksForRegisteringTextOrDefault_withoutSavedText() throws Exception {
        final Site site = TestUtil.createSite();

        final DraftChildSiteRegistration childSiteRegistration = ChildSiteRegistrationManager.createDefaultChildSiteRegistration(site.getSiteId());
        childSiteRegistration.setThanksForRegisteringText(null);
        childSiteRegistration.setName("childSiteRegistrationName");

        Assert.assertEquals("Thank you for registering with &lt;network site name&gt;.<br><br>You have one more step to set up your account.<br><br>We have just sent you an `activation email` (to the email address that you provided). To complete your registration you will need to open the email we just sent you, and click on the `account activation link` link in the email.",
                new ChildSiteRegistrationManager(childSiteRegistration).getSavedThanksForRegisteringTextOrDefault());
    }

    @Test
    public void CreateDefaultChildSiteRegistration() {
        Site site = TestUtil.createSite();

        DraftChildSiteRegistration childSiteRegistration = ChildSiteRegistrationManager.createDefaultChildSiteRegistration(site.getSiteId());


        Assert.assertNotNull(childSiteRegistration);
        Assert.assertEquals(-1, childSiteRegistration.getFormId());
        Assert.assertEquals(0, childSiteRegistration.getFormItems().size());
        Assert.assertEquals("default terms and conditions to follow", childSiteRegistration.getTermsAndConditions());
        Assert.assertEquals(
                "Dear &lt;first name&gt; &lt;last name&gt;,<br><br>Thank you for registering with &lt;site name (where child site reg is displayed)&gt;.<br><br>Your registration gives you more than just your &lt;network site name&gt; membership. Part of your member`s benefits includes the potential to create `your own website`. &lt;network site name&gt; and our wonderful technology provider Web-Deva.com, have teamed up to provide you with access to a great tool for building and editing your site. Its really easy to use and gives great professional results.<br><br>(if applicable) Start Date: &lt;start date or today`s date&gt;<br>End Date: &lt;end date specified in network settings.&gt;<br>Fees: $&lt;fee for membership at network price&gt; &lt;per month/one time fee&gt; (250mb media storage included)<br><br>The following link will take you to a confirmation and welcome message on the Web-Deva site.<br><br>To verify your email click here &lt;account activation link&gt;<br><br>This service is powered by Web-Deva.com - a great web based tool for building sites and networks of sites. Once you have activated your account, you will be invited to build your web pages using Web-Deva`s `push button web site builder`; and templates created by &lt;Name of Membership option&gt;.  Its easy, quick, and fun.<br><br>Thanks for registering, &lt;network site name&gt;",
                childSiteRegistration.getEmailText());
        Assert.assertEquals("Thank you for registering with &lt;network site name&gt;. Your registration gives you more than just your &lt;network site name&gt; membership. Part of your member`s benefits includes the potential to create `your own website`. &lt;network site name&gt; and our wonderful technology provider Web-Deva.com, have teamed up to provide you with access to a great tool for building and editing your site. Its really easy to use and gives great professional results.",
                childSiteRegistration.getWelcomeText());
        Assert.assertEquals("Thank you for registering with &lt;network site name&gt;.<br><br>You have one more step to set up your account.<br><br>We have just sent you an `activation email` (to the email address that you provided). To complete your registration you will need to open the email we just sent you, and click on the `account activation link` link in the email.",
                childSiteRegistration.getThanksForRegisteringText());

        Assert.assertEquals(false, childSiteRegistration.isModified());

        Assert.assertEquals(15.0, childSiteRegistration.getPrice250mb());
        Assert.assertEquals(18.0, childSiteRegistration.getPrice500mb());
        Assert.assertEquals(20.0, childSiteRegistration.getPrice1gb());
        Assert.assertEquals(25.0, childSiteRegistration.getPrice3gb());
    }

    @Test(expected = ChildSiteRegistrationNotFoundException.class)
    public void testCreate_withoutChildSiteRegistration() {
        new ChildSiteRegistrationManager(null);
    }


    @Test
    public void testGetEmailTextOrDefault_WithEmail() {
        DraftChildSiteRegistration registration = new DraftChildSiteRegistration();
        registration.setEmailText("email text");
        ChildSiteRegistrationManager manager = new ChildSiteRegistrationManager(registration);
        Assert.assertEquals("email text", manager.getSavedEmailTextOrDefault());
    }

    @Test
    public void testGetEmailTextOrDefault_WithoutEmail() {
        DraftChildSiteRegistration registration = new DraftChildSiteRegistration();
        registration.setEmailText(null);
        ChildSiteRegistrationManager manager = new ChildSiteRegistrationManager(registration);
        Assert.assertEquals(
                "Dear &lt;first name&gt; &lt;last name&gt;,<br><br>Thank you for registering with &lt;site name (where child site reg is displayed)&gt;.<br><br>Your registration gives you more than just your &lt;network site name&gt; membership. Part of your member`s benefits includes the potential to create `your own website`. &lt;network site name&gt; and our wonderful technology provider Web-Deva.com, have teamed up to provide you with access to a great tool for building and editing your site. Its really easy to use and gives great professional results.<br><br>(if applicable) Start Date: &lt;start date or today`s date&gt;<br>End Date: &lt;end date specified in network settings.&gt;<br>Fees: $&lt;fee for membership at network price&gt; &lt;per month/one time fee&gt; (250mb media storage included)<br><br>The following link will take you to a confirmation and welcome message on the Web-Deva site.<br><br>To verify your email click here &lt;account activation link&gt;<br><br>This service is powered by Web-Deva.com - a great web based tool for building sites and networks of sites. Once you have activated your account, you will be invited to build your web pages using Web-Deva`s `push button web site builder`; and templates created by &lt;Name of Membership option&gt;.  Its easy, quick, and fun.<br><br>Thanks for registering, &lt;network site name&gt;",
                manager.getSavedEmailTextOrDefault());
    }


    @Test
    public void testGetSavedTermsAndConditionsTextOrDefault_WithEmail() {
        DraftChildSiteRegistration registration = new DraftChildSiteRegistration();
        registration.setTermsAndConditions("terms");
        ChildSiteRegistrationManager manager = new ChildSiteRegistrationManager(registration);
        Assert.assertEquals("terms", manager.getSavedTermsAndConditionsTextOrDefault());
    }

    @Test
    public void testGetSavedTermsAndConditionsTextOrDefault_WithoutEmail() {
        DraftChildSiteRegistration registration = new DraftChildSiteRegistration();
        registration.setTermsAndConditions(null);
        ChildSiteRegistrationManager manager = new ChildSiteRegistrationManager(registration);
        Assert.assertEquals(
                "default terms and conditions to follow",
                manager.getSavedTermsAndConditionsTextOrDefault());
    }

    @Test
    public void testGetSavedWelcomeEmailTextOrDefault_WithEmail() {
        DraftChildSiteRegistration registration = new DraftChildSiteRegistration();
        registration.setWelcomeText("WelcomeText");
        ChildSiteRegistrationManager manager = new ChildSiteRegistrationManager(registration);
        Assert.assertEquals("WelcomeText", manager.getSavedWelcomeTextOrDefault());
    }

    @Test
    public void testGetSavedWelcomeEmailTextOrDefault_WithoutEmail() {
        DraftChildSiteRegistration registration = new DraftChildSiteRegistration();
        registration.setWelcomeText(null);
        ChildSiteRegistrationManager manager = new ChildSiteRegistrationManager(registration);
        Assert.assertEquals(
                "Thank you for registering with &lt;network site name&gt;. Your registration gives you more than just your &lt;network site name&gt; membership. Part of your member`s benefits includes the potential to create `your own website`. &lt;network site name&gt; and our wonderful technology provider Web-Deva.com, have teamed up to provide you with access to a great tool for building and editing your site. Its really easy to use and gives great professional results.",
                manager.getSavedWelcomeTextOrDefault());
    }

    @Test
    public void createEmailBodyByCustomEmailText() {
        final String customEmailBody = international.get("customizeEmailTextBody");
        final String emailBody = ChildSiteRegistrationManager.createEmailBodyByCustomEmailText(
                customEmailBody, "firstName", "lastName", "parentSiteName", "01/01/2001", "01/01/2010", "10.0",
                "childSiteRegistrationName", "http://www.shroggle.com/account/registrationConfirmation.action?settingsId=1&confirmCode=asdfasdfasdfasdfasdf", false);


        Assert.assertEquals("Dear firstName lastName,<br><br>Thank you for registering with parentSiteName.<br><br>Your registration gives you more than just your parentSiteName membership. Part of your member`s benefits includes the potential to create `your own website`. parentSiteName and our wonderful technology provider Web-Deva.com, have teamed up to provide you with access to a great tool for building and editing your site. Its really easy to use and gives great professional results.<br><br> Start Date: 01/01/2001<br>End Date: 01/01/2010<br>Fees: $10.0 per month (250mb media storage included)<br><br>The following link will take you to a confirmation and welcome message on the Web-Deva site.<br><br>To verify your email click here <a href='http://www.shroggle.com/account/registrationConfirmation.action?settingsId=1&confirmCode=asdfasdfasdfasdfasdf'>account activation link</a><br><br>This service is powered by Web-Deva.com - a great web based tool for building sites and networks of sites. Once you have activated your account, you will be invited to build your web pages using Web-Deva`s `push button web site builder`; and templates created by childSiteRegistrationName.  Its easy, quick, and fun.<br><br>Thanks for registering, parentSiteName",
                emailBody);
    }

    @Test
    public void createEmailBodyByCustomEmailText_useOneTimeFee() {
        final String customEmailBody = international.get("customizeEmailTextBody");
        final String emailBody = ChildSiteRegistrationManager.createEmailBodyByCustomEmailText(
                customEmailBody, "firstName", "lastName", "parentSiteName", "01/01/2001", "01/01/2010", "10.0",
                "childSiteRegistrationName", "http://www.shroggle.com/account/registrationConfirmation.action?settingsId=1&confirmCode=asdfasdfasdfasdfasdf", true);


        Assert.assertEquals("Dear firstName lastName,<br><br>Thank you for registering with parentSiteName.<br><br>Your registration gives you more than just your parentSiteName membership. Part of your member`s benefits includes the potential to create `your own website`. parentSiteName and our wonderful technology provider Web-Deva.com, have teamed up to provide you with access to a great tool for building and editing your site. Its really easy to use and gives great professional results.<br><br> Start Date: 01/01/2001<br>End Date: 01/01/2010<br>Fees: $10.0 one time fee<br><br>The following link will take you to a confirmation and welcome message on the Web-Deva site.<br><br>To verify your email click here <a href='http://www.shroggle.com/account/registrationConfirmation.action?settingsId=1&confirmCode=asdfasdfasdfasdfasdf'>account activation link</a><br><br>This service is powered by Web-Deva.com - a great web based tool for building sites and networks of sites. Once you have activated your account, you will be invited to build your web pages using Web-Deva`s `push button web site builder`; and templates created by childSiteRegistrationName.  Its easy, quick, and fun.<br><br>Thanks for registering, parentSiteName",
                emailBody);
    }

    @Test
    public void createEmailBodyByCustomEmailText_withoutStartDate() {
        final String customEmailBody = international.get("customizeEmailTextBody");
        final String emailBody = ChildSiteRegistrationManager.createEmailBodyByCustomEmailText(
                customEmailBody, "firstName", "lastName", "parentSiteName", "", "01/01/2010", "10.0",
                "childSiteRegistrationName", "http://www.shroggle.com/account/registrationConfirmation.action?settingsId=1&confirmCode=asdfasdfasdfasdfasdf", false);


        Assert.assertEquals("Dear firstName lastName,<br><br>Thank you for registering with parentSiteName.<br><br>Your registration gives you more than just your parentSiteName membership. Part of your member`s benefits includes the potential to create `your own website`. parentSiteName and our wonderful technology provider Web-Deva.com, have teamed up to provide you with access to a great tool for building and editing your site. Its really easy to use and gives great professional results.<br><br>End Date: 01/01/2010<br>Fees: $10.0 per month (250mb media storage included)<br><br>The following link will take you to a confirmation and welcome message on the Web-Deva site.<br><br>To verify your email click here <a href='http://www.shroggle.com/account/registrationConfirmation.action?settingsId=1&confirmCode=asdfasdfasdfasdfasdf'>account activation link</a><br><br>This service is powered by Web-Deva.com - a great web based tool for building sites and networks of sites. Once you have activated your account, you will be invited to build your web pages using Web-Deva`s `push button web site builder`; and templates created by childSiteRegistrationName.  Its easy, quick, and fun.<br><br>Thanks for registering, parentSiteName",
                emailBody);
    }

    @Test
    public void createEmailBodyByCustomEmailText_withoutEndDate() {
        final String customEmailBody = international.get("customizeEmailTextBody");
        final String emailBody = ChildSiteRegistrationManager.createEmailBodyByCustomEmailText(
                customEmailBody, "firstName", "lastName", "parentSiteName", "01/01/2001", "", "10.0",
                "childSiteRegistrationName", "http://www.shroggle.com/account/registrationConfirmation.action?settingsId=1&confirmCode=asdfasdfasdfasdfasdf", false);


        Assert.assertEquals("Dear firstName lastName,<br><br>Thank you for registering with parentSiteName.<br><br>Your registration gives you more than just your parentSiteName membership. Part of your member`s benefits includes the potential to create `your own website`. parentSiteName and our wonderful technology provider Web-Deva.com, have teamed up to provide you with access to a great tool for building and editing your site. Its really easy to use and gives great professional results.<br><br> Start Date: 01/01/2001<br>Fees: $10.0 per month (250mb media storage included)<br><br>The following link will take you to a confirmation and welcome message on the Web-Deva site.<br><br>To verify your email click here <a href='http://www.shroggle.com/account/registrationConfirmation.action?settingsId=1&confirmCode=asdfasdfasdfasdfasdf'>account activation link</a><br><br>This service is powered by Web-Deva.com - a great web based tool for building sites and networks of sites. Once you have activated your account, you will be invited to build your web pages using Web-Deva`s `push button web site builder`; and templates created by childSiteRegistrationName.  Its easy, quick, and fun.<br><br>Thanks for registering, parentSiteName",
                emailBody);
    }

    @Test
    public void createEmailBodyByCustomEmailText_withoutStartEndDate() {
        final String customEmailBody = international.get("customizeEmailTextBody");
        final String emailBody = ChildSiteRegistrationManager.createEmailBodyByCustomEmailText(
                customEmailBody, "firstName", "lastName", "parentSiteName", "", "", "10.0",
                "childSiteRegistrationName", "http://www.shroggle.com/account/registrationConfirmation.action?settingsId=1&confirmCode=asdfasdfasdfasdfasdf", false);


        Assert.assertEquals("Dear firstName lastName,<br><br>Thank you for registering with parentSiteName.<br><br>Your registration gives you more than just your parentSiteName membership. Part of your member`s benefits includes the potential to create `your own website`. parentSiteName and our wonderful technology provider Web-Deva.com, have teamed up to provide you with access to a great tool for building and editing your site. Its really easy to use and gives great professional results.<br><br>Fees: $10.0 per month (250mb media storage included)<br><br>The following link will take you to a confirmation and welcome message on the Web-Deva site.<br><br>To verify your email click here <a href='http://www.shroggle.com/account/registrationConfirmation.action?settingsId=1&confirmCode=asdfasdfasdfasdfasdf'>account activation link</a><br><br>This service is powered by Web-Deva.com - a great web based tool for building sites and networks of sites. Once you have activated your account, you will be invited to build your web pages using Web-Deva`s `push button web site builder`; and templates created by childSiteRegistrationName.  Its easy, quick, and fun.<br><br>Thanks for registering, parentSiteName",
                emailBody);
    }

    @Test
    public void createEmailBodyByCustomEmailText_withoutLinkTag() {
        final String customEmailBody = international.get("customizeEmailTextBody").replace("&lt;account activation link&gt;", "");
        final String emailBody = ChildSiteRegistrationManager.createEmailBodyByCustomEmailText(
                customEmailBody, "firstName", "lastName", "parentSiteName", "01/01/2001", "01/01/2010", "10.0",
                "childSiteRegistrationName", "http://www.shroggle.com/account/registrationConfirmation.action?settingsId=1&confirmCode=asdfasdfasdfasdfasdf", false);


        Assert.assertEquals("Dear firstName lastName,<br><br>Thank you for registering with parentSiteName.<br><br>Your registration gives you more than just your parentSiteName membership. Part of your member`s benefits includes the potential to create `your own website`. parentSiteName and our wonderful technology provider Web-Deva.com, have teamed up to provide you with access to a great tool for building and editing your site. Its really easy to use and gives great professional results.<br><br> Start Date: 01/01/2001<br>End Date: 01/01/2010<br>Fees: $10.0 per month (250mb media storage included)<br><br>The following link will take you to a confirmation and welcome message on the Web-Deva site.<br><br>To verify your email click here <br><br>This service is powered by Web-Deva.com - a great web based tool for building sites and networks of sites. Once you have activated your account, you will be invited to build your web pages using Web-Deva`s `push button web site builder`; and templates created by childSiteRegistrationName.  Its easy, quick, and fun.<br><br>Thanks for registering, parentSiteName<br><br>The following link will take you to confirmation and welcome message page of the site builder.<br><br> To verify your email click here <a href='http://www.shroggle.com/account/registrationConfirmation.action?settingsId=1&confirmCode=asdfasdfasdfasdfasdf'>account activation link</a><br><br>",
                emailBody);
    }

    @Test
    public void createEmailBodyByCustomEmailText_withCustomText() {
        final String customEmailBody = "custom email text";
        final String emailBody = ChildSiteRegistrationManager.createEmailBodyByCustomEmailText(
                customEmailBody, "firstName", "lastName", "parentSiteName", "01/01/2001", "01/01/2010", "10.0",
                "childSiteRegistrationName", "http://www.shroggle.com/account/registrationConfirmation.action?settingsId=1&confirmCode=asdfasdfasdfasdfasdf", false);


        Assert.assertEquals("custom email text<br><br>The following link will take you to confirmation and welcome message page of the site builder.<br><br> To verify your email click here <a href='http://www.shroggle.com/account/registrationConfirmation.action?settingsId=1&confirmCode=asdfasdfasdfasdfasdf'>account activation link</a><br><br>",
                emailBody);
    }

    @Test
    public void createEmailBodyByCustomEmailText_withCustomTextAndLinkTag() {
        final String customEmailBody = "custom email text &lt;account activation link&gt;";
        final String emailBody = ChildSiteRegistrationManager.createEmailBodyByCustomEmailText(
                customEmailBody, "firstName", "lastName", "parentSiteName", "01/01/2001", "01/01/2010", "10.0",
                "childSiteRegistrationName", "http://www.shroggle.com/account/registrationConfirmation.action?settingsId=1&confirmCode=asdfasdfasdfasdfasdf", false);


        Assert.assertEquals("custom email text <a href='http://www.shroggle.com/account/registrationConfirmation.action?settingsId=1&confirmCode=asdfasdfasdfasdfasdf'>account activation link</a>",
                emailBody);
    }

    @Test
    public void showFormsForEdit_withoutLoginedUser() {
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(site);
        Widget widget = TestUtil.createWidgetChildSiteRegistration(registration.getFormId());
        final boolean showFormsForEdit = ChildSiteRegistrationManager.showFormsForEdit(widget, true);
        Assert.assertFalse(showFormsForEdit);
    }


    @Test
    public void showFormsForEdit_withoutForceFormShowing() {
        TestUtil.createUserAndLogin("email");
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(site);
        Widget widget = TestUtil.createWidgetChildSiteRegistration(registration.getFormId());
        final boolean showFormsForEdit = ChildSiteRegistrationManager.showFormsForEdit(widget, false);
        Assert.assertFalse(showFormsForEdit);
    }


    @Test
    public void showFormsForEdit_withoutFormInWidget() {
        TestUtil.createUserAndLogin("email");
        Widget widget = TestUtil.createWidgetChildSiteRegistration(null);
        final boolean showFormsForEdit = ChildSiteRegistrationManager.showFormsForEdit(widget, false);
        Assert.assertFalse(showFormsForEdit);
    }

    @Test
    public void showFormsForEdit_withoutFIlledForms() {
        TestUtil.createUserAndLogin("email");
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(site);
        Widget widget = TestUtil.createWidgetChildSiteRegistration(registration.getFormId());
        final boolean showFormsForEdit = ChildSiteRegistrationManager.showFormsForEdit(widget, false);
        Assert.assertFalse(showFormsForEdit);
    }

    @Test
    public void showFormsForEdit_withoutUserInFilledForm() {
        TestUtil.createUserAndLogin("email");
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(site);
        Widget widget = TestUtil.createWidgetChildSiteRegistration(registration.getFormId());
        TestUtil.createFilledForm(registration.getFormId());

        final boolean showFormsForEdit = ChildSiteRegistrationManager.showFormsForEdit(widget, false);
        Assert.assertFalse(showFormsForEdit);
    }

    @Test
    public void showFormsForEdit() {
        User user = TestUtil.createUserAndLogin("email");
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(site);
        Widget widget = TestUtil.createWidgetChildSiteRegistration(registration.getFormId());

        FilledForm filledForm = TestUtil.createFilledForm(registration.getFormId());
        filledForm.setUser(user);
        final boolean showFormsForEdit = ChildSiteRegistrationManager.showFormsForEdit(widget, false);
        Assert.assertTrue(showFormsForEdit);
    }


    @Test
    public void showYouHaveNotFilledThisFormMessage() {
        TestUtil.createUserAndLogin("email");
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(site);
        Widget widget = TestUtil.createWidgetChildSiteRegistration(registration.getFormId());

        final boolean showYouHaveNotFilledThisFormMessage =
                ChildSiteRegistrationManager.showYouHaveNotFilledThisFormMessage(widget.getWidgetId());

        Assert.assertTrue(showYouHaveNotFilledThisFormMessage);
    }

    @Test
    public void showYouHaveNotFilledThisFormMessage_withFilledForms() {
        User user = TestUtil.createUserAndLogin("email");
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(site);
        Widget widget = TestUtil.createWidgetChildSiteRegistration(registration.getFormId());

        FilledForm filledForm = TestUtil.createFilledForm(registration.getFormId());
        filledForm.setUser(user);

        final boolean showYouHaveNotFilledThisFormMessage =
                ChildSiteRegistrationManager.showYouHaveNotFilledThisFormMessage(widget.getWidgetId());

        Assert.assertFalse(showYouHaveNotFilledThisFormMessage);
    }

    @Test
    public void showYouHaveNotFilledThisFormMessage_withoutFormIdInWidget() {
        User user = TestUtil.createUserAndLogin("email");
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(site);
        Widget widget = TestUtil.createWidgetChildSiteRegistration(null);

        FilledForm filledForm = TestUtil.createFilledForm(registration.getFormId());
        filledForm.setUser(user);

        final boolean showYouHaveNotFilledThisFormMessage =
                ChildSiteRegistrationManager.showYouHaveNotFilledThisFormMessage(widget.getWidgetId());

        Assert.assertFalse(showYouHaveNotFilledThisFormMessage);
    }

    @Test
    public void showYouHaveNotFilledThisFormMessage_withoutForm() {
        TestUtil.createUserAndLogin("email");

        Widget widget = TestUtil.createWidgetChildSiteRegistration(-1);

        final boolean showYouHaveNotFilledThisFormMessage =
                ChildSiteRegistrationManager.showYouHaveNotFilledThisFormMessage(widget.getWidgetId());

        Assert.assertFalse(showYouHaveNotFilledThisFormMessage);
    }


    @Test
    public void showYouHaveNotFilledThisFormMessage_withoutLoginedUser() {
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(site);
        Widget widget = TestUtil.createWidgetChildSiteRegistration(registration.getFormId());

        final boolean showYouHaveNotFilledThisFormMessage =
                ChildSiteRegistrationManager.showYouHaveNotFilledThisFormMessage(widget.getWidgetId());

        Assert.assertFalse(showYouHaveNotFilledThisFormMessage);
    }

    @Test
    public void showYouHaveNotFilledThisFormMessage_withoutWidget() {
        TestUtil.createUserAndLogin("email");

        final boolean showYouHaveNotFilledThisFormMessage =
                ChildSiteRegistrationManager.showYouHaveNotFilledThisFormMessage(null);

        Assert.assertFalse(showYouHaveNotFilledThisFormMessage);
    }

    @Test
    public void testRemoveWrongBlueprintsId_withoutWrongIds() throws Exception {
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), new Site());
        final DraftChildSiteRegistration registration = childSiteSettings.getChildSiteRegistration();
        final Site blueprint1 = TestUtil.createBlueprint();
        final Site blueprint2 = TestUtil.createBlueprint();
        registration.setBlueprintsId(Arrays.asList(blueprint1.getId(), blueprint2.getId()));
        Assert.assertEquals(2, registration.getBlueprintsId().size());


        final ChildSiteRegistrationManager manager = new ChildSiteRegistrationManager(childSiteSettings.getChildSiteRegistration());
        manager.removeWrongBlueprintsId();


        Assert.assertEquals(2, registration.getBlueprintsId().size());
        Assert.assertEquals(true, registration.getBlueprintsId().contains(blueprint1.getId()));
        Assert.assertEquals(true, registration.getBlueprintsId().contains(blueprint2.getId()));
    }

    @Test
    public void testRemoveWrongBlueprintsId_withOneCommonSite() throws Exception {
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), new Site());
        final DraftChildSiteRegistration registration = childSiteSettings.getChildSiteRegistration();
        final Site blueprint1 = TestUtil.createBlueprint();
        final Site blueprint2 = TestUtil.createSite();
        registration.setBlueprintsId(Arrays.asList(blueprint1.getId(), blueprint2.getId()));
        Assert.assertEquals(2, registration.getBlueprintsId().size());


        final ChildSiteRegistrationManager manager = new ChildSiteRegistrationManager(childSiteSettings.getChildSiteRegistration());
        manager.removeWrongBlueprintsId();


        Assert.assertEquals(1, registration.getBlueprintsId().size());
        Assert.assertEquals(true, registration.getBlueprintsId().contains(blueprint1.getId()));
        Assert.assertEquals(false, registration.getBlueprintsId().contains(blueprint2.getId()));
    }

    @Test
    public void testRemoveWrongBlueprintsId_withOneWrongId() throws Exception {
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), new Site());
        final DraftChildSiteRegistration registration = childSiteSettings.getChildSiteRegistration();
        final Site blueprint1 = TestUtil.createBlueprint();
        final int wrongSiteId = 123234235;
        registration.setBlueprintsId(Arrays.asList(blueprint1.getId(), wrongSiteId));
        Assert.assertEquals(2, registration.getBlueprintsId().size());


        final ChildSiteRegistrationManager manager = new ChildSiteRegistrationManager(childSiteSettings.getChildSiteRegistration());
        manager.removeWrongBlueprintsId();


        Assert.assertEquals(1, registration.getBlueprintsId().size());
        Assert.assertEquals(true, registration.getBlueprintsId().contains(blueprint1.getId()));
        Assert.assertEquals(false, registration.getBlueprintsId().contains(wrongSiteId));
    }

    @Test
    public void testGetDefaultBlueprint_withTwoBlueprints() throws Exception {
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), new Site());
        final DraftChildSiteRegistration registration = childSiteSettings.getChildSiteRegistration();
        final Site blueprint1 = TestUtil.createBlueprint();
        final Site blueprint2 = TestUtil.createBlueprint();
        registration.setBlueprintsId(Arrays.asList(blueprint1.getId(), blueprint2.getId()));
        Assert.assertEquals(2, registration.getBlueprintsId().size());


        final ChildSiteRegistrationManager manager = new ChildSiteRegistrationManager(childSiteSettings.getChildSiteRegistration());


        Assert.assertEquals(null, manager.getDefaultBlueprint());

        Assert.assertEquals(2, registration.getBlueprintsId().size());
        Assert.assertEquals(true, registration.getBlueprintsId().contains(blueprint1.getId()));
        Assert.assertEquals(true, registration.getBlueprintsId().contains(blueprint2.getId()));
    }

    @Test
    public void testGetDefaultBlueprint_withWrongFirstId() throws Exception {
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), new Site());
        final DraftChildSiteRegistration registration = childSiteSettings.getChildSiteRegistration();
        final int wrongSiteId = 123234235;
        final Site blueprint2 = TestUtil.createBlueprint();
        registration.setBlueprintsId(Arrays.asList(wrongSiteId, blueprint2.getId()));
        Assert.assertEquals(2, registration.getBlueprintsId().size());


        final ChildSiteRegistrationManager manager = new ChildSiteRegistrationManager(childSiteSettings.getChildSiteRegistration());


        Assert.assertEquals(blueprint2, manager.getDefaultBlueprint());

        Assert.assertEquals(1, registration.getBlueprintsId().size());
        Assert.assertEquals(false, registration.getBlueprintsId().contains(wrongSiteId));
        Assert.assertEquals(true, registration.getBlueprintsId().contains(blueprint2.getId()));
    }

    @Test
    public void testGetDefaultBlueprint_withCommonSiteOnFirstPosition() throws Exception {
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), new Site());
        final DraftChildSiteRegistration registration = childSiteSettings.getChildSiteRegistration();
        final Site blueprint1 = TestUtil.createSite();
        final Site blueprint2 = TestUtil.createBlueprint();
        registration.setBlueprintsId(Arrays.asList(blueprint1.getId(), blueprint2.getId()));
        Assert.assertEquals(2, registration.getBlueprintsId().size());


        final ChildSiteRegistrationManager manager = new ChildSiteRegistrationManager(childSiteSettings.getChildSiteRegistration());


        Assert.assertEquals(blueprint2, manager.getDefaultBlueprint());

        Assert.assertEquals(1, registration.getBlueprintsId().size());
        Assert.assertEquals(false, registration.getBlueprintsId().contains(blueprint1.getId()));
        Assert.assertEquals(true, registration.getBlueprintsId().contains(blueprint2.getId()));
    }

    @Test
    public void testGetUsedBlueprints() throws Exception {
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), new Site());
        final DraftChildSiteRegistration registration = childSiteSettings.getChildSiteRegistration();
        final Site blueprint1 = TestUtil.createBlueprint();
        final Site blueprint2 = TestUtil.createBlueprint();
        registration.setBlueprintsId(Arrays.asList(blueprint1.getId(), blueprint2.getId()));
        Assert.assertEquals(2, registration.getBlueprintsId().size());


        final ChildSiteRegistrationManager manager = new ChildSiteRegistrationManager(childSiteSettings.getChildSiteRegistration());
        final List<Site> blueprints = manager.getUsedBlueprints();


        Assert.assertEquals(2, blueprints.size());
        Assert.assertEquals(true, blueprints.contains(blueprint1));
        Assert.assertEquals(true, blueprints.contains(blueprint2));
    }

    @Test
    public void testGetUsedBlueprints_withWrongFirstId() throws Exception {
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), new Site());
        final DraftChildSiteRegistration registration = childSiteSettings.getChildSiteRegistration();
        final int wrongSiteId = 123234235;
        final Site blueprint2 = TestUtil.createBlueprint();
        registration.setBlueprintsId(Arrays.asList(wrongSiteId, blueprint2.getId()));
        Assert.assertEquals(2, registration.getBlueprintsId().size());


        final ChildSiteRegistrationManager manager = new ChildSiteRegistrationManager(childSiteSettings.getChildSiteRegistration());
        final List<Site> blueprints = manager.getUsedBlueprints();


        Assert.assertEquals(1, blueprints.size());
        Assert.assertEquals(true, blueprints.contains(blueprint2));
    }

    @Test
    public void testGetUsedBlueprints_withCommonSiteOnFirstPosition() throws Exception {
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), new Site());
        final DraftChildSiteRegistration registration = childSiteSettings.getChildSiteRegistration();
        final Site blueprint1 = TestUtil.createSite();
        final Site blueprint2 = TestUtil.createBlueprint();
        registration.setBlueprintsId(Arrays.asList(blueprint1.getId(), blueprint2.getId()));
        Assert.assertEquals(2, registration.getBlueprintsId().size());


        final ChildSiteRegistrationManager manager = new ChildSiteRegistrationManager(childSiteSettings.getChildSiteRegistration());
        final List<Site> blueprints = manager.getUsedBlueprints();


        Assert.assertEquals(1, blueprints.size());
        Assert.assertEquals(false, blueprints.contains(blueprint1));
        Assert.assertEquals(true, blueprints.contains(blueprint2));
    }

    @Test
    public void testGetId() throws Exception {
        final DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(TestUtil.createSite());

        Assert.assertEquals(registration.getId(), new ChildSiteRegistrationManager(registration).getId());
    }

    @Test
    public void testGetName() throws Exception {
        final DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(TestUtil.createSite());
        registration.setName("name");

        Assert.assertEquals(registration.getName(), new ChildSiteRegistrationManager(registration).getName());
    }

    @Test
    public void testGetName_withNull() throws Exception {
        final DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(TestUtil.createSite());
        registration.setName(null);

        Assert.assertEquals("", new ChildSiteRegistrationManager(registration).getName());
    }

    @Test
    public void testGetFromEmail() throws Exception {
        final DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(TestUtil.createSite());
        registration.setFromEmail("fromEmail");

        Assert.assertEquals(registration.getFromEmail(), new ChildSiteRegistrationManager(registration).getFromEmail());
    }

    @Test
    public void testGetFromEmail_withNull() throws Exception {
        final DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(TestUtil.createSite());
        registration.setFromEmail(null);

        Assert.assertEquals("", new ChildSiteRegistrationManager(registration).getFromEmail());
    }

    @Test
    public void testGetBrandedUrl() throws Exception {
        final DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(TestUtil.createSite());
        registration.setBrandedUrl("BrandedUrl");

        Assert.assertEquals(registration.getBrandedUrl(), new ChildSiteRegistrationManager(registration).getBrandedUrl());
    }

    @Test
    public void testGetBrandedUrl_withNull() throws Exception {
        final DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(TestUtil.createSite());
        registration.setBrandedUrl(null);

        Assert.assertEquals("", new ChildSiteRegistrationManager(registration).getBrandedUrl());
    }

    @Test
    public void testGetFooterText() throws Exception {
        final DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(TestUtil.createSite());
        registration.setFooterText("FooterText");

        Assert.assertEquals(registration.getFooterText(), new ChildSiteRegistrationManager(registration).getFooterText());
    }

    @Test
    public void testGetFooterText_withEmpty() throws Exception {
        final DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(TestUtil.createSite());
        registration.setFooterText("");
        registration.setName("name");

        Assert.assertEquals("If footer text is empty - network name must be used as default value",
                registration.getName(), new ChildSiteRegistrationManager(registration).getFooterText());
    }

    @Test
    public void testGetFooterText_withNull() throws Exception {
        final DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(TestUtil.createSite());
        registration.setFooterText(null);
        registration.setName("name");

        Assert.assertEquals("If footer text not saved - network name must be used as default value",
                registration.getName(), new ChildSiteRegistrationManager(registration).getFooterText());
    }

    @Test
    public void testGetFooterUrl() throws Exception {
        final DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(TestUtil.createSite());
        registration.setFooterUrl("FooterUrl");

        Assert.assertEquals(registration.getFooterUrl(), new ChildSiteRegistrationManager(registration).getFooterUrl());
    }

    @Test
    public void testGetFooterUrl_withEmpty() throws Exception {
        ServiceLocator.getConfigStorage().get().setApplicationUrl("testApplicationUrl");
        final DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(TestUtil.createSite());
        registration.setFooterUrl("");

        Assert.assertEquals("If footer text is empty - application url must be used as default value",
                ServiceLocator.getConfigStorage().get().getApplicationUrl(), new ChildSiteRegistrationManager(registration).getFooterUrl());
    }

    @Test
    public void testGetFooterUrl_withNull() throws Exception {
        ServiceLocator.getConfigStorage().get().setApplicationUrl("testApplicationUrl");
        final DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(TestUtil.createSite());
        registration.setFooterUrl(null);

        Assert.assertEquals("If footer text not saved - application url must be used as default value",
                ServiceLocator.getConfigStorage().get().getApplicationUrl(), new ChildSiteRegistrationManager(registration).getFooterUrl());
    }

    @Test
    public void testIsBrandedAllowShroggleDomain() throws Exception {
        final DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(TestUtil.createSite());
        registration.setBrandedAllowShroggleDomain(true);

        Assert.assertEquals(registration.isBrandedAllowShroggleDomain(), new ChildSiteRegistrationManager(registration).isBrandedAllowShroggleDomain());
    }

    @Test
    public void testGetFooterImageId() throws Exception {
        final DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(TestUtil.createSite());
        registration.setFooterImageId(11);

        Assert.assertEquals(registration.getFooterImageId(), new ChildSiteRegistrationManager(registration).getFooterImageId());
    }

    @Test
    public void testGetContactUsPageId() throws Exception {
        final DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(TestUtil.createSite());
        registration.setContactUsPageId(11);

        Assert.assertEquals(registration.getContactUsPageId(), new ChildSiteRegistrationManager(registration).getContactUsPageId());
    }

    private final International international = ServiceLocator.getInternationStorage().get("configureChildSiteRegistration", Locale.US);

}
