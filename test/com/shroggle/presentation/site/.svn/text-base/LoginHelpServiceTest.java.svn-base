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
import com.shroggle.exception.UserNotLoginedException;
import junit.framework.Assert;
import org.junit.Test;

public class LoginHelpServiceTest extends TestBaseWithMockService {

    @Test
    public void execute() {
        TestUtil.createUserAndLogin();
        Assert.assertEquals("ok", service.execute());
    }

    @Test
    public void executeWithNotSamePassword() {
        TestUtil.createUserAndLogin().setPassword("111");

        Assert.assertEquals("wrongOldPassword", service.changeUserPassword("b", "a"));
    }

    @Test
    public void executeWithoutPassword() {
        TestUtil.createUserAndLogin().setPassword(null);

        Assert.assertEquals("PasswordNotFound", service.execute());
    }

    @Test
    public void executeWithoutEmail() {
        TestUtil.createUserAndLogin().setEmail(null);

        Assert.assertEquals("EmailNotFound", service.execute());
    }

    @Test
    public void changeUserPasswordWithoutPassword() {
        TestUtil.createUserAndLogin().setPassword(null);
        Assert.assertEquals("PasswordNotFound", service.execute());
    }

    @Test
    public void changeUserPasswordWithoutEmail() {
        TestUtil.createUserAndLogin().setEmail(null);
        Assert.assertEquals("EmailNotFound", service.execute());
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() {
        service.execute();
    }

    @Test(expected = UserNotLoginedException.class)
    public void changeUserPasswordWithoutLogin() {
        service.changeUserPassword("b", "a");
    }

    private final LoginHelpService service = new LoginHelpService();

}