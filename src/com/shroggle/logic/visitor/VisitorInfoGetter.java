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

import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.logic.user.UsersGroupManager;
import com.shroggle.presentation.site.RegisteredVisitorInfo;
import com.shroggle.entity.*;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.exception.VisitorOnSiteRightNotFoundException;
import com.shroggle.util.DateUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;

/**
 * @author dmitry.solomadin
 */
public class VisitorInfoGetter {

    public List<RegisteredVisitorInfo> execute(final List<User> users, final int siteId) {
        List<RegisteredVisitorInfo> visitorInfos = new ArrayList<RegisteredVisitorInfo>();
        for (User user : users) {
            visitorInfos.add(execute(user, siteId));
        }
        return visitorInfos;
    }

    public RegisteredVisitorInfo execute(final User user, final int siteId) {
        final Site site = persistance.getSite(siteId);
        if (site == null) {
            throw new SiteNotFoundException("Cannot find site by Id=" + siteId);
        }

        final UserOnSiteRight visitorOnSiteRight = persistance.getUserOnSiteRightByUserAndSiteId(user.getUserId(), siteId);
        if (visitorOnSiteRight == null) {
            throw new VisitorOnSiteRightNotFoundException();
        }

        final VisitorManager visitorManager = new VisitorManager(user);

        final FilledForm filledRegistrationForm = FilledFormManager.getFirstRegistrationFilledFormForSite(user, site);
        final Date updatedDate = visitorManager.getVisitorUpdateDate() == null ? visitorOnSiteRight.getCreated() : visitorManager.getVisitorUpdateDate();

        RegisteredVisitorInfo registeredVisitorInfo = new RegisteredVisitorInfo();
        registeredVisitorInfo.setFirstName(FilledFormManager.getFilledFormItemValueByItemName(filledRegistrationForm, FilledFormManager.getFormInternational().get(FormItemName.FIRST_NAME + "_FN")));
        registeredVisitorInfo.setLastName(FilledFormManager.getFilledFormItemValueByItemName(filledRegistrationForm, FilledFormManager.getFormInternational().get(FormItemName.LAST_NAME + "_FN")));
        registeredVisitorInfo.setEmail(FilledFormManager.getFilledFormItemValueByItemName(filledRegistrationForm, FilledFormManager.getFormInternational().get(FormItemName.EMAIL + "_FN")));
        registeredVisitorInfo.setCreated(DateUtil.toCommonDateStr(visitorOnSiteRight.getCreated()));
        registeredVisitorInfo.setUpdated(DateUtil.toCommonDateStr(updatedDate));
        registeredVisitorInfo.setCreatedDateRaw(visitorOnSiteRight.getCreated());
        registeredVisitorInfo.setUpdatedDateRaw(updatedDate);

        for (Integer filledRegistrationFormId : visitorOnSiteRight.getFilledRegistrationFormIds()){
            final Integer formId = persistance.getFilledFormById(filledRegistrationFormId).getFormId();

            registeredVisitorInfo.addForm(persistance.getFormById(formId));
        }

        registeredVisitorInfo.setStatus(getManageRegistrantsInternational().get(visitorOnSiteRight.getVisitorStatus().toString() + "_STATUS"));
        registeredVisitorInfo.setVisitorId(user.getUserId());
        registeredVisitorInfo.setInvited(visitorOnSiteRight.isInvited());

        final UsersGroupManager usersGroupManager = new UsersGroupManager(user);
        if (!usersGroupManager.getAccessibleGroupsId().isEmpty()) {
            final StringBuilder stringBuilder = new StringBuilder("");
            for (Group group : persistance.getGroups(usersGroupManager.getAccessibleGroupsIdForSite(site.getSiteId()))) {
                stringBuilder.append(group.getName());
                final Date expirationDate = usersGroupManager.getExpirationDateForGroup(group.getGroupId());
                if (expirationDate != null) {
                    stringBuilder.append(" (till ");
                    stringBuilder.append(DateUtil.toMonthDayAndYear(expirationDate));
                    stringBuilder.append(")");
                }
                stringBuilder.append(",<br> ");
            }
            if (stringBuilder.length() > 0) {
                stringBuilder.replace(stringBuilder.length() - 6, stringBuilder.length(), "");
            }
            registeredVisitorInfo.setGroupsNames(stringBuilder.toString());
        }
        return registeredVisitorInfo;
    }

    public static International getManageRegistrantsInternational() {
        return ServiceLocator.getInternationStorage().get("manageRegistrants", Locale.US);
    }

    private final Persistance persistance = ServiceLocator.getPersistance();

}
