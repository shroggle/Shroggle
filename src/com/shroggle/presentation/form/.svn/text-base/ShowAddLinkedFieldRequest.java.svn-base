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
package com.shroggle.presentation.form;

import org.directwebremoting.annotations.DataTransferObject;
import com.shroggle.entity.FormItemType;

/**
 * @author dmitry.solomadin
 */
@DataTransferObject
public class ShowAddLinkedFieldRequest {

    // represents form to which we are adding a new item.
    private Integer targetFormId;

    // represents form item that is linked to item that is currenly edited.
    private Integer linkedFormItemId;

    // represents formItemText of the item that is currenly edited.
    private String formItemText;

    // represents formItemDisplayType of the item that is currenly edited.
    private FormItemType formItemDisplayType;

    public String getFormItemText() {
        return formItemText;
    }

    public void setFormItemText(String formItemText) {
        this.formItemText = formItemText;
    }

    public FormItemType getFormItemDisplayType() {
        return formItemDisplayType;
    }

    public void setFormItemDisplayType(FormItemType formItemDisplayType) {
        this.formItemDisplayType = formItemDisplayType;
    }

    public Integer getTargetFormId() {
        return targetFormId;
    }

    public void setTargetFormId(Integer targetFormId) {
        this.targetFormId = targetFormId;
    }

    public Integer getLinkedFormItemId() {
        return linkedFormItemId;
    }

    public void setLinkedFormItemId(Integer linkedFormItemId) {
        this.linkedFormItemId = linkedFormItemId;
    }
}
