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

import junit.framework.Assert;
import org.junit.Test;

import java.io.File;
import java.net.URL;

/**
 * @author Artem Stasuk
 */
public class FileConfigStorageTest {

    @Test(expected = ConfigException.class)
    public void createWithNullPath() {
        new FileConfigStorage(null);
    }

    @Test(expected = ConfigException.class)
    public void getWithNoExistFile() {
        new FileConfigStorage("f").get();
    }

    @Test(expected = ConfigException.class)
    public void getWithCorruptConfigFile() {
        final URL corruptConfigFile = FileConfigStorageTest.class.getResource("corruptTestConfig.xml");
        new FileConfigStorage(new File(corruptConfigFile.getFile()).getPath()).get();
    }

    @Test
    public void get() {
        final URL corruptConfigFile = FileConfigStorageTest.class.getResource("testConfig.xml");
        ConfigStorage configStorage = new FileConfigStorage(new File(corruptConfigFile.getFile()).getPath());
        Config config = configStorage.get();

        Assert.assertEquals("siteResources", config.getSiteResourcesPath());
    }

}
