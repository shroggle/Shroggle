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
import com.shroggle.entity.EmailUpdateRequest;
import com.shroggle.entity.User;
import com.shroggle.exception.CannotFindEmailUpdateRequestException;
import com.shroggle.exception.UserNotFoundException;
import com.shroggle.presentation.MockSessionStorage;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import net.sourceforge.stripes.action.ForwardResolution;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;

@RunWith(value = TestRunnerWithMockServices.class)
public class EmailUpdateApproveActionTest {

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final MockSessionStorage sessionStorage = (MockSessionStorage) ServiceLocator.getSessionStorage();
    private final EmailUpdateApproveAction action = new EmailUpdateApproveAction();

    @Test
    public void execute() throws ServletException, IOException {
        User user = TestUtil.createUser();
        user.setEmail("a@a.a");

        String newUserEmail = "b@b.b";

        EmailUpdateRequest emailUpdateRequest = new EmailUpdateRequest();
        emailUpdateRequest.setUserId(user.getUserId());
        emailUpdateRequest.setUpdateId("1");
        emailUpdateRequest.setNewEmail(newUserEmail);
        persistance.putEmailUpdateRequest(emailUpdateRequest);

        action.updateId = "1";

        Assert.assertEquals(new ForwardResolution("/account/emailUpdateApprove.jsp").toString(), action.show().toString());
        Assert.assertEquals(newUserEmail, user.getEmail());
    }

    @Test(expected = CannotFindEmailUpdateRequestException.class)
    public void executeWithWrongUpdateId() throws ServletException, IOException {
        User user = TestUtil.createUser();
        user.setEmail("a@a.a");

        String newUserEmail = "b@b.b";

        EmailUpdateRequest emailUpdateRequest = new EmailUpdateRequest();
        emailUpdateRequest.setUserId(user.getUserId());
        emailUpdateRequest.setUpdateId("1");
        emailUpdateRequest.setNewEmail(newUserEmail);
        persistance.putEmailUpdateRequest(emailUpdateRequest);

        action.updateId = "2";

        Assert.assertEquals(new ForwardResolution("/account/emailUpdateApprove.jsp").toString(), action.show().toString());
    }

    @Test(expected = UserNotFoundException.class)
    public void executeWithoutUserId() throws ServletException, IOException {
        User user = TestUtil.createUser();
        user.setEmail("a@a.a");

        String newUserEmail = "b@b.b";

        EmailUpdateRequest emailUpdateRequest = new EmailUpdateRequest();
        emailUpdateRequest.setUpdateId("1");
        emailUpdateRequest.setNewEmail(newUserEmail);
        persistance.putEmailUpdateRequest(emailUpdateRequest);

        action.updateId = "1";

        Assert.assertEquals(new ForwardResolution("/account/emailUpdateApprove.jsp").toString(), action.show().toString());
    }


}
