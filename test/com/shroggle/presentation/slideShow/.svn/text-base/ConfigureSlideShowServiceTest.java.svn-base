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
package com.shroggle.presentation.slideShow;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.directwebremoting.annotations.RemoteMethod;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@RunWith(TestRunnerWithMockServices.class)
public class ConfigureSlideShowServiceTest {

    @Test
    public void execute() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftSlideShow slideShow = TestUtil.createSlideShow(site);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));

        final DraftForm formWithoutImages = TestUtil.createCustomForm(site);
        formWithoutImages.setFormItems(new ArrayList<DraftFormItem>());
        final DraftForm formWithImages = TestUtil.createCustomForm(site);
        formWithImages.setFormItems(new ArrayList<DraftFormItem>(){{
            add(TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, formWithImages, 0));
        }});

        service.execute(null, slideShow.getId());
        Assert.assertEquals(slideShow, service.getSlideShow());
        Assert.assertNull(service.getWidgetTitle());
        Assert.assertEquals(site.getSiteId(), service.getSite().getSiteId());
        Assert.assertEquals(1, service.getFormManagers().size());
    }

    @Test
    public void executeFromSiteEditPage() throws Exception {
        final Site site = TestUtil.createSite();
        final PageManager page = TestUtil.createPageVersionAndPage(site);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftSlideShow slideShow = TestUtil.createSlideShow(site);

        WidgetItem slideShowWidget = TestUtil.createWidgetItem();
        slideShowWidget.setDraftItem(slideShow);
        page.addWidget(slideShowWidget);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));

        service.execute(slideShowWidget.getWidgetId(), null);
        Assert.assertEquals(slideShow, service.getSlideShow());
        Assert.assertNotNull(service.getWidgetTitle());
        Assert.assertEquals(site.getSiteId(), service.getSite().getSiteId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void executeFromSiteEditPageWithWidgetWithoutItem() throws Exception {
        final Site site = TestUtil.createSite();
        final PageManager page = TestUtil.createPageVersionAndPage(site);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        WidgetItem slideShowWidget = TestUtil.createWidgetItem();
        page.addWidget(slideShowWidget);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));

        service.execute(slideShowWidget.getWidgetId(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void executeWithBothWidgetAndSlideShowIdsEmpty() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftSlideShow slideShow = new DraftSlideShow();
        slideShow.setSiteId(site.getSiteId());
        slideShow.setName("aa");
        persistance.putItem(slideShow);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));

        service.execute(null, null);
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWihoutLogin() throws Exception {
        service.execute(null, -1);
    }

    private final ConfigureSlideShowService service = new ConfigureSlideShowService();
    private final Persistance persistance = ServiceLocator.getPersistance();

}
