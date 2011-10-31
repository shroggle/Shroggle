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
package com.shroggle.util.resource.resize;

import org.junit.Test;
import org.junit.Assert;
import com.shroggle.exception.VideoNotFoundException;
import com.shroggle.entity.Video;

/**
 * @author Balakirev Anatoliy
 *         Date: 22.09.2009
 */
public class FfmpegVideoSizeCreatorTest {


    @Test(expected = VideoNotFoundException.class)
    public void create_withoutVideo() {
        new FfmpegVideoSizeCreator(null);
    }


    @Test
    public void testCreateFfmpegResizeSettings128x72() {
        Video video = new Video();
        video.setSourceWidth(640);
        video.setSourceHeight(480);
        FfmpegVideoSizeCreator ffmpegVideoSizeCreator = new FfmpegVideoSizeCreator(video);
        String ffmpegSettings = ffmpegVideoSizeCreator.execute(128, 72);
        Assert.assertEquals(" -s 96x72 -padleft 16 -padright 16 ", ffmpegSettings);
    }

    @Test
    public void testCreateFfmpegResizeSettings256x144() {
        Video video = new Video();
        video.setSourceWidth(640);
        video.setSourceHeight(480);
        FfmpegVideoSizeCreator ffmpegVideoSizeCreator = new FfmpegVideoSizeCreator(video);
        String ffmpegSettings = ffmpegVideoSizeCreator.execute(256, 144);
        Assert.assertEquals(" -s 192x144 -padleft 32 -padright 32 ", ffmpegSettings);
    }

    @Test
    public void testCreateFfmpegResizeSettings800x600() {
        Video video = new Video();
        video.setSourceWidth(640);
        video.setSourceHeight(480);
        FfmpegVideoSizeCreator ffmpegVideoSizeCreator = new FfmpegVideoSizeCreator(video);
        String ffmpegSettings = ffmpegVideoSizeCreator.execute(800, 600);
        Assert.assertEquals(" -s 800x600 ", ffmpegSettings);
    }

    @Test
    public void testCreateFfmpegResizeSettings720x576() {
        Video video = new Video();
        video.setSourceWidth(640);
        video.setSourceHeight(480);
        FfmpegVideoSizeCreator ffmpegVideoSizeCreator = new FfmpegVideoSizeCreator(video);
        String ffmpegSettings = ffmpegVideoSizeCreator.execute(720, 576);
        Assert.assertEquals(" -s 720x540 -padtop 18 -padbottom 18 ", ffmpegSettings);
    }


    @Test
    public void testCreateFfmpegResizeSettings1280x480() {
        Video video = new Video();
        video.setSourceWidth(640);
        video.setSourceHeight(480);
        FfmpegVideoSizeCreator ffmpegVideoSizeCreator = new FfmpegVideoSizeCreator(video);
        String ffmpegSettings = ffmpegVideoSizeCreator.execute(1280, 480);
        Assert.assertEquals(" -s 640x480 -padleft 320 -padright 320 ", ffmpegSettings);
    }


    @Test
    public void testCreateFfmpegResizeSettings640x960() {
        Video video = new Video();
        video.setSourceWidth(640);
        video.setSourceHeight(480);
        FfmpegVideoSizeCreator ffmpegVideoSizeCreator = new FfmpegVideoSizeCreator(video);
        String ffmpegSettings = ffmpegVideoSizeCreator.execute(640, 960);
        Assert.assertEquals(" -s 640x480 -padtop 240 -padbottom 240 ", ffmpegSettings);
    }

    @Test
    public void testCreateFfmpegResizeSettings100x200() {
        Video video = new Video();
        video.setSourceWidth(320);
        video.setSourceHeight(240);
        FfmpegVideoSizeCreator ffmpegVideoSizeCreator = new FfmpegVideoSizeCreator(video);
        String ffmpegSettings = ffmpegVideoSizeCreator.execute(100, 200);
        Assert.assertEquals(" -s 100x76 -padtop 62 -padbottom 62 ", ffmpegSettings);
    }


    @Test
    public void testCreateFfmpegResizeSettings720x480() {
        Video video = new Video();
        video.setSourceWidth(720);
        video.setSourceHeight(480);
        FfmpegVideoSizeCreator ffmpegVideoSizeCreator = new FfmpegVideoSizeCreator(video);
        String ffmpegSettings = ffmpegVideoSizeCreator.execute(640, 480);
        Assert.assertEquals(" -s 640x426 -padtop 28 -padbottom 28 ", ffmpegSettings);
    }

    @Test
    public void testCreateFfmpegResizeSettings153x754() {
        Video video = new Video();
        video.setSourceWidth(153);
        video.setSourceHeight(754);
        FfmpegVideoSizeCreator ffmpegVideoSizeCreator = new FfmpegVideoSizeCreator(video);
        String ffmpegSettings = ffmpegVideoSizeCreator.execute(640, 480);
        Assert.assertEquals(" -s 98x480 -padleft 272 -padright 272 ", ffmpegSettings);
    }

    @Test
    public void testCreateFfmpegResizeSettingsWithoutWidth() {
        Video video = new Video();
        video.setSourceWidth(153);
        video.setSourceHeight(754);
        FfmpegVideoSizeCreator ffmpegVideoSizeCreator = new FfmpegVideoSizeCreator(video);
        String ffmpegSettings = ffmpegVideoSizeCreator.execute(null, 480);
        Assert.assertEquals(" ", ffmpegSettings);
    }

    @Test
    public void testCreateFfmpegResizeSettingsWithoutHeight() {
        Video video = new Video();
        video.setSourceWidth(153);
        video.setSourceHeight(754);
        FfmpegVideoSizeCreator ffmpegVideoSizeCreator = new FfmpegVideoSizeCreator(video);
        String ffmpegSettings = ffmpegVideoSizeCreator.execute(213, null);
        Assert.assertEquals(" ", ffmpegSettings);
    }

    @Test
    public void testCreateFfmpegResizeSettingsWithoutWidthAndHeight() {
        Video video = new Video();
        video.setSourceWidth(153);
        video.setSourceHeight(754);
        FfmpegVideoSizeCreator ffmpegVideoSizeCreator = new FfmpegVideoSizeCreator(video);
        String ffmpegSettings = ffmpegVideoSizeCreator.execute(null, null);
        Assert.assertEquals(" ", ffmpegSettings);
    }
}
