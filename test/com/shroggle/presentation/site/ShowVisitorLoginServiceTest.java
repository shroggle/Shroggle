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

import com.shroggle.PersistanceMock;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.MD5;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import java.io.IOException;


@RunWith(TestRunnerWithMockServices.class)
public class ShowVisitorLoginServiceTest {

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(mockHttpServletRequest);
    }

    @Test
    public void execute() throws IOException, ServletException {
        final PageManager pageVersion = TestUtil.createPageVersionSiteUser();

        final DraftLogin draftLogin = new DraftLogin();
        ServiceLocator.getPersistance().putItem(draftLogin);

        final WidgetItem widgetItem = TestUtil.createWidgetItem();
        widgetItem.setDraftItem(draftLogin);
        ServiceLocator.getPersistance().putWidget(widgetItem);
        pageVersion.addWidget(widgetItem);

        Assert.assertEquals("/site/render/widgetLogin.jsp", service.execute(widgetItem.getWidgetId(), false));
        Assert.assertFalse(service.isReturnToRegistration());
        Assert.assertFalse(service.isReturnToChildSiteRegistration());
    }

    @Test
    public void executeForRegistration() throws IOException, ServletException {
        final PageManager pageVersion = TestUtil.createPageVersionSiteUser();

        final DraftLogin draftLogin = new DraftLogin();
        ServiceLocator.getPersistance().putItem(draftLogin);

        final WidgetItem widgetItem = TestUtil.createWidgetItem();
        widgetItem.setDraftItem(draftLogin);
        ServiceLocator.getPersistance().putWidget(widgetItem);
        pageVersion.addWidget(widgetItem);

        Assert.assertEquals("/site/render/widgetLogin.jsp", service.executeForRegistration(widgetItem.getWidgetId()));
        Assert.assertTrue(service.isReturnToRegistration());
        Assert.assertFalse(service.isReturnToChildSiteRegistration());
    }
    
    @Test
    public void executeForChildSiteRegistration() throws IOException, ServletException {
        final PageManager pageVersion = TestUtil.createPageVersionSiteUser();

        final DraftLogin draftLogin = new DraftLogin();
        ServiceLocator.getPersistance().putItem(draftLogin);

        final WidgetItem widgetItem = TestUtil.createWidgetItem();
        widgetItem.setDraftItem(draftLogin);
        ServiceLocator.getPersistance().putWidget(widgetItem);
        pageVersion.addWidget(widgetItem);

        Assert.assertEquals("/site/render/widgetLogin.jsp", service.executeForChildSiteRegistration(widgetItem.getWidgetId()));
        Assert.assertFalse(service.isReturnToRegistration());
        Assert.assertTrue(service.isReturnToChildSiteRegistration());
    }

    @Test
    public void executeForNotLoginWidget() throws IOException, ServletException {
        final PageManager pageVersion = TestUtil.createPageVersionSiteUser();
        final WidgetItem widgetBlog = new WidgetItem();
        persistanceMock.putWidget(widgetBlog);
        pageVersion.addWidget(widgetBlog);

        Assert.assertEquals("/site/render/widgetLogin.jsp", service.execute(widgetBlog.getWidgetId(), false));
        Assert.assertFalse(service.isReturnToRegistration());
        Assert.assertFalse(service.isReturnToChildSiteRegistration());
    }

    @Test(expected = WidgetNotFoundException.class)
    public void executeWithNotFoundWidget() throws IOException, ServletException {
        service.execute(1, false);
    }

    @Test
    public void executeWithLoginedVisitorAndForce() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        final DraftLogin draftLogin = new DraftLogin();
        ServiceLocator.getPersistance().putItem(draftLogin);

        final WidgetItem widgetItem = TestUtil.createWidgetItem();
        widgetItem.setDraftItem(draftLogin);
        ServiceLocator.getPersistance().putWidget(widgetItem);
        pageVersion.addWidget(widgetItem);

        final User user = TestUtil.createUserAndUserOnSiteRight(site);
        user.setEmail("login");
        user.setPassword("password");
        persistanceMock.putUser(user);
        TestUtil.loginUser(user);

        Assert.assertEquals("/site/render/widgetLogin.jsp", service.execute(widgetItem.getWidgetId(), true));
        Assert.assertFalse(service.isReturnToRegistration());
        Assert.assertFalse(service.isReturnToChildSiteRegistration());
    }

    @Test
    public void executeForRegistrationWithLoginedVisitor() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        final DraftLogin draftLogin = new DraftLogin();
        ServiceLocator.getPersistance().putItem(draftLogin);

        final WidgetItem widgetItem = TestUtil.createWidgetItem();
        widgetItem.setDraftItem(draftLogin);
        ServiceLocator.getPersistance().putWidget(widgetItem);
        pageVersion.addWidget(widgetItem);

        final User user = TestUtil.createUserAndUserOnSiteRight(site);
        user.setEmail("login");
        user.setPassword("password");
        persistanceMock.putUser(user);
        TestUtil.loginUser(user);

        Assert.assertEquals("/site/render/visitorLogined.jsp", service.executeForRegistration(widgetItem.getWidgetId()));
        Assert.assertTrue(service.isReturnToRegistration());
        Assert.assertFalse(service.isReturnToChildSiteRegistration());
    }

    @Test
    public void executeForChildSiteRegistrationWithLoginedVisitor() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        final DraftLogin draftLogin = new DraftLogin();
        ServiceLocator.getPersistance().putItem(draftLogin);

        final WidgetItem widgetItem = TestUtil.createWidgetItem();
        widgetItem.setDraftItem(draftLogin);
        ServiceLocator.getPersistance().putWidget(widgetItem);
        pageVersion.addWidget(widgetItem);

        final User user = TestUtil.createUserAndUserOnSiteRight(site);
        user.setEmail("login");
        user.setPassword("password");
        persistanceMock.putUser(user);
        TestUtil.loginUser(user);

        Assert.assertEquals("/site/render/widgetLogin.jsp", service.executeForChildSiteRegistration(widgetItem.getWidgetId()));
        Assert.assertFalse(service.isReturnToRegistration());
        Assert.assertTrue(service.isReturnToChildSiteRegistration());
    }

    @Test
    public void executeWithLoginedVisitor() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        final DraftLogin draftLogin = new DraftLogin();
        ServiceLocator.getPersistance().putItem(draftLogin);

        final WidgetItem widgetItem = TestUtil.createWidgetItem();
        widgetItem.setDraftItem(draftLogin);
        ServiceLocator.getPersistance().putWidget(widgetItem);
        pageVersion.addWidget(widgetItem);

        final User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site);
        user.setEmail("login");
        user.setPassword("password");

        Assert.assertEquals("/site/render/visitorLogined.jsp", service.execute(widgetItem.getWidgetId(), false));
        Assert.assertEquals(user.getUserId(), service.getVisitor().getUserId());
        Assert.assertFalse(service.isReturnToRegistration());
        Assert.assertFalse(service.isReturnToChildSiteRegistration());
    }

    @Test
    public void executeWithCookies() throws Exception {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        final DraftLogin draftLogin = new DraftLogin();
        ServiceLocator.getPersistance().putItem(draftLogin);

        final WidgetItem widgetItem = TestUtil.createWidgetItem();
        widgetItem.setDraftItem(draftLogin);
        ServiceLocator.getPersistance().putWidget(widgetItem);
        pageVersion.addWidget(widgetItem);

        final User user = TestUtil.createUserAndUserOnSiteRight(site);
        user.setEmail("login");
        user.setPassword("password");
        persistanceMock.putUser(user);

        //Initializing Cookies
        Cookie crypted = new Cookie("sh_cpt" + site.getSiteId(), MD5.crypt(user.getEmail() + user.getPassword()));
        Cookie visitorId = new Cookie("sh_vid" + site.getSiteId(), "" + user.getUserId());
        mockHttpServletRequest.setCookies(new Cookie[]{crypted, visitorId});

        Assert.assertEquals("/site/render/visitorLogined.jsp", service.execute(widgetItem.getWidgetId(), false));
        Assert.assertEquals(user, service.getVisitor());
        Assert.assertEquals(user.getUserId(), new UsersManager().getLoginedUser().getUserId());
        Assert.assertFalse(service.isReturnToRegistration());
        Assert.assertFalse(service.isReturnToChildSiteRegistration());
    }

    @Test
    public void executeWithNotThisSiteCookies() throws Exception {
        final Site site1 = TestUtil.createSite();
        final Site site2 = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site1);

        final DraftLogin draftLogin = new DraftLogin();
        ServiceLocator.getPersistance().putItem(draftLogin);

        final WidgetItem widgetItem = TestUtil.createWidgetItem();
        widgetItem.setDraftItem(draftLogin);
        ServiceLocator.getPersistance().putWidget(widgetItem);
        pageVersion.addWidget(widgetItem);

        final User user = TestUtil.createUserAndUserOnSiteRight(site1);
        user.setEmail("login");
        user.setPassword("password");
        persistanceMock.putUser(user);

        //Initializing Cookies
        Cookie crypted = new Cookie("sh_cpt" + site2.getSiteId(), MD5.crypt(user.getEmail() + user.getPassword()));
        Cookie visitorId = new Cookie("sh_vid" + site2.getSiteId(), "" + user.getUserId());
        mockHttpServletRequest.setCookies(new Cookie[]{crypted, visitorId});

        Assert.assertEquals("/site/render/widgetLogin.jsp", service.execute(widgetItem.getWidgetId(), false));
        Assert.assertNull(new UsersManager().getLoginedUser());
        Assert.assertFalse(service.isReturnToRegistration());
        Assert.assertFalse(service.isReturnToChildSiteRegistration());
    }

    @Test
    public void executeWithBadCookies() throws Exception {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        final DraftLogin draftLogin = new DraftLogin();
        ServiceLocator.getPersistance().putItem(draftLogin);

        final WidgetItem widgetItem = TestUtil.createWidgetItem();
        widgetItem.setDraftItem(draftLogin);
        ServiceLocator.getPersistance().putWidget(widgetItem);
        pageVersion.addWidget(widgetItem);

        final User visitor1 = TestUtil.createUserAndUserOnSiteRight(site);
        visitor1.setEmail("login");
        visitor1.setPassword("password");
        persistanceMock.putUser(visitor1);

        final User visitor2 = TestUtil.createUserAndUserOnSiteRight(site);
        visitor2.setEmail("login2");
        visitor2.setPassword("password");
        persistanceMock.putUser(visitor2);

        final Cookie crypted = new Cookie(
                "sh_cpt" + site.getSiteId(),
                MD5.crypt(visitor1.getEmail() + visitor1.getPassword()));
        final Cookie visitorId = new Cookie("sh_vid" + site.getSiteId(), "" + visitor2.getUserId());
        mockHttpServletRequest.setCookies(new Cookie[]{crypted, visitorId});

        Assert.assertEquals("/site/render/widgetLogin.jsp", service.execute(widgetItem.getWidgetId(), false));
        Assert.assertNull(new UsersManager().getLoginedUser());
        Assert.assertFalse(service.isReturnToRegistration());
        Assert.assertFalse(service.isReturnToChildSiteRegistration());
    }

    private final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest("", "");
    private final ShowVisitorLoginService service = new ShowVisitorLoginService();
    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
    private final PersistanceMock persistanceMock = (PersistanceMock) ServiceLocator.getPersistance();

}
