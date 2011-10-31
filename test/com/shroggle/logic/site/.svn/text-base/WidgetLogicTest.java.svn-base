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
package com.shroggle.logic.site;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.Site;
import com.shroggle.entity.User;
import com.shroggle.entity.Widget;
import com.shroggle.entity.WidgetItem;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class WidgetLogicTest {

    @Test(expected = WidgetNotFoundException.class)
    public void createNotFound() {
        TestUtil.createUserAndLogin();
        new WidgetManager(1);
    }

    @Test(expected = UserNotLoginedException.class)
    public void createWithoutLogin() {
        final PageManager pageVersion = TestUtil.createPageVersionPageAndSite();
        final Widget widget = new WidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        new WidgetManager(widget.getWidgetId());
    }

    @Test
    public void create() {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUserAndLogin();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final Widget widget = new WidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        new WidgetManager(widget.getWidgetId());
    }

    @Test
    public void getWidget() {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUserAndLogin();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final Widget widget = new WidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        final WidgetManager widgetManager = new WidgetManager(widget.getWidgetId());
        
        Assert.assertEquals(widget, widgetManager.getWidget());
    }

    @Test
    public void getRights() {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUserAndLogin();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final Widget widget = new WidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);
        
        final WidgetManager widgetManager = new WidgetManager(widget.getWidgetId());

        Assert.assertNotNull(widgetManager.getRights());
    }

    /*@Test
    public void isWidgetWithForm() {
        Assert.assertFalse(ItemType.BLOG.isWidgetWithForm());
        Assert.assertFalse(ItemType.FORUM.isWidgetWithForm());
        Assert.assertFalse(ItemType.TEXT.isWidgetWithForm());
        Assert.assertFalse(ItemType.IMAGE.isWidgetWithForm());
        Assert.assertFalse(ItemType.LOGIN.isWidgetWithForm());
        Assert.assertFalse(ItemType.VIDEO.isWidgetWithForm());
        Assert.assertFalse(ItemType.COMPOSIT.isWidgetWithForm());
        Assert.assertFalse(ItemType.MENU.isWidgetWithForm());
        Assert.assertFalse(ItemType.BLOG_SUMMARY.isWidgetWithForm());
        Assert.assertTrue(ItemType.CHILD_SITE_REGISTRATION.isWidgetWithForm());
        Assert.assertTrue(ItemType.CONTACT_US.isWidgetWithForm());
        Assert.assertTrue(ItemType.GALLERY.isWidgetWithForm());
        Assert.assertTrue(ItemType.CUSTOM_FORM.isWidgetWithForm());
        Assert.assertTrue(ItemType.GALLERY_DATA.isWidgetWithForm());
        Assert.assertTrue(ItemType.REGISTRATION.isWidgetWithForm());
    }*/

    private final Persistance persistance = ServiceLocator.getPersistance();

}
