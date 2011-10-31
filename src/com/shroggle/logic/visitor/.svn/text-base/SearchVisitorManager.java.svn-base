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
import com.shroggle.exception.VisitorOnSiteRightNotFoundException;
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.ServiceLocator;

import java.util.*;

/**
 * @author dmitry.solomadin
 */
public class SearchVisitorManager {

    public List<User> searchVisitorsByStatusAndSearchKey(final RegistrantFilterType status, final String searchKey, final int siteId) {
        final List<User> usersByStatus = searchVisitorsByStatus(status, siteId);

        return searchVisitorsBySearchKey(usersByStatus, searchKey, siteId);
    }

    public List<User> searchVisitorsByStatus(final RegistrantFilterType status, final int siteId) {
        List<User> allUsers = persistance.getVisitorsBySiteId(siteId);

        if (allUsers == null) {
            return Collections.emptyList();
        }

        final List<User> returnList = new ArrayList<User>();

        switch (status) {
            case SHOW_ALL: {
                for (User user : allUsers) {
                    final UserOnSiteRight visitorOnSiteRight = persistance.getUserOnSiteRightByUserAndSiteId(user.getUserId(), siteId);

                    if (visitorOnSiteRight == null) {
                        throw new VisitorOnSiteRightNotFoundException();
                    }

                    returnList.add(user);
                }
                break;
            }
            case EXPIRED: {
                for (User user : allUsers) {
                    final UserOnSiteRight visitorOnSiteRight = persistance.getUserOnSiteRightByUserAndSiteId(user.getUserId(), siteId);

                    if (visitorOnSiteRight == null) {
                        throw new VisitorOnSiteRightNotFoundException();
                    }

                    if (visitorOnSiteRight.getVisitorStatus().equals(VisitorStatus.EXPIRED)) {
                        returnList.add(user);
                    }
                }
                break;
            }
            case INVITED: {
                for (User user : allUsers) {
                    final UserOnSiteRight visitorOnSiteRight = persistance.getUserOnSiteRightByUserAndSiteId(user.getUserId(), siteId);

                    if (visitorOnSiteRight == null) {
                        throw new VisitorOnSiteRightNotFoundException();
                    }

                    if (visitorOnSiteRight.isInvited()) {
                        returnList.add(user);
                    }
                }
                break;
            }
            case PENDING: {
                for (User user : allUsers) {
                    final UserOnSiteRight visitorOnSiteRight = persistance.getUserOnSiteRightByUserAndSiteId(user.getUserId(), siteId);

                    if (visitorOnSiteRight == null) {
                        throw new VisitorOnSiteRightNotFoundException();
                    }

                    if (visitorOnSiteRight.getVisitorStatus().equals(VisitorStatus.PENDING)) {
                        returnList.add(user);
                    }
                }
                break;
            }
            case REGISTERED: {
                for (User user : allUsers) {
                    final UserOnSiteRight visitorOnSiteRight = persistance.getUserOnSiteRightByUserAndSiteId(user.getUserId(), siteId);

                    if (visitorOnSiteRight == null) {
                        throw new VisitorOnSiteRightNotFoundException();
                    }

                    if (visitorOnSiteRight.getVisitorStatus().equals(VisitorStatus.REGISTERED)) {
                        returnList.add(user);
                    }
                }
                break;
            }
        }

        return returnList;
    }

    public List<User> searchVisitorsBySearchKey(final List<User> usersToSearchAmong, final String searchKey, final int siteId) {
        final Site site = persistance.getSite(siteId);

        Set<User> keyUsers = new HashSet<User>();
        if (!searchKey.trim().isEmpty()) {
            String[] keys = searchKey.split(" ");
            for (String key : keys) {
                for (User user : usersToSearchAmong) {
                    final FilledForm filledRegistrationForm = FilledFormManager.getFirstRegistrationFilledFormForSite(user, site);
                    String firstName = FilledFormManager.getFilledFormItemValueByItemName(filledRegistrationForm, FilledFormManager.getFormInternational().get(FormItemName.FIRST_NAME + "_FN"));
                    String lastName = FilledFormManager.getFilledFormItemValueByItemName(filledRegistrationForm, FilledFormManager.getFormInternational().get(FormItemName.LAST_NAME + "_FN"));
                    String email = FilledFormManager.getFilledFormItemValueByItemName(filledRegistrationForm, FilledFormManager.getFormInternational().get(FormItemName.EMAIL + "_FN"));
                    if (firstName != null && lastName != null && email != null && (firstName.toLowerCase().contains(key.toLowerCase()) ||
                            lastName.toLowerCase().contains(key.toLowerCase()) ||
                            email.toLowerCase().contains(key.toLowerCase()))) {
                        keyUsers.add(user);
                    }
                }
            }
            if (searchKey.equals("not specified")) {
                for (User user : usersToSearchAmong) {
                    final FilledForm filledRegistrationForm = FilledFormManager.getFirstRegistrationFilledFormForSite(user, site);
                    String firstName = FilledFormManager.getFilledFormItemValueByItemName(filledRegistrationForm, FilledFormManager.getFormInternational().get(FormItemName.FIRST_NAME + "_FN"));
                    String lastName = FilledFormManager.getFilledFormItemValueByItemName(filledRegistrationForm, FilledFormManager.getFormInternational().get(FormItemName.LAST_NAME + "_FN"));
                    if ((firstName.trim().isEmpty() || lastName.trim().isEmpty())) {
                        keyUsers.add(user);
                    }
                }
            }
            return new ArrayList<User>(keyUsers);
        } else {
            return usersToSearchAmong;
        }
    }

    private final Persistance persistance = ServiceLocator.getPersistance();

}
