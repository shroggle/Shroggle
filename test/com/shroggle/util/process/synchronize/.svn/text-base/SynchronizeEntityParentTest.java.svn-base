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
import com.shroggle.entity.Site;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Author: Artem Stasuk (artem).
 * </p>
 * Date: 24.10.2008
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class SynchronizeEntityParentTest {

    @Test
    public void nextWithNullId() {
        SynchronizeEntityParentResult result = new SynchronizeEntityParentResult(Site.class, null);
        Assert.assertNull(SynchronizeEntityParent.next(result));
    }

    @Test
    public void nextWithNotEntity() {
        SynchronizeEntityParentResult result = new SynchronizeEntityParentResult(Object.class, 1);
        Assert.assertNull(SynchronizeEntityParent.next(result));
    }

    @Test
    public void nextWithNotFound() {
        SynchronizeEntityParentResult result = new SynchronizeEntityParentResult(Site.class, 1);
        Assert.assertNull(SynchronizeEntityParent.next(result));
    }

}
