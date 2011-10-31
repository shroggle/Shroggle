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
package com.shroggle.logic.accessibility;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.exception.WidgetRightsNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class UserSiteRightManagerTest {

    @Test(expected = SiteNotFoundException.class)
    public void getSiteForEditForNull() {
        final User user = TestUtil.createUser();
        new UserManager(user).getRight().getSiteRight().getSiteForEdit(null);
    }

    @Test(expected = SiteNotFoundException.class)
    public void getSiteForEditForNotExists() {
        final User user = TestUtil.createUser();
        new UserManager(user).getRight().getSiteRight().getSiteForEdit(1);
    }

    @Test
    public void getSiteForEdit() {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);

        Assert.assertEquals(site, new UserManager(user).getRight().getSiteRight().getSiteForEdit(site.getSiteId()).getSite());
    }

    @Test
    public void getSiteForEditWithEditorAccess() {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.EDITOR);

        Assert.assertEquals(site, new UserManager(user).getRight().getSiteRight().getSiteForEdit(site.getSiteId()).getSite());
    }

    @Test(expected = SiteNotFoundException.class)
    public void getSiteForEditWithVisitorAccess() {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.VISITOR);

        new UserManager(user).getRight().getSiteRight().getSiteForEdit(site.getSiteId());
    }

    @Test(expected = WidgetNotFoundException.class)
    public void getWidgetForEditWithNullId() {
        final User user = TestUtil.createUser();
        new UserRightManager(user).getSiteRight().getWidgetForEdit(null);
    }

    @Test
    public void getWidgetForEdit() {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final Widget widget = new WidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        final UserRightManager userRightManager = new UserRightManager(user);
        Assert.assertEquals(widget, userRightManager.getSiteRight().getWidgetForEdit(
                widget.getWidgetId()));
    }

    @Test(expected = WidgetRightsNotFoundException.class)
    public void getWidgetForEditWithRightView() {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.VISITOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final Widget widget = new WidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        new UserRightManager(user).getSiteRight().getWidgetForEdit(
                widget.getWidgetId());
    }

    @Test(expected = WidgetNotFoundException.class)
    public void getWidgetForEditNotFind() {
        final User user = TestUtil.createUser();
        final UserRightManager userRightManager = new UserRightManager(user);
        userRightManager.getSiteRight().getWidgetForEdit(1);
    }

    @Test
    public void testHasRightToDelete_withActiveAdmin() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        Assert.assertTrue(new UserSiteRightManager(new UserRightManager(user)).hasRightToDelete(site));
    }

    @Test
    public void testHasRightToDelete_withActiveVisitor() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.VISITOR);

        Assert.assertFalse(new UserSiteRightManager(new UserRightManager(user)).hasRightToDelete(site));
    }

    @Test
    public void testHasRightToDelete_withActiveEditor() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.EDITOR);

        Assert.assertFalse(new UserSiteRightManager(new UserRightManager(user)).hasRightToDelete(site));
    }

    @Test
    public void testHasRightToDelete_withActiveGuest() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.GUEST);

        Assert.assertFalse(new UserSiteRightManager(new UserRightManager(user)).hasRightToDelete(site));
    }

    @Test
    public void testHasRightToDelete_withoutRight() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();

        Assert.assertFalse(new UserSiteRightManager(new UserRightManager(user)).hasRightToDelete(site));
    }

    private final Persistance persistance = ServiceLocator.getPersistance();

}