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
package com.shroggle.logic.start;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.Group;
import com.shroggle.entity.Site;
import com.shroggle.entity.User;
import com.shroggle.entity.UsersGroupId;
import com.shroggle.logic.user.UsersGroupManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class RemoveVisitorsFromGroupsTaskTest {

    @Test
    public void testRun() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();

        final Date expiredDate = new Date(1000);
        final Group group = TestUtil.createGroup("group1", site);
        TestUtil.createUsersGroup(user, group, expiredDate);
        final Group group2 = TestUtil.createGroup("group2", site);
        TestUtil.createUsersGroup(user, group2, new Date(System.currentTimeMillis() + 10000000000L));
        final Group group3 = TestUtil.createGroup("group3", site);
        TestUtil.createUsersGroup(user, group3, expiredDate);


        new RemoveVisitorsFromGroupsTask().run();


        Assert.assertEquals(false, new UsersGroupManager(user).hasAccessToGroup(group.getGroupId()));
        Assert.assertNull(persistance.getUsersGroup(new UsersGroupId(user, group)));
        Assert.assertEquals(true, new UsersGroupManager(user).hasAccessToGroup(group2.getGroupId()));
        Assert.assertNotNull(persistance.getUsersGroup(new UsersGroupId(user, group2)));
        Assert.assertEquals(false, new UsersGroupManager(user).hasAccessToGroup(group3.getGroupId()));
        Assert.assertNull(persistance.getUsersGroup(new UsersGroupId(user, group3)));

    }

    private final Persistance persistance = ServiceLocator.getPersistance();
}
