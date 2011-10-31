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
package com.shroggle.util.process.synchronize;

import com.shroggle.entity.Site;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Author: Artem Stasuk (artem).
 * </p>
 * Date: 23.10.2008
 */
public class SynchronizeRequestAllEntityTest {

    @Test
    public void getPoints() {
        SynchronizeRequest request = new SynchronizeRequestAllEntity(Site.class);
        Assert.assertEquals(1, request.getPoints().size());
        Assert.assertTrue(request.getPoints().contains(new SynchronizePointAllEntity(Site.class)));
        Assert.assertEquals(SynchronizeMethod.WRITE, request.getPoints().iterator().next().getMethod());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createWithNull() {
        new SynchronizeRequestAllEntity(null);
    }

}
