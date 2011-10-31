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

package com.shroggle.util.process.synchronize;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Set;

/**
 * @author Stasuk Artem
 */
@RunWith(TestRunnerWithMockServices.class)
public class SynchronizeRequestEntityTest {

    @Test(expected = NullPointerException.class)
    public void createWithNullRegion() {
        new SynchronizeRequestEntity(null, null, null);
    }

    @Test(expected = NullPointerException.class)
    public void createWithNullMethod() {
        new SynchronizeRequestEntity(User.class, null, null);
    }

    @Test
    public void createWithNullKey() {
        SynchronizeRequest request = new SynchronizeRequestEntity(User.class, SynchronizeMethod.READ, null);
        
        Assert.assertEquals(0, request.getPoints().size());
    }

    @Test
    public void create() {
        SynchronizeRequestEntity request = new SynchronizeRequestEntity(
                User.class, SynchronizeMethod.READ, "a");

        Assert.assertEquals("a", request.getEntityId());
        Assert.assertEquals(User.class, request.getEntityClass());
        Assert.assertEquals(SynchronizeMethod.READ, request.getMethod());
    }

    @Test
    public void getPointsForWidget() {
        final PageManager pageVersion = TestUtil.createPageVersionPageAndSite();
        final WidgetItem widgetItem = TestUtil.createWidgetItem();
        ServiceLocator.getPersistance().putWidget(widgetItem);
        pageVersion.addWidget(widgetItem);

        SynchronizeRequest request = new SynchronizeRequestEntity(
                Widget.class, SynchronizeMethod.READ, widgetItem.getWidgetId());
        final Set<SynchronizePoint> points = request.getPoints();

        Assert.assertEquals(3, points.size());
    }

    @Test
    public void getPointsForWriteSite() {
        final Site site = TestUtil.createSite();

        SynchronizeRequest request = new SynchronizeRequestEntity(
                Site.class, SynchronizeMethod.WRITE, site.getSiteId());
        final Set<SynchronizePoint> points = request.getPoints();

        Assert.assertEquals(2, points.size());
        Assert.assertTrue(points.contains(new SynchronizePointAllEntity(Site.class)));
    }

    @Test
    public void getPointsForReadSite() {
        final Site site = TestUtil.createSite();

        SynchronizeRequest request = new SynchronizeRequestEntity(
                Site.class, SynchronizeMethod.READ, site.getSiteId());
        final Set<SynchronizePoint> points = request.getPoints();

        Assert.assertEquals(1, points.size());
    }

    @Test
    public void getPointsForParentDeepReadSite() {
        final PageManager pageVersion = TestUtil.createPageVersionPageAndSite();

        SynchronizeRequest request = new SynchronizeRequestEntity(
                Page.class, SynchronizeMethod.READ, pageVersion.getPageId(), 1);
        final Set<SynchronizePoint> points = request.getPoints();

        Assert.assertEquals(1, points.size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getPointsForNegativeParentDeepReadSite() {
        TestUtil.createUser();
        final Site site = TestUtil.createSite();

        new SynchronizeRequestEntity(Site.class, SynchronizeMethod.READ, site.getSiteId(), -1);
    }

    @Test
    public void getPointsForParentDeepWriteSite() {
        final PageManager pageVersion = TestUtil.createPageVersionPageAndSite();

        SynchronizeRequest request = new SynchronizeRequestEntity(
                Page.class, SynchronizeMethod.WRITE, pageVersion.getPageId(), 1);
        final Set<SynchronizePoint> points = request.getPoints();

        Assert.assertEquals(2, points.size());
        Assert.assertTrue(points.contains(new SynchronizePointAllEntity(Site.class)));
    }

    @Test
    public void getPointsForWriteUser() {
        final User account = TestUtil.createUser();

        SynchronizeRequest request = new SynchronizeRequestEntity(
                User.class, SynchronizeMethod.WRITE, account.getUserId());
        final Set<SynchronizePoint> points = request.getPoints();

        Assert.assertEquals(2, points.size());
        Assert.assertTrue(points.contains(new SynchronizePointAllEntity(User.class)));
    }

}
