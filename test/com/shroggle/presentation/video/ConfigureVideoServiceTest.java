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

package com.shroggle.presentation.video;

import com.shroggle.TestBaseWithMockService;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystem;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author Balakirev Anatoliy
 */
public class ConfigureVideoServiceTest extends TestBaseWithMockService {

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void executeFromSiteEditPage() throws Exception {
        Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        WidgetItem widgetVideo = TestUtil.createWidgetItem();
        persistance.putWidget(widgetVideo);
        pageVersion.addWidget(widgetVideo);

        final DraftVideo video1 = new DraftVideo();
        persistance.putItem(video1);
        widgetVideo.setDraftItem(video1);

        service.execute(widgetVideo.getWidgetId(), null);

        Assert.assertNotNull(service.getWidgetTitle());
        Assert.assertEquals(video1.getId(), service.getDraftVideo().getId());
    }

    @Test
    public void executeFromManageItems() throws Exception {
        Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftVideo video1 = new DraftVideo();
        video1.setSiteId(site.getSiteId());
        persistance.putItem(video1);

        service.execute(null, video1.getId());

        Assert.assertNull(service.getWidgetTitle());
        Assert.assertEquals(video1.getId(), service.getDraftVideo().getId());
    }

    @Test
    public void executeWithSelectedVideo() throws Exception {
        Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        FlvVideo flvVideo = new FlvVideo();
        persistance.putFlvVideo(flvVideo);

        WidgetItem widgetVideo = TestUtil.createWidgetItem();
        final DraftVideo video1 = new DraftVideo();
        persistance.putItem(video1);
        widgetVideo.setDraftItem(video1);
        video1.setFlvVideoId(flvVideo.getFlvVideoId());
        persistance.putWidget(widgetVideo);
        pageVersion.addWidget(widgetVideo);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        service.execute(widgetVideo.getWidgetId(), null);

        Assert.assertNotNull(service.getWidgetTitle());
        Assert.assertEquals(new Integer(site.getSiteId()), service.getSiteId());

        Assert.assertNull(service.getSelectedVideo());
        Assert.assertEquals(widgetVideo.getWidgetId(), service.getWidgetVideo().getWidgetId());
    }

    @Test
    public void executeWithSelectedVideo_WithEmptyImagesForVideo() throws Exception {
        Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        FlvVideo flvVideo = new FlvVideo();
        persistance.putFlvVideo(flvVideo);

        WidgetItem widgetVideo = TestUtil.createWidgetItem();
        final DraftVideo video1 = new DraftVideo();
        persistance.putItem(video1);
        widgetVideo.setDraftItem(video1);
        video1.setFlvVideoId(flvVideo.getFlvVideoId());
        persistance.putWidget(widgetVideo);
        pageVersion.addWidget(widgetVideo);

        for (int i = 0; i < 10; i++) {
            ImageForVideo imageForVideo = TestUtil.createImageForVideo(site.getSiteId(), 10, 10);
            File file = new File(getClass().getResource("test.png").toURI());
            fileSystem.setResourceStream(imageForVideo, new FileInputStream(file));
        }

        for (int i = 0; i < 10; i++) {
            ImageForVideo imageForVideo = TestUtil.createImageForVideo(site.getSiteId(), 0, 0);
            File file = new File(getClass().getResource("test.png").toURI());
            fileSystem.setResourceStream(imageForVideo, new FileInputStream(file));
        }

        for (int i = 0; i < 10; i++) {
            TestUtil.createImageForVideo(site.getSiteId(), 10, 10);
        }


        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        service.execute(widgetVideo.getWidgetId(), null);

        Assert.assertNotNull(service.getWidgetTitle());
        Assert.assertEquals(new Integer(site.getSiteId()), service.getSiteId());

        Assert.assertNull(service.getSelectedVideo());
        Assert.assertEquals(widgetVideo.getWidgetId(), service.getWidgetVideo().getWidgetId());

        Assert.assertEquals(30, ServiceLocator.getPersistance().getImagesForVideoBySiteId(site.getSiteId()).size());
        Assert.assertEquals(10, service.getImageThumbnailsWithPaths().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void executeWithWidgetWithoutItem() throws Exception {
        Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        WidgetItem widgetVideo = TestUtil.createWidgetItem();
        persistance.putWidget(widgetVideo);
        pageVersion.addWidget(widgetVideo);

        service.execute(widgetVideo.getWidgetId(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void executeWithNotFoundItem() throws Exception {
        Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        service.execute(null, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void executeWithoutBothWidgetAndItem() throws Exception {
        Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        service.execute(null, null);
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() throws Exception {
        service.execute(1, null);
    }

    private final ConfigureVideoService service = new ConfigureVideoService();
    private final FileSystem fileSystem = ServiceLocator.getFileSystem();
}