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

package com.shroggle.logic.advancedSearch;

import com.shroggle.entity.*;
import com.shroggle.exception.MalformedDateRangeException;
import com.shroggle.logic.SiteItemsManager;
import com.shroggle.logic.form.*;
import com.shroggle.logic.form.FormManager;
import com.shroggle.logic.gallery.*;
import com.shroggle.logic.gallery.paypal.PaypalSettingsForGalleryManager;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.gallery.SaveGalleryRequest;
import com.shroggle.util.CollectionUtil;
import com.shroggle.util.DateUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.persistance.PersistanceTransactionContext;

import java.util.*;

/**
 * @author dmitry.solomadin
 */
public class AdvancedSearchHelper {

    // If range is a date then formats separate date parts as one date (i.e 12;17;1988 to 12-17-1988)
    // If range is a ONLY_NUMBERS field then if criteria got ranges (e.g ;5 or 1;2 or 5;) return formatted value else
    // returns actual value
    public static String formatRange(final DraftFormItem formItem, final String criteria, final boolean formatAsRange) {
        if (formItem.getFormItemName().getCheckers().contains(FormItemCheckerType.ONLY_NUMBERS)) {
            if (criteria.contains(";")) {
                String[] criterias = criteria.split(";");

                if (criterias.length > 1) {
                    String fromCriteria = criterias[0];
                    String tillCriteria = criterias[1];

                    if (!fromCriteria.isEmpty()) {
                        return fromCriteria + "&nbsp;&mdash;&nbsp;" + tillCriteria;
                    } else {
                        return "<&nbsp;" + tillCriteria;
                    }
                } else {
                    return ">&nbsp;" + criterias[0];
                }
            } else {
                return criteria;
            }
        }

        String resultRanges = "";
        String resultStartRange = "";
        String resultEndRange = "";
        boolean allStartRangesAreEmpty = true;
        boolean allEndRangesAreEmpty = true;
        if (formItem.getFormItemName().isDate()) {
            final List<String> dateString = Arrays.asList(criteria.split(";"));

            for (int i = 0; i < formItem.getFormItemName().getType().getFieldsCount(); i++) {
                if (!CollectionUtil.getEmptyOrString(dateString, i).isEmpty()) {
                    allStartRangesAreEmpty = false;
                }
            }

            for (int i = formItem.getFormItemName().getType().getFieldsCount(); i < formItem.getFormItemName().getType().getFieldsCount() * 2; i++) {
                if (!CollectionUtil.getEmptyOrString(dateString, i).isEmpty()) {
                    allEndRangesAreEmpty = false;
                }
            }

            if (formItem.getFormItemName().getCheckers().contains(FormItemCheckerType.DATE_FORMAT_HH_MM)) {
                resultStartRange = DateUtil.constructDateString(CollectionUtil.getEmptyOrString(dateString, 0),
                        CollectionUtil.getEmptyOrString(dateString, 1), true);
                resultEndRange = DateUtil.constructDateString(CollectionUtil.getEmptyOrString(dateString, 2),
                        CollectionUtil.getEmptyOrString(dateString, 3), true);
            } else if (formItem.getFormItemName().getCheckers().contains(FormItemCheckerType.DATE_FORMAT_DD_MM_YYYY)) {
                resultStartRange = DateUtil.constructDateString(CollectionUtil.getEmptyOrString(dateString, 0),
                        CollectionUtil.getEmptyOrString(dateString, 1), CollectionUtil.getEmptyOrString(dateString, 2), true);
                resultEndRange = DateUtil.constructDateString(CollectionUtil.getEmptyOrString(dateString, 3),
                        CollectionUtil.getEmptyOrString(dateString, 4), CollectionUtil.getEmptyOrString(dateString, 5), true);
            } else if (formItem.getFormItemName().getCheckers().contains(FormItemCheckerType.DATE_FORMAT_DD_MM_YYYY_HH_MM)) {
                resultStartRange = DateUtil.constructDateString(CollectionUtil.getEmptyOrString(dateString, 0),
                        CollectionUtil.getEmptyOrString(dateString, 1), CollectionUtil.getEmptyOrString(dateString, 2),
                        CollectionUtil.getEmptyOrString(dateString, 3), CollectionUtil.getEmptyOrString(dateString, 4), true);
                resultEndRange = DateUtil.constructDateString(CollectionUtil.getEmptyOrString(dateString, 5),
                        CollectionUtil.getEmptyOrString(dateString, 6), CollectionUtil.getEmptyOrString(dateString, 7),
                        CollectionUtil.getEmptyOrString(dateString, 8), CollectionUtil.getEmptyOrString(dateString, 9), true);
            } else {
                throw new UnsupportedOperationException();
            }

            if (allStartRangesAreEmpty && allEndRangesAreEmpty) {
                throw new MalformedDateRangeException("Start and end ranges are empty, at least one range should be filled");
            }

            if (allStartRangesAreEmpty) {
                return (formatAsRange ? "<&nbsp;" : "") + resultEndRange;
            }

            if (allEndRangesAreEmpty) {
                return (formatAsRange ? ">&nbsp;" : "") + resultStartRange;
            }

            resultRanges = resultStartRange + "&nbsp;&mdash;&nbsp;" + resultEndRange;
        }

        return resultRanges;
    }

    public void addSeparateOptionCriteria(final DraftAdvancedSearchOption searchOption) {
        final List<String> optionCriteriaList = new ArrayList<String>();

        final List<FilledFormItem> filledFormItems = persistance.getFilledFormItemByFormItemId(searchOption.getFormItemId());
        final DraftFormItem formItem = persistance.getFormItemById(searchOption.getFormItemId());

        for (FilledFormItem filledFormItem : filledFormItems) {
            final String optionCriteria;
            if (formItem.getFormItemName().isDate()) {
                optionCriteria = new FilledFormItemManager(filledFormItem).formatDateAsString();
            } else {
                optionCriteria = FilledFormItemManager.getValue(filledFormItem, 0);
            }

            if (!StringUtil.isNullOrEmpty(optionCriteria) && !optionCriteriaList.contains(optionCriteria)) {
                optionCriteriaList.add(optionCriteria);
            }
        }

        persistanceTransaction.execute(new Runnable() {
            public void run() {
                searchOption.setOptionCriteria(optionCriteriaList);
            }
        });
    }

    public DraftAdvancedSearch createArtificialAdvancedSearch(final DraftAdvancedSearch advancedSearch) {
        final DraftAdvancedSearch artificialAdvancedSearch = new DraftAdvancedSearch();

        artificialAdvancedSearch.setId(advancedSearch.getId());
        for (DraftAdvancedSearchOption searchOption : advancedSearch.getAdvancedSearchOptions()) {
            addArtificialOption(artificialAdvancedSearch, searchOption);
        }

        return artificialAdvancedSearch;
    }

    public DraftAdvancedSearchOption addArtificialOption(final DraftAdvancedSearch artificialAdvancedSearch,
                                                         final DraftAdvancedSearchOption searchOption) {
        final DraftAdvancedSearchOption artificialSearchOption = new DraftAdvancedSearchOption();
        artificialSearchOption.setAdvancedSearch(artificialAdvancedSearch);
        artificialSearchOption.setDisplayType(searchOption.getDisplayType());
        artificialSearchOption.setAlsoSearchByFields(searchOption.getAlsoSearchByFields());
        artificialSearchOption.setOptionCriteria(new ArrayList<String>());
        artificialSearchOption.setFormItemId(searchOption.getFormItemId());
        artificialSearchOption.setFieldLabel(searchOption.getFieldLabel());
        artificialSearchOption.setPosition(searchOption.getPosition());
        artificialSearchOption.setAdvancedSearchOptionId(searchOption.getAdvancedSearchOptionId());

        artificialAdvancedSearch.addSearchOption(artificialSearchOption);

        return artificialSearchOption;
    }

    // Splits advanced search range criteria into normal criteria to filter.

    public static List<String> splitDateOption(final String optionCriteria) {
        return Arrays.asList(optionCriteria.split(";"));
    }

    public void sortOptionsByPosition(final List<DraftAdvancedSearchOption> searchOptions) {
        Collections.sort(searchOptions, new SearchOptionPositionComparator());
    }

    public FormLogic createDefaultSearchForm(final int siteId, final UserManager userManager) {
        final SiteManager siteManager = userManager.getRight().getSiteRight().getSiteForEdit(siteId);

        return persistanceTransaction.execute(
                new PersistanceTransactionContext<FormLogic>() {
                    public FormLogic execute() {
                        return siteManager.getForms().createCustomForm(getDefaultSearchItems(), "Advanced Search Default Form", false);
                    }
                });
    }

    public Gallery createDefaultGallery(final int siteId, final int formId) {
        final DraftForm form = persistance.getFormById(formId);
        final FormItem imageField = FormManager.getFormItemByFormItemName(FormItemName.IMAGE_FILE_UPLOAD, form);
        final SaveGalleryRequest gallerySaveRequest = createDefaultAdvSearchGalleryRequest(siteId,
                imageField != null ? imageField.getFormItemId() : null);
        final GalleryEdit galleryEdit = gallerySaveRequest.getGallerySave();
        galleryEdit.setFormId(formId);

        return persistanceTransaction.execute(
                new PersistanceTransactionContext<Gallery>() {
                    public Gallery execute() {
                        final GalleryManager galleryManager = GalleryManager.createInstance(-1, siteId);
                        galleryManager.save(galleryEdit);
                        return galleryManager.getEntity();
                    }
                });
    }

    //Keep this method here, because settings can change 

    private static SaveGalleryRequest createDefaultAdvSearchGalleryRequest(final int siteId,
                                                                           final Integer imageFormItemId) {
        SaveGalleryRequest request = new SaveGalleryRequest();
        request.setGalleryId(-1);
        request.setWidgetGalleryId(null);
        GalleryEdit galleryEdit = new GalleryEdit();
        galleryEdit.setFirstSort(3);
        galleryEdit.setSecondSort(0);
        galleryEdit.setName(SiteItemsManager.getNextDefaultName(ItemType.GALLERY, siteId, "Default Advanced Search Gallery", false));
        galleryEdit.setNotesComments("");
        galleryEdit.setShowNotesComments(true);
        galleryEdit.setFormId(null);
        galleryEdit.setFirstSortType(GallerySortOrder.DESCENDING);
        galleryEdit.setSecondSortType(GallerySortOrder.ASCENDING);
        GalleryItemEdit item = new GalleryItemEdit();
        item.setId(imageFormItemId == null ? 1 : imageFormItemId);
        item.setName("");
        item.setAlign(GalleryAlign.CENTER);
        item.setColumn(GalleryItemColumn.COLUMN_1);
        item.setWidth(800);
        item.setHeight(535);
        galleryEdit.setItems(Arrays.asList(item));
        GalleryLabelEdit label = new GalleryLabelEdit();
        label.setColumn(0);
        label.setId(imageFormItemId == null ? 1 : imageFormItemId);
        label.setAlign(GalleryAlign.CENTER);
        galleryEdit.setLabels(Arrays.asList(label));
        galleryEdit.setNavigationPaginatorType(GalleryNavigationPaginatorType.PREVIOUS_NEXT_WITH_NUMBERS);
        galleryEdit.getDataPaginator().setDataPaginatorType(GalleryDataPaginatorType.PREVIOUS_NEXT);
        galleryEdit.getDataPaginator().setDataPaginatorArrow(null);
        galleryEdit.setOrientation(GalleryOrientation.NAVIGATION_ABOVE_DATA_BELOW);
        galleryEdit.setOrientationLayout("test1");
        galleryEdit.setRows(3);
        galleryEdit.setColumns(3);
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
        galleryEdit.setPaypalSettings(PaypalSettingsForGalleryManager.createDefaultPaypalSettingsData(siteId));
        request.setGallerySave(galleryEdit);
        return request;
    }

    //You can't use this without form. Use create default form method instead.

    private List<DraftFormItem> getDefaultSearchItems() {
        final FormManager formManager = new FormManager();
        final List<DraftFormItem> items = new ArrayList<DraftFormItem>();

        //To avoid duplication of form item names we changing names manually. This needed only for this default gallery form.
        final DraftFormItem nameItem = FormItemManager.createFormItemByName(FormItemName.NAME, 0, false);
        nameItem.setItemName("Name / Title");
        final DraftFormItem imageUploadItem = FormItemManager.createFormItemByName(FormItemName.IMAGE_FILE_UPLOAD, 1, false);
        imageUploadItem.setItemName("Image File Upload(ed)");
        final DraftFormItem descriptionItem = FormItemManager.createFormItemByName(FormItemName.DESCRIPTION, 2, false);
        final DraftFormItem dateAddedItem = FormItemManager.createFormItemByName(FormItemName.DATE_ADDED, 3, false);
        final DraftFormItem sortOrderItem = FormItemManager.createFormItemByName(FormItemName.SORT_ORDER, 4, false);
        final DraftFormItem enteredByItem = FormItemManager.createFormItemByName(FormItemName.ENTERED_BY, 5, false);

        items.add(nameItem);
        items.add(imageUploadItem);
        items.add(descriptionItem);
        items.add(dateAddedItem);
        items.add(sortOrderItem);
        items.add(enteredByItem);

        return items;
    }

    private class SearchOptionPositionComparator implements Comparator<DraftAdvancedSearchOption> {

        public int compare(DraftAdvancedSearchOption o1, DraftAdvancedSearchOption o2) {
            Integer p1 = o1.getPosition();
            Integer p2 = o2.getPosition();
            return p1.compareTo(p2);
        }

    }

    private PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private Persistance persistance = ServiceLocator.getPersistance();

}
