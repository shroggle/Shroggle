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

package com.shroggle.util.persistance.hibernate;

import com.shroggle.entity.CreditCard;
import com.shroggle.entity.Site;
import com.shroggle.entity.ThemeId;
import com.shroggle.entity.User;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class HibernateCreditCardPersistanceTest extends HibernatePersistanceTestBase {

    @Test
    public void putCreditCard() {
        User account1 = new User();
        account1.setEmail("aa");
        account1.setRegistrationDate(new Date());
        persistance.putUser(account1);

        CreditCard card1 = new CreditCard();
        card1.setBillingAddress1("adr1");
        card1.setBillingAddress2("adr2");
        card1.setCreditCardNumber("1111111222222234");
        card1.setUser(account1);
        persistance.putCreditCard(card1);


        CreditCard card2 = new CreditCard();
        card2.setBillingAddress1("adr11");
        card2.setBillingAddress2("adr22");
        card2.setCreditCardNumber("1111111222222234");
        card2.setUser(account1);
        persistance.putCreditCard(card2);


        CreditCard findCard1 = HibernateManager.get().find(CreditCard.class, card1.getCreditCardId());
        CreditCard findCard2 = HibernateManager.get().find(CreditCard.class, card2.getCreditCardId());
        Assert.assertEquals("adr1", findCard1.getBillingAddress1());
        Assert.assertEquals("adr2", findCard1.getBillingAddress2());
        Assert.assertEquals("adr11", findCard2.getBillingAddress1());
        Assert.assertEquals("adr22", findCard2.getBillingAddress2());

        Assert.assertEquals(card1.getCreditCardId(), findCard1.getCreditCardId());
        Assert.assertEquals(card2.getCreditCardId(), findCard2.getCreditCardId());

    }


    @Test
    public void removeCreditCard() {
        User user1 = new User();
        user1.setEmail("aa");
        user1.setRegistrationDate(new Date());
        persistance.putUser(user1);

        Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        site.getSitePaymentSettings().setUserId(-1);
        ThemeId themeId = site.getThemeId();
        themeId.setTemplateDirectory("");
        themeId.setThemeCss("");
        site.setSubDomain(" ");
        site.setCreationDate(new Date());
        site.setTitle("f");
        persistance.putSite(site);

        CreditCard card1 = new CreditCard();
        card1.setBillingAddress1("adr1");
        card1.setBillingAddress2("adr2");
        card1.setCreditCardNumber("1111111222222234");
        card1.setUser(user1);
        site.getSitePaymentSettings().setCreditCard(card1);
        persistance.putCreditCard(card1);

        HibernateManager.get().flush();
        persistance.removeCreditCard(card1);
        HibernateManager.get().flush();
        Assert.assertNull(HibernateManager.get().find(CreditCard.class, card1.getCreditCardId()));
        Assert.assertEquals(site, HibernateManager.get().find(Site.class, site.getSiteId()));
        Assert.assertEquals(user1, HibernateManager.get().find(User.class, user1.getUserId()));
        HibernateManager.get().refresh(site);
        HibernateManager.get().refresh(site.getSitePaymentSettings());
        Assert.assertNull(site.getSitePaymentSettings().getCreditCard());
    }

    @Test
    public void testGetSitesConnectedToCreditCard() {
        User user1 = new User();
        user1.setEmail("aa");
        user1.setRegistrationDate(new Date());
        persistance.putUser(user1);

        CreditCard card1 = new CreditCard();
        card1.setBillingAddress1("adr1");
        card1.setBillingAddress2("adr2");
        card1.setCreditCardNumber("1111111222222234");
        card1.setUser(user1);
        persistance.putCreditCard(card1);

        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        ThemeId themeId = site.getThemeId();
        themeId.setTemplateDirectory("");
        themeId.setThemeCss("");
        site.setSubDomain(" ");
        site.setCreationDate(new Date());
        site.setTitle("f");
        site.getSitePaymentSettings().setCreditCard(card1);
        persistance.putSite(site);

        Site site2 = new Site();
        site2.getSitePaymentSettings().setUserId(-1);
        ThemeId themeId2 = site2.getThemeId();
        themeId2.setTemplateDirectory("2");
        themeId2.setThemeCss("2");
        site2.setSubDomain("2");
        site2.setCreationDate(new Date());
        site2.setTitle("2");
        site2.getSitePaymentSettings().setCreditCard(card1);
        persistance.putSite(site2);


        Site site3 = new Site();
        site3.getSitePaymentSettings().setUserId(-1);
        ThemeId themeId3 = site3.getThemeId();
        themeId3.setTemplateDirectory("3");
        themeId3.setThemeCss("3");
        site3.setSubDomain("3");
        site3.setCreationDate(new Date());
        site3.setTitle("3");
        site3.getSitePaymentSettings().setCreditCard(null);
        persistance.putSite(site3);

        final List<Site> sites = persistance.getSitesConnectedToCreditCard(card1.getCreditCardId());
        Assert.assertEquals(2, sites.size());
        Assert.assertTrue(sites.contains(site));
        Assert.assertTrue(sites.contains(site2));
    }
}