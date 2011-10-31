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
package com.shroggle.presentation.site;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.FormFile;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystemReal;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.persistance.Persistance;

import java.io.IOException;

import net.sourceforge.stripes.action.FileBean;
import junit.framework.Assert;

/**
 * @author BalakirevAnatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class CreateFormFileUtilTest {

    @Before
    public void before() throws IOException {
        final FileSystem fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        ServiceLocator.setFileSystem(fileSystem);
    }

    @Test
    public void testCreateFormFile() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        FileBean fileBean = new FileBean(TestUtil.createFile(this.getClass(), "test.png"), "file", "test.png");

        Integer formFileId = util.createFormFile(fileBean, site.getSiteId());
        Assert.assertNotNull(formFileId);
        FormFile file = persistance.getFormFileById(formFileId);
        Assert.assertNotNull(file);
        Assert.assertEquals(site.getSiteId(), file.getSiteId());
        Assert.assertEquals("png", file.getSourceExtension());
        Assert.assertEquals("test.png", file.getSourceName());
        Assert.assertEquals(new Integer(800), file.getWidth());
        Assert.assertEquals(new Integer(600), file.getHeight());

    }

    @Test
    public void testCreateFormFile_WithNotImageFile() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        FileBean fileBean = new FileBean(TestUtil.createFile(this.getClass(), "tempFileForTests.test"), "file", "tempFileForTests.test");

        Integer formFileId = util.createFormFile(fileBean, site.getSiteId());
        Assert.assertNotNull(formFileId);
        FormFile file = persistance.getFormFileById(formFileId);
        Assert.assertNotNull(file);
        Assert.assertEquals(site.getSiteId(), file.getSiteId());
        Assert.assertEquals("test", file.getSourceExtension());
        Assert.assertEquals("tempFileForTests.test", file.getSourceName());
        Assert.assertNull(file.getWidth());
        Assert.assertNull(file.getHeight());

    }


    @Test
    public void testCreateFormFile_withoutInputStrem() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        FileBean fileBean = new FileBean(null, "file", "test.png");
        Assert.assertNull(util.createFormFile(fileBean, site.getSiteId()));
    }


    @Test
    public void testCreateFormFile_withEmptyExtension() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        FileBean fileBean = new FileBean(TestUtil.createFile(this.getClass(), "test.png"), "file", "test.");

        Assert.assertNull(util.createFormFile(fileBean, site.getSiteId()));
    }

    @Test
    public void testCreateFormFile_withoutExtension2() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        FileBean fileBean = new FileBean(TestUtil.createFile(this.getClass(), "test.png"), "file", "test");

        Assert.assertNull(util.createFormFile(fileBean, site.getSiteId()));
    }

    @Test
    public void testCreateFormFile_withNullExtension2() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        FileBean fileBean = new FileBean(TestUtil.createFile(this.getClass(), "test.png"), "file", null);

        Assert.assertNull(util.createFormFile(fileBean, site.getSiteId()));
    }

    private CreateFormFileUtil util = new CreateFormFileUtil();
    private final Persistance persistance = ServiceLocator.getPersistance();
}
