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
package com.shroggle.logic.form;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.shroggle.entity.*;
import com.shroggle.TestUtil;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystemMock;
import com.shroggle.util.html.HtmlUtil;
import com.shroggle.util.resource.provider.ResourceGetterType;
import junit.framework.Assert;

import java.io.ByteArrayInputStream;

/**
 * @author Balakirev Anatoliy
 */
@Ignore
@RunWith(TestRunnerWithMockServices.class)
public class FormFileManagerTest {

    @Test
    public void testGetFileTypesDescriptionByFormItemName() {
        Assert.assertEquals("Image Files", FormFileManager.getFileTypesDescriptionByFormItemName(FormItemName.IMAGE_FILE_UPLOAD));
        Assert.assertEquals("Video Files", FormFileManager.getFileTypesDescriptionByFormItemName(FormItemName.VIDEO_FILE_UPLOAD));
        Assert.assertEquals("PDF Files", FormFileManager.getFileTypesDescriptionByFormItemName(FormItemName.PDF_FILE_UPLOAD));
        Assert.assertEquals("Audio Files", FormFileManager.getFileTypesDescriptionByFormItemName(FormItemName.AUDIO_FILE_UPLOAD));
        Assert.assertEquals("All files", FormFileManager.getFileTypesDescriptionByFormItemName(FormItemName.ADOPTIVE_FATHER_NAME));
        Assert.assertEquals("All files", FormFileManager.getFileTypesDescriptionByFormItemName(null));
    }

    @Test
    public void testCreateFormFileData_IMAGE_FILE_UPLOAD() {
        final int widgetId = 1;
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        DraftFormItem formItem = TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, form, 0);
        formItem.setItemName("Image upload form item");
        FilledForm filledForm = TestUtil.createFilledForm(form);
        FormFile formFile = TestUtil.createFormFile("name", site.getSiteId());

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        filledFormItem.setItemName(formItem.getItemName());
        filledFormItem.setValue("" + formFile.getFormFileId());
        filledForm.addFilledFormItem(filledFormItem);


        FormFileData formFileData = FormFileManager.createFormFileData(filledForm, formItem, widgetId);


        Assert.assertNotNull(formFileData);
        Assert.assertEquals(true, formFileData.isFileExist());
        Assert.assertEquals(formFile.getSourceName(), formFileData.getFileName());
        Assert.assertEquals(true, formFileData.isShowImagePreview());
        Assert.assertEquals(ServiceLocator.getResourceGetter().get(ResourceGetterType.PREVIEW_IMAGE_FORM_FILE, formFile.getFormFileId(), 0, 0, 0, false), formFileData.getImagePreviewUrl());
        Assert.assertEquals(ServiceLocator.getResourceGetter().get(ResourceGetterType.FORM_FILE, formFile.getFormFileId(), 0, 0, 0, false), formFileData.getImageFullSizeUrl());
        Assert.assertEquals((formItem.getFormItemName().toString() + widgetId + formItem.getFormItemId() + formItem.getItemName()), formFileData.getId());
        Assert.assertEquals(widgetId, formFileData.getWidgetId());
        Assert.assertEquals(true, formFileData.isShowItemName());
        Assert.assertEquals(FlvVideo.DEFAULT_VIDEO_QUALITY, formFileData.getVideoQuality());
        Assert.assertEquals(formItem.getItemName(), formFileData.getItemName());
        Assert.assertEquals(formItem.getFormItemName(), formFileData.getFormItemName());
        Assert.assertEquals(formItem.getFormItemId(), formFileData.getFormItemId());
        Assert.assertEquals(formItem.getPosition(), formFileData.getPosition());
        Assert.assertEquals(formItem.isRequired(), formFileData.isRequired());
        Assert.assertEquals(filledForm.getFilledFormId(), formFileData.getFilledFormId().intValue());
        Assert.assertNotNull(formFileData.getFilledFormItemId());
        Assert.assertEquals(filledFormItem.getItemId(), formFileData.getFilledFormItemId().intValue());
        Assert.assertNull(formFileData.getSourceVideoWidth());
        Assert.assertNull(formFileData.getSourceVideoHeight());
        Assert.assertNull(formFileData.getFlvVideoUrl());
        Assert.assertNull(formFileData.getSourceVideoUrl());
        Assert.assertNull(formFileData.getVideoStatus());
        Assert.assertNull(formFileData.getVideoImageUrl());
        Assert.assertNull(formFileData.getVideoImageUrlId());
        Assert.assertEquals("", formFileData.getKeywords());
        Assert.assertEquals(true, formFileData.showKeywordsField());
    }

    @Test
    public void testCreateFormFileData_AUDIO_FILE_UPLOAD() {
        final int widgetId = 1;
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        DraftFormItem formItem = TestUtil.createFormItem(FormItemName.AUDIO_FILE_UPLOAD, form, 0);
        formItem.setItemName("Image upload form item");
        FilledForm filledForm = TestUtil.createFilledForm(form);
        FormFile formFile = TestUtil.createFormFile("name", site.getSiteId());

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.AUDIO_FILE_UPLOAD);
        filledFormItem.setItemName(formItem.getItemName());
        filledFormItem.setValue("" + formFile.getFormFileId());
        filledForm.addFilledFormItem(filledFormItem);


        FormFileData formFileData = FormFileManager.createFormFileData(filledForm, formItem, widgetId);


        Assert.assertNotNull(formFileData);
        Assert.assertEquals(true, formFileData.isFileExist());
        Assert.assertEquals(formFile.getSourceName(), formFileData.getFileName());
        Assert.assertEquals(false, formFileData.isShowImagePreview());
        Assert.assertEquals("", formFileData.getImagePreviewUrl());
        Assert.assertEquals("", formFileData.getImageFullSizeUrl());
        Assert.assertEquals((formItem.getFormItemName().toString() + widgetId + formItem.getFormItemId() + formItem.getItemName()), formFileData.getId());
        Assert.assertEquals(widgetId, formFileData.getWidgetId());
        Assert.assertEquals(true, formFileData.isShowItemName());
        Assert.assertEquals(FlvVideo.DEFAULT_VIDEO_QUALITY, formFileData.getVideoQuality());
        Assert.assertEquals(formItem.getItemName(), formFileData.getItemName());
        Assert.assertEquals(formItem.getFormItemName(), formFileData.getFormItemName());
        Assert.assertEquals(formItem.getFormItemId(), formFileData.getFormItemId());
        Assert.assertEquals(formItem.getPosition(), formFileData.getPosition());
        Assert.assertEquals(formItem.isRequired(), formFileData.isRequired());
        Assert.assertEquals(filledForm.getFilledFormId(), formFileData.getFilledFormId().intValue());
        Assert.assertNotNull(formFileData.getFilledFormItemId());
        Assert.assertEquals(filledFormItem.getItemId(), formFileData.getFilledFormItemId().intValue());
        Assert.assertEquals(FormFileTypeForDeletion.FORM_FILE, formFileData.getFormFileTypeForDeletion());
        Assert.assertNull(formFileData.getSourceVideoWidth());
        Assert.assertNull(formFileData.getSourceVideoHeight());
        Assert.assertNull(formFileData.getFlvVideoUrl());
        Assert.assertNull(formFileData.getSourceVideoUrl());
        Assert.assertNull(formFileData.getVideoStatus());
        Assert.assertNull(formFileData.getVideoImageUrl());
        Assert.assertNull(formFileData.getVideoImageUrlId());
        Assert.assertEquals(null, formFileData.getKeywords());
        Assert.assertEquals(false, formFileData.showKeywordsField());
    }


    @Test
    public void testCreateFormFileData_withoutFilledFormItem() {
        final int widgetId = 1;
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        DraftFormItem formItem = TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, form, 0);
        formItem.setItemName("Image upload form item");
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFileData formFileData = FormFileManager.createFormFileData(filledForm, formItem, widgetId);


        Assert.assertNotNull(formFileData);
        Assert.assertEquals(false, formFileData.isFileExist());
        Assert.assertEquals("", formFileData.getFileName());
        Assert.assertEquals(false, formFileData.isShowImagePreview());
        Assert.assertEquals("", formFileData.getImagePreviewUrl());
        Assert.assertEquals("", formFileData.getImageFullSizeUrl());
        Assert.assertEquals((formItem.getFormItemName().toString() + widgetId + formItem.getFormItemId() + formItem.getItemName()), formFileData.getId());
        Assert.assertEquals(widgetId, formFileData.getWidgetId());
        Assert.assertEquals(true, formFileData.isShowItemName());
        Assert.assertEquals(FlvVideo.DEFAULT_VIDEO_QUALITY, formFileData.getVideoQuality());
        Assert.assertEquals(formItem.getItemName(), formFileData.getItemName());
        Assert.assertEquals(formItem.getFormItemName(), formFileData.getFormItemName());
        Assert.assertEquals(formItem.getFormItemId(), formFileData.getFormItemId());
        Assert.assertEquals(formItem.getPosition(), formFileData.getPosition());
        Assert.assertEquals(formItem.isRequired(), formFileData.isRequired());
        Assert.assertEquals(filledForm.getFilledFormId(), formFileData.getFilledFormId().intValue());
        Assert.assertNull(formFileData.getFilledFormItemId());
        Assert.assertEquals(FormFileTypeForDeletion.FORM_FILE, formFileData.getFormFileTypeForDeletion());
        Assert.assertNull(formFileData.getSourceVideoWidth());
        Assert.assertNull(formFileData.getSourceVideoHeight());
        Assert.assertNull(formFileData.getFlvVideoUrl());
        Assert.assertNull(formFileData.getSourceVideoUrl());
        Assert.assertNull(formFileData.getVideoStatus());
        Assert.assertNull(formFileData.getVideoImageUrl());
        Assert.assertNull(formFileData.getVideoImageUrlId());
    }

    @Test
    public void testCreateFormFileData_withoutValueInFilledFormItem() {
        final int widgetId = 1;
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        DraftFormItem formItem = TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, form, 0);
        formItem.setItemName("Image upload form item");
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        filledFormItem.setItemName(formItem.getItemName());
        filledForm.addFilledFormItem(filledFormItem);


        FormFileData formFileData = FormFileManager.createFormFileData(filledForm, formItem, widgetId);


        Assert.assertNotNull(formFileData);
        Assert.assertEquals(false, formFileData.isFileExist());
        Assert.assertEquals("", formFileData.getFileName());
        Assert.assertEquals(false, formFileData.isShowImagePreview());
        Assert.assertEquals("", formFileData.getImagePreviewUrl());
        Assert.assertEquals("", formFileData.getImageFullSizeUrl());
        Assert.assertEquals((formItem.getFormItemName().toString() + widgetId + formItem.getFormItemId() + formItem.getItemName()), formFileData.getId());
        Assert.assertEquals(widgetId, formFileData.getWidgetId());
        Assert.assertEquals(true, formFileData.isShowItemName());
        Assert.assertEquals(FlvVideo.DEFAULT_VIDEO_QUALITY, formFileData.getVideoQuality());
        Assert.assertEquals(formItem.getItemName(), formFileData.getItemName());
        Assert.assertEquals(formItem.getFormItemName(), formFileData.getFormItemName());
        Assert.assertEquals(formItem.getFormItemId(), formFileData.getFormItemId());
        Assert.assertEquals(formItem.getPosition(), formFileData.getPosition());
        Assert.assertEquals(formItem.isRequired(), formFileData.isRequired());
        Assert.assertEquals(filledForm.getFilledFormId(), formFileData.getFilledFormId().intValue());
        Assert.assertNotNull(formFileData.getFilledFormItemId());
        Assert.assertEquals(filledFormItem.getItemId(), formFileData.getFilledFormItemId().intValue());
        Assert.assertEquals(FormFileTypeForDeletion.FORM_FILE, formFileData.getFormFileTypeForDeletion());
        Assert.assertNull(formFileData.getSourceVideoWidth());
        Assert.assertNull(formFileData.getSourceVideoHeight());
        Assert.assertNull(formFileData.getFlvVideoUrl());
        Assert.assertNull(formFileData.getSourceVideoUrl());
        Assert.assertNull(formFileData.getVideoStatus());
        Assert.assertNull(formFileData.getVideoImageUrl());
        Assert.assertNull(formFileData.getVideoImageUrlId());
    }

    @Test
    public void testCreateFormFileData_withoutFilledForm() {
        final int widgetId = 1;
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        DraftFormItem formItem = TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, form, 0);
        formItem.setItemName("Image upload form item");

        FormFileData formFileData = FormFileManager.createFormFileData(null, formItem, widgetId);


        Assert.assertNotNull(formFileData);
        Assert.assertEquals(false, formFileData.isFileExist());
        Assert.assertEquals("", formFileData.getFileName());
        Assert.assertEquals(false, formFileData.isShowImagePreview());
        Assert.assertEquals("", formFileData.getImagePreviewUrl());
        Assert.assertEquals("", formFileData.getImageFullSizeUrl());
        Assert.assertEquals((formItem.getFormItemName().toString() + widgetId + formItem.getFormItemId() + formItem.getItemName()), formFileData.getId());
        Assert.assertEquals(widgetId, formFileData.getWidgetId());
        Assert.assertEquals(true, formFileData.isShowItemName());
        Assert.assertEquals(FlvVideo.DEFAULT_VIDEO_QUALITY, formFileData.getVideoQuality());
        Assert.assertEquals(formItem.getItemName(), formFileData.getItemName());
        Assert.assertEquals(formItem.getFormItemName(), formFileData.getFormItemName());
        Assert.assertEquals(formItem.getFormItemId(), formFileData.getFormItemId());
        Assert.assertEquals(formItem.getPosition(), formFileData.getPosition());
        Assert.assertEquals(formItem.isRequired(), formFileData.isRequired());
        Assert.assertEquals(null, formFileData.getFilledFormId());
        Assert.assertEquals(null, formFileData.getFilledFormItemId());
        Assert.assertEquals(FormFileTypeForDeletion.FORM_FILE, formFileData.getFormFileTypeForDeletion());
        Assert.assertNull(formFileData.getSourceVideoWidth());
        Assert.assertNull(formFileData.getSourceVideoHeight());
        Assert.assertNull(formFileData.getFlvVideoUrl());
        Assert.assertNull(formFileData.getSourceVideoUrl());
        Assert.assertNull(formFileData.getVideoStatus());
        Assert.assertNull(formFileData.getVideoImageUrl());
        Assert.assertNull(formFileData.getVideoImageUrlId());
    }


    @Test
    public void testCreateFormFileVideoData() {
        final int widgetId = 1;
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        DraftFormItem formItem = TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, form, 0);
        formItem.setItemName("Image upload form item");
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FlvVideo flvVideo = TestUtil.createVideoFLV(video.getVideoId(), video.getSourceWidth(), video.getSourceHeight(), formVideo.getQuality());
        final FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
        fileSystemMock.setResourceStream(flvVideo, new ByteArrayInputStream(new byte[0]));
        
        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        filledFormItem.setItemName(formItem.getItemName());
        filledFormItem.setValue("" + formVideo.getFormVideoId());
        filledForm.addFilledFormItem(filledFormItem);


        FormFileData formFileData = FormFileManager.createFormFileVideoData(filledForm, formItem, widgetId);


        Assert.assertNotNull(formFileData);
        Assert.assertEquals(true, formFileData.isFileExist());
        Assert.assertEquals(video.getSourceName(), formFileData.getFileName());
        Assert.assertEquals(false, formFileData.isShowImagePreview());
        Assert.assertEquals("", formFileData.getImagePreviewUrl());
        Assert.assertEquals("", formFileData.getImageFullSizeUrl());
        Assert.assertEquals((formItem.getFormItemName().toString() + widgetId + formItem.getFormItemId() + formItem.getItemName()) + "VideoField", formFileData.getId());
        Assert.assertEquals(widgetId, formFileData.getWidgetId());
        Assert.assertEquals(false, formFileData.isShowItemName());
        Assert.assertEquals(FlvVideo.DEFAULT_VIDEO_QUALITY, formFileData.getVideoQuality());
        Assert.assertEquals(formItem.getItemName(), formFileData.getItemName());
        Assert.assertEquals(formItem.getFormItemName(), formFileData.getFormItemName());
        Assert.assertEquals(formItem.getFormItemId(), formFileData.getFormItemId());
        Assert.assertEquals(formItem.getPosition(), formFileData.getPosition());
        Assert.assertEquals(formItem.isRequired(), formFileData.isRequired());
        Assert.assertEquals(filledForm.getFilledFormId(), formFileData.getFilledFormId().intValue());
        Assert.assertNotNull(formFileData.getFilledFormItemId());
        Assert.assertEquals(filledFormItem.getItemId(), formFileData.getFilledFormItemId().intValue());
        Assert.assertEquals(FormFileTypeForDeletion.VIDEO, formFileData.getFormFileTypeForDeletion());
        Assert.assertEquals(video.getSourceWidth(), formFileData.getSourceVideoWidth().intValue());
        Assert.assertEquals(video.getSourceHeight(), formFileData.getSourceVideoHeight().intValue());
        Assert.assertEquals(HtmlUtil.encodeToPercent(ResourceGetterType.FLV_VIDEO.getVideoUrl(flvVideo)), formFileData.getFlvVideoUrl());
        Assert.assertEquals(ServiceLocator.getResourceGetter().get(ResourceGetterType.SOURCE_VIDEO,  video.getVideoId(), 0, 0, 0, true), formFileData.getSourceVideoUrl());
        Assert.assertEquals("ok", formFileData.getVideoStatus());
        Assert.assertEquals(HtmlUtil.encodeToPercent(ServiceLocator.getResourceGetter().get(ResourceGetterType.FORM_FILE, image.getFormFileId(), 0, 0, 0, false)), formFileData.getVideoImageUrl());
        Assert.assertEquals((FormItemName.IMAGE_FILE_UPLOAD.toString() + widgetId + formItem.getFormItemId() + formItem.getItemName()) + "VideoImageField", formFileData.getVideoImageUrlId());
    }


    @Test
    public void testCreateFormFileVideoData_withoutFilledFormItem() {
        final int widgetId = 1;
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        DraftFormItem formItem = TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, form, 0);
        formItem.setItemName("Image upload form item");
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFileData formFileData = FormFileManager.createFormFileVideoData(filledForm, formItem, widgetId);


        Assert.assertNotNull(formFileData);
        Assert.assertEquals(false, formFileData.isFileExist());
        Assert.assertEquals("", formFileData.getFileName());
        Assert.assertEquals(false, formFileData.isShowImagePreview());
        Assert.assertEquals("", formFileData.getImagePreviewUrl());
        Assert.assertEquals("", formFileData.getImageFullSizeUrl());
        Assert.assertEquals((formItem.getFormItemName().toString() + widgetId + formItem.getFormItemId() + formItem.getItemName()) + "VideoField", formFileData.getId());
        Assert.assertEquals(widgetId, formFileData.getWidgetId());
        Assert.assertEquals(false, formFileData.isShowItemName());
        Assert.assertEquals(FlvVideo.DEFAULT_VIDEO_QUALITY, formFileData.getVideoQuality());
        Assert.assertEquals(formItem.getItemName(), formFileData.getItemName());
        Assert.assertEquals(formItem.getFormItemName(), formFileData.getFormItemName());
        Assert.assertEquals(formItem.getFormItemId(), formFileData.getFormItemId());
        Assert.assertEquals(formItem.getPosition(), formFileData.getPosition());
        Assert.assertEquals(formItem.isRequired(), formFileData.isRequired());
        Assert.assertEquals(filledForm.getFilledFormId(), formFileData.getFilledFormId().intValue());
        Assert.assertEquals(null, formFileData.getFilledFormItemId());
        Assert.assertEquals(FormFileTypeForDeletion.VIDEO, formFileData.getFormFileTypeForDeletion());
        Assert.assertNull(formFileData.getSourceVideoWidth());
        Assert.assertNull(formFileData.getSourceVideoHeight());
        Assert.assertNull(formFileData.getFlvVideoUrl());
        Assert.assertNull(formFileData.getSourceVideoUrl());
        Assert.assertEquals("notfound", formFileData.getVideoStatus());
        Assert.assertEquals("", formFileData.getVideoImageUrl());
        Assert.assertEquals((FormItemName.IMAGE_FILE_UPLOAD.toString() + widgetId + formItem.getFormItemId() + formItem.getItemName()) + "VideoImageField", formFileData.getVideoImageUrlId());
    }

    @Test
    public void testCreateFormFileVideoData_withoutValueInFilledFormItem() {
        final int widgetId = 1;
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        DraftFormItem formItem = TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, form, 0);
        formItem.setItemName("Image upload form item");
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        filledFormItem.setItemName(formItem.getItemName());
        filledForm.addFilledFormItem(filledFormItem);


        FormFileData formFileData = FormFileManager.createFormFileVideoData(filledForm, formItem, widgetId);


        Assert.assertNotNull(formFileData);
        Assert.assertEquals(false, formFileData.isFileExist());
        Assert.assertEquals("", formFileData.getFileName());
        Assert.assertEquals(false, formFileData.isShowImagePreview());
        Assert.assertEquals("", formFileData.getImagePreviewUrl());
        Assert.assertEquals("", formFileData.getImageFullSizeUrl());
        Assert.assertEquals((formItem.getFormItemName().toString() + widgetId + formItem.getFormItemId() + formItem.getItemName()) + "VideoField", formFileData.getId());
        Assert.assertEquals(widgetId, formFileData.getWidgetId());
        Assert.assertEquals(false, formFileData.isShowItemName());
        Assert.assertEquals(FlvVideo.DEFAULT_VIDEO_QUALITY, formFileData.getVideoQuality());
        Assert.assertEquals(formItem.getItemName(), formFileData.getItemName());
        Assert.assertEquals(formItem.getFormItemName(), formFileData.getFormItemName());
        Assert.assertEquals(formItem.getFormItemId(), formFileData.getFormItemId());
        Assert.assertEquals(formItem.getPosition(), formFileData.getPosition());
        Assert.assertEquals(formItem.isRequired(), formFileData.isRequired());
        Assert.assertEquals(filledForm.getFilledFormId(), formFileData.getFilledFormId().intValue());
        Assert.assertEquals(filledFormItem.getItemId(), formFileData.getFilledFormItemId().intValue());
        Assert.assertEquals(FormFileTypeForDeletion.VIDEO, formFileData.getFormFileTypeForDeletion());
        Assert.assertNull(formFileData.getSourceVideoWidth());
        Assert.assertNull(formFileData.getSourceVideoHeight());
        Assert.assertNull(formFileData.getFlvVideoUrl());
        Assert.assertNull(formFileData.getSourceVideoUrl());
        Assert.assertEquals("notfound", formFileData.getVideoStatus());
        Assert.assertEquals("", formFileData.getVideoImageUrl());
        Assert.assertEquals((FormItemName.IMAGE_FILE_UPLOAD.toString() + widgetId + formItem.getFormItemId() + formItem.getItemName()) + "VideoImageField", formFileData.getVideoImageUrlId());
    }

    @Test
    public void testCreateFormFileVideoData_withoutFilledForm() {
        final int widgetId = 1;
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        DraftFormItem formItem = TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, form, 0);
        formItem.setItemName("Image upload form item");

        FormFileData formFileData = FormFileManager.createFormFileVideoData(null, formItem, widgetId);


        Assert.assertNotNull(formFileData);
        Assert.assertEquals(false, formFileData.isFileExist());
        Assert.assertEquals("", formFileData.getFileName());
        Assert.assertEquals(false, formFileData.isShowImagePreview());
        Assert.assertEquals("", formFileData.getImagePreviewUrl());
        Assert.assertEquals("", formFileData.getImageFullSizeUrl());
        Assert.assertEquals((formItem.getFormItemName().toString() + widgetId + formItem.getFormItemId() + formItem.getItemName()) + "VideoField", formFileData.getId());
        Assert.assertEquals(widgetId, formFileData.getWidgetId());
        Assert.assertEquals(false, formFileData.isShowItemName());
        Assert.assertEquals(FlvVideo.DEFAULT_VIDEO_QUALITY, formFileData.getVideoQuality());
        Assert.assertEquals(formItem.getItemName(), formFileData.getItemName());
        Assert.assertEquals(formItem.getFormItemName(), formFileData.getFormItemName());
        Assert.assertEquals(formItem.getFormItemId(), formFileData.getFormItemId());
        Assert.assertEquals(formItem.getPosition(), formFileData.getPosition());
        Assert.assertEquals(formItem.isRequired(), formFileData.isRequired());
        Assert.assertEquals(null, formFileData.getFilledFormId());
        Assert.assertEquals(null, formFileData.getFilledFormItemId());
        Assert.assertEquals(FormFileTypeForDeletion.VIDEO, formFileData.getFormFileTypeForDeletion());
        Assert.assertNull(formFileData.getSourceVideoWidth());
        Assert.assertNull(formFileData.getSourceVideoHeight());
        Assert.assertNull(formFileData.getFlvVideoUrl());
        Assert.assertNull(formFileData.getSourceVideoUrl());
        Assert.assertEquals("notfound", formFileData.getVideoStatus());
        Assert.assertEquals("", formFileData.getVideoImageUrl());
        Assert.assertEquals((FormItemName.IMAGE_FILE_UPLOAD.toString() + widgetId + formItem.getFormItemId() + formItem.getItemName()) + "VideoImageField", formFileData.getVideoImageUrlId());
    }

    //---------------------------------------
    @Test
    public void testCreateFormFileVideoImageData() {
        final int widgetId = 1;
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        DraftFormItem formItem = TestUtil.createFormItem(FormItemName.ADOPTIVE_MOTHER_NAME, form, 0);
        formItem.setRequired(true);
        formItem.setItemName("Image upload form item");
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        filledFormItem.setItemName(formItem.getItemName());
        filledFormItem.setValue("" + formVideo.getFormVideoId());
        filledForm.addFilledFormItem(filledFormItem);


        FormFileData formFileData = FormFileManager.createFormFileVideoImageData(filledForm, formItem, widgetId);


        Assert.assertNotNull(formFileData);
        Assert.assertEquals(true, formFileData.isFileExist());
        Assert.assertEquals(image.getSourceName(), formFileData.getFileName());
        Assert.assertEquals(true, formFileData.isShowImagePreview());
        Assert.assertEquals(ServiceLocator.getResourceGetter().get(ResourceGetterType.PREVIEW_IMAGE_FORM_FILE, image.getFormFileId(), 0, 0, 0, false), formFileData.getImagePreviewUrl());
        Assert.assertEquals(ServiceLocator.getResourceGetter().get(ResourceGetterType.FORM_FILE, image.getFormFileId(), 0, 0, 0, false), formFileData.getImageFullSizeUrl());
        Assert.assertEquals((FormItemName.IMAGE_FILE_UPLOAD.toString() + widgetId + formItem.getFormItemId() + formItem.getItemName()) + "VideoImageField", formFileData.getId());
        Assert.assertEquals(widgetId, formFileData.getWidgetId());
        Assert.assertEquals(false, formFileData.isShowItemName());
        Assert.assertEquals(FlvVideo.DEFAULT_VIDEO_QUALITY, formFileData.getVideoQuality());
        Assert.assertEquals(formItem.getItemName(), formFileData.getItemName());
        Assert.assertEquals(FormItemName.IMAGE_FILE_UPLOAD, formFileData.getFormItemName());
        Assert.assertEquals(formItem.getFormItemId(), formFileData.getFormItemId());
        Assert.assertEquals(formItem.getPosition(), formFileData.getPosition());
        Assert.assertEquals(false, formFileData.isRequired());
        Assert.assertEquals(filledForm.getFilledFormId(), formFileData.getFilledFormId().intValue());
        Assert.assertNotNull(formFileData.getFilledFormItemId());
        Assert.assertEquals(filledFormItem.getItemId(), formFileData.getFilledFormItemId().intValue());
        Assert.assertEquals(FormFileTypeForDeletion.IMAGE, formFileData.getFormFileTypeForDeletion());
        Assert.assertNull(formFileData.getSourceVideoWidth());
        Assert.assertNull(formFileData.getSourceVideoHeight());
        Assert.assertNull(formFileData.getFlvVideoUrl());
        Assert.assertNull(formFileData.getSourceVideoUrl());
        Assert.assertNull(formFileData.getVideoStatus());
        Assert.assertNull(formFileData.getVideoImageUrl());
        Assert.assertNull(formFileData.getVideoImageUrlId());
    }


    @Test
    public void testCreateFormFileVideoImageData_withoutFilledFormItem() {
        final int widgetId = 1;
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        DraftFormItem formItem = TestUtil.createFormItem(FormItemName.ADOPTIVE_MOTHER_NAME, form, 0);
        formItem.setRequired(true);
        formItem.setItemName("Image upload form item");
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FormFileData formFileData = FormFileManager.createFormFileVideoImageData(filledForm, formItem, widgetId);


        Assert.assertNotNull(formFileData);
        Assert.assertEquals(false, formFileData.isFileExist());
        Assert.assertEquals("", formFileData.getFileName());
        Assert.assertEquals(false, formFileData.isShowImagePreview());
        Assert.assertEquals("", formFileData.getImagePreviewUrl());
        Assert.assertEquals("", formFileData.getImageFullSizeUrl());
        Assert.assertEquals((FormItemName.IMAGE_FILE_UPLOAD.toString() + widgetId + formItem.getFormItemId() + formItem.getItemName()) + "VideoImageField", formFileData.getId());
        Assert.assertEquals(widgetId, formFileData.getWidgetId());
        Assert.assertEquals(false, formFileData.isShowItemName());
        Assert.assertEquals(FlvVideo.DEFAULT_VIDEO_QUALITY, formFileData.getVideoQuality());
        Assert.assertEquals(formItem.getItemName(), formFileData.getItemName());
        Assert.assertEquals(FormItemName.IMAGE_FILE_UPLOAD, formFileData.getFormItemName());
        Assert.assertEquals(formItem.getFormItemId(), formFileData.getFormItemId());
        Assert.assertEquals(formItem.getPosition(), formFileData.getPosition());
        Assert.assertEquals(false, formFileData.isRequired());
        Assert.assertEquals(filledForm.getFilledFormId(), formFileData.getFilledFormId().intValue());
        Assert.assertEquals(null, formFileData.getFilledFormItemId());
        Assert.assertEquals(FormFileTypeForDeletion.IMAGE, formFileData.getFormFileTypeForDeletion());
        Assert.assertNull(formFileData.getSourceVideoWidth());
        Assert.assertNull(formFileData.getSourceVideoHeight());
        Assert.assertNull(formFileData.getFlvVideoUrl());
        Assert.assertNull(formFileData.getSourceVideoUrl());
        Assert.assertNull(formFileData.getVideoStatus());
        Assert.assertNull(formFileData.getVideoImageUrl());
        Assert.assertNull(formFileData.getVideoImageUrlId());
    }

    @Test
    public void testCreateFormFileVideoImageData_withoutValueInFilledFormItem() {
        final int widgetId = 1;
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        DraftFormItem formItem = TestUtil.createFormItem(FormItemName.ADOPTIVE_MOTHER_NAME, form, 0);
        formItem.setRequired(true);
        formItem.setItemName("Image upload form item");
        FilledForm filledForm = TestUtil.createFilledForm(form);

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        filledFormItem.setItemName(formItem.getItemName());
        filledForm.addFilledFormItem(filledFormItem);


        FormFileData formFileData = FormFileManager.createFormFileVideoImageData(filledForm, formItem, widgetId);


        Assert.assertNotNull(formFileData);
        Assert.assertEquals(false, formFileData.isFileExist());
        Assert.assertEquals("", formFileData.getFileName());
        Assert.assertEquals(false, formFileData.isShowImagePreview());
        Assert.assertEquals("", formFileData.getImagePreviewUrl());
        Assert.assertEquals("", formFileData.getImageFullSizeUrl());
        Assert.assertEquals((FormItemName.IMAGE_FILE_UPLOAD.toString() + widgetId + formItem.getFormItemId() + formItem.getItemName()) + "VideoImageField", formFileData.getId());
        Assert.assertEquals(widgetId, formFileData.getWidgetId());
        Assert.assertEquals(false, formFileData.isShowItemName());
        Assert.assertEquals(FlvVideo.DEFAULT_VIDEO_QUALITY, formFileData.getVideoQuality());
        Assert.assertEquals(formItem.getItemName(), formFileData.getItemName());
        Assert.assertEquals(FormItemName.IMAGE_FILE_UPLOAD, formFileData.getFormItemName());
        Assert.assertEquals(formItem.getFormItemId(), formFileData.getFormItemId());
        Assert.assertEquals(formItem.getPosition(), formFileData.getPosition());
        Assert.assertEquals(false, formFileData.isRequired());
        Assert.assertEquals(filledForm.getFilledFormId(), formFileData.getFilledFormId().intValue());
        Assert.assertEquals(filledFormItem.getItemId(), formFileData.getFilledFormItemId().intValue());
        Assert.assertEquals(FormFileTypeForDeletion.IMAGE, formFileData.getFormFileTypeForDeletion());
        Assert.assertNull(formFileData.getSourceVideoWidth());
        Assert.assertNull(formFileData.getSourceVideoHeight());
        Assert.assertNull(formFileData.getFlvVideoUrl());
        Assert.assertNull(formFileData.getSourceVideoUrl());
        Assert.assertNull(formFileData.getVideoStatus());
        Assert.assertNull(formFileData.getVideoImageUrl());
        Assert.assertNull(formFileData.getVideoImageUrlId());
    }

    @Test
    public void testCreateFormFileVideoImageData_withoutFilledForm() {
        final int widgetId = 1;
        Site site = TestUtil.createSite();
        DraftForm form = TestUtil.createChildSiteRegistration(site);
        DraftFormItem formItem = TestUtil.createFormItem(FormItemName.FATHER_NAME, form, 0);
        formItem.setRequired(true);
        formItem.setItemName("Image upload form item");

        FormFileData formFileData = FormFileManager.createFormFileVideoImageData(null, formItem, widgetId);


        Assert.assertNotNull(formFileData);
        Assert.assertEquals(false, formFileData.isFileExist());
        Assert.assertEquals("", formFileData.getFileName());
        Assert.assertEquals(false, formFileData.isShowImagePreview());
        Assert.assertEquals("", formFileData.getImagePreviewUrl());
        Assert.assertEquals("", formFileData.getImageFullSizeUrl());
        Assert.assertEquals((FormItemName.IMAGE_FILE_UPLOAD.toString() + widgetId + formItem.getFormItemId() + formItem.getItemName()) + "VideoImageField", formFileData.getId());
        Assert.assertEquals(widgetId, formFileData.getWidgetId());
        Assert.assertEquals(false, formFileData.isShowItemName());
        Assert.assertEquals(FlvVideo.DEFAULT_VIDEO_QUALITY, formFileData.getVideoQuality());
        Assert.assertEquals(formItem.getItemName(), formFileData.getItemName());
        Assert.assertEquals(FormItemName.IMAGE_FILE_UPLOAD, formFileData.getFormItemName());
        Assert.assertEquals(formItem.getFormItemId(), formFileData.getFormItemId());
        Assert.assertEquals(formItem.getPosition(), formFileData.getPosition());
        Assert.assertEquals(false, formFileData.isRequired());
        Assert.assertEquals(null, formFileData.getFilledFormId());
        Assert.assertEquals(null, formFileData.getFilledFormItemId());
        Assert.assertEquals(FormFileTypeForDeletion.IMAGE, formFileData.getFormFileTypeForDeletion());
        Assert.assertNull(formFileData.getSourceVideoWidth());
        Assert.assertNull(formFileData.getSourceVideoHeight());
        Assert.assertNull(formFileData.getFlvVideoUrl());
        Assert.assertNull(formFileData.getSourceVideoUrl());
        Assert.assertNull(formFileData.getVideoStatus());
        Assert.assertNull(formFileData.getVideoImageUrl());
        Assert.assertNull(formFileData.getVideoImageUrlId());
    }

}
