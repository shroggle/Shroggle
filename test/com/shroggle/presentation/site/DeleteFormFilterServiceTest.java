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
import com.shroggle.exception.FormFilterNotFoundException;
import com.shroggle.presentation.form.filter.DeleteFormFilterService;
import com.shroggle.entity.*;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Author: dmitry.solomadin
 */
@RunWith(TestRunnerWithMockServices.class)
public class DeleteFormFilterServiceTest {

    @Test
    public void execute() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final DraftCustomForm form = TestUtil.createCustomForm(site.getSiteId(), "custom form");

        final DraftFormFilter formFilter = TestUtil.createFormFilter(
                form, "name", new DraftFormFilterRule());

        service.execute(formFilter.getFormFilterId());

        Assert.assertNull(persistance.getFormFilterById(formFilter.getFormFilterId()));
    }

    @Test
    public void executeForNotMy() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);
        final DraftCustomForm form = TestUtil.createCustomForm(site.getSiteId(), "custom form");
        TestUtil.createUserAndLogin();

        final DraftFormFilter formFilter = TestUtil.createFormFilter(
                form, "name", new DraftFormFilterRule());

        service.execute(formFilter.getFormFilterId());

        Assert.assertNotNull(persistance.getFormFilterById(formFilter.getFormFilterId()));
    }

    @Test(expected = FormFilterNotFoundException.class)
    public void executeNotExisting() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        service.execute(0);
    }

    @Test(expected = UserNotLoginedException.class)
    public void deleteFormFilterWithoutUser() {
        service.execute(0);
    }

    private final DeleteFormFilterService service = new DeleteFormFilterService();
    private final Persistance persistance = ServiceLocator.getPersistance();

}