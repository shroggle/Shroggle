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

package com.shroggle.presentation.site;

import com.shroggle.TestUtil;
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.User;
import com.shroggle.entity.UserOnSiteRight;
import com.shroggle.presentation.TestService;
import com.shroggle.util.ServiceLocator;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.Ignore;


public class ResentAnActivationEmailServiceTest extends TestService<ResentAnActivationEmailService> {

    public ResentAnActivationEmailServiceTest() {
        super(ResentAnActivationEmailService.class);
    }

    @Ignore
    @Test
    public void execute() {
        actionOrService.execute("asd");

        Assert.assertEquals(1, mailSender.getMails().size());
    }

    @Ignore
    @Test
    public void showShareYourSitesPageWithInvitationTextFromLoginedUser() throws Exception {
        User loginedUser = TestUtil.createUserAndLogin("a@a");
        User adminUser1 = TestUtil.createUser("admin1@a.com");
        User adminUser2 = TestUtil.createUser("admin2@a.com");
        User adminUser3 = TestUtil.createUser("admin3@a.com");
        User user = TestUtil.createUser("user@a.com");
        user.setPassword(null);
        ServiceLocator.getPersistance().putUser(user);
        Site[] sites = new Site[10];
        for (int i = 0; i < 10; i++) {
            sites[i] = TestUtil.createSite("Site" + i, "url" + i);
            TestUtil.createUserOnSiteRightActive(loginedUser, sites[i], SiteAccessLevel.ADMINISTRATOR);
            TestUtil.createUserOnSiteRightActive(adminUser1, sites[i], SiteAccessLevel.ADMINISTRATOR);
            TestUtil.createUserOnSiteRightActive(adminUser2, sites[i], SiteAccessLevel.ADMINISTRATOR);
            TestUtil.createUserOnSiteRightActive(adminUser3, sites[i], SiteAccessLevel.ADMINISTRATOR);
        }

        UserOnSiteRight right1 = TestUtil.createUserOnSiteRightInactiveAdmin(user, sites[0]);
        right1.setInvitationText("Invitation Text 1");
        right1.setRequesterUserId(loginedUser.getUserId());


        UserOnSiteRight right2 = TestUtil.createUserOnSiteRightInactiveAdmin(user, sites[1]);
        right2.setInvitationText("Invitation Text 2");
        right2.setRequesterUserId(adminUser1.getUserId());


        UserOnSiteRight right3 = TestUtil.createUserOnSiteRightActiveAdmin(user, sites[2]);
        right3.setInvitationText("Invitation Text 3");
        right3.setRequesterUserId(adminUser2.getUserId());


        UserOnSiteRight right4 = TestUtil.createUserOnSiteRightInactiveAdmin(user, sites[3]);
        right4.setInvitationText("Invitation Text 4");
        right4.setRequesterUserId(adminUser3.getUserId());

        actionOrService.execute(user.getEmail());
        Assert.assertEquals(3, mailSender.getMails().size());
    }

}