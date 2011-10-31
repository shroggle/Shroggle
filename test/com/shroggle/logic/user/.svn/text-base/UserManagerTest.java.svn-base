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
package com.shroggle.logic.user;

import com.shroggle.TestBaseWithMockService;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.SiteItemNotFoundException;
import com.shroggle.exception.UserNotFoundException;
import com.shroggle.logic.childSites.childSiteSettings.ChildSiteSettingsManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.Config;
import com.shroggle.util.international.International;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * @author Artem Stasuk
 */
public class UserManagerTest extends TestBaseWithMockService {

    @Test
    public void createByEmail() {
        TestUtil.createUser("a@a");
        new UserManager("a@a");
    }

    @Test
    public void createByUser() {
        new UserManager(new User());
    }

    @Test
    public void createByEmailInOtherCase() {
        TestUtil.createUser("a@a");
        new UserManager("a@A");
    }

    @Test
    public void createByEmailWithSpaces() {
        TestUtil.createUser("a@a");
        new UserManager("   a@a    ");
    }

    @Test(expected = UserNotFoundException.class)
    public void createByEmailWithSpacesIn() {
        TestUtil.createUser("a@a");
        new UserManager("a @a");
    }

    @Test
    public void sendActivationEmails() {
        final Config config = ServiceLocator.getConfigStorage().get();
        config.setApplicationName("applicationName");
        config.setApplicationUrl("www.applicationUrl.com");
        config.setUserSitesUrl("applicationUrl.com");
        config.setSupportEmail("support@email.com");

        final User user = TestUtil.createUser("a@a");
        user.setActiveted(null);
        user.setPassword("aa");
        user.setLastName("lastName");
        user.setUserId(1);
        user.setRegistrationDate(new Date(1234567890L));

        final UserManager userManager = new UserManager(user);
        userManager.sendActivationOrInvitationEmails();

        final String registrationCode = new UserManager(user).createRegistrationCode();
        Assert.assertEquals(1, mailSender.getMails().size());
        Assert.assertEquals(config.getApplicationName() + " welcomes  lastName", mailSender.getMails().get(0).getSubject());
        Assert.assertEquals("Dear  lastName,\n" +
                "\n" +
                "Thank you for registering with " + config.getApplicationName() + ". Click here to activate your account:\n" +
                "http://" + config.getApplicationUrl() + "/account/registrationConfirmation.action?userId=" + user.getUserId() + "&registrationCode=" + registrationCode + "\n" +
                "\n" +
                "Your " + config.getApplicationName() + " username and password are:\n" +
                "\n" +
                "USERNAME: " + user.getEmail() + "\n" +
                "PASSWORD: " + user.getPassword() + "\n" +
                "\n" +
                config.getApplicationName() + " is a fabulous new tool for creating and maintaining\n" +
                "professional web sites at the push of a button. " + config.getApplicationName() + " allows you to create functionally rich sites, with plenty of media.\n" +
                "\n" +
                "If you have any questions regarding your account, refer to the FAQs at\n" +
                "http://www.demo." + config.getUserSitesUrl() + "/FAQ\n" +
                "\n" +
                "or contact us directly with any questions or comments: " + config.getSupportEmail() + "\n" +
                "\n" +
                "If you did not request this account, or if you do not want to proceed please click here:\n" +
                "http://" + config.getApplicationUrl() + "/account/registrationCancel.action?userId=" + user.getUserId() + "&registrationCode=" + registrationCode, mailSender.getMails().get(0).getText());
    }

    @Test
    public void sendActivationEmailsForActiveUser() {
        final User user = TestUtil.createUser("a@a");
        user.setPassword("aa");

        final UserManager userManager = new UserManager(user);
        userManager.sendActivationOrInvitationEmails();

        Assert.assertEquals(0, mailSender.getMails().size());
    }

    @Test
    public void sendInvitationEmails() {
        final User user = TestUtil.createUser("a@a");
        final Site site = TestUtil.createSite();
        final UserOnSiteRight userOnSiteRight = TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        userOnSiteRight.setActive(false);
        userOnSiteRight.setRequesterUserId(TestUtil.createUser().getUserId());

        final UserManager userManager = new UserManager(user);
        userManager.sendActivationOrInvitationEmails();

        Assert.assertEquals(1, mailSender.getMails().size());
    }

    @Test
    public void sendActivedInvitationEmails() {
        final User user = TestUtil.createUser("a@a");
        final Site site = TestUtil.createSite();
        final UserOnSiteRight userOnSiteRight = TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        userOnSiteRight.setActive(true);
        userOnSiteRight.setRequesterUserId(TestUtil.createUser().getUserId());

        final UserManager userManager = new UserManager(user);
        userManager.sendActivationOrInvitationEmails();

        Assert.assertEquals(0, mailSender.getMails().size());
    }

    @Test
    public void sendManyInvitationEmails() {
        final User user = TestUtil.createUser("a@a");
        final Site site = TestUtil.createSite();

        final UserOnSiteRight userOnSiteRight = TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        userOnSiteRight.setActive(false);
        userOnSiteRight.setRequesterUserId(TestUtil.createUser().getUserId());

        final UserOnSiteRight userOnSiteRight2 = TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        userOnSiteRight2.setActive(false);
        userOnSiteRight2.setCreated(new Date(System.currentTimeMillis() * 2));
        userOnSiteRight2.setRequesterUserId(TestUtil.createUser().getUserId());

        final UserManager userManager = new UserManager(user);
        userManager.sendActivationOrInvitationEmails();

        Assert.assertEquals(2, mailSender.getMails().size());
    }

    @Test
    public void sendManyInvitationWithSameDateEmails() {
        final User user = TestUtil.createUser("a@a");
        final Site site = TestUtil.createSite();

        final UserOnSiteRight userOnSiteRight = TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        userOnSiteRight.setActive(false);
        userOnSiteRight.setRequesterUserId(TestUtil.createUser().getUserId());

        final UserOnSiteRight userOnSiteRight2 = TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        userOnSiteRight2.setActive(false);
        userOnSiteRight2.setCreated(userOnSiteRight.getCreated());
        userOnSiteRight2.setRequesterUserId(userOnSiteRight2.getRequesterUserId());

        final UserManager userManager = new UserManager(user);
        userManager.sendActivationOrInvitationEmails();

        Assert.assertEquals(1, mailSender.getMails().size());
    }

    @Test
    public void sendManyInvitationWithNotSameUsersEmails() {
        final User user = TestUtil.createUser("a@a");
        final Site site = TestUtil.createSite();

        final UserOnSiteRight userOnSiteRight = TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        userOnSiteRight.setActive(false);
        userOnSiteRight.setRequesterUserId(TestUtil.createUser().getUserId());

        final UserOnSiteRight userOnSiteRight2 = TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        userOnSiteRight2.setActive(false);
        userOnSiteRight2.setCreated(userOnSiteRight.getCreated());
        userOnSiteRight2.setRequesterUserId(TestUtil.createUser().getUserId());

        final UserManager userManager = new UserManager(user);
        userManager.sendActivationOrInvitationEmails();

        Assert.assertEquals(2, mailSender.getMails().size());
    }

    @Test
    public void sendChildSiteEmails() {
        final User user = TestUtil.createUser("a@a");

        final DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        persistance.putItem(childSiteRegistration);

        final Site parentSite = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        childSiteSettings.setParentSite(parentSite);
        childSiteSettings.setChildSiteRegistration(childSiteRegistration);
        childSiteSettings.setUserId(user.getUserId());
        persistance.putChildSiteSettings(childSiteSettings);
        user.addChildSiteSettingsId(childSiteSettings.getChildSiteSettingsId());

        final UserManager userManager = new UserManager(user);
        userManager.sendActivationOrInvitationEmails();

        Assert.assertEquals(1, mailSender.getMails().size());
    }

    @Test
    public void sendActivationAndInvitationEmails() {
        final User user = TestUtil.createUser("a@a");
        user.setActiveted(null);

        final Site site = TestUtil.createSite();
        final UserOnSiteRight userOnSiteRight = TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        userOnSiteRight.setActive(false);
        userOnSiteRight.setRequesterUserId(TestUtil.createUser().getUserId());

        final UserManager userManager = new UserManager(user);
        userManager.sendActivationOrInvitationEmails();

        Assert.assertEquals(2, mailSender.getMails().size());
    }

    @Test(expected = UserNotFoundException.class)
    public void createByEmailNotFound() {
        new UserManager("a@a");
    }

    @Test(expected = UserNotFoundException.class)
    public void createByNullEmail() {
        final String email = null;
        new UserManager(email);
    }

    @Test(expected = UserNotFoundException.class)
    public void createByNullUser() {
        final User user = null;
        new UserManager(user);
    }

    @Test(expected = SiteItemNotFoundException.class)
    public void getSiteItemByNullId() {
        final User user = TestUtil.createUser();

        new UserManager(user).getSiteItemForEditById(null, ItemType.BLOG);
    }

    @Test(expected = SiteItemNotFoundException.class)
    public void getSiteItemByIdNotFound() {
        final User user = TestUtil.createUser();

        new UserManager(user).getSiteItemForEditById(1, ItemType.BLOG);
    }

    @Test
    public void getSiteItemByIdForOwnerSiteWithRightAdministrator() {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUser();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        persistance.putItem(blog);

        Assert.assertEquals(blog, new UserManager(user).getSiteItemForEditById(
                blog.getId(), ItemType.BLOG));
    }

    @Test(expected = SiteItemNotFoundException.class)
    public void getSiteItemByIdWithoutRight() {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUser();

        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        persistance.putItem(blog);

        new UserManager(user).getSiteItemForEditById(blog.getId(), ItemType.BLOG);
    }

    @Test(expected = SiteItemNotFoundException.class)
    public void getSiteItemByIdForOverWithRightAdministrator() {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUser();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        final DraftBlog blog = new DraftBlog();
        persistance.putItem(blog);

        final SiteOnItem siteOnItemRight = blog.createSiteOnItemRight(site);
        siteOnItemRight.setAcceptDate(new Date());
        persistance.putSiteOnItem(siteOnItemRight);

        new UserManager(user).getSiteItemForEditById(blog.getId(), ItemType.BLOG);
    }

    @Test
    public void updateUserInfoByFilledForm_withAllFields() {
        final Coordinate coordinate = new Coordinate(Country.US, 0.0, 0.0, "post code");
        persistance.putCoordinate(coordinate);

        final User user = TestUtil.createUser();
        user.setFirstName("");
        user.setLastName("");
        user.setTelephone("");
        user.setTelephone2("");
        user.setBillingAddress1("");
        user.setBillingAddress2("");
        user.setPostalCode("");


        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        childSiteSettings.setAccessLevel(SiteAccessLevel.ADMINISTRATOR);
        childSiteSettings.setPrice250mb(250);
        childSiteSettings.setPrice500mb(500);
        childSiteSettings.setPrice1gb(1000);
        childSiteSettings.setPrice3gb(3000);
        childSiteSettings.setRequiredToUseSiteBlueprint(true);
        childSiteSettings.setTermsAndConditions("TermsAndConditions");


        final DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        childSiteRegistration.setEmailText(international.get("customizeEmailTextBody"));
        childSiteRegistration.setName("childSiteRegistration");
        childSiteSettings.setChildSiteRegistration(childSiteRegistration);
        persistance.putChildSiteSettings(childSiteSettings);


        final FilledForm filledForm = TestUtil.createFilledForm(childSiteRegistration.getFormId());
        List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
        FilledFormItem filledFormItem;

        filledFormItem = TestUtil.createFilledFormItem(FormItemName.FIRST_NAME);
        filledFormItem.setValue("firstName");
        filledFormItems.add(filledFormItem);
        persistance.putFilledFormItem(filledFormItem);
        filledFormItem = TestUtil.createFilledFormItem(FormItemName.LAST_NAME);
        filledFormItem.setValue("lastName");
        filledFormItems.add(filledFormItem);
        persistance.putFilledFormItem(filledFormItem);
        filledFormItem = TestUtil.createFilledFormItem(FormItemName.BILLING_ADDRESS);
        filledFormItem.setValue("billing adress");
        filledFormItems.add(filledFormItem);
        persistance.putFilledFormItem(filledFormItem);
        filledFormItem = TestUtil.createFilledFormItem(FormItemName.TELEPHONE);
        filledFormItem.setValue("telephone");
        filledFormItems.add(filledFormItem);
        persistance.putFilledFormItem(filledFormItem);
        filledFormItem = TestUtil.createFilledFormItem(FormItemName.POST_CODE);
        filledFormItem.setValue("post code");
        filledFormItems.add(filledFormItem);
        filledFormItem = TestUtil.createFilledFormItem(FormItemName.COUNTRY);
        filledFormItem.setValue("US");
        filledFormItems.add(filledFormItem);
        persistance.putFilledFormItem(filledFormItem);
        filledForm.setFilledFormItems(filledFormItems);


        filledForm.setChildSiteSettingsId(childSiteSettings.getChildSiteSettingsId());
        childSiteSettings.setFilledFormId(filledForm.getFilledFormId());


        Assert.assertEquals("", user.getFirstName());
        Assert.assertEquals("", user.getLastName());
        Assert.assertEquals("", user.getTelephone());
        Assert.assertEquals("", user.getTelephone2());
        Assert.assertEquals("", user.getPostalCode());
        Assert.assertEquals("", user.getBillingAddress1());
        Assert.assertEquals("", user.getBillingAddress2());

        new UserManager(user).updateUserInfoByFilledForm(filledForm);

        Assert.assertEquals("firstName", user.getFirstName());
        Assert.assertEquals("lastName", user.getLastName());
        Assert.assertEquals("telephone", user.getTelephone());
        Assert.assertEquals("telephone", user.getTelephone2());
        Assert.assertEquals("post code", user.getPostalCode());
        Assert.assertEquals("billing adress", user.getBillingAddress1());
        Assert.assertEquals("billing adress", user.getBillingAddress2());
    }

    @Test
    public void updateUserInfoByFilledForm_withOneField() {
        final User user = TestUtil.createUser();
        user.setFirstName("");
        user.setLastName("");
        user.setTelephone("");
        user.setTelephone2("");
        user.setBillingAddress1("");
        user.setBillingAddress2("");
        user.setPostalCode("");


        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        childSiteSettings.setAccessLevel(SiteAccessLevel.ADMINISTRATOR);
        childSiteSettings.setPrice250mb(250);
        childSiteSettings.setPrice500mb(500);
        childSiteSettings.setPrice1gb(1000);
        childSiteSettings.setPrice3gb(3000);
        childSiteSettings.setRequiredToUseSiteBlueprint(true);
        childSiteSettings.setTermsAndConditions("TermsAndConditions");


        final DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        childSiteRegistration.setEmailText(international.get("customizeEmailTextBody"));
        childSiteRegistration.setName("childSiteRegistration");
        childSiteSettings.setChildSiteRegistration(childSiteRegistration);
        persistance.putChildSiteSettings(childSiteSettings);


        final FilledForm filledForm = TestUtil.createFilledForm(childSiteRegistration.getFormId());
        List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
        FilledFormItem filledFormItem;

        filledFormItem = TestUtil.createFilledFormItem(FormItemName.FIRST_NAME);
        filledFormItem.setValue("firstName");
        filledFormItems.add(filledFormItem);
        persistance.putFilledFormItem(filledFormItem);
        filledForm.setFilledFormItems(filledFormItems);


        filledForm.setChildSiteSettingsId(childSiteSettings.getChildSiteSettingsId());
        childSiteSettings.setFilledFormId(filledForm.getFilledFormId());


        Assert.assertEquals("", user.getFirstName());
        Assert.assertEquals("", user.getLastName());
        Assert.assertEquals("", user.getTelephone());
        Assert.assertEquals("", user.getTelephone2());
        Assert.assertEquals("", user.getPostalCode());
        Assert.assertEquals("", user.getBillingAddress1());
        Assert.assertEquals("", user.getBillingAddress2());

        new UserManager(user).updateUserInfoByFilledForm(filledForm);

        Assert.assertEquals("firstName", user.getFirstName());
        Assert.assertEquals("", user.getLastName());
        Assert.assertEquals("", user.getTelephone());
        Assert.assertEquals("", user.getTelephone2());
        Assert.assertEquals("", user.getPostalCode());
        Assert.assertEquals("", user.getBillingAddress1());
        Assert.assertEquals("", user.getBillingAddress2());
    }

    @Test
    public void updateUserInfoByFilledForm_withoutFilledForm() {
        final User user = TestUtil.createUser();
        user.setFirstName("");
        user.setLastName("");
        user.setTelephone("");
        user.setTelephone2("");
        user.setBillingAddress1("");
        user.setBillingAddress2("");
        user.setPostalCode("");


        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        childSiteSettings.setAccessLevel(SiteAccessLevel.ADMINISTRATOR);
        childSiteSettings.setPrice250mb(250);
        childSiteSettings.setPrice500mb(500);
        childSiteSettings.setPrice1gb(1000);
        childSiteSettings.setPrice3gb(3000);
        childSiteSettings.setRequiredToUseSiteBlueprint(true);
        childSiteSettings.setTermsAndConditions("TermsAndConditions");


        final DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        childSiteRegistration.setEmailText(international.get("customizeEmailTextBody"));
        childSiteRegistration.setName("childSiteRegistration");
        childSiteSettings.setChildSiteRegistration(childSiteRegistration);
        persistance.putChildSiteSettings(childSiteSettings);


        Assert.assertEquals("", user.getFirstName());
        Assert.assertEquals("", user.getLastName());
        Assert.assertEquals("", user.getTelephone());
        Assert.assertEquals("", user.getTelephone2());
        Assert.assertEquals("", user.getPostalCode());
        Assert.assertEquals("", user.getBillingAddress1());
        Assert.assertEquals("", user.getBillingAddress2());

        new UserManager(user).updateUserInfoByFilledForm(null);

        Assert.assertEquals("", user.getFirstName());
        Assert.assertEquals("", user.getLastName());
        Assert.assertEquals("", user.getTelephone());
        Assert.assertEquals("", user.getTelephone2());
        Assert.assertEquals("", user.getPostalCode());
        Assert.assertEquals("", user.getBillingAddress1());
        Assert.assertEquals("", user.getBillingAddress2());
    }

    @Test
    public void testShowNetworkLogo_withSite() {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        Assert.assertFalse(new UserManager(user).isNetworkUser());
    }

    @Test
    public void testShowNetworkLogo_withoutSite() {
        final User user = TestUtil.createUser();
        Assert.assertFalse(new UserManager(user).isNetworkUser());
    }

    @Test
    public void testShowNetworkLogo_withChildSite() {
        final User user = TestUtil.createUser();
        Site site = TestUtil.createChildSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        Assert.assertTrue(new UserManager(user).isNetworkUser());
    }

    @Test
    public void testShowNetworkLogo_withTwoChildSitesFromTwoDifferentNetwork() {
        final User user = TestUtil.createUser();
        final Site site1 = TestUtil.createChildSite();
        final Site site2 = TestUtil.createChildSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site1);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site2);
        Assert.assertFalse(new UserManager(user).isNetworkUser());
    }

    @Test
    public void testShowNetworkLogo_withTwoChildSitesFromTwoDifferentNetwork_andOneOfSitesNotCreatedYet() {
        final User user = TestUtil.createUser();
        final Site site1 = TestUtil.createChildSite();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(TestUtil.createChildSiteRegistration(TestUtil.createSite()), new Site());
        user.setChildSiteSettingsId(Arrays.asList(childSiteSettings.getId()));
        TestUtil.createUserOnSiteRightActiveAdmin(user, site1);
        Assert.assertFalse(new UserManager(user).isNetworkUser());
    }

    @Test
    public void testShowNetworkLogo_withChildSiteSettingsId() {
        final User user = TestUtil.createUser();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(TestUtil.createChildSiteRegistration(TestUtil.createSite()), new Site());
        user.setChildSiteSettingsId(Arrays.asList(childSiteSettings.getId()));
        Assert.assertTrue(new UserManager(user).isNetworkUser());
    }

    @Test
    public void testGetFirstChildSiteSettingsForNetworkUser_withSite() {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        Assert.assertNull(new UserManager(user).getNetworkForNetworkUser());
    }

    @Test
    public void testGetFirstChildSiteSettingsForNetworkUser_withoutSite() {
        final User user = TestUtil.createUser();
        Assert.assertNull(new UserManager(user).getNetworkForNetworkUser());
    }

    @Test
    public void testGetFirstChildSiteSettingsForNetworkUser_withChildSite() {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createChildSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final ChildSiteSettingsManager manager = new UserManager(user).getNetworkForNetworkUser();
        Assert.assertNotNull(manager);
        Assert.assertEquals(site.getChildSiteSettings().getId(), manager.getId());
    }

    @Test
    public void testGetFirstChildSiteSettingsForNetworkUser_withTwoChildSites() {
        final User user = TestUtil.createUser();
        final Site site1 = TestUtil.createChildSite();
        final Site site2 = TestUtil.createChildSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site1);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site2);

        Assert.assertNull(new UserManager(user).getNetworkForNetworkUser());
    }

    @Test
    public void testGetFirstChildSiteSettingsForNetworkUser_withChildSiteSettingsId() {
        final User user = TestUtil.createUser();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), new Site());
        user.setChildSiteSettingsId(Arrays.asList(childSiteSettings.getId()));

        final ChildSiteSettingsManager manager = new UserManager(user).getNetworkForNetworkUser();
        Assert.assertNotNull(manager);
        Assert.assertEquals(childSiteSettings.getId(), manager.getId());
    }

    private final International international = ServiceLocator.getInternationStorage().get("configureChildSiteRegistration", Locale.US);
}
