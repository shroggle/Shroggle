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
package com.shroggle.logic.site;

import org.junit.Test;
import org.junit.runner.RunWith;
import com.shroggle.entity.*;
import com.shroggle.TestUtil;
import com.shroggle.TestRunnerWithMockServices;
import junit.framework.Assert;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class AccessGroupManagerTest {

    interface A{

    }
    class B implements A{

    }

    class C{

        void put(A a){

        }

        void asd(){
            put(new B());
        }

    }

    @Test
    public void testIsHasRight_AccessGroupALL_userWithoutRights() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();

        Assert.assertTrue(AccessGroupManager.isUserFitsForAccessGroup(AccessGroup.ALL, user, site));
    }

    @Test
    public void testIsHasRight_AccessGroupALL_userWithVisitorRights() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.VISITOR);

        Assert.assertTrue(AccessGroupManager.isUserFitsForAccessGroup(AccessGroup.ALL, user, site));
    }

    @Test
    public void testIsHasRight_AccessGroupALL_userWithGuestRights() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.GUEST);

        Assert.assertTrue(AccessGroupManager.isUserFitsForAccessGroup(AccessGroup.ALL, user, site));
    }

    @Test
    public void testIsHasRight_AccessGroupALL_userWithOwnerRights() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        Assert.assertTrue(AccessGroupManager.isUserFitsForAccessGroup(AccessGroup.ALL, user, site));
    }

    @Test
    public void testIsHasRight_AccessGroupVISITORS_userWithoutRights() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();

        Assert.assertFalse(AccessGroupManager.isUserFitsForAccessGroup(AccessGroup.VISITORS, user, site));
    }

    @Test
    public void testIsHasRight_AccessGroupVISITORS_userWithVisitorRights() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.VISITOR);

        Assert.assertTrue(AccessGroupManager.isUserFitsForAccessGroup(AccessGroup.VISITORS, user, site));
    }

    @Test
    public void testIsHasRight_AccessGroupVISITORS_userWithGuestRights() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.GUEST);

        Assert.assertTrue(AccessGroupManager.isUserFitsForAccessGroup(AccessGroup.VISITORS, user, site));
    }

    @Test
    public void testIsHasRight_AccessGroupVISITORS_userWithOwnerRights() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        Assert.assertTrue(AccessGroupManager.isUserFitsForAccessGroup(AccessGroup.VISITORS, user, site));
    }

    @Test
    public void testIsHasRight_AccessGroupGUEST_userWithoutRights() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();

        Assert.assertFalse(AccessGroupManager.isUserFitsForAccessGroup(AccessGroup.GUEST, user, site));
    }

    @Test
    public void testIsHasRight_AccessGroupGUEST_userWithVisitorRights() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.VISITOR);

        Assert.assertFalse(AccessGroupManager.isUserFitsForAccessGroup(AccessGroup.GUEST, user, site));
    }

    @Test
    public void testIsHasRight_AccessGroupGUEST_userWithGuestRights() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.GUEST);

        Assert.assertTrue(AccessGroupManager.isUserFitsForAccessGroup(AccessGroup.GUEST, user, site));
    }

    @Test
    public void testIsHasRight_AccessGroupGUEST_userWithOwnerRights() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        Assert.assertTrue(AccessGroupManager.isUserFitsForAccessGroup(AccessGroup.GUEST, user, site));
    }

    @Test
    public void testIsHasRight_AccessGroupOWNER_userWithoutRights() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();

        Assert.assertFalse(AccessGroupManager.isUserFitsForAccessGroup(AccessGroup.OWNER, user, site));
    }

    @Test
    public void testIsHasRight_AccessGroupOWNER_userWithVisitorRights() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.VISITOR);

        Assert.assertFalse(AccessGroupManager.isUserFitsForAccessGroup(AccessGroup.OWNER, user, site));
    }

    @Test
    public void testIsHasRight_AccessGroupOWNER_userWithGuestRights() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.GUEST);

        Assert.assertFalse(AccessGroupManager.isUserFitsForAccessGroup(AccessGroup.OWNER, user, site));
    }

    @Test
    public void testIsHasRight_AccessGroupOWNER_userWithOwnerRights() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        Assert.assertTrue(AccessGroupManager.isUserFitsForAccessGroup(AccessGroup.OWNER, user, site));
    }

    @Test
    public void testIsHasRight_AccessGroupALL_userWithPendingRights() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final UserOnSiteRight rights = TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.VISITOR);
        rights.setVisitorStatus(VisitorStatus.PENDING);

        Assert.assertTrue(AccessGroupManager.isUserFitsForAccessGroup(AccessGroup.ALL, user, site));
    }

    @Test
    public void testIsHasRight_AccessGroupALL_userWithExpiredRights() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final UserOnSiteRight rights = TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.VISITOR);
        rights.setVisitorStatus(VisitorStatus.EXPIRED);

        Assert.assertTrue(AccessGroupManager.isUserFitsForAccessGroup(AccessGroup.ALL, user, site));
    }

    @Test
    public void testIsHasRight_AccessGroupALL_userWithNotActiveRights() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final UserOnSiteRight rights = TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.VISITOR);
        rights.setActive(false);

        Assert.assertTrue(AccessGroupManager.isUserFitsForAccessGroup(AccessGroup.ALL, user, site));
    }

    @Test
    public void testIsHasRight_AccessGroupOTHER_THEN_ALL_userWithPendingRights() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final UserOnSiteRight rights = TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.VISITOR);
        rights.setVisitorStatus(VisitorStatus.PENDING);

        Assert.assertFalse(AccessGroupManager.isUserFitsForAccessGroup(AccessGroup.OWNER, user, site));
    }

    @Test
    public void testIsHasRight_AccessGroupOTHER_THEN_ALL_userWithExpiredRights() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final UserOnSiteRight rights = TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.VISITOR);
        rights.setVisitorStatus(VisitorStatus.EXPIRED);

        Assert.assertFalse(AccessGroupManager.isUserFitsForAccessGroup(AccessGroup.OWNER, user, site));
    }

    @Test
    public void testIsHasRight_AccessGroupOTHER_THEN_ALL_userWithNotActiveRights() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final UserOnSiteRight rights = TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.VISITOR);
        rights.setActive(false);

        Assert.assertFalse(AccessGroupManager.isUserFitsForAccessGroup(AccessGroup.OWNER, user, site));
    }

}
