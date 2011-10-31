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

package com.shroggle.logic.paginator;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.ItemType;
import com.shroggle.entity.Site;
import com.shroggle.entity.User;
import com.shroggle.logic.user.items.UserItemsInfo;
import com.shroggle.logic.user.items.UserItemsProvider;
import com.shroggle.logic.user.items.UserItemsSortType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class PaginatorTest {

    @Test
    public void testConsistency() {
        List<UserItemsInfo> items = createUserAndUserItems(54);

        final Paginator paginator = new Paginator(items);
        Assert.assertTrue(paginator.shouldBePaginated());

        List<UserItemsInfo> itemsOnFirstPage = (List<UserItemsInfo>) paginator.getItems();
        for (int i = 0; i < itemsOnFirstPage.size(); i++) {
            UserItemsInfo itemOnFirstPage = itemsOnFirstPage.get(i);
            UserItemsInfo item = items.get(i);
            Assert.assertEquals(itemOnFirstPage, item);
        }
        Assert.assertEquals(1, paginator.getPageNumber());
        Assert.assertEquals(3, paginator.getPagesCount());
    }

    @Test
    public void testGetPageCount_WITH_ONE_PAGE() {
        List<UserItemsInfo> items = createUserAndUserItems(1);
        Paginator paginator = new Paginator(items);

        Assert.assertEquals(1, paginator.getPagesCount());

        items = createUserAndUserItems(0);
        paginator = new Paginator(items);

        Assert.assertEquals(1, paginator.getPagesCount());
    }

    @Test
    public void testGetPageCount_WITH_MORE_THAN_ONE_PAGE() {
        List<UserItemsInfo> items = createUserAndUserItems(54);
        Paginator paginator = new Paginator(items);

        Assert.assertEquals(3, paginator.getPagesCount());

        items = createUserAndUserItems(41);
        paginator = new Paginator(items);

        Assert.assertEquals(3, paginator.getPagesCount());

        items = createUserAndUserItems(40);
        paginator = new Paginator(items);

        Assert.assertEquals(2, paginator.getPagesCount());
    }

    @Test
    public void testGetCurrentPage() {
        List<UserItemsInfo> items = createUserAndUserItems(54);
        Paginator paginator = new Paginator(items);

        Assert.assertEquals(3, paginator.getPagesCount());
        Assert.assertEquals(1, paginator.getPageNumber());
        List<UserItemsInfo> itemsOnFirstPage = (List<UserItemsInfo>) paginator.getItems();
        for (int i = 0; i < itemsOnFirstPage.size(); i++) {
            UserItemsInfo itemOnFirstPage = itemsOnFirstPage.get(i);
            UserItemsInfo item = items.get(i);
            Assert.assertEquals(itemOnFirstPage, item);
        }

        //On first page, go to next = second.
        itemsOnFirstPage = (List<UserItemsInfo>) paginator.setPageNumber(paginator.getPageNumber() + 1).getItems();
        Assert.assertEquals(2, paginator.getPageNumber());
        for (int i = 0; i < itemsOnFirstPage.size(); i++) {
            UserItemsInfo itemOnFirstPage = itemsOnFirstPage.get(i);
            UserItemsInfo item = items.get(i + 20);
            Assert.assertEquals(itemOnFirstPage, item);
        }

        //On second page, go to next = third.
        itemsOnFirstPage = (List<UserItemsInfo>) paginator.setPageNumber(paginator.getPageNumber() + 1).getItems();
        Assert.assertEquals(3, paginator.getPageNumber());
        for (int i = 0; i < itemsOnFirstPage.size(); i++) {
            UserItemsInfo itemOnFirstPage = itemsOnFirstPage.get(i);
            UserItemsInfo item = items.get(i + 40);
            Assert.assertEquals(itemOnFirstPage, item);
        }

        //On third page, go to next = .. still third.
        itemsOnFirstPage = (List<UserItemsInfo>) paginator.setPageNumber(paginator.getPageNumber() + 1).getItems();
        Assert.assertEquals(3, paginator.getPageNumber());
        for (int i = 0; i < itemsOnFirstPage.size(); i++) {
            UserItemsInfo itemOnFirstPage = itemsOnFirstPage.get(i);
            UserItemsInfo item = items.get(i + 40);
            Assert.assertEquals(itemOnFirstPage, item);
        }

        //On third page, go to prev = second.
        itemsOnFirstPage = (List<UserItemsInfo>) paginator.setPageNumber(paginator.getPageNumber() - 1).getItems();
        Assert.assertEquals(2, paginator.getPageNumber());
        for (int i = 0; i < itemsOnFirstPage.size(); i++) {
            UserItemsInfo itemOnFirstPage = itemsOnFirstPage.get(i);
            UserItemsInfo item = items.get(i + 20);
            Assert.assertEquals(itemOnFirstPage, item);
        }

        //On second page, go to prev = first.
        itemsOnFirstPage = (List<UserItemsInfo>) paginator.setPageNumber(paginator.getPageNumber() - 1).getItems();
        Assert.assertEquals(1, paginator.getPageNumber());
        for (int i = 0; i < itemsOnFirstPage.size(); i++) {
            UserItemsInfo itemOnFirstPage = itemsOnFirstPage.get(i);
            UserItemsInfo item = items.get(i);
            Assert.assertEquals(itemOnFirstPage, item);
        }

        //On first page, go to prev = .. still first.
        itemsOnFirstPage = (List<UserItemsInfo>) paginator.setPageNumber(paginator.getPageNumber() - 1).getItems();
        Assert.assertEquals(1, paginator.getPageNumber());
        for (int i = 0; i < itemsOnFirstPage.size(); i++) {
            UserItemsInfo itemOnFirstPage = itemsOnFirstPage.get(i);
            UserItemsInfo item = items.get(i);
            Assert.assertEquals(itemOnFirstPage, item);
        }

        //Go to third page.
        itemsOnFirstPage = (List<UserItemsInfo>) paginator.setPageNumber(3).getItems();
        Assert.assertEquals(3, paginator.getPageNumber());
        for (int i = 0; i < itemsOnFirstPage.size(); i++) {
            UserItemsInfo itemOnFirstPage = itemsOnFirstPage.get(i);
            UserItemsInfo item = items.get(i + 40);
            Assert.assertEquals(itemOnFirstPage, item);
        }

        //Go to 54 page (not exists).
        itemsOnFirstPage = (List<UserItemsInfo>) paginator.setPageNumber(54).getItems();
        Assert.assertEquals(3, paginator.getPageNumber());
        for (int i = 0; i < itemsOnFirstPage.size(); i++) {
            UserItemsInfo itemOnFirstPage = itemsOnFirstPage.get(i);
            UserItemsInfo item = items.get(i + 40);
            Assert.assertEquals(itemOnFirstPage, item);
        }
    }

    @Test
    public void testIsShouldBePaginated_WITH_CORRECT_NUMBER_OF_ITEMS() {
        List<UserItemsInfo> items = createUserAndUserItems(31);

        final Paginator paginator = new Paginator(items);
        Assert.assertTrue(paginator.shouldBePaginated());
    }

    @Test
    public void testIsShouldBePaginated_WITH_INCORRECT_NUMBER_OF_ITEMS() {
        List<UserItemsInfo> items = createUserAndUserItems(29);

        final Paginator paginator = new Paginator(items);
        Assert.assertFalse(paginator.shouldBePaginated());
    }

    private List<UserItemsInfo> createUserAndUserItems(final int count) {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        for (int i = 0; i < count; i++) {
            TestUtil.createBlog(site);
        }

        return new UserItemsProvider().execute(ItemType.BLOG, UserItemsSortType.NAME, false, -1, null);
    }
}
