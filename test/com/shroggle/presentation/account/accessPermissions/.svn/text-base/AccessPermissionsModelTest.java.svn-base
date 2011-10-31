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
package com.shroggle.presentation.account.accessPermissions;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.User;
import com.shroggle.entity.UserOnSiteRight;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.util.StringUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class AccessPermissionsModelTest {

    @Test
    public void testCreate() throws Exception {
        final Date currentDate = new Date();
        final User user = TestUtil.createUserAndLogin();
        user.setRegistrationDate(new Date(currentDate.getTime() - 9000000000L));

        final Site site1 = TestUtil.createSite("abc", "");
        final Site site2 = TestUtil.createSite("def", "");
        final Site site3 = TestUtil.createSite("GHI", "");
        final Site site4 = TestUtil.createSite();

        TestUtil.createUserOnSiteRightActiveAdmin(user, site1);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site2);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site3);
        TestUtil.createUserOnSiteRightActive(user, site4, SiteAccessLevel.GUEST);

        final User user1 = TestUtil.createUser("email1");
        user1.setRegistrationDate(new Date(currentDate.getTime() - 5000000000L));
        TestUtil.createUserOnSiteRightActiveAdmin(user1, site1);
        TestUtil.createUserOnSiteRightActiveAdmin(user1, site2);
        TestUtil.createUserOnSiteRightActiveAdmin(user1, site3);
        TestUtil.createUserOnSiteRightActiveAdmin(user1, site4);


        final User user2 = TestUtil.createUser("email2");
        user2.setRegistrationDate(new Date(currentDate.getTime() - 1000000000L));
        TestUtil.createUserOnSiteRightActive(user2, site1, SiteAccessLevel.GUEST);
        TestUtil.createUserOnSiteRightActive(user2, site2, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightActive(user2, site3, SiteAccessLevel.VISITOR);
        TestUtil.createUserOnSiteRightActiveAdmin(user2, site4);


        final AccessPermissionsModel model = new AccessPermissionsModel();


        Assert.assertEquals(user.getUserId(), model.getLoginUserId());
        Assert.assertEquals(null, model.getRemovedRightsResponse());
        Assert.assertEquals(3, model.getUsersCount());

        final List<AccessPermissionsModel.UserSites> userSites = model.getUserSites();
        Assert.assertEquals(3, userSites.size());
        /*-----------------------------------------------Sites for user-----------------------------------------------*/
        final AccessPermissionsModel.UserSites userSites1 = userSites.get(0);
        Assert.assertEquals(user.getUserId(), userSites1.getUserId());
        Assert.assertEquals(StringUtil.getEmptyOrString(user.getEmail()), userSites1.getUserEmail());
        Assert.assertEquals(StringUtil.getEmptyOrString(user.getFirstName()), userSites1.getUserFirstName());
        Assert.assertEquals(StringUtil.getEmptyOrString(user.getLastName()), userSites1.getUserLastName());
        Assert.assertEquals(3, userSites1.getRights().size());
        Assert.assertEquals(3, userSites1.getSiteTitles().size());
        Assert.assertEquals("abc", userSites1.getSiteTitles().get(0));
        Assert.assertEquals("def", userSites1.getSiteTitles().get(1));
        Assert.assertEquals("GHI", userSites1.getSiteTitles().get(2));
        for (int i = 0; i < userSites1.getRights().size(); i++) {
            final UserOnSiteRight userOnSiteRight = userSites1.getRights().get(i);
            Assert.assertEquals(userOnSiteRight.getId().getSite().getTitle(), userSites1.getSiteTitles().get(i));
        }
        /*-----------------------------------------------Sites for user-----------------------------------------------*/

        /*-----------------------------------------------Sites for user1----------------------------------------------*/
        final AccessPermissionsModel.UserSites userSites2 = userSites.get(1);
        Assert.assertEquals(user1.getUserId(), userSites2.getUserId());
        Assert.assertEquals(StringUtil.getEmptyOrString(user1.getEmail()), userSites2.getUserEmail());
        Assert.assertEquals(StringUtil.getEmptyOrString(user1.getFirstName()), userSites2.getUserFirstName());
        Assert.assertEquals(StringUtil.getEmptyOrString(user1.getLastName()), userSites2.getUserLastName());
        Assert.assertEquals(3, userSites2.getRights().size());
        Assert.assertEquals(3, userSites2.getSiteTitles().size());
        Assert.assertEquals("abc", userSites2.getSiteTitles().get(0));
        Assert.assertEquals("def", userSites2.getSiteTitles().get(1));
        Assert.assertEquals("GHI", userSites2.getSiteTitles().get(2));
        for (int i = 0; i < userSites2.getRights().size(); i++) {
            final UserOnSiteRight userOnSiteRight = userSites2.getRights().get(i);
            Assert.assertEquals(userOnSiteRight.getId().getSite().getTitle(), userSites2.getSiteTitles().get(i));
        }
        /*-----------------------------------------------Sites for user1----------------------------------------------*/
        
        /*-----------------------------------------------Sites for user2----------------------------------------------*/
        final AccessPermissionsModel.UserSites userSites3 = userSites.get(2);
        Assert.assertEquals(user2.getUserId(), userSites3.getUserId());
        Assert.assertEquals(StringUtil.getEmptyOrString(user2.getEmail()), userSites3.getUserEmail());
        Assert.assertEquals(StringUtil.getEmptyOrString(user2.getFirstName()), userSites3.getUserFirstName());
        Assert.assertEquals(StringUtil.getEmptyOrString(user2.getLastName()), userSites3.getUserLastName());
        Assert.assertEquals(1, userSites3.getRights().size());
        Assert.assertEquals(1, userSites3.getSiteTitles().size());
        Assert.assertEquals("def", userSites3.getSiteTitles().get(0));
        for (int i = 0; i < userSites3.getRights().size(); i++) {
            final UserOnSiteRight userOnSiteRight = userSites3.getRights().get(i);
            Assert.assertEquals(userOnSiteRight.getId().getSite().getTitle(), userSites3.getSiteTitles().get(i));
        }
        /*-----------------------------------------------Sites for user2----------------------------------------------*/
    }

    @Test(expected = UserNotLoginedException.class)
    public void testCreate_withoutLoginedUser() throws Exception {
        new AccessPermissionsModel();
    }

}
