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
 * @author Artem Stasuk
 */
@DataTransferObject
@MappedSuperclass
public abstract class FormItem {

    @Id
    private int formItemId;

    private boolean required;

    private int position;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 250)
    private FormItemName formItemName;

    @Column(length = 250)
    private String itemName;

    @Lob
    private String instruction;

    private Integer linkedFormItemId;

    private Integer linkedFormId;

    // If this item is a link on another item then it will have this display type
    // and will look on preview accordingly to this type(dropdown box, checkboxes, radiobuttons)
    @Enumerated(value = EnumType.STRING)
    private FormItemType formItemDisplayType;

    public Integer getLinkedFormId() {
        return linkedFormId;
    }

    public void setLinkedFormId(Integer linkedFormId) {
        this.linkedFormId = linkedFormId;
    }

    public FormItemType getFormItemDisplayType() {
        return formItemDisplayType;
    }

    public void setFormItemDisplayType(FormItemType formItemDisplayType) {
        this.formItemDisplayType = formItemDisplayType;
    }

    public Integer getLinkedFormItemId() {
        return linkedFormItemId;
    }

    public void setLinkedFormItemId(Integer linkedFormItemId) {
        this.linkedFormItemId = linkedFormItemId;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getFormItemId() {
        return formItemId;
    }

    public void setFormItemId(int formItemId) {
        this.formItemId = formItemId;
    }

    public FormItemName getFormItemName() {
        return formItemName;
    }

    public void setFormItemName(FormItemName formItemName) {
        this.formItemName = formItemName;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[id: " + formItemId + ", type: " + formItemName + "]";
    }

    public abstract Form getForm();

}