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
import com.shroggle.logic.site.SiteManager;
import com.shroggle.presentation.site.render.RenderContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.context.ContextStorage;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.shroggle.entity.GalleryItemColumn.*;

/**
 * @author Artem Stasuk
 */
public class GalleryData {

    // todo. I`ve moved this code from another place and there were no any tests. Please, add tests for this method. Tolik

    public static GalleryData newInstance(Gallery gallery, final RenderContext context, final Widget widget, final SiteShowOption siteShowOption) {
        final Integer selectedCrossWidgetId = context.getIntParameterByName("gallerySelectedCrossWidgetId");
        int crossWidgetId = widget.getCrossWidgetId();
        if (widget.getParentCrossWidgetId() != null) {
            crossWidgetId = widget.getParentCrossWidgetId();
        }

        // If search engine parameter is present then we should always dispatch gallery to selected filled form.
        final boolean isSearchEngineLink = !StringUtil.isNullOrEmpty(context.getParameterByName("SELink"));

        Integer selectedFilledFormId = null;
        if (isSearchEngineLink || (selectedCrossWidgetId != null && selectedCrossWidgetId == crossWidgetId)) {
            selectedFilledFormId = context.getIntParameterByName("gallerySelectedFilledFormId");
        }

        final ContextStorage contextStorage = ServiceLocator.getContextStorage();
        final String referer = context.getRequest().getHeader("referer");
        if (!StringUtil.isNullOrEmpty(referer) && selectedFilledFormId != null && gallery.getDataCrossWidgetId() != null) {
            contextStorage.get().setBackToNavigationUrl(gallery.getDataCrossWidgetId(), referer);
            contextStorage.get().setGalleryShowInData(gallery.getDataCrossWidgetId(), gallery.getId());
        }

        if (selectedFilledFormId == null && widget.isWidgetItem()) {
            final WidgetItem widgetGalleryData = (WidgetItem) widget;
            if (widgetGalleryData.getParentCrossWidgetId() != null) {
                // http://jira.web-deva.com/browse/SW-3911
                final DraftForm form = ServiceLocator.getPersistance().getFormById(gallery.getFormId1());
                if (form.getType() == FormType.CHILD_SITE_REGISTRATION) {
                    final Site site = widget.getSite();
                    selectedFilledFormId = site.getChildSiteFilledFormId();
                }
            }
        }
        return new GalleryData(gallery, widget, selectedFilledFormId, siteShowOption);
    }

    public GalleryData(final Gallery gallery, final Widget widget, final Integer filledFormId, final SiteShowOption siteShowOption) {
        this.siteShowOption = siteShowOption;
        final Persistance persistance = ServiceLocator.getPersistance();
        FilledForm tempFilledForm = persistance.getFilledFormById(filledFormId);

        if (tempFilledForm == null) {
            final DraftItem draftItem = widget == null ? null : ((WidgetItem) widget).getDraftItem();
            final List<FilledForm> filledForms = new GalleryItemsSorter().getFilledForms(gallery, draftItem, siteShowOption);
            if (filledForms.size() > 0) {
                tempFilledForm = filledForms.get(0);
            }
        }

        if (tempFilledForm == null) {
            tempFilledForm = new FilledForm();

            DraftForm form = null;
            if (gallery.getFormFilterId() != null) {
                final DraftFormFilter formFilter = persistance.getFormFilterById(gallery.getFormFilterId());
                if (formFilter != null) {
                    form = formFilter.getDraftForm();
                }
            }

            if (form == null) {
                form = persistance.getFormById(gallery.getFormId1());
            }

            if (form != null) {
                tempFilledForm.setFormId(form.getFormId());
                for (final DraftFormItem formItem : form.getDraftFormItems()) {
                    final FilledFormItem filledFormItem = new FilledFormItem();
                    filledFormItem.setFormItemId(formItem.getFormItemId());
                    filledFormItem.setFormItemName(formItem.getFormItemName());
                    tempFilledForm.addFilledFormItem(filledFormItem);
                    if (formItem.getFormItemName() != FormItemName.IMAGE_FILE_UPLOAD
                            && formItem.getFormItemName() != FormItemName.VIDEO_FILE_UPLOAD
                            && formItem.getFormItemName() != FormItemName.LINKED) {
                        filledFormItem.setValue(formItem.getItemName() + " text");
                    } else {
                        filledFormItem.setValue(null);
                    }
                }
            }
        }

        this.filledForm = tempFilledForm;
        this.gallery = new GalleryManager(gallery);
        this.widget = widget;

        final ContextStorage contextStorage = ServiceLocator.getContextStorage();
        if (gallery.getDataCrossWidgetId() != null) {
            backToNavigationUrl = contextStorage.get().getBackToNavigationUrl(gallery.getDataCrossWidgetId());
        } else {
            backToNavigationUrl = null;
        }
    }

    public GalleryCellData[][] getCells() {
        int maxRow = 0;
        int maxColumn = 0;

        final List<GalleryItemData> itemDatas = new ArrayList<GalleryItemData>();
        if (filledForm != null) {
            for (final GalleryItem item : gallery.getItems()) {
                final GalleryItemData itemData = new GalleryItemData(filledForm, item, widget.getSiteId());

                if (!gallery.getEntity().isHideEmpty() || !StringUtil.isNullOrEmpty(itemData.getValue())) {
                    itemDatas.add(itemData);

                    if (itemData.getRow() + 1 > maxRow) {
                        maxRow = itemData.getRow() + 1;
                    }

                    final GalleryItemColumn column = itemData.getColumn();
                    if (column == COLUMN_12 || column == COLUMN_23 || column == COLUMN_123) {
                        if (maxColumn < 1) {
                            maxColumn = 1;
                        }
                    } else if (column.ordinal() + 1 > maxColumn) {
                        maxColumn = column.ordinal() + 1;
                    }
                }
            }
        }

        final ChildSiteLink childSiteLink = gallery.getChildSiteLink();
        if (childSiteLink != null) {
            maxRow = Math.max(maxRow, childSiteLink.getChildSiteLinkRow() + 1);
            maxColumn = Math.max(maxColumn, getColumnIndex(childSiteLink.getChildSiteLinkColumn()) + 1);
        }

        final PaypalSettingsForGallery paypalSettings = gallery.getPaypalSettings();
        if (paypalSettings != null && paypalSettings.isEnable()) {
            maxRow = Math.max(maxRow, paypalSettings.getPaypalSettingsRow() + 1);
            maxColumn = Math.max(maxColumn, getColumnIndex(paypalSettings.getPaypalSettingsColumn()) + 1);

            if (paypalSettings.isGoToShoppingCartDisplay()) {
                maxRow = Math.max(maxRow, paypalSettings.getGoToShoppingCartRow() + 1);
                maxColumn = Math.max(maxColumn, getColumnIndex(paypalSettings.getGoToShoppingCartColumn()) + 1);
            }
        }

        final GalleryBackToNavigation backToNavigation = gallery.getEntity().getBackToNavigation();
        final boolean showBackToNavigation = backToNavigationUrl != null && backToNavigation.getBackToNavigationColumn() != null;
        if (showBackToNavigation) {
            maxRow = Math.max(maxRow, backToNavigation.getBackToNavigationRow() + 1);
            maxColumn = Math.max(maxColumn, getColumnIndex(backToNavigation.getBackToNavigationColumn()) + 1);
        }

        final VoteSettings voteSettings = gallery.getVoteSettings();
        if (voteSettings != null) {
            maxRow = Math.max(maxRow, voteSettings.getVotingStarsRow() + 1);
            maxRow = Math.max(maxRow, voteSettings.getVotingTextLinksRow() + 1);
            maxColumn = Math.max(maxColumn, getColumnIndex(voteSettings.getVotingStarsColumn()) + 1);
            maxColumn = Math.max(maxColumn, getColumnIndex(voteSettings.getVotingTextLinksColumn()) + 1);
        }

        maxRow = Math.max(maxRow, gallery.getDataPaginator().getDataPaginatorRow() + 1);
        maxColumn = Math.max(maxColumn, getColumnIndex(gallery.getDataPaginator().getDataPaginatorColumn()) + 1);

        final GalleryCellData[][] cellDatas = new GalleryCellData[maxRow][];
        for (int c = 0; c < maxRow; c++) {
            cellDatas[c] = new GalleryCellData[maxColumn];
            for (int r = 0; r < maxColumn; r++) {
                cellDatas[c][r] = new GalleryCellData();
            }
        }

        if (filledForm != null) {
            for (final GalleryItemData itemData : itemDatas) {
                final int column = getColumnIndex(itemData.getColumn());
                final int row = itemData.getRow();

                cellDatas[row][column].getItems().add(itemData);
            }
        }

        if (gallery.isIncludesVotingModule()) {
            insertVotingItems(cellDatas);
        }

        if (gallery.isIncludesChildSiteLink()) {
            insertChildSiteLink(cellDatas);
        }

        if (gallery.isIncludesPaypalSettings()) {
            insertPaypalSettings(cellDatas);
        }

        insertDataPaginator(cellDatas);

        if (showBackToNavigation) {
            final GalleryItemColumn column = backToNavigation.getBackToNavigationColumn();
            final int row = backToNavigation.getBackToNavigationRow();

            cellDatas[row][getColumnIndex(column)].getItems()
                    .add(0, new GalleryItemData(GalleryItemType.BACK_TO_NAVIGATION, row, column, backToNavigation.getBackToNavigationPosition(), widget.getSiteId()));
        }

        /**
         * Normalize, its need if user set to one row after splitted cells other cell.
         * In this case we move other cell to one with splitted column.
         */
        normalizeItems(cellDatas);
        showVotingItems = checkFilledFormExistence(cellDatas);
        return cellDatas;
    }

    private void normalizeItems(final GalleryCellData[][] itemsDatas) {
        for (final GalleryCellData[] rowData : itemsDatas) {
            for (int r = 0; r < rowData.length; r++) {

                if (rowData[r] != null) {
                    int width = rowData[r].getWidth();
                    if (width > 1) {
                        for (int w = r + 1; w - r - 1 < width - 1 && w < rowData.length; w++) {
                            rowData[w] = null;
                        }
                    }
                }

            }
        }
    }

    private boolean checkFilledFormExistence(final GalleryCellData[][] itemsDatas) {
        for (GalleryCellData[] galleryCellDatas : itemsDatas) {
            for (GalleryCellData galleryCellData : galleryCellDatas) {
                if (galleryCellData != null) {
                    for (GalleryItemData galleryItemData : galleryCellData.getItems()) {
                        if (galleryItemData.isFilledFormItemExist()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private int getColumnIndex(final GalleryItemColumn column) {
        if (column == COLUMN_123 || column == COLUMN_12 || column == COLUMN_23) {
            return 0;
        }
        return column.ordinal();
    }

    public GalleryNavigationData getNavigation() {
        return new GalleryNavigationData(gallery, widget, filledForm, siteShowOption);
    }

    public GalleryManager getGalleryManager() {
        return gallery;
    }

    public String getBackToNavigationUrl() {
        return backToNavigationUrl;
    }

    public String getBackToNavigationName() {
        return gallery.getEntity().getBackToNavigation().getBackToNavigationName();
    }

    public int getFilledFormId() {
        return filledForm != null ? filledForm.getFilledFormId() : -1;
    }

    public String getVotingLinksAlign() {
        return gallery.getVoteSettings().getVotingTextLinksAlign().toString();
    }

    public String getVotingStarsAlign() {
        return gallery.getVoteSettings().getVotingStarsAlign().toString();
    }

    public String getBackToNavigationAlign() {
        return gallery.getEntity().getBackToNavigation().getBackToNavigationAlign().toString();
    }

    public String getChildSiteLinkAlign() {
        return gallery.getChildSiteLink().getChildSiteLinkAlign().toString();
    }

    public String getPaypalButtonAlign() {
        return gallery.getPaypalSettings().getPaypalSettingsAlign().toString();
    }

    public PaypalSettingsForGallery getPaypalSettings() {
        return gallery.getPaypalSettings();
    }

    public String getChildSiteLinkText() {
        International international = ServiceLocator.getInternationStorage().get("childSiteLink", Locale.US);
        String linkText = international.get("siteIsNotCreated");
        if (isChildSiteExistAndActive()) {
            linkText = gallery.getChildSiteLink().getChildSiteLinkName();
            linkText = linkText != null ? linkText : international.get("moreInfoAboutChildSite");
            if (linkText.contains("<child site name>")) {
                linkText = linkText.replace("<child site name>", StringUtil.getEmptyOrString(getChildSite().getTitle()));
            } else {
                linkText += " " + StringUtil.getEmptyOrString(getChildSite().getTitle());
            }
        }
        return linkText;
    }

    public String getChildSiteLink() {
        String url = "javascript:void(0);";
        if (isChildSiteExistAndActive()) {
            final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
            url = "http://" + getChildSite().getSubDomain() + "." + configStorage.get().getUserSitesUrl();
        }
        return url;
    }

    public boolean isChildSiteExistAndActive() {
        final Site childSite = getChildSite();
        return childSite != null && new SiteManager(childSite).isActive();
    }

    private Site getChildSite() {
        final Persistance persistance = ServiceLocator.getPersistance();
        final ChildSiteSettings settings = persistance.getChildSiteSettingsById(filledForm != null ? filledForm.getChildSiteSettingsId() : null);
        if (settings != null) {
            return settings.getSite();
        }
        return null;
    }

    @Override
    public String toString() {
        return "GalleryData [gallery: " + gallery + ", filledForm: " + filledForm + "]";
    }

    private void insertVotingItems(final GalleryCellData[][] itemsByColumn) {
        final VoteSettings settings = gallery.getVoteSettings();
        if (settings == null) {
            return;
        }

        if (settings.getVotingStarsRow() < settings.getVotingTextLinksRow()) {
            insertVotingItem(itemsByColumn, settings.getVotingStarsColumn(), settings.getVotingStarsRow(), GalleryItemType.VOTING_STARS, settings.getVotingStarsPosition());
            insertVotingItem(itemsByColumn, settings.getVotingTextLinksColumn(), settings.getVotingTextLinksRow(), GalleryItemType.VOTING_LINKS, settings.getVotingTextLinksPosition());
        } else {
            insertVotingItem(itemsByColumn, settings.getVotingTextLinksColumn(), settings.getVotingTextLinksRow(), GalleryItemType.VOTING_LINKS, settings.getVotingTextLinksPosition());
            insertVotingItem(itemsByColumn, settings.getVotingStarsColumn(), settings.getVotingStarsRow(), GalleryItemType.VOTING_STARS, settings.getVotingStarsPosition());
        }
    }

    private void insertChildSiteLink(final GalleryCellData[][] itemsDatas) {
        final ChildSiteLink childSiteLink = gallery.getChildSiteLink();
        if (childSiteLink == null) {
            return;
        }

        final GalleryItemColumn column = childSiteLink.getChildSiteLinkColumn();
        final int row = childSiteLink.getChildSiteLinkRow();

        itemsDatas[row][getColumnIndex(column)].getItems()
                .add(0, new GalleryItemData(GalleryItemType.CHILD_SITE_LINK, row, column, childSiteLink.getChildSiteLinkPosition(), widget.getSiteId()));
    }

    private void insertPaypalSettings(final GalleryCellData[][] itemsDatas) {
        final PaypalSettingsForGallery paypalSettings = gallery.getPaypalSettings();
        if (paypalSettings == null) {
            return;
        }

        final GalleryItemColumn column = paypalSettings.getPaypalSettingsColumn();
        final int row = paypalSettings.getPaypalSettingsRow();

        itemsDatas[row][getColumnIndex(column)].getItems()
                .add(0, new GalleryItemData(GalleryItemType.PAYPAL_BUTTON, row, column, paypalSettings.getPaypalSettingsPosition(), widget.getSiteId()));

        if (paypalSettings.isGoToShoppingCartDisplay()) {
            itemsDatas[paypalSettings.getGoToShoppingCartRow()][getColumnIndex(paypalSettings.getGoToShoppingCartColumn())].getItems()
                    .add(0, new GalleryItemData(GalleryItemType.GO_TO_SHOPPING_CART,
                            paypalSettings.getGoToShoppingCartRow(), paypalSettings.getGoToShoppingCartColumn(),
                            paypalSettings.getGoToShoppingCartPosition(), widget.getSiteId()));
        }

        if (paypalSettings.isViewPurchaseHistoryDisplay()) {
            itemsDatas[paypalSettings.getGoToShoppingCartRow()][getColumnIndex(paypalSettings.getGoToShoppingCartColumn())].getItems()
                    .add(0, new GalleryItemData(GalleryItemType.VIEW_PURCHASE_HISTORY,
                            paypalSettings.getGoToShoppingCartRow(), paypalSettings.getGoToShoppingCartColumn(),
                            paypalSettings.getGoToShoppingCartPosition(), widget.getSiteId()));
        }
    }

    private void insertDataPaginator(final GalleryCellData[][] itemsDatas) {
        final GalleryDataPaginator dataPaginator = gallery.getDataPaginator();
        if (dataPaginator == null) {
            return;
        }

        final GalleryItemColumn column = dataPaginator.getDataPaginatorColumn();
        final int row = dataPaginator.getDataPaginatorRow();

        itemsDatas[row][getColumnIndex(column)].getItems()
                .add(0, new GalleryItemData(GalleryItemType.DATA_PAGINATOR, row,
                        column, dataPaginator.getDataPaginatorPosition(), widget.getSiteId()));
    }

    private void insertVotingItem(
            final GalleryCellData[][] itemsDatas, GalleryItemColumn column,
            int row, final GalleryItemType type, int position) {
        row = row < 0 ? 0 : row;

        itemsDatas[row][getColumnIndex(column)].getItems()
                .add(0, new GalleryItemData(type, row, column, position, widget.getSiteId()));
    }

    public boolean isShowVotingItems() {
        return showVotingItems;
    }

    private final GalleryManager gallery;
    private final FilledForm filledForm;
    private final String backToNavigationUrl;
    private final Widget widget;
    private boolean showVotingItems;
    private final SiteShowOption siteShowOption;
}
