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

package com.shroggle.util.resource;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.entity.FlvVideo;
import com.shroggle.util.NowTimeMock;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.filesystem.FileSystemReal;
import com.shroggle.util.config.ConfigResourcesVideo;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.resource.provider.ResourceGetterType;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;

/**
 * @author Stasuk Artem
 */
@RunWith(TestRunnerWithMockServices.class)
public class ResourceGetterInternalTest {

    @Before
    public void before() {
        FileSystem fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        ServiceLocator.setFileSystem(fileSystem);
    }

    @Test
    public void getImageUrl() {
        Assert.assertEquals(
                "/resourceGetter.action?resourceId=11&resourceSizeId=0&resourceSizeAdditionId=0" +
                        "&resourceGetterType=IMAGE&resourceVersion=0&resourceDownload=false",
                resourceGetterUrl.get(ResourceGetterType.IMAGE, 11, 0, 0, 0, false));
    }

    @Test
    public void getImageUrlWithAdditionSize() {
        Assert.assertEquals(
                "/resourceGetter.action?resourceId=11&resourceSizeId=0&resourceSizeAdditionId=334" +
                        "&resourceGetterType=IMAGE&resourceVersion=0&resourceDownload=false",
                resourceGetterUrl.get(ResourceGetterType.IMAGE, 11, 0, 334, 0, false));
    }

    @Test
    public void getImageThumbnailUrl() {
        Assert.assertEquals(
                "/resourceGetter.action?resourceId=11&resourceSizeId=0&resourceSizeAdditionId=0" +
                        "&resourceGetterType=IMAGE_THUMBNAIL&resourceVersion=0&resourceDownload=false",
                resourceGetterUrl.get(ResourceGetterType.IMAGE_THUMBNAIL, 11, 0, 0, 0, false));
    }

    @Test
    public void getVideoUrl() {
        Assert.assertEquals(
                "/resourceGetter.action?resourceId=11&resourceVersion=0&resourceGetterType=FLV_VIDEO&resourceDownload=false",
                ResourceGetterType.FLV_VIDEO.getUrl(11));
    }

    @Ignore
    @Test
    public void getVideoUrlForNgnixWithSecureToken() {
        nowTime.setTime(1222344);
        final ConfigResourcesVideo resourcesVideo = configStorage.get().getSiteResourcesVideo();
        resourcesVideo.setUseNgnix(true);
        resourcesVideo.setNgnixSecretToken("ffffg#");
        resourcesVideo.setNgnixUrlPrefix("data");

        FlvVideo flv = new FlvVideo();
        flv.setWidth(12);
        flv.setHeight(3);
        flv.setSourceVideoId(4);

        Assert.assertEquals(
                "/data/9fe204cf9205878c517ad92ea6acce61/1222344/site0/video_id_4_width_12_height_3_quality_7.flv",
                ResourceGetterType.FLV_VIDEO.getVideoUrl(flv));
    }

    @Test
    public void getVideoUrl_forNullFlvFile() {
        Assert.assertNull(ResourceGetterType.FLV_VIDEO.getVideoUrl(null));
    }

    @Ignore
    @Test
    public void getVideoUrlForNgnix() {
        nowTime.setTime(1222344);
        final ConfigResourcesVideo resourcesVideo = configStorage.get().getSiteResourcesVideo();
        resourcesVideo.setUseNgnix(true);
        resourcesVideo.setNgnixUrlPrefix("data");

        FlvVideo flv = new FlvVideo();
        flv.setWidth(12);
        flv.setHeight(3);
        flv.setSourceVideoId(4);

        Assert.assertEquals(
                "/data/site0/video_id_4_width_12_height_3_quality_7.flv",
                ResourceGetterType.FLV_VIDEO.getVideoUrl(flv));
    }

    @Test
    public void getVideoUrlForInternal() {
        FlvVideo flv = new FlvVideo();
        flv.setFlvVideoId(11);

        Assert.assertEquals(
                "/resourceGetter.action?resourceId=11&resourceVersion=0&resourceGetterType=FLV_VIDEO&resourceDownload=false",
                ResourceGetterType.FLV_VIDEO.getVideoUrl(flv));
    }

    private final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
    private final ResourceGetterUrl resourceGetterUrl = ServiceLocator.getResourceGetter();
    private final NowTimeMock nowTime = (NowTimeMock) ServiceLocator.getNowTime();

}
