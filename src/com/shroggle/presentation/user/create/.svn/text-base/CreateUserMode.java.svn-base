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

/**
 * @author Artem Stasuk
 */
public enum CreateUserMode {

    NEW(
            new CreateUserShowNo(),
            new CreateUserRedirectFinishRegistration(),
            new CreateUserFullValidate(new CreateUserValidateEmail()),
            new CreateUserYesCreateUser(new CreateUserUpdateUserFull()),
            new CreateUserRightNo(),
            new CreateUserLoginNo()
    ),

    NEW_WITHOUT_CONFIRM(
            new CreateUserShowNo(),
            new CreateUserRedirectFinishRegistration(),
            new CreateUserFullValidate(new CreateUserValidateEmail()),
            new CreateUserYesCreateUser(new CreateUserUpdateUserAndActivateIfNeed(new CreateUserUpdateUserFull())),
            new CreateUserRightNo(),
            new CreateUserLoginYes()
    ),

    INVITED_EXSIST(
            new CreateUserShowFull(),
            new CreateUserRedirectDashboard(),
            new CreateUserValidateNoBotCode(),
            new CreateUserNoCreateUser(new CreateUserUpdateUserAndActivateIfNeed(new CreateUserUpdateUserNo())),
            new CreateUserRightActivate(),
            new CreateUserLoginYes()
    ),

    INVITED_EXSIST_WANT_NEW(
            new CreateUserShowNo(),
            new CreateUserRedirectFinishRegistration(),
            new CreateUserFullValidate(new CreateUserValidateEmail()),
            new CreateUserYesCreateUser(new CreateUserUpdateUserFull()),
            new CreateUserRightMoveAndActivate(),
            new CreateUserLoginNo()
    ),

    INVITED_NEW(
            new CreateUserShowSimple(),
            new CreateUserRedirectDashboard(),
            new CreateUserFullValidate(new CreateUserValidateEmailWithUser()),
            new CreateUserNoCreateUser(new CreateUserUpdateUserAndActivateIfNeed(new CreateUserUpdateUserFull())),
            new CreateUserRightActivate(),
            new CreateUserLoginYes()
    ),

    INVITED_DELETE(
            new CreateUserShowSimple(),
            new CreateUserRedirectCreateSite(),
            new CreateUserFullValidate(new CreateUserValidateEmailWithUser()),
            new CreateUserNoCreateUser(new CreateUserUpdateUserAndActivateIfNeed(new CreateUserUpdateUserFull())),
            new CreateUserRightNo(),
            new CreateUserLoginYes()
    ),

    NEW_INVITED_WITH_NEW_EMAIL(
            new CreateUserShowNo(),
            new CreateUserRedirectFinishRegistration(),
            new CreateUserFullValidate(new CreateUserValidateEmailWithUser()),
            new CreateUserNoCreateUser(new CreateUserUpdateUserFull()),
            new CreateUserRightActivate(),
            new CreateUserLoginNo()
    ),

    DELETE_INVITED_WITH_NEW_EMAIL(
            new CreateUserShowNo(),
            new CreateUserRedirectFinishRegistration(),
            new CreateUserFullValidate(new CreateUserValidateEmailWithUser()),
            new CreateUserYesCreateUser(new CreateUserUpdateUserFull()),
            new CreateUserRightCreateAndActivate(),
            new CreateUserLoginNo()
    );

    CreateUserMode(
            final CreateUserShow show,
            final CreateUserRedirect redirect,
            final CreateUserValidate validate,
            final CreateUserCreateUser createUser,
            final CreateUserRight userRight,
            final CreateUserLogin login) {
        this.redirect = redirect;
        this.login = login;
        this.show = show;
        this.createUser = createUser;
        this.validate = validate;
        this.userRight = userRight;
    }

    public CreateUserValidate getValidate() {
        return validate;
    }

    public CreateUserRedirect getRedirect() {
        return redirect;
    }

    public CreateUserRight getRight() {
        return userRight;
    }

    public CreateUserCreateUser getCreateUser() {
        return createUser;
    }

    public CreateUserShow getShow() {
        return show;
    }

    public boolean isInvited() {
        return this != NEW && this != NEW_WITHOUT_CONFIRM;
    }

    public CreateUserLogin getLogin() {
        return login;
    }

    public boolean isInvitedExist() {
        return this == INVITED_EXSIST || this == INVITED_EXSIST_WANT_NEW;
    }

    private final CreateUserValidate validate;
    private final CreateUserRedirect redirect;
    private final CreateUserLogin login;
    private final CreateUserShow show;
    private final CreateUserRight userRight;
    private final CreateUserCreateUser createUser;

}
