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
package com.shroggle.presentation.manageRegistrants;

import com.shroggle.entity.*;
import com.shroggle.exception.IncorrectEmailException;
import com.shroggle.exception.RegistrationFormNotFoundException;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.exception.VisitorWithNotUniqueLogin;
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.logic.groups.GroupsTime;
import com.shroggle.logic.user.EmailChecker;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersGroupManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.visitor.VisitorInfoGetter;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.GuestInvitationRequest;
import com.shroggle.presentation.site.RegisteredVisitorInfo;
import com.shroggle.util.MD5;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.international.International;
import com.shroggle.util.mail.Mail;
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

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class InviteGuestService extends AbstractService {

    @RemoteMethod
    public String show(final int siteId) throws ServletException, IOException {
        final UserManager userManager = new UsersManager().getLogined();

        this.siteId = siteId;
        final Site site = persistance.getSite(siteId);

        if (site == null) {
            throw new SiteNotFoundException();
        }

        siteName = StringUtil.isNullOrEmpty(site.getTitle()) ? site.getSubDomain() : site.getTitle();

        // Here should be only registration forms, created for selected site.
        registrationForms.addAll(persistance.getDraftItemsBySiteId(siteId, ItemType.REGISTRATION));
        groups.addAll(site.getOwnGroups());

        getContext().getHttpServletRequest().setAttribute("service", this);
        return getContext().forwardToString("/account/manageRegistrants/inviteGuest.jsp");
    }

    @RemoteMethod
    public RegisteredVisitorInfo sendGuestInvitation(final GuestInvitationRequest request) {
        new UsersManager().getLogined();

        try {
            new EmailChecker().execute(request.getEmail().toLowerCase());
        } catch (Exception exception) {
            throw new IncorrectEmailException("");
        }

        final Site site = persistance.getSite(request.getSiteId());

        if (site == null) {
            throw new SiteNotFoundException("Cannot find site by Id=" + request.getSiteId());
        }

        final DraftRegistrationForm registrationForm = persistance.getRegistrationFormById(request.getFormId());
        if (registrationForm == null) {
            throw new RegistrationFormNotFoundException("Cannot find registration form by Id=" + request.getFormId());
        }

        final String formDescription = registrationForm.getDescription();

        final User dublicateUser = persistance.getUserByEmail(request.getEmail().toLowerCase());
        if (dublicateUser != null) {
            final UserOnSiteRightId userOnSiteRightId = new UserOnSiteRightId(dublicateUser, site);
            final UserOnSiteRight userOnSiteRight = persistance.getUserOnSiteRightById(userOnSiteRightId);
            if (userOnSiteRight != null && (userOnSiteRight.getSiteAccessType() == SiteAccessLevel.ADMINISTRATOR
                    || userOnSiteRight.getSiteAccessType() == SiteAccessLevel.EDITOR)) {
                throw new VisitorWithNotUniqueLogin(international.get("VisitorWithUserEmail"));
            } else if (userOnSiteRight != null) {
                throw new VisitorWithNotUniqueLogin(international.get("VisitorWithNotUniqueEmail"));
            }
        }

        String emailBody = findAndReplaceTextInInvitation(request, site);

        final User user = dublicateUser != null ? dublicateUser : new User();
        persistanceTransaction.execute(new Runnable() {
            public void run() {
                if (dublicateUser == null) {
                    //Creating user
                    user.setRegistrationDate(new Date());
                    user.setEmail(request.getEmail());
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    persistance.putUser(user);

                    //Creating filled registration form for this user
                    final FilledForm filledRegistrationForm = createRegistrationForm(request, formDescription, user);

                    //Creating user rights
                    createVisitorOnSiteRights(user, site, filledRegistrationForm, VisitorStatus.PENDING);
                } else {
                    //Creating filled registration form for this user
                    final FilledForm filledRegistrationForm = createRegistrationForm(request, formDescription, user);

                    //Creating user rights
                    createVisitorOnSiteRights(user, site, filledRegistrationForm, VisitorStatus.PENDING);
                }
                for (GroupsTime groupsTime : request.getGroupsWithTimeInterval()) {
                    final Group group = persistance.getGroup(groupsTime.getGroupId());
                    if (group != null) {
                        new UsersGroupManager(user).addAccessToGroup(group, groupsTime.getTimeInterval());
                    } else {
                        logger.warning("Unable to find group with id = " + groupsTime.getGroupId());
                    }
                }
            }
        });

        final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
        // "" + request.getFormId() -- this fixes strange bug (http://jira.web-deva.com/browse/SW-4872).
        emailBody = international.get("emailMessage", emailBody, configStorage.get().getApplicationUrl(), "" + request.getFormId(), user.getUserId(), MD5.crypt(request.getEmail() + user.getUserId()));

        String messageSubject = international.get("emailSubject", site.getTitle() == null ? site.getSubDomain() : site.getTitle());
        ServiceLocator.getMailSender().send(new Mail(request.getEmail(), emailBody, messageSubject));

        return new VisitorInfoGetter().execute(user, request.getSiteId());
    }

    private void createVisitorOnSiteRights(final User user, final Site site, final FilledForm filledRegistrationForm, final VisitorStatus visitorStatus) {
        final UserOnSiteRight visitorOnSiteRight = new UserOnSiteRight();
        // We sould always have filledRegistration form here.
        visitorOnSiteRight.addFilledRegistrationFormId(filledRegistrationForm.getFilledFormId());
        visitorOnSiteRight.setVisitorStatus(visitorStatus);
        visitorOnSiteRight.setSiteAccessType(SiteAccessLevel.GUEST);
        visitorOnSiteRight.setInvited(true);
        site.addUserOnSiteRight(visitorOnSiteRight);
        user.addUserOnSiteRight(visitorOnSiteRight);

        persistance.putUserOnSiteRight(visitorOnSiteRight);
    }

    private FilledForm createRegistrationForm(final GuestInvitationRequest request, final String formDescription, final User user) {
        final FilledForm filledRegistrationForm = new FilledForm();
        filledRegistrationForm.setUser(user);
        filledRegistrationForm.setFormDescription(StringUtil.isNullOrEmpty(formDescription) ? "" : formDescription);
        filledRegistrationForm.setFormId(request.getFormId());
        filledRegistrationForm.setType(FormType.REGISTRATION);
        persistance.putFilledForm(filledRegistrationForm);

        //Adding fisrt name, last name and email as items to new registration form.
        FilledFormManager.saveFilledFormItem(FilledFormManager.getFormInternational().get(FormItemName.EMAIL + "_FN"),
                FormItemName.EMAIL, filledRegistrationForm, request.getEmail());

        FilledFormManager.saveFilledFormItem(FilledFormManager.getFormInternational().get(FormItemName.FIRST_NAME + "_FN"),
                FormItemName.FIRST_NAME, filledRegistrationForm, request.getFirstName());

        FilledFormManager.saveFilledFormItem(FilledFormManager.getFormInternational().get(FormItemName.LAST_NAME + "_FN"),
                FormItemName.LAST_NAME, filledRegistrationForm, request.getLastName());

        return filledRegistrationForm;
    }

    private String findAndReplaceTextInInvitation(final GuestInvitationRequest request, final Site site) {
        final ConfigStorage configStorage = ServiceLocator.getConfigStorage();

        String emailBody = request.getInvitationText();
        emailBody = emailBody.replace("<first name last name>", ((request.getFirstName() != null ? request.getFirstName() : "") + " " + (request.getLastName() != null ? request.getLastName() : "")));
        emailBody = emailBody.replace("<web site name>", site.getTitle() == null ? site.getSubDomain() : site.getTitle());
        emailBody = emailBody.replace("<site url>", "http://" + site.getSubDomain() + "." + configStorage.get().getApplicationUrl());
        return emailBody;
    }

    public List<DraftItem> getRegistrationForms() {
        return registrationForms;
    }

    public int getSiteId() {
        return siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public String getDefaultGroupName() {
        return defaultGroupName;
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final International international = ServiceLocator.getInternationStorage().get("inviteGuest", Locale.US);
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private int siteId;
    private String siteName;
    private List<DraftItem> registrationForms = new ArrayList<DraftItem>();
    private final List<Group> groups = new ArrayList<Group>();
    private final String defaultGroupName = ServiceLocator.getInternationStorage().get("configureGroupsWindow", Locale.US).get("invited");
    private final Logger logger = Logger.getLogger(this.getClass().getName());
}
