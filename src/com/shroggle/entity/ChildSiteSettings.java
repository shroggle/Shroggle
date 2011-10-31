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

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "childSiteSettings")
public class ChildSiteSettings implements PaymentSettingsOwner {

    @Id
    private int childSiteSettingsId;

    @ManyToOne
    @JoinColumn(name = "parentSiteId")
    @ForeignKey(name = "childSiteSettingsParentSite")
    private Site parentSite;

    @OneToOne
    @JoinColumn(name = "siteId")
    @ForeignKey(name = "childSiteSettingsSite")
    private Site site;

    @OneToOne
    @JoinColumn(name = "childSiteRegistrationId")
    @ForeignKey(name = "childSiteSettingsChildSiteRegistrationId")
    private DraftChildSiteRegistration childSiteRegistration;

    @Column(nullable = false)
    private int userId;

    private double price250mb;

    private double price500mb;

    private double price1gb;

    private double price3gb;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date endDate;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SiteAccessLevel accessLevel = SiteAccessLevel.ADMINISTRATOR;

    @Column(length = 250)
    private String confirmCode;

    @OneToOne
    @JoinColumn(name = "sitePaymentSettingsId")
    @ForeignKey(name = "childSiteSettingsSitePaymentSettingsId")
    private SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();

    private boolean requiredToUseSiteBlueprint;

    @Column(nullable = false)
    private boolean canBePublishedMessageSent;

    @Lob
    private String termsAndConditions;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date notificationMailSent;

    @Column(nullable = false)
    private int filledFormId;

    @Lob
    @Column(nullable = false)
    private String welcomeText;

    @Column(length = 255)
    private String fromEmail;

    private Integer logoId;

    private double oneTimeFee;

    private boolean useOneTimeFee;

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

    public int getFilledFormId() {
        return filledFormId;
    }

    public void setFilledFormId(int filledFormId) {
        if (site != null) {
            site.setChildSiteFilledFormId(filledFormId);
        }
        this.filledFormId = filledFormId;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Date getNotificationMailSent() {
        return notificationMailSent;
    }

    public void setNotificationMailSent(Date notificationMailSent) {
        this.notificationMailSent = notificationMailSent;
    }

    public boolean isCanBePublishedMessageSent() {
        return canBePublishedMessageSent;
    }

    public void setCanBePublishedMessageSent(boolean canBePublishedMessageSent) {
        this.canBePublishedMessageSent = canBePublishedMessageSent;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }

    public Site getParentSite() {
        return parentSite;
    }

    public void setParentSite(Site parentSite) {
        this.parentSite = parentSite;
    }

    public boolean isRequiredToUseSiteBlueprint() {
        return requiredToUseSiteBlueprint;
    }

    public void setRequiredToUseSiteBlueprint(boolean requiredToUseSiteBlueprint) {
        this.requiredToUseSiteBlueprint = requiredToUseSiteBlueprint;
    }

    public DraftChildSiteRegistration getChildSiteRegistration() {
        return childSiteRegistration;
    }

    public void setChildSiteRegistration(DraftChildSiteRegistration childSiteRegistration) {
        this.childSiteRegistration = childSiteRegistration;
    }

    public int getUserId() {
        return userId;
    }

    public SitePaymentSettings getSitePaymentSettings() {
        return sitePaymentSettings;
    }

    public void setSitePaymentSettings(SitePaymentSettings sitePaymentSettings) {
        this.sitePaymentSettings = sitePaymentSettings;
    }

    @Override
    public int getId() {
        return childSiteSettingsId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    //todo. remove this method. Tolik
    public SiteStatus getSiteStatus() {
        return sitePaymentSettings.getSiteStatus();
    }

    //todo. remove this method. Tolik
    public void setSiteStatus(SiteStatus siteStatus) {
        sitePaymentSettings.setSiteStatus(siteStatus);
    }

    public String getConfirmCode() {
        return confirmCode;
    }

    public void setConfirmCode(String confirmCode) {
        this.confirmCode = confirmCode;
    }

    public int getChildSiteSettingsId() {
        return childSiteSettingsId;
    }

    public void setChildSiteSettingsId(int childSiteSettingsId) {
        this.childSiteSettingsId = childSiteSettingsId;
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

    public SiteAccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(SiteAccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }
}