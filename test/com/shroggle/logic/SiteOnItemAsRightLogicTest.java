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

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import com.shroggle.entity.*;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class SiteOnItemAsRightLogicTest {

    @Test
    public void isDeletable() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final DraftBlog blog = new DraftBlog();
        persistance.putItem(blog);

        final SiteOnItem siteOnItemRight = blog.createSiteOnItemRight(site);
        persistance.putSiteOnItem(siteOnItemRight);

        final SiteOnItemManager siteOnItemManager = new SiteOnItemAsRightManager(siteOnItemRight);
        Assert.assertTrue(siteOnItemManager.isDeletable());
    }

    @Test
    public void isDeletableNotAdministrator() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.EDITOR);
        final DraftBlog blog = new DraftBlog();
        persistance.putItem(blog);

        final SiteOnItem siteOnItemRight = blog.createSiteOnItemRight(site);
        persistance.putSiteOnItem(siteOnItemRight);

        final SiteOnItemManager siteOnItemManager = new SiteOnItemAsRightManager(siteOnItemRight);
        Assert.assertFalse(siteOnItemManager.isDeletable());
    }

    @Test
    public void isDeletableNotAcive() {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUserAndLogin();
        final UserOnSiteRight userOnSiteRight = TestUtil.createUserOnSiteRightActive(
                user, site, SiteAccessLevel.ADMINISTRATOR);
        userOnSiteRight.setActive(false);

        final DraftBlog blog = new DraftBlog();
        persistance.putItem(blog);

        final SiteOnItem siteOnItemRight = blog.createSiteOnItemRight(site);
        persistance.putSiteOnItem(siteOnItemRight);

        final SiteOnItemManager siteOnItemManager = new SiteOnItemAsRightManager(siteOnItemRight);
        Assert.assertFalse(siteOnItemManager.isDeletable());
    }

    @Test
    public void isDeletableOverOwnerSite() {
        final Site ownerSite = TestUtil.createSite();
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUserAndLogin();
        TestUtil.createUserOnSiteRightActive(user, ownerSite, SiteAccessLevel.ADMINISTRATOR);

        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(ownerSite.getSiteId());
        persistance.putItem(blog);

        final SiteOnItem siteOnItemRight = blog.createSiteOnItemRight(site);
        persistance.putSiteOnItem(siteOnItemRight);

        final SiteOnItemManager siteOnItemManager = new SiteOnItemAsRightManager(siteOnItemRight);
        Assert.assertTrue(siteOnItemManager.isDeletable());
    }

    @Test
    public void isDeletableOverOwnerNotFoundSite() {
        final Site ownerSite = TestUtil.createSite();
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUserAndLogin();
        TestUtil.createUserOnSiteRightActive(user, ownerSite, SiteAccessLevel.ADMINISTRATOR);
        persistance.removeSite(ownerSite);

        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(ownerSite.getSiteId());
        persistance.putItem(blog);

        final SiteOnItem siteOnItemRight = blog.createSiteOnItemRight(site);
        persistance.putSiteOnItem(siteOnItemRight);

        final SiteOnItemManager siteOnItemManager = new SiteOnItemAsRightManager(siteOnItemRight);
        Assert.assertFalse(siteOnItemManager.isDeletable());
    }

    @Test
    public void isDeletableOverOwnerSiteNotAdministrator() {
        final Site ownerSite = TestUtil.createSite();
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUserAndLogin();
        TestUtil.createUserOnSiteRightActive(user, ownerSite, SiteAccessLevel.EDITOR);

        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(ownerSite.getSiteId());
        persistance.putItem(blog);

        final SiteOnItem siteOnItemRight = blog.createSiteOnItemRight(site);
        persistance.putSiteOnItem(siteOnItemRight);

        final SiteOnItemManager siteOnItemManager = new SiteOnItemAsRightManager(siteOnItemRight);
        Assert.assertFalse(siteOnItemManager.isDeletable());
    }

    private final Persistance persistance = ServiceLocator.getPersistance();

}