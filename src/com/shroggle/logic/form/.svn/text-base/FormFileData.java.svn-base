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

import com.shroggle.entity.FormItemName;

/**
 * @author Balakirev Anatoliy
 */
public class FormFileData {

    public FormFileData(String id, int widgetId, boolean fileExist, String fileName, boolean showImagePreview,
                        String imagePreviewUrl, String imageFullSizeUrl, boolean showItemName, int videoQuality, String itemName,
                        FormItemName formItemName, int formItemId, int position, boolean required, Integer filledFormId,
                        Integer filledFormItemId, FormFileTypeForDeletion formFileTypeForDeletion, Integer sourceVideoWidth,
                        Integer sourceVideoHeight, String flvVideoUrl, String sourceVideoUrl, String videoImageUrl,
                        String videoImageUrlId, String videoStatus, String keywords) {
        this.id = id;
        this.widgetId = widgetId;
        this.fileExist = fileExist;
        this.fileName = fileName;
        this.showImagePreview = showImagePreview;
        this.imagePreviewUrl = imagePreviewUrl;
        this.imageFullSizeUrl = imageFullSizeUrl;
        this.showItemName = showItemName;
        this.videoQuality = videoQuality;
        this.itemName = itemName;
        this.formItemName = formItemName;
        this.formItemId = formItemId;
        this.position = position;
        this.required = required;
        this.filledFormId = filledFormId;
        this.filledFormItemId = filledFormItemId;
        this.formFileTypeForDeletion = formFileTypeForDeletion;
        this.sourceVideoWidth = sourceVideoWidth;
        this.sourceVideoHeight = sourceVideoHeight;
        this.flvVideoUrl = flvVideoUrl;
        this.sourceVideoUrl = sourceVideoUrl;
        this.videoImageUrl = videoImageUrl;
        this.videoImageUrlId = videoImageUrlId;
        this.videoStatus = videoStatus;
        this.keywords = keywords;
    }

    private final String id;

    private final int widgetId;

    private final boolean fileExist;

    private final String fileName;

    private final boolean showImagePreview;

    private final String imagePreviewUrl;

    private final String imageFullSizeUrl;

    private final boolean showItemName;

    private final int videoQuality;

    private Integer flvVideoId;

    /*------------------------------------------------form item fields------------------------------------------------*/
    private final String itemName;

    private final FormItemName formItemName;

    private final int formItemId;

    private final int position;

    private final boolean required;

    private final Integer filledFormId;

    private final Integer filledFormItemId;
    /*------------------------------------------------form item fields------------------------------------------------*/

    private final FormFileTypeForDeletion formFileTypeForDeletion;

    private final Integer sourceVideoWidth;

    private final Integer sourceVideoHeight;

    private final String flvVideoUrl;

    private final String sourceVideoUrl;

    private final String videoImageUrl;

    private final String videoImageUrlId;

    private final String videoStatus;

    private final String keywords;

    public String getVideoStatus() {
        return videoStatus;
    }

    public String getImageFullSizeUrl() {
        return imageFullSizeUrl;
    }

    public String getVideoImageUrlId() {
        return videoImageUrlId;
    }

    public Integer getSourceVideoWidth() {
        return sourceVideoWidth;
    }

    public Integer getSourceVideoHeight() {
        return sourceVideoHeight;
    }

    public String getFlvVideoUrl() {
        return flvVideoUrl;
    }

    public String getSourceVideoUrl() {
        return sourceVideoUrl;
    }

    public String getVideoImageUrl() {
        return videoImageUrl;
    }

    public FormFileTypeForDeletion getFormFileTypeForDeletion() {
        return formFileTypeForDeletion;
    }

    public String getId() {
        return id.replace(" ", "").replace("(", "").replace(")", "");
    }

    public String getBulkUploaderId() {
        return getId() + "Bulk";
    }

    public int getWidgetId() {
        return widgetId;
    }

    public boolean isFileExist() {
        return fileExist;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isShowImagePreview() {
        return showImagePreview;
    }

    public String getImagePreviewUrl() {
        return imagePreviewUrl;
    }

    public boolean isShowItemName() {
        return showItemName;
    }

    public int getVideoQuality() {
        return videoQuality;
    }

    public String getItemName() {
        return itemName;
    }

    public FormItemName getFormItemName() {
        return formItemName;
    }

    public boolean isImage() {
        return formItemName == FormItemName.IMAGE_FILE_UPLOAD;
    }

    public int getFormItemId() {
        return formItemId;
    }

    public int getPosition() {
        return position;
    }

    public boolean isRequired() {
        return required;
    }

    public Integer getFilledFormId() {
        return filledFormId;
    }

    public Integer getFilledFormItemId() {
        return filledFormItemId;
    }

    public Integer getFlvVideoId() {
        return flvVideoId;
    }

    public void setFlvVideoId(Integer flvVideoId) {
        this.flvVideoId = flvVideoId;
    }

    public String getKeywords() {
        return keywords;
    }

    public boolean showKeywordsField() {
        return keywords != null;
    }
}
