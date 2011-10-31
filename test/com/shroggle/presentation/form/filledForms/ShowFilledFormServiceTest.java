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
import com.shroggle.entity.*;
import com.shroggle.exception.FilledFormNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.presentation.form.filledForms.ShowFilledFormService;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author dmitry.solomadin, Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class ShowFilledFormServiceTest {

    @Before
    public void before() {
        MockWebContext webContext = (MockWebContext) service.getContext();
        webContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void showFormRecordData() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final User user = TestUtil.createUserAndUserOnSiteRight(site);
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "formName");
        final FilledForm filledForm = TestUtil.createFilledContactUsForm(user, TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS), form);
        TestUtil.createFilledContactUsForm(user, TestUtil.createFilledFormItems(FormItemName.EMAIL, FormItemName.LAST_NAME), form);

        Assert.assertEquals("/site/formRecordData.jsp", service.execute(filledForm.getFilledFormId()));
        Assert.assertEquals(filledForm, service.getFilledForm());
    }

    @Test(expected = FilledFormNotFoundException.class)
    public void showFormRecordDataWithoutForm() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final User user = TestUtil.createUserAndUserOnSiteRight(site);
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "formName");
        TestUtil.createFilledContactUsForm(user, TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS), form);
        TestUtil.createFilledContactUsForm(user, TestUtil.createFilledFormItems(FormItemName.EMAIL, FormItemName.LAST_NAME), form);

        service.execute(0);
    }

    @Test(expected = UserNotLoginedException.class)
    public void showFormRecordDataWithNotLoginedUser() throws IOException, ServletException {
        service.execute(0);
    }

    private final ShowFilledFormService service = new ShowFilledFormService();

}