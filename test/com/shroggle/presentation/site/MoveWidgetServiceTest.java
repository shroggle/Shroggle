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

import com.shroggle.TestBaseWithMockService;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.exception.WidgetRightsNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import junit.framework.Assert;
import org.junit.Test;


public class MoveWidgetServiceTest extends TestBaseWithMockService {

    @Test(expected = WidgetNotFoundException.class)
    public void executeWithNotFoundWidget() {
        TestUtil.createUserAndLogin();
        MoveWidgetRequest request = new MoveWidgetRequest();
        request.setToWidgetPosition(-1);
        service.execute(request);
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithNotLogin() {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Widget widget = TestUtil.createWidgetItem();
        widget.setPosition(0);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        MoveWidgetRequest request = new MoveWidgetRequest();
        request.setToWidgetPosition(1);
        Assert.assertFalse(service.execute(request));
    }

    @Test(expected = WidgetRightsNotFoundException.class)
    public void executeWithNotMy() {
        final Site site = TestUtil.createSite();
        PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserAndLogin();

        Widget widget = TestUtil.createWidgetItem();
        widget.setPosition(0);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        MoveWidgetRequest request = new MoveWidgetRequest();
        request.setWidgetId(widget.getWidgetId());
        request.setToWidgetPosition(1);
        service.execute(request);
    }

    @Test(expected = WidgetNotFoundException.class)
    public void executeWithToSequinceMoreExist() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Widget widget = TestUtil.createWidgetItem();
        widget.setPosition(0);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        MoveWidgetRequest request = new MoveWidgetRequest();
        request.setToWidgetPosition(1);

        service.execute(request);
        Assert.assertEquals(0, widget.getPosition());
    }

    @Test
    public void executeInCompositPositionSetEnd() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetComposit widgetComposit = new WidgetComposit();
        persistance.putWidget(widgetComposit);
        pageVersion.addWidget(widgetComposit);


        Widget widget1 = TestUtil.createWidgetItem();
        widgetComposit.addChild(widget1);
        widget1.setPosition(1);
        persistance.putWidget(widget1);
        pageVersion.addWidget(widget1);


        Widget widget2 = TestUtil.createWidgetItem();
        widgetComposit.addChild(widget2);
        widget2.setPosition(0);
        persistance.putWidget(widget2);
        pageVersion.addWidget(widget2);


        MoveWidgetRequest request = new MoveWidgetRequest();
        //Please refer to code in normalizeDropTarget in pageIllustration.js to understand why we set up such value position
        request.setToWidgetPosition(2);
        request.setToWidgetId(widgetComposit.getWidgetId());
        request.setWidgetId(widget2.getWidgetId());

        Assert.assertTrue(service.execute(request));
        Assert.assertEquals(1, widget2.getPosition());
    }

    @Test
    public void executeInOneCompositPositionSetFirst() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetComposit widgetComposit = new WidgetComposit();
        persistance.putWidget(widgetComposit);
        pageVersion.addWidget(widgetComposit);


        Widget widget1 = TestUtil.createWidgetItem();
        widget1.setPosition(1);
        widgetComposit.addChild(widget1);
        persistance.putWidget(widget1);
        pageVersion.addWidget(widget1);


        Widget widget2 = TestUtil.createWidgetItem();
        widgetComposit.addChild(widget2);
        widget2.setPosition(0);
        persistance.putWidget(widget2);
        pageVersion.addWidget(widget2);


        MoveWidgetRequest request = new MoveWidgetRequest();
        request.setToWidgetPosition(0);
        request.setToWidgetId(widgetComposit.getWidgetId());
        request.setWidgetId(widget1.getWidgetId());

        Assert.assertTrue(service.execute(request));
        Assert.assertEquals(0, widget1.getPosition());
        Assert.assertEquals(1, widget2.getPosition());
    }

    private final MoveWidgetService service = new MoveWidgetService();

}