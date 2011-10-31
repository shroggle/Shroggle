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
package com.shroggle.presentation.site;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.util.ServiceLocator;
import com.shroggle.entity.*;
import com.shroggle.presentation.MockWebContext;
import net.sourceforge.stripes.mock.MockHttpServletRequest;

import java.util.List;
import java.util.ArrayList;

import junit.framework.Assert;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class SearchChildSiteFilledFormsServiceTest {

    SearchChildSiteFilledFormsService service = new SearchChildSiteFilledFormsService();

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void testExecute() throws Exception {
        final Site networkSite = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(networkSite, SiteAccessLevel.ADMINISTRATOR);
        List<Integer> childSiteRegistrationsIds = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            final User user = TestUtil.createUser();
            DraftChildSiteRegistration reg = TestUtil.createChildSiteRegistration("name", "comment", networkSite);
            FilledForm childSiteRegistrationForm = TestUtil.createFilledChildSiteRegistrationFormForVisitor(user, reg.getFormId());
            if (i % 2 == 0) {
                childSiteRegistrationForm.getFilledFormItems().get(0).setValue("childSiteFirstName");
            }

            childSiteRegistrationsIds.add(reg.getFormId());
        }
        networkSite.setChildSiteRegistrationsId(childSiteRegistrationsIds);

        service.execute("childSiteFirstName", networkSite.getSiteId());

        List<FilledForm> filledForms = service.getFilledForms();
        Assert.assertNotNull(filledForms.size());
        Assert.assertEquals(5, filledForms.size());
        for (FilledForm filledForm : filledForms) {
            Assert.assertEquals("childSiteFirstName", filledForm.getFilledFormItems().get(0).getValues().get(0));
        }
    }

    @Test
    public void testExecute_searchByEmptyKey() throws Exception {
        final Site networkSite = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(networkSite, SiteAccessLevel.ADMINISTRATOR);
        List<Integer> childSiteRegistrationsIds = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            final User user = new User();
            ServiceLocator.getPersistance().putUser(user);
            DraftChildSiteRegistration reg = TestUtil.createChildSiteRegistration("name", "comment", networkSite);
            childSiteRegistrationsIds.add(reg.getFormId());
            FilledForm childSiteRegistrationForm = TestUtil.createFilledChildSiteRegistrationFormForVisitor(user, reg.getFormId());
            if (i % 2 == 0) {
                childSiteRegistrationForm.getFilledFormItems().get(0).setValue("childSiteFirstName 2");
            } else {
                childSiteRegistrationForm.getFilledFormItems().get(0).setValue("childSiteFirstName");
            }
        }
        networkSite.setChildSiteRegistrationsId(childSiteRegistrationsIds);

        service.execute("", networkSite.getSiteId());

        List<FilledForm> filledForms = service.getFilledForms();
        Assert.assertNotNull(filledForms.size());
        Assert.assertEquals(10, filledForms.size());
    }

    @Test(expected = UserNotLoginedException.class)
    public void testExecute_withoutLogin() throws Exception {
        service.execute("", 0);
    }

    @Test(expected = SiteNotFoundException.class)
    public void testExecute_withoutParentSite() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        service.execute("", 0);
    }
}
