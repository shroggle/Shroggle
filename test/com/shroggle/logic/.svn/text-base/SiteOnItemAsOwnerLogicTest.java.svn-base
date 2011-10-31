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
package com.shroggle.logic;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import com.shroggle.entity.Site;
import com.shroggle.TestRunnerWithMockServices;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class SiteOnItemAsOwnerLogicTest {

    @Test
    public void isDeletable() {
        final Site site = new Site();
        final SiteOnItemManager siteOnItemManager = new SiteOnItemAsOwnerManager(site);
        Assert.assertFalse(siteOnItemManager.isDeletable());
    }

}
