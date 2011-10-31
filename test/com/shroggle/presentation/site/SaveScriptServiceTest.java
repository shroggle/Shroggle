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
import com.shroggle.exception.ScriptNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author dmitry.solomadin
 */
@RunWith(TestRunnerWithMockServices.class)
public class SaveScriptServiceTest {

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void executeFromSiteEditPage() throws Exception {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        WidgetItem widget = TestUtil.createWidgetScript(pageVersion);

        SaveScriptRequest request = new SaveScriptRequest();
        request.setScriptItemId(widget.getDraftItem().getId());
        request.setWidgetId(widget.getWidgetId());
        request.setText("aaa");

        FunctionalWidgetInfo response = service.execute(request);

        Assert.assertNotNull(response);
        Assert.assertEquals(widget.getWidgetId(), response.getWidget().getWidgetId());

        Assert.assertEquals("aaa", ((DraftScript)widget.getDraftItem()).getText());
    }

    @Test
    public void executeFromManageItems() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftScript script = new DraftScript();
        script.setSiteId(site.getSiteId());
        persistance.putItem(script);

        SaveScriptRequest request = new SaveScriptRequest();
        request.setScriptItemId(script.getId());
        request.setText("aaa");

        FunctionalWidgetInfo response = service.execute(request);

        Assert.assertNotNull(response);
        Assert.assertNull(response.getWidget());

        Assert.assertEquals("aaa", script.getText());
    }

    @Test(expected = ScriptNotFoundException.class)
    public void executeWithoutScriptItem() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Widget widget = TestUtil.createWidgetItem(pageVersion);

        SaveScriptRequest request = new SaveScriptRequest();
        request.setWidgetId(widget.getWidgetId());
        request.setText("aaa");
        service.execute(request);
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() throws Exception {
        final PageManager pageVersion = TestUtil.createPageVersionPageAndSite();

        Widget widget = TestUtil.createWidgetItem();
        widget.setPosition(0);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        SaveScriptRequest request = new SaveScriptRequest();
        request.setWidgetId(widget.getWidgetId());
        service.execute(request);
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final SaveScriptService service = new SaveScriptService();

}
