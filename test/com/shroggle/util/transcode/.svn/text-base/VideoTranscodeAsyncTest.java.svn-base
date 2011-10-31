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
package com.shroggle.util.transcode;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import junit.framework.Assert;
import com.shroggle.entity.FlvVideo;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.process.ThreadUtil;
import com.shroggle.TestRunnerWithMockServices;

/**
 * @author Artem Stasuk
 */
@Ignore
@RunWith(TestRunnerWithMockServices.class)
public class VideoTranscodeAsyncTest {

    @Test
    public void isExecutingWithoutAny() {
        Assert.assertFalse(videoTranscode.isExecuting(1));
    }

    @Test
    public void isExecutingWithoutAnyBig() {
        Assert.assertFalse(videoTranscode.isExecuting(32423));
    }

    @Test
    public void isExecutingWithoutAnyNegative() {
        Assert.assertFalse(videoTranscode.isExecuting(-1));
    }

    @Test
    public void isExecutingWithoutIn() {
        final FlvVideo flvVideo = new FlvVideo();
        persistance.putFlvVideo(flvVideo);

        Assert.assertFalse(videoTranscode.isExecuting(flvVideo.getFlvVideoId()));
    }

    @Test
    public void isExecutingWithIn() {
        final FlvVideo flvVideo = new FlvVideo();
        persistance.putFlvVideo(flvVideo);
        videoTranscodeMock.setExecuteTime(50L);
        videoTranscode.execute(flvVideo, ServiceLocator.getPersistance().getVideoById(flvVideo.getSourceVideoId()));

        Assert.assertTrue(videoTranscode.isExecuting(flvVideo.getFlvVideoId()));

        ThreadUtil.sleep(100);
        Assert.assertFalse(videoTranscode.isExecuting(flvVideo.getFlvVideoId()));
    }

    @Test
    public void isExecutingWithInException() {
        final FlvVideo flvVideo = new FlvVideo();
        persistance.putFlvVideo(flvVideo);
        videoTranscodeMock.setExecuteTime(50L);
        videoTranscode.execute(flvVideo, ServiceLocator.getPersistance().getVideoById(flvVideo.getSourceVideoId()));

        ServiceLocator.setPersistance(null);

        Assert.assertTrue(videoTranscode.isExecuting(flvVideo.getFlvVideoId()));

        ThreadUtil.sleep(5);
        Assert.assertFalse(videoTranscode.isExecuting(flvVideo.getFlvVideoId()));
    }

    @Test
    public void isExecutingWithInManyLimit1() {
        videoTranscodeMock.setExecuteTime(50L);

        final FlvVideo flvVideo1 = new FlvVideo();
        persistance.putFlvVideo(flvVideo1);
        videoTranscode.execute(flvVideo1, ServiceLocator.getPersistance().getVideoById(flvVideo1.getSourceVideoId()));

        final FlvVideo flvVideo2 = new FlvVideo();
        persistance.putFlvVideo(flvVideo2);
        videoTranscode.execute(flvVideo2, ServiceLocator.getPersistance().getVideoById(flvVideo2.getSourceVideoId()));

        final FlvVideo flvVideo3 = new FlvVideo();
        persistance.putFlvVideo(flvVideo3);
        videoTranscode.execute(flvVideo3, ServiceLocator.getPersistance().getVideoById(flvVideo3.getSourceVideoId()));

        Assert.assertTrue(videoTranscode.isExecuting(flvVideo1.getFlvVideoId()));
        Assert.assertTrue(videoTranscode.isExecuting(flvVideo2.getFlvVideoId()));
        Assert.assertTrue(videoTranscode.isExecuting(flvVideo3.getFlvVideoId()));

        ThreadUtil.sleep(55);
        Assert.assertFalse(videoTranscode.isExecuting(flvVideo1.getFlvVideoId()));
        Assert.assertTrue(videoTranscode.isExecuting(flvVideo2.getFlvVideoId()));
        Assert.assertTrue(videoTranscode.isExecuting(flvVideo3.getFlvVideoId()));

        ThreadUtil.sleep(55);
        Assert.assertFalse(videoTranscode.isExecuting(flvVideo1.getFlvVideoId()));
        Assert.assertFalse(videoTranscode.isExecuting(flvVideo2.getFlvVideoId()));
        Assert.assertTrue(videoTranscode.isExecuting(flvVideo3.getFlvVideoId()));

        ThreadUtil.sleep(55);
        Assert.assertFalse(videoTranscode.isExecuting(flvVideo1.getFlvVideoId()));
        Assert.assertFalse(videoTranscode.isExecuting(flvVideo2.getFlvVideoId()));
        Assert.assertFalse(videoTranscode.isExecuting(flvVideo3.getFlvVideoId()));
    }

    @Test
    public void isExecutingWithInManyLimit2() {
        ServiceLocator.getConfigStorage().get().setConcurrentConvertThreadCount(2);
        final VideoTranscode videoTranscode = new VideoTranscodeAsync(videoTranscodeMock);
        videoTranscodeMock.setExecuteTime(50L);

        final FlvVideo flvVideo1 = new FlvVideo();
        persistance.putFlvVideo(flvVideo1);
        videoTranscode.execute(flvVideo1, ServiceLocator.getPersistance().getVideoById(flvVideo1.getSourceVideoId()));

        final FlvVideo flvVideo2 = new FlvVideo();
        persistance.putFlvVideo(flvVideo2);
        videoTranscode.execute(flvVideo2, ServiceLocator.getPersistance().getVideoById(flvVideo2.getSourceVideoId()));

        final FlvVideo flvVideo3 = new FlvVideo();
        persistance.putFlvVideo(flvVideo3);
        videoTranscode.execute(flvVideo3, ServiceLocator.getPersistance().getVideoById(flvVideo3.getSourceVideoId()));

        Assert.assertTrue(videoTranscode.isExecuting(flvVideo1.getFlvVideoId()));
        Assert.assertTrue(videoTranscode.isExecuting(flvVideo2.getFlvVideoId()));
        Assert.assertTrue(videoTranscode.isExecuting(flvVideo3.getFlvVideoId()));

        ThreadUtil.sleep(55);
        Assert.assertFalse(videoTranscode.isExecuting(flvVideo1.getFlvVideoId()));
        Assert.assertFalse(videoTranscode.isExecuting(flvVideo2.getFlvVideoId()));
        Assert.assertTrue(videoTranscode.isExecuting(flvVideo3.getFlvVideoId()));

        ThreadUtil.sleep(55);
        Assert.assertFalse(videoTranscode.isExecuting(flvVideo1.getFlvVideoId()));
        Assert.assertFalse(videoTranscode.isExecuting(flvVideo2.getFlvVideoId()));
        Assert.assertFalse(videoTranscode.isExecuting(flvVideo3.getFlvVideoId()));
    }

    @Test
    public void executeWithNull() {
        videoTranscode.execute(null, null);
    }

    private final VideoTranscodeMock videoTranscodeMock = new VideoTranscodeMock();
    private final VideoTranscode videoTranscode = new VideoTranscodeAsync(videoTranscodeMock);
    private final Persistance persistance = ServiceLocator.getPersistance();

}
