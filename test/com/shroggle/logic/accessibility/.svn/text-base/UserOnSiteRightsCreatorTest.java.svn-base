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
package com.shroggle.logic.accessibility;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.User;
import com.shroggle.logic.user.UserManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.Config;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class UserOnSiteRightsCreatorTest {

    @Test
    public void testCreateRightsForAppAdmins() throws Exception {
        final Config config = ServiceLocator.getConfigStorage().get();
        final User user1 = TestUtil.createUser("email1");
        final User user2 = TestUtil.createUser("email2");
        final User user3 = TestUtil.createUser("email3");
        final User user4 = TestUtil.createUser("email4");
        config.setAdminEmails(Arrays.asList(user1.getEmail(), user2.getEmail(), user3.getEmail()));

        final Site site = TestUtil.createSite();


        UserOnSiteRightsCreator.createRightsForAppAdmins(site);

        
        Assert.assertEquals(SiteAccessLevel.ADMINISTRATOR, new UserManager(user1).getRight().toSite(site).getSiteAccessType());
        Assert.assertEquals(SiteAccessLevel.ADMINISTRATOR, new UserManager(user2).getRight().toSite(site).getSiteAccessType());
        Assert.assertEquals(SiteAccessLevel.ADMINISTRATOR, new UserManager(user3).getRight().toSite(site).getSiteAccessType());
        Assert.assertNull(new UserManager(user4).getRight().toSite(site));
    }

}
