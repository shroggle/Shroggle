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
import com.shroggle.entity.Group;
import com.shroggle.entity.Site;
import com.shroggle.entity.User;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.presentation.MockWebContext;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class ShowGroupsServiceTest {

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void testExecute() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite("title", "url");
        final Group group = TestUtil.createGroup("group1", site);
        TestUtil.createUsersGroup(user, group);

        service.execute(site.getSiteId());

        final List<GroupData> groupsData = (List<GroupData>)service.getContext().getHttpServletRequest().getAttribute("groupsData");
        final String siteName = (String)service.getContext().getHttpServletRequest().getAttribute("siteName");

        Assert.assertEquals("title", siteName);
        Assert.assertEquals(1, groupsData.size());
        Assert.assertEquals(group.getName(), groupsData.get(0).getName());
        Assert.assertEquals(group.getGroupId(), groupsData.get(0).getGroupId());
        Assert.assertEquals(1, groupsData.get(0).getVisitorsCount());
    }

    @Test
    public void testExecute_withoutCreatedGroups() throws Exception {
        TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite("title", "url");

        service.execute(site.getSiteId());

        final List<GroupData> groupsData = (List<GroupData>)service.getContext().getHttpServletRequest().getAttribute("groupsData");
        final String siteName = (String)service.getContext().getHttpServletRequest().getAttribute("siteName");

        Assert.assertEquals("title", siteName);
        Assert.assertEquals("One default group should be created in this case", 1, groupsData.size());
        Assert.assertEquals("Invited", groupsData.get(0).getName());
        Assert.assertEquals(0, groupsData.get(0).getVisitorsCount());
    }

    @Test(expected = UserNotLoginedException.class)
    public void testExecute_withoutLoginedUser() throws Exception {
        final Site site = TestUtil.createSite("title", "url");
        TestUtil.createGroup("group1", site);

        service.execute(site.getSiteId());
    }

    @Test(expected = SiteNotFoundException.class)
    public void testExecute_withoutSite() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite("title", "url");
        final Group group = TestUtil.createGroup("group1", site);
        TestUtil.createUsersGroup(user, group);

        service.execute(-1);
    }

    private final ShowGroupsService service = new ShowGroupsService();
}
