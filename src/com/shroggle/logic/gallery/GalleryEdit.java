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
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Artem Stasuk
 */
@DataTransferObject
public class GalleryEdit {

    public int getFirstSort() {
        return firstSort;
    }

    public void setFirstSort(int firstSort) {
        this.firstSort = firstSort;
    }

    public int getSecondSort() {
        return secondSort;
    }

    public void setSecondSort(int secondSort) {
        this.secondSort = secondSort;
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

    public void setSecondSortType(GallerySortOrder secondSortType) {
        this.secondSortType = secondSortType;
    }

    public List<GalleryItemEdit> getItems() {
        return items;
    }

    List<GalleryItemEdit> getItemsWithoutDuplicate() {
        return getWithoutDuplicate(items);
    }

    List<GalleryLabelEdit> getLabelsWithoutDuplicate() {
        return getWithoutDuplicate(labels);
    }

    <T extends GalleryIdedEdit> List<T> getWithoutDuplicate(final List<T> items) {
        final List<T> itemsWithoutDuplicate = new ArrayList<T>();
        final Set<Integer> alredyAdded = new HashSet<Integer>();
        for (final T item : items) {
            if (!alredyAdded.contains(item.getId())) {
                itemsWithoutDuplicate.add(item);
                alredyAdded.add(item.getId());
            }
        }
        return itemsWithoutDuplicate;
    }

    public List<GalleryLabelEdit> getLabels() {
        return labels;
    }

    public void setLabels(List<GalleryLabelEdit> labels) {
        this.labels = labels;
    }

    public void setItems(List<GalleryItemEdit> items) {
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotesComments() {
        return notesComments;
    }

    public void setNotesComments(String notesComments) {
        this.notesComments = notesComments;
    }

    public Integer getFormId() {
        return formId;
    }

    public void setFormId(Integer formId) {
        this.formId = formId;
    }

    public GalleryNavigationPaginatorType getNavigationPaginatorType() {
        return navigationPaginatorType;
    }

    public void setNavigationPaginatorType(final GalleryNavigationPaginatorType navigationPaginatorType) {
        this.navigationPaginatorType = navigationPaginatorType;
    }

    public int getCellBorderWidth() {
        return cellBorderWidth;
    }

    public void setCellBorderWidth(int cellBorderWidth) {
        this.cellBorderWidth = cellBorderWidth;
    }

    public int getCellHeight() {
        return cellHeight;
    }

    public void setCellHeight(int cellHeight) {
        this.cellHeight = cellHeight;
    }

    public int getCellWidth() {
        return cellWidth;
    }

    public void setCellWidth(int cellWidth) {
        this.cellWidth = cellWidth;
    }

    public int getCellVerticalMargin() {
        return cellVerticalMargin;
    }

    public void setCellVerticalMargin(int cellVerticalMargin) {
        this.cellVerticalMargin = cellVerticalMargin;
    }

    public int getCellHorizontalMargin() {
        return cellHorizontalMargin;
    }

    public void setCellHorizontalMargin(int cellHorizontalMargin) {
        this.cellHorizontalMargin = cellHorizontalMargin;
    }

    public int getThumbnailHeight() {
        return thumbnailHeight;
    }

    public void setThumbnailHeight(int thumbnailHeight) {
        this.thumbnailHeight = thumbnailHeight;
    }

    public int getThumbnailWidth() {
        return thumbnailWidth;
    }

    public void setThumbnailWidth(int thumbnailWidth) {
        this.thumbnailWidth = thumbnailWidth;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public GalleryOrientation getOrientation() {
        return orientation;
    }

    public void setOrientation(final GalleryOrientation orientation) {
        this.orientation = orientation;
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

    public void setDataCrossWidgetId(Integer dataCrossWidgetId) {
        this.dataCrossWidgetId = dataCrossWidgetId;
    }

    public Integer getDataPageId() {
        return dataPageId;
    }

    public void setDataPageId(Integer dataPageId) {
        this.dataPageId = dataPageId;
    }

    public Integer getDataWidgetId() {
        return dataWidgetId;
    }

    public void setDataWidgetId(Integer dataWidgetId) {
        this.dataWidgetId = dataWidgetId;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
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

    public String getBorderStyle() {
        return borderStyle;
    }

    public void setBorderStyle(String borderStyle) {
        this.borderStyle = borderStyle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getFormFilterId() {
        return formFilterId;
    }

    public void setFormFilterId(Integer formFilterId) {
        this.formFilterId = formFilterId;
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

    public VoteStarsLinks getVoteStars() {
        return voteStars;
    }

    public void setVoteStars(VoteStarsLinks voteStars) {
        this.voteStars = voteStars;
    }

    public VoteStarsLinks getVoteLinks() {
        return voteLinks;
    }

    public void setVoteLinks(VoteStarsLinks voteLinks) {
        this.voteLinks = voteLinks;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public boolean isShowOnlyMyRecords() {
        return showOnlyMyRecords;
    }

    public void setShowOnlyMyRecords(boolean showOnlyMyRecords) {
        this.showOnlyMyRecords = showOnlyMyRecords;
    }

    public ChildSiteLinkData getChildSiteLinkData() {
        return childSiteLinkData;
    }

    public void setChildSiteLinkData(ChildSiteLinkData childSiteLinkData) {
        this.childSiteLinkData = childSiteLinkData;
    }

    public boolean isShowNotesComments() {
        return showNotesComments;
    }

    public void setShowNotesComments(boolean showNotesComments) {
        this.showNotesComments = showNotesComments;
    }

    public String getVotingStartDateString() {
        return votingStartDateString;
    }

    public void setVotingStartDateString(String votingStartDateString) {
        this.votingStartDateString = votingStartDateString;
    }

    public String getVotingEndDateString() {
        return votingEndDateString;
    }

    public void setVotingEndDateString(String votingEndDateString) {
        this.votingEndDateString = votingEndDateString;
    }

    public GalleryBackToNavigation getBackToNavigation() {
        return backToNavigation;
    }

    public void setBackToNavigation(GalleryBackToNavigation backToNavigation) {
        this.backToNavigation = backToNavigation;
    }

    public boolean isHideEmpty() {
        return hideEmpty;
    }

    public void setHideEmpty(boolean hideEmpty) {
        this.hideEmpty = hideEmpty;
    }

    public PaypalSettingsData getPaypalSettings() {
        return paypalSettings;
    }

    public void setPaypalSettings(PaypalSettingsData paypalSettings) {
        this.paypalSettings = paypalSettings;
    }

    public GalleryDataPaginator getDataPaginator() {
        return dataPaginator;
    }

    public void setDataPaginator(GalleryDataPaginator dataPaginator) {
        this.dataPaginator = dataPaginator;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    @RemoteProperty
    private int firstSort;

    @RemoteProperty
    private int secondSort;

    @RemoteProperty
    private String name;

    @RemoteProperty
    private String notesComments;

    @RemoteProperty
    private Integer formId;

    @RemoteProperty
    private GallerySortOrder firstSortType;

    @RemoteProperty
    private GallerySortOrder secondSortType;

    @RemoteProperty
    private List<GalleryItemEdit> items = new ArrayList<GalleryItemEdit>();

    @RemoteProperty
    private List<GalleryLabelEdit> labels = new ArrayList<GalleryLabelEdit>();

    @RemoteProperty
    private GalleryNavigationPaginatorType navigationPaginatorType;

    @RemoteProperty
    private GalleryOrientation orientation;

    @RemoteProperty
    private String orientationLayout;

    @RemoteProperty
    private int rows = 2;

    @RemoteProperty
    private int columns = 2;

    @RemoteProperty
    private int thumbnailWidth = 100;

    @RemoteProperty
    private int thumbnailHeight = 100;

    @RemoteProperty
    private int cellHorizontalMargin = 100;

    @RemoteProperty
    private int cellVerticalMargin = 5;

    @RemoteProperty
    private int cellWidth = 100;

    @RemoteProperty
    private int cellHeight = 100;

    @RemoteProperty
    private Integer dataPageId;

    @RemoteProperty
    private Integer dataWidgetId;

    /**
     * Use only as output parameter on page.
     */
    @RemoteProperty
    private int id;

    /**
     * Use only as output parameter on page for select
     * need widget in data widgets list.
     */
    @RemoteProperty
    private Integer dataCrossWidgetId;

    @RemoteProperty
    private int cellBorderWidth = 0;

    @RemoteProperty
    private String borderColor;

    @RemoteProperty
    private String backgroundColor;

    @RemoteProperty
    private String borderStyle;

    @RemoteProperty
    private Integer formFilterId;

    @RemoteProperty
    private boolean includesVotingModule;

    @RemoteProperty
    private VoteSettings voteSettings;

    @RemoteProperty
    private VoteStarsLinks voteStars;

    @RemoteProperty
    private int siteId;

    @RemoteProperty
    private boolean showOnlyMyRecords;

    @RemoteProperty
    private VoteStarsLinks voteLinks;

    @RemoteProperty
    private ChildSiteLinkData childSiteLinkData;

    @RemoteProperty
    private boolean showNotesComments;

    @RemoteProperty
    private String votingStartDateString;

    @RemoteProperty
    private String votingEndDateString;

    @RemoteProperty
    private GalleryBackToNavigation backToNavigation;

    @RemoteProperty
    private GalleryDataPaginator dataPaginator = new GalleryDataPaginator();

    @RemoteProperty
    private boolean hideEmpty;

    @RemoteProperty
    private PaypalSettingsData paypalSettings;

    @RemoteProperty
    private boolean modified;

}
