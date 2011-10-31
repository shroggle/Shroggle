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
import com.shroggle.exception.*;
import com.shroggle.logic.form.FormItemsManager;
import com.shroggle.logic.form.FormManager;
import com.shroggle.logic.user.EmailChecker;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByAllEntity;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Locale;


/**
 * @author Balakirev Anatoliy, dmitry.solomadin
 */

@RemoteProxy
public class CreateContactUsService extends AbstractService {

    @RemoteMethod
    @SynchronizeByAllEntity(
            entityClass = WidgetItem.class)
    public FunctionalWidgetInfo execute(final CreateContactUsWidgetRequest request) throws ServletException, IOException {
        final UserManager userManager = new UsersManager().getLogined();

        final WidgetItem widget;
        if (request.getWidgetId() != null) {
            widget = (WidgetItem) userManager.getRight().getSiteRight().getWidgetForEditInPresentationalService(
                    request.getWidgetId());
        } else {
            widget = null;
        }

        try {
            new EmailChecker().execute(request.getEmail());
        } catch (IncorrectEmailException e) {
            throw new InvalidContactUsEmailException(contactUsBunlde.get("InvalidContactUsEmail"));
        } catch (NullOrEmptyEmailException e) {
            throw new InvalidContactUsEmailException(contactUsBunlde.get("InvalidContactUsEmail"));
        }

        if (new FormItemsManager().isPageBreakBeforeRequiredFields(request.getFormItems())) {
            throw new PageBreakBeforeRequiredFieldsException(contactUsBunlde.get("PageBreakBeforeRequiredFieldsException"));
        }

        persistanceTransaction.execute(new Runnable() {

            public void run() {
                final DraftContactUs contactUs = persistance.getDraftItem(request.getContactUsId());
                if (contactUs == null || contactUs.getSiteId() <= 0) {
                    throw new ContactUsNotFoundException("Cannot find contact us by Id=" + request.getContactUsId());
                }

                final Site site;
                if (widget != null) {
                    site = widget.getSite();
                } else {
                    site = persistance.getSite(contactUs.getSiteId());
                }

                final DraftContactUs duplicateContactUs = persistance.getContactUsByNameAndSiteId(request.getContactUsName(), site.getSiteId());
                if (duplicateContactUs != null && (duplicateContactUs.getId() != contactUs.getId())) {
                    throw new ContactUsNameNotUniqueException(contactUsBunlde.get("ContactUsNotUniqueNameException"));
                }

                contactUs.setShowDescription(request.getDisplayHeader());
                contactUs.setEmail(request.getEmail());
                contactUs.setDescription(request.getHeader());
                contactUs.setName(request.getContactUsName());
                contactUs.setFormItems(new FormManager().createOrSetFormItems(contactUs, request.getFormItems()));
            }
        });

        return new GetFunctionalWidgetsService().createFunctionalWidgetInfo(widget, "widget", true);
    }

    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final International contactUsBunlde = ServiceLocator.getInternationStorage().get("configureContactUs", Locale.US);

}
