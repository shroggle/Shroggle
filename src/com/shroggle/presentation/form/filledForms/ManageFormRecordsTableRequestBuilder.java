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

import com.shroggle.entity.*;
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.logic.form.FilledFormsManager;
import com.shroggle.logic.form.FormManager;
import com.shroggle.logic.form.customization.CustomizeManageRecordsFieldManager;
import com.shroggle.logic.form.customization.CustomizeManageRecordsManager;
import com.shroggle.logic.form.filter.FormFilterManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.presentation.site.FilledFormInfo;
import com.shroggle.presentation.site.ManageFormRecordSortType;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.persistance.Persistance;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
public class ManageFormRecordsTableRequestBuilder extends ServiceWithExecutePage {  // todo. Add tests. Tolik

    public ManageFormRecordsTableRequestBuilder(final DraftForm form, final Integer formFilterId, final String searchKey, final SortProperties sortProperties) {
        this.form = form;
        this.searchKey = StringUtil.getEmptyOrString(searchKey);
        this.formFilterId = formFilterId;
        manageRecordsManager = CustomizeManageRecordsManager.getExistingOrConstructNew(this.form, new UsersManager().getLogined().getUserId());
        this.sortProperties = sortProperties != null ? sortProperties :
                new SortProperties(manageRecordsManager.getFirstIncludedFieldItemName(), false, ManageFormRecordSortType.CUSTOM_FIELD);
        ServiceLocator.getPersistanceTransaction().execute(new Runnable() {
            public void run() {
                for (final FilledForm filledForm : persistance.getFilledFormsByFormId(form.getFormId())) {
                    FilledFormManager.updateAndAddFilledFormItemNames(filledForm);
                }
            }
        });
    }

    public ManageFormRecordsTableRequestBuilder(DraftForm form) {
        this(form, null, "", null);
    }

    public ManageFormRecordsTableRequest build() {
        Integer imageFormItemId = null;
        boolean showBulkUpload = false;
        final List<FilledFormInfo> filledFormInfos = new ArrayList<FilledFormInfo>();
        final List<String> customCellsItemNames = new ArrayList<String>();

        if (form != null) {
            final FormItem imageFormItem = FormManager.getFormItemByFormItemName(FormItemName.IMAGE_FILE_UPLOAD, form);
            if (imageFormItem != null) {
                imageFormItemId = imageFormItem.getFormItemId();
                showBulkUpload = true;
            }

            final DraftFormFilter filter = persistance.getFormFilterById(formFilterId);
            final List<FilledForm> filledForms = filter != null ? new FormFilterManager(filter).getFilledForms() : persistance.getFilledFormsByFormId(form.getFormId());

            final FilledFormsManager filledFormsManager = new FilledFormsManager(filledForms);
            filledFormsManager.retainBySearchKey(searchKey);
            filledFormsManager.setDescendingSort(sortProperties.isDescending());
            filledFormsManager.sort(sortProperties.getSortFieldType(), sortProperties.getItemName());

            for (CustomizeManageRecordsFieldManager fieldManager : manageRecordsManager.getIncludedFields()) {
                customCellsItemNames.add(fieldManager.getItemName());
            }
            for (final FilledForm filledForm : filledFormsManager.getFilledForms()) {
                filledFormInfos.add(FilledFormManager.getFilledFormInfo(filledForm, customCellsItemNames));
            }
        }
        final boolean showForChildSiteRegistration = form instanceof ChildSiteRegistration;
        return new ManageFormRecordsTableRequest(showForChildSiteRegistration, showBulkUpload, customCellsItemNames,
                filledFormInfos, imageFormItemId, sortProperties);
    }

    public DraftForm getForm() {
        return form;
    }


    private final DraftForm form;
    private final Integer formFilterId;
    private final String searchKey;
    private final SortProperties sortProperties;
    private final CustomizeManageRecordsManager manageRecordsManager;
    private final Persistance persistance = ServiceLocator.getPersistance();

}
