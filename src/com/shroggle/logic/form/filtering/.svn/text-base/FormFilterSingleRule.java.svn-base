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
package com.shroggle.logic.form.filtering;

import com.shroggle.entity.*;
import com.shroggle.util.*;
import com.shroggle.util.filesystem.FileSystemException;
import com.shroggle.logic.form.FilledFormLogic;
import com.shroggle.logic.form.FilledFormItemManager;
import com.shroggle.logic.coordinates.CoordinateCreator;
import com.shroggle.logic.distance.Distance;
import com.shroggle.logic.advancedSearch.AdvancedSearchHelper;
import com.shroggle.exception.InconsistentCriteriaException;

import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author Artem Stasuk
 */
public class FormFilterSingleRule implements FormFilterRuleProcessor {

    public FormFilterSingleRule(final FormFilterRuleIface rule) {
        this.rule = rule;
    }

    @Override
    public boolean accept(final FilledFormLogic filledFormLogic) {
        /* CHECKING RULE CONSISTENSY */
        if (filledFormLogic == null) {
            return false;
        }

        if (rule == null || rule.getCriteria() == null || rule.getCriteria().isEmpty()) {
            return true;
        }

        final FilledFormItem item = filledFormLogic.getFilledItemByFormItemId(rule.getFormItemId());
        if (item == null) {
            return true;
        }

        final List<String> ruleCriteriaList = rule.getCriteria();

        /* FILTERING */
        final boolean formAccepted;
        if (rule.getDisplayType().isPickTypeMultiSelect()) {
            boolean formAcceptedInternal = false;
            for (String criteria : ruleCriteriaList) {
                if (executeFilteringInternal(filledFormLogic, item, Arrays.asList(criteria))) {
                    formAcceptedInternal = true;
                    break;
                }
            }
            formAccepted = formAcceptedInternal;
        } else {
            formAccepted = executeFilteringInternal(filledFormLogic, item, ruleCriteriaList);
        }


        if (rule.isInclude()) {
            return formAccepted;
        } else {
            return !formAccepted;
        }
    }

    private boolean executeFilteringInternal(final FilledFormLogic filledFormLogic, final FilledFormItem item, final List<String> ruleCriteria) {
        if (item.getFormItemName().getType() == FormItemType.FILE_UPLOAD) {
            // Filtering file upload fields.
            return filterFileUpload(item, ruleCriteria);
        } else if (item.getFormItemName() == FormItemName.POST_CODE) {
            return filterPostCode(item, ruleCriteria);
        } else if (item.getFormItemName().getCheckers().contains(FormItemCheckerType.DATE_FORMAT_HH_MM)) {
            // Filtering hh:mm dates.
            return filterDate_HH_MM(item, ruleCriteria);
        } else if (item.getFormItemName().getCheckers().contains(FormItemCheckerType.DATE_FORMAT_DD_MM_YYYY)) {
            // Filtering dd/mm/yyyy dates.
            return filterDate_MM_DD_YYY(item, ruleCriteria);
        } else if (item.getFormItemName().getCheckers().contains(FormItemCheckerType.DATE_FORMAT_DD_MM_YYYY_HH_MM)) {
            // Filtering dd/mm/yyyy hh:mm dates.
            return filterDate_MM_DD_YYYY_HH_MM(item, ruleCriteria);
        } else if (item.getFormItemName().getCheckers().contains(FormItemCheckerType.ONLY_NUMBERS)) {
            // Filtering ONLY_NUMBERS fields.
            return filterONLY_NUMBERS(item, ruleCriteria);
        }  else if (item.getFormItemName().getType() == FormItemType.CHECKBOX) {
            // Filtering ONLY_NUMBERS fields.
            return filterSingleCheckbox(item, ruleCriteria);
        } else {
            /* ADDING ADDITIONAL ITEMS TO SEARCH BY (this option currently available only for text item in adv. search) */
            final List<FilledFormItem> itemsToSearchBy = new ArrayList<FilledFormItem>();
            itemsToSearchBy.add(item);
            if (rule.getAlsoSearchByFields() != null && !rule.getAlsoSearchByFields().isEmpty()) {
                for (Integer additionalItemId : rule.getAlsoSearchByFields()) {
                    final FilledFormItem additionalItem = filledFormLogic.getFilledItemByFormItemId(additionalItemId);

                    if (additionalItem != null) {
                        itemsToSearchBy.add(additionalItem);
                    }
                }
            }

            return filterSimpleStringFields(itemsToSearchBy, ruleCriteria);
        }

    }

    private boolean filterPostCode(final FilledFormItem item, List<String> criteria) {
        if (criteria.size() != 2) {
            //todo this means that we are searching for filters. fix.
            return true;
        }

        final String zipInSearch = criteria.get(0);

        final Coordinate coordinateInForm = CoordinateCreator.createCoordinate(item);
        final Coordinate coordinateInSearch = CoordinateCreator.getExistingOrCreateNew(zipInSearch);

        // "null" means "Any value".
        if (criteria.get(1).equals("null")) {
            return true;
        }

        final double allowedDistance = Double.parseDouble(criteria.get(1));

        if (coordinateInForm != null) {
            final Double realDistance = new Distance(coordinateInForm, coordinateInSearch).getNauticalMiles();

            if (realDistance == null) {
                return false;
            }

            if (allowedDistance > realDistance) {
                return true;
            }
        }

        return false;
    }

    /*
    * SimpleStringFileds include such fields as:
    * simple pick lists
    * multiple pick lists
    * checkboxes
    * text fields
    * text areas
    */

    private boolean filterSimpleStringFields(final List<FilledFormItem> items, final List<String> criteria) {
        for (FilledFormItem item : items) {
            final List<String> values = item.getValues();
            if (values.size() >= criteria.size()) {
                // Checking if every value in criterias equals to value in filled form item.
                for (int i = 0; i < criteria.size(); i++) {
                    final String value = StringUtil.getEmptyOrString(values.get(i));
                    final String ruleValue = StringUtil.getEmptyOrString(criteria.get(i));
                    if (value.contains(ruleValue)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean filterDate_HH_MM(final FilledFormItem item, List<String> criteria) {
        final String hoursInForm = FilledFormItemManager.getValue(item, 0);
        final String minutesInForm = FilledFormItemManager.getValue(item, 1);

        Date dateInForm;
        if (hoursInForm.isEmpty() && minutesInForm.isEmpty()) {
            return true;
        } else {
            dateInForm = DateUtil.constructDate(hoursInForm, minutesInForm);
        }

        if (getFilteringMode() == FilteringMode.ADV_SEARCH &&
                !criteria.isEmpty()) {
            criteria = AdvancedSearchHelper.splitDateOption(criteria.get(0));
        }

        final String hoursFrom = criteria.get(0);
        final String minutesFrom = criteria.get(1);
        Date dateFrom;
        if (hoursFrom.isEmpty() && minutesFrom.isEmpty()) {
            dateFrom = null;
        } else {
            dateFrom = DateUtil.constructDate(hoursFrom, minutesFrom);
        }

        final Date dateTill;
        if (criteria.size() == item.getFormItemName().getType().getFieldsCount()) {
            dateTill = null;
        } else {
            final String hoursTill = criteria.get(2);
            final String minutesTill = criteria.get(3);
            if (hoursTill.isEmpty() && minutesTill.isEmpty()) {
                dateTill = null;
            } else {
                dateTill = DateUtil.constructDate(hoursTill, minutesTill);
            }
        }

        return isDateInFormInsideDatePeriod(dateInForm, dateFrom, dateTill);
    }

    private boolean filterDate_MM_DD_YYY(final FilledFormItem item, List<String> criteria) {
        final String monthInForm = FilledFormItemManager.getValue(item, 0);
        final String dayInForm = FilledFormItemManager.getValue(item, 1);
        final String yearInForm = FilledFormItemManager.getValue(item, 2);

        Date dateInForm;
        if (monthInForm.isEmpty() && dayInForm.isEmpty() && yearInForm.isEmpty()) {
            return true;
        } else {
            dateInForm = DateUtil.constructDate(monthInForm, dayInForm, yearInForm);
        }

        if (getFilteringMode() == FilteringMode.ADV_SEARCH &&
                !criteria.isEmpty()) {
            criteria = AdvancedSearchHelper.splitDateOption(criteria.get(0));
        }

        final String monthFrom = criteria.get(0);
        final String dayFrom = criteria.get(1);
        final String yearFrom = criteria.get(2);
        Date dateFrom;
        if (monthFrom.isEmpty() && dayFrom.isEmpty() && yearFrom.isEmpty()) {
            dateFrom = null;
        } else {
            dateFrom = DateUtil.constructDate(monthFrom, dayFrom, yearFrom);
        }

        final Date dateTill;
        if (criteria.size() == item.getFormItemName().getType().getFieldsCount()) {
            dateTill = null;
        } else {
            final String monthTill = criteria.get(3);
            final String dayTill = criteria.get(4);
            final String yearTill = criteria.get(5);
            if (monthTill.isEmpty() && dayTill.isEmpty() && yearTill.isEmpty()) {
                dateTill = null;
            } else {
                dateTill = DateUtil.constructDate(monthTill, dayTill, yearTill);
            }
        }

        return dateFrom == null && dateTill == null || isDateInFormInsideDatePeriod(dateInForm, dateFrom, dateTill);
    }

    private boolean filterDate_MM_DD_YYYY_HH_MM(final FilledFormItem item, List<String> criteria) {
        final String monthInForm = FilledFormItemManager.getValue(item, 0);
        final String dayInForm = FilledFormItemManager.getValue(item, 1);
        final String yearInForm = FilledFormItemManager.getValue(item, 2);
        final String hoursInForm = FilledFormItemManager.getValue(item, 3);
        final String minutesInForm = FilledFormItemManager.getValue(item, 4);

        Date dateInForm;
        if (monthInForm.isEmpty() && dayInForm.isEmpty() && yearInForm.isEmpty()
                && hoursInForm.isEmpty() && minutesInForm.isEmpty()) {
            return true;
        } else {
            dateInForm = DateUtil.constructDate(monthInForm, dayInForm, yearInForm, hoursInForm, minutesInForm);
        }

        if (getFilteringMode() == FilteringMode.ADV_SEARCH &&
                !criteria.isEmpty()) {
            criteria = AdvancedSearchHelper.splitDateOption(criteria.get(0));
        }

        final String monthFrom = criteria.get(0);
        final String dayFrom = criteria.get(1);
        final String yearFrom = criteria.get(2);
        final String hoursFrom = criteria.get(3);
        final String minutesFrom = criteria.get(4);
        Date dateFrom;
        if (monthFrom.isEmpty() && dayFrom.isEmpty() && yearFrom.isEmpty()
                && hoursFrom.isEmpty() && minutesFrom.isEmpty()) {
            dateFrom = null;
        } else {
            dateFrom = DateUtil.constructDate(monthFrom, dayFrom, yearFrom, hoursFrom, minutesFrom);
        }

        final Date dateTill;
        if (criteria.size() == item.getFormItemName().getType().getFieldsCount()) {
            dateTill = null;
        } else {
            final String monthTill = criteria.get(5);
            final String dayTill = criteria.get(6);
            final String yearTill = criteria.get(7);
            final String hoursTill = criteria.get(8);
            final String minutesTill = criteria.get(9);
            if (monthTill.isEmpty() && dayTill.isEmpty() && yearTill.isEmpty()
                    && hoursTill.isEmpty() && minutesTill.isEmpty()) {
                dateTill = null;
            } else {
                dateTill = DateUtil.constructDate(monthTill, dayTill, yearTill, hoursTill, minutesTill);
            }
        }

        return isDateInFormInsideDatePeriod(dateInForm, dateFrom, dateTill);
    }

    private boolean filterONLY_NUMBERS(final FilledFormItem item, List<String> criteria) {
        final Double numberInForm = FilledFormItemManager.getDoubleValueOrNull(item, 0);

        if (numberInForm == null) {
            return true;
        }

        if (getFilteringMode() == FilteringMode.ADV_SEARCH &&
                !criteria.isEmpty()) {
            criteria = AdvancedSearchHelper.splitDateOption(criteria.get(0));
        }

        final Double from = criteria.size() > 0 ? DoubleUtil.getDoubleOrNull(criteria.get(0)) : null;
        final Double till = criteria.size() > 1 ? DoubleUtil.getDoubleOrNull(criteria.get(1)) : null;

        if (from == null && till == null) {
            return true;
        }

        boolean checkForRange = rule.getDisplayType() == OptionDisplayType.NONE || rule.getDisplayType().checkForRange();

        if (from != null && till != null) {
            return (checkForRange && from < numberInForm && numberInForm < till)
                    || from.equals(numberInForm) || till.equals(numberInForm);
        } else if (from == null) {
            return (checkForRange && numberInForm < till) || till.equals(numberInForm);
        } else {
            return (checkForRange && numberInForm > from) || from.equals(numberInForm);
        }
    }

     private boolean filterSingleCheckbox(final FilledFormItem item, List<String> criteria) {
        final String valueInForm = FilledFormItemManager.getValue(item);

        if (valueInForm == null) {
            return true;
        } else {
            for (String singleCriteria : criteria){
                if (singleCriteria.equals(valueInForm)){
                    return true;
                }
            }

            return false;
        }
    }

    private boolean filterFileUpload(final FilledFormItem item, final List<String> criteria) {
        if (criteria.size() < 2) {
            throw new InconsistentCriteriaException("Rule for file upload should have 2 criterias");
        }

        final Resource resource;
        if (item.getFormItemName() == FormItemName.VIDEO_FILE_UPLOAD) {
            final FormVideo formVideo = ServiceLocator.getPersistance().getFormVideoById(FilledFormItemManager.getIntValue(item));
            if (formVideo != null) {
                resource = ServiceLocator.getPersistance().getVideoById(formVideo.getVideoId());
            } else {
                return false;
            }
        } else {
            resource = ServiceLocator.getPersistance().getFormFileById(FilledFormItemManager.getIntValue(item));
        }

        if (resource != null) {
            try {
                final InputStream inputStream = ServiceLocator.getFileSystem().getResourceStream(resource);

                if (inputStream == null) {
                    return false;
                }

                final long fileSize = inputStream.available();
                inputStream.close();

                final long minValue = criteria.get(0).trim().isEmpty() ? 0 : LongUtil.parseLong(criteria.get(0));
                final Long maxValue = criteria.get(1).trim().isEmpty() ? null : LongUtil.parseLong(criteria.get(1));
                if (fileSize >= minValue && (maxValue == null || fileSize <= maxValue)) {
                    return true;
                }
            } catch (FileSystemException fileSystemException) {
                if (fileSystemException.getCause() instanceof FileNotFoundException) {
                    return false;
                } else {
                    throw fileSystemException;
                }
            } catch (IOException e) {
                //
            }
        }

        return false;
    }

    private boolean isDateInFormInsideDatePeriod(final Date dateInForm, final Date dateFrom, final Date dateTill) {
        boolean checkForRange = rule.getDisplayType() == OptionDisplayType.NONE || rule.getDisplayType().checkForRange();

        if (dateFrom != null && dateTill != null) {
            return (checkForRange && dateInForm.after(dateFrom) && dateInForm.before(dateTill)) ||
                    dateInForm.equals(dateFrom) || dateInForm.equals(dateTill);
        } else if (dateFrom == null) {
            return (checkForRange && dateInForm.before(dateTill)) || dateInForm.equals(dateTill);
        } else {
            return (checkForRange && dateInForm.after(dateFrom)) || dateInForm.equals(dateFrom);
        }
    }


    private FilteringMode getFilteringMode() {
        if (rule instanceof DraftAdvancedSearchOption) {
            return FilteringMode.ADV_SEARCH;
        } else {
            return FilteringMode.FILTERS;
        }
    }

    private enum FilteringMode {
        ADV_SEARCH, FILTERS
    }

    private final FormFilterRuleIface rule;

}
