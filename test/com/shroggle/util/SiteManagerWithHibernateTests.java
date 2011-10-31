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
package com.shroggle.util;

import com.shroggle.TestUtil;
import com.shroggle.entity.IncomeSettings;
import com.shroggle.entity.Site;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.util.persistance.hibernate.TestRunnerWithHibernateService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithHibernateService.class)
public class SiteManagerWithHibernateTests {
    
    @Test
    public void getIncomeSettings_withIncomeSettings() {
        final Site site = TestUtil.createSite();
        IncomeSettings incomeSettings = TestUtil.createIncomeSettings(site, "paypal", 150);
        Assert.assertEquals(incomeSettings, site.getIncomeSettings());

        final SiteManager siteManager = new SiteManager(site);
        Assert.assertEquals(site.getIncomeSettings(), siteManager.getOrCreateIncomeSettings());
    }

    @Test
    public void getIncomeSettings_withoutIncomeSettings() {
        final Site site = TestUtil.createSite();
        Assert.assertNull(site.getIncomeSettings());
        final SiteManager siteManager = new SiteManager(site);
        IncomeSettings incomeSettings = siteManager.getOrCreateIncomeSettings();
        Assert.assertNotNull(incomeSettings);
        Assert.assertEquals("", incomeSettings.getPaypalAddress());
        Assert.assertEquals(0.0, incomeSettings.getSum(), 1);
    }

}
