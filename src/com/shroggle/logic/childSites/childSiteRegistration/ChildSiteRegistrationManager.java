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
package com.shroggle.logic.childSites.childSiteRegistration;

import com.shroggle.entity.*;
import com.shroggle.exception.ChildSiteRegistrationNotFoundException;
import com.shroggle.logic.SiteItemsManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.SynchronizeContext;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.SynchronizeRequest;
import com.shroggle.util.process.synchronize.SynchronizeRequestEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 *         Date: 09.08.2009
 */
public class ChildSiteRegistrationManager {

    public ChildSiteRegistrationManager(DraftChildSiteRegistration childSiteRegistration) {
        if (childSiteRegistration == null) {
            throw new ChildSiteRegistrationNotFoundException("Can`t create ChildSiteRegistrationManager by null ChildSiteRegistration");
        }
        this.childSiteRegistration = childSiteRegistration;
    }

    public static DraftChildSiteRegistration createDefaultChildSiteRegistration(final int siteId) {
        DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        childSiteRegistration.setSiteId(siteId);
        int formId = -1;
        childSiteRegistration.setId(formId);
        childSiteRegistration.setDescription("");
        International international = ServiceLocator.getInternationStorage().get("configureChildSiteRegistration", Locale.US);
        childSiteRegistration.setTermsAndConditions(international.get("defaultTermsAndConditions"));
        childSiteRegistration.setEmailText(international.get("customizeEmailTextBody"));
        childSiteRegistration.setWelcomeText(international.get("customizeWelcomeTextBody"));
        childSiteRegistration.setThanksForRegisteringText(international.get("customizeThanksForRegisteringTextBody"));

        childSiteRegistration.setShowDescription(true);
        childSiteRegistration.setName(SiteItemsManager.getNextDefaultName(ItemType.CHILD_SITE_REGISTRATION, siteId));
        childSiteRegistration.setStartDate(null);
        childSiteRegistration.setEndDate(null);
        childSiteRegistration.setAccessLevel(SiteAccessLevel.ADMINISTRATOR);
        childSiteRegistration.setPrice250mb(15);
        childSiteRegistration.setPrice500mb(18);
        childSiteRegistration.setPrice1gb(20);
        childSiteRegistration.setPrice3gb(25);

        childSiteRegistration.setUseOwnAuthorize(false);
        childSiteRegistration.setUseOwnPaypal(false);
        childSiteRegistration.setPaypalApiUserName("");
        childSiteRegistration.setPaypalApiPassword("");
        childSiteRegistration.setPaypalSignature("");
        childSiteRegistration.setAuthorizeLogin("");
        childSiteRegistration.setAuthorizeTransactionKey("");

        childSiteRegistration.setFooterUrl(ServiceLocator.getConfigStorage().get().getApplicationUrl());
        childSiteRegistration.setFooterText(childSiteRegistration.getName());

        childSiteRegistration.setModified(false);
        return childSiteRegistration;
    }

    public String getSavedEmailTextOrDefault() {
        if (childSiteRegistration.getEmailText() != null) {
            return childSiteRegistration.getEmailText();
        } else {
            return international.get("customizeEmailTextBody");
        }
    }

    public String getSavedTermsAndConditionsTextOrDefault() {
        if (childSiteRegistration.getTermsAndConditions() != null) {
            return childSiteRegistration.getTermsAndConditions();
        } else {
            return international.get("defaultTermsAndConditions");
        }
    }

    public String getSavedWelcomeTextOrDefault() {
        if (childSiteRegistration.getWelcomeText() != null) {
            return childSiteRegistration.getWelcomeText();
        } else {
            return international.get("customizeWelcomeTextBody");
        }
    }

    public String getSavedThanksForRegisteringTextOrDefault() {
        if (childSiteRegistration.getThanksForRegisteringText() != null) {
            return childSiteRegistration.getThanksForRegisteringText();
        } else {
            return international.get("customizeThanksForRegisteringTextBody");
        }
    }

    public String getNormalizedThanksForRegisteringText() {
        return getSavedThanksForRegisteringTextOrDefault().replace("&lt;network site name&gt;", childSiteRegistration.getName());
    }

    public static String createEmailBodyByCustomEmailText(String customEmailText, final String firstName,
                                                          final String lastName, final String parentSiteName,
                                                          final String startDate, final String endDate, final String price,
                                                          final String childSiteRegistrationName, final String link,
                                                          final boolean useOneTimeFee) {
        customEmailText = customEmailText.replace("&lt;first name&gt;", firstName);
        customEmailText = customEmailText.replace("&lt;last name&gt;", lastName);
        customEmailText = customEmailText.replace("&lt;site name (where child site reg is displayed)&gt;", parentSiteName);
        customEmailText = customEmailText.replace("&lt;network site name&gt;", parentSiteName);
        if (startDate.isEmpty()) {
            customEmailText = customEmailText.replace("(if applicable) Start Date: &lt;start date or today`s date&gt;<br>", startDate);
        } else {
            customEmailText = customEmailText.replace("(if applicable)", "");
            customEmailText = customEmailText.replace("&lt;start date or today`s date&gt;", startDate);
        }
        if (endDate.isEmpty()) {
            customEmailText = customEmailText.replace("End Date: &lt;end date specified in network settings.&gt;<br>", "");
        } else {
            customEmailText = customEmailText.replace("&lt;end date specified in network settings.&gt;", endDate);
        }
        customEmailText = customEmailText.replace("&lt;fee for membership at network price&gt;", price);
        if (useOneTimeFee) {
            customEmailText = customEmailText.replace("&lt;per month/one time fee&gt; (250mb media storage included)", "one time fee");
        } else {
            customEmailText = customEmailText.replace("&lt;per month/one time fee&gt;", "per month");
        }

        final boolean linkTextExist = customEmailText.indexOf("&lt;account activation link&gt;") > 0;
        if (linkTextExist) {
            customEmailText = customEmailText.replace("&lt;account activation link&gt;",
                    "<a href='" + link + "'>account activation link</a>");
        }
        customEmailText = customEmailText.replace("&lt;Name of Membership option&gt;", childSiteRegistrationName);

        if (!linkTextExist) {
            customEmailText +=
                    "<br><br>The following link will take you to confirmation and welcome message page of the " +
                            "site builder.<br><br> To verify your email click here " + "<a href='" + link + "'>" +
                            "account activation link</a>" + "<br><br>";
        }
        return customEmailText;
    }


    public static boolean showFormsForEdit(final Widget widget, final boolean forceFormShowing) {
        final User user = new UsersManager().getLoginedUser();
        if (user == null || forceFormShowing) {
            return false;
        } else {
            final Persistance persistance = ServiceLocator.getPersistance();
            final DraftChildSiteRegistration form = (DraftChildSiteRegistration) ((WidgetItem) widget).getDraftItem();
            return form != null && persistance.getFilledFormsByFormAndUserId(form.getFormId(), user.getUserId()).size() > 0;
        }
    }

    public static boolean showYouHaveNotFilledThisFormMessage(final Integer widgetId) {
        final Persistance persistance = ServiceLocator.getPersistance();
        final User user = new UsersManager().getLoginedUser();
        Widget widget = persistance.getWidget(widgetId);
        if (user != null && widget != null && widget.isWidgetItem()) {
            final DraftChildSiteRegistration form = (DraftChildSiteRegistration) ((WidgetItem) widget).getDraftItem();
            return form != null && persistance.getFilledFormsByFormAndUserId(form.getFormId(), user.getUserId()).size() == 0;
        }
        return false;
    }

    public Site getDefaultBlueprint() {
        removeWrongBlueprintsId();
        // We can get a default blueprint if only one blueprint is saved in child site registration.
        if (childSiteRegistration.getBlueprintsId().isEmpty() || childSiteRegistration.getBlueprintsId().size() > 1) {
            return null;
        }
        return persistance.getSite(childSiteRegistration.getBlueprintsId().get(0));
    }

    public List<Site> getUsedBlueprints() {
        removeWrongBlueprintsId();
        if (childSiteRegistration.getBlueprintsId().isEmpty()) {
            return Collections.emptyList();
        }
        final List<Site> blueprints = new ArrayList<Site>();
        for (Integer blueprintId : childSiteRegistration.getBlueprintsId()) {
            blueprints.add(persistance.getSite(blueprintId));
        }
        return blueprints;
    }

    protected void removeWrongBlueprintsId() {
        if (childSiteRegistration.getBlueprintsId().isEmpty()) {
            return;
        }
        final List<Integer> wrongBlueprintsId = new ArrayList<Integer>();
        for (Integer blueprintId : childSiteRegistration.getBlueprintsId()) {
            final Site blueprint = persistance.getSite(blueprintId);
            if (blueprint != null) {
                if (blueprint.getType() != SiteType.BLUEPRINT) {
                    wrongBlueprintsId.add(blueprintId);
                    logger.severe("Common sites id contains in the childSiteRegistrations \"blueprintsId\" list, " +
                            "which should contains only blueprints id.\n" +
                            " childSiteRegistrationId = " + childSiteRegistration.getId() + "\n" +
                            " common siteId (which should be blueprint) = " + blueprintId + "\n " +
                            " Removing this siteId from blueprintsId list...");
                }
            } else {
                logger.severe("ChildSiteRegistration (id = " + childSiteRegistration.getId() + ")" +
                        " contains in the blueprintsId link to removed blueprint. Check site deletion mechanism! " +
                        "This id (" + blueprintId + ") will be removed from childSiteRegistration.");
                wrongBlueprintsId.add(blueprintId);
            }
        }
        removeWrongBlueprintsId(wrongBlueprintsId);
    }

    private void removeWrongBlueprintsId(final List<Integer> wrongBlueprintsId) {
        if (wrongBlueprintsId == null || wrongBlueprintsId.isEmpty()) {
            return;
        }
        try {
            final SynchronizeRequest synchronizeRequest = new SynchronizeRequestEntity(
                    ChildSiteRegistration.class, SynchronizeMethod.WRITE, childSiteRegistration.getId());
            ServiceLocator.getSynchronize().execute(synchronizeRequest, new SynchronizeContext<Void>() {
                public Void execute() {
                    persistanceTransaction.execute(new Runnable() {
                        public void run() {
                            final List<Integer> blueprintsId = new ArrayList<Integer>(childSiteRegistration.getBlueprintsId());
                            blueprintsId.removeAll(wrongBlueprintsId);
                            childSiteRegistration.setBlueprintsId(blueprintsId);
                        }
                    });
                    return null;
                }
            });
        } catch (Exception exception) {
            logger.log(Level.SEVERE, "Can't remove wrong blueprintsId from childSiteRegistration!\n" +
                    " childSiteRegistrationId = " + childSiteRegistration.getId() + "\n" +
                    " wrong blueprints id = " + wrongBlueprintsId,
                    exception);
        }
    }

    public boolean isUseOwnAuthorize() {
        return childSiteRegistration.isUseOwnAuthorize();
    }

    public void setUseOwnAuthorize(boolean useOwnAuthorize) {
        this.childSiteRegistration.setUseOwnAuthorize(useOwnAuthorize);
    }

    public boolean isUseOwnPaypal() {
        return childSiteRegistration.isUseOwnPaypal();
    }

    public void setUseOwnPaypal(boolean useOwnPaypal) {
        this.childSiteRegistration.setUseOwnPaypal(useOwnPaypal);
    }

    public String getAuthorizeLogin() {
        return StringUtil.getEmptyOrString(childSiteRegistration.getAuthorizeLogin());
    }

    public void setAuthorizeLogin(String authorizeLogin) {
        this.childSiteRegistration.setAuthorizeLogin(authorizeLogin);
    }

    public String getAuthorizeTransactionKey() {
        return StringUtil.getEmptyOrString(childSiteRegistration.getAuthorizeTransactionKey());
    }

    public void setAuthorizeTransactionKey(String authorizeTransactionKey) {
        this.childSiteRegistration.setAuthorizeTransactionKey(authorizeTransactionKey);
    }

    public String getPaypalApiUserName() {
        return StringUtil.getEmptyOrString(childSiteRegistration.getPaypalApiUserName());
    }

    public void setPaypalApiUserName(String paypalApiUserName) {
        this.childSiteRegistration.setPaypalApiUserName(paypalApiUserName);
    }

    public String getPaypalApiPassword() {
        return StringUtil.getEmptyOrString(childSiteRegistration.getPaypalApiPassword());
    }

    public void setPaypalApiPassword(String paypalApiPassword) {
        this.childSiteRegistration.setPaypalApiPassword(paypalApiPassword);
    }

    public String getPaypalSignature() {
        return StringUtil.getEmptyOrString(childSiteRegistration.getPaypalSignature());
    }

    public void setPaypalSignature(String paypalSignature) {
        this.childSiteRegistration.setPaypalSignature(paypalSignature);
    }

    public int getId() {
        return this.childSiteRegistration.getId();
    }

    public String getName() {
        return StringUtil.getEmptyOrString(this.childSiteRegistration.getName());
    }

    public String getFromEmail() {
        return StringUtil.getEmptyOrString(this.childSiteRegistration.getFromEmail());
    }

    public Integer getFooterImageId() {
        return childSiteRegistration.getFooterImageId();
    }

    public boolean isBrandedAllowShroggleDomain() {
        return childSiteRegistration.isBrandedAllowShroggleDomain();
    }

    public Integer getContactUsPageId() {
        return childSiteRegistration.getContactUsPageId();
    }

    public String getBrandedUrl() {
        return StringUtil.getEmptyOrString(childSiteRegistration.getBrandedUrl());
    }

    public String getFooterText() {
        if (StringUtil.isNullOrEmpty(childSiteRegistration.getFooterText())) {
            return getName();
        } else {
            return childSiteRegistration.getFooterText();
        }
    }

    public String getFooterUrl() {
        if (StringUtil.isNullOrEmpty(childSiteRegistration.getFooterUrl())) {
            return ServiceLocator.getConfigStorage().get().getApplicationUrl();
        } else {
            return childSiteRegistration.getFooterUrl();
        }
    }

    private final DraftChildSiteRegistration childSiteRegistration;
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final International international = ServiceLocator.getInternationStorage().get("configureChildSiteRegistration", Locale.US);
}
