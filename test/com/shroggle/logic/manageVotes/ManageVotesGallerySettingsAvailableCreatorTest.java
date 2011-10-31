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
package com.shroggle.logic.manageVotes;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.manageVotes.GalleryWithWidgets;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class ManageVotesGallerySettingsAvailableCreatorTest {

    private final ManageVotesGallerySettingsAvailableCreator creator = new ManageVotesGallerySettingsAvailableCreator();

    @Test
    public void testGetAvailableBySite() {
        User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final Site site2 = TestUtil.createSite();
        final PageManager pageVersion2 = TestUtil.createPageVersionAndPage(site2);

        final WidgetItem widgetGallery1 = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGallery1.setCrossWidgetId(1008);
        final DraftGallery gallery1 = TestUtil.createGallery(site);
        widgetGallery1.setDraftItem(gallery1);
        gallery1.setIncludesVotingModule(true);

        final WidgetItem widgetGallery2 = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGallery2.setCrossWidgetId(1008);
        final DraftGallery gallery2 = TestUtil.createGallery(site);
        widgetGallery2.setDraftItem(gallery2);
        gallery2.setIncludesVotingModule(true);

        final WidgetItem widgetGalleryWithoutVoting = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGalleryWithoutVoting.setCrossWidgetId(1008);
        final DraftGallery galleryWithoutVoting = TestUtil.createGallery(site);
        widgetGalleryWithoutVoting.setDraftItem(galleryWithoutVoting);

        final WidgetItem widgetGalleryFromOtherSite = TestUtil.createWidgetGalleryForPageVersion(pageVersion2);
        widgetGalleryFromOtherSite.setCrossWidgetId(1008);
        final DraftGallery galleryFromOtherSite = TestUtil.createGallery(site);
        widgetGalleryFromOtherSite.setDraftItem(galleryFromOtherSite);
        galleryFromOtherSite.setIncludesVotingModule(true);

        final List<GalleryWithWidgets> returnList = creator.getAvailable(null, true, null, site.getSiteId(), SiteShowOption.getDraftOption());

        Assert.assertEquals(2, returnList.size());
        Assert.assertEquals(gallery1.getId(), returnList.get(0).getGallery().getId());
        Assert.assertEquals(gallery2.getId(), returnList.get(1).getGallery().getId());
    }

    @Test
    public void testGetAvailableByUser() {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final WidgetItem widgetGallery1 = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGallery1.setCrossWidgetId(1008);
        final DraftGallery gallery1 = TestUtil.createGallery(site);
        widgetGallery1.setDraftItem(gallery1);
        gallery1.setIncludesVotingModule(true);

        final WidgetItem widgetGallery2 = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGallery2.setCrossWidgetId(1008);
        final DraftGallery gallery2 = TestUtil.createGallery(site);
        widgetGallery2.setDraftItem(gallery2);
        gallery2.setIncludesVotingModule(true);

        final WidgetItem widgetGalleryFormOtherSite = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGalleryFormOtherSite.setCrossWidgetId(1008);
        final DraftGallery galleryFromOtherSite = TestUtil.createGallery(site);
        widgetGalleryFormOtherSite.setDraftItem(galleryFromOtherSite);
        galleryFromOtherSite.setIncludesVotingModule(true);

        final WidgetItem widgetGalleryWithoutVoting1 = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGalleryWithoutVoting1.setCrossWidgetId(1008);
        final DraftGallery galleryWithoutVoting1 = TestUtil.createGallery(site);
        widgetGalleryWithoutVoting1.setDraftItem(galleryWithoutVoting1);

        final WidgetItem widgetGalleryWithoutVoting2 = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGalleryWithoutVoting2.setCrossWidgetId(1008);
        final DraftGallery galleryWithoutVoting2 = TestUtil.createGallery(site);
        widgetGalleryWithoutVoting2.setDraftItem(galleryWithoutVoting2);

        final List<GalleryWithWidgets> returnList = creator.getAvailable(null, false, user.getUserId(), null, SiteShowOption.getDraftOption());

        Assert.assertEquals(3, returnList.size());
        Assert.assertEquals(gallery1.getId(), returnList.get(0).getGallery().getId());
        Assert.assertEquals("#FFFFFF", returnList.get(0).getManageVotesGallerySettings().getColorCode());
        Assert.assertEquals(gallery1.getName(), returnList.get(0).getManageVotesGallerySettings().getCustomName());
        Assert.assertEquals(gallery2.getId(), returnList.get(1).getGallery().getId());
        Assert.assertEquals(galleryFromOtherSite.getId(), returnList.get(2).getGallery().getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAvailableBySiteWithoutSiteId() {
        creator.getAvailable(null, true, null, null, SiteShowOption.getDraftOption());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAvailableByUserWithoutUserId() {
        creator.getAvailable(null, false, null, null, SiteShowOption.getDraftOption());
    }
}
