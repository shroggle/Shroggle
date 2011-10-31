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
package com.shroggle.presentation.groups;

import com.shroggle.logic.user.UsersGroupManager;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import com.shroggle.entity.User;
import com.shroggle.entity.Site;
import com.shroggle.entity.Group;
import com.shroggle.entity.RegistrantFilterType;
import com.shroggle.TestUtil;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.logic.manageRegistrants.ManageRegistrantsSortType;
import com.shroggle.presentation.manageRegistrants.ShowManageRegistrantsRequest;
import com.shroggle.exception.UserNotLoginedException;

import java.util.Arrays;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class RemoveVisitorsFromGroupServiceTest {

    @Test
    public void testExecute() throws Exception {
        final User user1 = TestUtil.createUserAndLogin();
        final User user2 = TestUtil.createUser();
        final User user3 = TestUtil.createUser();
        final User user4 = TestUtil.createUser();

        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user1, site);
        final Group group = TestUtil.createGroup("name", site);

        TestUtil.createUsersGroup(user1, group);
        TestUtil.createUsersGroup(user2, group);
        TestUtil.createUsersGroup(user3, group);
        TestUtil.createUsersGroup(user4, group);


        Assert.assertEquals(true, new UsersGroupManager(user1).hasAccessToGroup(group.getGroupId()));
        Assert.assertEquals(true, new UsersGroupManager(user2).hasAccessToGroup(group.getGroupId()));
        Assert.assertEquals(true, new UsersGroupManager(user3).hasAccessToGroup(group.getGroupId()));
        Assert.assertEquals(true, new UsersGroupManager(user4).hasAccessToGroup(group.getGroupId()));


        final ShowManageRegistrantsRequest manageRegistrantsRequest = new ShowManageRegistrantsRequest(
                RegistrantFilterType.SHOW_ALL, "", site.getSiteId(), ManageRegistrantsSortType.FIRST_NAME, false);
        service.execute(group.getGroupId(), Arrays.asList(user1.getUserId(), user2.getUserId()), manageRegistrantsRequest);


        Assert.assertEquals(false, new UsersGroupManager(user1).hasAccessToGroup(group.getGroupId()));
        Assert.assertEquals(false, new UsersGroupManager(user2).hasAccessToGroup(group.getGroupId()));
        Assert.assertEquals(true, new UsersGroupManager(user3).hasAccessToGroup(group.getGroupId()));
        Assert.assertEquals(true, new UsersGroupManager(user4).hasAccessToGroup(group.getGroupId()));
    }

    @Test(expected = UserNotLoginedException.class)
    public void testExecute_withoutLoginedUser() throws Exception {
        service.execute(1, Arrays.asList(1), new ShowManageRegistrantsRequest());
    }

    private final RemoveVisitorsFromGroupService service = new RemoveVisitorsFromGroupService();
}
