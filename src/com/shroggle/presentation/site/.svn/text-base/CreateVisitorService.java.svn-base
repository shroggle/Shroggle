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
import com.shroggle.logic.form.FormManager;
import com.shroggle.logic.groups.GroupsTime;
import com.shroggle.logic.groups.GroupsTimeManager;
import com.shroggle.logic.user.EmailChecker;
import com.shroggle.logic.user.UsersGroupManager;
import com.shroggle.logic.user.UsersManager;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

@RemoteProxy
public class CreateVisitorService extends AbstractService {

    @RemoteMethod
    public CreateVisitorResponse execute(final CreateVisitorRequest request)
            throws ServletException, IOException {
        if (!request.isShowFromAddRecord() && !request.isEditDetails() && (request.getPageBreaksToPass() == 0 || request.getPageBreaksToPass() == 1)) {
            final String noBotCode = sessionStorage.getNoBotCode(this, "registration" + request.getWidgetId());
            if (!noBotCode.equals(request.getVerificationCode())) {
                throw new WrongVerificationCodeException(international.get("wrongCode"));
            }
        }

        final DraftRegistrationForm registrationForm = persistance.getRegistrationFormById(request.getFormId());

        if (registrationForm == null) {
            throw new RegistrationFormNotFoundException("Cannot find registration by Id=" + request.getFormId());
        }

        final Site site;
        if (request.getInviteForSiteId() != null) {
            site = persistance.getSite(request.getInviteForSiteId());
        } else {
            if (registrationForm.isNetworkRegistration() || request.getWidgetId() == 0) {
                site = persistance.getSite(registrationForm.getSiteId());
            } else {
                final Widget widget = persistance.getWidget(request.getWidgetId());
                if (widget == null) {
                    throw new WidgetNotFoundException("Cannot find widget by id=" + request.getWidgetId());
                }
                site = widget.getSite();
            }
        }

        //Checking parameters only on first page of the form. Assuming that they are right on other pages.
        if ((request.getPageBreaksToPass() <= 1) && (!request.isEditDetails() || request.isFillOutFormCompletely())) {
            final User duplicateUser = persistance.getUserByEmail(request.getEmail().toLowerCase());
            //Keep here direct rights getting from persistance.
            final UserOnSiteRight visitorOnSiteRight = persistance.getUserOnSiteRightById(new UserOnSiteRightId(duplicateUser, site));

            //Ture if we have user to update info AND we are editing details for user
            //                                       OR this user has no rights on this site(we are adding him to this site)
            //                                       OR this has rights on this site but now he is registering from other form.
            //                                       OR this user is pending(we came from invitation).
            final boolean updatingInfo = duplicateUser != null &&
                    (request.isEditDetails()
                            || (visitorOnSiteRight == null && duplicateUser.getPassword() != null && request.getPassword().equals(duplicateUser.getPassword()))
                            || (visitorOnSiteRight != null && persistance.getFilledRegistrationFormByUserAndFormId(duplicateUser, request.getFormId()) == null)
                            || (visitorOnSiteRight != null && visitorOnSiteRight.getVisitorStatus().equals(VisitorStatus.PENDING) && request.getInviteForSiteId() != null));

            if (visitorOnSiteRight != null && visitorOnSiteRight.getVisitorStatus() == VisitorStatus.EXPIRED
                    && request.getInviteForSiteId() != null){
                throw new ExpiredVisitorAttemptToRegisterException(international.get("ExpiredVisitorAttemptToRegisterException"));
            }

            if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                throw new VisitorWithNullOrEmptyEmailException(international.get("VisitorWithNullOrEmptyEmailException"));
            } else if ((request.getPassword() == null || request.getPassword().isEmpty()) && !updatingInfo) {
                throw new VisitorWithNullPasswordException(international.get("VisitorWithNullPasswordException"));
            } else if ((request.getPassword() == null || !request.getPassword().equals(request.getConfirmPassword())) && !request.isEditDetails()) {
                throw new VisitorWithNotEqualsPasswordAndConfirmPaswordException(international.get("VisitorWithNotEqualsPasswordAndConfirmPaswordException"));
            } else if (duplicateUser != null && (!updatingInfo)) {
                throw new VisitorWithNotUniqueLogin("");
            } else if (site == null) {
                throw new CannotCreateVisitorForNullSiteException();
            } else if (!updatingInfo) {
                try {
                    new EmailChecker().execute(request.getEmail());
                } catch (IncorrectEmailException ex) {
                    throw new NotValidVisitorEmailException(international.get("NotValidVisitorEmailException"));
                }
            }

            persistanceTransaction.execute(new Runnable() {
                public void run() {
                    if (updatingInfo) {
                        visitor = duplicateUser;
                        updateVisitorInternal(visitor, request.getScreenName(),
                                request.getFirstName(), request.getLastName());
                    } else {
                        visitor = createVisitorInternal(request.getEmail(), request.getPassword(),
                                request.getScreenName(), request.getFirstName(), request.getLastName());
                    }
                    if (!registrationForm.getGroupsWithTime().isEmpty()) {
                        for (GroupsTime groupsTime : GroupsTimeManager.valueOf(registrationForm.getGroupsWithTime())) {
                            final Group group = persistance.getGroup(groupsTime.getGroupId());
                            if (group != null) {
                                new UsersGroupManager(visitor).addAccessToGroup(group, groupsTime.getTimeInterval());
                            } else {
                                logger.warning("Unable to find group with id = " + groupsTime.getGroupId());
                            }
                        }
                    }
                    filledRegistrationForm = new VisitorManager(visitor).createOrUpadateFilledRegistrationForm(request.getFilledFormItems(), registrationForm, site);

                    final VisitorManager visitorManager = new VisitorManager(visitor);
                    if (visitorOnSiteRight == null) {
                        visitorManager.addVisitorOnSiteRight(site, filledRegistrationForm);
                    } else {
                        if (visitorOnSiteRight.getVisitorStatus().equals(VisitorStatus.PENDING)) {
                            //Activating visitor if it was invited
                            visitorManager.activateVisitor(site, request.getPassword());
                        }

                        visitorOnSiteRight.addFilledRegistrationFormId(filledRegistrationForm.getFilledFormId());
                    }
                }
            });
        } else {
            visitor = persistance.getUserById(request.getUserId());

            //If page isn't first just update form.
            persistanceTransaction.execute(new Runnable() {
                public void run() {
                    filledRegistrationForm = new VisitorManager(visitor).createOrUpadateFilledRegistrationForm(request.getFilledFormItems(), registrationForm, site);
                }
            });
        }

        //Linking pageVisitor and user
        final Integer pageVisitorId = ActionUtil.getPageVisitorId(getContext().getHttpServletRequest().getCookies());
        if (pageVisitorId != null) {
            final PageVisitor pageVisitor = persistance.getPageVisitorById(pageVisitorId);
            if (pageVisitor != null && pageVisitor.getUserId() == null) {
                persistanceTransaction.execute(new Runnable() {
                    public void run() {
                        pageVisitor.setUserId(visitor.getUserId());
                    }
                });
            }
        }

        if (!request.isShowFromAddRecord()) {
            sessionStorage.setRegistrationFormFilledInThisSessionInService(this, request.getFormId());
            new UsersManager().login(visitor.getUserId());
        }

        return composeResponse(request, registrationForm);
    }

    private User createVisitorInternal(final String email, final String password, final String screenName,
                                       final String firstName, final String lastName) {
        final User visitor = new User();
        visitor.setRegistrationDate(new Date());
        visitor.setActiveted(new Date());
        visitor.setEmail(email);
        visitor.setPassword(password);
        visitor.setScreenName(screenName != null ? screenName : "");
        visitor.setFirstName(firstName != null ? firstName : "");
        visitor.setLastName(lastName != null ? lastName : "");
        persistance.putUser(visitor);

        return visitor;
    }

    private void updateVisitorInternal(final User visitor, final String screenName,
                                       final String firstName, final String lastName) {
        visitor.setScreenName(screenName);
        visitor.setFirstName(firstName);
        visitor.setLastName(lastName);
    }

    private CreateVisitorResponse composeResponse(final CreateVisitorRequest request,
                                                  final DraftRegistrationForm registrationForm) throws IOException, ServletException {
        final CreateVisitorResponse response = new CreateVisitorResponse();
        response.setFilledFormId(filledRegistrationForm.getFilledFormId());

        List<FormPageAdditionalParameters> additionalParameters = new ArrayList<FormPageAdditionalParameters>();
        additionalParameters.add(new FormPageAdditionalParameters("registrationUserId", "" + visitor.getUserId()));
        if (request.isRequestNextPage() && !(request.getPageBreaksToPass() > FormManager.getTotalPageBreaks(persistance.getFormById(request.getFormId())))) {
            if (request.getWidgetId() != 0) {
                final RenderContext context = createRenderContext(false);                

                response.setNextPageHtml(new RenderFormPageBreak().execute(request.getWidgetId(), request.getFormId(),
                        request.getPageBreaksToPass(), filledRegistrationForm.getFilledFormId(),
                        additionalParameters, context));
            } else {
                response.setNextPageHtml(new RenderFormPageBreak().executeForAddRecord(ItemType.REGISTRATION,
                        request.getPageBreaksToPass(), filledRegistrationForm.getFilledFormId(),
                        registrationForm.getFormId(), additionalParameters));
            }
        }

        response.setFilledFormId(filledRegistrationForm.getFilledFormId());
        if (request.getInviteForSiteId() != null) {
            response.setCode("InvitedRegistered");
            final Site site = persistance.getSite(request.getInviteForSiteId());
            response.setRedirectURL("http://" + site.getSubDomain() + "." + ServiceLocator.getConfigStorage().get().getUserSitesUrl());
            return response;
        } else if (request.isEditDetails()){
            response.setCode("SuccessfullyEdited");
            return response;
        } else {
            response.setCode("SuccessfullyRegistered");
            return response;
        }
    }

    private FilledForm filledRegistrationForm;
    private User visitor;
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ShowVisitorLoginService showVisitorLoginService = new ShowVisitorLoginService();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final International international = ServiceLocator.getInternationStorage().get("registration", Locale.US);
    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
    private final Logger logger = Logger.getLogger(this.getClass().getName());
}
