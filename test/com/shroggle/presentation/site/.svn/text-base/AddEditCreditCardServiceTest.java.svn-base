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

import com.shroggle.TestBaseWithMockService;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.SitePaymentSettingsNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.presentation.site.payment.AddEditCreditCardService;
import com.shroggle.presentation.site.payment.UpdateSitePaymentInfoRequest;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Test;

import java.util.Date;

/**
 * @author Balakirev Anatoliy
 */
public class AddEditCreditCardServiceTest extends TestBaseWithMockService {

    @Test
    public void showCreditCardWindow() throws Exception {
        Site site = TestUtil.createSite();
        final User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        user.setPassword("1");
        user.setEmail("a@a");
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        service.showCreditCardWindow(-1);
    }


    @Test(expected = UserNotLoginedException.class)
    public void showCreditCardWindowWithoutLogin() throws Exception {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        service.showCreditCardWindow(-1);
    }


    @Test(expected = UserNotLoginedException.class)
    public void addCreditCardWithoutLogin() throws Exception {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        service.addCreditCard(null);
    }


    @Test
    public void addCreditCardWithoutAnyData() throws Exception {
        User account = new User();
        account.setEmail("a@a");

        User user = TestUtil.createUserAndLogin();
        user.setPassword("1");
        user.setEmail("a@a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);
        UserOnSiteRight usersAccessType = new UserOnSiteRight();
        // usersAccessType.setUser(user);
        usersAccessType.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        account.addUserOnSiteRight(usersAccessType);
        persistance.putUser(account);
        persistance.putUser(account);


        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        request.setCreditCardType(CreditCardType.VISA);
        response = service.addCreditCard(request);


        Assert.assertNotNull(response.getCardValidationErrors());
        Assert.assertEquals(3, response.getCardValidationErrors().size());
        Assert.assertEquals("Security code must be a 3- or 4-digit number", response.getCardValidationErrors().get(0));
        Assert.assertEquals("Credit card has expired. Please check the expiration date or enter a new credit card number.", response.getCardValidationErrors().get(1));
        Assert.assertEquals("The credit card number is wrong. Please check it and try again.", response.getCardValidationErrors().get(2));

        Assert.assertNull(response.getInnerHTML());
        Assert.assertNull(response.getCreditCardId());
        Assert.assertNull(response.getCreditCardNumber());

    }

    
    @Test
    public void addCreditCardWithoutCCNumber() throws Exception {
        User account = new User();
        account.setEmail("a@a");

        User user = TestUtil.createUserAndLogin();
        user.setPassword("1");
        user.setEmail("a@a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);
        UserOnSiteRight usersAccessType = new UserOnSiteRight();
        // usersAccessType.setUser(user);
        usersAccessType.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        account.addUserOnSiteRight(usersAccessType);
        persistance.putUser(account);
        persistance.putUser(account);

        request.setBillingAddress1("");
        request.setCreditCardExpirationMonth((byte)10);
        request.setCreditCardExpirationYear((short)2011);
        request.setCreditCardType(CreditCardType.VISA);
        request.setSecurityCode("123456");

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        response = service.addCreditCard(request);


        Assert.assertNotNull(response.getCardValidationErrors());
        Assert.assertEquals(2, response.getCardValidationErrors().size());
        Assert.assertEquals("Security code must be a 3- or 4-digit number", response.getCardValidationErrors().get(0));
        Assert.assertEquals("The credit card number is wrong. Please check it and try again.", response.getCardValidationErrors().get(1));

        //Assert.assertEquals("The expiration date you have entered would make this card invalid.", response.getErrors().get(1));
        //Assert.assertEquals("Please, enter a credit card number", response.getErrors().get(2));

        Assert.assertNull(response.getInnerHTML());
        Assert.assertNull(response.getCreditCardId());
        Assert.assertNull(response.getCreditCardNumber());
    }

    
    @Test
    public void addCreditCardWithCCNumberWithLetters() throws Exception {
        User account = new User();
        account.setEmail("a@a");

        User user = TestUtil.createUserAndLogin();
        user.setPassword("1");
        user.setEmail("a@a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);
        UserOnSiteRight usersAccessType = new UserOnSiteRight();
        // usersAccessType.setUser(user);
        usersAccessType.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        account.addUserOnSiteRight(usersAccessType);
        persistance.putUser(account);
        persistance.putUser(account);

        request.setBillingAddress1("");
        request.setCreditCardExpirationMonth((byte)10);
        request.setCreditCardExpirationYear((short)2011);
        request.setCreditCardType(CreditCardType.VISA);
        request.setSecurityCode("111");
        request.setCreditCardNumber("411f11111d111l11");

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        response = service.addCreditCard(request);


        Assert.assertNotNull(response.getCardValidationErrors());
        Assert.assertEquals(1, response.getCardValidationErrors().size());
        Assert.assertEquals("The credit card number is wrong. Please check it and try again.", response.getCardValidationErrors().get(0));
        //Assert.assertEquals("The expiration date you have entered would make this card invalid.", response.getErrors().get(1));
        //Assert.assertEquals("Please, enter a credit card number", response.getErrors().get(2));

        Assert.assertNull(response.getInnerHTML());
        Assert.assertNull(response.getCreditCardId());
        Assert.assertNull(response.getCreditCardNumber());
    }

    
    @Test
    public void addCreditCardWithNotValidVisaCard() throws Exception {
        User account = new User();
        account.setEmail("a@a");

        User user = TestUtil.createUserAndLogin();
        user.setPassword("1");
        user.setEmail("a@a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);
        UserOnSiteRight usersAccessType = new UserOnSiteRight();
        // usersAccessType.setUser(user);
        usersAccessType.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        account.addUserOnSiteRight(usersAccessType);
        persistance.putUser(account);
        persistance.putUser(account);

        request.setBillingAddress1("");
        request.setCreditCardExpirationMonth((byte)10);
        request.setCreditCardExpirationYear((short)2011);
        request.setCreditCardType(CreditCardType.VISA);
        request.setSecurityCode("111");
        request.setCreditCardNumber("41111111111111");

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        response = service.addCreditCard(request);


        Assert.assertNotNull(response.getCardValidationErrors());
        Assert.assertEquals(1, response.getCardValidationErrors().size());
        Assert.assertEquals("The credit card number is wrong. Please check it and try again.", response.getCardValidationErrors().get(0));
        //Assert.assertEquals("The expiration date you have entered would make this card invalid.", response.getErrors().get(1));
        //Assert.assertEquals("Please, enter a credit card number", response.getErrors().get(2));

        Assert.assertNull(response.getInnerHTML());
        Assert.assertNull(response.getCreditCardId());
        Assert.assertNull(response.getCreditCardNumber());

    }

    
    @Test
    public void addCreditCardWithNotValidMasterCard() throws Exception {
        User account = new User();
        account.setEmail("a@a");

        User user = TestUtil.createUserAndLogin();
        user.setPassword("1");
        user.setEmail("a@a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);
        UserOnSiteRight usersAccessType = new UserOnSiteRight();
        // usersAccessType.setUser(user);
        usersAccessType.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        account.addUserOnSiteRight(usersAccessType);
        persistance.putUser(account);
        persistance.putUser(account);

        request.setBillingAddress1("");
        request.setCreditCardExpirationMonth((byte)10);
        request.setCreditCardExpirationYear((short)2011);
        request.setCreditCardType(CreditCardType.MASTER_CARD);
        request.setSecurityCode("111");
        request.setCreditCardNumber("6431111111111111");

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        response = service.addCreditCard(request);


        Assert.assertNotNull(response.getCardValidationErrors());
        Assert.assertEquals(1, response.getCardValidationErrors().size());
        Assert.assertEquals("The credit card number is wrong. Please check it and try again.", response.getCardValidationErrors().get(0));
        //Assert.assertEquals("The expiration date you have entered would make this card invalid.", response.getErrors().get(1));
        //Assert.assertEquals("Please, enter a credit card number", response.getErrors().get(2));

        Assert.assertNull(response.getInnerHTML());
        Assert.assertNull(response.getCreditCardId());
        Assert.assertNull(response.getCreditCardNumber());

    }

    @Test
    public void addCreditCardWithNotValidExpirationDate() throws Exception {
        User account = new User();
        account.setEmail("a@a");

        User user = TestUtil.createUserAndLogin();
        user.setPassword("1");
        user.setEmail("a@a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);
        UserOnSiteRight usersAccessType = new UserOnSiteRight();
        // usersAccessType.setUser(user);
        usersAccessType.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        account.addUserOnSiteRight(usersAccessType);
        persistance.putUser(account);
        persistance.putUser(account);

        request.setBillingAddress1("");
        request.setCreditCardExpirationMonth((byte)10);
        request.setCreditCardExpirationYear((short)2007);
        request.setCreditCardType(CreditCardType.VISA);
        request.setSecurityCode("111");
        request.setCreditCardNumber("4111111111111111");

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        response = service.addCreditCard(request);


        Assert.assertNotNull(response.getCardValidationErrors());
        Assert.assertEquals(1, response.getCardValidationErrors().size());
        Assert.assertEquals("Credit card has expired. Please check the expiration date or enter a new credit card number.", response.getCardValidationErrors().get(0));
        //Assert.assertEquals("The expiration date you have entered would make this card invalid.", response.getErrors().get(1));
        //Assert.assertEquals("Please, enter a credit card number", response.getErrors().get(2));

        Assert.assertNull(response.getInnerHTML());
        Assert.assertNull(response.getCreditCardId());
        Assert.assertNull(response.getCreditCardNumber());
    }

    @Test
    public void addCreditCardWithNotValidSecurityCode() throws Exception {
        User account = new User();
        account.setEmail("a@a");

        User user = TestUtil.createUserAndLogin();
        user.setPassword("1");
        user.setEmail("a@a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);
        UserOnSiteRight usersAccessType = new UserOnSiteRight();
        // usersAccessType.setUser(user);
        usersAccessType.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        account.addUserOnSiteRight(usersAccessType);
        persistance.putUser(account);
        persistance.putUser(account);

        request.setBillingAddress1("");
        request.setCreditCardExpirationMonth((byte)10);
        request.setCreditCardExpirationYear((short)2017);
        request.setCreditCardType(CreditCardType.MASTER_CARD);
        request.setSecurityCode("1");
        request.setCreditCardNumber("5431111111111111");

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        response = service.addCreditCard(request);


        Assert.assertNotNull(response.getCardValidationErrors());
        Assert.assertEquals(1, response.getCardValidationErrors().size());
        Assert.assertEquals("Security code must be a 3- or 4-digit number", response.getCardValidationErrors().get(0));
        //Assert.assertEquals("The expiration date you have entered would make this card invalid.", response.getErrors().get(1));
        //Assert.assertEquals("Please, enter a credit card number", response.getErrors().get(2));

        Assert.assertNull(response.getInnerHTML());
        Assert.assertNull(response.getCreditCardId());
        Assert.assertNull(response.getCreditCardNumber());
    }

    @Test
    public void addCreditCardWithNotValidChecksum() throws Exception {
        User account = new User();
        account.setEmail("a@a");

        User user = TestUtil.createUserAndLogin();
        user.setPassword("1");
        user.setEmail("a@a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);
        UserOnSiteRight usersAccessType = new UserOnSiteRight();
        // usersAccessType.setUser(user);
        usersAccessType.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        account.addUserOnSiteRight(usersAccessType);
        persistance.putUser(account);
        persistance.putUser(account);

        request.setBillingAddress1("");
        request.setCreditCardExpirationMonth((byte)10);
        request.setCreditCardExpirationYear((short)2017);
        request.setCreditCardType(CreditCardType.VISA);
        request.setSecurityCode("111");
        request.setCreditCardNumber("4111111171199991");

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        response = service.addCreditCard(request);


        Assert.assertNotNull(response.getCardValidationErrors());
        Assert.assertEquals(1, response.getCardValidationErrors().size());
        Assert.assertEquals("The credit card number is wrong. Please check it and try again.", response.getCardValidationErrors().get(0));
        //Assert.assertEquals("The expiration date you have entered would make this card invalid.", response.getErrors().get(1));
        //Assert.assertEquals("Please, enter a credit card number", response.getErrors().get(2));

        Assert.assertNull(response.getInnerHTML());
        Assert.assertNull(response.getCreditCardId());
        Assert.assertNull(response.getCreditCardNumber());

    }

    @Test
    public void addCreditCardWithValidVisaCard() throws Exception {
        User account = new User();
        account.setEmail("a@a");

        User user = TestUtil.createUserAndLogin();
        user.setPassword("1");
        user.setEmail("a@a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);
        UserOnSiteRight usersAccessType = new UserOnSiteRight();
        // usersAccessType.setUser(user);
        usersAccessType.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        account.addUserOnSiteRight(usersAccessType);
        persistance.putUser(account);
        persistance.putUser(account);

        request.setBillingAddress1("");
        request.setCreditCardExpirationMonth((byte)10);
        request.setCreditCardExpirationYear((short)2017);
        request.setCreditCardType(CreditCardType.VISA);
        request.setSecurityCode("111");
        request.setCreditCardNumber("4111111111111111");

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        response = service.addCreditCard(request);


        Assert.assertNotNull(response.getCardValidationErrors());
        Assert.assertEquals(0, response.getCardValidationErrors().size());
        Assert.assertNotNull(response.getInnerHTML());
        Assert.assertNotNull(response.getCreditCardId());
        Assert.assertNotNull(response.getCreditCardNumber());

        CreditCard card = persistance.getCreditCardById(Integer.valueOf(response.getCreditCardId()));
        Assert.assertNotNull(card);
        Assert.assertNull(card.getNotificationMailSent());

    }

    @Test
    public void addCreditCardWithValidMasterCard() throws Exception {
        User account = new User();
        account.setEmail("a@a");

        User user = TestUtil.createUserAndLogin();
        user.setPassword("1");
        user.setEmail("a@a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);
        UserOnSiteRight usersAccessType = new UserOnSiteRight();
        // usersAccessType.setUser(user);
        usersAccessType.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        account.addUserOnSiteRight(usersAccessType);
        persistance.putUser(account);
        persistance.putUser(account);

        request.setBillingAddress1("");
        request.setCreditCardExpirationMonth((byte)10);
        request.setCreditCardExpirationYear((short)2017);
        request.setCreditCardType(CreditCardType.MASTER_CARD);
        request.setSecurityCode("111");
        request.setCreditCardNumber("5431111111111111");

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        response = service.addCreditCard(request);


        Assert.assertNotNull(response.getCardValidationErrors());
        Assert.assertEquals(0, response.getCardValidationErrors().size());
        Assert.assertNotNull(response.getInnerHTML());
        Assert.assertNotNull(response.getCreditCardId());
        Assert.assertNotNull(response.getCreditCardNumber());

        CreditCard card = persistance.getCreditCardById(Integer.valueOf(response.getCreditCardId()));
        Assert.assertNotNull(card);
        Assert.assertNull(card.getNotificationMailSent());
    }


    @Test(expected = UserNotLoginedException.class)
    public void removeCreditCardWithoutLogin() throws Exception {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        service.addCreditCard(null);
    }

    @Test
    public void removeCreditCard() throws Exception {
        User account = new User();
        account.setEmail("a@a");

        User user = TestUtil.createUserAndLogin();
        user.setPassword("1");
        user.setEmail("a@a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);
        UserOnSiteRight usersAccessType = new UserOnSiteRight();
        // usersAccessType.setUser(user);
        usersAccessType.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        account.addUserOnSiteRight(usersAccessType);
        persistance.putUser(account);
        persistance.putUser(account);

        Site site = new Site();
        site.setSubDomain(" ");

        site.setCreationDate(new Date());
        persistance.putSite(site);

        CreditCard card1 = new CreditCard();
        card1.setBillingAddress1("adr1");
        card1.setBillingAddress2("adr2");
        card1.setCreditCardNumber("1111111222222234");
        card1.setUser(account);
        site.getSitePaymentSettings().setCreditCard(card1);
        persistance.putCreditCard(card1);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        service.removeCreditCard(card1.getCreditCardId());


        CreditCard findCard1 = persistance.getCreditCardById(card1.getCreditCardId());
        Assert.assertNull(findCard1);
        Assert.assertEquals(site, persistance.getSite(site.getSiteId()));
        Assert.assertEquals(account, persistance.getUserById(account.getUserId()));
    }

    @Test(expected = UserNotLoginedException.class)
    public void updateSitePaymentInfoWithoutLogin() throws Exception {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        final UpdateSitePaymentInfoRequest request = new UpdateSitePaymentInfoRequest();
        request.setSiteId(1);
        request.setCardId(1);
        request.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        service.updateSitePaymentInfo(request);
    }

    @Test(expected = UserNotLoginedException.class)
    public void deactivateSiteWithoutLogin() throws Exception {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        service.deactivateSite(1);
    }

    @Test
    public void deactivateSite() throws Exception {
        User account1 = new User();
        account1.setEmail("aa");
        account1.setRegistrationDate(new Date());

        User user = TestUtil.createUserAndLogin();
        user.setPassword("1");
        user.setEmail("a@a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);
        UserOnSiteRight usersAccessType = new UserOnSiteRight();
        // usersAccessType.setUser(user);
        usersAccessType.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        account1.addUserOnSiteRight(usersAccessType);
        persistance.putUser(account1);
        Site site = new Site();
        site.setSubDomain(" ");
        site.setCreationDate(new Date());
        site.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_MONTHLY_FEE));
        site.getSitePaymentSettings().setExpirationDate(new Date(new Date().getTime() + 31535555555l));
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        persistance.putSite(site);

        CreditCard card1 = new CreditCard();
        card1.setBillingAddress1("adr1");
        card1.setBillingAddress2("adr2");
        card1.setCreditCardNumber("1111111222222234");
        card1.setUser(account1);
        site.getSitePaymentSettings().setCreditCard(card1);
        persistance.putCreditCard(card1);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));

        service.deactivateSite(site.getSiteId());
        Assert.assertEquals(new SiteManager(site).getSiteStatus(), SiteStatus.SUSPENDED);
    }

    @Test(expected = SitePaymentSettingsNotFoundException.class)
    public void deactivateSiteWithoutPaymentSettings() throws Exception {
        User account1 = new User();
        account1.setEmail("aa");
        account1.setRegistrationDate(new Date());

        User user = TestUtil.createUserAndLogin();
        user.setPassword("1");
        user.setEmail("a@a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);
        UserOnSiteRight usersAccessType = new UserOnSiteRight();
        // usersAccessType.setUser(user);
        usersAccessType.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        account1.addUserOnSiteRight(usersAccessType);
        persistance.putUser(account1);
        Site site = new Site();
        site.setSubDomain(" ");
        site.setCreationDate(new Date());
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);
        site.setSitePaymentSettings(null);
        //site.getSitePaymentSettings().setExpirationDate(new Date(new Date().getTime() + 31535555555l));
        persistance.putSite(site);

        CreditCard card1 = new CreditCard();
        card1.setBillingAddress1("adr1");
        card1.setBillingAddress2("adr2");
        card1.setCreditCardNumber("1111111222222234");
        card1.setUser(account1);
        //site.getSitePaymentSettings().setCreditCard(card1);
        persistance.putCreditCard(card1);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));

        service.deactivateSite(site.getSiteId());
    }

    @Test(expected = UserNotLoginedException.class)
    public void reactivateSiteWithoutLogin() throws Exception {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        service.reactivateSite(1);
    }

    @Test
    public void reactivateSite() throws Exception {
        User account1 = new User();
        account1.setEmail("aa");
        account1.setRegistrationDate(new Date());

        User user = TestUtil.createUserAndLogin();
        user.setPassword("1");
        user.setEmail("a@a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);
        UserOnSiteRight usersAccessType = new UserOnSiteRight();
        // usersAccessType.setUser(user);
        usersAccessType.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        account1.addUserOnSiteRight(usersAccessType);
        persistance.putUser(account1);
        Site site = new Site();
        site.setSubDomain(" ");
        site.setCreationDate(new Date());
        site.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_MONTHLY_FEE));
        site.getSitePaymentSettings().setRemainingTimeOfUsage(10000000l);
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.SUSPENDED);
        site.getSitePaymentSettings().setExpirationDate(new Date(new Date().getTime() + 31535555555l));
        persistance.putSite(site);

        CreditCard card1 = new CreditCard();
        card1.setBillingAddress1("adr1");
        card1.setBillingAddress2("adr2");
        card1.setCreditCardNumber("1111111222222234");
        card1.setUser(account1);
        site.getSitePaymentSettings().setCreditCard(card1);
        persistance.putCreditCard(card1);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));

        service.reactivateSite(site.getSiteId());
        Assert.assertEquals(new SiteManager(site).getSiteStatus(), SiteStatus.ACTIVE);
    }

    @Test(expected = SitePaymentSettingsNotFoundException.class)
    public void reactivateSiteWithoutPaymentSettings() throws Exception {
        User account1 = new User();
        account1.setEmail("aa");
        account1.setRegistrationDate(new Date());

        User user = TestUtil.createUserAndLogin();
        user.setPassword("1");
        user.setEmail("a@a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);
        UserOnSiteRight usersAccessType = new UserOnSiteRight();
        // usersAccessType.setUser(user);
        usersAccessType.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        account1.addUserOnSiteRight(usersAccessType);
        persistance.putUser(account1);
        Site site = new Site();
        site.setSubDomain(" ");
        site.setCreationDate(new Date());
        site.setSitePaymentSettings(null);
        //site.getSitePaymentSettings().setExpirationDate(new Date(new Date().getTime() + 31535555555l));
        persistance.putSite(site);

        CreditCard card1 = new CreditCard();
        card1.setBillingAddress1("adr1");
        card1.setBillingAddress2("adr2");
        card1.setCreditCardNumber("1111111222222234");
        card1.setUser(account1);
        //site.getSitePaymentSettings().setCreditCard(card1);
        persistance.putCreditCard(card1);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));

        service.reactivateSite(site.getSiteId());
    }

    private final AddEditCreditCardService service = new AddEditCreditCardService();
    private AddEditCreditCardResponse response = new AddEditCreditCardResponse();
    private final AddEditCreditCardRequest request = new AddEditCreditCardRequest();
}