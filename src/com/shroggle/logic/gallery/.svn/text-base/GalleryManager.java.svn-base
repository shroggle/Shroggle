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
import com.shroggle.exception.GalleryNameIncorrectException;
import com.shroggle.exception.GalleryNameNotUniqueException;
import com.shroggle.exception.PageNotFoundException;
import com.shroggle.logic.SiteItemsManager;
import com.shroggle.logic.accessibility.Right;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.form.FilledFormItemManager;
import com.shroggle.logic.form.FormItemManager;
import com.shroggle.logic.form.FormLogic;
import com.shroggle.logic.form.FormManager;
import com.shroggle.logic.gallery.childSiteLink.ChildSiteLinkManager;
import com.shroggle.logic.gallery.paypal.PaypalSettingsForGalleryManager;
import com.shroggle.logic.gallery.voting.VoteSettingsManager;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.site.item.ItemCreator;
import com.shroggle.logic.site.item.ItemCreatorRequest;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.video.FlvVideoManager;
import com.shroggle.util.DateUtil;
import com.shroggle.util.Dimension;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;

import java.util.*;

/**
 * @author Artem Stasuk
 */
public class GalleryManager {

    public boolean isIncludesChildSiteLink() {
        return gallery.getChildSiteLink() != null && gallery.getChildSiteLink().isShowChildSiteLink();
    }

    public boolean isIncludesVotingModule() {
        return gallery.isIncludesVotingModule();
    }

    public boolean isIncludesPaypalSettings() {
        return gallery.getPaypalSettings() != null && gallery.getPaypalSettings().isEnable();
    }

    public PaypalSettingsForGallery getPaypalSettings() {
        return gallery.getPaypalSettings();
    }

    public GalleryDataPaginator getDataPaginator() {
        return gallery.getDataPaginator();
    }

    public VoteSettings getVoteSettings() {
        return gallery.getVoteSettings();
    }

    public ChildSiteLink getChildSiteLink() {
        return gallery.getChildSiteLink();
    }

    public GalleryManager(final Gallery gallery) {
        this.gallery = gallery;
    }

    public GalleryManager(final int galleryId) {
        this.gallery = persistance.getGalleryById(galleryId);
    }

    public String getName() {
        return gallery.getName();
    }

    public int getFormId() {
        return gallery.getFormId1();
    }

    public int getFirstSortItemId() {
        return gallery.getFirstSortItemId();
    }

    public int getSecondSortItemId() {
        return gallery.getSecondSortItemId();
    }

    public int getId() {
        return gallery.getId();
    }

    public int getSiteId() {
        return gallery.getSiteId();
    }

    public void setSiteId(final int siteId) {
        gallery.setSiteId(siteId);
    }

    public boolean isModified() {
        return gallery.isModified();
    }

    public String getLeftArrowUrl() {
        String rightArrowUrl = getRightArrowUrl();
        if (rightArrowUrl != null) {
            return rightArrowUrl.replace("Right", "Left");
        }
        return null;
    }

    public String getRightArrowUrl() {
        return gallery.getDataPaginator().getDataPaginatorArrow();
    }

    public Integer getFirstFilledFormId() {
        return gallery.getFirstFilledFormId();
    }

    public FormLogic getFormLogic() {
        if (gallery.getFormId1() > 0) {
            final DraftForm form = persistance.getFormById(gallery.getFormId1());
            if (form != null) {
                return new FormLogic(form);
            }
        }
        return null;
    }

    public boolean isShowNotesComments() {
        return gallery.isShowDescription();
    }

    public String getNotesComments() {
        if (gallery.getDescription() == null) {
            return "";
        }
        return gallery.getDescription();
    }

    public Gallery getEntity() {
        return gallery;
    }

    /**
     * @return - form items
     * @link http://wiki.web-deva.com/display/SRS2/Gallery+Default+Form+Fields
     */
    public List<FormItem> getDefaultFormItems() {
        final List<FormItem> items = new ArrayList<FormItem>();

        if (gallery.getPaypalSettings().isEnable()) {
            FormItem nameItem = FormItemManager.createFormItemByName(FormItemName.NAME, 0, false);
            nameItem.setItemName("Product Name");
            items.add(nameItem);
            items.add(FormItemManager.createFormItemByName(FormItemName.DESCRIPTION, 1, false));
            FormItem imageUploadItem = FormItemManager.createFormItemByName(FormItemName.IMAGE_FILE_UPLOAD, 2, false);
            imageUploadItem.setItemName("Image File Upload(ed)");
            items.add(imageUploadItem);
            items.add(FormItemManager.createFormItemByName(FormItemName.PRICE, 3, false));
            items.add(FormItemManager.createFormItemByName(FormItemName.PRODUCT_ACCESS_GROUPS, 4, false));
            items.add(FormItemManager.createFormItemByName(FormItemName.DATE_ADDED, 5, false));
            items.add(FormItemManager.createFormItemByName(FormItemName.SORT_ORDER, 6, false));
            items.add(FormItemManager.createFormItemByName(FormItemName.ENTERED_BY, 7, false));
        } else {
            FormItem nameItem = FormItemManager.createFormItemByName(FormItemName.NAME, 0, false);
            nameItem.setItemName("Name / Title");
            items.add(nameItem);
            FormItem imageUploadItem = FormItemManager.createFormItemByName(FormItemName.IMAGE_FILE_UPLOAD, 1, false);
            imageUploadItem.setItemName("Image File Upload(ed)");
            items.add(imageUploadItem);
            items.add(FormItemManager.createFormItemByName(FormItemName.DESCRIPTION, 2, false));
            items.add(FormItemManager.createFormItemByName(FormItemName.DATE_ADDED, 3, false));
            items.add(FormItemManager.createFormItemByName(FormItemName.SORT_ORDER, 4, false));
            items.add(FormItemManager.createFormItemByName(FormItemName.ENTERED_BY, 5, false));
        }

        return items;
    }

    /**
     * Important! Need outer transaction for execute this method.
     *
     * @param edit - gallery save
     */
    public void save(final GalleryEdit edit) {
        final UserManager userManager = new UsersManager().getLogined();
        final UserRightManager userRightManager = userManager.getRight();

        if (StringUtil.getEmptyOrString(edit.getName()).trim().isEmpty()) {
            throw new GalleryNameIncorrectException("Incorrect gallery name: " + edit.getName());
        }

        final Gallery findGallery = persistance.getGalleryByNameAndSiteId(edit.getName(), gallery.getSiteId());
        if (findGallery != gallery && findGallery != null) {
            throw new GalleryNameNotUniqueException(
                    "Please, enter unique Gallery name.");
        }

        String needDescription = "";
        if (edit.getNotesComments() != null) {
            needDescription = edit.getNotesComments();
        }

        if (edit.getFormFilterId() != null) {
            final DraftFormFilter formFilter = persistance.getFormFilterById(edit.getFormFilterId());
            if (formFilter != null) {
                edit.setFormId(formFilter.getForm().getId());
            }
        }

        gallery.setVersion(gallery.getVersion() + 1);
        gallery.setShowOnlyMyRecords(edit.isShowOnlyMyRecords());
        gallery.setDescription(needDescription);
        gallery.setShowDescription(edit.isShowNotesComments());
        gallery.setHideEmpty(edit.isHideEmpty());
        gallery.setFormFilterId(edit.getFormFilterId());
        gallery.setBackgroundColor(edit.getBackgroundColor());
        gallery.setBorderStyle(edit.getBorderStyle());
        gallery.setBorderColor(edit.getBorderColor());
        gallery.setFirstSortType(edit.getFirstSortType());
        gallery.setNavigationPaginatorType(edit.getNavigationPaginatorType());
        gallery.setDataPaginator(edit.getDataPaginator());
        gallery.setSecondSortType(edit.getSecondSortType());
        gallery.setName(edit.getName());
        gallery.setBackToNavigation(edit.getBackToNavigation());
        gallery.setRows(edit.getRows());
        gallery.setColumns(edit.getColumns());
        gallery.setCellBorderWidth(edit.getCellBorderWidth());
        gallery.setCellHeight(edit.getCellHeight());
        gallery.setCellWidth(edit.getCellWidth());
        gallery.setCellHorizontalMargin(edit.getCellHorizontalMargin());
        gallery.setCellVerticalMargin(edit.getCellVerticalMargin());
        gallery.setOrientation(edit.getOrientation());
        gallery.setOrientationLayout(edit.getOrientationLayout());
        gallery.setThumbnailHeight(edit.getThumbnailHeight());
        gallery.setThumbnailWidth(edit.getThumbnailWidth());
        gallery.setFirstSortItemId(edit.getFirstSort());
        gallery.setSecondSortItemId(edit.getSecondSort());
        gallery.setIncludesVotingModule(edit.isIncludesVotingModule());
        gallery.setModified(edit.isModified());
        VoteSettings voteSettings = edit.getVoteSettings();
        if (voteSettings.isDurationOfVoteLimited()) {
            voteSettings.setStartDate(DateUtil.getDateByString(edit.getVotingStartDateString()));
            voteSettings.setEndDate(DateUtil.getDateByString(edit.getVotingEndDateString()));
        } else {
            voteSettings.setStartDate(null);
            voteSettings.setEndDate(null);
        }
        gallery.setVoteSettings(voteSettings);
        gallery.setChildSiteLink(ChildSiteLinkManager.createChildSiteLink(edit.getChildSiteLinkData()));
        if (gallery.getId() < 1) {
            persistance.putItem(gallery);
        }

        final WidgetItem widgetGalleryData;
        final Integer userId = ServiceLocator.getContextStorage().get().getUserId();
        if (edit.getDataPageId() != null) {
            final PageManager pageManager = new PageManager(persistance.getPage(edit.getDataPageId()));
            if (!Right.isAuthorizedUser(pageManager, userId)) {
                throw new PageNotFoundException("Can't find page for create gallery data widget!");
            }

            widgetGalleryData = new WidgetItem();
            final DraftItem draftItem = ItemCreator.create(new ItemCreatorRequest(ItemType.GALLERY_DATA, pageManager.getSite()));
            widgetGalleryData.setDraftItem(draftItem);

            final WidgetComposit widgetComposit = (WidgetComposit) pageManager.getWidgets().get(0);
            widgetGalleryData.setPosition(widgetComposit.getChilds().size());
            widgetComposit.addChild(widgetGalleryData);
            persistance.putWidget(widgetGalleryData);
            pageManager.addWidget(widgetGalleryData);
        } else if (edit.getDataWidgetId() != null) {
            widgetGalleryData = userRightManager.getSiteRight().getWidgetItemForEditInPresentationalService(
                    edit.getDataWidgetId());
        } else {
            widgetGalleryData = null;
        }

        if (widgetGalleryData != null) {
            gallery.setDataCrossWidgetId(widgetGalleryData.getCrossWidgetId());
        }

        /**
         * If save gallery doesn't have formId, we must create default form and
         * use it's for gallery. Be careful, on page default form have id as index.
         */
        // this is needed for form creation
        gallery.getPaypalSettings().setEnable(edit.getPaypalSettings().isEnable());
        final FormLogic formLogic;
        if (edit.getFormId() == null || edit.getFormId() < 1) {
            formLogic = createDefaultGalleryForm(edit, userManager);
        } else {
            formLogic = new FormLogic(edit.getFormId());
        }
        gallery.setFormId1(formLogic.getId());

        new PaypalSettingsForGalleryManager(gallery).fillPaypalSettingsForGallery(edit.getPaypalSettings());

        final Set<Integer> formItemIds = formLogic.getItemIds();
        final Map<Integer, GalleryLabel> labelByIds = getLabelIds();
        final List<GalleryLabelEdit> labelsEdit = edit.getLabelsWithoutDuplicate();
        for (int index = 0; index < labelsEdit.size(); index++) {
            final GalleryLabelEdit labelEdit = labelsEdit.get(index);

            /**
             * If saving label has reference on not found form item, skip its.
             */
            if (!formItemIds.contains(labelEdit.getId())) continue;

            GalleryLabel label = labelByIds.remove(labelEdit.getId());
            if (label != null) {
                label.setAlign(labelEdit.getAlign());
                label.setColumn(labelEdit.getColumn());
                label.setPosition(index);
            } else {
                label = new DraftGalleryLabel();
                label.setAlign(labelEdit.getAlign());
                label.setColumn(labelEdit.getColumn());
                label.setPosition(index);
                label.getId().setFormItemId(labelEdit.getId());
                gallery.addLabel(label);
                persistance.putGalleryLabel((DraftGalleryLabel) label);
            }
        }

        /**
         * Remove all labels that's not used in save data.
         */
        for (final GalleryLabel label : labelByIds.values()) {
            gallery.removeLabel(label);
            persistance.removeGalleryLabel((DraftGalleryLabel) label);
        }

        final Map<Integer, GalleryItem> itemByIds = getItemByIds();
        final List<GalleryItemEdit> itemsEdit = edit.getItemsWithoutDuplicate();
        for (int index = 0; index < itemsEdit.size(); index++) {
            final GalleryItemEdit itemEdit = itemsEdit.get(index);

            /**
             * If saving item has reference on not found form item, skip its.
             */
            if (!formItemIds.contains(itemEdit.getId())) continue;

            GalleryItem item = itemByIds.remove(itemEdit.getId());
            if (item != null) {
                item.setCrop(itemEdit.isCrop());
                item.setRow(itemEdit.getRow());
                item.setAlign(itemEdit.getAlign());
                item.setColumn(itemEdit.getColumn());
                item.setName(itemEdit.getName());
                item.setHeight(itemEdit.getHeight());
                item.setWidth(itemEdit.getWidth());
                item.setPosition(index);
            } else {
                item = new DraftGalleryItem();
                item.setCrop(itemEdit.isCrop());
                item.setRow(itemEdit.getRow());
                item.setHeight(itemEdit.getHeight());
                item.setWidth(itemEdit.getWidth());
                item.setAlign(itemEdit.getAlign());
                item.setColumn(itemEdit.getColumn());
                item.setName(itemEdit.getName());
                item.getId().setFormItemId(itemEdit.getId());
                item.setPosition(index);
                gallery.addItem(item);
                persistance.putGalleryItem((DraftGalleryItem) item);
            }
        }

        /**
         * Remove all items that's not used in save data.
         */
        for (final GalleryItem item : itemByIds.values()) {
            gallery.removeItem(item);
            persistance.removeGalleryItem((DraftGalleryItem) item);
        }

        createVideoFLVByNewSize();
    }

    private FormLogic createDefaultGalleryForm(GalleryEdit edit, UserManager userManager) {
        final SiteManager siteManager = userManager.getRight().getSiteRight().getSiteForEdit(gallery.getSiteId());

        String namePattern = "Gallery Default Form";
        if (edit.getPaypalSettings().isEnable()) {
            namePattern = "E-Commerce Form " + edit.getName();
        }

        FormLogic formLogic = siteManager.getForms().createCustomForm(getDefaultFormItems(), namePattern, true);

        final List<DraftFormItem> formItems = formLogic.getItems();
        for (final GalleryLabelEdit labelEdit : edit.getLabels()) {
            labelEdit.setId(formItems.get(labelEdit.getId()).getFormItemId());
        }
        for (final GalleryItemEdit itemEdit : edit.getItems()) {
            itemEdit.setId(formItems.get(itemEdit.getId()).getFormItemId());
        }

        // Changing paypal form item indexes to formItemId
        final PaypalSettingsData paypalSettings = edit.getPaypalSettings();
        if (paypalSettings.getFormItemIdWithFullPrice() != null) {
            final int correctFormItemIdWithPrice = formItems.get(paypalSettings.getFormItemIdWithFullPrice()).getFormItemId();
            paypalSettings.setFormItemIdWithFullPrice(correctFormItemIdWithPrice);
        }
        if (paypalSettings.getFormItemIdWithProductName() != null) {
            final int correctFormItemIdWithProductName = formItems.get(paypalSettings.getFormItemIdWithProductName()).getFormItemId();
            paypalSettings.setFormItemIdWithProductName(correctFormItemIdWithProductName);
        }
        // Changing paypal form item indexes to formItemId

        gallery.setFirstSortItemId(formItems.get(gallery.getFirstSortItemId()).getFormItemId());
        gallery.setSecondSortItemId(formItems.get(gallery.getSecondSortItemId()).getFormItemId());
        return formLogic;
    }

    public void createVideoFLVByNewSize() {
        for (GalleryItem videoItem : getVideoItemsWithCorrectSize()) {
            for (FilledFormItem filledFormItem : persistance.getFilledFormItemByFormItemId(videoItem.getId().getFormItemId())) {
                FormVideo formVideo = persistance.getFormVideoById(FilledFormItemManager.getIntValue(filledFormItem));
                if (formVideo != null) {
                    Video video = persistance.getVideoById(formVideo.getVideoId());
                    if (video != null) {
                        final FlvVideo normalFlvVideo = FlvVideoManager.getFlvVideoOrCreateNew(video.getVideoId(), videoItem.getWidth(), videoItem.getHeight(), formVideo.getQuality(), video.getSiteId());
                        final Dimension dimension = new FlvVideoManager(normalFlvVideo).createLargeVideoDimension();
                        if (dimension == null) {
                            continue;
                        }
                        FlvVideoManager.getFlvVideoOrCreateNew(video.getVideoId(), dimension.getWidth(), dimension.getHeight(), formVideo.getQuality(), video.getSiteId());
                    }
                }
            }
        }
    }

    protected List<GalleryItem> getVideoItemsWithCorrectSize() {
        List<GalleryItem> galleryItems = new ArrayList<GalleryItem>();
        if (gallery != null && gallery.getItems() != null) {
            for (GalleryItem item : gallery.getItems()) {
                if (item != null && item.getWidth() != null && item.getWidth() > 0 && item.getHeight() != null && item.getHeight() > 0) {
                    final DraftFormItem formItem = persistance.getFormItemById(item.getId().getFormItemId());
                    if (formItem != null && formItem.getFormItemName() == FormItemName.VIDEO_FILE_UPLOAD) {
                        galleryItems.add(item);
                    }
                }
            }
        }
        return galleryItems;
    }

    private Map<Integer, GalleryItem> getItemByIds() {
        final Map<Integer, GalleryItem> itemByIds = new HashMap<Integer, GalleryItem>();
        for (final GalleryItem item : gallery.getItems()) {
            itemByIds.put(item.getId().getFormItemId(), item);
        }
        return itemByIds;
    }

    private Map<Integer, GalleryLabel> getLabelIds() {
        final Map<Integer, GalleryLabel> labelByIds = new HashMap<Integer, GalleryLabel>();
        for (final GalleryLabel label : gallery.getLabels()) {
            labelByIds.put(label.getId().getFormItemId(), label);
        }
        return labelByIds;
    }

    public GalleryEdit getEdit() {
        final GalleryEdit edit = new GalleryEdit();
        edit.setId(gallery.getId());
        edit.setNotesComments(getNotesComments());
        edit.setFirstSort(gallery.getFirstSortItemId());
        edit.setFirstSortType(gallery.getFirstSortType());
        edit.setSecondSort(gallery.getSecondSortItemId());
        edit.setSecondSortType(gallery.getSecondSortType());
        edit.setName(gallery.getName());
        edit.setShowOnlyMyRecords(gallery.isShowOnlyMyRecords());
        edit.setRows(gallery.getRows());
        edit.setBackToNavigation(gallery.getBackToNavigation());
        edit.setColumns(gallery.getColumns());
        edit.setCellBorderWidth(gallery.getCellBorderWidth());
        edit.setCellHeight(gallery.getCellHeight());
        edit.setHideEmpty(gallery.isHideEmpty());
        edit.setCellWidth(gallery.getCellWidth());
        edit.setCellHorizontalMargin(gallery.getCellHorizontalMargin());
        edit.setCellVerticalMargin(gallery.getCellVerticalMargin());
        edit.setThumbnailHeight(gallery.getThumbnailHeight());
        edit.setThumbnailWidth(gallery.getThumbnailWidth());
        edit.setNavigationPaginatorType(gallery.getNavigationPaginatorType());
        edit.setDataPaginator(gallery.getDataPaginator());
        edit.setOrientation(gallery.getOrientation());
        edit.setOrientationLayout(gallery.getOrientationLayout());
        edit.setDataCrossWidgetId(gallery.getDataCrossWidgetId());
        edit.setFormId(gallery.getFormId1() > 0 ? gallery.getFormId1() : null);
        edit.setBackgroundColor(gallery.getBackgroundColor());
        edit.setBorderColor(gallery.getBorderColor());
        edit.setBorderStyle(gallery.getBorderStyle());
        edit.setFormFilterId(gallery.getFormFilterId());
        edit.setVoteSettings(gallery.getVoteSettings());
        edit.setPaypalSettings(new PaypalSettingsForGalleryManager(gallery).createPaypalSettingsData());
        edit.setModified(gallery.isModified());

        final List<GalleryItem> items = getItems();
        for (final GalleryItem item : items) {
            final GalleryItemEdit itemEdit = new GalleryItemEdit();
            itemEdit.setAlign(item.getAlign());
            itemEdit.setCrop(item.isCrop());
            itemEdit.setId(item.getId().getFormItemId());
            itemEdit.setColumn(item.getColumn());
            itemEdit.setName(item.getName());
            itemEdit.setRow(item.getRow());
            itemEdit.setWidth(item.getWidth());
            itemEdit.setHeight(item.getHeight());
            edit.getItems().add(itemEdit);
        }

        final List<GalleryLabel> labels = new ArrayList<GalleryLabel>(gallery.getLabels());
        Collections.sort(labels, new Comparator<GalleryLabel>() {

            @Override
            public int compare(final GalleryLabel o1, final GalleryLabel o2) {
                return o1.getPosition() - o2.getPosition();
            }

        });
        for (final GalleryLabel label : labels) {
            final GalleryLabelEdit labelEdit = new GalleryLabelEdit();
            labelEdit.setAlign(label.getAlign());
            labelEdit.setId(label.getId().getFormItemId());
            labelEdit.setColumn(label.getColumn());
            edit.getLabels().add(labelEdit);
        }

        VoteSettingsManager voteSettingsManager = new VoteSettingsManager(gallery.getVoteSettings());
        edit.setVoteStars(voteSettingsManager.createVoteStars());
        edit.setVoteLinks(voteSettingsManager.createVoteLinks());
        edit.setChildSiteLinkData(new ChildSiteLinkManager(gallery.getChildSiteLink()).createChildSiteLinkData());
        return edit;
    }

    public List<GalleryItem> getItems() {
        final List<GalleryItem> items = new ArrayList<GalleryItem>(gallery.getItems());
        Collections.sort(items, new Comparator<GalleryItem>() {

            @Override
            public int compare(final GalleryItem o1, final GalleryItem o2) {
                return o1.getPosition() - o2.getPosition();
            }

        });
        return items;
    }

    public int createNavigationWidth(final int paginatorWidth) {
        int width = createNavigationRowWidth();
        return width > paginatorWidth ? width : paginatorWidth;
    }

    public int createNavigationRowWidth() {
        int width = 0;
        if (gallery != null) {
            width = (gallery.getColumns() * gallery.getCellWidth()) +
                    (gallery.getCellHorizontalMargin() * gallery.getColumns() * 2) +
                    (gallery.getCellBorderWidth() * gallery.getColumns() * 2);
        }
        return width;
    }

    public int createNavigationHeight() {
        int height = 0;
        if (gallery != null) {
            height = (gallery.getRows() * gallery.getCellHeight()) +
                    (gallery.getCellVerticalMargin() * 2 * gallery.getRows()) +
                    (gallery.getCellBorderWidth() * gallery.getRows() * 2);
            final PaginatorType paginatorType = GalleryPaginatorManager.getPaginatorType(gallery);
            if (paginatorType != PaginatorType.NONE || gallery.getNavigationPaginatorType() == GalleryNavigationPaginatorType.SCROLL_HORIZONTALLY) {
                height += 28;
            }
        }
        return height;
    }

    public boolean showDataAndNavigation() {
        return gallery != null && !showNavigationOnly() && !showDataOnly();
    }

    public boolean showNavigationOnly() {
        return gallery != null && gallery.getOrientation() == GalleryOrientation.NAVIGATION_ONLY;
    }

    public boolean showDataOnly() {
        return gallery != null && (gallery.getOrientation() == GalleryOrientation.DATA_ONLY);
    }

    public static enum CreateGalleryRequestType {
        GALLERY, VOTING, E_COMMERCE_STORE
    }

    public static SaveGalleryRequest createDefaultGalleryRequest(final Integer widgetId, final Site site,
                                                                 final CreateGalleryRequestType createGalleryRequestType,
                                                                 final String customNamePattern) {

        SaveGalleryRequest request = new SaveGalleryRequest();
        request.setGalleryId(-1);
        request.setWidgetGalleryId(widgetId);
        GalleryEdit galleryEdit = new GalleryEdit();
        galleryEdit.setFirstSort(3);
        galleryEdit.setSecondSort(0);
        galleryEdit.setName(SiteItemsManager.getNextDefaultName(ItemType.GALLERY, site.getSiteId(), customNamePattern, false));
        galleryEdit.setNotesComments("");
        galleryEdit.setShowNotesComments(true);
        galleryEdit.setFormId(null);
        galleryEdit.setFirstSortType(GallerySortOrder.DESCENDING);
        galleryEdit.setSecondSortType(GallerySortOrder.ASCENDING);
        GalleryItemEdit item = new GalleryItemEdit();
        item.setId(createGalleryRequestType == CreateGalleryRequestType.E_COMMERCE_STORE ? 2 : 1);
        item.setName("");
        item.setAlign(GalleryAlign.CENTER);
        item.setColumn(GalleryItemColumn.COLUMN_1);
        item.setWidth(800);
        item.setHeight(535);
        galleryEdit.setItems(Arrays.asList(item));
        GalleryLabelEdit label = new GalleryLabelEdit();
        label.setColumn(0);
        label.setId(createGalleryRequestType == CreateGalleryRequestType.E_COMMERCE_STORE ? 2 : 1);
        label.setAlign(GalleryAlign.CENTER);
        galleryEdit.setLabels(Arrays.asList(label));
        galleryEdit.setNavigationPaginatorType(GalleryNavigationPaginatorType.PREVIOUS_NEXT_WITH_NUMBERS);
        galleryEdit.getDataPaginator().setDataPaginatorPosition(200);
        galleryEdit.getDataPaginator().setDataPaginatorType(GalleryDataPaginatorType.PREVIOUS_NEXT);
        galleryEdit.getDataPaginator().setDataPaginatorArrow(null);
        galleryEdit.setOrientation(GalleryOrientation.NAVIGATION_ABOVE_DATA_BELOW);
        galleryEdit.setOrientationLayout("test1");
        galleryEdit.setRows(1);
        galleryEdit.setColumns(12);
        galleryEdit.setThumbnailWidth(60);
        galleryEdit.setThumbnailHeight(50);
        galleryEdit.setCellHorizontalMargin(6);
        galleryEdit.setCellVerticalMargin(6);
        galleryEdit.setCellWidth(66);
        galleryEdit.setCellHeight(56);
        galleryEdit.setDataPageId(null);
        galleryEdit.setId(-1);
        galleryEdit.setVoteSettings(new VoteSettings());
        galleryEdit.setDataCrossWidgetId(null);
        galleryEdit.setCellBorderWidth(0);
        galleryEdit.setBorderColor("transparent");
        galleryEdit.setBackgroundColor("transparent");
        galleryEdit.setBorderStyle(null);
        galleryEdit.setFormFilterId(0);
        galleryEdit.setBackToNavigation(new GalleryBackToNavigation());
        galleryEdit.setPaypalSettings(new PaypalSettingsData());
        galleryEdit.getPaypalSettings().setRegistrationFormId(site.getDefaultFormId());
        galleryEdit.getPaypalSettings().setPosition(8);
        galleryEdit.getPaypalSettings().setGoToShoppingCartDisplay(true);
        galleryEdit.getPaypalSettings().setGoToShoppingCartPosition(9);

        if (createGalleryRequestType == CreateGalleryRequestType.VOTING) {
            galleryEdit.setIncludesVotingModule(true);
            galleryEdit.getVoteSettings().setRegistrationFormIdForVoters(site.getDefaultFormId());
        }

        if (createGalleryRequestType == CreateGalleryRequestType.E_COMMERCE_STORE) {
            galleryEdit.getPaypalSettings().setEnable(true);
        }

        request.setGallerySave(galleryEdit);
        return request;
    }

    public void saveInTransaction(final SaveGalleryRequest request) {
        persistanceTransaction.execute(new Runnable() {

            @Override
            public void run() {

                request.getGallerySave().setModified(true);// Gallery have to be modified after it`s settings saving. Tolik
                save(request.getGallerySave());

            }

        });
    }


    public DraftGallery createGallery(final GalleryEdit galleryEdit) {
        galleryEdit.setModified(false);// New gallery have to be not modified. Tolik
        save(galleryEdit);
        return (DraftGallery) gallery;
    }

    public int getRegistrationFormIdForVoters() {
        final Integer id = gallery.getVoteSettings() != null ? gallery.getVoteSettings().getRegistrationFormIdForVoters() : null;
        return id != null ? id : -1;
    }

    public static GalleryManager createInstance(final int galleryId, final Integer siteId) {
        if (galleryId < 1) {
            return new GalleriesManager().getNew(siteId);
        }
        return new GalleriesManager().get(galleryId);
    }

    public List<Integer> getFilledFormsIds() {
        final List<Integer> filledFormsIds = new ArrayList<Integer>();
        for (FilledForm filledForm : getFilledForms()) {
            filledFormsIds.add(filledForm.getFilledFormId());
        }
        return filledFormsIds;
    }

    public List<FilledForm> getFilledForms() {
        if (gallery != null) {
            return persistance.getFilledFormsByFormId(gallery.getFormId1());
        } else {
            return Collections.emptyList();
        }
    }

    // Should be called within transaction. Return's created form.

    public DraftForm createOrdersForm(final Integer productFormItemId, final int registrationFormId, final UserManager userManager) {
        final SiteManager siteManager = userManager.getRight().getSiteRight().getSiteForEdit(gallery.getSiteId());

        final Form form = getFormLogic().getForm();

        final FormLogic createdFormLogic = siteManager.getForms().createCustomForm(
                FormType.ORDER_FORM, getDefaultOrderFormItems(productFormItemId, registrationFormId), "Orders from " + form.getName(), true);

        return createdFormLogic.getForm();
    }

    //You can't use this without form. Use create default form method instead.

    private List<DraftFormItem> getDefaultOrderFormItems(final Integer productFormItemId, final int registrationFormId) {
        final List<DraftFormItem> items = new ArrayList<DraftFormItem>();

        final DraftFormItem productNameItem = FormItemManager.createFormItemByName(FormItemName.PRODUCT_NAME, 0, false);
        final DraftFormItem productLinkItem = FormItemManager.createFormItemByName(FormItemName.LINKED, 1, false, false);
        productLinkItem.setFormItemDisplayType(FormItemType.SELECT);
        productLinkItem.setLinkedFormId(gallery.getFormId1());
        productLinkItem.setLinkedFormItemId(productFormItemId);
        productLinkItem.setItemName("Product Name Link");

        final DraftFormItem customerNameItem = FormItemManager.createFormItemByName(FormItemName.CUSTOMER_NAME, 2, false);
        final DraftFormItem userLink = FormItemManager.createFormItemByName(FormItemName.LINKED, 3, false, false);
        userLink.setFormItemDisplayType(FormItemType.SELECT);
        userLink.setLinkedFormId(registrationFormId);
        FormItem suitableItemInUsersFormToConnect = findSuitableItemInUsersFormToConnect(registrationFormId);
        userLink.setLinkedFormItemId((suitableItemInUsersFormToConnect != null ? suitableItemInUsersFormToConnect.getFormItemId() : null));
        userLink.setItemName("Customer Link");

        final DraftFormItem purchaseDateAndTimeItem = FormItemManager.createFormItemByName(FormItemName.PURCHASE_DATE_AND_TIME, 4, false);
        final DraftFormItem paidAmountItem = FormItemManager.createFormItemByName(FormItemName.PAID_AMOUNT, 5, false);
        final DraftFormItem taxAmountItem = FormItemManager.createFormItemByName(FormItemName.TAX_AMOUNT, 6, false);
        final DraftFormItem orderStatusItem = FormItemManager.createFormItemByName(FormItemName.ORDER_STATUS, 7, false);
        final DraftFormItem statusNotesItem = FormItemManager.createFormItemByName(FormItemName.STATUS_NOTES, 8, false);

        items.add(productNameItem);
        items.add(productLinkItem);
        items.add(customerNameItem);
        items.add(userLink);
        items.add(purchaseDateAndTimeItem);
        items.add(paidAmountItem);
        items.add(taxAmountItem);
        items.add(orderStatusItem);
        items.add(statusNotesItem);

        return items;
    }

    protected FormItem findSuitableItemInUsersFormToConnect(final int registrationFormId) {
        DraftForm form = ServiceLocator.getPersistance().getFormById(registrationFormId);
        if (form == null || form.getFormItems().isEmpty()) {
            return null;
        }

        FormItem suitableItem = FormManager.getFormItemByFormItemName(FormItemName.EMAIL, form);
        if (suitableItem != null) {
            return suitableItem;
        }

        suitableItem = FormManager.getFormItemByFormItemName(FormItemName.FIRST_NAME, form);
        if (suitableItem != null) {
            return suitableItem;
        }

        suitableItem = FormManager.getFormItemByFormItemName(FormItemName.LAST_NAME, form);
        if (suitableItem != null) {
            return suitableItem;
        }

        suitableItem = FormManager.getFormItemByFormItemType(FormItemType.TEXT_INPUT_FIELD, form);
        if (suitableItem != null) {
            return suitableItem;
        }

        suitableItem = form.getFormItems().get(0);

        return suitableItem;
    }

    public DraftForm getGalleryRealForm() {
        DraftForm form = null;
        if (gallery.getFormFilterId() != null && gallery.getFormFilterId() > 0) {
            DraftFormFilter formFilter = persistance.getFormFilterById(gallery.getFormFilterId());
            if (formFilter != null) {
                form = formFilter.getDraftForm();
            }
        } else {
            form = persistance.getFormById(gallery.getFormId1());
        }

        return form;
    }

    private final Gallery gallery;
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

}
