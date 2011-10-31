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

import com.shroggle.entity.Site;
import com.shroggle.entity.User;
import com.shroggle.entity.UserOnSiteRight;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Artem Stasuk
 */
public class HibernateRemoveUserTest extends HibernatePersistanceTestBase {

    @Before
    public void before() {
        super.before();

        final Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        site.setSubDomain("GG");
        site.setTitle("f");
        site.getThemeId().setTemplateDirectory("A");
        site.getThemeId().setThemeCss("G");
        persistance.putSite(site);

        final User user1 = new User();
        user1.setEmail("a1@a");
        persistance.putUser(user1);

        final User user2 = new User();
        user2.setEmail("a2@a");
        persistance.putUser(user2);

        final UserOnSiteRight userOnUserRight1 = new UserOnSiteRight();
        site.addUserOnSiteRight(userOnUserRight1);
        user1.addUserOnSiteRight(userOnUserRight1);
        persistance.putUserOnSiteRight(userOnUserRight1);

        final UserOnSiteRight userOnUserRight2 = new UserOnSiteRight();
        site.addUserOnSiteRight(userOnUserRight2);
        user2.addUserOnSiteRight(userOnUserRight2);
        persistance.putUserOnSiteRight(userOnUserRight2);
        userId = user1.getUserId();
    }

    @Test
    public void execute() {
        persistance.removeUser(persistance.getUserById(userId));
        Assert.assertNull(persistance.getUserByEmail("a1@a"));
        Assert.assertNotNull(persistance.getSiteBySubDomain("GG"));
        Assert.assertNotNull(persistance.getUserByEmail("a2@a"));
    }

    private int userId;

}