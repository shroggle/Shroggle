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

package com.shroggle.presentation.customForm;

import com.shroggle.entity.DraftCustomForm;
import com.shroggle.entity.Site;
import com.shroggle.entity.Widget;
import com.shroggle.entity.WidgetItem;
import com.shroggle.exception.CustomFormNameNotUniqueException;
import com.shroggle.exception.CustomFormNotFoundException;
import com.shroggle.exception.FormWithoutFormItemsException;
import com.shroggle.exception.PageBreakBeforeRequiredFieldsException;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.form.FormItemsManager;
import com.shroggle.logic.form.FormManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.FunctionalWidgetInfo;
import com.shroggle.presentation.site.GetFunctionalWidgetsService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameterProperty;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Locale;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class CreateCustomFormService extends AbstractService {

    @SynchronizeByMethodParameterProperty(
            entityClass = Widget.class,
            method = SynchronizeMethod.WRITE,
            entityIdPropertyPath = "widgetId")
    @RemoteMethod
    public FunctionalWidgetInfo execute(final CreateCustomFormRequest request) throws ServletException, IOException {
        final UserManager userManager = new UsersManager().getLogined();
        final UserRightManager userRightManager = userManager.getRight();

        final WidgetItem widget;
        if (request.getWidgetId() != null) {
            widget = userRightManager.getSiteRight().getWidgetItemForEditInPresentationalService(
                    request.getWidgetId());
        } else {
            widget = null;
        }

        if (request.getFormItems() == null || request.getFormItems().size() == 0) {
            throw new FormWithoutFormItemsException(international.get("FormWithoutFormItemsException"));
        }

        if (new FormItemsManager().isPageBreakBeforeRequiredFields(request.getFormItems())) {
            throw new PageBreakBeforeRequiredFieldsException(international.get("PageBreakBeforeRequiredFieldsException"));
        }

        persistanceTransaction.execute(new Runnable() {
            public void run() {
                final DraftCustomForm customForm = persistance.getDraftItem(request.getFormId());
                if (customForm == null || customForm.getSiteId() <= 0) {
                    throw new CustomFormNotFoundException("Cannot find custom form by Id=" + request.getFormId());
                }

                final Site site;
                if (widget != null) {
                    site = widget.getSite();
                } else {
                    site = persistance.getSite(customForm.getSiteId());
                }

                final DraftCustomForm duplicateForm =
                        persistance.getCustomFormByNameAndSiteId(request.getFormName(), site.getSiteId());
                if (duplicateForm != null && duplicateForm != customForm) {
                    throw new CustomFormNameNotUniqueException(international.get("CustomFormNameNotUniqueException"));
                }

                customForm.setName(request.getFormName());
                customForm.setDescription(request.getFormDescription());
                customForm.setShowDescription(request.isShowHeader());
                customForm.setFormItems(new FormManager().createOrSetFormItems(customForm, request.getFormItems()));
            }
        });

        return new GetFunctionalWidgetsService().createFunctionalWidgetInfo(widget, "widget", true);
    }

    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final International international = ServiceLocator.getInternationStorage().get("configureCustomForm", Locale.US);

}
