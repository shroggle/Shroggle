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
package com.shroggle.presentation.site;

import com.shroggle.entity.DraftForm;
import com.shroggle.entity.DraftFormItem;
import com.shroggle.entity.FormItemName;
import com.shroggle.exception.FormNotFoundException;
import com.shroggle.logic.form.FormItemManager;
import com.shroggle.logic.form.filter.FilterFormItemInfo;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: dmitry.solomadin
 */
@RemoteProxy
public class GetFormItemsByFormService extends AbstractService {

    public List<FilterFormItemInfo> execute(final int formId) {
        new UsersManager().getLogined();

        final DraftForm form = ServiceLocator.getPersistance().getFormById(formId);

        if (form == null) {
            throw new FormNotFoundException("Cannot find by Id=" + formId);
        }

        final List<FilterFormItemInfo> itemInfos = new ArrayList<FilterFormItemInfo>();
        for (DraftFormItem formItem : form.getDraftFormItems()) {
            if (formItem.getFormItemName().equals(FormItemName.REGISTRATION_PASSWORD)
                    || formItem.getFormItemName().equals(FormItemName.REGISTRATION_PASSWORD_RETYPE)) {
                continue;
            }
            itemInfos.add(new FormItemManager().getItemInfo(formItem.getFormItemId()));
        }

        return itemInfos;
    }
}
