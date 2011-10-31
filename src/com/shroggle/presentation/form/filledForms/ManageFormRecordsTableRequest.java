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
package com.shroggle.presentation.form.filledForms;

import com.shroggle.presentation.site.FilledFormInfo;

import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
public class ManageFormRecordsTableRequest {

    public ManageFormRecordsTableRequest(boolean showForChildSiteRegistration, boolean showBulkUpload, List<String> customCellsItemNames,
                                         List<FilledFormInfo> filledFormInfos, final Integer imageFormItemId, final SortProperties sortProperties) {
        this.showForChildSiteRegistration = showForChildSiteRegistration;
        this.showBulkUpload = showBulkUpload;
        this.filledFormInfos = filledFormInfos;
        this.imageFormItemId = imageFormItemId;
        this.sortProperties = sortProperties;
        this.customCellsItemNames = customCellsItemNames;
    }

    private final boolean showForChildSiteRegistration;

    private final boolean showBulkUpload;

    private final List<String> customCellsItemNames;

    private final List<FilledFormInfo> filledFormInfos;

    private final Integer imageFormItemId;

    private final SortProperties sortProperties;

    public SortProperties getSortProperties() {
        return sortProperties;
    }

    public Integer getImageFormItemId() {
        return imageFormItemId;
    }

    public boolean isShowForChildSiteRegistration() {
        return showForChildSiteRegistration;
    }

    public boolean isShowBulkUpload() {
        return showBulkUpload;
    }

    public List<String> getCustomCellsItemNames() {
        return customCellsItemNames;
    }

    public List<FilledFormInfo> getFilledFormInfos() {
        return filledFormInfos;
    }
}
