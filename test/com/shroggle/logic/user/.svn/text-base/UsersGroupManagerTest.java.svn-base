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
package com.shroggle.logic.user;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.util.DateUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.TimeInterval;
import com.shroggle.util.persistance.Persistance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class UsersGroupManagerTest {


    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithoutUser() throws Exception {
        new UsersGroupManager(null);
    }


    @Test
    public void testRemoveAccessToGroup() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final Group group = TestUtil.createGroup("group1", site);
        TestUtil.createUsersGroup(user, group);
        final Group group2 = TestUtil.createGroup("group2", site);
        TestUtil.createUsersGroup(user, group2);
        final Group group3 = TestUtil.createGroup("group3", site);
        TestUtil.createUsersGroup(user, group3);

        Assert.assertEquals(true, new UsersGroupManager(user).hasAccessToGroup(group.getGroupId()));
        Assert.assertNotNull(persistance.getUsersGroup(new UsersGroupId(user, group)));
        Assert.assertEquals(true, new UsersGroupManager(user).hasAccessToGroup(group2.getGroupId()));
        Assert.assertNotNull(persistance.getUsersGroup(new UsersGroupId(user, group2)));
        Assert.assertEquals(true, new UsersGroupManager(user).hasAccessToGroup(group3.getGroupId()));
        Assert.assertNotNull(persistance.getUsersGroup(new UsersGroupId(user, group3)));


        new UsersGroupManager(user).removeAccessToGroup(group.getGroupId());


        Assert.assertEquals(false, new UsersGroupManager(user).hasAccessToGroup(group.getGroupId()));
        Assert.assertNull(persistance.getUsersGroup(new UsersGroupId(user, group)));
        Assert.assertEquals(true, new UsersGroupManager(user).hasAccessToGroup(group2.getGroupId()));
        Assert.assertNotNull(persistance.getUsersGroup(new UsersGroupId(user, group2)));
        Assert.assertEquals(true, new UsersGroupManager(user).hasAccessToGroup(group3.getGroupId()));
        Assert.assertNotNull(persistance.getUsersGroup(new UsersGroupId(user, group3)));
    }


    @Test
    public void testRemoveExpiredAccessToGroups() {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();

        final Date expiredDate = new Date(1000);
        final Group group = TestUtil.createGroup("group1", site);
        TestUtil.createUsersGroup(user, group, expiredDate);
        final Group group2 = TestUtil.createGroup("group2", site);
        TestUtil.createUsersGroup(user, group2, new Date(System.currentTimeMillis() + 10000000000L));
        final Group group3 = TestUtil.createGroup("group3", site);
        TestUtil.createUsersGroup(user, group3, expiredDate);


        new UsersGroupManager(user).removeExpiredAccessToGroups();


        Assert.assertEquals(false, new UsersGroupManager(user).hasAccessToGroup(group.getGroupId()));
        Assert.assertNull(persistance.getUsersGroup(new UsersGroupId(user, group)));
        Assert.assertEquals(true, new UsersGroupManager(user).hasAccessToGroup(group2.getGroupId()));
        Assert.assertNotNull(persistance.getUsersGroup(new UsersGroupId(user, group2)));
        Assert.assertEquals(false, new UsersGroupManager(user).hasAccessToGroup(group3.getGroupId()));
        Assert.assertNull(persistance.getUsersGroup(new UsersGroupId(user, group3)));
    }

    @Test
    public void testHasAccessToOneOfGroups() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final Group group = TestUtil.createGroup("group1", site);
        final Group group2 = TestUtil.createGroup("group2", site);
        TestUtil.createUsersGroup(user, group2);
        final Group group3 = TestUtil.createGroup("group3", site);

        final List<Integer> groupsId = new ArrayList<Integer>() {{
            add(group.getGroupId());
            add(group2.getGroupId());
            add(group3.getGroupId());
        }};
        Assert.assertEquals(true, new UsersGroupManager(user).hasAccessToOneOfGroups(groupsId));
    }

    @Test
    public void testHasAccessToOneOfGroups_withExpiredDate() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final Group group = TestUtil.createGroup("group1", site);
        TestUtil.createUsersGroup(user, group, new Date(System.currentTimeMillis() - 100000000));

        Assert.assertEquals(false, new UsersGroupManager(user).hasAccessToOneOfGroups(Arrays.asList(group.getGroupId())));
        Assert.assertNull(persistance.getUsersGroup(new UsersGroupId(user, group)));
    }

    @Test
    public void testHasAccessToGroup() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final Group group = TestUtil.createGroup("group1", site);
        final Group group2 = TestUtil.createGroup("group2", site);
        TestUtil.createUsersGroup(user, group2);
        final Group group3 = TestUtil.createGroup("group3", site);

        Assert.assertEquals(false, new UsersGroupManager(user).hasAccessToGroup(group.getGroupId()));
        Assert.assertEquals(true, new UsersGroupManager(user).hasAccessToGroup(group2.getGroupId()));
        Assert.assertEquals(false, new UsersGroupManager(user).hasAccessToGroup(group3.getGroupId()));
        Assert.assertNotNull(persistance.getUsersGroup(new UsersGroupId(user, group2)));
    }

    @Test
    public void testHasAccessToGroup_withExpiredDate() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final Group group = TestUtil.createGroup("group1", site);
        TestUtil.createUsersGroup(user, group, new Date(System.currentTimeMillis() - 100000000));

        Assert.assertEquals(false, new UsersGroupManager(user).hasAccessToGroup(group.getGroupId()));
        Assert.assertNull(persistance.getUsersGroup(new UsersGroupId(user, group)));
    }

    @Test
    public void testGetExpirationDateForGroup(){
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final Group group = TestUtil.createGroup("group", site);
        final Calendar calendar = new GregorianCalendar(2110, 10, 1);
        TestUtil.createUsersGroup(user, group, calendar.getTime());

        Assert.assertEquals(true, new UsersGroupManager(user).hasAccessToGroup(group.getGroupId()));
        Assert.assertNotNull(persistance.getUsersGroup(new UsersGroupId(user, group)));

        final Date expirationDate = new UsersGroupManager(user).getExpirationDateForGroup(group.getGroupId());
        Assert.assertEquals("11/01/2110", DateUtil.toMonthDayAndYear(expirationDate));
    }

    @Test
    public void testGetExpirationDateForGroup_withExpiredDate(){
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final Group group = TestUtil.createGroup("group", site);
        final Calendar calendar = new GregorianCalendar(110, 10, 1);
        TestUtil.createUsersGroup(user, group, calendar.getTime());

        Assert.assertEquals(false, new UsersGroupManager(user).hasAccessToGroup(group.getGroupId()));
        Assert.assertNull(persistance.getUsersGroup(new UsersGroupId(user, group)));

        final Date expirationDate = new UsersGroupManager(user).getExpirationDateForGroup(group.getGroupId());
        Assert.assertEquals(null, expirationDate);
    }

    @Test
    public void testGetExpirationDateForGroup_withoutExpirationDate(){
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final Group group = TestUtil.createGroup("group", site);
        TestUtil.createUsersGroup(user, group);

        Assert.assertEquals(true, new UsersGroupManager(user).hasAccessToGroup(group.getGroupId()));
        Assert.assertNotNull(persistance.getUsersGroup(new UsersGroupId(user, group)));

        final Date expirationDate = new UsersGroupManager(user).getExpirationDateForGroup(group.getGroupId());
        Assert.assertEquals(null, expirationDate);
    }

    @Test
    public void testIsAccessExpired_oldDate() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final Group group = TestUtil.createGroup("group1", site);
        final UsersGroup usersGroup = TestUtil.createUsersGroup(user, group, new Date(System.currentTimeMillis() - 100000000));

        Assert.assertEquals(true, new UsersGroupManager(user).isAccessExpired(usersGroup));
    }

    @Test
    public void testIsAccessExpired_dateInFuture() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final Group group = TestUtil.createGroup("group1", site);
        final UsersGroup usersGroup = TestUtil.createUsersGroup(user, group, new Date(System.currentTimeMillis() + 100000000));

        Assert.assertEquals(false, new UsersGroupManager(user).isAccessExpired(usersGroup));
    }

    @Test
    public void testIsAccessExpired_withoutExpirationDate() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final Group group = TestUtil.createGroup("group1", site);
        final UsersGroup usersGroup = TestUtil.createUsersGroup(user, group);

        Assert.assertEquals(false, new UsersGroupManager(user).isAccessExpired(usersGroup));
    }

    @Test
    public void testGetAccessibleGroupsId() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final Group group = TestUtil.createGroup("group1", site);
        final Group group2 = TestUtil.createGroup("group2", site);
        final Group group3 = TestUtil.createGroup("group3", site);
        TestUtil.createUsersGroup(user, group);
        TestUtil.createUsersGroup(user, group3);


        final List<Integer> accessibleGroups = new UsersGroupManager(user).getAccessibleGroupsId();


        Assert.assertEquals(2, accessibleGroups.size());
        Assert.assertEquals(true, accessibleGroups.contains(group.getGroupId()));
        Assert.assertEquals(false, accessibleGroups.contains(group2.getGroupId()));
        Assert.assertEquals(true, accessibleGroups.contains(group3.getGroupId()));
    }

    @Test
    public void testGetAccessibleGroupsId_withOneExpiredAccess() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final Group group = TestUtil.createGroup("group1", site);
        final Group group2 = TestUtil.createGroup("group2", site);
        final Group group3 = TestUtil.createGroup("group3", site);
        TestUtil.createUsersGroup(user, group);
        TestUtil.createUsersGroup(user, group3, new Date(System.currentTimeMillis() - 100000000));


        final List<Integer> accessibleGroups = new UsersGroupManager(user).getAccessibleGroupsId();


        Assert.assertEquals(1, accessibleGroups.size());
        Assert.assertEquals(true, accessibleGroups.contains(group.getGroupId()));
        Assert.assertEquals(false, accessibleGroups.contains(group2.getGroupId()));
        Assert.assertEquals(false, accessibleGroups.contains(group3.getGroupId()));
    }

    @Test
    public void testGetAccessibleGroupsId_forSite() throws Exception {
        final User user = TestUtil.createUser();
        final Site site1 = TestUtil.createSite();
        final Site site2 = TestUtil.createSite();
        final Group group = TestUtil.createGroup("group1", site1);
        final Group group2 = TestUtil.createGroup("group2", site2);
        final Group group3 = TestUtil.createGroup("group3", site1);
        TestUtil.createUsersGroup(user, group);
        TestUtil.createUsersGroup(user, group3);


        final List<Integer> accessibleGroups = new UsersGroupManager(user).getAccessibleGroupsIdForSite(site1.getSiteId());


        Assert.assertEquals(2, accessibleGroups.size());
        Assert.assertEquals(true, accessibleGroups.contains(group.getGroupId()));
        Assert.assertEquals(false, accessibleGroups.contains(group2.getGroupId()));
        Assert.assertEquals(true, accessibleGroups.contains(group3.getGroupId()));
    }

    @Test
    public void testGetAccessibleGroupsId_withOneExpiredAccess_forSite() throws Exception {
        final User user = TestUtil.createUser();
        final Site site1 = TestUtil.createSite();
        final Site site2 = TestUtil.createSite();
        final Group group = TestUtil.createGroup("group1", site1);
        final Group group2 = TestUtil.createGroup("group2", site2);
        final Group group3 = TestUtil.createGroup("group3", site1);
        TestUtil.createUsersGroup(user, group);
        TestUtil.createUsersGroup(user, group2);
        TestUtil.createUsersGroup(user, group3, new Date(System.currentTimeMillis() - 100000000));


        final List<Integer> accessibleGroups = new UsersGroupManager(user).getAccessibleGroupsIdForSite(site1.getSiteId());


        Assert.assertEquals(1, accessibleGroups.size());
        Assert.assertEquals(true, accessibleGroups.contains(group.getGroupId()));
        Assert.assertEquals(false, accessibleGroups.contains(group2.getGroupId()));
        Assert.assertEquals(false, accessibleGroups.contains(group3.getGroupId()));
    }

    @Test
    public void testAddAccessToGroup_withoutDate() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final Group group = TestUtil.createGroup("group1", site);

        Assert.assertEquals(false, new UsersGroupManager(user).hasAccessToOneOfGroups(Arrays.asList(group.getGroupId())));
        Assert.assertNull(persistance.getUsersGroup(new UsersGroupId(user, group)));


        new UsersGroupManager(user).addAccessToGroup(group);


        Assert.assertEquals(true, new UsersGroupManager(user).hasAccessToOneOfGroups(Arrays.asList(group.getGroupId())));
        Assert.assertNotNull(persistance.getUsersGroup(new UsersGroupId(user, group)));
        Assert.assertNull(persistance.getUsersGroup(new UsersGroupId(user, group)).getExpirationDate());
    }

    @Test
    public void testAddAccessToGroup_withoutOldExpirationDate() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final Group group = TestUtil.createGroup("group1", site);

        Assert.assertEquals(false, new UsersGroupManager(user).hasAccessToOneOfGroups(Arrays.asList(group.getGroupId())));
        Assert.assertNull(persistance.getUsersGroup(new UsersGroupId(user, group)));


        new UsersGroupManager(user).addAccessToGroup(group, TimeInterval.ONE_DAY);


        final Date currentDatePlusOneDay = new Date(System.currentTimeMillis() + TimeInterval.ONE_DAY.getMillis());
        Assert.assertEquals(true, new UsersGroupManager(user).hasAccessToOneOfGroups(Arrays.asList(group.getGroupId())));
        Assert.assertNotNull(persistance.getUsersGroup(new UsersGroupId(user, group)));
        Assert.assertEquals(currentDatePlusOneDay.getTime() / 10000, persistance.getUsersGroup(new UsersGroupId(user, group)).getExpirationDate().getTime() / 10000);
    }


    @Test
    public void testAddAccessToGroup_withoutTimeINterval() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final Group group = TestUtil.createGroup("group1", site);
        final Date oldExpirationDate = new Date(System.currentTimeMillis() + 100000);
        TestUtil.createUsersGroup(user, group, oldExpirationDate);
        Assert.assertEquals(true, new UsersGroupManager(user).hasAccessToOneOfGroups(Arrays.asList(group.getGroupId())));
        Assert.assertNotNull(persistance.getUsersGroup(new UsersGroupId(user, group)));
        Assert.assertEquals(oldExpirationDate, persistance.getUsersGroup(new UsersGroupId(user, group)).getExpirationDate());


        new UsersGroupManager(user).addAccessToGroup(group, null);


        Assert.assertEquals(true, new UsersGroupManager(user).hasAccessToOneOfGroups(Arrays.asList(group.getGroupId())));
        Assert.assertNotNull(persistance.getUsersGroup(new UsersGroupId(user, group)));
        Assert.assertEquals(null,
                persistance.getUsersGroup(new UsersGroupId(user, group)).getExpirationDate());
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
}
