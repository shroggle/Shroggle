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
package com.shroggle.logic.registration;

import com.shroggle.entity.*;
import com.shroggle.exception.*;
import com.shroggle.logic.form.FormItemsManager;
import com.shroggle.logic.form.FormManager;
import com.shroggle.logic.groups.GroupsTime;
import com.shroggle.logic.groups.GroupsTimeManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.presentation.registration.SaveRegistrationRequest;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author dmitry.solomadin
 */
public class RegistrationFormCreator {

    public RegistrationFormCreator(final UserManager userManager) {
        this.userManager = userManager;
    }

    public void save(final SaveRegistrationRequest request) {
        final Widget widget;
        if (request.getWidgetId() != null) {
            widget = userManager.getRight().getSiteRight().getWidgetForEditInPresentationalService(
                    request.getWidgetId());
        } else {
            widget = null;
        }

        if (StringUtil.isNullOrEmpty(request.getFormName())) {
            throw new RegistrationFormNullOrEmptyNameException(registrationBundle.get("RegistrationFormNullOrEmptyNameException"));
        }

        if (new FormItemsManager().isPageBreakBeforeRequiredFields(request.getFormItems())) {
            throw new PageBreakBeforeRequiredFieldsException(registrationBundle.get("PageBreakBeforeRequiredFieldsException"));
        }

        persistanceTransaction.execute(new Runnable() {
            public void run() {
                final DraftRegistrationForm form = persistance.getDraftItem(request.getFormId());
                if (form == null || form.getSiteId() <= 0) {
                    throw new RegistrationFormNotFoundException("Cannot find registration by Id=" + request.getFormId());
                }

                final Site site;
                if (widget != null) {
                    site = widget.getSite();
                } else {
                    site = persistance.getSite(form.getSiteId());
                }

                final DraftRegistrationForm duplicateForm = persistance.getRegistrationFormByNameAndSiteId(
                        request.getFormName(), site.getSiteId());
                if (duplicateForm != null && duplicateForm != form) {
                    throw new RegistrationFormNotUniqueNameException(registrationBundle.get("RegistrationFormNotUniqueNameException"));
                }

                form.setName(request.getFormName());
                form.setDescription(request.getFormHeader());
                form.setShowDescription(request.isShowHeader());
                form.setTermsAndConditions(request.getTermsAndConditions());
                form.setRequireTermsAndConditions(request.isRequireTermsAndConditions());
                form.setNetworkRegistration(request.isNetworkRegistration());

                final List<GroupsTime> groupsTimes = new ArrayList<GroupsTime>();
                for (GroupsTime groupIdWithTimeInterval : request.getGroupsWithTime()) {
                    final GroupsTime groupsTime = new GroupsTime(groupIdWithTimeInterval.getGroupId(), groupIdWithTimeInterval.getTimeInterval());
                    groupsTimes.add(groupsTime);
                }
                form.setGroupsWithTime(GroupsTimeManager.valueOf(groupsTimes));

                //Setting default registration form for this site
                if (request.isDefaultForm()) {
                    site.setDefaultFormId(form.getFormId());
                }

                form.setFormItems(new FormManager().createOrSetFormItems(form, request.getFormItems()));
            }
        });
    }

    private Persistance persistance = ServiceLocator.getPersistance();
    private PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private International registrationBundle = ServiceLocator.getInternationStorage().get("registration", Locale.US);
    private UserManager userManager;
}
