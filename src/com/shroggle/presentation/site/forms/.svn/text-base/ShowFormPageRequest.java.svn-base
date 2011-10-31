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

package com.shroggle.presentation.site.forms;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;
import com.shroggle.entity.ItemType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@DataTransferObject
public class ShowFormPageRequest {

    @RemoteProperty
    private int widgetId;

    @RemoteProperty
    private int pageBreaksToPass;

    @RemoteProperty
    private int formId;

    @RemoteProperty
    private int filledFormToUpdateId;

    @RemoteProperty
    private ItemType widgetType;
    
    @RemoteProperty
    private List<FormPageAdditionalParameters> additionalParameters = new ArrayList<FormPageAdditionalParameters>();

    public List<FormPageAdditionalParameters> getAdditionalParameters() {
        return additionalParameters;
    }

    public void setAdditionalParameters(List<FormPageAdditionalParameters> additionalParameters) {
        this.additionalParameters = additionalParameters;
    }

    public ItemType getItemType() {
        return widgetType;
    }

    public void setItemType(ItemType widgetType) {
        this.widgetType = widgetType;
    }

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public int getFilledFormToUpdateId() {
        return filledFormToUpdateId;
    }

    public void setFilledFormToUpdateId(int filledFormToUpdateId) {
        this.filledFormToUpdateId = filledFormToUpdateId;
    }

    public int getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(int widgetId) {
        this.widgetId = widgetId;
    }

    public int getPageBreaksToPass() {
        return pageBreaksToPass;
    }

    public void setPageBreaksToPass(int pageBreaksToPass) {
        this.pageBreaksToPass = pageBreaksToPass;
    }
}
