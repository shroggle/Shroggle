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

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.entity.FlvVideo;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystemMock;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.transcode.VideoTranscodeMockWithSourceVideoCheck;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayInputStream;

/**
 * @author Stasuk Artem
 */
@RunWith(TestRunnerWithMockServices.class)
public class CheckFlvVideoActionTest {

    @Before
    public void before() {
        videoTranscodeMock = new VideoTranscodeMockWithSourceVideoCheck();
        ServiceLocator.setVideoTranscode(videoTranscodeMock);
    }


    @Test
    public void executeForNotExist() {
        action.setFlvVideoId(1);

        action.execute();

        Assert.assertEquals("notfound", action.getResult());
    }

    @Test
    public void executeForNotExistData() {
        final FlvVideo flvVideo = new FlvVideo();
        persistance.putFlvVideo(flvVideo);
        action.setFlvVideoId(flvVideo.getFlvVideoId());

        action.execute();

        Assert.assertEquals("notfound", action.getResult());
    }

    @Test
    public void executeForNowConverting() {
        final FlvVideo flvVideo = new FlvVideo();
        persistance.putFlvVideo(flvVideo);

        videoTranscodeMock.getExecuting().add(flvVideo.getFlvVideoId());

        action.setFlvVideoId(flvVideo.getFlvVideoId());

        action.execute();

        Assert.assertEquals("converting", action.getResult());
    }

    @Test
    public void execute() {
        final FlvVideo flvVideo = new FlvVideo();
        persistance.putFlvVideo(flvVideo);

        fileSystemMock.setResourceStream(flvVideo, new ByteArrayInputStream(new byte[0]));

        action.setFlvVideoId(flvVideo.getFlvVideoId());

        action.execute();

        Assert.assertEquals("ok", action.getResult());
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
    private VideoTranscodeMockWithSourceVideoCheck videoTranscodeMock;
    private final CheckFlvVideoAction action = new CheckFlvVideoAction();

}
