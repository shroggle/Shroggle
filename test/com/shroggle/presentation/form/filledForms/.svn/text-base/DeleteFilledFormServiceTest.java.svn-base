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
package com.shroggle.presentation.form.filledForms;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.presentation.form.filledForms.DeleteFilledFormService;
import com.shroggle.entity.*;
import com.shroggle.exception.FilledFormNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author dmitry.solomadin
 */
@RunWith(TestRunnerWithMockServices.class)
public class DeleteFilledFormServiceTest {

    @Test
    public void deleteFilledForm() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final User user = TestUtil.createUserAndUserOnSiteRight(site);
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "formName");
        final FilledForm filledForm = TestUtil.createFilledContactUsForm(user, TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS), form);
        TestUtil.createFilledContactUsForm(user, TestUtil.createFilledFormItems(FormItemName.EMAIL, FormItemName.LAST_NAME), form);

        service.execute(filledForm.getFilledFormId());
        Assert.assertNull(persistance.getFilledFormById(filledForm.getFilledFormId()));
        Assert.assertFalse(user.getFilledForms().contains(filledForm));
    }

    @Test(expected = FilledFormNotFoundException.class)
    public void deleteFilledFormWithoutForm() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final User user = TestUtil.createUserAndUserOnSiteRight(site);
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "formName");
        TestUtil.createFilledContactUsForm(user, TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS), form);
        TestUtil.createFilledContactUsForm(user, TestUtil.createFilledFormItems(FormItemName.EMAIL, FormItemName.LAST_NAME), form);

        service.execute(0);
    }

    @Test(expected = UserNotLoginedException.class)
    public void deleteFilledFormWithNotLoginedUser() throws IOException, ServletException {
        service.execute(0);
    }

    private final DeleteFilledFormService service = new DeleteFilledFormService();
    private final Persistance persistance = ServiceLocator.getPersistance();

}