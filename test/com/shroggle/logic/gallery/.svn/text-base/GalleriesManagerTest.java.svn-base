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
import com.shroggle.entity.*;
import com.shroggle.exception.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class GalleriesManagerTest {

    @Test
    public void createNew() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final GalleryManager galleryManager = new GalleriesManager().getNew(site.getSiteId());
        Assert.assertEquals(false, galleryManager.isModified());
    }

    @Test(expected = SiteNotFoundException.class)
    public void createNewWithNotMySite() {
        TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        new GalleriesManager().getNew(site.getSiteId());
    }

    @Test(expected = SiteNotFoundException.class)
    public void createNewWithNotFoundSite() {
        TestUtil.createUserAndLogin();
        new GalleriesManager().getNew(1);
    }

    @Test(expected = GalleryNotFoundException.class)
    public void createNotFound() {
        new GalleriesManager().get(1);
    }

    @Test
    public void createNotMy() {
        TestUtil.createUserAndLogin();
        final DraftGallery gallery = new DraftGallery();
        persistance.putItem(gallery);
        new GalleriesManager().get(gallery.getId());
    }

    @Test
    public void createForExist() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final DraftGallery gallery = new DraftGallery();
        gallery.setSiteId(site.getSiteId());
        persistance.putItem(gallery);

        Assert.assertEquals(gallery.getId(), new GalleriesManager().get(gallery.getId()).getId());
    }

    @Test
    public void testUpdateVideoFilesForGalleriesThatUseCurentForm() {
        Site site = TestUtil.createSite();
        DraftGallery gallery = TestUtil.createGallery(site);
        DraftForm form = TestUtil.createCustomForm(site);
        gallery.setFormId1(form.getFormId());
        DraftFormItem formItemVideo1 = TestUtil.createFormItem(FormItemName.VIDEO_FILE_UPLOAD, form, 0);
        DraftFormItem formItemVideo2 = TestUtil.createFormItem(FormItemName.VIDEO_FILE_UPLOAD, form, 1);
        DraftFormItem formItemImage = TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, form, 2);
        DraftFormItem formItemAudio = TestUtil.createFormItem(FormItemName.AUDIO_FILE_UPLOAD, form, 3);


        DraftGalleryItem galleryItemVideo1 = TestUtil.createGalleryItem(123123, 18645600, gallery, formItemVideo1.getFormItemId());
        DraftGalleryItem galleryItemVideo2 = TestUtil.createGalleryItem(8797853, 876153, gallery, formItemVideo2.getFormItemId());
        TestUtil.createGalleryItem(100, 100, gallery, formItemAudio.getFormItemId());
        TestUtil.createGalleryItem(100, 100, gallery, formItemImage.getFormItemId());


        /*------------------------------------filled form items for formItemVideo1------------------------------------*/
        FilledForm filledForm = TestUtil.createFilledForm(form);
        FilledFormItem filledFormItem1 = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem1.setFormItemId(formItemVideo1.getFormItemId());
        Video video = TestUtil.createVideoForSite("name", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), null);
        filledFormItem1.setValue("" + formVideo.getFormVideoId());
        filledFormItem1.setFilledForm(filledForm);
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem1));

        FilledForm filledForm2 = TestUtil.createFilledForm(form);
        FilledFormItem filledFormItem2 = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem2.setFormItemId(formItemVideo1.getFormItemId());
        Video video2 = TestUtil.createVideoForSite("name", site.getSiteId());
        FormVideo formVideo2 = TestUtil.createFormVideo(video2.getVideoId(), null);
        filledFormItem2.setValue("" + formVideo2.getFormVideoId());
        filledFormItem2.setFilledForm(filledForm2);
        filledForm2.setFilledFormItems(Arrays.asList(filledFormItem2));
        /*------------------------------------filled form items for formItemVideo1------------------------------------*/


        /*------------------------------------filled form items for formItemVideo2------------------------------------*/
        FilledForm filledForm3 = TestUtil.createFilledForm(form);
        FilledFormItem filledFormItem3 = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem3.setFormItemId(formItemVideo2.getFormItemId());
        Video video3 = TestUtil.createVideoForSite("name", site.getSiteId());
        FormVideo formVideo3 = TestUtil.createFormVideo(video3.getVideoId(), null);
        filledFormItem3.setValue("" + formVideo3.getFormVideoId());
        filledFormItem3.setFilledForm(filledForm3);
        filledForm3.setFilledFormItems(Arrays.asList(filledFormItem3));

        FilledForm filledForm4 = TestUtil.createFilledForm(form);
        FilledFormItem filledFormItem4 = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem4.setFormItemId(formItemVideo2.getFormItemId());
        Video video4 = TestUtil.createVideoForSite("name", site.getSiteId());
        FormVideo formVideo4 = TestUtil.createFormVideo(video4.getVideoId(), null);
        filledFormItem4.setValue("" + formVideo4.getFormVideoId());
        filledFormItem4.setFilledForm(filledForm4);
        filledForm4.setFilledFormItems(Arrays.asList(filledFormItem4));
        /*------------------------------------filled form items for formItemVideo2------------------------------------*/


        Assert.assertNull(persistance.getFlvVideo(video.getVideoId(), galleryItemVideo1.getWidth(), galleryItemVideo1.getHeight(), FlvVideo.DEFAULT_VIDEO_QUALITY));
        Assert.assertNull(persistance.getFlvVideo(video2.getVideoId(), galleryItemVideo1.getWidth(), galleryItemVideo1.getHeight(), FlvVideo.DEFAULT_VIDEO_QUALITY));
        Assert.assertNull(persistance.getFlvVideo(video3.getVideoId(), galleryItemVideo2.getWidth(), galleryItemVideo2.getHeight(), FlvVideo.DEFAULT_VIDEO_QUALITY));
        Assert.assertNull(persistance.getFlvVideo(video4.getVideoId(), galleryItemVideo2.getWidth(), galleryItemVideo2.getHeight(), FlvVideo.DEFAULT_VIDEO_QUALITY));



        GalleriesManager.updateVideoFilesForGalleriesThatUseCurentForm(form.getFormId());


        FlvVideo flvVideoForVideo1 = persistance.getFlvVideo(video.getVideoId(), galleryItemVideo1.getWidth(), galleryItemVideo1.getHeight(), FlvVideo.DEFAULT_VIDEO_QUALITY);
        FlvVideo flvVideoForVideo2 = persistance.getFlvVideo(video2.getVideoId(), galleryItemVideo1.getWidth(), galleryItemVideo1.getHeight(), FlvVideo.DEFAULT_VIDEO_QUALITY);
        FlvVideo flvVideoForVideo3 = persistance.getFlvVideo(video3.getVideoId(), galleryItemVideo2.getWidth(), galleryItemVideo2.getHeight(), FlvVideo.DEFAULT_VIDEO_QUALITY);
        FlvVideo flvVideoForVideo4 = persistance.getFlvVideo(video4.getVideoId(), galleryItemVideo2.getWidth(), galleryItemVideo2.getHeight(), FlvVideo.DEFAULT_VIDEO_QUALITY);

        Assert.assertNotNull(flvVideoForVideo1);
        Assert.assertNotNull(flvVideoForVideo2);
        Assert.assertNotNull(flvVideoForVideo3);
        Assert.assertNotNull(flvVideoForVideo4);


        Assert.assertEquals(galleryItemVideo1.getWidth(), flvVideoForVideo1.getWidth());
        Assert.assertEquals(galleryItemVideo1.getWidth(), flvVideoForVideo2.getWidth());
        Assert.assertEquals(galleryItemVideo1.getHeight(), flvVideoForVideo1.getHeight());
        Assert.assertEquals(galleryItemVideo1.getHeight(), flvVideoForVideo2.getHeight());

        Assert.assertEquals(galleryItemVideo2.getWidth(), flvVideoForVideo3.getWidth());
        Assert.assertEquals(galleryItemVideo2.getWidth(), flvVideoForVideo4.getWidth());
        Assert.assertEquals(galleryItemVideo2.getHeight(), flvVideoForVideo3.getHeight());
        Assert.assertEquals(galleryItemVideo2.getHeight(), flvVideoForVideo4.getHeight());


        Assert.assertEquals(video.getVideoId(), flvVideoForVideo1.getSourceVideoId());
        Assert.assertEquals(video2.getVideoId(), flvVideoForVideo2.getSourceVideoId());
        Assert.assertEquals(video3.getVideoId(), flvVideoForVideo3.getSourceVideoId());
        Assert.assertEquals(video4.getVideoId(), flvVideoForVideo4.getSourceVideoId());
    }

    private final Persistance persistance = ServiceLocator.getPersistance();

}