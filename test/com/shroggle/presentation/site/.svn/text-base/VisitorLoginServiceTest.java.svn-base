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
import com.shroggle.entity.*;
import com.shroggle.exception.UserNotActivatedException;
import com.shroggle.exception.UserWithWrongPasswordException;
import com.shroggle.exception.VisitorNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.MD5;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.Context;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import net.sourceforge.stripes.mock.MockHttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

@RunWith(TestRunnerWithMockServices.class)
public class VisitorLoginServiceTest {

    @Before
    public void setRequest() {
        MockWebContext webContext = (MockWebContext) service.getContext();
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest("", "");
        mockHttpServletRequest.setCookies(new Cookie[0]);
        webContext.setHttpServletRequest(mockHttpServletRequest);
    }

    //Trying to login with existing visitor
    @Test
    public void executeWithCorrectDate() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        User user = TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.EDITOR);
        user.setEmail("login");
        user.setPassword("password");

        final VisitorLoginRequest request = new VisitorLoginRequest();
        request.setLogin(user.getEmail());
        request.setPassword(user.getPassword());
        request.setRemember(false);

        String response = service.execute(request, widget.getWidgetId(), false);

        assertEquals("ok", response);
        assertEquals((Integer) user.getUserId(), context.getUserId());
    }

    //Trying to login with existing user with checkbox rememberMe - on.
    @Test
    public void executeWithRememberMe() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        User user = TestUtil.createUserAndUserOnSiteRight(site);
        user.setEmail("login");
        user.setPassword("password");
        persistance.putUser(user);

        VisitorLoginRequest visitorLoginRequest = new VisitorLoginRequest();
        visitorLoginRequest.setLogin(user.getEmail());
        visitorLoginRequest.setPassword(user.getPassword());
        visitorLoginRequest.setRemember(true);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletResponse(new MockHttpServletResponse());

        String response = service.execute(visitorLoginRequest, widget.getWidgetId(), false);

        assertEquals("ok", response);
        assertEquals((Integer) user.getUserId(), context.getUserId());
        assertEquals(MD5.crypt(user.getEmail() + user.getPassword()),
                mockWebContext.getHttpServletResponse().getCookies()[0].getValue());
        assertEquals("sh_cpt",
                mockWebContext.getHttpServletResponse().getCookies()[0].getName());
        assertEquals("" + user.getUserId(),
                mockWebContext.getHttpServletResponse().getCookies()[1].getValue());
    }

    @Test(expected = UserWithWrongPasswordException.class)
    public void executeWithExistingUserAndIncorrectPassword() throws ServletException, IOException {
        User user = new User();
        user.setPassword("111");
        user.setEmail("asd");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        VisitorLoginRequest visitorLoginRequest = new VisitorLoginRequest();
        visitorLoginRequest.setLogin(user.getEmail());
        visitorLoginRequest.setPassword("icorrect password");
        visitorLoginRequest.setRemember(false);

        service.execute(visitorLoginRequest, widget.getWidgetId(), false);
        assertNull(context.getUserId());
    }

    //Trying to login with not eixsting visitor
    @Test(expected = VisitorNotFoundException.class)
    public void executeWithNotExistingVisitor() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        VisitorLoginRequest visitorLoginRequest = new VisitorLoginRequest();
        visitorLoginRequest.setLogin("asd@online.ua");
        visitorLoginRequest.setPassword("bbb");
        visitorLoginRequest.setRemember(false);

        service.execute(visitorLoginRequest, widget.getWidgetId(), false);
        User user = persistance.getUserByEmail("asd@online.ua");

        Assert.assertNull("Asserting that new user for existing user not exists", user);
    }

    @Test(expected = VisitorNotFoundException.class)
    public void executeWithNotThisSiteVisitor() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        final Site site2 = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        User user = TestUtil.createUserAndUserOnSiteRight(site2);
        user.setEmail("login");
        user.setPassword("password");
        persistance.putUser(user);

        VisitorLoginRequest visitorLoginRequest = new VisitorLoginRequest();
        visitorLoginRequest.setLogin(user.getEmail());
        visitorLoginRequest.setPassword(user.getPassword());
        visitorLoginRequest.setRemember(false);

        service.execute(visitorLoginRequest, widget.getWidgetId(), false);
    }
    
    @Test(expected = UserNotActivatedException.class)
    public void executeWithNotActivatedUser() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        final Site site2 = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        User user = TestUtil.createUserAndUserOnSiteRight(site2);
        user.setActiveted(null);
        user.setEmail("login");
        user.setPassword("password");
        persistance.putUser(user);

        VisitorLoginRequest visitorLoginRequest = new VisitorLoginRequest();
        visitorLoginRequest.setLogin(user.getEmail());
        visitorLoginRequest.setPassword(user.getPassword());
        visitorLoginRequest.setRemember(false);

        service.execute(visitorLoginRequest, widget.getWidgetId(), false);
    }

    private final VisitorLoginService service = new VisitorLoginService();
    private final Context context = ServiceLocator.getContextStorage().get();
    private final Persistance persistance = ServiceLocator.getPersistance();

}
