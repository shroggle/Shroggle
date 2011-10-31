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

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.Group;
import com.shroggle.entity.RegistrantFilterType;
import com.shroggle.entity.Site;
import com.shroggle.entity.User;
import com.shroggle.logic.manageRegistrants.ManageRegistrantsSortType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class ShowManageGroupsWindowServiceTest {

    @Test
    public void testExecute() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final Group group1 = TestUtil.createGroup("group1", site);
        final Group group2 = TestUtil.createGroup("group2", site);
        final Group group3 = TestUtil.createGroup("group3", site);

        final ShowManageRegistrantsRequest request = new ShowManageRegistrantsRequest();
        request.setSearchKey("");
        request.setStatus(RegistrantFilterType.SHOW_ALL);
        request.setSiteId(site.getSiteId());
        request.setSortType(ManageRegistrantsSortType.FIRST_NAME);
        request.setDesc(false);

        final List<Integer> checkedVisitorsId = new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(3);
        }};
        service.execute(site.getSiteId(), checkedVisitorsId);


        final ShowManageGroupsWindowService service = (ShowManageGroupsWindowService) this.service.getContext().getHttpServletRequest().getAttribute("service");
        Assert.assertEquals("1, 2, 3", service.getCheckedVisitorsId());
        Assert.assertEquals(3, service.getGroups().size());
        Assert.assertEquals(true, service.getGroups().contains(group1));
        Assert.assertEquals(true, service.getGroups().contains(group2));
        Assert.assertEquals(true, service.getGroups().contains(group3));
    }

    private final ShowManageGroupsWindowService service = new ShowManageGroupsWindowService();
}
