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

package com.shroggle.presentation.user.create;


public final class CreateUserRequest {

    public void setNotWantNewUser(final boolean notWantNewUser) {
        this.notWantNewUser = notWantNewUser;
    }

    public boolean isNotWantNewUser() {
        return notWantNewUser;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setTelephone(final String telephone) {
        this.telephone = telephone;
    }

    public void setPass(final String pass) {
        this.pass = pass;
    }

    public void setPasswordConfirm(final String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public boolean isAgree() {
        return agree;
    }

    public void setAgree(boolean agree) {
        this.agree = agree;
    }

    public String getNoBotCodeConfirm() {
        return noBotCodeConfirm;
    }

    public void setNoBotCodeConfirm(String noBotCodeConfirm) {
        this.noBotCodeConfirm = noBotCodeConfirm;
    }

    public String getEmailConfirm() {
        return emailConfirm;
    }

    public void setEmailConfirm(String emailConfirm) {
        this.emailConfirm = emailConfirm;
    }

    public String getPass() {
        return pass;
    }

    public CreateUserMode getMode() {
        return mode;
    }

    public void setMode(final CreateUserMode mode) {
        this.mode = mode;
    }

    public boolean isFirstShow() {
        return firstShow;
    }

    public String getOriginalEmail() {
        return originalEmail;
    }

    public void setOriginalEmail(String originalEmail) {
        this.originalEmail = originalEmail;
    }

    public String getOriginalTelephone() {
        return originalTelephone;
    }

    public void setOriginalTelephone(String originalTelephone) {
        this.originalTelephone = originalTelephone;
    }

    public String getOriginalPassword() {
        return originalPassword;
    }

    public void setOriginalPassword(String originalPassword) {
        this.originalPassword = originalPassword;
    }

    public String getOriginalPasswordConfirm() {
        return originalPasswordConfirm;
    }

    public void setOriginalPasswordConfirm(String originalPasswordConfirm) {
        this.originalPasswordConfirm = originalPasswordConfirm;
    }

    public String getOriginalEmailConfirm() {
        return originalEmailConfirm;
    }

    public void setOriginalEmailConfirm(String originalEmailConfirm) {
        this.originalEmailConfirm = originalEmailConfirm;
    }

    public String getOriginalFirstName() {
        return originalFirstName;
    }

    public void setOriginalFirstName(String originalFirstName) {
        this.originalFirstName = originalFirstName;
    }

    public String getOriginalLastName() {
        return originalLastName;
    }

    public void setOriginalLastName(String originalLastName) {
        this.originalLastName = originalLastName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getInvitedUserId() {
        return invitedUserId;
    }

    public void setInvitedUserId(final Integer invitedUserId) {
        this.invitedUserId = invitedUserId;
    }

    /**
     * See detail description on varibale.
     *
     * @param firstShow - not used. It need for call this method from stripes.
     */
    @SuppressWarnings({"UnusedDeclaration"})
    public void setFirstShow(final boolean firstShow) {
        this.firstShow = false;
    }

    public String getConfirmCode() {
        return confirmCode;
    }

    public void setConfirmCode(final String confirmCode) {
        this.confirmCode = confirmCode;
    }

    public boolean isShowWrongUrlMessage() {
        return showWrongUrlMessage;
    }

    public void setShowWrongUrlMessage(boolean showWrongUrlMessage) {
        this.showWrongUrlMessage = showWrongUrlMessage;
    }

    public String getHasAccessToSitesMessage() {
        return hasAccessToSitesMessage;
    }

    public void setHasAccessToSitesMessage(String hasAccessToSitesMessage) {
        this.hasAccessToSitesMessage = hasAccessToSitesMessage;
    }

    private String email;
    private String telephone;
    private String pass;
    private String passwordConfirm;
    private String emailConfirm;
    private String firstName;
    private String lastName;
    private String postalCode;
    
    /**
     * This is field used only for invited existed users. Default value is true. In this case
     * exist user can't edit his field. It is can edit only email. If value false. Exist invited user
     * want new user in this case his can edit any fields. Why is this field "not"? It is need for jsp.
     * For other mode this field not use.
     */
    private boolean notWantNewUser;

    /**
     * This field uses for find all allow sites and activate their.
     * This field newer change value.
     */
    private Integer userId;

    /**
     * This user action use for accept registration data.
     * If user want new user this field not used. When action create
     * new user it set his id in this field.
     */
    private Integer invitedUserId;

    /**
     * Special field for check correct invite information.
     */
    private String confirmCode;
    private String originalEmail;
    private String originalTelephone;
    private String originalPassword;
    private String originalPasswordConfirm;
    private String originalEmailConfirm;
    private String originalFirstName;
    private String originalLastName;

    /**
     * If this parameter false create account page show before submit
     * terms and user must click OK or ... For invited users this field
     * set in true automaticay when page is show
     */
    private boolean agree;
    private String noBotCodeConfirm;
    /**
     * This parameter true only in first show, after reload it must be always false.
     * This parameter stored on page as false.
     */
    private boolean firstShow = true;
    /**
     * State for create account. This field setting in first show and after
     * not changes.
     */
    private CreateUserMode mode = CreateUserMode.NEW;


    private boolean showWrongUrlMessage = false;

    private String hasAccessToSitesMessage;

}