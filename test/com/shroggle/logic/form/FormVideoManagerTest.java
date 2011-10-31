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
package com.shroggle.logic.form;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import com.shroggle.entity.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;

/**
 * @author Balakirev Anatoliy
 *         Date: 04.09.2009
 */
@RunWith(TestRunnerWithMockServices.class)
public class FormVideoManagerTest {
    @Test
    public void testGetDefaultVideoQuality() {
        Assert.assertEquals(7, FlvVideo.DEFAULT_VIDEO_QUALITY);
    }

    @Test
    public void testCreateFormVideo() {
        FormVideo formVideo = new FormVideo();
        Assert.assertEquals(0, formVideo.getFormVideoId());
        Assert.assertEquals(null, formVideo.getImageId());
        Assert.assertEquals(null, formVideo.getVideoId());
        Assert.assertEquals(FlvVideo.DEFAULT_VIDEO_QUALITY, formVideo.getQuality());
    }


    @Test
    public void testCreateNewFormVideoOrUpdateExisting_createNew() {
        String values = "3 ";

        FormVideo formVideo = FormVideoManager.createNewFormVideoOrUpdateExisting(null, values);

        Assert.assertNotNull(formVideo);
        Assert.assertEquals(3, formVideo.getQuality());
        Assert.assertEquals(null, formVideo.getImageId());
        Assert.assertEquals(null, formVideo.getVideoId());
        Assert.assertEquals(true, formVideo.getFormVideoId() > 0);
        FormVideo newFormVideo = ServiceLocator.getPersistance().getFormVideoById(formVideo.getFormVideoId());
        Assert.assertEquals(formVideo, newFormVideo);
    }

    @Test
    public void testCreateNewFormVideoOrUpdateExisting_updateExisting() {
        Site site = TestUtil.createSite();
        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo;
        flvVideo = ServiceLocator.getPersistance().getFlvVideo(video.getVideoId(), 1000, 12345, formVideo.getQuality());
        Assert.assertNull(flvVideo);


        FormVideo newFormVideo = FormVideoManager.createNewFormVideoOrUpdateExisting(formVideo.getFormVideoId(), "3");


        flvVideo = ServiceLocator.getPersistance().getFlvVideo(video.getVideoId(), 1000, 12345, formVideo.getQuality());
        Assert.assertNull(flvVideo);
        Assert.assertNotNull(formVideo);
        Assert.assertEquals(3, formVideo.getQuality());
        Assert.assertEquals(image.getFormFileId(), formVideo.getImageId().intValue());
        Assert.assertEquals(video.getVideoId(), formVideo.getVideoId().intValue());
        Assert.assertEquals(true, formVideo.getFormVideoId() > 0);
        Assert.assertEquals(formVideo, newFormVideo);
    }


    @Test
    public void testCreateNewFormVideoOrUpdateExisting_updateExisting_withWrongVideoId() {
        Site site = TestUtil.createSite();
        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(-1, image.getFormFileId());
        FlvVideo flvVideo;
        flvVideo = ServiceLocator.getPersistance().getFlvVideo(video.getVideoId(), 1000, 12345, formVideo.getQuality());
        Assert.assertNull(flvVideo);
        Assert.assertNotNull(formVideo.getVideoId());


        FormVideo newFormVideo = FormVideoManager.createNewFormVideoOrUpdateExisting(formVideo.getFormVideoId(), "3");


        Assert.assertNull(formVideo.getVideoId());
        flvVideo = ServiceLocator.getPersistance().getFlvVideo(video.getVideoId(), 1000, 12345, formVideo.getQuality());
        Assert.assertNull(flvVideo);
        Assert.assertNotNull(formVideo);
        Assert.assertEquals(3, formVideo.getQuality());
        Assert.assertEquals(image.getFormFileId(), formVideo.getImageId().intValue());
        Assert.assertEquals(true, formVideo.getFormVideoId() > 0);
        Assert.assertEquals(formVideo, newFormVideo);
    }
}
