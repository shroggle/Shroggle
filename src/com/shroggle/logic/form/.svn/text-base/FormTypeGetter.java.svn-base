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

import com.shroggle.entity.FormItemName;
import com.shroggle.entity.FormItemType;
import com.shroggle.entity.FormItemCheckerType;

import java.util.List;
import java.util.ArrayList;

/**
 * @author dmitry.solomadin
 */
public class FormTypeGetter {

    public static List<FormItemName> getByType(FormItemType type){
        final List<FormItemName> returnList = new ArrayList<FormItemName>();
        for (FormItemName itemName : FormItemName.values()){
            if (itemName.getType() == type){
                returnList.add(itemName);
            }
        }

        return returnList;
    }

    public static List<FormItemName> getMandatory(){
        return getByChecker(FormItemCheckerType.MANDATORY);
    }

    public static List<FormItemName> getAlwaysRequired(){
        return getByChecker(FormItemCheckerType.ALWAYS_REQUIRED);
    }

    public static List<FormItemName> getByChecker(FormItemCheckerType checkerType){
        final List<FormItemName> returnList = new ArrayList<FormItemName>();
        for (FormItemName itemName : FormItemName.values()){
            if (itemName.getCheckers().contains(checkerType)){
                returnList.add(itemName);
            }
        }

        return returnList;
    }
}
