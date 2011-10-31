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
import com.shroggle.entity.User;
import com.shroggle.exception.UserNotActivatedException;
import com.shroggle.exception.UserNotFoundException;
import com.shroggle.exception.UserWithWrongPasswordException;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.context.SessionStorage;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Date;

public class LoginUserServiceTest extends TestBaseWithMockService {

    @Test
    public void execute() {
        final User user = TestUtil.createUserAndLogin("a");
        user.setPassword("1");
        user.setActiveted(new Date());

        LoginUserRequest request = new LoginUserRequest();
        request.setEmail("a");
        request.setPassword("1");
        Assert.assertFalse("Login in account return true, but it's allow only for admin!", service.execute(request));
        Assert.assertNotNull("Login in account not put accountId in session", new UsersManager().getLogined().getUserId());
    }

    @Test
    public void executeForAdmin() {
        final User user = TestUtil.createUserAndLogin();
        user.setPassword("1");
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        user.setActiveted(new Date());
        persistance.putUser(user);

        TestUtil.loginUser(user);

        configStorage.get().setAdminLogin("a");
        configStorage.get().setAdminPassword("1");

        LoginUserRequest request = new LoginUserRequest();
        request.setEmail("a");
        request.setPassword("1");
        Assert.assertTrue(service.execute(request));
        Assert.assertNotNull("Login in account not put accountId in session", new UsersManager().getLogined().getUserId());
    }

    @Test(expected = UserWithWrongPasswordException.class)
    public void executeWithNotSamePassword() {
        TestUtil.createUser("a");

        LoginUserRequest request = new LoginUserRequest();
        request.setEmail("a");
        request.setPassword("2");
        service.execute(request);
    }

    @Test(expected = UserNotFoundException.class)
    public void executeWithNotFoundEmail() {
        LoginUserRequest request = new LoginUserRequest();
        request.setEmail("a");
        service.execute(request);
    }

    @Test(expected = UserNotActivatedException.class)
    public void executeWithInactiveUser() {
        final User user = TestUtil.createUser("a");
        user.setActiveted(null);
        user.setPassword("1");

        LoginUserRequest request = new LoginUserRequest();
        request.setEmail("a");
        request.setPassword("1");
        service.execute(request);
    }

    private final LoginUserService service = new LoginUserService();
    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
    private final ConfigStorage configStorage = ServiceLocator.getConfigStorage();

}