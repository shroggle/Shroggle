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
import com.shroggle.entity.Site;
import com.shroggle.entity.User;
import com.shroggle.logic.user.UsersGroupManager;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import net.sourceforge.stripes.mock.MockHttpSession;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class ManageRegistratnsActionTest {

    @Before
    public void before() {
        action.setContext(new ActionBeanContext());
        action.getContext().setRequest(new MockHttpServletRequest("", "") {
            public HttpSession getSession() {
                return session;
            }
            private final HttpSession session = new MockHttpSession(action.getContext().getServletContext());
        });
    }

    @Test
    public void testShow() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final Group group1 = TestUtil.createGroup("group1", site);
        final Group group2 = TestUtil.createGroup("group2", site);
        final Group group3 = TestUtil.createGroup("group3", site);


        new UsersGroupManager(user).addAccessToGroup(group1);
        new UsersGroupManager(user).addAccessToGroup(group3);

        action.setSiteId(site.getSiteId());

        action.show();

        Assert.assertEquals(site.getTitle(), action.getSiteName());
        final List<Group> groups = (List<Group>)action.getContext().getRequest().getAttribute("availableGroups");
        Assert.assertEquals(3, groups.size());
        Assert.assertEquals(true, groups.contains(group1));
        Assert.assertEquals(true, groups.contains(group2));
        Assert.assertEquals(true, groups.contains(group3));
    }

    @Test
    public void testShow_withoutGroups() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        action.setSiteId(site.getSiteId());

        action.show();

        Assert.assertEquals(site.getTitle(), action.getSiteName());
        final List<Group> groups = (List<Group>)action.getContext().getRequest().getAttribute("availableGroups");//action.getAvailableGroups();
        Assert.assertEquals(0, groups.size());
    }


    private final ManageRegistratnsAction action = new ManageRegistratnsAction();
}
