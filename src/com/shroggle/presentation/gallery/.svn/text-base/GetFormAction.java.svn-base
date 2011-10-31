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
package com.shroggle.presentation.gallery;

import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.logic.form.FormLogic;
import com.shroggle.util.ServiceLocator;

/**
 * @author Artem Stasuk
 */
@UrlBinding("/gallery/getForm.action")
public class GetFormAction extends Action {

    @DefaultHandler
    public Resolution execute() {
        form = new FormLogic(formId);
        return resolutionCreator.forwardToUrl("/gallery/getForm.jsp");
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public FormLogic getForm() {
        return form;
    }

    private int formId;
    private FormLogic form;
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();

}
