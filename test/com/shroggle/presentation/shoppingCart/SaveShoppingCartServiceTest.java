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
package com.shroggle.presentation.shoppingCart;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.DraftShoppingCart;
import com.shroggle.entity.Site;
import com.shroggle.entity.User;
import com.shroggle.entity.WidgetItem;
import com.shroggle.exception.ShoppingCartNameNotUniqueException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.site.FunctionalWidgetInfo;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class SaveShoppingCartServiceTest {

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final SaveShoppingCartService service = new SaveShoppingCartService();

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

        DraftShoppingCart draftShoppingCart = new DraftShoppingCart();
        draftShoppingCart.setName("old_name");
        draftShoppingCart.setDescription("old_desc");
        draftShoppingCart.setShowDescription(false);
        draftShoppingCart.setImageHeight(1);
        draftShoppingCart.setImageWidth(1);
        draftShoppingCart.setSiteId(site.getSiteId());
        persistance.putItem(draftShoppingCart);
        widget.setDraftItem(draftShoppingCart);

        SaveShoppingCartRequest edit = new SaveShoppingCartRequest();
        edit.setWidgetId(widget.getWidgetId());
        edit.setName("new_name");
        edit.setDescription("new_desc");
        edit.setShowDescription(true);
        edit.setImageHeight(2);
        edit.setImageWidth(2);
        edit.setId(draftShoppingCart.getId());

        FunctionalWidgetInfo response = service.execute(edit);

        Assert.assertNotNull(response);
        Assert.assertEquals(widget.getWidgetId(), response.getWidget().getWidgetId());

        Assert.assertEquals("new_name", draftShoppingCart.getName());
        Assert.assertEquals("new_desc", draftShoppingCart.getDescription());
        Assert.assertEquals(true, draftShoppingCart.isShowDescription());
        Assert.assertEquals(2, draftShoppingCart.getImageWidth());
        Assert.assertEquals(2, draftShoppingCart.getImageHeight());
    }

    @Test
    public void executeFromManageItems() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        DraftShoppingCart draftShoppingCart = new DraftShoppingCart();
        draftShoppingCart.setName("old_name");
        draftShoppingCart.setDescription("old_desc");
        draftShoppingCart.setShowDescription(false);
        draftShoppingCart.setImageHeight(1);
        draftShoppingCart.setImageWidth(1);
        draftShoppingCart.setSiteId(site.getSiteId());
        persistance.putItem(draftShoppingCart);

        SaveShoppingCartRequest edit = new SaveShoppingCartRequest();
        edit.setName("new_name");
        edit.setDescription("new_desc");
        edit.setShowDescription(true);
        edit.setImageHeight(2);
        edit.setImageWidth(2);
        edit.setId(draftShoppingCart.getId());

        FunctionalWidgetInfo response = service.execute(edit);

        Assert.assertNotNull(response);
        Assert.assertNull(response.getWidget());

        Assert.assertEquals("new_name", draftShoppingCart.getName());
        Assert.assertEquals("new_desc", draftShoppingCart.getDescription());
        Assert.assertEquals(true, draftShoppingCart.isShowDescription());
        Assert.assertEquals(2, draftShoppingCart.getImageWidth());
        Assert.assertEquals(2, draftShoppingCart.getImageHeight());
    }

    @Test(expected = ShoppingCartNameNotUniqueException.class)
    public void executeWithNotUniqueName() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        widget.setPosition(0);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        DraftShoppingCart draftShoppingCart = new DraftShoppingCart();
        draftShoppingCart.setName("old_name");
        draftShoppingCart.setDescription("old_desc");
        draftShoppingCart.setShowDescription(false);
        draftShoppingCart.setImageHeight(1);
        draftShoppingCart.setImageWidth(1);
        draftShoppingCart.setSiteId(site.getSiteId());
        persistance.putItem(draftShoppingCart);
        widget.setDraftItem(draftShoppingCart);

        DraftShoppingCart duplicateShoppingCart = new DraftShoppingCart();
        duplicateShoppingCart.setName("qwe123");
        duplicateShoppingCart.setSiteId(site.getSiteId());
        persistance.putItem(duplicateShoppingCart);

        SaveShoppingCartRequest edit = new SaveShoppingCartRequest();
        edit.setName("qwe123");
        edit.setId(draftShoppingCart.getId());

        service.execute(edit);
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

        SaveShoppingCartRequest edit = new SaveShoppingCartRequest();
        edit.setName("qwe123");

        service.execute(edit);
    }

}
