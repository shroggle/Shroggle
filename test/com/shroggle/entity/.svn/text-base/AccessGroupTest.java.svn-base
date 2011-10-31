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

package com.shroggle.entity;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import com.shroggle.TestRunnerWithMockServices;

/**
 * @author dmitry.solomadin
 */
@RunWith(TestRunnerWithMockServices.class)
public class AccessGroupTest {

    @Test
    public void testMax() {
        Assert.assertEquals(AccessGroup.OWNER, AccessGroup.max(AccessGroup.GUEST, AccessGroup.OWNER));
        Assert.assertEquals(AccessGroup.VISITORS, AccessGroup.max(AccessGroup.ALL, AccessGroup.VISITORS));
        Assert.assertEquals(AccessGroup.OWNER, AccessGroup.max(AccessGroup.OWNER, AccessGroup.OWNER));
    }
}
