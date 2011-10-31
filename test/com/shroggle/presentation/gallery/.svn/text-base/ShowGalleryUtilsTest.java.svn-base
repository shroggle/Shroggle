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
package com.shroggle.presentation.gallery;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.presentation.site.render.RenderContext;
import com.shroggle.util.ServiceLocator;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class ShowGalleryUtilsTest {

    private RenderContext renderContext;   
    private ShowGalleryUtils logic = new ShowGalleryUtils(SiteShowOption.INSIDE_APP);

    @Before
    public void before() {
        MockWebContext context = (MockWebContext) ServiceLocator.getWebContextGetter().get();
        context.setHttpServletRequest(new MockHttpServletRequest("", ""));
        renderContext = new RenderContext(context.getHttpServletRequest(),
                context.getHttpServletResponse(), context.getServletContext(), null, false);
    }


    @Test
    public void testCreateGalleryInnerHtml() throws Exception {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        DraftGallery gallery1 = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1");

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site1, "page1");
        WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion, gallery1.getId(), true, true);


        final String innerHtml = logic.createGalleryInnerHtml(gallery1, widgetGallery, renderContext);
        Assert.assertEquals("", innerHtml);
    }
}
