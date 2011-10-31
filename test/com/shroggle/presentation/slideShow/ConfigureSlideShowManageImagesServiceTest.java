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
import com.shroggle.entity.DraftSlideShow;
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.SlideShowImage;
import com.shroggle.exception.SlideShowImageNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class ConfigureSlideShowManageImagesServiceTest {

    @Test
    public void testRemoveImage() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftSlideShow slideShow = TestUtil.createSlideShow(site);

        final SlideShowImage slideShowImage = TestUtil.createSlideShowImage(slideShow);
        slideShowImage.setPosition(1);

        service.removeImage(slideShowImage.getSlideShowImageId());

        Assert.assertEquals(0, slideShow.getImages().size());
    }

    @Test(expected = SlideShowImageNotFoundException.class)
    public void testRemoveImageWithNotFoundImage() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        service.removeImage(-1);
    }

    @Test
    public void testMoveImageLeft() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftSlideShow slideShow = TestUtil.createSlideShow(site);

        final SlideShowImage slideShowImage1 = TestUtil.createSlideShowImage(slideShow);
        slideShowImage1.setPosition(0);

        final SlideShowImage slideShowImage2 = TestUtil.createSlideShowImage(slideShow);
        slideShowImage2.setPosition(12);

        final SlideShowImage slideShowImage3 = TestUtil.createSlideShowImage(slideShow);
        slideShowImage3.setPosition(54);

        final SlideShowImage slideShowImage4 = TestUtil.createSlideShowImage(slideShow);
        slideShowImage4.setPosition(155);

        final SlideShowImage slideShowImage5 = TestUtil.createSlideShowImage(slideShow);
        slideShowImage5.setPosition(351);

        service.moveImageLeft(slideShowImage3.getSlideShowImageId());

        Assert.assertEquals(5, slideShow.getImages().size());

        Assert.assertEquals(0, slideShowImage1.getPosition());
        Assert.assertEquals(54, slideShowImage2.getPosition());
        Assert.assertEquals(12, slideShowImage3.getPosition());
        Assert.assertEquals(155, slideShowImage4.getPosition());
        Assert.assertEquals(351, slideShowImage5.getPosition());
    }
    
    @Test
    public void testMoveImageLeftWithFirstImage() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftSlideShow slideShow = TestUtil.createSlideShow(site);

        final SlideShowImage slideShowImage1 = TestUtil.createSlideShowImage(slideShow);
        slideShowImage1.setPosition(0);

        final SlideShowImage slideShowImage2 = TestUtil.createSlideShowImage(slideShow);
        slideShowImage2.setPosition(12);

        final SlideShowImage slideShowImage3 = TestUtil.createSlideShowImage(slideShow);
        slideShowImage3.setPosition(54);

        final SlideShowImage slideShowImage4 = TestUtil.createSlideShowImage(slideShow);
        slideShowImage4.setPosition(155);

        final SlideShowImage slideShowImage5 = TestUtil.createSlideShowImage(slideShow);
        slideShowImage5.setPosition(351);

        service.moveImageLeft(slideShowImage1.getSlideShowImageId());

        Assert.assertEquals(5, slideShow.getImages().size());

        Assert.assertEquals(0, slideShowImage1.getPosition());
        Assert.assertEquals(12, slideShowImage2.getPosition());
        Assert.assertEquals(54, slideShowImage3.getPosition());
        Assert.assertEquals(155, slideShowImage4.getPosition());
        Assert.assertEquals(351, slideShowImage5.getPosition());
    }

    @Test(expected = SlideShowImageNotFoundException.class)
    public void testMoveImageLeftWithoutImage() {
        service.moveImageLeft(-1);
    }

    @Test
    public void testMoveImageRight() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftSlideShow slideShow = TestUtil.createSlideShow(site);

        final SlideShowImage slideShowImage1 = TestUtil.createSlideShowImage(slideShow);
        slideShowImage1.setPosition(0);

        final SlideShowImage slideShowImage2 = TestUtil.createSlideShowImage(slideShow);
        slideShowImage2.setPosition(12);

        final SlideShowImage slideShowImage3 = TestUtil.createSlideShowImage(slideShow);
        slideShowImage3.setPosition(54);

        final SlideShowImage slideShowImage4 = TestUtil.createSlideShowImage(slideShow);
        slideShowImage4.setPosition(155);

        final SlideShowImage slideShowImage5 = TestUtil.createSlideShowImage(slideShow);
        slideShowImage5.setPosition(351);

        service.moveImageRight(slideShowImage3.getSlideShowImageId());

        Assert.assertEquals(5, slideShow.getImages().size());

        Assert.assertEquals(0, slideShowImage1.getPosition());
        Assert.assertEquals(12, slideShowImage2.getPosition());
        Assert.assertEquals(155, slideShowImage3.getPosition());
        Assert.assertEquals(54, slideShowImage4.getPosition());
        Assert.assertEquals(351, slideShowImage5.getPosition());
    }

    @Test
    public void testMoveImageRightWithLastImage() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftSlideShow slideShow = TestUtil.createSlideShow(site);

        final SlideShowImage slideShowImage1 = TestUtil.createSlideShowImage(slideShow);
        slideShowImage1.setPosition(0);

        final SlideShowImage slideShowImage2 = TestUtil.createSlideShowImage(slideShow);
        slideShowImage2.setPosition(12);

        final SlideShowImage slideShowImage3 = TestUtil.createSlideShowImage(slideShow);
        slideShowImage3.setPosition(54);

        final SlideShowImage slideShowImage4 = TestUtil.createSlideShowImage(slideShow);
        slideShowImage4.setPosition(155);

        final SlideShowImage slideShowImage5 = TestUtil.createSlideShowImage(slideShow);
        slideShowImage5.setPosition(351);

        service.moveImageRight(slideShowImage5.getSlideShowImageId());

        Assert.assertEquals(5, slideShow.getImages().size());

        Assert.assertEquals(0, slideShowImage1.getPosition());
        Assert.assertEquals(12, slideShowImage2.getPosition());
        Assert.assertEquals(54, slideShowImage3.getPosition());
        Assert.assertEquals(155, slideShowImage4.getPosition());
        Assert.assertEquals(351, slideShowImage5.getPosition());
    }

    @Test(expected = SlideShowImageNotFoundException.class)
    public void testMoveImageRightWithoutImage() {
        service.moveImageRight(-1);
    }

    private ConfigureSlideShowManageImagesService service = new ConfigureSlideShowManageImagesService();

}
