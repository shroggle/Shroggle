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
import com.shroggle.entity.User;
import com.shroggle.entity.Site;
import com.shroggle.entity.RegistrantFilterType;
import com.shroggle.TestUtil;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.logic.manageRegistrants.ManageRegistrantsSortType;
import com.shroggle.presentation.manageRegistrants.ShowManageRegistrantsRequest;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class AddGroupServiceTest {


    @Test
    public void testExecute_withGroups() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        TestUtil.createGroup("New Group", site);
        TestUtil.createGroup("New Group1", site);
        TestUtil.createGroup("New Group2", site);


        final GroupIdWithNewName groupIdWithNewName = service.execute(site.getSiteId(), manageRegistrantsRequest).getGroupIdWithNewName();
        Assert.assertEquals("New Group3", groupIdWithNewName.getName());
        Assert.assertNotNull(persistance.getGroup(groupIdWithNewName.getGroupId()));
    }

    @Test
    public void testExecute_withoutGroups() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);


        final GroupIdWithNewName groupIdWithNewName = service.execute(site.getSiteId(), manageRegistrantsRequest).getGroupIdWithNewName();
        Assert.assertEquals("New Group", groupIdWithNewName.getName());
        Assert.assertNotNull(persistance.getGroup(groupIdWithNewName.getGroupId()));
    }

    @Test(expected = UserNotLoginedException.class)
    public void testExecute_withoutLoginedUser() throws Exception {
        service.execute(1, manageRegistrantsRequest);
    }

    @Test(expected = SiteNotFoundException.class)
    public void testExecute_withoutSite() throws Exception {
        TestUtil.createUserAndLogin();
        service.execute(-1, manageRegistrantsRequest);
    }

    @Test(expected = SiteNotFoundException.class)
    public void testExecute_withoutRightsOnSite() throws Exception {
        TestUtil.createUserAndLogin();
        service.execute(site.getSiteId(), manageRegistrantsRequest);
    }

    private final AddGroupService service = new AddGroupService();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final Site site = TestUtil.createSite();
    private final ShowManageRegistrantsRequest manageRegistrantsRequest = new ShowManageRegistrantsRequest(
            RegistrantFilterType.SHOW_ALL, "", site.getSiteId(), ManageRegistrantsSortType.FIRST_NAME, false);
}
