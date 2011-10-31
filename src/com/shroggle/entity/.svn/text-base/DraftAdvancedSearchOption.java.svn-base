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
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@DataTransferObject
@Entity(name = "advancedSearchOptions")
public class DraftAdvancedSearchOption implements FormFilterRuleIface {

    @Id
    private int advancedSearchOptionId;

    private int formItemId;

    @Column(length = 250)
    private String fieldLabel;

    @CollectionOfElements
    private List<String> optionCriteria = new ArrayList<String>();

    private OptionDisplayType displayType;

    private int position;

    @ManyToOne
    @ForeignKey(name = "advancedSearchOptionsAdvancedSearchIdDraft")
    @JoinColumn(name = "advancedSearchIdDraft", nullable = false)
    private DraftAdvancedSearch advancedSearch;

    @CollectionOfElements(fetch = FetchType.EAGER)
    private List<Integer> alsoSearchByFields = new ArrayList<Integer>();

    public List<Integer> getAlsoSearchByFields() {
        return alsoSearchByFields;
    }

    public void setAlsoSearchByFields(List<Integer> alsoSearchByFields) {
        this.alsoSearchByFields = alsoSearchByFields;
    }

    public void addAlsoSearchByFields(int alsoSearchByFieldsFieldId){
        if (!alsoSearchByFields.contains(alsoSearchByFieldsFieldId)){
            alsoSearchByFields.add(alsoSearchByFieldsFieldId);
        }
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getFieldLabel() {
        return fieldLabel;
    }

    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    public OptionDisplayType getDisplayType() {
        return displayType;
    }

    public void setDisplayType(OptionDisplayType displayType) {
        this.displayType = displayType;
    }

    public List<String> getOptionCriteria() {
        return optionCriteria;
    }

    public void setOptionCriteria(List<String> optionCriteria) {
        this.optionCriteria = optionCriteria;
    }

    public DraftAdvancedSearch getAdvancedSearch() {
        return advancedSearch;
    }

    public void setAdvancedSearch(DraftAdvancedSearch advancedSearch) {
        this.advancedSearch = advancedSearch;
    }

    public List<String> getCriteria() {
        return optionCriteria;
    }

    public int getFormItemId() {
        return formItemId;
    }

    public boolean isInclude() {
        return true;
    }

    public void setFormItemId(int formItemId) {
        this.formItemId = formItemId;
    }

    public int getAdvancedSearchOptionId() {
        return advancedSearchOptionId;
    }

    public void setAdvancedSearchOptionId(int advancedSearchOptionId) {
        this.advancedSearchOptionId = advancedSearchOptionId;
    }

}
