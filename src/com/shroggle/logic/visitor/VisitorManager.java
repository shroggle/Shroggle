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
package com.shroggle.logic.visitor;

import com.shroggle.entity.*;
import com.shroggle.exception.VisitorManagerInitException;
import com.shroggle.exception.VisitorOnSiteRightNotFoundException;
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.logic.forum.ForumManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.Context;
import com.shroggle.util.context.ContextStorage;
import com.shroggle.util.persistance.Persistance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
public class VisitorManager {

    public VisitorManager(User visitor) {
        if (visitor == null) {
            throw new VisitorManagerInitException("Cannot initialize VisitorManager with null visitor.");
        }

        this.visitor = visitor;
    }

    public void removeVisitor() {
        //If there is no rights left then remove it.
        if (visitor.getUserOnSiteRights().isEmpty()) {
            //Unlinking user with all corresponding to it pageVisitors.
            final List<PageVisitor> pageVisitorsToClean = persistance.getPageVisitorsByUserId(visitor.getUserId());
            if (pageVisitorsToClean != null && !pageVisitorsToClean.isEmpty()) {
                for (PageVisitor pageVisitor : pageVisitorsToClean) {
                    pageVisitor.setUserId(null);
                }
            }

            //Removing all filled forms that user has completed
            List<FilledForm> filledForms = new ArrayList<FilledForm>();
            filledForms.addAll(visitor.getFilledForms());
            for (final FilledForm filledForm : filledForms) {
                FilledFormManager.remove(filledForm);
            }

            //Cleaing up all forum user activity
            new ForumManager().cleanVisitorForumActivity(visitor);

            persistance.removeUser(visitor);

            //If we were logined as deleted user let's logout this user
            final Context context = contextStorage.get();
            if (context.getUserId() != null && visitor.getUserId() == context.getUserId()) {
                context.setUserId(null);
            }
        }
    }

    /**
     * Updates old registration from if we have one in rights or creates new one.
     *
     * @param filledFormItems - items to update
     * @param form            - form
     * @param site            - site
     * @return created or updated form.
     */
    public FilledForm createOrUpadateFilledRegistrationForm(
            final List<FilledFormItem> filledFormItems, final DraftForm form, final Site site) {
        final UserOnSiteRight userOnSiteRight = persistance.getUserOnSiteRightById(new UserOnSiteRightId(visitor, site));
        FilledForm filledRegistrationForm = persistance.getFilledRegistrationFormByUserAndFormId(visitor, form.getFormId());

        if (userOnSiteRight != null && filledRegistrationForm != null) {
            //Updating existing filled registration form
            FilledFormManager.updateFilledFormItems(filledFormItems, filledRegistrationForm);
            //Updating user info
            new UserManager(visitor).updateUserInfoByFilledForm(filledRegistrationForm);
        } else {
            filledRegistrationForm = new FilledForm();
            filledRegistrationForm.setFormDescription(form.getDescription());
            filledRegistrationForm.setUser(visitor);
            filledRegistrationForm.setFormId(form.getFormId());
            filledRegistrationForm.setNetworkRegistration(((DraftRegistrationForm) form).isNetworkRegistration());
            filledRegistrationForm.setType(FormType.REGISTRATION);
            persistance.putFilledForm(filledRegistrationForm);

            //Updating existing filled registration form
            FilledFormManager.updateFilledFormItems(filledFormItems, filledRegistrationForm);
            //Updating user info
            new UserManager(visitor).updateUserInfoByFilledForm(filledRegistrationForm);
        }

        return filledRegistrationForm;
    }

    public void activateVisitor(final Site site, final String password) {
        final UserOnSiteRight visitorOnSiteRight = persistance.getUserOnSiteRightById(new UserOnSiteRightId(visitor, site));
        //Updating rights
        visitorOnSiteRight.setInvited(true);
        visitorOnSiteRight.setActive(true);
        //Updating status
        visitorOnSiteRight.setVisitorStatus(VisitorStatus.REGISTERED);

        //Adding password to invited user
        visitor.setActiveted(new Date());
        visitor.setPassword(password);
    }

    public void addVisitorOnSiteRight(final Site site, final FilledForm filledRegistrationForm) {
        UserOnSiteRight userOnSiteRight = new UserOnSiteRight();
        userOnSiteRight.setActive(true);
        userOnSiteRight.setSiteAccessType(SiteAccessLevel.VISITOR);
        // We sould always have filledRegistration form here.
        userOnSiteRight.addFilledRegistrationFormId(filledRegistrationForm.getFilledFormId());
        userOnSiteRight.setVisitorStatus(VisitorStatus.REGISTERED);
        site.addUserOnSiteRight(userOnSiteRight);
        visitor.addUserOnSiteRight(userOnSiteRight);

        persistance.putUserOnSiteRight(userOnSiteRight);
    }

    public void removeVisitorOnSiteRight(final int siteId) {
        final UserOnSiteRight visitorOnSiteRight = persistance.getUserOnSiteRightByUserAndSiteId(visitor.getUserId(), siteId);

        if (visitorOnSiteRight == null) {
            throw new VisitorOnSiteRightNotFoundException();
        }

        // UserOnSiteRights should contain registration filled form only in case when we are dealing with visitor(NOT user) rights.
        if (visitorOnSiteRight.getSiteAccessType().isVisitorAccessLevel()) {
            for (Integer filledRegistrationFormId : visitorOnSiteRight.getFilledRegistrationFormIds()) {
                final FilledForm filledRegistrationForm = persistance.getFilledFormById(filledRegistrationFormId);

                FilledFormManager.remove(filledRegistrationForm);
            }
        }

        //Removing user on site right.
        persistance.removeUserOnSiteRight(visitorOnSiteRight);
    }

    public void removeFilledFormFromVisitorOnSiteRight(final int siteId, final int filledFormId) {
        final UserOnSiteRight visitorOnSiteRight = persistance.getUserOnSiteRightByUserAndSiteId(visitor.getUserId(), siteId);

        if (visitorOnSiteRight == null) {
            throw new VisitorOnSiteRightNotFoundException();
        }

        // UserOnSiteRights should contain registration filled form only in case when we are dealing with visitor(NOT user) rights.
        if (visitorOnSiteRight.getSiteAccessType().isVisitorAccessLevel()) {
            visitorOnSiteRight.removeFilledRegistrationFormId(filledFormId);

            final FilledForm filledRegistrationForm = persistance.getFilledFormById(filledFormId);

            if (filledRegistrationForm != null) {
                FilledFormManager.remove(filledRegistrationForm);
            }

            if (!visitorOnSiteRight.getFilledRegistrationFormIds().isEmpty()) {
                //Removing user on site right.
                persistance.removeUserOnSiteRight(visitorOnSiteRight);
            }
        }
    }

    public Date getVisitorUpdateDate() {
        List<FilledForm> formList = new ArrayList<FilledForm>();
        formList.addAll(visitor.getFilledForms());
        List<UserOnSiteRight> visitorOnSiteRights = visitor.getUserOnSiteRights();

        for (UserOnSiteRight visitorOnSiteRight : visitorOnSiteRights) {
            for (Integer filledRegistrationFormId : visitorOnSiteRight.getFilledRegistrationFormIds()) {
                final FilledForm filledRegistrationForm = persistance.getFilledFormById(filledRegistrationFormId);

                if (filledRegistrationForm != null) {
                    formList.add(filledRegistrationForm);
                }
            }
        }

        if (!formList.isEmpty()) {
            Date newestDate = formList.get(0).getUpdatedDate() != null ? formList.get(0).getUpdatedDate() : formList.get(0).getFillDate();
            for (FilledForm filledForm : formList) {
                if (newestDate.compareTo(filledForm.getUpdatedDate() != null ? filledForm.getUpdatedDate() : filledForm.getFillDate()) < 0) {
                    newestDate = filledForm.getFillDate();
                }
            }

            return newestDate;
        } else {
            return null;
        }
    }

    public FilledForm addFilledFormToVisitor(final List<FilledFormItem> filledFormItems, final Form form) {
        final FilledForm filledForm = FilledFormManager.saveFilledForm(filledFormItems, form);

        filledForm.setUser(visitor);
        visitor.addFilledForm(filledForm);

        return filledForm;
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ContextStorage contextStorage = ServiceLocator.getContextStorage();
    private final User visitor;
}
