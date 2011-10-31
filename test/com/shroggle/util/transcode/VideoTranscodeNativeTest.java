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

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.entity.Video;
import com.shroggle.entity.FlvVideo;
import com.shroggle.util.IOUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.filesystem.FileSystemReal;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.SystemConsoleMock;
import com.shroggle.util.process.SystemConsoleReal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class VideoTranscodeNativeTest {

    @SuppressWarnings({"ResultOfMethodCallIgnored"})
    @Before
    public void before() throws IOException {
        fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        ServiceLocator.setFileSystem(fileSystem);
        systemConsole = new SystemConsoleMock(new SystemConsoleReal());
        ServiceLocator.setSystemConsole(systemConsole);

        Video video = new Video();
        video.setSiteId(1);
        video.setSourceExtension("avi");
        video.setSourceWidth(320);
        video.setSourceHeight(240);
        persistance.putVideo(video);

        flvVideo = new FlvVideo();
        flvVideo.setSiteId(video.getSiteId());
        flvVideo.setWidth(100);
        flvVideo.setHeight(200);        
        flvVideo.setSourceVideoId(video.getVideoId());
        persistance.putFlvVideo(flvVideo);

        final URL videoSourcePath = VideoTranscodeNativeTest.class.getResource("test.avi");
        new File(fileSystem.getResourcePath(video)).getParentFile().mkdirs();
        fileSystem.removeResource(flvVideo);
        IOUtil.copyFile(videoSourcePath.getFile(), fileSystem.getResourcePath(video));
    }

    @Test
    public void convertToFlv() {
        videoTranscode.execute(flvVideo, ServiceLocator.getPersistance().getVideoById(flvVideo.getSourceVideoId()));

        Assert.assertTrue(new File(fileSystem.getResourcePath(flvVideo)).exists());
    }

    @Test
    public void convertToFlvWithErrorOnFlvMeta() {
        systemConsole.getExecutedCommandResults().add(1);

        videoTranscode.execute(flvVideo, ServiceLocator.getPersistance().getVideoById(flvVideo.getSourceVideoId()));

        Assert.assertTrue(new File(fileSystem.getResourcePath(flvVideo)).exists());
    }

    @Test
    public void convertToFlvWithIncorrectPathOnFlvMeta() {
        configStorage.get().setFlvmeta("ggg4");

        videoTranscode.execute(flvVideo, ServiceLocator.getPersistance().getVideoById(flvVideo.getSourceVideoId()));

        Assert.assertTrue(new File(fileSystem.getResourcePath(flvVideo)).exists());
    }

    @Test(expected = VideoTranscodeException.class)
    public void convertToFlvWithErrorOnFfmpeg() {
        systemConsole.getExecutedCommandResults().add(0);

        videoTranscode.execute(flvVideo, ServiceLocator.getPersistance().getVideoById(flvVideo.getSourceVideoId()));
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final VideoTranscode videoTranscode = new VideoTranscodeNative();
    private final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
    private SystemConsoleMock systemConsole;
    private FlvVideo flvVideo;
    private FileSystem fileSystem;

}
