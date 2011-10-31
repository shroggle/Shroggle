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
package com.shroggle.logic.widget;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.DraftBlog;
import com.shroggle.entity.ItemType;
import com.shroggle.entity.Site;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Author: dmitry.solomadin
 * Date: 13.04.2009
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class DefaultNameGetterTest {

    private final DefaultNameGetterService defaultNameGetterService = new DefaultNameGetterService();

    @Test
    public void test() {
        final Site site = TestUtil.createSite();
        final DraftBlog blog1 = TestUtil.createBlog(site);
        blog1.setName("Blog1");
        final DraftBlog blog2 = TestUtil.createBlog(site);
        blog2.setName("Blog2");
        final DraftBlog blog3 = TestUtil.createBlog(site);
        blog3.setName("Blogggg");

        Assert.assertEquals("Blog3", defaultNameGetterService.getNextDefaultName(ItemType.BLOG, site.getSiteId()));

        //

        blog1.setName("Blog");
        blog2.setName("Blogg");
        blog3.setName("Bloggg");

        Assert.assertEquals("Blog1", defaultNameGetterService.getNextDefaultName(ItemType.BLOG, site.getSiteId()));

        //

        blog1.setName("Blog1");
        blog2.setName("Blog2");
        blog3.setName("Blog4");

        Assert.assertEquals("Blog3", defaultNameGetterService.getNextDefaultName(ItemType.BLOG, site.getSiteId()));
    }

    @Test
    public void testWithoutSiteId() {
        Assert.assertEquals("", defaultNameGetterService.getNextDefaultName(ItemType.BLOG, null));
    }

}
