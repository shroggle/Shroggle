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
import com.shroggle.entity.AccessForRender;
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.logic.site.page.PageManager;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Old version this class has 779 lines, new version after use pattern Builder use 281 + 143 = 424 lines
 *
 * @author Balakirev Anatoliy, Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class AccessibleForRenderManagerTest {

    @Test
    public void testIsAccessibleForRender() {
        // Add your code here
    }

    /*-------------------------------------------Check Element Accessibility------------------------------------------*/

    @Test
    public void testIsAccessibleForRender_checkWidget_AdminOnlyInWidget_withLoginedUser() {
        Assert.assertTrue(new AccessibleForRenderManagerHelper(AccessForRender.RESTRICTED,
                SiteAccessLevel.ADMINISTRATOR, false).create().isAccessibleForRender());
    }

    @Test
    public void testIsAccessibleForRender_checkWidget_AdminOnlyInWidget_withoutLoginedUser() {
        final AccessibleForRenderManager manager = new AccessibleForRenderManagerHelper(AccessForRender.RESTRICTED, null, false).create();
        Assert.assertFalse(manager.isAccessibleForRender());
    }

    @Test
    public void testIsAccessibleForRender_checkWidget_AdminAndAllVisitorInWidget_withLoginedUser() {
        Assert.assertTrue(new AccessibleForRenderManagerHelper(AccessForRender.RESTRICTED,
                SiteAccessLevel.ADMINISTRATOR, true).create().isAccessibleForRender());
    }

    @Test
    public void testIsAccessibleForRender_checkWidget_AdminAndAllVisitorInWidget_withoutLoginedUser() {
        final AccessibleForRenderManagerHelper helper = new AccessibleForRenderManagerHelper(
                AccessForRender.RESTRICTED, null, true);
        Assert.assertFalse(helper.create().isAccessibleForRender());
    }

    @Test
    public void testIsAccessibleForRender_checkWidget_AdminAndAllVisitorInWidget_withVisitor() {
        Assert.assertTrue(new AccessibleForRenderManagerHelper(AccessForRender.RESTRICTED,
                SiteAccessLevel.VISITOR, true).create().isAccessibleForRender());
    }

    @Test
    public void testIsAccessibleForRender_checkWidget_AdminAndGroupVisitorsInWidget_withVisitorWithAccessToGroup() {
        final AccessibleForRenderManagerHelper helper = new AccessibleForRenderManagerHelper(
                AccessForRender.RESTRICTED, SiteAccessLevel.VISITOR, true, 112312, 32413);
        helper.setCreateGroup(true);
        helper.setUseCreatedGroup(true);
        Assert.assertTrue(helper.create().isAccessibleForRender());
    }

    @Test
    public void testIsAccessibleForRender_checkWidget_AdminAndGroupVisitorsInWidget_withVisitorWithoutAccessToGroup() {
        final AccessibleForRenderManagerHelper helper = new AccessibleForRenderManagerHelper(
                AccessForRender.RESTRICTED, SiteAccessLevel.VISITOR, true, 312411, 21342, 312343);
        helper.setCreateGroup(true);
        Assert.assertFalse(helper.create().isAccessibleForRender());
    }

    @Test
    public void testIsAccessibleForRender_checkWidget_AllVisitorInWidget_withVisitor() {
        final AccessibleForRenderManagerHelper helper = new AccessibleForRenderManagerHelper(
                AccessForRender.RESTRICTED, SiteAccessLevel.VISITOR, true);
        helper.setWidgetAdministrators(false);
        Assert.assertTrue(helper.create().isAccessibleForRender());
    }

    @Test
    public void testIsAccessibleForRender_checkWidget_AllVisitorInWidget_withoutVisitorWithLoginedUser() {
        final AccessibleForRenderManagerHelper helper = new AccessibleForRenderManagerHelper(
                AccessForRender.RESTRICTED, SiteAccessLevel.ADMINISTRATOR, true);
        helper.setWidgetAdministrators(false);
        Assert.assertFalse(helper.create().isAccessibleForRender());
    }

    @Test
    public void testIsAccessibleForRender_checkWidget_GroupVisitorInWidget_withVisitor() {
        final AccessibleForRenderManagerHelper helper = new AccessibleForRenderManagerHelper(
                AccessForRender.RESTRICTED, SiteAccessLevel.VISITOR, true);
        helper.setWidgetAdministrators(false);
        helper.setUseCreatedGroup(true);
        helper.setCreateGroup(true);
        helper.setVisitorGroupIds(3241, 334213);
        Assert.assertTrue(helper.create().isAccessibleForRender());
    }

    @Test
    public void testIsAccessibleForRender_checkWidget_GroupVisitorInWidget_withVisitor_withoutAccessToGroup() {
        final AccessibleForRenderManagerHelper helper = new AccessibleForRenderManagerHelper(
                AccessForRender.RESTRICTED, SiteAccessLevel.VISITOR, true);
        helper.setWidgetAdministrators(false);
        helper.setVisitorGroupIds(1, 2, 3);
        Assert.assertFalse(helper.create().isAccessibleForRender());
    }

    @Test
    public void testIsAccessibleForRender_checkWidget_GroupVisitorInWidget_withVisitorAndLogindAdmin_withoutAccessToGroup() {
        final AccessibleForRenderManagerHelper helper = new AccessibleForRenderManagerHelper(
                AccessForRender.RESTRICTED, SiteAccessLevel.VISITOR, true);
        helper.setWidgetAdministrators(false);
        helper.setCreateSecondUser(SiteAccessLevel.ADMINISTRATOR);
        helper.setVisitorGroupIds(1, 2, 3);
        Assert.assertFalse(helper.create().isAccessibleForRender());
    }
    /*-------------------------------------------Check Element Accessibility------------------------------------------*/


    /*-------------------------------Check Element Accessibility Check Tree Accessibility-----------------------------*/

    @Test
    public void testIsAccessibleForRender_checkWidget_AdminOnlyInWidget() {
        final AccessibleForRenderManagerHelper helper = new AccessibleForRenderManagerHelper(
                AccessForRender.RESTRICTED, SiteAccessLevel.ADMINISTRATOR, false);
        Assert.assertTrue(helper.create().isAccessibleForRender());
    }

    @Test
    public void testIsAccessibleForRender_checkWidget_AdminOnlyInWidget_withoutLoginedAdmin() {
        final AccessibleForRenderManagerHelper helper = new AccessibleForRenderManagerHelper(
                AccessForRender.RESTRICTED, SiteAccessLevel.ADMINISTRATOR, false);
        helper.setLoginCreatedUser(false);
        Assert.assertFalse(helper.create().isAccessibleForRender());
    }

    @Test
    public void testIsAccessibleForRender_checkWidget_AdminOnlyInPageVersion() {
        final AccessibleForRenderManagerHelper helper = new AccessibleForRenderManagerHelper(
                AccessForRender.UNLIMITED, SiteAccessLevel.ADMINISTRATOR, false);
        helper.setPageAccess(AccessForRender.RESTRICTED);
        helper.setPageAdministrators(true);
        Assert.assertTrue(helper.create().isAccessibleForRender());
    }

    @Test
    public void testIsAccessibleForRender_checkWidget_AdminOnlyInPageVersion_withoutLoginedAdmin() {
        final AccessibleForRenderManagerHelper helper = new AccessibleForRenderManagerHelper(
                AccessForRender.UNLIMITED, SiteAccessLevel.ADMINISTRATOR, false);
        helper.setPageAccess(AccessForRender.RESTRICTED);
        helper.setPageAdministrators(true);
        helper.setLoginCreatedUser(false);
        Assert.assertFalse(helper.create().isAccessibleForRender());
    }

    @Test
    public void testIsAccessibleForRender_checkWidget_AdminOnlyInSite() {
        final AccessibleForRenderManagerHelper helper = new AccessibleForRenderManagerHelper(
                AccessForRender.UNLIMITED, SiteAccessLevel.ADMINISTRATOR, false);
        helper.setSiteAccess(AccessForRender.RESTRICTED);
        helper.setSiteAdministrators(true);
        Assert.assertTrue(helper.create().isAccessibleForRender());
    }

    @Test
    public void testIsAccessibleForRender_checkWidget_AdminOnlyInSite_withoutLoginedAdmin() {
        final AccessibleForRenderManagerHelper helper = new AccessibleForRenderManagerHelper(
                AccessForRender.UNLIMITED, SiteAccessLevel.ADMINISTRATOR, false);
        helper.setSiteAccess(AccessForRender.RESTRICTED);
        helper.setSiteAdministrators(true);
        helper.setLoginCreatedUser(false);
        Assert.assertFalse(helper.create().isAccessibleForRender());
    }

    /*-------------------------------Check Element Accessibility Check Tree Accessibility-----------------------------*/


    /*------------------------------------Check Widget Accessibility (Admin only)-------------------------------------*/

    @Test
    public void testIsOnlyAdminsHasAccess_checkWidget_AdminOnlyInWidget() {
        final AccessibleForRenderManagerHelper helper = new AccessibleForRenderManagerHelper(
                AccessForRender.RESTRICTED, null, false);
        Assert.assertTrue(helper.create().isOnlyAdminsHasAccess());
    }

    @Test
    public void testIsOnlyAdminsHasAccess_checkWidget_AdminOnlyInPageVersion() {
        final AccessibleForRenderManagerHelper helper = new AccessibleForRenderManagerHelper(
                AccessForRender.UNLIMITED, null, false);
        helper.setPageAccess(AccessForRender.RESTRICTED);
        helper.setPageAdministrators(true);
        Assert.assertTrue(helper.create().isOnlyAdminsHasAccess());
    }

    @Test
    public void testIsOnlyAdminsHasAccess_checkWidget_AdminOnlyInSite() {
        final AccessibleForRenderManagerHelper helper = new AccessibleForRenderManagerHelper(
                AccessForRender.UNLIMITED, null, false);
        helper.setSiteAccess(AccessForRender.RESTRICTED);
        helper.setSiteAdministrators(true);
        Assert.assertTrue(helper.create().isOnlyAdminsHasAccess());
    }
    /*------------------------------------Check Widget Accessibility (Admin only)-------------------------------------*/


    /*-------------------------------------Check Page Accessibility (Admin only)--------------------------------------*/

    @Test
    public void testIsOnlyAdminsHasAccess_checkPage_AdminOnlyInWidget() {
        final AccessibleForRenderManagerHelper helper = new AccessibleForRenderManagerHelper(
                AccessForRender.RESTRICTED, null, false);
        helper.setManagerFor(PageManager.class);
        Assert.assertFalse(helper.create().isOnlyAdminsHasAccess());
    }

    @Test
    public void testIsOnlyAdminsHasAccess_checkPage_AdminOnlyInPageVersion() {
        final AccessibleForRenderManagerHelper helper = new AccessibleForRenderManagerHelper(
                AccessForRender.UNLIMITED, null, false);
        helper.setPageAccess(AccessForRender.RESTRICTED);
        helper.setPageAdministrators(true);
        helper.setManagerFor(PageManager.class);
        Assert.assertTrue(helper.create().isOnlyAdminsHasAccess());
    }

    @Test
    public void testIsOnlyAdminsHasAccess_checkPage_AdminOnlyInSite() {
        final AccessibleForRenderManagerHelper helper = new AccessibleForRenderManagerHelper(
                AccessForRender.UNLIMITED, null, false);
        helper.setSiteAccess(AccessForRender.RESTRICTED);
        helper.setSiteAdministrators(true);
        helper.setManagerFor(PageManager.class);
        Assert.assertTrue(helper.create().isOnlyAdminsHasAccess());
    }
    /*-------------------------------------Check Page Accessibility (Admin only)--------------------------------------*/


    /*-------------------------------------Check Site Accessibility (Admin only)--------------------------------------*/

    @Test
    public void testIsOnlyAdminsHasAccess_checkSite_AdminOnlyInWidget() {
        final AccessibleForRenderManagerHelper helper = new AccessibleForRenderManagerHelper(
                AccessForRender.RESTRICTED, null, false);
        helper.setManagerFor(Site.class);
        Assert.assertFalse(helper.create().isOnlyAdminsHasAccess());
    }

    @Test
    public void testIsOnlyAdminsHasAccess_checkSite_AdminOnlyInPageVersion() {
        final AccessibleForRenderManagerHelper helper = new AccessibleForRenderManagerHelper(
                AccessForRender.UNLIMITED, null, false);
        helper.setPageAccess(AccessForRender.RESTRICTED);
        helper.setPageAdministrators(true);
        helper.setManagerFor(Site.class);
        Assert.assertFalse(helper.create().isOnlyAdminsHasAccess());
    }

    @Test
    public void testIsOnlyAdminsHasAccess_checkSite_AdminOnlyInSite() {
        final AccessibleForRenderManagerHelper helper = new AccessibleForRenderManagerHelper(
                AccessForRender.UNLIMITED, null, false);
        helper.setSiteAccess(AccessForRender.RESTRICTED);
        helper.setSiteAdministrators(true);
        helper.setManagerFor(Site.class);
        Assert.assertTrue(helper.create().isOnlyAdminsHasAccess());
    }
    /*-------------------------------------Check Site Accessibility (Admin only)--------------------------------------*/

}