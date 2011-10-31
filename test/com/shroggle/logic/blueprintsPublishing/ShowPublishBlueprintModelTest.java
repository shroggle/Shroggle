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
package com.shroggle.logic.blueprintsPublishing;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.Page;
import com.shroggle.entity.Site;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.site.page.PageManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class ShowPublishBlueprintModelTest {

    @Test
    public void testGetDescription() throws Exception {
        TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        final SiteManager siteManager = new SiteManager(site);
        siteManager.getPublicBlueprintsSettings().setDescription("desc");

        final ShowPublishBlueprintModel model = new ShowPublishBlueprintModel(site.getSiteId(), true);
        Assert.assertEquals("desc", model.getDescription());
    }
    
    @Test
    public void testGetDescription_withNull() throws Exception {
        TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        final SiteManager siteManager = new SiteManager(site);
        siteManager.getPublicBlueprintsSettings().setDescription(null);

        final ShowPublishBlueprintModel model = new ShowPublishBlueprintModel(site.getSiteId(), true);
        Assert.assertEquals("", model.getDescription());
    }

    @Test
    public void testGetPages() throws Exception {
        TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        final Page page1 = TestUtil.createPage(site);
        final Page page2 = TestUtil.createPage(site);
        final Page page3 = TestUtil.createPage(site);

        new PageManager(page2).publish();
        page3.setSystem(true);

        final ShowPublishBlueprintModel model = new ShowPublishBlueprintModel(site.getSiteId(), true);
        Assert.assertEquals(1, model.getPages().size());
        Assert.assertEquals(page2.getPageId(), model.getPages().get(0).getPageId());
    }

    @Test
    public void testGetSiteId() throws Exception {
        TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();

        final ShowPublishBlueprintModel model = new ShowPublishBlueprintModel(site.getSiteId(), true);
        Assert.assertEquals(site.getSiteId(), model.getSiteId());
    }

    @Test
    public void testIsActivationMode() throws Exception {
        TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();

        final ShowPublishBlueprintModel model = new ShowPublishBlueprintModel(site.getSiteId(), true);
        Assert.assertEquals(true, model.isActivationMode());
    }

    @Test
    public void testIsActivationMode_false() throws Exception {
        TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();

        final ShowPublishBlueprintModel model = new ShowPublishBlueprintModel(site.getSiteId(), false);
        Assert.assertEquals(false, model.isActivationMode());
    }
}
