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
package com.shroggle.presentation.manageRegistrants;

import com.shroggle.logic.user.UsersGroupManager;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.logic.manageRegistrants.ManageRegistrantsSortType;
import com.shroggle.entity.User;
import com.shroggle.entity.Site;
import com.shroggle.entity.Group;
import com.shroggle.entity.RegistrantFilterType;

import java.util.*;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class ShowManageRegistrantsServiceTest {

    @Test
    public void testExecute() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final Group group1 = TestUtil.createGroup("group1", site);
        final Group group2 = TestUtil.createGroup("group2", site);
        final Group group3 = TestUtil.createGroup("group3", site);
        new UsersGroupManager(user).addAccessToGroup(group1);
        new UsersGroupManager(user).addAccessToGroup(group3);

        final ShowManageRegistrantsRequest request = new ShowManageRegistrantsRequest();
        request.setSearchKey("");
        request.setStatus(RegistrantFilterType.SHOW_ALL);
        request.setSiteId(site.getSiteId());
        request.setSortType(ManageRegistrantsSortType.FIRST_NAME);
        request.setDesc(false);


        service.execute(request);


        final List<Group> groups = (List<Group>) service.getContext().getHttpServletRequest().getAttribute("availableGroups");
        Assert.assertEquals(3, groups.size());
        Assert.assertEquals(true, groups.contains(group1));
        Assert.assertEquals(true, groups.contains(group2));
        Assert.assertEquals(true, groups.contains(group3));
    }

    private final ShowManageRegistrantsTableService service = new ShowManageRegistrantsTableService();
}
