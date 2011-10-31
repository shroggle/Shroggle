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

import java.util.List;

/**
 * @author dmitry.solomadin
 */
public interface FormFilterRuleIface {

    public List<String> getCriteria();

    public int getFormItemId();

    public boolean isInclude();

    //Return list contains form items to which this rule should be also applied.
    List<Integer> getAlsoSearchByFields();

    OptionDisplayType getDisplayType();

}
