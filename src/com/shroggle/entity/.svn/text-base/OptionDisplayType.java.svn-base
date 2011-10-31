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

/**
 * @author dmitry.solomadin
 */
public enum OptionDisplayType {
    PICK_LIST_SELECT(PickType.SINGLE_SELECTION), PICK_LIST_MULTISELECT(PickType.MULTI_SELECTION),
    RANGE_AS_RANGE(PickType.MULTI_SELECTION), RANGE_AS_SEP_OPTION(PickType.MULTI_SELECTION), RANGE_AS_RANGE_INPUTS(PickType.NONE),
    TEXT_AS_SEP_OPTION(PickType.SINGLE_SELECTION), TEXT_AS_FREE(PickType.NONE),
    POST_CODE(PickType.SINGLE_SELECTION), SINGLE_CHECKBOX(PickType.SINGLE_SELECTION),
    NONE(PickType.NONE);

    OptionDisplayType(PickType pickType) {
        this.pickType = pickType;
    }

    private enum PickType {
        SINGLE_SELECTION, MULTI_SELECTION, NONE
    }

    public boolean isPickTypeMultiSelect() {
        return this.getPickType() == PickType.MULTI_SELECTION;
    }

    //RANGE_AS_SEP_OPTION should be check only for equality not as range.
    public boolean checkForRange() {
        return this == OptionDisplayType.RANGE_AS_RANGE || this == OptionDisplayType.RANGE_AS_RANGE_INPUTS;
    }

    public boolean isMultiselect(){
        return this == RANGE_AS_SEP_OPTION || this == RANGE_AS_RANGE || this == PICK_LIST_MULTISELECT;
    }

    public PickType getPickType() {
        return pickType;
    }

    private PickType pickType;
}
