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
package com.shroggle.presentation.form;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.Site;
import com.shroggle.entity.User;
import com.shroggle.entity.Form;
import com.shroggle.exception.UserNotLoginedException;

import java.util.List;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class GetAvailableFormsServiceTest {

    final GetAvailableFormsService service = new GetAvailableFormsService();

    @Test
    public void testExecute() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        final Site site2 = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final Form firstForm = TestUtil.createCustomForm(site);
        firstForm.setName("a");
        final Form secondForm = TestUtil.createCustomForm(site);
        secondForm.setName("b");

        //Not our's form.
        TestUtil.createCustomForm(site2);

        final List<AvailableFormResponse> availableForms = service.execute();
        Assert.assertEquals(2, availableForms.size());
        Assert.assertEquals(firstForm.getId(), availableForms.get(0).getFormId());
        Assert.assertEquals(secondForm.getId(), availableForms.get(1).getFormId());
    }

    @Test(expected = UserNotLoginedException.class)
    public void testExecuteWithoutLoginedUser() {
        service.execute();
    }

}
