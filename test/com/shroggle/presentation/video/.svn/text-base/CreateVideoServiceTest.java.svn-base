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
import com.shroggle.exception.VideoNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.video.CreateVideoRequest;
import com.shroggle.presentation.MockWebContext;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import java.io.IOException;


/**
 * @author Balakirev Anatoliy
 */
public class CreateVideoServiceTest extends TestBaseWithMockService {

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void executeFromSiteEditPage() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        final DraftVideo video1 = TestUtil.createVideo1(site);
        widget.setDraftItem(video1);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        Video video = new Video();
        video.setSiteId(1);
        video.setSourceExtension("avi");
        video.setSourceName("1.avi");
        persistance.putVideo(video);

        CreateVideoRequest edit = new CreateVideoRequest();
        edit.setHeight(100);
        edit.setWidth(4100);
        edit.setIncludeDescription(true);
        edit.setKeywords("Keywords");
        edit.setPlayInCurrentPage(true);
        edit.setVideoDescription("Description");
        edit.setVideoId(video.getVideoId());
        edit.setVideoImageId(-1);
        edit.setVideoName("VideoName");
        edit.setWidgetId(widget.getWidgetId());
        edit.setVideoItemId(video1.getId());

        service.execute(edit);
        Assert.assertEquals("Keywords", video1.getKeywords());
        Assert.assertEquals("Description", video1.getDescription());
        Assert.assertEquals("VideoName", video1.getName());
        Assert.assertNull(video1.getImageId());
    }

    @Test
    public void executeFromManageItems() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftVideo video1 = TestUtil.createVideo1(site);

        Video video = new Video();
        video.setSiteId(1);
        video.setSourceExtension("avi");
        video.setSourceName("1.avi");
        persistance.putVideo(video);

        CreateVideoRequest edit = new CreateVideoRequest();
        edit.setHeight(100);
        edit.setWidth(4100);
        edit.setIncludeDescription(true);
        edit.setKeywords("Keywords");
        edit.setPlayInCurrentPage(true);
        edit.setVideoDescription("Description");
        edit.setVideoId(video.getVideoId());
        edit.setVideoImageId(-1);
        edit.setVideoName("VideoName");
        edit.setVideoItemId(video1.getId());

        service.execute(edit);
        Assert.assertEquals("Keywords", video1.getKeywords());
        Assert.assertEquals("Description", video1.getDescription());
        Assert.assertEquals("VideoName", video1.getName());
        Assert.assertNull(video1.getImageId());
    }

    @Test
    public void executeWithDisplayTwoOptions() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        final DraftVideo video1 = TestUtil.createVideo1(site);
        widget.setDraftItem(video1);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        Video video = new Video();
        video.setSiteId(1);
        video.setSourceExtension("avi");
        video.setSourceName("1.avi");
        persistance.putVideo(video);

        CreateVideoRequest edit = new CreateVideoRequest();
        edit.setHeight(100);
        edit.setWidth(4100);
        edit.setIncludeDescription(true);
        edit.setKeywords("Keywords");
        edit.setPlayInCurrentPage(true);
        edit.setVideoSmallSize("320x240");
        edit.setVideoLargeSize("800x600");
        edit.setVideoDescription("Description");
        edit.setVideoId(video.getVideoId());
        edit.setVideoImageId(-1);
        edit.setVideoName("VideoName");
        edit.setWidgetId(widget.getWidgetId());
        edit.setDisplaySmallOptions(true);
        edit.setDisplayLargeOptions(true);
        edit.setVideoItemId(video1.getId());

        Assert.assertNull(video1.getSmallFlvVideoId());
        Assert.assertNull(video1.getLargeFlvVideoId());
        Assert.assertNull(video1.getFlvVideoId());

        service.execute(edit);
        Assert.assertEquals("Keywords", video1.getKeywords());
        Assert.assertEquals("Description", video1.getDescription());
        Assert.assertEquals("VideoName", video1.getName());
        Assert.assertNull(video1.getImageId());
        Assert.assertEquals(new Integer(1), video1.getSmallFlvVideoId());
        Assert.assertEquals(new Integer(2), video1.getLargeFlvVideoId());
        Assert.assertEquals(new Integer(3), video1.getFlvVideoId());
    }

    @Test
    public void executeWithDisplayTwoOptions_withExistingVideo() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        final DraftVideo video1 = TestUtil.createVideo1(site);
        widget.setDraftItem(video1);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        Video video = new Video();
        video.setSiteId(1);
        video.setSourceExtension("avi");
        video.setSourceName("1.avi");
        persistance.putVideo(video);

        CreateVideoRequest request = new CreateVideoRequest();
        request.setHeight(100);
        request.setWidth(4100);
        request.setIncludeDescription(true);
        request.setKeywords("Keywords");
        request.setPlayInCurrentPage(true);
        request.setVideoSmallSize("320x240");
        request.setVideoLargeSize("800x600");
        request.setVideoDescription("Description");
        request.setVideoId(video.getVideoId());
        request.setVideoImageId(-1);
        request.setVideoName("VideoName");
        request.setWidgetId(widget.getWidgetId());
        request.setDisplaySmallOptions(true);
        request.setDisplayLargeOptions(true);
        request.setVideoItemId(video1.getId());

        service.execute(request);
        Assert.assertEquals("Keywords", video1.getKeywords());
        Assert.assertEquals("Description", video1.getDescription());
        Assert.assertEquals("VideoName", video1.getName());
        Assert.assertNull(video1.getImageId());

        Assert.assertEquals(new Integer(1), video1.getSmallFlvVideoId());
        Assert.assertEquals(new Integer(2), video1.getLargeFlvVideoId());
        Assert.assertEquals(new Integer(3), video1.getFlvVideoId());

        service.execute(request);

        Assert.assertEquals(new Integer(1), video1.getSmallFlvVideoId());
        Assert.assertEquals(new Integer(2), video1.getLargeFlvVideoId());
        Assert.assertEquals(new Integer(3), video1.getFlvVideoId());
    }

    @Test
    public void executeWithDisplayTwoOptions_withExistingVideoButNotSetToWidget() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        final DraftVideo video1 = TestUtil.createVideo1(site);
        widget.setDraftItem(video1);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        Video video = new Video();
        video.setSiteId(1);
        video.setSourceExtension("avi");
        video.setSourceName("1.avi");
        persistance.putVideo(video);

        FlvVideo flvVideo1 = new FlvVideo();
        flvVideo1.setSourceVideoId(video.getVideoId());
        flvVideo1.setWidth(400);
        flvVideo1.setHeight(300);
        persistance.putFlvVideo(flvVideo1);

        FlvVideo flvVideo2 = new FlvVideo();
        flvVideo2.setSourceVideoId(video.getVideoId());
        flvVideo2.setWidth(100);
        flvVideo2.setHeight(100);
        persistance.putFlvVideo(flvVideo2);

        FlvVideo flvVideo3 = new FlvVideo();
        flvVideo3.setSourceVideoId(video.getVideoId());
        flvVideo3.setWidth(300);
        flvVideo3.setHeight(170);
        persistance.putFlvVideo(flvVideo3);

        FlvVideo flvVideo4 = new FlvVideo();
        flvVideo4.setSourceVideoId(1000);
        flvVideo4.setWidth(300);
        flvVideo4.setHeight(170);
        persistance.putFlvVideo(flvVideo4);

        CreateVideoRequest edit = new CreateVideoRequest();
        edit.setHeight(100);
        edit.setWidth(100);
        edit.setIncludeDescription(true);
        edit.setKeywords("Keywords");
        edit.setPlayInCurrentPage(true);
        edit.setVideoSmallSize("300x170");
        edit.setVideoLargeSize("400x300");
        edit.setVideoDescription("Description");
        edit.setVideoId(video.getVideoId());
        edit.setVideoImageId(-1);
        edit.setVideoName("VideoName");
        edit.setWidgetId(widget.getWidgetId());
        edit.setDisplaySmallOptions(true);
        edit.setDisplayLargeOptions(true);
        edit.setVideoItemId(video1.getId());

        service.execute(edit);
        Assert.assertEquals("Keywords", video1.getKeywords());
        Assert.assertEquals("Description", video1.getDescription());
        Assert.assertEquals("VideoName", video1.getName());
        Assert.assertNull(video1.getImageId());

        Assert.assertEquals(new Integer(flvVideo3.getFlvVideoId()), video1.getSmallFlvVideoId());
        Assert.assertEquals(new Integer(flvVideo1.getFlvVideoId()), video1.getLargeFlvVideoId());
        Assert.assertEquals(new Integer(flvVideo2.getFlvVideoId()), video1.getFlvVideoId());
    }

    @Test
    public void executeWithDisplayTwoOptions_withoutExistingVideo() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        final DraftVideo video1 = TestUtil.createVideo1(site);
        widget.setDraftItem(video1);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        Video video = new Video();
        video.setSiteId(1);
        video.setSourceExtension("avi");
        video.setSourceName("1.avi");
        persistance.putVideo(video);

        FlvVideo flvVideo1 = new FlvVideo();
        flvVideo1.setSourceVideoId(video.getVideoId());
        flvVideo1.setWidth(400);
        flvVideo1.setHeight(300);
        persistance.putFlvVideo(flvVideo1);

        FlvVideo flvVideo2 = new FlvVideo();
        flvVideo2.setSourceVideoId(video.getVideoId());
        flvVideo2.setWidth(100);
        flvVideo2.setHeight(100);
        persistance.putFlvVideo(flvVideo2);

        FlvVideo flvVideo3 = new FlvVideo();
        flvVideo3.setSourceVideoId(video.getVideoId());
        flvVideo3.setWidth(300);
        flvVideo3.setHeight(170);
        persistance.putFlvVideo(flvVideo3);

        FlvVideo flvVideo4 = new FlvVideo();
        flvVideo4.setSourceVideoId(1000);
        flvVideo4.setWidth(300);
        flvVideo4.setHeight(170);
        persistance.putFlvVideo(flvVideo4);

        CreateVideoRequest edit = new CreateVideoRequest();
        edit.setHeight(1000);
        edit.setWidth(1000);
        edit.setIncludeDescription(true);
        edit.setKeywords("Keywords");
        edit.setPlayInCurrentPage(true);
        edit.setVideoSmallSize("301x170");
        edit.setVideoLargeSize("400x400");
        edit.setVideoDescription("Description");
        edit.setVideoId(video.getVideoId());
        edit.setVideoImageId(-1);
        edit.setVideoName("VideoName");
        edit.setWidgetId(widget.getWidgetId());
        edit.setDisplaySmallOptions(true);
        edit.setDisplayLargeOptions(true);
        edit.setVideoItemId(video1.getId());

        service.execute(edit);
        Assert.assertEquals("Keywords", video1.getKeywords());
        Assert.assertEquals("Description", video1.getDescription());
        Assert.assertEquals("VideoName", video1.getName());
        Assert.assertNull(video1.getImageId());

        final int smallSizeVideoId = video1.getSmallFlvVideoId();
        final int largeSizeVideoId = video1.getLargeFlvVideoId();
        final int normalSizeVideoId = video1.getFlvVideoId();

        Assert.assertNotSame(flvVideo1.getFlvVideoId(), smallSizeVideoId);
        Assert.assertNotSame(flvVideo2.getFlvVideoId(), smallSizeVideoId);
        Assert.assertNotSame(flvVideo3.getFlvVideoId(), smallSizeVideoId);
        Assert.assertNotSame(flvVideo4.getFlvVideoId(), smallSizeVideoId);

        Assert.assertNotSame(flvVideo1.getFlvVideoId(), largeSizeVideoId);
        Assert.assertNotSame(flvVideo2.getFlvVideoId(), largeSizeVideoId);
        Assert.assertNotSame(flvVideo3.getFlvVideoId(), largeSizeVideoId);
        Assert.assertNotSame(flvVideo4.getFlvVideoId(), largeSizeVideoId);

        Assert.assertNotSame(flvVideo1.getFlvVideoId(), normalSizeVideoId);
        Assert.assertNotSame(flvVideo2.getFlvVideoId(), normalSizeVideoId);
        Assert.assertNotSame(flvVideo3.getFlvVideoId(), normalSizeVideoId);
        Assert.assertNotSame(flvVideo4.getFlvVideoId(), normalSizeVideoId);
    }

    @Test
    public void executeWithVideo() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Video video = new Video();
        video.setSiteId(1);
        video.setSourceExtension("avi");
        video.setSourceName("1.avi");
        persistance.putVideo(video);

        FlvVideo flvVideo = new FlvVideo();
        flvVideo.setSourceVideoId(video.getVideoId());
        flvVideo.setWidth(4100);
        flvVideo.setHeight(100);
        persistance.putFlvVideo(flvVideo);

        WidgetItem widget = TestUtil.createWidgetItem();
        final DraftVideo video1 = TestUtil.createVideo1(site);
        widget.setDraftItem(video1);
        video1.setFlvVideoId(flvVideo.getFlvVideoId());
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        CreateVideoRequest edit = new CreateVideoRequest();
        edit.setHeight(100);
        edit.setWidth(410);
        edit.setIncludeDescription(true);
        edit.setKeywords("Keywords");
        edit.setPlayInCurrentPage(true);
        edit.setVideoDescription("Description");
        edit.setVideoId(video.getVideoId());
        edit.setVideoImageId(-1);
        edit.setVideoName("VideoName");
        edit.setWidgetId(widget.getWidgetId());
        edit.setVideoItemId(video1.getId());

        service.execute(edit);
        Assert.assertEquals("Keywords", video1.getKeywords());
        Assert.assertEquals("Description", video1.getDescription());
        Assert.assertEquals("VideoName", video1.getName());
        Assert.assertNull(video1.getImageId());
    }

    @Test
    public void executeWithNotSameVideoSize() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Video video = new Video();
        video.setSiteId(1);
        video.setSourceExtension("avi");
        video.setSourceName("1.avi");
        persistance.putVideo(video);

        FlvVideo flvVideo = new FlvVideo();
        flvVideo.setSourceVideoId(video.getVideoId());
        flvVideo.setWidth(410);
        flvVideo.setHeight(100);
        persistance.putFlvVideo(flvVideo);

        WidgetItem widget = TestUtil.createWidgetItem();
        final DraftVideo video1 = TestUtil.createVideo1(site);
        widget.setDraftItem(video1);
        video1.setFlvVideoId(flvVideo.getFlvVideoId());
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        flvVideo.setSiteId(site.getSiteId());
        persistance.putFlvVideo(flvVideo);

        CreateVideoRequest edit = new CreateVideoRequest();
        edit.setHeight(100);
        edit.setWidth(4100);
        edit.setIncludeDescription(true);
        edit.setKeywords("Keywords");
        edit.setPlayInCurrentPage(true);
        edit.setVideoDescription("Description");
        edit.setVideoId(video.getVideoId());
        edit.setVideoImageId(-1);
        edit.setVideoName("VideoName");
        edit.setWidgetId(widget.getWidgetId());
        edit.setVideoItemId(video1.getId());

        flvVideo.setWidth(400);
        persistance.putFlvVideo(flvVideo);
        service.execute(edit);
        Assert.assertEquals("Keywords", video1.getKeywords());
        Assert.assertEquals("Description", video1.getDescription());
        Assert.assertEquals("VideoName", video1.getName());
        Assert.assertNull(video1.getImageId());
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() throws ServletException, IOException {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        final CreateVideoRequest edit = new CreateVideoRequest();
        service.execute(edit);
    }

    @Test(expected = VideoNotFoundException.class)
    public void executeWithoutVideo() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        Widget widgetVideo = new WidgetItem();
        persistance.putWidget(widgetVideo);
        pageVersion.addWidget(widgetVideo);

        final CreateVideoRequest edit = new CreateVideoRequest();
        edit.setWidgetId(widgetVideo.getWidgetId());

        service.execute(edit);
    }

    private final CreateVideoService service = new CreateVideoService();

}