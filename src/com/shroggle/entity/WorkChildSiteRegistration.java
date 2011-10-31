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

package com.shroggle.entity;

import org.hibernate.annotations.CollectionOfElements;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "workChildSiteRegistrations")
@Inheritance(strategy = InheritanceType.JOINED)
public class WorkChildSiteRegistration extends WorkForm implements ChildSiteRegistration {

    /*------------------------------------------------Network settings------------------------------------------------*/

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date endDate;

    @Lob
    @Column(nullable = false)
    private String termsAndConditions;

    @Lob
    @Column(nullable = false)
    private String emailText;

    @Lob
    @Column(nullable = false)
    private String welcomeText;

    @Lob
    @Column
    private String thanksForRegisteringText;

    @Column(length = 255)
    private String fromEmail;

    private Integer logoId;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private SiteAccessLevel accessLevel = SiteAccessLevel.ADMINISTRATOR;

    private double price250mb;

    private double price500mb;

    private double price1gb;

    private double price3gb;

    private double oneTimeFee;

    private boolean useOneTimeFee;

    @CollectionOfElements
    private List<Integer> blueprintsId = new ArrayList<Integer>();

    private boolean requiredToUseSiteBlueprint;

    private boolean agreeToTerms = false;

    private Integer footerImageId;

    @Lob
    @Column
    private String footerText;

    @Column(length = 500)
    private String footerUrl;

    @Column(length = 255)
    private String brandedUrl;

    private Integer contactUsPageId;

    private boolean brandedAllowShroggleDomain = true;

    private boolean useOwnAuthorize;

    private boolean useOwnPaypal;

    @Column(length = 255)
    private String authorizeLogin;

    @Column(length = 255)
    private String authorizeTransactionKey;


    @Column(length = 255)
    private String paypalApiUserName;

    @Column(length = 255)
    private String paypalApiPassword;

    @Column(length = 255)
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

    public void setBrandedAllowShroggleDomain(final boolean brandedAllowShroggleDomain) {
        this.brandedAllowShroggleDomain = brandedAllowShroggleDomain;
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

    @Override
    public String getFromEmail() {
        return fromEmail;
    }

    @Override
    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    @Override
    public String getFooterText() {
        return footerText;
    }

    @Override
    public String getFooterUrl() {
        return footerUrl;
    }

    @Override
    public Integer getFooterImageId() {
        return footerImageId;
    }

    public List<Integer> getBlueprintsId() {
        return blueprintsId;
    }

    public void setBlueprintsId(List<Integer> blueprintsId) {
        this.blueprintsId = blueprintsId;
    }

    public boolean isAgreeToTerms() {
        return agreeToTerms;
    }

    public void setAgreeToTerms(boolean agreeToTerms) {
        this.agreeToTerms = agreeToTerms;
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

    public ItemType getItemType() {
        return ItemType.CHILD_SITE_REGISTRATION;
    }

    public boolean isRequiredToUseSiteBlueprint() {
        return requiredToUseSiteBlueprint;
    }

    public void setRequiredToUseSiteBlueprint(boolean requiredToUseSiteBlueprint) {
        this.requiredToUseSiteBlueprint = requiredToUseSiteBlueprint;
    }

    /*------------------------------------------------Network settings------------------------------------------------*/

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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

    public double getPrice250mb() {
        return price250mb;
    }

    public void setPrice250mb(double price250mb) {
        this.price250mb = price250mb;
    }

    public double getPrice500mb() {
        return price500mb;
    }

    public void setPrice500mb(double price500mb) {
        this.price500mb = price500mb;
    }

    public double getPrice1gb() {
        return price1gb;
    }

    public void setPrice1gb(double price1gb) {
        this.price1gb = price1gb;
    }

    public double getPrice3gb() {
        return price3gb;
    }

    public void setPrice3gb(double price3gb) {
        this.price3gb = price3gb;
    }

    public FormType getType() {
        return FormType.CHILD_SITE_REGISTRATION;
    }
    //Network settings

    public void setFooterUrl(String footerUrl) {
        this.footerUrl = footerUrl;
    }

    public void setFooterText(String footerText) {
        this.footerText = footerText;
    }

    public void setFooterImageId(Integer footerImageId) {
        this.footerImageId = footerImageId;
    }

    @Override
    public String getBrandedUrl() {
        return brandedUrl;
    }

    public void setBrandedUrl(String brandedUrl) {
        this.brandedUrl = brandedUrl;
    }

    @Override
    public boolean isBrandedAllowShroggleDomain() {
        return brandedAllowShroggleDomain;
    }
    
    public int getParentSiteId() {
        return this.getSiteId();
    }


}