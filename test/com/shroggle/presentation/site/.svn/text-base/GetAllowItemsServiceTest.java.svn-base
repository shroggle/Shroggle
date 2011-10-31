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
package com.shroggle.presentation.site;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class GetAllowItemsServiceTest {

    @Test
    public void testExecute() {
        final User user = TestUtil.createUserAndLogin();
        final User otherUser = TestUtil.createUser();

        // Two sites:
        // - site1 with two pages.
        // - site2 with one page.
        final Site site1 = TestUtil.createSite();
        site1.setTitle("site1");
        final PageManager site1_page1 = TestUtil.createPageVersionAndPage(site1, "p1");
        final PageManager site1_page2 = TestUtil.createPageVersionAndPage(site1, "p2");
        final Site site2 = TestUtil.createSite();
        site2.setTitle("site2");
        final PageManager site2_page1 = TestUtil.createPageVersionAndPage(site2, "p1");
        final Site otherSite = TestUtil.createSite();
        otherSite.setTitle("otherSite");

        TestUtil.createUserOnSiteRightActiveAdmin(user, site1);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site2);
        TestUtil.createUserOnSiteRightActiveAdmin(otherUser, otherSite);

        // site1_page1 got two widgets with same item. +2 items in result list.
        // site1_page2 got one widget with item. +1 item in result list.
        // site2_page1 got one widget with item. +1 item in result list.
        // site2 got one orphan item. +1 item in result list.
        // otherSite got one orphan item that is shared with site1. +1 item in result list.
        // Total we should get 6 items in result list.
        final DraftItem itemOnFirstSiteFirstPage = TestUtil.createCustomForm(site1);
        final WidgetItem widgetOnFirstSiteFirstPage = TestUtil.createWidgetItem(site1_page1);
        widgetOnFirstSiteFirstPage.setDraftItem(itemOnFirstSiteFirstPage);
        itemOnFirstSiteFirstPage.setName("itemOnFirstSiteFirstPage");

        final WidgetItem widgetOnFirstSiteFirstPage_Again = TestUtil.createWidgetItem(site1_page1);
        widgetOnFirstSiteFirstPage_Again.setDraftItem(itemOnFirstSiteFirstPage);

        final DraftItem itemOnFirstSiteSecondPage = TestUtil.createCustomForm(site1);
        final WidgetItem widgetItem2 = TestUtil.createWidgetItem(site1_page2);
        widgetItem2.setDraftItem(itemOnFirstSiteSecondPage);
        itemOnFirstSiteSecondPage.setName("itemOnFirstSiteSecondPage");

        final DraftItem itemOnSecondSiteFirstPage = TestUtil.createCustomForm(site2);
        final WidgetItem widgetItem3 = TestUtil.createWidgetItem(site2_page1);
        widgetItem3.setDraftItem(itemOnSecondSiteFirstPage);
        itemOnSecondSiteFirstPage.setName("itemOnSecondSiteFirstPage");

        final DraftItem orphanSecondSite = TestUtil.createCustomForm(site2);
        orphanSecondSite.setName("orphanSecondSite");
        final DraftItem orphanItemOnOtherSite = TestUtil.createCustomForm(otherSite);
        orphanItemOnOtherSite.setName("orphanItemOnOtherSite");

        TestUtil.createSiteOnItemRight(site1, orphanItemOnOtherSite);

        final GetAllowItemsRequest request = new GetAllowItemsRequest();
        request.setItemType(ItemType.CUSTOM_FORM);
        request.setSiteId(site1.getSiteId());

        final List<GetAllowItem> itemsList = service.execute(request);
        Assert.assertEquals(6, itemsList.size());

        Assert.assertEquals("otherSite / orphanItemOnOtherSite", itemsList.get(0).getName());
        Assert.assertEquals("site1 / p1 / itemOnFirstSiteFirstPage", itemsList.get(1).getName());
        Assert.assertEquals("site1 / p1 / itemOnFirstSiteFirstPage (2)", itemsList.get(2).getName());
        Assert.assertEquals("site1 / p2 / itemOnFirstSiteSecondPage", itemsList.get(3).getName());
        Assert.assertEquals("site2 / orphanSecondSite", itemsList.get(4).getName());
        Assert.assertEquals("site2 / p1 / itemOnSecondSiteFirstPage", itemsList.get(5).getName());
    }

    private GetAllowItemsService service = new GetAllowItemsService();

}
