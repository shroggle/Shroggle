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
import com.shroggle.entity.DraftCustomForm;
import com.shroggle.entity.ItemType;
import com.shroggle.entity.Site;
import com.shroggle.entity.User;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class UserItemsFilterTest {

    @Test
    public void testFilter() {
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
        TestUtil.createChildSiteRegistration(site);

        final UserManager userManager = new UsersManager().getLogined();
        final UserRightManager userRightManager = userManager.getRight();
        final List<ItemManager> items =
                ItemManager.siteItemsToManagers(userRightManager.getSiteItemsForView(ItemType.CUSTOM_FORM));
        Assert.assertEquals(3, items.size());

        final List<ItemManager> filteredItems =
                userItemsFilter.filter(items, site.getSiteId(), null);
        Assert.assertEquals(2, filteredItems.size());
    }

    @Test
    public void testFilterWithNoFilter() {
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
        TestUtil.createChildSiteRegistration(site);

        final UserManager userManager = new UsersManager().getLogined();
        final UserRightManager userRightManager = userManager.getRight();
        final List<ItemManager> items =
                ItemManager.siteItemsToManagers(userRightManager.getSiteItemsForView(ItemType.ALL_FORMS));
        Assert.assertEquals(5, items.size());

        final List<ItemManager> filteredItems =
                userItemsFilter.filter(items, -1, null);
        Assert.assertEquals(5, filteredItems.size());
    }

    @Test
    public void testFilterWithNoFilterBySite() {
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
        TestUtil.createChildSiteRegistration(site);

        final UserManager userManager = new UsersManager().getLogined();
        final UserRightManager userRightManager = userManager.getRight();
        final List<ItemManager> items =
                ItemManager.siteItemsToManagers(userRightManager.getSiteItemsForView(ItemType.CUSTOM_FORM));
        Assert.assertEquals(3, items.size());

        final List<ItemManager> filteredItems =
                userItemsFilter.filter(items, -1, null);
        Assert.assertEquals(3, filteredItems.size());
    }

    @Test
    public void testFilterWithNoFilterByForm() {
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
        TestUtil.createChildSiteRegistration(site);

        final UserManager userManager = new UsersManager().getLogined();
        final UserRightManager userRightManager = userManager.getRight();
        final List<ItemManager> items =
                ItemManager.siteItemsToManagers(userRightManager.getSiteItemsForView(ItemType.ALL_FORMS));
        Assert.assertEquals(5, items.size());

        final List<ItemManager> filteredItems =
                userItemsFilter.filter(items, site.getSiteId(), null);
        Assert.assertEquals(4, filteredItems.size());
    }

    @Test
    public void testFilterWithNotFormGroupTypeAndFormFilter() {
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
        TestUtil.createChildSiteRegistration(site);

        final UserManager userManager = new UsersManager().getLogined();
        final UserRightManager userRightManager = userManager.getRight();
        final List<ItemManager> items =
                ItemManager.siteItemsToManagers(userRightManager.getSiteItemsForView(ItemType.ALL_FORMS));
        Assert.assertEquals(5, items.size());

        final UserItemsFilter userItemsFilter = new UserItemsFilter();
        final List<ItemManager> filteredItems =
                userItemsFilter.filter(items, site.getSiteId(), null);
        Assert.assertEquals(4, filteredItems.size());
    }

    @Test
    public void testFilterWithSearchByNameFilterAndSiteIdFilter() {
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
        TestUtil.createChildSiteRegistration(site);

        final UserManager userManager = new UsersManager().getLogined();
        final UserRightManager userRightManager = userManager.getRight();
        final List<ItemManager> items =
                ItemManager.siteItemsToManagers(userRightManager.getSiteItemsForView(ItemType.CUSTOM_FORM));
        Assert.assertEquals(3, items.size());

        final UserItemsFilter userItemsFilter = new UserItemsFilter();
        final List<ItemManager> filteredItems =
                userItemsFilter.filter(items, site.getSiteId(), "a");
        Assert.assertEquals(1, filteredItems.size());
    }

    @Test
    public void testFilterWithSearchByNameFilter() {
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
        TestUtil.createChildSiteRegistration(site);

        final UserManager userManager = new UsersManager().getLogined();
        final UserRightManager userRightManager = userManager.getRight();
        final List<ItemManager> items =
                ItemManager.siteItemsToManagers(userRightManager.getSiteItemsForView(ItemType.CUSTOM_FORM));
        Assert.assertEquals(3, items.size());

        final UserItemsFilter userItemsFilter = new UserItemsFilter();
        final List<ItemManager> filteredItems =
                userItemsFilter.filter(items, -1, "a");
        Assert.assertEquals(2, filteredItems.size());
    }

    @Test
    public void testFilterWithSearchByNameFilterAndSiteIdFilter_withUpperCase() {
        final User user = TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final Site site2 = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site2);

        final DraftCustomForm customForm1 = TestUtil.createCustomForm(site);
        customForm1.setName("b");
        final DraftCustomForm customForm2 = TestUtil.createCustomForm(site);
        customForm2.setName("A");
        final DraftCustomForm customForm3 = TestUtil.createCustomForm(site2);
        customForm3.setName("A");
        TestUtil.createChildSiteRegistration(site);
        TestUtil.createChildSiteRegistration(site);

        final UserManager userManager = new UsersManager().getLogined();
        final UserRightManager userRightManager = userManager.getRight();
        final List<ItemManager> items =
                ItemManager.siteItemsToManagers(userRightManager.getSiteItemsForView(ItemType.CUSTOM_FORM));
        Assert.assertEquals(3, items.size());

        final UserItemsFilter userItemsFilter = new UserItemsFilter();
        final List<ItemManager> filteredItems =
                userItemsFilter.filter(items, site.getSiteId(), "a");
        Assert.assertEquals(1, filteredItems.size());
    }

    @Test
    public void testFilterWithSearchByNameFilter_withUpperCase() {
        final User user = TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final Site site2 = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site2);

        final DraftCustomForm customForm1 = TestUtil.createCustomForm(site);
        customForm1.setName("b");
        final DraftCustomForm customForm2 = TestUtil.createCustomForm(site);
        customForm2.setName("A");
        final DraftCustomForm customForm3 = TestUtil.createCustomForm(site2);
        customForm3.setName("A");
        TestUtil.createChildSiteRegistration(site);
        TestUtil.createChildSiteRegistration(site);

        final UserManager userManager = new UsersManager().getLogined();
        final UserRightManager userRightManager = userManager.getRight();
        final List<ItemManager> items =
                ItemManager.siteItemsToManagers(userRightManager.getSiteItemsForView(ItemType.CUSTOM_FORM));
        Assert.assertEquals(3, items.size());

        final UserItemsFilter userItemsFilter = new UserItemsFilter();
        final List<ItemManager> filteredItems =
                userItemsFilter.filter(items, -1, "a");
        Assert.assertEquals(2, filteredItems.size());
    }

    final UserItemsFilter userItemsFilter = new UserItemsFilter();

}
