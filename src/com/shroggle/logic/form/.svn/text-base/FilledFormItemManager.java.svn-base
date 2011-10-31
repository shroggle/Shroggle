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
package com.shroggle.logic.form;

import com.shroggle.entity.*;
import com.shroggle.exception.FilledFormItemNotFoundException;
import com.shroggle.logic.site.taxRates.TaxManager;
import com.shroggle.util.*;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.resource.provider.ResourceGetterType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy, dmitry.solomadin
 */
public class FilledFormItemManager {

    public FilledFormItemManager(FilledFormItem filledFormItem) {
        if (filledFormItem == null) {
            throw new FilledFormItemNotFoundException("Can`t create FilledFormItemManager without FilledFormItem.");
        }
        this.filledFormItem = filledFormItem;
    }

    public String getStringValueForDataExport() {
        String retValue;
        switch (filledFormItem.getFormItemName().getType()) {
            case TEXT_INPUT_FIELD:
            case SELECT:
            case TWO_TEXT_FIELDS:
            case TWO_PICK_LISTS:
            case THREE_PICK_LISTS:
            case PICK_LIST_AND_TEXT_FIELD:
            case SINGLE_CHOICE_OPTION_LIST:
            case MULITSELECT:
            case SELECTION_LIST:
            case FIVE_PICK_LISTS: {
                retValue = filledFormItem.getValue();
                break;
            }
            case TEXT_AREA:
            case TEXT_AREA_DOUBLE_SIZE: {
                retValue = "\"" + filledFormItem.getValue() + "\"";
                break;
            }
            case FILE_UPLOAD: {
                retValue = getFormFileName();
                break;
            }
            case LINKED: {
                final StringBuilder value = new StringBuilder();
                for (String tempValue : LinkedFormManager.getLinkedValues(filledFormItem)) {
                    value.append(tempValue);
                    value.append(" ");
                }
                retValue = value.toString();
                break;
            }
            case SPECIAL:
            case ACCESS_GROUPS:
            case RADIO_LIST:
            case CHECKBOX:
            case RADIOBUTTON: {
                retValue = "";
                break;
            }
            default: {
                logger.warning("Unknown FormItemType = " + filledFormItem.getFormItemName().getType());
                retValue = "";
                break;
            }
        }
        return StringUtil.getEmptyOrString(retValue);
    }

    public String getFormFileName() {
        if (filledFormItem.getFormItemName().getType() != FormItemType.FILE_UPLOAD) {
            return "";
        }
        if (filledFormItem.getFormItemName() == FormItemName.VIDEO_FILE_UPLOAD) {
            final FormVideo formVideo = persistance.getFormVideoById(filledFormItem.getIntValue());
            if (formVideo != null) {
                final Video video = persistance.getVideoById(formVideo.getVideoId());
                return video != null ? video.getName() : "";
            }
        } else {
            final FormFile formFile = persistance.getFormFileById(new FilledFormItemManager(filledFormItem).getFormImageId());
            return formFile != null ? formFile.getSourceName() : "";
        }
        return "";
    }

    // if siteId will be null tax calculation will be disabled.

    public String getFormattedValue(Integer siteId) {
        String formattedValues = "";

        if (filledFormItem.getFormItemName() == FormItemName.PRICE) {
            if (filledFormItem.getValue() != null) {
                double initialPrice = DoubleUtil.safeParse(filledFormItem.getValue());
                String roundedPrice = DoubleUtil.roundWithPrecision(initialPrice, 2);
                // SW-6499 | when store product is 0.00 it should just say 'Free'
                if (roundedPrice.equals("0.00")) {
                    return "Free";
                } else {
                    return "$" +  roundedPrice + " " +
                        new TaxManager().calculateTaxForRender(filledFormItem.getFilledForm(), initialPrice, null, siteId).getTaxString();
                }
            }
        } else if (filledFormItem.getFormItemName().getCheckers().contains(FormItemCheckerType.DATE_FORMAT_HH_MM)) {
            formattedValues = DateUtil.constructDateString(CollectionUtil.getEmptyOrString(filledFormItem.getValues(), 0),
                    CollectionUtil.getEmptyOrString(filledFormItem.getValues(), 1), true);
        } else if (filledFormItem.getFormItemName().getCheckers().contains(FormItemCheckerType.DATE_FORMAT_DD_MM_YYYY)) {
            formattedValues = DateUtil.constructDateString(CollectionUtil.getEmptyOrString(filledFormItem.getValues(), 0),
                    CollectionUtil.getEmptyOrString(filledFormItem.getValues(), 1), CollectionUtil.getEmptyOrString(filledFormItem.getValues(), 2), true);
        } else if (filledFormItem.getFormItemName().getCheckers().contains(FormItemCheckerType.DATE_FORMAT_DD_MM_YYYY_HH_MM)) {
            formattedValues = DateUtil.constructDateString(CollectionUtil.getEmptyOrString(filledFormItem.getValues(), 0),
                    CollectionUtil.getEmptyOrString(filledFormItem.getValues(), 1), CollectionUtil.getEmptyOrString(filledFormItem.getValues(), 2),
                    CollectionUtil.getEmptyOrString(filledFormItem.getValues(), 3), CollectionUtil.getEmptyOrString(filledFormItem.getValues(), 4), true);
        } else if (filledFormItem.getFormItemName() == FormItemName.LINKED) {
            List<String> linkedValues = LinkedFormManager.getLinkedValues(filledFormItem);
            for (int i = 0; i < linkedValues.size(); i++) {
                String linkedValue = linkedValues.get(i);

                formattedValues = formattedValues + (i == 0 ? "" : ", ") + linkedValue;
            }
        } else if (filledFormItem.getFormItemName() == FormItemName.INTERNAL_LINK) {
            formattedValues = "<a href=\"" + filledFormItem.getValue() + "\">" + filledFormItem.getValue() + "</a>";
        } else if (filledFormItem.getFormItemName() == FormItemName.URL ||
                filledFormItem.getFormItemName() == FormItemName.SECOND_URL) {
            // If URL not starts from http:// then lets add it to url otherwise url will lead on currnet site.
            // i.e 'test.com' url will lead on shroggle.com/page/test.com.
            String urlHref = filledFormItem.getValue();
            if (!urlHref.startsWith("http://")) {
                urlHref = "http://" + urlHref;
            }
            formattedValues = "<a href=\"" + urlHref + "\">" + filledFormItem.getValue() + "</a>";
        } else if (filledFormItem.getFormItemName() == FormItemName.AUDIO_FILE_UPLOAD ||
                filledFormItem.getFormItemName() == FormItemName.PDF_FILE_UPLOAD) {
            final FormFile formFile = persistance.getFormFileById(FilledFormItemManager.getIntValue(filledFormItem));
            if (formFile != null) {
                final String linkOnFile = ServiceLocator.getResourceGetter().get(
                        ResourceGetterType.FORM_FILE, FilledFormItemManager.getIntValue(filledFormItem),
                        0, 0, 0, false);
                final String fileName = formFile.getSourceName();
                final String linkOnFileLabel = filledFormItem.getFormItemName() == FormItemName.AUDIO_FILE_UPLOAD ?
                        "Play audio file" : "Open PDF file";

                formattedValues = fileName + " <a target='_blank' href=\"" + linkOnFile + "\">" + linkOnFileLabel + "</a>";
            } else {
                formattedValues = "";
            }
        } else {
            List<String> values = filledFormItem.getValues();
            for (int i = 0; i < values.size(); i++) {
                String value = values.get(i);

                formattedValues = formattedValues + (i == 0 ? "" : ", ") + value;
            }
        }

        return formattedValues;
    }

    // Formats date with ";" separator between date parts, like this: 12;17;1988;16;45

    public String formatDateAsString() {
        String resultValue = "";
        for (int i = 0; i < filledFormItem.getValues().size(); i++) {
            final String value = filledFormItem.getValues().get(i);
            resultValue += value + (i != filledFormItem.getValues().size() - 1 ? ";" : "");
        }

        return resultValue;
    }

    public static int getIntValue(final FilledFormItem filledFormItem) {
        return getIntValue(filledFormItem, 0);
    }

    public static double getDoubleValue(final FilledFormItem filledFormItem) {
        return getDoubleValue(filledFormItem, 0);
    }

    public static double getDoubleValue(final FilledFormItem filledFormItem, final int index) {
        try {
            return Double.parseDouble(getValue(filledFormItem, index));
        } catch (Exception e) {
            return 0;
        }
    }

    public static Double getDoubleValueOrNull(final FilledFormItem filledFormItem, final int index) {
        try {
            return Double.parseDouble(getValue(filledFormItem, index));
        } catch (Exception e) {
            return null;
        }
    }

    public static int getIntValue(final FilledFormItem filledFormItem, final int index) {
        try {
            return Integer.parseInt(getValue(filledFormItem, index));
        } catch (Exception e) {
            return 0;
        }
    }

    public static String getValue(final FilledFormItem filledFormItem, final int index) {
        if (filledFormItem != null) {
            if (filledFormItem.getValues().size() > index) {
                final String value = filledFormItem.getValues().get(index);
                if (value != null && !value.isEmpty()) {
                    return value;
                }
            }
        }
        return "";
    }

    public static String getValue(final FilledFormItem filledFormItem) {
        return getValue(filledFormItem, 0);
    }

    public static String extractKeywordsValue(final FilledFormItem filledItemUsedAsKeywords, final String delimiter) {
        String keywords = "";
        if (filledItemUsedAsKeywords != null && !filledItemUsedAsKeywords.getValue().isEmpty()) {
            StringTokenizer stringTokenizer = new StringTokenizer(filledItemUsedAsKeywords.getValue(),
                    " \t\n\r\f;+,;");
            boolean first = true;
            while (stringTokenizer.hasMoreTokens()) {
                keywords = keywords + (first ? "" : delimiter) + stringTokenizer.nextToken();
                first = false;
            }
        }

        return keywords;
    }

    public Integer getFormImageId() {
        final List<String> values = this.filledFormItem.getValues();
        if (isImageFormItem() && !values.isEmpty()) {
            try {
                return Integer.valueOf(values.get(0));
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public void setFormImageId(final Integer id) {
        final List<String> values = new ArrayList<String>(this.filledFormItem.getValues());
        if (id != null && isImageFormItem()) {
            if (!values.isEmpty()) {
                // removing old imageId from values.
                values.remove(0);
                // formImageId should be on the first place, alt - on the second.
                values.add(0, id.toString());
            } else {
                values.add(id.toString());
            }
            this.filledFormItem.setValues(values);
        } else {
            logger.warning("Can`t set formImage id to filledFormItem with id = " + filledFormItem.getItemId() + ".");
        }
    }

    public void setFormImageId(final String id) {
        try {
            setFormImageId(Integer.valueOf(id));
        } catch (Exception e) {
            logger.warning("Can`t parse formImageId value = " + id);
            setFormImageId(-1);
        }
    }

    public String getFormImageAlt() {
        final List<String> values = this.filledFormItem.getValues();
        if (isImageFormItem() && values.size() > 1) {
            return values.get(1);
        }
        return "";
    }

    public void setFormImageAlt(final String alt) {
        final List<String> values = new ArrayList<String>(this.filledFormItem.getValues());
        if (alt != null && isImageFormItem()) {
            if (values.isEmpty()) {
                values.addAll(Arrays.asList("-1", alt));
            } else if (values.size() == 1) {
                // formImageId should be on the first place, alt - on the second.
                values.add(alt);
            } else if (values.size() > 1) {
                // removing old keyvords from values.
                values.remove(1);
                // formImageId should be on the first place, alt - on the second.
                values.add(1, alt);
            }
            this.filledFormItem.setValues(values);
        } else {
            logger.warning("Can`t set alt id to filledFormItem with id = " + filledFormItem.getItemId() + ".");
        }
    }

    private boolean isImageFormItem() {
        return (this.filledFormItem != null && this.filledFormItem.getFormItemName() == FormItemName.IMAGE_FILE_UPLOAD);
    }

    private final FilledFormItem filledFormItem;
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final Persistance persistance = ServiceLocator.getPersistance();
}
