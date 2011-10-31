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

package com.shroggle.presentation.form.filter;

import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.annotations.RemoteMethod;

import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.form.filter.FormFiltersManager;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class FilterDefaultNameGetterService {

    @RemoteMethod
    public String get(final int formId){
        return new FormFiltersManager(new UsersManager().getLogined()).getDefaultName(formId);
    }

}
