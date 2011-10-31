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
import com.shroggle.TestUtil;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.util.ServiceLocator;
import com.shroggle.logic.manageRegistrants.ManageRegistrantsSortType;
import com.shroggle.presentation.manageRegistrants.ShowManageRegistrantsRequest;
import com.shroggle.entity.Group;
import com.shroggle.entity.Site;
import com.shroggle.entity.RegistrantFilterType;
import com.shroggle.entity.User;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class RemoveGroupServiceTest {


    @Test
    public void testExecute() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final Group group = TestUtil.createGroup("", new Site());
        final ShowManageRegistrantsRequest manageRegistrantsRequest = new ShowManageRegistrantsRequest(
                RegistrantFilterType.SHOW_ALL, "", site.getSiteId(), ManageRegistrantsSortType.FIRST_NAME, false);


        service.execute(group.getGroupId(), manageRegistrantsRequest);

        Assert.assertNull(ServiceLocator.getPersistance().getGroup(group.getGroupId()));
    }

    @Test
    public void testExecute_withoutGroup() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final Group group = TestUtil.createGroup("", new Site());
        final ShowManageRegistrantsRequest manageRegistrantsRequest = new ShowManageRegistrantsRequest(
                RegistrantFilterType.SHOW_ALL, "", site.getSiteId(), ManageRegistrantsSortType.FIRST_NAME, false);


        service.execute(-1, manageRegistrantsRequest);

        Assert.assertNotNull(ServiceLocator.getPersistance().getGroup(group.getGroupId()));
    }

    @Test(expected = UserNotLoginedException.class)
    public void testExecute_withoutLoginedUser() throws Exception {
        final Site site = TestUtil.createSite();
        final ShowManageRegistrantsRequest manageRegistrantsRequest = new ShowManageRegistrantsRequest(
                RegistrantFilterType.SHOW_ALL, "", site.getSiteId(), ManageRegistrantsSortType.FIRST_NAME, false);


        service.execute(-1, manageRegistrantsRequest);
    }

    private final RemoveGroupService service = new RemoveGroupService();
}
