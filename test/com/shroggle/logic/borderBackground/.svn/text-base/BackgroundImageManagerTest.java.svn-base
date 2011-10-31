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
package com.shroggle.logic.borderBackground;

import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import com.shroggle.exception.BackgroundImageNotFoundException;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.util.filesystem.FileSystemReal;
import com.shroggle.util.ServiceLocator;
import com.shroggle.entity.BackgroundImage;
import com.shroggle.entity.ResourceSize;

/**
 * @author Balakirev Anatoliy
 *         Date: 23.09.2009
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class BackgroundImageManagerTest {

    @Before
    public void before() {
        ServiceLocator.setFileSystem(new FileSystemReal(null, null, null, null, null, null, null, null, null));
    }

    @Test(expected = BackgroundImageNotFoundException.class)
    public void create_withoutImage() {
        new BackgroundImageManager(null);
    }

    @Test
    public void testGetResourceSize() {
        final BackgroundImage backgroundImage = TestUtil.createBackgroundImage(1);
        ResourceSize resourceSize = new BackgroundImageManager(backgroundImage).getResourceSize();
        Assert.assertNotNull(resourceSize);
        Assert.assertEquals(800, resourceSize.getWidth().intValue());
        Assert.assertEquals(600, resourceSize.getHeight().intValue());
    }
}
