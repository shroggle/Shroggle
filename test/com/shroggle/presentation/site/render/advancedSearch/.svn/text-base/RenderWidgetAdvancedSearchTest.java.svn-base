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
import com.shroggle.entity.SiteShowOption;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.presentation.site.render.RenderContext;
import com.shroggle.presentation.site.render.childSiteRegistration.RenderChildSiteRegistrationService;
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
public class RenderWidgetAdvancedSearchTest extends TestCase {

    private RenderContext context;

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) new RenderChildSiteRegistrationService().getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        context = new RenderChildSiteRegistrationService().createRenderContext(false);
    }

    @Test
    public void testRenderWidgetAdvancedSearch() throws Exception {
        final Site site = TestUtil.createSite();
        final WidgetItem widget = TestUtil.createWidgetItemWithPageAndPageVersion(site);
        final DraftAdvancedSearch advancedSearch = TestUtil.createAdvancedSearch(site);
        widget.setDraftItem(advancedSearch);
        final DraftGallery gallery = TestUtil.createGallery(site);
        advancedSearch.setGalleryId(gallery.getId());

        final String html =
                RenderWidgetAdvancedSearch.renderWidgetAdvancedSearch(widget, SiteShowOption.ON_USER_PAGES, context);

        Assert.assertNotNull(html);
        Assert.assertEquals(widget, context.getRequest().getAttribute("widget"));
        Assert.assertEquals(SiteShowOption.ON_USER_PAGES, context.getRequest().getAttribute("siteShowOption"));
        Assert.assertEquals(advancedSearch, context.getRequest().getAttribute("advancedSearch"));
        Assert.assertNotNull(context.getRequest().getAttribute("galleryHtml"));
    }

    @Test
    public void testRenderWidgetAdvancedSearchForNonConfigured() throws Exception {
        final Site site = TestUtil.createSite();
        final WidgetItem widget = TestUtil.createWidgetItemWithPageAndPageVersion(site);

        final String html =
                RenderWidgetAdvancedSearch.renderWidgetAdvancedSearch(widget, SiteShowOption.ON_USER_PAGES, context);

        Assert.assertNotNull(html);
        Assert.assertNull(context.getRequest().getAttribute("widget"));
        Assert.assertNull(context.getRequest().getAttribute("siteShowOption"));
        Assert.assertNull(context.getRequest().getAttribute("advancedSearch"));
        Assert.assertNull(context.getRequest().getAttribute("galleryHtml"));
    }


}
