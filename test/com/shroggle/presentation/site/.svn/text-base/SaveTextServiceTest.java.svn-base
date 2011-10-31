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
import com.shroggle.exception.TextItemNotFoundException;
import com.shroggle.exception.TextLargeException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;

@RunWith(TestRunnerWithMockServices.class)
public class SaveTextServiceTest {

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void executeFromSiteEditPage() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        WidgetItem widget = TestUtil.createTextWidget(pageVersion);

        SaveTextRequest request = new SaveTextRequest();
        request.setTextItemId(widget.getDraftItem().getId());
        request.setWidgetId(widget.getWidgetId());
        request.setWidgetText("aaa");

        FunctionalWidgetInfo response = service.execute(request);

        Assert.assertNotNull(response);
        Assert.assertEquals(widget.getWidgetId(), response.getWidget().getWidgetId());

        final DraftText draftText = (DraftText) widget.getDraftItem();
        Assert.assertEquals("aaa", draftText.getText());
    }
    
    @Test
    public void executeFromManageItems() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftText text = new DraftText();
        text.setSiteId(site.getSiteId());
        persistance.putItem(text);

        SaveTextRequest request = new SaveTextRequest();
        request.setTextItemId(text.getId());
        request.setWidgetText("aaa");

        FunctionalWidgetInfo response = service.execute(request);

        Assert.assertNotNull(response);
        Assert.assertNull(response.getWidget());

        Assert.assertEquals("aaa", text.getText());
    }

    @Test(expected = TextLargeException.class)
    public void executeLarge() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        WidgetItem widget = TestUtil.createTextWidget();
        widget.setPosition(0);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        SaveTextRequest request = new SaveTextRequest();
        request.setWidgetId(widget.getWidgetId());
        request.setWidgetText(TestUtil.createString(500001));
        service.execute(request);

        final DraftText draftText = (DraftText) widget.getDraftItem();
        Assert.assertEquals(60000, draftText.getText().length());
    }

    @Test
    public void execute_withIeComments() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        WidgetItem widget = TestUtil.createTextWidget(pageVersion);

        SaveTextRequest request = new SaveTextRequest();
        request.setTextItemId(widget.getDraftItem().getId());
        request.setWidgetId(widget.getWidgetId());
        request.setWidgetText("&lt;!--[if !supportLineBreakNewLine]--&gt;fdgsdfgsfdg  <br> &lt;!--[endif]--><!--[if !supportLineBreakNewLine]-->nnnnnnnnnnnnnnnnnn<!--[endif]-->");
        service.execute(request);

        final DraftText draftText = (DraftText) widget.getDraftItem();//persistance.getDraftItem(widget.getDraftItem());
        Assert.assertEquals("fdgsdfgsfdg  <br> nnnnnnnnnnnnnnnnnn", draftText.getText());
    }

    @Test(expected = TextItemNotFoundException.class)
    public void executeWithoutText() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Widget widget = TestUtil.createWidgetItem(pageVersion);

        SaveTextRequest request = new SaveTextRequest();
        request.setWidgetId(widget.getWidgetId());
        request.setWidgetText("aaa");
        service.execute(request);
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() throws ServletException, IOException {
        final PageManager pageVersion = TestUtil.createPageVersionPageAndSite();

        Widget widget = TestUtil.createWidgetItem();
        widget.setPosition(0);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        SaveTextRequest request = new SaveTextRequest();
        request.setWidgetId(widget.getWidgetId());
        service.execute(request);
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final SaveTextService service = new SaveTextService();

}