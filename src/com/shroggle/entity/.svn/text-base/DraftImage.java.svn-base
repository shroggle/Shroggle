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

package com.shroggle.entity;

import javax.persistence.*;

@Entity(name = "images1")
public class DraftImage extends DraftItem implements Image1 {

    /**
     * It's widgetId need for backcompatible for link on exists
     * images. If not null fileSystem represent image as WIDGET_IMAGE
     * in other cases as IMAGE1
     */
    private Integer oldId;

    @Column(length = 250)
    private String title;

    private Integer thumbnailWidth;

    /**
     * Thumbnail Roll over and image height
     */
    private Integer thumbnailHeight;

    @Column(length = 10)
    private String extension;

    @Column(length = 10)
    private String rollOverExtension;

    private boolean showDescriptionOnMouseOver;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private TitlePosition titlePosition = TitlePosition.NONE;

    private boolean saveRatio = true;

    private Integer imageId;

    private Integer rollOverImageId;

    private String aligment;

    private int version;

    private boolean labelIsALinnk;

    private boolean imageIsALinnk;

    private boolean customizeWindowSize;

    private int imageFileId;

    private int newWindowWidth;

    private int newWindowHeight;

    @Column(nullable = false)
    private ImageFileType imageFileType = ImageFileType.PDF;

    @Column(nullable = false)
    private ImageLinkType imageLinkType = ImageLinkType.EXTERNAL_URL;

    @Column(nullable = false)
    private ImagePdfDisplaySettings imagePdfDisplaySettings = ImagePdfDisplaySettings.OPEN_IN_NEW_WINDOW;

    @Column(nullable = false)
    private ImageAudioDisplaySettings imageAudioDisplaySettings = ImageAudioDisplaySettings.PLAY_IN_CURRENT_WINDOW;

    @Column(nullable = false)
    private ImageFlashDisplaySettings imageFlashDisplaySettings = ImageFlashDisplaySettings.OPEN_IN_NEW_WINDOW;

    @Lob
    @Column(nullable = false)
    private String externalUrl = "";

    @Column(nullable = false)
    private ExternalUrlDisplaySettings externalUrlDisplaySettings = ExternalUrlDisplaySettings.OPEN_IN_NEW_WINDOW;

    @Column(nullable = false)
    private int internalPageId = -1;

    @Column(nullable = false)
    private InternalPageDisplaySettings internalPageDisplaySettings = InternalPageDisplaySettings.OPEN_IN_NEW_WINDOW;

    @Lob
    @Column(nullable = false)
    private String textArea = "";

    @Column(nullable = false)
    private TextAreaDisplaySettings textAreaDisplaySettings = TextAreaDisplaySettings.OPEN_IN_NEW_WINDOW;

    private int margin;

    @Column(length = 250)
    private String onMouseOverText;

    public String getOnMouseOverText() {
        return onMouseOverText;
    }

    public void setOnMouseOverText(String onMouseOverText) {
        this.onMouseOverText = onMouseOverText;
    }

    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    public String getExternalUrl() {
        return externalUrl;
    }

    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
    }

    public ExternalUrlDisplaySettings getExternalUrlDisplaySettings() {
        return externalUrlDisplaySettings;
    }

    public void setExternalUrlDisplaySettings(ExternalUrlDisplaySettings externalUrlDisplaySettings) {
        this.externalUrlDisplaySettings = externalUrlDisplaySettings;
    }

    public int getInternalPageId() {
        return internalPageId;
    }

    public void setInternalPageId(int internalPageId) {
        this.internalPageId = internalPageId;
    }

    public InternalPageDisplaySettings getInternalPageDisplaySettings() {
        return internalPageDisplaySettings;
    }

    public void setInternalPageDisplaySettings(InternalPageDisplaySettings internalPageDisplaySettings) {
        this.internalPageDisplaySettings = internalPageDisplaySettings;
    }

    public String getTextArea() {
        return textArea;
    }

    public void setTextArea(String textArea) {
        this.textArea = textArea;
    }

    public TextAreaDisplaySettings getTextAreaDisplaySettings() {
        return textAreaDisplaySettings;
    }

    public void setTextAreaDisplaySettings(TextAreaDisplaySettings textAreaDisplaySettings) {
        this.textAreaDisplaySettings = textAreaDisplaySettings;
    }

    public boolean isLabelIsALinnk() {
        return labelIsALinnk;
    }

    public void setLabelIsALinnk(boolean labelIsALinnk) {
        this.labelIsALinnk = labelIsALinnk;
    }

    public boolean isImageIsALinnk() {
        return imageIsALinnk;
    }

    public void setImageIsALinnk(boolean imageIsALinnk) {
        this.imageIsALinnk = imageIsALinnk;
    }

    public boolean isCustomizeWindowSize() {
        return customizeWindowSize;
    }

    public void setCustomizeWindowSize(boolean customizeWindowSize) {
        this.customizeWindowSize = customizeWindowSize;
    }

    public Integer getImageFileId() {
        return imageFileId;
    }

    public void setImageFileId(Integer imageFileId) {
        this.imageFileId = imageFileId != null ? imageFileId : -1;
    }

    public int getNewWindowWidth() {
        return newWindowWidth;
    }

    public void setNewWindowWidth(int newWindowWidth) {
        this.newWindowWidth = newWindowWidth;
    }

    public int getNewWindowHeight() {
        return newWindowHeight;
    }

    public void setNewWindowHeight(int newWindowHeight) {
        this.newWindowHeight = newWindowHeight;
    }

    public ImageLinkType getImageLinkType() {
        return imageLinkType;
    }

    public void setImageLinkType(ImageLinkType imageLinkType) {
        this.imageLinkType = imageLinkType;
    }

    public ImagePdfDisplaySettings getImagePdfDisplaySettings() {
        return imagePdfDisplaySettings;
    }

    public void setImagePdfDisplaySettings(ImagePdfDisplaySettings imagePdfDisplaySettings) {
        this.imagePdfDisplaySettings = imagePdfDisplaySettings;
    }

    public ImageAudioDisplaySettings getImageAudioDisplaySettings() {
        return imageAudioDisplaySettings;
    }

    public void setImageAudioDisplaySettings(ImageAudioDisplaySettings imageAudioDisplaySettings) {
        this.imageAudioDisplaySettings = imageAudioDisplaySettings;
    }

    public ImageFlashDisplaySettings getImageFlashDisplaySettings() {
        return imageFlashDisplaySettings;
    }

    public void setImageFlashDisplaySettings(ImageFlashDisplaySettings imageFlashDisplaySettings) {
        this.imageFlashDisplaySettings = imageFlashDisplaySettings;
    }

    public ImageFileType getImageFileType() {
        return imageFileType;
    }

    public void setImageFileType(ImageFileType imageFileType) {
        this.imageFileType = imageFileType;
    }

    public boolean isSaveRatio() {
        return saveRatio;
    }

    public void setSaveRatio(boolean saveRatio) {
        this.saveRatio = saveRatio;
    }

    public void setAligment(String aligment) {
        this.aligment = aligment;
    }

    public String getAligment() {
        return aligment;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public Integer getRollOverImageId() {
        return rollOverImageId;
    }

    public void setRollOverImageId(Integer rollOverImageId) {
        this.rollOverImageId = rollOverImageId;
    }

    public Integer getImageId() {
        return imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.IMAGE;
    }

    public String getExtension() {
        return extension;
    }

    @Override
    public ResourceType getResourceType() {
        return ResourceType.WIDGET_IMAGE;
    }

    @Override
    public ResourceSize getResourceSize() {
        return ResourceSizeCustom.createByWidthHeight(thumbnailWidth, thumbnailHeight);
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getRollOverExtension() {
        return rollOverExtension;
    }

    public void setRollOverExtension(String rollOverExtension) {
        this.rollOverExtension = rollOverExtension;
    }

    public boolean isShowDescriptionOnMouseOver() {
        return showDescriptionOnMouseOver;
    }

    public void setShowDescriptionOnMouseOver(boolean showDescriptionOnMouseOver) {
        this.showDescriptionOnMouseOver = showDescriptionOnMouseOver;
    }

    public TitlePosition getTitlePosition() {
        return titlePosition;
    }

    public void setTitlePosition(TitlePosition titlePosition) {
        this.titlePosition = titlePosition;
    }

    public int getVersion() {
        return version;
    }

    @Override
    public int getResourceId() {
        return oldId == null ? getId() : oldId;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    public Integer getThumbnailWidth() {
        return thumbnailWidth;
    }

    public void setThumbnailWidth(Integer thumbnailWidth) {
        this.thumbnailWidth = thumbnailWidth;
    }

    public Integer getThumbnailHeight() {
        return thumbnailHeight;
    }

    public void setThumbnailHeight(Integer thumbnailHeight) {
        this.thumbnailHeight = thumbnailHeight;
    }

    public void setOldId(Integer oldId) {
        this.oldId = oldId;
    }

}