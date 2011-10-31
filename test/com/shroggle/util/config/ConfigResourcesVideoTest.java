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
package com.shroggle.util.config;

import org.junit.Test;
import org.junit.Assert;

/**
 * @author Artem Stasuk
 */
public class ConfigResourcesVideoTest {

    @Test
    public void ngnixSecretToken() {
        ConfigResourcesVideo resourcesVideo = new ConfigResourcesVideo();
        Assert.assertNull(resourcesVideo.getNgnixSecretToken());
        resourcesVideo.setNgnixSecretToken("ff");
        Assert.assertEquals("ff", resourcesVideo.getNgnixSecretToken());
    }

    @Test
    public void ngnixUrlPrefix() {
        ConfigResourcesVideo resourcesVideo = new ConfigResourcesVideo();
        Assert.assertEquals("", resourcesVideo.getNgnixUrlPrefix());
        resourcesVideo.setNgnixUrlPrefix("ff");
        Assert.assertEquals("ff", resourcesVideo.getNgnixUrlPrefix());
    }

    @Test
    public void path() {
        ConfigResourcesVideo resourcesVideo = new ConfigResourcesVideo();
        Assert.assertNull(resourcesVideo.getPath());
        resourcesVideo.setPath("ff");
        Assert.assertEquals("ff", resourcesVideo.getPath());
    }

    @Test
    public void useNgnix() {
        ConfigResourcesVideo resourcesVideo = new ConfigResourcesVideo();
        Assert.assertFalse(resourcesVideo.isUseNgnix());
        resourcesVideo.setUseNgnix(true);
        Assert.assertTrue(resourcesVideo.isUseNgnix());
    }

}
