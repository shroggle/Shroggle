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
import org.directwebremoting.convert.EnumConverter;

/**
 * @author Balakirev Anatoliy, dmitry.solomadin
 */
@DataTransferObject(converter = EnumConverter.class)
public enum FormItemType {

    TEXT_INPUT_FIELD(1),
    RTF(1),
    SELECT(1, 1),
    FILE_UPLOAD(1),
    CHECKBOX(1),
    RADIOBUTTON(1, 1),
    TWO_TEXT_FIELDS(2),
    TWO_PICK_LISTS(2, 2),
    THREE_PICK_LISTS(3, 3),
    FIVE_PICK_LISTS(5, 5),
    TEXT_AREA(1),
    TEXT_AREA_DOUBLE_SIZE(1),
    PICK_LIST_AND_TEXT_FIELD(2),
    MULITSELECT(1, 1),
    SINGLE_CHOICE_OPTION_LIST(1, 1),
    SPECIAL(0), // Special items are not displayed on 'manage form records' page.
    ACCESS_GROUPS(0),
    SELECTION_LIST(0),
    RADIO_LIST(0),
    LINKED(0);

    FormItemType(int fieldsCount) {
        this.pickListCount = 0;
        this.fieldsCount = fieldsCount;
    }

    FormItemType(int pickListCount, int fieldsCount) {
        this.pickListCount = pickListCount;
        this.fieldsCount = fieldsCount;
    }

    public int getPickListCount() {
        return pickListCount;
    }

    public int getFieldsCount() {
        return fieldsCount;
    }

    private final int pickListCount;
    private final int fieldsCount;

}