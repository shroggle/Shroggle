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
package com.shroggle.presentation.account.dashboard;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.ChildSiteSettings;
import com.shroggle.entity.Site;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.exception.ChildSiteSettingsNotFoundException;
import com.shroggle.presentation.MockWebContext;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import junit.framework.Assert;

/**
 * @author Balakirev Anatoliy
 *         Date: 07.08.2009
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class ShowNetworkSettingsInfoServiceTest {

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void testExecute() throws Exception {
        TestUtil.createUserAndLogin("email@email.com");
        ChildSiteSettings settings1 = TestUtil.createChildSiteSettings(new Site(), new Site());
        TestUtil.createChildSiteSettings(new Site(), new Site());
        TestUtil.createChildSiteSettings(new Site(), new Site());

        service.execute(settings1.getChildSiteSettingsId());
        ChildSiteSettings newSettings = (ChildSiteSettings) service.getContext().getHttpServletRequest().getAttribute("settings");
        Assert.assertNotNull(newSettings);
        Assert.assertEquals(settings1.getChildSiteSettingsId(), newSettings.getChildSiteSettingsId());
    }

    @Test(expected = UserNotLoginedException.class)
    public void testExecute_withoutLoginedUser() throws Exception {
        service.execute(1);
    }

    @Test(expected = ChildSiteSettingsNotFoundException.class)
    public void testExecute_withoutSettings() throws Exception {
        TestUtil.createUserAndLogin("email@email.com");
        service.execute(1);
    }

    private final ShowNetworkSettingsInfoService service = new ShowNetworkSettingsInfoService();
}
