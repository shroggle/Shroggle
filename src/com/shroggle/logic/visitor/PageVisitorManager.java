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
package com.shroggle.logic.visitor;

import com.shroggle.entity.FilledForm;
import com.shroggle.entity.FilledFormItem;
import com.shroggle.entity.Form;
import com.shroggle.entity.PageVisitor;
import com.shroggle.exception.PageVisitorManagerInitException;
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.persistance.Persistance;

import java.util.List;

/**
 * Author: dmitry.solomadin
 */
public class PageVisitorManager {

    public PageVisitorManager(final PageVisitor pageVisitor) {
        if (pageVisitor == null) {
            throw new PageVisitorManagerInitException("Cannot initialize PageVisitorManager with null page visitor.");
        }

        this.pageVisitor = pageVisitor;
    }

    public FilledForm addFilledFormToPageVisitor(final List<FilledFormItem> filledFormItems, final Form form) {
        final FilledForm filledForm = new FilledForm();

        filledForm.setFormId(form.getId());
        filledForm.setType(form.getType());
        filledForm.setFormDescription(StringUtil.isNullOrEmpty(form.getDescription()) ? "" : form.getDescription());
        persistance.putFilledForm(filledForm);

        FilledFormManager.updateFilledFormItems(filledFormItems, filledForm);

        filledForm.setPageVisitor(pageVisitor);
        pageVisitor.addFilledForm(filledForm);

        return filledForm;
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PageVisitor pageVisitor;
}
