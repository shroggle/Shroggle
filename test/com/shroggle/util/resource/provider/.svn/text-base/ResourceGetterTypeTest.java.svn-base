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
package com.shroggle.util.resource.provider;

import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import com.shroggle.entity.FlvVideo;
import com.shroggle.entity.Video;
import com.shroggle.entity.Site;
import com.shroggle.TestUtil;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.util.config.Config;
import com.shroggle.util.config.ConfigResourcesVideo;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystemReal;
import junit.framework.Assert;

/**
 * @author Balakirev Anatoliy
 *         Date: 16.09.2009
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class ResourceGetterTypeTest {

    @Before
    public void before() {
        FileSystemReal fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        ServiceLocator.setFileSystem(fileSystem);
    }

    @Test
    public void getVideoUrl_withoutUseNgnix() {
        final Config config = ServiceLocator.getConfigStorage().get();
        final ConfigResourcesVideo resourcesVideo = config.getSiteResourcesVideo();
        resourcesVideo.setUseNgnix(false);

        Site site = TestUtil.createSite();
        Video video = TestUtil.createVideoForSite("name", site.getSiteId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(video.getVideoId(), 100, 100, 7);
        final String url = ResourceGetterType.FLV_VIDEO.getVideoUrl(flvVideo);
        Assert.assertEquals(ResourceGetterType.FLV_VIDEO.getUrl(flvVideo.getResourceId()), url);
    }

    @Ignore
    @Test
    public void getVideoUrl_withUseNgnix() {
        final Config config = ServiceLocator.getConfigStorage().get();
        final ConfigResourcesVideo resourcesVideo = config.getSiteResourcesVideo();
        resourcesVideo.setUseNgnix(true);

        Site site = TestUtil.createSite();
        Video video = TestUtil.createVideoForSite("name", site.getSiteId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(video.getVideoId(), 100, 100, 7);
        final String url = ResourceGetterType.FLV_VIDEO.getVideoUrl(flvVideo);
        Assert.assertNotSame(ResourceGetterType.FLV_VIDEO.getUrl(flvVideo.getResourceId()), url);
        Assert.assertEquals("//" + ServiceLocator.getFileSystem().getResourceName(flvVideo), url);
    }

    @Test
    public void getVideoUrl_forNullFlvFile() {
        Assert.assertNull(ResourceGetterType.FLV_VIDEO.getVideoUrl(null));
    }

}
