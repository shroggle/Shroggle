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
package com.shroggle.logic.form;

import com.shroggle.entity.FormItemCheckerType;
import com.shroggle.entity.FormItemName;
import com.shroggle.entity.FormItemType;

/**
 * @author Balakirev Anatoliy
 */
public class FormItemNameManager {

    public static boolean showFieldOnManageRecords(final FormItemName formItemName) {
        return formItemName.getType() != FormItemType.SPECIAL
                && !formItemName.getCheckers().contains(FormItemCheckerType.NOT_DISPLAY_ON_SHOW_RECORDS);
    }

}
