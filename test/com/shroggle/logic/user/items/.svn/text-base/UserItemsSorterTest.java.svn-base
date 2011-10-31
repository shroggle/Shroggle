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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@RunWith(TestRunnerWithMockServices.class)
public class UserItemsSorterTest {

    private final UserItemsSorter sorter = new UserItemsSorter();

    @Test
    public void testExecute_FOR_BLOG_SORT_BY_NAME() {
        Site site = TestUtil.createSite();

        final DraftBlog blog1 = TestUtil.createBlog(site);
        blog1.setName("c");
        final ItemManager blogManager1 = new ItemManager(blog1);

        final DraftBlog blog2 = TestUtil.createBlog(site);
        blog2.setName("b");
        final ItemManager blogManager2 = new ItemManager(blog2);

        final DraftBlog blog3 = TestUtil.createBlog(site);
        blog3.setName("a");
        final ItemManager blogManager3 = new ItemManager(blog3);

        final List<ItemManager> items = new ArrayList<ItemManager>();
        items.add(blogManager1);
        items.add(blogManager2);
        items.add(blogManager3);

        final List<ItemManager> sortedItems = sorter.execute(items, UserItemsSortType.NAME, false);
        Assert.assertEquals(blogManager3, sortedItems.get(0));
        Assert.assertEquals(blogManager2, sortedItems.get(1));
        Assert.assertEquals(blogManager1, sortedItems.get(2));
    }

    @Test
    public void testExecute_FOR_BLOG_SORT_BY_NAME_DESC() {
        Site site = TestUtil.createSite();

        final DraftBlog blog1 = TestUtil.createBlog(site);
        blog1.setName("c");
        final ItemManager blogManager1 = new ItemManager(blog1);

        final DraftBlog blog2 = TestUtil.createBlog(site);
        blog2.setName("b");
        final ItemManager blogManager2 = new ItemManager(blog2);

        final DraftBlog blog3 = TestUtil.createBlog(site);
        blog3.setName("a");
        final ItemManager blogManager3 = new ItemManager(blog3);

        final List<ItemManager> items = new ArrayList<ItemManager>();
        items.add(blogManager1);
        items.add(blogManager2);
        items.add(blogManager3);

        final List<ItemManager> sortedItems = sorter.execute(items, UserItemsSortType.NAME, true);
        Assert.assertEquals(blogManager1, sortedItems.get(0));
        Assert.assertEquals(blogManager2, sortedItems.get(1));
        Assert.assertEquals(blogManager3, sortedItems.get(2));
    }

    @Test
    public void testExecute_FOR_BLOG_SORT_BY_DATE_CREATED() {
        Site site = TestUtil.createSite();

        final DraftBlog blog1 = TestUtil.createBlog(site);
        blog1.setName("c");
        blog1.setCreated(new Date(new Date().getTime() + 1000));
        final ItemManager blogManager1 = new ItemManager(blog1);

        final DraftBlog blog2 = TestUtil.createBlog(site);
        blog2.setName("b");
        blog2.setCreated(new Date(new Date().getTime() + 2000));

        final ItemManager blogManager2 = new ItemManager(blog2);

        final DraftBlog blog3 = TestUtil.createBlog(site);
        blog3.setName("a");
        blog3.setCreated(new Date(new Date().getTime()));
        final ItemManager blogManager3 = new ItemManager(blog3);

        final List<ItemManager> items = new ArrayList<ItemManager>();
        items.add(blogManager2);
        items.add(blogManager1);
        items.add(blogManager3);

        final List<ItemManager> sortedItems = sorter.execute(items, UserItemsSortType.DATE_CREATED, false);
        Assert.assertEquals(blogManager3, sortedItems.get(0));
        Assert.assertEquals(blogManager1, sortedItems.get(1));
        Assert.assertEquals(blogManager2, sortedItems.get(2));
    }

    @Test
    public void testExecute_FOR_BLOG_SORT_BY_DATE_UPDATED() {
        Site site = TestUtil.createSite();

        final DraftBlog blog1 = TestUtil.createBlog(site);
        blog1.setName("c");
        final ItemManager blogManager1 = new ItemManager(blog1);

        final DraftBlog blog2 = TestUtil.createBlog(site);
        blog2.setName("b");
        final ItemManager blogManager2 = new ItemManager(blog2);

        final DraftBlog blog3 = TestUtil.createBlog(site);
        blog3.setName("a");
        final ItemManager blogManager3 = new ItemManager(blog3);

        final BlogPost blogPost1 = TestUtil.createBlogPost("qwe", new Date(new Date().getTime() + 2000));
        blog2.addBlogPost(blogPost1);

        final BlogPost blogPost2 = TestUtil.createBlogPost("qwe", new Date(new Date().getTime() + 1000));
        blog1.addBlogPost(blogPost2);

        final List<ItemManager> items = new ArrayList<ItemManager>();
        items.add(blogManager1);
        items.add(blogManager2);
        items.add(blogManager3);

        final List<ItemManager> sortedItems = sorter.execute(items, UserItemsSortType.DATE_UPDATED, false);
        Assert.assertEquals(blogManager3, sortedItems.get(0));
        Assert.assertEquals(blogManager1, sortedItems.get(1));
        Assert.assertEquals(blogManager2, sortedItems.get(2));
    }

    @Test
    public void testExecute_FOR_BLOG_SORT_BY_RECORD_COUNT() {
        Site site = TestUtil.createSite();

        final DraftBlog blog1 = TestUtil.createBlog(site);
        blog1.setName("c");
        final ItemManager blogManager1 = new ItemManager(blog1);

        final DraftBlog blog2 = TestUtil.createBlog(site);
        blog2.setName("b");
        final ItemManager blogManager2 = new ItemManager(blog2);

        final DraftBlog blog3 = TestUtil.createBlog(site);
        blog3.setName("a");
        final ItemManager blogManager3 = new ItemManager(blog3);

        final BlogPost blogPost1 = TestUtil.createBlogPost("qwe", new Date(new Date().getTime() + 2000));
        blog2.addBlogPost(blogPost1);

        final BlogPost blogPost2 = TestUtil.createBlogPost("qwe", new Date(new Date().getTime() + 1000));
        blog1.addBlogPost(blogPost2);

        final BlogPost blogPost3 = TestUtil.createBlogPost("qwe", new Date(new Date().getTime() + 1000));
        blog1.addBlogPost(blogPost3);

        final List<ItemManager> items = new ArrayList<ItemManager>();
        items.add(blogManager1);
        items.add(blogManager2);
        items.add(blogManager3);

        final List<ItemManager> sortedItems = sorter.execute(items, UserItemsSortType.RECORDS_NUMBER, false);
        Assert.assertEquals(blogManager3, sortedItems.get(0));
        Assert.assertEquals(blogManager2, sortedItems.get(1));
        Assert.assertEquals(blogManager1, sortedItems.get(2));
    }

    // todo fix. Dmitry Solomadin
    @Ignore
    @Test
    public void testExecute_FOR_FORMS_SORT_BY_FORM_TYPE() {
        Site site = TestUtil.createSite();

        // displayed type = ACCOUNT_REGISTRATION
        final DraftRegistrationForm regForm = TestUtil.createRegistrationForm(site);
        final ItemManager regFormManager = new ItemManager(regForm);

        // displayed type = FORMS & REGISTRATIONS
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final ItemManager customFormManager = new ItemManager(customForm);

        // displayed type = CONTACT US
        final DraftContactUs contactUsForm = TestUtil.createContactUs(site);
        final ItemManager contactUsFormManager = new ItemManager(contactUsForm);


        final List<ItemManager> items = new ArrayList<ItemManager>();
        items.add(contactUsFormManager);
        items.add(customFormManager);
        items.add(regFormManager);

        final List<ItemManager> sortedItems = sorter.execute(items, UserItemsSortType.ITEM_TYPE, false);
        Assert.assertEquals(regFormManager, sortedItems.get(0));
        Assert.assertEquals(contactUsFormManager, sortedItems.get(1));
        Assert.assertEquals(customFormManager, sortedItems.get(2));
    }

}
