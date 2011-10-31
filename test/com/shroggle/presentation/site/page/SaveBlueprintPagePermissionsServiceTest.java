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

package com.shroggle.presentation.site.page;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.SiteType;
import com.shroggle.exception.PageNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.site.page.SaveBlueprintPagePermissionsService;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stasuk Artem
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class SaveBlueprintPagePermissionsServiceTest {

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() {
        final PageManager pageVersion = TestUtil.createPageVersionPageAndSite();
        pageVersion.getPage().getSite().setType(SiteType.BLUEPRINT);

        service.execute(pageVersion.getPageId(), false, false, false);
    }

    @Test(expected = PageNotFoundException.class)
    public void executeWithoutPage() {
        TestUtil.createUserAndLogin();
        service.execute(-1, false, false, false);
    }

    @Test(expected = PageNotFoundException.class)
    public void executeWithOtherLogin() {
        final Site site = TestUtil.createSite();
        site.setType(SiteType.BLUEPRINT);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserAndLogin();

        service.execute(pageVersion.getPageId(), false, false, false);
    }

    @Test(expected = PageNotFoundException.class)
    public void executeWithNotBlueprint() {
        final Site site = TestUtil.createSite();
        site.setType(SiteType.COMMON);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        service.execute(pageVersion.getPageId(), false, false, false);
    }

    @Test
    public void execute() {
        final Site site = TestUtil.createSite();
        site.setType(SiteType.BLUEPRINT);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        service.execute(pageVersion.getPageId(), true, true, true);

        Assert.assertEquals(true, pageVersion.isBlueprintRequired());
        Assert.assertEquals(true, pageVersion.isBlueprintLocked());
        Assert.assertEquals(true, pageVersion.isBlueprintNotEditable());
    }

    private final SaveBlueprintPagePermissionsService service = new SaveBlueprintPagePermissionsService();

}