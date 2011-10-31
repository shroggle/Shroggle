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

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.presentation.form.filter.ConfigureFormFilterService;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Author: dmitry.solomadin
 */
@RunWith(TestRunnerWithMockServices.class)
public class ConfigureFormFilterServiceTest {

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void showForNewAndWithoutForm() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createCustomForm(site.getSiteId(), "custom form");

        service.execute(null, null);
        Assert.assertNull(service.getSelectedForm());
        Assert.assertNull(service.getFormFilterLogic());
        Assert.assertTrue(!service.getForms().isEmpty());
    }

    @Test
    public void showForNewAndWithPredefinedForm() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final DraftCustomForm form = TestUtil.createCustomForm(site.getSiteId(), "custom form");

        service.execute(null, form.getFormId());
        Assert.assertEquals(form, service.getSelectedForm());
        Assert.assertNull(service.getFormFilterLogic());
        Assert.assertTrue(!service.getForms().isEmpty());
    }

    @Test
    public void showForEditAndWithPredefinedForm() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final DraftCustomForm form = TestUtil.createCustomForm(site.getSiteId(), "custom form");
        final DraftFormFilter formFilter = TestUtil.createFormFilter(form);

        service.execute(formFilter.getFormFilterId(), form.getFormId());
        Assert.assertNull(service.getSelectedForm());
        Assert.assertEquals(formFilter, service.getFormFilterLogic().getFormFilter());
        Assert.assertTrue(!service.getForms().isEmpty());
    }

    @Test
    public void showForEdit() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final DraftCustomForm form = TestUtil.createCustomForm(site.getSiteId(), "custom form");
        final DraftFormFilter formFilter = TestUtil.createFormFilter(form);

        service.execute(formFilter.getFormFilterId(), null);
        Assert.assertEquals(formFilter.getForm(), service.getSelectedForm());
        Assert.assertEquals(formFilter, service.getFormFilterLogic().getFormFilter());
        Assert.assertTrue(!service.getForms().isEmpty());
    }

    @Test(expected = UserNotLoginedException.class)
    public void showWithoutUser() throws IOException, ServletException {
        service.execute(null, null);
    }

    private final ConfigureFormFilterService service = new ConfigureFormFilterService();

}
