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
import com.shroggle.exception.CustomFormNotFoundException;
import com.shroggle.exception.WrongVerificationCodeException;
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.logic.form.FormManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.visitor.PageVisitorManager;
import com.shroggle.logic.visitor.VisitorManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.ActionUtil;
import com.shroggle.presentation.site.forms.FormPageAdditionalParameters;
import com.shroggle.presentation.site.render.RenderContext;
import com.shroggle.presentation.site.render.RenderFormPageBreak;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Author: dmitry.solomadin
 * Date: 03.02.2009
 */
@RemoteProxy
public class SubmitCustomFormService extends AbstractService {
    private Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
    private final International international = ServiceLocator.getInternationStorage().get("configureCustomForm", Locale.US);
    private FilledForm filledForm;

    @RemoteMethod
    public SubmitCustomFormResponse execute(final SubmitCustomFormRequest request)
            throws ServletException, IOException {
        final User user = new UsersManager().getLoginedUser();
        final Integer loginedVisitorId = user != null ? user.getUserId() : null;

        if (!request.isShowFromAddRecord() && (request.getPageBreaksToPass() == 0 || request.getPageBreaksToPass() == 1)) {
            final String noBotCode = ServiceLocator.getSessionStorage().getNoBotCode(this, (CUSTOM_FORM_NO_BOT_CODE + request.getWidgetId()));
            if (!noBotCode.equals(request.getVerificationCode())) {
                throw new WrongVerificationCodeException(international.get("wrongCode"));
            }
        }

        final DraftCustomForm customForm = persistance.getCustomFormById(request.getFormId());

        if (customForm == null) {
            throw new CustomFormNotFoundException("Cannot find custom form by Id=" + request.getFormId());
        }
        persistanceTransaction.execute(new Runnable() {
            public void run() {
                if (request.getFilledFormId() != null) {
                    //If we already have filledForm (i.e: filledForm created on first form page and updated on second.) then just update it with new items.
                    filledForm = persistance.getFilledFormById(request.getFilledFormId());
                    FilledFormManager.updateFilledFormItems(request.getFilledFormItems(), filledForm);
                } else {
                    if (loginedVisitorId != null) {
                        final User user = persistance.getUserById(loginedVisitorId);
                        filledForm = new VisitorManager(user).addFilledFormToVisitor(request.getFilledFormItems(), customForm);
                    } else {
                        final Integer pageVisitorId = ActionUtil.getPageVisitorId(getContext().getHttpServletRequest().getCookies());
                        if (pageVisitorId != null) {
                            final PageVisitor pageVisitor = persistance.getPageVisitorById(pageVisitorId);
                            if (pageVisitor != null) {
                                filledForm = new PageVisitorManager(pageVisitor).addFilledFormToPageVisitor(request.getFilledFormItems(), customForm);
                            }
                        }
                    }
                }
            }
        });
        final SubmitCustomFormResponse response = new SubmitCustomFormResponse();
        response.setFilledFormId(filledForm.getFilledFormId());

        // If user has filled out last form page then we should show him first page again.
        // In case we are showing form not from admin iface.
        if (!request.isShowFromAddRecord() &&
                request.getPageBreaksToPass() > FormManager.getTotalPageBreaks(customForm)){
            request.setPageBreaksToPass(0);
            response.setShowSuccessfullSubmitMessage(true);
        }

        if (request.isRequestNextPage() && !(request.getPageBreaksToPass() > FormManager.getTotalPageBreaks(customForm))) {
            if (request.getWidgetId() != 0) {
                final RenderContext context = createRenderContext(false);

                response.setNextPageHtml(new RenderFormPageBreak().execute(request.getWidgetId(), request.getFormId(),
                        request.getPageBreaksToPass(), filledForm.getFilledFormId(), new ArrayList<FormPageAdditionalParameters>(), context));
            } else {
                response.setNextPageHtml(new RenderFormPageBreak().executeForAddRecord(ItemType.CUSTOM_FORM,
                        request.getPageBreaksToPass(), filledForm.getFilledFormId(), customForm.getFormId(), new ArrayList<FormPageAdditionalParameters>()));
            }
        }

        return response;
    }

    private final static String CUSTOM_FORM_NO_BOT_CODE = "customForm";
}
