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

/**
 * Author: Artem Stasuk (artem).
 * </p>
 * Date: 02.10.2008
 */
public class CacheConfigStorageTest {

    @Test
    public void create() {
        final ConfigStorage configStorage = new CacheConfigStorage(new ConfigStorage() {

            public Config get() {
                if (first) {
                    first = false;
                    return new Config();
                }
                return null;
            }

            private boolean first = true;

        });

        final Config config = configStorage.get();
        Assert.assertNotNull(config);
        Assert.assertEquals(config, configStorage.get());
    }

    @Test(expected = ConfigException.class)
    public void createWithNullConfig() {
        new CacheConfigStorage(new ConfigStorage() {

            public Config get() {
                return null;
            }

        });
    }

    @Test(expected = ConfigException.class)
    public void createWithNullStorage() {
        new CacheConfigStorage(null);
    }

}
