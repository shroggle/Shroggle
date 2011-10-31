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
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.presentation.StartAction;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class DeleteUserActionTest {

    @Test
    public void executeWithoutLogin() {
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();
        Assert.assertEquals(action, resolutionMock.getLoginInUserAction());
    }

    @Test
    public void showWithoutLogin() {
        ResolutionMock resolutionMock = (ResolutionMock) action.show();
        Assert.assertEquals(action, resolutionMock.getLoginInUserAction());
    }

    @Test
    public void execute() {
        User user = TestUtil.createUserAndLogin();

        User user2 = new User();
        user2.setRegistrationDate(new Date());
        user2.setEmail("a1@a.com");
        persistance.putUser(user2);

        List<Site> sites = new ArrayList<Site>();
        for (int i = 0; i < 5; i++) {
            Site site = new Site();
            site.setTitle("title" + i);
            site.setSubDomain("1" + i);
            site.setCreationDate(new Date());
            ThemeId id = new ThemeId();
            id.setTemplateDirectory("" + i);
            id.setThemeCss("" + i);
            site.setThemeId(id);
            persistance.putSite(site);
            sites.add(site);
        }

        CreditCard creditCard = new CreditCard();
        creditCard.setUser(user);
        user.addCreditCard(creditCard);
        for (int i = 0; i < 5; i++) {
            sites.get(i).getSitePaymentSettings().setCreditCard(creditCard);
        }
        persistance.putCreditCard(creditCard);

        for (int i = 0; i < 5; i++) {
            UserOnSiteRight userOnUserRight = new UserOnSiteRight();
            userOnUserRight.setActive(true);
            user.addUserOnSiteRight(userOnUserRight);
            userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
            user.addUserOnSiteRight(userOnUserRight);
            sites.get(i).addUserOnSiteRight(userOnUserRight);
            persistance.putUserOnSiteRight(userOnUserRight);
        }

        for (int i = 0; i < 5; i++) {
            UserOnSiteRight userOnUserRight = new UserOnSiteRight();
            userOnUserRight.setActive(true);
            user2.addUserOnSiteRight(userOnUserRight);
            userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
            user2.addUserOnSiteRight(userOnUserRight);
            sites.get(i).addUserOnSiteRight(userOnUserRight);
            persistance.putUserOnSiteRight(userOnUserRight);
        }
        for (int i = 0; i < 5; i++) {
            Assert.assertEquals(sites.get(i).getUserOnSiteRights().size(), 2);
            Assert.assertNotNull(sites.get(i).getSitePaymentSettings().getCreditCard());
        }

        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        for (int i = 0; i < 5; i++) {
            Assert.assertEquals(sites.get(i).getUserOnSiteRights().size(), 1);
            Assert.assertNull(sites.get(i).getSitePaymentSettings().getCreditCard());
        }
        Assert.assertNull(persistance.getUserById(user.getUserId()));
        Assert.assertNull(persistance.getUserById(user.getUserId()));
        Assert.assertEquals(StartAction.class, resolutionMock.getRedirectByAction());
    }

    private final DeleteUserAction action = new DeleteUserAction();
    private final Persistance persistance = ServiceLocator.getPersistance();

}