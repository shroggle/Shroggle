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
import com.shroggle.entity.User;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Author: Artem Stasuk (artem).
 * </p>
 * Date: 23.10.2008
 */
public class SynhronizePointEntityTest {

    @Test
    public void equalsWithDifferendMethod() {
        SynchronizePoint point0 = new SynchronizePointEntity(
                User.class, 1, SynchronizeMethod.WRITE);
        SynchronizePoint point1 = new SynchronizePointEntity(
                User.class, 1, SynchronizeMethod.READ);

        Assert.assertEquals(point0, point1);
    }

    @Test
    public void equalsWithEqualsMethod() {
        SynchronizePoint point0 = new SynchronizePointEntity(
                User.class, 1, SynchronizeMethod.READ);
        SynchronizePoint point1 = new SynchronizePointEntity(
                User.class, 1, SynchronizeMethod.READ);

        Assert.assertEquals(point0, point1);
    }

    @Test
    public void equalsWithDifferendClass() {
        SynchronizePoint point0 = new SynchronizePointEntity(
                Site.class, 1, SynchronizeMethod.READ);
        SynchronizePoint point1 = new SynchronizePointEntity(
                User.class, 1, SynchronizeMethod.READ);

        Assert.assertNotSame(point0, point1);
    }

    @Test
    public void equalsWithDifferendId() {
        SynchronizePoint point0 = new SynchronizePointEntity(
                User.class, 11, SynchronizeMethod.READ);
        SynchronizePoint point1 = new SynchronizePointEntity(
                User.class, 1, SynchronizeMethod.READ);

        Assert.assertNotSame(point0, point1);
    }

    @Test
    public void equalsWithEqualsIdAndClass() {
        SynchronizePoint point0 = new SynchronizePointEntity(
                User.class, 1, SynchronizeMethod.READ);
        SynchronizePoint point1 = new SynchronizePointEntity(
                User.class, 1, SynchronizeMethod.READ);

        Assert.assertEquals(point0, point1);
    }

}
