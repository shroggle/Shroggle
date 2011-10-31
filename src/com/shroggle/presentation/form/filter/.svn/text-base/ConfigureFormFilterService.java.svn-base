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
package com.shroggle.presentation.form.filter;

import com.shroggle.entity.*;
import com.shroggle.exception.FormFilterNotFoundException;
import com.shroggle.exception.FormNotFoundException;
import com.shroggle.logic.form.filter.*;
import com.shroggle.logic.form.FormItemManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class ConfigureFormFilterService extends ServiceWithExecutePage {

    @RemoteMethod
    public ConfigureFormFilterResponse execute(
            final Integer filterId, final Integer formId) throws IOException, ServletException {
        loginedUserId = new UsersManager().getLogined().getUserId();

        if (filterId != null) {
            final DraftFormFilter formFilter = persistance.getFormFilterById(filterId);
            if (formFilter == null) {
                throw new FormFilterNotFoundException("Cannot find form filter with Id=" + filterId);
            }
            formFilterManager = new FormFilterManager(formFilter);
        } else if (formId != null) {
            defaultFilterName = new FormFiltersManager(new UsersManager().getLogined()).getDefaultName(formId);
        }

        //Prefills form that will be selected in form pick list.
        if (filterId == null && formId != null) {
            selectedForm = persistance.getFormById(formId);
            if (selectedForm == null) {
                throw new FormNotFoundException("Cannot find form with Id=" + filterId);
            }
        }

        //Prefills form from filter.
        if (filterId != null && formId == null) {
            selectedForm = formFilterManager.getForm();
            if (selectedForm == null) {
                throw new FormNotFoundException("Cannot find form with Id=" + filterId);
            }
        }


        forms = persistance.getDraftItemsByUserId(loginedUserId, ItemType.ALL_FORMS);

        final ConfigureFormFilterResponse response = new ConfigureFormFilterResponse();

        if (selectedForm != null) {
            final List<FilterFormItemInfo> itemInfos = new ArrayList<FilterFormItemInfo>();
            for (DraftFormItem formItem : selectedForm.getDraftFormItems()) {
                if (!FormItemManager.isCorrectFormItemForFilter(formItem)) {
                    continue;
                }
                formItems.add(formItem);
                itemInfos.add(new FormItemManager().getItemInfo(formItem.getFormItemId()));
            }
            response.setItemInfos(itemInfos);
        }

        if (formFilterManager != null) {
            response.setRules(new ArrayList<FormFilterRuleEdit>());
            for (final FormFilterRuleLogic ruleLogic : formFilterManager.getRules()) {
                final FormFilterRuleEdit ruleEdit = new FormFilterRuleEdit();
                ruleEdit.setCriteria(ruleLogic.getFormFilterRule().getCriteria());
                ruleEdit.setFormItemId(ruleLogic.getFormFilterRule().getFormItemId());
                ruleEdit.setInclude(ruleLogic.getFormFilterRule().isInclude());
                response.getRules().add(ruleEdit);
            }
        }

        response.setHtml(executePage("/site/configureFormFilter.jsp"));
        return response;
    }

    public FormFilterManager getFormFilterLogic() {
        return formFilterManager;
    }

    public List<DraftItem> getForms() {
        return forms;
    }

    public DraftForm getSelectedForm() {
        return selectedForm;
    }

    public Integer getLoginedUserId() {
        return loginedUserId;
    }

    public List<DraftFormItem> getFormItems() {
        return formItems;
    }

    public String getDefaultFilterName() {
        return defaultFilterName;
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private Integer loginedUserId;
    private DraftForm selectedForm;
    private List<DraftItem> forms;
    private List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
    private FormFilterManager formFilterManager;
    private String defaultFilterName;

}
