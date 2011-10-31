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

import com.shroggle.entity.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.html.HtmlUtil;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.resource.provider.ResourceGetterType;
import com.shroggle.logic.video.FlvVideoManager;

/**
 * @author Balakirev Anatoliy
 *         Date: 02.09.2009
 */
public class FormFileManager {

    public static String getFileTypesDescriptionByFormItemName(final FormItemName formItemName) {
        if (formItemName != null) {
            switch (formItemName) {
                case IMAGE_FILE_UPLOAD: {
                    return "Image Files";
                }
                case VIDEO_FILE_UPLOAD: {
                    return "Video Files";
                }
                case PDF_FILE_UPLOAD: {
                    return "PDF Files";
                }
                case AUDIO_FILE_UPLOAD: {
                    return "Audio Files";
                }
            }
        }
        return "All files";
    }

    public static FormFileData createFormFileData(final FilledForm filledForm, final FormItem formItem, final int widgetId) {
        final Persistance persistance = ServiceLocator.getPersistance();
        FilledFormItem filledFormItem = FilledFormManager.getFilledFormItemByItemName(filledForm, formItem.getItemName());
        final FormFile file = persistance.getFormFileById(FilledFormItemManager.getIntValue(filledFormItem));
        final boolean fileExist = file != null;
        final String fileName = fileExist ? file.getSourceName() : "";
        final boolean showImagePreview = fileExist && filledFormItem.getFormItemName() == FormItemName.IMAGE_FILE_UPLOAD;
        final String imagePreviewUrl;
        final String imageFullSizeUrl;
        if (showImagePreview) {
            imagePreviewUrl = ServiceLocator.getResourceGetter().get(ResourceGetterType.PREVIEW_IMAGE_FORM_FILE, file.getFormFileId(), 0, 0, 0, false);
            imageFullSizeUrl = ServiceLocator.getResourceGetter().get(ResourceGetterType.FORM_FILE, file.getFormFileId(), 0, 0, 0, false);
        } else {
            imagePreviewUrl = "";
            imageFullSizeUrl = "";
        }

        final String id = createId(formItem.getFormItemName(), widgetId, formItem);

        final String keywords;
        if (formItem.getFormItemName() == FormItemName.IMAGE_FILE_UPLOAD) {
            if (filledFormItem != null) {
                keywords = new FilledFormItemManager(filledFormItem).getFormImageAlt();
            } else {
                keywords = "";
            }
        } else {
            keywords = null;
        }

        return new FormFileData(id, widgetId, fileExist, fileName, showImagePreview, imagePreviewUrl, imageFullSizeUrl, true, FlvVideo.DEFAULT_VIDEO_QUALITY,
                formItem.getItemName(), formItem.getFormItemName(), formItem.getFormItemId(), formItem.getPosition(), formItem.isRequired(),
                filledForm != null ? filledForm.getFilledFormId() : null, filledFormItem != null ? filledFormItem.getItemId() : null,
                FormFileTypeForDeletion.FORM_FILE, null, null, null, null, null, null, null, keywords);
    }

    public static FormFileData createFormFileVideoData(final FilledForm filledForm, final FormItem formItem, final int widgetId) {
        final Persistance persistance = ServiceLocator.getPersistance();
        final FilledFormItem filledFormItem = FilledFormManager.getFilledFormItemByItemName(filledForm, formItem.getItemName());
        FormVideo formVideo = persistance.getFormVideoById(FilledFormItemManager.getIntValue(filledFormItem));
        formVideo = formVideo != null ? formVideo : new FormVideo();
        final Video video = persistance.getVideoById(formVideo.getVideoId());

        final boolean fileExist = (formVideo.getFormVideoId() > 0 && video != null);
        final String fileName = fileExist ? video.getSourceName() : "";
        final boolean showImagePreview = false;
        final String id = createId(formItem.getFormItemName(), widgetId, formItem) + "VideoField";

        final FlvVideo flvVideo = FlvVideoManager.getFlvVideoOrCreateNew(video != null ? video.getVideoId() : null,
                video != null ? video.getSourceWidth() : null, video != null ? video.getSourceHeight() : null,
                formVideo != null ? formVideo.getQuality() : null, video != null ? video.getSiteId() : -1);

        final String flvVideoUrl = HtmlUtil.encodeToPercent(ResourceGetterType.FLV_VIDEO.getVideoUrl(flvVideo));
        final String sourceVideoUrl = video != null ? ServiceLocator.getResourceGetter().get(ResourceGetterType.SOURCE_VIDEO, video.getVideoId(), 0, 0, 0, true) : null;
        final String videoStatus = new FlvVideoManager(flvVideo).getFlvVideoStatusAndStartNewConversionIfNeeded();

        final FormFile image = persistance.getFormFileById(formVideo.getImageId());
        final String videoImageUrl = fileExist && image != null ? HtmlUtil.encodeToPercent(ServiceLocator.getResourceGetter().get(ResourceGetterType.FORM_FILE, image.getFormFileId(), 0, 0, 0, false)) : "";
        final String videoImageUrlId = createId(FormItemName.IMAGE_FILE_UPLOAD, widgetId, formItem) + "VideoImageField";

        final FormFileData data = new FormFileData(id, widgetId, fileExist, fileName, showImagePreview, "", "", false,
                formVideo.getQuality(), formItem.getItemName(), formItem.getFormItemName(), formItem.getFormItemId(),
                formItem.getPosition(), formItem.isRequired(), filledForm != null ? filledForm.getFilledFormId() : null,
                filledFormItem != null ? filledFormItem.getItemId() : null, FormFileTypeForDeletion.VIDEO,
                video != null ? video.getSourceWidth() : null, video != null ? video.getSourceHeight() : null,
                flvVideoUrl, sourceVideoUrl, videoImageUrl, videoImageUrlId, videoStatus, null);
        data.setFlvVideoId(flvVideo != null ? flvVideo.getFlvVideoId() : null);
        return data;
    }

    public static FormFileData createFormFileVideoImageData(final FilledForm filledForm, final FormItem formItem, final int widgetId) {
        final Persistance persistance = ServiceLocator.getPersistance();
        final FilledFormItem filledFormItem = FilledFormManager.getFilledFormItemByItemName(filledForm, formItem.getItemName());
        FormVideo formVideo = persistance.getFormVideoById(FilledFormItemManager.getIntValue(filledFormItem));
        formVideo = formVideo != null ? formVideo : new FormVideo();
        final FormFile image = persistance.getFormFileById(formVideo.getImageId());

        final boolean fileExist = (formVideo.getFormVideoId() > 0 && image != null);
        final String fileName = fileExist ? image.getName() : "";
        final String imagePreviewUrl;
        final String imageFullSizeUrl;
        if (fileExist) {
            imagePreviewUrl = ServiceLocator.getResourceGetter().get(ResourceGetterType.PREVIEW_IMAGE_FORM_FILE, image.getFormFileId(), 0, 0, 0, false);
            imageFullSizeUrl = ServiceLocator.getResourceGetter().get(ResourceGetterType.FORM_FILE, image.getFormFileId(), 0, 0, 0, false);
        } else {
            imagePreviewUrl = "";
            imageFullSizeUrl = "";
        }
        final String id = createId(FormItemName.IMAGE_FILE_UPLOAD, widgetId, formItem) + "VideoImageField";

        return new FormFileData(id, widgetId, fileExist, fileName, fileExist, imagePreviewUrl, imageFullSizeUrl, false,
                formVideo.getQuality(), formItem.getItemName(), FormItemName.IMAGE_FILE_UPLOAD, formItem.getFormItemId(),
                formItem.getPosition(), false, filledForm != null ? filledForm.getFilledFormId() : null,
                filledFormItem != null ? filledFormItem.getItemId() : null, FormFileTypeForDeletion.IMAGE, null, null, null, null, null, null, null, null);
    }

    private static String createId(final FormItemName formItemName, final Integer widgetId, final FormItem formItem) {
        return formItemName.toString() + widgetId + formItem.getFormItemId() + formItem.getItemName();
    }

}
