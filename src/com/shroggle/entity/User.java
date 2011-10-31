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

import com.shroggle.util.cache.CachePolicy;
import com.shroggle.util.persistance.hibernate.CrypedStringUserType;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/*
 A visitor of user site.
 */
@CachePolicy(maxElementsInMemory = 4000)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DataTransferObject
@TypeDef(name = "cryptedString", typeClass = CrypedStringUserType.class)
@Entity(name = "users")
public class User {

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public List<FilledForm> getFilledForms() {
        return Collections.unmodifiableList(filledForms);
    }

    public void addFilledForm(final FilledForm filledForm) {
        filledForm.setUser(this);
        filledForms.add(filledForm);
    }

    public void removeFilledForm(FilledForm filledForm) {
        filledForms.remove(filledForm);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(final int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getTelephone2() {
        return telephone2;
    }

    public void setTelephone2(String telephone2) {
        this.telephone2 = telephone2;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Deprecated
    // Use persistance.getAllSites instead.
    public List<UserOnSiteRight> getUserOnSiteRights() {
        return userOnSiteRights;
    }

    public void addUserOnSiteRight(final UserOnSiteRight userOnSiteRight) {
        userOnSiteRight.getId().setUser(this);
        userOnSiteRights.add(userOnSiteRight);
    }

    public void removeUserOnSiteRight(final UserOnSiteRight userOnSiteRight) {
        userOnSiteRights.remove(userOnSiteRight);
    }

    public Date getActiveted() {
        return activeted;
    }

    public void setActiveted(Date activeted) {
        this.activeted = activeted;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBillingAddress1() {
        return billingAddress1;
    }

    public void setBillingAddress1(String billingAddress1) {
        this.billingAddress1 = billingAddress1;
    }

    public String getBillingAddress2() {
        return billingAddress2;
    }

    public void setBillingAddress2(String billingAddress2) {
        this.billingAddress2 = billingAddress2;
    }

    public List<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(List<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    public void addCreditCard(CreditCard card) {
        creditCards.add(card);
    }

    public List<Integer> getChildSiteSettingsId() {
        return childSiteSettingsId;
    }

    public void addVideoRange(final GalleryVideoRange videoRange) {
        videoRange.setUser(this);
        videoRanges.add(videoRange);
    }

    public void setChildSiteSettingsId(List<Integer> childSiteSettingsId) {
        this.childSiteSettingsId = childSiteSettingsId;
    }

    public void addChildSiteSettingsId(Integer childSiteSettingsId) {
        this.childSiteSettingsId.add(childSiteSettingsId);
    }

    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    public List<GalleryVideoRange> getVideoRanges() {
        return Collections.unmodifiableList(videoRanges);
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Deprecated
// Use UsersGroupManager to operate with groups. Tolik
public List<UsersGroup> getUsersGroups() {
        return usersGroups;
    }

    @Deprecated
// Use UsersGroupManager to operate with groups. Tolik
public void setUsersGroups(List<UsersGroup> usersGroups) {
        this.usersGroups = usersGroups;
    }

    @Deprecated
// Use UsersGroupManager to operate with groups. Tolik
public void addAccessToGroup(final UsersGroup group) {
        usersGroups.add(group);
    }

    @Override
    public boolean equals(Object o) {
        return this == o || (o instanceof User && userId == ((User) o).userId);
    }

    @Override
    public int hashCode() {
        return userId;
    }

    @Id
    @RemoteProperty
    private int userId;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date activeted;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "id.user")
    private List<UserOnSiteRight> userOnSiteRights = new ArrayList<UserOnSiteRight>();

    @RemoteProperty
    @Column(nullable = false, length = 250, unique = true)
    private String email;

    @RemoteProperty
    @Column(length = 250)
    private String city;

    @RemoteProperty
    @Column(length = 250)
    private String street;

    @RemoteProperty
    @Column(length = 250)
    private String region;

    @RemoteProperty
    @Column(length = 250)
    private String companyName;

    @RemoteProperty
    @Column(length = 250)
    private String unitNumber;

    @RemoteProperty
    @Enumerated(EnumType.STRING)
    @Column(length = 2, nullable = false)
    private Country country = Country.US;

    @RemoteProperty
    @Column(length = 250)
    private String postalCode;

    @Column(length = 250)
    private String billingAddress1;

    @Column(length = 250)
    private String billingAddress2;

    @RemoteProperty
    @Column(length = 100)
    private String telephone2;

    @RemoteProperty
    @Column(length = 100)
    private String fax;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<CreditCard> creditCards = new ArrayList<CreditCard>();

    @CollectionOfElements
    private List<Integer> childSiteSettingsId = new ArrayList<Integer>();

    @Column(length = 250)
    private String firstName;

    @Column(length = 250)
    private String lastName;

    @Column(length = 250)
    private String screenName;

    @Column(length = 250)
    private String telephone;

    @Type(type = "cryptedString")
    @RemoteProperty
    private String password;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date registrationDate = new Date();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<FilledForm> filledForms = new ArrayList<FilledForm>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<GalleryVideoRange> videoRanges = new ArrayList<GalleryVideoRange>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "id.user")
    private List<UsersGroup> usersGroups = new ArrayList<UsersGroup>();

}
