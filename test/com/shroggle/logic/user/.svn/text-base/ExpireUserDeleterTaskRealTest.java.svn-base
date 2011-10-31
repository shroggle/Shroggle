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

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.User;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class ExpireUserDeleterTaskRealTest {

    @Test
    public void runWithoutUsers() {
        taskReal.run();
    }

    @Test
    public void runWithUserWithoutUser() {
        final User user = TestUtil.createUser();
        user.setActiveted(null);
        user.setRegistrationDate(new Date(System.currentTimeMillis() - 5L * 24L * 60L * 61L * 1000L));

        taskReal.run();

        Assert.assertNull("User not deleted!", persistance.getUserById(user.getUserId()));
    }

    @Test
    public void runWithUserWithUser() {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);
        user.setRegistrationDate(new Date(System.currentTimeMillis() - 5L * 24L * 60L * 61L * 1000L));
        user.setActiveted(null);

        taskReal.run();

        Assert.assertNull("User not deleted!", persistance.getUserById(user.getUserId()));
        Assert.assertNotNull("Site deleted!", persistance.getSite(site.getSiteId()));
    }

    @Test
    public void runWithUserWithoutUserAndNotExpired() {
        final User user = TestUtil.createUser();
        user.setRegistrationDate(new Date(System.currentTimeMillis()));

        taskReal.run();

        Assert.assertNotNull("User deleted!", persistance.getUserById(user.getUserId()));
    }

    private final ExpireUserDeleterTaskReal taskReal = new ExpireUserDeleterTaskReal();
    private final Persistance persistance = ServiceLocator.getPersistance();

}
