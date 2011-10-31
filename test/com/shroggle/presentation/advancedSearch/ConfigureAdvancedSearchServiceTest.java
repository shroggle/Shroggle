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

package com.shroggle.presentation.advancedSearch;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.DraftAdvancedSearch;
import com.shroggle.entity.Site;
import com.shroggle.entity.User;
import com.shroggle.entity.WidgetItem;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import junit.framework.Assert;
import junit.framework.TestCase;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class ConfigureAdvancedSearchServiceTest extends TestCase {

    private ConfigureAdvancedSearchService service = new ConfigureAdvancedSearchService();

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void testShowFromSiteEditPage() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        final Site site2 = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final PageManager page = TestUtil.createPageVersionAndPage(site);

        TestUtil.createCustomForm(site);
        TestUtil.createCustomForm(site2);

        TestUtil.createGallery(site);
        TestUtil.createGallery(site);
        TestUtil.createGallery(site2);

        final DraftAdvancedSearch existingAdvancedSearch = TestUtil.createAdvancedSearch(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        widget.setDraftItem(existingAdvancedSearch);
        page.addWidget(widget);

        service.show(widget.getWidgetId(), null);

        Assert.assertEquals(2, service.getAvailableGalleries().size());
        Assert.assertEquals(1, service.getAvailableForms().size());
        Assert.assertEquals(existingAdvancedSearch, service.getSelectedAdvancedSearch());
        Assert.assertEquals(widget, service.getWidget());
        Assert.assertEquals(site, service.getSite());
    }

    @Test
    public void testShowFromManageItems() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        final Site site2 = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        TestUtil.createCustomForm(site);
        TestUtil.createCustomForm(site2);

        TestUtil.createGallery(site);
        TestUtil.createGallery(site);
        TestUtil.createGallery(site2);

        final DraftAdvancedSearch existingAdvancedSearch = TestUtil.createAdvancedSearch(site);

        service.show(null, existingAdvancedSearch.getId());

        Assert.assertEquals(2, service.getAvailableGalleries().size());
        Assert.assertEquals(1, service.getAvailableForms().size());
        Assert.assertEquals(existingAdvancedSearch, service.getSelectedAdvancedSearch());
        Assert.assertEquals(null, service.getWidget());
        Assert.assertEquals(site, service.getSite());
    }

    @Test(expected = UserNotLoginedException.class)
    public void testShowWithoutLoginedUser() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final WidgetItem widgetItem = TestUtil.createWidgetItemWithPageAndPageVersion(site);

        service.show(widgetItem.getWidgetId(), null);
    }

    @Test(expected = WidgetNotFoundException.class)
    public void testShowWithoutWidget() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        TestUtil.createWidgetItemWithPageAndPageVersion(site);

        service.show(0, null);
    }

}
