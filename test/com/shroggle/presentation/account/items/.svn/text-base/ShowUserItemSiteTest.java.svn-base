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
package com.shroggle.presentation.account.items;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.entity.*;
import com.shroggle.util.ServiceLocator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class ShowUserItemSiteTest {

    @Test
    public void get() {
        DraftBlog blog = new DraftBlog();
        Site site = new Site();
        SiteOnItem siteOnItem = blog.createSiteOnItemRight(site);
        siteOnItem.setType(SiteOnItemRightType.READ);
        ServiceLocator.getPersistance().putSiteOnItem(siteOnItem);

        List<ShowUserItemSite> sites = ShowUserItemSite.get(blog, site);

        Assert.assertEquals(2, sites.size());
        Assert.assertNull(sites.get(0).getRightType());
        Assert.assertEquals(SiteOnItemRightType.READ, sites.get(1).getRightType());
    }

    @Test
    public void createRight() {
        Site site = new Site();
        site.setSiteId(33);
        site.setTitle("ff");
        site.setSubDomain("gg");
        SiteOnItem right = new SiteOnItem();
        right.getId().setSite(site);
        right.setType(SiteOnItemRightType.READ);
        ShowUserItemSite show = new ShowUserItemSite(right);

        Assert.assertEquals(SiteOnItemRightType.READ, show.getRightType());
        Assert.assertEquals("ff", show.getName());
        Assert.assertEquals("gg.shroggle.com", show.getUrl());
        Assert.assertEquals(site.getSiteId(), show.getSiteId());
    }

    @Test
    public void createWithSite() {
        Site site = new Site();
        site.setSiteId(33);
        site.setTitle("ff");
        site.setSubDomain("gg");
        ShowUserItemSite show = new ShowUserItemSite(site);

        Assert.assertNull(show.getRightType());
        Assert.assertEquals("ff", show.getName());
        Assert.assertEquals("gg.shroggle.com", show.getUrl());
        Assert.assertEquals(site.getSiteId(), show.getSiteId());
    }

    @Test
    public void compareMixed() {
        Site site = new Site();
        site.setSiteId(33);
        site.setTitle("ff");
        site.setSubDomain("gg");
        ShowUserItemSite show1 = new ShowUserItemSite(site);

        Site site1 = new Site();
        site1.setSiteId(33);
        site1.setTitle("ff");
        site1.setSubDomain("gg");
        SiteOnItem right = new SiteOnItem();
        right.getId().setSite(site1);
        right.setType(SiteOnItemRightType.READ);
        ShowUserItemSite show2 = new ShowUserItemSite(right);

        Assert.assertEquals(-1, show1.compareTo(show2));
    }

    @Test
    public void compareName() {
        Site site1 = new Site();
        site1.setSiteId(33);
        site1.setTitle("aff");
        site1.setSubDomain("gg");
        SiteOnItem right1 = new SiteOnItem();
        right1.getId().setSite(site1);
        right1.setType(SiteOnItemRightType.READ);
        ShowUserItemSite show1 = new ShowUserItemSite(site1);

        Site site2 = new Site();
        site2.setSiteId(33);
        site2.setTitle("ff");
        site2.setSubDomain("gg");
        SiteOnItem right2 = new SiteOnItem();
        right2.getId().setSite(site2);
        right2.setType(SiteOnItemRightType.READ);
        ShowUserItemSite show2 = new ShowUserItemSite(right2);

        Assert.assertEquals(-1, show1.compareTo(show2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createWithNullSite() {
        Site site = null;
        new ShowUserItemSite(site);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createWithNullRight() {
        SiteOnItem right = null;
        new ShowUserItemSite(right);
    }

}
