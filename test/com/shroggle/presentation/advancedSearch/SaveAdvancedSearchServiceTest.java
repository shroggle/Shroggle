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
import com.shroggle.entity.*;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.presentation.site.FunctionalWidgetInfo;
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
public class SaveAdvancedSearchServiceTest extends TestCase {

    private SaveAdvancedSearchService service = new SaveAdvancedSearchService();

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }
    
    @Test
    public void testExecute() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final WidgetItem widgetAdvancedSearch = TestUtil.createWidgetItemWithPageAndPageVersion(site);
        final DraftAdvancedSearch advancedSearch = TestUtil.createAdvancedSearch(site);
        widgetAdvancedSearch.setDraftItem(advancedSearch);
        final SaveAdvancedSearchRequest request = new SaveAdvancedSearchRequest();
        request.setAdvancedSearchId(advancedSearch.getId());
        request.setWidgetId(widgetAdvancedSearch.getWidgetId());
        request.setName("test");

        final FunctionalWidgetInfo functionalWidgetInfo = service.execute(request);
        
        Assert.assertEquals(widgetAdvancedSearch.getWidgetId(), functionalWidgetInfo.getWidget().getWidgetId());
        Assert.assertEquals("<span style=\"word-wrap:break-word; vertical-align:top;\"" +
                " id=\"widgetNameSpan1001widget\">Advanced Search: test</span>", functionalWidgetInfo.getWidgetInfo());
    }

    @Test(expected = UserNotLoginedException.class)
    public void testExecuteWithoutLoginedUser() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final WidgetItem widgetAdvancedSearch = TestUtil.createWidgetItemWithPageAndPageVersion(site);
        final DraftAdvancedSearch advancedSearch = TestUtil.createAdvancedSearch(site);
        final SaveAdvancedSearchRequest request = new SaveAdvancedSearchRequest();
        request.setAdvancedSearchId(advancedSearch.getId());
        request.setWidgetId(widgetAdvancedSearch.getWidgetId());
        request.setName("test");

        service.execute(request);
    }
    
    @Test(expected = UserNotLoginedException.class)
    public void testExecuteWithoutWidget() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final DraftAdvancedSearch advancedSearch = TestUtil.createAdvancedSearch(site);
        final SaveAdvancedSearchRequest request = new SaveAdvancedSearchRequest();
        request.setAdvancedSearchId(advancedSearch.getId());
        request.setWidgetId(0);
        request.setName("test");

        service.execute(request);
    }
}
