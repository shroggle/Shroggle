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
package com.shroggle.presentation.account.dashboard.blueprintsPublishing;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.BlueprintCategory;
import com.shroggle.entity.Page;
import com.shroggle.entity.Site;
import com.shroggle.entity.User;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class ActivateBlueprintServiceTest {

    final ActivateBlueprintService service = new ActivateBlueprintService();

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Ignore// todo. Remove this annotation after SiteManager.copy() fixing. Tolik
    @Test
    public void testExecute() throws Exception {
        ServiceLocator.getConfigStorage().get().setAdminEmails(Arrays.asList("email1@email1.com", "email2@email2.com"));
        ServiceLocator.getConfigStorage().get().setApplicationUrl("http://www.shroggle.com");
        final User user = TestUtil.createUserAndLogin();
        user.setFirstName("Balakirev");
        user.setLastName("Anatoliy");
        user.setEmail("email@email.email");
        final Site site = TestUtil.createBlueprint();
        site.setTitle("Blueprint");
        site.getPublicBlueprintsSettings().setActivated(null);
        site.getPublicBlueprintsSettings().setPublished(null);
        site.getPublicBlueprintsSettings().setDescription(null);
        final Page page1 = TestUtil.createPage(site);
        final Page page2 = TestUtil.createPage(site);

        final Map<Integer, Integer> screenShotIds = new HashMap<Integer, Integer>();
        screenShotIds.put(page1.getPageId(), 1234);
        screenShotIds.put(page2.getPageId(), 5678);



        service.execute(site.getSiteId(), "", screenShotIds, BlueprintCategory.ALTERNATIVE_THERAPISTS);
        Assert.assertNotNull(ServiceLocator.getSessionStorage().getSelectedSiteInfoHashForDashboard(null));
    }

}
