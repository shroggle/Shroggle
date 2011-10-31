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
package com.shroggle.util;

import junit.framework.Assert;
import org.junit.Test;

/**
 * @author Artem Stasuk
 */
public class StringUtilTest {

    @Test
    public void isNullOrEmptyWithNull() {
        Assert.assertTrue(StringUtil.isNullOrEmpty(null));
    }

    @Test
    public void isNullOrEmptyWithEmpty() {
        Assert.assertTrue(StringUtil.isNullOrEmpty("   "));
    }

    @Test
    public void isNullOrEmpty() {
        Assert.assertFalse(StringUtil.isNullOrEmpty("a  ad "));
    }

    @Test
    public void create() {
        new StringUtil();
    }

    @Test
    public void trimCutIfNeedAndLowerForSmall() {
        Assert.assertEquals("f", StringUtil.trimCutIfNeedAndLower("f", 2));
    }

    @Test
    public void trimCutIfNeedAndLowerOnlySpace() {
        Assert.assertEquals(null, StringUtil.trimCutIfNeedAndLower("     ", 2));
    }

    @Test
    public void trimCutIfNeedAndLowerUpCase() {
        Assert.assertEquals("aa", StringUtil.trimCutIfNeedAndLower("aA", 2));
    }

    @Test
    public void trimCutIfNeedAndLowerBig() {
        Assert.assertEquals("aa", StringUtil.trimCutIfNeedAndLower("aAa", 2));
    }

    @Test
    public void testGetEmptyOrString_String_notNull() {
        Assert.assertEquals("aAa", StringUtil.getEmptyOrString("aAa"));
    }

    @Test
    public void testGetEmptyOrString_String_null() {
        String string = null;
        Assert.assertEquals("", StringUtil.getEmptyOrString(string));
    }

    @Test
    public void testGetEmptyOrString_Intger_notNull() {
        Assert.assertEquals("123", StringUtil.getEmptyOrString(123));
    }

    @Test
    public void testGetEmptyOrString_Intger_null() {
        Integer integer = null;
        Assert.assertEquals("", StringUtil.getEmptyOrString(integer));
    }
}
