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
public class MD5Test {

    @Test
    public void crypt() {
        Assert.assertEquals("098f6bcd4621d373cade4e832627b4f6", MD5.crypt("test"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void cryptNullString() {
        MD5.crypt(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cryptEmptyString() {
        MD5.crypt("");
    }

    @Test
    public void create() {
        new MD5();
    }

}
