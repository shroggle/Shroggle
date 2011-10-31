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
package com.shroggle.presentation.site.render.customForm;

import com.shroggle.entity.*;
import com.shroggle.logic.accessibility.Right;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByClassProperty;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: dmitry.solomadin
 * Date: 16.02.2009
 */
@UrlBinding("/site/showCustomForm.action")
public class ShowCustomFormAction extends Action {

    @SynchronizeByClassProperty(
            entityClass = DraftForm.class,
            entityIdFieldPath = "formId")
    @DefaultHandler
    public Resolution execute() throws IOException, ServletException {
        final DraftCustomForm customForm = persistance.getCustomFormById(formId);
        final User user = new UsersManager().getLoginedUser();

        Map<ItemType, String> parameterMap = new HashMap<ItemType, String>();
        if (showFromAddRecord) {
            String customFormParameters = "&showFromAddRecord=" + true;
            parameterMap.put(ItemType.CUSTOM_FORM, customFormParameters);
        }

        final WidgetItem widgetItem = new WidgetItem();
        widgetItem.setDraftItem(customForm);

        return resolutionCreator.showWidgetPreview(widgetItem, getContext().getServletContext(), (user != null ? user.getUserId() : null), parameterMap);
    }

    public void setFormId(final int formId) {
        this.formId = formId;
    }

    public void setShowFromAddRecord(boolean showFromAddRecord) {
        this.showFromAddRecord = showFromAddRecord;
    }

    private int formId;
    private boolean showFromAddRecord;
    private ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
}
