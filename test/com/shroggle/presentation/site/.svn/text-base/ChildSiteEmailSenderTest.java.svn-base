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
import com.shroggle.util.DateUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.international.International;
import com.shroggle.util.mail.MockMailSender;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

/**
 * @author Balakirev Anatoliy
 *         Date: 05.08.2009
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class ChildSiteEmailSenderTest {

    @Before
    public void before(){
        ServiceLocator.getConfigStorage().get().setApplicationUrl("www.web-deva.com");
    }


    @Test
    public void testSendEmail() {
        final User user = TestUtil.createUser("email");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        final Site site = TestUtil.createSite();
        site.setTitle("siteTitle");

        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        final Date startEndDate = new Date();
        childSiteSettings.setEndDate(startEndDate);
        childSiteSettings.setStartDate(startEndDate);
        childSiteSettings.setAccessLevel(SiteAccessLevel.ADMINISTRATOR);
        childSiteSettings.setPrice250mb(250);
        childSiteSettings.setPrice500mb(500);
        childSiteSettings.setPrice1gb(1000);
        childSiteSettings.setPrice3gb(3000);
        childSiteSettings.setRequiredToUseSiteBlueprint(true);
        childSiteSettings.setTermsAndConditions("TermsAndConditions");
        DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        childSiteRegistration.setName("childSiteRegistration");
        childSiteSettings.setChildSiteRegistration(childSiteRegistration);
        persistance.putChildSiteSettings(childSiteSettings);

        ChildSiteEmailSender.execute(childSiteSettings, site, user);

        Assert.assertEquals(1, mockMailSender.getMails().size());
        Assert.assertEquals(user.getEmail(), mockMailSender.getMails().get(0).getTo());

        Assert.assertEquals(1, mockMailSender.getMails().size());
        Assert.assertEquals(1, mockMailSender.getMails().size());

        Assert.assertEquals("childSiteRegistration welcomes " + user.getFirstName() + " " + user.getLastName(), mockMailSender.getMails().get(0).getSubject());
        Assert.assertEquals(" <shroggle-admin@email>", mockMailSender.getMails().get(0).getFrom());
        final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
        Assert.assertEquals("www.web-deva.com", configStorage.get().getApplicationUrl());
        Assert.assertEquals("Thank you for registering with " + site.getTitle() + ". \n" +
                "\n" +
                "Start Date: " + DateUtil.toMonthDayAndYear(startEndDate) + "\n" +
                "End Date: " + DateUtil.toMonthDayAndYear(startEndDate) + "\n" +
                "Fees: $" + new Double(childSiteSettings.getPrice250mb()).intValue() + " per month (250mb media storage included)\n" +
                "To create your site, please, follow this LINK:\n" +
                "http://www.web-deva.com/childSiteRegistrationConfirmation.action?settingsId=" + childSiteSettings.getChildSiteSettingsId() + "&confirmCode=" + childSiteSettings.getConfirmCode() + "\n" +
                "\n" +
                "This service is powered by Web-Deva.com - a great web based tool for building sites and networks of sites.\n" +
                "Once you have activated your account, you will be invited to build your web pages using Web-Deva`s\n" +
                "`push button web site builder`; and templates created by " + childSiteRegistration.getName() + ". Its easy, quick, and fun.", mockMailSender.getMails().get(0).getHtml());
    }


    @Test
    public void testSendEmail_withFrom() {
        final User user = TestUtil.createUser("email");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        final Site site = TestUtil.createSite();
        site.setTitle("siteTitle");

        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        final Date startEndDate = new Date();
        childSiteSettings.setEndDate(startEndDate);
        childSiteSettings.setStartDate(startEndDate);
        childSiteSettings.setAccessLevel(SiteAccessLevel.ADMINISTRATOR);
        childSiteSettings.setPrice250mb(250);
        childSiteSettings.setPrice500mb(500);
        childSiteSettings.setPrice1gb(1000);
        childSiteSettings.setPrice3gb(3000);
        childSiteSettings.setRequiredToUseSiteBlueprint(true);
        childSiteSettings.setTermsAndConditions("TermsAndConditions");
        childSiteSettings.setFromEmail("from@email.email");
        DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        childSiteRegistration.setName("childSiteRegistration");
        childSiteSettings.setChildSiteRegistration(childSiteRegistration);
        persistance.putChildSiteSettings(childSiteSettings);

        ChildSiteEmailSender.execute(childSiteSettings, site, user);

        Assert.assertEquals(1, mockMailSender.getMails().size());
        Assert.assertEquals(user.getEmail(), mockMailSender.getMails().get(0).getTo());

        Assert.assertEquals(1, mockMailSender.getMails().size());
        Assert.assertEquals(1, mockMailSender.getMails().size());

        Assert.assertEquals("childSiteRegistration welcomes " + user.getFirstName() + " " + user.getLastName(), mockMailSender.getMails().get(0).getSubject());
        Assert.assertEquals("from@email.email <shroggle-admin@email>", mockMailSender.getMails().get(0).getFrom());
        Assert.assertEquals("from@email.email", mockMailSender.getMails().get(0).getReply());
        final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
        Assert.assertEquals("www.web-deva.com", configStorage.get().getApplicationUrl());
        Assert.assertEquals("Thank you for registering with " + site.getTitle() + ". \n" +
                "\n" +
                "Start Date: " + DateUtil.toMonthDayAndYear(startEndDate) + "\n" +
                "End Date: " + DateUtil.toMonthDayAndYear(startEndDate) + "\n" +
                "Fees: $" + new Double(childSiteSettings.getPrice250mb()).intValue() + " per month (250mb media storage included)\n" +
                "To create your site, please, follow this LINK:\n" +
                "http://www.web-deva.com/childSiteRegistrationConfirmation.action?settingsId=" + childSiteSettings.getChildSiteSettingsId() + "&confirmCode=" + childSiteSettings.getConfirmCode() + "\n" +
                "\n" +
                "This service is powered by Web-Deva.com - a great web based tool for building sites and networks of sites.\n" +
                "Once you have activated your account, you will be invited to build your web pages using Web-Deva`s\n" +
                "`push button web site builder`; and templates created by " + childSiteRegistration.getName() + ". Its easy, quick, and fun.", mockMailSender.getMails().get(0).getHtml());
    }


    @Test
    public void testSendEmail_withoutStartDate() {
        final User user = TestUtil.createUser("email");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        final Site site = TestUtil.createSite();
        site.setTitle("siteTitle");

        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        final Date startEndDate = new Date();
        childSiteSettings.setEndDate(startEndDate);
        //childSiteSettings.setStartDate(startEndDate);
        childSiteSettings.setAccessLevel(SiteAccessLevel.ADMINISTRATOR);
        childSiteSettings.setPrice250mb(250);
        childSiteSettings.setPrice500mb(500);
        childSiteSettings.setPrice1gb(1000);
        childSiteSettings.setPrice3gb(3000);
        childSiteSettings.setRequiredToUseSiteBlueprint(true);
        childSiteSettings.setTermsAndConditions("TermsAndConditions");
        DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        childSiteRegistration.setName("childSiteRegistration");
        childSiteSettings.setChildSiteRegistration(childSiteRegistration);
        persistance.putChildSiteSettings(childSiteSettings);

        ChildSiteEmailSender.execute(childSiteSettings, site, user);

        Assert.assertEquals(1, mockMailSender.getMails().size());
        Assert.assertEquals(user.getEmail(), mockMailSender.getMails().get(0).getTo());

        Assert.assertEquals(1, mockMailSender.getMails().size());
        Assert.assertEquals(1, mockMailSender.getMails().size());

        Assert.assertEquals("childSiteRegistration welcomes " + user.getFirstName() + " " + user.getLastName(), mockMailSender.getMails().get(0).getSubject());
        final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
        Assert.assertEquals("www.web-deva.com", configStorage.get().getApplicationUrl());
        Assert.assertEquals("Thank you for registering with " + site.getTitle() + ". \n" +
                "\n" +
                "End Date: " + DateUtil.toMonthDayAndYear(startEndDate) + "\n" +
                "Fees: $" + new Double(childSiteSettings.getPrice250mb()).intValue() + " per month (250mb media storage included)\n" +
                "To create your site, please, follow this LINK:\n" +
                "http://www.web-deva.com/childSiteRegistrationConfirmation.action?settingsId=" + childSiteSettings.getChildSiteSettingsId() + "&confirmCode=" + childSiteSettings.getConfirmCode() + "\n" +
                "\n" +
                "This service is powered by Web-Deva.com - a great web based tool for building sites and networks of sites.\n" +
                "Once you have activated your account, you will be invited to build your web pages using Web-Deva`s\n" +
                "`push button web site builder`; and templates created by " + childSiteRegistration.getName() + ". Its easy, quick, and fun.", mockMailSender.getMails().get(0).getHtml());
    }

    @Test
    public void testSendEmail_withoutEndDate() {
        final User user = TestUtil.createUser("email");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        final Site site = TestUtil.createSite();
        site.setTitle("siteTitle");

        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        final Date startEndDate = new Date();
        childSiteSettings.setStartDate(startEndDate);
        childSiteSettings.setAccessLevel(SiteAccessLevel.ADMINISTRATOR);
        childSiteSettings.setPrice250mb(250);
        childSiteSettings.setPrice500mb(500);
        childSiteSettings.setPrice1gb(1000);
        childSiteSettings.setPrice3gb(3000);
        childSiteSettings.setRequiredToUseSiteBlueprint(true);
        childSiteSettings.setTermsAndConditions("TermsAndConditions");
        DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        childSiteRegistration.setName("childSiteRegistration");
        childSiteSettings.setChildSiteRegistration(childSiteRegistration);
        persistance.putChildSiteSettings(childSiteSettings);

        ChildSiteEmailSender.execute(childSiteSettings, site, user);

        Assert.assertEquals(1, mockMailSender.getMails().size());
        Assert.assertEquals(user.getEmail(), mockMailSender.getMails().get(0).getTo());

        Assert.assertEquals(1, mockMailSender.getMails().size());
        Assert.assertEquals(1, mockMailSender.getMails().size());

        Assert.assertEquals("childSiteRegistration welcomes " + user.getFirstName() + " " + user.getLastName(), mockMailSender.getMails().get(0).getSubject());
        final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
        Assert.assertEquals("www.web-deva.com", configStorage.get().getApplicationUrl());
        Assert.assertEquals("Thank you for registering with " + site.getTitle() + ". \n" +
                "\n" +
                "Start Date: " + DateUtil.toMonthDayAndYear(startEndDate) + "\n" +
                "Fees: $" + new Double(childSiteSettings.getPrice250mb()).intValue() + " per month (250mb media storage included)\n" +
                "To create your site, please, follow this LINK:\n" +
                "http://www.web-deva.com/childSiteRegistrationConfirmation.action?settingsId=" + childSiteSettings.getChildSiteSettingsId() + "&confirmCode=" + childSiteSettings.getConfirmCode() + "\n" +
                "\n" +
                "This service is powered by Web-Deva.com - a great web based tool for building sites and networks of sites.\n" +
                "Once you have activated your account, you will be invited to build your web pages using Web-Deva`s\n" +
                "`push button web site builder`; and templates created by " + childSiteRegistration.getName() + ". Its easy, quick, and fun.", mockMailSender.getMails().get(0).getHtml());
    }

    @Test
    public void testSendEmail_withoutStartEndDate() {
        final User user = TestUtil.createUser("email");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        final Site site = TestUtil.createSite();
        site.setTitle("siteTitle");

        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        childSiteSettings.setAccessLevel(SiteAccessLevel.ADMINISTRATOR);
        childSiteSettings.setPrice250mb(250);
        childSiteSettings.setPrice500mb(500);
        childSiteSettings.setPrice1gb(1000);
        childSiteSettings.setPrice3gb(3000);
        childSiteSettings.setRequiredToUseSiteBlueprint(true);
        childSiteSettings.setTermsAndConditions("TermsAndConditions");
        DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        childSiteRegistration.setName("childSiteRegistration");
        childSiteSettings.setChildSiteRegistration(childSiteRegistration);
        persistance.putChildSiteSettings(childSiteSettings);

        ChildSiteEmailSender.execute(childSiteSettings, site, user);

        Assert.assertEquals(1, mockMailSender.getMails().size());
        Assert.assertEquals(user.getEmail(), mockMailSender.getMails().get(0).getTo());

        Assert.assertEquals(1, mockMailSender.getMails().size());
        Assert.assertEquals(1, mockMailSender.getMails().size());

        Assert.assertEquals("childSiteRegistration welcomes " + user.getFirstName() + " " + user.getLastName(), mockMailSender.getMails().get(0).getSubject());
        final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
        Assert.assertEquals("www.web-deva.com", configStorage.get().getApplicationUrl());
        Assert.assertEquals("Thank you for registering with " + site.getTitle() + ". \n" +
                "\n" +
                "Fees: $" + new Double(childSiteSettings.getPrice250mb()).intValue() + " per month (250mb media storage included)\n" +
                "To create your site, please, follow this LINK:\n" +
                "http://www.web-deva.com/childSiteRegistrationConfirmation.action?settingsId=" + childSiteSettings.getChildSiteSettingsId() + "&confirmCode=" + childSiteSettings.getConfirmCode() + "\n" +
                "\n" +
                "This service is powered by Web-Deva.com - a great web based tool for building sites and networks of sites.\n" +
                "Once you have activated your account, you will be invited to build your web pages using Web-Deva`s\n" +
                "`push button web site builder`; and templates created by " + childSiteRegistration.getName() + ". Its easy, quick, and fun.", mockMailSender.getMails().get(0).getHtml());
    }


    @Test
    public void createMessageBody_withSavedEmailText() {
        final User user = TestUtil.createUser("email");
        user.setFirstName("firstName");
        user.setLastName("lastName");

        final Site site = TestUtil.createSite();
        site.setTitle("siteTitle");

        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        childSiteSettings.setAccessLevel(SiteAccessLevel.ADMINISTRATOR);
        childSiteSettings.setPrice250mb(250);
        childSiteSettings.setPrice500mb(500);
        childSiteSettings.setPrice1gb(1000);
        childSiteSettings.setPrice3gb(3000);
        childSiteSettings.setRequiredToUseSiteBlueprint(true);
        childSiteSettings.setTermsAndConditions("TermsAndConditions");



        DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        childSiteRegistration.setEmailText(international.get("customizeEmailTextBody"));
        childSiteRegistration.setName("childSiteRegistration");
        childSiteRegistration.setBlueprintsId(Arrays.asList(1));
        childSiteSettings.setChildSiteRegistration(childSiteRegistration);
        persistance.putChildSiteSettings(childSiteSettings);

        FilledForm filledForm = TestUtil.createFilledForm(childSiteRegistration.getFormId());
        List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
        FilledFormItem filledFormItem;
        filledFormItem = TestUtil.createFilledFormItem(FormItemName.FIRST_NAME);
        filledFormItem.setValue(user.getFirstName());
        filledFormItems.add(filledFormItem);
        filledFormItem = TestUtil.createFilledFormItem(FormItemName.LAST_NAME);
        filledFormItem.setValue(user.getLastName());
        filledFormItems.add(filledFormItem);
        filledForm.setFilledFormItems(filledFormItems);
        filledForm.setChildSiteSettingsId(childSiteSettings.getChildSiteSettingsId());
        childSiteSettings.setFilledFormId(filledForm.getFilledFormId());


        final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
        configStorage.get().setApplicationUrl("www.web-deva.com");

        String mailBody = ChildSiteEmailSender.createMessageBody(childSiteSettings, site);
        

        Assert.assertEquals("Dear firstName lastName,<br><br>Thank you for registering with siteTitle.<br><br>Your registration gives you more than just your siteTitle membership. Part of your member`s benefits includes the potential to create `your own website`. siteTitle and our wonderful technology provider Web-Deva.com, have teamed up to provide you with access to a great tool for building and editing your site. Its really easy to use and gives great professional results.<br><br>Fees: $250.0 per month (250mb media storage included)<br><br>The following link will take you to a confirmation and welcome message on the Web-Deva site.<br><br>To verify your email click here <a href='http://www.web-deva.com/childSiteRegistrationConfirmation.action?settingsId=1&confirmCode=null'>account activation link</a><br><br>This service is powered by Web-Deva.com - a great web based tool for building sites and networks of sites. Once you have activated your account, you will be invited to build your web pages using Web-Deva`s `push button web site builder`; and templates created by childSiteRegistration.  Its easy, quick, and fun.<br><br>Thanks for registering, siteTitle",
                mailBody);

    }


    @Test
    public void createMessageBody_withoutSavedEmailText() {
        final User user = TestUtil.createUser("email");
        user.setFirstName("firstName");
        user.setLastName("lastName");

        final Site site = TestUtil.createSite();
        site.setTitle("siteTitle");

        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        childSiteSettings.setAccessLevel(SiteAccessLevel.ADMINISTRATOR);
        childSiteSettings.setPrice250mb(250);
        childSiteSettings.setPrice500mb(500);
        childSiteSettings.setPrice1gb(1000);
        childSiteSettings.setPrice3gb(3000);
        childSiteSettings.setRequiredToUseSiteBlueprint(true);
        childSiteSettings.setTermsAndConditions("TermsAndConditions");

        DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        childSiteRegistration.setEmailText(null);
        childSiteRegistration.setName("childSiteRegistration");
        childSiteSettings.setChildSiteRegistration(childSiteRegistration);
        persistance.putChildSiteSettings(childSiteSettings);

        final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
        configStorage.get().setApplicationUrl("www.web-deva.com");

        String mailBody = ChildSiteEmailSender.createMessageBody(childSiteSettings, site);


        Assert.assertEquals("Thank you for registering with siteTitle. \n" +
                "\n" +
                "Fees: $250 per month (250mb media storage included)\n" +
                "To create your site, please, follow this LINK:\n" +
                "http://www.web-deva.com/childSiteRegistrationConfirmation.action?settingsId=1&confirmCode=null\n" +
                "\n" +
                "This service is powered by Web-Deva.com - a great web based tool for building sites and networks of sites.\n" +
                "Once you have activated your account, you will be invited to build your web pages using Web-Deva`s\n" +
                "`push button web site builder`; and templates created by childSiteRegistration. Its easy, quick, and fun.",
                mailBody);

    }

    private final International international = ServiceLocator.getInternationStorage().get("configureChildSiteRegistration", Locale.US);
    private final MockMailSender mockMailSender = (MockMailSender) ServiceLocator.getMailSender();
    private final Persistance persistance = ServiceLocator.getPersistance();
}
