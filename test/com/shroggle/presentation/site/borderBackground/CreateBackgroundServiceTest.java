/*
*/
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


package com.shroggle.presentation.site.borderBackground;

import com.shroggle.TestBaseWithMockService;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.site.WidgetManager;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Date;


@RunWith(TestRunnerWithMockServices.class)
public class CreateBackgroundServiceTest extends TestBaseWithMockService {

    private CreateBackgroundService service = new CreateBackgroundService();

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void execute_forWidgetWithWidgetId() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        user.setPassword("1");
        user.setEmail("a@a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        WidgetItem widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);


        persistance.putSite(site);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);
        pageVersion.addWidget(widget);

        BackgroundImage backgroundImage = new BackgroundImage();
        persistance.putBackgroundImage(backgroundImage);

        final Background tempBackground = new Background();
        tempBackground.setBackgroundRepeat("repeat");
        tempBackground.setBackgroundImageId(backgroundImage.getBackgroundImageId());
        tempBackground.setBackgroundPosition("top");

        Assert.assertEquals("ok", service.executeForWidget(widget.getWidgetId(), null, tempBackground, false).getInnerHTML());
        Assert.assertEquals(tempBackground.getBackgroundPosition(), new WidgetManager(widget).getBackground(SiteShowOption.getDraftOption()).getBackgroundPosition());
        Assert.assertEquals(tempBackground.getBackgroundRepeat(), new WidgetManager(widget).getBackground(SiteShowOption.getDraftOption()).getBackgroundRepeat());
        Assert.assertEquals(tempBackground.getBackgroundImageId(), new WidgetManager(widget).getBackground(SiteShowOption.getDraftOption()).getBackgroundImageId());
    }

    @Test
    public void execute_forWidgetWithDraftItemId() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        user.setPassword("1");
        user.setEmail("a@a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        WidgetItem widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);


        persistance.putSite(site);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);
        pageVersion.addWidget(widget);

        BackgroundImage backgroundImage = new BackgroundImage();
        persistance.putBackgroundImage(backgroundImage);

        final Background tempBackground = new Background();
        tempBackground.setBackgroundRepeat("repeat");
        tempBackground.setBackgroundImageId(backgroundImage.getBackgroundImageId());
        tempBackground.setBackgroundPosition("");

        final DraftImage draftImage = TestUtil.createDraftImage(site);

        Assert.assertEquals("ok", service.executeForWidget(null, draftImage.getId(), tempBackground, false).getInnerHTML());
        Assert.assertEquals(tempBackground.getBackgroundPosition(), new ItemManager(draftImage).getBackground(SiteShowOption.getDraftOption()).getBackgroundPosition());
        Assert.assertEquals(tempBackground.getBackgroundRepeat(), new ItemManager(draftImage).getBackground(SiteShowOption.getDraftOption()).getBackgroundRepeat());
        Assert.assertEquals(tempBackground.getBackgroundImageId(), new ItemManager(draftImage).getBackground(SiteShowOption.getDraftOption()).getBackgroundImageId());
    }


    @Test
    public void executeForPage() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        user.setPassword("1");
        user.setEmail("a@a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        WidgetItem widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);


        persistance.putSite(site);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);

        pageVersion.addWidget(widget);


        Page page2 = TestUtil.createPage(site);

        PageManager pageVersion2 = new PageManager(page2);
        pageVersion2.addWidget(widget);


        Page page3 = TestUtil.createPage(site);

        PageManager pageVersion3 = new PageManager(page3);
        pageVersion3.addWidget(widget);


        Page page4 = TestUtil.createPage(site);

        PageManager pageVersion4 = new PageManager(page4);
        pageVersion4.addWidget(widget);


        BackgroundImage backgroundImage = new BackgroundImage();
        persistance.putBackgroundImage(backgroundImage);

        Background tempBackground = new Background();
        tempBackground.setBackgroundRepeat("repeat");
        tempBackground.setBackgroundImageId(1);
        tempBackground.setBackgroundPosition("");

        Assert.assertEquals("ok", service.executeForPage(pageVersion.getPageId(), tempBackground, false).getInnerHTML());
        Assert.assertEquals(null, pageVersion.getBackground().getBackgroundColor());
        Assert.assertNull(pageVersion2.getBorder());
        Assert.assertNull(pageVersion3.getBorder());
        Assert.assertNull(pageVersion4.getBorder());
    }

    @Test
    public void executeForAllPages() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        user.setPassword("1");
        user.setEmail("a@a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        WidgetItem widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);


        persistance.putSite(site);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);

        pageVersion.addWidget(widget);


        Page page2 = TestUtil.createPage(site);

        PageManager pageVersion2 = new PageManager(page2);
        pageVersion2.addWidget(widget);


        Page page3 = TestUtil.createPage(site);

        PageManager pageVersion3 = new PageManager(page3);
        pageVersion3.addWidget(widget);


        Page page4 = TestUtil.createPage(site);

        PageManager pageVersion4 = new PageManager(page4);
        pageVersion4.addWidget(widget);


        BackgroundImage backgroundImage = new BackgroundImage();
        persistance.putBackgroundImage(backgroundImage);
        Background tempBackground = new Background();
        tempBackground.setBackgroundRepeat("repeat");
        tempBackground.setBackgroundImageId(1);
        tempBackground.setBackgroundPosition("");

        Assert.assertEquals("ok", service.executeForPage(pageVersion.getPageId(), tempBackground, true).getInnerHTML());
        Assert.assertNotNull(pageVersion.getBackground());
        Assert.assertEquals(null, pageVersion.getBackground().getBackgroundColor());

        Assert.assertNotNull(pageVersion2.getBackground());
        Assert.assertEquals(null, pageVersion2.getBackground().getBackgroundColor());

        Assert.assertNotNull(pageVersion3.getBackground());
        Assert.assertEquals(null, pageVersion3.getBackground().getBackgroundColor());

        Assert.assertNotNull(pageVersion4.getBackground());
        Assert.assertEquals(null, pageVersion4.getBackground().getBackgroundColor());
    }


    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() throws IOException, ServletException {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        service.executeForWidget(null, null, null, false);
    }


    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin_forPage() throws IOException, ServletException {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        service.executeForPage(-1, null, false);
    }
}