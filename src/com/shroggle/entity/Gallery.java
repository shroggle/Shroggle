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

import java.util.List;

/**
 * @author Artem Stasuk
 */
public interface Gallery extends Item {

    public GalleryDataPaginator getDataPaginator();

    public void setDataPaginator(GalleryDataPaginator dataPaginator);

    public GallerySortOrder getFirstSortType();

    public void setFirstSortType(GallerySortOrder firstSortType);

    public GallerySortOrder getSecondSortType();

    public void setSecondSortType(GallerySortOrder secondSortType);

    public int getFormId1();

    public void setFormId1(int formId);

    public GalleryOrientation getOrientation();

    public void setOrientation(GalleryOrientation orientation);

    public List<GalleryLabel> getLabels();

    public void addLabel(final GalleryLabel label);

    public void addItem(final GalleryItem item);

    public int getSecondSortItemId();

    public void setSecondSortItemId(int secondSortItemId);

    public int getFirstSortItemId();

    public List<GalleryItem> getItems();

    public void setFirstSortItemId(int firstSortItemId);

    public void removeLabel(final GalleryLabel label);

    public void removeItem(final GalleryItem item);

    public GalleryNavigationPaginatorType getNavigationPaginatorType();

    public void setNavigationPaginatorType(final GalleryNavigationPaginatorType navigationPaginatorType);

    public int getRows();

    public void setRows(int rows);

    public int getColumns();

    public void setColumns(int columns);

    public int getThumbnailHeight();

    public void setThumbnailHeight(int thumbnailHeight);

    public int getCellHorizontalMargin();

    public void setCellHorizontalMargin(final int cellHorizontalMargin);

    public int getCellVerticalMargin();

    public void setCellVerticalMargin(final int cellVerticalMargin);

    public int getCellWidth();

    public void setCellWidth(final int cellWidth);

    public int getCellHeight();

    public void setCellHeight(final int cellHeight);

    public int getCellBorderWidth();

    public void setCellBorderWidth(final int cellBorderWidth);

    public int getThumbnailWidth();

    public void setThumbnailWidth(final int thumbnailWidth);

    public String getOrientationLayout();

    public void setOrientationLayout(final String orientationLayout);

    public Integer getDataCrossWidgetId();

    public void setDataCrossWidgetId(final Integer dataCrossWidgetId);

    public String getBorderStyle();

    public void setBorderStyle(String borderStyle);

    public String getBorderColor();

    public void setBorderColor(String borderColor);

    public String getBackgroundColor();

    public void setBackgroundColor(final String backgroundColor);

    public int getVersion();

    public void setVersion(final int version);

    public Integer getFormFilterId();

    public void setFormFilterId(Integer formFilterId);

    public GalleryBackToNavigation getBackToNavigation();

    public void setBackToNavigation(GalleryBackToNavigation backToNavigation);

    public void setItems(final List<GalleryItem> items);

    public void setLabels(final List<GalleryLabel> labels);

    public boolean isIncludesVotingModule();

    public void setIncludesVotingModule(boolean includesVotingModule);

    public VoteSettings getVoteSettings();

    public void setVoteSettings(VoteSettings voteSettings);

    public boolean isShowOnlyMyRecords();

    public void setShowOnlyMyRecords(boolean showOnlyMyRecords);

    public ChildSiteLink getChildSiteLink();

    public void setChildSiteLink(ChildSiteLink childSiteLink);

    public boolean isHideEmpty();

    public void setHideEmpty(boolean hideEmpty);

    public List<Integer> getCurrentFilledFormHashCodes();

    public void setCurrentFilledFormHashCodes(List<Integer> currentFilledFormHashCodes);

    public List<Integer> getFullFilledFormHashCodes();

    public void setFullFilledFormHashCodes(List<Integer> fullFilledFormHashCodes);

    public Integer getFirstFilledFormId();

    public void setFirstFilledFormId(Integer firstFilledFormId);

    public PaypalSettingsForGallery getPaypalSettings();

    public void setPaypalSettings(PaypalSettingsForGallery paypalSettings);

    boolean isModified();

    void setModified(boolean modified);
}