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

import com.shroggle.logic.video.VideoDataManager;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.util.filesystem.FileSystemReal;
import com.shroggle.util.ServiceLocator;
import com.shroggle.presentation.video.VideoData;
import com.shroggle.entity.Video;
import com.shroggle.entity.Site;

import java.util.Date;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 *         Date: 30.09.2009
 */
@RunWith(TestRunnerWithMockServices.class)
public class VideoDataManagerTest {

    @Before
    public void before() {
        ServiceLocator.setFileSystem(new FileSystemReal(null, null, null, null, null, null, null, null, null));
    }


    @Test
    public void testCreateVideoDatas() {
        final Date currentDate = new Date();
        Site site = TestUtil.createSite();
        Video video1 = TestUtil.createVideoForSite("videoName1", site.getSiteId());
        video1.setCreated(new Date(currentDate.getTime() + 10000));
        Video video2 = TestUtil.createVideoForSite("videoName2", site.getSiteId());
        video2.setCreated(new Date(currentDate.getTime() + 20000));
        Video video3 = TestUtil.createVideoForSite("videoName3", site.getSiteId());
        video3.setCreated(new Date(currentDate.getTime() + 30000));
        Video video4 = TestUtil.createVideoForSite("videoName4", site.getSiteId());
        video4.setCreated(new Date(currentDate.getTime() + 40000));


        List<VideoData> videoDatas = VideoDataManager.createVideoDatas(site.getSiteId(), video2.getVideoId());


        Assert.assertNotNull(videoDatas);
        Assert.assertEquals(4, videoDatas.size());
        // video2 is selected because of this it is on the first position
        Assert.assertEquals(video2.getVideoId(), videoDatas.get(0).getId());
        Assert.assertEquals(video4.getVideoId(), videoDatas.get(1).getId());
        Assert.assertEquals(video3.getVideoId(), videoDatas.get(2).getId());
        Assert.assertEquals(video1.getVideoId(), videoDatas.get(3).getId());
    }


    @Test
    public void testCreateVideoDatas_withoutOneFile() {
        final Date currentDate = new Date();
        Site site = TestUtil.createSite();
        Video video1 = TestUtil.createVideoForSite("videoName1", site.getSiteId());
        video1.setCreated(new Date(currentDate.getTime() + 10000));
        Video video2 = TestUtil.createVideoForSite("videoName2", site.getSiteId());
        video2.setCreated(new Date(currentDate.getTime() + 20000));
        Video video3 = TestUtil.createVideoForSite("videoName3", site.getSiteId());
        video3.setCreated(new Date(currentDate.getTime() + 30000));
        Video video4 = new Video();
        video4.setSourceName("videoName4");
        video4.setSiteId(site.getSiteId());
        ServiceLocator.getPersistance().putVideo(video4);
        video4.setCreated(new Date(currentDate.getTime() + 40000));


        List<VideoData> videoDatas = VideoDataManager.createVideoDatas(site.getSiteId(), video2.getVideoId());


        
        Assert.assertNotNull(videoDatas);
        Assert.assertEquals(3, videoDatas.size());
        // video2 is selected because of this it is on the first position
        Assert.assertEquals(video2.getVideoId(), videoDatas.get(0).getId());
        Assert.assertEquals(video3.getVideoId(), videoDatas.get(1).getId());
        Assert.assertEquals(video1.getVideoId(), videoDatas.get(2).getId());
    }

}
