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

package com.shroggle.entity;

import org.directwebremoting.annotations.DataTransferObject;

import javax.persistence.*;

/**
 * @author dmitry.solomadin
 */
@DataTransferObject
@Entity(name = "customForms")
public class DraftCustomForm extends DraftForm implements CustomForm {

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 250)
    public FormType formType = FormType.CUSTOM_FORM;

    public ItemType getItemType() {
        return ItemType.CUSTOM_FORM;
    }

    public FormType getType() {
        return formType;
    }

    public void setType(final FormType formType) {
        this.formType = formType;
    }

}
