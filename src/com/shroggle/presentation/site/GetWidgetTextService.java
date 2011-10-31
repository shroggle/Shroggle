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

package com.shroggle.presentation.site;

import com.shroggle.entity.*;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class, params = {})
public class GetWidgetTextService extends AbstractService {

    @SynchronizeByMethodParameter(
            entityClass = Widget.class)
    @RemoteMethod
    public String execute(final int widgetId) {
        final Widget widget = persistance.getWidget(widgetId);
        if (widget == null || widget.getItemType() != ItemType.TEXT) {
            return null;
        }
        final WidgetItem widgetItem = (WidgetItem) widget;
        final DraftText draftText = (DraftText)widgetItem.getDraftItem();//persistance.getDraftItem(widgetItem.getDraftItem());
        return draftText.getText() == null ? "" : draftText.getText();
    }

    private Persistance persistance = ServiceLocator.getPersistance();

}