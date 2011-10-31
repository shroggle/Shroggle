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
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.logic.form.FormManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.visitor.VisitorManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.forms.FormPageAdditionalParameters;
import com.shroggle.presentation.site.render.RenderContext;
import com.shroggle.presentation.site.render.RenderFormPageBreak;
import com.shroggle.presentation.site.render.RenderWidgets;
import com.shroggle.util.MD5;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Balakirev Anatoliy
 */
@RemoteProxy
public class CreateChildSiteService extends AbstractService {

    @RemoteMethod
    public CreateChildSiteResponse execute(final CreateChildSiteRequest request) throws Exception {
        if (!request.isEditDetails()) {
            if ((request.getPageBreaksToPass() <= 1)) {
                final String noBotCode = sessionStorage.getNoBotCode(this, "childSiteRegistration" + request.getWidgetId());
                CreateChildSiteInputParametersChecker.execute(request, request.getWidgetId(), noBotCode);
            } else {
                CreateChildSiteInputParametersChecker.executeForNotFirstPage(request);
            }
        } else {
            CreateChildSiteInputParametersChecker.executeForEdit(request);
        }

        final DraftChildSiteRegistration childSiteRegistration = persistance.getChildSiteRegistrationById(request.getFormId());

        if ((request.getPageBreaksToPass() <= 1) && !request.isEditDetails()) {
            final Site networkSite = getSiteByRequest(request);
            final DraftForm form = persistance.getFormById(request.getFormId());

            persistanceTransaction.execute(new Runnable() {

                public void run() {
                    user = createChildSiteUser(request.getEmail(), request.getPassword(), networkSite);
                    filledChildSiteRegistrationForm = new VisitorManager(user).addFilledFormToVisitor(request.getFilledFormItems(), form);
                    final UserManager userManager = new UserManager(user);
                    userManager.updateUserInfoByFilledForm(filledChildSiteRegistrationForm);
                    childSiteSettings = createChildSiteSettings(
                            childSiteRegistration, user, networkSite, filledChildSiteRegistrationForm);
                    ChildSiteEmailSender.execute(childSiteSettings, networkSite, user);
                }

            });
        } else {
            persistanceTransaction.execute(new Runnable() {

                public void run() {
                    filledChildSiteRegistrationForm = persistance.getFilledFormById(request.getFilledFormId());

                    FilledFormManager.updateFilledFormItems(request.getFilledFormItems(), filledChildSiteRegistrationForm);
                    new UserManager(persistance.getUserById(request.getUserId())).updateUserInfoByFilledForm(filledChildSiteRegistrationForm);
                }

            });
        }

        return createResponse(request, childSiteRegistration);
    }

    protected User createChildSiteUser(final String email, final String password, final Site networkSite) {
        User user = persistance.getUserByEmail(email.toLowerCase());
        if (user == null) {
            user = new User();
            user.setEmail(email.toLowerCase());
            user.setPassword(password);
            user.setRegistrationDate(new Date());
            persistance.putUser(user);
        }

        final UserManager userManager = new UserManager(user);
        final UserRightManager userRightManager = userManager.getRight();
        if (userRightManager.getValidUserOnSiteRight(networkSite.getSiteId()) == null) {
            userRightManager.createUserOnSiteRight(networkSite, SiteAccessLevel.VISITOR, false);
        }
        return user;
    }

    private CreateChildSiteResponse createResponse(final CreateChildSiteRequest request,
                                                   final DraftChildSiteRegistration childSiteRegistration)
            throws IOException, ServletException {
        final CreateChildSiteResponse response = new CreateChildSiteResponse();
        response.setFilledFormId(filledChildSiteRegistrationForm.getFilledFormId());

        final HttpServletRequest httpRequest = getRequest();

        final Integer settingsId = childSiteSettings != null ? childSiteSettings.getChildSiteSettingsId() : request.getSettingsId();
        final Integer userId = user != null ? user.getUserId() : request.getUserId();
        httpRequest.setAttribute("settingsId", settingsId);
        httpRequest.setAttribute("childSiteUserId", userId);
        httpRequest.setAttribute("editDetails", request.isEditDetails());
        httpRequest.setAttribute("filledFormToUpdateId", filledChildSiteRegistrationForm.getFilledFormId());

        if (request.isEditDetails()) {
            httpRequest.setAttribute("prefilledForm", filledChildSiteRegistrationForm);
        }

        final User loginedUser = new UsersManager().getLoginedUser();
        final String registeredUserEmail = StringUtil.isNullOrEmpty(request.getEmail()) ?
                persistance.getUserById(request.getUserId()).getEmail() : request.getEmail();
        if (request.isRequestNextPage() && !(request.getPageBreaksToPass() > FormManager.getTotalPageBreaks(childSiteRegistration))) {
            //Response with next form page
            if (request.getWidgetId() != 0) {
                final RenderContext context = createRenderContext(false);

                response.setNextPageHtml(new RenderFormPageBreak().execute(request.getWidgetId(), request.getFormId(),
                        request.getPageBreaksToPass(), filledChildSiteRegistrationForm.getFilledFormId(),
                        new ArrayList<FormPageAdditionalParameters>(), context));
            } else {
                response.setNextPageHtml(new RenderFormPageBreak().executeForAddRecord(ItemType.CHILD_SITE_REGISTRATION,
                        request.getPageBreaksToPass(), filledChildSiteRegistrationForm.getFilledFormId(),
                        childSiteRegistration.getFormId(), new ArrayList<FormPageAdditionalParameters>()));
            }
        } else if (loginedUser != null && loginedUser.getEmail().equals(registeredUserEmail) && request.getWidgetId() != 0) {
            //Response with child site forms list page
            final Widget widget = persistance.getWidget(request.getWidgetId());

            response.setNextPageHtml(new RenderWidgets(new PageManager(widget.getPage()), SiteShowOption.ON_USER_PAGES)
                .executeWidgetWithoutItsSize(widget, createRenderContext(false), false));
        }

        return response;
    }

    private Site getSiteByRequest(final CreateChildSiteRequest request) {
        final Widget widget = persistance.getWidget(request.getWidgetId());
        if (widget != null) {
            return widget.getSite();
        } else {
            return persistance.getSite(persistance.getFormById(request.getFormId()).getSiteId());
        }
    }

    public static ChildSiteSettings createChildSiteSettings(
            final DraftChildSiteRegistration childSiteRegistration,
            final User user, final Site networkSite, final FilledForm filledForm) {
        final ChildSiteSettings settings = executeInTransaction(childSiteRegistration, user, networkSite);
        filledForm.setChildSiteSettingsId(settings.getChildSiteSettingsId());
        settings.setFilledFormId(filledForm.getFilledFormId());
        return settings;
    }

    public static ChildSiteSettings executeInTransaction(
            final DraftChildSiteRegistration childSiteRegistration, final User user, final Site networkSite) {
        final ChildSiteSettings settings = new ChildSiteSettings();
        settings.setParentSite(networkSite);
        settings.setChildSiteRegistration(childSiteRegistration);
        settings.setWelcomeText(childSiteRegistration.getWelcomeText());
        settings.setFromEmail(childSiteRegistration.getFromEmail());
        settings.setLogoId(childSiteRegistration.getLogoId());
        settings.setAccessLevel(childSiteRegistration.getAccessLevel());
        settings.setEndDate(childSiteRegistration.getEndDate() != null ? childSiteRegistration.getEndDate() : null);
        settings.setStartDate(childSiteRegistration.getStartDate() != null ? childSiteRegistration.getStartDate() : null);
        settings.setCanBePublishedMessageSent(childSiteRegistration.getStartDate() == null || childSiteRegistration.getStartDate().before(new Date()));
        settings.setPrice1gb(childSiteRegistration.getPrice1gb());
        settings.setPrice250mb(childSiteRegistration.getPrice250mb());
        settings.setPrice500mb(childSiteRegistration.getPrice500mb());
        settings.setPrice3gb(childSiteRegistration.getPrice3gb());
        settings.setUserId(user.getUserId());
        settings.setConfirmCode(MD5.crypt(String.valueOf(System.currentTimeMillis())));
        settings.setRequiredToUseSiteBlueprint(childSiteRegistration.isRequiredToUseSiteBlueprint());
        settings.setTermsAndConditions(childSiteRegistration.getTermsAndConditions());
        settings.setCreatedDate(new Date());
        settings.setUseOneTimeFee(childSiteRegistration.isUseOneTimeFee());
        settings.setOneTimeFee(childSiteRegistration.getOneTimeFee());
        settings.getSitePaymentSettings().setUserId(user.getUserId());
        ServiceLocator.getPersistance().putChildSiteSettings(settings);
        if (!user.getChildSiteSettingsId().contains(settings.getChildSiteSettingsId())) {
            user.addChildSiteSettingsId(settings.getChildSiteSettingsId());
        }
        return settings;
    }

    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private User user;
    private FilledForm filledChildSiteRegistrationForm;
    private ChildSiteSettings childSiteSettings;

}



