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
import com.shroggle.logic.video.FlvVideoManager;
import com.shroggle.logic.form.FilledFormItemManager;
import com.shroggle.util.Dimension;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class GalleryItemDataTest {

    /*-----------------------------------------------Normal video url-------------------------------------------------*/

    @Test
    public void testGetNormalVideoUrl() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValue("" + formVideo.getFormVideoId());
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId());
        galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(123123);
        galleryItem.setHeight(324213);
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), galleryItem.getWidth(), galleryItem.getHeight());
        flvVideo.setFlvVideoId(15321);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        Assert.assertEquals("/resourceGetter.action%3fresourceId=" + flvVideo.getFlvVideoId() + "%26resourceVersion=0%26resourceGetterType=FLV_VIDEO%26resourceDownload=false", galleryItemData.getNormalVideoUrl());
    }


    @Test
    public void testGetNormalVideoUrl_withoutFilledFormItem() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), 320, 240, formVideo.getQuality());
        flvVideo.setFlvVideoId(15321);

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItem.setId(galleryItemId);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        Assert.assertEquals("", galleryItemData.getNormalVideoUrl());
    }

    @Test
    public void testGetNormalVideoUrl_withWrongFilledFormItemType() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), 320, 240, formVideo.getQuality());
        flvVideo.setFlvVideoId(15321);

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.AUDIO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValue("" + formVideo.getFormVideoId());
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId());
        galleryItem.setId(galleryItemId);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        Assert.assertEquals("", galleryItemData.getNormalVideoUrl());
    }

    @Test
    public void testGetNormalVideoUrl_withEmptyFilledFormItemValues() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), 320, 240, formVideo.getQuality());
        flvVideo.setFlvVideoId(15321);

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValues(new ArrayList<String>());
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId());
        galleryItem.setId(galleryItemId);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        Assert.assertEquals("", galleryItemData.getNormalVideoUrl());
    }

    @Test
    public void testGetNormalVideoUrl_withoutFormVideo() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), 320, 240, formVideo.getQuality());
        flvVideo.setFlvVideoId(15321);

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValues(Arrays.asList("-1", "-1"));
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId());
        galleryItem.setId(galleryItemId);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        Assert.assertEquals("", galleryItemData.getNormalVideoUrl());
    }

    @Test
    public void testGetNormalVideoUrl_withoutFlvVideo() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValue("" + formVideo.getFormVideoId());
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId());
        galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        Assert.assertEquals("/resourceGetter.action%3fresourceId=1%26resourceVersion=0%26resourceGetterType=FLV_VIDEO%26resourceDownload=false", galleryItemData.getNormalVideoUrl());
    }
    /*-----------------------------------------------Normal video url-------------------------------------------------*/

    /*-----------------------------------------------Large video url--------------------------------------------------*/

    @Test
    public void testGetLargeVideoUrl() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());


        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValue("" + formVideo.getFormVideoId());
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId());
        galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(123123);
        galleryItem.setHeight(324213);
        final Dimension dimension = FlvVideoManager.createLargeVideoDimension(galleryItem.getWidth(), galleryItem.getHeight());
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), dimension.getWidth(), dimension.getHeight());
        flvVideo.setFlvVideoId(15321);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        Assert.assertEquals("/resourceGetter.action%3fresourceId=" + flvVideo.getFlvVideoId() + "%26resourceVersion=0%26resourceGetterType=FLV_VIDEO%26resourceDownload=false", galleryItemData.getLargeVideoUrl());
    }


    @Test
    public void testGetLargeVideoUrl_withoutFilledFormItem() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), 320, 240, formVideo.getQuality());
        flvVideo.setFlvVideoId(15321);

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItem.setId(galleryItemId);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        Assert.assertEquals("", galleryItemData.getLargeVideoUrl());
    }

    @Test
    public void testGetLargeVideoUrl_withWrongFilledFormItemType() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), 320, 240, formVideo.getQuality());
        flvVideo.setFlvVideoId(15321);

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.AUDIO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValue("" + formVideo.getFormVideoId());
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId());
        galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        Assert.assertEquals("", galleryItemData.getLargeVideoUrl());
    }

    @Test
    public void testGetLargeVideoUrl_withEmptyFilledFormItemValues() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), 320, 240, formVideo.getQuality());
        flvVideo.setFlvVideoId(15321);

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValues(new ArrayList<String>());
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId());
        galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        Assert.assertEquals("", galleryItemData.getLargeVideoUrl());
    }

    @Test
    public void testGetLargeVideoUrl_withoutFormVideo() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), 320, 240, formVideo.getQuality());
        flvVideo.setFlvVideoId(15321);

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValues(Arrays.asList("-1", "-1"));
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId());
        galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        Assert.assertEquals("", galleryItemData.getLargeVideoUrl());
    }

    @Test
    public void testGetLargeVideoUrl_withoutFlvVideo() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValue("" + formVideo.getFormVideoId());
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId());
        galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        Assert.assertEquals("/resourceGetter.action%3fresourceId=2%26resourceVersion=0%26resourceGetterType=FLV_VIDEO%26resourceDownload=false", galleryItemData.getLargeVideoUrl());
    }
    /*-----------------------------------------------Large video url--------------------------------------------------*/


    /*------------------------------------------------Video Image url-------------------------------------------------*/

    @Test
    public void testGetVideoImageUrl() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        image.setFormFileId(165456);
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValue("" + formVideo.getFormVideoId());
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId());
        galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        Assert.assertEquals("/resourceGetter.action%3fresourceId=165456%26resourceSizeId=0%26resourceSizeAdditionId=0%26resourceGetterType=FORM_FILE%26resourceVersion=0%26resourceDownload=false", galleryItemData.getVideoImageUrl());
    }


    @Test
    public void testGetVideoImageUrl_withoutFilledFormItem() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), 320, 240, formVideo.getQuality());
        flvVideo.setFlvVideoId(15321);

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItem.setId(galleryItemId);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        Assert.assertEquals("", galleryItemData.getVideoImageUrl());
    }

    @Test
    public void testGetVideoImageUrl_withWrongFilledFormItemType() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), 320, 240, formVideo.getQuality());
        flvVideo.setFlvVideoId(15321);

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.AUDIO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValue("" + formVideo.getFormVideoId());
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId());
        galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        Assert.assertEquals("", galleryItemData.getVideoImageUrl());
    }

    @Test
    public void testGetVideoImageUrl_withEmptyFilledFormItemValues() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), 320, 240, formVideo.getQuality());
        flvVideo.setFlvVideoId(15321);

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValues(new ArrayList<String>());
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId());
        galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        Assert.assertEquals("", galleryItemData.getVideoImageUrl());
    }

    @Test
    public void testGetVideoImageUrl_withoutFormVideo() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), 320, 240, formVideo.getQuality());
        flvVideo.setFlvVideoId(15321);

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValues(Arrays.asList("-1", "-1"));
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId());
        galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        Assert.assertEquals("", galleryItemData.getVideoImageUrl());
    }

    @Test
    public void testGetVideoImageUrl_withoutImage() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), null);

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValue("" + formVideo.getFormVideoId());
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId());
        galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        Assert.assertEquals("", galleryItemData.getVideoImageUrl());
    }
    /*------------------------------------------------Video Image url-------------------------------------------------*/


    /*----------------------------------------------Normal video width------------------------------------------------*/

    @Test
    public void testGetNormalVideoWidth() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        image.setFormFileId(165456);
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValue("" + formVideo.getFormVideoId());
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId());
        galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(213123);
        galleryItem.setHeight(56365);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        Assert.assertEquals(galleryItem.getWidth().intValue(), galleryItemData.getNormalVideoWidth());
    }


    @Test
    public void testGetNormalVideoWidth_withoutFilledFormItem() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), 320, 240, formVideo.getQuality());
        flvVideo.setFlvVideoId(15321);

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(213123);
        galleryItem.setHeight(56365);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        Assert.assertEquals(galleryItem.getWidth().intValue(), galleryItemData.getNormalVideoWidth());
    }

    @Test
    public void testGetNormalVideoWidth_withWrongFilledFormItemType() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), 320, 240, formVideo.getQuality());
        flvVideo.setFlvVideoId(15321);

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.AUDIO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValue("" + formVideo.getFormVideoId());
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId());
        galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(213123);
        galleryItem.setHeight(56365);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        Assert.assertEquals(galleryItem.getWidth().intValue(), galleryItemData.getNormalVideoWidth());
    }

    @Test
    public void testGetNormalVideoWidth_withEmptyFilledFormItemValues() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), 320, 240, formVideo.getQuality());
        flvVideo.setFlvVideoId(15321);

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValues(new ArrayList<String>());
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId());
        galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(213123);
        galleryItem.setHeight(56365);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        Assert.assertEquals(galleryItem.getWidth().intValue(), galleryItemData.getNormalVideoWidth());
    }

    @Test
    public void testGetNormalVideoWidth_withoutFormVideo() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), 320, 240, formVideo.getQuality());
        flvVideo.setFlvVideoId(15321);

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValues(Arrays.asList("-1", "-1"));
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId());
        galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(213123);
        galleryItem.setHeight(56365);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        Assert.assertEquals(galleryItem.getWidth().intValue(), galleryItemData.getNormalVideoWidth());
    }
    /*----------------------------------------------Normal video width------------------------------------------------*/

    /*----------------------------------------------Normal video height-----------------------------------------------*/

    @Test
    public void testGetNormalVideoHeight() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        image.setFormFileId(165456);
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValue("" + formVideo.getFormVideoId());
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId());
        galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(213123);
        galleryItem.setHeight(56365);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        Assert.assertEquals(galleryItem.getHeight().intValue(), galleryItemData.getNormalVideoHeight());
    }


    @Test
    public void testGetNormalVideoHeight_withoutFilledFormItem() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), 320, 240, formVideo.getQuality());
        flvVideo.setFlvVideoId(15321);

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(213123);
        galleryItem.setHeight(56365);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        Assert.assertEquals(galleryItem.getHeight().intValue(), galleryItemData.getNormalVideoHeight());
    }

    @Test
    public void testGetNormalVideoHeight_withWrongFilledFormItemType() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), 320, 240, formVideo.getQuality());
        flvVideo.setFlvVideoId(15321);

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.AUDIO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValue("" + formVideo.getFormVideoId());
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId());
        galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(213123);
        galleryItem.setHeight(56365);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        Assert.assertEquals(galleryItem.getHeight().intValue(), galleryItemData.getNormalVideoHeight());
    }

    @Test
    public void testGetNormalVideoHeight_withEmptyFilledFormItemValues() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), 320, 240, formVideo.getQuality());
        flvVideo.setFlvVideoId(15321);

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValues(new ArrayList<String>());
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId());
        galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(213123);
        galleryItem.setHeight(56365);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        Assert.assertEquals(galleryItem.getHeight().intValue(), galleryItemData.getNormalVideoHeight());
    }

    @Test
    public void testGetNormalVideoHeight_withoutFormVideo() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), 320, 240, formVideo.getQuality());
        flvVideo.setFlvVideoId(15321);

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValues(Arrays.asList("-1", "-1"));
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId());
        galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(213123);
        galleryItem.setHeight(56365);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        Assert.assertEquals(galleryItem.getHeight().intValue(), galleryItemData.getNormalVideoHeight());
    }
    /*----------------------------------------------Normal video height-----------------------------------------------*/

    /*----------------------------------------------Large video width-------------------------------------------------*/

    @Test
    public void testGetLargeVideoWidth() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        image.setFormFileId(165456);
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValue("" + formVideo.getFormVideoId());
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId()); galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(213123);
        galleryItem.setHeight(56365);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());
        final Dimension dimension = FlvVideoManager.createLargeVideoDimension(galleryItem.getWidth(), galleryItem.getHeight());
        Assert.assertEquals(dimension.getWidth(), galleryItemData.getLargeVideoWidth().intValue());
    }


    @Test
    public void testGetLargeVideoWidth_withoutFilledFormItem() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), 320, 240, formVideo.getQuality());
        flvVideo.setFlvVideoId(15321);

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(213123);
        galleryItem.setHeight(56365);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());
        final Dimension dimension = FlvVideoManager.createLargeVideoDimension(galleryItem.getWidth(), galleryItem.getHeight());
        Assert.assertEquals(dimension.getWidth(), galleryItemData.getLargeVideoWidth().intValue());
    }

    @Test
    public void testGetLargeVideoWidth_withWrongFilledFormItemType() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), 320, 240, formVideo.getQuality());
        flvVideo.setFlvVideoId(15321);

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.AUDIO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValue("" + formVideo.getFormVideoId());
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId()); galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(213123);
        galleryItem.setHeight(56365);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());
        final Dimension dimension = FlvVideoManager.createLargeVideoDimension(galleryItem.getWidth(), galleryItem.getHeight());
        Assert.assertEquals(dimension.getWidth(), galleryItemData.getLargeVideoWidth().intValue());
    }

    @Test
    public void testGetLargeVideoWidth_withEmptyFilledFormItemValues() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), 320, 240, formVideo.getQuality());
        flvVideo.setFlvVideoId(15321);

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValues(new ArrayList<String>());
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId()); galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(213123);
        galleryItem.setHeight(56365);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        final Dimension dimension = FlvVideoManager.createLargeVideoDimension(galleryItem.getWidth(), galleryItem.getHeight());
        Assert.assertEquals(dimension.getWidth(), galleryItemData.getLargeVideoWidth().intValue());
    }

    @Test
    public void testGetLargeVideoWidth_withoutFormVideo() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), 320, 240, formVideo.getQuality());
        flvVideo.setFlvVideoId(15321);

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValues(Arrays.asList("-1", "-1"));
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId()); galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(213123);
        galleryItem.setHeight(56365);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        final Dimension dimension = FlvVideoManager.createLargeVideoDimension(galleryItem.getWidth(), galleryItem.getHeight());
        Assert.assertEquals(dimension.getWidth(), galleryItemData.getLargeVideoWidth().intValue());
    }


    @Test
    public void testGetLargeVideoWidth_withoutItemWidth() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), 320, 240, formVideo.getQuality());
        flvVideo.setFlvVideoId(15321);

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValues(Arrays.asList("-1", "-1"));
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId()); galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(null);
        galleryItem.setHeight(56365);

        final Dimension dimension = FlvVideoManager.createLargeVideoDimension(galleryItem.getWidth(), galleryItem.getHeight());
        Assert.assertNull(dimension);
    }
    /*----------------------------------------------Large video width-------------------------------------------------*/


    /*----------------------------------------------Large video height------------------------------------------------*/

    @Test
    public void testGetLargeVideoHeight() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        image.setFormFileId(165456);
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValue("" + formVideo.getFormVideoId());
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId()); galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(213123);
        galleryItem.setHeight(56365);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        final Dimension dimension = FlvVideoManager.createLargeVideoDimension(galleryItem.getWidth(), galleryItem.getHeight());
        Assert.assertEquals(dimension.getHeight(), galleryItemData.getLargeVideoHeight().intValue());
    }


    @Test
    public void testGetLargeVideoHeight_withoutFilledFormItem() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), 320, 240, formVideo.getQuality());
        flvVideo.setFlvVideoId(15321);

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(213123);
        galleryItem.setHeight(56365);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        final Dimension dimension = FlvVideoManager.createLargeVideoDimension(galleryItem.getWidth(), galleryItem.getHeight());
        Assert.assertEquals(dimension.getHeight(), galleryItemData.getLargeVideoHeight().intValue());
    }

    @Test
    public void testGetLargeVideoHeight_withWrongFilledFormItemType() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), 320, 240, formVideo.getQuality());
        flvVideo.setFlvVideoId(15321);

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.AUDIO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValue("" + formVideo.getFormVideoId());
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId()); galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(213123);
        galleryItem.setHeight(56365);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        final Dimension dimension = FlvVideoManager.createLargeVideoDimension(galleryItem.getWidth(), galleryItem.getHeight());
        Assert.assertEquals(dimension.getHeight(), galleryItemData.getLargeVideoHeight().intValue());
    }

    @Test
    public void testGetLargeVideoHeight_withEmptyFilledFormItemValues() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), 320, 240, formVideo.getQuality());
        flvVideo.setFlvVideoId(15321);

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValues(new ArrayList<String>());
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId()); galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(213123);
        galleryItem.setHeight(56365);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        final Dimension dimension = FlvVideoManager.createLargeVideoDimension(galleryItem.getWidth(), galleryItem.getHeight());
        Assert.assertEquals(dimension.getHeight(), galleryItemData.getLargeVideoHeight().intValue());
    }

    @Test
    public void testGetLargeVideoHeight_withoutFormVideo() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), 320, 240, formVideo.getQuality());
        flvVideo.setFlvVideoId(15321);

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValues(Arrays.asList("-1", "-1"));
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId()); galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(213123);
        galleryItem.setHeight(56365);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        final Dimension dimension = FlvVideoManager.createLargeVideoDimension(galleryItem.getWidth(), galleryItem.getHeight());
        Assert.assertEquals(dimension.getHeight(), galleryItemData.getLargeVideoHeight().intValue());
    }

    @Test
    public void testGetLargeVideoHeight_withoutItemHeight() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), 320, 240, formVideo.getQuality());
        flvVideo.setFlvVideoId(15321);

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValues(Arrays.asList("-1", "-1"));
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId()); galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(213123);
        galleryItem.setHeight(null);

        final Dimension dimension = FlvVideoManager.createLargeVideoDimension(galleryItem.getWidth(), galleryItem.getHeight());
        Assert.assertNull(dimension);
    }
    /*----------------------------------------------Large video height-----------------------------------------------*/


    /*-------------------------------------------------Flv Video Id---------------------------------------------------*/

    @Test
    public void testGetFlvVideoId() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());


        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValue("" + formVideo.getFormVideoId());
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId()); galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(1000);
        galleryItem.setHeight(2000);

        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), galleryItem.getWidth(), galleryItem.getHeight(), formVideo.getQuality());
        flvVideo.setFlvVideoId(15321);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        Assert.assertEquals(flvVideo.getFlvVideoId(), galleryItemData.getNormalVideoFlvId());
    }


    @Test
    public void testGetFlvVideoId_withoutFilledFormItem() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), 320, 240, formVideo.getQuality());
        flvVideo.setFlvVideoId(15321);

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItem.setId(galleryItemId);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        Assert.assertEquals(-1, galleryItemData.getNormalVideoFlvId());
    }

    @Test
    public void testGetFlvVideoId_withWrongFilledFormItemType() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), 320, 240, formVideo.getQuality());
        flvVideo.setFlvVideoId(15321);

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.AUDIO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValue("" + formVideo.getFormVideoId());
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId()); galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        Assert.assertEquals(-1, galleryItemData.getNormalVideoFlvId());
    }

    @Test
    public void testGetFlvVideoId_withEmptyFilledFormItemValues() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), 320, 240, formVideo.getQuality());
        flvVideo.setFlvVideoId(15321);

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValues(new ArrayList<String>());
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId()); galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        Assert.assertEquals(-1, galleryItemData.getNormalVideoFlvId());
    }

    @Test
    public void testGetFlvVideoId_withoutFormVideo() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(formVideo.getVideoId(), 320, 240, formVideo.getQuality());
        flvVideo.setFlvVideoId(15321);

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValues(Arrays.asList("-1", "-1"));
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId()); galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        Assert.assertEquals(-1, galleryItemData.getNormalVideoFlvId());
    }

    @Test
    public void testGetFlvVideoId_withoutFlvVideo() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValue("" + formVideo.getFormVideoId());
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId()); galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        Assert.assertEquals(1, galleryItemData.getNormalVideoFlvId());
    }
    /*-------------------------------------------------Flv Video Id---------------------------------------------------*/


    @Test
    public void testIsVideo_VIDEO_FILE_UPLOAD() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);
        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId()); galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());
        Assert.assertTrue(galleryItemData.isVideo());
    }

    @Test
    public void testIsVideo_AUDIO_FILE_UPLOAD() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        FilledForm filledForm = TestUtil.createFilledForm(form);
        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.AUDIO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(filledFormItem.getFormItemId()); galleryItemId.setGallery(TestUtil.createGallery(site));
        galleryItem.setId(galleryItemId);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());
        Assert.assertFalse(galleryItemData.isVideo());
    }


    @Test
    public void testIsHr() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        DraftFormItem formItem = TestUtil.createFormItem(FormItemName.LINE_HR, form, 10);
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValue("" + -1);
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(formItem.getFormItemId());
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(123123);
        galleryItem.setHeight(324213);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());
        Assert.assertTrue(galleryItemData.isHr());
    }

    @Test
    public void testIsHeader() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        DraftFormItem formItem = TestUtil.createFormItem(FormItemName.HEADER, form, 10);
        formItem.setInstruction("instruction");
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.HEADER);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValue("" + -1);
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(formItem.getFormItemId());
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(123123);
        galleryItem.setHeight(324213);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());
        Assert.assertTrue(galleryItemData.isHeader());
        Assert.assertEquals(formItem.getInstruction(), galleryItemData.getHeader());
    }

    @Test
    public void testIsTextArea_CAST_LIST() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        DraftFormItem formItem = TestUtil.createFormItem(FormItemName.CAST_LIST, form, 10);
        formItem.setInstruction("instruction");
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.CAST_LIST);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValue("cast1\ncast2\ncast3\ncast4");
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(formItem.getFormItemId());
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(123123);
        galleryItem.setHeight(324213);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());
        Assert.assertTrue(galleryItemData.isTextArea());
        Assert.assertFalse(galleryItemData.getTextAreaValue().contains("\n"));
        Assert.assertEquals("cast1<br>cast2<br>cast3<br>cast4", galleryItemData.getTextAreaValue());
    }

    @Test
    public void testIsTextArea_CAST_LIST_LONG() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        DraftFormItem formItem = TestUtil.createFormItem(FormItemName.CAST_LIST_LONG, form, 10);
        formItem.setInstruction("instruction");
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.CAST_LIST);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValue("cast1\ncast2\ncast3\ncast4");
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(formItem.getFormItemId());
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(123123);
        galleryItem.setHeight(324213);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());
        Assert.assertTrue(galleryItemData.isTextArea());
        Assert.assertFalse(galleryItemData.getTextAreaValue().contains("\n"));
        Assert.assertEquals("cast1<br>cast2<br>cast3<br>cast4", galleryItemData.getTextAreaValue());
    }

    @Test
    public void testIsImage() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        DraftFormItem formItem = TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, form, 10);
        formItem.setInstruction("instruction");
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValue("" + -1);
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(formItem.getFormItemId());
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(123123);
        galleryItem.setHeight(324213);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());
        Assert.assertTrue(galleryItemData.isImage());
    }


    @Test
    public void testGetSourceImageUrl() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        DraftFormItem formItem = TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, form, 10);
        formItem.setInstruction("instruction");
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile formFile = TestUtil.createFormFile("file", site.getSiteId());
        formFile.setWidth(100);
        formFile.setHeight(200);


        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValue(formFile.getFormFileId());
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(formItem.getFormItemId());
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(123123);
        galleryItem.setHeight(324213);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());
        Assert.assertTrue(galleryItemData.getSourceImageUrl().startsWith("/resourceGetter.action?resourceId=" + formFile.getFormFileId() + "&resourceSizeId=0&resourceSizeAdditionId=0&resourceGetterType=GALLERY_DATA_SOURCE_SIZE"));
    }

    @Test
    public void testGetSourceImageUrl_withBigWidthAndHeight() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        DraftFormItem formItem = TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, form, 10);
        formItem.setInstruction("instruction");
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile formFile = TestUtil.createFormFile("file", site.getSiteId());
        formFile.setWidth(10000);
        formFile.setHeight(20000);


        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValue(formFile.getFormFileId());
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(formItem.getFormItemId());
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(123123);
        galleryItem.setHeight(324213);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());
        Assert.assertTrue(galleryItemData.getSourceImageUrl().startsWith("/resourceGetter.action?resourceId=" + formFile.getFormFileId() + "&resourceSizeId=0&resourceSizeAdditionId=0&resourceGetterType=GALLERY_DATA_SOURCE_SIZE"));
    }


    @Test
    public void testGetResizedImageUrl() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        DraftFormItem formItem = TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, form, 10);
        formItem.setInstruction("instruction");
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile formFile = TestUtil.createFormFile("file", site.getSiteId());
        formFile.setWidth(100);
        formFile.setHeight(200);


        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValue(formFile.getFormFileId());
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGallery gallery = TestUtil.createGallery(site);

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(formItem.getFormItemId());
        galleryItemId.setGallery(gallery);
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(123123);
        galleryItem.setHeight(324213);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());
        final String resizedImageUrl = galleryItemData.getResizedImageUrl();
        Assert.assertTrue(resizedImageUrl.startsWith("/resourceGetter.action?resourceId=" + formFile.getFormFileId() + "&resourceSizeId=" + gallery.getId() + "&resourceSizeAdditionId=" + galleryItem.getId().getFormItemId() + "&resourceGetterType=GALLERY_DATA"));
    }


     @Test
    public void testGetValue() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        DraftFormItem formItem = TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, form, 10);
        formItem.setInstruction("instruction");
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile formFile = TestUtil.createFormFile("file", site.getSiteId());
        formFile.setWidth(100);
        formFile.setHeight(200);


        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValue("filled form item value");
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGallery gallery = TestUtil.createGallery(site);

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(formItem.getFormItemId());
        galleryItemId.setGallery(gallery);
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(123123);
        galleryItem.setHeight(324213);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());
        final String value = galleryItemData.getValue();
        Assert.assertEquals(value, "filled form item value");
    }


    @Test
    public void testGetSourceImageWidthHeight() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        DraftFormItem formItem = TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, form, 10);
        formItem.setInstruction("instruction");
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile formFile = TestUtil.createFormFile("file", site.getSiteId());
        formFile.setWidth(100);
        formFile.setHeight(200);


        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValue(formFile.getFormFileId());
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(formItem.getFormItemId());
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(123123);
        galleryItem.setHeight(324213);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());
        final Dimension dimension = galleryItemData.getSourceImageDimension();

        Assert.assertEquals(100, dimension.getWidth());
        Assert.assertEquals(200, dimension.getHeight());
    }

    @Test
    public void testGetSourceImageWidthHeight_withBigWidth() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        DraftFormItem formItem = TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, form, 10);
        formItem.setInstruction("instruction");
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile formFile = TestUtil.createFormFile("file", site.getSiteId());
        formFile.setWidth(1300);
        formFile.setHeight(200);


        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValue(formFile.getFormFileId());
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(formItem.getFormItemId());
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(123123);
        galleryItem.setHeight(324213);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        final Dimension dimension = galleryItemData.getSourceImageDimension();

        Assert.assertEquals(1280, dimension.getWidth());
        Assert.assertEquals(196, dimension.getHeight());
    }

    @Test
    public void testGetSourceImageWidthHeight_withBigHeight() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        DraftFormItem formItem = TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, form, 10);
        formItem.setInstruction("instruction");
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile formFile = TestUtil.createFormFile("file", site.getSiteId());
        formFile.setWidth(100);
        formFile.setHeight(1100);


        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValue(formFile.getFormFileId());
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(formItem.getFormItemId());
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(123123);
        galleryItem.setHeight(324213);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());

        final Dimension dimension = galleryItemData.getSourceImageDimension();

        Assert.assertEquals(93, dimension.getWidth());
        Assert.assertEquals(1024, dimension.getHeight());
    }


    @Test
    public void testGetImageAltAndTitle_withKeywordsInFilledFormItem() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        DraftFormItem formItem = TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, form, 10);
        formItem.setInstruction("instruction");
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile formFile = TestUtil.createFormFile("file", site.getSiteId());
        formFile.setWidth(100);
        formFile.setHeight(200);


        final FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        final FilledFormItemManager manager = new FilledFormItemManager(filledFormItem);
        manager.setFormImageId(formFile.getFormFileId());
        manager.setFormImageAlt("keywords");

        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));


        DraftGallery gallery = TestUtil.createGallery(site);

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(formItem.getFormItemId());
        galleryItemId.setGallery(gallery);
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(123123);
        galleryItem.setHeight(324213);
        galleryItem.setName("name");

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());
        Assert.assertEquals("keywords", galleryItemData.getImageAltAndTitle());
    }


    @Test
    public void testGetImageAltAndTitle_withoutKeywordsInFilledFormItem() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        DraftFormItem formItem = TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, form, 10);
        formItem.setInstruction("instruction");
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile formFile = TestUtil.createFormFile("file", site.getSiteId());
        formFile.setWidth(100);
        formFile.setHeight(200);


        final FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        final FilledFormItemManager manager = new FilledFormItemManager(filledFormItem);
        manager.setFormImageId(formFile.getFormFileId());
        manager.setFormImageAlt("");

        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));


        DraftGallery gallery = TestUtil.createGallery(site);

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(formItem.getFormItemId());
        galleryItemId.setGallery(gallery);
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(123123);
        galleryItem.setHeight(324213);
        galleryItem.setName("name");

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());
        Assert.assertEquals("name", galleryItemData.getImageAltAndTitle());
    }


    @Test
    public void testGetImageAltAndTitle_withoutFilledFormItem() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        DraftFormItem formItem = TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, form, 10);
        formItem.setInstruction("instruction");
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile formFile = TestUtil.createFormFile("file", site.getSiteId());
        formFile.setWidth(100);
        formFile.setHeight(200);

        DraftGallery gallery = TestUtil.createGallery(site);

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(formItem.getFormItemId());
        galleryItemId.setGallery(gallery);
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(123123);
        galleryItem.setHeight(324213);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());
        Assert.assertEquals("", galleryItemData.getImageAltAndTitle());
    }


    @Test
    public void testGetImageAltAndTitle_withFilledFormItemNotIMAGE_FILE_UPLOAD() {
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        DraftFormItem formItem = TestUtil.createFormItem(FormItemName.VIDEO_FILE_UPLOAD, form, 10);
        formItem.setInstruction("instruction");
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile formFile = TestUtil.createFormFile("file", site.getSiteId());
        formFile.setWidth(100);
        formFile.setHeight(200);


        final FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItem.setFormItemId(1);
        filledFormItem.setValues(Arrays.asList("123", "keywords"));

        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));


        DraftGallery gallery = TestUtil.createGallery(site);

        DraftGalleryItem galleryItem = new DraftGalleryItem();
        DraftGalleryItemId galleryItemId = new DraftGalleryItemId();
        galleryItemId.setFormItemId(formItem.getFormItemId());
        galleryItemId.setGallery(gallery);
        galleryItem.setId(galleryItemId);
        galleryItem.setWidth(123123);
        galleryItem.setHeight(324213);

        final GalleryItemData galleryItemData = new GalleryItemData(filledForm, galleryItem, site.getSiteId());
        Assert.assertEquals("", galleryItemData.getImageAltAndTitle());
    }
}
