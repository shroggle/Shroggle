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

package com.shroggle.logic.gallery;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.DraftGallery;
import com.shroggle.entity.Site;
import com.shroggle.entity.User;
import com.shroggle.entity.WidgetItem;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.manageVotes.GalleryWithWidgets;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class AvailableGalleriesWithWidgetsCreatorTest {

    private AvailableGalleriesWithWidgetsCreator creator = new AvailableGalleriesWithWidgetsCreator(true);

    @Test
    public void testCreateByWidgets_SIMPLE() {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final List<WidgetItem> widgets = new ArrayList<WidgetItem>();

        final WidgetItem widgetGallery1 = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGallery1.setCrossWidgetId(1008);
        final DraftGallery gallery1 = TestUtil.createGallery(site);
        widgetGallery1.setDraftItem(gallery1);
        gallery1.setIncludesVotingModule(true);
        widgets.add(widgetGallery1);

        final WidgetItem widgetGallery2 = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGallery2.setCrossWidgetId(1008);
        widgetGallery2.setDraftItem(gallery1);
        widgets.add(widgetGallery2);

        final List<GalleryWithWidgets> returnList = creator.createGalleriesWithWidgets(widgets);

        Assert.assertEquals(1, returnList.size());
        Assert.assertEquals(2, returnList.get(0).getWidgets().size());
        Assert.assertTrue(returnList.get(0).getWidgets().contains(widgetGallery1));
        Assert.assertTrue(returnList.get(0).getWidgets().contains(widgetGallery2));
    }

    @Test
    public void testCreateByWidgets_COMPLEX() {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final PageManager pageVersion1 = TestUtil.createPageVersionAndPage(site);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final List<WidgetItem> widgets = new ArrayList<WidgetItem>();

        final WidgetItem widgetGallery1 = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGallery1.setCrossWidgetId(1008);
        final DraftGallery gallery1 = TestUtil.createGallery(site);
        widgetGallery1.setDraftItem(gallery1);
        gallery1.setIncludesVotingModule(true);
        widgets.add(widgetGallery1);

        final WidgetItem widgetGallery2 = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGallery2.setCrossWidgetId(1008);
        final DraftGallery gallery2 = TestUtil.createGallery(site);
        widgetGallery2.setDraftItem(gallery2);
        gallery2.setIncludesVotingModule(true);
        widgets.add(widgetGallery2);

        final WidgetItem widgetGallery1OnAnotherPage = TestUtil.createWidgetGalleryForPageVersion(pageVersion1);
        widgetGallery1OnAnotherPage.setCrossWidgetId(1009);
        widgetGallery1OnAnotherPage.setDraftItem(gallery1);
        widgets.add(widgetGallery1OnAnotherPage);

        final List<GalleryWithWidgets> returnList = creator.createGalleriesWithWidgets(widgets);

        Assert.assertEquals(2, returnList.size());

        for (GalleryWithWidgets galleryWithWidgets : returnList) {
            if (galleryWithWidgets.getGallery().getId() == gallery1.getId()) {
                Assert.assertEquals(2, returnList.get(0).getWidgets().size());
                Assert.assertTrue(returnList.get(0).getWidgets().contains(widgetGallery1));
                Assert.assertTrue(returnList.get(0).getWidgets().contains(widgetGallery1OnAnotherPage));
            }

            if (galleryWithWidgets.getGallery().getId() == gallery1.getId()) {
                Assert.assertEquals(1, returnList.get(1).getWidgets().size());
                Assert.assertTrue(returnList.get(1).getWidgets().contains(widgetGallery2));
            }
        }
    }

}
