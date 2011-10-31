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
import com.shroggle.entity.User;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(value = TestRunnerWithMockServices.class)
public class CheckUserEmailServiceTest {

    @Test
    public void executeWithoutLogin() {
        Assert.assertEquals("", service.execute("a@a.com", null));
    }

    @Test
    public void executeWithLogin() {
        User account = new User();
        account.setEmail("a@gmail.com");
        persistance.putUser(account);

        Assert.assertEquals("", service.execute("a@a.com", null));
    }

    @Test
    public void executeWithNotUnique() {
        User account = new User();
        account.setEmail("a@a.com");
        persistance.putUser(account);

        Assert.assertEquals("com.shroggle.exception.NotUniqueUserEmailException", service.execute("a@a.com", null));
    }

    @Test
    public void executeWithNotUniqueInOtherCase() {
        User account = new User();
        account.setEmail("a@a.com");
        persistance.putUser(account);

        Assert.assertEquals("com.shroggle.exception.NotUniqueUserEmailException", service.execute("a@A.com", null));
    }

    @Test
    public void executeWithEmpty() {
        Assert.assertNotSame("", service.execute("", null));
    }

    @Test
    public void executeWithOnlySpacers() {
        Assert.assertNotSame("", service.execute("                           ", null));
    }

    @Test
    public void executeWithIncorrect() {
        Assert.assertNotSame("", service.execute("a1", null));
    }

    private final CheckUserEmailService service = new CheckUserEmailService();
    private final Persistance persistance = ServiceLocator.getPersistance();

}