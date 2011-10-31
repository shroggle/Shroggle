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
package com.shroggle.logic.user;

import com.shroggle.entity.*;
import com.shroggle.exception.SiteItemNotFoundException;
import com.shroggle.exception.UserNotFoundException;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.childSites.childSiteSettings.ChildSiteSettingsManager;
import com.shroggle.logic.countries.states.StateManager;
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.logic.gallery.VideoRangesManager;
import com.shroggle.logic.site.SitesManager;
import com.shroggle.presentation.account.accessPermissions.InviteUserHelper;
import com.shroggle.presentation.site.ChildSiteEmailSender;
import com.shroggle.util.MD5;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.config.Config;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.international.International;
import com.shroggle.util.international.InternationalStorage;
import com.shroggle.util.mail.Mail;
import com.shroggle.util.mail.MailSender;
import com.shroggle.util.persistance.Persistance;

import java.text.DateFormat;
import java.util.*;

/**
 * @author Artem Stasuk
 */
public class UserManager {

    /**
     * @param email - user email in any cases and allow unnecessary spaces before and
     *              after email. Without any security check.
     * @see UsersManager
     */
    public UserManager(final String email) {
        if (email != null) {
            user = persistance.getUserByEmail(email.trim().toLowerCase());
        } else {
            user = null;
        }

        if (user == null) {
            throw new UserNotFoundException("Can't find user \"" + email + "\"");
        }
    }

    public UserManager(final User user) {
        if (user == null) {
            throw new UserNotFoundException("Can't create logic by null user");
        }
        this.user = user;
    }

    public UserManager(final Integer id) {
        if (id == null) {
            throw new UserNotFoundException("Can't find user by null id.");
        }

        this.user = persistance.getUserById(id);
        if (user == null) {
            throw new UserNotFoundException("Can't find user " + id);
        }
    }

    public void sendActivationOrInvitationEmails() {
        if (user.getActiveted() == null) {
            // Send activation user email.
            final Config config = configStorage.get();
            final International international = internationalStorage.get("createUser", Locale.US);
            mailSender.send(new Mail(
                    user.getEmail(),
                    international.get("confirmMessage",
                            StringUtil.getEmptyOrString(user.getFirstName()),
                            StringUtil.getEmptyOrString(user.getLastName()),
                            config.getApplicationName(),
                            config.getApplicationUrl(),
                            user.getUserId(),
                            createRegistrationCode(),
                            user.getEmail(),
                            user.getPassword(),
                            config.getUserSitesUrl(),
                            config.getSupportEmail()
                    ),
                    international.get("confirmMessageSubject",
                            config.getApplicationName(),
                            StringUtil.getEmptyOrString(user.getFirstName()),
                            StringUtil.getEmptyOrString(user.getLastName())
                    )
            ));
        }


        final Map<String, UserOnSiteRight> invitedRightsByEmails = new HashMap<String, UserOnSiteRight>();
        for (final UserOnSiteRight right : user.getUserOnSiteRights()) {
            if (!right.isActive() && right.getRequesterUserId() != null) {
                final String emailId = right.getCreated() + "" + right.getRequesterUserId();
                invitedRightsByEmails.put(emailId, right);
            }
        }

        for (final String emailId : invitedRightsByEmails.keySet()) {
            final UserOnSiteRight right = invitedRightsByEmails.get(emailId);
            new InviteUserHelper().sendMessageForInvitedUser(
                    user.getEmail().toLowerCase(), right.getInvitationText(), right.getRequesterUserId());
        }

        for (final Integer childSiteSettingsId : user.getChildSiteSettingsId()) {
            final ChildSiteSettings childSiteSettings = persistance.getChildSiteSettingsById(childSiteSettingsId);

            // Child site settings can be null if smb deleted form record from admin iface.
            if (childSiteSettings != null) {
                if (childSiteSettings.getSite() == null) {
                    ChildSiteEmailSender.execute(childSiteSettings, childSiteSettings.getParentSite(), user);
                }
            }
        }
    }

    public String getEmail() {
        return user.getEmail();
    }

    public String getLastName() {
        return user.getLastName();
    }

    public String getFirstName() {
        if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
            return user.getEmail();
        }

        return user.getFirstName();
    }

    public String getFirstLastNamePair() {
        return (StringUtil.isNullOrEmpty(user.getFirstName()) ? "" : user.getFirstName() + " ") +
                (StringUtil.isNullOrEmpty(user.getLastName()) ? "" : user.getLastName());
    }

    public UserRightManager getRight() {
        return new UserRightManager(user);
    }

    public User getUser() {
        return user;
    }

    public int getUserId() {
        return user.getUserId();
    }

    public State getStateEnteredForSite(final int siteId) {
        final UserOnSiteRight userOnSiteRight = persistance.getUserOnSiteRightByUserAndSiteId(user.getUserId(), siteId);
        if (userOnSiteRight != null) {
            for (Integer filledRegistrationFormId : userOnSiteRight.getFilledRegistrationFormIds()) {
                FilledFormItem stateItem = FilledFormManager.getFilledFormItemByFormItemName(filledRegistrationFormId, FormItemName.STATE);
                if (stateItem != null) {
                    return StateManager.parseState(stateItem.getValue());
                }
            }
        }
        return null;
    }

    public <T extends DraftItem> T getSiteItemForEditById(
            final Integer siteItemId, final ItemType itemType) {
        if (siteItemId != null && itemType != null) {
            @SuppressWarnings({"unchecked"})// This is correct, because persistance.getDraftItem`s return type is also <T extends DraftItem>
            final T siteItem = (T) persistance.getDraftItem(siteItemId);
            final SiteOnItemRightType siteOnItemRightType = getRight().toSiteItem(siteItem);
            if (siteOnItemRightType == SiteOnItemRightType.EDIT) {
                return siteItem;
            }
        }
        throw new SiteItemNotFoundException("Can't find site item " + siteItemId);
    }

    public <T extends DraftItem> T getSiteItemForViewById(
            final Integer siteItemId, final ItemType itemType) {
        if (siteItemId != null && itemType != null) {
            @SuppressWarnings({"unchecked"})// This is correct, because persistance.getDraftItem`s return type is also <T extends DraftItem>
            final T siteItem = (T) persistance.getDraftItem(siteItemId);
            final SiteOnItemRightType siteOnItemRightType = getRight().toSiteItem(siteItem);
            if (siteOnItemRightType != null) {
                return siteItem;
            }
        }
        throw new SiteItemNotFoundException("Can't find site item " + siteItemId);
    }

    public VideoRangesManager getVideoRanges() {
        return new VideoRangesManager(this);
    }

    public void updateUserInfoByFilledForm(final FilledForm filledForm) {
        if (filledForm != null) {
            FilledFormItem item;
            item = FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.FIRST_NAME);
            if (item != null && !item.getValues().isEmpty()) {
                user.setFirstName(item.getValues().get(0));
            }

            item = FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.LAST_NAME);
            if (item != null && !item.getValues().isEmpty()) {
                user.setLastName(item.getValues().get(0));
            }

            item = FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.TELEPHONE);
            if (item != null && !item.getValues().isEmpty()) {
                user.setTelephone(item.getValues().get(0));
                user.setTelephone2(item.getValues().get(0));
            }

            item = FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.BILLING_ADDRESS);
            if (item != null && !item.getValues().isEmpty()) {
                user.setBillingAddress1(item.getValues().get(0));
                user.setBillingAddress2(item.getValues().get(0));
            }

            item = FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.POST_CODE);
            if (item != null && !item.getValues().isEmpty()) {
                user.setPostalCode(item.getValues().get(0));
            }
        }
    }

    public String createRegistrationCode() {
        return MD5.crypt("crypt=" + user.getUserId() + "="
                + DateFormat.getDateTimeInstance().format(user.getRegistrationDate()));
    }

    /**
     * User is network if he has only child sites from one network.
     *
     * @return true or false
     */
    public boolean isNetworkUser() {
        final SitesManager sitesManager = getSitesManagerOrCreateNew();
        final int commonSitesCount = sitesManager.getCommonSitesWithoutNetworkAndChildSitesByUserId().size();
        final int networkSitesCount = sitesManager.getNetworkSitesByUserId().size();
        if (commonSitesCount == 0 && networkSitesCount == 0) {
            final List<Site> childSites = sitesManager.getChildSitesByUserId();
            final Set<Integer> networkIds = new HashSet<Integer>();
            // Getting ChildSiteRegistration ids from child sites
            for (Site childSite : childSites) {
                networkIds.add(childSite.getChildSiteSettings().getChildSiteRegistration().getId());
            }
            // Getting ChildSiteRegistration ids from ChildSiteSettings (user may be logined through childSiteRegistration,
            // but has not created site yet).
            for (Integer childSiteSettingsId : user.getChildSiteSettingsId()) {
                final ChildSiteSettings childSiteSettings = ServiceLocator.getPersistance().getChildSiteSettingsById(childSiteSettingsId);
                if (childSiteSettings != null) {
                    networkIds.add(childSiteSettings.getChildSiteRegistration().getId());
                }
            }
            return networkIds.size() == 1;
        }
        return false;
    }

    public ChildSiteSettingsManager getNetworkForNetworkUser() {
        if (!isNetworkUser()) {
            return null;
        } else {
            final SitesManager sitesManager = getSitesManagerOrCreateNew();
            final List<Site> childSites = sitesManager.getChildSitesByUserId();
            // All ChildSiteSettings point to the same ChildSiteRegistration for network user, so we just return first. Tolik
            if (!childSites.isEmpty()) {
                return new ChildSiteSettingsManager(childSites.get(0).getChildSiteSettings());
            } else {
                return new ChildSiteSettingsManager(user.getChildSiteSettingsId().get(0));
            }
        }
    }

    private SitesManager getSitesManagerOrCreateNew() {
        if (sitesManager != null) {
            return sitesManager;
        } else {
            sitesManager = new SitesManager(user.getUserId());
            return sitesManager;
        }
    }

    private final User user;
    private SitesManager sitesManager;
    private final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
    private final InternationalStorage internationalStorage = ServiceLocator.getInternationStorage();
    private final MailSender mailSender = ServiceLocator.getMailSender();
    private final Persistance persistance = ServiceLocator.getPersistance();

}
