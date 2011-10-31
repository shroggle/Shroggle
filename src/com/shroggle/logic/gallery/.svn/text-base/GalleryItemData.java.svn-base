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

import com.shroggle.entity.*;
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.logic.form.FilledFormItemManager;
import com.shroggle.logic.video.FlvVideoManager;
import com.shroggle.util.Dimension;
import com.shroggle.util.ResourceSizeCreator;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.html.HtmlUtil;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.resource.ResourceGetterUrl;
import com.shroggle.util.resource.provider.ResourceGetterType;

import java.util.Locale;

/**
 * @author Artem Stasuk
 */
public class GalleryItemData {

    public GalleryItemData(final FilledForm filledForm, final GalleryItem item, final int siteId) {
        Persistance persistance = ServiceLocator.getPersistance();
        this.filledFormItem = FilledFormManager.getFilledFormItemByFormItemId(filledForm, item.getId().getFormItemId());
        this.item = item;
        this.siteId = siteId;
        type = GalleryItemType.GALLERY_DATA_ITEM;
        formItem = persistance.getFormItemById(item != null ? item.getId().getFormItemId() : null);
        normalFlvVideo = getNormalFlvVideo();
        largeFlvVideo = getLargeFlvVideo();
        row = item.getRow();
        column = item.getColumn();
        position = item.getPosition();
        /*-------------------------------------------------image size-------------------------------------------------*/
        if (filledFormItem != null && filledFormItem.getFormItemName() == FormItemName.IMAGE_FILE_UPLOAD) {
            FormFile formFile = ServiceLocator.getPersistance().getFormFileById(FilledFormItemManager.getIntValue(filledFormItem));
            sourceWidth = ((formFile != null && formFile.getWidth() != null) ? formFile.getWidth() : (item.getWidth() != null ? item.getWidth() : 0));
            sourceHeight = ((formFile != null && formFile.getHeight() != null) ? formFile.getHeight() : (item.getHeight() != null ? item.getHeight() : 0));
            Dimension dimension = ResourceSizeCreator.execute(item.getWidth(), item.getHeight(), sourceWidth, sourceHeight, true);
            resizedWidth = dimension.getWidth();
            resizedHeight = dimension.getHeight();
            dataItemWidth = item.getWidth();
            dataItemHeight = item.getHeight();
        }
        /*-------------------------------------------------image size-------------------------------------------------*/
    }

    public GalleryItemData(final GalleryItemType type, final int row, final GalleryItemColumn column, final int position, final int siteId) {
        this.type = type;
        this.row = row;
        this.column = column;
        this.position = position;
        this.siteId = siteId;
        filledFormItem = null;
        item = null;
        formItem = null;
        normalFlvVideo = null;
        largeFlvVideo = null;
    }

    public String getValue() {
        if (formItem == null || filledFormItem == null || filledFormItem.getValues().isEmpty()) {
            return "";
        }
        return new FilledFormItemManager(filledFormItem).getFormattedValue(siteId);
    }

    public String getResizedImageUrl() {
        if (formItem == null || filledFormItem == null || filledFormItem.getValues().isEmpty() ||
                formItem.getFormItemName() != FormItemName.IMAGE_FILE_UPLOAD ||
                !ServiceLocator.getFileSystem().isResourceExist(ServiceLocator.getPersistance().getFormFileById(FilledFormItemManager.getIntValue(filledFormItem)))) {
            return "";
        }
        final GalleryItemId itemId = item.getId();
        return ServiceLocator.getResourceGetter().get(
                ResourceGetterType.GALLERY_DATA, FilledFormItemManager.getIntValue(filledFormItem),
                itemId.getGallery().getId(), itemId.getFormItemId(),
                itemId.getGallery().getVersion(), false);
    }

    public String getImageAltAndTitle() {
        if (filledFormItem != null && filledFormItem.getFormItemName() == FormItemName.IMAGE_FILE_UPLOAD) {
            final FilledFormItemManager manager = new FilledFormItemManager(filledFormItem);
            final String keywords = manager.getFormImageAlt();
            if (!keywords.isEmpty()) {
                return keywords;
            } else {
                return item != null ? StringUtil.getEmptyOrString(item.getName()) : "";
            }
        }
        return "";
    }

    public String getSourceImageUrl() {
        if (formItem == null || filledFormItem == null || filledFormItem.getValues().isEmpty() ||
                formItem.getFormItemName() != FormItemName.IMAGE_FILE_UPLOAD) {
            return "";
        }
        final ResourceGetterUrl resourceGetterUrl = ServiceLocator.getResourceGetter();
        int version = (int) System.currentTimeMillis();
        return resourceGetterUrl.get(
                ResourceGetterType.GALLERY_DATA_SOURCE_SIZE, FilledFormItemManager.getIntValue(filledFormItem), 0, 0, version, false);
    }

    public Dimension getSourceImageDimension() {
        if (sourceWidth > FormFile.MAX_SOURCE_IMAGE_WIDTH || sourceHeight > FormFile.MAX_SOURCE_IMAGE_HEIGHT) {
            return ResourceSizeCreator.execute(FormFile.MAX_SOURCE_IMAGE_WIDTH, FormFile.MAX_SOURCE_IMAGE_HEIGHT, sourceWidth, sourceHeight, true);
        }
        return new Dimension(sourceWidth, sourceHeight, 0, 0);
    }

    public int getNormalVideoFlvId() {
        if (normalFlvVideo != null) {
            return normalFlvVideo.getFlvVideoId();
        } else {
            return -1;
        }
    }

    public int getLargeVideoFlvId() {
        if (largeFlvVideo != null) {
            return largeFlvVideo.getFlvVideoId();
        } else {
            return -1;
        }
    }

    public String getNormalVideoStatus() {
        return new FlvVideoManager(normalFlvVideo).getFlvVideoStatusAndStartNewConversionIfNeeded();
    }

    public String getLargeVideoStatus() {
        return new FlvVideoManager(largeFlvVideo).getFlvVideoStatusAndStartNewConversionIfNeeded();
    }

    public String getNormalVideoUrl() {
        if (normalFlvVideo != null) {
            return HtmlUtil.encodeToPercent(ResourceGetterType.FLV_VIDEO.getVideoUrl(normalFlvVideo));
        } else {
            return "";
        }
    }

    public String getLargeVideoUrl() {
        if (largeFlvVideo != null) {
            return HtmlUtil.encodeToPercent(ResourceGetterType.FLV_VIDEO.getVideoUrl(largeFlvVideo));
        } else {
            return "";
        }
    }

    public String getVideoImageUrl() {
        if (filledFormItem != null && !filledFormItem.getValues().isEmpty() &&
                filledFormItem.getFormItemName() == FormItemName.VIDEO_FILE_UPLOAD) {
            final Persistance persistance = ServiceLocator.getPersistance();
            FormVideo formVideo = persistance.getFormVideoById(FilledFormItemManager.getIntValue(filledFormItem));
            if (formVideo != null) {
                FormFile formFile = persistance.getFormFileById(formVideo.getImageId());
                if (formFile != null) {
                    final ResourceGetterUrl resourceGetterUrl = ServiceLocator.getResourceGetter();
                    return HtmlUtil.encodeToPercent(resourceGetterUrl.get(ResourceGetterType.FORM_FILE, formFile.getFormFileId(), 0, 0, 0, false));
                }
            }
        }
        return "";
    }

    public int getNormalVideoWidth() {
        return item.getWidth();
    }

    public int getNormalVideoHeight() {
        return item.getHeight();
    }

    public Integer getLargeVideoWidth() {
        final Dimension dimension = FlvVideoManager.createLargeVideoDimension(getNormalVideoWidth(), getNormalVideoHeight());
        return dimension != null ? dimension.getWidth() : null;
    }

    public Integer getLargeVideoHeight() {
        final Dimension dimension = FlvVideoManager.createLargeVideoDimension(getNormalVideoWidth(), getNormalVideoHeight());
        return dimension != null ? dimension.getHeight() : null;
    }

    public GalleryItem getItem() {
        return item;
    }

    public boolean isImage() {
        return formItem != null && formItem.getFormItemName() == FormItemName.IMAGE_FILE_UPLOAD;
    }

    public boolean isHr() {
        return formItem != null && formItem.getFormItemName() == FormItemName.LINE_HR;
    }

    public boolean isHeader() {
        return formItem != null && formItem.getFormItemName() == FormItemName.HEADER;
    }

    public boolean isTextArea() {
        return formItem != null && (formItem.getFormItemName().getType() == FormItemType.TEXT_AREA ||
                formItem.getFormItemName().getType() == FormItemType.TEXT_AREA_DOUBLE_SIZE);
    }

    public String getTextAreaValue() {
        if (formItem == null || filledFormItem == null || filledFormItem.getValues().isEmpty()) {
            return "";
        }
        return filledFormItem.getValues().get(0).replace("\n", "<br>");
    }

    public String getHeader() {
        return formItem != null ? formItem.getInstruction() : "";
    }

    public boolean isVideo() {
        return filledFormItem != null && filledFormItem.getFormItemName() == FormItemName.VIDEO_FILE_UPLOAD;
    }

    public int getResizedHeight() {
        return resizedHeight;
    }

    public int getResizedWidth() {
        return resizedWidth;
    }

    public GalleryItemType getType() {
        return type;
    }

    public int getRow() {
        if (row != null) {
            return row;
        }
        return item.getRow();
    }

    public GalleryItemColumn getColumn() {
        if (column != null) {
            return column;
        }
        return item.getColumn();
    }

    public International getInternational() {
        return international;
    }

    public boolean isFilledFormItemExist() {
        return filledFormItem != null;
    }

    private FlvVideo getNormalFlvVideo() {
        if (filledFormItem != null && !filledFormItem.getValues().isEmpty() &&
                filledFormItem.getFormItemName() == FormItemName.VIDEO_FILE_UPLOAD) {
            final Persistance persistance = ServiceLocator.getPersistance();
            FormVideo formVideo = persistance.getFormVideoById(FilledFormItemManager.getIntValue(filledFormItem));
            if (formVideo != null) {
                return FlvVideoManager.getFlvVideoOrCreateNew(formVideo.getVideoId(), item.getWidth(), item.getHeight(),
                        formVideo.getQuality(), item.getId().getGallery().getSiteId());
            }
        }
        return null;
    }

    private FlvVideo getLargeFlvVideo() {
        if (filledFormItem != null && !filledFormItem.getValues().isEmpty() &&
                filledFormItem.getFormItemName() == FormItemName.VIDEO_FILE_UPLOAD) {
            final Persistance persistance = ServiceLocator.getPersistance();
            FormVideo formVideo = persistance.getFormVideoById(FilledFormItemManager.getIntValue(filledFormItem));
            if (formVideo != null) {
                final Dimension dimension = FlvVideoManager.createLargeVideoDimension(getNormalVideoWidth(), getNormalVideoHeight());
                if (dimension != null) {
                    return FlvVideoManager.getFlvVideoOrCreateNew(formVideo.getVideoId(), dimension.getWidth(),
                            dimension.getHeight(), formVideo.getQuality(), item.getId().getGallery().getSiteId());
                }
            }
        }
        return null;
    }

    public Integer getPosition() {
        return position;
    }

    public int getSiteId() {
        return siteId;
    }

    public int getDataItemWidth() {
        return dataItemWidth;
    }

    public int getDataItemHeight() {
        return dataItemHeight;
    }

    private int sourceWidth;
    private int sourceHeight;
    private int resizedWidth;
    private int resizedHeight;
    private int dataItemWidth;
    private int dataItemHeight;
    private final FilledFormItem filledFormItem;
    private final GalleryItem item;
    private final GalleryItemColumn column;
    private final Integer row;
    private final Integer position;
    private final GalleryItemType type;
    private final DraftFormItem formItem;
    private final FlvVideo normalFlvVideo;
    private final FlvVideo largeFlvVideo;
    private final int siteId;
    private final International international = ServiceLocator.getInternationStorage().get("renderWidgetGalleryData", Locale.US);

}
