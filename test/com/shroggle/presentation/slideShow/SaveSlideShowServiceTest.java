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
package com.shroggle.presentation.slideShow;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.SlideShowNameNotUniqueException;
import com.shroggle.exception.SlideShowNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.site.FunctionalWidgetInfo;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class SaveSlideShowServiceTest {

    @Test
    public void executeFromSiteEditPage() throws Exception {
        final Site site = TestUtil.createSite();
        final PageManager page = TestUtil.createPageVersionAndPage(site);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftSlideShow slideShow = TestUtil.createSlideShow(site);

        final WidgetItem widget = TestUtil.createWidgetItem();
        widget.setDraftItem(slideShow);
        page.addWidget(widget);

        SaveSlideShowRequest request = new SaveSlideShowRequest();
        request.setName("new slide show name");
        request.setSlideShowId(slideShow.getId());
        request.setWidgetId(widget.getWidgetId());
        request.setNumberOfImagesShown(12);
        request.setTransitionEffectType(SlideShowTransitionEffectType.FADE_SLOW);
        request.setDisplayType(SlideShowDisplayType.CAROUSEL_HORIZONTAL);
        request.setImageWidth(100);
        request.setImageHeight(100);
        request.setDisplayControls(true);
        request.setAutoPlay(true);
        request.setAutoPlayInterval(10);

        final FunctionalWidgetInfo functionalWidgetInfo = service.execute(request);

        Assert.assertNotNull(functionalWidgetInfo);
        Assert.assertEquals(widget.getWidgetId(), functionalWidgetInfo.getWidget().getWidgetId());

        Assert.assertEquals("new slide show name", slideShow.getName());
        Assert.assertEquals(SlideShowType.MULTIPLE_IMAGES, slideShow.getSlideShowType());
        Assert.assertEquals(SlideShowTransitionEffectType.FADE_SLOW, slideShow.getTransitionEffectType());
        Assert.assertEquals(SlideShowDisplayType.CAROUSEL_HORIZONTAL, slideShow.getDisplayType());
        Assert.assertEquals(100, (int) slideShow.getImageWidth());
        Assert.assertEquals(100, (int) slideShow.getImageHeight());
        Assert.assertTrue(slideShow.isDisplayControls());
        Assert.assertTrue(slideShow.isAutoPlay());
        Assert.assertEquals(10, slideShow.getAutoPlayInterval());
    }

    @Test
    public void executeFromManageItems() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftSlideShow slideShow = TestUtil.createSlideShow(site);

        SaveSlideShowRequest request = new SaveSlideShowRequest();
        request.setName("new slide show name");
        request.setSlideShowId(slideShow.getId());
        request.setNumberOfImagesShown(12);
        request.setTransitionEffectType(SlideShowTransitionEffectType.FADE_SLOW);
        request.setDisplayType(SlideShowDisplayType.CAROUSEL_HORIZONTAL);
        request.setImageWidth(100);
        request.setImageHeight(100);
        request.setDisplayControls(true);
        request.setAutoPlay(true);
        request.setAutoPlayInterval(10);

        final FunctionalWidgetInfo functionalWidgetInfo = service.execute(request);

        Assert.assertNotNull(functionalWidgetInfo);
        Assert.assertNull(functionalWidgetInfo.getWidget());

        Assert.assertEquals("new slide show name", slideShow.getName());
        Assert.assertEquals(SlideShowType.MULTIPLE_IMAGES, slideShow.getSlideShowType());
        Assert.assertEquals(SlideShowTransitionEffectType.FADE_SLOW, slideShow.getTransitionEffectType());
        Assert.assertEquals(SlideShowDisplayType.CAROUSEL_HORIZONTAL, slideShow.getDisplayType());
        Assert.assertEquals(100, (int) slideShow.getImageWidth());
        Assert.assertEquals(100, (int) slideShow.getImageHeight());
        Assert.assertTrue(slideShow.isDisplayControls());
        Assert.assertTrue(slideShow.isAutoPlay());
        Assert.assertEquals(10, slideShow.getAutoPlayInterval());
    }

    @Test(expected = SlideShowNameNotUniqueException.class)
    public void executeWithDuplicateSlideShowName() throws Exception {
        final Site site = TestUtil.createSite();
        final PageManager page = TestUtil.createPageVersionAndPage(site);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftSlideShow slideShow = TestUtil.createSlideShow(site);
        slideShow.setName("bb");

        TestUtil.createSlideShow(site);

        final WidgetItem widget = TestUtil.createWidgetItem();
        widget.setDraftItem(slideShow);
        page.addWidget(widget);

        SaveSlideShowRequest request = new SaveSlideShowRequest();
        request.setName("aa");
        request.setSlideShowId(slideShow.getId());
        request.setNumberOfImagesShown(12);
        request.setTransitionEffectType(SlideShowTransitionEffectType.FADE_SLOW);
        request.setDisplayType(SlideShowDisplayType.CAROUSEL_HORIZONTAL);
        request.setImageWidth(100);
        request.setImageHeight(100);
        request.setDisplayControls(true);
        request.setAutoPlay(true);
        request.setAutoPlayInterval(10);

        service.execute(request);
    }

    @Test(expected = SlideShowNotFoundException.class)
    public void executeWithNotFoundSlideShow() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        SaveSlideShowRequest request = new SaveSlideShowRequest();
        request.setName("new slide show name");
        request.setSlideShowId(-1);
        request.setNumberOfImagesShown(12);
        request.setTransitionEffectType(SlideShowTransitionEffectType.FADE_SLOW);
        request.setDisplayType(SlideShowDisplayType.CAROUSEL_HORIZONTAL);
        request.setImageWidth(100);
        request.setImageHeight(100);
        request.setDisplayControls(true);
        request.setAutoPlay(true);
        request.setAutoPlayInterval(10);

        service.execute(request);
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLoginedUser() throws Exception {
        SaveSlideShowRequest request = new SaveSlideShowRequest();
        request.setName("new slide show name");
        request.setSlideShowId(-1);
        request.setNumberOfImagesShown(12);
        request.setTransitionEffectType(SlideShowTransitionEffectType.FADE_SLOW);
        request.setDisplayType(SlideShowDisplayType.CAROUSEL_HORIZONTAL);
        request.setImageWidth(100);
        request.setImageHeight(100);
        request.setDisplayControls(true);
        request.setAutoPlay(true);
        request.setAutoPlayInterval(10);

        service.execute(request);
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final SaveSlideShowService service = new SaveSlideShowService();

}
