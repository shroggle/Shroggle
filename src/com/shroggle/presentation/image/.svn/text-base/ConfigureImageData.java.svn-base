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
package com.shroggle.presentation.image;

import com.shroggle.entity.*;
import com.shroggle.logic.image.ImageManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.page.PagesWithoutSystem;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.resource.ResourceGetterUrl;
import com.shroggle.util.resource.provider.ResourceGetterType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 *         Date: 15.09.2009
 */
public class ConfigureImageData {

    public ConfigureImageData(final DraftImage imageItem, final Integer widgetId) {
        if (imageItem == null) {
            throw new IllegalArgumentException();
        }

        this.imageItem = imageItem;
        this.widgetId = widgetId;
    }

    private final DraftImage imageItem;
    private final Integer widgetId;

    public String getImageUrl() {
        if (ImageManager.imageFileExist(imageItem.getImageId())) {
            final ResourceGetterUrl resourceGetterUrl = ServiceLocator.getResourceGetter();
            return resourceGetterUrl.get(ResourceGetterType.WIDGET_IMAGE, imageItem.getId(), 0, 0, imageItem.getVersion(), false);
        }
        return "undefined";
    }

    public String getRollOverImageUrl() {
        if (ImageManager.imageFileExist(imageItem.getRollOverImageId())) {
            final ResourceGetterUrl resourceGetterUrl = ServiceLocator.getResourceGetter();
            return resourceGetterUrl.get(ResourceGetterType.WIDGET_IMAGE_ROLLOVER, imageItem.getId(), 0, 0, imageItem.getVersion(), false);
        }
        return "undefined";
    }

    public int getMargin() {
        return imageItem.getMargin();
    }

    public List<PageManager> getPageManagers() {
        final List<PageManager> versions = new ArrayList<PageManager>();
        for (Page page : PagesWithoutSystem.get(persistance.getSite(imageItem.getSiteId()).getPages())) {
            versions.add(new PageManager(page));
        }
        return versions;
    }

    // selectedImageFileId is the file that selected image item contains. We are adding it explicitly
    // to ensure that we will not have any problems with image items inserted from other site.

    public List<ImageFile> createImageFiles(final ImageFileType type, final Integer selectedImageFileId) {
        final List<ImageFile> imageFiles = new ArrayList<ImageFile>();
        final List<ImageFile> imageFilesFromSite = persistance.getImageFilesByTypeAndSiteId(type, getSiteId());

        if (selectedImageFileId != null && selectedImageFileId != 0) {
            boolean imageFilesFromSiteContainsSelectedImageFile = false;
            for (ImageFile imageFile : imageFilesFromSite) {
                if (imageFile.getImageFileId() == selectedImageFileId) {
                    imageFilesFromSiteContainsSelectedImageFile = true;
                }
            }
            if (!imageFilesFromSiteContainsSelectedImageFile) {
                final ImageFile imageFile = persistance.getImageFileById(selectedImageFileId);

                if (imageFile != null) {
                    imageFiles.add(imageFile);
                }
            }
        }

        imageFiles.addAll(imageFilesFromSite);
        Collections.sort(imageFiles, new Comparator<ImageFile>() {
            public int compare(final ImageFile image1, final ImageFile image2) {
                return image2.getCreated().compareTo(image1.getCreated());
            }
        });
        return imageFiles;
    }

    public String getTitle() {
        if (imageItem.isShowDescriptionOnMouseOver()) {
            return imageItem.getDescription();
        } else {
            return imageItem.getTitle();
        }
    }


    /*----------------------------------------------------getters-----------------------------------------------------*/

    public boolean isSaveRatio() {
        return imageItem.isSaveRatio();
    }

    public Integer getWidth() {
        return imageItem.getThumbnailWidth();
    }

    public Integer getHeight() {
        return imageItem.getThumbnailHeight();
    }

    public String getAligment() {
        return imageItem.getAligment();
    }

    public String getWidgetImageTitle() {
        return StringUtil.getEmptyOrString(imageItem.getTitle());
    }

    public boolean isLabelCheckboxesDisabled() {
        return StringUtil.isNullOrEmpty(getWidgetImageTitle());
    }

    public String getDescription() {
        return StringUtil.getEmptyOrString(imageItem.getDescription());
    }

    public String getOnMouseOverText() {
        return StringUtil.getEmptyOrString(imageItem.getOnMouseOverText());
    }

    public boolean isDescriptionOnMouseOver() {
        return imageItem.isShowDescriptionOnMouseOver();
    }

    public Integer getImageId() {
        return imageItem.getImageId();
    }

    public Integer getRollOverImageId() {
        return imageItem.getRollOverImageId();
    }

    public Integer getSiteId() {
        if (widgetId != null) {
            return ServiceLocator.getPersistance().getWidget(widgetId).getSiteId();
        }

        return imageItem.getSiteId();
    }

    public Integer getWidgetId() {
        return widgetId;
    }

    public ImageFileType getImageFileType() {
        return imageItem.getImageFileType();
    }

    public Integer getImageFileId() {
        return imageItem.getImageFileId();
    }

    public TitlePosition getTitlePosition() {
        return imageItem.getTitlePosition();
    }

    public boolean isLabelIsALinnk() {
        return imageItem.isLabelIsALinnk();
    }

    public boolean isImageIsALinnk() {
        return imageItem.isImageIsALinnk();
    }

    public boolean isDisableLinksArea() {
        return !isLabelIsALinnk() && !isImageIsALinnk();
    }

    public ImageLinkType getImageLinkType() {
        return imageItem.getImageLinkType();
    }

    public String getExternalUrl() {
        return StringUtil.getEmptyOrString(imageItem.getExternalUrl());
    }

    public ExternalUrlDisplaySettings getExternalUrlDisplaySettings() {
        return imageItem.getExternalUrlDisplaySettings();
    }

    public int getInternalPageId() {
        return imageItem.getInternalPageId();
    }

    public InternalPageDisplaySettings getInternalPageDisplaySettings() {
        return imageItem.getInternalPageDisplaySettings();
    }

    public ImagePdfDisplaySettings getImagePdfDisplaySettings() {
        return imageItem.getImagePdfDisplaySettings();
    }

    public ImageAudioDisplaySettings getImageAudioDisplaySettings() {
        return imageItem.getImageAudioDisplaySettings();
    }

    public ImageFlashDisplaySettings getImageFlashDisplaySettings() {
        return imageItem.getImageFlashDisplaySettings();
    }

    public boolean isCustomizeWindowSize() {
        return imageItem.isCustomizeWindowSize();
    }

    public int getNewWindowWidth() {
        return imageItem.getNewWindowWidth();
    }

    public int getNewWindowHeight() {
        return imageItem.getNewWindowHeight();
    }

    public TextAreaDisplaySettings getTextAreaDisplaySettings() {
        return imageItem.getTextAreaDisplaySettings();
    }

    public String getName() {
        return StringUtil.getEmptyOrString(imageItem.getName());
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
}
