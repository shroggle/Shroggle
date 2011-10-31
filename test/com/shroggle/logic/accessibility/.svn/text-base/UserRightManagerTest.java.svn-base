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
import com.shroggle.exception.SiteOnItemRightNotFoundException;
import com.shroggle.exception.SiteOnItemRightOwnerException;
import com.shroggle.logic.user.UserManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class UserRightManagerTest {


    @Test
    public void testNetworkRightsCorrectData() {
        final Site parentSite = TestUtil.createSite();
        final Site childSite = TestUtil.createChildSite(parentSite);
        final DraftRegistrationForm networkRegistration = TestUtil.createRegistrationForm(parentSite);
        networkRegistration.setNetworkRegistration(true);
        final FilledForm filledNetworkRegistrationForm = TestUtil.createFilledForm(networkRegistration);
        filledNetworkRegistrationForm.setNetworkRegistration(true);
        final User visitor = TestUtil.createVisitorForSite(parentSite, false, VisitorStatus.REGISTERED, filledNetworkRegistrationForm);

        final UserOnSiteRight networkRights = new UserRightManager(visitor).getNetworkRight(childSite);
        Assert.assertNotNull(networkRights);
        Assert.assertEquals(SiteAccessLevel.VISITOR, networkRights.getSiteAccessType());
    }

    @Test
    public void testNetworkRightsWithAdminRightsOnParentSite() {
        final Site parentSite = TestUtil.createSite();
        final Site childSite = TestUtil.createChildSite(parentSite);
        final DraftRegistrationForm networkRegistration = TestUtil.createRegistrationForm(parentSite);
        final FilledForm filledNetworkRegistrationForm = TestUtil.createFilledForm(networkRegistration);
        filledNetworkRegistrationForm.setNetworkRegistration(true);
        TestUtil.createVisitorForSite(parentSite, false, VisitorStatus.REGISTERED, filledNetworkRegistrationForm);
        final User user = TestUtil.createUserAndUserOnSiteRight(parentSite);

        final UserOnSiteRight networkRights = new UserRightManager(user).getNetworkRight(childSite);
        Assert.assertNull(networkRights);
    }
    
    @Test
    public void testNetworkRightsWithFilledNotNetworkRegistrationForm_forOrdinarySite() {
        final Site parentSite = TestUtil.createSite();
        final Site ordinarySiteThatIsNotInNetwork = TestUtil.createSite();
        final DraftRegistrationForm networkRegistration = TestUtil.createRegistrationForm(parentSite);
        networkRegistration.setNetworkRegistration(true);
        final FilledForm filledNetworkRegistrationForm = TestUtil.createFilledForm(networkRegistration);
        final User visitor = TestUtil.createVisitorForSite(parentSite, false, VisitorStatus.REGISTERED, filledNetworkRegistrationForm);

        final UserOnSiteRight networkRights = new UserRightManager(visitor).getNetworkRight(ordinarySiteThatIsNotInNetwork);
        Assert.assertNull(networkRights);
    }

    @Test
    public void testNetworkRightsWithFilledNotNetworkRegistrationForm_forChildSite() {
        final Site parentSite = TestUtil.createSite();
        final Site childSite = TestUtil.createChildSite(parentSite);
        final DraftRegistrationForm networkRegistration = TestUtil.createRegistrationForm(parentSite);
        networkRegistration.setNetworkRegistration(true);
        final FilledForm filledNetworkRegistrationForm = TestUtil.createFilledForm(networkRegistration);
        final User visitor = TestUtil.createVisitorForSite(parentSite, false, VisitorStatus.REGISTERED, filledNetworkRegistrationForm);

        final UserOnSiteRight networkRights = new UserRightManager(visitor).getNetworkRight(childSite);
        Assert.assertNotNull(networkRights);
        Assert.assertEquals(SiteAccessLevel.VISITOR, networkRights.getSiteAccessType());
    }

    @Test
    public void testNetworkRightsWithoutRightsOnParentSite() {
        final Site parentSite = TestUtil.createSite();
        final Site parentSite2 = TestUtil.createSite();
        final Site childSite = TestUtil.createChildSite(parentSite);
        final DraftRegistrationForm networkRegistration = TestUtil.createRegistrationForm(parentSite);
        networkRegistration.setNetworkRegistration(true);
        final FilledForm filledNetworkRegistrationForm = TestUtil.createFilledForm(networkRegistration);
        filledNetworkRegistrationForm.setNetworkRegistration(true);
        final User visitor = TestUtil.createVisitorForSite(parentSite2, false, VisitorStatus.REGISTERED, filledNetworkRegistrationForm);

        final UserOnSiteRight networkRights = new UserRightManager(visitor).getNetworkRight(childSite);
        Assert.assertNull(networkRights);
    }

    @Test
    public void testNetworkRightsWithoutForm() {
        final Site parentSite = TestUtil.createSite();
        final Site childSite = TestUtil.createChildSite(parentSite);
        final DraftRegistrationForm networkRegistration = TestUtil.createRegistrationForm(parentSite);
        networkRegistration.setNetworkRegistration(true);
        final FilledForm filledNetworkRegistrationForm = TestUtil.createFilledForm(networkRegistration);
        filledNetworkRegistrationForm.setNetworkRegistration(true);
        final User visitor = TestUtil.createVisitorForSite(parentSite, false, VisitorStatus.REGISTERED, filledNetworkRegistrationForm);

        persistance.removeDraftItem(networkRegistration);
        final UserOnSiteRight networkRights = new UserRightManager(visitor).getNetworkRight(childSite);
        Assert.assertNotNull(networkRights);
        Assert.assertEquals(SiteAccessLevel.VISITOR, networkRights.getSiteAccessType());
    }
    
    @Test
    public void getSiteItemsForViewByUserOverForGalleries() {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final DraftGallery gallery = new DraftGallery();
        persistance.putItem(gallery);

        final SiteOnItem siteOnItemRight = gallery.createSiteOnItemRight(site);
        siteOnItemRight.setAcceptDate(new Date());
        persistance.putSiteOnItem(siteOnItemRight);

        final UserRightManager userRightManager = new UserManager(user).getRight();
        final List<DraftItem> galleries = userRightManager.getSiteItemsForView(ItemType.GALLERY);
        Assert.assertEquals(1, galleries.size());
        Assert.assertEquals(gallery, galleries.get(0));
    }

    @Test
    public void getSiteItemsForViewByUserForGalleries() {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final DraftGallery gallery = new DraftGallery();
        gallery.setSiteId(site.getSiteId());
        persistance.putItem(gallery);

        final UserRightManager userRightManager = new UserManager(user).getRight();
        final List<DraftItem> galleries = userRightManager.getSiteItemsForView(ItemType.GALLERY);
        Assert.assertEquals(1, galleries.size());
        Assert.assertEquals(gallery, galleries.get(0));
    }

    @Test
    public void getSiteItemsForViewByUserOverWithout() {
        final User user = TestUtil.createUser();

        final DraftBlog blog1 = new DraftBlog();
        blog1.setName("g");
        persistance.putItem(blog1);

        final UserRightManager userRightManager = new UserManager(user).getRight();
        final List<DraftItem> blogs = userRightManager.getSiteItemsForView(ItemType.BLOG);
        Assert.assertEquals(0, blogs.size());
    }

    @Test
    public void toSiteForNull() {
        final User user = TestUtil.createUser();
        final UserRightManager userRightManager = new UserRightManager(user);

        Assert.assertNull(userRightManager.toSite(null));
    }

    @Test
    public void toSiteWithoutRight() {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final UserRightManager userRightManager = new UserRightManager(user);

        Assert.assertNull(userRightManager.toSite(site));
    }

    @Test
    public void toSite() {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.VISITOR);
        final UserRightManager userRightManager = new UserRightManager(user);

        Assert.assertEquals(SiteAccessLevel.VISITOR, userRightManager.toSite(site).getSiteAccessType());
    }

    @Test
    public void toSiteWithoutActive() {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final UserOnSiteRight userOnSiteRight = TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.VISITOR);
        userOnSiteRight.setActive(false);
        final UserRightManager userRightManager = new UserRightManager(user);

        Assert.assertNull(userRightManager.toSite(site));
    }

    @Test
    public void toSiteWithDeactiveUser() {
        final User user = TestUtil.createUser();
        user.setActiveted(null);
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.VISITOR);
        final UserRightManager userRightManager = new UserRightManager(user);

        Assert.assertNull(userRightManager.toSite(site));
    }

    @Test
    public void toSiteWithoutRegistrationUser() {
        final User user = TestUtil.createUser();
        user.setRegistrationDate(null);
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.VISITOR);
        final UserRightManager userRightManager = new UserRightManager(user);

        Assert.assertNull(userRightManager.toSite(site));
    }

    @Test
    public void toSiteItemForNull() {
        final User user = TestUtil.createUser();
        final UserRightManager userRightManager = new UserRightManager(user);

        Assert.assertNull(userRightManager.toSiteItem(null));
    }

    @Test
    public void toSiteItemForForeignUser() {
        final User user = TestUtil.createUser();
        final UserRightManager userRightManager = new UserRightManager(user);
        final DraftBlog blog = new DraftBlog();
        persistance.putItem(blog);

        Assert.assertNull(userRightManager.toSiteItem(blog));
    }

    @Test
    public void toSiteItemBetweenBlogAndOwnerSite() {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUser();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final UserRightManager userRightManager = new UserRightManager(user);
        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        persistance.putItem(blog);

        Assert.assertEquals(SiteOnItemRightType.EDIT, userRightManager.toSiteItem(blog));
    }

    @Test
    public void toSiteItemBetweenBlogAndOwnerSiteNotActiveUserOnSiteRight() {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUser();
        final UserOnSiteRight userOnSiteRight =
                TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        userOnSiteRight.setActive(false);
        final UserRightManager userRightManager = new UserRightManager(user);
        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        persistance.putItem(blog);

        Assert.assertNull(userRightManager.toSiteItem(blog));
    }

    @Test
    public void toSiteItemBetweenBlogAndOwnerSiteNotActive() {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUser();
        user.setActiveted(null);
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final UserRightManager userRightManager = new UserRightManager(user);
        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        persistance.putItem(blog);

        Assert.assertNull(userRightManager.toSiteItem(blog));
    }

    @Test
    public void toSiteItemBetweenBlogAndSiteOver() {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUser();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final UserRightManager userRightManager = new UserRightManager(user);
        final DraftBlog blog = new DraftBlog();
        persistance.putItem(blog);

        final SiteOnItem siteOnItemRight = blog.createSiteOnItemRight(site);
        siteOnItemRight.setAcceptDate(new Date());
        siteOnItemRight.setType(SiteOnItemRightType.EDIT);
        persistance.putSiteOnItem(siteOnItemRight);

        Assert.assertEquals(SiteOnItemRightType.EDIT, userRightManager.toSiteItem(blog));
    }

    @Test
    public void toSiteItemBetweenBlogAndSiteOverRead() {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUser();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final UserRightManager userRightManager = new UserRightManager(user);
        final DraftBlog blog = new DraftBlog();
        persistance.putItem(blog);

        final SiteOnItem siteOnItemRight = blog.createSiteOnItemRight(site);
        siteOnItemRight.setAcceptDate(new Date());
        siteOnItemRight.setType(SiteOnItemRightType.READ);
        persistance.putSiteOnItem(siteOnItemRight);

        Assert.assertEquals(SiteOnItemRightType.READ, userRightManager.toSiteItem(blog));
    }

    @Test
    public void toSiteItemBetweenBlogAndSiteOverNotActive() {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUser();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final UserRightManager userRightManager = new UserRightManager(user);
        final DraftBlog blog = new DraftBlog();
        persistance.putItem(blog);

        final SiteOnItem siteOnItemRight = blog.createSiteOnItemRight(site);
        siteOnItemRight.setAcceptDate(null);
        siteOnItemRight.setType(SiteOnItemRightType.EDIT);
        persistance.putSiteOnItem(siteOnItemRight);

        Assert.assertNull(userRightManager.toSiteItem(blog));
    }

    @Test(expected = SiteOnItemRightNotFoundException.class)
    public void getSiteOnItemRightForEditByNullSiteItemId() {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final UserRightManager userRightManager = new UserRightManager(user);
        userRightManager.getSiteOnItemRightForEdit(null, site.getSiteId(), ItemType.BLOG);
    }

    @Test(expected = SiteOnItemRightNotFoundException.class)
    public void getSiteOnItemRightForEditByNullSiteId() {
        final User user = TestUtil.createUser();
        final DraftBlog blog = new DraftBlog();
        persistance.putItem(blog);
        final UserRightManager userRightManager = new UserRightManager(user);
        userRightManager.getSiteOnItemRightForEdit(blog.getId(), null, ItemType.BLOG);
    }

    @Test(expected = SiteOnItemRightNotFoundException.class)
    public void getSiteOnItemRightForEditByNullType() {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final DraftBlog blog = new DraftBlog();
        persistance.putItem(blog);
        final UserRightManager userRightManager = new UserRightManager(user);
        userRightManager.getSiteOnItemRightForEdit(blog.getId(), null, ItemType.BLOG);
    }

    @Test(expected = SiteOnItemRightOwnerException.class)
    public void getSiteOnItemRightForEditForOwnerOnOwner() {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        persistance.putItem(blog);
        final UserRightManager userRightManager = new UserRightManager(user);
        userRightManager.getSiteOnItemRightForEdit(blog.getId(), site.getSiteId(), ItemType.BLOG);
    }

    @Test
    public void getSiteOnItemRightForEdit() {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        final DraftBlog blog = new DraftBlog();
        persistance.putItem(blog);

        final SiteOnItem siteOnItemRight = blog.createSiteOnItemRight(site);
        siteOnItemRight.setAcceptDate(new Date());
        persistance.putSiteOnItem(siteOnItemRight);

        final UserRightManager userRightManager = new UserRightManager(user);
        final SiteOnItem findSiteOnItemRight = userRightManager.getSiteOnItemRightForEdit(
                blog.getId(), site.getSiteId(), ItemType.BLOG);

        Assert.assertEquals(siteOnItemRight, findSiteOnItemRight);
    }

    @Test
    public void getSiteOnItemRightForEditOwner() {
        final User user = TestUtil.createUser();
        final Site owner = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, owner, SiteAccessLevel.ADMINISTRATOR);

        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(owner.getSiteId());
        persistance.putItem(blog);

        final Site site = TestUtil.createSite();
        final SiteOnItem siteOnItemRight = blog.createSiteOnItemRight(site);
        siteOnItemRight.setAcceptDate(new Date());
        persistance.putSiteOnItem(siteOnItemRight);

        final UserRightManager userRightManager = new UserRightManager(user);
        final SiteOnItem findSiteOnItemRight = userRightManager.getSiteOnItemRightForEdit(
                blog.getId(), site.getSiteId(), ItemType.BLOG);

        Assert.assertEquals(siteOnItemRight, findSiteOnItemRight);
    }

    @Test(expected = SiteOnItemRightNotFoundException.class)
    public void getSiteOnItemRightForEditWithRightView() {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.VISITOR);

        final DraftBlog blog = new DraftBlog();
        persistance.putItem(blog);

        final SiteOnItem siteOnItemRight = blog.createSiteOnItemRight(site);
        siteOnItemRight.setType(SiteOnItemRightType.READ);
        siteOnItemRight.setAcceptDate(new Date());
        persistance.putSiteOnItem(siteOnItemRight);

        final UserRightManager userRightManager = new UserRightManager(user);
        userRightManager.getSiteOnItemRightForEdit(blog.getId(), site.getSiteId(), ItemType.BLOG);
    }

    @Test(expected = SiteOnItemRightNotFoundException.class)
    public void getSiteOnItemRightForEditWithInactiveRight() {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final UserOnSiteRight userOnSiteRight = TestUtil.createUserOnSiteRightActive(
                user, site, SiteAccessLevel.ADMINISTRATOR);
        userOnSiteRight.setActive(false);

        final DraftBlog blog = new DraftBlog();
        persistance.putItem(blog);

        final SiteOnItem siteOnItemRight = blog.createSiteOnItemRight(site);
        siteOnItemRight.setType(SiteOnItemRightType.READ);
        siteOnItemRight.setAcceptDate(new Date());
        persistance.putSiteOnItem(siteOnItemRight);

        final UserRightManager userRightManager = new UserRightManager(user);
        userRightManager.getSiteOnItemRightForEdit(blog.getId(), site.getSiteId(), ItemType.BLOG);
    }

    @Test(expected = SiteOnItemRightNotFoundException.class)
    public void getSiteOnItemRightForEditWithoutUserOnSiteRight() {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();

        final DraftBlog blog = new DraftBlog();
        persistance.putItem(blog);

        final SiteOnItem siteOnItemRight = blog.createSiteOnItemRight(site);
        siteOnItemRight.setType(SiteOnItemRightType.READ);
        siteOnItemRight.setAcceptDate(new Date());
        persistance.putSiteOnItem(siteOnItemRight);

        final UserRightManager userRightManager = new UserRightManager(user);
        userRightManager.getSiteOnItemRightForEdit(blog.getId(), site.getSiteId(), ItemType.BLOG);
    }

    @Test(expected = SiteOnItemRightNotFoundException.class)
    public void getSiteOnItemRightForEditForNotAcceptedRight() {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        final DraftBlog blog = new DraftBlog();
        persistance.putItem(blog);

        final SiteOnItem siteOnItemRight = blog.createSiteOnItemRight(site);
        siteOnItemRight.setAcceptDate(null);
        persistance.putSiteOnItem(siteOnItemRight);

        final UserRightManager userRightManager = new UserRightManager(user);
        userRightManager.getSiteOnItemRightForEdit(blog.getId(), site.getSiteId(), ItemType.BLOG);
    }

    @Test
    public void getSiteOnItemRightForEditForNotAcceptedRightForOwner() {
        final User user = TestUtil.createUser();
        final Site ownerSite = TestUtil.createSite();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, ownerSite, SiteAccessLevel.ADMINISTRATOR);

        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(ownerSite.getSiteId());
        persistance.putItem(blog);

        final SiteOnItem siteOnItemRight = blog.createSiteOnItemRight(site);
        siteOnItemRight.setAcceptDate(null);
        persistance.putSiteOnItem(siteOnItemRight);

        final UserRightManager userRightManager = new UserRightManager(user);
        Assert.assertEquals(siteOnItemRight, userRightManager.getSiteOnItemRightForEdit(
                blog.getId(), site.getSiteId(), ItemType.BLOG));
    }

    @Test
    public void createUserOnSiteRight_notFromNetwork() {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();

        final UserRightManager userRightManager = new UserRightManager(user);

        Assert.assertNull(userRightManager.toSite(site));

        userRightManager.createUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR, false);

        Assert.assertEquals(SiteAccessLevel.ADMINISTRATOR, userRightManager.toSite(site).getSiteAccessType());
        UserOnSiteRight right = userRightManager.getValidUserOnSiteRight(site.getSiteId());
        Assert.assertFalse(right.isFromNetwork());
        Assert.assertTrue(right.isActive());
    }

    @Test
    public void createUserOnSiteRight_fromNetwork() {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();

        final UserRightManager userRightManager = new UserRightManager(user);

        Assert.assertNull(userRightManager.toSite(site));

        userRightManager.createUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR, true);

        Assert.assertEquals(SiteAccessLevel.ADMINISTRATOR, userRightManager.toSite(site).getSiteAccessType());
        UserOnSiteRight right = userRightManager.getValidUserOnSiteRight(site.getSiteId());
        Assert.assertTrue(right.isFromNetwork());
        Assert.assertTrue(right.isActive());
    }

    @Test
    public void testIsUserOnSiteRightValid() {
        UserOnSiteRight userOnSiteRight = new UserOnSiteRight();
        userOnSiteRight.setActive(true);
        userOnSiteRight.setVisitorStatus(VisitorStatus.REGISTERED);
        Assert.assertTrue(UserRightManager.isUserOnSiteRightValid(userOnSiteRight));
    }
    
    @Test
    public void testIsUserOnSiteRightValid_withoutRights() {
        Assert.assertFalse(UserRightManager.isUserOnSiteRightValid(null));
    }


    @Test
    public void testIsUserOnSiteRightValid_PENDING() {
        UserOnSiteRight userOnSiteRight = new UserOnSiteRight();
        userOnSiteRight.setActive(true);
        userOnSiteRight.setVisitorStatus(VisitorStatus.PENDING);
        Assert.assertFalse(UserRightManager.isUserOnSiteRightValid(userOnSiteRight));
    }

    @Test
    public void testIsUserOnSiteRightValid_EXPIRED() {
        UserOnSiteRight userOnSiteRight = new UserOnSiteRight();
        userOnSiteRight.setActive(true);
        userOnSiteRight.setVisitorStatus(VisitorStatus.EXPIRED);
        Assert.assertFalse(UserRightManager.isUserOnSiteRightValid(userOnSiteRight));
    }

    @Test
    public void testIsUserOnSiteRightValid_notActive() {
        UserOnSiteRight userOnSiteRight = new UserOnSiteRight();
        userOnSiteRight.setActive(false);
        userOnSiteRight.setVisitorStatus(VisitorStatus.REGISTERED);
        Assert.assertFalse(UserRightManager.isUserOnSiteRightValid(userOnSiteRight));
    }

    private final Persistance persistance = ServiceLocator.getPersistance();

}
