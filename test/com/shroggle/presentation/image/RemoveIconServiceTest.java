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
package com.shroggle.presentation.image;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.Icon;
import com.shroggle.entity.Site;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class RemoveIconServiceTest {

    RemoveIconService service = new RemoveIconService();

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void testRemove() throws Exception {
        TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        final Icon icon = TestUtil.createIcon();

        site.setIcon(icon);

        service.remove(icon.getIconId());

        Assert.assertNull(ServiceLocator.getPersistance().getIcon(icon.getIconId()));
        Assert.assertNull(site.getIcon());
    }

    @Test(expected = UserNotLoginedException.class)
    public void testRemove_withoutLoginedUser() throws Exception {
        final Site site = TestUtil.createSite();
        final Icon icon = TestUtil.createIcon();

        site.setIcon(icon);

        service.remove(icon.getIconId());
    }
}
