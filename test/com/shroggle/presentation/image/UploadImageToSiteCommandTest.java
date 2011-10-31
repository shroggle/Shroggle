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

package com.shroggle.presentation.image;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.Site;
import com.shroggle.exception.ImageWriteException;
import com.shroggle.exception.SourceInputStreamNullException;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystemMockException;
import com.shroggle.util.filesystem.FileSystemRealTest;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(TestRunnerWithMockServices.class)
public class UploadImageToSiteCommandTest {

    @Test
    public void execute() throws Exception {
        Site site = new Site();
        persistance.putSite(site);

        command.setSiteId(site.getSiteId());
        command.setTitle("a");
        command.setDescription("b");
        command.setKeywords("c");
        command.setFileBean(TestUtil.createFileBean(FileSystemRealTest.class, "test.png"));

        command.execute();

        Assert.assertEquals("a", command.getImage().getName());
        Assert.assertEquals("c", command.getImage().getKeywords());
        Assert.assertEquals("b", command.getImage().getDescription());
        Assert.assertEquals(
                persistance.getImageById(command.getImage().getImageId()),
                command.getImage());
        Assert.assertTrue(command.getImage().getImageId() > 0);
    }

    @Test
    public void executeWithCapitalExtension() throws Exception {
        Site site = new Site();
        persistance.putSite(site);

        command.setSiteId(site.getSiteId());
        command.setTitle("a");
        command.setDescription("b");
        command.setKeywords("c");
        command.setFileBean(TestUtil.createFileBean(FileSystemRealTest.class, "test.png"));

        command.execute();

        Assert.assertEquals("a", command.getImage().getName());
        Assert.assertEquals("c", command.getImage().getKeywords());
        Assert.assertEquals("b", command.getImage().getDescription());
        Assert.assertEquals(
                persistance.getImageById(command.getImage().getImageId()),
                command.getImage());
        Assert.assertTrue(command.getImage().getImageId() > 0);
    }

    @Test(expected = SourceInputStreamNullException.class)
    public void executeWithNullImageSourceInputStream() throws IOException {
        Site site = new Site();
        persistance.putSite(site);

        command.setSiteId(site.getSiteId());
        command.execute();
    }

    @Test(expected = ImageWriteException.class)
    public void executeWithFileSystemException() throws Exception {
        ServiceLocator.setFileSystem(new FileSystemMockException());
        final UploadImageToSiteCommand command = new UploadImageToSiteCommand();

        Site site = new Site();
        persistance.putSite(site);

        command.setSiteId(site.getSiteId());
        command.setTitle("a");
        command.setDescription("b");
        command.setKeywords("c");
        command.setFileBean(TestUtil.createFileBean(FileSystemRealTest.class, "test.png"));

        command.execute();
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final UploadImageToSiteCommand command = new UploadImageToSiteCommand();

}