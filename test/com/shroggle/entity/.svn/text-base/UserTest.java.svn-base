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
package com.shroggle.entity;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.logic.user.UsersGroupManager;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class UserTest {

    @Test
    public void testHasAccessToOneOfGroups() {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();

        final Group group1 = TestUtil.createGroup("name1", site);
        final Group group2 = TestUtil.createGroup("name2", site);
        final Group group3 = TestUtil.createGroup("name3", site);
        final Group group4 = TestUtil.createGroup("name4", site);

        final UsersGroupManager userManager = new UsersGroupManager(user);
        userManager.addAccessToGroup(group1);
        userManager.addAccessToGroup(group2);
        userManager.addAccessToGroup(group3);
        userManager.addAccessToGroup(group4);

        final UsersGroupManager usersGroupManager = new UsersGroupManager(user);

        Assert.assertTrue(usersGroupManager.hasAccessToGroup(group1.getGroupId()));
        Assert.assertTrue(usersGroupManager.hasAccessToGroup(group2.getGroupId()));
        Assert.assertTrue(usersGroupManager.hasAccessToGroup(group3.getGroupId()));
        Assert.assertTrue(usersGroupManager.hasAccessToGroup(group4.getGroupId()));
        Assert.assertFalse(usersGroupManager.hasAccessToGroup(5));
        Assert.assertFalse(usersGroupManager.hasAccessToGroup(6));
        Assert.assertFalse(usersGroupManager.hasAccessToGroup(7));

        Assert.assertTrue(usersGroupManager.hasAccessToOneOfGroups(Arrays.asList(34, 645, 56, 1)));
        Assert.assertFalse(usersGroupManager.hasAccessToOneOfGroups(Arrays.asList(34, 645, 56)));

    }
}
