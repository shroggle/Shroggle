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
package com.shroggle.logic;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balakirev Anatoliy, dmitry.solomadin
 *         Date: 11.09.2009
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class SiteItemManagerTest {

    @Test
    public void testIsChildSiteRegistration() {
        Assert.assertTrue(new ItemManager(new DraftChildSiteRegistration()).isChildSiteRegistration());
        Assert.assertFalse(new ItemManager(new DraftBlog()).isChildSiteRegistration());
        Assert.assertFalse(new ItemManager(new DraftForum()).isChildSiteRegistration());
        Assert.assertFalse(new ItemManager(new DraftGallery()).isChildSiteRegistration());
    }

    @Test
    public void testShare(){
        final User user = TestUtil.createUser();
        final Site sourceSite = TestUtil.createSite();
        final Site targetSite = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, sourceSite);
        TestUtil.createUserOnSiteRightActiveAdmin(user, targetSite);

        final DraftItem draftItem = TestUtil.createCustomForm(sourceSite);

        new ItemManager(draftItem).share(targetSite, SiteOnItemRightType.EDIT);
        final SiteOnItem createdRights = persistance.getSiteOnItemRightBySiteIdItemIdAndType(targetSite.getSiteId(),
                draftItem.getId(), ItemType.CUSTOM_FORM);
        Assert.assertNotNull(createdRights);
    }

    @Test
    public void testShareWithOwner(){
        final User user = TestUtil.createUser();
        final Site sourceSite = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, sourceSite);

        final DraftItem draftItem = TestUtil.createCustomForm(sourceSite);

        new ItemManager(draftItem).share(sourceSite, SiteOnItemRightType.EDIT);
        final SiteOnItem createdRights = persistance.getSiteOnItemRightBySiteIdItemIdAndType(sourceSite.getSiteId(),
                draftItem.getId(), ItemType.CUSTOM_FORM);
        Assert.assertNull(createdRights);
    }

    @Test
    public void testShareWithOtherTightsType(){
        final User user = TestUtil.createUser();
        final Site sourceSite = TestUtil.createSite();
        final Site targetSite = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, sourceSite);
        TestUtil.createUserOnSiteRightActiveAdmin(user, targetSite);

        final DraftItem draftItem = TestUtil.createCustomForm(sourceSite);

        new ItemManager(draftItem).share(targetSite, SiteOnItemRightType.READ);
        final SiteOnItem createdRights = persistance.getSiteOnItemRightBySiteIdItemIdAndType(targetSite.getSiteId(),
                draftItem.getId(), ItemType.CUSTOM_FORM);
        Assert.assertNotNull(createdRights);
        Assert.assertEquals(SiteOnItemRightType.READ, createdRights.getType());

        new ItemManager(draftItem).share(targetSite, SiteOnItemRightType.EDIT);
        Assert.assertEquals(SiteOnItemRightType.EDIT, createdRights.getType());
    }
    
    private final Persistance persistance = ServiceLocator.getPersistance();

}
