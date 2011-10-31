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
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.url.UrlValidator;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.exception.*;
import com.shroggle.logic.user.EmailChecker;
import com.shroggle.exception.IncorrectEmailException;

import java.util.Locale;


/**
 * @author Balakirev Anatoliy
 */
public class CreateChildSiteInputParametersChecker {

    public static void execute(final CreateChildSiteRequest request,
                               final int widgetId, final String noBotCode) throws Exception {
        final International international = ServiceLocator.getInternationStorage().get("childSiteRegistration", Locale.US);
        if (!request.isShowFromAddRecord()) {
            if (request.getVerificationCode() == null || noBotCode == null || !noBotCode.equals(request.getVerificationCode())) {
                throw new WrongVerificationCodeException(international.get("wrongCode"));
            }
        }

        final Persistance persistance = ServiceLocator.getPersistance();

        final Widget widget = persistance.getWidget(widgetId);
        final Site site;
        if (widget != null) {
            site = widget.getSite();
        } else {
            DraftForm form = persistance.getFormById(request.getFormId());

            if (form == null) {
                throw new FormNotFoundException("Cannot find form by Id=" + request.getFormId());
            }

            site = persistance.getSite(form.getSiteId());
        }

        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new VisitorWithNullOrEmptyEmailException(international.get("VisitorWithNullOrEmptyEmailException"));
        } else if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new VisitorWithNullPasswordException(international.get("VisitorWithNullPasswordException"));
        } else if (request.getConfirmPassword() == null || !request.getPassword().equals(request.getConfirmPassword())) {
            throw new VisitorWithNotEqualsPasswordAndConfirmPaswordException(international.get("VisitorWithNotEqualsPasswordAndConfirmPaswordException"));
        } else if (site == null) {
            throw new CannotCreateVisitorForNullSiteException();
        } else if (persistance.getUserByEmail(request.getEmail().toLowerCase()) != null
                && (persistance.getUserByEmail(request.getEmail().toLowerCase()).getPassword() == null
                || !persistance.getUserByEmail(request.getEmail().toLowerCase()).getPassword().equals(request.getPassword()))) {
            throw new VisitorWithNotUniqueLogin(international.get("emailRegistered"));
        } else {
            try {
                new EmailChecker().execute(request.getEmail());
            } catch (final IncorrectEmailException ex) {
                throw new NotValidVisitorEmailException(international.get("NotValidVisitorEmailException"));
            }
        }

        if (persistance.getChildSiteRegistrationById(request.getFormId()) == null) {
            throw new ChildSiteRegistrationNotFoundException("Cannot find ChildSiteRegistration by id=" + request.getFormId());
        }
        checkDomainName(FilledFormManager.getDomainName(request.getFilledFormItems()));
    }

    public static void executeForNotFirstPage(final CreateChildSiteRequest request) throws Exception {
        if (request.getUserId() <= 0) {
            throw new UserNotFoundException("User Id isn't set in request.");
        }

        if (request.getSettingsId() <= 0) {
            throw new ChildSiteSettingsNotFoundException("Child site settings Id isn't set in request.");
        }

        final Persistance persistance = ServiceLocator.getPersistance();

        if (persistance.getChildSiteRegistrationById(request.getFormId()) == null) {
            throw new ChildSiteRegistrationNotFoundException("Cannot find ChildSiteRegistration by Id=" + request.getFormId());
        }

        if (persistance.getFilledFormById(request.getFilledFormId()) == null) {
            throw new FilledFormNotFoundException("Cannot find FilledForm by Id=" + request.getFormId());
        }
        checkDomainName(FilledFormManager.getDomainName(request.getFilledFormItems()));
    }

    public static void executeForEdit(final CreateChildSiteRequest request) {
        final Persistance persistance = ServiceLocator.getPersistance();

        if (persistance.getFilledFormById(request.getFilledFormId()) == null) {
            throw new FilledFormNotFoundException("Cannot find FilledForm by Id=" + request.getFormId());
        }

        if (persistance.getChildSiteRegistrationById(request.getFormId()) == null) {
            throw new ChildSiteRegistrationNotFoundException("Cannot find ChildSiteRegistration by Id=" + request.getFormId());
        }
        checkDomainName(FilledFormManager.getDomainName(request.getFilledFormItems()));
    }


    protected static void checkDomainName(final String domainName) {
        final International international = ServiceLocator.getInternationStorage().get("childSiteRegistration", Locale.US);
        if (!StringUtil.isNullOrEmpty(domainName)) {
            if (!UrlValidator.isSystemSubDomainValid(domainName)) {
                throw new WrongSubDomainNameException(international.get("enterCorrectDomainName"));
            }
        }
    }
}