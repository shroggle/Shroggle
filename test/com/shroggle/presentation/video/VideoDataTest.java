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

import org.junit.Test;
import org.junit.Assert;
import com.shroggle.entity.Video;
import com.shroggle.exception.VideoNotFoundException;

/**
 * @author Balakirev Anatoliy
 *         Date: 30.09.2009
 */
public class VideoDataTest {


    @Test(expected = VideoNotFoundException.class)
    public void create_withoutVideo() {
        new VideoData(null);
    }

    @Test
    public void create_avi_withWidthAndHeightNotMultiplyerOfTwo() {
        Video video = new Video();
        video.setVideoId(111);
        video.setSourceWidth(123123);
        video.setSourceHeight(7356345);
        video.setSourceName("gkjsfdkjghsdkj");
        video.setSourceExtension("avi");
        VideoData videoData = new VideoData(video);
        Assert.assertEquals(String.valueOf(123124), videoData.getWidth());
        Assert.assertEquals(String.valueOf(7356346), videoData.getHeight());
        Assert.assertEquals(video.getSourceName(), videoData.getName());
        Assert.assertEquals(video.getVideoId(), videoData.getId());
        Assert.assertFalse(videoData.isAudioFile());
    }

    @Test
    public void create_avi() {
        Video video = new Video();
        video.setVideoId(111);
        video.setSourceWidth(123124);
        video.setSourceHeight(7356344);
        video.setSourceName("gkjsfdkjghsdkj");
        video.setSourceExtension("avi");
        VideoData videoData = new VideoData(video);
        Assert.assertEquals(String.valueOf(video.getSourceWidth()), videoData.getWidth());
        Assert.assertEquals(String.valueOf(video.getSourceHeight()), videoData.getHeight());
        Assert.assertEquals(video.getSourceName(), videoData.getName());
        Assert.assertEquals(video.getVideoId(), videoData.getId());
        Assert.assertFalse(videoData.isAudioFile());
    }


    @Test
    public void create_mp3() {
        Video video = new Video();
        video.setVideoId(111);
        video.setSourceWidth(123123);
        video.setSourceHeight(7356345);
        video.setSourceName("gkjsfdkjghsdkj");
        video.setSourceExtension("mp3");
        VideoData videoData = new VideoData(video);
        Assert.assertEquals("", videoData.getWidth());
        Assert.assertEquals("", videoData.getHeight());
        Assert.assertEquals(video.getSourceName(), videoData.getName());
        Assert.assertEquals(video.getVideoId(), videoData.getId());
        Assert.assertTrue(videoData.isAudioFile());
    }


    @Test
    public void create_wav() {
        Video video = new Video();
        video.setVideoId(111);
        video.setSourceWidth(123123);
        video.setSourceHeight(7356345);
        video.setSourceName("gkjsfdkjghsdkj");
        video.setSourceExtension("wav");
        VideoData videoData = new VideoData(video);
        Assert.assertEquals("", videoData.getWidth());
        Assert.assertEquals("", videoData.getHeight());
        Assert.assertEquals(video.getSourceName(), videoData.getName());
        Assert.assertEquals(video.getVideoId(), videoData.getId());
        Assert.assertTrue(videoData.isAudioFile());
    }

    @Test
    public void create_wav_withoutName() {
        Video video = new Video();
        video.setVideoId(111);
        video.setSourceWidth(123123);
        video.setSourceHeight(7356345);
        video.setSourceName(null);
        video.setSourceExtension("wav");
        VideoData videoData = new VideoData(video);
        Assert.assertEquals("", videoData.getWidth());
        Assert.assertEquals("", videoData.getHeight());
        Assert.assertEquals("", videoData.getName());
        Assert.assertEquals(video.getVideoId(), videoData.getId());
        Assert.assertTrue(videoData.isAudioFile());
    }

    
    @Test
    public void create_emptyConstructor() {
        VideoData videoData = new VideoData();
        Assert.assertEquals("", videoData.getWidth());
        Assert.assertEquals("", videoData.getHeight());
        Assert.assertEquals("", videoData.getName());
        Assert.assertEquals(-1, videoData.getId());
        Assert.assertFalse(videoData.isAudioFile());
    }
}
