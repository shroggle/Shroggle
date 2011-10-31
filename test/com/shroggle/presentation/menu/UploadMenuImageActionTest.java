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
package com.shroggle.presentation.menu;

import org.junit.Test;
import com.shroggle.presentation.TestAction;
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.TestUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.resource.provider.ResourceGetterType;
import com.shroggle.util.filesystem.FileSystemMockException;
import junit.framework.Assert;

import java.io.File;
import java.util.Locale;

import net.sourceforge.stripes.action.FileBean;
import net.sourceforge.stripes.action.ForwardResolution;

/**
 * @author Balakirev Anatoliy
 */
public class UploadMenuImageActionTest extends TestAction<UploadMenuImageAction> {


    public UploadMenuImageActionTest() {
        super(UploadMenuImageAction.class, true);
    }

    @Test
    public void execute() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final File file = TestUtil.getTempImageFile();
        final FileBean image = new FileBean(file, "file", "test.png");

        actionOrService.setFileData(image);
        actionOrService.setSiteId(site.getSiteId());
        final ForwardResolution resolutionMock = (ForwardResolution) actionOrService.upload();

        Assert.assertEquals("/SWFUpload/success.jsp", resolutionMock.getUrl(Locale.US));
        Assert.assertNotNull(persistance.getMenuImageById(actionOrService.getResourceId()));
        Assert.assertEquals("test.png", persistance.getMenuImageById(actionOrService.getResourceId()).getName());
        Assert.assertEquals("png", persistance.getMenuImageById(actionOrService.getResourceId()).getExtension());
        Assert.assertEquals(site.getSiteId(), persistance.getMenuImageById(actionOrService.getResourceId()).getSiteId());
        Assert.assertEquals(ResourceGetterType.MENU_IMAGE.getUrl(actionOrService.getResourceId()), actionOrService.getResourceUrl());
    }

    @Test
    public void executeWithoutData() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        actionOrService.setSiteId(site.getSiteId());
        final ForwardResolution resolutionMock = (ForwardResolution) actionOrService.upload();

        Assert.assertEquals("/SWFUpload/error.jsp", resolutionMock.getUrl(Locale.US));
        Assert.assertNull(persistance.getMenuImageById(actionOrService.getResourceId()));
    }

    @Test
    public void executeWithException() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final File file = TestUtil.getTempImageFile();
        final FileBean image = new FileBean(file, "file", "test.png");

        actionOrService.setFileData(image);
        ServiceLocator.setFileSystem(new FileSystemMockException());

        final ForwardResolution resolutionMock = (ForwardResolution) actionOrService.upload();

        Assert.assertEquals("/SWFUpload/error.jsp", resolutionMock.getUrl(Locale.US));
        Assert.assertNull(persistance.getMenuImageById(actionOrService.getResourceId()));
    }
}
