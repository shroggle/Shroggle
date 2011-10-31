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
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;


public class AddWidgetServiceTest extends TestBaseWithMockService {

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void executeWithChildForComposit() throws Exception {
        TestUtil.createUserAndLogin();
        PageManager pageVersion = TestUtil.createPageVersionPageAndSite();

        Widget parent = TestUtil.createWidgetComposit();
        persistance.putWidget(parent);
        pageVersion.addWidget(parent);


        AddWidgetRequest request = new AddWidgetRequest();
        request.setWidgetId(parent.getWidgetId());
        request.setItemType(ItemType.TEXT);

        WidgetInfo widget = service.execute(request).getWidget();

        Assert.assertNotNull(widget);
        Assert.assertEquals(0, widget.getPosition());
        Assert.assertEquals(2, pageVersion.getWidgets().size());
    }

    @Test
    public void executeWithWidgetImage() throws Exception {
        TestUtil.createUserAndLogin();
        final PageManager pageVersion = TestUtil.createPageVersionPageAndSite();

        final Widget parent = TestUtil.createWidgetComposit();
        persistance.putWidget(parent);
        pageVersion.addWidget(parent);


        final AddWidgetRequest request = new AddWidgetRequest();
        request.setWidgetId(parent.getWidgetId());
        request.setItemType(ItemType.IMAGE);

        final WidgetInfo widget = service.execute(request).getWidget();

        Assert.assertNotNull(widget);
        Assert.assertEquals(0, widget.getPosition());
        Assert.assertEquals(2, pageVersion.getWidgets().size());
    }

    @Test
    public void executeWithWidgetAdminLogin() throws Exception {
        TestUtil.createUserAndLogin();
        final PageManager pageVersion = TestUtil.createPageVersionPageAndSite();

        final Widget parent = TestUtil.createWidgetComposit();
        persistance.putWidget(parent);
        pageVersion.addWidget(parent);


        final AddWidgetRequest request = new AddWidgetRequest();
        request.setWidgetId(parent.getWidgetId());
        request.setItemType(ItemType.ADMIN_LOGIN);

        final WidgetInfo widget = service.execute(request).getWidget();

        Assert.assertNotNull(widget);
        Assert.assertEquals(0, widget.getPosition());
        Assert.assertEquals(2, pageVersion.getWidgets().size());
    }

    @Test
    public void executeWithWidgetVideo() throws Exception {
        TestUtil.createUserAndLogin();
        final PageManager pageVersion = TestUtil.createPageVersionPageAndSite();

        final Widget parent = TestUtil.createWidgetComposit();
        persistance.putWidget(parent);
        pageVersion.addWidget(parent);

        final AddWidgetRequest request = new AddWidgetRequest();
        request.setWidgetId(parent.getWidgetId());
        request.setItemType(ItemType.VIDEO);

        final WidgetInfo widget = service.execute(request).getWidget();

        Assert.assertNotNull(widget);
        Assert.assertEquals(0, widget.getPosition());
        Assert.assertEquals(2, pageVersion.getWidgets().size());
    }

    @Test
    public void executeWithChildForCompositAndIncorrectPosition() throws Exception {
        TestUtil.createUserAndLogin();
        PageManager pageVersion = TestUtil.createPageVersionPageAndSite();

        WidgetComposit parent = TestUtil.createWidgetComposit();
        persistance.putWidget(parent);
        pageVersion.addWidget(parent);


        Widget child = TestUtil.createWidgetItem();
        child.setPosition(0);
        parent.addChild(child);
        persistance.putWidget(child);
        pageVersion.addWidget(child);


        AddWidgetRequest request = new AddWidgetRequest();
        request.setWidgetId(parent.getWidgetId());
        request.setItemType(ItemType.TEXT);
        WidgetInfo widget = service.execute(request).getWidget();

        Assert.assertNotNull(widget);
        Assert.assertEquals(1, widget.getPosition());
        Assert.assertEquals(3, pageVersion.getWidgets().size());
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() throws Exception {
        AddWidgetRequest addWidgetRequest = new AddWidgetRequest();
        addWidgetRequest.setItemType(ItemType.MENU);

        Assert.assertNull(service.execute(addWidgetRequest).getWidget());
    }

    private final AddWidgetService service = new AddWidgetService();

}