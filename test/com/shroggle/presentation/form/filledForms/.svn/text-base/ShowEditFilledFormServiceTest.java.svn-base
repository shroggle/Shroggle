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
import com.shroggle.presentation.form.filledForms.ShowEditFilledFormService;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import java.io.IOException;

/**
 * @author dmitry.solomadin
 */
@RunWith(TestRunnerWithMockServices.class)
public class ShowEditFilledFormServiceTest {

    @Before
    public void before() {
        MockWebContext webContext = (MockWebContext) service.getContext();
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest("", "");
        mockHttpServletRequest.setCookies(new Cookie[0]);
        webContext.setHttpServletRequest(mockHttpServletRequest);
    }

    @Test
    public void showEditRecordWindow() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final User user = TestUtil.createUserAndUserOnSiteRight(site);
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "formName");
        final FilledForm filledForm = TestUtil.createFilledContactUsForm(user, TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS), form);
        TestUtil.createFilledContactUsForm(user, TestUtil.createFilledFormItems(FormItemName.EMAIL, FormItemName.LAST_NAME), form);

        Assert.assertEquals("/site/render/editFormRecord.jsp", service.execute(filledForm.getFilledFormId(), null));
        Assert.assertEquals(filledForm, service.getFilledForm());
        Assert.assertEquals(filledForm, service.getContext().getHttpServletRequest().getAttribute("prefilledForm"));
    }

    @Test
    public void showEditRecordWindowWithFilledFormWithoutUser() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final User user = TestUtil.createUserAndUserOnSiteRight(site);
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "formName");
        final FilledForm filledForm = TestUtil.createFilledContactUsForm(user, TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS), form);
        filledForm.setUser(null);
        TestUtil.createFilledContactUsForm(user, TestUtil.createFilledFormItems(FormItemName.EMAIL, FormItemName.LAST_NAME), form);

        Assert.assertEquals("/site/render/editFormRecord.jsp", service.execute(filledForm.getFilledFormId(), null));
        Assert.assertEquals(filledForm, service.getFilledForm());
        Assert.assertEquals(filledForm, service.getContext().getHttpServletRequest().getAttribute("prefilledForm"));
    }

    @Test(expected = FilledFormNotFoundException.class)
    public void showEditRecordWindowWithoutForm() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final User user = TestUtil.createUserAndUserOnSiteRight(site);
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "formName");
        TestUtil.createFilledContactUsForm(user, TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS), form);
        TestUtil.createFilledContactUsForm(user, TestUtil.createFilledFormItems(FormItemName.EMAIL, FormItemName.LAST_NAME), form);

        service.execute(0, null);
    }

    @Test(expected = UserNotLoginedException.class)
    public void showEditRecordWindowWithNotLoginedUser() throws IOException, ServletException {
        service.execute(0, null);
    }

    private final ShowEditFilledFormService service = new ShowEditFilledFormService();

}