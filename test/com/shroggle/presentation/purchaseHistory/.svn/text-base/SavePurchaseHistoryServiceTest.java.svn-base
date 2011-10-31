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
package com.shroggle.presentation.purchaseHistory;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.DraftPurchaseHistory;
import com.shroggle.entity.Site;
import com.shroggle.entity.User;
import com.shroggle.entity.WidgetItem;
import com.shroggle.exception.PurchaseHistoryNameNotUniqueException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.site.FunctionalWidgetInfo;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class SavePurchaseHistoryServiceTest {

    private final Persistance persistance = ServiceLocator.getPersistance();

    @Test
    public void executeFromSiteEditPage() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        widget.setPosition(0);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        DraftPurchaseHistory draftPurchaseHistory = new DraftPurchaseHistory();
        draftPurchaseHistory.setName("old_name");
        draftPurchaseHistory.setDescription("old_desc");
        draftPurchaseHistory.setShowDescription(false);
        draftPurchaseHistory.setImageHeight(1);
        draftPurchaseHistory.setImageWidth(1);
        draftPurchaseHistory.setShowProductImage(false);
        draftPurchaseHistory.setSiteId(site.getSiteId());
        persistance.putItem(draftPurchaseHistory);
        widget.setDraftItem(draftPurchaseHistory);

        DraftPurchaseHistory edit = new DraftPurchaseHistory();
        edit.setName("new_name");
        edit.setDescription("new_desc");
        edit.setShowDescription(true);
        edit.setImageHeight(2);
        edit.setImageWidth(2);
        edit.setShowProductImage(true);
        edit.setId(draftPurchaseHistory.getId());

        FunctionalWidgetInfo response = new SavePurchaseHistoryService().execute(widget.getWidgetId(), edit);

        Assert.assertNotNull(response);
        Assert.assertEquals(widget.getWidgetId(), response.getWidget().getWidgetId());

        Assert.assertEquals("new_name", draftPurchaseHistory.getName());
        Assert.assertEquals("new_desc", draftPurchaseHistory.getDescription());
        Assert.assertEquals(true, draftPurchaseHistory.isShowDescription());
        Assert.assertEquals(2, draftPurchaseHistory.getImageWidth());
        Assert.assertEquals(2, draftPurchaseHistory.getImageHeight());
        Assert.assertEquals(true, draftPurchaseHistory.isShowProductImage());
    }

    @Test
    public void executeFromManageItems() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        DraftPurchaseHistory draftPurchaseHistory = new DraftPurchaseHistory();
        draftPurchaseHistory.setName("old_name");
        draftPurchaseHistory.setDescription("old_desc");
        draftPurchaseHistory.setShowDescription(false);
        draftPurchaseHistory.setImageHeight(1);
        draftPurchaseHistory.setImageWidth(1);
        draftPurchaseHistory.setShowProductImage(false);
        draftPurchaseHistory.setSiteId(site.getSiteId());
        persistance.putItem(draftPurchaseHistory);

        DraftPurchaseHistory edit = new DraftPurchaseHistory();
        edit.setName("new_name");
        edit.setDescription("new_desc");
        edit.setShowDescription(true);
        edit.setImageHeight(2);
        edit.setImageWidth(2);
        edit.setShowProductImage(true);
        edit.setId(draftPurchaseHistory.getId());

        FunctionalWidgetInfo response = new SavePurchaseHistoryService().execute(null, edit);

        Assert.assertNotNull(response);
        Assert.assertNull(response.getWidget());

        Assert.assertEquals("new_name", draftPurchaseHistory.getName());
        Assert.assertEquals("new_desc", draftPurchaseHistory.getDescription());
        Assert.assertEquals(true, draftPurchaseHistory.isShowDescription());
        Assert.assertEquals(2, draftPurchaseHistory.getImageWidth());
        Assert.assertEquals(2, draftPurchaseHistory.getImageHeight());
        Assert.assertEquals(true, draftPurchaseHistory.isShowProductImage());
    }

    @Test(expected = PurchaseHistoryNameNotUniqueException.class)
    public void executeWithNotUniqueName() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        widget.setPosition(0);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        DraftPurchaseHistory draftPurchaseHistory = new DraftPurchaseHistory();
        draftPurchaseHistory.setName("old_name");
        draftPurchaseHistory.setDescription("old_desc");
        draftPurchaseHistory.setShowDescription(false);
        draftPurchaseHistory.setImageHeight(1);
        draftPurchaseHistory.setImageWidth(1);
        draftPurchaseHistory.setShowProductImage(false);
        draftPurchaseHistory.setSiteId(site.getSiteId());
        persistance.putItem(draftPurchaseHistory);
        widget.setDraftItem(draftPurchaseHistory);

        DraftPurchaseHistory duplicatePurchaseHistory = new DraftPurchaseHistory();
        duplicatePurchaseHistory.setName("qwe123");
        duplicatePurchaseHistory.setSiteId(site.getSiteId());
        persistance.putItem(duplicatePurchaseHistory);

        DraftPurchaseHistory edit = new DraftPurchaseHistory();
        edit.setName("qwe123");
        edit.setId(draftPurchaseHistory.getId());

        new SavePurchaseHistoryService().execute(widget.getWidgetId(), edit);
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLoginedUser() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        widget.setPosition(0);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        DraftPurchaseHistory duplicatePurchaseHistory = new DraftPurchaseHistory();
        duplicatePurchaseHistory.setName("qwe123");
        duplicatePurchaseHistory.setSiteId(site.getSiteId());
        persistance.putItem(duplicatePurchaseHistory);

        DraftPurchaseHistory edit = new DraftPurchaseHistory();
        edit.setName("qwe123");

        new SavePurchaseHistoryService().execute(widget.getWidgetId(), edit);
    }

}
