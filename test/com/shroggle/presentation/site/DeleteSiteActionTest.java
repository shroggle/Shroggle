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
import com.shroggle.exception.AttemptToDeleteSiteWithoutRightException;
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.User;
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.presentation.account.dashboard.DashboardAction;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import net.sourceforge.stripes.action.ActionBeanContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Artem Stasuk
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class DeleteSiteActionTest {

    @Before
    public void before() {
        action.setContext(new ActionBeanContext());
    }

    @Test
    public void execute() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        action.setSiteId(site.getSiteId());
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertNull(persistance.getSite(site.getSiteId()));
        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeWithoutLogin() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);

        action.setSiteId(site.getSiteId());
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertNotNull(persistance.getSite(site.getSiteId()));
        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeWithoutRight() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserAndLogin();

        action.setSiteId(site.getSiteId());
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertNotNull("Action delete site without right!", persistance.getSite(site.getSiteId()));
        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }
    
    @Test(expected = AttemptToDeleteSiteWithoutRightException.class)
    public void executeWithEditorRights() {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.EDITOR);
        TestUtil.loginUser(user);

        action.setSiteId(site.getSiteId());
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertNotNull("Action delete site without right!", persistance.getSite(site.getSiteId()));
        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeWithoutSite() {
        TestUtil.createUserAndLogin();

        action.setSiteId(1);
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private DeleteSiteAction action = new DeleteSiteAction();

}
