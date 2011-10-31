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

package com.shroggle.presentation.site.render.advancedSearch;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.AdvancedSearchNotFoundException;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.ContextStorage;
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
public class ResetAdvancedSearchServiceTest extends TestCase {

    private final ResetAdvancedSearchService service = new ResetAdvancedSearchService();
    private final ContextStorage contextStorage = ServiceLocator.getContextStorage();

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void testExecute() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftGallery gallery = TestUtil.createGallery(site);

        final DraftAdvancedSearch advancedSearch = TestUtil.createAdvancedSearch(site);
        advancedSearch.setGalleryId(gallery.getId());
        contextStorage.get().setAdvancedSearchRequest(advancedSearch);

        final WidgetItem widget = TestUtil.createWidgetItemWithPageAndPageVersion(site);
        widget.setDraftItem(advancedSearch);

        final ResetAdvancedSearchRequest request = new ResetAdvancedSearchRequest();
        request.setSiteShowOption(SiteShowOption.ON_USER_PAGES);
        request.setWidgetId(widget.getWidgetId());
        request.setAdvancedSearchId(advancedSearch.getId());

        final String advancedSearchHtml = service.execute(request);
        Assert.assertNotNull(advancedSearchHtml);
        Assert.assertNull(contextStorage.get().getAdvancedSearchRequestById(advancedSearch.getId()));
    }

    @Test
    public void executeWithoutAdvancedSearchInContext() throws Exception {
        final Site site = TestUtil.createSite();
        final WidgetItem widget = TestUtil.createWidgetItemWithPageAndPageVersion(site);
        final DraftAdvancedSearch advancedSearch = TestUtil.createAdvancedSearch(site);
        widget.setDraftItem(advancedSearch);
        final DraftGallery gallery = TestUtil.createGallery(site);
        advancedSearch.setGalleryId(gallery.getId());

        final ResetAdvancedSearchRequest request = new ResetAdvancedSearchRequest();
        request.setSiteShowOption(SiteShowOption.ON_USER_PAGES);
        request.setWidgetId(widget.getWidgetId());
        request.setAdvancedSearchId(advancedSearch.getId());

        final String advancedSearchHtml = service.execute(request);
        Assert.assertNotNull(advancedSearchHtml);
        Assert.assertNull(contextStorage.get().getAdvancedSearchRequestById(advancedSearch.getId()));
    }

    @Test(expected = AdvancedSearchNotFoundException.class)
    public void executeWithoutAdvancedSearch() throws Exception {
        final Site site = TestUtil.createSite();
        final WidgetItem widget = TestUtil.createWidgetItemWithPageAndPageVersion(site);

        final ResetAdvancedSearchRequest request = new ResetAdvancedSearchRequest();
        request.setSiteShowOption(SiteShowOption.ON_USER_PAGES);
        request.setWidgetId(widget.getWidgetId());
        request.setAdvancedSearchId(0);

        service.execute(request);
    }
    
    @Test(expected = WidgetNotFoundException.class)
    public void executeWithoutWidget() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftAdvancedSearch advancedSearch = TestUtil.createAdvancedSearch(site);

        final ResetAdvancedSearchRequest request = new ResetAdvancedSearchRequest();
        request.setSiteShowOption(SiteShowOption.ON_USER_PAGES);
        request.setWidgetId(0);
        request.setAdvancedSearchId(advancedSearch.getId());

         service.execute(request);
    }

}
