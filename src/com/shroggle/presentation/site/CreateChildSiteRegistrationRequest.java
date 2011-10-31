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

import com.shroggle.entity.DraftFormItem;
import com.shroggle.entity.SiteAccessLevel;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Balakirev Anatoliy
 */
@DataTransferObject
public class CreateChildSiteRegistrationRequest {

    @RemoteProperty
    private Integer widgetId;

    @RemoteProperty
    private String name;

    @RemoteProperty
    private String description;

    @RemoteProperty
    private boolean displayDescription;

    @RemoteProperty
    private int formId;

    @RemoteProperty
    private List<DraftFormItem> formItems;

    @RemoteProperty
    private Integer networkSettingsId;

    @RemoteProperty
    private boolean useStartDate;

    @RemoteProperty
    private boolean useEndDate;

    @RemoteProperty
    private String startDate;

    @RemoteProperty
    private String endDate;

    @RemoteProperty
    private String termsAndConditions;

    @RemoteProperty
    private String emailText;

    @RemoteProperty
    private String welcomeText;

    @RemoteProperty
    private String thanksForRegisteringText;

    @RemoteProperty
    private Integer logoId;

    @RemoteProperty
    private SiteAccessLevel accessLevel;

    @RemoteProperty
    private Double price250mb;

    @RemoteProperty
    private Double price500mb;

    @RemoteProperty
    private Double price1gb;

    @RemoteProperty
    private Double price3gb;

    @RemoteProperty
    List<Integer> templatesId = new ArrayList<Integer>();

    @RemoteProperty
    private boolean requiredToUseSiteBlueprint;

    @RemoteProperty
    private double oneTimeFee;

    @RemoteProperty
    private boolean useOneTimeFee;

    @RemoteProperty
    private String fromEmail;

    @RemoteProperty
    private Integer footerImageId;

    @RemoteProperty
    private String footerText;

    @RemoteProperty
    private String footerUrl;

    @RemoteProperty
    private String brandedUrl;

    @RemoteProperty
    private boolean brandedAllowShroggleDomain;

    @RemoteProperty
    private Integer contactUsPageId;

    @RemoteProperty
    private boolean useOwnAuthorize;

    @RemoteProperty
    private boolean useOwnPaypal;

    @RemoteProperty
    private String authorizeLogin;

    @RemoteProperty
    private String authorizeTransactionKey;

    @RemoteProperty
    private String paypalApiUserName;

    @RemoteProperty
    private String paypalApiPassword;

    @RemoteProperty
    private String paypalSignature;

    public boolean isUseOwnAuthorize() {
        return useOwnAuthorize;
    }

    public void setUseOwnAuthorize(boolean useOwnAuthorize) {
        this.useOwnAuthorize = useOwnAuthorize;
    }

    public boolean isUseOwnPaypal() {
        return useOwnPaypal;
    }

    public void setUseOwnPaypal(boolean useOwnPaypal) {
        this.useOwnPaypal = useOwnPaypal;
    }

    public String getAuthorizeLogin() {
        return authorizeLogin;
    }

    public void setAuthorizeLogin(String authorizeLogin) {
        this.authorizeLogin = authorizeLogin;
    }

    public String getAuthorizeTransactionKey() {
        return authorizeTransactionKey;
    }

    public void setAuthorizeTransactionKey(String authorizeTransactionKey) {
        this.authorizeTransactionKey = authorizeTransactionKey;
    }

    public String getPaypalApiUserName() {
        return paypalApiUserName;
    }

    public void setPaypalApiUserName(String paypalApiUserName) {
        this.paypalApiUserName = paypalApiUserName;
    }

    public String getPaypalApiPassword() {
        return paypalApiPassword;
    }

    public void setPaypalApiPassword(String paypalApiPassword) {
        this.paypalApiPassword = paypalApiPassword;
    }

    public String getPaypalSignature() {
        return paypalSignature;
    }

    public void setPaypalSignature(String paypalSignature) {
        this.paypalSignature = paypalSignature;
    }

    public Integer getContactUsPageId() {
        return contactUsPageId;
    }

    public void setContactUsPageId(Integer contactUsPageId) {
        this.contactUsPageId = contactUsPageId;
    }

    public String getThanksForRegisteringText() {
        return thanksForRegisteringText;
    }

    public void setThanksForRegisteringText(String thanksForRegisteringText) {
        this.thanksForRegisteringText = thanksForRegisteringText;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public double getOneTimeFee() {
        return oneTimeFee;
    }

    public void setOneTimeFee(double oneTimeFee) {
        this.oneTimeFee = oneTimeFee;
    }

    public boolean isUseOneTimeFee() {
        return useOneTimeFee;
    }

    public void setUseOneTimeFee(boolean useOneTimeFee) {
        this.useOneTimeFee = useOneTimeFee;
    }

    public String getEmailText() {
        return emailText;
    }

    public void setEmailText(String emailText) {
        this.emailText = emailText;
    }

    public String getWelcomeText() {
        return welcomeText;
    }

    public void setWelcomeText(String welcomeText) {
        this.welcomeText = welcomeText;
    }

    public Integer getLogoId() {
        return logoId;
    }

    public void setLogoId(Integer logoId) {
        this.logoId = logoId;
    }

    public boolean isRequiredToUseSiteBlueprint() {
        return requiredToUseSiteBlueprint;
    }

    public void setRequiredToUseSiteBlueprint(boolean requiredToUseSiteBlueprint) {
        this.requiredToUseSiteBlueprint = requiredToUseSiteBlueprint;
    }

    public Integer getNetworkSettingsId() {
        return networkSettingsId;
    }

    public void setNetworkSettingsId(Integer networkSettingsId) {
        this.networkSettingsId = networkSettingsId;
    }

    public boolean isUseStartDate() {
        return useStartDate;
    }

    public void setUseStartDate(boolean useStartDate) {
        this.useStartDate = useStartDate;
    }

    public boolean isUseEndDate() {
        return useEndDate;
    }

    public void setUseEndDate(boolean useEndDate) {
        this.useEndDate = useEndDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }

    public SiteAccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(SiteAccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    public Double getPrice250mb() {
        return price250mb;
    }

    public void setPrice250mb(Double price250mb) {
        this.price250mb = price250mb;
    }

    public Double getPrice500mb() {
        return price500mb;
    }

    public void setPrice500mb(Double price500mb) {
        this.price500mb = price500mb;
    }

    public Double getPrice1gb() {
        return price1gb;
    }

    public void setPrice1gb(Double price1gb) {
        this.price1gb = price1gb;
    }

    public Double getPrice3gb() {
        return price3gb;
    }

    public void setPrice3gb(Double price3gb) {
        this.price3gb = price3gb;
    }

    public List<Integer> getTemplatesId() {
        return templatesId;
    }

    public void setTemplatesId(List<Integer> templatesId) {
        this.templatesId = templatesId;
    }

    public List<DraftFormItem> getFormItems() {
        return formItems;
    }

    public void setFormItems(List<DraftFormItem> formItems) {
        this.formItems = formItems;
    }

    public Integer getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(Integer widgetId) {
        this.widgetId = widgetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDisplayDescription() {
        return displayDescription;
    }

    public void setDisplayDescription(boolean displayDescription) {
        this.displayDescription = displayDescription;
    }

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public Integer getFooterImageId() {
        return footerImageId;
    }

    public void setFooterImageId(Integer footerImageId) {
        this.footerImageId = footerImageId;
    }

    public String getFooterText() {
        return footerText;
    }

    public void setFooterText(String footerText) {
        this.footerText = footerText;
    }

    public String getFooterUrl() {
        return footerUrl;
    }

    public void setFooterUrl(String footerUrl) {
        this.footerUrl = footerUrl;
    }

    public String getBrandedUrl() {
        return brandedUrl;
    }

    public void setBrandedUrl(String brandedUrl) {
        this.brandedUrl = brandedUrl;
    }

    public boolean isBrandedAllowShroggleDomain() {
        return brandedAllowShroggleDomain;
    }

    public void setBrandedAllowShroggleDomain(boolean brandedAllowShroggleDomain) {
        this.brandedAllowShroggleDomain = brandedAllowShroggleDomain;
    }

}