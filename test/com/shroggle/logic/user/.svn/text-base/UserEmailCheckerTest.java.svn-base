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
import com.shroggle.exception.NullOrEmptyEmailException;
import com.shroggle.exception.NotUniqueUserEmailException;
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Artem Stasuk
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class UserEmailCheckerTest {

    @Test
    public void execute() {
        new UserEmailChecker().execute("a@a.com", null);
    }

    @Test(expected = NullOrEmptyEmailException.class)
    public void executeWithNull() {
        new UserEmailChecker().execute(null, null);
    }

    @Test(expected = NullOrEmptyEmailException.class)
    public void executeWithEmpty() {
        new UserEmailChecker().execute(" ", null);
    }

    @Test(expected = NotUniqueUserEmailException.class)
    public void executeWithNotUnique() {
        final Site site = TestUtil.createSite();
        User user = TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);

        user.setEmail("a@a.com");
        new UserEmailChecker().execute("a@a.com", null);
    }

    @Test
    public void executeForExists() {
        final Site site = TestUtil.createSite();
        User user = TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);

        user.setEmail("a@a.com");
        new UserEmailChecker().execute("a@a.com", user.getUserId());
    }

    @Test(expected = NotUniqueUserEmailException.class)
    public void executeWithNotUniqueForExistsUser() {
        final User user = TestUtil.createUser("b@b.com");
        TestUtil.createUser("a@a.com");

        new UserEmailChecker().execute("a@a.com", user.getUserId());
    }

    @Test(expected = NotUniqueUserEmailException.class)
    public void executeWithNotUniqueForExistsVisitor() {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUserAndUserOnSiteRight(site);
        user.setEmail("a@a.com");

        new UserEmailChecker().execute("a@a.com", null);
    }

    @Test
    public void executeForExistsUserAndVisitor() {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUser("a@a.com");

        new UserEmailChecker().execute("a@a.com", user.getUserId());
    }

    @Test(expected = NotUniqueUserEmailException.class)
    public void executeWithNotUniqueAndDifferentCase() {
        final Site site = TestUtil.createSite();
        User user = TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);

        user.setEmail("a@a.com");
        new UserEmailChecker().execute("a@A.com", null);
    }

}