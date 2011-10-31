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
package com.shroggle.presentation.form;

import com.shroggle.entity.FormItemName;
import com.shroggle.entity.DraftForm;
import com.shroggle.entity.FormItem;
import com.shroggle.logic.form.FormManager;

import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
public interface ShowForm {

    public DraftForm getForm();

    public FormManager getFormManager();

    public List<FormItem> getExistingFormItems();

    public List<FormItemName> getInitFormItems();
}
