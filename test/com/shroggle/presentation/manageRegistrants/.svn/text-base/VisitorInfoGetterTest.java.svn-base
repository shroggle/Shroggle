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
import com.shroggle.entity.*;
import com.shroggle.logic.user.UsersGroupManager;
import com.shroggle.logic.visitor.VisitorInfoGetter;
import com.shroggle.presentation.site.RegisteredVisitorInfo;
import com.shroggle.util.DateUtil;
import com.shroggle.util.TimeInterval;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class VisitorInfoGetterTest {

    @Test
    public void testExecute() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftForm form = TestUtil.createRegistrationForm(site.getSiteId());
        final FilledForm filledForm = TestUtil.createFilledForm(form);

        final Date fillDate = new Date();
        final User user = TestUtil.createVisitorForSite(site, false, VisitorStatus.REGISTERED, filledForm);
        user.setFirstName("fn");
        user.setLastName("ln");
        user.setEmail("e");
        final Date creationDate = new Date();
        user.setRegistrationDate(creationDate);

        final Group group1 = TestUtil.createGroup("group1", site);
        final Group group2 = TestUtil.createGroup("group2", site);
        final Group group3 = TestUtil.createGroup("group3", site);
        final Group group4 = TestUtil.createGroup("group4", site);
        final Group group5 = TestUtil.createGroup("group5", site);
        final Group group6 = TestUtil.createGroup("group6", TestUtil.createSite());

        new UsersGroupManager(user).addAccessToGroup(group1);
        new UsersGroupManager(user).addAccessToGroup(group2);
        new UsersGroupManager(user).addAccessToGroup(group3, TimeInterval.ONE_MONTH);
        new UsersGroupManager(user).addAccessToGroup(group4);
        new UsersGroupManager(user).addAccessToGroup(group5);
        new UsersGroupManager(user).addAccessToGroup(group6);

        RegisteredVisitorInfo registeredVisitorInfo = new VisitorInfoGetter().execute(user, site.getSiteId());
        Assert.assertEquals("User First Name", registeredVisitorInfo.getFirstName());
        Assert.assertEquals("User Last Name", registeredVisitorInfo.getLastName());
        Assert.assertEquals("User Email Address", registeredVisitorInfo.getEmail());
        final Date expirationDate = new Date(System.currentTimeMillis() + TimeInterval.ONE_MONTH.getMillis());
        Assert.assertEquals("group1,<br> group2,<br> group3 (till " + DateUtil.toMonthDayAndYear(expirationDate) + "),<br> group4,<br> group5", registeredVisitorInfo.getGroupsNames());

        Assert.assertEquals(DateUtil.toCommonDateStr(creationDate), registeredVisitorInfo.getCreated());
        Assert.assertEquals(DateUtil.toCommonDateStr(fillDate), registeredVisitorInfo.getUpdated());
        Assert.assertEquals(VisitorInfoGetter.getManageRegistrantsInternational().get(VisitorStatus.REGISTERED + "_STATUS"),
                registeredVisitorInfo.getStatus());
        Assert.assertEquals(form.getName(), registeredVisitorInfo.getForms().get(0).getName());
        Assert.assertEquals((int) user.getUserId(), registeredVisitorInfo.getVisitorId());
    }

}
