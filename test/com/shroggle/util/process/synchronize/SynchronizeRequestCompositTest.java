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

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.entity.Page;
import com.shroggle.entity.Widget;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Set;

/**
 * @author Artem Stasuk
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class SynchronizeRequestCompositTest {

    @Test
    public void create() {
        new SynchronizeRequestComposit(
                new SynchronizeRequestEntity(Page.class, SynchronizeMethod.READ, 1),
                new SynchronizeRequestEntity(Page.class, SynchronizeMethod.READ, 2)
        );
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createWithNull() {
        new SynchronizeRequestComposit();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createWithNullRequest() {
        new SynchronizeRequestComposit(
                new SynchronizeRequestEntity(Page.class, SynchronizeMethod.READ, 1),
                null
        );
    }

    @Test
    public void getPoints() {
        SynchronizeRequest synchronizeRequest = new SynchronizeRequestComposit(
                new SynchronizeRequestEntity(Page.class, SynchronizeMethod.READ, 1),
                new SynchronizeRequestEntity(Widget.class, SynchronizeMethod.WRITE, 2),
                new SynchronizeRequestEntity(Widget.class, SynchronizeMethod.WRITE, 2)
        );
        Set<SynchronizePoint> synchronizePoints = synchronizeRequest.getPoints();

        Assert.assertNotNull(synchronizePoints);
        Assert.assertEquals(2, synchronizePoints.size());
    }

}
