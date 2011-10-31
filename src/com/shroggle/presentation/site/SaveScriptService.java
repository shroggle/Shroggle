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

import com.shroggle.entity.DraftScript;
import com.shroggle.entity.WidgetItem;
import com.shroggle.exception.ScriptNotFoundException;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * @author Artem Stasuk, dmitry.solomadin
 */
@RemoteProxy
public class SaveScriptService extends AbstractService {

    @RemoteMethod
    public FunctionalWidgetInfo execute(final SaveScriptRequest request) throws Exception {
        final UserManager userManager = new UsersManager().getLogined();

        final WidgetItem widget;
        if (request.getWidgetId() != null) {
            widget = (WidgetItem) userManager.getRight().getSiteRight().getWidgetForEditInPresentationalService(
                    request.getWidgetId());
        } else {
            widget = null;
        }

        persistanceTransaction.execute(new Runnable() {

            @Override
            public void run() {
                final DraftScript draftScript = persistance.getDraftItem(request.getScriptItemId());
                if (draftScript == null || draftScript.getSiteId() <= 0) {
                    throw new ScriptNotFoundException("Cannot find script item by Id=" + request.getScriptItemId());
                }

                draftScript.setText(request.getText());
                draftScript.setName(request.getName());
                draftScript.setDescription(request.getDescription());
                draftScript.setShowDescription(request.isShowDescription());
            }

        });

        return new GetFunctionalWidgetsService().createFunctionalWidgetInfo(widget, "widget", true);
    }

    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private Persistance persistance = ServiceLocator.getPersistance();

}