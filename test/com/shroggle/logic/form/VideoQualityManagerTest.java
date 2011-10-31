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
import com.shroggle.entity.FlvVideo;
import com.shroggle.logic.video.FlvVideoManager;

/**
 * @author Balakirev Anatoliy
 *         Date: 07.09.2009
 */
public class VideoQualityManagerTest {


    @Test
    public void testGetMinVideoQuality() {
        Assert.assertEquals(31, FlvVideo.MIN_VIDEO_QUALITY);
    }

    @Test
    public void testGetMaxVideoQuality() {
        Assert.assertEquals(1, FlvVideo.MAX_VIDEO_QUALITY);
    }

    @Test
    public void testGetDefaultVideoQuality() {
        Assert.assertEquals(7, FlvVideo.DEFAULT_VIDEO_QUALITY);
    }

    @Test
    public void testIsVideoQualityCorrect() {
        for (int i = FlvVideo.MAX_VIDEO_QUALITY; i <= FlvVideo.MIN_VIDEO_QUALITY; i++) {
            Assert.assertTrue(FlvVideoManager.isVideoQualityCorrect(i));
        }
        Assert.assertFalse(FlvVideoManager.isVideoQualityCorrect(-1));
        Assert.assertFalse(FlvVideoManager.isVideoQualityCorrect(0));
        Assert.assertFalse(FlvVideoManager.isVideoQualityCorrect(-100));
        Assert.assertFalse(FlvVideoManager.isVideoQualityCorrect(100));
        Assert.assertFalse(FlvVideoManager.isVideoQualityCorrect(32));
    }
}
