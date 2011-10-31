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

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.logic.manageRegistrants.ManageRegistrantsSortType;
import com.shroggle.presentation.manageRegistrants.ShowManageRegistrantsRequest;
import com.shroggle.exception.DuplicateGroupNamesException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.exception.EmptyGroupNameException;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.entity.Site;
import com.shroggle.entity.Group;
import com.shroggle.entity.User;
import com.shroggle.entity.RegistrantFilterType;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class SaveGroupsServiceTest {

    @Test
    public void testSave() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final Group group1 = TestUtil.createGroup("group1", site);
        final Group group2 = TestUtil.createGroup("group2", site);
        final Group group3 = TestUtil.createGroup("group3", site);

        List<GroupIdWithNewName> groupIdWithNewName = new ArrayList<GroupIdWithNewName>() {{
            add(new GroupIdWithNewName(group1.getGroupId(), "group2"));
            add(new GroupIdWithNewName(group2.getGroupId(), "group3"));
            add(new GroupIdWithNewName(group3.getGroupId(), "group1"));
        }};
        final SaveGroupsRequest request = new SaveGroupsRequest();
        request.setGroupIdWithNewName(groupIdWithNewName);
        final ShowManageRegistrantsRequest manageRegistrantsRequest = new ShowManageRegistrantsRequest(
                RegistrantFilterType.SHOW_ALL, "", site.getSiteId(), ManageRegistrantsSortType.FIRST_NAME, false);
        request.setManageRegistrantsRequest(manageRegistrantsRequest);

        service.execute(request);

        Assert.assertEquals("group2", persistance.getGroup(group1.getGroupId()).getName());
        Assert.assertEquals("group3", persistance.getGroup(group2.getGroupId()).getName());
        Assert.assertEquals("group1", persistance.getGroup(group3.getGroupId()).getName());
    }

    @Test
    public void testSave_withDuplicateNames() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final Group group1 = TestUtil.createGroup("group1", site);
        final Group group2 = TestUtil.createGroup("group2", site);
        final Group group3 = TestUtil.createGroup("group3", site);

        List<GroupIdWithNewName> groupIdWithNewName = new ArrayList<GroupIdWithNewName>() {{
            add(new GroupIdWithNewName(group1.getGroupId(), "group2"));
            add(new GroupIdWithNewName(group2.getGroupId(), "group3"));
            add(new GroupIdWithNewName(group3.getGroupId(), "group3"));
        }};
        final SaveGroupsRequest request = new SaveGroupsRequest();
        request.setGroupIdWithNewName(groupIdWithNewName);
        final ShowManageRegistrantsRequest manageRegistrantsRequest = new ShowManageRegistrantsRequest(
                RegistrantFilterType.SHOW_ALL, "", site.getSiteId(), ManageRegistrantsSortType.FIRST_NAME, false);
        request.setManageRegistrantsRequest(manageRegistrantsRequest);

        boolean exceptionThrown = false;
        try {
            service.execute(request);
        } catch (DuplicateGroupNamesException exception) {
            exceptionThrown = true;
        }

        Assert.assertTrue(exceptionThrown);
        Assert.assertEquals("group1", persistance.getGroup(group1.getGroupId()).getName());
        Assert.assertEquals("group2", persistance.getGroup(group2.getGroupId()).getName());
        Assert.assertEquals("group3", persistance.getGroup(group3.getGroupId()).getName());
    }

    @Test
    public void testSave_withEmptyName() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final Group group1 = TestUtil.createGroup("group1", site);
        final Group group2 = TestUtil.createGroup("group2", site);
        final Group group3 = TestUtil.createGroup("group3", site);

        List<GroupIdWithNewName> groupIdWithNewName = new ArrayList<GroupIdWithNewName>() {{
            add(new GroupIdWithNewName(group1.getGroupId(), "group2"));
            add(new GroupIdWithNewName(group2.getGroupId(), "group3"));
            add(new GroupIdWithNewName(group3.getGroupId(), ""));
        }};
        final SaveGroupsRequest request = new SaveGroupsRequest();
        request.setGroupIdWithNewName(groupIdWithNewName);
        final ShowManageRegistrantsRequest manageRegistrantsRequest = new ShowManageRegistrantsRequest(
                RegistrantFilterType.SHOW_ALL, "", site.getSiteId(), ManageRegistrantsSortType.FIRST_NAME, false);
        request.setManageRegistrantsRequest(manageRegistrantsRequest);

        boolean exceptionThrown = false;
        try {
            service.execute(request);
        } catch (EmptyGroupNameException exception) {
            exceptionThrown = true;
        }

        Assert.assertTrue(exceptionThrown);
        Assert.assertEquals("group1", persistance.getGroup(group1.getGroupId()).getName());
        Assert.assertEquals("group2", persistance.getGroup(group2.getGroupId()).getName());
        Assert.assertEquals("group3", persistance.getGroup(group3.getGroupId()).getName());
    }

    @Test(expected = UserNotLoginedException.class)
    public void testSave_withoutLoginedUser() throws Exception {
        service.execute(new SaveGroupsRequest());
    }

    private final SaveGroupsService service = new SaveGroupsService();
    private final Persistance persistance = ServiceLocator.getPersistance();
}
