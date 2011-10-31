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
package com.shroggle.logic;

import com.shroggle.entity.*;
import com.shroggle.util.ServiceLocator;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.Assert;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class SiteItemsManagerTest {

    @Test
    public void getDefaultNameWithoutItems() {
        final Site site = TestUtil.createSite();
        final String name = SiteItemsManager.getNextDefaultName(ItemType.BLOG, site.getSiteId());
        Assert.assertEquals("Blog1", name);
    }

    @Test
    public void getDefaultNameWithOneBig() {
        final Site site = TestUtil.createSite();
        final DraftBlog blog = TestUtil.createBlog(site);
        blog.setName("Blog2222222222");
        final String name = SiteItemsManager.getNextDefaultName(ItemType.BLOG, site.getSiteId());
        Assert.assertEquals("Blog1", name);
    }

    /*@Test
    public void getDefaultNameWithExisting() {
        final Site site = TestUtil.createSite();
        final DraftBlog blog = TestUtil.createBlog(site);
        blog.setName("Blog1");
        final String name = siteItemsManager.getNextDefaultName(ItemType.BLOG, site.getSiteId(), null, false);
        Assert.assertEquals("Blog2", name);
    }*/

    @Test
    public void getDefaultNameWithMax() {
        final Site site = TestUtil.createSite();
        final DraftBlog blog = TestUtil.createBlog(site);
        blog.setName("Blog" + Integer.MAX_VALUE);
        final String name = SiteItemsManager.getNextDefaultName(ItemType.BLOG, site.getSiteId());
        Assert.assertEquals("Blog1", name);
    }

    @Test
    public void getDefaultNameWithNullType() {
        final Site site = TestUtil.createSite();
        final DraftBlog blog = TestUtil.createBlog(site);
        blog.setName("Blog1");
        final String name = SiteItemsManager.getNextDefaultName(null, site.getSiteId());
        Assert.assertEquals("Undefined Item1", name);
    }

    @Test
    public void getDefaultNameWithoutItems_dontUseFirstNumber() {
        final Site site = TestUtil.createSite();
        final String name = SiteItemsManager.getNextDefaultName(ItemType.BLOG, site.getSiteId(), null, true);
        Assert.assertEquals("Blog", name);
    }

    @Test
    public void getDefaultNameWithExisting_dontUserFirstNumber() {
        final Site site = TestUtil.createSite();
        final DraftBlog blog = TestUtil.createBlog(site);
        blog.setName("Blog");
        final String name = SiteItemsManager.getNextDefaultName(ItemType.BLOG, site.getSiteId(), null, true);
        Assert.assertEquals("Blog2", name);
    }

    @Test
    public void getNextDefaultNameForFormExportTask() {
        final Site site = TestUtil.createSite();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        customForm.setSiteId(site.getSiteId());

        final FormExportTask formExportTask1 = new FormExportTask();
        formExportTask1.setFormId(customForm.getId());
        formExportTask1.setName("Form export1");
        ServiceLocator.getPersistance().putFormExportTask(formExportTask1);

        final FormExportTask formExportTask2 = new FormExportTask();
        formExportTask2.setFormId(1232);
        formExportTask2.setName("Form export2");
        ServiceLocator.getPersistance().putFormExportTask(formExportTask2);

        final FormExportTask formExportTask3 = new FormExportTask();
        formExportTask3.setFormId(customForm.getId());
        formExportTask3.setName("Form export3");
        ServiceLocator.getPersistance().putFormExportTask(formExportTask3);

        final String name = SiteItemsManager.getNextDefaultNameForFormExportTask(site.getSiteId());
        Assert.assertEquals("Form export2", name);
    }
}
