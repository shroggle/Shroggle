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

import com.shroggle.entity.DraftAdminLogin;
import com.shroggle.entity.Widget;
import com.shroggle.exception.AdminLoginNameNotUniqueException;
import com.shroggle.exception.AdminLoginNotFoundException;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameterProperty;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Locale;

/**
 * @author Artem Stasuk, dmitry.solomadin
 */
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class SaveAdminLoginService extends AbstractService {

    @SynchronizeByMethodParameterProperty(
            method = SynchronizeMethod.WRITE,
            entityIdPropertyPath = "adminLoginId",
            entityClass = DraftAdminLogin.class)
    @RemoteMethod
    public FunctionalWidgetInfo execute(final SaveAdminLoginRequest request) throws IOException, ServletException {
        final UserManager userManager = new UsersManager().getLogined();

        final Widget widget;
        if (request.getWidgetId() != null) {
            widget = userManager.getRight().getSiteRight().getWidgetForEditInPresentationalService(
                    request.getWidgetId());
        } else {
            widget = null;
        }
        final DraftAdminLogin adminLogin = persistance.getDraftItem(request.getAdminLoginId());
        if (adminLogin == null || adminLogin.getSiteId() <= 0) {
            throw new AdminLoginNotFoundException("Cannot find admin login by Id=" + request.getAdminLoginId());
        }

        final DraftAdminLogin duplicateAdminLogin = persistance.getAdminLoginByNameAndSiteId(request.getName(), adminLogin.getSiteId());
        if (duplicateAdminLogin != null && duplicateAdminLogin.getId() != adminLogin.getId()) {
            throw new AdminLoginNameNotUniqueException(ServiceLocator.getInternationStorage().get("configureAdminLogin", Locale.US).get("pleaseEnterUniqueName"));
        }

        persistanceTransaction.execute(new Runnable() {

            @Override
            public void run() {
                adminLogin.setDescription(StringUtil.getEmptyOrString(request.getDescription()));
                adminLogin.setShowDescription(request.isShowDescription());
                adminLogin.setText(StringUtil.cutIfNeed(request.getText(), 250));
                adminLogin.setName(request.getName());
            }

        });

        return new GetFunctionalWidgetsService().createFunctionalWidgetInfo(widget, "widget", true);
    }

    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final Persistance persistance = ServiceLocator.getPersistance();

}