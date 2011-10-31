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
public class ConfigYuiCompresserTest {

    @Test
    public void getSetObfuscate() {
        Assert.assertTrue(configYuiCompressor.isObfuscate());

        configYuiCompressor.setObfuscate(false);
        Assert.assertFalse(configYuiCompressor.isObfuscate());
    }

    @Test
    public void getSetDebug() {
        Assert.assertFalse(configYuiCompressor.isDebug());

        configYuiCompressor.setDebug(true);
        Assert.assertTrue(configYuiCompressor.isDebug());
    }

    @Test
    public void getSetDisableForAgent() {
        Assert.assertTrue(configYuiCompressor.isUse());

        configYuiCompressor.setUse(false);
        Assert.assertFalse(configYuiCompressor.isUse());
    }

    private final ConfigYuiCompressor configYuiCompressor = new ConfigYuiCompressor();

}
