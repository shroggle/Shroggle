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
import com.shroggle.entity.DraftAdminLogin;
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.WidgetItem;
import com.shroggle.exception.AdminLoginNameNotUniqueException;
import com.shroggle.exception.AdminLoginNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Artem Stasuk, dmitry.solomadin
 */
@RunWith(TestRunnerWithMockServices.class)
public class SaveAdminLoginServiceTest {

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test(expected = AdminLoginNameNotUniqueException.class)
    public void executeFromSiteEdit_withNotUniqueName() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        final DraftAdminLogin draftAdminLogin = TestUtil.createAdminLogin(site);
        draftAdminLogin.setName("notUniqueName");

        final DraftAdminLogin adminLogin = TestUtil.createAdminLogin(site);

        final WidgetItem widgetAdminLogin = TestUtil.createWidgetAdminLogin();
        widgetAdminLogin.setDraftItem(adminLogin);
        pageVersion.addWidget(widgetAdminLogin);

        SaveAdminLoginRequest request = new SaveAdminLoginRequest();
        request.setText("g");
        request.setShowDescription(true);
        request.setDescription("desc");
        request.setWidgetId(widgetAdminLogin.getWidgetId());
        request.setAdminLoginId(adminLogin.getId());
        request.setName("notUniqueName");

        service.execute(request);
    }

    @Test
    public void executeFromSiteEdit() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        final DraftAdminLogin adminLogin = TestUtil.createAdminLogin(site);

        final WidgetItem widgetAdminLogin = TestUtil.createWidgetAdminLogin();
        widgetAdminLogin.setDraftItem(adminLogin);
        pageVersion.addWidget(widgetAdminLogin);

        SaveAdminLoginRequest request = new SaveAdminLoginRequest();
        request.setText("g");
        request.setShowDescription(true);
        request.setDescription("desc");
        request.setWidgetId(widgetAdminLogin.getWidgetId());
        request.setAdminLoginId(adminLogin.getId());
        request.setName("notUniqueName");

        final FunctionalWidgetInfo response = service.execute(request);

        Assert.assertNotNull(response);
        Assert.assertEquals(widgetAdminLogin.getWidgetId(), response.getWidget().getWidgetId());

        Assert.assertEquals("g", adminLogin.getText());
        Assert.assertEquals("desc", adminLogin.getDescription());
        Assert.assertEquals(true, adminLogin.isShowDescription());
        Assert.assertEquals("notUniqueName", adminLogin.getName());
    }

    @Test
    public void executeFromManageItems() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftAdminLogin adminLogin = TestUtil.createAdminLogin(site);

        final SaveAdminLoginRequest request = new SaveAdminLoginRequest();
        request.setText("g");
        request.setShowDescription(true);
        request.setDescription("desc");
        request.setAdminLoginId(adminLogin.getId());

        final FunctionalWidgetInfo response = service.execute(request);

        Assert.assertNotNull(response);
        Assert.assertNull(response.getWidget());

        Assert.assertEquals("g", adminLogin.getText());
        Assert.assertEquals("desc", adminLogin.getDescription());
        Assert.assertEquals(true, adminLogin.isShowDescription());
    }

    @Test
    public void executeLong() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        final DraftAdminLogin adminLogin = new DraftAdminLogin();
        adminLogin.setSiteId(site.getSiteId());
        persistance.putItem(adminLogin);

        final WidgetItem widgetAdminLogin = TestUtil.createWidgetAdminLogin();
        widgetAdminLogin.setDraftItem(adminLogin);
        pageVersion.addWidget(widgetAdminLogin);

        final SaveAdminLoginRequest request = new SaveAdminLoginRequest();
        request.setText(TestUtil.createString(1000));
        request.setShowDescription(true);
        request.setDescription("desc");
        request.setAdminLoginId(adminLogin.getId());

        service.execute(request);

        Assert.assertEquals(250, ((DraftAdminLogin) widgetAdminLogin.getDraftItem()).getText().length());
    }

    @Test(expected = AdminLoginNotFoundException.class)
    public void executeWithNotFoundAdminLogin() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftAdminLogin adminLogin = TestUtil.createAdminLogin(site);

        final SaveAdminLoginRequest request = new SaveAdminLoginRequest();
        request.setText("g");
        request.setShowDescription(true);
        request.setDescription("desc");
        request.setAdminLoginId(-1);

        service.execute(request);
    }

    @Test(expected = WidgetNotFoundException.class)
    public void executeWithoutEditRight() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.VISITOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final WidgetItem widgetAdminLogin = TestUtil.createWidgetAdminLogin();
        pageVersion.addWidget(widgetAdminLogin);

        final DraftAdminLogin adminLogin = TestUtil.createAdminLogin(site);
        widgetAdminLogin.setDraftItem(adminLogin);

        final SaveAdminLoginRequest request = new SaveAdminLoginRequest();
        request.setText("g");
        request.setShowDescription(true);
        request.setDescription("desc");
        request.setAdminLoginId(adminLogin.getId());
        request.setWidgetId(widgetAdminLogin.getWidgetId());

        service.execute(request);
    }

    @Test(expected = WidgetNotFoundException.class)
    public void executeNotMy() throws IOException, ServletException {
        TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final WidgetItem widgetAdminLogin = TestUtil.createWidgetAdminLogin();
        pageVersion.addWidget(widgetAdminLogin);

        final DraftAdminLogin adminLogin = TestUtil.createAdminLogin(site);
        widgetAdminLogin.setDraftItem(adminLogin);

        final SaveAdminLoginRequest request = new SaveAdminLoginRequest();
        request.setText("g");
        request.setShowDescription(true);
        request.setDescription("desc");
        request.setAdminLoginId(adminLogin.getId());
        request.setWidgetId(widgetAdminLogin.getWidgetId());

        service.execute(request);
    }

    @Test(expected = WidgetNotFoundException.class)
    public void executeWithNotFound() throws IOException, ServletException {
        TestUtil.createUserAndLogin();

        final DraftAdminLogin adminLogin = new DraftAdminLogin();

        final SaveAdminLoginRequest request = new SaveAdminLoginRequest();
        request.setText(TestUtil.createString(1000));
        request.setShowDescription(true);
        request.setDescription("desc");
        request.setAdminLoginId(adminLogin.getId());
        request.setWidgetId(-1);

        service.execute(request);
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithNotLogin() throws IOException, ServletException {
        service.execute(new SaveAdminLoginRequest());
    }

    private final SaveAdminLoginService service = new SaveAdminLoginService();
    private final Persistance persistance = ServiceLocator.getPersistance();

}