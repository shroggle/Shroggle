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
package com.shroggle.presentation.user.create;

import org.junit.Test;
import org.junit.runner.RunWith;
import com.shroggle.TestUtil;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.entity.User;
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import junit.framework.Assert;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class CreateUserSitesTest {


    @Test
    public void testExecute_forAdminAndEditor() {
        final User user = TestUtil.createUser();
        final User invitedUser = TestUtil.createUser();

        final Site site1 = TestUtil.createSite("title1", "url1");
        final Site site2 = TestUtil.createSite("title2", "url2");
        final Site site3 = TestUtil.createSite("title3", "url3");
        final Site site4 = TestUtil.createSite("title4", "url4");
        final Site site5 = TestUtil.createSite("title5", "url5");
        final Site site6 = TestUtil.createSite("title6", "url6");
        final Site site7 = TestUtil.createSite("title7", "url7");

        TestUtil.createUserOnSiteRightActiveAdmin(user, site1);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site2);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site3);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site4);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site5);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site6);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site7);

        TestUtil.createUserOnSiteRightInactiveAdmin(invitedUser, site1);
        TestUtil.createUserOnSiteRightInactiveAdmin(invitedUser, site2);
        TestUtil.createUserOnSiteRightInactiveAdmin(invitedUser, site3);
        TestUtil.createUserOnSiteRightInactive(invitedUser, site4, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightInactive(invitedUser, site5, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightInactive(invitedUser, site6, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightInactive(invitedUser, site7, SiteAccessLevel.EDITOR);


        final CreateUserRequest request = new CreateUserRequest();
        request.setUserId(user.getUserId());
        request.setInvitedUserId(invitedUser.getUserId());
        final CreateUserState state = new CreateUserState(request);
        final CreateUserAction action = new CreateUserAction();
        action.setState(state);

        new CreateUserSites().execute(action);


        Assert.assertEquals("You are invited to be an administrator for the following site(s): title1, title2, title3 " +
                "and editor for the following site(s): title4, title5, title6, title7.",
                action.getRequest().getHasAccessToSitesMessage());
    }

    @Test
    public void testExecute_forAdmin() {
        final User user = TestUtil.createUser();
        final User invitedUser = TestUtil.createUser();

        final Site site1 = TestUtil.createSite("title1", "url1");
        final Site site2 = TestUtil.createSite("title2", "url2");
        final Site site3 = TestUtil.createSite("title3", "url3");
        final Site site4 = TestUtil.createSite("title4", "url4");
        final Site site5 = TestUtil.createSite("title5", "url5");
        final Site site6 = TestUtil.createSite("title6", "url6");
        final Site site7 = TestUtil.createSite("title7", "url7");

        TestUtil.createUserOnSiteRightActiveAdmin(user, site1);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site2);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site3);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site4);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site5);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site6);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site7);

        TestUtil.createUserOnSiteRightInactiveAdmin(invitedUser, site1);
        TestUtil.createUserOnSiteRightInactiveAdmin(invitedUser, site2);
        TestUtil.createUserOnSiteRightInactiveAdmin(invitedUser, site3);
        TestUtil.createUserOnSiteRightInactiveAdmin(invitedUser, site4);
        TestUtil.createUserOnSiteRightInactiveAdmin(invitedUser, site5);
        TestUtil.createUserOnSiteRightInactiveAdmin(invitedUser, site6);
        TestUtil.createUserOnSiteRightInactiveAdmin(invitedUser, site7);


        final CreateUserRequest request = new CreateUserRequest();
        request.setUserId(user.getUserId());
        request.setInvitedUserId(invitedUser.getUserId());
        final CreateUserState state = new CreateUserState(request);
        final CreateUserAction action = new CreateUserAction();
        action.setState(state);

        new CreateUserSites().execute(action);


        Assert.assertEquals("You are invited to be an administrator for the following site(s): title1, title2, title3, " +
                "title4, title5, title6, title7.",
                action.getRequest().getHasAccessToSitesMessage());
    }

    @Test
    public void testExecute_forEditor() {
        final User user = TestUtil.createUser();
        final User invitedUser = TestUtil.createUser();

        final Site site1 = TestUtil.createSite("title1", "url1");
        final Site site2 = TestUtil.createSite("title2", "url2");
        final Site site3 = TestUtil.createSite("title3", "url3");
        final Site site4 = TestUtil.createSite("title4", "url4");
        final Site site5 = TestUtil.createSite("title5", "url5");
        final Site site6 = TestUtil.createSite("title6", "url6");
        final Site site7 = TestUtil.createSite("title7", "url7");

        TestUtil.createUserOnSiteRightActiveAdmin(user, site1);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site2);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site3);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site4);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site5);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site6);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site7);

        TestUtil.createUserOnSiteRightInactive(invitedUser, site1, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightInactive(invitedUser, site2, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightInactive(invitedUser, site3, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightInactive(invitedUser, site4, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightInactive(invitedUser, site5, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightInactive(invitedUser, site6, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightInactive(invitedUser, site7, SiteAccessLevel.EDITOR);


        final CreateUserRequest request = new CreateUserRequest();
        request.setUserId(user.getUserId());
        request.setInvitedUserId(invitedUser.getUserId());
        final CreateUserState state = new CreateUserState(request);
        final CreateUserAction action = new CreateUserAction();
        action.setState(state);

        new CreateUserSites().execute(action);


        Assert.assertEquals("You are invited to be an editor for the following site(s): title1, title2, title3, title4, " +
                "title5, title6, title7.",
                action.getRequest().getHasAccessToSitesMessage());
    }

    @Test
    public void testExecute_withoutRights() {
        final User user = TestUtil.createUser();
        final User invitedUser = TestUtil.createUser();

        final Site site1 = TestUtil.createSite("title1", "url1");
        final Site site2 = TestUtil.createSite("title2", "url2");
        final Site site3 = TestUtil.createSite("title3", "url3");
        final Site site4 = TestUtil.createSite("title4", "url4");
        final Site site5 = TestUtil.createSite("title5", "url5");
        final Site site6 = TestUtil.createSite("title6", "url6");
        final Site site7 = TestUtil.createSite("title7", "url7");

        TestUtil.createUserOnSiteRightActiveAdmin(user, site1);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site2);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site3);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site4);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site5);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site6);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site7);


        final CreateUserRequest request = new CreateUserRequest();
        request.setUserId(user.getUserId());
        request.setInvitedUserId(invitedUser.getUserId());
        final CreateUserState state = new CreateUserState(request);
        final CreateUserAction action = new CreateUserAction();
        action.setState(state);

        new CreateUserSites().execute(action);


        Assert.assertEquals("", action.getRequest().getHasAccessToSitesMessage());
    }

}
