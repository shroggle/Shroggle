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

import com.shroggle.util.cache.CachePolicy;
import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.util.*;

/**
 * @author dmitry.solomadin
 */
@CachePolicy(maxElementsInMemory = 50000)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DataTransferObject
@Entity(name = "filledFormItems")
public class FilledFormItem {

    @Id
    private int itemId;

    //Used if user edited formItem.itemName and we need to change this entity.
    private int formItemId;

    @Column(length = 250)
    private String itemName;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private FormItemName formItemName;

    @ManyToOne
    @JoinColumn(name = "filledFormId", nullable = false)
    @ForeignKey(name = "filledFormItemsFilledFormId")
    private FilledForm filledForm;

    // VALUE STRUCTURE (<del> - means delimeter):
    // LINKED - linkedFilledFormId;linkedFilledFormItemId
    // DATE_MM_DD_YYYY - mm<del>dd<del>yyyy
    // DATE_MM_DD_YYYY_HH_mm - mm<del>dd<del>yyyy<del>hh<del>mm
    // DATE_HH_mm - hh<del>mm
    // GROUPS - 
    @Lob
    private String value = "";

    private int position;

    @Transient
    public static final String VALUE_DELIMETER = "\u0007";

    public String getValue() {
        return value;
    }

    public Integer getIntValue() {
        if (getValues().isEmpty()){
            return null;
        }

        try {
            return Integer.valueOf(getValues().get(0));
        } catch (Exception e) {
            return null;
        }
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setValue(int value) {
        this.value = String.valueOf(value);
    }

    public void setValues(List<String> values) {
        if (values == null) {
            return;
        }

        String resultValue = "";
        for (int i = 0; i < values.size(); i++) {
            String valueFromList = values.get(i);
            resultValue += valueFromList + (i + 1 == values.size() ? "" : VALUE_DELIMETER);
        }
        value = resultValue;
    }

    public List<String> getValues() {
        if (value == null) {
            return Collections.emptyList();
        }
        return new ArrayList<String>(Arrays.asList(value.split(VALUE_DELIMETER)));
    }

    public int getFormItemId() {
        return formItemId;
    }

    public void setFormItemId(int formItemId) {
        this.formItemId = formItemId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public FormItemName getFormItemName() {
        return formItemName;
    }

    public void setFormItemName(FormItemName formItemName) {
        this.formItemName = formItemName;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public FilledForm getFilledForm() {
        return filledForm;
    }

    public void setFilledForm(FilledForm filledForm) {
        this.filledForm = filledForm;
    }
}
