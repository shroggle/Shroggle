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

import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.presentation.TestAction;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystemMockException;
import com.shroggle.util.filesystem.FileSystemRealTest;
import junit.framework.Assert;
import net.sourceforge.stripes.action.FileBean;
import org.junit.Test;

/**
 * @author Balakirev Anatoliy
 */
public class UploadVideoImageActionTest extends TestAction<UploadVideoImageAction> {

    public UploadVideoImageActionTest() {
        super(UploadVideoImageAction.class, true);
    }

    @Test
    public void execute() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Assert.assertEquals(0, persistance.getVideosBySiteId(site.getSiteId()).size());
        FileBean image = TestUtil.createFileBean(FileSystemRealTest.class, "test.png");

        actionOrService.setFileData(image);
        actionOrService.setSiteId(site.getSiteId());
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.upload();

        Assert.assertEquals("/SWFUpload/success.jsp", resolutionMock.getForwardToUrl());
        Assert.assertEquals(1, persistance.getImagesForVideoBySiteId(site.getSiteId()).size());
    }

    @Test
    public void executeWithoutData() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        actionOrService.setSiteId(site.getSiteId());
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.upload();

        Assert.assertEquals("/SWFUpload/error.jsp", resolutionMock.getForwardToUrl());
        Assert.assertEquals(0, persistance.getVideosBySiteId(site.getSiteId()).size());
    }

    @Test
    public void executeWithException() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Assert.assertEquals(0, persistance.getVideosBySiteId(site.getSiteId()).size());
        FileBean image = TestUtil.createFileBean(FileSystemRealTest.class, "test.png");

        actionOrService.setFileData(image);
        ServiceLocator.setFileSystem(new FileSystemMockException());

        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.upload();

        Assert.assertEquals("/SWFUpload/error.jsp", resolutionMock.getForwardToUrl());
        Assert.assertEquals(0, persistance.getVideosBySiteId(site.getSiteId()).size());
    }

}
