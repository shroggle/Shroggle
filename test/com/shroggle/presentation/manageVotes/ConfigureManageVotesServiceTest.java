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
package com.shroggle.presentation.manageVotes;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.ManageVotesNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class ConfigureManageVotesServiceTest {

    private final ConfigureManageVotesService service = new ConfigureManageVotesService();

    @Before
    public void before() {
        final MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void executeFromSiteEditPage() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        final Site site2 = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final WidgetItem widgetManageVotes = TestUtil.createWidgetManageVotes(null);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.addWidget(widgetManageVotes);

        final DraftManageVotes manageVotes = TestUtil.createManageVotes(site);
        widgetManageVotes.setDraftItem(manageVotes);
        TestUtil.createManageVotes(site);

        final WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGallery.setCrossWidgetId(1001);
        final DraftGallery gallery1 = TestUtil.createGallery(site);
        widgetGallery.setDraftItem(gallery1);
        gallery1.setIncludesVotingModule(true);
        TestUtil.createGallery(site);
        TestUtil.createGallery(site2);

        service.execute(widgetManageVotes.getWidgetId(), null);

        Assert.assertEquals(site, service.getSite());
        Assert.assertNotNull(service.getSelectedManageVotes());
        Assert.assertNotNull(service.getWidget());
        Assert.assertEquals(1, service.getAvailableGalleriesWithWidgets().size());
    }

    @Test
    public void executeFromManageItems() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        final Site site2 = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final DraftManageVotes manageVotes = TestUtil.createManageVotes(site);

        final WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGallery.setCrossWidgetId(1001);
        final DraftGallery gallery1 = TestUtil.createGallery(site);
        widgetGallery.setDraftItem(gallery1);
        gallery1.setIncludesVotingModule(true);

        TestUtil.createGallery(site);
        TestUtil.createGallery(site2);

        service.execute(null, manageVotes.getId());

        Assert.assertEquals(site, service.getSite());
        Assert.assertEquals(manageVotes.getId(), service.getSelectedManageVotes().getId());
        Assert.assertNull(service.getWidget());
        Assert.assertEquals(1, service.getAvailableGalleriesWithWidgets().size());
    }

    @Test(expected = WidgetNotFoundException.class)
    public void executeWithNotFoundWidget() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        service.execute(-1, null);
    }

    @Test(expected = ManageVotesNotFoundException.class)
    public void executeWithWidgetWithoutItem() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final WidgetItem widgetManageVotes = TestUtil.createWidgetManageVotes(null);
        pageVersion.addWidget(widgetManageVotes);

        service.execute(widgetManageVotes.getWidgetId(), null);
    }

    @Test(expected = ManageVotesNotFoundException.class)
    public void executeWithNotFoundByFormId() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final WidgetItem widgetManageVotes = TestUtil.createWidgetManageVotes(null);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.addWidget(widgetManageVotes);

        widgetManageVotes.setDraftItem(null);

        service.execute(widgetManageVotes.getWidgetId(), 123);
    }
    
    @Test(expected = UserNotLoginedException.class)
    public void executeWithNotLoginedUser() throws Exception {
        service.execute(null, null);
    }

}
