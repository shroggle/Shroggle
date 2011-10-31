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
package com.shroggle.logic.menu;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class MenuPageCheckSometimesTest {
  
    @Test
    public void testIsIncluded() {
        Site site1 = TestUtil.createSite();

        DraftMenu defaultSiteMenu = TestUtil.createMenu();
        defaultSiteMenu.setSiteId(site1.getSiteId());

        site1.setMenu(defaultSiteMenu);

        /*---------------------------------------------Default structure----------------------------------------------*/
        Page page1 = TestUtil.createPage(site1);
        Page page2 = TestUtil.createPage(site1);
        Page page3 = TestUtil.createPage(site1);
        final MenuItem siteMenuItem1 = TestUtil.createMenuItem(page1.getPageId(), defaultSiteMenu);
        final MenuItem siteMenuItem1_1 = TestUtil.createMenuItem(page2.getPageId(), defaultSiteMenu);
        final MenuItem siteMenuItem1_2 = TestUtil.createMenuItem(page3.getPageId(), defaultSiteMenu);

        siteMenuItem1.setIncludeInMenu(false);
        siteMenuItem1_1.setIncludeInMenu(true);
        siteMenuItem1_2.setIncludeInMenu(false);

        siteMenuItem1.setParent(null);
        siteMenuItem1_1.setParent(siteMenuItem1);
        siteMenuItem1_2.setParent(siteMenuItem1);
        /*---------------------------------------------Default structure----------------------------------------------*/


        MenuPageCheckSometimes checkSometimes = new MenuPageCheckSometimes(defaultSiteMenu);



        Assert.assertFalse(checkSometimes.isIncluded(siteMenuItem1));
        Assert.assertTrue(checkSometimes.isIncluded(siteMenuItem1_1));
        Assert.assertFalse(checkSometimes.isIncluded(siteMenuItem1_2));
    }
}
