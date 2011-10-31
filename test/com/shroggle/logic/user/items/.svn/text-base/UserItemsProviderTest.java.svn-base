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

package com.shroggle.logic.user.items;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.item.ItemManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class UserItemsProviderTest {

    @Test
    public void testExecute() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        final Site site2 = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final DraftBlog blog1 = TestUtil.createBlog(site);
        blog1.setName("b");
        final DraftBlog blog2 = TestUtil.createBlog(site);
        blog2.setName("a");

        TestUtil.createBlog(site2);

        List<UserItemsInfo> items = new UserItemsProvider().execute(ItemType.BLOG, UserItemsSortType.NAME, false, -1, null);
        Assert.assertEquals(2, items.size());
        Assert.assertEquals("a", items.get(0).getName());
        Assert.assertEquals("b", items.get(1).getName());
    }

    @Test
    public void testExecuteWithFormAndSiteFilter() {
        final User user = TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final Site site2 = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site2);

        final DraftCustomForm customForm1 = TestUtil.createCustomForm(site);
        customForm1.setName("b");
        final DraftCustomForm customForm2 = TestUtil.createCustomForm(site);
        customForm2.setName("a");
        final DraftCustomForm customForm3 = TestUtil.createCustomForm(site2);
        customForm3.setName("a");
        TestUtil.createChildSiteRegistration(site);

        List<UserItemsInfo> items = new UserItemsProvider().execute(ItemType.CUSTOM_FORM, UserItemsSortType.NAME, false, site.getSiteId(), null);
        Assert.assertEquals(2, items.size());
        Assert.assertEquals("a", items.get(0).getName());
        Assert.assertEquals("b", items.get(1).getName());
    }

    @Test
    public void testConstructInfo() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final DraftBlog blog0 = TestUtil.createBlog(site);
        blog0.setName("a");
        final Date blogCreatedDate = new Date();
        blog0.setCreated(blogCreatedDate);

        final BlogPost blogPost1 = TestUtil.createBlogPost("asd");
        blog0.addBlogPost(blogPost1);

        final Date blogPostCreatedDate = new Date();
        final BlogPost blogPost2 = TestUtil.createBlogPost("qwe", blogPostCreatedDate);
        blog0.addBlogPost(blogPost2);

        List<UserItemsInfo> items = new UserItemsProvider().constructInfos(Arrays.asList(new ItemManager(blog0)));
        Assert.assertEquals(1, items.size());
        Assert.assertEquals("a", items.get(0).getName());
        Assert.assertEquals(blogCreatedDate, items.get(0).getItemCreatedDate());
        Assert.assertEquals(blogPostCreatedDate, items.get(0).getItemUpdatedDate());
        Assert.assertEquals(2, items.get(0).getRecordCount());
    }

    @Test
    public void testConstructInfo_froGallery() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final DraftGallery gallery = TestUtil.createGallery(site);
        gallery.setName("a");


        DraftForm form = TestUtil.createContactUsForm();
        TestUtil.createFilledForm(form.getFormId());
        TestUtil.createFilledForm(form.getFormId());
        TestUtil.createFilledForm(form.getFormId());
        
        gallery.setFormId1(form.getFormId());


        List<UserItemsInfo> items = new UserItemsProvider().constructInfos(Arrays.asList(new ItemManager(gallery)));
        Assert.assertEquals(1, items.size());
        Assert.assertEquals("a", items.get(0).getName());
        Assert.assertEquals(3, items.get(0).getRecordCount());
    }
}
