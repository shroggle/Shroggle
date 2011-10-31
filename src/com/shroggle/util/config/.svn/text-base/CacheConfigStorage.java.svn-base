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

/**
 * @author Stasuk Artem
 */
public class CacheConfigStorage implements ConfigStorage {

    public CacheConfigStorage(final ConfigStorage configStorage) {
        if (configStorage == null) {
            throw new ConfigException("Can't create cache config with null storage!");
        }
        this.config = configStorage.get();
        if (this.config == null) {
            throw new ConfigException("Can't create cache config with null config!");
        }
    }

    public Config get() {
        return config;
    }

    private final Config config;

}