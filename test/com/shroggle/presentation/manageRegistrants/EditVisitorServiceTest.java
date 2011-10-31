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
package com.shroggle.presentation.manageRegistrants;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.UserNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.presentation.manageRegistrants.EditVisitorService;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.http.Cookie;

/**
 * @author dmitry.solomadin
 */
@RunWith(TestRunnerWithMockServices.class)
public class EditVisitorServiceTest {

    @Before
    public void before() {
        MockWebContext webContext = (MockWebContext) service.getContext();
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest("", "");
        mockHttpServletRequest.setCookies(new Cookie[0]);
        webContext.setHttpServletRequest(mockHttpServletRequest);
    }

    @Test
    public void show() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final User user = TestUtil.createUser();
        DraftForm form = TestUtil.createRegistrationForm(site.getSiteId());
        TestUtil.createDefaultRegistrationFilledFormForVisitorAndFormId(user, site, form.getFormId());

        Assert.assertEquals("/account/manageRegistrants/editRegisteredVisitor.jsp", service.show(user.getUserId(), site.getSiteId()));
        Assert.assertEquals(user.getUserId(), service.getVisitorToEdit().getUserId());
    }

    @Test(expected = UserNotLoginedException.class)
    public void showWithNotLoginedUser() throws Exception {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createVisitorForSite(site);

        service.show(user.getUserId(), site.getSiteId());
    }

    @Test(expected = UserNotFoundException.class)
    public void showWithoutVisitor() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        service.show(0, site.getSiteId());
    }
    
    @Test(expected = SiteNotFoundException.class)
    public void showWithoutSite() throws Exception {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        service.show(user.getUserId(), 0);
    }

    private final EditVisitorService service = new EditVisitorService();

}
