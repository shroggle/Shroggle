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

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.groups.GroupsTime;
import com.shroggle.logic.manageRegistrants.ManageRegistrantsSortType;
import com.shroggle.logic.user.UsersGroupManager;
import com.shroggle.presentation.manageRegistrants.ShowManageRegistrantsRequest;
import com.shroggle.util.DateUtil;
import com.shroggle.util.TimeInterval;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class AddVisitorsToGroupServiceTest {

    @Test
    public void testExecute() throws Exception {
        final User user1 = TestUtil.createUserAndLogin();
        final User user2 = TestUtil.createUser();
        final User user3 = TestUtil.createUser();
        final User user4 = TestUtil.createUser();

        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user1, site);
        final Group group = TestUtil.createGroup("name", site);


        Assert.assertEquals(false, new UsersGroupManager(user1).hasAccessToGroup(group.getGroupId()));
        Assert.assertEquals(false, new UsersGroupManager(user2).hasAccessToGroup(group.getGroupId()));
        Assert.assertEquals(false, new UsersGroupManager(user3).hasAccessToGroup(group.getGroupId()));
        Assert.assertEquals(false, new UsersGroupManager(user4).hasAccessToGroup(group.getGroupId()));


        final ShowManageRegistrantsRequest manageRegistrantsRequest = new ShowManageRegistrantsRequest(
                RegistrantFilterType.SHOW_ALL, "", site.getSiteId(), ManageRegistrantsSortType.FIRST_NAME, false);

        final List<GroupsTime> groupsWithTime = new ArrayList<GroupsTime>(){{
            add(new GroupsTime(group.getGroupId(), null));
        }};
        service.execute(groupsWithTime, Arrays.asList(user1.getUserId(), user2.getUserId()), manageRegistrantsRequest);

        Assert.assertEquals(true, new UsersGroupManager(user1).hasAccessToGroup(group.getGroupId()));
        Assert.assertEquals(true, new UsersGroupManager(user2).hasAccessToGroup(group.getGroupId()));
        Assert.assertEquals(false,new UsersGroupManager(user3).hasAccessToGroup(group.getGroupId()));
        Assert.assertEquals(false,new UsersGroupManager(user4).hasAccessToGroup(group.getGroupId()));
        for(UsersGroup usersGroup : user1.getUsersGroups()){
            Assert.assertNull(usersGroup.getExpirationDate());
        }
        for(UsersGroup usersGroup : user2.getUsersGroups()){
            Assert.assertNull(usersGroup.getExpirationDate());
        }
    }

    @Test
    public void testExecute_withTimeInterval() throws Exception {
        final User user1 = TestUtil.createUserAndLogin();
        final User user2 = TestUtil.createUser();
        final User user3 = TestUtil.createUser();
        final User user4 = TestUtil.createUser();

        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user1, site);
        final Group group = TestUtil.createGroup("name", site);


        Assert.assertEquals(false, new UsersGroupManager(user1).hasAccessToGroup(group.getGroupId()));
        Assert.assertEquals(false, new UsersGroupManager(user2).hasAccessToGroup(group.getGroupId()));
        Assert.assertEquals(false, new UsersGroupManager(user3).hasAccessToGroup(group.getGroupId()));
        Assert.assertEquals(false, new UsersGroupManager(user4).hasAccessToGroup(group.getGroupId()));


        final ShowManageRegistrantsRequest manageRegistrantsRequest = new ShowManageRegistrantsRequest(
                RegistrantFilterType.SHOW_ALL, "", site.getSiteId(), ManageRegistrantsSortType.FIRST_NAME, false);

        final List<GroupsTime> groupsWithTime = new ArrayList<GroupsTime>(){{
            add(new GroupsTime(group.getGroupId(), TimeInterval.ONE_DAY));
        }};
        service.execute(groupsWithTime, Arrays.asList(user1.getUserId(), user2.getUserId()), manageRegistrantsRequest);

        Assert.assertEquals(true, new UsersGroupManager(user1).hasAccessToGroup(group.getGroupId()));
        Assert.assertEquals(true, new UsersGroupManager(user2).hasAccessToGroup(group.getGroupId()));
        Assert.assertEquals(false,new UsersGroupManager(user3).hasAccessToGroup(group.getGroupId()));
        Assert.assertEquals(false,new UsersGroupManager(user4).hasAccessToGroup(group.getGroupId()));
        for(UsersGroup usersGroup : user1.getUsersGroups()){
            Assert.assertEquals(DateUtil.toCommonDateStr(new Date(System.currentTimeMillis() + TimeInterval.ONE_DAY.getMillis())),
                    DateUtil.toCommonDateStr(usersGroup.getExpirationDate()));
        }
        for(UsersGroup usersGroup : user2.getUsersGroups()){
            Assert.assertEquals(DateUtil.toCommonDateStr(new Date(System.currentTimeMillis() + TimeInterval.ONE_DAY.getMillis())),
                    DateUtil.toCommonDateStr(usersGroup.getExpirationDate()));
        }
    }

    @Test(expected = UserNotLoginedException.class)
    public void testExecute_withoutLoginedUser() throws Exception {
        service.execute(null, null, null);
    }

    private final AddVisitorsToGroupService service = new AddVisitorsToGroupService();
}
