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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Important use default settings as in wiki.
 *
 * @author Balakirev Anatoliy
 * @link http://wiki.web-deva.com/display/SRS2/Gallery+Basic+Settings+Defaults
 */
@Entity(name = "galleries")
public class DraftGallery extends DraftItem implements Gallery {

    private Integer formFilterId;

    @Column(name = "formId1")
    private int formId1;

    private int rows = 1;

    private int columns = 8;

    private int thumbnailWidth = 55;

    private int thumbnailHeight = 55;

    private int cellHorizontalMargin = 5;

    private int cellVerticalMargin = 5;

    private int cellWidth = 60;

    private int cellHeight = 60;

    private int cellBorderWidth = 0;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private GalleryOrientation orientation = GalleryOrientation.NAVIGATION_ABOVE_DATA_BELOW;

    /**
     * Containt specific default layout name for selected orintation.
     * Need only for show selected case on configure interface, if system
     * find in field unknown value, it think that default layout = null
     */
    @Column(length = 100)
    private String orientationLayout;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private GallerySortOrder firstSortType = GallerySortOrder.DESCENDING;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private GallerySortOrder secondSortType = GallerySortOrder.ASCENDING;

    private int firstSortItemId = 0;

    private int secondSortItemId = 0;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private GalleryNavigationPaginatorType navigationPaginatorType
            = GalleryNavigationPaginatorType.SCROLL_HORIZONTALLY;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "id.gallery")
    private List<DraftGalleryLabel> labels = new ArrayList<DraftGalleryLabel>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "id.gallery")
    private List<DraftGalleryItem> items = new ArrayList<DraftGalleryItem>();

    /**
     * It's crossWidgetId where navigation show selected element.
     * If its null navigation may show selected item in self or don't show.
     */
    private Integer dataCrossWidgetId;

    @Column(length = 50)
    private String borderStyle;

    @Column(length = 15)
    private String borderColor = "transparent";

    @Column(length = 15)
    private String backgroundColor = "transparent";

    /**
     * This fields used only for create new links on gallery images after changed it.
     */
    private int version;

    @Column(nullable = false)
    private boolean includesVotingModule;

    @Column(nullable = false)
    @Embedded
    private VoteSettings voteSettings = new VoteSettings();

    @Column(nullable = false)
    @Embedded
    private PaypalSettingsForGallery paypalSetings = new PaypalSettingsForGallery();

    @OneToMany(mappedBy = "gallery", cascade = CascadeType.ALL)
    private List<GalleryComment> comments = new ArrayList<GalleryComment>();

    private boolean showOnlyMyRecords;

    @Column(nullable = false)
    @Embedded
    private ChildSiteLink childSiteLink = new ChildSiteLink();

    @Embedded
    private GalleryDataPaginator dataPaginator = new GalleryDataPaginator();

    @Embedded
    private GalleryBackToNavigation backToNavigation = new GalleryBackToNavigation();

    @Transient
    private List<Integer> currentFilledFormHashCodes = new ArrayList<Integer>();

    @Transient
    private List<Integer> fullFilledFormHashCodes = new ArrayList<Integer>();

    @Transient
    private Integer firstFilledFormId;

    private boolean hideEmpty;

    public boolean equals(Object o) {
        return o instanceof DraftGallery && this.getId() == ((DraftGallery) o).getId();
    }

    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.getId();
        return hash;
    }

    public GallerySortOrder getFirstSortType() {
        return firstSortType;
    }

    public void setFirstSortType(GallerySortOrder firstSortType) {
        this.firstSortType = firstSortType;
    }

    public GallerySortOrder getSecondSortType() {
        return secondSortType;
    }

    @Override
    public GalleryDataPaginator getDataPaginator() {
        return dataPaginator;
    }

    @Override
    public void setDataPaginator(final GalleryDataPaginator dataPaginator) {
        this.dataPaginator = dataPaginator;
    }

    public void setSecondSortType(GallerySortOrder secondSortType) {
        this.secondSortType = secondSortType;
    }

    public int getFormId1() {
        return formId1;
    }

    public void setFormId1(int formId) {
        this.formId1 = formId;
    }

    public GalleryOrientation getOrientation() {
        return orientation;
    }

    public void setOrientation(GalleryOrientation orientation) {
        this.orientation = orientation;
    }

    public ItemType getItemType() {
        return ItemType.GALLERY;
    }

    public List<GalleryLabel> getLabels() {
        return (List) Collections.unmodifiableList(labels);
    }

    public void addLabel(final GalleryLabel label) {
        label.getId().setGallery(this);
        labels.add((DraftGalleryLabel) label);
    }

    public void addItem(final GalleryItem item) {
        item.getId().setGallery(this);
        items.add((DraftGalleryItem) item);
    }

    public int getSecondSortItemId() {
        return secondSortItemId;
    }

    public void setSecondSortItemId(int secondSortItemId) {
        this.secondSortItemId = secondSortItemId;
    }

    public int getFirstSortItemId() {
        return firstSortItemId;
    }

    public List<GalleryItem> getItems() {
        return (List) items;
    }

    public void setFirstSortItemId(int firstSortItemId) {
        this.firstSortItemId = firstSortItemId;
    }

    public void removeLabel(final GalleryLabel label) {
        labels.remove((DraftGalleryLabel) label);
    }

    public void removeItem(final GalleryItem item) {
        items.remove((DraftGalleryItem) item);
    }

    public GalleryNavigationPaginatorType getNavigationPaginatorType() {
        return navigationPaginatorType;
    }

    public void setNavigationPaginatorType(final GalleryNavigationPaginatorType navigationPaginatorType) {
        this.navigationPaginatorType = navigationPaginatorType;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getThumbnailHeight() {
        return thumbnailHeight;
    }

    public void setThumbnailHeight(int thumbnailHeight) {
        this.thumbnailHeight = thumbnailHeight;
    }

    public int getCellHorizontalMargin() {
        return cellHorizontalMargin;
    }

    public void setCellHorizontalMargin(final int cellHorizontalMargin) {
        this.cellHorizontalMargin = cellHorizontalMargin;
    }

    public int getCellVerticalMargin() {
        return cellVerticalMargin;
    }

    public void setCellVerticalMargin(final int cellVerticalMargin) {
        this.cellVerticalMargin = cellVerticalMargin;
    }

    public int getCellWidth() {
        return cellWidth;
    }

    public void setCellWidth(final int cellWidth) {
        this.cellWidth = cellWidth;
    }

    public int getCellHeight() {
        return cellHeight;
    }

    public void setCellHeight(final int cellHeight) {
        this.cellHeight = cellHeight;
    }

    public int getCellBorderWidth() {
        return cellBorderWidth;
    }

    public void setCellBorderWidth(final int cellBorderWidth) {
        this.cellBorderWidth = cellBorderWidth;
    }

    public int getThumbnailWidth() {
        return thumbnailWidth;
    }

    public void setThumbnailWidth(final int thumbnailWidth) {
        this.thumbnailWidth = thumbnailWidth;
    }

    public String getOrientationLayout() {
        return orientationLayout;
    }

    public void setOrientationLayout(final String orientationLayout) {
        this.orientationLayout = orientationLayout;
    }

    public Integer getDataCrossWidgetId() {
        return dataCrossWidgetId;
    }

    public void setDataCrossWidgetId(final Integer dataCrossWidgetId) {
        this.dataCrossWidgetId = dataCrossWidgetId;
    }

    public String getBorderStyle() {
        return borderStyle;
    }

    public void setBorderStyle(String borderStyle) {
        this.borderStyle = borderStyle;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(final String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    public Integer getFormFilterId() {
        return formFilterId;
    }

    public void setFormFilterId(Integer formFilterId) {
        this.formFilterId = formFilterId;
    }

    public GalleryBackToNavigation getBackToNavigation() {
        return backToNavigation;
    }

    public void setBackToNavigation(GalleryBackToNavigation backToNavigation) {
        this.backToNavigation = backToNavigation;
    }

    /**
     * This method used for correct copy gallery object over Copier
     *
     * @param items = items
     * @see com.shroggle.util.copier.CopierWorker
     */
    public void setItems(final List<GalleryItem> items) {
        this.items = (List) items;
    }

    /**
     * This method used for correct copy gallery object over Copier
     *
     * @param labels - labels
     * @see com.shroggle.util.copier.CopierWorker
     */
    public void setLabels(final List<GalleryLabel> labels) {
        this.labels = (List) labels;
    }

    public Integer getFirstFilledFormId() {
        return firstFilledFormId;
    }

    public void setFirstFilledFormId(Integer firstFilledFormId) {
        this.firstFilledFormId = firstFilledFormId;
    }

    public boolean isIncludesVotingModule() {
        return includesVotingModule;
    }

    public void setIncludesVotingModule(boolean includesVotingModule) {
        this.includesVotingModule = includesVotingModule;
    }

    public VoteSettings getVoteSettings() {
        return voteSettings;
    }

    public void setVoteSettings(VoteSettings voteSettings) {
        this.voteSettings = voteSettings;
    }

    public boolean isShowOnlyMyRecords() {
        return showOnlyMyRecords;
    }

    public void setShowOnlyMyRecords(boolean showOnlyMyRecords) {
        this.showOnlyMyRecords = showOnlyMyRecords;
    }

    public ChildSiteLink getChildSiteLink() {
        return childSiteLink;
    }

    public void setChildSiteLink(ChildSiteLink childSiteLink) {
        this.childSiteLink = childSiteLink;
    }

    public boolean isHideEmpty() {
        return hideEmpty;
    }

    public void setHideEmpty(boolean hideEmpty) {
        this.hideEmpty = hideEmpty;
    }

    public List<Integer> getCurrentFilledFormHashCodes() {
        return currentFilledFormHashCodes;
    }

    public void setCurrentFilledFormHashCodes(List<Integer> currentFilledFormHashCodes) {
        this.currentFilledFormHashCodes = currentFilledFormHashCodes;
    }

    public List<Integer> getFullFilledFormHashCodes() {
        return fullFilledFormHashCodes;
    }

    public void setFullFilledFormHashCodes(List<Integer> fullFilledFormHashCodes) {
        this.fullFilledFormHashCodes = fullFilledFormHashCodes;
    }

    public PaypalSettingsForGallery getPaypalSettings() {
        return paypalSetings == null ? new PaypalSettingsForGallery() : paypalSetings;
    }

    public void setPaypalSettings(PaypalSettingsForGallery paypalSetings) {
        this.paypalSetings = paypalSetings;
    }

    public boolean isIncludeECommerce() {
        return paypalSetings != null && paypalSetings.isEnable();
    }
}