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
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.resource.provider.ResourceGetterType;

import java.util.List;
import java.util.ArrayList;

/**
 * @author dmitry.solomadin
 */
public class LinkedFormManager {

    public static boolean isPrefilledItemsContainsFilledFormItemId(final List<String> prefilledItems,
                                                                   final String filledFormItemId) {
        for (String prefilledItem : prefilledItems) {
            if (prefilledItem.contains(filledFormItemId)) {
                return true;
            }
        }

        return false;
    }

    public static SplitOptionResponse splitAndRestoreLinkedOptions(final List<String> linkedOptions,
                                                                   final List<String> prefilledItems) {
        final List<String> options = new ArrayList<String>();
        final List<String> filledFormsIds = new ArrayList<String>();
        final List<String> filledItemsIds = new ArrayList<String>();

        for (String linkedOption : linkedOptions) {
            final String[] splittedOtpion = linkedOption.split(";");

            options.add(splittedOtpion[0]);
            filledFormsIds.add(splittedOtpion[1]);
            filledItemsIds.add(splittedOtpion[2]);
        }

        if (prefilledItems != null) {
            final SplitOptionResponse splitOptionResponse =
                    LinkedFormManager.getPrefilledOptionsFormOtherFormOrWereDeleted(prefilledItems, filledFormsIds);

            options.addAll(splitOptionResponse.getOptions());
            filledFormsIds.addAll(splitOptionResponse.getLinkedFilledFormsIds());
            filledItemsIds.addAll(splitOptionResponse.getLinkedFilledFormItemsIds());
        }

        final SplitOptionResponse splitOptionResponse = new SplitOptionResponse();
        splitOptionResponse.setOptions(options);
        splitOptionResponse.setLinkedFilledFormsIds(filledFormsIds);
        splitOptionResponse.setLinkedFilledFormItemsIds(filledItemsIds);

        return splitOptionResponse;
    }

    public static class SplitOptionResponse {

        private List<String> options = new ArrayList<String>();

        private List<String> linkedFilledFormsIds = new ArrayList<String>();

        private List<String> linkedFilledFormItemsIds = new ArrayList<String>();

        public void addLinkedFilledFormItemId(String linkedFilledFormItemId) {
            linkedFilledFormItemsIds.add(linkedFilledFormItemId);
        }

        public void addLinkedFilledFormId(String linkedFilledFormId) {
            linkedFilledFormsIds.add(linkedFilledFormId);
        }

        public void addOption(String option) {
            options.add(option);
        }

        public List<String> getLinkedFilledFormItemsIds() {
            return linkedFilledFormItemsIds;
        }

        public void setLinkedFilledFormItemsIds(List<String> linkedFilledFormItemsIds) {
            this.linkedFilledFormItemsIds = linkedFilledFormItemsIds;
        }

        public List<String> getOptions() {
            return options;
        }

        public void setOptions(List<String> options) {
            this.options = options;
        }

        public List<String> getLinkedFilledFormsIds() {
            return linkedFilledFormsIds;
        }

        public void setLinkedFilledFormsIds(List<String> linkedFilledFormsIds) {
            this.linkedFilledFormsIds = linkedFilledFormsIds;
        }
    }

    protected static List<String> getLinkedOptions(final FormItem formItem) {
        if (formItem == null) {
            throw new IllegalArgumentException("formItem cannot be null.");
        }

        final List<String> returnList = new ArrayList<String>();
        final List<FilledFormItem> filledFormItems =
                ServiceLocator.getPersistance().getFilledFormItemByFormItemId(formItem.getLinkedFormItemId());

        for (FilledFormItem filledFormItem : filledFormItems) {
            if (filledFormItem.getValue().isEmpty()){
                // Skip empty values.
                continue;
            }

            returnList.add(filledFormItem.getValue() + ";" + filledFormItem.getFilledForm().getFilledFormId() + ";" + filledFormItem.getItemId());
        }

        return returnList;
    }

    public static List<String> getLinkedValues(final FilledFormItem filledFormItem) {
        if (filledFormItem == null || filledFormItem.getFormItemName() != FormItemName.LINKED) {
            throw new IllegalArgumentException("formItem cannot be null.");
        }

        final List<String> returnList = new ArrayList<String>();

        for (String link : filledFormItem.getValues()) {
            final String linkedValue = getLinkedValue(link);
            if (linkedValue != null) {
                returnList.add(linkedValue);
            }
        }

        return returnList;
    }

    public static FilledForm getLinkedFilledFormByLinkedFilledFormItem(final FilledFormItem filledFormItem){
        if (filledFormItem == null || filledFormItem.getFormItemName() != FormItemName.LINKED) {
            throw new IllegalArgumentException("formItem cannot be null.");
        }

        String linkedFilledForm = filledFormItem.getValues().get(0).split(";")[0];

        return ServiceLocator.getPersistance().getFilledFormById(Integer.parseInt(linkedFilledForm));
    }

    public static String getLinkedValue(final String link) {
        if (link.isEmpty()){
            return null;
        }

        String linkedFilledFormItemId = link.split(";")[1];

        FilledFormItem linkedFilledFormItem;
        try {
            linkedFilledFormItem =
                    ServiceLocator.getPersistance().getFilledFormItemById(Integer.parseInt(linkedFilledFormItemId));
        } catch (NumberFormatException ex) {
            linkedFilledFormItem = null;
        }

        if (linkedFilledFormItem != null) {
            return linkedFilledFormItem.getValue();
        }

        return null;
    }

    public static SplitOptionResponse getPrefilledOptionsFormOtherFormOrWereDeleted(final List<String> prefilledOptions,
                                                                                    final List<String> currentFilledFormIds) {
        final SplitOptionResponse splitOptionResponse = new SplitOptionResponse();
        for (String prefilledOption : prefilledOptions) {
            if (prefilledOption.isEmpty()) {
                continue;
            }

            String[] splittedPrefilledOption = prefilledOption.split(";");
            String prefilledFilledFormId = splittedPrefilledOption[0];
            String prefilledFilledFormItemId = splittedPrefilledOption[1];

            boolean formChangedOrItemDeleted = true;
            for (String currentFilledFormId : currentFilledFormIds) {
                if (currentFilledFormId.equals(prefilledFilledFormId)) {
                    formChangedOrItemDeleted = false;
                }
            }

            if (formChangedOrItemDeleted) {
                splitOptionResponse.addLinkedFilledFormId(prefilledFilledFormId);
                splitOptionResponse.addLinkedFilledFormItemId(prefilledFilledFormItemId);

                if (ServiceLocator.getPersistance().getFilledFormById(Integer.valueOf(prefilledFilledFormId)) == null) {
                    splitOptionResponse.addOption("(deleted record)");
                    continue;
                }

                splitOptionResponse.addOption(ServiceLocator.getPersistance().
                        getFilledFormItemById(Integer.valueOf(prefilledFilledFormItemId)).getValue() + "(record from old form)");
            }
        }

        return splitOptionResponse;
    }

    public static FilledFormItem getLinkedFilledItemByFormItemIdAndFilledFormId(final Integer formItemId,
                                                                                final Integer filledFormId) {
        if (formItemId == null || filledFormId == null) {
            return null;
        }

        final List<FilledFormItem> filledItems = ServiceLocator.getPersistance().getFilledFormItemByFormItemId(formItemId);

        return FilledFormItemsManager.getFirstItemByFilledFormId(filledItems, filledFormId);
    }

    public static class LinkedFormFileData {

        private String fullSizeUrl;

        private String previewSrc;

        private String fileName;

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFullSizeUrl() {
            return fullSizeUrl;
        }

        public void setFullSizeUrl(String fullSizeUrl) {
            this.fullSizeUrl = fullSizeUrl;
        }

        public String getPreviewSrc() {
            return previewSrc;
        }

        public void setPreviewSrc(String previewSrc) {
            this.previewSrc = previewSrc;
        }

    }

    public static LinkedFormFileData createLinkedFormFileData(final int linkedFilledFormItemId) {
        FilledFormItem filledFormItem = ServiceLocator.getPersistance().getFilledFormItemById(linkedFilledFormItemId);
        final FormFile file = ServiceLocator.getPersistance().getFormFileById(FilledFormItemManager.getIntValue(filledFormItem));

        final boolean fileExist = file != null;
        if (fileExist) {
            final String imagePreviewUrl =
                    ServiceLocator.getResourceGetter().get(ResourceGetterType.PREVIEW_IMAGE_FORM_FILE, file.getFormFileId(), 0, 0, 0, false);
            final String imageFullSizeUrl =
                    ServiceLocator.getResourceGetter().get(ResourceGetterType.FORM_FILE, file.getFormFileId(), 0, 0, 0, false);

            final LinkedFormFileData linkedFormFileData = new LinkedFormFileData();
            linkedFormFileData.setFullSizeUrl(imageFullSizeUrl);
            linkedFormFileData.setPreviewSrc(imagePreviewUrl);
            linkedFormFileData.setFileName(file.getName());

            return linkedFormFileData;
        } else {
            return null;
        }
    }

    public static final String DELTED_RECORD_TEXT = "(deleted record)";

}
