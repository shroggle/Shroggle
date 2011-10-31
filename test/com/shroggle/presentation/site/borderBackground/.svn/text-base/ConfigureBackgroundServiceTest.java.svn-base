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

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.logic.site.WidgetManager;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;


@RunWith(TestRunnerWithMockServices.class)
public class ConfigureBackgroundServiceTest {

    @Before
    public void before() {
        final MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void execute() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion1 = new PageManager(page);

        final Background background = new Background();
        background.setSiteId(site.getSiteId());
        persistance.putBackground(background);

        WidgetItem widgetItem = TestUtil.createWidgetItem();
        widgetItem.setBackgroundId(background.getId());
        persistance.putWidget(widgetItem);
        pageVersion1.addWidget(widgetItem);


        Date currentDate = new Date();
        BackgroundImage backgroundImage1 = TestUtil.createBackgroundImage(site.getSiteId());
        BackgroundImage backgroundImage2 = TestUtil.createBackgroundImage(site.getSiteId());
        BackgroundImage backgroundImage3 = TestUtil.createBackgroundImage(site.getSiteId());
        BackgroundImage backgroundImage4 = TestUtil.createBackgroundImage(site.getSiteId());
        BackgroundImage backgroundImage5 = TestUtil.createBackgroundImage(site.getSiteId());

        backgroundImage1.setCreated(new Date(currentDate.getTime() + 100000));
        backgroundImage2.setCreated(new Date(currentDate.getTime() + 200000));
        backgroundImage3.setCreated(new Date(currentDate.getTime() + 300000));
        backgroundImage4.setCreated(new Date(currentDate.getTime() + 400000));
        backgroundImage5.setCreated(new Date(currentDate.getTime() + 500000));

        service.execute(widgetItem.getWidgetId(), null, null, false);

        Assert.assertNotNull(service.getDraftBackground());
        Assert.assertEquals(new WidgetManager(widgetItem).getBackground(SiteShowOption.getDraftOption()), service.getDraftBackground());
        Assert.assertEquals(site.getSiteId(), service.getSiteId());
        Assert.assertNotNull(service.getWidgetTitle());
        Assert.assertNotNull(service.getImageThumbnailsDatas());
        Assert.assertEquals(5, service.getImageThumbnailsDatas().size());
        Assert.assertEquals(backgroundImage5.getBackgroundImageId(), service.getImageThumbnailsDatas().get(0).getImageId().intValue());
        Assert.assertEquals(backgroundImage4.getBackgroundImageId(), service.getImageThumbnailsDatas().get(1).getImageId().intValue());
        Assert.assertEquals(backgroundImage3.getBackgroundImageId(), service.getImageThumbnailsDatas().get(2).getImageId().intValue());
        Assert.assertEquals(backgroundImage2.getBackgroundImageId(), service.getImageThumbnailsDatas().get(3).getImageId().intValue());
        Assert.assertEquals(backgroundImage1.getBackgroundImageId(), service.getImageThumbnailsDatas().get(4).getImageId().intValue());
    }

    @Test
    public void execute_forDraftItem() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion1 = new PageManager(page);

        final Background background = new Background();
        background.setSiteId(site.getSiteId());
        persistance.putBackground(background);

        WidgetItem widgetItem = TestUtil.createTextWidget();
        widgetItem.getDraftItem().setBackgroundId(background.getId());
        widgetItem.getDraftItem().setSiteId(site.getSiteId());
        persistance.putWidget(widgetItem);
        pageVersion1.addWidget(widgetItem);


        Date currentDate = new Date();
        BackgroundImage backgroundImage1 = TestUtil.createBackgroundImage(site.getSiteId());
        BackgroundImage backgroundImage2 = TestUtil.createBackgroundImage(site.getSiteId());
        BackgroundImage backgroundImage3 = TestUtil.createBackgroundImage(site.getSiteId());
        BackgroundImage backgroundImage4 = TestUtil.createBackgroundImage(site.getSiteId());
        BackgroundImage backgroundImage5 = TestUtil.createBackgroundImage(site.getSiteId());

        backgroundImage1.setCreated(new Date(currentDate.getTime() + 100000));
        backgroundImage2.setCreated(new Date(currentDate.getTime() + 200000));
        backgroundImage3.setCreated(new Date(currentDate.getTime() + 300000));
        backgroundImage4.setCreated(new Date(currentDate.getTime() + 400000));
        backgroundImage5.setCreated(new Date(currentDate.getTime() + 500000));

        service.execute(null, widgetItem.getDraftItem().getId(), null, false);

        Assert.assertNotNull(service.getDraftBackground());
        Assert.assertEquals(new ItemManager(widgetItem.getDraftItem()).getBackground(SiteShowOption.getDraftOption()), service.getDraftBackground());
        Assert.assertEquals(site.getSiteId(), service.getSiteId());
        Assert.assertNull(service.getWidgetTitle());
        Assert.assertNotNull(service.getImageThumbnailsDatas());
        Assert.assertEquals(5, service.getImageThumbnailsDatas().size());
        Assert.assertEquals(backgroundImage5.getBackgroundImageId(), service.getImageThumbnailsDatas().get(0).getImageId().intValue());
        Assert.assertEquals(backgroundImage4.getBackgroundImageId(), service.getImageThumbnailsDatas().get(1).getImageId().intValue());
        Assert.assertEquals(backgroundImage3.getBackgroundImageId(), service.getImageThumbnailsDatas().get(2).getImageId().intValue());
        Assert.assertEquals(backgroundImage2.getBackgroundImageId(), service.getImageThumbnailsDatas().get(3).getImageId().intValue());
        Assert.assertEquals(backgroundImage1.getBackgroundImageId(), service.getImageThumbnailsDatas().get(4).getImageId().intValue());
    }

    @Test
    public void showUploadedBackgroundImages() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        Date currentDate = new Date();
        BackgroundImage backgroundImage1 = TestUtil.createBackgroundImage(site.getSiteId());
        BackgroundImage backgroundImage2 = TestUtil.createBackgroundImage(site.getSiteId());
        BackgroundImage backgroundImage3 = TestUtil.createBackgroundImage(site.getSiteId());
        BackgroundImage backgroundImage4 = TestUtil.createBackgroundImage(site.getSiteId());
        BackgroundImage backgroundImage5 = TestUtil.createBackgroundImage(site.getSiteId());

        backgroundImage1.setCreated(new Date(currentDate.getTime() + 100000));
        backgroundImage2.setCreated(new Date(currentDate.getTime() + 200000));
        backgroundImage3.setCreated(new Date(currentDate.getTime() + 300000));
        backgroundImage4.setCreated(new Date(currentDate.getTime() + 400000));
        backgroundImage5.setCreated(new Date(currentDate.getTime() + 500000));

        service.showUploadedBackgroundImages(site.getSiteId());

        Assert.assertEquals(5, service.getImageThumbnailsDatas().size());
        Assert.assertEquals(backgroundImage5.getBackgroundImageId(), service.getImageThumbnailsDatas().get(0).getImageId().intValue());
        Assert.assertEquals(backgroundImage4.getBackgroundImageId(), service.getImageThumbnailsDatas().get(1).getImageId().intValue());
        Assert.assertEquals(backgroundImage3.getBackgroundImageId(), service.getImageThumbnailsDatas().get(2).getImageId().intValue());
        Assert.assertEquals(backgroundImage2.getBackgroundImageId(), service.getImageThumbnailsDatas().get(3).getImageId().intValue());
        Assert.assertEquals(backgroundImage1.getBackgroundImageId(), service.getImageThumbnailsDatas().get(4).getImageId().intValue());
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutRight() throws Exception {
        final Site site = TestUtil.createSite();
        //TestUtil.createUserAndLogin();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        final Border background = new Border();
        persistance.putBorder(background);

        final WidgetItem widgetBlog = new WidgetItem();
        widgetBlog.setBorderId(background.getId());
        persistance.putWidget(widgetBlog);
        pageVersion.addWidget(widgetBlog);

        service.execute(widgetBlog.getWidgetId(), null, null, false);
    }

    @Test(expected = UserNotLoginedException.class)
    public void getCheckedBackgroundByIdWithoutLogin() throws Exception {
        Site site = new Site();
        persistance.putSite(site);

        PageManager pageVersion = new PageManager(TestUtil.createPage(site));

        DraftBlog blog = new DraftBlog();
        persistance.putItem(blog);

        WidgetItem widget = TestUtil.createWidgetItem();
        widget.setDraftItem(blog);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        service.execute(widget.getWidgetId(), null, null, false);
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() throws Exception {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final WidgetItem widget = new WidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        service.execute(widget.getWidgetId(), null, null, false);
    }


    @Test(expected = WidgetNotFoundException.class)
    public void executeWithoutWidget() throws Exception {
        TestUtil.createUserAndLogin();
        service.execute(-1, null, null, false);
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ConfigureBackgroundService service = new ConfigureBackgroundService();

}