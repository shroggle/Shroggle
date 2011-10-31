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

package com.shroggle.logic;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.page.pageversion.PageVersionNormalizerReal;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stasuk Artem
 */
@RunWith(TestRunnerWithMockServices.class)
public class PageVersionNormalizerTest {

    @Test
    public void executeWithoutMediaBlockPositions() {
        PageManager pageVersion = new PageManager(TestUtil.createPageAndSite());
        pageVersion.setHtml("");

        Widget widget = new WidgetItem();
        widget.setPosition(1);
        pageVersion.addWidget(widget);

        new PageVersionNormalizerReal().execute(pageVersion);

        Assert.assertEquals(1, pageVersion.getWidgets().size());
        Assert.assertEquals(1, pageVersion.getWidgets().get(0).getPosition());
        Assert.assertEquals(true, pageVersion.getWidgets().get(0).isWidgetItem());
    }

    @Test
    public void execute() {
        Site site = new Site();
        Page page = TestUtil.createPage(site);
        PageManager pageVersion = new PageManager(page);
        pageVersion.setHtml("<!-- MEDIA_BLOCK --><!-- MEDIA_BLOCK --><!-- MEDIA_BLOCK -->");

        Widget widget = new WidgetItem();
        widget.setPosition(1);
        pageVersion.addWidget(widget);

        new PageVersionNormalizerReal().execute(pageVersion);

        Assert.assertEquals(3, pageVersion.getWidgets().size());
        Assert.assertEquals(1, pageVersion.getWidgets().get(0).getPosition());
        Assert.assertEquals(true, pageVersion.getWidgets().get(0).isWidgetItem());
        Assert.assertEquals(ItemType.COMPOSIT, pageVersion.getWidgets().get(1).getItemType());
        Assert.assertEquals(ItemType.COMPOSIT, pageVersion.getWidgets().get(2).getItemType());
        Assert.assertEquals(0, pageVersion.getWidgets().get(1).getPosition());
        Assert.assertEquals(2, pageVersion.getWidgets().get(2).getPosition());
    }

    @Test
    public void executeMoreWidgetAndNoOnPosition() {
        Site site = new Site();
        Page page = TestUtil.createPage(site);
        PageManager pageVersion  = new PageManager(page);
        pageVersion.setHtml("<!-- MEDIA_BLOCK -->");

        Widget widget = new WidgetItem();
        widget.setPosition(1);
        pageVersion.addWidget(widget);

        new PageVersionNormalizerReal().execute(pageVersion);

        Assert.assertEquals(2, pageVersion.getWidgets().size());
        Assert.assertEquals(1, pageVersion.getWidgets().get(0).getPosition());
        Assert.assertEquals(0, pageVersion.getWidgets().get(1).getPosition());
        Assert.assertEquals(true, pageVersion.getWidgets().get(0).isWidgetItem());
        Assert.assertEquals(ItemType.COMPOSIT, pageVersion.getWidgets().get(1).getItemType());
    }

    @Test
    public void executeMoreWidgetAndWithoutComposit() {
        PageManager pageVersion = new PageManager(TestUtil.createPageAndSite());
        pageVersion.setHtml("<!-- MEDIA_BLOCK -->");

        Widget widget1 = new WidgetItem();
        widget1.setPosition(0);
        pageVersion.addWidget(widget1);

        Widget widget = new WidgetItem();
        widget.setPosition(1);
        pageVersion.addWidget(widget);

        new PageVersionNormalizerReal().execute(pageVersion);

        Assert.assertEquals(2, pageVersion.getWidgets().size());
        Assert.assertEquals(0, pageVersion.getWidgets().get(0).getPosition());
        Assert.assertEquals(1, pageVersion.getWidgets().get(1).getPosition());
        Assert.assertEquals(true, pageVersion.getWidgets().get(0).isWidgetItem());
        Assert.assertEquals(true, pageVersion.getWidgets().get(1).isWidgetItem());
    }

    @Test
    public void executeMoreWidget() {
        PageManager pageVersion = new PageManager(TestUtil.createPageAndSite());
        pageVersion.setHtml("<!-- MEDIA_BLOCK -->");

        Widget widget1 = new WidgetComposit();
        widget1.setPosition(0);
        pageVersion.addWidget(widget1);

        Widget widget = new WidgetItem();
        widget.setPosition(1);
        pageVersion.addWidget(widget);

        new PageVersionNormalizerReal().execute(pageVersion);

        Assert.assertEquals(2, pageVersion.getWidgets().size());
        Assert.assertEquals(1, pageVersion.getWidgets().get(1).getPosition());
        Assert.assertEquals(0, pageVersion.getWidgets().get(0).getPosition());
        Assert.assertEquals(ItemType.COMPOSIT, pageVersion.getWidgets().get(0).getItemType());
        Assert.assertEquals(true, pageVersion.getWidgets().get(1).isWidgetItem());
    }

    @Test
    public void executeMoreWidgetAndComposit() {
        PageManager pageVersion = new PageManager(TestUtil.createPageAndSite());
        pageVersion.setHtml("<!-- MEDIA_BLOCK -->");

        WidgetComposit widget1 = (WidgetComposit) new WidgetComposit();
        widget1.setPosition(0);
        pageVersion.addWidget(widget1);

        Widget widget4 = new WidgetItem();
        widget4.setPosition(0);
        widget1.addChild(widget4);
        pageVersion.addWidget(widget4);

        WidgetComposit widget2 = (WidgetComposit) new WidgetComposit();
        widget2.setPosition(1);
        pageVersion.addWidget(widget2);

        Widget widget3 = new WidgetItem();
        widget3.setPosition(0);
        widget2.addChild(widget3);
        pageVersion.addWidget(widget3);

        new PageVersionNormalizerReal().execute(pageVersion);

        Assert.assertEquals(3, pageVersion.getWidgets().size());
        Assert.assertEquals(0, widget1.getPosition());
        Assert.assertEquals(0, widget4.getPosition());
        Assert.assertEquals(1, widget3.getPosition());
        Assert.assertEquals(2, widget1.getChilds().size());
        Assert.assertEquals(true, widget1.getChilds().get(0).isWidgetItem());
        Assert.assertEquals(true, widget1.getChilds().get(1).isWidgetItem());
    }

}
