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
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class ShowHtmlAsPageActionTest {

    @Before
    public void before() {
        action.setContext(new ActionBeanContext());
        action.getContext().setRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void executeWithoutRight() throws IOException, ServletException {
        final Site site = TestUtil.createSite();

        action.setSiteId(site.getId());
        action.setHtml("<!-- MEDIA_BLOCK -->");

        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(1, site.getPages().size());
        Assert.assertEquals(ShowPageVersionAction.class, resolutionMock.getRedirectByAction());
        Assert.assertEquals(2, resolutionMock.getRedirectByActionParameters().length);
        final int pageId = (Integer) resolutionMock.getRedirectByActionParameters()[0].getValue();
        Assert.assertEquals(-1, pageId);
        Assert.assertEquals(SiteShowOption.INSIDE_APP, resolutionMock.getRedirectByActionParameters()[1].getValue());
    }

    @Test
    public void executeWithoutHtml() throws IOException, ServletException {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        action.setSiteId(site.getId());

        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(2, site.getPages().size());
        Assert.assertEquals(ShowPageVersionAction.class, resolutionMock.getRedirectByAction());
        Assert.assertEquals(2, resolutionMock.getRedirectByActionParameters().length);
        final int pageId = (Integer) resolutionMock.getRedirectByActionParameters()[0].getValue();
        final Page page = persistance.getPage(pageId);
        Assert.assertNotNull("Can't find created page: " + pageId, page);
        Assert.assertEquals("test", page.getPageSettings().getName());
        Assert.assertEquals("test", page.getPageSettings().getUrl());
        Assert.assertEquals(SiteShowOption.INSIDE_APP, resolutionMock.getRedirectByActionParameters()[1].getValue());
    }

    @Test
    public void execute() throws IOException, ServletException {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        action.setSiteId(site.getId());
        action.setHtml("<!-- MEDIA_BLOCK -->");

        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(2, site.getPages().size());
        Assert.assertEquals(ShowPageVersionAction.class, resolutionMock.getRedirectByAction());
        Assert.assertEquals(2, resolutionMock.getRedirectByActionParameters().length);
        final int pageId = (Integer) resolutionMock.getRedirectByActionParameters()[0].getValue();
        final Page page = persistance.getPage(pageId);
        Assert.assertNotNull("Can't find created page: " + pageId, page);
        Assert.assertEquals("test", page.getPageSettings().getName());
        Assert.assertEquals("test", page.getPageSettings().getUrl());
        Assert.assertEquals(SiteShowOption.INSIDE_APP, resolutionMock.getRedirectByActionParameters()[1].getValue());
    }

    @Test
    public void executeTwice() throws IOException, ServletException {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        action.setSiteId(site.getId());
        action.setHtml("<!-- MEDIA_BLOCK -->");

        action.execute();
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(2, site.getPages().size());
        Assert.assertEquals(ShowPageVersionAction.class, resolutionMock.getRedirectByAction());
        Assert.assertEquals(2, resolutionMock.getRedirectByActionParameters().length);
        final int pageId = (Integer) resolutionMock.getRedirectByActionParameters()[0].getValue();
        final Page page = persistance.getPage(pageId);
        Assert.assertNotNull("Can't find created page: " + pageId, page);
        Assert.assertEquals("test", page.getPageSettings().getName());
        Assert.assertEquals("test", page.getPageSettings().getUrl());
        Assert.assertEquals(SiteShowOption.INSIDE_APP, resolutionMock.getRedirectByActionParameters()[1].getValue());
    }

    @Test
    public void executeWithoutSiteId() throws IOException, ServletException {
        TestUtil.createUserAndLogin();

        action.setHtml("<!-- MEDIA_BLOCK -->");

        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(ShowPageVersionAction.class, resolutionMock.getRedirectByAction());
        Assert.assertEquals(2, resolutionMock.getRedirectByActionParameters().length);
        final int pageId = (Integer) resolutionMock.getRedirectByActionParameters()[0].getValue();
        Assert.assertEquals(-1, pageId);
        Assert.assertEquals(SiteShowOption.INSIDE_APP, resolutionMock.getRedirectByActionParameters()[1].getValue());
    }

    private final ShowHtmlAsPageAction action = new ShowHtmlAsPageAction();
    private final Persistance persistance = ServiceLocator.getPersistance();

}
