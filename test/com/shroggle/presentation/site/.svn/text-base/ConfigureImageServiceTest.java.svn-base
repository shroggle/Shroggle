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
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.presentation.image.ConfigureImageData;
import com.shroggle.presentation.image.ConfigureImageService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystemException;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;

@RunWith(value = TestRunnerWithMockServices.class)
public class ConfigureImageServiceTest {

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void executeWithoutImage() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widgetImage = TestUtil.createWidgetItem();
        final DraftImage image1 = new DraftImage();
        image1.setThumbnailWidth(21341243);
        image1.setThumbnailHeight(3241234);
        persistance.putItem(image1);
        widgetImage.setDraftItem(image1);
        persistance.putWidget(widgetImage);
        pageVersion.addWidget(widgetImage);

        service.execute(widgetImage.getWidgetId(), null);

        ConfigureImageData configureImageData = service.getConfigureImageData();
        Assert.assertEquals("undefined", configureImageData.getImageUrl());
        final DraftImage draftImage = (DraftImage)(widgetImage.getDraftItem());
        Assert.assertEquals(draftImage.getThumbnailWidth(), configureImageData.getWidth());
        Assert.assertEquals(draftImage.getThumbnailHeight(), configureImageData.getHeight());
    }

    @Test
    public void executeFromSiteEditPage() throws IOException, ServletException {
        final Site site = TestUtil.createSite("url", "title");
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widgetImage = TestUtil.createWidgetItem();
        pageVersion.addWidget(widgetImage);

        final DraftImage image1 = new DraftImage();
        persistance.putItem(image1);
        image1.setExtension("png");
        image1.setThumbnailWidth(200);
        image1.setThumbnailHeight(150);
        widgetImage.setDraftItem(image1);

        service.execute(widgetImage.getWidgetId(), null);

        ConfigureImageData configureImageData = service.getConfigureImageData();
        Assert.assertEquals("Image", service.getWidgetTitle().getWidgetTitle());
        Assert.assertEquals(pageVersion.getName(), service.getWidgetTitle().getPageVersionTitle());
        Assert.assertEquals(site.getTitle(), service.getWidgetTitle().getSiteTitle());
        Assert.assertEquals("undefined", configureImageData.getImageUrl());

        final DraftImage draftImage = (DraftImage)(widgetImage.getDraftItem());
        Assert.assertEquals(draftImage.getThumbnailWidth(), configureImageData.getWidth());
        Assert.assertEquals(draftImage.getThumbnailHeight(), configureImageData.getHeight());
    }

    @Test
    public void executeFromManageItems() throws IOException, ServletException {
        final Site site = TestUtil.createSite("url", "title");
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftImage image1 = new DraftImage();
        persistance.putItem(image1);
        image1.setExtension("png");
        image1.setThumbnailWidth(200);
        image1.setThumbnailHeight(150);

        service.execute(null, image1.getId());

        ConfigureImageData configureImageData = service.getConfigureImageData();
        Assert.assertNull(service.getWidgetTitle());
        Assert.assertEquals("undefined", configureImageData.getImageUrl());

        Assert.assertEquals(image1.getThumbnailWidth(), configureImageData.getWidth());
        Assert.assertEquals(image1.getThumbnailHeight(), configureImageData.getHeight());
    }

    @Test(expected = IllegalArgumentException.class)
    public void executeWithoutDraftImage() throws IOException, ServletException, FileSystemException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        service.execute(widget.getWidgetId(), null);
        Assert.assertEquals(null, service.getImageItem());
    }

    @Test(expected = WidgetNotFoundException.class)
    public void executeWithNotMy() throws IOException, ServletException, FileSystemException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndLogin();
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        service.execute(widget.getWidgetId(), null);
    }

    @Test(expected = WidgetNotFoundException.class)
    public void executeWithNotFoundWidget() throws IOException, ServletException, FileSystemException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        service.execute(1, null);
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() throws IOException, ServletException, FileSystemException {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        service.execute(widget.getWidgetId(), null);
    }

    private final ConfigureImageService service = new ConfigureImageService();
    private final Persistance persistance = ServiceLocator.getPersistance();

}
