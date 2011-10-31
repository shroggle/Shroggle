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

import com.shroggle.PersistanceMock;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.entity.Site;
import com.shroggle.entity.User;
import com.shroggle.util.ServiceLocator;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

/**
 * @author Artem Stasuk
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class CacheByEntitysTest {

    @Test
    public void add() {
        cache.add(Arrays.asList(new CacheByEntitysId(User.class, 12)), Arrays.asList(7));

        Assert.assertEquals(Arrays.asList(7), cache.get(new CacheByEntitysId(User.class, 12)));
    }

    @Test
    public void notMyEvents() {
        cache.add(Arrays.asList(new CacheByEntitysId(User.class, 12)), Arrays.asList(7));
        persistanceMock.notifyRemoveListeners(User.class, 13);
        persistanceMock.notifyRemoveListeners(Site.class, 13);

        Assert.assertEquals(Arrays.asList(7), cache.get(new CacheByEntitysId(User.class, 12)));
    }

    @Test
    public void events() {
        cache.add(Arrays.asList(new CacheByEntitysId(User.class, 12)), Arrays.asList(7));
        persistanceMock.notifyRemoveListeners(User.class, 12);

        Assert.assertNull(cache.get(new CacheByEntitysId(User.class, 12)));
    }

    private final PersistanceMock persistanceMock = (PersistanceMock) ServiceLocator.getPersistance();
    private final Cache<Object, List<Integer>> cache =
            new CacheByEntitys<Integer>(new CacheByAttributesSimple<Integer>("a", 5));

}
