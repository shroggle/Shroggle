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
package com.shroggle.logic.site;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.ChildSiteSettings;
import com.shroggle.entity.Site;
import com.shroggle.entity.User;
import com.shroggle.logic.childSites.childSiteSettings.ChildSiteSettingsManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class SitesManagerTest {
    @Test
    public void getChildSitesByUserId() {
        User user = TestUtil.createUser();
        Site childSite1 = TestUtil.createChildSite();
        Site childSite2 = TestUtil.createChildSite();
        Site childSite3 = TestUtil.createChildSite();
        Site childSite4 = TestUtil.createChildSite();
        Site site = TestUtil.createSite();
        Site notUserSite = TestUtil.createSite();

        TestUtil.createUserOnSiteRightActiveAdmin(user, childSite1);
        TestUtil.createUserOnSiteRightActiveAdmin(user, childSite2);
        TestUtil.createUserOnSiteRightActiveAdmin(user, childSite3);
        TestUtil.createUserOnSiteRightActiveAdmin(user, childSite4);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final List<Site> childSites = new SitesManager(user.getUserId()).getChildSitesByUserId();
        Assert.assertEquals(4, childSites.size());
        Assert.assertTrue(childSites.contains(childSite1));
        Assert.assertTrue(childSites.contains(childSite2));
        Assert.assertTrue(childSites.contains(childSite3));
        Assert.assertTrue(childSites.contains(childSite4));
        Assert.assertFalse(childSites.contains(site));
        Assert.assertFalse(childSites.contains(notUserSite));
    }

    @Test
    public void getNetworkSitesByUserId() {
        User user = TestUtil.createUser();
        Site networkSite1 = TestUtil.createNetworkSite();
        Site networkSite2 = TestUtil.createNetworkSite();
        Site networkSite3 = TestUtil.createNetworkSite();
        Site networkSite4 = TestUtil.createNetworkSite();
        Site site = TestUtil.createSite();
        Site notUserSite = TestUtil.createSite();

        TestUtil.createUserOnSiteRightActiveAdmin(user, networkSite1);
        TestUtil.createUserOnSiteRightActiveAdmin(user, networkSite2);
        TestUtil.createUserOnSiteRightActiveAdmin(user, networkSite3);
        TestUtil.createUserOnSiteRightActiveAdmin(user, networkSite4);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final List<Site> childSites = new SitesManager(user.getUserId()).getNetworkSitesByUserId();
        Assert.assertEquals(4, childSites.size());
        Assert.assertTrue(childSites.contains(networkSite1));
        Assert.assertTrue(childSites.contains(networkSite2));
        Assert.assertTrue(childSites.contains(networkSite3));
        Assert.assertTrue(childSites.contains(networkSite4));
        Assert.assertFalse(childSites.contains(site));
        Assert.assertFalse(childSites.contains(notUserSite));
    }

    @Test
    public void getCommonSitesWithoutNetworkAndChildSitesByUserId() {
        User user = TestUtil.createUser();
        Site networkSite1 = TestUtil.createNetworkSite();
        Site networkSite2 = TestUtil.createNetworkSite();
        Site childSite1 = TestUtil.createChildSite();
        Site childSite2 = TestUtil.createChildSite();

        Site site1 = TestUtil.createSite();
        Site site2 = TestUtil.createSite();

        Site notUserSite = TestUtil.createSite();

        TestUtil.createUserOnSiteRightActiveAdmin(user, networkSite1);
        TestUtil.createUserOnSiteRightActiveAdmin(user, networkSite2);
        TestUtil.createUserOnSiteRightActiveAdmin(user, childSite1);
        TestUtil.createUserOnSiteRightActiveAdmin(user, childSite2);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site1);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site2);

        final List<Site> commonSites = new SitesManager(user.getUserId()).getCommonSitesWithoutNetworkAndChildSitesByUserId();
        Assert.assertEquals(2, commonSites.size());
        Assert.assertFalse(commonSites.contains(networkSite1));
        Assert.assertFalse(commonSites.contains(networkSite2));
        Assert.assertFalse(commonSites.contains(childSite1));
        Assert.assertFalse(commonSites.contains(childSite2));
        Assert.assertTrue(commonSites.contains(site1));
        Assert.assertTrue(commonSites.contains(site2));
        Assert.assertFalse(commonSites.contains(notUserSite));
    }

    @Test
    public void testGetChildSiteSettingsForCreatedAndNotChildSitesByUserId() throws Exception {
        User user = TestUtil.createUser();
        Site networkSite1 = TestUtil.createNetworkSite();
        Site networkSite2 = TestUtil.createNetworkSite();
        Site childSite1 = TestUtil.createChildSite();
        Site childSite2 = TestUtil.createChildSite();

        Site site1 = TestUtil.createSite();
        Site site2 = TestUtil.createSite();

        TestUtil.createUserOnSiteRightActiveAdmin(user, networkSite1);
        TestUtil.createUserOnSiteRightActiveAdmin(user, networkSite2);
        TestUtil.createUserOnSiteRightActiveAdmin(user, childSite1);
        TestUtil.createUserOnSiteRightActiveAdmin(user, childSite2);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site1);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site2);

        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), new Site());
        user.addChildSiteSettingsId(childSiteSettings.getId());
        childSiteSettings.setSite(null);

        final List<ChildSiteSettingsManager> managers = new SitesManager(user.getUserId()).getChildSiteSettingsForCreatedAndNotChildSitesByUserId();

        Assert.assertEquals(3, managers.size());

        final List<Integer> childSiteSettingsId = new ArrayList<Integer>();
        for (ChildSiteSettingsManager manager : managers) {
            childSiteSettingsId.add(manager.getId());
        }

        Assert.assertTrue(childSiteSettingsId.contains(childSite1.getChildSiteSettings().getId()));
        Assert.assertTrue(childSiteSettingsId.contains(childSite1.getChildSiteSettings().getId()));
        Assert.assertTrue(childSiteSettingsId.contains(childSiteSettings.getId()));
    }
}
