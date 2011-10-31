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

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.presentation.resource.CheckResourceStatusService;
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.User;
import com.shroggle.entity.Video;
import com.shroggle.exception.ExtensionNullOrEmptyException;
import com.shroggle.exception.SourceInputStreamNullException;
import com.shroggle.presentation.video.SourceVideoFileCreator;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.process.SystemConsoleMock;
import com.shroggle.util.process.SystemConsoleReal;
import com.shroggle.util.filesystem.FileSystemMock;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.filesystem.FileSystemReal;
import com.shroggle.util.filesystem.fileWriter.Status;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;

import java.io.InputStream;
import java.io.IOException;

@RunWith(value = TestRunnerWithMockServices.class)
public class SourceVideoFileCreatorTest {

    @Before
    public void before() throws IOException {
        FileSystem fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        ServiceLocator.setFileSystem(fileSystem);
        SystemConsoleMock systemConsole = new SystemConsoleMock(new SystemConsoleReal());
        ServiceLocator.setSystemConsole(systemConsole);
    }

    @Test
    public void execute() {
        User account = new User();
        persistance.putUser(account);

        final InputStream inputStream = getClass().getResourceAsStream("video1.flv");
        final SourceVideoFileCreator sourceVideoFileCreator = new SourceVideoFileCreator(1, "flv", "video1.flv", inputStream);
        final Video video = sourceVideoFileCreator.execute();

        Assert.assertEquals(persistance.getVideoById(video.getVideoId()), video);
        Assert.assertTrue(video.getVideoId() > 0);
        Status status = new CheckResourceStatusService().execute(video.getVideoId(), video.getResourceType());
        while (status != Status.SAVED) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            status = new CheckResourceStatusService().execute(video.getVideoId(), video.getResourceType());
        }
        Assert.assertEquals(inputStream, fileSystemMock.getResourceStream(video));
        //Assert.assertEquals(320, video.getSourceWidth());
        // Assert.assertEquals(240, video.getSourceHeight());
    }

    @Test
    public void executeWithCapitalExtension() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);

        final InputStream inputStream = getClass().getResourceAsStream("test.avi");
        final SourceVideoFileCreator sourceVideoFileCreator = new SourceVideoFileCreator(1, "Avi", "test.avi", inputStream);

        final Video video = sourceVideoFileCreator.execute();

        Assert.assertEquals(persistance.getVideoById(video.getVideoId()), video);
        Assert.assertTrue(video.getVideoId() > 0);
        Status status = new CheckResourceStatusService().execute(video.getVideoId(), video.getResourceType());
        while (status != Status.SAVED) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            status = new CheckResourceStatusService().execute(video.getVideoId(), video.getResourceType());
        }
        Assert.assertEquals(inputStream, fileSystemMock.getResourceStream(video));
    }


    @Test
    public void executeWithExistingVideoName() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);

        final SourceVideoFileCreator sourceVideoFileCreator = new SourceVideoFileCreator(site.getSiteId(), "Avi", "test.avi", getClass().getResourceAsStream("test.avi"));
        sourceVideoFileCreator.execute();

        final Video video = sourceVideoFileCreator.execute();

        Assert.assertEquals("test(2).avi", video.getSourceName());
        Assert.assertEquals(persistance.getVideoById(video.getVideoId()), video);
        Assert.assertTrue(video.getVideoId() > 0);
    }


    @Test(expected = SourceInputStreamNullException.class)
    public void executeWithNullImageSourceInputStream() {
        User account = new User();
        persistance.putUser(account);
        final SourceVideoFileCreator sourceVideoFileCreator = new SourceVideoFileCreator(1, "Avi", "test.avi", null);
        sourceVideoFileCreator.execute();
    }

    @Test(expected = ExtensionNullOrEmptyException.class)
    public void executeWithNullImageExtension() {
        User account = new User();
        persistance.putUser(account);
        final SourceVideoFileCreator sourceVideoFileCreator = new SourceVideoFileCreator(1, "", "test.avi", getClass().getResourceAsStream("test.avi"));

        sourceVideoFileCreator.execute();
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();

}