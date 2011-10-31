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
import com.shroggle.entity.Image;
import com.shroggle.entity.Site;
import com.shroggle.exception.ImageDuplicateNameException;
import com.shroggle.exception.ImageNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class SaveImageNameServiceTest {

    @Test
    public void execute() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site);
        final Image image = TestUtil.createImage(site.getId(), "oldName", "jpg");

        saveImageNameService.execute(image.getImageId(), "newName");

        Assert.assertEquals("newName", image.getName());
    }

    @Test
    public void executeWithDuplicateImage() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site);
        TestUtil.createImage(site.getId(), "dupName", "jpg");
        final Image image = TestUtil.createImage(site.getId(), "oldName", "jpg");

        try {
            saveImageNameService.execute(image.getImageId(), "dupName");
        } catch (ImageDuplicateNameException ex) {
            Assert.assertNotNull("Check that service thrown exception...", ex);
        }

        Assert.assertEquals("... and renamed image to not duplicate name", "dupName1", image.getName());
    }

    @Test(expected = ImageNotFoundException.class)
    public void executeWithNotFoundImage() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site);

        saveImageNameService.execute(-1, "newName");
    }

    @Test
    public void executeWithEmptyName() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site);
        final Image image = TestUtil.createImage(site.getId(), "oldName", "jpg");

        saveImageNameService.execute(image.getImageId(), "");

        Assert.assertEquals("oldName", image.getName());
    }

    @Test
    public void executeWithNullName() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site);
        final Image image = TestUtil.createImage(site.getId(), "oldName", "jpg");

        saveImageNameService.execute(image.getImageId(), null);

        Assert.assertEquals("oldName", image.getName());
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLoginedUser() {
        final Site site = TestUtil.createSite();
        final Image image = TestUtil.createImage(site.getId(), "oldName", "jpg");

        saveImageNameService.execute(image.getImageId(), null);

        Assert.assertEquals("oldName", image.getName());
    }

    private SaveImageNameService saveImageNameService = new SaveImageNameService();
}
