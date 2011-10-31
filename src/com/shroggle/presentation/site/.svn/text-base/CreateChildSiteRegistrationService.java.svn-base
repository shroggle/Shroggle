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
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.DateUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByAllEntity;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * @author Balakirev Anatoliy, dmitry.solomadin
 */

@RemoteProxy
public class CreateChildSiteRegistrationService extends AbstractService {

    @RemoteMethod
    @SynchronizeByAllEntity(
            entityClass = WidgetItem.class)
    public CheckDateResponse saveFormSettingsTab(final CreateChildSiteRegistrationRequest request) throws Exception {
        new UsersManager().getLogined();
        if (new FormItemsManager().isPageBreakBeforeRequiredFields(request.getFormItems())) {
            throw new PageBreakBeforeRequiredFieldsException(international.get("PageBreakBeforeRequiredFieldsException"));
        }

        if (new FormItemsManager().isPaymentBlockOnFirstPage(request.getFormItems())) {
            throw new PaymentBlockOnFirstPageException(international.get("PaymentBlockOnFirstPageException"));
        }

        final Response response = getChildSiteRegistration(request);
        persistanceTransaction.execute(new Runnable() {

            public void run() {
                final DraftChildSiteRegistration childSiteRegistration = response.getDraftChildSiteRegistration();
                childSiteRegistration.setModified(true);
                childSiteRegistration.setName(request.getName());
                childSiteRegistration.setDescription(request.getDescription());
                childSiteRegistration.setShowDescription(request.isDisplayDescription());
                childSiteRegistration.setFormItems(new FormManager().createOrSetFormItems(childSiteRegistration, request.getFormItems()));
            }
        });
        final CheckDateResponse checkDateResponse = new CheckDateResponse();
        checkIncomeSettings(response.getSite(), response.getUser(), checkDateResponse);
        checkDateResponse.setWidgetInfo(new GetFunctionalWidgetsService().createFunctionalWidgetInfo(response.getWidget(), "widget", true));
        return checkDateResponse;
    }

    @RemoteMethod
    @SynchronizeByAllEntity(
            entityClass = WidgetItem.class)
    public CheckDateResponse saveNetworkSettingsTab(final CreateChildSiteRegistrationRequest request) throws Exception {
        new UsersManager().getLogined();
        final CheckDateResponse checkDateResponse = checkRequestDate(request);
        if (checkDateResponse.isEndBeforeCurrent() || checkDateResponse.isEndBeforeStart() ||
                checkDateResponse.isWrongEndDate() || checkDateResponse.isWrongStartDate()) {
            //todo Refactor to throw exception.
            return checkDateResponse;
        }

        final Response response = getChildSiteRegistration(request);
        persistanceTransaction.execute(new Runnable() {

            public void run() {
                final DraftChildSiteRegistration childSiteRegistration = response.getDraftChildSiteRegistration();
                childSiteRegistration.setModified(true);
                childSiteRegistration.setAccessLevel(request.getAccessLevel());

                childSiteRegistration.setBlueprintsId(request.getTemplatesId());
                childSiteRegistration.setRequiredToUseSiteBlueprint(request.isRequiredToUseSiteBlueprint());

                childSiteRegistration.setUseOneTimeFee(request.isUseOneTimeFee());
                childSiteRegistration.setOneTimeFee(request.getOneTimeFee());
                childSiteRegistration.setPrice1gb(request.getPrice1gb());
                childSiteRegistration.setPrice250mb(request.getPrice250mb());
                childSiteRegistration.setPrice3gb(request.getPrice3gb());
                childSiteRegistration.setPrice500mb(request.getPrice500mb());


                childSiteRegistration.setUseOwnAuthorize(request.isUseOwnAuthorize());
                childSiteRegistration.setUseOwnPaypal(request.isUseOwnPaypal());
                childSiteRegistration.setAuthorizeLogin(request.getAuthorizeLogin());
                childSiteRegistration.setAuthorizeTransactionKey(request.getAuthorizeTransactionKey());
                childSiteRegistration.setPaypalApiUserName(request.getPaypalApiUserName());
                childSiteRegistration.setPaypalApiPassword(request.getPaypalApiPassword());
                childSiteRegistration.setPaypalSignature(request.getPaypalSignature());

                if (request.isUseStartDate()) {
                    childSiteRegistration.setStartDate(DateUtil.getDateByString(request.getStartDate()));
                } else {
                    childSiteRegistration.setStartDate(null);
                }

                if (request.isUseEndDate()) {
                    childSiteRegistration.setEndDate(DateUtil.getDateByString(request.getEndDate()));
                } else {
                    childSiteRegistration.setEndDate(null);
                }
            }
        });
        checkIncomeSettings(response.getSite(), response.getUser(), checkDateResponse);
        checkDateResponse.setWidgetInfo(new GetFunctionalWidgetsService().createFunctionalWidgetInfo(response.getWidget(), "widget", true));
        return checkDateResponse;
    }

    @RemoteMethod
    // todo Artem fixed
    @SynchronizeByAllEntity(
            entityClass = WidgetItem.class)
    public CheckDateResponse saveWhiteLabelSettingsTab(final CreateChildSiteRegistrationRequest request) throws Exception {
        new UsersManager().getLogined();
        final CheckDateResponse checkDateResponse = checkRequestDate(request);

        final Response response = getChildSiteRegistration(request);
        persistanceTransaction.execute(new Runnable() {

            @Override
            public void run() {
                final DraftChildSiteRegistration childSiteRegistration = response.getDraftChildSiteRegistration();
                childSiteRegistration.setModified(true);
                childSiteRegistration.setBrandedAllowShroggleDomain(request.isBrandedAllowShroggleDomain());
                childSiteRegistration.setBrandedUrl(StringUtil.getNotEmptyOrNull(request.getBrandedUrl()));
                childSiteRegistration.setFooterUrl(request.getFooterUrl());
                childSiteRegistration.setFooterText(request.getFooterText());
                childSiteRegistration.setFooterImageId(request.getFooterImageId());
                childSiteRegistration.setContactUsPageId(request.getContactUsPageId());
                childSiteRegistration.setLogoId(request.getLogoId());
                childSiteRegistration.setFromEmail(request.getFromEmail());
                childSiteRegistration.setTermsAndConditions(request.getTermsAndConditions());
                childSiteRegistration.setEmailText(request.getEmailText());
                childSiteRegistration.setWelcomeText(request.getWelcomeText());
                childSiteRegistration.setThanksForRegisteringText(request.getThanksForRegisteringText());
            }

        });
        checkIncomeSettings(response.getSite(), response.getUser(), checkDateResponse);
        checkDateResponse.setWidgetInfo(new GetFunctionalWidgetsService().createFunctionalWidgetInfo(response.getWidget(), "widget", true));
        return checkDateResponse;
    }

    @RemoteMethod
    public CheckDateResponse checkDate(final String startDateString, final String endDateString) {
        CheckDateResponse response = new CheckDateResponse();
        Date startDate = DateUtil.getDateByString(startDateString);
        Date endDate = DateUtil.getDateByString(endDateString);
        response.setWrongStartDate(startDate == null);
        response.setWrongEndDate(endDate == null);
        response.setEndBeforeCurrent(endDate != null && endDate.before(new Date()));
        response.setEndBeforeStart(startDate != null && endDate != null && endDate.before(startDate));
        return response;
    }

    @RemoteMethod
    public CheckDateResponse isEndDateCorrect(final String date) {
        CheckDateResponse response = new CheckDateResponse();
        final Date endDate = DateUtil.roundDateTo(DateUtil.getDateByString(date), Calendar.DAY_OF_MONTH);
        if (endDate != null) {
            final Date currentDate = DateUtil.roundDateTo(new Date(), Calendar.DAY_OF_MONTH);
            final boolean endDateBeforeCurrent = !endDate.equals(currentDate) && endDate.before(currentDate);
            response.setEndBeforeCurrent(endDateBeforeCurrent);
        } else {
            response.setWrongEndDate(true);
        }
        return response;
    }

    @RemoteMethod
    public boolean isDateCorrect(final String date) {
        return DateUtil.getDateByString(date) != null;
    }

    private Response getChildSiteRegistration(final CreateChildSiteRegistrationRequest request) {
        final UserManager userManager = new UsersManager().getLogined();
        final WidgetItem widget;
        if (request.getWidgetId() != null) {
            widget = (WidgetItem) userManager.getRight().getSiteRight().getWidgetForEditInPresentationalService(
                    request.getWidgetId());
        } else {
            widget = null;
        }


        final DraftChildSiteRegistration childSiteRegistration = persistance.getDraftItem(request.getFormId());
        if (childSiteRegistration == null || childSiteRegistration.getSiteId() <= 0) {
            throw new ChildSiteRegistrationNotFoundException("Cannot find child site registration by Id=" +
                    request.getFormId());
        }

        final Site site;
        if (widget != null) {
            site = widget.getSite();
        } else {
            site = persistance.getSite(childSiteRegistration.getSiteId());
        }

        final DraftChildSiteRegistration duplicateForm = persistance.getChildSiteRegistrationFormByNameAndSiteId(request.getName(), site.getSiteId());
        if (duplicateForm != null && (duplicateForm.getFormId() != request.getFormId())) {
            throw new ChildSiteRegistrationNameNotUnique(international.get("childSiteRegistrationNameNotUnique"));
        }
        return new Response(childSiteRegistration, site, widget, userManager.getUser());
    }

    private void checkIncomeSettings(final Site site, final User user, final CheckDateResponse response) throws Exception {
        if (site != null) {
            final IncomeSettings incomeSettings = new SiteManager(site).getOrCreateIncomeSettings();
            if (StringUtil.isNullOrEmpty(incomeSettings.getPaypalAddress())) {
                getRequest().setAttribute("paypalEmail", user.getEmail());
                getRequest().setAttribute("siteId", site.getSiteId());
                response.setReceivePaymentsInnerHtml(getContext().forwardToString("/site/incomeSettings.jsp"));
                response.setShowIncomeSettingsWindow(true);
            }
        }
    }

    private CheckDateResponse checkRequestDate(final CreateChildSiteRegistrationRequest request) {
        CheckDateResponse response = new CheckDateResponse();
        if (request.isUseStartDate() && request.isUseEndDate()) {
            return checkDate(request.getStartDate(), request.getEndDate());
        } else if (request.isUseStartDate()) {
            response.setWrongStartDate(!isDateCorrect(request.getStartDate()));
            return response;
        } else if (request.isUseEndDate()) {
            return isEndDateCorrect(request.getEndDate());
        }
        return response;
    }

    private static class Response {

        private Response(DraftChildSiteRegistration draftChildSiteRegistration, Site site, Widget widget, User user) {
            this.draftChildSiteRegistration = draftChildSiteRegistration;
            this.site = site;
            this.widget = widget;
            this.user = user;
        }

        private final DraftChildSiteRegistration draftChildSiteRegistration;

        private final Site site;

        private final Widget widget;

        private final User user;

        public User getUser() {
            return user;
        }

        public DraftChildSiteRegistration getDraftChildSiteRegistration() {
            return draftChildSiteRegistration;
        }

        public Site getSite() {
            return site;
        }


        public Widget getWidget() {
            return widget;
        }
    }

    private PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final International international = ServiceLocator.getInternationStorage().get("configureChildSiteRegistration", Locale.US);
}
