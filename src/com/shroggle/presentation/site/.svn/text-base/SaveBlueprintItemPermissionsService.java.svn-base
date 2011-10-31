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

import com.shroggle.entity.SiteType;
import com.shroggle.entity.Widget;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class SaveBlueprintItemPermissionsService {

    @SynchronizeByMethodParameter(
            method = SynchronizeMethod.WRITE,
            entityClass = Widget.class)
    @RemoteMethod
    public void execute(
            final int widgetId, final boolean editable,
            final boolean required, final boolean editRuche, final boolean shareble) {
        final UserManager userManager = new UsersManager().getLogined();
        final Widget widget = userManager.getRight().getSiteRight().getWidgetForEdit(widgetId);

        if (widget.getSite().getType() != SiteType.BLUEPRINT) {
            throw new WidgetNotFoundException(
                    "Can't find widget " + widgetId + " because it isn't blueprint!");
        }

        persistanceTransaction.execute(new Runnable() {

            public void run() {
                widget.setBlueprintEditable(editable);
                widget.setBlueprintRequired(required);
                widget.setBlueprintEditRuche(editRuche);
                widget.setBlueprintShareble(shareble);
                new PageManager(widget.getPage()).setChanged(true);
            }

        });
    }

    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

}