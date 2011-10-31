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
package com.shroggle.util.cache;

import junit.framework.Assert;
import org.junit.Test;

/**
 * @author Artem Stasuk
 */
public class CacheMissBlockerTest {

    @Test
    public void getNotFound() {
        listenerMock.setFind(33);
        Assert.assertEquals(33, (int) cacheMissBlocker.get(12));
    }

    @Test
    public void get() {
        listenerMock.setFind(33);
        cacheMissBlocker.add(12, 44);
        Assert.assertEquals(44, (int) cacheMissBlocker.get(12));
    }

    private final CacheMissBlockerListenerMock<Integer, Integer> listenerMock =
            new CacheMissBlockerListenerMock<Integer, Integer>();
    private final Cache<Integer, Integer> cache = new CacheMock<Integer, Integer>();
    private final Cache<Integer, Integer> cacheMissBlocker =
            new CacheMissBlocker<Integer, Integer>(cache, listenerMock);

}
