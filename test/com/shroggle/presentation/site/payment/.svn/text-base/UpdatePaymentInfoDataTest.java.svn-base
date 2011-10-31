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
package com.shroggle.presentation.site.payment;

import com.shroggle.logic.site.SiteManager;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import com.shroggle.entity.Site;
import com.shroggle.entity.User;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.CreditCard;
import com.shroggle.TestUtil;
import com.shroggle.TestRunnerWithMockServices;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class UpdatePaymentInfoDataTest {

    @Test
    public void create_withAdminRightsForSelectedSite() {
        User user = TestUtil.createUser();
        CreditCard creditCard1 = TestUtil.createCreditCard(new Date(), user);
        creditCard1.setCreditCardNumber("1234567891234567");
        CreditCard creditCard2 = TestUtil.createCreditCard(new Date(), user);
        creditCard2.setCreditCardNumber("9876543219876543");
        final List<Site> availableSites = new ArrayList<Site>();
        Site site1 = TestUtil.createSite();
        Site site2 = TestUtil.createSite();
        Site site3 = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightActive(user, site2, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user, site3, SiteAccessLevel.EDITOR);
        availableSites.add(site1);
        availableSites.add(site2);
        availableSites.add(site3);
        UpdatePaymentInfoData data = new UpdatePaymentInfoData(availableSites, site2.getSiteId(), user);
        Assert.assertEquals(3, data.getAvailableSites().size());
        Assert.assertEquals(true, data.getAvailableSites().contains(site1));
        Assert.assertEquals(true, data.getAvailableSites().contains(site2));
        Assert.assertEquals(true, data.getAvailableSites().contains(site3));
        Assert.assertEquals(site2, data.getAvailableSites().get(0));
        Assert.assertEquals(true, data.isHasAdminAccessToThisSite());
        Assert.assertEquals(site2, data.getSelectedSite());
        Assert.assertEquals(new SiteManager(site2).getSiteStatus(), data.getSelectedSiteStatus());
        Assert.assertEquals(2, data.getCreditCards().size());
    }
    

    @Test
    public void create_withNotAdminRightsForSelectedSite() {
        User user = TestUtil.createUser();
        CreditCard creditCard1 = TestUtil.createCreditCard(new Date(), user);
        creditCard1.setCreditCardNumber("1234567891234567");
        CreditCard creditCard2 = TestUtil.createCreditCard(new Date(), user);
        creditCard2.setCreditCardNumber("9876543219876543");
        final List<Site> availableSites = new ArrayList<Site>();
        Site site1 = TestUtil.createSite();
        Site site2 = TestUtil.createSite();
        Site site3 = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user, site2, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightActive(user, site3, SiteAccessLevel.ADMINISTRATOR);
        availableSites.add(site1);
        availableSites.add(site2);
        availableSites.add(site3);
        UpdatePaymentInfoData data = new UpdatePaymentInfoData(availableSites, site2.getSiteId(), user);
        Assert.assertEquals(3, data.getAvailableSites().size());
        Assert.assertEquals(true, data.getAvailableSites().contains(site1));
        Assert.assertEquals(true, data.getAvailableSites().contains(site2));
        Assert.assertEquals(true, data.getAvailableSites().contains(site3));
        Assert.assertEquals(site2, data.getAvailableSites().get(0));
        Assert.assertEquals(false, data.isHasAdminAccessToThisSite());
        Assert.assertEquals(site2, data.getSelectedSite());
        Assert.assertEquals(new SiteManager(site2).getSiteStatus(), data.getSelectedSiteStatus());
        Assert.assertEquals(2, data.getCreditCards().size());
    }
}
