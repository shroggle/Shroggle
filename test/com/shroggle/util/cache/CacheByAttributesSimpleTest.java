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

import com.shroggle.util.process.ThreadUtil;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author Artem Stasuk
 */
public class CacheByAttributesSimpleTest {

    @Test
    public void add() {
        cache.add(Arrays.asList(1), Arrays.asList(2));

        final List<Integer> values = cache.get(1);
        Assert.assertEquals(1, values.size());
        Assert.assertEquals(2, (int) values.get(0));
    }

    @Test
    public void reset() {
        cache.add(Arrays.asList(1), Arrays.asList(2));
        cache.reset();

        Assert.assertNull(cache.get(1));
    }

    @Test
    public void destroy() {
        cache.add(Arrays.asList(1), Arrays.asList(2));
        cache.destroy();

        Assert.assertNull(cache.get(1));
    }

    @Test
    public void getName() {
        Assert.assertEquals("a", cache.getName());
    }

    @Test
    public void addWithManyAttributes() {
        cache.add(Arrays.asList(1, 10, 11), Arrays.asList(2));

        Assert.assertEquals(Arrays.asList(2), cache.get(1));
        Assert.assertEquals(Arrays.asList(2), cache.get(10));
        Assert.assertEquals(Arrays.asList(2), cache.get(11));
    }

    @Test
    public void addOnDifferentValues() {
        cache.add(Arrays.asList(11), Arrays.asList(2));
        cache.add(Arrays.asList(11), Arrays.asList(20));
        cache.add(Arrays.asList(11), Arrays.asList(200));

        Assert.assertEquals(Arrays.asList(2, 20, 200), cache.get(11));
    }

    @Test
    public void addWithClear() {
        cache.add(Arrays.asList(1), Arrays.asList(10));
        ThreadUtil.sleep(2);
        cache.add(Arrays.asList(2), Arrays.asList(20));
        cache.add(Arrays.asList(3), Arrays.asList(30));
        cache.add(Arrays.asList(4), Arrays.asList(40));
        cache.add(Arrays.asList(5), Arrays.asList(50));
        cache.add(Arrays.asList(6), Arrays.asList(60));

        Assert.assertNull(cache.get(1));
        Assert.assertEquals(Arrays.asList(60), cache.get(6));
    }

    @Test
    public void addWithClearManyValues() {
        cache.add(Arrays.asList(1, 0, -1), Arrays.asList(10, 23, 44));
        ThreadUtil.sleep(2);
        cache.add(Arrays.asList(2), Arrays.asList(20));
        cache.add(Arrays.asList(3), Arrays.asList(30));
        cache.add(Arrays.asList(4), Arrays.asList(40));
        cache.add(Arrays.asList(5), Arrays.asList(50));
        cache.add(Arrays.asList(6), Arrays.asList(60));

        Assert.assertNull(cache.get(1));
        Assert.assertEquals(Arrays.asList(60), cache.get(6));
    }

    @Test
    public void remove() {
        cache.add(Arrays.asList(1), Arrays.asList(2));

        final List<Integer> values = cache.get(1);
        Assert.assertNotNull("Can't pop my value!", values);
        Assert.assertEquals(1, values.size());
        Assert.assertEquals(2, (int) values.get(0));
    }

    private final CacheByAttributes<Integer> cache =
            new CacheByAttributesSimple<Integer>("a", 5);

}
