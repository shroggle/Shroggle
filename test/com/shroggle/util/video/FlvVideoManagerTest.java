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
package com.shroggle.util.video;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.FlvVideo;
import com.shroggle.entity.Site;
import com.shroggle.entity.Video;
import com.shroggle.exception.FlvVideoNotFoundException;
import com.shroggle.logic.video.FlvVideoManager;
import com.shroggle.util.Dimension;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystemMock;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.transcode.VideoTranscode;
import com.shroggle.util.transcode.VideoTranscodeAsync;
import com.shroggle.util.transcode.VideoTranscodeMock;
import com.shroggle.util.transcode.VideoTranscodeMockWithSourceVideoCheck;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayInputStream;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class FlvVideoManagerTest {

    @Before
    public void before() {
        ServiceLocator.setVideoTranscode(new VideoTranscodeMockWithSourceVideoCheck());
    }

    @Test
    public void testCreateVideoFlv_withQuality() {
        Site site = TestUtil.createSite();
        final Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FlvVideo flvVideo = FlvVideoManager.createVideoFlv(video.getVideoId(), 100, 200, 1, video.getSiteId());
        Assert.assertNotNull(flvVideo);
        Assert.assertEquals(100, flvVideo.getWidth().intValue());
        Assert.assertEquals(200, flvVideo.getHeight().intValue());
        Assert.assertEquals(1, flvVideo.getQuality());
        Assert.assertEquals(site.getSiteId(), flvVideo.getSiteId());
        Assert.assertEquals(video.getVideoId(), flvVideo.getSourceVideoId());
    }

    @Test
    public void testCreate_withoutFlvVideo() {
        FlvVideoManager flvVideoManager = new FlvVideoManager(null);
        Assert.assertNotNull(flvVideoManager);
    }

    @Test
    public void testCreateLargerVideoDimension() {
        FlvVideo flvVideo = new FlvVideo();
        flvVideo.setWidth(100);
        flvVideo.setHeight(200);
        FlvVideoManager flvVideoManager = new FlvVideoManager(flvVideo);
        Dimension dimension = flvVideoManager.createLargeVideoDimension();
        Assert.assertNotNull(dimension);
        Assert.assertEquals(Math.round(flvVideo.getWidth() * FlvVideoManager.LARGER_VIDEO_MULTIPLIER), dimension.getWidth());
        Assert.assertEquals(Math.round(flvVideo.getHeight() * FlvVideoManager.LARGER_VIDEO_MULTIPLIER), dimension.getHeight());
    }


    @Test
    public void testCreateLargerVideoDimension_withoutWidth() {
        FlvVideo flvVideo = new FlvVideo();
        flvVideo.setWidth(null);
        flvVideo.setHeight(200);
        FlvVideoManager flvVideoManager = new FlvVideoManager(flvVideo);
        Dimension dimension = flvVideoManager.createLargeVideoDimension();
        Assert.assertNull(dimension);
    }

    @Test
    public void testCreateLargerVideoDimension_withoutHeight() {
        FlvVideo flvVideo = new FlvVideo();
        flvVideo.setWidth(100);
        flvVideo.setHeight(null);
        FlvVideoManager flvVideoManager = new FlvVideoManager(flvVideo);
        Dimension dimension = flvVideoManager.createLargeVideoDimension();
        Assert.assertNull(dimension);
    }

    @Test
    public void testCreateLargerVideoDimension_withoutWidthAndHeight() {
        FlvVideo flvVideo = new FlvVideo();
        flvVideo.setWidth(null);
        flvVideo.setHeight(null);
        FlvVideoManager flvVideoManager = new FlvVideoManager(flvVideo);
        Dimension dimension = flvVideoManager.createLargeVideoDimension();
        Assert.assertNull(dimension);
    }

    @Test(expected = FlvVideoNotFoundException.class)
    public void testCreateLargerVideoDimension_withoutVideo() {
        FlvVideoManager flvVideoManager = new FlvVideoManager(null);
        flvVideoManager.createLargeVideoDimension();
    }

    @Test
    public void testGetFlvVideoStatus_converting() {
        ServiceLocator.setVideoTranscode(videoTranscode);
        videoTranscodeMock.setExecuteTime(50L);
        final FlvVideo flvVideo = new FlvVideo();
        persistance.putFlvVideo(flvVideo);
        videoTranscode.execute(flvVideo, ServiceLocator.getPersistance().getVideoById(flvVideo.getSourceVideoId()));

        FlvVideoManager flvVideoManager = new FlvVideoManager(flvVideo);
        Assert.assertEquals("converting", flvVideoManager.getFlvVideoStatusAndStartNewConversionIfNeeded());
    }

    @Test
    public void testGetFlvVideoStatus_notFoundFlvVideoFile() {
        FlvVideoManager flvVideoManager = new FlvVideoManager(null);
        Assert.assertEquals("notfound", flvVideoManager.getFlvVideoStatusAndStartNewConversionIfNeeded());
    }

    @Test
    public void testGetFlvVideoStatus_withoutConvertedVideoAndConversionProcessButWithSourceVideoFile() {
        final FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();

        Site site = TestUtil.createSite();
        final Video video = new Video();
        video.setSiteId(site.getSiteId());
        video.setSourceExtension("avi");
        video.setSourceName("video name");
        persistance.putVideo(video);
        fileSystemMock.setResourceStream(video, new ByteArrayInputStream(new byte[0]));


        final FlvVideo flvVideo = new FlvVideo();
        flvVideo.setSourceVideoId(video.getVideoId());
        persistance.putFlvVideo(flvVideo);

        FlvVideoManager flvVideoManager = new FlvVideoManager(flvVideo);
        Assert.assertEquals("converting", flvVideoManager.getFlvVideoStatusAndStartNewConversionIfNeeded());
    }


    @Test
    public void testGetFlvVideoStatus_notFoundSourceFile() {
        final FlvVideo flvVideo = new FlvVideo();
        persistance.putFlvVideo(flvVideo);

        FlvVideoManager flvVideoManager = new FlvVideoManager(flvVideo);
        Assert.assertEquals("notfound", flvVideoManager.getFlvVideoStatusAndStartNewConversionIfNeeded());
    }


    @Test
    public void execute() {
        final FlvVideo flvVideo = new FlvVideo();
        persistance.putFlvVideo(flvVideo);
        final FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
        fileSystemMock.setResourceStream(flvVideo, new ByteArrayInputStream(new byte[0]));

        FlvVideoManager flvVideoManager = new FlvVideoManager(flvVideo);
        Assert.assertEquals("ok", flvVideoManager.getFlvVideoStatusAndStartNewConversionIfNeeded());
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final VideoTranscodeMock videoTranscodeMock = new VideoTranscodeMock();
    private final VideoTranscode videoTranscode = new VideoTranscodeAsync(videoTranscodeMock);
}
