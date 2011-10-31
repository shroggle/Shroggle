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

import org.junit.Test;
import org.junit.runner.RunWith;
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.User;
import com.shroggle.entity.UserOnSiteRightId;
import com.shroggle.TestUtil;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.ServiceLocator;
import com.shroggle.exception.UserNotLoginedException;

import java.util.Arrays;

import junit.framework.Assert;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class UpdateInvitedVisitorStateServiceTest {

    final UpdateInvitedVisitorStateService service = new UpdateInvitedVisitorStateService();

    @Test
    public void testExecute() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final User userInvited = TestUtil.createVisitorForSite(site, true);
        final User userInvited2 = TestUtil.createVisitorForSite(site, true);
        final User userReg1 = TestUtil.createVisitorForSite(site);
        final User userReg2 = TestUtil.createVisitorForSite(site);

        service.execute(Arrays.asList(userReg1.getUserId(), userInvited.getUserId()),
                Arrays.asList(userReg2.getUserId(), userInvited2.getUserId()), site.getSiteId());
        Assert.assertTrue(persistance.getUserOnSiteRightById(new UserOnSiteRightId(userInvited, site)).isInvited());
        Assert.assertTrue(persistance.getUserOnSiteRightById(new UserOnSiteRightId(userReg1, site)).isInvited());
        Assert.assertFalse(persistance.getUserOnSiteRightById(new UserOnSiteRightId(userReg2, site)).isInvited());
        Assert.assertFalse(persistance.getUserOnSiteRightById(new UserOnSiteRightId(userInvited2, site)).isInvited());
    }

    @Test(expected = UserNotLoginedException.class)
    public void testExecuteWithoutLoginedUser() throws Exception {
        service.execute(null, null, 0);
    }

    private final Persistance persistance = ServiceLocator.getPersistance();

}
